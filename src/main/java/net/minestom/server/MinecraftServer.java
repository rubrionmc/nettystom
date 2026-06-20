// Déclaration du paquet de ce fichier
package net.minestom.server;

// Import d'une classe nécessaire
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
// Import d'une classe nécessaire
import net.minestom.server.advancements.AdvancementManager;
// Import d'une classe nécessaire
import net.minestom.server.adventure.ClickCallbackManager;
// Import d'une classe nécessaire
import net.minestom.server.adventure.bossbar.BossBarManager;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandManager;
// Import d'une classe nécessaire
import net.minestom.server.dialog.Dialog;
// Import d'une classe nécessaire
import net.minestom.server.entity.damage.DamageType;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.ChickenVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.CowVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.FrogVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.PigVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.ZombieNautilusVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.tameable.CatVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.tameable.WolfSoundVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.tameable.WolfVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.other.PaintingVariant;
// Import d'une classe nécessaire
import net.minestom.server.event.GlobalEventHandler;
// Import d'une classe nécessaire
import net.minestom.server.exception.ExceptionManager;
// Import d'une classe nécessaire
import net.minestom.server.instance.InstanceManager;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockManager;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.banner.BannerPattern;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.jukebox.JukeboxSong;
// Import d'une classe nécessaire
import net.minestom.server.item.armor.TrimMaterial;
// Import d'une classe nécessaire
import net.minestom.server.item.armor.TrimPattern;
// Import d'une classe nécessaire
import net.minestom.server.item.enchant.*;
// Import d'une classe nécessaire
import net.minestom.server.item.instrument.Instrument;
// Import d'une classe nécessaire
import net.minestom.server.listener.manager.PacketListenerManager;
// Import d'une classe nécessaire
import net.minestom.server.message.ChatType;
// Import d'une classe nécessaire
import net.minestom.server.monitoring.BenchmarkManager;
// Import d'une classe nécessaire
import net.minestom.server.network.ConnectionManager;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketParser;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.PluginMessagePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.ServerDifficultyPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.socket.Server;
// Import d'une classe nécessaire
import net.minestom.server.recipe.RecipeManager;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.scoreboard.TeamManager;
// Import d'une classe nécessaire
import net.minestom.server.thread.TickSchedulerThread;
// Import d'une classe nécessaire
import net.minestom.server.timer.SchedulerManager;
// Import d'une classe nécessaire
import net.minestom.server.utils.PacketSendingUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import net.minestom.server.world.Difficulty;
// Import d'une classe nécessaire
import net.minestom.server.world.DimensionType;
// Import d'une classe nécessaire
import net.minestom.server.world.biome.Biome;
// Import d'une classe nécessaire
import net.minestom.server.world.timeline.Timeline;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.net.InetSocketAddress;
// Import d'une classe nécessaire
import java.net.SocketAddress;

/**
 * The main server class used to start the server and retrieve all the managers.
 * <p>
 * The server needs to be initialized with {@link #init()} and started with {@link #start(String, int)}.
 * You should register all of your dimensions, biomes, commands, events, etc... in-between.
 */
// Déclaration de type (classe/interface/enum/record)
public final class MinecraftServer implements MinecraftConstants {

    // Appelle une méthode
    public static final ComponentLogger LOGGER = ComponentLogger.logger(MinecraftServer.class);

    // Threads
    // Affecte une valeur
    public static final String THREAD_NAME_BENCHMARK = "Ms-Benchmark";

    // Affecte une valeur
    public static final String THREAD_NAME_TICK_SCHEDULER = "Ms-TickScheduler";
    // Affecte une valeur
    public static final String THREAD_NAME_TICK = "Ms-Tick";

    // Config
    // Can be modified at performance cost when increased
    // Annotation pour l'élément suivant
    @Deprecated
    // Affecte une valeur
    public static final int TICK_PER_SECOND = ServerFlag.SERVER_TICKS_PER_SECOND;
    // Affecte une valeur
    public static final int TICK_MS = 1000 / TICK_PER_SECOND;

    // In-Game Manager
    // Instruction de code
    private static volatile @UnknownNullability ServerProcess serverProcess;

    // Affecte une valeur
    private static int compressionThreshold = 256;
    // Affecte une valeur
    private static String brandName = "Minestom";
    // Affecte une valeur
    private static Difficulty difficulty = Difficulty.NORMAL;

