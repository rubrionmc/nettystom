// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.event.instance.InstanceTickEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerMoveEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerTickEvent;
// Import d'une classe nécessaire
import net.minestom.server.world.DimensionType;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.lang.ref.WeakReference;
// Import d'une classe nécessaire
import java.util.UUID;

// Import statique d'un membre
import static net.minestom.testing.TestUtils.waitUntilCleared;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class InstanceUnregisterIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sharedInstance(Env env) {
        // Ensure that unregistering a shared instance does not unload the container chunks
        // Appelle une méthode
        var instanceManager = env.process().instance();
        // Appelle une méthode
        var instance = instanceManager.createInstanceContainer();
        // Appelle une méthode
        var shared1 = instanceManager.createSharedInstance(instance);
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(shared1, new Pos(0, 40, 0));

        // Appelle une méthode
        var listener = env.listen(PlayerTickEvent.class);
        // Appelle une méthode
        listener.followup();
        // Appelle une méthode
        env.tick();

        // Appelle une méthode
        var acquired = player.acquirable().lock();
        // Appelle une méthode
        player.setInstance(instanceManager.createSharedInstance(instance)).join();
        // Appelle une méthode
        acquired.unlock();
        // Appelle une méthode
        listener.followup();
        // Appelle une méthode
        env.tick();

        // Appelle une méthode
        instanceManager.unregisterInstance(shared1);
        // Appelle une méthode
        listener.followup();
        // Appelle une méthode
        env.tick();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void instanceGC(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var ref = new WeakReference<>(instance);
        // Appelle une méthode
        env.process().instance().unregisterInstance(instance);

        //noinspection UnusedAssignment
        // Affecte une valeur
        instance = null;
        // Appelle une méthode
        waitUntilCleared(ref);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void instanceNodeGC(Env env) {
        // Déclaration de type (classe/interface/enum/record)
        final class Game {
            // Instruction de code
            final Instance instance;

            // Début d'une méthode/d'un bloc
            Game(Env env) {
                // Appelle une méthode
                instance = env.process().instance().createInstanceContainer();
                // Appelle une méthode
                instance.eventNode().addListener(PlayerMoveEvent.class, e -> System.out.println(instance));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        var game = new Game(env);
        // Appelle une méthode
        var ref = new WeakReference<>(game);
        // Appelle une méthode
        env.process().instance().unregisterInstance(game.instance);

        //noinspection UnusedAssignment
        // Affecte une valeur
        game = null;
        // Appelle une méthode
        waitUntilCleared(ref);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void chunkGC(Env env) {
        // Ensure that unregistering an instance does release its chunks
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var chunk = instance.loadChunk(0, 0).join();
        // Appelle une méthode
        var ref = new WeakReference<>(chunk);
        // Appelle une méthode
        instance.unloadChunk(chunk);
        // Appelle une méthode
        env.process().instance().unregisterInstance(instance);
        // Instruction de code
        env.tick(); // Required to remove the chunk from the thread dispatcher

        //noinspection UnusedAssignment
        // Affecte une valeur
        chunk = null;
        // Appelle une méthode
        waitUntilCleared(ref);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testGCWithEventsLambda(Env env) {
        // Appelle une méthode
        var ref = new WeakReference<>(new InstanceContainer(UUID.randomUUID(), DimensionType.OVERWORLD));
        // Appelle une méthode
        env.process().instance().registerInstance(ref.get());

        // Appelle une méthode
        tmp(ref.get());

        // Appelle une méthode
        ref.get().tick(0);
        // Appelle une méthode
        env.process().instance().unregisterInstance(ref.get());

        // Appelle une méthode
        waitUntilCleared(ref);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void tmp(InstanceContainer instanceContainer) {
        // Début d'une méthode/d'un bloc
        instanceContainer.eventNode().addListener(InstanceTickEvent.class, (e) -> {
            // Appelle une méthode
            var uuid = instanceContainer.getUuid();
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
