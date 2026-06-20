// Déclaration du paquet de ce fichier
package net.minestom.server.network.player;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.crypto.PlayerPublicKey;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.OutgoingTransferEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerDisconnectEvent;
// Import d'une classe nécessaire
import net.minestom.server.monitoring.EventsJFR;
// Import d'une classe nécessaire
import net.minestom.server.network.ConnectionState;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.SendablePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.CookieRequestPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.CookieStorePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.DisconnectPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.TransferPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.configuration.SelectKnownPacksPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.login.LoginDisconnectPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.plugin.LoginPluginMessageProcessor;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.net.SocketAddress;
// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.concurrent.CompletableFuture;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;

/**
 * A PlayerConnection is an object needed for all created {@link Player}.
 * It can be extended to create a new kind of player (NPC for instance).
 */
// Déclaration de type (classe/interface/enum/record)
public abstract class PlayerConnection {
    // Instruction de code
    private Player player;

    // Server & client states can differ during configuration.
    // "server" state means the state the server thinks its in.
    // "client" state means the state the client thinks its in.
    // For example, after sending start configuration but before receiving the ack,
    // the server will be in CONFIGURATION while the client is still in PLAY.
    // Instruction de code
    private volatile ConnectionState serverState, clientState;

    // Instruction de code
    private @Nullable PlayerPublicKey playerPublicKey;
    // Instruction de code
    volatile boolean online;
    // Instruction de code
    private volatile boolean wasTransferred;

    // Appelle une méthode
    private @Nullable LoginPluginMessageProcessor loginPluginMessageProcessor = new LoginPluginMessageProcessor(this);

    // Affecte une valeur
    private @Nullable CompletableFuture<List<SelectKnownPacksPacket.Entry>> knownPacksFuture = null; // Present only when waiting for a response from the client.

    // Appelle une méthode
    private final Map<Key, CompletableFuture<byte @Nullable []>> pendingCookieRequests = new ConcurrentHashMap<>();