    // Début d'une méthode/d'un bloc
    public static MinecraftServer init(Auth auth) {
        // Appelle une méthode
        updateProcess(auth);
        // Renvoie une valeur à l'appelant
        return new MinecraftServer();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static MinecraftServer init() {
        // Renvoie une valeur à l'appelant
        return init(new Auth.Offline());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public static ServerProcess updateProcess(Auth auth) {
        // Appelle une méthode
        ServerProcess process = new ServerProcessImpl(auth);
        // Affecte une valeur
        serverProcess = process;
        // Renvoie une valeur à l'appelant
        return process;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public static ServerProcess updateProcess() {
        // Renvoie une valeur à l'appelant
        return updateProcess(new Auth.Offline());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the current server brand name.
     *
     * @return the server brand name
     */
    // Début d'une méthode/d'un bloc
    public static String getBrandName() {
        // Renvoie une valeur à l'appelant
        return brandName;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the server brand name and send the change to all connected players.
     *
     * @param brandName the server brand name
     * @throws NullPointerException if {@code brandName} is null
     */
    // Début d'une méthode/d'un bloc
    public static void setBrandName(String brandName) {
        // Affecte une valeur
        MinecraftServer.brandName = brandName;
        // Appelle une méthode
        PacketSendingUtils.broadcastPlayPacket(PluginMessagePacket.brandPacket(brandName));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the server difficulty showed in game option.
     *
     * @return the server difficulty
     */
    // Début d'une méthode/d'un bloc
    public static Difficulty getDifficulty() {
        // Renvoie une valeur à l'appelant
        return difficulty;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the server difficulty and send the appropriate packet to all connected clients.
     *
     * @param difficulty the new server difficulty
     */
    // Début d'une méthode/d'un bloc
    public static void setDifficulty(Difficulty difficulty) {
        // Affecte une valeur
        MinecraftServer.difficulty = difficulty;
        // Appelle une méthode
        PacketSendingUtils.broadcastPlayPacket(new ServerDifficultyPacket(difficulty, true));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static @UnknownNullability ServerProcess process() {
        // Renvoie une valeur à l'appelant
        return serverProcess;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static GlobalEventHandler getGlobalEventHandler() {
        // Renvoie une valeur à l'appelant
        return serverProcess.eventHandler();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static PacketListenerManager getPacketListenerManager() {
        // Renvoie une valeur à l'appelant
        return serverProcess.packetListener();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static InstanceManager getInstanceManager() {
        // Renvoie une valeur à l'appelant
        return serverProcess.instance();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static BlockManager getBlockManager() {
        // Renvoie une valeur à l'appelant
        return serverProcess.block();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static CommandManager getCommandManager() {
        // Renvoie une valeur à l'appelant
        return serverProcess.command();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static RecipeManager getRecipeManager() {
        // Renvoie une valeur à l'appelant
        return serverProcess.recipe();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static TeamManager getTeamManager() {
        // Renvoie une valeur à l'appelant
        return serverProcess.team();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static SchedulerManager getSchedulerManager() {
        // Renvoie une valeur à l'appelant
        return serverProcess.scheduler();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the manager handling server monitoring.
     *
     * @return the benchmark manager
     */
    // Début d'une méthode/d'un bloc
    public static BenchmarkManager getBenchmarkManager() {
        // Renvoie une valeur à l'appelant
        return serverProcess.benchmark();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static ExceptionManager getExceptionManager() {
        // Renvoie une valeur à l'appelant
        return serverProcess.exception();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static ConnectionManager getConnectionManager() {
        // Renvoie une valeur à l'appelant
        return serverProcess.connection();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static BossBarManager getBossBarManager() {
        // Renvoie une valeur à l'appelant
        return serverProcess.bossBar();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static PacketParser.Client getPacketParser() {
        // Renvoie une valeur à l'appelant
        return serverProcess.packetParser();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static boolean isStarted() {
        // Renvoie une valeur à l'appelant
        return serverProcess.isAlive();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static boolean isStopping() {
        // Renvoie une valeur à l'appelant
        return !isStarted();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the chunk view distance of the server.
     * <p>
     * Deprecated in favor of {@link ServerFlag#CHUNK_VIEW_DISTANCE}
     *
     * @return the chunk view distance
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public static int getChunkViewDistance() {
        // Renvoie une valeur à l'appelant
        return ServerFlag.CHUNK_VIEW_DISTANCE;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the entity view distance of the server.
     * <p>
     * Deprecated in favor of {@link ServerFlag#ENTITY_VIEW_DISTANCE}
     *
     * @return the entity view distance
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public static int getEntityViewDistance() {
        // Renvoie une valeur à l'appelant
        return ServerFlag.ENTITY_VIEW_DISTANCE;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the compression threshold of the server.
     *
     * @return the compression threshold, 0 means that compression is disabled
     */
    // Début d'une méthode/d'un bloc
    public static int getCompressionThreshold() {
        // Renvoie une valeur à l'appelant
        return compressionThreshold;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the compression threshold of the server.
     * <p>
     * WARNING: this need to be called before {@link #start(SocketAddress)}.
     *
     * @param compressionThreshold the new compression threshold, 0 to disable compression
     * @throws IllegalStateException if this is called after the server started
     */
    // Début d'une méthode/d'un bloc
    public static void setCompressionThreshold(int compressionThreshold) {
        // Appelle une méthode
        Check.stateCondition(serverProcess != null && serverProcess.isAlive(), "The compression threshold cannot be changed after the server has been started.");
        // Affecte une valeur
        MinecraftServer.compressionThreshold = compressionThreshold;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static AdvancementManager getAdvancementManager() {
        // Renvoie une valeur à l'appelant
        return serverProcess.advancement();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static ClickCallbackManager getClickCallbackManager() {
        // Renvoie une valeur à l'appelant
        return serverProcess.clickCallbackManager();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<ChatType> getChatTypeRegistry() {
        // Renvoie une valeur à l'appelant
        return serverProcess.chatType();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<Dialog> getDialogRegistry() {
        // Renvoie une valeur à l'appelant
        return serverProcess.dialog();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<DimensionType> getDimensionTypeRegistry() {
        // Renvoie une valeur à l'appelant
        return serverProcess.dimensionType();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<Biome> getBiomeRegistry() {
        // Renvoie une valeur à l'appelant
        return serverProcess.biome();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<DamageType> getDamageTypeRegistry() {
        // Renvoie une valeur à l'appelant
        return serverProcess.damageType();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<TrimMaterial> getTrimMaterialRegistry() {
        // Renvoie une valeur à l'appelant
        return serverProcess.trimMaterial();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<TrimPattern> getTrimPatternRegistry() {
        // Renvoie une valeur à l'appelant
        return serverProcess.trimPattern();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<BannerPattern> getBannerPatternRegistry() {
        // Renvoie une valeur à l'appelant
        return serverProcess.bannerPattern();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<WolfVariant> getWolfVariantRegistry() {
        // Renvoie une valeur à l'appelant
        return serverProcess.wolfVariant();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<WolfSoundVariant> getWolfSoundVariantRegistry() {
        // Renvoie une valeur à l'appelant
        return serverProcess.wolfSoundVariant();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<CatVariant> getCatVariantRegistry() {
        // Renvoie une valeur à l'appelant
        return serverProcess.catVariant();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<ChickenVariant> getChickenVariantRegistry() {
        // Renvoie une valeur à l'appelant
        return serverProcess.chickenVariant();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<CowVariant> getCowVariantRegistry() {
        // Renvoie une valeur à l'appelant
        return serverProcess.cowVariant();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<FrogVariant> getFrogVariantRegistry() {
        // Renvoie une valeur à l'appelant
        return serverProcess.frogVariant();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<PigVariant> getPigVariantRegistry() {
        // Renvoie une valeur à l'appelant
        return serverProcess.pigVariant();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<ZombieNautilusVariant> getZombieNautilusVariantRegistry() {
        // Renvoie une valeur à l'appelant
        return serverProcess.zombieNautilusVariant();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<Enchantment> getEnchantmentRegistry() {
        // Renvoie une valeur à l'appelant
        return serverProcess.enchantment();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<PaintingVariant> getPaintingVariantRegistry() {
        // Renvoie une valeur à l'appelant
        return serverProcess.paintingVariant();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<JukeboxSong> getJukeboxSongRegistry() {
        // Renvoie une valeur à l'appelant
        return serverProcess.jukeboxSong();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<Instrument> getInstrumentRegistry() {
        // Renvoie une valeur à l'appelant
        return serverProcess.instrument();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<Timeline> getTimelineRegistry() {
        // Renvoie une valeur à l'appelant
        return serverProcess.timeline();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<StructCodec<? extends LevelBasedValue>> enchantmentLevelBasedValues() {
        // Renvoie une valeur à l'appelant
        return serverProcess.enchantmentLevelBasedValues();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<StructCodec<? extends ValueEffect>> enchantmentValueEffects() {
        // Renvoie une valeur à l'appelant
        return serverProcess.enchantmentValueEffects();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<StructCodec<? extends EntityEffect>> enchantmentEntityEffects() {
        // Renvoie une valeur à l'appelant
        return serverProcess.enchantmentEntityEffects();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static DynamicRegistry<StructCodec<? extends LocationEffect>> enchantmentLocationEffects() {
        // Renvoie une valeur à l'appelant
        return serverProcess.enchantmentLocationEffects();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Server getServer() {
        // Renvoie une valeur à l'appelant
        return serverProcess.server();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Starts the server.
     * <p>
     * It should be called after {@link #init()} and probably your own initialization code.
     *
     * @param address the server address
     * @throws IllegalStateException if called before {@link #init()} or if the server is already running
     */
    // Début d'une méthode/d'un bloc
    public void start(SocketAddress address) {
        // Appelle une méthode
        serverProcess.start(address);
        // Appelle une méthode
        serverProcess.dispatcher().start();
        // Crée un nouvel objet
        new TickSchedulerThread(serverProcess).start();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void start(String address, int port) {
        // Appelle une méthode
        start(new InetSocketAddress(address, port));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Stops this server properly (saves if needed, kicking players, etc.)
     */
    // Début d'une méthode/d'un bloc
    public static void stopCleanly() {
        // Appelle une méthode
        serverProcess.stop();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
