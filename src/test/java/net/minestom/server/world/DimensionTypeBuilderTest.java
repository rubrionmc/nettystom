// Déclaration du paquet de ce fichier
package net.minestom.server.world;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
class DimensionTypeBuilderTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testCoordinateScale() {
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder().coordinateScale(0));
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder().coordinateScale(30000001));

        // Appelle une méthode
        assertDoesNotThrow(() -> DimensionType.builder().coordinateScale(1.5));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testMinY() {
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder().minY(-2048));
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder().minY(2032));
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder().minY(7));

        // Appelle une méthode
        assertDoesNotThrow(() -> DimensionType.builder().minY(-16));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testHeight() {
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder().height(0));
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder().height(4080));
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder().height(17));

        // Appelle une méthode
        assertDoesNotThrow(() -> DimensionType.builder().height(16));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testLogicalHeight() {
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder().logicalHeight(-1));

        // Appelle une méthode
        assertDoesNotThrow(() -> DimensionType.builder().logicalHeight(17));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testMonsterSpawnBlockLightLimit() {
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder().monsterSpawnBlockLightLimit(-1));
        // Appelle une méthode
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder().monsterSpawnBlockLightLimit(16));

        // Appelle une méthode
        assertDoesNotThrow(() -> DimensionType.builder().monsterSpawnBlockLightLimit(15));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testBuild() {
        // Instruction de code
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder()
                // Instruction de code
                .height(32)
                // Instruction de code
                .logicalHeight(33)
                // Appelle une méthode
                .build());

        // Instruction de code
        assertThrows(IllegalArgumentException.class, () -> DimensionType.builder()
                // Instruction de code
                .height(32)
                // Instruction de code
                .minY(2016)
                // Appelle une méthode
                .build());

        // Instruction de code
        assertDoesNotThrow(() -> DimensionType.builder()
                // Instruction de code
                .minY(2000)
                // Instruction de code
                .height(32)
                // Instruction de code
                .logicalHeight(10)
                // Appelle une méthode
                .build());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}