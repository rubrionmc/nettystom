// Déclaration du paquet de ce fichier
package net.minestom.server.network.player;

// Import d'une classe nécessaire
import io.netty.buffer.ByteBuf;
// Import d'une classe nécessaire
import io.netty.channel.Channel;
// Import d'une classe nécessaire
import io.netty.channel.ChannelDuplexHandler;
// Import d'une classe nécessaire
import io.netty.channel.ChannelHandlerContext;
// Import d'une classe nécessaire
import io.netty.channel.ChannelPromise;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.adventure.MinestomAdventure;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.ListenerHandle;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerPacketOutEvent;
// Import d'une classe nécessaire
import net.minestom.server.extras.mojangAuth.MojangCrypt;
// Import d'une classe nécessaire
import net.minestom.server.network.ConnectionState;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketParser;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketReading;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketVanilla;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketWriting;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.common.ClientCookieResponsePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.common.ClientKeepAlivePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.common.ClientPingRequestPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.configuration.ClientFinishConfigurationPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.configuration.ClientSelectKnownPacksPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.handshake.ClientHandshakePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.login.ClientEncryptionResponsePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.login.ClientLoginAcknowledgedPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.login.ClientLoginPluginResponsePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.login.ClientLoginStartPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.status.StatusRequestPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.*;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.login.SetCompressionPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.collection.ConcurrentMessageQueues;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jctools.queues.MessagePassingQueue;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import javax.crypto.Cipher;
// Import d'une classe nécessaire
import javax.crypto.SecretKey;
// Import d'une classe nécessaire
import java.io.EOFException;
// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.net.SocketAddress;
// Import d'une classe nécessaire
import java.nio.channels.SocketChannel;
// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicBoolean;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicLong;
// Import d'une classe nécessaire
import java.util.concurrent.locks.LockSupport;
// Import d'une classe nécessaire
import java.util.zip.DataFormatException;


// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public class PlayerSocketConnection extends PlayerConnection {

    // Affecte une valeur
    private static final Set<Class<? extends ClientPacket>> IMMEDIATE_PROCESS_PACKETS = Set.of(
            // Instruction de code
            ClientHandshakePacket.class, // First received packet
            // Instruction de code
            ClientCookieResponsePacket.class,
            // Instruction de code
            StatusRequestPacket.class,
            // Instruction de code
            ClientPingRequestPacket.class,
            // Instruction de code
            ClientKeepAlivePacket.class, // Used to calculate latency
            // Instruction de code
            ClientLoginStartPacket.class,
            // Instruction de code
            ClientEncryptionResponsePacket.class, // Auth request
            // Instruction de code
            ClientLoginPluginResponsePacket.class,
            // Instruction de code
            ClientSelectKnownPacksPacket.class, // Immediate answer to server request on config
            // Instruction de code
            ClientLoginAcknowledgedPacket.class, // Handle config state
            // Instruction de code
            ClientFinishConfigurationPacket.class // Enter play state
    // Fin d'un bloc/d'une expression
    );

    // Instruction de code
    private final Channel channel;
    // Instruction de code
    private SocketAddress remoteAddress;
    // Instruction de code
    private final PacketParser<ClientPacket> packetParser;

    /** Cipher context for AES-CFB8 Mojang encryption (nullable until login). */
    // Instruction de code
    private volatile EncryptionContext encryptionContext;
    // Affecte une valeur
    private byte[] nonce = new byte[4];

    // Data from client packets
    // Instruction de code
    private String loginUsername;
    // Instruction de code
    private GameProfile gameProfile;
    // Instruction de code
    private String serverAddress;
    // Instruction de code
    private int serverPort;
    // Instruction de code
    private int protocolVersion;

    // Instruction de code
    private final NetworkBuffer readBuffer =
            // Appelle une méthode
            NetworkBuffer.resizableBuffer(ServerFlag.POOLED_BUFFER_SIZE, MinecraftServer.process());

    // Instruction de code
    private final MessagePassingQueue<SendablePacket> packetQueue =
            // Appelle une méthode
            ConcurrentMessageQueues.mpscUnboundedArrayQueue(1024);

    // Appelle une méthode
    private final AtomicLong sentPacketCounter = new AtomicLong();
    // Index where compression starts, linked to `sentPacketCounter`
    // Used instead of a simple boolean so we can get proper timing for serialization
    // Affecte une valeur
    private volatile long compressionStart = Long.MAX_VALUE;

    // Instruction de code
    private final ListenerHandle<PlayerPacketOutEvent> outgoing =
            // Appelle une méthode
            EventDispatcher.getHandle(PlayerPacketOutEvent.class);

    // Appelle une méthode
    private final ConnectionHandler handler = new ConnectionHandler();

    // Instruction de code
    public PlayerSocketConnection(Channel channel,
                                  // Instruction de code
                                  SocketAddress remoteAddress,
                                  // Début d'une méthode/d'un bloc
                                  PacketParser<ClientPacket> packetParser) {
        // Accès à l'objet courant/parent
        super();
        // Accès à l'objet courant/parent
        this.channel       = channel;
        // Accès à l'objet courant/parent
        this.remoteAddress = remoteAddress;
        // Accès à l'objet courant/parent
        this.packetParser  = packetParser;
    // Fin d'un bloc/d'une expression
    }

    /** Returns the Netty {@link ChannelDuplexHandler} to be added to the pipeline. */
    // Début d'une méthode/d'un bloc
    public ConnectionHandler channelHandler() {
        // Renvoie une valeur à l'appelant
        return handler;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void handleRead(ByteBuf frame) {
        // Affecte une valeur
        final NetworkBuffer readBuffer = this.readBuffer;

        // Append frame bytes to our accumulation buffer
        // Appelle une méthode
        final long writeIndexBefore = readBuffer.writeIndex();
        // Appelle une méthode
        readBuffer.readFromByteBuf(frame);

        // Decrypt newly appended bytes
        // Affecte une valeur
        final EncryptionContext ctx = this.encryptionContext;
        // Embranchement : vérifie une condition
        if (ctx != null) {
            // Appelle une méthode
            final long written = readBuffer.writeIndex() - writeIndexBefore;
            // Appelle une méthode
            readBuffer.cipher(ctx.decrypt(), writeIndexBefore, written);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        processPackets(readBuffer);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private boolean compression() {
        // Renvoie une valeur à l'appelant
        return compressionStart != Long.MAX_VALUE;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void processPackets(NetworkBuffer readBuffer) {
        // Appelle une méthode
        final ConnectionState startingState = getClientState();
        // Instruction de code
        final PacketReading.Result<ClientPacket> result;
        // Gestion des exceptions
        try {
            // Affecte une valeur
            result = PacketReading.readPackets(
                    // Instruction de code
                    readBuffer,
                    // Instruction de code
                    packetParser,
                    // Instruction de code
                    startingState, PacketVanilla::nextClientState,
                    // Instruction de code
                    compression()
            // Fin d'un bloc/d'une expression
            );
        // Début d'une méthode/d'un bloc
        } catch (DataFormatException e) {
            // Appelle une méthode
            MinecraftServer.getExceptionManager().handleException(e);
            // Appelle une méthode
            disconnect();
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Embranchement multiple (switch/case)
        switch (result) {
            // Embranchement multiple (switch/case)
            case PacketReading.Result.Success<ClientPacket> success -> {
                // Boucle : répète un bloc
                for (PacketReading.ParsedPacket<ClientPacket> parsed : success.packets()) {
                    // Appelle une méthode
                    final ClientPacket packet = parsed.packet();
                    // Gestion des exceptions
                    try {
                        // Embranchement : vérifie une condition
                        if (IMMEDIATE_PROCESS_PACKETS.contains(packet.getClass())) {
                            // Instruction de code
                            MinecraftServer.getPacketListenerManager()
                                    // Appelle une méthode
                                    .processClientPacket(packet, this);
                        // Branche alternative de la condition
                        } else {
                            // To be processed during the next player tick
                            // Appelle une méthode
                            final Player player = getPlayer();
                            // Instruction de code
                            assert player != null;
                            // Appelle une méthode
                            player.addPacketToQueue(packet);
                        // Fin d'un bloc/d'une expression
                        }
                    // Début d'une méthode/d'un bloc
                    } catch (Exception e) {
                        // Appelle une méthode
                        MinecraftServer.getExceptionManager().handleException(e);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
                // Compact in case of incomplete read
                // Appelle une méthode
                readBuffer.compact();
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case PacketReading.Result.Empty<ClientPacket> ignored -> { /* nothing yet */ }
            // Embranchement multiple (switch/case)
            case PacketReading.Result.Failure<ClientPacket> failure -> {
                // Appelle une méthode
                final long required = failure.requiredCapacity();
                // Appelle une méthode
                assert required > readBuffer.capacity();
                // Appelle une méthode
                readBuffer.resize(required);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void sendPacket(SendablePacket packet) {
        // Appelle une méthode
        packetQueue.relaxedOffer(packet);
        // Instruction de code
        channel.flush(); // schedule a write on the event loop
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void sendPackets(Collection<SendablePacket> packets) {
        // Boucle : répète un bloc
        for (SendablePacket p : packets) packetQueue.relaxedOffer(p);
        // Appelle une méthode
        channel.flush();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Drains {@link #packetQueue} into a single Netty {@link ByteBuf} and writes
     * it to the channel. Called exclusively from the Netty I/O thread.
     */
    // Début d'une méthode/d'un bloc
    private void flushQueue() {
        // Embranchement : vérifie une condition
        if (packetQueue.isEmpty()) return;

        // Appelle une méthode
        final NetworkBuffer buffer = PacketVanilla.PACKET_POOL.get();
        // Début d'une méthode/d'un bloc
        PacketWriting.writeQueue(buffer, packetQueue, 1, (b, packet) -> {
            // Appelle une méthode
            final boolean compressed = sentPacketCounter.get() > compressionStart;
            // Appelle une méthode
            final boolean ok = writePacketSync(b, packet, compressed);
            // Embranchement : vérifie une condition
            if (ok) sentPacketCounter.getAndIncrement();
            // Renvoie une valeur à l'appelant
            return ok;
        // Fin d'un bloc/d'une expression
        });

        // Transfer buffer contents to a Netty ByteBuf and write to channel
        // Appelle une méthode
        final long readable = buffer.readableBytes();
        // Embranchement : vérifie une condition
        if (readable > 0) {
            // Appelle une méthode
            final ByteBuf out = channel.alloc().buffer((int) readable);
            // Appelle une méthode
            buffer.writeToByteBuf(out);

            // Encrypt if needed
            // Affecte une valeur
            final EncryptionContext ctx = this.encryptionContext;
            // Embranchement : vérifie une condition
            if (ctx != null && out.isReadable()) {
                // cipher() works on the NetworkBuffer; re-apply on raw bytes
                // Appelle une méthode
                final byte[] raw = new byte[out.readableBytes()];
                // Appelle une méthode
                out.getBytes(out.readerIndex(), raw);
                // Gestion des exceptions
                try {
                    // Appelle une méthode
                    final byte[] encrypted = ctx.encrypt().update(raw);
                    // Appelle une méthode
                    out.clear();
                    // Appelle une méthode
                    out.writeBytes(encrypted);
                // Début d'une méthode/d'un bloc
                } catch (Exception e) {
                    // Appelle une méthode
                    out.release();
                    // Lève une exception
                    throw new RuntimeException(e);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            channel.writeAndFlush(out);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        PacketVanilla.PACKET_POOL.add(buffer);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private boolean writePacketSync(NetworkBuffer buffer, SendablePacket packet, boolean compressed) {
        // Appelle une méthode
        final Player player = getPlayer();
        // Appelle une méthode
        final ConnectionState state = getServerState();
        // Embranchement : vérifie une condition
        if (player != null) {
            // Outgoing event
            // Embranchement : vérifie une condition
            if (outgoing.hasListener()) {
                // Appelle une méthode
                final ServerPacket serverPacket = SendablePacket.extractServerPacket(state, packet);
                // Embranchement : vérifie une condition
                if (serverPacket != null) { // Events are not called for buffered packets
                    // Appelle une méthode
                    PlayerPacketOutEvent event = new PlayerPacketOutEvent(player, serverPacket);
                    // Appelle une méthode
                    outgoing.call(event);
                    // Embranchement : vérifie une condition
                    if (event.isCancelled()) return true;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Translation
            // Embranchement : vérifie une condition
            if (ServerFlag.AUTOMATIC_COMPONENT_TRANSLATION && packet instanceof ServerPacket.ComponentHolding translatablePacket) {
                // Affecte une valeur
                packet = translatablePacket.copyWithOperator(component ->
                        // Appelle une méthode
                        MinestomAdventure.COMPONENT_TRANSLATOR.apply(component, Objects.requireNonNullElseGet(player.getLocale(), MinestomAdventure::getDefaultLocale)));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Write packet
        // Appelle une méthode
        final long start = buffer.writeIndex();
        // Appelle une méthode
        final int compressionThreshold = compressed ? MinecraftServer.getCompressionThreshold() : 0;
        // Gestion des exceptions
        try {
            // Renvoie une valeur à l'appelant
            return switch (packet) {
                // Embranchement multiple (switch/case)
                case ServerPacket serverPacket -> {
                    // Appelle une méthode
                    var nextState = PacketVanilla.nextServerState(serverPacket, state);
                    // Embranchement : vérifie une condition
                    if (nextState != state) setServerState(nextState);

                    // Appelle une méthode
                    PacketWriting.writeFramedPacket(buffer, state, serverPacket, compressionThreshold);
                    // Instruction de code
                    yield true;
                // Fin d'un bloc/d'une expression
                }
                // Embranchement multiple (switch/case)
                case FramedPacket framedPacket -> {
                    // Appelle une méthode
                    final NetworkBuffer body = framedPacket.body();
                    // Appelle une méthode
                    yield writeBuffer(buffer, body, 0, body.capacity());
                // Fin d'un bloc/d'une expression
                }
                // Embranchement multiple (switch/case)
                case CachedPacket cachedPacket -> {
                    // Appelle une méthode
                    final NetworkBuffer body = cachedPacket.body(state);
                    // Embranchement : vérifie une condition
                    if (body != null) {
                        // Appelle une méthode
                        yield writeBuffer(buffer, body, 0, body.capacity());
                    // Branche alternative de la condition
                    } else {
                        // Appelle une méthode
                        PacketWriting.writeFramedPacket(buffer, state, cachedPacket.packet(state), compressionThreshold);
                        // Instruction de code
                        yield true;
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
                // Embranchement multiple (switch/case)
                case LazyPacket lazyPacket -> {
                    // Appelle une méthode
                    PacketWriting.writeFramedPacket(buffer, state, lazyPacket.packet(), compressionThreshold);
                    // Instruction de code
                    yield true;
                // Fin d'un bloc/d'une expression
                }
                // Embranchement multiple (switch/case)
                case BufferedPacket bufferedPacket -> {
                    // Appelle une méthode
                    final NetworkBuffer rawBuffer = bufferedPacket.buffer();
                    // Appelle une méthode
                    final long index = bufferedPacket.index();
                    // Appelle une méthode
                    final long length = bufferedPacket.length();
                    // Appelle une méthode
                    yield writeBuffer(buffer, rawBuffer, index, length);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            };
        // Début d'une méthode/d'un bloc
        } catch (IndexOutOfBoundsException exception) {
            // Appelle une méthode
            buffer.writeIndex(start);
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private boolean writeBuffer(NetworkBuffer dst, NetworkBuffer src,
                                // Début d'une méthode/d'un bloc
                                long index, long length) {
        // Embranchement : vérifie une condition
        if (dst.writableBytes() < length) return false;
        // Appelle une méthode
        NetworkBuffer.copy(src, index, dst, dst.writeIndex(), length);
        // Appelle une méthode
        dst.advanceWrite(length);
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setEncryptionKey(SecretKey secretKey) {
        // Appelle une méthode
        Check.stateCondition(encryptionContext != null, "Encryption is already enabled!");
        // Accès à l'objet courant/parent
        this.encryptionContext = new EncryptionContext(
                // Instruction de code
                MojangCrypt.getCipher(1, secretKey),
                // Appelle une méthode
                MojangCrypt.getCipher(2, secretKey));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void startCompression() {
        // Appelle une méthode
        Check.stateCondition(compression(), "Compression is already enabled!");
        // Accès à l'objet courant/parent
        this.compressionStart = sentPacketCounter.get();
        // Appelle une méthode
        final int threshold = MinecraftServer.getCompressionThreshold();
        // Instruction de code
        Check.stateCondition(threshold == 0,
                // Instruction de code
                "Compression cannot be enabled because the threshold is equal to 0");
        // Appelle une méthode
        sendPacket(new SetCompressionPacket(threshold));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public SocketAddress getRemoteAddress() {
        // Renvoie une valeur à l'appelant
        return remoteAddress;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void setRemoteAddress(SocketAddress remoteAddress) {
        // Accès à l'objet courant/parent
        this.remoteAddress = remoteAddress;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Channel getChannel() {
        // Renvoie une valeur à l'appelant
        return channel;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable GameProfile gameProfile() {
        // Renvoie une valeur à l'appelant
        return gameProfile;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void UNSAFE_setProfile(GameProfile gameProfile) {
        // Accès à l'objet courant/parent
        this.gameProfile = gameProfile;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable String getLoginUsername() {
        // Renvoie une valeur à l'appelant
        return loginUsername;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void UNSAFE_setLoginUsername(String loginUsername) {
        // Accès à l'objet courant/parent
        this.loginUsername = loginUsername;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the server address that the client used to connect.
     * <p>
     * WARNING: it is given by the client, it is possible for it to be wrong.
     *
     * @return the server address used
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable String getServerAddress() {
        // Renvoie une valeur à l'appelant
        return serverAddress;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the server port that the client used to connect.
     * <p>
     * WARNING: it is given by the client, it is possible for it to be wrong.
     *
     * @return the server port used
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int getServerPort() {
        // Renvoie une valeur à l'appelant
        return serverPort;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the protocol version of a client.
     *
     * @return protocol version of client.
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int getProtocolVersion() {
        // Renvoie une valeur à l'appelant
        return protocolVersion;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Used in {@link ClientHandshakePacket} to change the internal fields.
     *
     * @param serverAddress   the server address which the client used
     * @param serverPort      the server port which the client used
     * @param protocolVersion the protocol version which the client used
     */
    // Début d'une méthode/d'un bloc
    public void refreshServerInformation(@Nullable String serverAddress, int serverPort, int protocolVersion) {
        // Accès à l'objet courant/parent
        this.serverAddress = serverAddress;
        // Accès à l'objet courant/parent
        this.serverPort = serverPort;
        // Accès à l'objet courant/parent
        this.protocolVersion = protocolVersion;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public byte[] getNonce() {
        // Renvoie une valeur à l'appelant
        return nonce;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setNonce(byte[] nonce) {
        // Accès à l'objet courant/parent
        this.nonce = nonce;
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public final class ConnectionHandler extends ChannelDuplexHandler {

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            // Embranchement : vérifie une condition
            if (msg instanceof ByteBuf frame) {
                // Gestion des exceptions
                try {
                    // Appelle une méthode
                    handleRead(frame);
                // Début d'une méthode/d'un bloc
                } finally {
                    // Appelle une méthode
                    frame.release();
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void flush(ChannelHandlerContext ctx) {
            // Appelle une méthode
            flushQueue();
            // Appelle une méthode
            ctx.flush();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void channelInactive(ChannelHandlerContext ctx) {
            // Appelle une méthode
            final ChannelPromise promise = ctx.newPromise();

            // Gestion des exceptions
            try {
                // Appelle une méthode
                disconnect(ctx, promise);
            // Début d'une méthode/d'un bloc
            } catch (Exception e) {
                // Appelle une méthode
                ctx.close(promise);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            // Instruction de code
            final boolean expected =
                    // Instruction de code
                    cause instanceof IOException &&
                            // Instruction de code
                            (cause.getMessage() != null &&
                                    // Instruction de code
                                    (cause.getMessage().contains("Connection reset") ||
                                            // Appelle une méthode
                                            cause.getMessage().contains("Broken pipe")));
            // Embranchement : vérifie une condition
            if (!expected) {
                // Appelle une méthode
                MinecraftServer.getExceptionManager().handleException(cause);
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            final ChannelPromise promise = ctx.newPromise();

            // Gestion des exceptions
            try {
                // Appelle une méthode
                disconnect(ctx, promise);
            // Début d'une méthode/d'un bloc
            } catch (Exception e) {
                // Appelle une méthode
                ctx.close(promise);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void disconnect() {
        // Accès à l'objet courant/parent
        super.disconnect();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record EncryptionContext(Cipher encrypt, Cipher decrypt) {}
// Fin d'un bloc/d'une expression
}