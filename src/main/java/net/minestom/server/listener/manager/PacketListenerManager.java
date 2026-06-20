// Package declaration for this file
package net.minestom.server.listener.manager;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.PlayerPacketEvent;
// Import of a required class
import net.minestom.server.listener.*;
// Import of a required class
import net.minestom.server.listener.common.*;
// Import of a required class
import net.minestom.server.listener.preplay.HandshakeListener;
// Import of a required class
import net.minestom.server.listener.preplay.LoginListener;
// Import of a required class
import net.minestom.server.listener.preplay.StatusListener;
// Import of a required class
import net.minestom.server.network.ConnectionState;
// Import of a required class
import net.minestom.server.network.packet.PacketVanilla;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.network.packet.client.common.*;
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
import net.minestom.server.network.packet.client.play.*;
// Import of a required class
import net.minestom.server.network.packet.client.status.StatusRequestPacket;
// Import of a required class
import net.minestom.server.network.player.PlayerConnection;
// Import of a required class
import org.slf4j.Logger;
// Import of a required class
import org.slf4j.LoggerFactory;

// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;

// Type declaration (class/interface/enum/record)
public final class PacketListenerManager {

    // Calls a method
    private final static Logger LOGGER = LoggerFactory.getLogger(PacketListenerManager.class);

    // Calls a method
    private final Map<Class<? extends ClientPacket>, PacketPrePlayListenerConsumer>[] listeners = new Map[ConnectionState.values().length];

