// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.adventure.MinestomAdventure;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.predicate.BlockPredicate;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static java.util.Map.entry;
// Import statique d'un membre
import static net.minestom.server.codec.CodecAssertions.assertOk;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertTrue;
// Import statique d'un membre
import static org.junit.jupiter.api.Assumptions.assumeFalse;

// Déclaration de type (classe/interface/enum/record)
public class BlockPredicatesTest extends AbstractItemComponentTest<BlockPredicates> {

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected DataComponent<BlockPredicates> component() {
        // Renvoie une valeur à l'appelant
        return DataComponents.CAN_PLACE_ON; // CAN_BREAK is the same thing
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected List<Map.Entry<String, BlockPredicates>> directReadWriteEntries() {
        // Renvoie une valeur à l'appelant
        return List.of(
                // TODO(1.21.5)
                // Instruction de code
                entry("empty", new BlockPredicates(List.of()))
//                entry("single, no tooltip", new BlockPredicates(BlockPredicate.ALL)),
//                entry("many", new BlockPredicates(List.of(BlockPredicate.ALL, BlockPredicate.NONE)))
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testSingleBlockNbtInput() throws IOException {
        // Appelle une méthode
        assumeFalse(true, "TODO(1.21.5)");
        // Appelle une méthode
        var tag = MinestomAdventure.tagStringIO().asTag("{blocks:'minecraft:stone'}");
        // Appelle une méthode
        var component = assertOk(DataComponents.CAN_PLACE_ON.decode(Transcoder.NBT, tag));
        // Appelle une méthode
        var expected = new BlockPredicates(new BlockPredicate(Block.STONE));
        // Appelle une méthode
        assertEquals(expected, component);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testMultiMatch() {
        // Just sanity check that it actually runs both of the predicates
        // Appelle une méthode
        var predicate = new BlockPredicates(List.of(BlockPredicate.NONE, BlockPredicate.ALL));
        // Appelle une méthode
        assertTrue(predicate.test(Block.AIR));
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
