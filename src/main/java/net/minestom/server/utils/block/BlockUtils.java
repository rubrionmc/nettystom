// Package declaration for this file
package net.minestom.server.utils.block;

// Import of a required class
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockHandler;
// Import of a required class
import net.minestom.server.tag.Tag;
// Import of a required class
import net.minestom.server.utils.StringUtils;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
public class BlockUtils {

    // Code statement
    private final Block.Getter instance;
    // Code statement
    private final Point position;

    // Start of a method/block
    public BlockUtils(Block.Getter instance, Point position) {
        // Access to the current/parent object
        this.instance = instance;
        // Access to the current/parent object
        this.position = position;
    // End of a block/expression
    }

    // Start of a method/block
    public BlockUtils getRelativeTo(int x, int y, int z) {
        // Returns a value to the caller
        return new BlockUtils(instance, position.add(x, y, z));
    // End of a block/expression
    }

    // Start of a method/block
    public BlockUtils above() {
        // Returns a value to the caller
        return getRelativeTo(0, 1, 0);
    // End of a block/expression
    }

    // Start of a method/block
    public BlockUtils below() {
        // Returns a value to the caller
        return getRelativeTo(0, -1, 0);
    // End of a block/expression
    }

    // Start of a method/block
    public BlockUtils north() {
        // Returns a value to the caller
        return getRelativeTo(0, 0, -1);
    // End of a block/expression
    }

    // Start of a method/block
    public BlockUtils east() {
        // Returns a value to the caller
        return getRelativeTo(1, 0, 0);
    // End of a block/expression
    }

    // Start of a method/block
    public BlockUtils south() {
        // Returns a value to the caller
        return getRelativeTo(0, 0, 1);
    // End of a block/expression
    }

    // Start of a method/block
    public BlockUtils west() {
        // Returns a value to the caller
        return getRelativeTo(-1, 0, 0);
    // End of a block/expression
    }

    // Start of a method/block
    public Block getBlock() {
        // Returns a value to the caller
        return instance.getBlock(position);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean equals(Block block) {
        // Returns a value to the caller
        return getBlock().compare(block);
    // End of a block/expression
    }

    // Start of a method/block
    public static Map<String, String> parseProperties(String query) {
        // Branch: checks a condition
        if (!query.startsWith("[") || !query.endsWith("]")) return Map.of();
        // Branch: checks a condition
        if (query.length() == 2) return Map.of();

        // Calls a method
        final int entries = StringUtils.countMatches(query, ',') + 1;
        // Code statement
        assert entries > 0;
        // Assigns a value
        String[] keys = new String[entries];
        // Assigns a value
        String[] values = new String[entries];
        // Assigns a value
        int entryCount = 0;

        // Calls a method
        final int length = query.length() - 1;
        // Assigns a value
        int start = 1;
        // Assigns a value
        int index = 1;
        // Loop: repeats a block
        while (index <= length) {
            // Branch: checks a condition
            if (query.charAt(index) == ',' || index == length) {
                // Calls a method
                final int equalIndex = query.indexOf('=', start);
                // Branch: checks a condition
                if (equalIndex != -1) {
                    // Calls a method
                    final String key = query.substring(start, equalIndex).trim();
                    // Calls a method
                    final String value = query.substring(equalIndex + 1, index).trim();
                    // Assigns a value
                    keys[entryCount] = key;
                    // Assigns a value
                    values[entryCount++] = value;
                // End of a block/expression
                }
                // Assigns a value
                start = index + 1;
            // End of a block/expression
            }
            // Code statement
            index++;
        // End of a block/expression
        }
        // Returns a value to the caller
        return new Object2ObjectArrayMap<>(keys, values, entryCount);
    // End of a block/expression
    }

    // Start of a method/block
    public static @Nullable CompoundBinaryTag extractClientNbt(Block block) {
        // Branch: checks a condition
        if (!block.registry().isBlockEntity()) return null;
        // Append handler tags
        // Calls a method
        final BlockHandler handler = block.handler();
        // Calls a method
        final CompoundBinaryTag blockNbt = Objects.requireNonNullElseGet(block.nbt(), CompoundBinaryTag::empty);
        // Branch: checks a condition
        if (handler != null) {
            // Extract explicitly defined tags and keep the rest server-side
            // Calls a method
            var builder = CompoundBinaryTag.builder();
            // Loop: repeats a block
            for (Tag<?> tag : handler.getBlockEntityTags()) {
                // Calls a method
                final var value = tag.read(blockNbt);
                // Branch: checks a condition
                if (value != null) {
                    // Tag is present and valid
                    // Calls a method
                    tag.writeUnsafe(builder, value);
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Returns a value to the caller
            return builder.build();
        // End of a block/expression
        }
        // Complete nbt shall be sent if the block has no handler
        // Necessary to support all vanilla blocks
        // Returns a value to the caller
        return blockNbt;
    // End of a block/expression
    }
// End of a block/expression
}
