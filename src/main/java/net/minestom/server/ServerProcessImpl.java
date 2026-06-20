// Package declaration for this file
package net.minestom.server;

// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
// Import of a required class
import net.minestom.server.advancements.AdvancementManager;
// Import of a required class
import net.minestom.server.adventure.ClickCallbackManager;
// Import of a required class
import net.minestom.server.adventure.bossbar.BossBarManager;
// Import of a required class
import net.minestom.server.command.CommandManager;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.GlobalEventHandler;
// Import of a required class
import net.minestom.server.event.server.ServerTickMonitorEvent;
// Import of a required class
import net.minestom.server.exception.ExceptionManager;
// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.InstanceManager;
// Import of a required class
import net.minestom.server.instance.block.BlockManager;
// Import of a required class
import net.minestom.server.listener.manager.PacketListenerManager;
// Import of a required class
import net.minestom.server.monitoring.BenchmarkManager;
// Import of a required class
import net.minestom.server.monitoring.EventsJFR;
// Import of a required class
import net.minestom.server.monitoring.TickMonitor;
// Import of a required class
import net.minestom.server.network.ConnectionManager;
// Import of a required class
import net.minestom.server.network.packet.PacketParser;
// Import of a required class
import net.minestom.server.network.packet.PacketVanilla;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.network.socket.Server;
// Import of a required class
import net.minestom.server.recipe.RecipeManager;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.scoreboard.TeamManager;
// Import of a required class
import net.minestom.server.snapshot.*;
// Import of a required class
import net.minestom.server.thread.Acquirable;
// Import of a required class
import net.minestom.server.thread.ThreadDispatcher;
// Import of a required class
import net.minestom.server.thread.ThreadProvider;
// Import of a required class
import net.minestom.server.timer.SchedulerManager;
// Import of a required class
import net.minestom.server.utils.PacketViewableUtils;
// Import of a required class
import net.minestom.server.utils.collection.MappedCollection;
// Import of a required class
import net.minestom.server.utils.time.Tick;
// Import of a required class
import org.slf4j.Logger;
// Import of a required class
import org.slf4j.LoggerFactory;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.net.SocketAddress;
// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.concurrent.TimeUnit;
// Import of a required class
import java.util.concurrent.atomic.AtomicBoolean;
// Import of a required class
import java.util.concurrent.atomic.AtomicReference;

