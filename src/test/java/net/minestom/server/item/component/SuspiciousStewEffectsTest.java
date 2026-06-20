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
import net.minestom.server.potion.PotionEffect;
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
public class SuspiciousStewEffectsTest extends AbstractItemComponentTest<SuspiciousStewEffects> {

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected DataComponent<SuspiciousStewEffects> component() {
        // Renvoie une valeur à l'appelant
        return DataComponents.SUSPICIOUS_STEW_EFFECTS;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected List<Map.Entry<String, SuspiciousStewEffects>> directReadWriteEntries() {
        // Renvoie une valeur à l'appelant
        return List.of(
                // Instruction de code
                Map.entry("empty", SuspiciousStewEffects.EMPTY),
                // Instruction de code
                Map.entry("single", new SuspiciousStewEffects(new SuspiciousStewEffects.Effect(PotionEffect.ABSORPTION, 100))),
                // Instruction de code
                Map.entry("multi", new SuspiciousStewEffects(List.of(
                        // Crée un nouvel objet
                        new SuspiciousStewEffects.Effect(PotionEffect.ABSORPTION, 100),
                        // Crée un nouvel objet
                        new SuspiciousStewEffects.Effect(PotionEffect.STRENGTH, 2)
                // Instruction de code
                )))
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void nbtReadDefaultDuration() throws Exception {
        // Affecte une valeur
        var value = assertOk(DataComponents.SUSPICIOUS_STEW_EFFECTS.decode(Transcoder.NBT, MinestomAdventure.tagStringIO().asTag("""
                [{"id": "minecraft:strength"}]
                """)));
        // Appelle une méthode
        var expected = new SuspiciousStewEffects(new SuspiciousStewEffects.Effect(PotionEffect.STRENGTH, 160));
        // Appelle une méthode
        assertEquals(expected, value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
