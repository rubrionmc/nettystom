// Déclaration du paquet de ce fichier
package net.minestom.server.instance.light;

// Import d'une classe nécessaire
import net.minestom.server.collision.Shape;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertFalse;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertTrue;

// Déclaration de type (classe/interface/enum/record)
public class BlockIsOccludedTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockAir() {
        // Appelle une méthode
        Shape airBlock = Block.AIR.registry().occlusionShape();

        // Boucle : répète un bloc
        for (BlockFace face : BlockFace.values()) {
            // Appelle une méthode
            assertFalse(airBlock.isOccluded(airBlock, face));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockLantern() {
        // Appelle une méthode
        Shape shape = Block.LANTERN.registry().occlusionShape();
        // Appelle une méthode
        Shape airBlock = Block.AIR.registry().occlusionShape();

        // Boucle : répète un bloc
        for (BlockFace face : BlockFace.values()) {
            // Appelle une méthode
            assertFalse(shape.isOccluded(airBlock, face));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockSpruceLeaves() {
        // Appelle une méthode
        Shape shape = Block.SPRUCE_LEAVES.registry().occlusionShape();
        // Appelle une méthode
        Shape airBlock = Block.AIR.registry().occlusionShape();

        // Boucle : répète un bloc
        for (BlockFace face : BlockFace.values()) {
            // Appelle une méthode
            assertFalse(shape.isOccluded(airBlock, face));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockCauldron() {
        // Appelle une méthode
        Shape shape = Block.CAULDRON.registry().occlusionShape();
        // Appelle une méthode
        Shape airBlock = Block.AIR.registry().occlusionShape();

        // Boucle : répète un bloc
        for (BlockFace face : BlockFace.values()) {
            // Appelle une méthode
            assertFalse(shape.isOccluded(airBlock, face));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockSlabBottomAir() {
        // Appelle une méthode
        Shape shape = Block.SANDSTONE_SLAB.registry().occlusionShape();
        // Appelle une méthode
        Shape airBlock = Block.AIR.registry().occlusionShape();

        // Appelle une méthode
        assertTrue(shape.isOccluded(airBlock, BlockFace.BOTTOM));

        // Appelle une méthode
        assertFalse(shape.isOccluded(airBlock, BlockFace.NORTH));
        // Appelle une méthode
        assertFalse(shape.isOccluded(airBlock, BlockFace.SOUTH));
        // Appelle une méthode
        assertFalse(shape.isOccluded(airBlock, BlockFace.EAST));
        // Appelle une méthode
        assertFalse(shape.isOccluded(airBlock, BlockFace.WEST));
        // Appelle une méthode
        assertFalse(shape.isOccluded(airBlock, BlockFace.TOP));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockSlabTopEnchantingTable() {
        // Appelle une méthode
        Shape shape1 = Block.SANDSTONE_SLAB.withProperty("type", "top").registry().occlusionShape();
        // Appelle une méthode
        Shape shape2 = Block.ENCHANTING_TABLE.registry().occlusionShape();

        // Appelle une méthode
        assertFalse(shape1.isOccluded(shape2, BlockFace.BOTTOM));

        // Appelle une méthode
        assertTrue(shape1.isOccluded(shape2, BlockFace.NORTH));
        // Appelle une méthode
        assertTrue(shape1.isOccluded(shape2, BlockFace.SOUTH));
        // Appelle une méthode
        assertTrue(shape1.isOccluded(shape2, BlockFace.EAST));
        // Appelle une méthode
        assertTrue(shape1.isOccluded(shape2, BlockFace.WEST));
        // Appelle une méthode
        assertTrue(shape1.isOccluded(shape2, BlockFace.TOP));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockStairWest() {
        // Affecte une valeur
        Shape shape = Block.SANDSTONE_STAIRS.withProperties(Map.of(
                // Instruction de code
                "facing", "west",
                // Instruction de code
                "half", "bottom",
                // Appelle une méthode
                "shape", "straight")).registry().occlusionShape();

        // Appelle une méthode
        Shape airBlock = Block.AIR.registry().occlusionShape();

        // Appelle une méthode
        assertTrue(shape.isOccluded(airBlock, BlockFace.WEST));
        // Appelle une méthode
        assertTrue(shape.isOccluded(airBlock, BlockFace.BOTTOM));

        // Appelle une méthode
        assertFalse(shape.isOccluded(airBlock, BlockFace.SOUTH));
        // Appelle une méthode
        assertFalse(shape.isOccluded(airBlock, BlockFace.EAST));
        // Appelle une méthode
        assertFalse(shape.isOccluded(airBlock, BlockFace.NORTH));
        // Appelle une méthode
        assertFalse(shape.isOccluded(airBlock, BlockFace.TOP));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockSlabBottomStone() {
        // Appelle une méthode
        Shape shape = Block.SANDSTONE_SLAB.registry().occlusionShape();
        // Appelle une méthode
        Shape stoneBlock = Block.STONE.registry().occlusionShape();

        // Appelle une méthode
        assertTrue(shape.isOccluded(stoneBlock, BlockFace.BOTTOM));
        // Appelle une méthode
        assertTrue(shape.isOccluded(stoneBlock, BlockFace.NORTH));
        // Appelle une méthode
        assertTrue(shape.isOccluded(stoneBlock, BlockFace.SOUTH));
        // Appelle une méthode
        assertTrue(shape.isOccluded(stoneBlock, BlockFace.EAST));
        // Appelle une méthode
        assertTrue(shape.isOccluded(stoneBlock, BlockFace.WEST));
        // Appelle une méthode
        assertTrue(shape.isOccluded(stoneBlock, BlockFace.TOP));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockStone() {
        // Appelle une méthode
        Shape shape = Block.STONE.registry().occlusionShape();
        // Appelle une méthode
        Shape airBlock = Block.AIR.registry().occlusionShape();

        // Boucle : répète un bloc
        for (BlockFace face : BlockFace.values()) {
            // Appelle une méthode
            assertTrue(shape.isOccluded(airBlock, face));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockStair() {
        // Appelle une méthode
        Shape shape = Block.SANDSTONE_STAIRS.registry().occlusionShape();
        // Appelle une méthode
        Shape airBlock = Block.AIR.registry().occlusionShape();

        // Appelle une méthode
        assertTrue(shape.isOccluded(airBlock, BlockFace.NORTH));
        // Appelle une méthode
        assertTrue(shape.isOccluded(airBlock, BlockFace.BOTTOM));

        // Appelle une méthode
        assertFalse(shape.isOccluded(airBlock, BlockFace.SOUTH));
        // Appelle une méthode
        assertFalse(shape.isOccluded(airBlock, BlockFace.EAST));
        // Appelle une méthode
        assertFalse(shape.isOccluded(airBlock, BlockFace.WEST));
        // Appelle une méthode
        assertFalse(shape.isOccluded(airBlock, BlockFace.TOP));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockSlab() {
        // Appelle une méthode
        Shape shape = Block.SANDSTONE_SLAB.registry().occlusionShape();
        // Appelle une méthode
        Shape airBlock = Block.AIR.registry().occlusionShape();

        // Appelle une méthode
        assertTrue(shape.isOccluded(airBlock, BlockFace.BOTTOM));

        // Appelle une méthode
        assertFalse(shape.isOccluded(airBlock, BlockFace.NORTH));
        // Appelle une méthode
        assertFalse(shape.isOccluded(airBlock, BlockFace.SOUTH));
        // Appelle une méthode
        assertFalse(shape.isOccluded(airBlock, BlockFace.EAST));
        // Appelle une méthode
        assertFalse(shape.isOccluded(airBlock, BlockFace.WEST));
        // Appelle une méthode
        assertFalse(shape.isOccluded(airBlock, BlockFace.TOP));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockSlabBottomAndSlabTop() {
        // Appelle une méthode
        Shape shape1 = Block.SANDSTONE_SLAB.registry().occlusionShape();
        // Appelle une méthode
        Shape shape2 = Block.SANDSTONE_SLAB.withProperty("type", "top").registry().occlusionShape();

        // Appelle une méthode
        assertFalse(shape1.isOccluded(shape2, BlockFace.TOP));

        // Appelle une méthode
        assertTrue(shape1.isOccluded(shape2, BlockFace.BOTTOM));
        // Appelle une méthode
        assertTrue(shape1.isOccluded(shape2, BlockFace.EAST));
        // Appelle une méthode
        assertTrue(shape1.isOccluded(shape2, BlockFace.WEST));
        // Appelle une méthode
        assertTrue(shape1.isOccluded(shape2, BlockFace.NORTH));
        // Appelle une méthode
        assertTrue(shape1.isOccluded(shape2, BlockFace.SOUTH));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockSlabBottomAndSlabBottom() {
        // Appelle une méthode
        Shape shape = Block.SANDSTONE_SLAB.registry().occlusionShape();

        // Appelle une méthode
        assertTrue(shape.isOccluded(shape, BlockFace.BOTTOM));
        // Appelle une méthode
        assertTrue(shape.isOccluded(shape, BlockFace.TOP));

        // Appelle une méthode
        assertFalse(shape.isOccluded(shape, BlockFace.EAST));
        // Appelle une méthode
        assertFalse(shape.isOccluded(shape, BlockFace.WEST));
        // Appelle une méthode
        assertFalse(shape.isOccluded(shape, BlockFace.NORTH));
        // Appelle une méthode
        assertFalse(shape.isOccluded(shape, BlockFace.SOUTH));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockStairAndSlabBottom() {
        // Appelle une méthode
        Shape shape1 = Block.STONE_STAIRS.registry().occlusionShape();
        // Appelle une méthode
        Shape shape2 = Block.SANDSTONE_SLAB.registry().occlusionShape();

        // Appelle une méthode
        assertTrue(shape1.isOccluded(shape2, BlockFace.BOTTOM));
        // Appelle une méthode
        assertTrue(shape1.isOccluded(shape2, BlockFace.NORTH));
        // Appelle une méthode
        assertTrue(shape1.isOccluded(shape2, BlockFace.TOP));

        // Appelle une méthode
        assertFalse(shape1.isOccluded(shape2, BlockFace.EAST));
        // Appelle une méthode
        assertFalse(shape1.isOccluded(shape2, BlockFace.WEST));
        // Appelle une méthode
        assertFalse(shape1.isOccluded(shape2, BlockFace.SOUTH));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockStairAndSlabTop() {
        // Appelle une méthode
        Shape shape1 = Block.STONE_STAIRS.registry().occlusionShape();
        // Appelle une méthode
        Shape shape2 = Block.SANDSTONE_SLAB.withProperty("type", "top").registry().occlusionShape();

        // Appelle une méthode
        assertTrue(shape1.isOccluded(shape2, BlockFace.NORTH));
        // Appelle une méthode
        assertTrue(shape1.isOccluded(shape2, BlockFace.BOTTOM));
        // Appelle une méthode
        assertTrue(shape1.isOccluded(shape2, BlockFace.EAST));
        // Appelle une méthode
        assertTrue(shape1.isOccluded(shape2, BlockFace.WEST));
        // Appelle une méthode
        assertTrue(shape1.isOccluded(shape2, BlockFace.SOUTH));

        // Appelle une méthode
        assertFalse(shape1.isOccluded(shape2, BlockFace.TOP));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void occlusionShapeLeaves() {
        // Appelle une méthode
        Shape shape = Block.OAK_LEAVES.registry().occlusionShape();
        // Appelle une méthode
        Shape airBlock = Block.AIR.registry().occlusionShape();

        // Boucle : répète un bloc
        for (BlockFace face : BlockFace.values()) {
            // Appelle une méthode
            assertFalse(shape.isOccluded(airBlock, face));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void collisionShapeLeaves() {
        // Appelle une méthode
        Shape shape = Block.OAK_LEAVES.registry().collisionShape();
        // Appelle une méthode
        Shape airBlock = Block.AIR.registry().collisionShape();

        // Boucle : répète un bloc
        for (BlockFace face : BlockFace.values()) {
            // Appelle une méthode
            assertTrue(shape.isOccluded(airBlock, face));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
