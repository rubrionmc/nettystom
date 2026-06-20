// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.adventure.MinestomAdventure;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.potion.PotionEffect;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Static import of a member
import static net.minestom.server.codec.CodecAssertions.assertOk;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
public class SuspiciousStewEffectsTest extends AbstractItemComponentTest<SuspiciousStewEffects> {

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected DataComponent<SuspiciousStewEffects> component() {
        // Returns a value to the caller
        return DataComponents.SUSPICIOUS_STEW_EFFECTS;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected List<Map.Entry<String, SuspiciousStewEffects>> directReadWriteEntries() {
        // Returns a value to the caller
        return List.of(
                // Code statement
                Map.entry("empty", SuspiciousStewEffects.EMPTY),
                // Code statement
                Map.entry("single", new SuspiciousStewEffects(new SuspiciousStewEffects.Effect(PotionEffect.ABSORPTION, 100))),
                // Code statement
                Map.entry("multi", new SuspiciousStewEffects(List.of(
                        // Creates a new object
                        new SuspiciousStewEffects.Effect(PotionEffect.ABSORPTION, 100),
                        // Creates a new object
                        new SuspiciousStewEffects.Effect(PotionEffect.STRENGTH, 2)
                // Code statement
                )))
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void nbtReadDefaultDuration() throws Exception {
        // Assigns a value
        var value = assertOk(DataComponents.SUSPICIOUS_STEW_EFFECTS.decode(Transcoder.NBT, MinestomAdventure.tagStringIO().asTag("""
                [{"id": "minecraft:strength"}]
                """)));
        // Calls a method
        var expected = new SuspiciousStewEffects(new SuspiciousStewEffects.Effect(PotionEffect.STRENGTH, 160));
        // Calls a method
        assertEquals(expected, value);
    // End of a block/expression
    }
// End of a block/expression
}
