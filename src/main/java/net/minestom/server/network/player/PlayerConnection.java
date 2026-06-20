// Package declaration for this file
package net.minestom.server.network.player;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.crypto.PlayerPublicKey;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.OutgoingTransferEvent;
// Import of a required class
import net.minestom.server.event.player.PlayerDisconnectEvent;
// Import of a required class
import net.minestom.server.monitoring.EventsJFR;
// Import of a required class
import net.minestom.server.network.ConnectionState;
// Import of a required class
import net.minestom.server.network.packet.server.SendablePacket;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.network.packet.server.common.CookieRequestPacket;
// Import of a required class
import net.minestom.server.network.packet.server.common.CookieStorePacket;
// Import of a required class
import net.minestom.server.network.packet.server.common.DisconnectPacket;
// Import of a required class
import net.minestom.server.network.packet.server.common.TransferPacket;
// Import of a required class
import net.minestom.server.network.packet.server.configuration.SelectKnownPacksPacket;
// Import of a required class
import net.minestom.server.network.packet.server.login.LoginDisconnectPacket;
// Import of a required class
import net.minestom.server.network.plugin.LoginPluginMessageProcessor;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.net.SocketAddress;
// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.concurrent.CompletableFuture;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;

/**
 * A PlayerConnection is an object needed for all created {@link Player}.
 * It can be extended to create a new kind of player (NPC for instance).
 */
// Type declaration (class/interface/enum/record)
public abstract class PlayerConnection {
    // Code statement
    private Player player;

    // Server & client states can differ during configuration.
    // "server" state means the state the server thinks its in.
    // "client" state means the state the client thinks its in.
    // For example, after sending start configuration but before receiving the ack,
    // the server will be in CONFIGURATION while the client is still in PLAY.
    // Code statement
    private volatile ConnectionState serverState, clientState;

    // Code statement
    private @Nullable PlayerPublicKey playerPublicKey;
    // Code statement
    volatile boolean online;
    // Code statement
    private volatile boolean wasTransferred;

    // Calls a method
    private @Nullable LoginPluginMessageProcessor loginPluginMessageProcessor = new LoginPluginMessageProcessor(this);

    // Assigns a value
    private @Nullable CompletableFuture<List<SelectKnownPacksPacket.Entry>> knownPacksFuture = null; // Present only when waiting for a response from the client.

    // Calls a method
    private final Map<Key, CompletableFuture<byte @Nullable []>> pendingCookieRequests = new ConcurrentHashMap<>();

    // Start of a method/block
    public PlayerConnection() {
        // Access to the current/parent object
        this.online = true;
        // Access to the current/parent object
        this.serverState = ConnectionState.HANDSHAKE;
        // Access to the current/parent object
        this.clientState = ConnectionState.HANDSHAKE;
    // End of a block/expression
    }

    /**
     * Returns a printable identifier for this connection, will be the player username
     * or the connection remote address.
     *
     * @return this connection identifier
     */
    // Start of a method/block
    public String getIdentifier() {
        // Calls a method
        final Player player = getPlayer();
        // Returns a value to the caller
        return player != null ?
                // Code statement
                player.getUsername() :
                // Calls a method
                getRemoteAddress().toString();
    // End of a block/expression
    }

    /**
     * Serializes the packet and send it to the client.
     *
     * @param packet the packet to send
     */
    // Calls a method
    public abstract void sendPacket(SendablePacket packet);

    // Start of a method/block
    public void sendPackets(Collection<SendablePacket> packets) {
        // Calls a method
        packets.forEach(this::sendPacket);
    // End of a block/expression
    }

    // Start of a method/block
    public void sendPackets(SendablePacket... packets) {
        // Calls a method
        sendPackets(List.of(packets));
    // End of a block/expression
    }

    /**
     * Gets the remote address of the client.
     *
     * @return the remote address
     */
    // Calls a method
    public abstract SocketAddress getRemoteAddress();

    /**
     * Gets protocol version of client.
     *
     * @return the protocol version
     */
    // Start of a method/block
    public int getProtocolVersion() {
        // Returns a value to the caller
        return MinecraftServer.PROTOCOL_VERSION;
    // End of a block/expression
    }

