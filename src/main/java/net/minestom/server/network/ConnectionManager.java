// Package declaration for this file
package net.minestom.server.network;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
// Import of a required class
import net.minestom.server.event.player.AsyncPlayerPreLoginEvent;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.listener.preplay.LoginListener;
// Import of a required class
import net.minestom.server.network.packet.server.CachedPacket;
// Import of a required class
import net.minestom.server.network.packet.server.common.KeepAlivePacket;
// Import of a required class
import net.minestom.server.network.packet.server.common.PluginMessagePacket;
// Import of a required class
import net.minestom.server.network.packet.server.configuration.FinishConfigurationPacket;
// Import of a required class
import net.minestom.server.network.packet.server.configuration.ResetChatPacket;
// Import of a required class
import net.minestom.server.network.packet.server.configuration.SelectKnownPacksPacket;
// Import of a required class
import net.minestom.server.network.packet.server.configuration.UpdateEnabledFeaturesPacket;
// Import of a required class
import net.minestom.server.network.packet.server.login.LoginSuccessPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.StartConfigurationPacket;
// Import of a required class
import net.minestom.server.network.player.GameProfile;
// Import of a required class
import net.minestom.server.network.player.PlayerConnection;
// Import of a required class
import net.minestom.server.network.player.PlayerSocketConnection;
// Import of a required class
import net.minestom.server.network.plugin.LoginPluginMessageProcessor;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.StaticProtocolObject;
// Import of a required class
import net.minestom.server.utils.StringUtils;
// Import of a required class
import net.minestom.server.utils.collection.ConcurrentMessageQueues;
// Import of a required class
import org.jctools.queues.MessagePassingQueue;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.slf4j.Logger;
// Import of a required class
import org.slf4j.LoggerFactory;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.*;
// Import of a required class
import java.util.function.Function;

/**
 * Manages the connected clients.
 */
