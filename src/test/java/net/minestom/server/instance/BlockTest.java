// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockEntityType;
// Import d'une classe nécessaire
import net.minestom.server.tag.Tag;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.HashSet;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.Objects;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class BlockTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testNBT() {
        // Affecte une valeur
        Block block = Block.CHEST;
        // Appelle une méthode
        assertFalse(block.hasNbt());
        // Appelle une méthode
        assertNull(block.nbt());

        // Appelle une méthode
        var nbt = CompoundBinaryTag.builder().putInt("key", 5).build();
        // Appelle une méthode
        block = block.withNbt(nbt);
        // Appelle une méthode
        assertTrue(block.hasNbt());
        // Appelle une méthode
        assertEquals(block.nbt(), nbt);

        // Appelle une méthode
        block = block.withNbt(null);
        // Appelle une méthode
        assertFalse(block.hasNbt());
        // Appelle une méthode
        assertNull(block.nbt());

        // Appelle une méthode
        var value = block.getTag(Tag.String("key").defaultValue("Default"));
        // Appelle une méthode
        assertEquals("Default", value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void validProperties() {
        // Affecte une valeur
        Block block = Block.CHEST;
        // Appelle une méthode
        assertEquals(block.properties(), Objects.requireNonNull(Block.fromBlockId(block.id())).properties());

        // Default state may change, but the test is required to ensure the `properties` method is working
        // Instruction de code
        assertEquals(Map.of("facing", "north",
                // Instruction de code
                "type", "single",
                // Appelle une méthode
                "waterlogged", "false"), block.properties());

        // Boucle : répète un bloc
        for (var possible : block.possibleStates()) {
            // Appelle une méthode
            assertEquals(possible, block.withProperties(possible.properties()));
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        assertEquals("north", block.withProperty("facing", "north").getProperty("facing"));
        // Appelle une méthode
        assertNotEquals(block.withProperty("facing", "north"), block.withProperty("facing", "south"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testState() {
        // Appelle une méthode
        assertEquals("minecraft:dirt", Block.DIRT.state());
        // Appelle une méthode
        assertEquals(Block.DIRT, Block.fromState("minecraft:dirt"));
        // Appelle une méthode
        assertEquals(Block.CHEST, Block.fromState("minecraft:chest"));
        // Appelle une méthode
        assertEquals(Block.CHEST, Block.fromState("minecraft:chest[]"));
        // Appelle une méthode
        assertEquals(Block.CHEST.withProperty("facing", "north"), Block.fromState("minecraft:chest[facing=north]"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void invalidProperties() {
        // Affecte une valeur
        Block block = Block.CHEST;
        // Appelle une méthode
        assertThrows(Exception.class, () -> block.withProperty("random", "randomKey"));
        // Appelle une méthode
        assertThrows(Exception.class, () -> block.withProperties(Map.of("random", "randomKey")));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testEquality() {
        // Appelle une méthode
        var nbt = CompoundBinaryTag.builder().putInt("key", 5).build();
        // Affecte une valeur
        Block b1 = Block.CHEST;
        // Affecte une valeur
        Block b2 = Block.CHEST;
        // Appelle une méthode
        assertEquals(b1.withNbt(nbt), b2.withNbt(nbt));

        // Appelle une méthode
        assertEquals("north", b1.withProperty("facing", "north").getProperty("facing"));
        // Appelle une méthode
        assertEquals(b1.withProperty("facing", "north"), b2.withProperty("facing", "north"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testMutability() {
        // Affecte une valeur
        Block block = Block.CHEST;
        // Appelle une méthode
        assertThrows(Exception.class, () -> block.properties().put("facing", "north"));
        // Appelle une méthode
        assertThrows(Exception.class, () -> block.withProperty("facing", "north").properties().put("facing", "south"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testShape() {
        // Appelle une méthode
        Point start = Block.LANTERN.registry().collisionShape().relativeStart();
        // Appelle une méthode
        Point end = Block.LANTERN.registry().collisionShape().relativeEnd();

        // Appelle une méthode
        assertEquals(new Vec(0.3125, 0, 0.3125), start);
        // Appelle une méthode
        assertEquals(new Vec(0.6875, 0.5625, 0.6875), end);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testDuplicateProperties() {
        // Appelle une méthode
        HashSet<Integer> assignedStates = new HashSet<>();
        // Boucle : répète un bloc
        for (Block block : Block.values()) {
            // Boucle : répète un bloc
            for (Block blockWithState : block.possibleStates()) {
                // Appelle une méthode
                assertTrue(assignedStates.add(blockWithState.stateId()));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testStateIdConversion() {
        // Boucle : répète un bloc
        for (Block block : Block.values()) {
            // Boucle : répète un bloc
            for (Block blockWithState : block.possibleStates()) {
                // Appelle une méthode
                assertEquals(blockWithState, Block.fromStateId(blockWithState.stateId()));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testBlockEntityRegistryLoading() {
        // Sanity to ensure we correctly load block entity types
        // Appelle une méthode
        assertEquals(BlockEntityType.SIGN, Block.OAK_SIGN.registry().blockEntityType());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
