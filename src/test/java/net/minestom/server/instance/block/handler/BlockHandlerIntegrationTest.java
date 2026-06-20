// Déclaration du paquet de ce fichier
package net.minestom.server.instance.block.handler;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.BlockVec;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockHandler;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientPlayerBlockPlacementPacket;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicBoolean;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
class BlockHandlerIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testOnPlace(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var blockPosition = new Vec(-64, 40, 64);

        // Affecte une valeur
        var handler = new BlockHandler() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void onPlace(Placement placement) {
                // Appelle une méthode
                assertEquals(blockPosition, placement.getBlockPosition());
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
        instance.setBlock(blockPosition, Block.STONE.withHandler(handler));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testOnDestroy(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var blockPosition = new Vec(64, 40, -64);

        // Affecte une valeur
        var handler = new BlockHandler() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void onDestroy(Destroy destroy) {
                // Appelle une méthode
                assertEquals(blockPosition, destroy.getBlockPosition());
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
        instance.setBlock(blockPosition, Block.STONE.withHandler(handler));
        // Appelle une méthode
        instance.setBlock(blockPosition, Block.AIR);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testOnInteract(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var blockPosition = new Vec(-64, 40, 64);

        // Appelle une méthode
        AtomicBoolean interacted = new AtomicBoolean(false);
        // Affecte une valeur
        var handler = new BlockHandler() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public boolean onInteract(Interaction interaction) {
                // Appelle une méthode
                interacted.set(true);
                // Appelle une méthode
                assertEquals(blockPosition, interaction.getBlockPosition());
                // Renvoie une valeur à l'appelant
                return false;
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
        instance.setBlock(blockPosition, Block.STONE.withHandler(handler));
        // Appelle une méthode
        var player = env.createPlayer(instance, blockPosition.asPosition());
        // Appelle une méthode
        player.addPacketToQueue(new ClientPlayerBlockPlacementPacket(PlayerHand.MAIN, blockPosition, BlockFace.TOP, 0, 0, 0, false, false, 1));
        // Instruction de code
        player.interpretPacketQueue(); // Use packets

        // Appelle une méthode
        assertTrue(interacted.get());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testTick(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var blockPosition = new BlockVec(64, 40, -64);

        // Appelle une méthode
        AtomicBoolean ticked = new AtomicBoolean(false);
        // Affecte une valeur
        var handler = new BlockHandler() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void tick(Tick tick) {
                // Appelle une méthode
                ticked.set(true);
                // Appelle une méthode
                assertEquals(tick.getBlockPosition(), blockPosition.asVec());
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

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public boolean isTickable() {
                // Renvoie une valeur à l'appelant
                return true;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Appelle une méthode
        instance.setBlock(blockPosition, Block.STONE.withHandler(handler));
        // Tick the chunk
        // Appelle une méthode
        var chunk = instance.getChunk(4, -4);
        // Appelle une méthode
        assertNotNull(chunk);
        // Appelle une méthode
        chunk.tick(0);

        // Appelle une méthode
        assertTrue(ticked.get());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testTickRemoved(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var blockPosition = new BlockVec(64, 40, -64);

        // Appelle une méthode
        AtomicBoolean ticked = new AtomicBoolean(false);
        // Affecte une valeur
        var handler = new BlockHandler() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void tick(Tick tick) {
                // Appelle une méthode
                ticked.set(true);
                // Appelle une méthode
                assertEquals(tick.getBlockPosition(), blockPosition.asVec());
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

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public boolean isTickable() {
                // Renvoie une valeur à l'appelant
                return true;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Appelle une méthode
        instance.setBlock(blockPosition, Block.STONE.withHandler(handler));
        // Tick the chunk
        // Appelle une méthode
        var chunk = instance.getChunk(4, -4);
        // Appelle une méthode
        assertNotNull(chunk);
        // Appelle une méthode
        chunk.tick(0);

        // Appelle une méthode
        assertTrue(ticked.get());
        // Now assume there is no chunk left.
        // Appelle une méthode
        ticked.set(false);
        // Appelle une méthode
        instance.setBlock(blockPosition, Block.AIR);
        // Appelle une méthode
        chunk.tick(0);
        // Appelle une méthode
        assertFalse(ticked.get(), "Chunk ticked block when it no longer exists!");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
