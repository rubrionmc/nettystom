// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.adventure.MinestomAdventure;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.io.IOException;

// Static import of a member
import static net.minestom.server.instance.block.Block.STATE_STRUCT_CODEC;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
public class BlockStateMapCodecTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testEquivalence() throws IOException {
        // Assigns a value
        String particleString = "{Properties:{lit:\"true\"},Name:\"copper_bulb\"}";
        // Calls a method
        CompoundBinaryTag nbt = MinestomAdventure.NBT_CODEC.decode(particleString);
        // Calls a method
        Block block = STATE_STRUCT_CODEC.decode(Transcoder.NBT,nbt).orElseThrow();
        // Calls a method
        assertEquals("true", block.getProperty("lit"));
        // Calls a method
        assertEquals(block.defaultState().getProperty("powered"), block.getProperty("powered"));
        // Calls a method
        assertEquals("minecraft:copper_bulb", block.name());
        // Calls a method
        BinaryTag newNBT = STATE_STRUCT_CODEC.encode(Transcoder.NBT,block).orElseThrow();
        // Calls a method
        String newString = MinestomAdventure.NBT_CODEC.encode((CompoundBinaryTag) newNBT);
        // Calls a method
        assertEquals(particleString, newString);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testDefaultBlockState() throws IOException {
        // Calls a method
        String defaultFacing = Block.SPRUCE_STAIRS.defaultState().getProperty("facing");
        // Code statement
        assert defaultFacing != null;
        // Calls a method
        Block block = Block.SPRUCE_STAIRS.withProperty("facing", defaultFacing);
        // Calls a method
        BinaryTag nbt = STATE_STRUCT_CODEC.encode(Transcoder.NBT, block).orElseThrow();
        // Calls a method
        String nbtString = MinestomAdventure.NBT_CODEC.encode((CompoundBinaryTag) nbt);
        // Calls a method
        assertEquals("{Name:\"spruce_stairs\"}", nbtString);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testPropertyBlockState() throws IOException {
        // Calls a method
        Block block = Block.SPRUCE_STAIRS.withProperty("facing", "south");
        // Calls a method
        BinaryTag nbt = STATE_STRUCT_CODEC.encode(Transcoder.NBT, block).orElseThrow();
        // Calls a method
        String nbtString = MinestomAdventure.NBT_CODEC.encode((CompoundBinaryTag) nbt);
        // Calls a method
        assertEquals("{Properties:{facing:\"south\"},Name:\"spruce_stairs\"}", nbtString);
    // End of a block/expression
    }
// End of a block/expression
}
