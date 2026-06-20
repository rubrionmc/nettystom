// Package declaration for this file
package net.minestom.server.network.socket;

// Import of a required class
import io.netty.bootstrap.ServerBootstrap;
// Import of a required class
import io.netty.channel.*;
// Import of a required class
import io.netty.channel.epoll.Epoll;
// Import of a required class
import io.netty.channel.epoll.EpollEventLoopGroup;
// Import of a required class
import io.netty.channel.epoll.EpollServerSocketChannel;
// Import of a required class
import io.netty.channel.nio.NioEventLoopGroup;
// Import of a required class
import io.netty.channel.socket.SocketChannel;
// Import of a required class
import io.netty.channel.socket.nio.NioServerSocketChannel;
// Import of a required class
import io.netty.channel.unix.DomainSocketAddress;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.network.packet.PacketParser;
// Import of a required class
import net.minestom.server.network.packet.PacketVanilla;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.network.player.PlayerSocketConnection;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.net.*;
// Import of a required class
import java.nio.file.Files;

// Type declaration (class/interface/enum/record)
public final class Server {

    // Code statement
    private final PacketParser.Client packetParser;

    // Code statement
    private volatile boolean stop;
    // Code statement
    private EventLoopGroup bossGroup;
    // Code statement
    private EventLoopGroup workerGroup;
    // Code statement
    private Channel serverChannel;

    // Code statement
    private SocketAddress socketAddress;
    // Code statement
    private String address;
    // Code statement
    private int port;

    // Start of a method/block
    public Server(PacketParser.Client packetParser) {
        // Access to the current/parent object
        this.packetParser = packetParser;
    // End of a block/expression
    }

    // Start of a method/block
    public Server() {
        // Calls a method
        this(PacketVanilla.CLIENT_PACKET_PARSER);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void init(SocketAddress address) throws IOException {
        // Multiple branching (switch/case)
        switch (address) {
            // Multiple branching (switch/case)
            case InetSocketAddress inet -> {
                // Access to the current/parent object
                this.address = inet.getHostString();
                // Access to the current/parent object
                this.port    = inet.getPort();
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case UnixDomainSocketAddress unix -> {
                // Access to the current/parent object
                this.address = "unix://" + unix.getPath();
                // Access to the current/parent object
                this.port    = 0;
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            default -> throw new IllegalArgumentException(
                    // Code statement
                    "Address must be InetSocketAddress or UnixDomainSocketAddress");
        // End of a block/expression
        }
        // Access to the current/parent object
        this.socketAddress = address;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void start() {
        // Calls a method
        final boolean epoll = Epoll.isAvailable();

        // Assigns a value
        bossGroup   = epoll ? new EpollEventLoopGroup(1)
                // Calls a method
                : new NioEventLoopGroup(1);
        // Assigns a value
        workerGroup = epoll ? new EpollEventLoopGroup()
                // Calls a method
                : new NioEventLoopGroup();

        // Code statement
        final Class<? extends ServerChannel> channelClass =
                // Code statement
                epoll ? EpollServerSocketChannel.class
                        // Code statement
                        : NioServerSocketChannel.class;

        // Assigns a value
        final PacketParser<ClientPacket> parser = this.packetParser;

        // Assigns a value
        final ServerBootstrap bootstrap = new ServerBootstrap()
                // Code statement
                .group(bossGroup, workerGroup)
                // Code statement
                .channel(channelClass)
                // Code statement
                .childOption(ChannelOption.TCP_NODELAY, ServerFlag.SOCKET_NO_DELAY)
                // Code statement
                .childOption(ChannelOption.SO_SNDBUF,   ServerFlag.SOCKET_SEND_BUFFER_SIZE)
                // Code statement
                .childOption(ChannelOption.SO_RCVBUF,   ServerFlag.SOCKET_RECEIVE_BUFFER_SIZE)
                // Start of a method/block
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    // Annotation for the following element
                    @Override
                    // Start of a method/block
                    protected void initChannel(SocketChannel ch) {
                        // Calls a method
                        final ChannelPipeline pipeline = ch.pipeline();

                        // reads the varint-length-prefixed packets
                        // Calls a method
                        pipeline.addLast("frame-decoder", new MinecraftVarintFrameDecoder());

                        // Code statement
                        final PlayerSocketConnection conn =
                                // Creates a new object
                                new PlayerSocketConnection(ch, ch.remoteAddress(), parser);
                        // Calls a method
                        pipeline.addLast("handler", conn.channelHandler());
                    // End of a block/expression
                    }
                // End of a block/expression
                });

        // Code statement
        final ChannelFuture future;
        // Branch: checks a condition
        if (socketAddress instanceof InetSocketAddress inet) {
            // Calls a method
            future = bootstrap.bind(inet);
        // Branch: checks a condition
        } else if (socketAddress instanceof UnixDomainSocketAddress unix) {
            // Calls a method
            future = bootstrap.bind(new DomainSocketAddress(unix.getPath().toString()));
        // Alternative branch of the condition
        } else {
            // Throws an exception
            throw new IllegalStateException("Unsupported address type: " + socketAddress);
        // End of a block/expression
        }

        // Exception handling
        try {
            // Calls a method
            serverChannel = future.sync().channel();
        // Start of a method/block
        } catch (InterruptedException e) {
            // Calls a method
            Thread.currentThread().interrupt();
            // Throws an exception
            throw new RuntimeException("Server bind interrupted", e);
        // End of a block/expression
        }

        // Branch: checks a condition
        if (socketAddress instanceof InetSocketAddress && port == 0) {
            // Calls a method
            port = ((InetSocketAddress) serverChannel.localAddress()).getPort();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isOpen() {
        // Returns a value to the caller
        return !stop;
    // End of a block/expression
    }

    // Start of a method/block
    public void stop() {
        // Access to the current/parent object
        this.stop = true;
        // Branch: checks a condition
        if (serverChannel != null) {
            // Calls a method
            serverChannel.close().awaitUninterruptibly();
        // End of a block/expression
        }
        // Branch: checks a condition
        if (bossGroup   != null) bossGroup.shutdownGracefully();
        // Branch: checks a condition
        if (workerGroup != null) workerGroup.shutdownGracefully();

        // Branch: checks a condition
        if (socketAddress instanceof UnixDomainSocketAddress unix) {
            // Exception handling
            try {
                // Calls a method
                Files.deleteIfExists(unix.getPath());
            // Start of a method/block
            } catch (IOException e) {
                // Calls a method
                MinecraftServer.getExceptionManager().handleException(e);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }


    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public PacketParser.Client packetParser() {
        // Returns a value to the caller
        return packetParser;
    // End of a block/expression
    }

    // Start of a method/block
    public SocketAddress socketAddress() {
        // Returns a value to the caller
        return socketAddress;
    // End of a block/expression
    }

    // Start of a method/block
    public String getAddress() {
        // Returns a value to the caller
        return address;
    // End of a block/expression
    }

    // Start of a method/block
    public int getPort() {
        // Returns a value to the caller
        return port;
    // End of a block/expression
    }
// End of a block/expression
}