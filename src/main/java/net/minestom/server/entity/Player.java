// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.longs.LongArrayPriorityQueue;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.longs.LongPriorityQueue;
// Import d'une classe nécessaire
import net.kyori.adventure.audience.MessageType;
// Import d'une classe nécessaire
import net.kyori.adventure.bossbar.BossBar;
// Import d'une classe nécessaire
import net.kyori.adventure.dialog.DialogLike;
// Import d'une classe nécessaire
import net.kyori.adventure.identity.Identity;
// Import d'une classe nécessaire
import net.kyori.adventure.inventory.Book;
// Import d'une classe nécessaire
import net.kyori.adventure.pointer.Pointers;
// Import d'une classe nécessaire
import net.kyori.adventure.pointer.PointersSupplier;
// Import d'une classe nécessaire
import net.kyori.adventure.resource.ResourcePackCallback;
// Import d'une classe nécessaire
import net.kyori.adventure.resource.ResourcePackInfo;
// Import d'une classe nécessaire
import net.kyori.adventure.resource.ResourcePackRequest;
// Import d'une classe nécessaire
import net.kyori.adventure.resource.ResourcePackStatus;
// Import d'une classe nécessaire
import net.kyori.adventure.sound.Sound;
// Import d'une classe nécessaire
import net.kyori.adventure.sound.SoundStop;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.HoverEvent;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.HoverEvent.ShowEntity;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.HoverEventSource;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
// Import d'une classe nécessaire
import net.kyori.adventure.title.TitlePart;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.advancements.AdvancementTab;
// Import d'une classe nécessaire
import net.minestom.server.advancements.Notification;
// Import d'une classe nécessaire
import net.minestom.server.adventure.AdventurePacketConvertor;
// Import d'une classe nécessaire
import net.minestom.server.adventure.audience.Audiences;
// Import d'une classe nécessaire
import net.minestom.server.collision.BoundingBox;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.*;
// Import d'une classe nécessaire
import net.minestom.server.dialog.Dialog;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.Attribute;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.LivingEntityMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.avatar.PlayerMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.vehicle.PlayerInputs;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.inventory.InventoryCloseEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.inventory.InventoryOpenEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.item.ItemDropEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.item.PickupExperienceEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.item.PlayerFinishItemUseEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.player.*;
// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.EntityTracker;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.SharedInstance;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.inventory.AbstractInventory;
// Import d'une classe nécessaire
import net.minestom.server.inventory.Inventory;
// Import d'une classe nécessaire
import net.minestom.server.inventory.PlayerInventory;
// Import d'une classe nécessaire
import net.minestom.server.inventory.click.ClickPreprocessor;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.item.component.WrittenBookContent;
// Import d'une classe nécessaire
import net.minestom.server.listener.manager.PacketListenerManager;
// Import d'une classe nécessaire
import net.minestom.server.message.ChatPosition;
// Import d'une classe nécessaire
import net.minestom.server.message.Messenger;
// Import d'une classe nécessaire
import net.minestom.server.monitoring.EventsJFR;
// Import d'une classe nécessaire
import net.minestom.server.network.ConnectionManager;
// Import d'une classe nécessaire
import net.minestom.server.network.ConnectionState;
// Import d'une classe nécessaire
import net.minestom.server.network.PlayerProvider;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.SendablePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.*;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.*;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.data.WorldPos;
// Import d'une classe nécessaire
import net.minestom.server.network.player.ClientSettings;
// Import d'une classe nécessaire
import net.minestom.server.network.player.GameProfile;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerConnection;
// Import d'une classe nécessaire
import net.minestom.server.recipe.RecipeManager;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.scoreboard.BelowNameTag;
// Import d'une classe nécessaire
import net.minestom.server.scoreboard.Team;
// Import d'une classe nécessaire
import net.minestom.server.snapshot.EntitySnapshot;
// Import d'une classe nécessaire
import net.minestom.server.snapshot.PlayerSnapshot;
// Import d'une classe nécessaire
import net.minestom.server.snapshot.SnapshotImpl;
// Import d'une classe nécessaire
import net.minestom.server.snapshot.SnapshotUpdater;
// Import d'une classe nécessaire
import net.minestom.server.statistic.PlayerStatistic;
// Import d'une classe nécessaire
import net.minestom.server.thread.Acquirable;
// Import d'une classe nécessaire
import net.minestom.server.timer.Scheduler;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.PacketSendingUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.async.AsyncUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.chunk.ChunkUpdateLimitChecker;
// Import d'une classe nécessaire
import net.minestom.server.utils.collection.ConcurrentMessageQueues;
// Import d'une classe nécessaire
import net.minestom.server.utils.identity.NamedAndIdentified;
// Import d'une classe nécessaire
import net.minestom.server.utils.inventory.PlayerInventoryUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.time.Cooldown;
// Import d'une classe nécessaire
import net.minestom.server.utils.time.TimeUnit;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import net.minestom.server.world.DimensionType;
// Import d'une classe nécessaire
import net.minestom.server.worldevent.WorldEvent;
// Import d'une classe nécessaire
import org.intellij.lang.annotations.MagicConstant;
// Import d'une classe nécessaire
import org.jctools.queues.MessagePassingQueue;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.nio.charset.StandardCharsets;
// Import d'une classe nécessaire
import java.time.Duration;
// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.CompletableFuture;
// Import d'une classe nécessaire
import java.util.concurrent.CountDownLatch;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;
// Import d'une classe nécessaire
import java.util.concurrent.locks.ReentrantLock;
// Import d'une classe nécessaire
import java.util.function.Consumer;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

/**
 * Those are the major actors of the server
 * <p>
 * You can easily create your own implementation of this and use it with {@link ConnectionManager#setPlayerProvider(PlayerProvider)}.
 */
// Déclaration de type (classe/interface/enum/record)
public class Player extends LivingEntity implements CommandSender, HoverEventSource<ShowEntity>, NamedAndIdentified {
    // Appelle une méthode
    private static final DynamicRegistry<DimensionType> DIMENSION_TYPE_REGISTRY = MinecraftServer.getDimensionTypeRegistry();

    // Appelle une méthode
    private static final Component REMOVE_MESSAGE = Component.text("You have been removed from the server without reason.", NamedTextColor.RED);
    // Appelle une méthode
    private static final Component MISSING_REQUIRED_RESOURCE_PACK = Component.text("Required resource pack was not loaded.", NamedTextColor.RED);

    // Adventure pointer supplier
    // Affecte une valeur
    protected static final PointersSupplier<Player> PLAYER_POINTERS_SUPPLIER = PointersSupplier.<Player>builder()
            // Instruction de code
            .parent(ENTITY_POINTERS_SUPPLIER)
            // Instruction de code
            .resolving(Identity.NAME, Player::getUsername)
            // Instruction de code
            .resolving(Identity.DISPLAY_NAME, Player::getDisplayName)
            // Instruction de code
            .resolving(Identity.LOCALE, Player::getLocale)
            // Appelle une méthode
            .build();

    // This probably should be configurable (eg an instance field). However I(matt) am unclear
    // on what it actually does so am holding off on adding API for this until I understand.
    // Affecte une valeur
    private static final int DEFAULT_SEA_LEVEL = 63;

    // Instruction de code
    private long lastKeepAlive;
    // Instruction de code
    private boolean answerKeepAlive;

    // Instruction de code
    private final GameProfile gameProfile;
    // Instruction de code
    private String username;
    // Instruction de code
    private Component usernameComponent;
    // Instruction de code
    protected final PlayerConnection playerConnection;

    // Instruction de code
    private volatile int latency;
    // Instruction de code
    private Component displayName;
    // Instruction de code
    private PlayerSkin skin;

    // Affecte une valeur
    private Instance pendingInstance = null;
    // Instruction de code
    private int dimensionTypeId;
    // Instruction de code
    private GameMode gameMode;
    // Instruction de code
    private WorldPos deathLocation;

    /**
     * Keeps track of what chunks are sent to the client, this defines the center of the loaded area
     * in the range of {@link ServerFlag#CHUNK_VIEW_DISTANCE}
     */
    // Affecte une valeur
    private Vec chunksLoadedByClient = Vec.ZERO;
    // Appelle une méthode
    private final ReentrantLock chunkQueueLock = new ReentrantLock();
    // Appelle une méthode
    private final LongPriorityQueue chunkQueue = new LongArrayPriorityQueue(this::compareChunkDistance);
    // Affecte une valeur
    private boolean needsChunkPositionSync = true;
    // Affecte une valeur
    private float targetChunksPerTick = 9f; // Always send 9 chunks immediately
    // Affecte une valeur
    private float pendingChunkCount = 0f; // Number of chunks to send on the current tick (ie 0.5 means we cannot send a chunk yet, 1.5 would send a single chunk with a 0.5 remainder)
    // Affecte une valeur
    private int maxChunkBatchLead = 1; // Maximum number of batches to send before waiting for a reply
    // Affecte une valeur
    private int chunkBatchLead = 0; // Number of batches sent without a reply

    // Affecte une valeur
    final ChunkRange.ChunkConsumer chunkAdder = (chunkX, chunkZ) -> {
        // Load new chunks
        // Accès à l'objet courant/parent
        this.instance.loadOptionalChunk(chunkX, chunkZ).thenAccept(this::sendChunk);
    // Fin d'un bloc/d'une expression
    };
    // Affecte une valeur
    final ChunkRange.ChunkConsumer chunkRemover = (chunkX, chunkZ) -> {
        // Unload old chunks
        // Appelle une méthode
        sendPacket(new UnloadChunkPacket(chunkX, chunkZ));
        // Appelle une méthode
        EventDispatcher.call(new PlayerChunkUnloadEvent(this, chunkX, chunkZ));
    // Fin d'un bloc/d'une expression
    };

    // Appelle une méthode
    private final AtomicInteger teleportId = new AtomicInteger();
    // Instruction de code
    private int receivedTeleportId;

    // Appelle une méthode
    private final MessagePassingQueue<ClientPacket> packets = ConcurrentMessageQueues.mpscArrayQueue(ServerFlag.PLAYER_PACKET_QUEUE_SIZE);
    // Instruction de code
    private final boolean levelFlat;
    // Affecte une valeur
    private ClientSettings settings = ClientSettings.DEFAULT;
    // Instruction de code
    private float exp;
    // Instruction de code
    private int level;
    // Affecte une valeur
    private int portalCooldown = 0;

    // Appelle une méthode
    protected ClickPreprocessor clickPreprocessor = new ClickPreprocessor();
    // Instruction de code
    protected PlayerInventory inventory;
    // Instruction de code
    private AbstractInventory openInventory;
    // Used internally to allow the closing of inventory within the inventory listener
    // Instruction de code
    private boolean didCloseInventory;

    // Instruction de code
    private byte heldSlot;

    // Instruction de code
    private Pos respawnPoint;

    // Instruction de code
    private int food;
    // Instruction de code
    private float foodSaturation;

    // Instruction de code
    private long startItemUseTime;
    // Instruction de code
    private long itemUseTime;
    // Instruction de code
    private PlayerHand itemUseHand;

    // Game state (https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Protocol#Game_Event)
    // Instruction de code
    private boolean enableRespawnScreen;
    // Appelle une méthode
    private final ChunkUpdateLimitChecker chunkUpdateLimitChecker = new ChunkUpdateLimitChecker(ServerFlag.PLAYER_CHUNK_UPDATE_LIMITER_HISTORY_SIZE);

    // Experience orb pickup
    // Appelle une méthode
    protected Cooldown experiencePickupCooldown = new Cooldown(Duration.of(10, TimeUnit.SERVER_TICK));

    // Instruction de code
    private BelowNameTag belowNameTag;

    // Instruction de code
    private int permissionLevel;

    // Instruction de code
    private boolean reducedDebugScreenInformation;
    // Instruction de code
    private boolean hardcore;

    // Abilities
    // Instruction de code
    private boolean flying;
    // Instruction de code
    private boolean allowFlying;
    // Instruction de code
    private boolean instantBreak;
    // Affecte une valeur
    private float flyingSpeed = 0.05f;
    // Affecte une valeur
    private float fieldViewModifier = 0.1f;

    // Affecte une valeur
    private final Map<PlayerStatistic, Integer> statisticValueMap = new Hashtable<>();

    // Appelle une méthode
    private final PlayerInputs inputs = new PlayerInputs();

    // Resource packs
    // Déclaration de type (classe/interface/enum/record)
    record PendingResourcePack(boolean required, ResourcePackCallback callback) {
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    private final Map<UUID, PendingResourcePack> pendingResourcePacks = new HashMap<>();
    // The future is non-null when a resource pack is in-flight, and completed when all statuses have been received.
    // Affecte une valeur
    private CompletableFuture<Void> resourcePackFuture = null;

    // Début d'une méthode/d'un bloc
    public Player(PlayerConnection playerConnection, GameProfile gameProfile) {
        // Accès à l'objet courant/parent
        super(EntityType.PLAYER, gameProfile.uuid());
        // Accès à l'objet courant/parent
        this.gameProfile = gameProfile;
        // Accès à l'objet courant/parent
        this.username = gameProfile.name();
        // Accès à l'objet courant/parent
        this.usernameComponent = Component.text(username);
        // Accès à l'objet courant/parent
        this.playerConnection = playerConnection;

        // Appelle une méthode
        setRespawnPoint(Pos.ZERO);

        // Accès à l'objet courant/parent
        this.inventory = new PlayerInventory();

        // Instruction de code
        setCanPickupItem(true); // By default

        // Allow the server to send the next keep alive packet
        // Appelle une méthode
        refreshAnswerKeepAlive(true);

        // Accès à l'objet courant/parent
        this.gameMode = GameMode.SURVIVAL;
        // Accès à l'objet courant/parent
        this.dimensionTypeId = DIMENSION_TYPE_REGISTRY.getId(DimensionType.OVERWORLD); // Default dimension
        // Accès à l'objet courant/parent
        this.levelFlat = true;
        // Appelle une méthode
        getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0.1);

