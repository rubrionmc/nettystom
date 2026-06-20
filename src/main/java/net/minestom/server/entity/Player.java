// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import it.unimi.dsi.fastutil.longs.LongArrayPriorityQueue;
// Import of a required class
import it.unimi.dsi.fastutil.longs.LongPriorityQueue;
// Import of a required class
import net.kyori.adventure.bossbar.BossBar;
// Import of a required class
import net.kyori.adventure.dialog.DialogLike;
// Import of a required class
import net.kyori.adventure.identity.Identity;
// Import of a required class
import net.kyori.adventure.inventory.Book;
// Import of a required class
import net.kyori.adventure.pointer.Pointers;
// Import of a required class
import net.kyori.adventure.pointer.PointersSupplier;
// Import of a required class
import net.kyori.adventure.resource.ResourcePackCallback;
// Import of a required class
import net.kyori.adventure.resource.ResourcePackInfo;
// Import of a required class
import net.kyori.adventure.resource.ResourcePackRequest;
// Import of a required class
import net.kyori.adventure.resource.ResourcePackStatus;
// Import of a required class
import net.kyori.adventure.sound.Sound;
// Import of a required class
import net.kyori.adventure.sound.SoundStop;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.event.HoverEvent;
// Import of a required class
import net.kyori.adventure.text.event.HoverEvent.ShowEntity;
// Import of a required class
import net.kyori.adventure.text.event.HoverEventSource;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
// Import of a required class
import net.kyori.adventure.title.TitlePart;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.advancements.AdvancementTab;
// Import of a required class
import net.minestom.server.advancements.Notification;
// Import of a required class
import net.minestom.server.adventure.AdventurePacketConvertor;
// Import of a required class
import net.minestom.server.adventure.audience.Audiences;
// Import of a required class
import net.minestom.server.collision.BoundingBox;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.coordinate.*;
// Import of a required class
import net.minestom.server.dialog.Dialog;
// Import of a required class
import net.minestom.server.entity.metadata.LivingEntityMeta;
// Import of a required class
import net.minestom.server.entity.metadata.avatar.PlayerMeta;
// Import of a required class
import net.minestom.server.entity.vehicle.PlayerInputs;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.inventory.InventoryCloseEvent;
// Import of a required class
import net.minestom.server.event.inventory.InventoryOpenEvent;
// Import of a required class
import net.minestom.server.event.item.ItemDropEvent;
// Import of a required class
import net.minestom.server.event.item.PickupExperienceEvent;
// Import of a required class
import net.minestom.server.event.item.PlayerFinishItemUseEvent;
// Import of a required class
import net.minestom.server.event.player.*;
// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import net.minestom.server.instance.EntityTracker;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.SharedInstance;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.inventory.AbstractInventory;
// Import of a required class
import net.minestom.server.inventory.Inventory;
// Import of a required class
import net.minestom.server.inventory.PlayerInventory;
// Import of a required class
import net.minestom.server.inventory.click.ClickPreprocessor;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.item.component.WrittenBookContent;
// Import of a required class
import net.minestom.server.listener.manager.PacketListenerManager;
// Import of a required class
import net.minestom.server.message.ChatPosition;
// Import of a required class
import net.minestom.server.message.Messenger;
// Import of a required class
import net.minestom.server.monitoring.EventsJFR;
// Import of a required class
import net.minestom.server.network.ConnectionManager;
// Import of a required class
import net.minestom.server.network.ConnectionState;
// Import of a required class
import net.minestom.server.network.PlayerProvider;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.network.packet.server.SendablePacket;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.network.packet.server.common.*;
// Import of a required class
import net.minestom.server.network.packet.server.play.*;
// Import of a required class
import net.minestom.server.network.packet.server.play.data.WorldPos;
// Import of a required class
import net.minestom.server.network.player.ClientSettings;
// Import of a required class
import net.minestom.server.network.player.GameProfile;
// Import of a required class
import net.minestom.server.network.player.PlayerConnection;
// Import of a required class
import net.minestom.server.recipe.RecipeManager;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.scoreboard.BelowNameTag;
// Import of a required class
import net.minestom.server.scoreboard.Team;
// Import of a required class
import net.minestom.server.snapshot.EntitySnapshot;
// Import of a required class
import net.minestom.server.snapshot.PlayerSnapshot;
// Import of a required class
import net.minestom.server.snapshot.SnapshotImpl;
// Import of a required class
import net.minestom.server.snapshot.SnapshotUpdater;
// Import of a required class
import net.minestom.server.statistic.PlayerStatistic;
// Import of a required class
import net.minestom.server.thread.Acquirable;
// Import of a required class
import net.minestom.server.timer.Scheduler;
// Import of a required class
import net.minestom.server.utils.MathUtils;
// Import of a required class
import net.minestom.server.utils.PacketSendingUtils;
// Import of a required class
import net.minestom.server.utils.async.AsyncUtils;
// Import of a required class
import net.minestom.server.utils.chunk.ChunkUpdateLimitChecker;
// Import of a required class
import net.minestom.server.utils.collection.ConcurrentMessageQueues;
// Import of a required class
import net.minestom.server.utils.identity.NamedAndIdentified;
// Import of a required class
import net.minestom.server.utils.inventory.PlayerInventoryUtils;
// Import of a required class
import net.minestom.server.utils.time.Cooldown;
// Import of a required class
import net.minestom.server.utils.time.TimeUnit;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import net.minestom.server.world.DimensionType;
// Import of a required class
import net.minestom.server.worldevent.WorldEvent;
// Import of a required class
import org.intellij.lang.annotations.MagicConstant;
// Import of a required class
import org.jctools.queues.MessagePassingQueue;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.nio.charset.StandardCharsets;
// Import of a required class
import java.time.Duration;
// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.CompletableFuture;
// Import of a required class
import java.util.concurrent.CountDownLatch;
// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;
// Import of a required class
import java.util.concurrent.locks.ReentrantLock;
// Import of a required class
import java.util.function.Consumer;
// Import of a required class
import java.util.function.UnaryOperator;

/**
 * Those are the major actors of the server
 * <p>
 * You can easily create your own implementation of this and use it with {@link ConnectionManager#setPlayerProvider(PlayerProvider)}.
 */
// Type declaration (class/interface/enum/record)
public class Player extends LivingEntity implements CommandSender, HoverEventSource<ShowEntity>, NamedAndIdentified {
    // Calls a method
    private static final DynamicRegistry<DimensionType> DIMENSION_TYPE_REGISTRY = MinecraftServer.getDimensionTypeRegistry();

    // Calls a method
    private static final Component REMOVE_MESSAGE = Component.text("You have been removed from the server without reason.", NamedTextColor.RED);
    // Calls a method
    private static final Component MISSING_REQUIRED_RESOURCE_PACK = Component.text("Required resource pack was not loaded.", NamedTextColor.RED);

    // Adventure pointer supplier
    // Assigns a value
    protected static final PointersSupplier<Player> PLAYER_POINTERS_SUPPLIER = PointersSupplier.<Player>builder()
            // Code statement
            .parent(ENTITY_POINTERS_SUPPLIER)
            // Code statement
            .resolving(Identity.NAME, Player::getUsername)
            // Code statement
            .resolving(Identity.DISPLAY_NAME, Player::getDisplayName)
            // Code statement
            .resolving(Identity.LOCALE, Player::getLocale)
            // Calls a method
            .build();

    // This probably should be configurable (eg an instance field). However I(matt) am unclear
    // on what it actually does so am holding off on adding API for this until I understand.
    // Assigns a value
    private static final int DEFAULT_SEA_LEVEL = 63;

    // Code statement
    private long lastKeepAlive;
    // Code statement
    private boolean answerKeepAlive;

    // Code statement
    private final GameProfile gameProfile;
    // Code statement
    private String username;
    // Code statement
    private Component usernameComponent;
    // Code statement
    protected final PlayerConnection playerConnection;

    // Code statement
    private volatile int latency;
    // Code statement
    private Component displayName;
    // Assigns a value
    private boolean listed = true;
    // Code statement
    private int listOrder;
    // Code statement
    private PlayerSkin skin;

    // Assigns a value
    private Instance pendingInstance = null;
    // Code statement
    private int dimensionTypeId;
    // Code statement
    private GameMode gameMode;
    // Code statement
    private WorldPos deathLocation;

    /**
     * Keeps track of what chunks are sent to the client, this defines the center of the loaded area
     * in the range of {@link ServerFlag#CHUNK_VIEW_DISTANCE}
     */
    // Assigns a value
    private Vec chunksLoadedByClient = Vec.ZERO;
    // Calls a method
    private final ReentrantLock chunkQueueLock = new ReentrantLock();
    // Calls a method
    private final LongPriorityQueue chunkQueue = new LongArrayPriorityQueue(this::compareChunkDistance);
    // Assigns a value
    private boolean needsChunkPositionSync = true;
    // Assigns a value
    private float targetChunksPerTick = 9f; // Always send 9 chunks immediately
    // Assigns a value
    private float pendingChunkCount = 0f; // Number of chunks to send on the current tick (ie 0.5 means we cannot send a chunk yet, 1.5 would send a single chunk with a 0.5 remainder)
    // Assigns a value
    private int maxChunkBatchLead = 1; // Maximum number of batches to send before waiting for a reply
    // Assigns a value
    private int chunkBatchLead = 0; // Number of batches sent without a reply

    // Assigns a value
    final ChunkRange.ChunkConsumer chunkAdder = (chunkX, chunkZ) -> {
        // Load new chunks
        // Access to the current/parent object
        this.instance.loadOptionalChunk(chunkX, chunkZ).thenAccept(this::sendChunk);
    // End of a block/expression
    };
    // Assigns a value
    final ChunkRange.ChunkConsumer chunkRemover = (chunkX, chunkZ) -> {
        // Unload old chunks
        // Calls a method
        sendPacket(new UnloadChunkPacket(chunkX, chunkZ));
        // Calls a method
        EventDispatcher.call(new PlayerChunkUnloadEvent(this, chunkX, chunkZ));
    // End of a block/expression
    };

    // Calls a method
    private final AtomicInteger teleportId = new AtomicInteger();
    // Code statement
    private int receivedTeleportId;

    // Calls a method
    private final MessagePassingQueue<ClientPacket> packets = ConcurrentMessageQueues.mpscArrayQueue(ServerFlag.PLAYER_PACKET_QUEUE_SIZE);
    // Code statement
    private final boolean levelFlat;
    // Assigns a value
    private ClientSettings settings = ClientSettings.DEFAULT;
    // Code statement
    private float exp;
    // Code statement
    private int level;
    // Assigns a value
    private int portalCooldown = 0;

    // Calls a method
    protected ClickPreprocessor clickPreprocessor = new ClickPreprocessor();
    // Code statement
    protected PlayerInventory inventory;
    // Code statement
    private AbstractInventory openInventory;
    // Used internally to allow the closing of inventory within the inventory listener
    // Code statement
    private boolean didCloseInventory;

    // Code statement
    private byte heldSlot;

    // Code statement
    private Pos respawnPoint;

    // Code statement
    private int food;
    // Code statement
    private float foodSaturation;

    // Code statement
    private long startItemUseTime;
    // Code statement
    private long itemUseTime;
    // Code statement
    private @Nullable PlayerHand itemUseHand;

    // Game state (https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Protocol#Game_Event)
    // Code statement
    private boolean enableRespawnScreen;
    // Calls a method
    private final ChunkUpdateLimitChecker chunkUpdateLimitChecker = new ChunkUpdateLimitChecker(ServerFlag.PLAYER_CHUNK_UPDATE_LIMITER_HISTORY_SIZE);

    // Experience orb pickup
    // Calls a method
    protected Cooldown experiencePickupCooldown = new Cooldown(Duration.of(10, TimeUnit.SERVER_TICK));

    // Code statement
    private BelowNameTag belowNameTag;

    // Code statement
    private int permissionLevel;

    // Code statement
    private boolean reducedDebugScreenInformation;
    // Code statement
    private boolean hardcore;

    // Abilities
    // Code statement
    private boolean flying;
    // Code statement
    private boolean allowFlying;
    // Code statement
    private boolean instantBreak;
    // Assigns a value
    private float flyingSpeed = 0.05f;
    // Assigns a value
    private float fieldViewModifier = 0.1f;

    // Calls a method
    private final Map<PlayerStatistic, Integer> statisticValueMap = new Hashtable<>();

    // Calls a method
    private final PlayerInputs inputs = new PlayerInputs();

    // Resource packs
    // Type declaration (class/interface/enum/record)
    record PendingResourcePack(boolean required, ResourcePackCallback callback) {
    // End of a block/expression
    }

    // Calls a method
    private final Map<UUID, PendingResourcePack> pendingResourcePacks = new HashMap<>();
    // The future is non-null when a resource pack is in-flight, and completed when all statuses have been received.
    // Assigns a value
    private CompletableFuture<Void> resourcePackFuture = null;

