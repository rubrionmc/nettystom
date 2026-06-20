// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static net.kyori.adventure.nbt.StringBinaryTag.stringBinaryTag;
// Import statique d'un membre
import static net.minestom.server.codec.CodecAssertions.assertOk;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
public class ItemRarityTest extends AbstractItemComponentTest<ItemRarity> {
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected DataComponent<ItemRarity> component() {
        // Renvoie une valeur à l'appelant
        return DataComponents.RARITY;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected List<Map.Entry<String, ItemRarity>> directReadWriteEntries() {
        // Renvoie une valeur à l'appelant
        return List.of(
                // Instruction de code
                Map.entry("common", ItemRarity.COMMON)
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testReadFromNbtInt() {
        // Appelle une méthode
        var value = assertOk(ItemRarity.CODEC.decode(Transcoder.NBT, stringBinaryTag("rare")));
        // Appelle une méthode
        assertEquals(ItemRarity.RARE, value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
