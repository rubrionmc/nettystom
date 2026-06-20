// Déclaration du paquet de ce fichier
package net.minestom.server.registry;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.gamedata.DataPack;
// Import d'une classe nécessaire
import net.minestom.server.world.DimensionType;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;


// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class RegistryIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testUnnamedPack(Env env) {
        // Appelle une méthode
        DynamicRegistry<DimensionType> dimensionRegistry = env.process().dimensionType();
        // Affecte une valeur
        DimensionType dimensionType = DimensionType.builder()
                // Instruction de code
                .ambientLight(2f)
                // Appelle une méthode
                .build();
        // Appelle une méthode
        var registryKey = dimensionRegistry.register(Key.key("toocool:fortests"), dimensionType, DataPack.MINESTOM_UNNAMED);
        // Appelle une méthode
        assertEquals(dimensionType, dimensionRegistry.get(registryKey));
        // Appelle une méthode
        assertEquals(DataPack.MINESTOM_UNNAMED, dimensionRegistry.getPack(registryKey));
        // Début d'une méthode/d'un bloc
        assertDoesNotThrow(() -> {
            // Appelle une méthode
            dimensionRegistry.registryDataPacket(env.process(), false);
        // Instruction de code
        }, "Registry data packet should not throw for null pack");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testDifferentPacksInterlaced(Env env) {
        // Appelle une méthode
        DynamicRegistry<DimensionType> dimensionRegistry = env.process().dimensionType();
        // Affecte une valeur
        DimensionType dimensionType = DimensionType.builder()
                // Instruction de code
                .ambientLight(2f)
                // Appelle une méthode
                .build();
        // Appelle une méthode
        assertDoesNotThrow(()-> dimensionRegistry.register(Key.key("toocool:fortests"), dimensionType, DataPack.MINESTOM_UNNAMED));
        // Appelle une méthode
        assertDoesNotThrow(() -> dimensionRegistry.register(Key.key("toocool:fortests2"), dimensionType, DataPack.MINECRAFT_CORE));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
