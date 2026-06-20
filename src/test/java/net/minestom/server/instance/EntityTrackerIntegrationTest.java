// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertSame;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class EntityTrackerIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void maxDistance(Env env) {
        // Appelle une méthode
        final Instance instance = env.createFlatInstance();
        // Appelle une méthode
        final Pos spawnPos = new Pos(0, 41, 0);
        // Affecte une valeur
        final int viewDistanceInChunks = ServerFlag.ENTITY_VIEW_DISTANCE;

        // Appelle une méthode
        final Player viewer = env.createPlayer(instance, spawnPos);
        // Appelle une méthode
        final AtomicInteger viewersCount = new AtomicInteger();
        // Affecte une valeur
        final Entity entity = new Entity(EntityType.ZOMBIE) {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void updateNewViewer(Player player) {
                // Appelle une méthode
                viewersCount.incrementAndGet();
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void updateOldViewer(Player player) {
                // Appelle une méthode
                viewersCount.decrementAndGet();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        entity.setInstance(instance, spawnPos).join();
        // Appelle une méthode
        assertEquals(1, viewersCount.get());
        // Instruction de code
        viewer.teleport(new Pos(viewDistanceInChunks * 16 + 15, 41, 0)).join(); // viewer at max chunk range
        // Appelle une méthode
        assertEquals(1, viewersCount.get());
        // Instruction de code
        viewer.teleport(new Pos(viewDistanceInChunks * 16 + 16, 41, 0)).join(); // viewer outside of chunk range
        // Appelle une méthode
        assertEquals(0, viewersCount.get());
        // Instruction de code
        viewer.teleport(new Pos(viewDistanceInChunks * 16 + 15, 41, 0)).join(); // viewer back to max chunk range
        // Appelle une méthode
        assertEquals(1, viewersCount.get());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cornerInstanceSwap(Env env) {
        // Appelle une méthode
        final Instance instance = env.createFlatInstance();
        // Appelle une méthode
        final Instance anotherInstance = env.createFlatInstance();
        // Appelle une méthode
        final Pos spawnPos = new Pos(0, 41, 0);
        // Affecte une valeur
        final int viewDistanceInChunks = ServerFlag.ENTITY_VIEW_DISTANCE;

        // Appelle une méthode
        final Player viewer = env.createPlayer(instance, spawnPos);
        // Appelle une méthode
        final AtomicInteger viewersCount = new AtomicInteger();
        // Affecte une valeur
        final Entity entity = new Entity(EntityType.ZOMBIE) {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void updateNewViewer(Player player) {
                // Appelle une méthode
                viewersCount.incrementAndGet();
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void updateOldViewer(Player player) {
                // Appelle une méthode
                viewersCount.decrementAndGet();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        entity.setInstance(instance, spawnPos).join();
        // Appelle une méthode
        assertEquals(1, viewersCount.get());
        // Instruction de code
        viewer.teleport(new Pos(viewDistanceInChunks * 16 + 15, 41, 0)).join(); // viewer at max chunk range
        // Appelle une méthode
        assertEquals(1, viewersCount.get());
        // Instruction de code
        viewer.setInstance(anotherInstance, spawnPos).join(); // viewer swapped instance
        // Appelle une méthode
        assertEquals(0, viewersCount.get());
        // Instruction de code
        viewer.setInstance(instance, spawnPos).join(); // viewer back to spawn
        // Appelle une méthode
        assertEquals(1, viewersCount.get());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void viewable(Env env) {
        // Appelle une méthode
        final Instance instance = env.createFlatInstance();
        // Appelle une méthode
        final Pos spawnPos = new Pos(0, 41, 0);
        // Appelle une méthode
        var viewable = instance.getEntityTracker().viewable(spawnPos.chunkX(), spawnPos.chunkZ());
        // Appelle une méthode
        assertEquals(0, viewable.getViewers().size());

        // Appelle une méthode
        final Player player = env.createPlayer(instance, spawnPos);
        // Appelle une méthode
        assertEquals(1, viewable.getViewers().size());
        // Appelle une méthode
        assertSame(viewable, instance.getEntityTracker().viewable(spawnPos.chunkX(), spawnPos.chunkZ()));

        // Appelle une méthode
        player.teleport(new Pos(10_000, 41, 0)).join();
        // Appelle une méthode
        assertEquals(0, viewable.getViewers().size());

        // Appelle une méthode
        player.teleport(spawnPos).join();
        // Appelle une méthode
        assertEquals(1, viewable.getViewers().size());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void viewableShared(Env env) {
        // Appelle une méthode
        final InstanceContainer instance = (InstanceContainer) env.createFlatInstance();
        // Appelle une méthode
        var shared = env.process().instance().createSharedInstance(instance);
        // Appelle une méthode
        var sharedList = instance.getSharedInstances();

        // Appelle une méthode
        final Pos spawnPos = new Pos(0, 41, 0);
        // Appelle une méthode
        var viewable = instance.getEntityTracker().viewable(sharedList, spawnPos.chunkX(), spawnPos.chunkZ());
        // Appelle une méthode
        assertEquals(0, viewable.getViewers().size());

        // Appelle une méthode
        final Player player = env.createPlayer(instance, spawnPos);
        // Appelle une méthode
        assertEquals(1, viewable.getViewers().size());
        // Appelle une méthode
        assertSame(viewable, instance.getEntityTracker().viewable(sharedList, spawnPos.chunkX(), spawnPos.chunkZ()));

        // Appelle une méthode
        player.setInstance(shared).join();
        // Appelle une méthode
        assertEquals(1, viewable.getViewers().size());

        // Appelle une méthode
        player.teleport(new Pos(10_000, 41, 0)).join();
        // Appelle une méthode
        assertEquals(0, viewable.getViewers().size());

        // Appelle une méthode
        var shared2 = env.process().instance().createSharedInstance(instance);
        // Appelle une méthode
        player.setInstance(shared2, spawnPos).join();
        // Appelle une méthode
        assertEquals(1, viewable.getViewers().size());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
