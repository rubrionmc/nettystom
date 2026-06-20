// Déclaration du paquet de ce fichier
package net.minestom.server.collision;

// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import org.junit.jupiter.params.ParameterizedTest;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.Arguments;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.MethodSource;

// Import d'une classe nécessaire
import java.util.stream.Stream;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
public class TestShape {

    // Début d'une méthode/d'un bloc
    private static Stream<Arguments> isFullFaceCases() {
        // Renvoie une valeur à l'appelant
        return Stream.of(
                // Instruction de code
                Arguments.of(Block.STONE, BlockFace.BOTTOM, true),
                // Instruction de code
                Arguments.of(Block.ENCHANTING_TABLE, BlockFace.BOTTOM, true),
                // Instruction de code
                Arguments.of(Block.ENCHANTING_TABLE, BlockFace.TOP, false),
                // Instruction de code
                Arguments.of(Block.ENCHANTING_TABLE, BlockFace.NORTH, false),
                // Instruction de code
                Arguments.of(Block.ACACIA_FENCE, BlockFace.TOP, false),
                // Instruction de code
                Arguments.of(Block.IRON_BARS, BlockFace.TOP, false),
                // We are testing collision faces here, so this should be true even though it doesnt occlude light
                // Instruction de code
                Arguments.of(Block.GLASS, BlockFace.TOP, true),
                // Instruction de code
                Arguments.of(Block.DARK_OAK_DOOR, BlockFace.NORTH, false),
                // Instruction de code
                Arguments.of(Block.DARK_OAK_DOOR, BlockFace.SOUTH, true)
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @MethodSource("isFullFaceCases")
    // Début d'une méthode/d'un bloc
    void isFullFace(Block block, BlockFace face, boolean isFullFace) {
        // Appelle une méthode
        assertEquals(block.registry().collisionShape().isFaceFull(face), isFullFace);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
