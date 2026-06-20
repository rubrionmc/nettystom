// Déclaration du paquet de ce fichier
package net.minestom.server.listener.manager;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerPacketEvent;
// Import d'une classe nécessaire
import net.minestom.server.listener.*;
// Import d'une classe nécessaire
import net.minestom.server.listener.common.*;
// Import d'une classe nécessaire
import net.minestom.server.listener.preplay.HandshakeListener;
// Import d'une classe nécessaire
import net.minestom.server.listener.preplay.LoginListener;
// Import d'une classe nécessaire
import net.minestom.server.listener.preplay.StatusListener;
// Import d'une classe nécessaire
import net.minestom.server.network.ConnectionState;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketVanilla;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.common.*;
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
import net.minestom.server.network.packet.client.play.*;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.status.StatusRequestPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerConnection;
// Import d'une classe nécessaire
import org.slf4j.Logger;
// Import d'une classe nécessaire
import org.slf4j.LoggerFactory;

// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;

// Déclaration de type (classe/interface/enum/record)
public final class PacketListenerManager {

    // Appelle une méthode
    private final static Logger LOGGER = LoggerFactory.getLogger(PacketListenerManager.class);

    // Appelle une méthode
    private final Map<Class<? extends ClientPacket>, PacketPrePlayListenerConsumer>[] listeners = new Map[ConnectionState.values().length];

