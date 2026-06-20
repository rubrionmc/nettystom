// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.predicate.BlockPredicate;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.function.Predicate;

// Type declaration (class/interface/enum/record)
public record BlockPredicates(List<BlockPredicate> predicates) implements Predicate<Block> {
    /**
     * Will never match any block.
     */
    // Calls a method
    public static final BlockPredicates NEVER = new BlockPredicates(List.of());

    // Assigns a value
    public static final NetworkBuffer.Type<BlockPredicates> NETWORK_TYPE = BlockPredicate.NETWORK_TYPE.list(Short.MAX_VALUE)
            // Calls a method
            .transform(BlockPredicates::new, BlockPredicates::predicates);
    // Assigns a value
    public static final Codec<BlockPredicates> CODEC = BlockPredicate.CODEC.listOrSingle(Short.MAX_VALUE)
            // Calls a method
            .transform(BlockPredicates::new, BlockPredicates::predicates);

    // Start of a method/block
    public BlockPredicates {
        // Calls a method
        predicates = List.copyOf(predicates);
    // End of a block/expression
    }

    // Start of a method/block
    public BlockPredicates(BlockPredicate predicate) {
        // Calls a method
        this(List.of(predicate));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean test(Block block) {
        // Loop: repeats a block
        for (BlockPredicate predicate : predicates) {
            // Branch: checks a condition
            if (predicate.test(block)) {
                // Returns a value to the caller
                return true;
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }
// End of a block/expression
}
