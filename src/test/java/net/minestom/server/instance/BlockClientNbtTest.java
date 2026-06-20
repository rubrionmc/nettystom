// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockHandler;
// Import of a required class
import net.minestom.server.tag.Tag;
// Import of a required class
import net.minestom.server.utils.block.BlockUtils;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertNull;

// Type declaration (class/interface/enum/record)
public class BlockClientNbtTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void basic() {
        // Calls a method
        assertNull(BlockUtils.extractClientNbt(Block.STONE));
        // Calls a method
        assertNull(BlockUtils.extractClientNbt(Block.GRASS_BLOCK));
        // Calls a method
        assertEquals(CompoundBinaryTag.empty(), BlockUtils.extractClientNbt(Block.CHEST));

        // Calls a method
        var nbt = CompoundBinaryTag.builder().putString("test", "test").build();
        // Calls a method
        assertEquals(nbt, BlockUtils.extractClientNbt(Block.CHEST.withNbt(nbt)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void handler() {
        // Assigns a value
        var handler = new BlockHandler() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public Collection<Tag<?>> getBlockEntityTags() {
                // Returns a value to the caller
                return List.of(Tag.String("test"));
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Key getKey() {
                // Returns a value to the caller
                return Key.key("minestom:test");
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Calls a method
        var nbt = CompoundBinaryTag.builder().putString("test", "test").build();
        // Calls a method
        assertNull(BlockUtils.extractClientNbt(Block.STONE.withNbt(nbt).withHandler(handler)));
        // Calls a method
        assertEquals(nbt, BlockUtils.extractClientNbt(Block.CHEST.withNbt(nbt).withHandler(handler)));
        // Code statement
        assertEquals(nbt, BlockUtils.extractClientNbt(Block.CHEST
                // Code statement
                .withNbt(CompoundBinaryTag.builder().putString("test", "test").putString("test2", "test2").build())
                // Calls a method
                .withHandler(handler)));
    // End of a block/expression
    }
// End of a block/expression
}
