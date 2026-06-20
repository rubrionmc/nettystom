// Déclaration du paquet de ce fichier
package net.minestom.server.network.socket;

// Import d'une classe nécessaire
import io.netty.bootstrap.ServerBootstrap;
// Import d'une classe nécessaire
import io.netty.channel.*;
// Import d'une classe nécessaire
import io.netty.channel.epoll.Epoll;
// Import d'une classe nécessaire
import io.netty.channel.epoll.EpollEventLoopGroup;
// Import d'une classe nécessaire
import io.netty.channel.epoll.EpollServerSocketChannel;
// Import d'une classe nécessaire
import io.netty.channel.nio.NioEventLoopGroup;
// Import d'une classe nécessaire
import io.netty.channel.socket.SocketChannel;
// Import d'une classe nécessaire
import io.netty.channel.socket.nio.NioServerSocketChannel;
// Import d'une classe nécessaire
import io.netty.channel.unix.DomainSocketAddress;
// Import d'une classe nécessaire
import io.netty.channel.unix.UnixChannel;
// Import d'une classe nécessaire
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketParser;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketVanilla;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerSocketConnection;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.net.*;

// Déclaration de type (classe/interface/enum/record)
public final class Server {

    // Instruction de code
    private final PacketParser.Client packetParser;

    // Instruction de code
    private volatile boolean stop;
    // Instruction de code
    private EventLoopGroup bossGroup;
    // Instruction de code
    private EventLoopGroup workerGroup;
    // Instruction de code
    private Channel serverChannel;

    // Instruction de code
    private SocketAddress socketAddress;
    // Instruction de code
    private String address;
    // Instruction de code
    private int port;

    // Début d'une méthode/d'un bloc
    public Server(PacketParser.Client packetParser) {
        // Accès à l'objet courant/parent
        this.packetParser = packetParser;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Server() {
        // Appelle une méthode
        this(PacketVanilla.CLIENT_PACKET_PARSER);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void init(SocketAddress address) throws IOException {
        // Embranchement multiple (switch/case)
        switch (address) {
            // Embranchement multiple (switch/case)
            case InetSocketAddress inet -> {
                // Accès à l'objet courant/parent
                this.address = inet.getHostString();
                // Accès à l'objet courant/parent
                this.port    = inet.getPort();
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case UnixDomainSocketAddress unix -> {
                // Accès à l'objet courant/parent
                this.address = "unix://" + unix.getPath();
                // Accès à l'objet courant/parent
                this.port    = 0;
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            default -> throw new IllegalArgumentException(
                    // Instruction de code
                    "Address must be InetSocketAddress or UnixDomainSocketAddress");
        // Fin d'un bloc/d'une expression
        }
        // Accès à l'objet courant/parent
        this.socketAddress = address;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void start() {
        // Appelle une méthode
        final boolean epoll = Epoll.isAvailable();

        // Affecte une valeur
        bossGroup   = epoll ? new EpollEventLoopGroup(1)
                // Appelle une méthode
                : new NioEventLoopGroup(1);
        // Affecte une valeur
        workerGroup = epoll ? new EpollEventLoopGroup()
                // Appelle une méthode
                : new NioEventLoopGroup();

        // Instruction de code
        final Class<? extends ServerChannel> channelClass =
                // Instruction de code
                epoll ? EpollServerSocketChannel.class
                        // Instruction de code
                        : NioServerSocketChannel.class;

        // Affecte une valeur
        final PacketParser<ClientPacket> parser = this.packetParser;

        // Affecte une valeur
        final ServerBootstrap bootstrap = new ServerBootstrap()
                // Instruction de code
                .group(bossGroup, workerGroup)
                // Instruction de code
                .channel(channelClass)
                // Instruction de code
                .childOption(ChannelOption.TCP_NODELAY, ServerFlag.SOCKET_NO_DELAY)
                // Instruction de code
                .childOption(ChannelOption.SO_SNDBUF,   ServerFlag.SOCKET_SEND_BUFFER_SIZE)
                // Instruction de code
                .childOption(ChannelOption.SO_RCVBUF,   ServerFlag.SOCKET_RECEIVE_BUFFER_SIZE)
                // Début d'une méthode/d'un bloc
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    // Annotation pour l'élément suivant
                    @Override
                    // Début d'une méthode/d'un bloc
                    protected void initChannel(SocketChannel ch) {
                        // Appelle une méthode
                        final ChannelPipeline pipeline = ch.pipeline();

                        // reads the varint-length-prefixed packets
                        // Appelle une méthode
                        pipeline.addLast("frame-decoder", new MinecraftVarintFrameDecoder());

                        // Instruction de code
                        final PlayerSocketConnection conn =
                                // Crée un nouvel objet
                                new PlayerSocketConnection(ch, ch.remoteAddress(), parser);
                        // Appelle une méthode
                        pipeline.addLast("handler", conn.channelHandler());
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                });

        // Instruction de code
        final ChannelFuture future;
        // Embranchement : vérifie une condition
        if (socketAddress instanceof InetSocketAddress inet) {
            // Appelle une méthode
            future = bootstrap.bind(inet);
        // Embranchement : vérifie une condition
        } else if (socketAddress instanceof UnixDomainSocketAddress unix) {
            // Netty uses its own DomainSocketAddress type
            // Appelle une méthode
            future = bootstrap.bind(new DomainSocketAddress(unix.getPath().toString()));
        // Branche alternative de la condition
        } else {
            // Lève une exception
            throw new IllegalStateException("Unsupported address type: " + socketAddress);
        // Fin d'un bloc/d'une expression
        }

        // Gestion des exceptions
        try {
            // Appelle une méthode
            serverChannel = future.sync().channel();
        // Début d'une méthode/d'un bloc
        } catch (InterruptedException e) {
            // Appelle une méthode
            Thread.currentThread().interrupt();
            // Lève une exception
            throw new RuntimeException("Server bind interrupted", e);
        // Fin d'un bloc/d'une expression
        }

        // If port was 0 (OS-assigned), read it back
        // Embranchement : vérifie une condition
        if (socketAddress instanceof InetSocketAddress && port == 0) {
            // Appelle une méthode
            port = ((InetSocketAddress) serverChannel.localAddress()).getPort();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isOpen() {
        // Renvoie une valeur à l'appelant
        return !stop;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void stop() {
        // Accès à l'objet courant/parent
        this.stop = true;
        // Embranchement : vérifie une condition
        if (serverChannel != null) {
            // Appelle une méthode
            serverChannel.close().awaitUninterruptibly();
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (bossGroup   != null) bossGroup.shutdownGracefully();
        // Embranchement : vérifie une condition
        if (workerGroup != null) workerGroup.shutdownGracefully();
    // Fin d'un bloc/d'une expression
    }


    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public PacketParser.Client packetParser() {
        // Renvoie une valeur à l'appelant
        return packetParser;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public SocketAddress socketAddress() {
        // Renvoie une valeur à l'appelant
        return socketAddress;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public String getAddress() {
        // Renvoie une valeur à l'appelant
        return address;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getPort() {
        // Renvoie une valeur à l'appelant
        return port;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}