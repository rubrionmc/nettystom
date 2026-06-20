// Déclaration du paquet de ce fichier
package net.minestom.server.instance.generator;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicReference;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNull;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class GeneratorForkConsumerIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void empty(Env env) {
        // Appelle une méthode
        var manager = env.process().instance();
        // Appelle une méthode
        var instance = manager.createInstanceContainer();
        // Appelle une méthode
        AtomicReference<Exception> failed = new AtomicReference<>();
        // Début d'une méthode/d'un bloc
        instance.setGenerator(unit -> {
            // Gestion des exceptions
            try {
                // Début d'une méthode/d'un bloc
                unit.fork(setter -> {
                // Fin d'un bloc/d'une expression
                });
            // Début d'une méthode/d'un bloc
            } catch (Exception e) {
                // Appelle une méthode
                failed.set(e);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Appelle une méthode
        assertNull(failed.get(), "Failed: " + failed.get());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void local(Env env) {
        // Appelle une méthode
        var manager = env.process().instance();
        // Appelle une méthode
        var instance = manager.createInstanceContainer();
        // Début d'une méthode/d'un bloc
        instance.setGenerator(unit -> unit.fork(setter -> {
            // Appelle une méthode
            var dynamic = (GeneratorImpl.DynamicFork) setter;
            // Appelle une méthode
            assertNull(dynamic.minSection);
            // Appelle une méthode
            assertEquals(0, dynamic.width);
            // Appelle une méthode
            assertEquals(0, dynamic.height);
            // Appelle une méthode
            assertEquals(0, dynamic.depth);
            // Appelle une méthode
            setter.setBlock(unit.absoluteStart(), Block.STONE);
            // Appelle une méthode
            assertEquals(unit.absoluteStart(), dynamic.minSection);
            // Appelle une méthode
            assertEquals(1, dynamic.width);
            // Appelle une méthode
            assertEquals(1, dynamic.height);
            // Appelle une méthode
            assertEquals(1, dynamic.depth);
        // Instruction de code
        }));
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Appelle une méthode
        assertEquals(Block.STONE, instance.getBlock(0, -64, 0));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void doubleLocal(Env env) {
        // Appelle une méthode
        var manager = env.process().instance();
        // Appelle une méthode
        var instance = manager.createInstanceContainer();
        // Début d'une méthode/d'un bloc
        instance.setGenerator(unit -> unit.fork(setter -> {
            // Appelle une méthode
            setter.setBlock(unit.absoluteStart(), Block.STONE);
            // Appelle une méthode
            setter.setBlock(unit.absoluteStart().add(1, 0, 0), Block.STONE);
        // Instruction de code
        }));
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Appelle une méthode
        assertEquals(Block.STONE, instance.getBlock(0, -64, 0));
        // Appelle une méthode
        assertEquals(Block.STONE, instance.getBlock(1, -64, 0));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void neighborZ(Env env) {
        // Appelle une méthode
        var manager = env.process().instance();
        // Appelle une méthode
        var instance = manager.createInstanceContainer();
        // Début d'une méthode/d'un bloc
        instance.setGenerator(unit -> unit.fork(setter -> {
            // Appelle une méthode
            var dynamic = (GeneratorImpl.DynamicFork) setter;
            // Appelle une méthode
            assertNull(dynamic.minSection);
            // Appelle une méthode
            assertEquals(0, dynamic.width);
            // Appelle une méthode
            assertEquals(0, dynamic.height);
            // Appelle une méthode
            assertEquals(0, dynamic.depth);
            // Appelle une méthode
            setter.setBlock(unit.absoluteStart(), Block.STONE);
            // Appelle une méthode
            setter.setBlock(unit.absoluteStart().add(0, 0, 16), Block.GRASS_BLOCK);
            // Appelle une méthode
            assertEquals(unit.absoluteStart(), dynamic.minSection);
            // Appelle une méthode
            assertEquals(1, dynamic.width);
            // Appelle une méthode
            assertEquals(1, dynamic.height);
            // Appelle une méthode
            assertEquals(2, dynamic.depth);
        // Instruction de code
        }));
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Appelle une méthode
        instance.setGenerator(null);
        // Appelle une méthode
        instance.loadChunk(0, 1).join();
        // Appelle une méthode
        assertEquals(Block.STONE, instance.getBlock(0, -64, 0));
        // Appelle une méthode
        assertEquals(Block.GRASS_BLOCK, instance.getBlock(0, -64, 16));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void neighborX(Env env) {
        // Appelle une méthode
        var manager = env.process().instance();
        // Appelle une méthode
        var instance = manager.createInstanceContainer();
        // Début d'une méthode/d'un bloc
        instance.setGenerator(unit -> unit.fork(setter -> {
            // Appelle une méthode
            var dynamic = (GeneratorImpl.DynamicFork) setter;
            // Appelle une méthode
            assertNull(dynamic.minSection);
            // Appelle une méthode
            assertEquals(0, dynamic.width);
            // Appelle une méthode
            assertEquals(0, dynamic.height);
            // Appelle une méthode
            assertEquals(0, dynamic.depth);
            // Appelle une méthode
            setter.setBlock(unit.absoluteStart(), Block.STONE);
            // Appelle une méthode
            setter.setBlock(unit.absoluteStart().add(16, 0, 0), Block.GRASS_BLOCK);
            // Appelle une méthode
            assertEquals(unit.absoluteStart(), dynamic.minSection);
            // Appelle une méthode
            assertEquals(2, dynamic.width);
            // Appelle une méthode
            assertEquals(1, dynamic.height);
            // Appelle une méthode
            assertEquals(1, dynamic.depth);
        // Instruction de code
        }));
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Appelle une méthode
        instance.setGenerator(null);
        // Appelle une méthode
        instance.loadChunk(1, 0).join();
        // Appelle une méthode
        assertEquals(Block.STONE, instance.getBlock(0, -64, 0));
        // Appelle une méthode
        assertEquals(Block.GRASS_BLOCK, instance.getBlock(16, -64, 0));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void neighborY(Env env) {
        // Appelle une méthode
        var manager = env.process().instance();
        // Appelle une méthode
        var instance = manager.createInstanceContainer();
        // Début d'une méthode/d'un bloc
        instance.setGenerator(unit -> unit.fork(setter -> {
            // Appelle une méthode
            var dynamic = (GeneratorImpl.DynamicFork) setter;
            // Appelle une méthode
            assertNull(dynamic.minSection);
            // Appelle une méthode
            assertEquals(0, dynamic.width);
            // Appelle une méthode
            assertEquals(0, dynamic.height);
            // Appelle une méthode
            assertEquals(0, dynamic.depth);
            // Appelle une méthode
            setter.setBlock(unit.absoluteStart(), Block.STONE);
            // Appelle une méthode
            setter.setBlock(unit.absoluteStart().add(0, 16, 0), Block.GRASS_BLOCK);
            // Appelle une méthode
            assertEquals(unit.absoluteStart(), dynamic.minSection);
            // Appelle une méthode
            assertEquals(1, dynamic.width);
            // Appelle une méthode
            assertEquals(2, dynamic.height);
            // Appelle une méthode
            assertEquals(1, dynamic.depth);
        // Instruction de code
        }));
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Appelle une méthode
        assertEquals(Block.STONE, instance.getBlock(0, -64, 0));
        // Appelle une méthode
        assertEquals(Block.GRASS_BLOCK, instance.getBlock(0, -48, 0));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void verticalAndHorizontalSectionBorders(Env env) {
        // Appelle une méthode
        var manager = env.process().instance();
        // Appelle une méthode
        var instance = manager.createInstanceContainer();
        // Appelle une méthode
        Set<Point> points = ConcurrentHashMap.newKeySet();
        // Début d'une méthode/d'un bloc
        instance.setGenerator(unit -> {
            // Appelle une méthode
            final Point start = unit.absoluteStart().withY(96);
            // Début d'une méthode/d'un bloc
            unit.fork(setter -> {
                // Appelle une méthode
                var dynamic = (GeneratorImpl.DynamicFork) setter;
                // Boucle : répète un bloc
                for (int i = 0; i < 16; i++) {
                    // Appelle une méthode
                    setter.setBlock(start.add(i, 0, 0), Block.STONE);
                    // Appelle une méthode
                    setter.setBlock(start.add(-i, 0, 0), Block.STONE);
                    // Appelle une méthode
                    setter.setBlock(start.add(0, i, 0), Block.STONE);
                    // Appelle une méthode
                    setter.setBlock(start.add(0, -i, 0), Block.STONE);

                    // Appelle une méthode
                    points.add(start.add(i, 0, 0));
                    // Appelle une méthode
                    points.add(start.add(-i, 0, 0));
                    // Appelle une méthode
                    points.add(start.add(0, i, 0));
                    // Appelle une méthode
                    points.add(start.add(0, -i, 0));
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                assertEquals(2, dynamic.width);
                // Appelle une méthode
                assertEquals(2, dynamic.height);
                // Appelle une méthode
                assertEquals(1, dynamic.depth);
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Boucle : répète un bloc
        for (Point point : points) {
            // Embranchement : vérifie une condition
            if (!instance.isChunkLoaded(point)) continue;
            // Appelle une méthode
            assertEquals(Block.STONE, instance.getBlock(point), point.toString());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
