// Déclaration du paquet de ce fichier
package net.minestom.server.extras.query;

// Import d'une classe nécessaire
import io.netty.bootstrap.Bootstrap;
// Import d'une classe nécessaire
import io.netty.buffer.ByteBuf;
// Import d'une classe nécessaire
import io.netty.buffer.Unpooled;
// Import d'une classe nécessaire
import io.netty.channel.*;
// Import d'une classe nécessaire
import io.netty.channel.nio.NioEventLoopGroup;
// Import d'une classe nécessaire
import io.netty.channel.socket.DatagramPacket;
// Import d'une classe nécessaire
import io.netty.channel.socket.nio.NioDatagramChannel;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.extras.query.event.BasicQueryEvent;
// Import d'une classe nécessaire
import net.minestom.server.extras.query.event.FullQueryEvent;
// Import d'une classe nécessaire
import net.minestom.server.extras.query.response.BasicQueryResponse;
// Import d'une classe nécessaire
import net.minestom.server.extras.query.response.FullQueryResponse;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.timer.Task;
// Import d'une classe nécessaire
import net.minestom.server.utils.time.TimeUnit;
// Import d'une classe nécessaire
import org.slf4j.Logger;
// Import d'une classe nécessaire
import org.slf4j.LoggerFactory;

// Import d'une classe nécessaire
import java.net.InetSocketAddress;
// Import d'une classe nécessaire
import java.net.SocketAddress;
// Import d'une classe nécessaire
import java.nio.charset.Charset;
// Import d'une classe nécessaire
import java.nio.charset.StandardCharsets;
// Import d'une classe nécessaire
import java.util.Random;

/**
 * GameSpy4 Query Protocol implementation backed by Netty UDP rather than
 * {@code java.net.DatagramSocket}.
 *
 * <p>No {@code java.nio.channels.*} or {@code sun.misc.Unsafe} references;
 * raw byte I/O is performed through Netty's {@link ByteBuf}.
 *
 * @see <a href="https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Query">
 *     Minecraft wiki – Query protocol</a>
 */
// Déclaration de type (classe/interface/enum/record)
public class Query {

    // Affecte une valeur
    public static final Charset CHARSET = StandardCharsets.ISO_8859_1;

    // Appelle une méthode
    private static final Logger LOGGER   = LoggerFactory.getLogger(Query.class);
    // Appelle une méthode
    private static final Random RANDOM   = new Random();

    /** challenge-token -> sender address */
    // Affecte une valeur
    private static final Int2ObjectMap<SocketAddress> CHALLENGE_TOKENS =
            // Appelle une méthode
            Int2ObjectMaps.synchronize(new Int2ObjectOpenHashMap<>());

    // Instruction de code
    private static volatile boolean       started;
    // Instruction de code
    private static volatile Channel       udpChannel;
    // Instruction de code
    private static volatile EventLoopGroup eventLoopGroup;
    // Instruction de code
    private static volatile Task          task;

    // Instruction de code
    private Query() {}

