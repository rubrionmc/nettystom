// Déclaration du paquet de ce fichier
package net.minestom.server;

// Import d'une classe nécessaire
import net.minestom.server.advancements.AdvancementManager;
// Import d'une classe nécessaire
import net.minestom.server.adventure.ClickCallbackManager;
// Import d'une classe nécessaire
import net.minestom.server.adventure.bossbar.BossBarManager;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandManager;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.event.GlobalEventHandler;
// Import d'une classe nécessaire
import net.minestom.server.exception.ExceptionManager;
// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.InstanceManager;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockManager;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.rule.BlockPlacementRule;
// Import d'une classe nécessaire
import net.minestom.server.listener.manager.PacketListenerManager;
// Import d'une classe nécessaire
import net.minestom.server.monitoring.BenchmarkManager;
// Import d'une classe nécessaire
import net.minestom.server.network.ConnectionManager;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketParser;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.socket.Server;
// Import d'une classe nécessaire
import net.minestom.server.recipe.RecipeManager;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.scoreboard.TeamManager;
// Import d'une classe nécessaire
import net.minestom.server.snapshot.Snapshotable;
// Import d'une classe nécessaire
import net.minestom.server.thread.ThreadDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.timer.SchedulerManager;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.net.SocketAddress;

// Annotation pour l'élément suivant
@ApiStatus.NonExtendable
// Déclaration de type (classe/interface/enum/record)
public interface ServerProcess extends Registries, Snapshotable {
    // Appelle une méthode
    Auth auth();

    /**
     * Handles incoming connections/players.
     */
    // Appelle une méthode
    ConnectionManager connection();

    /**
     * Handles registered instances.
     */
    // Appelle une méthode
    InstanceManager instance();

    /**
     * Handles {@link net.minestom.server.instance.block.BlockHandler block handlers}
     * and {@link BlockPlacementRule placement rules}.
     */
    // Appelle une méthode
    BlockManager block();

    /**
     * Handles registered commands.
     */
    // Appelle une méthode
    CommandManager command();

    /**
     * Handles registered recipes shown to clients.
     */
    // Appelle une méthode
    RecipeManager recipe();

    /**
     * Handles registered teams.
     */
    // Appelle une méthode
    TeamManager team();

    /**
     * Gets the global event handler.
     * <p>
     * Used to register event callback at a global scale.
     */
    // Appelle une méthode
    GlobalEventHandler eventHandler();

    /**
     * Main scheduler ticked at the server rate.
     */
    // Appelle une méthode
    SchedulerManager scheduler();

    // Appelle une méthode
    BenchmarkManager benchmark();

    /**
     * Handles registered advancements.
     */
    // Appelle une méthode
    AdvancementManager advancement();

    /**
     * Handles registered boss bars.
     */
    // Appelle une méthode
    BossBarManager bossBar();

    /**
     * Handles all thrown exceptions from the server.
     */
    // Appelle une méthode
    ExceptionManager exception();

    /**
     * Handles incoming packets.
     */
    // Appelle une méthode
    PacketListenerManager packetListener();

    /**
     * Gets the object handling the client packets parsing.
     * <p>
     * Can be used if you want to convert a buffer to a client packet object.
     */
    // Appelle une méthode
    PacketParser.Client packetParser();

    /**
     * Exposed socket server.
     */
    // Appelle une méthode
    Server server();

    /**
     * Dispatcher for tickable game objects.
     */
    // Appelle une méthode
    ThreadDispatcher<Chunk, Entity> dispatcher();

    /**
     * Handles the server ticks.
     */
    // Appelle une méthode
    Ticker ticker();

    /**
     * The click callback manager.
     */
    // Appelle une méthode
    ClickCallbackManager clickCallbackManager();

    // Appelle une méthode
    void start(SocketAddress socketAddress);

    // Appelle une méthode
    void stop();

    // Appelle une méthode
    boolean isAlive();

    // Annotation pour l'élément suivant
    @ApiStatus.NonExtendable
    // Déclaration de type (classe/interface/enum/record)
    interface Ticker {
        // Appelle une méthode
        void tick(long nanoTime);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