    /**
     * Gets the server address that the client used to connect.
     * <p>
     * WARNING: it is given by the client, it is possible for it to be wrong.
     *
     * @return the server address used
     */
    // Start of a method/block
    public @Nullable String getServerAddress() {
        // Returns a value to the caller
        return MinecraftServer.getServer().getAddress();
    // End of a block/expression
    }


    /**
     * Gets the server port that the client used to connect.
     * <p>
     * WARNING: it is given by the client, it is possible for it to be wrong.
     *
     * @return the server port used
     */
    // Start of a method/block
    public int getServerPort() {
        // Returns a value to the caller
        return MinecraftServer.getServer().getPort();
    // End of a block/expression
    }


    /**
     * Kicks the player with a reason.
     *
     * @param component the reason
     */
    // Start of a method/block
    public void kick(Component component) {
        // Packet type depends on the current player connection state
        // Code statement
        final ServerPacket disconnectPacket;
        // Branch: checks a condition
        if (serverState == ConnectionState.LOGIN) {
            // Calls a method
            disconnectPacket = new LoginDisconnectPacket(component);
        // Alternative branch of the condition
        } else {
            // Calls a method
            disconnectPacket = new DisconnectPacket(component);
        // End of a block/expression
        }
        // Calls a method
        sendPacket(disconnectPacket);
        // Calls a method
        disconnect();
    // End of a block/expression
    }

