// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.SuspiciousGravelBlockHandler;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.rule.BlockPlacementRule;
// Import d'une classe nécessaire
import net.minestom.server.tag.Tag;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicReference;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertThrows;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class InstanceBlockIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void basic(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Instruction de code
        assertThrows(NullPointerException.class, () -> instance.getBlock(0, 0, 0),
                // Instruction de code
                "No exception throw when getting a block in an unloaded chunk");

        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Appelle une méthode
        assertEquals(Block.AIR, instance.getBlock(0, 50, 0));

        // Appelle une méthode
        instance.setBlock(0, 50, 0, Block.GRASS_BLOCK);
        // Appelle une méthode
        assertEquals(Block.GRASS_BLOCK, instance.getBlock(0, 50, 0));

        // Appelle une méthode
        instance.setBlock(0, 50, 0, Block.STONE);
        // Appelle une méthode
        assertEquals(Block.STONE, instance.getBlock(0, 50, 0));

        // Instruction de code
        assertThrows(NullPointerException.class, () -> instance.getBlock(16, 0, 0),
                // Instruction de code
                "No exception throw when getting a block in an unloaded chunk");
        // Appelle une méthode
        instance.loadChunk(1, 0).join();
        // Appelle une méthode
        assertEquals(Block.AIR, instance.getBlock(16, 50, 0));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void unloadCache(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.loadChunk(0, 0).join();

        // Appelle une méthode
        instance.setBlock(0, 50, 0, Block.GRASS_BLOCK);
        // Appelle une méthode
        assertEquals(Block.GRASS_BLOCK, instance.getBlock(0, 50, 0));

        // Appelle une méthode
        instance.unloadChunk(0, 0);
        // Instruction de code
        assertThrows(NullPointerException.class, () -> instance.getBlock(0, 0, 0),
                // Instruction de code
                "No exception throw when getting a block in an unloaded chunk");

        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Appelle une méthode
        assertEquals(Block.AIR, instance.getBlock(0, 50, 0));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockNbt(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Instruction de code
        assertThrows(NullPointerException.class, () -> instance.getBlock(0, 0, 0),
                // Instruction de code
                "No exception throw when getting a block in an unloaded chunk");

        // Appelle une méthode
        instance.loadChunk(0, 0).join();

        // Appelle une méthode
        var tag = Tag.Integer("key");
        // Appelle une méthode
        var block = Block.STONE.withTag(tag, 5);
        // Appelle une méthode
        var point = new Vec(0, 50, 0);
        // Initial placement
        // Appelle une méthode
        instance.setBlock(point, block);
        // Appelle une méthode
        assertEquals(5, instance.getBlock(point).getTag(tag));

        // Override
        // Appelle une méthode
        instance.setBlock(point, block.withTag(tag, 7));
        // Appelle une méthode
        assertEquals(7, instance.getBlock(point).getTag(tag));

        // Different block type
        // Appelle une méthode
        instance.setBlock(point, Block.GRASS_BLOCK.withTag(tag, 8));
        // Appelle une méthode
        assertEquals(8, instance.getBlock(point).getTag(tag));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void handlerPresentInPlacementRuleUpdate(Env env) {

        // Affecte une valeur
        AtomicReference<Block> currentBlock = new AtomicReference<>();
        // Appelle une méthode
        env.process().block().registerHandler(SuspiciousGravelBlockHandler.INSTANCE.getKey(), () -> SuspiciousGravelBlockHandler.INSTANCE);
        // Début d'une méthode/d'un bloc
        env.process().block().registerBlockPlacementRule(new BlockPlacementRule(Block.SUSPICIOUS_GRAVEL) {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public @Nullable Block blockPlace(PlacementState placementState) {
                // Renvoie une valeur à l'appelant
                return block;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Block blockUpdate(UpdateState updateState) {
                // Appelle une méthode
                currentBlock.set(updateState.currentBlock());
                // Renvoie une valeur à l'appelant
                return super.blockUpdate(updateState);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });

        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var theBlock = Block.SUSPICIOUS_GRAVEL.withHandler(SuspiciousGravelBlockHandler.INSTANCE);
        // Appelle une méthode
        instance.setBlock(0, 50, 0, theBlock);
        // Appelle une méthode
        instance.setBlock(1, 50, 0, theBlock);

        // Appelle une méthode
        assertEquals(theBlock, currentBlock.get());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