    // Début d'une méthode/d'un bloc
    public PacketListenerManager() {
        // Boucle : répète un bloc
        for (int i = 0; i < listeners.length; i++) {
            // Appelle une méthode
            listeners[i] = new ConcurrentHashMap<>();
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        setListener(ConnectionState.HANDSHAKE, ClientHandshakePacket.class, HandshakeListener::listener);

        // Appelle une méthode
        setListener(ConnectionState.STATUS, StatusRequestPacket.class, StatusListener::requestListener);
        // Appelle une méthode
        setListener(ConnectionState.STATUS, ClientPingRequestPacket.class, StatusListener::pingRequestListener);

        // Appelle une méthode
        setListener(ConnectionState.LOGIN, ClientLoginStartPacket.class, LoginListener::loginStartListener);
        // Appelle une méthode
        setListener(ConnectionState.LOGIN, ClientEncryptionResponsePacket.class, LoginListener::loginEncryptionResponseListener);
        // Appelle une méthode
        setListener(ConnectionState.LOGIN, ClientLoginPluginResponsePacket.class, LoginListener::loginPluginResponseListener);
        // Appelle une méthode
        setListener(ConnectionState.LOGIN, ClientLoginAcknowledgedPacket.class, LoginListener::loginAckListener);
        // Appelle une méthode
        setListener(ConnectionState.LOGIN, ClientCookieResponsePacket.class, CookieListener::handleCookieResponse);

        // Appelle une méthode
        setConfigurationListener(ClientSettingsPacket.class, SettingsListener::listener);
        // Appelle une méthode
        setConfigurationListener(ClientPluginMessagePacket.class, PluginMessageListener::listener);
        // Appelle une méthode
        setConfigurationListener(ClientKeepAlivePacket.class, KeepAliveListener::listener);
        // Appelle une méthode
        setConfigurationListener(ClientPongPacket.class, (packet, player) -> {/* empty */});
        // Appelle une méthode
        setConfigurationListener(ClientResourcePackStatusPacket.class, ResourcePackListener::listener);
        // Appelle une méthode
        setConfigurationListener(ClientSelectKnownPacksPacket.class, LoginListener::selectKnownPacks);
        // Appelle une méthode
        setConfigurationListener(ClientFinishConfigurationPacket.class, LoginListener::finishConfigListener);
        // Appelle une méthode
        setListener(ConnectionState.CONFIGURATION, ClientCookieResponsePacket.class, CookieListener::handleCookieResponse);
        // Appelle une méthode
        setConfigurationListener(ClientCustomClickActionPacket.class, CustomClickListener::listener);

        // Appelle une méthode
        setPlayListener(ClientKeepAlivePacket.class, KeepAliveListener::listener);
        // Appelle une méthode
        setPlayListener(ClientCommandChatPacket.class, ChatMessageListener::commandChatListener);
        // Appelle une méthode
        setPlayListener(ClientChatMessagePacket.class, ChatMessageListener::chatMessageListener);
        // Appelle une méthode
        setPlayListener(ClientClickWindowPacket.class, WindowListener::clickWindowListener);
        // Appelle une méthode
        setPlayListener(ClientCloseWindowPacket.class, WindowListener::closeWindowListener);
        // Appelle une méthode
        setPlayListener(ClientClickWindowButtonPacket.class, WindowListener::inventoryButtonClickListener);
        // Appelle une méthode
        setPlayListener(ClientConfigurationAckPacket.class, LoginListener::configAckListener);
        // Appelle une méthode
        setPlayListener(ClientPongPacket.class, WindowListener::pong);
        // Appelle une méthode
        setPlayListener(ClientEntityActionPacket.class, EntityActionListener::listener);
        // Appelle une méthode
        setPlayListener(ClientHeldItemChangePacket.class, PlayerHeldListener::heldListener);
        // Appelle une méthode
        setPlayListener(ClientPlayerBlockPlacementPacket.class, BlockPlacementListener::listener);
        // Appelle une méthode
        setPlayListener(ClientInputPacket.class, PlayerInputListener::listener);
        // Appelle une méthode
        setPlayListener(ClientChangeGameModePacket.class, PlayerGameModeChangeListener::listener);
        // Appelle une méthode
        setPlayListener(ClientVehicleMovePacket.class, PlayerVehicleListener::vehicleMoveListener);
        // Appelle une méthode
        setPlayListener(ClientSteerBoatPacket.class, PlayerVehicleListener::boatSteerListener);
        // Appelle une méthode
        setPlayListener(ClientPlayerPositionStatusPacket.class, PlayerPositionListener::playerPacketListener);
        // Appelle une méthode
        setPlayListener(ClientPlayerRotationPacket.class, PlayerPositionListener::playerLookListener);
        // Appelle une méthode
        setPlayListener(ClientPlayerPositionPacket.class, PlayerPositionListener::playerPositionListener);
        // Appelle une méthode
        setPlayListener(ClientPlayerPositionAndRotationPacket.class, PlayerPositionListener::playerPositionAndLookListener);
        // Appelle une méthode
        setPlayListener(ClientTeleportConfirmPacket.class, PlayerPositionListener::teleportConfirmListener);
        // Appelle une méthode
        setPlayListener(ClientPlayerActionPacket.class, PlayerActionListener::playerActionListener);
        // Appelle une méthode
        setPlayListener(ClientAnimationPacket.class, AnimationListener::animationListener);
        // Appelle une méthode
        setPlayListener(ClientInteractEntityPacket.class, UseEntityListener::useEntityListener);
        // Appelle une méthode
        setPlayListener(ClientAttackPacket.class, UseEntityListener::attackEntityListener);
        // Appelle une méthode
        setPlayListener(ClientUseItemPacket.class, UseItemListener::useItemListener);
        // Appelle une méthode
        setPlayListener(ClientPickItemFromBlockPacket.class, PlayerPickListener::playerPickBlockListener);
        // Appelle une méthode
        setPlayListener(ClientPickItemFromEntityPacket.class, PlayerPickListener::playerPickEntityListener);
        // Appelle une méthode
        setPlayListener(ClientStatusPacket.class, PlayStatusListener::listener);
        // Appelle une méthode
        setPlayListener(ClientSettingsPacket.class, SettingsListener::listener);
        // Appelle une méthode
        setPlayListener(ClientCreativeInventoryActionPacket.class, CreativeInventoryActionListener::listener);
        // Appelle une méthode
        setPlayListener(ClientSetRecipeBookStatePacket.class, (packet, player) -> {/* empty */});
        // Appelle une méthode
        setPlayListener(ClientPlaceRecipePacket.class, RecipeListener::listener);
        // Appelle une méthode
        setPlayListener(ClientTabCompletePacket.class, TabCompleteListener::listener);
        // Appelle une méthode
        setPlayListener(ClientPluginMessagePacket.class, PluginMessageListener::listener);
        // Appelle une méthode
        setPlayListener(ClientPlayerAbilitiesPacket.class, AbilitiesListener::listener);
        // Appelle une méthode
        setPlayListener(ClientResourcePackStatusPacket.class, ResourcePackListener::listener);
        // Appelle une méthode
        setPlayListener(ClientAdvancementTabPacket.class, AdvancementTabListener::listener);
        // Appelle une méthode
        setPlayListener(ClientSpectateEntityPacket.class, PlayerSpectatorListener::listener);
        // Appelle une méthode
        setPlayListener(ClientTeleportToEntityPacket.class, PlayerSpectatorListener::listener);
        // Appelle une méthode
        setPlayListener(ClientEditBookPacket.class, BookListener::listener);
        // Appelle une méthode
        setPlayListener(ClientChatSessionUpdatePacket.class, (packet, player) -> {/* empty */});
        // Appelle une méthode
        setPlayListener(ClientChunkBatchReceivedPacket.class, ChunkBatchListener::batchReceivedListener);
        // Appelle une méthode
        setPlayListener(ClientPingRequestPacket.class, PlayPingListener::requestListener);
        // Appelle une méthode
        setListener(ConnectionState.PLAY, ClientCookieResponsePacket.class, CookieListener::handleCookieResponse);
        // Appelle une méthode
        setPlayListener(ClientNameItemPacket.class, AnvilListener::nameItemListener);
        // Appelle une méthode
        setPlayListener(ClientTickEndPacket.class, PlayerTickListener::listener);
        // Appelle une méthode
        setPlayListener(ClientPlayerLoadedPacket.class, PlayerLoadedListener::listener);
        // Appelle une méthode
        setPlayListener(ClientSelectBundleItemPacket.class, (packet, player) -> {/* noop for now */});
        // Appelle une méthode
        setPlayListener(ClientSignedCommandChatPacket.class, ChatMessageListener::signedCommandChatListener);
        // Appelle une méthode
        setPlayListener(ClientCustomClickActionPacket.class, CustomClickListener::listener);
        // Appelle une méthode
        setPlayListener(ClientUpdateSignPacket.class, EditSignListener::listener);
        // Appelle une méthode
        setPlayListener(ClientDebugSubscriptionRequestPacket.class, DebugSubscriptionListener::requestListener);
        // Appelle une méthode
        setPlayListener(ClientSetGameRulesPacket.class, PlayerSettingsMenuListener::setGameRules);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Processes a packet by getting its {@link PacketPlayListenerConsumer} and calling all the packet listeners.
     *
     * @param packet     the received packet
     * @param connection the connection of the player who sent the packet
     * @param <T>        the packet type
     */
    // Début d'une méthode/d'un bloc
    public <T extends ClientPacket> void processClientPacket(T packet, PlayerConnection connection) {
        // Update connection state 'as we receive' the packet, aka before we send any responses
        // from processing. This is important for sending packets in response which are state-dependent.
        // Appelle une méthode
        final ConnectionState currState = connection.getClientState();
        // Appelle une méthode
        final ConnectionState nextState = PacketVanilla.nextClientState(packet, currState);
        // Embranchement : vérifie une condition
        if (nextState != currState) connection.setClientState(nextState);

        // Appelle une méthode
        final Class clazz = packet.getClass();
        // Appelle une méthode
        PacketPrePlayListenerConsumer<T> packetListenerConsumer = listeners[currState.ordinal()].get(clazz);

        // Listener can be null if none has been set before, call PacketConsumer anyway
        // Embranchement : vérifie une condition
        if (packetListenerConsumer == null) {
            // Appelle une méthode
            LOGGER.warn("Packet {}:{} does not have any default listener! (The issue likely comes from Minestom)", clazz, currState);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Event
        // Embranchement : vérifie une condition
        if (currState == ConnectionState.PLAY) {
            // Appelle une méthode
            PlayerPacketEvent playerPacketEvent = new PlayerPacketEvent(connection.getPlayer(), packet);
            // Appelle une méthode
            EventDispatcher.call(playerPacketEvent);
            // Embranchement : vérifie une condition
            if (playerPacketEvent.isCancelled()) {
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Finally execute the listener
        // Gestion des exceptions
        try {
            // Appelle une méthode
            packetListenerConsumer.accept(packet, connection);
        // Début d'une méthode/d'un bloc
        } catch (Exception e) {
            // Packet is likely invalid
            // Appelle une méthode
            MinecraftServer.getExceptionManager().handleException(e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public <T extends ClientPacket> void setListener(ConnectionState state, Class<T> packetClass, PacketPrePlayListenerConsumer<T> consumer) {
        // Accès à l'objet courant/parent
        this.listeners[state.ordinal()].put(packetClass, consumer);
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public <T extends ClientPacket> void setPlayListener(Class<T> packetClass, PacketPlayListenerConsumer<T> consumer) {
        // Appelle une méthode
        setListener(ConnectionState.PLAY, packetClass, (packet, playerConnection) -> consumer.accept(packet, playerConnection.getPlayer()));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public <T extends ClientPacket> void setConfigurationListener(Class<T> packetClass, PacketPlayListenerConsumer<T> consumer) {
        // Appelle une méthode
        setListener(ConnectionState.CONFIGURATION, packetClass, (packet, playerConnection) -> consumer.accept(packet, playerConnection.getPlayer()));
    // Fin d'un bloc/d'une expression
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
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public <T extends ClientPacket> void setListener(Class<T> packetClass, PacketPlayListenerConsumer<T> consumer) {
        // Appelle une méthode
        setPlayListener(packetClass, consumer);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