// Type declaration (class/interface/enum/record)
public final class ConnectionManager {
    // Calls a method
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);

    // Calls a method
    private static final Component TIMEOUT_TEXT = Component.text("Timeout", NamedTextColor.RED);
    // Calls a method
    private static final Component SHUTDOWN_TEXT = Component.text("Server shutting down");

    // Calls a method
    private final CachedPacket cachedTagsPacket = new CachedPacket(() -> Registries.tagsPacket(MinecraftServer.process()));

    // All players once their Player object has been instantiated.
    // Calls a method
    private final Map<PlayerConnection, Player> connectionPlayerMap = new ConcurrentHashMap<>();
    // Players waiting to be spawned (post configuration state)
    // Calls a method
    private final MessagePassingQueue<Player> playWaitingPlayers = ConcurrentMessageQueues.mpscUnboundedArrayQueue(64);
    // Players waiting to be (re) configured
    // Calls a method
    private final MessagePassingQueue<Player> configWaitingPlayers = ConcurrentMessageQueues.mpscUnboundedArrayQueue(64);
    // Players in configuration state
    // Calls a method
    private final Set<Player> configurationPlayers = new CopyOnWriteArraySet<>();
    // Players in play state
    // Calls a method
    private final Set<Player> playPlayers = new CopyOnWriteArraySet<>();

    // The players who need keep alive ticks. This was added because we may not send a keep alive in
    // the time after sending finish configuration but before receiving configuration end (to swap to play).
    // I(mattw) could not come up with a better way to express this besides completely splitting client/server
    // states. Perhaps there will be an improvement in the future.
    // Calls a method
    private final Set<Player> keepAlivePlayers = new CopyOnWriteArraySet<>();

    // Calls a method
    private final Set<Player> unmodifiableConfigurationPlayers = Collections.unmodifiableSet(configurationPlayers);
    // Calls a method
    private final Set<Player> unmodifiablePlayPlayers = Collections.unmodifiableSet(playPlayers);

    // The player provider to have your own Player implementation
    // Assigns a value
    private volatile PlayerProvider playerProvider = Player::new;

    /**
     * Gets the number of "online" players, e.g. for the query response.
     *
     * <p>Only includes players in the play state, not players in configuration.</p>
     */
    // Start of a method/block
    public int getOnlinePlayerCount() {
        // Returns a value to the caller
        return playPlayers.size();
    // End of a block/expression
    }

    /**
     * Returns an unmodifiable set containing the players currently in the play state.
     */
    // Start of a method/block
    public Collection<Player> getOnlinePlayers() {
        // Returns a value to the caller
        return unmodifiablePlayPlayers;
    // End of a block/expression
    }

    /**
     * Returns an unmodifiable set containing the players currently in the configuration state.
     */
    // Start of a method/block
    public Collection<Player> getConfigPlayers() {
        // Returns a value to the caller
        return unmodifiableConfigurationPlayers;
    // End of a block/expression
    }

    /**
     * Gets the {@link Player} linked to a {@link PlayerConnection}.
     *
     * <p>The player will be returned whether they are in the play or config state,
     * so be sure to check before sending packets to them.</p>
     *
     * @param connection the player connection
     * @return the player linked to the connection
     */
    // Start of a method/block
    public @Nullable Player getPlayer(PlayerConnection connection) {
        // Returns a value to the caller
        return connectionPlayerMap.get(connection);
    // End of a block/expression
    }

    /**
     * Gets the first player in the play state which validates {@link String#equalsIgnoreCase(String)}.
     * <p>
     * This can cause issue if two or more players have the same username.
     *
     * @param username the player username (case-insensitive)
     * @return the first player who validate the username condition, null if none was found
     */
    // Start of a method/block
    public @Nullable Player getOnlinePlayerByUsername(String username) {
        // Loop: repeats a block
        for (Player player : getOnlinePlayers()) {
            // Branch: checks a condition
            if (player.getUsername().equalsIgnoreCase(username))
                // Returns a value to the caller
                return player;
        // End of a block/expression
        }
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }

    /**
     * Gets the first player in the play state which validates {@link UUID#equals(Object)}.
     * <p>
     * This can cause issue if two or more players have the same UUID.
     *
     * @param uuid the player UUID
     * @return the first player who validate the UUID condition, null if none was found
     */
    // Start of a method/block
    public @Nullable Player getOnlinePlayerByUuid(UUID uuid) {
        // Loop: repeats a block
        for (Player player : getOnlinePlayers()) {
            // Branch: checks a condition
            if (player.getUuid().equals(uuid))
                // Returns a value to the caller
                return player;
        // End of a block/expression
        }
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }

    /**
     * Finds the closest player in the play state matching a given username.
     *
     * @param username the player username (can be partial)
     * @return the closest match, null if no players are online
     */
    // Start of a method/block
    public @Nullable Player findOnlinePlayer(String username) {
        // Calls a method
        Player exact = getOnlinePlayerByUsername(username);
        // Branch: checks a condition
        if (exact != null) return exact;
        // Calls a method
        final String username1 = username.toLowerCase(Locale.ROOT);

        // Assigns a value
        Function<Player, Double> distanceFunction = player -> {
            // Calls a method
            final String username2 = player.getUsername().toLowerCase(Locale.ROOT);
            // Returns a value to the caller
            return StringUtils.jaroWinklerScore(username1, username2);
        // End of a block/expression
        };
        // Returns a value to the caller
        return getOnlinePlayers().stream()
                // Code statement
                .max(Comparator.comparingDouble(distanceFunction::apply))
                // Code statement
                .filter(player -> distanceFunction.apply(player) > 0)
                // Calls a method
                .orElse(null);
    // End of a block/expression
    }

    /**
     * Changes the {@link Player} provider, to change which object to link to him.
     *
     * @param playerProvider the new {@link PlayerProvider}, can be set to null to apply the default provider
     */
    // Start of a method/block
    public void setPlayerProvider(@Nullable PlayerProvider playerProvider) {
        // Access to the current/parent object
        this.playerProvider = playerProvider != null ? playerProvider : Player::new;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public Player createPlayer(PlayerConnection connection, GameProfile gameProfile) {
        // Calls a method
        assert ServerFlag.INSIDE_TEST || Thread.currentThread().isVirtual();
        // Calls a method
        final Player player = playerProvider.createPlayer(connection, gameProfile);
        // Access to the current/parent object
        this.connectionPlayerMap.put(connection, player);
        // Returns a value to the caller
        return player;
    // End of a block/expression
    }

    // Start of a method/block
    public void sendRegistryTags(Player player) {
        // Calls a method
        player.sendPacket(cachedTagsPacket);
    // End of a block/expression
    }

    // This is a somewhat weird implementation where connectionmanager owns the caching of tags.
    // There should be no registry->connectionmanager communication.
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void invalidateTags() {
        // Access to the current/parent object
        this.cachedTagsPacket.invalidate();
    // End of a block/expression
    }

    // Start of a method/block
    public GameProfile transitionLoginToConfig(PlayerConnection connection, GameProfile gameProfile) {
        // Calls a method
        assert ServerFlag.INSIDE_TEST || Thread.currentThread().isVirtual();
        // Compression
        // Branch: checks a condition
        if (connection instanceof PlayerSocketConnection socketConnection) {
            // Calls a method
            final int threshold = MinecraftServer.getCompressionThreshold();
            // Branch: checks a condition
            if (threshold > 0) socketConnection.startCompression();
        // End of a block/expression
        }
        // Call pre login event
        // Calls a method
        LoginPluginMessageProcessor pluginMessageProcessor = connection.loginPluginMessageProcessor();
        // Calls a method
        AsyncPlayerPreLoginEvent asyncPlayerPreLoginEvent = new AsyncPlayerPreLoginEvent(connection, gameProfile, pluginMessageProcessor);
        // Calls a method
        EventDispatcher.call(asyncPlayerPreLoginEvent);
        // Branch: checks a condition
        if (!connection.isOnline()) return gameProfile; // Player has been kicked
        // Change UUID/Username based on the event
        // Calls a method
        gameProfile = asyncPlayerPreLoginEvent.getGameProfile();
        // Wait for pending login plugin messages
        // Exception handling
        try {
            // Calls a method
            pluginMessageProcessor.awaitReplies(ServerFlag.LOGIN_PLUGIN_MESSAGE_TIMEOUT, TimeUnit.MILLISECONDS);
        // Start of a method/block
        } catch (Throwable t) {
            // Calls a method
            connection.kick(LoginListener.INVALID_PROXY_RESPONSE);
            // Throws an exception
            throw new RuntimeException("Error getting replies for login plugin messages", t);
        // End of a block/expression
        }
        // Send login success packet (and switch to configuration phase)
        // Calls a method
        connection.sendPacket(new LoginSuccessPacket(gameProfile));
        // Returns a value to the caller
        return gameProfile;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void transitionPlayToConfig(Player player) {
        // Calls a method
        configWaitingPlayers.relaxedOffer(player);
    // End of a block/expression
    }

    /**
     * Return value exposed for testing
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void doConfiguration(Player player, boolean isFirstConfig) {
        // Calls a method
        assert ServerFlag.INSIDE_TEST || Thread.currentThread().isVirtual();
        // Branch: checks a condition
        if (isFirstConfig) {
            // Calls a method
            configurationPlayers.add(player);
            // Calls a method
            keepAlivePlayers.add(player);
        // End of a block/expression
        }
        // Calls a method
        player.sendPacket(PluginMessagePacket.brandPacket(MinecraftServer.getBrandName()));
        // Request known packs immediately, but don't wait for the response until required (sending registry data).
        // Calls a method
        final var knownPacksFuture = player.getPlayerConnection().requestKnownPacks(List.of(SelectKnownPacksPacket.MINECRAFT_CORE));

        // Calls a method
        var event = new AsyncPlayerConfigurationEvent(player, isFirstConfig);
        // Calls a method
        EventDispatcher.call(event);
        // Branch: checks a condition
        if (!player.isOnline()) return; // Player was kicked during config.

        // send player features that were enabled or disabled during async config event
        // Calls a method
        player.sendPacket(new UpdateEnabledFeaturesPacket(event.getFeatureFlags().stream().map(StaticProtocolObject::name).toList()));

        // Calls a method
        final Instance spawningInstance = event.getSpawningInstance();
        // Calls a method
        Objects.requireNonNull(spawningInstance, "You need to specify a spawning instance in the AsyncPlayerConfigurationEvent");

        // Branch: checks a condition
        if (event.willClearChat()) player.sendPacket(new ResetChatPacket());

        // Registry data (if it should be sent)
        // Branch: checks a condition
        if (event.willSendRegistryData()) {
            // Code statement
            List<SelectKnownPacksPacket.Entry> knownPacks;
            // Exception handling
            try {
                // Calls a method
                knownPacks = knownPacksFuture.get(ServerFlag.KNOWN_PACKS_RESPONSE_TIMEOUT, TimeUnit.MILLISECONDS);
            // Start of a method/block
            } catch (InterruptedException | TimeoutException e) {
                // Calls a method
                LOGGER.warn("Player {} failed to respond to known packs query", player.getUsername());
                // Calls a method
                player.getPlayerConnection().disconnect();
                // Returns a value to the caller
                return;
            // Start of a method/block
            } catch (ExecutionException e) {
                // Throws an exception
                throw new RuntimeException("Error receiving known packs", e);
            // End of a block/expression
            }
            // Calls a method
            boolean excludeVanilla = knownPacks.contains(SelectKnownPacksPacket.MINECRAFT_CORE);

            // Calls a method
            Registries registries = MinecraftServer.process();
            // Calls a method
            player.sendPackets(Registries.registryDataPackets(registries, excludeVanilla));
            // TODO: TEST_ENVIRONMENT, TEST_INSTANCE

            // Calls a method
            sendRegistryTags(player);
        // End of a block/expression
        }

        // Wait for pending resource packs if any
        // Calls a method
        var packFuture = player.getResourcePackFuture();
        // Branch: checks a condition
        if (packFuture != null) packFuture.join();

        // Calls a method
        keepAlivePlayers.remove(player);
        // Calls a method
        player.setPendingOptions(spawningInstance, event.isHardcore());
        // Calls a method
        player.sendPacket(new FinishConfigurationPacket());
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void transitionConfigToPlay(Player player) {
        // Access to the current/parent object
        this.playWaitingPlayers.relaxedOffer(player);
    // End of a block/expression
    }

    /**
     * Removes a {@link Player} from the players list.
     * <p>
     * Used during disconnection, you shouldn't have to do it manually.
     *
     * @param connection the player connection
     * @see PlayerConnection#disconnect() to properly disconnect a player
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public synchronized void removePlayer(PlayerConnection connection) {
        // Calls a method
        final Player player = this.connectionPlayerMap.remove(connection);
        // Branch: checks a condition
        if (player == null) return;
        // Access to the current/parent object
        this.configurationPlayers.remove(player);
        // Access to the current/parent object
        this.playPlayers.remove(player);
        // Access to the current/parent object
        this.keepAlivePlayers.remove(player);
    // End of a block/expression
    }

    /**
     * Shutdowns the connection manager by kicking all the currently connected players.
     */
    // Start of a method/block
    public synchronized void shutdown() {
        // Loop: repeats a block
        for (final PlayerConnection configPlayer : connectionPlayerMap.keySet())
            // Calls a method
            configPlayer.kick(SHUTDOWN_TEXT);
        // Access to the current/parent object
        this.configurationPlayers.clear();
        // Loop: repeats a block
        for (final Player playPlayer : playPlayers)
            // Calls a method
            playPlayer.kick(SHUTDOWN_TEXT);
        // Access to the current/parent object
        this.playPlayers.clear();

        // Access to the current/parent object
        this.keepAlivePlayers.clear();
        // Access to the current/parent object
        this.connectionPlayerMap.clear();
    // End of a block/expression
    }

    // Start of a method/block
    public void tick(long tickStart) {
        // Let waiting players into their instances
        // Calls a method
        updateWaitingPlayers();

        // Send keep alive packets
        // Calls a method
        handleKeepAlive(keepAlivePlayers, tickStart);

        // Interpret packets for configuration players
        // Calls a method
        configurationPlayers.forEach(Player::interpretPacketQueue);
    // End of a block/expression
    }

    /**
     * Connects waiting players.
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void updateWaitingPlayers() {
        // Access to the current/parent object
        this.configWaitingPlayers.drain(player -> {
            // In case the method was called multiple times, the player disconnected, etc. just ignore it.
            // Branch: checks a condition
            if (!playPlayers.remove(player)) return;

            // Calls a method
            configurationPlayers.add(player);
            // Calls a method
            player.remove(false);
            // Calls a method
            player.sendPacket(new StartConfigurationPacket());
        // End of a block/expression
        });
        // Access to the current/parent object
        this.playWaitingPlayers.drain(player -> {
            // Branch: checks a condition
            if (!player.isOnline()) return; // Player disconnected while in queued to join
            // Calls a method
            configurationPlayers.remove(player);
            // Calls a method
            playPlayers.add(player);
            // Calls a method
            keepAlivePlayers.add(player);

            // This fixes a bug with Geyser. They do not reply to keep alive during config, meaning that
            // `Player#didAnswerKeepAlive()` will always be false when entering the play state, so a new keep
            // alive will never be sent and they will disconnect themselves or we will kick them for not replying.
            // Calls a method
            player.refreshAnswerKeepAlive(true);

            // Spawn the player at Player#getRespawnPoint
            // Calls a method
            CompletableFuture<Void> spawnFuture = player.UNSAFE_init();

            // Required to get the exact moment the player spawns
            // Branch: checks a condition
            if (ServerFlag.INSIDE_TEST) spawnFuture.join();
        // End of a block/expression
        });
    // End of a block/expression
    }

    /**
     * Updates keep alive by checking the last keep alive packet and send a new one if needed.
     *
     * @param tickStart the time of the update in nanoseconds, forwarded to the packet
     */
    // Start of a method/block
    private void handleKeepAlive(Collection<Player> playerGroup, long tickStart) {
        // Calls a method
        final KeepAlivePacket keepAlivePacket = new KeepAlivePacket(tickStart);
        // Loop: repeats a block
        for (Player player : playerGroup) {
            // Calls a method
            final long lastKeepAlive = tickStart - player.getLastKeepAlive();
            // Branch: checks a condition
            if (lastKeepAlive > TimeUnit.MILLISECONDS.toNanos(ServerFlag.KEEP_ALIVE_DELAY) && player.didAnswerKeepAlive()) {
                // Calls a method
                player.refreshKeepAlive(tickStart);
                // Calls a method
                player.sendPacket(keepAlivePacket);
            // Branch: checks a condition
            } else if (lastKeepAlive >= TimeUnit.MILLISECONDS.toNanos(ServerFlag.KEEP_ALIVE_KICK)) {
                // Calls a method
                player.kick(TIMEOUT_TEXT);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
