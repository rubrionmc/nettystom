// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.StringBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.color.Color;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.potion.CustomPotionEffect;
// Import d'une classe nécessaire
import net.minestom.server.potion.PotionEffect;
// Import d'une classe nécessaire
import net.minestom.server.potion.PotionType;
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
public class PotionContentsTest extends AbstractItemComponentTest<PotionContents> {

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected DataComponent<PotionContents> component() {
        // Renvoie une valeur à l'appelant
        return DataComponents.POTION_CONTENTS;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected List<Map.Entry<String, PotionContents>> directReadWriteEntries() {
        // Renvoie une valeur à l'appelant
        return List.of(
                // Instruction de code
                Map.entry("empty", PotionContents.EMPTY),
                // Instruction de code
                Map.entry("single effect", new PotionContents(PotionType.STRONG_SWIFTNESS)),
                // Instruction de code
                Map.entry("single effect, color", new PotionContents(PotionType.STRONG_SWIFTNESS, new Color(0x123456))),
                // Instruction de code
                Map.entry("custom effect", new PotionContents(new CustomPotionEffect(PotionEffect.INVISIBILITY, (byte) 2, 10, true, false, true))),
                // Instruction de code
                Map.entry("custom effect recursive", new PotionContents(new CustomPotionEffect(PotionEffect.INVISIBILITY, new CustomPotionEffect.Settings(
                        // Instruction de code
                        (byte) 2, 10, true, false, true, new CustomPotionEffect.Settings(
                        // Instruction de code
                        (byte) 2, 10, true, false, true, null))))),
                // Instruction de code
                Map.entry("custom effect", new PotionContents(List.of(
                        // Crée un nouvel objet
                        new CustomPotionEffect(PotionEffect.INVISIBILITY, (byte) 2, 10, true, false, true),
                        // Crée un nouvel objet
                        new CustomPotionEffect(PotionEffect.STRENGTH, (byte) 3, 10000, false, true, false)
                // Instruction de code
                )))
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void alternativeNbtSyntax() {
        // Affecte une valeur
        var value = assertOk(DataComponents.POTION_CONTENTS.decode(Transcoder.NBT,
                // Appelle une méthode
                StringBinaryTag.stringBinaryTag("minecraft:strong_swiftness")));
        // Appelle une méthode
        var expected = new PotionContents(PotionType.STRONG_SWIFTNESS, null, List.of(), null);
        // Appelle une méthode
        assertEquals(expected, value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
