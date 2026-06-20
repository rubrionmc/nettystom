// Déclaration du paquet de ce fichier
package net.minestom.server.collision;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockHandler;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.HashSet;
// Import d'une classe nécessaire
import java.util.Set;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertTrue;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class EntityBlockTouchTickIntegrationTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckTouchTick(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Appelle une méthode
        Set<Point> positions = new HashSet<>();
        // Affecte une valeur
        var handler = new BlockHandler() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void onTouch(Touch touch) {
                // Appelle une méthode
                assertTrue(positions.add(touch.getBlockPosition()));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Key getKey() {
                // Renvoie une valeur à l'appelant
                return Key.key("minestom:test");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Appelle une méthode
        instance.setBlock(0, 42, 0, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(0, 42, 1, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(0, 43, 1, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(0, 43, -1, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(1, 42, 1, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(1, 42, 0, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(0, 42, 10, Block.STONE.withHandler(handler));

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 42, 0.7)).join();

        // Appelle une méthode
        entity.tick(0);

        // Instruction de code
        assertEquals(Set.of(new Vec(0, 42, 0),
                // Crée un nouvel objet
                new Vec(0, 42, 1),
                // Crée un nouvel objet
                new Vec(0, 43, 1)),
                // Instruction de code
                positions);

        // Appelle une méthode
        assertEquals(instance, entity.getInstance());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckTouchTickFarZ(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.loadChunk(new Pos(1000, 1000, 1000));

        // Appelle une méthode
        Set<Point> positions = new HashSet<>();
        // Affecte une valeur
        var handler = new BlockHandler() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void onTouch(Touch touch) {
                // Appelle une méthode
                assertTrue(positions.add(touch.getBlockPosition()));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Key getKey() {
                // Renvoie une valeur à l'appelant
                return Key.key("minestom:test");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Appelle une méthode
        instance.setBlock(1000, 42, 1000, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(1000, 42, 1001, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(1000, 43, 1001, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(1000, 43, 999, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(1001, 42, 1001, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(1001, 42, 1000, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(1000, 42, 1010, Block.STONE.withHandler(handler));

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(1000, 42, 1000.7)).join();

        // Appelle une méthode
        entity.tick(0);

        // Instruction de code
        assertEquals(Set.of(
                // Crée un nouvel objet
                new Vec(1000, 42, 1000),
                // Crée un nouvel objet
                new Vec(1000, 42, 1001),
                // Crée un nouvel objet
                new Vec(1000, 43, 1001)
            // Instruction de code
            ), positions);

        // Appelle une méthode
        assertEquals(instance, entity.getInstance());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckTouchTickFarX(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.loadChunk(new Pos(1000, 1000, 1000));

        // Appelle une méthode
        Set<Point> positions = new HashSet<>();
        // Affecte une valeur
        var handler = new BlockHandler() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void onTouch(Touch touch) {
                // Appelle une méthode
                assertTrue(positions.add(touch.getBlockPosition()));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Key getKey() {
                // Renvoie une valeur à l'appelant
                return Key.key("minestom:test");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Appelle une méthode
        instance.setBlock(1000, 42, 1000, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(1000, 42, 1001, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(1000, 43, 1001, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(1000, 43, 999, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(1001, 43, 999, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(1001, 42, 999, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(1001, 42, 1001, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(1001, 43, 1000, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(999, 42, 1001, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(1001, 43, 1001, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(1001, 42, 1000, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(1000, 42, 1010, Block.STONE.withHandler(handler));

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(1000.699, 42, 1000)).join();

        // Appelle une méthode
        entity.tick(0);

        // Instruction de code
        assertEquals(Set.of(
                // Crée un nouvel objet
                new Vec(1000, 43, 999),
                // Crée un nouvel objet
                new Vec(1000, 42, 1000),
                // Crée un nouvel objet
                new Vec(1001, 43, 1000),
                // Crée un nouvel objet
                new Vec(1001, 42, 1000),
                // Crée un nouvel objet
                new Vec(1001, 42, 999),
                // Crée un nouvel objet
                new Vec(1001, 43, 999)
            // Instruction de code
            ), positions);

        // Appelle une méthode
        assertEquals(instance, entity.getInstance());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityPhysicsCheckTouchTickFarNegative(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.loadChunk(new Pos(-1000, 44, -1000));

        // Appelle une méthode
        Set<Point> positions = new HashSet<>();
        // Affecte une valeur
        var handler = new BlockHandler() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void onTouch(Touch touch) {
                // Appelle une méthode
                assertTrue(positions.add(touch.getBlockPosition()));
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Key getKey() {
                // Renvoie une valeur à l'appelant
                return Key.key("minestom:test");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Appelle une méthode
        instance.setBlock(-1000, 42, -1000, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(-1000, 42, -1001, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(-1000, 43, -1001, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(-1000, 43, -999, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(-1001, 43, -999, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(-1001, 42, -999, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(-1001, 42, -1001, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(-1001, 43, -1000, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(-999, 42, -1001, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(-1001, 43, -1001, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(-1001, 42, -1000, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(-1000, 42, -1010, Block.STONE.withHandler(handler));

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(-1000.301, 42, -1000)).join();

        // Appelle une méthode
        entity.tick(0);

        // Instruction de code
        assertEquals(Set.of(
                // Crée un nouvel objet
                new Vec(-1001, 43, -1000),
                // Crée un nouvel objet
                new Vec(-1001, 42, -1000),
                // Crée un nouvel objet
                new Vec(-1001, 43, -1001),
                // Crée un nouvel objet
                new Vec(-1001, 42, -1001),
                // Crée un nouvel objet
                new Vec(-1000, 43, -1001),
                // Crée un nouvel objet
                new Vec(-1000, 42, -1001),
                // Crée un nouvel objet
                new Vec(-1000, 42, -1000)
        // Instruction de code
        ), positions);

        // Appelle une méthode
        assertEquals(instance, entity.getInstance());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