    // Début d'une méthode/d'un bloc
    public PlayerConnection() {
        // Accès à l'objet courant/parent
        this.online = true;
        // Accès à l'objet courant/parent
        this.serverState = ConnectionState.HANDSHAKE;
        // Accès à l'objet courant/parent
        this.clientState = ConnectionState.HANDSHAKE;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns a printable identifier for this connection, will be the player username
     * or the connection remote address.
     *
     * @return this connection identifier
     */
    // Début d'une méthode/d'un bloc
    public String getIdentifier() {
        // Appelle une méthode
        final Player player = getPlayer();
        // Renvoie une valeur à l'appelant
        return player != null ?
                // Instruction de code
                player.getUsername() :
                // Appelle une méthode
                getRemoteAddress().toString();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Serializes the packet and send it to the client.
     *
     * @param packet the packet to send
     */
    // Appelle une méthode
    public abstract void sendPacket(SendablePacket packet);

    // Début d'une méthode/d'un bloc
    public void sendPackets(Collection<SendablePacket> packets) {
        // Appelle une méthode
        packets.forEach(this::sendPacket);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void sendPackets(SendablePacket... packets) {
        // Appelle une méthode
        sendPackets(List.of(packets));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the remote address of the client.
     *
     * @return the remote address
     */
    // Appelle une méthode
    public abstract SocketAddress getRemoteAddress();

    /**
     * Gets protocol version of client.
     *
     * @return the protocol version
     */
    // Début d'une méthode/d'un bloc
    public int getProtocolVersion() {
        // Renvoie une valeur à l'appelant
        return MinecraftServer.PROTOCOL_VERSION;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the server address that the client used to connect.
     * <p>
     * WARNING: it is given by the client, it is possible for it to be wrong.
     *
     * @return the server address used
     */
    // Début d'une méthode/d'un bloc
    public @Nullable String getServerAddress() {
        // Renvoie une valeur à l'appelant
        return MinecraftServer.getServer().getAddress();
    // Fin d'un bloc/d'une expression
    }


    /**
     * Gets the server port that the client used to connect.
     * <p>
     * WARNING: it is given by the client, it is possible for it to be wrong.
     *
     * @return the server port used
     */
    // Début d'une méthode/d'un bloc
    public int getServerPort() {
        // Renvoie une valeur à l'appelant
        return MinecraftServer.getServer().getPort();
    // Fin d'un bloc/d'une expression
    }


    /**
     * Kicks the player with a reason.
     *
     * @param component the reason
     */
    // Début d'une méthode/d'un bloc
    public void kick(Component component) {
        // Packet type depends on the current player connection state
        // Instruction de code
        final ServerPacket disconnectPacket;
        // Embranchement : vérifie une condition
        if (serverState == ConnectionState.LOGIN) {
            // Appelle une méthode
            disconnectPacket = new LoginDisconnectPacket(component);
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            disconnectPacket = new DisconnectPacket(component);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        sendPacket(disconnectPacket);
        // Appelle une méthode
        disconnect();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Forcing the player to disconnect.
     */
    // Début d'une méthode/d'un bloc
    public void disconnect() {
        // Accès à l'objet courant/parent
        this.online = false;
        // Appelle une méthode
        final Player player = MinecraftServer.getConnectionManager().getPlayer(this);
        // Embranchement : vérifie une condition
        if (player != null) {
            // Appelle une méthode
            MinecraftServer.getConnectionManager().removePlayer(this);
            // Embranchement : vérifie une condition
            if (serverState == ConnectionState.PLAY && !player.isRemoved())
                // Appelle une méthode
                player.scheduleNextTick(Entity::remove);
            // Branche alternative de la condition
            else {
                // Appelle une méthode
                EventDispatcher.call(new PlayerDisconnectEvent(player));
                // Appelle une méthode
                EventsJFR.newPlayerLeave(player.getUuid()).commit();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the player linked to this connection.
     *
     * @return the player, can be null if not initialized yet
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Player getPlayer() {
        // Renvoie une valeur à l'appelant
        return player;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the player linked to this connection.
     * <p>
     * WARNING: unsafe.
     *
     * @param player the player
     */
    // Début d'une méthode/d'un bloc
    public void setPlayer(Player player) {
        // Accès à l'objet courant/parent
        this.player = player;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the client is still connected to the server.
     *
     * @return true if the player is online, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean isOnline() {
        // Renvoie une valeur à l'appelant
        return online;
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated Use {@link #getClientState()} or {@link #getServerState()} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated(forRemoval = true)
    // Début d'une méthode/d'un bloc
    public ConnectionState getConnectionState() {
        // Renvoie une valeur à l'appelant
        return getClientState();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ConnectionState getServerState() {
        // Renvoie une valeur à l'appelant
        return serverState;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ConnectionState getClientState() {
        // Renvoie une valeur à l'appelant
        return clientState;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setClientState(ConnectionState clientState) {
        // Embranchement : vérifie une condition
        if (this.clientState == ConnectionState.HANDSHAKE)
            // Accès à l'objet courant/parent
            this.serverState = clientState;
        // Accès à l'objet courant/parent
        this.clientState = clientState;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setServerState(ConnectionState serverState) {
        // Accès à l'objet courant/parent
        this.serverState = serverState;
        // Embranchement : vérifie une condition
        if (serverState != ConnectionState.LOGIN) {
            // Clear the plugin request map (it is not used beyond login)
            // Accès à l'objet courant/parent
            this.loginPluginMessageProcessor = null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable PlayerPublicKey playerPublicKey() {
        // Renvoie une valeur à l'appelant
        return playerPublicKey;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setPlayerPublicKey(PlayerPublicKey playerPublicKey) {
        // Accès à l'objet courant/parent
        this.playerPublicKey = playerPublicKey;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void storeCookie(String key, byte[] data) {
        // Appelle une méthode
        sendPacket(new CookieStorePacket(key, data));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public CompletableFuture<byte @Nullable []> fetchCookie(String key) {
        // Embranchement : vérifie une condition
        if (serverState == ConnectionState.CONFIGURATION && getPlayer() == null) {
            // This is a bit of an unfortunate limitation. The player provider blocks the player read virtual
            // thread waiting for the player provider so a cookie response would never be received and the
            // process would deadlock.
            // We cannot create the player provider without blocking the read thread because the client
            // has already sent the initial settings packet, and we need the Player to process the response.
            // We could store the settings on the connection, but it does not seem worth to get around this case.
            // Lève une exception
            throw new IllegalStateException("Cannot fetch cookie in PlayerProvider, use AsyncPlayerPreLoginEvent or AsyncPlayerConfigurationEvent");
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        CompletableFuture<byte[]> future = new CompletableFuture<>();
        // Appelle une méthode
        pendingCookieRequests.put(Key.key(key), future);
        // Appelle une méthode
        sendPacket(new CookieRequestPacket(key));
        // Renvoie une valeur à l'appelant
        return future;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void receiveCookieResponse(String key, byte @Nullable [] data) {
        // Appelle une méthode
        CompletableFuture<byte[]> future = pendingCookieRequests.remove(Key.key(key));
        // Embranchement : vérifie une condition
        if (future != null) {
            // Appelle une méthode
            future.complete(data);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the login plugin message processor, only available during the login state.
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public LoginPluginMessageProcessor loginPluginMessageProcessor() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(this.loginPluginMessageProcessor,
                // Instruction de code
                "Login plugin message processor is only available during the login state.");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public CompletableFuture<List<SelectKnownPacksPacket.Entry>> requestKnownPacks(List<SelectKnownPacksPacket.Entry> serverPacks) {
        // Appelle une méthode
        Check.stateCondition(knownPacksFuture != null, "Known packs already pending");
        // Appelle une méthode
        sendPacket(new SelectKnownPacksPacket(serverPacks));
        // Renvoie une valeur à l'appelant
        return knownPacksFuture = new CompletableFuture<>();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void receiveKnownPacksResponse(List<SelectKnownPacksPacket.Entry> clientPacks) {
        // Affecte une valeur
        final var future = knownPacksFuture;
        // Embranchement : vérifie une condition
        if (future != null) {
            // Appelle une méthode
            future.complete(clientPacks);
            // Affecte une valeur
            knownPacksFuture = null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Attempts to transfer the player to another server, using the {@link TransferPacket}.
     *
     * @param host the host, usually an IP or domain name.
     * @param port the port, usually 25565.
     */
    // Début d'une méthode/d'un bloc
    public void transfer(String host, int port) {
        // Appelle une méthode
        OutgoingTransferEvent event = new OutgoingTransferEvent(this.player, host, port);
        // Appelle une méthode
        EventDispatcher.callCancellable(event, () -> this.sendPacket(new TransferPacket(event.getHost(), event.getPort())));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns whether the player has indicated that they were redirected from another server.
     *
     * @return true if the client marked itself as transferred, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean wasTransferred() {
        // Renvoie une valeur à l'appelant
        return this.wasTransferred;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void markTransferred(boolean wasTransferred) {
        // Embranchement : vérifie une condition
        if (!wasTransferred && this.wasTransferred) {
            // Lève une exception
            throw new IllegalStateException("Cannot mark transferred connection as non-transferred");
        // Fin d'un bloc/d'une expression
        }

        // Accès à l'objet courant/parent
        this.wasTransferred = wasTransferred;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return "PlayerConnection{" +
                // Instruction de code
                "serverState=" + serverState +
                // Instruction de code
                ", clientState=" + clientState +
                // Instruction de code
                ", identifier=" + getIdentifier() +
                // Instruction de code
                '}';
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
