// Package declaration for this file
package net.minestom.testing.util;

// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.HashMap;
// Import of a required class
import java.util.Map;

// Type declaration (class/interface/enum/record)
public final class MockBlockGetter implements Block.Getter, Block.Setter {
    // Start of a method/block
    public static MockBlockGetter empty() {
        // Returns a value to the caller
        return new MockBlockGetter(Map.of(), Block.AIR);
    // End of a block/expression
    }

    // Start of a method/block
    public static MockBlockGetter single(Block block) {
        // Returns a value to the caller
        return new MockBlockGetter(Map.of(Vec.ZERO, block), Block.AIR);
    // End of a block/expression
    }

    // Start of a method/block
    public static MockBlockGetter all(Block block) {
        // Returns a value to the caller
        return new MockBlockGetter(Map.of(), block);
    // End of a block/expression
    }

    // Calls a method
    private final Map<Vec, Block> blocks = new HashMap<>();
    // Code statement
    private final Block defaultBlock;

    // Start of a method/block
    private MockBlockGetter(Map<Vec, Block> blocks, Block defaultBlock) {
        // Calls a method
        blocks.forEach((pos, block) -> this.blocks.put(new Vec(pos.blockX(), pos.blockY(), pos.blockZ()), block));
        // Access to the current/parent object
        this.defaultBlock = defaultBlock;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @UnknownNullability Block getBlock(int x, int y, int z, Condition condition) {
        // Returns a value to the caller
        return blocks.getOrDefault(new Vec(x, y, z), defaultBlock);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setBlock(int x, int y, int z, Block block) {
        // Calls a method
        blocks.put(new Vec(x, y, z), block);
    // End of a block/expression
    }
// End of a block/expression
}
