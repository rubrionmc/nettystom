// Package declaration for this file
package net.minestom.server;

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
import net.minestom.server.event.GlobalEventHandler;
// Import of a required class
import net.minestom.server.exception.ExceptionManager;
// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import net.minestom.server.instance.InstanceManager;
// Import of a required class
import net.minestom.server.instance.block.BlockManager;
// Import of a required class
import net.minestom.server.instance.block.rule.BlockPlacementRule;
// Import of a required class
import net.minestom.server.listener.manager.PacketListenerManager;
// Import of a required class
import net.minestom.server.monitoring.BenchmarkManager;
// Import of a required class
import net.minestom.server.network.ConnectionManager;
// Import of a required class
import net.minestom.server.network.packet.PacketParser;
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
import net.minestom.server.snapshot.Snapshotable;
// Import of a required class
import net.minestom.server.thread.ThreadDispatcher;
// Import of a required class
import net.minestom.server.timer.SchedulerManager;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.net.SocketAddress;

// Annotation for the following element
@ApiStatus.NonExtendable
// Type declaration (class/interface/enum/record)
public interface ServerProcess extends Registries, Snapshotable {
    // Calls a method
    Auth auth();

    /**
     * Handles incoming connections/players.
     */
    // Calls a method
    ConnectionManager connection();

    /**
     * Handles registered instances.
     */
    // Calls a method
    InstanceManager instance();

    /**
     * Handles {@link net.minestom.server.instance.block.BlockHandler block handlers}
     * and {@link BlockPlacementRule placement rules}.
     */
    // Calls a method
    BlockManager block();

    /**
     * Handles registered commands.
     */
    // Calls a method
    CommandManager command();

    /**
     * Handles registered recipes shown to clients.
     */
    // Calls a method
    RecipeManager recipe();

    /**
     * Handles registered teams.
     */
    // Calls a method
    TeamManager team();

    /**
     * Gets the global event handler.
     * <p>
     * Used to register event callback at a global scale.
     */
    // Calls a method
    GlobalEventHandler eventHandler();

    /**
     * Main scheduler ticked at the server rate.
     */
    // Calls a method
    SchedulerManager scheduler();

    // Calls a method
    BenchmarkManager benchmark();

    /**
     * Handles registered advancements.
     */
    // Calls a method
    AdvancementManager advancement();

    /**
     * Handles registered boss bars.
     */
    // Calls a method
    BossBarManager bossBar();

    /**
     * Handles all thrown exceptions from the server.
     */
    // Calls a method
    ExceptionManager exception();

    /**
     * Handles incoming packets.
     */
    // Calls a method
    PacketListenerManager packetListener();

    /**
     * Gets the object handling the client packets parsing.
     * <p>
     * Can be used if you want to convert a buffer to a client packet object.
     */
    // Calls a method
    PacketParser.Client packetParser();

    /**
     * Exposed socket server.
     */
    // Calls a method
    Server server();

    /**
     * Dispatcher for tickable game objects.
     */
    // Calls a method
    ThreadDispatcher<Chunk, Entity> dispatcher();

    /**
     * Handles the server ticks.
     */
    // Calls a method
    Ticker ticker();

    /**
     * The click callback manager.
     */
    // Calls a method
    ClickCallbackManager clickCallbackManager();

    // Calls a method
    void start(SocketAddress socketAddress);

    // Calls a method
    void stop();

    // Calls a method
    boolean isAlive();

    // Annotation for the following element
    @ApiStatus.NonExtendable
    // Type declaration (class/interface/enum/record)
    interface Ticker {
        // Calls a method
        void tick(long nanoTime);
    // End of a block/expression
    }
// End of a block/expression
}
