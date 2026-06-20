// Package declaration for this file
package net.minestom.server.network.player;

// Import of a required class
import io.netty.buffer.ByteBuf;
// Import of a required class
import io.netty.channel.Channel;
// Import of a required class
import io.netty.channel.ChannelDuplexHandler;
// Import of a required class
import io.netty.channel.ChannelHandlerContext;
// Import of a required class
import io.netty.channel.ChannelPromise;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.adventure.MinestomAdventure;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.ListenerHandle;
// Import of a required class
import net.minestom.server.event.player.PlayerPacketOutEvent;
// Import of a required class
import net.minestom.server.extras.mojangAuth.MojangCrypt;
// Import of a required class
import net.minestom.server.network.ConnectionState;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.PacketParser;
// Import of a required class
import net.minestom.server.network.packet.PacketReading;
// Import of a required class
import net.minestom.server.network.packet.PacketVanilla;
// Import of a required class
import net.minestom.server.network.packet.PacketWriting;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.network.packet.client.common.ClientCookieResponsePacket;
// Import of a required class
import net.minestom.server.network.packet.client.common.ClientKeepAlivePacket;
// Import of a required class
import net.minestom.server.network.packet.client.common.ClientPingRequestPacket;
// Import of a required class
import net.minestom.server.network.packet.client.configuration.ClientFinishConfigurationPacket;
// Import of a required class
import net.minestom.server.network.packet.client.configuration.ClientSelectKnownPacksPacket;
// Import of a required class
import net.minestom.server.network.packet.client.handshake.ClientHandshakePacket;
// Import of a required class
import net.minestom.server.network.packet.client.login.ClientEncryptionResponsePacket;
// Import of a required class
import net.minestom.server.network.packet.client.login.ClientLoginAcknowledgedPacket;
// Import of a required class
import net.minestom.server.network.packet.client.login.ClientLoginPluginResponsePacket;
// Import of a required class
import net.minestom.server.network.packet.client.login.ClientLoginStartPacket;
// Import of a required class
import net.minestom.server.network.packet.client.status.StatusRequestPacket;
// Import of a required class
import net.minestom.server.network.packet.server.*;
// Import of a required class
import net.minestom.server.network.packet.server.login.SetCompressionPacket;
// Import of a required class
import net.minestom.server.utils.collection.ConcurrentMessageQueues;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jctools.queues.MessagePassingQueue;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import javax.crypto.Cipher;
// Import of a required class
import javax.crypto.SecretKey;
// Import of a required class
import java.io.EOFException;
// Import of a required class
import java.io.IOException;
// Import of a required class
import java.net.SocketAddress;
// Import of a required class
import java.nio.channels.SocketChannel;
// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.concurrent.atomic.AtomicBoolean;
// Import of a required class
import java.util.concurrent.atomic.AtomicLong;
// Import of a required class
import java.util.concurrent.locks.LockSupport;
// Import of a required class
import java.util.zip.DataFormatException;


// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public class PlayerSocketConnection extends PlayerConnection {
    // Assigns a value
    private static final Set<Class<? extends ClientPacket>> IMMEDIATE_PROCESS_PACKETS = Set.of(
            // Code statement
            ClientHandshakePacket.class, // First received packet
            // Code statement
            ClientCookieResponsePacket.class,
            // Code statement
            StatusRequestPacket.class,
            // Code statement
            ClientPingRequestPacket.class,
            // Code statement
            ClientKeepAlivePacket.class, // Used to calculate latency
            // Code statement
            ClientLoginStartPacket.class,
            // Code statement
            ClientEncryptionResponsePacket.class, // Auth request
            // Code statement
            ClientLoginPluginResponsePacket.class,
            // Code statement
            ClientSelectKnownPacksPacket.class, // Immediate answer to server request on config
            // Code statement
            ClientLoginAcknowledgedPacket.class, // Handle config state
            // Code statement
            ClientFinishConfigurationPacket.class // Enter play state
    // End of a block/expression
    );

    // Code statement
    private final Channel channel;
    // Code statement
    private SocketAddress remoteAddress;
    // Code statement
    private final PacketParser<ClientPacket> packetParser;

    //Could be null. Only used for Mojang Auth
    // Code statement
    private volatile @Nullable EncryptionContext encryptionContext;
    // Assigns a value
    private byte[] nonce = new byte[4];

    // Data from client packets
    // Code statement
    private @Nullable String loginUsername;
    // Code statement
    private @Nullable GameProfile gameProfile;
    // Code statement
    private @Nullable String serverAddress;
    // Code statement
    private int serverPort;
    // Code statement
    private int protocolVersion;

    // Code statement
    private final NetworkBuffer readBuffer =
            // Calls a method
            NetworkBuffer.resizableBuffer(ServerFlag.POOLED_BUFFER_SIZE, MinecraftServer.process());

    // Code statement
    private final MessagePassingQueue<SendablePacket> packetQueue =
            // Calls a method
            ConcurrentMessageQueues.mpscUnboundedArrayQueue(1024);

    // Calls a method
    private final AtomicLong sentPacketCounter = new AtomicLong();
    // Index where compression starts, linked to `sentPacketCounter`
    // Used instead of a simple boolean so we can get proper timing for serialization
    // Assigns a value
    private volatile long compressionStart = Long.MAX_VALUE;

    // Code statement
    private final ListenerHandle<PlayerPacketOutEvent> outgoing =
            // Calls a method
            EventDispatcher.getHandle(PlayerPacketOutEvent.class);

    // Calls a method
    private final ConnectionHandler handler = new ConnectionHandler();

    // Code statement
    public PlayerSocketConnection(Channel channel,
                                  // Code statement
                                  SocketAddress remoteAddress,
                                  // Start of a method/block
                                  PacketParser<ClientPacket> packetParser) {
        // Access to the current/parent object
        super();
        // Access to the current/parent object
        this.channel       = channel;
        // Access to the current/parent object
        this.remoteAddress = remoteAddress;
        // Access to the current/parent object
        this.packetParser  = packetParser;
    // End of a block/expression
    }

    /** Returns the Netty {@link ChannelDuplexHandler} to be added to the pipeline. */
    // Start of a method/block
    public ConnectionHandler channelHandler() {
        // Returns a value to the caller
        return handler;
    // End of a block/expression
    }

    // Start of a method/block
    private void handleRead(ByteBuf frame) {
        // Assigns a value
        final NetworkBuffer readBuffer = this.readBuffer;

        // Append frame bytes to our accumulation buffer
        // Calls a method
        final long writeIndexBefore = readBuffer.writeIndex();
        // Calls a method
        readBuffer.readFromByteBuf(frame);

        // Decrypt newly appended bytes
        // Assigns a value
        final EncryptionContext ctx = this.encryptionContext;
        // Branch: checks a condition
        if (ctx != null) {
            // Calls a method
            final long written = readBuffer.writeIndex() - writeIndexBefore;
            // Calls a method
            readBuffer.cipher(ctx.decrypt(), writeIndexBefore, written);
        // End of a block/expression
        }

        // Calls a method
        processPackets(readBuffer);
    // End of a block/expression
    }

    // Start of a method/block
    private boolean compression() {
        // Returns a value to the caller
        return compressionStart != Long.MAX_VALUE;
    // End of a block/expression
    }

    // Start of a method/block
    private void processPackets(NetworkBuffer readBuffer) {
        // Calls a method
        final ConnectionState startingState = getClientState();
        // Code statement
        final PacketReading.Result<ClientPacket> result;
        // Exception handling
        try {
            // Assigns a value
            result = PacketReading.readPackets(
                    // Code statement
                    readBuffer,
                    // Code statement
                    packetParser,
                    // Code statement
                    startingState, PacketVanilla::nextClientState,
                    // Code statement
                    compression()
            // End of a block/expression
            );
        // Start of a method/block
        } catch (DataFormatException e) {
            // Calls a method
            MinecraftServer.getExceptionManager().handleException(e);
            // Calls a method
            disconnect();
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Multiple branching (switch/case)
        switch (result) {
            // Multiple branching (switch/case)
            case PacketReading.Result.Success<ClientPacket> success -> {
                // Loop: repeats a block
                for (PacketReading.ParsedPacket<ClientPacket> parsed : success.packets()) {
                    // Calls a method
                    final ClientPacket packet = parsed.packet();
                    // Exception handling
                    try {
                        // Branch: checks a condition
                        if (IMMEDIATE_PROCESS_PACKETS.contains(packet.getClass())) {
                            // Code statement
                            MinecraftServer.getPacketListenerManager()
                                    // Calls a method
                                    .processClientPacket(packet, this);
                        // Alternative branch of the condition
                        } else {
                            // To be processed during the next player tick
                            // Calls a method
                            final Player player = getPlayer();
                            // Code statement
                            assert player != null;
                            // Calls a method
                            player.addPacketToQueue(packet);
                        // End of a block/expression
                        }
                    // Start of a method/block
                    } catch (Exception e) {
                        // Calls a method
                        MinecraftServer.getExceptionManager().handleException(e);
                    // End of a block/expression
                    }
                // End of a block/expression
                }
                // Compact in case of incomplete read
                // Calls a method
                readBuffer.compact();
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case PacketReading.Result.Empty<ClientPacket> ignored -> { /* nothing yet */ }
            // Multiple branching (switch/case)
            case PacketReading.Result.Failure<ClientPacket> failure -> {
                // Calls a method
                final long required = failure.requiredCapacity();
                // Calls a method
                assert required > readBuffer.capacity();
                // Calls a method
                readBuffer.resize(required);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void sendPacket(SendablePacket packet) {
        // Calls a method
        packetQueue.relaxedOffer(packet);
        // Code statement
        channel.flush(); // schedule a write on the event loop
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void sendPackets(Collection<SendablePacket> packets) {
        // Loop: repeats a block
        for (SendablePacket p : packets) packetQueue.relaxedOffer(p);
        // Calls a method
        channel.flush();
    // End of a block/expression
    }

    /**
     * Drains {@link #packetQueue} into a single Netty {@link ByteBuf} and writes
     * it to the channel. Called exclusively from the Netty I/O thread.
     */
    // Start of a method/block
    private void flushQueue() {
        // Branch: checks a condition
        if (packetQueue.isEmpty()) return;

        // Calls a method
        final NetworkBuffer buffer = PacketVanilla.PACKET_POOL.get();
        // Start of a method/block
        PacketWriting.writeQueue(buffer, packetQueue, 1, (b, packet) -> {
            // Calls a method
            final boolean compressed = sentPacketCounter.get() > compressionStart;
            // Calls a method
            final boolean ok = writePacketSync(b, packet, compressed);
            // Branch: checks a condition
            if (ok) sentPacketCounter.getAndIncrement();
            // Returns a value to the caller
            return ok;
        // End of a block/expression
        });

        // Transfer buffer contents to a Netty ByteBuf and write to channel
        // Calls a method
        final long readable = buffer.readableBytes();
        // Branch: checks a condition
        if (readable > 0) {
            // Calls a method
            final ByteBuf out = channel.alloc().buffer((int) readable);
            // Calls a method
            buffer.writeToByteBuf(out);

            // Encrypt if needed
            // Assigns a value
            final EncryptionContext ctx = this.encryptionContext;
            // Branch: checks a condition
            if (ctx != null && out.isReadable()) {
                // cipher() works on the NetworkBuffer; re-apply on raw bytes
                // Calls a method
                final byte[] raw = new byte[out.readableBytes()];
                // Calls a method
                out.getBytes(out.readerIndex(), raw);
                // Exception handling
                try {
                    // Calls a method
                    final byte[] encrypted = ctx.encrypt().update(raw);
                    // Calls a method
                    out.clear();
                    // Calls a method
                    out.writeBytes(encrypted);
                // Start of a method/block
                } catch (Exception e) {
                    // Calls a method
                    out.release();
                    // Throws an exception
                    throw new RuntimeException(e);
                // End of a block/expression
                }
            // End of a block/expression
            }

            // Calls a method
            channel.writeAndFlush(out);
        // End of a block/expression
        }

        // Calls a method
        PacketVanilla.PACKET_POOL.add(buffer);
    // End of a block/expression
    }

    // Start of a method/block
    private boolean writePacketSync(NetworkBuffer buffer, SendablePacket packet, boolean compressed) {
        // Calls a method
        final Player player = getPlayer();
        // Calls a method
        final ConnectionState state = getServerState();
        // Branch: checks a condition
        if (player != null) {
            // Outgoing event
            // Branch: checks a condition
            if (outgoing.hasListener()) {
                // Calls a method
                final ServerPacket serverPacket = SendablePacket.extractServerPacket(state, packet);
                // Branch: checks a condition
                if (serverPacket != null) { // Events are not called for buffered packets
                    // Calls a method
                    PlayerPacketOutEvent event = new PlayerPacketOutEvent(player, serverPacket);
                    // Calls a method
                    outgoing.call(event);
                    // Branch: checks a condition
                    if (event.isCancelled()) return true;
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Translation
            // Branch: checks a condition
            if (ServerFlag.AUTOMATIC_COMPONENT_TRANSLATION && packet instanceof ServerPacket.ComponentHolding translatablePacket) {
                // Assigns a value
                packet = translatablePacket.copyWithOperator(component ->
                        // Calls a method
                        MinestomAdventure.COMPONENT_TRANSLATOR.apply(component, Objects.requireNonNullElseGet(player.getLocale(), MinestomAdventure::getDefaultLocale)));
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Write packet
        // Calls a method
        final long start = buffer.writeIndex();
        // Calls a method
        final int compressionThreshold = compressed ? MinecraftServer.getCompressionThreshold() : 0;
        // Exception handling
        try {
            // Returns a value to the caller
            return switch (packet) {
                // Multiple branching (switch/case)
                case ServerPacket serverPacket -> {
                    // Calls a method
                    var nextState = PacketVanilla.nextServerState(serverPacket, state);
                    // Branch: checks a condition
                    if (nextState != state) setServerState(nextState);

                    // Calls a method
                    PacketWriting.writeFramedPacket(buffer, state, serverPacket, compressionThreshold);
                    // Code statement
                    yield true;
                // End of a block/expression
                }
                // Multiple branching (switch/case)
                case FramedPacket framedPacket -> {
                    // Calls a method
                    final NetworkBuffer body = framedPacket.body();
                    // Calls a method
                    yield writeBuffer(buffer, body, 0, body.capacity());
                // End of a block/expression
                }
                // Multiple branching (switch/case)
                case CachedPacket cachedPacket -> {
                    // Calls a method
                    final NetworkBuffer body = cachedPacket.body(state);
                    // Branch: checks a condition
                    if (body != null) {
                        // Calls a method
                        yield writeBuffer(buffer, body, 0, body.capacity());
                    // Alternative branch of the condition
                    } else {
                        // Calls a method
                        PacketWriting.writeFramedPacket(buffer, state, cachedPacket.packet(state), compressionThreshold);
                        // Code statement
                        yield true;
                    // End of a block/expression
                    }
                // End of a block/expression
                }
                // Multiple branching (switch/case)
                case LazyPacket lazyPacket -> {
                    // Calls a method
                    PacketWriting.writeFramedPacket(buffer, state, lazyPacket.packet(), compressionThreshold);
                    // Code statement
                    yield true;
                // End of a block/expression
                }
                // Multiple branching (switch/case)
                case BufferedPacket bufferedPacket -> {
                    // Calls a method
                    final NetworkBuffer rawBuffer = bufferedPacket.buffer();
                    // Calls a method
                    final long index = bufferedPacket.index();
                    // Calls a method
                    final long length = bufferedPacket.length();
                    // Calls a method
                    yield writeBuffer(buffer, rawBuffer, index, length);
                // End of a block/expression
                }
            // End of a block/expression
            };
        // Start of a method/block
        } catch (IndexOutOfBoundsException exception) {
            // Calls a method
            buffer.writeIndex(start);
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Code statement
    private boolean writeBuffer(NetworkBuffer dst, NetworkBuffer src,
                                // Start of a method/block
                                long index, long length) {
        // Branch: checks a condition
        if (dst.writableBytes() < length) return false;
        // Calls a method
        NetworkBuffer.copy(src, index, dst, dst.writeIndex(), length);
        // Calls a method
        dst.advanceWrite(length);
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Start of a method/block
    public void setEncryptionKey(SecretKey secretKey) {
        // Calls a method
        Check.stateCondition(encryptionContext != null, "Encryption is already enabled!");
        // Access to the current/parent object
        this.encryptionContext = new EncryptionContext(
                // Code statement
                MojangCrypt.getCipher(1, secretKey),
                // Calls a method
                MojangCrypt.getCipher(2, secretKey));
    // End of a block/expression
    }

    // Start of a method/block
    public void startCompression() {
        // Calls a method
        Check.stateCondition(compression(), "Compression is already enabled!");
        // Access to the current/parent object
        this.compressionStart = sentPacketCounter.get();
        // Calls a method
        final int threshold = MinecraftServer.getCompressionThreshold();
        // Code statement
        Check.stateCondition(threshold == 0,
                // Code statement
                "Compression cannot be enabled because the threshold is equal to 0");
        // Calls a method
        sendPacket(new SetCompressionPacket(threshold));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public SocketAddress getRemoteAddress() {
        // Returns a value to the caller
        return remoteAddress;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void setRemoteAddress(SocketAddress remoteAddress) {
        // Access to the current/parent object
        this.remoteAddress = remoteAddress;
    // End of a block/expression
    }

    // Start of a method/block
    public Channel getChannel() {
        // Returns a value to the caller
        return channel;
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable GameProfile gameProfile() {
        // Returns a value to the caller
        return gameProfile;
    // End of a block/expression
    }

    // Start of a method/block
    public void UNSAFE_setProfile(GameProfile gameProfile) {
        // Access to the current/parent object
        this.gameProfile = gameProfile;
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable String getLoginUsername() {
        // Returns a value to the caller
        return loginUsername;
    // End of a block/expression
    }

    // Start of a method/block
    public void UNSAFE_setLoginUsername(String loginUsername) {
        // Access to the current/parent object
        this.loginUsername = loginUsername;
    // End of a block/expression
    }

    /**
     * Gets the server address that the client used to connect.
     * <p>
     * WARNING: it is given by the client, it is possible for it to be wrong.
     *
     * @return the server address used
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable String getServerAddress() {
        // Returns a value to the caller
        return serverAddress;
    // End of a block/expression
    }

    /**
     * Gets the server port that the client used to connect.
     * <p>
     * WARNING: it is given by the client, it is possible for it to be wrong.
     *
     * @return the server port used
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public int getServerPort() {
        // Returns a value to the caller
        return serverPort;
    // End of a block/expression
    }

    /**
     * Gets the protocol version of a client.
     *
     * @return protocol version of client.
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public int getProtocolVersion() {
        // Returns a value to the caller
        return protocolVersion;
    // End of a block/expression
    }

    /**
     * Used in {@link ClientHandshakePacket} to change the internal fields.
     *
     * @param serverAddress   the server address which the client used
     * @param serverPort      the server port which the client used
     * @param protocolVersion the protocol version which the client used
     */
    // Start of a method/block
    public void refreshServerInformation(@Nullable String serverAddress, int serverPort, int protocolVersion) {
        // Access to the current/parent object
        this.serverAddress = serverAddress;
        // Access to the current/parent object
        this.serverPort = serverPort;
        // Access to the current/parent object
        this.protocolVersion = protocolVersion;
    // End of a block/expression
    }

    // Start of a method/block
    public byte[] getNonce() {
        // Returns a value to the caller
        return nonce;
    // End of a block/expression
    }

    // Start of a method/block
    public void setNonce(byte[] nonce) {
        // Access to the current/parent object
        this.nonce = nonce;
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public final class ConnectionHandler extends ChannelDuplexHandler {

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            // Branch: checks a condition
            if (msg instanceof ByteBuf frame) {
                // Exception handling
                try {
                    // Calls a method
                    handleRead(frame);
                // Start of a method/block
                } finally {
                    // Calls a method
                    frame.release();
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void flush(ChannelHandlerContext ctx) {
            // Calls a method
            flushQueue();
            // Calls a method
            ctx.flush();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void channelInactive(ChannelHandlerContext ctx) {
            // Calls a method
            final ChannelPromise promise = ctx.newPromise();

            // Exception handling
            try {
                // Calls a method
                disconnect(ctx, promise);
            // Start of a method/block
            } catch (Exception e) {
                // Calls a method
                ctx.close(promise);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Assigns a value
        private @Nullable NetworkBuffer writeLeftover = null;

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            // Code statement
            final boolean expected =
                    // Code statement
                    cause instanceof IOException &&
                            // Code statement
                            (cause.getMessage() != null &&
                                    // Code statement
                                    (cause.getMessage().contains("Connection reset") ||
                                            // Calls a method
                                            cause.getMessage().contains("Broken pipe")));
            // Branch: checks a condition
            if (!expected) {
                // Calls a method
                MinecraftServer.getExceptionManager().handleException(cause);
            // End of a block/expression
            }

            // Calls a method
            final ChannelPromise promise = ctx.newPromise();

            // Exception handling
            try {
                // Calls a method
                disconnect(ctx, promise);
            // Start of a method/block
            } catch (Exception e) {
                // Calls a method
                ctx.close(promise);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void disconnect() {
        // Access to the current/parent object
        super.disconnect();
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record EncryptionContext(Cipher encrypt, Cipher decrypt) {
    // End of a block/expression
    }
// End of a block/expression
}