// Déclaration du paquet de ce fichier
package net.minestom.server.network;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.player.AsyncPlayerPreLoginEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.listener.preplay.LoginListener;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.CachedPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.KeepAlivePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.PluginMessagePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.TagsPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.configuration.FinishConfigurationPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.configuration.ResetChatPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.configuration.SelectKnownPacksPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.configuration.UpdateEnabledFeaturesPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.login.LoginSuccessPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.StartConfigurationPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.player.GameProfile;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerConnection;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerSocketConnection;
// Import d'une classe nécessaire
import net.minestom.server.network.plugin.LoginPluginMessageProcessor;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.registry.StaticProtocolObject;
// Import d'une classe nécessaire
import net.minestom.server.utils.StringUtils;
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
import org.slf4j.Logger;
// Import d'une classe nécessaire
import org.slf4j.LoggerFactory;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.*;
// Import d'une classe nécessaire
import java.util.function.Function;

/**
 * Manages the connected clients.
 */
// Déclaration de type (classe/interface/enum/record)
public final class ConnectionManager {
    // Appelle une méthode
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);

    // Appelle une méthode
    private static final Component TIMEOUT_TEXT = Component.text("Timeout", NamedTextColor.RED);
    // Appelle une méthode
    private static final Component SHUTDOWN_TEXT = Component.text("Server shutting down");

    // Appelle une méthode
    private final CachedPacket cachedTagsPacket = new CachedPacket(this::createTagsPacket);

    // All players once their Player object has been instantiated.
    // Affecte une valeur
    private final Map<PlayerConnection, Player> connectionPlayerMap = new ConcurrentHashMap<>();
    // Players waiting to be spawned (post configuration state)
    // Appelle une méthode
    private final MessagePassingQueue<Player> playWaitingPlayers = ConcurrentMessageQueues.mpscUnboundedArrayQueue(64);
    // Players waiting to be (re) configured
    // Appelle une méthode
    private final MessagePassingQueue<Player> configWaitingPlayers = ConcurrentMessageQueues.mpscUnboundedArrayQueue(64);
    // Players in configuration state
    // Affecte une valeur
    private final Set<Player> configurationPlayers = new CopyOnWriteArraySet<>();
    // Players in play state
    // Affecte une valeur
    private final Set<Player> playPlayers = new CopyOnWriteArraySet<>();

    // The players who need keep alive ticks. This was added because we may not send a keep alive in
    // the time after sending finish configuration but before receiving configuration end (to swap to play).
    // I(mattw) could not come up with a better way to express this besides completely splitting client/server
    // states. Perhaps there will be an improvement in the future.
    // Affecte une valeur
    private final Set<Player> keepAlivePlayers = new CopyOnWriteArraySet<>();

    // Appelle une méthode
    private final Set<Player> unmodifiableConfigurationPlayers = Collections.unmodifiableSet(configurationPlayers);
    // Appelle une méthode
    private final Set<Player> unmodifiablePlayPlayers = Collections.unmodifiableSet(playPlayers);

    // The player provider to have your own Player implementation
    // Affecte une valeur
    private volatile PlayerProvider playerProvider = Player::new;

    /**
     * Gets the number of "online" players, eg for the query response.
     *
     * <p>Only includes players in the play state, not players in configuration.</p>
     */
    // Début d'une méthode/d'un bloc
    public int getOnlinePlayerCount() {
        // Renvoie une valeur à l'appelant
        return playPlayers.size();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns an unmodifiable set containing the players currently in the play state.
     */
    // Début d'une méthode/d'un bloc
    public Collection<Player> getOnlinePlayers() {
        // Renvoie une valeur à l'appelant
        return unmodifiablePlayPlayers;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns an unmodifiable set containing the players currently in the configuration state.
     */
    // Début d'une méthode/d'un bloc
    public Collection<Player> getConfigPlayers() {
        // Renvoie une valeur à l'appelant
        return unmodifiableConfigurationPlayers;
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public Player getPlayer(PlayerConnection connection) {
        // Renvoie une valeur à l'appelant
        return connectionPlayerMap.get(connection);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the first player in the play state which validates {@link String#equalsIgnoreCase(String)}.
     * <p>
     * This can cause issue if two or more players have the same username.
     *
     * @param username the player username (case-insensitive)
     * @return the first player who validate the username condition, null if none was found
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Player getOnlinePlayerByUsername(String username) {
        // Boucle : répète un bloc
        for (Player player : getOnlinePlayers()) {
            // Embranchement : vérifie une condition
            if (player.getUsername().equalsIgnoreCase(username))
                // Renvoie une valeur à l'appelant
                return player;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the first player in the play state which validates {@link UUID#equals(Object)}.
     * <p>
     * This can cause issue if two or more players have the same UUID.
     *
     * @param uuid the player UUID
     * @return the first player who validate the UUID condition, null if none was found
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Player getOnlinePlayerByUuid(UUID uuid) {
        // Boucle : répète un bloc
        for (Player player : getOnlinePlayers()) {
            // Embranchement : vérifie une condition
            if (player.getUuid().equals(uuid))
                // Renvoie une valeur à l'appelant
                return player;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Finds the closest player in the play state matching a given username.
     *
     * @param username the player username (can be partial)
     * @return the closest match, null if no players are online
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Player findOnlinePlayer(String username) {
        // Appelle une méthode
        Player exact = getOnlinePlayerByUsername(username);
        // Embranchement : vérifie une condition
        if (exact != null) return exact;
        // Appelle une méthode
        final String username1 = username.toLowerCase(Locale.ROOT);

        // Affecte une valeur
        Function<Player, Double> distanceFunction = player -> {
            // Appelle une méthode
            final String username2 = player.getUsername().toLowerCase(Locale.ROOT);
            // Renvoie une valeur à l'appelant
            return StringUtils.jaroWinklerScore(username1, username2);
        // Fin d'un bloc/d'une expression
        };
        // Renvoie une valeur à l'appelant
        return getOnlinePlayers().stream()
                // Instruction de code
                .max(Comparator.comparingDouble(distanceFunction::apply))
                // Instruction de code
                .filter(player -> distanceFunction.apply(player) > 0)
                // Appelle une méthode
                .orElse(null);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the {@link Player} provider, to change which object to link to him.
     *
     * @param playerProvider the new {@link PlayerProvider}, can be set to null to apply the default provider
     */
    // Début d'une méthode/d'un bloc
    public void setPlayerProvider(@Nullable PlayerProvider playerProvider) {
        // Accès à l'objet courant/parent
        this.playerProvider = playerProvider != null ? playerProvider : Player::new;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public Player createPlayer(PlayerConnection connection, GameProfile gameProfile) {
        // Appelle une méthode
        assert ServerFlag.INSIDE_TEST || Thread.currentThread().isVirtual();
        // Appelle une méthode
        final Player player = playerProvider.createPlayer(connection, gameProfile);
        // Accès à l'objet courant/parent
        this.connectionPlayerMap.put(connection, player);
        // Renvoie une valeur à l'appelant
        return player;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void sendRegistryTags(Player player) {
        // Appelle une méthode
        player.sendPacket(cachedTagsPacket);
    // Fin d'un bloc/d'une expression
    }

    // This is a somewhat weird implementation where connectionmanager owns the caching of tags.
    // There should be no registry->connectionmanager communication.
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void invalidateTags() {
        // Accès à l'objet courant/parent
        this.cachedTagsPacket.invalidate();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public GameProfile transitionLoginToConfig(PlayerConnection connection, GameProfile gameProfile) {
        // Appelle une méthode
        assert ServerFlag.INSIDE_TEST || Thread.currentThread().isVirtual();
        // Compression
        // Embranchement : vérifie une condition
        if (connection instanceof PlayerSocketConnection socketConnection) {
            // Appelle une méthode
            final int threshold = MinecraftServer.getCompressionThreshold();
            // Embranchement : vérifie une condition
            if (threshold > 0) socketConnection.startCompression();
        // Fin d'un bloc/d'une expression
        }
        // Call pre login event
        // Appelle une méthode
        LoginPluginMessageProcessor pluginMessageProcessor = connection.loginPluginMessageProcessor();
        // Appelle une méthode
        AsyncPlayerPreLoginEvent asyncPlayerPreLoginEvent = new AsyncPlayerPreLoginEvent(connection, gameProfile, pluginMessageProcessor);
        // Appelle une méthode
        EventDispatcher.call(asyncPlayerPreLoginEvent);
        // Embranchement : vérifie une condition
        if (!connection.isOnline()) return gameProfile; // Player has been kicked
        // Change UUID/Username based on the event
        // Appelle une méthode
        gameProfile = asyncPlayerPreLoginEvent.getGameProfile();
        // Wait for pending login plugin messages
        // Gestion des exceptions
        try {
            // Appelle une méthode
            pluginMessageProcessor.awaitReplies(ServerFlag.LOGIN_PLUGIN_MESSAGE_TIMEOUT, TimeUnit.MILLISECONDS);
        // Début d'une méthode/d'un bloc
        } catch (Throwable t) {
            // Appelle une méthode
            connection.kick(LoginListener.INVALID_PROXY_RESPONSE);
            // Lève une exception
            throw new RuntimeException("Error getting replies for login plugin messages", t);
        // Fin d'un bloc/d'une expression
        }
        // Send login success packet (and switch to configuration phase)
        // Appelle une méthode
        connection.sendPacket(new LoginSuccessPacket(gameProfile));
        // Renvoie une valeur à l'appelant
        return gameProfile;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void transitionPlayToConfig(Player player) {
        // Appelle une méthode
        configWaitingPlayers.relaxedOffer(player);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Return value exposed for testing
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void doConfiguration(Player player, boolean isFirstConfig) {
        // Appelle une méthode
        assert ServerFlag.INSIDE_TEST || Thread.currentThread().isVirtual();
        // Embranchement : vérifie une condition
        if (isFirstConfig) {
            // Appelle une méthode
            configurationPlayers.add(player);
            // Appelle une méthode
            keepAlivePlayers.add(player);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        player.sendPacket(PluginMessagePacket.brandPacket(MinecraftServer.getBrandName()));
        // Request known packs immediately, but don't wait for the response until required (sending registry data).
        // Appelle une méthode
        final var knownPacksFuture = player.getPlayerConnection().requestKnownPacks(List.of(SelectKnownPacksPacket.MINECRAFT_CORE));

        // Appelle une méthode
        var event = new AsyncPlayerConfigurationEvent(player, isFirstConfig);
        // Appelle une méthode
        EventDispatcher.call(event);
        // Embranchement : vérifie une condition
        if (!player.isOnline()) return; // Player was kicked during config.

        // send player features that were enabled or disabled during async config event
        // Appelle une méthode
        player.sendPacket(new UpdateEnabledFeaturesPacket(event.getFeatureFlags().stream().map(StaticProtocolObject::name).toList()));

        // Appelle une méthode
        final Instance spawningInstance = event.getSpawningInstance();
        // Appelle une méthode
        Check.notNull(spawningInstance, "You need to specify a spawning instance in the AsyncPlayerConfigurationEvent");

        // Embranchement : vérifie une condition
        if (event.willClearChat()) player.sendPacket(new ResetChatPacket());

        // Registry data (if it should be sent)
        // Embranchement : vérifie une condition
        if (event.willSendRegistryData()) {
            // Instruction de code
            List<SelectKnownPacksPacket.Entry> knownPacks;
            // Gestion des exceptions
            try {
                // Appelle une méthode
                knownPacks = knownPacksFuture.get(ServerFlag.KNOWN_PACKS_RESPONSE_TIMEOUT, TimeUnit.MILLISECONDS);
            // Début d'une méthode/d'un bloc
            } catch (InterruptedException | TimeoutException e) {
                // Appelle une méthode
                LOGGER.warn("Player {} failed to respond to known packs query", player.getUsername());
                // Appelle une méthode
                player.getPlayerConnection().disconnect();
                // Renvoie une valeur à l'appelant
                return;
            // Début d'une méthode/d'un bloc
            } catch (ExecutionException e) {
                // Lève une exception
                throw new RuntimeException("Error receiving known packs", e);
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            boolean excludeVanilla = knownPacks.contains(SelectKnownPacksPacket.MINECRAFT_CORE);

            // Appelle une méthode
            Registries registries = MinecraftServer.process();
            // Appelle une méthode
            player.sendPacket(registries.chatType().registryDataPacket(registries, excludeVanilla));
            // Appelle une méthode
            player.sendPacket(registries.biome().registryDataPacket(registries, excludeVanilla));
            // Appelle une méthode
            player.sendPacket(registries.dialog().registryDataPacket(registries, excludeVanilla));
            // Appelle une méthode
            player.sendPacket(registries.damageType().registryDataPacket(registries, excludeVanilla));
            // Appelle une méthode
            player.sendPacket(registries.trimMaterial().registryDataPacket(registries, excludeVanilla));
            // Appelle une méthode
            player.sendPacket(registries.trimPattern().registryDataPacket(registries, excludeVanilla));
            // Appelle une méthode
            player.sendPacket(registries.bannerPattern().registryDataPacket(registries, excludeVanilla));
            // Appelle une méthode
            player.sendPacket(registries.enchantment().registryDataPacket(registries, excludeVanilla));
            // Appelle une méthode
            player.sendPacket(registries.paintingVariant().registryDataPacket(registries, excludeVanilla));
            // Appelle une méthode
            player.sendPacket(registries.jukeboxSong().registryDataPacket(registries, excludeVanilla));
            // Appelle une méthode
            player.sendPacket(registries.instrument().registryDataPacket(registries, excludeVanilla));
            // Appelle une méthode
            player.sendPacket(registries.wolfVariant().registryDataPacket(registries, excludeVanilla));
            // Appelle une méthode
            player.sendPacket(registries.wolfSoundVariant().registryDataPacket(registries, excludeVanilla));
            // Appelle une méthode
            player.sendPacket(registries.catVariant().registryDataPacket(registries, excludeVanilla));
            // Appelle une méthode
            player.sendPacket(registries.chickenVariant().registryDataPacket(registries, excludeVanilla));
            // Appelle une méthode
            player.sendPacket(registries.cowVariant().registryDataPacket(registries, excludeVanilla));
            // Appelle une méthode
            player.sendPacket(registries.frogVariant().registryDataPacket(registries, excludeVanilla));
            // Appelle une méthode
            player.sendPacket(registries.pigVariant().registryDataPacket(registries, excludeVanilla));
            // Appelle une méthode
            player.sendPacket(registries.zombieNautilusVariant().registryDataPacket(registries, excludeVanilla));
            // Appelle une méthode
            player.sendPacket(registries.timeline().registryDataPacket(registries, excludeVanilla));
            // Appelle une méthode
            player.sendPacket(registries.dimensionType().registryDataPacket(registries, excludeVanilla));
            // MUST BE IN SYNC WITH #createTagsPacket
            // TODO: TEST_ENVIRONMENT, TEST_INSTANCE

            // Appelle une méthode
            sendRegistryTags(player);
        // Fin d'un bloc/d'une expression
        }

        // Wait for pending resource packs if any
        // Appelle une méthode
        var packFuture = player.getResourcePackFuture();
        // Embranchement : vérifie une condition
        if (packFuture != null) packFuture.join();

        // Appelle une méthode
        keepAlivePlayers.remove(player);
        // Appelle une méthode
        player.setPendingOptions(spawningInstance, event.isHardcore());
        // Appelle une méthode
        player.sendPacket(new FinishConfigurationPacket());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void transitionConfigToPlay(Player player) {
        // Accès à l'objet courant/parent
        this.playWaitingPlayers.relaxedOffer(player);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Removes a {@link Player} from the players list.
     * <p>
     * Used during disconnection, you shouldn't have to do it manually.
     *
     * @param connection the player connection
     * @see PlayerConnection#disconnect() to properly disconnect a player
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public synchronized void removePlayer(PlayerConnection connection) {
        // Appelle une méthode
        final Player player = this.connectionPlayerMap.remove(connection);
        // Embranchement : vérifie une condition
        if (player == null) return;
        // Accès à l'objet courant/parent
        this.configurationPlayers.remove(player);
        // Accès à l'objet courant/parent
        this.playPlayers.remove(player);
        // Accès à l'objet courant/parent
        this.keepAlivePlayers.remove(player);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Shutdowns the connection manager by kicking all the currently connected players.
     */
    // Début d'une méthode/d'un bloc
    public synchronized void shutdown() {
        // Boucle : répète un bloc
        for (final PlayerConnection configPlayer : connectionPlayerMap.keySet())
            // Appelle une méthode
            configPlayer.kick(SHUTDOWN_TEXT);
        // Accès à l'objet courant/parent
        this.configurationPlayers.clear();
        // Boucle : répète un bloc
        for (final Player playPlayer : playPlayers)
            // Appelle une méthode
            playPlayer.kick(SHUTDOWN_TEXT);
        // Accès à l'objet courant/parent
        this.playPlayers.clear();

        // Accès à l'objet courant/parent
        this.keepAlivePlayers.clear();
        // Accès à l'objet courant/parent
        this.connectionPlayerMap.clear();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void tick(long tickStart) {
        // Let waiting players into their instances
        // Appelle une méthode
        updateWaitingPlayers();

        // Send keep alive packets
        // Appelle une méthode
        handleKeepAlive(keepAlivePlayers, tickStart);

        // Interpret packets for configuration players
        // Appelle une méthode
        configurationPlayers.forEach(Player::interpretPacketQueue);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Connects waiting players.
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void updateWaitingPlayers() {
        // Accès à l'objet courant/parent
        this.configWaitingPlayers.drain(player -> {
            // In case the method was called multiple times, the player disconnected, etc. just ignore it.
            // Embranchement : vérifie une condition
            if (!playPlayers.remove(player)) return;

            // Appelle une méthode
            configurationPlayers.add(player);
            // Appelle une méthode
            player.remove(false);
            // Appelle une méthode
            player.sendPacket(new StartConfigurationPacket());
        // Fin d'un bloc/d'une expression
        });
        // Accès à l'objet courant/parent
        this.playWaitingPlayers.drain(player -> {
            // Embranchement : vérifie une condition
            if (!player.isOnline()) return; // Player disconnected while in queued to join
            // Appelle une méthode
            configurationPlayers.remove(player);
            // Appelle une méthode
            playPlayers.add(player);
            // Appelle une méthode
            keepAlivePlayers.add(player);

            // This fixes a bug with Geyser. They do not reply to keep alive during config, meaning that
            // `Player#didAnswerKeepAlive()` will always be false when entering the play state, so a new keep
            // alive will never be sent and they will disconnect themselves or we will kick them for not replying.
            // Appelle une méthode
            player.refreshAnswerKeepAlive(true);

            // Spawn the player at Player#getRespawnPoint
            // Appelle une méthode
            CompletableFuture<Void> spawnFuture = player.UNSAFE_init();

            // Required to get the exact moment the player spawns
            // Embranchement : vérifie une condition
            if (ServerFlag.INSIDE_TEST) spawnFuture.join();
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    /**
     * Updates keep alive by checking the last keep alive packet and send a new one if needed.
     *
     * @param tickStart the time of the update in nanoseconds, forwarded to the packet
     */
    // Début d'une méthode/d'un bloc
    private void handleKeepAlive(Collection<Player> playerGroup, long tickStart) {
        // Appelle une méthode
        final KeepAlivePacket keepAlivePacket = new KeepAlivePacket(tickStart);
        // Boucle : répète un bloc
        for (Player player : playerGroup) {
            // Appelle une méthode
            final long lastKeepAlive = tickStart - player.getLastKeepAlive();
            // Embranchement : vérifie une condition
            if (lastKeepAlive > TimeUnit.MILLISECONDS.toNanos(ServerFlag.KEEP_ALIVE_DELAY) && player.didAnswerKeepAlive()) {
                // Appelle une méthode
                player.refreshKeepAlive(tickStart);
                // Appelle une méthode
                player.sendPacket(keepAlivePacket);
            // Embranchement : vérifie une condition
            } else if (lastKeepAlive >= TimeUnit.MILLISECONDS.toNanos(ServerFlag.KEEP_ALIVE_KICK)) {
                // Appelle une méthode
                player.kick(TIMEOUT_TEXT);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private TagsPacket createTagsPacket() {
        // Affecte une valeur
        final List<TagsPacket.Registry> entries = new ArrayList<>();

        // The following are the registries which contain tags used by the vanilla client.
        // We don't care about registries unused by the client.
        // Appelle une méthode
        final Registries registries = MinecraftServer.process();

        // static registries (with tags)
        // Appelle une méthode
        entries.add(registries.blocks().tagRegistry());
        // Appelle une méthode
        entries.add(registries.entityType().tagRegistry());
        // Appelle une méthode
        entries.add(registries.fluid().tagRegistry());
        // Appelle une méthode
        entries.add(registries.gameEvent().tagRegistry());
        // Appelle une méthode
        entries.add(registries.material().tagRegistry());
        // dynamic registries
        // Appelle une méthode
        entries.add(registries.chatType().tagRegistry());
        // Appelle une méthode
        entries.add(registries.biome().tagRegistry());
        // Appelle une méthode
        entries.add(registries.dialog().tagRegistry());
        // Appelle une méthode
        entries.add(registries.damageType().tagRegistry());
        // Appelle une méthode
        entries.add(registries.trimMaterial().tagRegistry());
        // Appelle une méthode
        entries.add(registries.trimPattern().tagRegistry());
        // Appelle une méthode
        entries.add(registries.bannerPattern().tagRegistry());
        // Appelle une méthode
        entries.add(registries.enchantment().tagRegistry());
        // Appelle une méthode
        entries.add(registries.paintingVariant().tagRegistry());
        // Appelle une méthode
        entries.add(registries.jukeboxSong().tagRegistry());
        // Appelle une méthode
        entries.add(registries.instrument().tagRegistry());
        // Appelle une méthode
        entries.add(registries.wolfVariant().tagRegistry());
        // Appelle une méthode
        entries.add(registries.wolfSoundVariant().tagRegistry());
        // Appelle une méthode
        entries.add(registries.catVariant().tagRegistry());
        // Appelle une méthode
        entries.add(registries.chickenVariant().tagRegistry());
        // Appelle une méthode
        entries.add(registries.cowVariant().tagRegistry());
        // Appelle une méthode
        entries.add(registries.frogVariant().tagRegistry());
        // Appelle une méthode
        entries.add(registries.pigVariant().tagRegistry());
        // Appelle une méthode
        entries.add(registries.zombieNautilusVariant().tagRegistry());
        // Appelle une méthode
        entries.add(registries.timeline().tagRegistry());
        // Appelle une méthode
        entries.add(registries.dimensionType().tagRegistry());
        // MUST BE IN SYNC WITH #doConfiguration

        // Renvoie une valeur à l'appelant
        return new TagsPacket(entries);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
