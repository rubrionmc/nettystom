// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.IntBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.kyori.adventure.util.RGBLike;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.color.Color;
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
import static java.util.Map.entry;
// Import statique d'un membre
import static net.minestom.server.codec.CodecAssertions.assertOk;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
public class ColorTest extends AbstractItemComponentTest<RGBLike> {
    // This is not a test, but it creates a compile error if the component type is changed away from Integer,
    // as a reminder that tests should be added for that new component type.
    // Affecte une valeur
    private static final List<DataComponent<RGBLike>> SHARED_COMPONENTS = List.of(
            // Instruction de code
            DataComponents.MAP_COLOR
    // Fin d'un bloc/d'une expression
    );

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected DataComponent<RGBLike> component() {
        // Renvoie une valeur à l'appelant
        return SHARED_COMPONENTS.getFirst();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected List<Map.Entry<String, RGBLike>> directReadWriteEntries() {
        // Renvoie une valeur à l'appelant
        return List.of(
                // Instruction de code
                entry("simple", new Color(0x123456))
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void namedTextColor() {
        // Appelle une méthode
        var tag = assertOk(DataComponents.MAP_COLOR.encode(Transcoder.NBT, NamedTextColor.YELLOW));
        // Appelle une méthode
        assertEquals(IntBinaryTag.intBinaryTag(16777045), tag);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
