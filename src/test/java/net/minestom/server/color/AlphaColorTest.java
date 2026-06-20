// Package declaration for this file
package net.minestom.server.color;

// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.StringBinaryTag;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
public class AlphaColorTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void alphaColorTest() {
        // Calls a method
        AlphaColor color = new AlphaColor(0x11, 0x22, 0x33, 0x44);
        // Calls a method
        assertEquals(0x11223344, color.asARGB());
        // Calls a method
        assertEquals(0x22334411, color.asRGBA());

        // Assigns a value
        String hexString = "#AABBCCDD";
        // Code statement
        assertEquals(
                // Creates a new object
                new AlphaColor(0xDDAABBCC),
                // Code statement
                AlphaColor.fromRGBAHexString(hexString)
        // End of a block/expression
        );
        // Code statement
        assertEquals(
                // Creates a new object
                new AlphaColor(0xAABBCCDD),
                // Code statement
                AlphaColor.fromARGBHexString(hexString)
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void codecTest() {
        // Calls a method
        AlphaColor testColor = new AlphaColor(0x01, 0x23, 0x45, 0x67);
        // Calls a method
        BinaryTag elementARGB = AlphaColor.ARGB_STRING_CODEC.encode(Transcoder.NBT, testColor).orElseThrow();
        // Calls a method
        BinaryTag elementRGBA = AlphaColor.RGBA_STRING_CODEC.encode(Transcoder.NBT, testColor).orElseThrow();
        // Calls a method
        assertEquals(StringBinaryTag.stringBinaryTag("#01234567"), elementARGB);
        // Calls a method
        assertEquals(StringBinaryTag.stringBinaryTag("#23456701"), elementRGBA);
    // End of a block/expression
    }
// End of a block/expression
}
