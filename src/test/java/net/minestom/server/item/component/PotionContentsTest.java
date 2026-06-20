// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.kyori.adventure.nbt.StringBinaryTag;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.color.Color;
// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.potion.CustomPotionEffect;
// Import of a required class
import net.minestom.server.potion.PotionEffect;
// Import of a required class
import net.minestom.server.potion.PotionType;
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
public class PotionContentsTest extends AbstractItemComponentTest<PotionContents> {

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected DataComponent<PotionContents> component() {
        // Returns a value to the caller
        return DataComponents.POTION_CONTENTS;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected List<Map.Entry<String, PotionContents>> directReadWriteEntries() {
        // Returns a value to the caller
        return List.of(
                // Code statement
                Map.entry("empty", PotionContents.EMPTY),
                // Code statement
                Map.entry("single effect", new PotionContents(PotionType.STRONG_SWIFTNESS)),
                // Code statement
                Map.entry("single effect, color", new PotionContents(PotionType.STRONG_SWIFTNESS, new Color(0x123456))),
                // Code statement
                Map.entry("custom effect", new PotionContents(new CustomPotionEffect(PotionEffect.INVISIBILITY, (byte) 2, 10, true, false, true))),
                // Code statement
                Map.entry("custom effect recursive", new PotionContents(new CustomPotionEffect(PotionEffect.INVISIBILITY, new CustomPotionEffect.Settings(
                        // Code statement
                        (byte) 2, 10, true, false, true, new CustomPotionEffect.Settings(
                        // Code statement
                        (byte) 2, 10, true, false, true, null))))),
                // Code statement
                Map.entry("custom effect", new PotionContents(List.of(
                        // Creates a new object
                        new CustomPotionEffect(PotionEffect.INVISIBILITY, (byte) 2, 10, true, false, true),
                        // Creates a new object
                        new CustomPotionEffect(PotionEffect.STRENGTH, (byte) 3, 10000, false, true, false)
                // Code statement
                )))
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void alternativeNbtSyntax() {
        // Assigns a value
        var value = assertOk(DataComponents.POTION_CONTENTS.decode(Transcoder.NBT,
                // Calls a method
                StringBinaryTag.stringBinaryTag("minecraft:strong_swiftness")));
        // Calls a method
        var expected = new PotionContents(PotionType.STRONG_SWIFTNESS, null, List.of(), null);
        // Calls a method
        assertEquals(expected, value);
    // End of a block/expression
    }
// End of a block/expression
}