    /**
     * Starts the query system on an OS-assigned port.
     *
     * @return the bound port
     * @throws IllegalArgumentException if already running
     */
    // Début d'une méthode/d'un bloc
    public static int start() {
        // Embranchement : vérifie une condition
        if (udpChannel != null) throw new IllegalArgumentException("System is already running");
        // Appelle une méthode
        start(0);
        // Renvoie une valeur à l'appelant
        return ((InetSocketAddress) udpChannel.localAddress()).getPort();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Starts the query system on the given {@code port}.
     *
     * @return {@code true} on success, {@code false} if already running or
     *         if the port could not be bound
     */
    // Début d'une méthode/d'un bloc
    public static boolean start(int port) {
        // Embranchement : vérifie une condition
        if (udpChannel != null) return false;

        // Appelle une méthode
        final EventLoopGroup group = new NioEventLoopGroup(1);
        // Affecte une valeur
        final Bootstrap bootstrap = new Bootstrap()
                // Instruction de code
                .group(group)
                // Instruction de code
                .channel(NioDatagramChannel.class)
                // Appelle une méthode
                .handler(new QueryHandler());

        // Gestion des exceptions
        try {
            // Appelle une méthode
            udpChannel      = bootstrap.bind(port).sync().channel();
            // Affecte une valeur
            eventLoopGroup  = group;
            // Affecte une valeur
            started         = true;
        // Début d'une méthode/d'un bloc
        } catch (Exception e) {
            // Appelle une méthode
            LOGGER.warn("Could not open the query port!", e);
            // Appelle une méthode
            group.shutdownGracefully();
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        task = MinecraftServer.getSchedulerManager()
                // Instruction de code
                .buildTask(CHALLENGE_TOKENS::clear)
                // Instruction de code
                .repeat(30, TimeUnit.SECOND)
                // Appelle une méthode
                .schedule();

        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Stops the query system.
     *
     * @return {@code true} if it was running, {@code false} otherwise
     */
    // Début d'une méthode/d'un bloc
    public static boolean stop() {
        // Embranchement : vérifie une condition
        if (!started) return false;

        // Affecte une valeur
        started = false;
        // Embranchement : vérifie une condition
        if (udpChannel != null) {
            // Appelle une méthode
            udpChannel.close().awaitUninterruptibly(); udpChannel = null;
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (eventLoopGroup != null) {
            // Appelle une méthode
            eventLoopGroup.shutdownGracefully();        eventLoopGroup = null;
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (task != null) {
            // Appelle une méthode
            task.cancel(); task = null;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        CHALLENGE_TOKENS.clear();
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    /** @return {@code true} if the query system is currently running */
    // Début d'une méthode/d'un bloc
    public static boolean isStarted() {
        // Renvoie une valeur à l'appelant
        return started;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Handles inbound UDP datagrams and replies inline on the same Netty
     * event-loop thread — no extra thread needed.
     */
    // Annotation pour l'élément suivant
    @ChannelHandler.Sharable
    // Début d'une méthode/d'un bloc
    private static final class QueryHandler extends SimpleChannelInboundHandler<DatagramPacket> {

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) {
            // Appelle une méthode
            final ByteBuf data   = msg.content();
            // Appelle une méthode
            final InetSocketAddress sender = msg.sender();

            // Check magic 0xFEFD
            // Embranchement : vérifie une condition
            if (data.readableBytes() < 3) return;
            // Appelle une méthode
            final int magic = data.readUnsignedShort();
            // Embranchement : vérifie une condition
            if (magic != 0xFEFD) return;

            // Appelle une méthode
            final byte type = data.readByte();

            // Embranchement : vérifie une condition
            if (type == 9) { // handshake
                // Embranchement : vérifie une condition
                if (data.readableBytes() < 4) return;
                // Appelle une méthode
                final int sessionID      = data.readInt();
                // Appelle une méthode
                final int challengeToken = RANDOM.nextInt();
                // Appelle une méthode
                CHALLENGE_TOKENS.put(challengeToken, sender);

                // Affecte une valeur
                final byte[] responseBytes = NetworkBuffer.makeArray(buf -> {
                    // Appelle une méthode
                    buf.write(NetworkBuffer.BYTE, (byte) 9);
                    // Appelle une méthode
                    buf.write(NetworkBuffer.INT, sessionID);
                    // Appelle une méthode
                    buf.write(NetworkBuffer.STRING_TERMINATED, String.valueOf(challengeToken));
                // Fin d'un bloc/d'une expression
                });

                // Appelle une méthode
                send(ctx, sender, responseBytes);

            // Embranchement : vérifie une condition
            } else if (type == 0) { // stat
                // Embranchement : vérifie une condition
                if (data.readableBytes() < 8) return;
                // Appelle une méthode
                final int sessionID      = data.readInt();
                // Appelle une méthode
                final int challengeToken = data.readInt();
                // Appelle une méthode
                final int remaining      = data.readableBytes();

                // Embranchement : vérifie une condition
                if (!CHALLENGE_TOKENS.containsKey(challengeToken)
                        // Appelle une méthode
                        || !CHALLENGE_TOKENS.get(challengeToken).equals(sender)) return;

                // Embranchement : vérifie une condition
                if (remaining == 0) { // basic query
                    // Appelle une méthode
                    final BasicQueryEvent event = new BasicQueryEvent(sender, sessionID);
                    // Instruction de code
                    EventDispatcher.callCancellable(event, () ->
                            // Instruction de code
                            sendQueryResponse(ctx, BasicQueryResponse.SERIALIZER,
                                    // Appelle une méthode
                                    event.getQueryResponse(), sessionID, sender));
                // Embranchement : vérifie une condition
                } else if (remaining == 5) { // full query
                    // Appelle une méthode
                    final FullQueryEvent event = new FullQueryEvent(sender, sessionID);
                    // Instruction de code
                    EventDispatcher.callCancellable(event, () ->
                            // Instruction de code
                            sendQueryResponse(ctx, FullQueryResponse.SERIALIZER,
                                    // Appelle une méthode
                                    event.getQueryResponse(), sessionID, sender));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            // Embranchement : vérifie une condition
            if (started) {
                // Appelle une méthode
                LOGGER.error("Error in query handler", cause);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static <T> void sendQueryResponse(ChannelHandlerContext ctx,
                                              // Instruction de code
                                              NetworkBuffer.Type<T> type, T response,
                                              // Début d'une méthode/d'un bloc
                                              int sessionID, InetSocketAddress sender) {
        // Affecte une valeur
        final byte[] payload = NetworkBuffer.makeArray(buf -> {
            // Appelle une méthode
            buf.write(NetworkBuffer.BYTE, (byte) 0);
            // Appelle une méthode
            buf.write(NetworkBuffer.INT, sessionID);
            // Appelle une méthode
            buf.write(type, response);
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        send(ctx, sender, payload);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static void send(ChannelHandlerContext ctx,
                             // Début d'une méthode/d'un bloc
                             InetSocketAddress recipient, byte[] data) {
        // Appelle une méthode
        final ByteBuf buf = ctx.alloc().buffer(data.length).writeBytes(data);
        // Instruction de code
        ctx.writeAndFlush(new DatagramPacket(buf, recipient))
                // Début d'une méthode/d'un bloc
                .addListener(f -> {
                    // Embranchement : vérifie une condition
                    if (!f.isSuccess() && started) {
                        // Appelle une méthode
                        LOGGER.error("Failed to send query response to {}", recipient, f.cause());
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                });
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}