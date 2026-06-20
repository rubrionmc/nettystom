// Déclaration du paquet de ce fichier
package net.minestom.server;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
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
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.dialog.Dialog;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.damage.DamageType;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.*;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.tameable.CatVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.tameable.WolfSoundVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.tameable.WolfVariant;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.other.PaintingVariant;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.GlobalEventHandler;
// Import d'une classe nécessaire
import net.minestom.server.event.server.ServerTickMonitorEvent;
// Import d'une classe nécessaire
import net.minestom.server.exception.ExceptionManager;
// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
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
import net.minestom.server.monitoring.EventsJFR;
// Import d'une classe nécessaire
import net.minestom.server.monitoring.TickMonitor;
// Import d'une classe nécessaire
import net.minestom.server.network.ConnectionManager;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketParser;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketVanilla;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.socket.Server;
// Import d'une classe nécessaire
import net.minestom.server.recipe.RecipeManager;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.scoreboard.TeamManager;
// Import d'une classe nécessaire
import net.minestom.server.snapshot.*;
// Import d'une classe nécessaire
import net.minestom.server.thread.Acquirable;
// Import d'une classe nécessaire
import net.minestom.server.thread.ThreadDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.thread.ThreadProvider;
// Import d'une classe nécessaire
import net.minestom.server.timer.SchedulerManager;
// Import d'une classe nécessaire
import net.minestom.server.utils.PacketViewableUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.collection.MappedCollection;
// Import d'une classe nécessaire
import net.minestom.server.utils.time.Tick;
// Import d'une classe nécessaire
import net.minestom.server.world.DimensionType;
// Import d'une classe nécessaire
import net.minestom.server.world.biome.Biome;
// Import d'une classe nécessaire
import net.minestom.server.world.timeline.Timeline;
// Import d'une classe nécessaire
import org.slf4j.Logger;
// Import d'une classe nécessaire
import org.slf4j.LoggerFactory;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.net.SocketAddress;
// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicBoolean;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicReference;