// Type declaration (class/interface/enum/record)
final class ServerProcessImpl implements ServerProcess, Registries.Delegating {
    // Calls a method
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerProcessImpl.class);

    // Code statement
    private final Auth auth;

    // Code statement
    private final ExceptionManager exception;
    // Code statement
    private final Registries registries;

    // Code statement
    private final ConnectionManager connection;
    // Code statement
    private final PacketListenerManager packetListener;
    // Code statement
    private final PacketParser.Client packetParser;
    // Code statement
    private final InstanceManager instance;
    // Code statement
    private final BlockManager block;
    // Code statement
    private final CommandManager command;
    // Code statement
    private final RecipeManager recipe;
    // Code statement
    private final TeamManager team;
    // Code statement
    private final GlobalEventHandler eventHandler;
    // Code statement
    private final SchedulerManager scheduler;
    // Code statement
    private final BenchmarkManager benchmark;
    // Code statement
    private final AdvancementManager advancement;
    // Code statement
    private final BossBarManager bossBar;
    // Code statement
    private final ClickCallbackManager clickCallbackManager;

    // Code statement
    private final Server server;

    // Code statement
    private final ThreadDispatcher<Chunk, Entity> dispatcher;
    // Code statement
    private final Ticker ticker;

    // Calls a method
    private final AtomicBoolean started = new AtomicBoolean();
    // Calls a method
    private final AtomicBoolean stopped = new AtomicBoolean();

    // Start of a method/block
    public ServerProcessImpl(Auth auth) {
        // Access to the current/parent object
        this.auth = auth;
        // Access to the current/parent object
        this.exception = new ExceptionManager();
        // Access to the current/parent object
        this.registries = Registries.vanilla();

        // Access to the current/parent object
        this.connection = new ConnectionManager();
        // Access to the current/parent object
        this.packetListener = new PacketListenerManager();
        // Access to the current/parent object
        this.packetParser = PacketVanilla.CLIENT_PACKET_PARSER;
        // Access to the current/parent object
        this.instance = new InstanceManager(this);
        // Access to the current/parent object
        this.block = new BlockManager();
        // Access to the current/parent object
        this.command = new CommandManager();
        // Access to the current/parent object
        this.recipe = new RecipeManager();
        // Access to the current/parent object
        this.team = new TeamManager();
        // Access to the current/parent object
        this.eventHandler = new GlobalEventHandler();
        // Access to the current/parent object
        this.scheduler = new SchedulerManager();
        // Access to the current/parent object
        this.benchmark = new BenchmarkManager();
        // Access to the current/parent object
        this.advancement = new AdvancementManager();
        // Access to the current/parent object
        this.bossBar = new BossBarManager();
        // Access to the current/parent object
        this.clickCallbackManager = new ClickCallbackManager();

        // Access to the current/parent object
        this.server = new Server(packetParser);

        // Access to the current/parent object
        this.dispatcher = ThreadDispatcher.dispatcher(ThreadProvider.counter(), ServerFlag.DISPATCHER_THREADS);
        // Access to the current/parent object
        this.ticker = new TickerImpl();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Auth auth() {
        // Returns a value to the caller
        return auth;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ExceptionManager exception() {
        // Returns a value to the caller
        return exception;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Registries registries() {
        // Returns a value to the caller
        return registries;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ConnectionManager connection() {
        // Returns a value to the caller
        return connection;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public InstanceManager instance() {
        // Returns a value to the caller
        return instance;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public BlockManager block() {
        // Returns a value to the caller
        return block;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public CommandManager command() {
        // Returns a value to the caller
        return command;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public RecipeManager recipe() {
        // Returns a value to the caller
        return recipe;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public TeamManager team() {
        // Returns a value to the caller
        return team;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public GlobalEventHandler eventHandler() {
        // Returns a value to the caller
        return eventHandler;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public SchedulerManager scheduler() {
        // Returns a value to the caller
        return scheduler;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public BenchmarkManager benchmark() {
        // Returns a value to the caller
        return benchmark;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public AdvancementManager advancement() {
        // Returns a value to the caller
        return advancement;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public BossBarManager bossBar() {
        // Returns a value to the caller
        return bossBar;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public PacketListenerManager packetListener() {
        // Returns a value to the caller
        return packetListener;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public PacketParser.Client packetParser() {
        // Returns a value to the caller
        return packetParser;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Server server() {
        // Returns a value to the caller
        return server;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ThreadDispatcher<Chunk, Entity> dispatcher() {
        // Returns a value to the caller
        return dispatcher;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Ticker ticker() {
        // Returns a value to the caller
        return ticker;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ClickCallbackManager clickCallbackManager() {
        // Returns a value to the caller
        return clickCallbackManager;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void start(SocketAddress socketAddress) {
        // Branch: checks a condition
        if (!started.compareAndSet(false, true)) {
            // Throws an exception
            throw new IllegalStateException("Server already started");
        // End of a block/expression
        }

        // Calls a method
        final String brand = MinecraftServer.getBrandName();
        // Calls a method
        LOGGER.info("Starting {} ({}) server.", brand, Git.version());
        // Multiple branching (switch/case)
        switch (auth) {
            // Multiple branching (switch/case)
            case Auth.Offline ignored ->
                    // Calls a method
                    LOGGER.info("Running in offline mode. Beware that this is not secure and players can impersonate each other.");
            // Multiple branching (switch/case)
            case Auth.Online ignored -> LOGGER.info("Running in online mode with Mojang's authentication.");
            // Multiple branching (switch/case)
            case Auth.Velocity ignored -> LOGGER.info("Running in Velocity mode with modern IP forwarding.");
            // Multiple branching (switch/case)
            case Auth.Bungee bungee -> {
                // Branch: checks a condition
                if (bungee.guard()) {
                    // Calls a method
                    LOGGER.info("Running in BungeeCord mode, using legacy IP forwarding with Guard enabled.");
                // Alternative branch of the condition
                } else {
                    // Calls a method
                    LOGGER.info("Running in BungeeCord mode without BungeeGuard. Be sure to configure your firewall to prevent direct connections.");
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Init server
        // Exception handling
        try {
            // Calls a method
            server.init(socketAddress);
        // Start of a method/block
        } catch (IOException e) {
            // Calls a method
            exception.handleException(e);
            // Throws an exception
            throw new RuntimeException(e);
        // End of a block/expression
        }

        // Start server
        // Calls a method
        server.start();

        // Calls a method
        LOGGER.info("{} server started successfully.", brand);

        // Stop the server on SIGINT
        // Branch: checks a condition
        if (ServerFlag.SHUTDOWN_ON_SIGNAL) Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void stop() {
        // Branch: checks a condition
        if (!stopped.compareAndSet(false, true)) return;
        // Calls a method
        final String brand = MinecraftServer.getBrandName();
        // Calls a method
        LOGGER.info("Stopping {} server.", brand);
        // Calls a method
        scheduler.shutdown();
        // Calls a method
        connection.shutdown();
        // Calls a method
        server.stop();
        // Calls a method
        LOGGER.info("Shutting down all thread pools.");
        // Calls a method
        benchmark.disable();
        // Calls a method
        dispatcher.shutdown();
        // Calls a method
        LOGGER.info("{} server stopped successfully.", brand);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isAlive() {
        // Returns a value to the caller
        return started.get() && !stopped.get();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ServerSnapshot updateSnapshot(SnapshotUpdater updater) {
        // Calls a method
        List<AtomicReference<InstanceSnapshot>> instanceRefs = new ArrayList<>();
        // Calls a method
        Int2ObjectOpenHashMap<AtomicReference<EntitySnapshot>> entityRefs = new Int2ObjectOpenHashMap<>();
        // Loop: repeats a block
        for (Instance instance : instance.getInstances()) {
            // Calls a method
            instanceRefs.add(updater.reference(instance));
            // Loop: repeats a block
            for (Entity entity : instance.getEntities()) {
                // Calls a method
                entityRefs.put(entity.getEntityId(), updater.reference(entity));
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return new SnapshotImpl.Server(MappedCollection.plainReferences(instanceRefs), entityRefs);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private final class TickerImpl implements Ticker {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void tick(long nanoTime) {
            // Calls a method
            var serverTickEvent = EventsJFR.newServerTick();
            // Calls a method
            serverTickEvent.begin();
            // Calls a method
            scheduler().processTick();

            // Connection tick (let waiting clients in, send keep alives, handle configuration players packets)
            // Calls a method
            connection().tick(nanoTime);

            // Server tick (chunks/entities)
            // Calls a method
            serverTick(nanoTime);

            // The click callback provider needs ticking to clean up the cache.
            // Calls a method
            clickCallbackManager().tick(nanoTime);

            // Calls a method
            scheduler().processTickEnd();

            // Flush all waiting packets
            // Calls a method
            PacketViewableUtils.flush();

            // Monitoring
            // Start of a block
            {
                // Calls a method
                final double acquisitionTimeMs = Acquirable.resetAcquiringTime() / 1e6D;
                // Calls a method
                final double tickTimeMs = (System.nanoTime() - nanoTime) / 1e6D;
                // Calls a method
                final TickMonitor tickMonitor = new TickMonitor(tickTimeMs, acquisitionTimeMs);
                // Calls a method
                EventDispatcher.call(new ServerTickMonitorEvent(tickMonitor));
            // End of a block/expression
            }
            // Calls a method
            serverTickEvent.commit();
        // End of a block/expression
        }

        // Start of a method/block
        private void serverTick(long nanoStart) {
            // Calls a method
            long milliStart = TimeUnit.NANOSECONDS.toMillis(nanoStart);
            // Tick all instances
            // Loop: repeats a block
            for (Instance instance : instance().getInstances()) {
                // Exception handling
                try {
                    // Calls a method
                    instance.tick(milliStart);
                // Start of a method/block
                } catch (Exception e) {
                    // Calls a method
                    exception().handleException(e);
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Tick all chunks (and entities inside)
            // Calls a method
            dispatcher().updateAndAwait(nanoStart);

            // Clear removed entities & update threads
            // Calls a method
            final long tickDuration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - nanoStart);
            // Calls a method
            final long remainingTickDuration = Tick.SERVER_TICKS.getDuration().toNanos() - tickDuration;
            // the nanoTimeout for refreshThreads is the remaining tick duration
            // Calls a method
            dispatcher().refreshThreads(remainingTickDuration);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
