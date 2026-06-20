// Package declaration for this file
package net.minestom.server;

// Import of a required class
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
// Import of a required class
import net.minestom.server.advancements.AdvancementManager;
// Import of a required class
import net.minestom.server.adventure.ClickCallbackManager;
// Import of a required class
import net.minestom.server.adventure.bossbar.BossBarManager;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.command.CommandManager;
// Import of a required class
import net.minestom.server.dialog.Dialog;
// Import of a required class
import net.minestom.server.entity.damage.DamageType;
// Import of a required class
import net.minestom.server.entity.metadata.animal.ChickenVariant;
// Import of a required class
import net.minestom.server.entity.metadata.animal.CowVariant;
// Import of a required class
import net.minestom.server.entity.metadata.animal.FrogVariant;
// Import of a required class
import net.minestom.server.entity.metadata.animal.PigVariant;
// Import of a required class
import net.minestom.server.entity.metadata.animal.ZombieNautilusVariant;
// Import of a required class
import net.minestom.server.entity.metadata.animal.tameable.CatVariant;
// Import of a required class
import net.minestom.server.entity.metadata.animal.tameable.WolfSoundVariant;
// Import of a required class
import net.minestom.server.entity.metadata.animal.tameable.WolfVariant;
// Import of a required class
import net.minestom.server.entity.metadata.other.PaintingVariant;
// Import of a required class
import net.minestom.server.event.GlobalEventHandler;
// Import of a required class
import net.minestom.server.exception.ExceptionManager;
// Import of a required class
import net.minestom.server.instance.InstanceManager;
// Import of a required class
import net.minestom.server.instance.block.BlockManager;
// Import of a required class
import net.minestom.server.instance.block.banner.BannerPattern;
// Import of a required class
import net.minestom.server.instance.block.jukebox.JukeboxSong;
// Import of a required class
import net.minestom.server.item.armor.TrimMaterial;
// Import of a required class
import net.minestom.server.item.armor.TrimPattern;
// Import of a required class
import net.minestom.server.item.enchant.*;
// Import of a required class
import net.minestom.server.item.instrument.Instrument;
// Import of a required class
import net.minestom.server.listener.manager.PacketListenerManager;
// Import of a required class
import net.minestom.server.message.ChatType;
// Import of a required class
import net.minestom.server.monitoring.BenchmarkManager;
// Import of a required class
import net.minestom.server.network.ConnectionManager;
// Import of a required class
import net.minestom.server.network.packet.PacketParser;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.network.packet.server.common.PluginMessagePacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.ServerDifficultyPacket;
// Import of a required class
import net.minestom.server.network.socket.Server;
// Import of a required class
import net.minestom.server.recipe.RecipeManager;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;
// Import of a required class
import net.minestom.server.scoreboard.TeamManager;
// Import of a required class
import net.minestom.server.thread.TickSchedulerThread;
// Import of a required class
import net.minestom.server.timer.SchedulerManager;
// Import of a required class
import net.minestom.server.utils.PacketSendingUtils;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import net.minestom.server.world.Difficulty;
// Import of a required class
import net.minestom.server.world.DimensionType;
// Import of a required class
import net.minestom.server.world.biome.Biome;
// Import of a required class
import net.minestom.server.world.timeline.Timeline;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.net.InetSocketAddress;
// Import of a required class
import java.net.SocketAddress;

/**
 * The main server class used to start the server and retrieve all the managers.
 * <p>
 * The server needs to be initialized with {@link #init()} and started with {@link #start(String, int)}.
 * You should register all of your dimensions, biomes, commands, events, etc... in-between.
 */
// Type declaration (class/interface/enum/record)
public final class MinecraftServer implements MinecraftConstants {

    // Calls a method
    public static final ComponentLogger LOGGER = ComponentLogger.logger(MinecraftServer.class);

    // Threads
    // Assigns a value
    public static final String THREAD_NAME_BENCHMARK = "Ms-Benchmark";

