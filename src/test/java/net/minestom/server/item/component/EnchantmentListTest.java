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
import net.minestom.server.item.enchant.Enchantment;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTranscoder;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static net.minestom.server.codec.CodecAssertions.assertOk;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
public class EnchantmentListTest extends AbstractItemComponentTest<EnchantmentList> {
    // This is not a test, but it creates a compile error if the component type is changed away from Unit,
    // as a reminder that tests should be added for that new component type.
    // Affecte une valeur
    private static final List<DataComponent<EnchantmentList>> SHARED_COMPONENTS = List.of(
            // Instruction de code
            DataComponents.ENCHANTMENTS,
            // Instruction de code
            DataComponents.STORED_ENCHANTMENTS
    // Fin d'un bloc/d'une expression
    );

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected DataComponent<EnchantmentList> component() {
        // Renvoie une valeur à l'appelant
        return SHARED_COMPONENTS.getFirst();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected List<Map.Entry<String, EnchantmentList>> directReadWriteEntries() {
        // Renvoie une valeur à l'appelant
        return List.of(
                // Instruction de code
                Map.entry("empty", EnchantmentList.EMPTY),
                // Instruction de code
                Map.entry("single entry", new EnchantmentList(Map.of(Enchantment.SHARPNESS, 1))),
                // Instruction de code
                Map.entry("multi entry", new EnchantmentList(Map.of(Enchantment.SHARPNESS, 1, Enchantment.PUNCH, 2)))
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testShorthandNbtSyntax(Env env) throws Exception {
        // Affecte une valeur
        var tag = MinestomAdventure.tagStringIO().asTag("""
                {
                    "sharpness": 1,
                    "punch": 2,
                }
                """);
        // Appelle une méthode
        var coder = new RegistryTranscoder<>(Transcoder.NBT, env.process());
        // Appelle une méthode
        var value = assertOk(component().decode(coder, tag));
        // Appelle une méthode
        assertEquals(new EnchantmentList(Map.of(Enchantment.SHARPNESS, 1, Enchantment.PUNCH, 2)), value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