    /**
     * Forcing the player to disconnect.
     */
    // Start of a method/block
    public void disconnect() {
        // Access to the current/parent object
        this.online = false;
        // Calls a method
        final Player player = MinecraftServer.getConnectionManager().getPlayer(this);
        // Branch: checks a condition
        if (player != null) {
            // Calls a method
            MinecraftServer.getConnectionManager().removePlayer(this);
            // Branch: checks a condition
            if (serverState == ConnectionState.PLAY && !player.isRemoved())
                // Calls a method
                player.scheduleNextTick(Entity::remove);
            // Alternative branch of the condition
            else {
                // Calls a method
                EventDispatcher.call(new PlayerDisconnectEvent(player));
                // Calls a method
                EventsJFR.newPlayerLeave(player.getUuid()).commit();
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets the player linked to this connection.
     *
     * @return the player, can be null if not initialized yet
     */
    // Start of a method/block
    public @Nullable Player getPlayer() {
        // Returns a value to the caller
        return player;
    // End of a block/expression
    }

    /**
     * Changes the player linked to this connection.
     * <p>
     * WARNING: unsafe.
     *
     * @param player the player
     */
    // Start of a method/block
    public void setPlayer(Player player) {
        // Access to the current/parent object
        this.player = player;
    // End of a block/expression
    }

    /**
     * Gets if the client is still connected to the server.
     *
     * @return true if the player is online, false otherwise
     */
    // Start of a method/block
    public boolean isOnline() {
        // Returns a value to the caller
        return online;
    // End of a block/expression
    }

    /**
     * @deprecated Use {@link #getClientState()} or {@link #getServerState()} instead.
     */
    // Annotation for the following element
    @Deprecated(forRemoval = true)
    // Start of a method/block
    public ConnectionState getConnectionState() {
        // Returns a value to the caller
        return getClientState();
    // End of a block/expression
    }

    // Start of a method/block
    public ConnectionState getServerState() {
        // Returns a value to the caller
        return serverState;
    // End of a block/expression
    }

    // Start of a method/block
    public ConnectionState getClientState() {
        // Returns a value to the caller
        return clientState;
    // End of a block/expression
    }

    // Start of a method/block
    public void setClientState(ConnectionState clientState) {
        // Branch: checks a condition
        if (this.clientState == ConnectionState.HANDSHAKE)
            // Access to the current/parent object
            this.serverState = clientState;
        // Access to the current/parent object
        this.clientState = clientState;
    // End of a block/expression
    }

    // Start of a method/block
    public void setServerState(ConnectionState serverState) {
        // Access to the current/parent object
        this.serverState = serverState;
        // Branch: checks a condition
        if (serverState != ConnectionState.LOGIN) {
            // Clear the plugin request map (it is not used beyond login)
            // Access to the current/parent object
            this.loginPluginMessageProcessor = null;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable PlayerPublicKey playerPublicKey() {
        // Returns a value to the caller
        return playerPublicKey;
    // End of a block/expression
    }

    // Start of a method/block
    public void setPlayerPublicKey(PlayerPublicKey playerPublicKey) {
        // Access to the current/parent object
        this.playerPublicKey = playerPublicKey;
    // End of a block/expression
    }

    // Start of a method/block
    public void storeCookie(String key, byte[] data) {
        // Calls a method
        sendPacket(new CookieStorePacket(key, data));
    // End of a block/expression
    }

    // Start of a method/block
    public CompletableFuture<byte @Nullable []> fetchCookie(String key) {
        // Branch: checks a condition
        if (serverState == ConnectionState.CONFIGURATION && getPlayer() == null) {
            // This is a bit of an unfortunate limitation. The player provider blocks the player read virtual
            // thread waiting for the player provider so a cookie response would never be received and the
            // process would deadlock.
            // We cannot create the player provider without blocking the read thread because the client
            // has already sent the initial settings packet, and we need the Player to process the response.
            // We could store the settings on the connection, but it does not seem worth to get around this case.
            // Throws an exception
            throw new IllegalStateException("Cannot fetch cookie in PlayerProvider, use AsyncPlayerPreLoginEvent or AsyncPlayerConfigurationEvent");
        // End of a block/expression
        }
        // Calls a method
        CompletableFuture<byte[]> future = new CompletableFuture<>();
        // Calls a method
        pendingCookieRequests.put(Key.key(key), future);
        // Calls a method
        sendPacket(new CookieRequestPacket(key));
        // Returns a value to the caller
        return future;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void receiveCookieResponse(String key, byte @Nullable [] data) {
        // Calls a method
        CompletableFuture<byte[]> future = pendingCookieRequests.remove(Key.key(key));
        // Branch: checks a condition
        if (future != null) {
            // Calls a method
            future.complete(data);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets the login plugin message processor, only available during the login state.
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public LoginPluginMessageProcessor loginPluginMessageProcessor() {
        // Returns a value to the caller
        return Objects.requireNonNull(this.loginPluginMessageProcessor,
                // Code statement
                "Login plugin message processor is only available during the login state.");
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public CompletableFuture<List<SelectKnownPacksPacket.Entry>> requestKnownPacks(List<SelectKnownPacksPacket.Entry> serverPacks) {
        // Calls a method
        Check.stateCondition(knownPacksFuture != null, "Known packs already pending");
        // Calls a method
        sendPacket(new SelectKnownPacksPacket(serverPacks));
        // Returns a value to the caller
        return knownPacksFuture = new CompletableFuture<>();
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void receiveKnownPacksResponse(List<SelectKnownPacksPacket.Entry> clientPacks) {
        // Assigns a value
        final var future = knownPacksFuture;
        // Branch: checks a condition
        if (future != null) {
            // Calls a method
            future.complete(clientPacks);
            // Assigns a value
            knownPacksFuture = null;
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Attempts to transfer the player to another server, using the {@link TransferPacket}.
     *
     * @param host the host, usually an IP or domain name.
     * @param port the port, usually 25565.
     */
    // Start of a method/block
    public void transfer(String host, int port) {
        // Calls a method
        OutgoingTransferEvent event = new OutgoingTransferEvent(this.player, host, port);
        // Calls a method
        EventDispatcher.callCancellable(event, () -> this.sendPacket(new TransferPacket(event.getHost(), event.getPort())));
    // End of a block/expression
    }

    /**
     * Returns whether the player has indicated that they were redirected from another server.
     *
     * @return true if the client marked itself as transferred, false otherwise
     */
    // Start of a method/block
    public boolean wasTransferred() {
        // Returns a value to the caller
        return this.wasTransferred;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void markTransferred(boolean wasTransferred) {
        // Branch: checks a condition
        if (!wasTransferred && this.wasTransferred) {
            // Throws an exception
            throw new IllegalStateException("Cannot mark transferred connection as non-transferred");
        // End of a block/expression
        }

        // Access to the current/parent object
        this.wasTransferred = wasTransferred;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return "PlayerConnection{" +
                // Code statement
                "serverState=" + serverState +
                // Code statement
                ", clientState=" + clientState +
                // Code statement
                ", identifier=" + getIdentifier() +
                // Code statement
                '}';
    // End of a block/expression
    }
// End of a block/expression
}