    // Assigns a value
    public static final String THREAD_NAME_TICK_SCHEDULER = "Ms-TickScheduler";
    // Assigns a value
    public static final String THREAD_NAME_TICK = "Ms-Tick";

    // Config
    // Can be modified at performance cost when increased
    // Annotation for the following element
    @Deprecated
    // Assigns a value
    public static final int TICK_PER_SECOND = ServerFlag.SERVER_TICKS_PER_SECOND;
    // Assigns a value
    public static final int TICK_MS = 1000 / TICK_PER_SECOND;

    // In-Game Manager
    // Code statement
    private static volatile @UnknownNullability ServerProcess serverProcess;

    // Assigns a value
    private static int compressionThreshold = 256;
    // Assigns a value
    private static String brandName = "Minestom";
    // Assigns a value
    private static Difficulty difficulty = Difficulty.NORMAL;

    // Start of a method/block
    public static MinecraftServer init(Auth auth) {
        // Calls a method
        updateProcess(auth);
        // Returns a value to the caller
        return new MinecraftServer();
    // End of a block/expression
    }

    // Start of a method/block
    public static MinecraftServer init() {
        // Returns a value to the caller
        return init(new Auth.Offline());
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public static ServerProcess updateProcess(Auth auth) {
        // Calls a method
        ServerProcess process = new ServerProcessImpl(auth);
        // Assigns a value
        serverProcess = process;
        // Returns a value to the caller
        return process;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public static ServerProcess updateProcess() {
        // Returns a value to the caller
        return updateProcess(new Auth.Offline());
    // End of a block/expression
    }

    /**
     * Gets the current server brand name.
     *
     * @return the server brand name
     */
    // Start of a method/block
    public static String getBrandName() {
        // Returns a value to the caller
        return brandName;
    // End of a block/expression
    }

    /**
     * Changes the server brand name and send the change to all connected players.
     *
     * @param brandName the server brand name
     * @throws NullPointerException if {@code brandName} is null
     */
    // Start of a method/block
    public static void setBrandName(String brandName) {
        // Assigns a value
        MinecraftServer.brandName = brandName;
        // Calls a method
        PacketSendingUtils.broadcastPlayPacket(PluginMessagePacket.brandPacket(brandName));
    // End of a block/expression
    }

    /**
     * Gets the server difficulty showed in game option.
     *
     * @return the server difficulty
     */
    // Start of a method/block
    public static Difficulty getDifficulty() {
        // Returns a value to the caller
        return difficulty;
    // End of a block/expression
    }

    /**
     * Changes the server difficulty and send the appropriate packet to all connected clients.
     *
     * @param difficulty the new server difficulty
     */
    // Start of a method/block
    public static void setDifficulty(Difficulty difficulty) {
        // Assigns a value
        MinecraftServer.difficulty = difficulty;
        // Calls a method
        PacketSendingUtils.broadcastPlayPacket(new ServerDifficultyPacket(difficulty, true));
    // End of a block/expression
    }

    // Start of a method/block
    public static @UnknownNullability ServerProcess process() {
        // Returns a value to the caller
        return serverProcess;
    // End of a block/expression
    }

    // Start of a method/block
    public static GlobalEventHandler getGlobalEventHandler() {
        // Returns a value to the caller
        return serverProcess.eventHandler();
    // End of a block/expression
    }

    // Start of a method/block
    public static PacketListenerManager getPacketListenerManager() {
        // Returns a value to the caller
        return serverProcess.packetListener();
    // End of a block/expression
    }

    // Start of a method/block
    public static InstanceManager getInstanceManager() {
        // Returns a value to the caller
        return serverProcess.instance();
    // End of a block/expression
    }

    // Start of a method/block
    public static BlockManager getBlockManager() {
        // Returns a value to the caller
        return serverProcess.block();
    // End of a block/expression
    }

    // Start of a method/block
    public static CommandManager getCommandManager() {
        // Returns a value to the caller
        return serverProcess.command();
    // End of a block/expression
    }

    // Start of a method/block
    public static RecipeManager getRecipeManager() {
        // Returns a value to the caller
        return serverProcess.recipe();
    // End of a block/expression
    }

    // Start of a method/block
    public static TeamManager getTeamManager() {
        // Returns a value to the caller
        return serverProcess.team();
    // End of a block/expression
    }

    // Start of a method/block
    public static SchedulerManager getSchedulerManager() {
        // Returns a value to the caller
        return serverProcess.scheduler();
    // End of a block/expression
    }

    /**
     * Gets the manager handling server monitoring.
     *
     * @return the benchmark manager
     */
    // Start of a method/block
    public static BenchmarkManager getBenchmarkManager() {
        // Returns a value to the caller
        return serverProcess.benchmark();
    // End of a block/expression
    }

    // Start of a method/block
    public static ExceptionManager getExceptionManager() {
        // Returns a value to the caller
        return serverProcess.exception();
    // End of a block/expression
    }

    // Start of a method/block
    public static ConnectionManager getConnectionManager() {
        // Returns a value to the caller
        return serverProcess.connection();
    // End of a block/expression
    }

    // Start of a method/block
    public static BossBarManager getBossBarManager() {
        // Returns a value to the caller
        return serverProcess.bossBar();
    // End of a block/expression
    }

    // Start of a method/block
    public static PacketParser.Client getPacketParser() {
        // Returns a value to the caller
        return serverProcess.packetParser();
    // End of a block/expression
    }

    // Start of a method/block
    public static boolean isStarted() {
        // Returns a value to the caller
        return serverProcess.isAlive();
    // End of a block/expression
    }

    // Start of a method/block
    public static boolean isStopping() {
        // Returns a value to the caller
        return !isStarted();
    // End of a block/expression
    }

    /**
     * Gets the chunk view distance of the server.
     * <p>
     * Deprecated in favor of {@link ServerFlag#CHUNK_VIEW_DISTANCE}
     *
     * @return the chunk view distance
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public static int getChunkViewDistance() {
        // Returns a value to the caller
        return ServerFlag.CHUNK_VIEW_DISTANCE;
    // End of a block/expression
    }

    /**
     * Gets the entity view distance of the server.
     * <p>
     * Deprecated in favor of {@link ServerFlag#ENTITY_VIEW_DISTANCE}
     *
     * @return the entity view distance
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public static int getEntityViewDistance() {
        // Returns a value to the caller
        return ServerFlag.ENTITY_VIEW_DISTANCE;
    // End of a block/expression
    }

    /**
     * Gets the compression threshold of the server.
     *
     * @return the compression threshold, 0 means that compression is disabled
     */
    // Start of a method/block
    public static int getCompressionThreshold() {
        // Returns a value to the caller
        return compressionThreshold;
    // End of a block/expression
    }

    /**
     * Changes the compression threshold of the server.
     * <p>
     * WARNING: this need to be called before {@link #start(SocketAddress)}.
     *
     * @param compressionThreshold the new compression threshold, 0 to disable compression
     * @throws IllegalStateException if this is called after the server started
     */
    // Start of a method/block
    public static void setCompressionThreshold(int compressionThreshold) {
        // Calls a method
        Check.stateCondition(serverProcess != null && serverProcess.isAlive(), "The compression threshold cannot be changed after the server has been started.");
        // Assigns a value
        MinecraftServer.compressionThreshold = compressionThreshold;
    // End of a block/expression
    }

    // Start of a method/block
    public static AdvancementManager getAdvancementManager() {
        // Returns a value to the caller
        return serverProcess.advancement();
    // End of a block/expression
    }

    // Start of a method/block
    public static ClickCallbackManager getClickCallbackManager() {
        // Returns a value to the caller
        return serverProcess.clickCallbackManager();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<ChatType> getChatTypeRegistry() {
        // Returns a value to the caller
        return serverProcess.chatType();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<Dialog> getDialogRegistry() {
        // Returns a value to the caller
        return serverProcess.dialog();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<DimensionType> getDimensionTypeRegistry() {
        // Returns a value to the caller
        return serverProcess.dimensionType();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<Biome> getBiomeRegistry() {
        // Returns a value to the caller
        return serverProcess.biome();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<DamageType> getDamageTypeRegistry() {
        // Returns a value to the caller
        return serverProcess.damageType();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<TrimMaterial> getTrimMaterialRegistry() {
        // Returns a value to the caller
        return serverProcess.trimMaterial();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<TrimPattern> getTrimPatternRegistry() {
        // Returns a value to the caller
        return serverProcess.trimPattern();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<BannerPattern> getBannerPatternRegistry() {
        // Returns a value to the caller
        return serverProcess.bannerPattern();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<WolfVariant> getWolfVariantRegistry() {
        // Returns a value to the caller
        return serverProcess.wolfVariant();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<WolfSoundVariant> getWolfSoundVariantRegistry() {
        // Returns a value to the caller
        return serverProcess.wolfSoundVariant();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<CatVariant> getCatVariantRegistry() {
        // Returns a value to the caller
        return serverProcess.catVariant();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<ChickenVariant> getChickenVariantRegistry() {
        // Returns a value to the caller
        return serverProcess.chickenVariant();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<CowVariant> getCowVariantRegistry() {
        // Returns a value to the caller
        return serverProcess.cowVariant();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<FrogVariant> getFrogVariantRegistry() {
        // Returns a value to the caller
        return serverProcess.frogVariant();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<PigVariant> getPigVariantRegistry() {
        // Returns a value to the caller
        return serverProcess.pigVariant();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<ZombieNautilusVariant> getZombieNautilusVariantRegistry() {
        // Returns a value to the caller
        return serverProcess.zombieNautilusVariant();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<Enchantment> getEnchantmentRegistry() {
        // Returns a value to the caller
        return serverProcess.enchantment();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<PaintingVariant> getPaintingVariantRegistry() {
        // Returns a value to the caller
        return serverProcess.paintingVariant();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<JukeboxSong> getJukeboxSongRegistry() {
        // Returns a value to the caller
        return serverProcess.jukeboxSong();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<Instrument> getInstrumentRegistry() {
        // Returns a value to the caller
        return serverProcess.instrument();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<Timeline> getTimelineRegistry() {
        // Returns a value to the caller
        return serverProcess.timeline();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<StructCodec<? extends LevelBasedValue>> enchantmentLevelBasedValues() {
        // Returns a value to the caller
        return serverProcess.enchantmentLevelBasedValues();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<StructCodec<? extends ValueEffect>> enchantmentValueEffects() {
        // Returns a value to the caller
        return serverProcess.enchantmentValueEffects();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<StructCodec<? extends EntityEffect>> enchantmentEntityEffects() {
        // Returns a value to the caller
        return serverProcess.enchantmentEntityEffects();
    // End of a block/expression
    }

    // Start of a method/block
    public static DynamicRegistry<StructCodec<? extends LocationEffect>> enchantmentLocationEffects() {
        // Returns a value to the caller
        return serverProcess.enchantmentLocationEffects();
    // End of a block/expression
    }

    // Start of a method/block
    public static Server getServer() {
        // Returns a value to the caller
        return serverProcess.server();
    // End of a block/expression
    }

    /**
     * Starts the server.
     * <p>
     * It should be called after {@link #init()} and probably your own initialization code.
     *
     * @param address the server address
     * @throws IllegalStateException if called before {@link #init()} or if the server is already running
     */
    // Start of a method/block
    public void start(SocketAddress address) {
        // Calls a method
        serverProcess.start(address);
        // Calls a method
        serverProcess.dispatcher().start();
        // Creates a new object
        new TickSchedulerThread(serverProcess).start();
    // End of a block/expression
    }

    // Start of a method/block
    public void start(String address, int port) {
        // Calls a method
        start(new InetSocketAddress(address, port));
    // End of a block/expression
    }

    /**
     * Stops this server properly (saves if needed, kicking players, etc.)
     */
    // Start of a method/block
    public static void stopCleanly() {
        // Calls a method
        serverProcess.stop();
    // End of a block/expression
    }
// End of a block/expression
}