    // Start of a method/block
    public PacketListenerManager() {
        // Loop: repeats a block
        for (int i = 0; i < listeners.length; i++) {
            // Calls a method
            listeners[i] = new ConcurrentHashMap<>();
        // End of a block/expression
        }

        // Calls a method
        setListener(ConnectionState.HANDSHAKE, ClientHandshakePacket.class, HandshakeListener::listener);

        // Calls a method
        setListener(ConnectionState.STATUS, StatusRequestPacket.class, StatusListener::requestListener);
        // Calls a method
        setListener(ConnectionState.STATUS, ClientPingRequestPacket.class, StatusListener::pingRequestListener);

        // Calls a method
        setListener(ConnectionState.LOGIN, ClientLoginStartPacket.class, LoginListener::loginStartListener);
        // Calls a method
        setListener(ConnectionState.LOGIN, ClientEncryptionResponsePacket.class, LoginListener::loginEncryptionResponseListener);
        // Calls a method
        setListener(ConnectionState.LOGIN, ClientLoginPluginResponsePacket.class, LoginListener::loginPluginResponseListener);
        // Calls a method
        setListener(ConnectionState.LOGIN, ClientLoginAcknowledgedPacket.class, LoginListener::loginAckListener);
        // Calls a method
        setListener(ConnectionState.LOGIN, ClientCookieResponsePacket.class, CookieListener::handleCookieResponse);

        // Calls a method
        setConfigurationListener(ClientSettingsPacket.class, SettingsListener::listener);
        // Calls a method
        setConfigurationListener(ClientPluginMessagePacket.class, PluginMessageListener::listener);
        // Calls a method
        setConfigurationListener(ClientKeepAlivePacket.class, KeepAliveListener::listener);
        // Calls a method
        setConfigurationListener(ClientPongPacket.class, (packet, player) -> {/* empty */});
        // Calls a method
        setConfigurationListener(ClientResourcePackStatusPacket.class, ResourcePackListener::listener);
        // Calls a method
        setConfigurationListener(ClientSelectKnownPacksPacket.class, LoginListener::selectKnownPacks);
        // Calls a method
        setConfigurationListener(ClientFinishConfigurationPacket.class, LoginListener::finishConfigListener);
        // Calls a method
        setListener(ConnectionState.CONFIGURATION, ClientCookieResponsePacket.class, CookieListener::handleCookieResponse);
        // Calls a method
        setConfigurationListener(ClientCustomClickActionPacket.class, CustomClickListener::listener);

        // Calls a method
        setPlayListener(ClientKeepAlivePacket.class, KeepAliveListener::listener);
        // Calls a method
        setPlayListener(ClientCommandChatPacket.class, ChatMessageListener::commandChatListener);
        // Calls a method
        setPlayListener(ClientChatMessagePacket.class, ChatMessageListener::chatMessageListener);
        // Calls a method
        setPlayListener(ClientClickWindowPacket.class, WindowListener::clickWindowListener);
        // Calls a method
        setPlayListener(ClientCloseWindowPacket.class, WindowListener::closeWindowListener);
        // Calls a method
        setPlayListener(ClientClickWindowButtonPacket.class, WindowListener::inventoryButtonClickListener);
        // Calls a method
        setPlayListener(ClientConfigurationAckPacket.class, LoginListener::configAckListener);
        // Calls a method
        setPlayListener(ClientPongPacket.class, WindowListener::pong);
        // Calls a method
        setPlayListener(ClientEntityActionPacket.class, EntityActionListener::listener);
        // Calls a method
        setPlayListener(ClientHeldItemChangePacket.class, PlayerHeldListener::heldListener);
        // Calls a method
        setPlayListener(ClientPlayerBlockPlacementPacket.class, BlockPlacementListener::listener);
        // Calls a method
        setPlayListener(ClientInputPacket.class, PlayerInputListener::listener);
        // Calls a method
        setPlayListener(ClientChangeGameModePacket.class, PlayerGameModeChangeListener::listener);
        // Calls a method
        setPlayListener(ClientVehicleMovePacket.class, PlayerVehicleListener::vehicleMoveListener);
        // Calls a method
        setPlayListener(ClientSteerBoatPacket.class, PlayerVehicleListener::boatSteerListener);
        // Calls a method
        setPlayListener(ClientPlayerPositionStatusPacket.class, PlayerPositionListener::playerPacketListener);
        // Calls a method
        setPlayListener(ClientPlayerRotationPacket.class, PlayerPositionListener::playerLookListener);
        // Calls a method
        setPlayListener(ClientPlayerPositionPacket.class, PlayerPositionListener::playerPositionListener);
        // Calls a method
        setPlayListener(ClientPlayerPositionAndRotationPacket.class, PlayerPositionListener::playerPositionAndLookListener);
        // Calls a method
        setPlayListener(ClientTeleportConfirmPacket.class, PlayerPositionListener::teleportConfirmListener);
        // Calls a method
        setPlayListener(ClientPlayerActionPacket.class, PlayerActionListener::playerActionListener);
        // Calls a method
        setPlayListener(ClientAnimationPacket.class, AnimationListener::animationListener);
        // Calls a method
        setPlayListener(ClientInteractEntityPacket.class, UseEntityListener::useEntityListener);
        // Calls a method
        setPlayListener(ClientAttackPacket.class, UseEntityListener::attackEntityListener);
        // Calls a method
        setPlayListener(ClientUseItemPacket.class, UseItemListener::useItemListener);
        // Calls a method
        setPlayListener(ClientPickItemFromBlockPacket.class, PlayerPickListener::playerPickBlockListener);
        // Calls a method
        setPlayListener(ClientPickItemFromEntityPacket.class, PlayerPickListener::playerPickEntityListener);
        // Calls a method
        setPlayListener(ClientStatusPacket.class, PlayStatusListener::listener);
        // Calls a method
        setPlayListener(ClientSettingsPacket.class, SettingsListener::listener);
        // Calls a method
        setPlayListener(ClientCreativeInventoryActionPacket.class, CreativeInventoryActionListener::listener);
        // Calls a method
        setPlayListener(ClientSetRecipeBookStatePacket.class, (packet, player) -> {/* empty */});
        // Calls a method
        setPlayListener(ClientPlaceRecipePacket.class, RecipeListener::listener);
        // Calls a method
        setPlayListener(ClientTabCompletePacket.class, TabCompleteListener::listener);
        // Calls a method
        setPlayListener(ClientPluginMessagePacket.class, PluginMessageListener::listener);
        // Calls a method
        setPlayListener(ClientPlayerAbilitiesPacket.class, AbilitiesListener::listener);
        // Calls a method
        setPlayListener(ClientResourcePackStatusPacket.class, ResourcePackListener::listener);
        // Calls a method
        setPlayListener(ClientAdvancementTabPacket.class, AdvancementTabListener::listener);
        // Calls a method
        setPlayListener(ClientSpectateEntityPacket.class, PlayerSpectatorListener::listener);
        // Calls a method
        setPlayListener(ClientTeleportToEntityPacket.class, PlayerSpectatorListener::listener);
        // Calls a method
        setPlayListener(ClientEditBookPacket.class, BookListener::listener);
        // Calls a method
        setPlayListener(ClientChatSessionUpdatePacket.class, (packet, player) -> {/* empty */});
        // Calls a method
        setPlayListener(ClientChunkBatchReceivedPacket.class, ChunkBatchListener::batchReceivedListener);
        // Calls a method
        setPlayListener(ClientPingRequestPacket.class, PlayPingListener::requestListener);
        // Calls a method
        setListener(ConnectionState.PLAY, ClientCookieResponsePacket.class, CookieListener::handleCookieResponse);
        // Calls a method
        setPlayListener(ClientNameItemPacket.class, AnvilListener::nameItemListener);
        // Calls a method
        setPlayListener(ClientTickEndPacket.class, PlayerTickListener::listener);
        // Calls a method
        setPlayListener(ClientPlayerLoadedPacket.class, PlayerLoadedListener::listener);
        // Calls a method
        setPlayListener(ClientSelectBundleItemPacket.class, (packet, player) -> {/* noop for now */});
        // Calls a method
        setPlayListener(ClientSignedCommandChatPacket.class, ChatMessageListener::signedCommandChatListener);
        // Calls a method
        setPlayListener(ClientCustomClickActionPacket.class, CustomClickListener::listener);
        // Calls a method
        setPlayListener(ClientUpdateSignPacket.class, EditSignListener::listener);
        // Calls a method
        setPlayListener(ClientDebugSubscriptionRequestPacket.class, DebugSubscriptionListener::requestListener);
        // Calls a method
        setPlayListener(ClientSetGameRulesPacket.class, PlayerSettingsMenuListener::setGameRules);
    // End of a block/expression
    }