        // FakePlayer init its connection there
        // Appelle une méthode
        playerConnectionInit();

        // When in configuration state no metadata updates can be sent.
        // Appelle une méthode
        metadata.setNotifyAboutChanges(false);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void setPendingOptions(Instance pendingInstance, boolean hardcore) {
        // I(mattw) am not a big fan of this function, but somehow we need to store
        // the instance and i didn't like a record in ConnectionManager either.
        // Accès à l'objet courant/parent
        this.pendingInstance = pendingInstance;
        // Accès à l'objet courant/parent
        this.hardcore = hardcore;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Used when the player is created.
     * Init the player and spawn him.
     * <p>
     * WARNING: executed in the main update thread
     * UNSAFE: Only meant to be used when a socket player connects through the server.
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public CompletableFuture<Void> UNSAFE_init() {
        // Affecte une valeur
        final Instance spawnInstance = this.pendingInstance;
        // Accès à l'objet courant/parent
        this.pendingInstance = null;

        // Accès à l'objet courant/parent
        this.removed = false;
        // Accès à l'objet courant/parent
        this.dimensionTypeId = DIMENSION_TYPE_REGISTRY.getId(spawnInstance.getDimensionType());

        // Affecte une valeur
        final JoinGamePacket joinGamePacket = new JoinGamePacket(
                // Instruction de code
                getEntityId(), this.hardcore, List.of(), 0,
                // Instruction de code
                ServerFlag.CHUNK_VIEW_DISTANCE, ServerFlag.CHUNK_VIEW_DISTANCE,
                // Instruction de code
                false, true, false,
                // Instruction de code
                dimensionTypeId, spawnInstance.getDimensionName(), 0,
                // Instruction de code
                gameMode, null, false, levelFlat,
                // Instruction de code
                deathLocation, portalCooldown, DEFAULT_SEA_LEVEL,
                // Instruction de code
                true);
        // Appelle une méthode
        sendPacket(joinGamePacket);

        // Start sending inventory updates
        // Appelle une méthode
        inventory.addViewer(this);

        // Difficulty
        // Appelle une méthode
        sendPacket(new ServerDifficultyPacket(MinecraftServer.getDifficulty(), true));

        // Instruction de code
        sendPacket(new SpawnPositionPacket(
                // Crée un nouvel objet
                new WorldPos(spawnInstance.getDimensionName(), respawnPoint),
                // Instruction de code
                respawnPoint.yaw(), respawnPoint.pitch()
        // Instruction de code
        ));

        // Reenable metadata notifications as we leave the configuration state
        // Appelle une méthode
        metadata.setNotifyAboutChanges(true);
        // Appelle une méthode
        sendPacket(getMetadataPacket());

        // Add player to list with spawning skin
        // Affecte une valeur
        PlayerSkin profileSkin = null;
        // Boucle : répète un bloc
        for (GameProfile.Property property : gameProfile.properties()) {
            // Embranchement : vérifie une condition
            if (property.name().equals("textures")) {
                // Appelle une méthode
                profileSkin = new PlayerSkin(property.value(), property.signature());
                // Interrompt la boucle/le bloc
                break;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        PlayerSkinInitEvent skinInitEvent = new PlayerSkinInitEvent(this, profileSkin);
        // Appelle une méthode
        EventDispatcher.call(skinInitEvent);
        // Accès à l'objet courant/parent
        this.skin = skinInitEvent.getSkin();
        // FIXME: when using Geyser, this line remove the skin of the client
        // Appelle une méthode
        PacketSendingUtils.broadcastPlayPacket(getAddPlayerToList());

        // Appelle une méthode
        var connectionManager = MinecraftServer.getConnectionManager();
        // Boucle : répète un bloc
        for (var player : connectionManager.getOnlinePlayers()) {
            // Embranchement : vérifie une condition
            if (player != this) {
                // Appelle une méthode
                sendPacket(player.getAddPlayerToList());
                // Embranchement : vérifie une condition
                if (player.displayName != null) {
                    // Appelle une méthode
                    sendPacket(new PlayerInfoUpdatePacket(PlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME, player.infoEntry()));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        //Teams
        // Boucle : répète un bloc
        for (Team team : MinecraftServer.getTeamManager().getTeams()) {
            // Appelle une méthode
            sendPacket(team.createTeamsCreationPacket());
        // Fin d'un bloc/d'une expression
        }

        // Commands
        // Appelle une méthode
        refreshCommands();

        // Recipes
        // Appelle une méthode
        refreshRecipes();

        // Some client updates
        // Instruction de code
        sendPacket(getPropertiesPacket()); // Send default properties
        // Instruction de code
        triggerStatus((byte) (EntityStatuses.Player.PERMISSION_LEVEL_0 + permissionLevel)); // Set permission level
        // Instruction de code
        refreshHealth(); // Heal and send health packet
        // Instruction de code
        refreshAbilities(); // Send abilities packet

        // Renvoie une valeur à l'appelant
        return setInstance(spawnInstance);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Moves the player to the configuration state at the end of the current tick.
     *
     * <p>The player is automatically moved to configuration upon finishing login, this method can be
     * used to move them back to configuration after entering the play state.</p>
     *
     * <p>This will result in them being removed from the current instance, player list, etc.</p>
     */
    // Début d'une méthode/d'un bloc
    public void startConfigurationPhase() {
        // Instruction de code
        Check.stateCondition(playerConnection.getServerState() != ConnectionState.PLAY,
                // Instruction de code
                "Player must be in the play state for reconfiguration.");

        // Appelle une méthode
        MinecraftServer.getConnectionManager().transitionPlayToConfig(this);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Used to initialize the player connection
     */
    // Début d'une méthode/d'un bloc
    protected void playerConnectionInit() {
        // Affecte une valeur
        PlayerConnection connection = playerConnection;
        // Embranchement : vérifie une condition
        if (connection != null) connection.setPlayer(this);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void update(long time) {
        // Process received packets
        // Appelle une méthode
        interpretPacketQueue();
        // It is possible to be removed during packet processing, if thats the case exit immediately.
        // Embranchement : vérifie une condition
        if (isRemoved()) return;

        // Send any available queued chunks
        // Appelle une méthode
        sendPendingChunks();

        // Accès à l'objet courant/parent
        super.update(time); // Super update (item pickup/fire management)

        // Experience orb pickup
        // Embranchement : vérifie une condition
        if (experiencePickupCooldown.isReady(time)) {
            // Appelle une méthode
            experiencePickupCooldown.refreshLastUpdate(time);
            // Accès à l'objet courant/parent
            this.instance.getEntityTracker().nearbyEntities(position, expandedBoundingBox.width(),
                    // Début d'une méthode/d'un bloc
                    EntityTracker.Target.EXPERIENCE_ORBS, experienceOrb -> {
                        // Embranchement : vérifie une condition
                        if (!expandedBoundingBox.intersectEntity(position, experienceOrb)) return;
                        // Appelle une méthode
                        final PickupExperienceEvent pickupExperienceEvent = new PickupExperienceEvent(this, experienceOrb);
                        // Début d'une méthode/d'un bloc
                        EventDispatcher.callCancellable(pickupExperienceEvent, () -> {
                            // Affecte une valeur
                            short experienceCount = pickupExperienceEvent.getExperienceCount(); // TODO give to player
                            // Appelle une méthode
                            experienceOrb.remove();
                        // Fin d'un bloc/d'une expression
                        });
                    // Fin d'un bloc/d'une expression
                    });
        // Fin d'un bloc/d'une expression
        }

        // Eating animation
        // Embranchement : vérifie une condition
        if (isUsingItem()) {
            // Affecte une valeur
            final PlayerHand itemUseHand = this.itemUseHand;
            // Embranchement : vérifie une condition
            if (itemUseTime > 0 && getCurrentItemUseTime() >= itemUseTime) {
                // Appelle une méthode
                final ItemStack itemStack = getItemInHand(itemUseHand);
                // Appelle une méthode
                PlayerFinishItemUseEvent finishUseEvent = new PlayerFinishItemUseEvent(this, itemUseHand, itemStack, itemUseTime);
                // Appelle une méthode
                EventDispatcher.call(finishUseEvent);

                // Reset client state
                // Appelle une méthode
                triggerStatus((byte) EntityStatuses.Player.MARK_ITEM_FINISHED);

                // Reset server state
                // Instruction de code
                final boolean isOffHand = itemUseHand == PlayerHand.OFF;
                // Appelle une méthode
                refreshActiveHand(false, isOffHand, finishUseEvent.isRiptideSpinAttack());
                // Appelle une méthode
                clearItemUse();

                // The client has predicted that the itemstack will have its count reduced, if the server
                // has not changed the item (the default behavior) we need to refresh the slot.
                // Embranchement : vérifie une condition
                if (itemStack.equals(getItemInHand(itemUseHand))) {
                    // Appelle une méthode
                    final int slot = isOffHand ? PlayerInventoryUtils.OFFHAND_SLOT : getHeldSlot();
                    // Appelle une méthode
                    inventory.sendSlotRefresh(slot, itemStack);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        updatePose();

        // Tick event
        // Appelle une méthode
        EventDispatcher.call(new PlayerTickEvent(this));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void kill() {
        // Embranchement : vérifie une condition
        if (!isDead()) {

            // Instruction de code
            Component deathText;
            // Instruction de code
            Component chatMessage;

            // get death screen text to the killed player
            // Début d'un bloc
            {
                // Embranchement : vérifie une condition
                if (lastDamage != null) {
                    // Appelle une méthode
                    deathText = lastDamage.buildDeathScreenText(this);
                // Branche alternative de la condition
                } else { // may happen if killed by the server without applying damage
                    // Appelle une méthode
                    deathText = Component.text("Killed by poor programming.");
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // get death message to chat
            // Début d'un bloc
            {
                // Embranchement : vérifie une condition
                if (lastDamage != null) {
                    // Appelle une méthode
                    chatMessage = lastDamage.buildDeathMessage(this);
                // Branche alternative de la condition
                } else { // may happen if killed by the server without applying damage
                    // Appelle une méthode
                    chatMessage = Component.text(getUsername() + " was killed by poor programming.");
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // Call player death event
            // Appelle une méthode
            PlayerDeathEvent playerDeathEvent = new PlayerDeathEvent(this, deathText, chatMessage);
            // Appelle une méthode
            EventDispatcher.call(playerDeathEvent);

            // Appelle une méthode
            deathText = playerDeathEvent.getDeathText();
            // Appelle une méthode
            chatMessage = playerDeathEvent.getChatMessage();

            // #buildDeathScreenText can return null, check here
            // Embranchement : vérifie une condition
            if (deathText != null) {
                // Appelle une méthode
                sendPacket(new DeathCombatEventPacket(getEntityId(), deathText));
            // Fin d'un bloc/d'une expression
            }

            // #buildDeathMessage can return null, check here
            // Embranchement : vérifie une condition
            if (chatMessage != null) {
                // Appelle une méthode
                Audiences.players().sendMessage(chatMessage);
            // Fin d'un bloc/d'une expression
            }

            // Set death location
            // Embranchement : vérifie une condition
            if (getInstance() != null)
                // Appelle une méthode
                setDeathLocation(getInstance().getDimensionName(), getPosition());
        // Fin d'un bloc/d'une expression
        }
        // Accès à l'objet courant/parent
        super.kill();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Respawns the player by sending a {@link RespawnPacket} to the player and teleporting him
     * to {@link #getRespawnPoint()}. It also resets fire and health.
     */
    // Début d'une méthode/d'un bloc
    public void respawn() {
        // Embranchement : vérifie une condition
        if (!isDead())
            // Renvoie une valeur à l'appelant
            return;

        // Appelle une méthode
        setFireTicks(0);
        // Appelle une méthode
        entityMeta.setOnFire(false);
        // Appelle une méthode
        refreshHealth();

        // Instruction de code
        sendPacket(new RespawnPacket(dimensionTypeId, instance.getDimensionName(),
                // Instruction de code
                0, gameMode, gameMode, false, levelFlat,
                // Instruction de code
                deathLocation, portalCooldown, DEFAULT_SEA_LEVEL, (byte) RespawnPacket.COPY_ALL));
        // Appelle une méthode
        refreshClientStateAfterRespawn();

        // Appelle une méthode
        PlayerRespawnEvent respawnEvent = new PlayerRespawnEvent(this);
        // Appelle une méthode
        EventDispatcher.call(respawnEvent);
        // Appelle une méthode
        refreshIsDead(false);
        // Appelle une méthode
        updatePose();

        // Appelle une méthode
        Pos respawnPosition = respawnEvent.getRespawnPosition();

        // The client unloads chunks when respawning, so resend all chunks next to spawn
        // Appelle une méthode
        ChunkRange.chunksInRange(respawnPosition, this.effectiveViewDistance(), chunkAdder);
        // Appelle une méthode
        chunksLoadedByClient = new Vec(respawnPosition.chunkX(), respawnPosition.chunkZ());
        // Client also needs all entities resent to them, since those are unloaded as well
        // Accès à l'objet courant/parent
        this.instance.getEntityTracker().nearbyEntitiesByChunkRange(respawnPosition, this.effectiveViewDistance(),
                // Début d'une méthode/d'un bloc
                EntityTracker.Target.ENTITIES, entity -> {
                    // Skip refreshing self with a new viewer
                    // Embranchement : vérifie une condition
                    if (!entity.getUuid().equals(getUuid()) && entity.isViewer(this)) {
                        // Appelle une méthode
                        entity.updateNewViewer(this);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                });
        // Appelle une méthode
        teleport(respawnPosition).thenRun(this::refreshAfterTeleport);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sends necessary packets to synchronize player data after a {@link RespawnPacket}
     */
    // Début d'une méthode/d'un bloc
    private void refreshClientStateAfterRespawn() {
        // Appelle une méthode
        sendPacket(new ChangeGameStatePacket(ChangeGameStatePacket.Reason.LEVEL_CHUNKS_LOAD_START, 0));
        // Appelle une méthode
        sendPacket(new ServerDifficultyPacket(MinecraftServer.getDifficulty(), false));
        // Appelle une méthode
        sendPacket(new UpdateHealthPacket(this.getHealth(), food, foodSaturation));
        // Appelle une méthode
        sendPacket(new SetExperiencePacket(exp, level, 0));
        // Instruction de code
        triggerStatus((byte) (EntityStatuses.Player.PERMISSION_LEVEL_0 + permissionLevel)); // Set permission level
        // Appelle une méthode
        refreshAbilities();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Refreshes the command list for this player. This checks the
     * {@link net.minestom.server.command.builder.condition.CommandCondition}s
     * again, and any changes will be visible to the player.
     */
    // Début d'une méthode/d'un bloc
    public void refreshCommands() {
        // Appelle une méthode
        sendPacket(MinecraftServer.getCommandManager().createDeclareCommandsPacket(this));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Refreshes the recipes and recipe book for this player, testing recipe predicates again.
     */
    // Début d'une méthode/d'un bloc
    public void refreshRecipes() {
        // Appelle une méthode
        RecipeManager recipeManager = MinecraftServer.getRecipeManager();
        // Instruction de code
        sendPackets(
                // Instruction de code
                recipeManager.getDeclareRecipesPacket(),
                // Instruction de code
                recipeManager.createRecipeBookResetPacket(this)
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isOnGround() {
        // Renvoie une valeur à l'appelant
        return onGround;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void remove(boolean permanent) {
        // Embranchement : vérifie une condition
        if (isRemoved()) return;

        // Embranchement : vérifie une condition
        if (permanent) {
            // Accès à l'objet courant/parent
            this.packets.clear();
            // Appelle une méthode
            EventDispatcher.call(new PlayerDisconnectEvent(this));
            // Appelle une méthode
            EventsJFR.newPlayerLeave(getUuid()).commit();
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final AbstractInventory currentInventory = getOpenInventory();
        // Embranchement : vérifie une condition
        if (currentInventory != null) currentInventory.removeViewer(this);

        // Appelle une méthode
        MinecraftServer.getBossBarManager().removeAllBossBars(this);
        // Advancement tabs cache
        // Début d'un bloc
        {
            // Appelle une méthode
            Set<AdvancementTab> advancementTabs = AdvancementTab.getTabs(this);
            // Embranchement : vérifie une condition
            if (advancementTabs != null) {
                // Boucle : répète un bloc
                for (AdvancementTab advancementTab : advancementTabs) {
                    // Appelle une méthode
                    advancementTab.removeViewer(this);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        final Pos position = this.position;
        // Appelle une méthode
        final int chunkX = position.chunkX();
        // Appelle une méthode
        final int chunkZ = position.chunkZ();
        // Clear all viewable chunks
        // Appelle une méthode
        ChunkRange.chunksInRange(chunkX, chunkZ, this.effectiveViewDistance(), chunkRemover);
        // Appelle une méthode
        resetChunkQueue();

        // Remove from the tab-list
        // Appelle une méthode
        PacketSendingUtils.broadcastPlayPacket(getRemovePlayerToList());

        // Accès à l'objet courant/parent
        super.remove(permanent);
        // Prevent the player from being stuck in loading screen, or just unable to interact with the server
        // This should be considered as a bug, since the player will ultimately time out anyway.
        // Embranchement : vérifie une condition
        if (permanent && playerConnection.isOnline()) kick(REMOVE_MESSAGE);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void sendPacketToViewersAndSelf(SendablePacket packet) {
        // Appelle une méthode
        sendPacket(packet);
        // Accès à l'objet courant/parent
        super.sendPacketToViewersAndSelf(packet);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the player instance and load surrounding chunks if needed.
     * <p>
     * Be aware that because chunk operations are expensive,
     * it is possible for this method to be non-blocking when retrieving chunks is required.
     *
     * @param instance      the new player instance
     * @param spawnPosition the new position of the player
     * @return a future called once the player instance changed
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public CompletableFuture<Void> setInstance(Instance instance, Pos spawnPosition) {
        // Affecte une valeur
        final Instance currentInstance = this.instance;
        // Appelle une méthode
        Check.argCondition(currentInstance == instance, "Instance should be different than the current one");
        // Embranchement : vérifie une condition
        if (SharedInstance.areLinked(currentInstance, instance) && spawnPosition.sameChunk(this.position)) {
            // The player already has the good version of all the chunks.
            // We just need to refresh his entity viewing list and add him to the instance
            // Appelle une méthode
            spawnPlayer(instance, spawnPosition, false, false, false);
            // Renvoie une valeur à l'appelant
            return AsyncUtils.VOID_FUTURE;
        // Fin d'un bloc/d'une expression
        }
        // Must update the player chunks
        // Appelle une méthode
        chunkUpdateLimitChecker.clearHistory();
        // Appelle une méthode
        final boolean dimensionChange = currentInstance != null && !Objects.equals(currentInstance.getDimensionName(), instance.getDimensionName());
        // Affecte une valeur
        final Consumer<Instance> runnable = (i) -> spawnPlayer(i, spawnPosition,
                // Instruction de code
                currentInstance == null, dimensionChange, true);

        // Appelle une méthode
        resetChunkQueue();

        // Ensure that surrounding chunks are loaded
        // Affecte une valeur
        List<CompletableFuture<Chunk>> futures = new ArrayList<>();
        // Début d'une méthode/d'un bloc
        ChunkRange.chunksInRange(spawnPosition, this.effectiveViewDistance(), (chunkX, chunkZ) -> {
            // Appelle une méthode
            final CompletableFuture<Chunk> future = instance.loadOptionalChunk(chunkX, chunkZ);
            // Embranchement : vérifie une condition
            if (!future.isDone()) futures.add(future);
        // Fin d'un bloc/d'une expression
        });
        // Embranchement : vérifie une condition
        if (futures.isEmpty()) {
            // All chunks are already loaded
            // Appelle une méthode
            runnable.accept(instance);
            // Renvoie une valeur à l'appelant
            return AsyncUtils.VOID_FUTURE;
        // Fin d'un bloc/d'une expression
        }

        // One or more chunks need to be loaded
        // Appelle une méthode
        final Thread runThread = Thread.currentThread();
        // Appelle une méthode
        CountDownLatch latch = new CountDownLatch(1);
        // Appelle une méthode
        Scheduler scheduler = MinecraftServer.getSchedulerManager();
        // Affecte une valeur
        CompletableFuture<Void> future = new CompletableFuture<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Void join() {
                // Prevent deadlock
                // Embranchement : vérifie une condition
                if (runThread == Thread.currentThread()) {
                    // Gestion des exceptions
                    try {
                        // Appelle une méthode
                        latch.await();
                    // Début d'une méthode/d'un bloc
                    } catch (InterruptedException e) {
                        // Lève une exception
                        throw new RuntimeException(e);
                    // Fin d'un bloc/d'une expression
                    }
                    // Appelle une méthode
                    scheduler.process();
                    // Appelle une méthode
                    assert isDone();
                // Fin d'un bloc/d'une expression
                }
                // Renvoie une valeur à l'appelant
                return super.join();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Instruction de code
        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new))
                // Début d'une méthode/d'un bloc
                .thenRun(() -> {
                    // Début d'une méthode/d'un bloc
                    scheduler.scheduleNextProcess(() -> {
                        // Appelle une méthode
                        runnable.accept(instance);
                        // Appelle une méthode
                        future.complete(null);
                    // Fin d'un bloc/d'une expression
                    });
                    // Appelle une méthode
                    latch.countDown();
                // Fin d'un bloc/d'une expression
                });
        // Renvoie une valeur à l'appelant
        return future;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the player instance without changing its position (defaulted to {@link #getRespawnPoint()}
     * if the player is not in any instance).
     *
     * @param instance the new player instance
     * @return a {@link CompletableFuture} called once the entity's instance has been set,
     * this is due to chunks needing to load for players
     * @see #setInstance(Instance, Pos)
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public CompletableFuture<Void> setInstance(Instance instance) {
        // Renvoie une valeur à l'appelant
        return setInstance(instance, this.instance != null ? getPosition() : getRespawnPoint());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Used to spawn the player once the client has all the required chunks.
     * <p>
     * Does add the player to {@code instance}, remove all viewable entities and call {@link PlayerSpawnEvent}.
     * <p>
     * UNSAFE: only called with {@link #setInstance(Instance, Pos)}.
     *
     * @param spawnPosition the position to teleport the player
     * @param firstSpawn    true if this is the player first spawn
     * @param updateChunks  true if chunks should be refreshed, false if the new instance shares the same
     *                      chunks
     */
    // Instruction de code
    private void spawnPlayer(Instance instance, Pos spawnPosition,
                             // Début d'une méthode/d'un bloc
                             boolean firstSpawn, boolean dimensionChange, boolean updateChunks) {
        // Embranchement : vérifie une condition
        if (!firstSpawn && !dimensionChange) {
            // Player instance changed, clear current viewable collections
            // Embranchement : vérifie une condition
            if (updateChunks)
                // Appelle une méthode
                ChunkRange.chunksInRange(spawnPosition, this.effectiveViewDistance(), chunkRemover);
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (dimensionChange) sendDimension(instance.getDimensionType(), instance.getDimensionName());

        // Accès à l'objet courant/parent
        super.setInstance(instance, spawnPosition);

        // Embranchement : vérifie une condition
        if (updateChunks) {
            // Appelle une méthode
            final int chunkX = spawnPosition.chunkX();
            // Appelle une méthode
            final int chunkZ = spawnPosition.chunkZ();
            // Appelle une méthode
            chunksLoadedByClient = new Vec(chunkX, chunkZ);
            // Appelle une méthode
            chunkUpdateLimitChecker.addToHistory(getChunk());
            // Appelle une méthode
            sendPacket(new UpdateViewPositionPacket(chunkX, chunkZ));

            // Load the nearby chunks and queue them to be sent to them
            // Appelle une méthode
            ChunkRange.chunksInRange(spawnPosition, this.effectiveViewDistance(), chunkAdder);
            // Instruction de code
            sendPendingChunks(); // Send available first chunk immediately to prevent falling through the floor
        // Fin d'un bloc/d'une expression
        }

        // Instruction de code
        synchronizePositionAfterTeleport(spawnPosition, Vec.ZERO, RelativeFlags.NONE, true); // So the player doesn't get stuck

        // Embranchement : vérifie une condition
        if (dimensionChange) {
            // Instruction de code
            sendPacket(new SpawnPositionPacket(
                    // Crée un nouvel objet
                    new WorldPos(instance.getDimensionName(), spawnPosition),
                    // Instruction de code
                    spawnPosition.yaw(), spawnPosition.pitch()
            // Instruction de code
            ));
            // Appelle une méthode
            sendPacket(instance.createInitializeWorldBorderPacket());
            // Appelle une méthode
            sendPacket(instance.createTimePacket());
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (dimensionChange || firstSpawn) {
            // Accès à l'objet courant/parent
            this.inventory.update();
            // Appelle une méthode
            sendPacket(new HeldItemChangePacket(heldSlot));

            // Tell the client to leave the loading terrain screen
            // Appelle une méthode
            sendPacket(new ChangeGameStatePacket(ChangeGameStatePacket.Reason.LEVEL_CHUNKS_LOAD_START, 0));
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        EventDispatcher.call(new PlayerSpawnEvent(this, instance, firstSpawn));
        // Embranchement : vérifie une condition
        if (firstSpawn) EventsJFR.newPlayerJoin(getUuid()).commit();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void onChunkBatchReceived(float newTargetChunksPerTick) {
//        logger.debug("chunk batch received player={} chunks/tick={} lead={}", username, newTargetChunksPerTick, chunkBatchLead);
        // Affecte une valeur
        chunkBatchLead -= 1;
        // Affecte une valeur
        targetChunksPerTick = Float.isNaN(newTargetChunksPerTick) ? ServerFlag.MIN_CHUNKS_PER_TICK : MathUtils.clamp(
                // Instruction de code
                newTargetChunksPerTick * ServerFlag.CHUNKS_PER_TICK_MULTIPLIER, ServerFlag.MIN_CHUNKS_PER_TICK, ServerFlag.MAX_CHUNKS_PER_TICK);

        // Beyond the first batch we can preemptively send up to 10 (matching mojang server)
        // Embranchement : vérifie une condition
        if (maxChunkBatchLead == 1) maxChunkBatchLead = 10;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Queues the given chunk to be sent to the player.
     *
     * @param chunk The chunk to send
     */
    // Début d'une méthode/d'un bloc
    public void sendChunk(Chunk chunk) {
        // Embranchement : vérifie une condition
        if (!chunk.isLoaded()) return;
        // Appelle une méthode
        chunkQueueLock.lock();
        // Gestion des exceptions
        try {
            // Appelle une méthode
            chunkQueue.enqueue(CoordConversion.chunkIndex(chunk.getChunkX(), chunk.getChunkZ()));
        // Début d'une méthode/d'un bloc
        } finally {
            // Appelle une méthode
            chunkQueueLock.unlock();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void sendPendingChunks() {
        // If we have nothing to send or have sent the max # of batches without reply, do nothing
        // Embranchement : vérifie une condition
        if (chunkQueue.isEmpty() || chunkBatchLead >= maxChunkBatchLead) return;

        // Increment the pending chunk count by the target chunks per tick
        // Appelle une méthode
        pendingChunkCount = Math.min(pendingChunkCount + targetChunksPerTick, ServerFlag.MAX_CHUNKS_PER_TICK);
        // Embranchement : vérifie une condition
        if (pendingChunkCount < 1) return; // Cant send anything

        // Appelle une méthode
        chunkQueueLock.lock();
        // Gestion des exceptions
        try {
            // Affecte une valeur
            int batchSize = 0;
            // Appelle une méthode
            sendPacket(new ChunkBatchStartPacket());
            // Boucle : répète un bloc
            while (!chunkQueue.isEmpty() && pendingChunkCount >= 1f) {
                // Appelle une méthode
                long chunkIndex = chunkQueue.dequeueLong();
                // Appelle une méthode
                int chunkX = CoordConversion.chunkIndexGetX(chunkIndex), chunkZ = CoordConversion.chunkIndexGetZ(chunkIndex);
                // Appelle une méthode
                var chunk = instance.getChunk(chunkX, chunkZ);
                // Embranchement : vérifie une condition
                if (chunk == null || !chunk.isLoaded()) continue;

                // Appelle une méthode
                sendPacket(chunk.getFullDataPacket());
                // Appelle une méthode
                EventDispatcher.call(new PlayerChunkLoadEvent(this, chunkX, chunkZ));

                // Affecte une valeur
                pendingChunkCount -= 1f;
                // Affecte une valeur
                batchSize += 1;
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            sendPacket(new ChunkBatchFinishedPacket(batchSize));
            // Affecte une valeur
            chunkBatchLead += 1;
//            logger.debug("chunk batch sent player={} chunks={} lead={}", username, batchSize, chunkBatchLead);

            // After sending the first chunk we always send a synchronize position to the client. This is to prevent
            // cases where the client falls through the floor slightly while loading the first chunk.
            // In the vanilla server they have an anticheat which teleports the client back if they enter the floor,
            // but since Minestom does not have an anticheat this provides a similar effect.
            // Embranchement : vérifie une condition
            if (needsChunkPositionSync) {
                // Appelle une méthode
                synchronizePositionAfterTeleport(getPosition(), Vec.ZERO, RelativeFlags.NONE, true);
                // Affecte une valeur
                needsChunkPositionSync = false;
            // Fin d'un bloc/d'une expression
            }
        // Début d'une méthode/d'un bloc
        } finally {
            // Appelle une méthode
            chunkQueueLock.unlock();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void resetChunkQueue() {
        // Appelle une méthode
        chunkQueueLock.lock();
        // Gestion des exceptions
        try {
            // Appelle une méthode
            chunkQueue.clear();
            // Affecte une valeur
            needsChunkPositionSync = true;
            // Affecte une valeur
            targetChunksPerTick = 9f;
            // Affecte une valeur
            pendingChunkCount = 0f;
        // Début d'une méthode/d'un bloc
        } finally {
            // Appelle une méthode
            chunkQueueLock.unlock();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected void updatePose() {
        // Appelle une méthode
        EntityPose oldPose = getPose();
        // Instruction de code
        EntityPose newPose;

        // Figure out their expected state
        // Appelle une méthode
        var meta = getEntityMeta();
        // Embranchement : vérifie une condition
        if (meta.isFlyingWithElytra()) {
            // Affecte une valeur
            newPose = EntityPose.FALL_FLYING;
        // Embranchement : vérifie une condition
        } else if (meta instanceof LivingEntityMeta livingMeta && livingMeta.getBedInWhichSleepingPosition() != null) {
            // Affecte une valeur
            newPose = EntityPose.SLEEPING;
        // Embranchement : vérifie une condition
        } else if (meta.isSwimming()) {
            // Affecte une valeur
            newPose = EntityPose.SWIMMING;
        // Embranchement : vérifie une condition
        } else if (meta instanceof LivingEntityMeta livingMeta && livingMeta.isInRiptideSpinAttack()) {
            // Affecte une valeur
            newPose = EntityPose.SPIN_ATTACK;
        // Embranchement : vérifie une condition
        } else if (isSneaking() && !isFlying()) {
            // Affecte une valeur
            newPose = EntityPose.SNEAKING;
        // Branche alternative de la condition
        } else {
            // Affecte une valeur
            newPose = EntityPose.STANDING;
        // Fin d'un bloc/d'une expression
        }

        // Try to put them in their expected state, or the closest if they don't fit.
        // Embranchement : vérifie une condition
        if (canFitWithBoundingBox(newPose)) {
            // Use expected state
        // Embranchement : vérifie une condition
        } else if (canFitWithBoundingBox(EntityPose.SNEAKING)) {
            // Affecte une valeur
            newPose = EntityPose.SNEAKING;
        // Embranchement : vérifie une condition
        } else if (canFitWithBoundingBox(EntityPose.SWIMMING)) {
            // Affecte une valeur
            newPose = EntityPose.SWIMMING;
        // Branche alternative de la condition
        } else {
            // If they can't fit anywhere, just use standing
            // Affecte une valeur
            newPose = EntityPose.STANDING;
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (newPose != oldPose) setPose(newPose);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns true if the player can fit at the current position with the given {@link EntityPose}, false otherwise.
     *
     * @param pose The pose to check
     */
    // Début d'une méthode/d'un bloc
    private boolean canFitWithBoundingBox(EntityPose pose) {
        // Appelle une méthode
        BoundingBox bb = pose == EntityPose.STANDING ? boundingBox : BoundingBox.fromPose(pose);
        // Embranchement : vérifie une condition
        if (bb == null) return false;

        // Appelle une méthode
        var position = getPosition();
        // Appelle une méthode
        var iter = bb.getBlocks(getPosition());
        // Boucle : répète un bloc
        while (iter.hasNext()) {
            // Appelle une méthode
            var pos = iter.next();
            // Instruction de code
            Block block;
            // Gestion des exceptions
            try {
                // Appelle une méthode
                block = instance.getBlock(pos.blockX(), pos.blockY(), pos.blockZ(), Block.Getter.Condition.TYPE);
            // Début d'une méthode/d'un bloc
            } catch (NullPointerException ignored) {
                // Affecte une valeur
                block = null;
            // Fin d'un bloc/d'une expression
            }

            // Block was in unloaded chunk, no bounding box.
            // Embranchement : vérifie une condition
            if (block == null) continue;

            // For now just ignore scaffolding. It seems to have a dynamic bounding box, or is just parsed
            // incorrectly in MinestomDataGenerator.
            // Embranchement : vérifie une condition
            if (block.id() == Block.SCAFFOLDING.id()) continue;

            // Affecte une valeur
            var hit = block.registry().collisionShape()
                    // Appelle une méthode
                    .intersectBox(position.sub(pos.blockX(), pos.blockY(), pos.blockZ()), bb);
            // Embranchement : vérifie une condition
            if (hit) return false;
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @SuppressWarnings({"UnstableApiUsage", "deprecation"})
    // Début d'une méthode/d'un bloc
    public void sendMessage(final Identity source, final Component message, final MessageType type) {
        // Note to readers: this method may be deprecated, however it is in fact required.
        // Appelle une méthode
        Messenger.sendMessage(this, message, ChatPosition.fromMessageType(type), source.uuid());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sends a plugin message to the player.
     *
     * @param channel the message channel
     * @param data    the message data
     */
    // Début d'une méthode/d'un bloc
    public void sendPluginMessage(String channel, byte[] data) {
        // Appelle une méthode
        sendPacket(new PluginMessagePacket(channel, data));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sends a plugin message to the player.
     * <p>
     * Message encoded to UTF-8.
     *
     * @param channel the message channel
     * @param message the message
     */
    // Début d'une méthode/d'un bloc
    public void sendPluginMessage(String channel, String message) {
        // Appelle une méthode
        sendPluginMessage(channel, message.getBytes(StandardCharsets.UTF_8));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void playSound(Sound sound) {
        // Accès à l'objet courant/parent
        this.playSound(sound, this.position.x(), this.position.y(), this.position.z());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void playSound(Sound sound, Point point) {
        // Appelle une méthode
        sendPacket(AdventurePacketConvertor.createSoundPacket(sound, point.x(), point.y(), point.z()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void playSound(Sound sound, double x, double y, double z) {
        // Appelle une méthode
        sendPacket(AdventurePacketConvertor.createSoundPacket(sound, x, y, z));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void playSound(Sound sound, Sound.Emitter emitter) {
        // Instruction de code
        final ServerPacket packet;
        // Embranchement : vérifie une condition
        if (emitter == Sound.Emitter.self()) {
            // Appelle une méthode
            packet = AdventurePacketConvertor.createSoundPacket(sound, this);
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            packet = AdventurePacketConvertor.createSoundPacket(sound, emitter);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        sendPacket(packet);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void stopSound(SoundStop stop) {
        // Appelle une méthode
        sendPacket(AdventurePacketConvertor.createSoundStopPacket(stop));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Plays a given worldEvent at the given position for this player.
     *
     * @param worldEvent            the worldEvent to play
     * @param x                     x position of the worldEvent
     * @param y                     y position of the worldEvent
     * @param z                     z position of the worldEvent
     * @param data                  data for the worldEvent
     * @param disableRelativeVolume disable volume scaling based on distance
     */
    // Début d'une méthode/d'un bloc
    public void playEffect(WorldEvent worldEvent, int x, int y, int z, int data, boolean disableRelativeVolume) {
        // Appelle une méthode
        sendPacket(new WorldEventPacket(worldEvent.id(), new Vec(x, y, z), data, disableRelativeVolume));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void sendPlayerListHeaderAndFooter(Component header, Component footer) {
        // Appelle une méthode
        sendPacket(new PlayerListHeaderAndFooterPacket(header, footer));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T> void sendTitlePart(TitlePart<T> part, T value) {
        // Appelle une méthode
        sendPacket(AdventurePacketConvertor.createTitlePartPacket(part, value));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void sendActionBar(Component message) {
        // Appelle une méthode
        sendPacket(new ActionBarPacket(message));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void resetTitle() {
        // Appelle une méthode
        sendPacket(new ClearTitlesPacket(true));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void clearTitle() {
        // Appelle une méthode
        sendPacket(new ClearTitlesPacket(false));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void showBossBar(BossBar bar) {
        // Appelle une méthode
        MinecraftServer.getBossBarManager().addBossBar(this, bar);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void hideBossBar(BossBar bar) {
        // Appelle une méthode
        MinecraftServer.getBossBarManager().removeBossBar(this, bar);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void openBook(Book book) {
        // Close the open inventory if there is one because the book will replace it.
        // Embranchement : vérifie une condition
        if (getOpenInventory() != null) {
            // Appelle une méthode
            closeInventory();
        // Fin d'un bloc/d'une expression
        }

        // TODO: when adventure updates, delete this
        // Appelle une méthode
        String title = PlainTextComponentSerializer.plainText().serialize(book.title());
        // Appelle une méthode
        String author = PlainTextComponentSerializer.plainText().serialize(book.author());
        // Affecte une valeur
        final ItemStack writtenBook = ItemStack.builder(Material.WRITTEN_BOOK)
                // Instruction de code
                .set(DataComponents.WRITTEN_BOOK_CONTENT, new WrittenBookContent(title, author, 0, book.pages(), false))
                // Appelle une méthode
                .build();

        // Set book in offhand
        // Appelle une méthode
        sendPacket(new SetSlotPacket((byte) 0, 0, (short) PlayerInventoryUtils.OFFHAND_SLOT, writtenBook));
        // Open the book
        // Appelle une méthode
        sendPacket(new OpenBookPacket(PlayerHand.OFF));
        // Restore the item in offhand
        // Appelle une méthode
        sendPacket(new SetSlotPacket((byte) 0, 0, (short) PlayerInventoryUtils.OFFHAND_SLOT, getItemInOffHand()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void showDialog(DialogLike dialog) {
        // Appelle une méthode
        sendPacket(new ShowDialogPacket(Dialog.unwrap(dialog)));
    // Fin d'un bloc/d'une expression
    }

    // TODO(1.21.6): Implementation for pending adventure method in 4.24.0.
    // Début d'une méthode/d'un bloc
    public void closeDialog() {
        // Appelle une méthode
        sendPacket(new ClearDialogPacket());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setHealth(float health) {
        // Appelle une méthode
        sendPacket(new UpdateHealthPacket(health, food, foodSaturation));
        // Accès à l'objet courant/parent
        super.setHealth(health);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the entity meta for the player.
     *
     * <p>Note that this method will throw an exception if the player's entity type has
     * been changed with {@link #switchEntityType(EntityType)}. It is wise to check
     * {@link #getEntityType()} first.</p>
     */
    // Début d'une méthode/d'un bloc
    public PlayerMeta getPlayerMeta() {
        // Renvoie une valeur à l'appelant
        return (PlayerMeta) super.getEntityMeta();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the player additional hearts.
     *
     * <p>Note that this function is uncallable if the player has their entity type switched
     * with {@link #switchEntityType(EntityType)}.</p>
     *
     * @return the player additional hearts
     */
    // Début d'une méthode/d'un bloc
    public float getAdditionalHearts() {
        // Renvoie une valeur à l'appelant
        return getPlayerMeta().getAdditionalHearts();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the amount of additional hearts shown.
     *
     * <p>Note that this function is uncallable if the player has their entity type switched
     * with {@link #switchEntityType(EntityType)}.</p>
     *
     * @param additionalHearts the count of additional hearts
     */
    // Début d'une méthode/d'un bloc
    public void setAdditionalHearts(float additionalHearts) {
        // Appelle une méthode
        getPlayerMeta().setAdditionalHearts(additionalHearts);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the player food.
     *
     * @return the player food
     */
    // Début d'une méthode/d'un bloc
    public int getFood() {
        // Renvoie une valeur à l'appelant
        return food;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets and refresh client food bar.
     *
     * @param food the new food value
     * @throws IllegalArgumentException if {@code food} is not between 0 and 20
     */
    // Début d'une méthode/d'un bloc
    public void setFood(int food) {
        // Instruction de code
        Check.argCondition(!MathUtils.isBetween(food, 0, 20),
                // Instruction de code
                "Food has to be between 0 and 20");
        // Accès à l'objet courant/parent
        this.food = food;
        // Appelle une méthode
        sendPacket(new UpdateHealthPacket(getHealth(), food, foodSaturation));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public float getFoodSaturation() {
        // Renvoie une valeur à l'appelant
        return foodSaturation;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets and refresh client food saturation.
     *
     * @param foodSaturation the food saturation
     * @throws IllegalArgumentException if {@code foodSaturation} is not between 0 and 20
     */
    // Début d'une méthode/d'un bloc
    public void setFoodSaturation(float foodSaturation) {
        // Instruction de code
        Check.argCondition(!MathUtils.isBetween(foodSaturation, 0, 20),
                // Instruction de code
                "Food saturation has to be between 0 and 20");
        // Accès à l'objet courant/parent
        this.foodSaturation = foodSaturation;
        // Appelle une méthode
        sendPacket(new UpdateHealthPacket(getHealth(), food, foodSaturation));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the player is eating.
     *
     * @return true if the player is eating, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean isEating() {
        // Embranchement : vérifie une condition
        if (!isUsingItem()) return false;
        // Appelle une méthode
        final ItemStack itemStack = getItemInHand(itemUseHand);
        // Renvoie une valeur à l'appelant
        return itemStack.has(DataComponents.FOOD) || itemStack.material() == Material.POTION;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the player is using an item.
     *
     * @return true if the player is using an item, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean isUsingItem() {
        // Renvoie une valeur à l'appelant
        return itemUseHand != null;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the hand which the player is using an item from.
     *
     * @return the item use hand, null if none
     */
    // Début d'une méthode/d'un bloc
    public @Nullable PlayerHand getItemUseHand() {
        // Renvoie une valeur à l'appelant
        return itemUseHand;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the amount of ticks which have passed since the player started using an item.
     *
     * @return the amount of ticks which have passed, or zero if the player is not using an item
     */
    // Début d'une méthode/d'un bloc
    public long getCurrentItemUseTime() {
        // Embranchement : vérifie une condition
        if (!isUsingItem()) return 0;
        // Renvoie une valeur à l'appelant
        return getAliveTicks() - startItemUseTime;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public double getEyeHeight() {
        // Renvoie une valeur à l'appelant
        return switch (getPose()) {
            // Embranchement multiple (switch/case)
            case SLEEPING -> 0.2;
            // Embranchement multiple (switch/case)
            case FALL_FLYING, SWIMMING, SPIN_ATTACK -> 0.4;
            // Embranchement multiple (switch/case)
            case SNEAKING -> 1.27;
            // Instruction de code
            default -> 1.62;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the player display name in the tab-list.
     *
     * @return the player display name, null means that {@link #getUsername()} is displayed
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Component getDisplayName() {
        // Renvoie une valeur à l'appelant
        return displayName;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the player display name in the tab-list.
     * <p>
     * Sets to null to show the player username.
     *
     * @param displayName the display name, null to display the username
     */
    // Début d'une méthode/d'un bloc
    public void setDisplayName(@Nullable Component displayName) {
        // Accès à l'objet courant/parent
        this.displayName = displayName;
        // Appelle une méthode
        PacketSendingUtils.broadcastPlayPacket(new PlayerInfoUpdatePacket(PlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME, infoEntry()));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the player skin.
     *
     * @return the player skin object,
     * null means that the player has his {@link #getUuid()} default skin
     */
    // Début d'une méthode/d'un bloc
    public @Nullable PlayerSkin getSkin() {
        // Renvoie une valeur à l'appelant
        return skin;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the player skin.
     * <p>
     * This does remove the player for all viewers to spawn it again with the correct new skin.
     *
     * @param skin the player skin, null to reset it to his {@link #getUuid()} default skin
     * @see PlayerSkinInitEvent if you want to apply the skin at connection
     */
    // Début d'une méthode/d'un bloc
    public synchronized void setSkin(@Nullable PlayerSkin skin) {
        // Accès à l'objet courant/parent
        this.skin = skin;
        // Embranchement : vérifie une condition
        if (instance == null)
            // Renvoie une valeur à l'appelant
            return;

        // Appelle une méthode
        DestroyEntitiesPacket destroyEntitiesPacket = new DestroyEntitiesPacket(getEntityId());

        // Appelle une méthode
        final PlayerInfoRemovePacket removePlayerPacket = getRemovePlayerToList();
        // Appelle une méthode
        final PlayerInfoUpdatePacket addPlayerPacket = getAddPlayerToList();

        // Affecte une valeur
        final RespawnPacket respawnPacket = new RespawnPacket(dimensionTypeId,
                // Instruction de code
                instance.getDimensionName(), 0, gameMode, gameMode,
                // Instruction de code
                false, levelFlat, deathLocation, portalCooldown,
                // Instruction de code
                DEFAULT_SEA_LEVEL, (byte) RespawnPacket.COPY_ALL);

        // Appelle une méthode
        sendPacket(removePlayerPacket);
        // Appelle une méthode
        sendPacket(destroyEntitiesPacket);
        // Appelle une méthode
        sendPacket(addPlayerPacket);
        // Appelle une méthode
        sendPacket(respawnPacket);
        // Appelle une méthode
        refreshClientStateAfterRespawn();

        // Début d'un bloc
        {
            // Remove player
            // Appelle une méthode
            PacketSendingUtils.broadcastPlayPacket(removePlayerPacket);
            // Appelle une méthode
            sendPacketToViewers(destroyEntitiesPacket);

            // Show player again
            // Appelle une méthode
            PacketSendingUtils.broadcastPlayPacket(addPlayerPacket);
            // Appelle une méthode
            getViewers().forEach(player -> showPlayer(player.getPlayerConnection()));
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        getInventory().update();
        // Appelle une méthode
        teleport(getPosition());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setDeathLocation(Pos position) {
        // Appelle une méthode
        setDeathLocation(getInstance().getDimensionName(), position);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setDeathLocation(String dimension, Pos position) {
        // Accès à l'objet courant/parent
        this.deathLocation = new WorldPos(dimension, position);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable WorldPos getDeathLocation() {
        // Renvoie une valeur à l'appelant
        return this.deathLocation;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the player has the respawn screen enabled or disabled.
     *
     * @return true if the player has the respawn screen, false if he didn't
     */
    // Début d'une méthode/d'un bloc
    public boolean isEnableRespawnScreen() {
        // Renvoie une valeur à l'appelant
        return enableRespawnScreen;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Enables or disable the respawn screen.
     *
     * @param enableRespawnScreen true to enable the respawn screen, false to disable it
     */
    // Début d'une méthode/d'un bloc
    public void setEnableRespawnScreen(boolean enableRespawnScreen) {
        // Accès à l'objet courant/parent
        this.enableRespawnScreen = enableRespawnScreen;
        // Appelle une méthode
        sendPacket(new ChangeGameStatePacket(ChangeGameStatePacket.Reason.ENABLE_RESPAWN_SCREEN, enableRespawnScreen ? 0 : 1));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the player's name as a component. This will either return the display name
     * (if set) or a component holding the username.
     *
     * @return the name
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Component getName() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNullElse(displayName, usernameComponent);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the player's username.
     *
     * @return the player's username
     */
    // Début d'une méthode/d'un bloc
    public String getUsername() {
        // Renvoie une valeur à l'appelant
        return username;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Calls an {@link ItemDropEvent} with a specified item.
     * <p>
     * Returns false if {@code item} is air.
     *
     * @param item the item to drop
     * @return true if player can drop the item (event not cancelled), false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean dropItem(ItemStack item) {
        // Embranchement : vérifie une condition
        if (item.isAir()) return false;
        // Appelle une méthode
        ItemDropEvent itemDropEvent = new ItemDropEvent(this, item);
        // Appelle une méthode
        EventDispatcher.call(itemDropEvent);
        // Renvoie une valeur à l'appelant
        return !itemDropEvent.isCancelled();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void sendResourcePacks(ResourcePackRequest request) {
        // Embranchement : vérifie une condition
        if (request.replace()) clearResourcePacks();

        // Boucle : répète un bloc
        for (final ResourcePackInfo pack : request.packs()) {
            // Appelle une méthode
            sendPacket(new ResourcePackPushPacket(pack, request.required(), request.prompt()));
            // Appelle une méthode
            pendingResourcePacks.put(pack.id(), new PendingResourcePack(request.required(), request.callback()));
            // Embranchement : vérifie une condition
            if (resourcePackFuture == null) {
                // Affecte une valeur
                resourcePackFuture = new CompletableFuture<>();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void removeResourcePacks(UUID id, UUID... others) {
        // Appelle une méthode
        sendPacket(new ResourcePackPopPacket(id));
        // Boucle : répète un bloc
        for (var other : others) {
            // Appelle une méthode
            sendPacket(new ResourcePackPopPacket(other));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void clearResourcePacks() {
        // Appelle une méthode
        sendPacket(new ResourcePackPopPacket((UUID) null));
    // Fin d'un bloc/d'une expression
    }

    /**
     * If there are resource packs in-flight, a future is returned which will be completed when
     * all resource packs have been responded to by the client. Otherwise null is returned.
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public @Nullable CompletableFuture<Void> getResourcePackFuture() {
        // Renvoie une valeur à l'appelant
        return resourcePackFuture;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void onResourcePackStatus(UUID id, ResourcePackStatus status) {
        // Appelle une méthode
        var pendingPack = pendingResourcePacks.get(id);
        // Embranchement : vérifie une condition
        if (pendingPack == null) return;

        // Appelle une méthode
        pendingPack.callback().packEventReceived(id, status, this);
        // Embranchement : vérifie une condition
        if (!status.intermediate()) {
            // Remove the callback and finish the future if relevant
            // Appelle une méthode
            pendingResourcePacks.remove(id);

            // If the resource pack is required and failed to load, bye bye!
            // Embranchement : vérifie une condition
            if (pendingPack.required() && status != ResourcePackStatus.SUCCESSFULLY_LOADED) {
                // Appelle une méthode
                kick(MISSING_REQUIRED_RESOURCE_PACK);
            // Fin d'un bloc/d'une expression
            }

            // Embranchement : vérifie une condition
            if (pendingResourcePacks.isEmpty() && resourcePackFuture != null) {
                // Appelle une méthode
                resourcePackFuture.complete(null);
                // Affecte une valeur
                resourcePackFuture = null;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Rotates the player to face {@code targetPosition}.
     *
     * @param facePoint      the point from where the player should aim
     * @param targetPosition the target position to face
     */
    // Début d'une méthode/d'un bloc
    public void facePosition(FacePoint facePoint, Point targetPosition) {
        // Appelle une méthode
        facePosition(facePoint, targetPosition, null, null);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Rotates the player to face {@code entity}.
     *
     * @param facePoint   the point from where the player should aim
     * @param entity      the entity to face
     * @param targetPoint the point to aim at {@code entity} position
     */
    // Début d'une méthode/d'un bloc
    public void facePosition(FacePoint facePoint, Entity entity, FacePoint targetPoint) {
        // Appelle une méthode
        facePosition(facePoint, entity.getPosition(), entity, targetPoint);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private void facePosition(FacePoint facePoint, Point targetPosition,
                              // Annotation pour l'élément suivant
                              @Nullable Entity entity, @Nullable FacePoint targetPoint) {
        // Appelle une méthode
        final int entityId = entity != null ? entity.getEntityId() : 0;
        // Instruction de code
        sendPacket(new FacePlayerPacket(
                // Instruction de code
                facePoint == FacePoint.EYE ?
                        // Instruction de code
                        FacePlayerPacket.FacePosition.EYES : FacePlayerPacket.FacePosition.FEET, targetPosition,
                // Instruction de code
                entityId,
                // Instruction de code
                targetPoint == FacePoint.EYE ?
                        // Instruction de code
                        FacePlayerPacket.FacePosition.EYES : FacePlayerPacket.FacePosition.FEET));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the camera at {@code entity} eyes.
     *
     * @param entity the entity to spectate
     */
    // Début d'une méthode/d'un bloc
    public void spectate(Entity entity) {
        // Appelle une méthode
        sendPacket(new CameraPacket(entity.getEntityId()));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Resets the camera at the player.
     */
    // Début d'une méthode/d'un bloc
    public void stopSpectating() {
        // Appelle une méthode
        spectate(this);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Used to retrieve the default spawn point.
     * <p>
     * Can be altered by the {@link PlayerRespawnEvent#setRespawnPosition(Pos)}.
     *
     * @return a copy of the default respawn point
     */
    // Début d'une méthode/d'un bloc
    public Pos getRespawnPoint() {
        // Renvoie une valeur à l'appelant
        return respawnPoint;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the default spawn point.
     *
     * @param respawnPoint the player respawn point
     */
    // Début d'une méthode/d'un bloc
    public void setRespawnPoint(Pos respawnPoint) {
        // Accès à l'objet courant/parent
        this.respawnPoint = respawnPoint;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Called after the player teleportation to refresh his position
     * and send data to his new viewers.
     */
    // Début d'une méthode/d'un bloc
    protected void refreshAfterTeleport() {
        // Appelle une méthode
        sendPacketsToViewers(getSpawnPacket());

        // Update for viewers
        // Appelle une méthode
        sendPacketToViewersAndSelf(getVelocityPacket());
        // Appelle une méthode
        sendPacketToViewersAndSelf(getMetadataPacket());
        // Appelle une méthode
        sendPacketToViewersAndSelf(getPropertiesPacket());
        // Appelle une méthode
        sendPacketToViewersAndSelf(getEquipmentsPacket());

        // Appelle une méthode
        getInventory().update();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the player food and health values to their maximum.
     */
    // Début d'une méthode/d'un bloc
    protected void refreshHealth() {
        // Accès à l'objet courant/parent
        this.food = 20;
        // Accès à l'objet courant/parent
        this.foodSaturation = 5;
        // refresh health and send health packet
        // Appelle une méthode
        heal();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the percentage displayed in the experience bar.
     *
     * @return the exp percentage 0-1
     */
    // Début d'une méthode/d'un bloc
    public float getExp() {
        // Renvoie une valeur à l'appelant
        return exp;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Used to change the percentage experience bar.
     * This cannot change the displayed level, see {@link #setLevel(int)}.
     *
     * @param exp a percentage between 0 and 1
     * @throws IllegalArgumentException if {@code exp} is not between 0 and 1
     */
    // Début d'une méthode/d'un bloc
    public void setExp(float exp) {
        // Appelle une méthode
        Check.argCondition(!MathUtils.isBetween(exp, 0, 1), "Exp should be between 0 and 1");
        // Accès à l'objet courant/parent
        this.exp = exp;
        // Appelle une méthode
        sendPacket(new SetExperiencePacket(exp, level, 0));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the level of the player displayed in the experience bar.
     *
     * @return the player level
     */
    // Début d'une méthode/d'un bloc
    public int getLevel() {
        // Renvoie une valeur à l'appelant
        return level;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Used to change the level of the player
     * This cannot change the displayed percentage bar see {@link #setExp(float)}
     *
     * @param level the new level of the player
     */
    // Début d'une méthode/d'un bloc
    public void setLevel(int level) {
        // Accès à l'objet courant/parent
        this.level = level;
        // Appelle une méthode
        sendPacket(new SetExperiencePacket(exp, level, 0));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getPortalCooldown() {
        // Renvoie une valeur à l'appelant
        return portalCooldown;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setPortalCooldown(int portalCooldown) {
        // Accès à l'objet courant/parent
        this.portalCooldown = portalCooldown;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the player connection.
     * <p>
     * Used to send packets and get stuff related to the connection.
     *
     * @return the player connection
     */
    // Début d'une méthode/d'un bloc
    public PlayerConnection getPlayerConnection() {
        // Renvoie une valeur à l'appelant
        return playerConnection;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Shortcut for {@link PlayerConnection#sendPacket(SendablePacket)}.
     *
     * @param packet the packet to send
     */
    // Début d'une méthode/d'un bloc
    public void sendPacket(SendablePacket packet) {
        // Accès à l'objet courant/parent
        this.playerConnection.sendPacket(packet);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void sendPackets(SendablePacket... packets) {
        // Accès à l'objet courant/parent
        this.playerConnection.sendPackets(packets);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void sendPackets(Collection<SendablePacket> packets) {
        // Accès à l'objet courant/parent
        this.playerConnection.sendPackets(packets);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the player is online or not.
     *
     * @return true if the player is online, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean isOnline() {
        // Renvoie une valeur à l'appelant
        return playerConnection.isOnline();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the player settings.
     *
     * @return the player settings
     */
    // Début d'une méthode/d'un bloc
    public ClientSettings getSettings() {
        // Renvoie une valeur à l'appelant
        return settings;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the player settings internally.
     * <p>
     * WARNING: the player will not be noticed by this change, probably unsafe.
     */
    // Début d'une méthode/d'un bloc
    public void refreshSettings(ClientSettings settings) {
        // Affecte une valeur
        final ClientSettings previous = this.settings;
        // Accès à l'objet courant/parent
        this.settings = settings;
        // Appelle une méthode
        boolean isInPlayState = getPlayerConnection().getClientState() == ConnectionState.PLAY;
        // Appelle une méthode
        PlayerMeta playerMeta = getPlayerMeta();
        // Embranchement : vérifie une condition
        if (isInPlayState) playerMeta.setNotifyAboutChanges(false);
        // Appelle une méthode
        playerMeta.setDisplayedSkinParts(settings.displayedSkinParts());
        // Appelle une méthode
        playerMeta.setMainHand(settings.mainHand());
        // Embranchement : vérifie une condition
        if (isInPlayState) playerMeta.setNotifyAboutChanges(true);

        // Appelle une méthode
        final byte previousViewDistance = previous.viewDistance();
        // Appelle une méthode
        final byte newViewDistance = settings.viewDistance();
        // Check to see if we're in an instance first, as this method is called when first logging in since the client sends the Settings packet during configuration
        // Embranchement : vérifie une condition
        if (instance != null) {
            // Load/unload chunks if necessary due to view distance changes
            // Embranchement : vérifie une condition
            if (previousViewDistance < newViewDistance) {
                // View distance expanded, send chunks
                // Début d'une méthode/d'un bloc
                ChunkRange.chunksInRange(position.chunkX(), position.chunkZ(), newViewDistance, (chunkX, chunkZ) -> {
                    // Embranchement : vérifie une condition
                    if (Math.abs(chunkX - position.chunkX()) > previousViewDistance || Math.abs(chunkZ - position.chunkZ()) > previousViewDistance) {
                        // Appelle une méthode
                        chunkAdder.accept(chunkX, chunkZ);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                });
            // Embranchement : vérifie une condition
            } else if (previousViewDistance > newViewDistance) {
                // View distance shrunk, unload chunks
                // Début d'une méthode/d'un bloc
                ChunkRange.chunksInRange(position.chunkX(), position.chunkZ(), previousViewDistance, (chunkX, chunkZ) -> {
                    // Embranchement : vérifie une condition
                    if (Math.abs(chunkX - position.chunkX()) > newViewDistance || Math.abs(chunkZ - position.chunkZ()) > newViewDistance) {
                        // Appelle une méthode
                        chunkRemover.accept(chunkX, chunkZ);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                });
            // Fin d'un bloc/d'une expression
            }
            // Else previous and current are equal, do nothing
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the player dimension.
     *
     * @return the player current dimension
     */
    // Début d'une méthode/d'un bloc
    public DimensionType getDimensionType() {
        // Renvoie une valeur à l'appelant
        return DIMENSION_TYPE_REGISTRY.get(dimensionTypeId);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public PlayerInventory getInventory() {
        // Renvoie une valeur à l'appelant
        return inventory;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Used to get the player latency,
     * computed by seeing how long it takes the client to answer the {@link KeepAlivePacket} packet.
     *
     * @return the player latency
     */
    // Début d'une méthode/d'un bloc
    public int getLatency() {
        // Renvoie une valeur à l'appelant
        return latency;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the player {@link GameMode}.
     *
     * @return the player current gamemode
     */
    // Début d'une méthode/d'un bloc
    public GameMode getGameMode() {
        // Renvoie une valeur à l'appelant
        return gameMode;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the player {@link GameMode}
     *
     * @param gameMode the new player GameMode
     * @return true if the gamemode was changed successfully, false otherwise (cancelled by event)
     */
    // Début d'une méthode/d'un bloc
    public boolean setGameMode(GameMode gameMode) {
        // Appelle une méthode
        PlayerGameModeChangeEvent playerGameModeChangeEvent = new PlayerGameModeChangeEvent(this, gameMode);
        // Appelle une méthode
        EventDispatcher.call(playerGameModeChangeEvent);
        // Embranchement : vérifie une condition
        if (playerGameModeChangeEvent.isCancelled()) {
            // Abort
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        gameMode = playerGameModeChangeEvent.getNewGameMode();

        // Accès à l'objet courant/parent
        this.gameMode = gameMode;
        // Condition to prevent sending the packets before spawning the player
        // Embranchement : vérifie une condition
        if (isActive()) {
            // Appelle une méthode
            sendPacket(new ChangeGameStatePacket(ChangeGameStatePacket.Reason.CHANGE_GAMEMODE, gameMode.ordinal()));
            // Appelle une méthode
            PacketSendingUtils.broadcastPlayPacket(new PlayerInfoUpdatePacket(PlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE, infoEntry()));
        // Fin d'un bloc/d'une expression
        }

        // The client updates their abilities based on the GameMode as follows
        // Accès à l'objet courant/parent
        this.allowFlying = gameMode.allowFlying();
        // Accès à l'objet courant/parent
        this.instantBreak = gameMode.instantBreak();
        // Accès à l'objet courant/parent
        this.invulnerable = gameMode.invulnerable();
        // Spectator automatically enables flying
        // If new game mode cannot fly, disable it
        // Embranchement : vérifie une condition
        if (gameMode == GameMode.SPECTATOR || !gameMode.allowFlying()) {
            // Embranchement : vérifie une condition
            if (isActive()) {
                // Appelle une méthode
                refreshFlying(gameMode.allowFlying());
            // Branche alternative de la condition
            } else {
                // Accès à l'objet courant/parent
                this.flying = gameMode.allowFlying();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Make sure that the player is in the PLAY state and synchronize their flight speed.
        // Embranchement : vérifie une condition
        if (isActive()) {
            // Appelle une méthode
            refreshAbilities();
            // Appelle une méthode
            updateCollisions();
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the dimension of the player.
     * Mostly unsafe since it requires sending chunks after.
     *
     * @param dimensionType the new player dimension
     */
    // Début d'une méthode/d'un bloc
    protected void sendDimension(RegistryKey<DimensionType> dimensionType, String dimensionName) {
        // Instruction de code
        Check.argCondition(instance.getDimensionName().equals(dimensionName),
                // Instruction de code
                "The dimension needs to be different than the current one!");
        // Accès à l'objet courant/parent
        this.dimensionTypeId = DIMENSION_TYPE_REGISTRY.getId(dimensionType);
        // Instruction de code
        sendPacket(new RespawnPacket(dimensionTypeId, dimensionName,
                // Instruction de code
                0, gameMode, gameMode, false, levelFlat,
                // Instruction de code
                deathLocation, portalCooldown, DEFAULT_SEA_LEVEL, (byte) RespawnPacket.COPY_ALL));
        // Appelle une méthode
        refreshClientStateAfterRespawn();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Kicks the player with a reason.
     *
     * @param component the reason
     */
    // Début d'une méthode/d'un bloc
    public void kick(Component component) {
        // Accès à l'objet courant/parent
        this.getPlayerConnection().kick(component);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Kicks the player with a reason.
     *
     * @param message the kick reason
     */
    // Début d'une méthode/d'un bloc
    public void kick(String message) {
        // Accès à l'objet courant/parent
        this.kick(Component.text(message));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the current held slot for the player.
     *
     * @param slot the slot that the player has to held
     * @throws IllegalArgumentException if {@code slot} is not between 0 and 8
     */
    // Début d'une méthode/d'un bloc
    public void setHeldItemSlot(byte slot) {
        // Appelle une méthode
        Check.argCondition(!MathUtils.isBetween(slot, 0, 8), "Slot has to be between 0 and 8");
        // Appelle une méthode
        refreshHeldSlot(slot);
        // Appelle une méthode
        sendPacket(new HeldItemChangePacket(slot));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the player held slot (0-8).
     *
     * @return the current held slot for the player
     */
    // Début d'une méthode/d'un bloc
    public byte getHeldSlot() {
        // Renvoie une valeur à l'appelant
        return heldSlot;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the tag below the name.
     *
     * @param belowNameTag The new below name tag
     */
    // Début d'une méthode/d'un bloc
    public void setBelowNameTag(BelowNameTag belowNameTag) {
        // Embranchement : vérifie une condition
        if (this.belowNameTag == belowNameTag) return;

        // Embranchement : vérifie une condition
        if (this.belowNameTag != null) {
            // Accès à l'objet courant/parent
            this.belowNameTag.removeViewer(this);
        // Fin d'un bloc/d'une expression
        }

        // Accès à l'objet courant/parent
        this.belowNameTag = belowNameTag;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ClickPreprocessor getClickPreprocessor() {
        // Renvoie une valeur à l'appelant
        return clickPreprocessor;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the player open inventory.
     *
     * @return the currently open inventory, null if there is not (player inventory is not detected)
     */
    // Début d'une méthode/d'un bloc
    public @Nullable AbstractInventory getOpenInventory() {
        // Renvoie une valeur à l'appelant
        return openInventory;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Opens the specified Inventory, close the previous inventory if existing.
     *
     * @param inventory the inventory to open
     * @return true if the inventory has been opened/sent to the player, false otherwise (cancelled by event)
     */
    // Début d'une méthode/d'un bloc
    public boolean openInventory(Inventory inventory) {
        // Appelle une méthode
        InventoryOpenEvent inventoryOpenEvent = new InventoryOpenEvent(inventory, this);

        // Début d'une méthode/d'un bloc
        EventDispatcher.callCancellable(inventoryOpenEvent, () -> {
            // Appelle une méthode
            AbstractInventory openInventory = getOpenInventory();
            // Embranchement : vérifie une condition
            if (openInventory != null) {
                // Appelle une méthode
                openInventory.removeViewer(this);
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            AbstractInventory newInventory = inventoryOpenEvent.getInventory();

            // Appelle une méthode
            newInventory.addViewer(this);
            // Accès à l'objet courant/parent
            this.openInventory = newInventory;
        // Fin d'un bloc/d'une expression
        });
        // Renvoie une valeur à l'appelant
        return !inventoryOpenEvent.isCancelled();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Closes the current inventory if there is any.
     * It closes the player inventory (when opened) if {@link #getOpenInventory()} returns null.
     */
    // Début d'une méthode/d'un bloc
    public void closeInventory() {
        // Appelle une méthode
        AbstractInventory open = getOpenInventory();
        // Appelle une méthode
        byte id = (open == null ? getInventory() : open).getWindowId();

        // Appelle une méthode
        closeInventory(false, id);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void closeInventory(boolean fromClient, byte windowId) {
        // Appelle une méthode
        AbstractInventory openInventory = windowId == 0 ? getInventory() : getOpenInventory();

        // Nothing happens if it has the wrong ID or if there's no inventory
        // Embranchement : vérifie une condition
        if (openInventory == null || windowId != openInventory.getWindowId()) return;

        // Appelle une méthode
        InventoryCloseEvent inventoryCloseEvent = new InventoryCloseEvent(openInventory, this, fromClient);
        // Appelle une méthode
        EventDispatcher.call(inventoryCloseEvent);

        // Embranchement : vérifie une condition
        if (!fromClient) {
            // Affecte une valeur
            didCloseInventory = true;
        // Fin d'un bloc/d'une expression
        }

        // Accès à l'objet courant/parent
        this.openInventory = null;
        // Embranchement : vérifie une condition
        if (openInventory != inventory) openInventory.removeViewer(this);
        // Appelle une méthode
        inventory.update();

        // Affecte une valeur
        didCloseInventory = false;

        // Appelle une méthode
        Inventory newInventory = inventoryCloseEvent.getNewInventory();
        // Embranchement : vérifie une condition
        if (newInventory != null)
            // Appelle une méthode
            openInventory(newInventory);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Used internally to determine when sending the close inventory packet should be skipped.
     */
    // Début d'une méthode/d'un bloc
    public boolean didCloseInventory() {
        // Renvoie une valeur à l'appelant
        return didCloseInventory;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Used internally to reset the skipClosePacket field, which determines when sending the close inventory packet
     * should be skipped.
     * <p>
     * Shouldn't be used externally without proper understanding of its consequence.
     *
     * @param didCloseInventory the new didCloseInventory field
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void UNSAFE_changeDidCloseInventory(boolean didCloseInventory) {
        // Accès à l'objet courant/parent
        this.didCloseInventory = didCloseInventory;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getNextTeleportId() {
        // Renvoie une valeur à l'appelant
        return teleportId.incrementAndGet();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getLastSentTeleportId() {
        // Renvoie une valeur à l'appelant
        return teleportId.get();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getLastReceivedTeleportId() {
        // Renvoie une valeur à l'appelant
        return receivedTeleportId;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void refreshReceivedTeleportId(int receivedTeleportId) {
        // Embranchement : vérifie une condition
        if (receivedTeleportId < 0) return;
        // Accès à l'objet courant/parent
        this.receivedTeleportId = receivedTeleportId;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Used to synchronize player position with viewers on spawn or after {@link Entity#teleport(Pos, long[], int)}
     * in properties where a {@link PlayerPositionAndLookPacket} is required
     *
     * @param position      the position used by {@link PlayerPositionAndLookPacket}
     *                      this may not be the same as the {@link Entity#position}
     * @param relativeFlags byte flags used by {@link PlayerPositionAndLookPacket}
     * @param shouldConfirm if false, the teleportation will be done without confirmation
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Instruction de code
    void synchronizePositionAfterTeleport(Pos position, Point velocity,
                                          // Annotation pour l'élément suivant
                                          @MagicConstant(flagsFromClass = RelativeFlags.class) int relativeFlags,
                                          // Début d'une méthode/d'un bloc
                                          boolean shouldConfirm) {
        // Appelle une méthode
        int teleportId = shouldConfirm ? getNextTeleportId() : -1;
        // Appelle une méthode
        sendPacket(new PlayerPositionAndLookPacket(teleportId, position, velocity, position.yaw(), position.pitch(), relativeFlags));
        // Accès à l'objet courant/parent
        super.synchronizePosition();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Forces the player's client to look towards the target yaw/pitch
     *
     * @param yaw   the new yaw
     * @param pitch the new pitch
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setView(float yaw, float pitch) {
        // Appelle une méthode
        teleport(new Pos(0, 0, 0, yaw, pitch), null, RelativeFlags.COORD).join();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Forces the player's client to look towards the specified point
     * <p>
     * Note: the player's position is not updated on the server until
     * the client receives this packet
     *
     * @param point the point to look at
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void lookAt(Point point) {
        // Let the player's client provide updated position values
        // Appelle une méthode
        sendPacket(new FacePlayerPacket(FacePlayerPacket.FacePosition.EYES, point, 0, null));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Forces the player's client to look towards the specified entity
     * <p>
     * Note: the player's position is not updated on the server until
     * the client receives this packet
     *
     * @param entity the entity to look at
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void lookAt(Entity entity) {
        // Let the player's client provide updated position values
        // Appelle une méthode
        sendPacket(new FacePlayerPacket(FacePlayerPacket.FacePosition.EYES, entity.getPosition(), entity.getEntityId(), FacePlayerPacket.FacePosition.EYES));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the player permission level.
     *
     * @return the player permission level
     */
    // Début d'une méthode/d'un bloc
    public int getPermissionLevel() {
        // Renvoie une valeur à l'appelant
        return permissionLevel;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the player permission level.
     *
     * @param permissionLevel the new player permission level
     * @throws IllegalArgumentException if {@code permissionLevel} is not between 0 and 4
     */
    // Début d'une méthode/d'un bloc
    public void setPermissionLevel(int permissionLevel) {
        // Appelle une méthode
        Check.argCondition(!MathUtils.isBetween(permissionLevel, 0, 4), "permissionLevel has to be between 0 and 4");

        // Accès à l'objet courant/parent
        this.permissionLevel = permissionLevel;

        // Condition to prevent sending the packets before spawning the player
        // Embranchement : vérifie une condition
        if (isActive()) {

            // Affecte une valeur
            final byte permissionLevelStatus = (byte) (EntityStatuses.Player.PERMISSION_LEVEL_0 + permissionLevel);
            // Appelle une méthode
            triggerStatus(permissionLevelStatus);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets or remove the reduced debug screen.
     *
     * @param reduced should the player has the reduced debug screen
     */
    // Début d'une méthode/d'un bloc
    public void setReducedDebugScreenInformation(boolean reduced) {
        // Accès à l'objet courant/parent
        this.reducedDebugScreenInformation = reduced;

        // Affecte une valeur
        final byte debugScreenStatus = (byte) (reduced ? EntityStatuses.Player.ENABLE_DEBUG_SCREEN : EntityStatuses.Player.DISABLE_DEBUG_SCREEN);
        // Appelle une méthode
        triggerStatus(debugScreenStatus);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the player has the reduced debug screen.
     *
     * @return true if the player has the reduced debug screen, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean hasReducedDebugScreenInformation() {
        // Renvoie une valeur à l'appelant
        return reducedDebugScreenInformation;
    // Fin d'un bloc/d'une expression
    }

    /**
     * This do update the {@code invulnerable} field in the packet {@link PlayerAbilitiesPacket}
     * and prevent the player from receiving damage.
     *
     * @param invulnerable should the player be invulnerable
     */
    // Début d'une méthode/d'un bloc
    public void setInvulnerable(boolean invulnerable) {
        // Accès à l'objet courant/parent
        super.setInvulnerable(invulnerable);
        // Appelle une méthode
        refreshAbilities();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setSneaking(boolean sneaking) {
        // Embranchement : vérifie une condition
        if (isFlying()) { //If we are flying, don't set the players pose to sneaking as this can clip them through blocks
            // Accès à l'objet courant/parent
            this.entityMeta.setSneaking(sneaking);
        // Branche alternative de la condition
        } else {
            // Accès à l'objet courant/parent
            super.setSneaking(sneaking);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the player is currently flying.
     *
     * @return true if the player if flying, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean isFlying() {
        // Renvoie une valeur à l'appelant
        return flying;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the player flying.
     *
     * @param flying should the player fly
     */
    // Début d'une méthode/d'un bloc
    public void setFlying(boolean flying) {
        // Appelle une méthode
        refreshFlying(flying);
        // Appelle une méthode
        refreshAbilities();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Updates the internal flying field.
     * <p>
     * Mostly unsafe since there is nothing to backup the value, used internally for creative players.
     *
     * @param flying the new flying field
     * @see #setFlying(boolean) instead
     */
    // Début d'une méthode/d'un bloc
    public void refreshFlying(boolean flying) {
        //When the player starts or stops flying, their pose needs to change
        // Embranchement : vérifie une condition
        if (this.flying != flying) {
            // Appelle une méthode
            EntityPose pose = getPose();

            // Embranchement : vérifie une condition
            if (this.isSneaking() && pose == EntityPose.STANDING) {
                // Appelle une méthode
                setPose(EntityPose.SNEAKING);
            // Embranchement : vérifie une condition
            } else if (pose == EntityPose.SNEAKING) {
                // Appelle une méthode
                setPose(EntityPose.STANDING);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Accès à l'objet courant/parent
        this.flying = flying;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the player is allowed to fly.
     *
     * @return true if the player if allowed to fly, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean isAllowFlying() {
        // Renvoie une valeur à l'appelant
        return allowFlying;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Allows or forbid the player to fly.
     *
     * @param allowFlying should the player be allowed to fly
     */
    // Début d'une méthode/d'un bloc
    public void setAllowFlying(boolean allowFlying) {
        // Accès à l'objet courant/parent
        this.allowFlying = allowFlying;
        // Appelle une méthode
        refreshAbilities();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isInstantBreak() {
        // Renvoie une valeur à l'appelant
        return instantBreak;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the player ability "Creative Mode".
     *
     * @param instantBreak true to allow instant break
     * @see <a href="https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Protocol#Player_Abilities_(clientbound)">player abilities</a>
     */
    // Début d'une méthode/d'un bloc
    public void setInstantBreak(boolean instantBreak) {
        // Accès à l'objet courant/parent
        this.instantBreak = instantBreak;
        // Appelle une méthode
        refreshAbilities();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the player flying speed.
     *
     * @return the flying speed of the player
     */
    // Début d'une méthode/d'un bloc
    public float getFlyingSpeed() {
        // Renvoie une valeur à l'appelant
        return flyingSpeed;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Updates the internal field and send a {@link PlayerAbilitiesPacket} with the new flying speed.
     *
     * @param flyingSpeed the new flying speed of the player
     */
    // Début d'une méthode/d'un bloc
    public void setFlyingSpeed(float flyingSpeed) {
        // Accès à l'objet courant/parent
        this.flyingSpeed = flyingSpeed;
        // Appelle une méthode
        refreshAbilities();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public float getFieldViewModifier() {
        // Renvoie une valeur à l'appelant
        return fieldViewModifier;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setFieldViewModifier(float fieldViewModifier) {
        // Accès à l'objet courant/parent
        this.fieldViewModifier = fieldViewModifier;
        // Appelle une méthode
        refreshAbilities();
    // Fin d'un bloc/d'une expression
    }

    /**
     * This is the map used to send the statistic packet.
     * It is possible to add/remove/change statistic value directly into it.
     *
     * @return the modifiable statistic map
     */
    // Début d'une méthode/d'un bloc
    public Map<PlayerStatistic, Integer> getStatisticValueMap() {
        // Renvoie une valeur à l'appelant
        return statisticValueMap;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the last reported set of player inputs.
     *
     * <p>This information comes from the client so should be considered as such.</p>
     */
    // Début d'une méthode/d'un bloc
    public PlayerInputs inputs() {
        // Renvoie une valeur à l'appelant
        return inputs;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sends to the player a {@link PlayerAbilitiesPacket} with all the updated fields.
     */
    // Début d'une méthode/d'un bloc
    protected void refreshAbilities() {
        // Affecte une valeur
        byte flags = 0;
        // Embranchement : vérifie une condition
        if (invulnerable)
            // Affecte une valeur
            flags |= PlayerAbilitiesPacket.FLAG_INVULNERABLE;
        // Embranchement : vérifie une condition
        if (flying)
            // Affecte une valeur
            flags |= PlayerAbilitiesPacket.FLAG_FLYING;
        // Embranchement : vérifie une condition
        if (allowFlying)
            // Affecte une valeur
            flags |= PlayerAbilitiesPacket.FLAG_ALLOW_FLYING;
        // Embranchement : vérifie une condition
        if (instantBreak)
            // Affecte une valeur
            flags |= PlayerAbilitiesPacket.FLAG_INSTANT_BREAK;
        // Appelle une méthode
        sendPacket(new PlayerAbilitiesPacket(flags, flyingSpeed, fieldViewModifier));
    // Fin d'un bloc/d'une expression
    }

    /**
     * All packets in the queue are executed in the {@link #update(long)} method
     * It is used internally to add all received packet from the client.
     * Could be used to "simulate" a received packet, but to use at your own risk.
     *
     * @param packet the packet to add in the queue
     */
    // Début d'une méthode/d'un bloc
    public void addPacketToQueue(ClientPacket packet) {
        // Appelle une méthode
        final boolean success = packets.offer(packet);
        // Embranchement : vérifie une condition
        if (!success) {
            // Appelle une méthode
            kick(Component.text("Too Many Packets", NamedTextColor.RED));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void interpretPacketQueue() {
        // Appelle une méthode
        final PacketListenerManager manager = MinecraftServer.getPacketListenerManager();
        // This method is NOT thread-safe
        // Accès à l'objet courant/parent
        this.packets.drain(packet -> manager.processClientPacket(packet, playerConnection), ServerFlag.PLAYER_PACKET_PER_TICK);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the storage player latency and update its tab value.
     *
     * @param latency the new player latency
     */
    // Début d'une méthode/d'un bloc
    public void refreshLatency(int latency) {
        // Accès à l'objet courant/parent
        this.latency = latency;
        // Embranchement : vérifie une condition
        if (getPlayerConnection().getServerState() == ConnectionState.PLAY) {
            // Appelle une méthode
            PacketSendingUtils.broadcastPlayPacket(new PlayerInfoUpdatePacket(PlayerInfoUpdatePacket.Action.UPDATE_LATENCY, infoEntry()));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void refreshOnGround(boolean onGround) {
        // Accès à l'objet courant/parent
        this.onGround = onGround;
        // Embranchement : vérifie une condition
        if (this.onGround && this.isFlyingWithElytra()) {
            // Accès à l'objet courant/parent
            this.setFlyingWithElytra(false);
            // Appelle une méthode
            EventDispatcher.call(new PlayerStopFlyingWithElytraEvent(this));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Used to change internally the last sent last keep alive id.
     * <p>
     * Warning: could lead to have the player kicked because of a wrong keep alive packet.
     *
     * @param lastKeepAlive the new lastKeepAlive id
     */
    // Début d'une méthode/d'un bloc
    public void refreshKeepAlive(long lastKeepAlive) {
        // Accès à l'objet courant/parent
        this.lastKeepAlive = lastKeepAlive;
        // Accès à l'objet courant/parent
        this.answerKeepAlive = false;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean didAnswerKeepAlive() {
        // Renvoie une valeur à l'appelant
        return answerKeepAlive;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void refreshAnswerKeepAlive(boolean answerKeepAlive) {
        // Accès à l'objet courant/parent
        this.answerKeepAlive = answerKeepAlive;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the held item for the player viewers
     * Also cancel item usage if {@link #isUsingItem()} was true.
     * <p>
     * Warning: the player will not be noticed by this chance, only his viewers,
     * see instead: {@link #setHeldItemSlot(byte)}.
     *
     * @param slot the new held slot
     */
    // Début d'une méthode/d'un bloc
    public void refreshHeldSlot(byte slot) {
        // Affecte une valeur
        byte oldHeldSlot = this.heldSlot;
        // Accès à l'objet courant/parent
        this.heldSlot = slot;
        // Appelle une méthode
        syncEquipment(EquipmentSlot.MAIN_HAND);
        // Appelle une méthode
        updateEquipmentAttributes(inventory.getItemStack(oldHeldSlot), inventory.getItemStack(this.heldSlot), EquipmentSlot.MAIN_HAND);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void refreshItemUse(@Nullable PlayerHand itemUseHand, long itemUseTimeTicks) {
        // Accès à l'objet courant/parent
        this.itemUseHand = itemUseHand;
        // Embranchement : vérifie une condition
        if (itemUseHand != null) {
            // Accès à l'objet courant/parent
            this.startItemUseTime = getAliveTicks();
            // Accès à l'objet courant/parent
            this.itemUseTime = itemUseTimeTicks;
        // Branche alternative de la condition
        } else {
            // Accès à l'objet courant/parent
            this.startItemUseTime = 0;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void clearItemUse() {
        // Appelle une méthode
        refreshItemUse(null, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void refreshInput(boolean forward, boolean backward, boolean left, boolean right, boolean jump, boolean shift, boolean sprint) {
        // Appelle une méthode
        boolean oldForward = this.inputs.forward();
        // Appelle une méthode
        boolean oldBackward = this.inputs.backward();
        // Appelle une méthode
        boolean oldLeft = this.inputs.left();
        // Appelle une méthode
        boolean oldRight = this.inputs.right();
        // Appelle une méthode
        boolean oldJump = this.inputs.jump();
        // Appelle une méthode
        boolean oldShift = this.inputs.shift();
        // Appelle une méthode
        boolean oldSprint = this.inputs.sprint();

        // Accès à l'objet courant/parent
        this.inputs.refresh(forward, backward, left, right, jump, shift, sprint);
        // Accès à l'objet courant/parent
        this.setSneaking(shift);

        // Appelle une méthode
        var event = new PlayerInputEvent(this, oldForward, oldBackward, oldLeft, oldRight, oldJump, oldShift, oldSprint);
        // Appelle une méthode
        EventDispatcher.call(event);

        // Embranchement : vérifie une condition
        if (event.hasPressedShiftKey()) {
            // Appelle une méthode
            EventDispatcher.call(new PlayerStartSneakingEvent(this));
        // Embranchement : vérifie une condition
        } else if (event.hasReleasedShiftKey()) {
            // Appelle une méthode
            EventDispatcher.call(new PlayerStopSneakingEvent(this));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the last sent keep alive id.
     *
     * @return the last keep alive id sent to the player
     */
    // Début d'une méthode/d'un bloc
    public long getLastKeepAlive() {
        // Renvoie une valeur à l'appelant
        return lastKeepAlive;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public HoverEvent<ShowEntity> asHoverEvent(UnaryOperator<ShowEntity> op) {
        // Renvoie une valeur à l'appelant
        return HoverEvent.showEntity(ShowEntity.showEntity(EntityType.PLAYER, getUuid(), this.displayName));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the packet to add the player from the tab-list.
     *
     * @return a {@link PlayerInfoUpdatePacket} to add the player
     */
    // Début d'une méthode/d'un bloc
    protected PlayerInfoUpdatePacket getAddPlayerToList() {
        // Renvoie une valeur à l'appelant
        return new PlayerInfoUpdatePacket(EnumSet.of(PlayerInfoUpdatePacket.Action.ADD_PLAYER, PlayerInfoUpdatePacket.Action.UPDATE_LISTED),
                // Appelle une méthode
                List.of(infoEntry()));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the packet to remove the player from the tab-list.
     *
     * @return a {@link PlayerInfoRemovePacket} to remove the player
     */
    // Début d'une méthode/d'un bloc
    protected PlayerInfoRemovePacket getRemovePlayerToList() {
        // Renvoie une valeur à l'appelant
        return new PlayerInfoRemovePacket(getUuid());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private PlayerInfoUpdatePacket.Entry infoEntry() {
        // Affecte une valeur
        final PlayerSkin skin = this.skin;
        // Instruction de code
        List<PlayerInfoUpdatePacket.Property> prop = skin != null ?
                // Instruction de code
                List.of(new PlayerInfoUpdatePacket.Property("textures", skin.textures(), skin.signature())) :
                // Appelle une méthode
                List.of();
        // Appelle une méthode
        byte hatIndex = ((MetadataDef.Entry.BitMask) MetadataDef.Player.IS_HAT_ENABLED).bitMask();
        // Renvoie une valeur à l'appelant
        return new PlayerInfoUpdatePacket.Entry(getUuid(), getUsername(), prop,
                // Appelle une méthode
                true, getLatency(), getGameMode(), displayName, null, 0, (settings.displayedSkinParts() & hatIndex) == hatIndex);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sends all the related packet to have the player sent to another with related data
     * (create player, spawn position, velocity, metadata, equipments, passengers, team).
     * <p>
     * WARNING: this alone does not sync the player, please use {@link #addViewer(Player)}.
     *
     * @param connection the connection to show the player to
     */
    // Début d'une méthode/d'un bloc
    protected void showPlayer(PlayerConnection connection) {
        // Appelle une méthode
        connection.sendPacket(getSpawnPacket());
        // Appelle une méthode
        connection.sendPacket(getVelocityPacket());
        // Appelle une méthode
        connection.sendPacket(getMetadataPacket());
        // Appelle une méthode
        connection.sendPacket(getEquipmentsPacket());
        // Embranchement : vérifie une condition
        if (hasPassenger()) {
            // Appelle une méthode
            connection.sendPacket(getPassengersPacket());
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        connection.sendPacket(new EntityHeadLookPacket(getEntityId(), headRotation));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ItemStack getEquipment(EquipmentSlot slot) {
        // Renvoie une valeur à l'appelant
        return inventory.getEquipment(slot, heldSlot);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setEquipment(EquipmentSlot slot, ItemStack itemStack) {
        // Appelle une méthode
        inventory.setEquipment(slot, heldSlot, itemStack);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public PlayerSnapshot updateSnapshot(SnapshotUpdater updater) {
        // Appelle une méthode
        final EntitySnapshot snapshot = super.updateSnapshot(updater);
        // Renvoie une valeur à l'appelant
        return new SnapshotImpl.Player(snapshot, username, gameMode);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Locale getLocale() {
        // Renvoie une valeur à l'appelant
        return settings.locale();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the player's locale. This will only set the locale of the player as it
     * is stored in the server. This will also be reset if the settings are refreshed.
     *
     * @param locale the new locale
     */
    // Début d'une méthode/d'un bloc
    public void setLocale(Locale locale) {
        // Affecte une valeur
        final ClientSettings settings = this.settings;
        // Instruction de code
        refreshSettings(new ClientSettings(
                // Instruction de code
                locale, settings.viewDistance(), settings.chatMessageType(), settings.chatColors(),
                // Instruction de code
                settings.displayedSkinParts(), settings.mainHand(), settings.enableTextFiltering(),
                // Instruction de code
                settings.allowServerListings(), settings.particleSetting()
        // Instruction de code
        ));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pointers pointers() {
        // Renvoie une valeur à l'appelant
        return PLAYER_POINTERS_SUPPLIER.view(this);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected void updateCollisions() {
        // Instruction de code
        preventBlockPlacement = gameMode != GameMode.SPECTATOR;
        // Instruction de code
        collidesWithEntities = gameMode != GameMode.SPECTATOR;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected void sendChunkUpdates(Chunk newChunk) {
        // Embranchement : vérifie une condition
        if (chunkUpdateLimitChecker.addToHistory(newChunk)) {
            // Appelle une méthode
            final int newX = newChunk.getChunkX();
            // Appelle une méthode
            final int newZ = newChunk.getChunkZ();
            // Affecte une valeur
            final Vec old = chunksLoadedByClient;
            // Appelle une méthode
            sendPacket(new UpdateViewPositionPacket(newX, newZ));
            // Instruction de code
            ChunkRange.chunksInRangeDiffering(newX, newZ, (int) old.x(), (int) old.z(),
                    // Accès à l'objet courant/parent
                    this.effectiveViewDistance(), chunkAdder, chunkRemover);
            // Accès à l'objet courant/parent
            this.chunksLoadedByClient = new Vec(newX, newZ);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * @see #teleport(Pos, long[], int)
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public CompletableFuture<Void> teleport(Pos position, long @Nullable [] chunks, int flags) {
        // Appelle une méthode
        chunkUpdateLimitChecker.clearHistory();
        // Renvoie une valeur à l'appelant
        return super.teleport(position, chunks, flags);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Send a {@link Notification} to the player.
     *
     * @param notification the {@link Notification} to send
     */
    // Début d'une méthode/d'un bloc
    public void sendNotification(Notification notification) {
        // Appelle une méthode
        sendPacket(notification.buildAddPacket());
        // Appelle une méthode
        sendPacket(notification.buildRemovePacket());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sends a {@link EntityAnimationPacket} to clear remove the sleep darkness.
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void leaveBed() {
        // Appelle une méthode
        EntityAnimationPacket packet = new EntityAnimationPacket(getEntityId(), EntityAnimationPacket.Animation.LEAVE_BED);
        // Appelle une méthode
        sendPacket(packet);
        // Accès à l'objet courant/parent
        super.leaveBed();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum FacePoint {
        // Instruction de code
        FEET,
        // Instruction de code
        EYE
    // Fin d'un bloc/d'une expression
    }

    // Settings enum

    // Début d'une méthode/d'un bloc
    private int compareChunkDistance(long chunkIndexA, long chunkIndexB) {
        // Appelle une méthode
        int chunkAX = CoordConversion.chunkIndexGetX(chunkIndexA);
        // Appelle une méthode
        int chunkAZ = CoordConversion.chunkIndexGetZ(chunkIndexA);
        // Appelle une méthode
        int chunkBX = CoordConversion.chunkIndexGetX(chunkIndexB);
        // Appelle une méthode
        int chunkBZ = CoordConversion.chunkIndexGetZ(chunkIndexB);
        // Appelle une méthode
        int chunkDistanceA = Math.abs(chunkAX - chunksLoadedByClient.blockX()) + Math.abs(chunkAZ - chunksLoadedByClient.blockZ());
        // Appelle une méthode
        int chunkDistanceB = Math.abs(chunkBX - chunksLoadedByClient.blockX()) + Math.abs(chunkBZ - chunksLoadedByClient.blockZ());
        // Renvoie une valeur à l'appelant
        return Integer.compare(chunkDistanceA, chunkDistanceB);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the client's 'effective' view distance, which is the minimum of the client's view distance settings, and the local instance settings, plus one
     * @return The effective chunk view distance range of the client
     */
    // Début d'une méthode/d'un bloc
    public int effectiveViewDistance() {
        // Affecte une valeur
        Instance instance = this.instance;
        // Appelle une méthode
        int maxViewDistance = instance != null ? instance.viewDistance() : ServerFlag.CHUNK_VIEW_DISTANCE;
        // Renvoie une valeur à l'appelant
        return Math.min(settings.viewDistance(), maxViewDistance) + 1;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Acquirable<? extends Player> acquirable() {
        // Renvoie une valeur à l'appelant
        return (Acquirable<? extends Player>) super.acquirable();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