    // Start of a method/block
    public Player(PlayerConnection playerConnection, GameProfile gameProfile) {
        // Access to the current/parent object
        super(EntityType.PLAYER, gameProfile.uuid());
        // Access to the current/parent object
        this.gameProfile = gameProfile;
        // Access to the current/parent object
        this.username = gameProfile.name();
        // Access to the current/parent object
        this.usernameComponent = Component.text(username);
        // Access to the current/parent object
        this.playerConnection = playerConnection;

        // Calls a method
        setRespawnPoint(Pos.ZERO);

        // Access to the current/parent object
        this.inventory = new PlayerInventory();

        // Code statement
        setCanPickupItem(true); // By default

        // Allow the server to send the next keep alive packet
        // Calls a method
        refreshAnswerKeepAlive(true);

        // Access to the current/parent object
        this.gameMode = GameMode.SURVIVAL;
        // Access to the current/parent object
        this.dimensionTypeId = DIMENSION_TYPE_REGISTRY.getId(DimensionType.OVERWORLD); // Default dimension
        // Access to the current/parent object
        this.levelFlat = true;

        // FakePlayer init its connection there
        // Calls a method
        playerConnectionInit();

        // When in configuration state no metadata updates can be sent.
        // Calls a method
        metadata.setNotifyAboutChanges(false);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void setPendingOptions(Instance pendingInstance, boolean hardcore) {
        // I(mattw) am not a big fan of this function, but somehow we need to store
        // the instance and i didn't like a record in ConnectionManager either.
        // Access to the current/parent object
        this.pendingInstance = pendingInstance;
        // Access to the current/parent object
        this.hardcore = hardcore;
    // End of a block/expression
    }

    /**
     * Used when the player is created.
     * Init the player and spawn him.
     * <p>
     * WARNING: executed in the main update thread
     * UNSAFE: Only meant to be used when a socket player connects through the server.
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public CompletableFuture<Void> UNSAFE_init() {
        // Assigns a value
        final Instance spawnInstance = this.pendingInstance;
        // Access to the current/parent object
        this.pendingInstance = null;

        // Access to the current/parent object
        this.removed = false;
        // Access to the current/parent object
        this.dimensionTypeId = DIMENSION_TYPE_REGISTRY.getId(spawnInstance.getDimensionType());

        // Assigns a value
        final JoinGamePacket joinGamePacket = new JoinGamePacket(
                // Code statement
                getEntityId(), this.hardcore, List.of(), 0,
                // Code statement
                ServerFlag.CHUNK_VIEW_DISTANCE, ServerFlag.CHUNK_VIEW_DISTANCE,
                // Code statement
                false, true, false,
                // Code statement
                dimensionTypeId, spawnInstance.getDimensionName(), 0,
                // Code statement
                gameMode, null, false, levelFlat,
                // Code statement
                deathLocation, portalCooldown, DEFAULT_SEA_LEVEL,
                // Code statement
                true);
        // Calls a method
        sendPacket(joinGamePacket);

        // Start sending inventory updates
        // Calls a method
        inventory.addViewer(this);

        // Difficulty
        // Calls a method
        sendPacket(new ServerDifficultyPacket(MinecraftServer.getDifficulty(), true));

        // Code statement
        sendPacket(new SpawnPositionPacket(
                // Creates a new object
                new WorldPos(spawnInstance.getDimensionName(), respawnPoint),
                // Code statement
                respawnPoint.yaw(), respawnPoint.pitch()
        // Code statement
        ));

        // Reenable metadata notifications as we leave the configuration state
        // Calls a method
        metadata.setNotifyAboutChanges(true);
        // Calls a method
        sendPacket(getMetadataPacket());

        // Add player to list with spawning skin
        // Assigns a value
        PlayerSkin profileSkin = null;
        // Loop: repeats a block
        for (GameProfile.Property property : gameProfile.properties()) {
            // Branch: checks a condition
            if (property.name().equals("textures")) {
                // Calls a method
                profileSkin = new PlayerSkin(property.value(), property.signature());
                // Breaks out of the loop/block
                break;
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        PlayerSkinInitEvent skinInitEvent = new PlayerSkinInitEvent(this, profileSkin);
        // Calls a method
        EventDispatcher.call(skinInitEvent);
        // Access to the current/parent object
        this.skin = skinInitEvent.getSkin();
        // FIXME: when using Geyser, this line remove the skin of the client
        // Calls a method
        PacketSendingUtils.broadcastPlayPacket(getAddPlayerToList());

        // Calls a method
        var connectionManager = MinecraftServer.getConnectionManager();
        // Loop: repeats a block
        for (var player : connectionManager.getOnlinePlayers()) {
            // Branch: checks a condition
            if (player != this) {
                // Calls a method
                sendPacket(player.getAddPlayerToList());
            // End of a block/expression
            }
        // End of a block/expression
        }

        //Teams
        // Loop: repeats a block
        for (Team team : MinecraftServer.getTeamManager().getTeams()) {
            // Calls a method
            sendPacket(team.createTeamsCreationPacket());
        // End of a block/expression
        }

        // Commands
        // Calls a method
        refreshCommands();

        // Recipes
        // Calls a method
        refreshRecipes();

        // Some client updates
        // Code statement
        sendPacket(getPropertiesPacket()); // Send default properties
        // Code statement
        triggerStatus((byte) (EntityStatuses.Player.PERMISSION_LEVEL_0 + permissionLevel)); // Set permission level
        // Code statement
        refreshHealth(); // Heal and send health packet
        // Code statement
        refreshAbilities(); // Send abilities packet

        // Returns a value to the caller
        return setInstance(spawnInstance);
    // End of a block/expression
    }

    /**
     * Moves the player to the configuration state at the end of the current tick.
     *
     * <p>The player is automatically moved to configuration upon finishing login, this method can be
     * used to move them back to configuration after entering the play state.</p>
     *
     * <p>This will result in them being removed from the current instance, player list, etc.</p>
     */
    // Start of a method/block
    public void startConfigurationPhase() {
        // Code statement
        Check.stateCondition(playerConnection.getServerState() != ConnectionState.PLAY,
                // Code statement
                "Player must be in the play state for reconfiguration.");

        // Calls a method
        MinecraftServer.getConnectionManager().transitionPlayToConfig(this);
    // End of a block/expression
    }

    /**
     * Used to initialize the player connection
     */
    // Start of a method/block
    protected void playerConnectionInit() {
        // Assigns a value
        PlayerConnection connection = playerConnection;
        // Branch: checks a condition
        if (connection != null) connection.setPlayer(this);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void update(long time) {
        // Process received packets
        // Calls a method
        interpretPacketQueue();
        // It is possible to be removed during packet processing, if thats the case exit immediately.
        // Branch: checks a condition
        if (isRemoved()) return;

        // Send any available queued chunks
        // Calls a method
        sendPendingChunks();

        // Access to the current/parent object
        super.update(time); // Super update (item pickup/fire management)

        // Experience orb pickup
        // Branch: checks a condition
        if (experiencePickupCooldown.isReady(time)) {
            // Calls a method
            experiencePickupCooldown.refreshLastUpdate(time);
            // Access to the current/parent object
            this.instance.getEntityTracker().nearbyEntities(position, expandedBoundingBox.width(),
                    // Start of a method/block
                    EntityTracker.Target.EXPERIENCE_ORBS, experienceOrb -> {
                        // Branch: checks a condition
                        if (!expandedBoundingBox.intersectEntity(position, experienceOrb)) return;
                        // Calls a method
                        final PickupExperienceEvent pickupExperienceEvent = new PickupExperienceEvent(this, experienceOrb);
                        // Start of a method/block
                        EventDispatcher.callCancellable(pickupExperienceEvent, () -> {
                            // Assigns a value
                            short experienceCount = pickupExperienceEvent.getExperienceCount(); // TODO give to player
                            // Calls a method
                            experienceOrb.remove();
                        // End of a block/expression
                        });
                    // End of a block/expression
                    });
        // End of a block/expression
        }

        // Eating animation
        // Branch: checks a condition
        if (isUsingItem()) {
            // Assigns a value
            final PlayerHand itemUseHand = this.itemUseHand;
            // Branch: checks a condition
            if (itemUseTime > 0 && getCurrentItemUseTime() >= itemUseTime) {
                // Calls a method
                final ItemStack itemStack = getItemInHand(itemUseHand);
                // Calls a method
                PlayerFinishItemUseEvent finishUseEvent = new PlayerFinishItemUseEvent(this, itemUseHand, itemStack, itemUseTime);
                // Calls a method
                EventDispatcher.call(finishUseEvent);

                // Reset client state
                // Calls a method
                triggerStatus((byte) EntityStatuses.Player.MARK_ITEM_FINISHED);

                // Reset server state
                // Assigns a value
                final boolean isOffHand = itemUseHand == PlayerHand.OFF;
                // Calls a method
                refreshActiveHand(false, isOffHand, finishUseEvent.isRiptideSpinAttack());
                // Calls a method
                clearItemUse();

                // The client has predicted that the itemstack will have its count reduced, if the server
                // has not changed the item (the default behavior) we need to refresh the slot.
                // Branch: checks a condition
                if (itemStack.equals(getItemInHand(itemUseHand))) {
                    // Calls a method
                    final int slot = isOffHand ? PlayerInventoryUtils.OFFHAND_SLOT : getHeldSlot();
                    // Calls a method
                    inventory.sendSlotRefresh(slot, itemStack);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        updatePose();

        // Tick event
        // Calls a method
        EventDispatcher.call(new PlayerTickEvent(this));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void kill() {
        // Branch: checks a condition
        if (!isDead()) {

            // Code statement
            Component deathText;
            // Code statement
            Component chatMessage;

            // get death screen text to the killed player
            // Start of a block
            {
                // Branch: checks a condition
                if (lastDamage != null) {
                    // Calls a method
                    deathText = lastDamage.buildDeathScreenText(this);
                // Alternative branch of the condition
                } else { // may happen if killed by the server without applying damage
                    // Calls a method
                    deathText = Component.text("Killed by poor programming.");
                // End of a block/expression
                }
            // End of a block/expression
            }

            // get death message to chat
            // Start of a block
            {
                // Branch: checks a condition
                if (lastDamage != null) {
                    // Calls a method
                    chatMessage = lastDamage.buildDeathMessage(this);
                // Alternative branch of the condition
                } else { // may happen if killed by the server without applying damage
                    // Calls a method
                    chatMessage = Component.text(getUsername() + " was killed by poor programming.");
                // End of a block/expression
                }
            // End of a block/expression
            }

            // Call player death event
            // Calls a method
            PlayerDeathEvent playerDeathEvent = new PlayerDeathEvent(this, deathText, chatMessage);
            // Calls a method
            EventDispatcher.call(playerDeathEvent);

            // Calls a method
            deathText = playerDeathEvent.getDeathText();
            // Calls a method
            chatMessage = playerDeathEvent.getChatMessage();

            // #buildDeathScreenText can return null, check here
            // Branch: checks a condition
            if (deathText != null) {
                // Calls a method
                sendPacket(new DeathCombatEventPacket(getEntityId(), deathText));
            // End of a block/expression
            }

            // #buildDeathMessage can return null, check here
            // Branch: checks a condition
            if (chatMessage != null) {
                // Calls a method
                Audiences.players().sendMessage(chatMessage);
            // End of a block/expression
            }

            // Set death location
            // Branch: checks a condition
            if (getInstance() != null)
                // Calls a method
                setDeathLocation(getInstance().getDimensionName(), getPosition());
        // End of a block/expression
        }
        // Access to the current/parent object
        super.kill();
    // End of a block/expression
    }

    /**
     * Respawns the player by sending a {@link RespawnPacket} to the player and teleporting him
     * to {@link #getRespawnPoint()}. It also resets fire and health.
     */
    // Start of a method/block
    public void respawn() {
        // Branch: checks a condition
        if (!isDead())
            // Returns a value to the caller
            return;

        // Calls a method
        setFireTicks(0);
        // Calls a method
        entityMeta.setOnFire(false);
        // Calls a method
        refreshHealth();

        // Code statement
        sendPacket(new RespawnPacket(dimensionTypeId, instance.getDimensionName(),
                // Code statement
                0, gameMode, gameMode, false, levelFlat,
                // Calls a method
                deathLocation, portalCooldown, DEFAULT_SEA_LEVEL, (byte) RespawnPacket.COPY_ALL));
        // Calls a method
        refreshClientStateAfterRespawn();

        // Calls a method
        PlayerRespawnEvent respawnEvent = new PlayerRespawnEvent(this);
        // Calls a method
        EventDispatcher.call(respawnEvent);
        // Calls a method
        refreshIsDead(false);
        // Calls a method
        updatePose();

        // Calls a method
        Pos respawnPosition = respawnEvent.getRespawnPosition();

        // The client unloads chunks when respawning, so resend all chunks next to spawn
        // Calls a method
        ChunkRange.chunksInRange(respawnPosition, this.effectiveViewDistance(), chunkAdder);
        // Calls a method
        chunksLoadedByClient = new Vec(respawnPosition.chunkX(), respawnPosition.chunkZ());
        // Client also needs all entities resent to them, since those are unloaded as well
        // Access to the current/parent object
        this.instance.getEntityTracker().nearbyEntitiesByChunkRange(respawnPosition, this.effectiveViewDistance(),
                // Start of a method/block
                EntityTracker.Target.ENTITIES, entity -> {
                    // Skip refreshing self with a new viewer
                    // Branch: checks a condition
                    if (!entity.getUuid().equals(getUuid()) && entity.isViewer(this)) {
                        // Calls a method
                        entity.updateNewViewer(this);
                    // End of a block/expression
                    }
                // End of a block/expression
                });
        // Calls a method
        teleport(respawnPosition).thenRun(this::refreshAfterTeleport);
    // End of a block/expression
    }

    /**
     * Sends necessary packets to synchronize player data after a {@link RespawnPacket}
     */
    // Start of a method/block
    private void refreshClientStateAfterRespawn() {
        // Calls a method
        sendPacket(new ChangeGameStatePacket(ChangeGameStatePacket.Reason.LEVEL_CHUNKS_LOAD_START, 0));
        // Calls a method
        sendPacket(new ServerDifficultyPacket(MinecraftServer.getDifficulty(), false));
        // Calls a method
        sendPacket(new UpdateHealthPacket(this.getHealth(), food, foodSaturation));
        // Calls a method
        sendPacket(new SetExperiencePacket(exp, level, 0));
        // Code statement
        triggerStatus((byte) (EntityStatuses.Player.PERMISSION_LEVEL_0 + permissionLevel)); // Set permission level
        // Calls a method
        refreshAbilities();
        // Calls a method
        sendPacket(instance.createTimePacket());
    // End of a block/expression
    }

    /**
     * Refreshes the command list for this player. This checks the
     * {@link net.minestom.server.command.builder.condition.CommandCondition}s
     * again, and any changes will be visible to the player.
     */
    // Start of a method/block
    public void refreshCommands() {
        // Calls a method
        sendPacket(MinecraftServer.getCommandManager().createDeclareCommandsPacket(this));
    // End of a block/expression
    }

    /**
     * Refreshes the recipes and recipe book for this player, testing recipe predicates again.
     */
    // Start of a method/block
    public void refreshRecipes() {
        // Calls a method
        RecipeManager recipeManager = MinecraftServer.getRecipeManager();
        // Code statement
        sendPackets(
                // Code statement
                recipeManager.getDeclareRecipesPacket(),
                // Code statement
                recipeManager.createRecipeBookResetPacket(this)
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isOnGround() {
        // Returns a value to the caller
        return onGround;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void remove(boolean permanent) {
        // Branch: checks a condition
        if (isRemoved()) return;

        // Branch: checks a condition
        if (permanent) {
            // Access to the current/parent object
            this.packets.clear();
            // Calls a method
            EventDispatcher.call(new PlayerDisconnectEvent(this));
            // Calls a method
            EventsJFR.newPlayerLeave(getUuid()).commit();
        // End of a block/expression
        }

        // Calls a method
        final AbstractInventory currentInventory = getOpenInventory();
        // Branch: checks a condition
        if (currentInventory != null) currentInventory.removeViewer(this);

        // Calls a method
        MinecraftServer.getBossBarManager().removeAllBossBars(this);
        // Advancement tabs cache
        // Start of a block
        {
            // Calls a method
            Set<AdvancementTab> advancementTabs = AdvancementTab.getTabs(this);
            // Branch: checks a condition
            if (advancementTabs != null) {
                // Loop: repeats a block
                for (AdvancementTab advancementTab : advancementTabs) {
                    // Calls a method
                    advancementTab.removeViewer(this);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Assigns a value
        final Pos position = this.position;
        // Calls a method
        final int chunkX = position.chunkX();
        // Calls a method
        final int chunkZ = position.chunkZ();
        // Clear all viewable chunks
        // Calls a method
        ChunkRange.chunksInRange(chunkX, chunkZ, this.effectiveViewDistance(), chunkRemover);
        // Calls a method
        resetChunkQueue();

        // Remove from the tab-list
        // Calls a method
        PacketSendingUtils.broadcastPlayPacket(getRemovePlayerToList());

        // Access to the current/parent object
        super.remove(permanent);
        // Prevent the player from being stuck in loading screen, or just unable to interact with the server
        // This should be considered as a bug, since the player will ultimately time out anyway.
        // Branch: checks a condition
        if (permanent && playerConnection.isOnline()) kick(REMOVE_MESSAGE);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void sendPacketToViewersAndSelf(SendablePacket packet) {
        // Calls a method
        sendPacket(packet);
        // Access to the current/parent object
        super.sendPacketToViewersAndSelf(packet);
    // End of a block/expression
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
    // Annotation for the following element
    @Override
    // Start of a method/block
    public CompletableFuture<Void> setInstance(Instance instance, Pos spawnPosition) {
        // Assigns a value
        final Instance currentInstance = this.instance;
        // Calls a method
        Check.argCondition(currentInstance == instance, "Instance should be different than the current one");
        // Branch: checks a condition
        if (SharedInstance.areLinked(currentInstance, instance) && spawnPosition.sameChunk(this.position)) {
            // The player already has the good version of all the chunks.
            // We just need to refresh his entity viewing list and add him to the instance
            // Calls a method
            spawnPlayer(instance, spawnPosition, false, false, false);
            // Returns a value to the caller
            return AsyncUtils.VOID_FUTURE;
        // End of a block/expression
        }
        // Must update the player chunks
        // Calls a method
        chunkUpdateLimitChecker.clearHistory();
        // Calls a method
        final boolean dimensionChange = currentInstance != null && !Objects.equals(currentInstance.getDimensionName(), instance.getDimensionName());
        // Assigns a value
        final Consumer<Instance> runnable = (i) -> spawnPlayer(i, spawnPosition,
                // Code statement
                currentInstance == null, dimensionChange, true);

        // Calls a method
        resetChunkQueue();

        // Ensure that surrounding chunks are loaded
        // Calls a method
        List<CompletableFuture<Chunk>> futures = new ArrayList<>();
        // Start of a method/block
        ChunkRange.chunksInRange(spawnPosition, this.effectiveViewDistance(), (chunkX, chunkZ) -> {
            // Calls a method
            final CompletableFuture<Chunk> future = instance.loadOptionalChunk(chunkX, chunkZ);
            // Branch: checks a condition
            if (!future.isDone()) futures.add(future);
        // End of a block/expression
        });
        // Branch: checks a condition
        if (futures.isEmpty()) {
            // All chunks are already loaded
            // Calls a method
            runnable.accept(instance);
            // Returns a value to the caller
            return AsyncUtils.VOID_FUTURE;
        // End of a block/expression
        }

        // One or more chunks need to be loaded
        // Calls a method
        final Thread runThread = Thread.currentThread();
        // Calls a method
        CountDownLatch latch = new CountDownLatch(1);
        // Calls a method
        Scheduler scheduler = MinecraftServer.getSchedulerManager();
        // Assigns a value
        CompletableFuture<Void> future = new CompletableFuture<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public Void join() {
                // Prevent deadlock
                // Branch: checks a condition
                if (runThread == Thread.currentThread()) {
                    // Exception handling
                    try {
                        // Calls a method
                        latch.await();
                    // Start of a method/block
                    } catch (InterruptedException e) {
                        // Throws an exception
                        throw new RuntimeException(e);
                    // End of a block/expression
                    }
                    // Calls a method
                    scheduler.process();
                    // Calls a method
                    assert isDone();
                // End of a block/expression
                }
                // Returns a value to the caller
                return super.join();
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Code statement
        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new))
                // Start of a method/block
                .thenRun(() -> {
                    // Start of a method/block
                    scheduler.scheduleNextProcess(() -> {
                        // Calls a method
                        runnable.accept(instance);
                        // Calls a method
                        future.complete(null);
                    // End of a block/expression
                    });
                    // Calls a method
                    latch.countDown();
                // End of a block/expression
                });
        // Returns a value to the caller
        return future;
    // End of a block/expression
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
    // Annotation for the following element
    @Override
    // Start of a method/block
    public CompletableFuture<Void> setInstance(Instance instance) {
        // Returns a value to the caller
        return setInstance(instance, this.instance != null ? getPosition() : getRespawnPoint());
    // End of a block/expression
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
    // Code statement
    private void spawnPlayer(Instance instance, Pos spawnPosition,
                             // Start of a method/block
                             boolean firstSpawn, boolean dimensionChange, boolean updateChunks) {
        // Branch: checks a condition
        if (!firstSpawn && !dimensionChange) {
            // Player instance changed, clear current viewable collections
            // Branch: checks a condition
            if (updateChunks)
                // Calls a method
                ChunkRange.chunksInRange(spawnPosition, this.effectiveViewDistance(), chunkRemover);
        // End of a block/expression
        }

        // Branch: checks a condition
        if (dimensionChange) sendDimension(instance.getDimensionType(), instance.getDimensionName());

        // Access to the current/parent object
        super.setInstance(instance, spawnPosition);

        // Branch: checks a condition
        if (updateChunks) {
            // Calls a method
            final int chunkX = spawnPosition.chunkX();
            // Calls a method
            final int chunkZ = spawnPosition.chunkZ();
            // Calls a method
            chunksLoadedByClient = new Vec(chunkX, chunkZ);
            // Calls a method
            chunkUpdateLimitChecker.addToHistory(getChunk());
            // Calls a method
            sendPacket(new UpdateViewPositionPacket(chunkX, chunkZ));

            // Load the nearby chunks and queue them to be sent to them
            // Calls a method
            ChunkRange.chunksInRange(spawnPosition, this.effectiveViewDistance(), chunkAdder);
            // Code statement
            sendPendingChunks(); // Send available first chunk immediately to prevent falling through the floor
        // End of a block/expression
        }

        // Code statement
        synchronizePositionAfterTeleport(spawnPosition, Vec.ZERO, RelativeFlags.NONE, true); // So the player doesn't get stuck

        // Branch: checks a condition
        if (dimensionChange) {
            // Code statement
            sendPacket(new SpawnPositionPacket(
                    // Creates a new object
                    new WorldPos(instance.getDimensionName(), spawnPosition),
                    // Code statement
                    spawnPosition.yaw(), spawnPosition.pitch()
            // Code statement
            ));
            // Calls a method
            sendPacket(instance.createInitializeWorldBorderPacket());
            // Calls a method
            sendPacket(instance.createTimePacket());
        // End of a block/expression
        }

        // Branch: checks a condition
        if (dimensionChange || firstSpawn) {
            // Access to the current/parent object
            this.inventory.update();
            // Calls a method
            sendPacket(new HeldItemChangePacket(heldSlot));

            // Tell the client to leave the loading terrain screen
            // Calls a method
            sendPacket(new ChangeGameStatePacket(ChangeGameStatePacket.Reason.LEVEL_CHUNKS_LOAD_START, 0));
        // End of a block/expression
        }

        // Calls a method
        EventDispatcher.call(new PlayerSpawnEvent(this, instance, firstSpawn));
        // Branch: checks a condition
        if (firstSpawn) EventsJFR.newPlayerJoin(getUuid()).commit();
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void onChunkBatchReceived(float newTargetChunksPerTick) {
//        logger.debug("chunk batch received player={} chunks/tick={} lead={}", username, newTargetChunksPerTick, chunkBatchLead);
        // Calls a method
        chunkBatchLead = Math.max(0, chunkBatchLead - 1);
        // Assigns a value
        newTargetChunksPerTick = newTargetChunksPerTick * ServerFlag.CHUNKS_PER_TICK_MULTIPLIER;
        // Assigns a value
        targetChunksPerTick = Float.isNaN(newTargetChunksPerTick) ? ServerFlag.MIN_CHUNKS_PER_TICK : MathUtils.clamp(
                // Code statement
                newTargetChunksPerTick, ServerFlag.MIN_CHUNKS_PER_TICK, ServerFlag.MAX_CHUNKS_PER_TICK);

        // Beyond the first batch we can preemptively send up to 10 (matching mojang server)
        // Branch: checks a condition
        if (maxChunkBatchLead == 1) maxChunkBatchLead = 10;
    // End of a block/expression
    }

    /**
     * Queues the given chunk to be sent to the player.
     *
     * @param chunk The chunk to send
     */
    // Start of a method/block
    public void sendChunk(Chunk chunk) {
        // Branch: checks a condition
        if (!chunk.isLoaded()) return;
        // Calls a method
        chunkQueueLock.lock();
        // Exception handling
        try {
            // Calls a method
            chunkQueue.enqueue(CoordConversion.chunkIndex(chunk.getChunkX(), chunk.getChunkZ()));
        // Start of a method/block
        } finally {
            // Calls a method
            chunkQueueLock.unlock();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void sendPendingChunks() {
        // If we have nothing to send or have sent the max # of batches without reply, do nothing
        // Branch: checks a condition
        if (chunkQueue.isEmpty() || chunkBatchLead >= maxChunkBatchLead) return;

        // Increment the pending chunk count by the target chunks per tick
        // Calls a method
        pendingChunkCount = Math.min(pendingChunkCount + targetChunksPerTick, ServerFlag.MAX_CHUNKS_PER_TICK);
        // Branch: checks a condition
        if (pendingChunkCount < 1) return; // Cant send anything

        // Calls a method
        chunkQueueLock.lock();
        // Exception handling
        try {
            // Assigns a value
            int batchSize = 0;
            // Calls a method
            sendPacket(new ChunkBatchStartPacket());
            // Loop: repeats a block
            while (!chunkQueue.isEmpty() && pendingChunkCount >= 1f) {
                // Calls a method
                long chunkIndex = chunkQueue.dequeueLong();
                // Calls a method
                int chunkX = CoordConversion.chunkIndexGetX(chunkIndex), chunkZ = CoordConversion.chunkIndexGetZ(chunkIndex);
                // Calls a method
                var chunk = instance.getChunk(chunkX, chunkZ);
                // Branch: checks a condition
                if (chunk == null || !chunk.isLoaded()) continue;

                // Calls a method
                sendPacket(chunk.getFullDataPacket());
                // Calls a method
                EventDispatcher.call(new PlayerChunkLoadEvent(this, chunkX, chunkZ));

                // Code statement
                pendingChunkCount -= 1f;
                // Code statement
                batchSize += 1;
            // End of a block/expression
            }
            // Calls a method
            sendPacket(new ChunkBatchFinishedPacket(batchSize));
            // Code statement
            chunkBatchLead += 1;
//            logger.debug("chunk batch sent player={} chunks={} lead={}", username, batchSize, chunkBatchLead);

            // After sending the first chunk we always send a synchronize position to the client. This is to prevent
            // cases where the client falls through the floor slightly while loading the first chunk.
            // In the vanilla server they have an anticheat which teleports the client back if they enter the floor,
            // but since Minestom does not have an anticheat this provides a similar effect.
            // Branch: checks a condition
            if (needsChunkPositionSync) {
                // Calls a method
                synchronizePositionAfterTeleport(getPosition(), Vec.ZERO, RelativeFlags.NONE, true);
                // Assigns a value
                needsChunkPositionSync = false;
            // End of a block/expression
            }
        // Start of a method/block
        } finally {
            // Calls a method
            chunkQueueLock.unlock();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void resetChunkQueue() {
        // Calls a method
        chunkQueueLock.lock();
        // Exception handling
        try {
            // Calls a method
            chunkQueue.clear();
            // Assigns a value
            needsChunkPositionSync = true;
            // Assigns a value
            targetChunksPerTick = 9f;
            // Assigns a value
            pendingChunkCount = 0f;
        // Start of a method/block
        } finally {
            // Calls a method
            chunkQueueLock.unlock();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected void updatePose() {
        // Calls a method
        EntityPose oldPose = getPose();
        // Code statement
        EntityPose newPose;

        // Figure out their expected state
        // Calls a method
        var meta = getEntityMeta();
        // Branch: checks a condition
        if (meta.isFlyingWithElytra()) {
            // Assigns a value
            newPose = EntityPose.FALL_FLYING;
        // Branch: checks a condition
        } else if (meta instanceof LivingEntityMeta livingMeta && livingMeta.getBedInWhichSleepingPosition() != null) {
            // Assigns a value
            newPose = EntityPose.SLEEPING;
        // Branch: checks a condition
        } else if (meta.isSwimming()) {
            // Assigns a value
            newPose = EntityPose.SWIMMING;
        // Branch: checks a condition
        } else if (meta instanceof LivingEntityMeta livingMeta && livingMeta.isInRiptideSpinAttack()) {
            // Assigns a value
            newPose = EntityPose.SPIN_ATTACK;
        // Branch: checks a condition
        } else if (isSneaking() && !isFlying()) {
            // Assigns a value
            newPose = EntityPose.SNEAKING;
        // Alternative branch of the condition
        } else {
            // Assigns a value
            newPose = EntityPose.STANDING;
        // End of a block/expression
        }

        // Try to put them in their expected state, or the closest if they don't fit.
        // Branch: checks a condition
        if (canFitWithBoundingBox(newPose)) {
            // Use expected state
        // Branch: checks a condition
        } else if (canFitWithBoundingBox(EntityPose.SNEAKING)) {
            // Assigns a value
            newPose = EntityPose.SNEAKING;
        // Branch: checks a condition
        } else if (canFitWithBoundingBox(EntityPose.SWIMMING)) {
            // Assigns a value
            newPose = EntityPose.SWIMMING;
        // Alternative branch of the condition
        } else {
            // If they can't fit anywhere, just use standing
            // Assigns a value
            newPose = EntityPose.STANDING;
        // End of a block/expression
        }

        // Branch: checks a condition
        if (newPose != oldPose) setPose(newPose);
    // End of a block/expression
    }

    /**
     * Returns true if the player can fit at the current position with the given {@link EntityPose}, false otherwise.
     *
     * @param pose The pose to check
     */
    // Start of a method/block
    private boolean canFitWithBoundingBox(EntityPose pose) {
        // Calls a method
        BoundingBox bb = pose == EntityPose.STANDING ? boundingBox : BoundingBox.fromPose(pose);
        // Branch: checks a condition
        if (bb == null) return false;

        // Calls a method
        var position = getPosition();
        // Calls a method
        var iter = bb.getBlocks(getPosition());
        // Loop: repeats a block
        while (iter.hasNext()) {
            // Calls a method
            var pos = iter.next();
            // Code statement
            Block block;
            // Exception handling
            try {
                // Calls a method
                block = instance.getBlock(pos.blockX(), pos.blockY(), pos.blockZ(), Block.Getter.Condition.TYPE);
            // Start of a method/block
            } catch (NullPointerException ignored) {
                // Assigns a value
                block = null;
            // End of a block/expression
            }

            // Block was in unloaded chunk, no bounding box.
            // Branch: checks a condition
            if (block == null) continue;

            // For now just ignore scaffolding. It seems to have a dynamic bounding box, or is just parsed
            // incorrectly in MinestomDataGenerator.
            // Branch: checks a condition
            if (block.id() == Block.SCAFFOLDING.id()) continue;

            // Assigns a value
            var hit = block.registry().collisionShape()
                    // Calls a method
                    .intersectBox(position.sub(pos.blockX(), pos.blockY(), pos.blockZ()), bb);
            // Branch: checks a condition
            if (hit) return false;
        // End of a block/expression
        }

        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void sendMessage(Component message) {
        // Calls a method
        Messenger.sendMessage(this, message, ChatPosition.SYSTEM_MESSAGE);
    // End of a block/expression
    }

    /**
     * Sends a plugin message to the player.
     *
     * @param channel the message channel
     * @param data    the message data
     */
    // Start of a method/block
    public void sendPluginMessage(String channel, byte[] data) {
        // Calls a method
        sendPacket(new PluginMessagePacket(channel, data));
    // End of a block/expression
    }

    /**
     * Sends a plugin message to the player.
     * <p>
     * Message encoded to UTF-8.
     *
     * @param channel the message channel
     * @param message the message
     */
    // Start of a method/block
    public void sendPluginMessage(String channel, String message) {
        // Calls a method
        sendPluginMessage(channel, message.getBytes(StandardCharsets.UTF_8));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void playSound(Sound sound) {
        // Access to the current/parent object
        this.playSound(sound, this.position.x(), this.position.y(), this.position.z());
    // End of a block/expression
    }

    // Start of a method/block
    public void playSound(Sound sound, Point point) {
        // Calls a method
        sendPacket(AdventurePacketConvertor.createSoundPacket(sound, point.x(), point.y(), point.z()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void playSound(Sound sound, double x, double y, double z) {
        // Calls a method
        sendPacket(AdventurePacketConvertor.createSoundPacket(sound, x, y, z));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void playSound(Sound sound, Sound.Emitter emitter) {
        // Code statement
        final ServerPacket packet;
        // Branch: checks a condition
        if (emitter == Sound.Emitter.self()) {
            // Calls a method
            packet = AdventurePacketConvertor.createSoundPacket(sound, this);
        // Alternative branch of the condition
        } else {
            // Calls a method
            packet = AdventurePacketConvertor.createSoundPacket(sound, emitter);
        // End of a block/expression
        }
        // Calls a method
        sendPacket(packet);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void stopSound(SoundStop stop) {
        // Calls a method
        sendPacket(AdventurePacketConvertor.createSoundStopPacket(stop));
    // End of a block/expression
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
    // Start of a method/block
    public void playEffect(WorldEvent worldEvent, int x, int y, int z, int data, boolean disableRelativeVolume) {
        // Calls a method
        sendPacket(new WorldEventPacket(worldEvent.id(), new Vec(x, y, z), data, disableRelativeVolume));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void sendPlayerListHeaderAndFooter(Component header, Component footer) {
        // Calls a method
        sendPacket(new PlayerListHeaderAndFooterPacket(header, footer));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T> void sendTitlePart(TitlePart<T> part, T value) {
        // Calls a method
        sendPacket(AdventurePacketConvertor.createTitlePartPacket(part, value));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void sendActionBar(Component message) {
        // Calls a method
        sendPacket(new ActionBarPacket(message));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void resetTitle() {
        // Calls a method
        sendPacket(new ClearTitlesPacket(true));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void clearTitle() {
        // Calls a method
        sendPacket(new ClearTitlesPacket(false));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void showBossBar(BossBar bar) {
        // Calls a method
        MinecraftServer.getBossBarManager().addBossBar(this, bar);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void hideBossBar(BossBar bar) {
        // Calls a method
        MinecraftServer.getBossBarManager().removeBossBar(this, bar);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void openBook(Book book) {
        // Close the open inventory if there is one because the book will replace it.
        // Branch: checks a condition
        if (getOpenInventory() != null) {
            // Calls a method
            closeInventory();
        // End of a block/expression
        }

        // TODO: when adventure updates, delete this
        // Calls a method
        String title = PlainTextComponentSerializer.plainText().serialize(book.title());
        // Calls a method
        String author = PlainTextComponentSerializer.plainText().serialize(book.author());
        // Assigns a value
        final ItemStack writtenBook = ItemStack.builder(Material.WRITTEN_BOOK)
                // Code statement
                .set(DataComponents.WRITTEN_BOOK_CONTENT, new WrittenBookContent(title, author, 0, book.pages(), false))
                // Calls a method
                .build();

        // Set book in offhand
        // Calls a method
        sendPacket(new SetSlotPacket((byte) 0, 0, (short) PlayerInventoryUtils.OFFHAND_SLOT, writtenBook));
        // Open the book
        // Calls a method
        sendPacket(new OpenBookPacket(PlayerHand.OFF));
        // Restore the item in offhand
        // Calls a method
        sendPacket(new SetSlotPacket((byte) 0, 0, (short) PlayerInventoryUtils.OFFHAND_SLOT, getItemInOffHand()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void showDialog(DialogLike dialog) {
        // Calls a method
        sendPacket(new ShowDialogPacket(Dialog.unwrap(dialog)));
    // End of a block/expression
    }

    // TODO(1.21.6): Implementation for pending adventure method in 4.24.0.
    // Start of a method/block
    public void closeDialog() {
        // Calls a method
        sendPacket(new ClearDialogPacket());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setHealth(float health) {
        // Calls a method
        sendPacket(new UpdateHealthPacket(health, food, foodSaturation));
        // Access to the current/parent object
        super.setHealth(health);
    // End of a block/expression
    }

    /**
     * Gets the entity meta for the player.
     *
     * <p>Note that this method will throw an exception if the player's entity type has
     * been changed with {@link #switchEntityType(EntityType)}. It is wise to check
     * {@link #getEntityType()} first.</p>
     */
    // Start of a method/block
    public PlayerMeta getPlayerMeta() {
        // Returns a value to the caller
        return (PlayerMeta) super.getEntityMeta();
    // End of a block/expression
    }

    /**
     * Gets the player additional hearts.
     *
     * <p>Note that this function is uncallable if the player has their entity type switched
     * with {@link #switchEntityType(EntityType)}.</p>
     *
     * @return the player additional hearts
     */
    // Start of a method/block
    public float getAdditionalHearts() {
        // Returns a value to the caller
        return getPlayerMeta().getAdditionalHearts();
    // End of a block/expression
    }

    /**
     * Changes the amount of additional hearts shown.
     *
     * <p>Note that this function is uncallable if the player has their entity type switched
     * with {@link #switchEntityType(EntityType)}.</p>
     *
     * @param additionalHearts the count of additional hearts
     */
    // Start of a method/block
    public void setAdditionalHearts(float additionalHearts) {
        // Calls a method
        getPlayerMeta().setAdditionalHearts(additionalHearts);
    // End of a block/expression
    }

    /**
     * Gets the player food.
     *
     * @return the player food
     */
    // Start of a method/block
    public int getFood() {
        // Returns a value to the caller
        return food;
    // End of a block/expression
    }

    /**
     * Sets and refresh client food bar.
     *
     * @param food the new food value
     * @throws IllegalArgumentException if {@code food} is not between 0 and 20
     */
    // Start of a method/block
    public void setFood(int food) {
        // Code statement
        Check.argCondition(!MathUtils.isBetween(food, 0, 20),
                // Code statement
                "Food has to be between 0 and 20");
        // Access to the current/parent object
        this.food = food;
        // Calls a method
        sendPacket(new UpdateHealthPacket(getHealth(), food, foodSaturation));
    // End of a block/expression
    }

    // Start of a method/block
    public float getFoodSaturation() {
        // Returns a value to the caller
        return foodSaturation;
    // End of a block/expression
    }

    /**
     * Sets and refresh client food saturation.
     *
     * @param foodSaturation the food saturation
     * @throws IllegalArgumentException if {@code foodSaturation} is not between 0 and 20
     */
    // Start of a method/block
    public void setFoodSaturation(float foodSaturation) {
        // Code statement
        Check.argCondition(!MathUtils.isBetween(foodSaturation, 0, 20),
                // Code statement
                "Food saturation has to be between 0 and 20");
        // Access to the current/parent object
        this.foodSaturation = foodSaturation;
        // Calls a method
        sendPacket(new UpdateHealthPacket(getHealth(), food, foodSaturation));
    // End of a block/expression
    }

    /**
     * Gets if the player is eating.
     *
     * @return true if the player is eating, false otherwise
     */
    // Start of a method/block
    public boolean isEating() {
        // Branch: checks a condition
        if (!isUsingItem()) return false;
        // Calls a method
        final ItemStack itemStack = getItemInHand(itemUseHand);
        // Returns a value to the caller
        return itemStack.has(DataComponents.FOOD) || itemStack.material() == Material.POTION;
    // End of a block/expression
    }

    /**
     * Gets if the player is using an item.
     *
     * @return true if the player is using an item, false otherwise
     */
    // Start of a method/block
    public boolean isUsingItem() {
        // Returns a value to the caller
        return itemUseHand != null;
    // End of a block/expression
    }

    /**
     * Gets the hand which the player is using an item from.
     *
     * @return the item use hand, null if none
     */
    // Start of a method/block
    public @Nullable PlayerHand getItemUseHand() {
        // Returns a value to the caller
        return itemUseHand;
    // End of a block/expression
    }

    /**
     * Gets the amount of ticks which have passed since the player started using an item.
     *
     * @return the amount of ticks which have passed, or zero if the player is not using an item
     */
    // Start of a method/block
    public long getCurrentItemUseTime() {
        // Branch: checks a condition
        if (!isUsingItem()) return 0;
        // Returns a value to the caller
        return getAliveTicks() - startItemUseTime;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public double getEyeHeight() {
        // Returns a value to the caller
        return switch (getPose()) {
            // Multiple branching (switch/case)
            case SLEEPING -> 0.2;
            // Multiple branching (switch/case)
            case FALL_FLYING, SWIMMING, SPIN_ATTACK -> 0.4;
            // Multiple branching (switch/case)
            case SNEAKING -> 1.27;
            // Multiple branching (switch/case)
            default -> 1.62;
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Gets the player display name in the tab-list.
     *
     * @return the player display name, null means that {@link #getUsername()} is displayed
     */
    // Start of a method/block
    public @Nullable Component getDisplayName() {
        // Returns a value to the caller
        return displayName;
    // End of a block/expression
    }

    /**
     * Changes the player display name in the tab-list.
     * <p>
     * Sets to null to show the player username.
     *
     * @param displayName the display name, null to display the username
     */
    // Start of a method/block
    public void setDisplayName(@Nullable Component displayName) {
        // Access to the current/parent object
        this.displayName = displayName;
        // Branch: checks a condition
        if (isActive()) {
            // Calls a method
            PacketSendingUtils.broadcastPlayPacket(new PlayerInfoUpdatePacket(PlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME, infoEntry()));
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets whether the player is listed in the tab-list
     *
     * @return true if the player is being displayed in the tab-list, false if they aren't
     */
    // Start of a method/block
    public boolean isListed() {
        // Returns a value to the caller
        return listed;
    // End of a block/expression
    }

    /**
     * Changes whether the player should be displayed in the tab-list.
     *
     * @param listed whether the player should be displayed in the tab-list
     */
    // Start of a method/block
    public void setListed(boolean listed) {
        // Access to the current/parent object
        this.listed = listed;
        // Branch: checks a condition
        if (isActive()) {
            // Calls a method
            PacketSendingUtils.broadcastPlayPacket(new PlayerInfoUpdatePacket(PlayerInfoUpdatePacket.Action.UPDATE_LISTED, infoEntry()));
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets the tab-list listing order of the player.
     * <p>
     * See {@link Player#setListOrder(int)} for further documentation.
     *
     * @return the order the player has for the tab-list
     */
    // Start of a method/block
    public int getListOrder() {
        // Returns a value to the caller
        return listOrder;
    // End of a block/expression
    }

    /**
     * Sets the tab-list listing priority of the player. This is also affected by other factors such as: whether the
     * player is spectating, their team name, and their username.
     * <p>
     * More information can be found <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#player-info:player-actions">here</a>.
     *
     * @param listOrder the order in which the player should be displayed in the tab-list. A higher number means
     *                  the player will appear higher in the tab-list.
     */
    // Start of a method/block
    public void setListOrder(int listOrder) {
        // Access to the current/parent object
        this.listOrder = listOrder;
        // Branch: checks a condition
        if (isActive()) {
            // Calls a method
            PacketSendingUtils.broadcastPlayPacket(new PlayerInfoUpdatePacket(PlayerInfoUpdatePacket.Action.UPDATE_LIST_ORDER, infoEntry()));
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets the player skin.
     *
     * @return the player skin object,
     * null means that the player has his {@link #getUuid()} default skin
     */
    // Start of a method/block
    public @Nullable PlayerSkin getSkin() {
        // Returns a value to the caller
        return skin;
    // End of a block/expression
    }

    /**
     * Changes the player skin.
     * <p>
     * This does remove the player for all viewers to spawn it again with the correct new skin.
     *
     * @param skin the player skin, null to reset it to his {@link #getUuid()} default skin
     * @see PlayerSkinInitEvent if you want to apply the skin at connection
     */
    // Start of a method/block
    public synchronized void setSkin(@Nullable PlayerSkin skin) {
        // Access to the current/parent object
        this.skin = skin;
        // Branch: checks a condition
        if (instance == null)
            // Returns a value to the caller
            return;

        // Calls a method
        DestroyEntitiesPacket destroyEntitiesPacket = new DestroyEntitiesPacket(getEntityId());

        // Calls a method
        final PlayerInfoRemovePacket removePlayerPacket = getRemovePlayerToList();
        // Calls a method
        final PlayerInfoUpdatePacket addPlayerPacket = getAddPlayerToList();

        // Assigns a value
        final RespawnPacket respawnPacket = new RespawnPacket(dimensionTypeId,
                // Code statement
                instance.getDimensionName(), 0, gameMode, gameMode,
                // Code statement
                false, levelFlat, deathLocation, portalCooldown,
                // Calls a method
                DEFAULT_SEA_LEVEL, (byte) RespawnPacket.COPY_ALL);

        // Calls a method
        sendPacket(removePlayerPacket);
        // Calls a method
        sendPacket(destroyEntitiesPacket);
        // Calls a method
        sendPacket(addPlayerPacket);
        // Calls a method
        sendPacket(respawnPacket);
        // Calls a method
        refreshClientStateAfterRespawn();

        // Start of a block
        {
            // Remove player
            // Calls a method
            PacketSendingUtils.broadcastPlayPacket(removePlayerPacket);
            // Calls a method
            sendPacketToViewers(destroyEntitiesPacket);

            // Show player again
            // Calls a method
            PacketSendingUtils.broadcastPlayPacket(addPlayerPacket);
            // Calls a method
            getViewers().forEach(player -> showPlayer(player.getPlayerConnection()));
        // End of a block/expression
        }

        // Calls a method
        getInventory().update();
        // Calls a method
        teleport(getPosition());
    // End of a block/expression
    }

    // Start of a method/block
    public void setDeathLocation(Pos position) {
        // Calls a method
        setDeathLocation(getInstance().getDimensionName(), position);
    // End of a block/expression
    }

    // Start of a method/block
    public void setDeathLocation(String dimension, Pos position) {
        // Access to the current/parent object
        this.deathLocation = new WorldPos(dimension, position);
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable WorldPos getDeathLocation() {
        // Returns a value to the caller
        return this.deathLocation;
    // End of a block/expression
    }

    /**
     * Gets if the player has the respawn screen enabled or disabled.
     *
     * @return true if the player has the respawn screen, false if he didn't
     */
    // Start of a method/block
    public boolean isEnableRespawnScreen() {
        // Returns a value to the caller
        return enableRespawnScreen;
    // End of a block/expression
    }

    /**
     * Enables or disable the respawn screen.
     *
     * @param enableRespawnScreen true to enable the respawn screen, false to disable it
     */
    // Start of a method/block
    public void setEnableRespawnScreen(boolean enableRespawnScreen) {
        // Access to the current/parent object
        this.enableRespawnScreen = enableRespawnScreen;
        // Calls a method
        sendPacket(new ChangeGameStatePacket(ChangeGameStatePacket.Reason.ENABLE_RESPAWN_SCREEN, enableRespawnScreen ? 0 : 1));
    // End of a block/expression
    }

    /**
     * Gets the player's name as a component. This will either return the display name
     * (if set) or a component holding the username.
     *
     * @return the name
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public Component getName() {
        // Returns a value to the caller
        return Objects.requireNonNullElse(displayName, usernameComponent);
    // End of a block/expression
    }

    /**
     * Gets the player's username.
     *
     * @return the player's username
     */
    // Start of a method/block
    public String getUsername() {
        // Returns a value to the caller
        return username;
    // End of a block/expression
    }

    /**
     * Calls an {@link ItemDropEvent} with a specified item.
     * <p>
     * Returns false if {@code item} is air.
     *
     * @param item the item to drop
     * @return true if player can drop the item (event not cancelled), false otherwise
     */
    // Start of a method/block
    public boolean dropItem(ItemStack item) {
        // Branch: checks a condition
        if (item.isAir()) return false;
        // Calls a method
        ItemDropEvent itemDropEvent = new ItemDropEvent(this, item);
        // Calls a method
        EventDispatcher.call(itemDropEvent);
        // Returns a value to the caller
        return !itemDropEvent.isCancelled();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void sendResourcePacks(ResourcePackRequest request) {
        // Branch: checks a condition
        if (request.replace()) clearResourcePacks();

        // Loop: repeats a block
        for (final ResourcePackInfo pack : request.packs()) {
            // Calls a method
            sendPacket(new ResourcePackPushPacket(pack, request.required(), request.prompt()));
            // Calls a method
            pendingResourcePacks.put(pack.id(), new PendingResourcePack(request.required(), request.callback()));
            // Branch: checks a condition
            if (resourcePackFuture == null) {
                // Calls a method
                resourcePackFuture = new CompletableFuture<>();
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void removeResourcePacks(UUID id, UUID... others) {
        // Calls a method
        sendPacket(new ResourcePackPopPacket(id));
        // Loop: repeats a block
        for (var other : others) {
            // Calls a method
            sendPacket(new ResourcePackPopPacket(other));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void clearResourcePacks() {
        // Calls a method
        sendPacket(new ResourcePackPopPacket(null));
    // End of a block/expression
    }

    /**
     * If there are resource packs in-flight, a future is returned which will be completed when
     * all resource packs have been responded to by the client. Otherwise null is returned.
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public @Nullable CompletableFuture<Void> getResourcePackFuture() {
        // Returns a value to the caller
        return resourcePackFuture;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void onResourcePackStatus(UUID id, ResourcePackStatus status) {
        // Calls a method
        var pendingPack = pendingResourcePacks.get(id);
        // Branch: checks a condition
        if (pendingPack == null) return;

        // Calls a method
        pendingPack.callback().packEventReceived(id, status, this);
        // Branch: checks a condition
        if (!status.intermediate()) {
            // Remove the callback and finish the future if relevant
            // Calls a method
            pendingResourcePacks.remove(id);

            // If the resource pack is required and failed to load, bye bye!
            // Branch: checks a condition
            if (pendingPack.required() && status != ResourcePackStatus.SUCCESSFULLY_LOADED) {
                // Calls a method
                kick(MISSING_REQUIRED_RESOURCE_PACK);
            // End of a block/expression
            }

            // Branch: checks a condition
            if (pendingResourcePacks.isEmpty() && resourcePackFuture != null) {
                // Calls a method
                resourcePackFuture.complete(null);
                // Assigns a value
                resourcePackFuture = null;
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Rotates the player to face {@code targetPosition}.
     *
     * @param facePoint      the point from where the player should aim
     * @param targetPosition the target position to face
     */
    // Start of a method/block
    public void facePosition(FacePoint facePoint, Point targetPosition) {
        // Calls a method
        facePosition(facePoint, targetPosition, null, null);
    // End of a block/expression
    }

    /**
     * Rotates the player to face {@code entity}.
     *
     * @param facePoint   the point from where the player should aim
     * @param entity      the entity to face
     * @param targetPoint the point to aim at {@code entity} position
     */
    // Start of a method/block
    public void facePosition(FacePoint facePoint, Entity entity, FacePoint targetPoint) {
        // Calls a method
        facePosition(facePoint, entity.getPosition(), entity, targetPoint);
    // End of a block/expression
    }

    // Code statement
    private void facePosition(FacePoint facePoint, Point targetPosition,
                              // Annotation for the following element
                              @Nullable Entity entity, @Nullable FacePoint targetPoint) {
        // Calls a method
        final int entityId = entity != null ? entity.getEntityId() : 0;
        // Code statement
        sendPacket(new FacePlayerPacket(
                // Code statement
                facePoint == FacePoint.EYE ?
                        // Code statement
                        FacePlayerPacket.FacePosition.EYES : FacePlayerPacket.FacePosition.FEET, targetPosition,
                // Code statement
                entityId,
                // Code statement
                targetPoint == FacePoint.EYE ?
                        // Code statement
                        FacePlayerPacket.FacePosition.EYES : FacePlayerPacket.FacePosition.FEET));
    // End of a block/expression
    }

    /**
     * Sets the camera at {@code entity} eyes.
     *
     * @param entity the entity to spectate
     */
    // Start of a method/block
    public void spectate(Entity entity) {
        // Calls a method
        sendPacket(new CameraPacket(entity.getEntityId()));
    // End of a block/expression
    }

    /**
     * Resets the camera at the player.
     */
    // Start of a method/block
    public void stopSpectating() {
        // Calls a method
        spectate(this);
    // End of a block/expression
    }

    /**
     * Used to retrieve the default spawn point.
     * <p>
     * Can be altered by the {@link PlayerRespawnEvent#setRespawnPosition(Pos)}.
     *
     * @return a copy of the default respawn point
     */
    // Start of a method/block
    public Pos getRespawnPoint() {
        // Returns a value to the caller
        return respawnPoint;
    // End of a block/expression
    }

    /**
     * Changes the default spawn point.
     *
     * @param respawnPoint the player respawn point
     */
    // Start of a method/block
    public void setRespawnPoint(Pos respawnPoint) {
        // Access to the current/parent object
        this.respawnPoint = respawnPoint;
    // End of a block/expression
    }

    /**
     * Called after the player teleportation to refresh his position
     * and send data to his new viewers.
     */
    // Start of a method/block
    protected void refreshAfterTeleport() {
        // Calls a method
        sendPacketsToViewers(getSpawnPacket());

        // Update for viewers
        // Calls a method
        sendPacketToViewersAndSelf(getVelocityPacket());
        // Calls a method
        sendPacketToViewersAndSelf(getMetadataPacket());
        // Calls a method
        sendPacketToViewersAndSelf(getPropertiesPacket());
        // Calls a method
        sendPacketToViewersAndSelf(getEquipmentsPacket());

        // Calls a method
        getInventory().update();
    // End of a block/expression
    }

    /**
     * Sets the player food and health values to their maximum.
     */
    // Start of a method/block
    protected void refreshHealth() {
        // Access to the current/parent object
        this.food = 20;
        // Access to the current/parent object
        this.foodSaturation = 5;
        // refresh health and send health packet
        // Calls a method
        heal();
    // End of a block/expression
    }

    /**
     * Gets the percentage displayed in the experience bar.
     *
     * @return the exp percentage 0-1
     */
    // Start of a method/block
    public float getExp() {
        // Returns a value to the caller
        return exp;
    // End of a block/expression
    }

    /**
     * Used to change the percentage experience bar.
     * This cannot change the displayed level, see {@link #setLevel(int)}.
     *
     * @param exp a percentage between 0 and 1
     * @throws IllegalArgumentException if {@code exp} is not between 0 and 1
     */
    // Start of a method/block
    public void setExp(float exp) {
        // Calls a method
        Check.argCondition(!MathUtils.isBetween(exp, 0, 1), "Exp should be between 0 and 1");
        // Access to the current/parent object
        this.exp = exp;
        // Calls a method
        sendPacket(new SetExperiencePacket(exp, level, 0));
    // End of a block/expression
    }

    /**
     * Gets the level of the player displayed in the experience bar.
     *
     * @return the player level
     */
    // Start of a method/block
    public int getLevel() {
        // Returns a value to the caller
        return level;
    // End of a block/expression
    }

    /**
     * Used to change the level of the player
     * This cannot change the displayed percentage bar see {@link #setExp(float)}
     *
     * @param level the new level of the player
     */
    // Start of a method/block
    public void setLevel(int level) {
        // Access to the current/parent object
        this.level = level;
        // Calls a method
        sendPacket(new SetExperiencePacket(exp, level, 0));
    // End of a block/expression
    }

    // Start of a method/block
    public int getPortalCooldown() {
        // Returns a value to the caller
        return portalCooldown;
    // End of a block/expression
    }

    // Start of a method/block
    public void setPortalCooldown(int portalCooldown) {
        // Access to the current/parent object
        this.portalCooldown = portalCooldown;
    // End of a block/expression
    }

    /**
     * Gets the player connection.
     * <p>
     * Used to send packets and get stuff related to the connection.
     *
     * @return the player connection
     */
    // Start of a method/block
    public PlayerConnection getPlayerConnection() {
        // Returns a value to the caller
        return playerConnection;
    // End of a block/expression
    }

    /**
     * Shortcut for {@link PlayerConnection#sendPacket(SendablePacket)}.
     *
     * @param packet the packet to send
     */
    // Start of a method/block
    public void sendPacket(SendablePacket packet) {
        // Access to the current/parent object
        this.playerConnection.sendPacket(packet);
    // End of a block/expression
    }

    // Start of a method/block
    public void sendPackets(SendablePacket... packets) {
        // Access to the current/parent object
        this.playerConnection.sendPackets(packets);
    // End of a block/expression
    }

    // Start of a method/block
    public void sendPackets(Collection<SendablePacket> packets) {
        // Access to the current/parent object
        this.playerConnection.sendPackets(packets);
    // End of a block/expression
    }

    /**
     * Gets if the player is online or not.
     *
     * @return true if the player is online, false otherwise
     */
    // Start of a method/block
    public boolean isOnline() {
        // Returns a value to the caller
        return playerConnection.isOnline();
    // End of a block/expression
    }

    /**
     * Gets the player settings.
     *
     * @return the player settings
     */
    // Start of a method/block
    public ClientSettings getSettings() {
        // Returns a value to the caller
        return settings;
    // End of a block/expression
    }

    /**
     * Changes the player settings internally.
     * <p>
     * WARNING: the player will not be noticed by this change, probably unsafe.
     */
    // Start of a method/block
    public void refreshSettings(ClientSettings settings) {
        // Assigns a value
        final ClientSettings previous = this.settings;
        // Access to the current/parent object
        this.settings = settings;
        // Calls a method
        boolean isInPlayState = getPlayerConnection().getClientState() == ConnectionState.PLAY;
        // Calls a method
        PlayerMeta playerMeta = getPlayerMeta();
        // Branch: checks a condition
        if (isInPlayState) playerMeta.setNotifyAboutChanges(false);
        // Calls a method
        playerMeta.setDisplayedSkinParts(settings.displayedSkinParts());
        // Calls a method
        playerMeta.setMainHand(settings.mainHand());
        // Branch: checks a condition
        if (isInPlayState) playerMeta.setNotifyAboutChanges(true);

        // Calls a method
        final byte previousViewDistance = previous.viewDistance();
        // Calls a method
        final byte newViewDistance = settings.viewDistance();
        // Check to see if we're in an instance first, as this method is called when first logging in since the client sends the Settings packet during configuration
        // Branch: checks a condition
        if (instance != null) {
            // Load/unload chunks if necessary due to view distance changes
            // Branch: checks a condition
            if (previousViewDistance < newViewDistance) {
                // View distance expanded, send chunks
                // Start of a method/block
                ChunkRange.chunksInRange(position.chunkX(), position.chunkZ(), newViewDistance, (chunkX, chunkZ) -> {
                    // Branch: checks a condition
                    if (Math.abs(chunkX - position.chunkX()) > previousViewDistance || Math.abs(chunkZ - position.chunkZ()) > previousViewDistance) {
                        // Calls a method
                        chunkAdder.accept(chunkX, chunkZ);
                    // End of a block/expression
                    }
                // End of a block/expression
                });
            // Branch: checks a condition
            } else if (previousViewDistance > newViewDistance) {
                // View distance shrunk, unload chunks
                // Start of a method/block
                ChunkRange.chunksInRange(position.chunkX(), position.chunkZ(), previousViewDistance, (chunkX, chunkZ) -> {
                    // Branch: checks a condition
                    if (Math.abs(chunkX - position.chunkX()) > newViewDistance || Math.abs(chunkZ - position.chunkZ()) > newViewDistance) {
                        // Calls a method
                        chunkRemover.accept(chunkX, chunkZ);
                    // End of a block/expression
                    }
                // End of a block/expression
                });
            // End of a block/expression
            }
            // Else previous and current are equal, do nothing
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets the player dimension.
     *
     * @return the player current dimension
     */
    // Start of a method/block
    public DimensionType getDimensionType() {
        // Returns a value to the caller
        return DIMENSION_TYPE_REGISTRY.get(dimensionTypeId);
    // End of a block/expression
    }

    // Start of a method/block
    public PlayerInventory getInventory() {
        // Returns a value to the caller
        return inventory;
    // End of a block/expression
    }

    /**
     * Used to get the player latency,
     * computed by seeing how long it takes the client to answer the {@link KeepAlivePacket} packet.
     *
     * @return the player latency
     */
    // Start of a method/block
    public int getLatency() {
        // Returns a value to the caller
        return latency;
    // End of a block/expression
    }

    /**
     * Gets the player {@link GameMode}.
     *
     * @return the player current gamemode
     */
    // Start of a method/block
    public GameMode getGameMode() {
        // Returns a value to the caller
        return gameMode;
    // End of a block/expression
    }

    /**
     * Changes the player {@link GameMode}
     *
     * @param gameMode the new player GameMode
     * @return true if the gamemode was changed successfully, false otherwise (cancelled by event)
     */
    // Start of a method/block
    public boolean setGameMode(GameMode gameMode) {
        // Calls a method
        PlayerGameModeChangeEvent playerGameModeChangeEvent = new PlayerGameModeChangeEvent(this, gameMode);
        // Calls a method
        EventDispatcher.call(playerGameModeChangeEvent);
        // Branch: checks a condition
        if (playerGameModeChangeEvent.isCancelled()) {
            // Abort
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }

        // Calls a method
        gameMode = playerGameModeChangeEvent.getNewGameMode();

        // Access to the current/parent object
        this.gameMode = gameMode;
        // Condition to prevent sending the packets before spawning the player
        // Branch: checks a condition
        if (isActive()) {
            // Calls a method
            sendPacket(new ChangeGameStatePacket(ChangeGameStatePacket.Reason.CHANGE_GAMEMODE, gameMode.ordinal()));
            // Calls a method
            PacketSendingUtils.broadcastPlayPacket(new PlayerInfoUpdatePacket(PlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE, infoEntry()));
        // End of a block/expression
        }

        // The client updates their abilities based on the GameMode as follows
        // Access to the current/parent object
        this.allowFlying = gameMode.allowFlying();
        // Access to the current/parent object
        this.instantBreak = gameMode.instantBreak();
        // Access to the current/parent object
        this.invulnerable = gameMode.invulnerable();
        // Spectator automatically enables flying
        // If new game mode cannot fly, disable it
        // Branch: checks a condition
        if (gameMode == GameMode.SPECTATOR || !gameMode.allowFlying()) {
            // Branch: checks a condition
            if (isActive()) {
                // Calls a method
                refreshFlying(gameMode.allowFlying());
            // Alternative branch of the condition
            } else {
                // Access to the current/parent object
                this.flying = gameMode.allowFlying();
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Make sure that the player is in the PLAY state and synchronize their flight speed.
        // Branch: checks a condition
        if (isActive()) {
            // Calls a method
            refreshAbilities();
            // Calls a method
            updateCollisions();
        // End of a block/expression
        }

        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    /**
     * Changes the dimension of the player.
     * Mostly unsafe since it requires sending chunks after.
     *
     * @param dimensionType the new player dimension
     */
    // Start of a method/block
    protected void sendDimension(RegistryKey<DimensionType> dimensionType, String dimensionName) {
        // Code statement
        Check.argCondition(instance.getDimensionName().equals(dimensionName),
                // Code statement
                "The dimension needs to be different than the current one!");
        // Access to the current/parent object
        this.dimensionTypeId = DIMENSION_TYPE_REGISTRY.getId(dimensionType);
        // Code statement
        sendPacket(new RespawnPacket(dimensionTypeId, dimensionName,
                // Code statement
                0, gameMode, gameMode, false, levelFlat,
                // Calls a method
                deathLocation, portalCooldown, DEFAULT_SEA_LEVEL, (byte) RespawnPacket.COPY_ALL));
        // Calls a method
        refreshClientStateAfterRespawn();
    // End of a block/expression
    }

    /**
     * Kicks the player with a reason.
     *
     * @param component the reason
     */
    // Start of a method/block
    public void kick(Component component) {
        // Access to the current/parent object
        this.getPlayerConnection().kick(component);
    // End of a block/expression
    }

    /**
     * Kicks the player with a reason.
     *
     * @param message the kick reason
     */
    // Start of a method/block
    public void kick(String message) {
        // Access to the current/parent object
        this.kick(Component.text(message));
    // End of a block/expression
    }

    /**
     * Changes the current held slot for the player.
     *
     * @param slot the slot that the player has to held
     * @throws IllegalArgumentException if {@code slot} is not between 0 and 8
     */
    // Start of a method/block
    public void setHeldItemSlot(byte slot) {
        // Calls a method
        Check.argCondition(!MathUtils.isBetween(slot, 0, 8), "Slot has to be between 0 and 8");
        // Calls a method
        refreshHeldSlot(slot);
        // Calls a method
        sendPacket(new HeldItemChangePacket(slot));
    // End of a block/expression
    }

    /**
     * Gets the player held slot (0-8).
     *
     * @return the current held slot for the player
     */
    // Start of a method/block
    public byte getHeldSlot() {
        // Returns a value to the caller
        return heldSlot;
    // End of a block/expression
    }

    /**
     * Changes the tag below the name.
     *
     * @param belowNameTag The new below name tag
     */
    // Start of a method/block
    public void setBelowNameTag(BelowNameTag belowNameTag) {
        // Branch: checks a condition
        if (this.belowNameTag == belowNameTag) return;

        // Branch: checks a condition
        if (this.belowNameTag != null) {
            // Access to the current/parent object
            this.belowNameTag.removeViewer(this);
        // End of a block/expression
        }

        // Access to the current/parent object
        this.belowNameTag = belowNameTag;
    // End of a block/expression
    }

    // Start of a method/block
    public ClickPreprocessor getClickPreprocessor() {
        // Returns a value to the caller
        return clickPreprocessor;
    // End of a block/expression
    }

    /**
     * Gets the player open inventory.
     *
     * @return the currently open inventory, null if there is not (player inventory is not detected)
     */
    // Start of a method/block
    public @Nullable AbstractInventory getOpenInventory() {
        // Returns a value to the caller
        return openInventory;
    // End of a block/expression
    }

    /**
     * Opens the specified Inventory, close the previous inventory if existing.
     *
     * @param inventory the inventory to open
     * @return true if the inventory has been opened/sent to the player, false otherwise (cancelled by event)
     */
    // Start of a method/block
    public boolean openInventory(Inventory inventory) {
        // Calls a method
        InventoryOpenEvent inventoryOpenEvent = new InventoryOpenEvent(inventory, this);

        // Start of a method/block
        EventDispatcher.callCancellable(inventoryOpenEvent, () -> {
            // Calls a method
            AbstractInventory openInventory = getOpenInventory();
            // Branch: checks a condition
            if (openInventory != null) {
                // Calls a method
                openInventory.removeViewer(this);
            // End of a block/expression
            }

            // Calls a method
            AbstractInventory newInventory = inventoryOpenEvent.getInventory();

            // Calls a method
            newInventory.addViewer(this);
            // Access to the current/parent object
            this.openInventory = newInventory;
        // End of a block/expression
        });
        // Returns a value to the caller
        return !inventoryOpenEvent.isCancelled();
    // End of a block/expression
    }

    /**
     * Closes the current inventory if there is any.
     * It closes the player inventory (when opened) if {@link #getOpenInventory()} returns null.
     */
    // Start of a method/block
    public void closeInventory() {
        // Calls a method
        AbstractInventory open = getOpenInventory();
        // Calls a method
        byte id = (open == null ? getInventory() : open).getWindowId();

        // Calls a method
        closeInventory(false, id);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void closeInventory(boolean fromClient, byte windowId) {
        // Calls a method
        AbstractInventory openInventory = windowId == 0 ? getInventory() : getOpenInventory();

        // Nothing happens if it has the wrong ID or if there's no inventory
        // Branch: checks a condition
        if (openInventory == null || windowId != openInventory.getWindowId()) return;

        // Calls a method
        InventoryCloseEvent inventoryCloseEvent = new InventoryCloseEvent(openInventory, this, fromClient);
        // Calls a method
        EventDispatcher.call(inventoryCloseEvent);

        // Branch: checks a condition
        if (!fromClient) {
            // Assigns a value
            didCloseInventory = true;
        // End of a block/expression
        }

        // Access to the current/parent object
        this.openInventory = null;
        // Branch: checks a condition
        if (openInventory != inventory) openInventory.removeViewer(this);
        // Calls a method
        inventory.update();

        // Assigns a value
        didCloseInventory = false;

        // Calls a method
        Inventory newInventory = inventoryCloseEvent.getNewInventory();
        // Branch: checks a condition
        if (newInventory != null)
            // Calls a method
            openInventory(newInventory);
    // End of a block/expression
    }

    /**
     * Used internally to determine when sending the close inventory packet should be skipped.
     */
    // Start of a method/block
    public boolean didCloseInventory() {
        // Returns a value to the caller
        return didCloseInventory;
    // End of a block/expression
    }

    /**
     * Used internally to reset the skipClosePacket field, which determines when sending the close inventory packet
     * should be skipped.
     * <p>
     * Shouldn't be used externally without proper understanding of its consequence.
     *
     * @param didCloseInventory the new didCloseInventory field
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void UNSAFE_changeDidCloseInventory(boolean didCloseInventory) {
        // Access to the current/parent object
        this.didCloseInventory = didCloseInventory;
    // End of a block/expression
    }

    // Start of a method/block
    public int getNextTeleportId() {
        // Returns a value to the caller
        return teleportId.incrementAndGet();
    // End of a block/expression
    }

    // Start of a method/block
    public int getLastSentTeleportId() {
        // Returns a value to the caller
        return teleportId.get();
    // End of a block/expression
    }

    // Start of a method/block
    public int getLastReceivedTeleportId() {
        // Returns a value to the caller
        return receivedTeleportId;
    // End of a block/expression
    }

    // Start of a method/block
    public void refreshReceivedTeleportId(int receivedTeleportId) {
        // Branch: checks a condition
        if (receivedTeleportId < 0) return;
        // Access to the current/parent object
        this.receivedTeleportId = receivedTeleportId;
    // End of a block/expression
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
    // Annotation for the following element
    @ApiStatus.Internal
    // Code statement
    void synchronizePositionAfterTeleport(Pos position, Point velocity,
                                          // Annotation for the following element
                                          @MagicConstant(flagsFromClass = RelativeFlags.class) int relativeFlags,
                                          // Start of a method/block
                                          boolean shouldConfirm) {
        // Calls a method
        int teleportId = shouldConfirm ? getNextTeleportId() : -1;
        // Calls a method
        sendPacket(new PlayerPositionAndLookPacket(teleportId, position, velocity, position.yaw(), position.pitch(), relativeFlags));
        // Access to the current/parent object
        super.synchronizePosition();
    // End of a block/expression
    }

    /**
     * Forces the player's client to look towards the target yaw/pitch
     *
     * @param yaw   the new yaw
     * @param pitch the new pitch
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setView(float yaw, float pitch) {
        // Calls a method
        teleport(new Pos(0, 0, 0, yaw, pitch), null, RelativeFlags.COORD).join();
    // End of a block/expression
    }

    /**
     * Forces the player's client to look towards the specified point
     * <p>
     * Note: the player's position is not updated on the server until
     * the client receives this packet
     *
     * @param point the point to look at
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public void lookAt(Point point) {
        // Let the player's client provide updated position values
        // Calls a method
        sendPacket(new FacePlayerPacket(FacePlayerPacket.FacePosition.EYES, point, 0, null));
    // End of a block/expression
    }

    /**
     * Forces the player's client to look towards the specified entity
     * <p>
     * Note: the player's position is not updated on the server until
     * the client receives this packet
     *
     * @param entity the entity to look at
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public void lookAt(Entity entity) {
        // Let the player's client provide updated position values
        // Calls a method
        sendPacket(new FacePlayerPacket(FacePlayerPacket.FacePosition.EYES, entity.getPosition(), entity.getEntityId(), FacePlayerPacket.FacePosition.EYES));
    // End of a block/expression
    }

    /**
     * Gets the player permission level.
     *
     * @return the player permission level
     */
    // Start of a method/block
    public int getPermissionLevel() {
        // Returns a value to the caller
        return permissionLevel;
    // End of a block/expression
    }

    /**
     * Changes the player permission level.
     *
     * @param permissionLevel the new player permission level
     * @throws IllegalArgumentException if {@code permissionLevel} is not between 0 and 4
     */
    // Start of a method/block
    public void setPermissionLevel(int permissionLevel) {
        // Calls a method
        Check.argCondition(!MathUtils.isBetween(permissionLevel, 0, 4), "permissionLevel has to be between 0 and 4");

        // Access to the current/parent object
        this.permissionLevel = permissionLevel;

        // Condition to prevent sending the packets before spawning the player
        // Branch: checks a condition
        if (isActive()) {

            // Calls a method
            final byte permissionLevelStatus = (byte) (EntityStatuses.Player.PERMISSION_LEVEL_0 + permissionLevel);
            // Calls a method
            triggerStatus(permissionLevelStatus);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Sets or remove the reduced debug screen.
     *
     * @param reduced should the player has the reduced debug screen
     */
    // Start of a method/block
    public void setReducedDebugScreenInformation(boolean reduced) {
        // Access to the current/parent object
        this.reducedDebugScreenInformation = reduced;

        // Calls a method
        final byte debugScreenStatus = (byte) (reduced ? EntityStatuses.Player.ENABLE_DEBUG_SCREEN : EntityStatuses.Player.DISABLE_DEBUG_SCREEN);
        // Calls a method
        triggerStatus(debugScreenStatus);
    // End of a block/expression
    }

    /**
     * Gets if the player has the reduced debug screen.
     *
     * @return true if the player has the reduced debug screen, false otherwise
     */
    // Start of a method/block
    public boolean hasReducedDebugScreenInformation() {
        // Returns a value to the caller
        return reducedDebugScreenInformation;
    // End of a block/expression
    }

    /**
     * This do update the {@code invulnerable} field in the packet {@link PlayerAbilitiesPacket}
     * and prevent the player from receiving damage.
     *
     * @param invulnerable should the player be invulnerable
     */
    // Start of a method/block
    public void setInvulnerable(boolean invulnerable) {
        // Access to the current/parent object
        super.setInvulnerable(invulnerable);
        // Calls a method
        refreshAbilities();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setSneaking(boolean sneaking) {
        // Branch: checks a condition
        if (isFlying()) { //If we are flying, don't set the players pose to sneaking as this can clip them through blocks
            // Access to the current/parent object
            this.entityMeta.setSneaking(sneaking);
        // Alternative branch of the condition
        } else {
            // Access to the current/parent object
            super.setSneaking(sneaking);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets if the player is currently flying.
     *
     * @return true if the player if flying, false otherwise
     */
    // Start of a method/block
    public boolean isFlying() {
        // Returns a value to the caller
        return flying;
    // End of a block/expression
    }

    /**
     * Sets the player flying.
     *
     * @param flying should the player fly
     */
    // Start of a method/block
    public void setFlying(boolean flying) {
        // Calls a method
        refreshFlying(flying);
        // Calls a method
        refreshAbilities();
    // End of a block/expression
    }

    /**
     * Updates the internal flying field.
     * <p>
     * Mostly unsafe since there is nothing to backup the value, used internally for creative players.
     *
     * @param flying the new flying field
     * @see #setFlying(boolean) instead
     */
    // Start of a method/block
    public void refreshFlying(boolean flying) {
        //When the player starts or stops flying, their pose needs to change
        // Branch: checks a condition
        if (this.flying != flying) {
            // Calls a method
            EntityPose pose = getPose();

            // Branch: checks a condition
            if (this.isSneaking() && pose == EntityPose.STANDING) {
                // Calls a method
                setPose(EntityPose.SNEAKING);
            // Branch: checks a condition
            } else if (pose == EntityPose.SNEAKING) {
                // Calls a method
                setPose(EntityPose.STANDING);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Access to the current/parent object
        this.flying = flying;
    // End of a block/expression
    }

    /**
     * Gets if the player is allowed to fly.
     *
     * @return true if the player if allowed to fly, false otherwise
     */
    // Start of a method/block
    public boolean isAllowFlying() {
        // Returns a value to the caller
        return allowFlying;
    // End of a block/expression
    }

    /**
     * Allows or forbid the player to fly.
     *
     * @param allowFlying should the player be allowed to fly
     */
    // Start of a method/block
    public void setAllowFlying(boolean allowFlying) {
        // Access to the current/parent object
        this.allowFlying = allowFlying;
        // Calls a method
        refreshAbilities();
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isInstantBreak() {
        // Returns a value to the caller
        return instantBreak;
    // End of a block/expression
    }

    /**
     * Changes the player ability "Creative Mode".
     *
     * @param instantBreak true to allow instant break
     * @see <a href="https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Protocol#Player_Abilities_(clientbound)">player abilities</a>
     */
    // Start of a method/block
    public void setInstantBreak(boolean instantBreak) {
        // Access to the current/parent object
        this.instantBreak = instantBreak;
        // Calls a method
        refreshAbilities();
    // End of a block/expression
    }

    /**
     * Gets the player flying speed.
     *
     * @return the flying speed of the player
     */
    // Start of a method/block
    public float getFlyingSpeed() {
        // Returns a value to the caller
        return flyingSpeed;
    // End of a block/expression
    }

    /**
     * Updates the internal field and send a {@link PlayerAbilitiesPacket} with the new flying speed.
     *
     * @param flyingSpeed the new flying speed of the player
     */
    // Start of a method/block
    public void setFlyingSpeed(float flyingSpeed) {
        // Access to the current/parent object
        this.flyingSpeed = flyingSpeed;
        // Calls a method
        refreshAbilities();
    // End of a block/expression
    }

    // Start of a method/block
    public float getFieldViewModifier() {
        // Returns a value to the caller
        return fieldViewModifier;
    // End of a block/expression
    }

    // Start of a method/block
    public void setFieldViewModifier(float fieldViewModifier) {
        // Access to the current/parent object
        this.fieldViewModifier = fieldViewModifier;
        // Calls a method
        refreshAbilities();
    // End of a block/expression
    }

    /**
     * This is the map used to send the statistic packet.
     * It is possible to add/remove/change statistic value directly into it.
     *
     * @return the modifiable statistic map
     */
    // Start of a method/block
    public Map<PlayerStatistic, Integer> getStatisticValueMap() {
        // Returns a value to the caller
        return statisticValueMap;
    // End of a block/expression
    }

    /**
     * Gets the last reported set of player inputs.
     *
     * <p>This information comes from the client so should be considered as such.</p>
     */
    // Start of a method/block
    public PlayerInputs inputs() {
        // Returns a value to the caller
        return inputs;
    // End of a block/expression
    }

    /**
     * Sends to the player a {@link PlayerAbilitiesPacket} with all the updated fields.
     */
    // Start of a method/block
    protected void refreshAbilities() {
        // Assigns a value
        byte flags = 0;
        // Branch: checks a condition
        if (invulnerable)
            // Code statement
            flags |= PlayerAbilitiesPacket.FLAG_INVULNERABLE;
        // Branch: checks a condition
        if (flying)
            // Code statement
            flags |= PlayerAbilitiesPacket.FLAG_FLYING;
        // Branch: checks a condition
        if (allowFlying)
            // Code statement
            flags |= PlayerAbilitiesPacket.FLAG_ALLOW_FLYING;
        // Branch: checks a condition
        if (instantBreak)
            // Code statement
            flags |= PlayerAbilitiesPacket.FLAG_INSTANT_BREAK;
        // Calls a method
        sendPacket(new PlayerAbilitiesPacket(flags, flyingSpeed, fieldViewModifier));
    // End of a block/expression
    }

    /**
     * All packets in the queue are executed in the {@link #update(long)} method
     * It is used internally to add all received packet from the client.
     * Could be used to "simulate" a received packet, but to use at your own risk.
     *
     * @param packet the packet to add in the queue
     */
    // Start of a method/block
    public void addPacketToQueue(ClientPacket packet) {
        // Calls a method
        final boolean success = packets.offer(packet);
        // Branch: checks a condition
        if (!success) {
            // Calls a method
            kick(Component.text("Too Many Packets", NamedTextColor.RED));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void interpretPacketQueue() {
        // Calls a method
        final PacketListenerManager manager = MinecraftServer.getPacketListenerManager();
        // This method is NOT thread-safe
        // Access to the current/parent object
        this.packets.drain(packet -> manager.processClientPacket(packet, playerConnection), ServerFlag.PLAYER_PACKET_PER_TICK);
    // End of a block/expression
    }

    /**
     * Changes the storage player latency and update its tab value.
     *
     * @param latency the new player latency
     */
    // Start of a method/block
    public void refreshLatency(int latency) {
        // Access to the current/parent object
        this.latency = latency;
        // Branch: checks a condition
        if (getPlayerConnection().getServerState() == ConnectionState.PLAY) {
            // Calls a method
            PacketSendingUtils.broadcastPlayPacket(new PlayerInfoUpdatePacket(PlayerInfoUpdatePacket.Action.UPDATE_LATENCY, infoEntry()));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public void refreshOnGround(boolean onGround) {
        // Access to the current/parent object
        this.onGround = onGround;
        // Branch: checks a condition
        if (this.onGround && this.isFlyingWithElytra()) {
            // Access to the current/parent object
            this.setFlyingWithElytra(false);
            // Calls a method
            EventDispatcher.call(new PlayerStopFlyingWithElytraEvent(this));
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Used to change internally the last sent last keep alive id.
     * <p>
     * Warning: could lead to have the player kicked because of a wrong keep alive packet.
     *
     * @param lastKeepAlive the new lastKeepAlive id
     */
    // Start of a method/block
    public void refreshKeepAlive(long lastKeepAlive) {
        // Access to the current/parent object
        this.lastKeepAlive = lastKeepAlive;
        // Access to the current/parent object
        this.answerKeepAlive = false;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean didAnswerKeepAlive() {
        // Returns a value to the caller
        return answerKeepAlive;
    // End of a block/expression
    }

    // Start of a method/block
    public void refreshAnswerKeepAlive(boolean answerKeepAlive) {
        // Access to the current/parent object
        this.answerKeepAlive = answerKeepAlive;
    // End of a block/expression
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
    // Start of a method/block
    public void refreshHeldSlot(byte slot) {
        // Assigns a value
        byte oldHeldSlot = this.heldSlot;
        // Access to the current/parent object
        this.heldSlot = slot;
        // Calls a method
        syncEquipment(EquipmentSlot.MAIN_HAND);
        // Calls a method
        updateEquipmentAttributes(inventory.getItemStack(oldHeldSlot), inventory.getItemStack(this.heldSlot), EquipmentSlot.MAIN_HAND);
    // End of a block/expression
    }

    // Start of a method/block
    public void refreshItemUse(@Nullable PlayerHand itemUseHand, long itemUseTimeTicks) {
        // Access to the current/parent object
        this.itemUseHand = itemUseHand;
        // Branch: checks a condition
        if (itemUseHand != null) {
            // Access to the current/parent object
            this.startItemUseTime = getAliveTicks();
            // Access to the current/parent object
            this.itemUseTime = itemUseTimeTicks;
        // Alternative branch of the condition
        } else {
            // Access to the current/parent object
            this.startItemUseTime = 0;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public void clearItemUse() {
        // Calls a method
        refreshItemUse(null, 0);
    // End of a block/expression
    }

    // Start of a method/block
    public void refreshInput(boolean forward, boolean backward, boolean left, boolean right, boolean jump, boolean shift, boolean sprint) {
        // Calls a method
        boolean oldForward = this.inputs.forward();
        // Calls a method
        boolean oldBackward = this.inputs.backward();
        // Calls a method
        boolean oldLeft = this.inputs.left();
        // Calls a method
        boolean oldRight = this.inputs.right();
        // Calls a method
        boolean oldJump = this.inputs.jump();
        // Calls a method
        boolean oldShift = this.inputs.shift();
        // Calls a method
        boolean oldSprint = this.inputs.sprint();

        // Access to the current/parent object
        this.inputs.refresh(forward, backward, left, right, jump, shift, sprint);
        // Access to the current/parent object
        this.setSneaking(shift);

        // Calls a method
        var event = new PlayerInputEvent(this, oldForward, oldBackward, oldLeft, oldRight, oldJump, oldShift, oldSprint);
        // Calls a method
        EventDispatcher.call(event);

        // Branch: checks a condition
        if (event.hasPressedShiftKey()) {
            // Calls a method
            EventDispatcher.call(new PlayerStartSneakingEvent(this));
        // Branch: checks a condition
        } else if (event.hasReleasedShiftKey()) {
            // Calls a method
            EventDispatcher.call(new PlayerStopSneakingEvent(this));
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets the last sent keep alive id.
     *
     * @return the last keep alive id sent to the player
     */
    // Start of a method/block
    public long getLastKeepAlive() {
        // Returns a value to the caller
        return lastKeepAlive;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public HoverEvent<ShowEntity> asHoverEvent(UnaryOperator<ShowEntity> op) {
        // Returns a value to the caller
        return HoverEvent.showEntity(ShowEntity.showEntity(EntityType.PLAYER, getUuid(), this.displayName));
    // End of a block/expression
    }

    /**
     * Gets the packet to add the player.
     *
     * @return a {@link PlayerInfoUpdatePacket} to add the player
     */
    // Start of a method/block
    protected PlayerInfoUpdatePacket getAddPlayerToList() {
        // Returns a value to the caller
        return new PlayerInfoUpdatePacket(EnumSet.allOf(PlayerInfoUpdatePacket.Action.class), List.of(infoEntry()));
    // End of a block/expression
    }

    /**
     * Gets the packet to remove the player.
     *
     * @return a {@link PlayerInfoRemovePacket} to remove the player
     */
    // Start of a method/block
    protected PlayerInfoRemovePacket getRemovePlayerToList() {
        // Returns a value to the caller
        return new PlayerInfoRemovePacket(getUuid());
    // End of a block/expression
    }

    // Start of a method/block
    private PlayerInfoUpdatePacket.Entry infoEntry() {
        // Assigns a value
        final PlayerSkin skin = this.skin;
        // Assigns a value
        List<PlayerInfoUpdatePacket.Property> prop = skin != null ?
                // Code statement
                List.of(new PlayerInfoUpdatePacket.Property("textures", skin.textures(), skin.signature())) :
                // Calls a method
                List.of();
        // Calls a method
        byte hatIndex = ((MetadataDef.Entry.BitMask) MetadataDef.Player.IS_HAT_ENABLED).bitMask();
        // Returns a value to the caller
        return new PlayerInfoUpdatePacket.Entry(getUuid(), getUsername(), prop,
                // Calls a method
                listed, getLatency(), getGameMode(), displayName, null, listOrder, (settings.displayedSkinParts() & hatIndex) == hatIndex);
    // End of a block/expression
    }

    /**
     * Sends all the related packet to have the player sent to another with related data
     * (create player, spawn position, velocity, metadata, equipments, passengers, team).
     * <p>
     * WARNING: this alone does not sync the player, please use {@link #addViewer(Player)}.
     *
     * @param connection the connection to show the player to
     */
    // Start of a method/block
    protected void showPlayer(PlayerConnection connection) {
        // Calls a method
        connection.sendPacket(getSpawnPacket());
        // Calls a method
        connection.sendPacket(getVelocityPacket());
        // Calls a method
        connection.sendPacket(getMetadataPacket());
        // Calls a method
        connection.sendPacket(getEquipmentsPacket());
        // Branch: checks a condition
        if (hasPassenger()) {
            // Calls a method
            connection.sendPacket(getPassengersPacket());
        // End of a block/expression
        }
        // Calls a method
        connection.sendPacket(new EntityHeadLookPacket(getEntityId(), headRotation));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ItemStack getEquipment(EquipmentSlot slot) {
        // Returns a value to the caller
        return inventory.getEquipment(slot, heldSlot);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setEquipment(EquipmentSlot slot, ItemStack itemStack) {
        // Calls a method
        inventory.setEquipment(slot, heldSlot, itemStack);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public PlayerSnapshot updateSnapshot(SnapshotUpdater updater) {
        // Calls a method
        final EntitySnapshot snapshot = super.updateSnapshot(updater);
        // Returns a value to the caller
        return new SnapshotImpl.Player(snapshot, username, gameMode);
    // End of a block/expression
    }

    // Start of a method/block
    public Locale getLocale() {
        // Returns a value to the caller
        return settings.locale();
    // End of a block/expression
    }

    /**
     * Sets the player's locale. This will only set the locale of the player as it
     * is stored in the server. This will also be reset if the settings are refreshed.
     *
     * @param locale the new locale
     */
    // Start of a method/block
    public void setLocale(Locale locale) {
        // Assigns a value
        final ClientSettings settings = this.settings;
        // Code statement
        refreshSettings(new ClientSettings(
                // Code statement
                locale, settings.viewDistance(), settings.chatMessageType(), settings.chatColors(),
                // Code statement
                settings.displayedSkinParts(), settings.mainHand(), settings.enableTextFiltering(),
                // Code statement
                settings.allowServerListings(), settings.particleSetting()
        // Code statement
        ));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Pointers pointers() {
        // Returns a value to the caller
        return PLAYER_POINTERS_SUPPLIER.view(this);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected void updateCollisions() {
        // Assigns a value
        preventBlockPlacement = gameMode != GameMode.SPECTATOR;
        // Assigns a value
        collidesWithEntities = gameMode != GameMode.SPECTATOR;
    // End of a block/expression
    }

    // Start of a method/block
    protected void sendChunkUpdates(Chunk newChunk) {
        // Branch: checks a condition
        if (chunkUpdateLimitChecker.addToHistory(newChunk)) {
            // Calls a method
            final int newX = newChunk.getChunkX();
            // Calls a method
            final int newZ = newChunk.getChunkZ();
            // Assigns a value
            final Vec old = chunksLoadedByClient;
            // Calls a method
            sendPacket(new UpdateViewPositionPacket(newX, newZ));
            // Code statement
            ChunkRange.chunksInRangeDiffering(newX, newZ, (int) old.x(), (int) old.z(),
                    // Access to the current/parent object
                    this.effectiveViewDistance(), chunkAdder, chunkRemover);
            // Access to the current/parent object
            this.chunksLoadedByClient = new Vec(newX, newZ);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * @see #teleport(Pos, long[], int)
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public CompletableFuture<Void> teleport(Pos position, long @Nullable [] chunks, int flags) {
        // Calls a method
        chunkUpdateLimitChecker.clearHistory();
        // Returns a value to the caller
        return super.teleport(position, chunks, flags);
    // End of a block/expression
    }

    /**
     * Send a {@link Notification} to the player.
     *
     * @param notification the {@link Notification} to send
     */
    // Start of a method/block
    public void sendNotification(Notification notification) {
        // Calls a method
        sendPacket(notification.buildAddPacket());
        // Calls a method
        sendPacket(notification.buildRemovePacket());
    // End of a block/expression
    }

    /**
     * Sends a {@link EntityAnimationPacket} to clear remove the sleep darkness.
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public void leaveBed() {
        // Calls a method
        EntityAnimationPacket packet = new EntityAnimationPacket(getEntityId(), EntityAnimationPacket.Animation.LEAVE_BED);
        // Calls a method
        sendPacket(packet);
        // Access to the current/parent object
        super.leaveBed();
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum FacePoint {
        // Code statement
        FEET,
        // Code statement
        EYE
    // End of a block/expression
    }

    // Settings enum

    // Start of a method/block
    private int compareChunkDistance(long chunkIndexA, long chunkIndexB) {
        // Calls a method
        int chunkAX = CoordConversion.chunkIndexGetX(chunkIndexA);
        // Calls a method
        int chunkAZ = CoordConversion.chunkIndexGetZ(chunkIndexA);
        // Calls a method
        int chunkBX = CoordConversion.chunkIndexGetX(chunkIndexB);
        // Calls a method
        int chunkBZ = CoordConversion.chunkIndexGetZ(chunkIndexB);
        // Calls a method
        int chunkDistanceA = Math.abs(chunkAX - chunksLoadedByClient.blockX()) + Math.abs(chunkAZ - chunksLoadedByClient.blockZ());
        // Calls a method
        int chunkDistanceB = Math.abs(chunkBX - chunksLoadedByClient.blockX()) + Math.abs(chunkBZ - chunksLoadedByClient.blockZ());
        // Returns a value to the caller
        return Integer.compare(chunkDistanceA, chunkDistanceB);
    // End of a block/expression
    }

    /**
     * Gets the client's 'effective' view distance, which is the minimum of the client's view distance settings, and the local instance settings, plus one
     *
     * @return The effective chunk view distance range of the client
     */
    // Start of a method/block
    public int effectiveViewDistance() {
        // Assigns a value
        Instance instance = this.instance;
        // Calls a method
        int maxViewDistance = instance != null ? instance.viewDistance() : ServerFlag.CHUNK_VIEW_DISTANCE;
        // Returns a value to the caller
        return Math.min(settings.viewDistance(), maxViewDistance) + 1;
    // End of a block/expression
    }

    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Annotation for the following element
    @ApiStatus.Experimental
    // Annotation for the following element
    @Override
    // Start of a method/block
    public Acquirable<? extends Player> acquirable() {
        // Returns a value to the caller
        return (Acquirable<? extends Player>) super.acquirable();
    // End of a block/expression
    }
// End of a block/expression
}