    /**
     * Processes a packet by getting its {@link PacketPlayListenerConsumer} and calling all the packet listeners.
     *
     * @param packet     the received packet
     * @param connection the connection of the player who sent the packet
     * @param <T>        the packet type
     */
    // Start of a method/block
    public <T extends ClientPacket> void processClientPacket(T packet, PlayerConnection connection) {
        // Update connection state 'as we receive' the packet, aka before we send any responses
        // from processing. This is important for sending packets in response which are state-dependent.
        // Calls a method
        final ConnectionState currState = connection.getClientState();
        // Calls a method
        final ConnectionState nextState = PacketVanilla.nextClientState(packet, currState);
        // Branch: checks a condition
        if (nextState != currState) connection.setClientState(nextState);

        // Calls a method
        final Class clazz = packet.getClass();
        // Calls a method
        PacketPrePlayListenerConsumer<T> packetListenerConsumer = listeners[currState.ordinal()].get(clazz);

        // Listener can be null if none has been set before, call PacketConsumer anyway
        // Branch: checks a condition
        if (packetListenerConsumer == null) {
            // Calls a method
            LOGGER.warn("Packet {}:{} does not have any default listener! (The issue likely comes from Minestom)", clazz, currState);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Event
        // Branch: checks a condition
        if (currState == ConnectionState.PLAY) {
            // Calls a method
            PlayerPacketEvent playerPacketEvent = new PlayerPacketEvent(connection.getPlayer(), packet);
            // Calls a method
            EventDispatcher.call(playerPacketEvent);
            // Branch: checks a condition
            if (playerPacketEvent.isCancelled()) {
                // Returns a value to the caller
                return;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Finally execute the listener
        // Exception handling
        try {
            // Calls a method
            packetListenerConsumer.accept(packet, connection);
        // Start of a method/block
        } catch (Exception e) {
            // Packet is likely invalid
            // Calls a method
            MinecraftServer.getExceptionManager().handleException(e);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Sets the listener of a packet.
     * <p>
     * WARNING: this will overwrite the default minestom listener, this is not reversible.
     *
     * @param state       the state of the packet
     * @param packetClass the class of the packet
     * @param consumer    the new packet's listener
     * @param <T>         the type of the packet
     */
    // Start of a method/block
    public <T extends ClientPacket> void setListener(ConnectionState state, Class<T> packetClass, PacketPrePlayListenerConsumer<T> consumer) {
        // Access to the current/parent object
        this.listeners[state.ordinal()].put(packetClass, consumer);
    // End of a block/expression
    }

    /**
     * Sets the listener of a packet.
     * <p>
     * WARNING: this will overwrite the default minestom listener, this is not reversible.
     *
     * @param packetClass the class of the packet
     * @param consumer    the new packet's listener
     * @param <T>         the type of the packet
     */
    // Start of a method/block
    public <T extends ClientPacket> void setPlayListener(Class<T> packetClass, PacketPlayListenerConsumer<T> consumer) {
        // Calls a method
        setListener(ConnectionState.PLAY, packetClass, (packet, playerConnection) -> consumer.accept(packet, playerConnection.getPlayer()));
    // End of a block/expression
    }

    // Start of a method/block
    public <T extends ClientPacket> void setConfigurationListener(Class<T> packetClass, PacketPlayListenerConsumer<T> consumer) {
        // Calls a method
        setListener(ConnectionState.CONFIGURATION, packetClass, (packet, playerConnection) -> consumer.accept(packet, playerConnection.getPlayer()));
    // End of a block/expression
    }

    /**
     * Sets the listener of a packet.
     * <p>
     * WARNING: this will overwrite the default minestom listener, this is not reversible.
     *
     * @param packetClass the class of the packet
     * @param consumer    the new packet's listener
     * @param <T>         the type of the packet
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public <T extends ClientPacket> void setListener(Class<T> packetClass, PacketPlayListenerConsumer<T> consumer) {
        // Calls a method
        setPlayListener(packetClass, consumer);
    // End of a block/expression
    }

// End of a block/expression
}