// Déclaration de type (classe/interface/enum/record)
final class ServerProcessImpl implements ServerProcess {
    // Appelle une méthode
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerProcessImpl.class);

    // Instruction de code
    private final Auth auth;

    // Instruction de code
    private final ExceptionManager exception;

    // Instruction de code
    private final DynamicRegistry<StructCodec<? extends LevelBasedValue>> enchantmentLevelBasedValues;
    // Instruction de code
    private final DynamicRegistry<StructCodec<? extends ValueEffect>> enchantmentValueEffects;
    // Instruction de code
    private final DynamicRegistry<StructCodec<? extends EntityEffect>> enchantmentEntityEffects;
    // Instruction de code
    private final DynamicRegistry<StructCodec<? extends LocationEffect>> enchantmentLocationEffects;

    // Instruction de code
    private final DynamicRegistry<ChatType> chatType;
    // Instruction de code
    private final DynamicRegistry<Dialog> dialog;
    // Instruction de code
    private final DynamicRegistry<DimensionType> dimensionType;
    // Instruction de code
    private final DynamicRegistry<Biome> biome;
    // Instruction de code
    private final DynamicRegistry<DamageType> damageType;
    // Instruction de code
    private final DynamicRegistry<TrimMaterial> trimMaterial;
    // Instruction de code
    private final DynamicRegistry<TrimPattern> trimPattern;
    // Instruction de code
    private final DynamicRegistry<BannerPattern> bannerPattern;
    // Instruction de code
    private final DynamicRegistry<Enchantment> enchantment;
    // Instruction de code
    private final DynamicRegistry<PaintingVariant> paintingVariant;
    // Instruction de code
    private final DynamicRegistry<JukeboxSong> jukeboxSong;
    // Instruction de code
    private final DynamicRegistry<Instrument> instrument;
    // Instruction de code
    private final DynamicRegistry<WolfVariant> wolfVariant;
    // Instruction de code
    private final DynamicRegistry<WolfSoundVariant> wolfSoundVariant;
    // Instruction de code
    private final DynamicRegistry<CatVariant> catVariant;
    // Instruction de code
    private final DynamicRegistry<ChickenVariant> chickenVariant;
    // Instruction de code
    private final DynamicRegistry<CowVariant> cowVariant;
    // Instruction de code
    private final DynamicRegistry<FrogVariant> frogVariant;
    // Instruction de code
    private final DynamicRegistry<PigVariant> pigVariant;
    // Instruction de code
    private final DynamicRegistry<ZombieNautilusVariant> zombieNautilusVariant;
    // Instruction de code
    private final DynamicRegistry<Timeline> timeline;

    // Instruction de code
    private final ConnectionManager connection;
    // Instruction de code
    private final PacketListenerManager packetListener;
    // Instruction de code
    private final PacketParser<ClientPacket> packetParser;
    // Instruction de code
    private final InstanceManager instance;
    // Instruction de code
    private final BlockManager block;
    // Instruction de code
    private final CommandManager command;
    // Instruction de code
    private final RecipeManager recipe;
    // Instruction de code
    private final TeamManager team;
    // Instruction de code
    private final GlobalEventHandler eventHandler;
    // Instruction de code
    private final SchedulerManager scheduler;
    // Instruction de code
    private final BenchmarkManager benchmark;
    // Instruction de code
    private final AdvancementManager advancement;
    // Instruction de code
    private final BossBarManager bossBar;
    // Instruction de code
    private final ClickCallbackManager clickCallbackManager;

    // Instruction de code
    private final Server server;

    // Instruction de code
    private final ThreadDispatcher<Chunk, Entity> dispatcher;
    // Instruction de code
    private final Ticker ticker;

    // Appelle une méthode
    private final AtomicBoolean started = new AtomicBoolean();
    // Appelle une méthode
    private final AtomicBoolean stopped = new AtomicBoolean();

    // Début d'une méthode/d'un bloc
    public ServerProcessImpl(Auth auth) {
        // Accès à l'objet courant/parent
        this.auth = auth;
        // Accès à l'objet courant/parent
        this.exception = new ExceptionManager();

        // The order of initialization here is relevant, we must load the enchantment util registries before the vanilla data is loaded.
        // Affecte une valeur
        var ignoredForInit = DataComponents.ITEM_NAME;

        // Accès à l'objet courant/parent
        this.enchantmentLevelBasedValues = LevelBasedValue.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.enchantmentValueEffects = ValueEffect.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.enchantmentEntityEffects = EntityEffect.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.enchantmentLocationEffects = LocationEffect.createDefaultRegistry();

        // Accès à l'objet courant/parent
        this.chatType = ChatType.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.dialog = Dialog.createDefaultRegistry(this);
        // Accès à l'objet courant/parent
        this.biome = Biome.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.damageType = DamageType.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.trimMaterial = TrimMaterial.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.trimPattern = TrimPattern.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.bannerPattern = BannerPattern.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.enchantment = Enchantment.createDefaultRegistry(this);
        // Accès à l'objet courant/parent
        this.paintingVariant = PaintingVariant.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.jukeboxSong = JukeboxSong.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.instrument = Instrument.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.wolfVariant = WolfVariant.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.wolfSoundVariant = WolfSoundVariant.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.catVariant = CatVariant.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.chickenVariant = ChickenVariant.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.cowVariant = CowVariant.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.frogVariant = FrogVariant.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.pigVariant = PigVariant.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.zombieNautilusVariant = ZombieNautilusVariant.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.timeline = Timeline.createDefaultRegistry();
        // Accès à l'objet courant/parent
        this.dimensionType = DimensionType.createDefaultRegistry(this); // depends on timelines

        // Accès à l'objet courant/parent
        this.connection = new ConnectionManager();
        // Accès à l'objet courant/parent
        this.packetListener = new PacketListenerManager();
        // Accès à l'objet courant/parent
        this.packetParser = PacketVanilla.CLIENT_PACKET_PARSER;
        // Accès à l'objet courant/parent
        this.instance = new InstanceManager(this);
        // Accès à l'objet courant/parent
        this.block = new BlockManager();
        // Accès à l'objet courant/parent
        this.command = new CommandManager();
        // Accès à l'objet courant/parent
        this.recipe = new RecipeManager();
        // Accès à l'objet courant/parent
        this.team = new TeamManager();
        // Accès à l'objet courant/parent
        this.eventHandler = new GlobalEventHandler();
        // Accès à l'objet courant/parent
        this.scheduler = new SchedulerManager();
        // Accès à l'objet courant/parent
        this.benchmark = new BenchmarkManager();
        // Accès à l'objet courant/parent
        this.advancement = new AdvancementManager();
        // Accès à l'objet courant/parent
        this.bossBar = new BossBarManager();
        // Accès à l'objet courant/parent
        this.clickCallbackManager = new ClickCallbackManager();

        // Accès à l'objet courant/parent
        this.server = new Server(packetParser);

        // Accès à l'objet courant/parent
        this.dispatcher = ThreadDispatcher.dispatcher(ThreadProvider.counter(), ServerFlag.DISPATCHER_THREADS);
        // Accès à l'objet courant/parent
        this.ticker = new TickerImpl();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Auth auth() {
        // Renvoie une valeur à l'appelant
        return auth;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ExceptionManager exception() {
        // Renvoie une valeur à l'appelant
        return exception;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<Dialog> dialog() {
        // Renvoie une valeur à l'appelant
        return dialog;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<Timeline> timeline() {
        // Renvoie une valeur à l'appelant
        return timeline;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<DamageType> damageType() {
        // Renvoie une valeur à l'appelant
        return damageType;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<TrimMaterial> trimMaterial() {
        // Renvoie une valeur à l'appelant
        return trimMaterial;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<TrimPattern> trimPattern() {
        // Renvoie une valeur à l'appelant
        return trimPattern;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<BannerPattern> bannerPattern() {
        // Renvoie une valeur à l'appelant
        return bannerPattern;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<Enchantment> enchantment() {
        // Renvoie une valeur à l'appelant
        return enchantment;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<PaintingVariant> paintingVariant() {
        // Renvoie une valeur à l'appelant
        return paintingVariant;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<JukeboxSong> jukeboxSong() {
        // Renvoie une valeur à l'appelant
        return jukeboxSong;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<Instrument> instrument() {
        // Renvoie une valeur à l'appelant
        return instrument;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<WolfVariant> wolfVariant() {
        // Renvoie une valeur à l'appelant
        return wolfVariant;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<WolfSoundVariant> wolfSoundVariant() {
        // Renvoie une valeur à l'appelant
        return wolfSoundVariant;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<CatVariant> catVariant() {
        // Renvoie une valeur à l'appelant
        return catVariant;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<ChickenVariant> chickenVariant() {
        // Renvoie une valeur à l'appelant
        return chickenVariant;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<CowVariant> cowVariant() {
        // Renvoie une valeur à l'appelant
        return cowVariant;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<FrogVariant> frogVariant() {
        // Renvoie une valeur à l'appelant
        return frogVariant;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<PigVariant> pigVariant() {
        // Renvoie une valeur à l'appelant
        return pigVariant;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<ZombieNautilusVariant> zombieNautilusVariant() {
        // Renvoie une valeur à l'appelant
        return zombieNautilusVariant;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<StructCodec<? extends LevelBasedValue>> enchantmentLevelBasedValues() {
        // Renvoie une valeur à l'appelant
        return enchantmentLevelBasedValues;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<StructCodec<? extends ValueEffect>> enchantmentValueEffects() {
        // Renvoie une valeur à l'appelant
        return enchantmentValueEffects;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<StructCodec<? extends EntityEffect>> enchantmentEntityEffects() {
        // Renvoie une valeur à l'appelant
        return enchantmentEntityEffects;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<StructCodec<? extends LocationEffect>> enchantmentLocationEffects() {
        // Renvoie une valeur à l'appelant
        return enchantmentLocationEffects;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ConnectionManager connection() {
        // Renvoie une valeur à l'appelant
        return connection;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public InstanceManager instance() {
        // Renvoie une valeur à l'appelant
        return instance;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public BlockManager block() {
        // Renvoie une valeur à l'appelant
        return block;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public CommandManager command() {
        // Renvoie une valeur à l'appelant
        return command;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public RecipeManager recipe() {
        // Renvoie une valeur à l'appelant
        return recipe;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public TeamManager team() {
        // Renvoie une valeur à l'appelant
        return team;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public GlobalEventHandler eventHandler() {
        // Renvoie une valeur à l'appelant
        return eventHandler;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public SchedulerManager scheduler() {
        // Renvoie une valeur à l'appelant
        return scheduler;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public BenchmarkManager benchmark() {
        // Renvoie une valeur à l'appelant
        return benchmark;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public AdvancementManager advancement() {
        // Renvoie une valeur à l'appelant
        return advancement;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public BossBarManager bossBar() {
        // Renvoie une valeur à l'appelant
        return bossBar;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<ChatType> chatType() {
        // Renvoie une valeur à l'appelant
        return chatType;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<DimensionType> dimensionType() {
        // Renvoie une valeur à l'appelant
        return dimensionType;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DynamicRegistry<Biome> biome() {
        // Renvoie une valeur à l'appelant
        return biome;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public PacketListenerManager packetListener() {
        // Renvoie une valeur à l'appelant
        return packetListener;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public PacketParser<ClientPacket> packetParser() {
        // Renvoie une valeur à l'appelant
        return packetParser;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Server server() {
        // Renvoie une valeur à l'appelant
        return server;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ThreadDispatcher<Chunk, Entity> dispatcher() {
        // Renvoie une valeur à l'appelant
        return dispatcher;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Ticker ticker() {
        // Renvoie une valeur à l'appelant
        return ticker;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ClickCallbackManager clickCallbackManager() {
        // Renvoie une valeur à l'appelant
        return clickCallbackManager;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void start(SocketAddress socketAddress) {
        // Embranchement : vérifie une condition
        if (!started.compareAndSet(false, true)) {
            // Lève une exception
            throw new IllegalStateException("Server already started");
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final String brand = MinecraftServer.getBrandName();
        // Appelle une méthode
        LOGGER.info("Starting {} ({}) server.", brand, Git.version());
        // Embranchement multiple (switch/case)
        switch (auth) {
            // Embranchement multiple (switch/case)
            case Auth.Offline ignored ->
                    // Appelle une méthode
                    LOGGER.info("Running in offline mode. Beware that this is not secure and players can impersonate each other.");
            // Embranchement multiple (switch/case)
            case Auth.Online ignored -> LOGGER.info("Running in online mode with Mojang's authentication.");
            // Embranchement multiple (switch/case)
            case Auth.Velocity ignored -> LOGGER.info("Running in Velocity mode with modern IP forwarding.");
            // Embranchement multiple (switch/case)
            case Auth.Bungee bungee -> {
                // Embranchement : vérifie une condition
                if (bungee.guard()) {
                    // Appelle une méthode
                    LOGGER.info("Running in BungeeCord mode, using legacy IP forwarding with Guard enabled.");
                // Branche alternative de la condition
                } else {
                    // Appelle une méthode
                    LOGGER.info("Running in BungeeCord mode without BungeeGuard. Be sure to configure your firewall to prevent direct connections.");
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Init server
        // Gestion des exceptions
        try {
            // Appelle une méthode
            server.init(socketAddress);
        // Début d'une méthode/d'un bloc
        } catch (IOException e) {
            // Appelle une méthode
            exception.handleException(e);
            // Lève une exception
            throw new RuntimeException(e);
        // Fin d'un bloc/d'une expression
        }

        // Start server
        // Appelle une méthode
        server.start();

        // Appelle une méthode
        LOGGER.info("{} server started successfully.", brand);

        // Stop the server on SIGINT
        // Embranchement : vérifie une condition
        if (ServerFlag.SHUTDOWN_ON_SIGNAL) Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void stop() {
        // Embranchement : vérifie une condition
        if (!stopped.compareAndSet(false, true)) return;
        // Appelle une méthode
        final String brand = MinecraftServer.getBrandName();
        // Appelle une méthode
        LOGGER.info("Stopping {} server.", brand);
        // Appelle une méthode
        scheduler.shutdown();
        // Appelle une méthode
        connection.shutdown();
        // Appelle une méthode
        server.stop();
        // Appelle une méthode
        LOGGER.info("Shutting down all thread pools.");
        // Appelle une méthode
        benchmark.disable();
        // Appelle une méthode
        dispatcher.shutdown();
        // Appelle une méthode
        LOGGER.info("{} server stopped successfully.", brand);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isAlive() {
        // Renvoie une valeur à l'appelant
        return started.get() && !stopped.get();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ServerSnapshot updateSnapshot(SnapshotUpdater updater) {
        // Affecte une valeur
        List<AtomicReference<InstanceSnapshot>> instanceRefs = new ArrayList<>();
        // Affecte une valeur
        Int2ObjectOpenHashMap<AtomicReference<EntitySnapshot>> entityRefs = new Int2ObjectOpenHashMap<>();
        // Boucle : répète un bloc
        for (Instance instance : instance.getInstances()) {
            // Appelle une méthode
            instanceRefs.add(updater.reference(instance));
            // Boucle : répète un bloc
            for (Entity entity : instance.getEntities()) {
                // Appelle une méthode
                entityRefs.put(entity.getEntityId(), updater.reference(entity));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new SnapshotImpl.Server(MappedCollection.plainReferences(instanceRefs), entityRefs);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private final class TickerImpl implements Ticker {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void tick(long nanoTime) {
            // Appelle une méthode
            var serverTickEvent = EventsJFR.newServerTick();
            // Appelle une méthode
            serverTickEvent.begin();
            // Appelle une méthode
            scheduler().processTick();

            // Connection tick (let waiting clients in, send keep alives, handle configuration players packets)
            // Appelle une méthode
            connection().tick(nanoTime);

            // Server tick (chunks/entities)
            // Appelle une méthode
            serverTick(nanoTime);

            // The click callback provider needs ticking to clean up the cache.
            // Appelle une méthode
            clickCallbackManager().tick(nanoTime);

            // Appelle une méthode
            scheduler().processTickEnd();

            // Flush all waiting packets
            // Appelle une méthode
            PacketViewableUtils.flush();

            // Monitoring
            // Début d'un bloc
            {
                // Appelle une méthode
                final double acquisitionTimeMs = Acquirable.resetAcquiringTime() / 1e6D;
                // Appelle une méthode
                final double tickTimeMs = (System.nanoTime() - nanoTime) / 1e6D;
                // Appelle une méthode
                final TickMonitor tickMonitor = new TickMonitor(tickTimeMs, acquisitionTimeMs);
                // Appelle une méthode
                EventDispatcher.call(new ServerTickMonitorEvent(tickMonitor));
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            serverTickEvent.commit();
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private void serverTick(long nanoStart) {
            // Appelle une méthode
            long milliStart = TimeUnit.NANOSECONDS.toMillis(nanoStart);
            // Tick all instances
            // Boucle : répète un bloc
            for (Instance instance : instance().getInstances()) {
                // Gestion des exceptions
                try {
                    // Appelle une méthode
                    instance.tick(milliStart);
                // Début d'une méthode/d'un bloc
                } catch (Exception e) {
                    // Appelle une méthode
                    exception().handleException(e);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Tick all chunks (and entities inside)
            // Appelle une méthode
            dispatcher().updateAndAwait(nanoStart);

            // Clear removed entities & update threads
            // Appelle une méthode
            final long tickDuration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - nanoStart);
            // Appelle une méthode
            final long remainingTickDuration = Tick.SERVER_TICKS.getDuration().toNanos() - tickDuration;
            // the nanoTimeout for refreshThreads is the remaining tick duration
            // Appelle une méthode
            dispatcher().refreshThreads(remainingTickDuration);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
