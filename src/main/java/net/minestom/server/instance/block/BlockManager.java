// Package declaration for this file
package net.minestom.server.instance.block;

// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.instance.block.rule.BlockPlacementRule;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.slf4j.Logger;
// Import of a required class
import org.slf4j.LoggerFactory;

// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;
// Import of a required class
import java.util.function.Supplier;

// Type declaration (class/interface/enum/record)
public final class BlockManager {
    // Calls a method
    private final static Logger LOGGER = LoggerFactory.getLogger(BlockManager.class);
    // Namespace -> handler supplier
    // Calls a method
    private final Map<String, Supplier<? extends BlockHandler>> blockHandlerMap = new ConcurrentHashMap<>();
    // block id -> block placement rule
    // Calls a method
    private final Int2ObjectMap<BlockPlacementRule> placementRuleMap = new Int2ObjectOpenHashMap<>();

    // Assigns a value
    private final Set<String> dummyWarning = ConcurrentHashMap.newKeySet(); // Prevent warning spam

    // Start of a method/block
    public void registerHandler(String namespace, Supplier<? extends BlockHandler> handlerSupplier) {
        // Calls a method
        blockHandlerMap.put(namespace, handlerSupplier);
    // End of a block/expression
    }

    // Start of a method/block
    public void registerHandler(Key key, Supplier<? extends BlockHandler> handlerSupplier) {
        // Calls a method
        registerHandler(key.toString(), handlerSupplier);
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable BlockHandler getHandler(String namespace) {
        // Calls a method
        final var handler = blockHandlerMap.get(namespace);
        // Returns a value to the caller
        return handler != null ? handler.get() : null;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public BlockHandler getHandlerOrDummy(String namespace) {
        // Calls a method
        BlockHandler handler = getHandler(namespace);
        // Branch: checks a condition
        if (handler == null) {
            // Branch: checks a condition
            if (dummyWarning.add(namespace)) {
                // Code statement
                LOGGER.warn("""
                        Block {} does not have any corresponding handler, default to dummy.
                        You may want to register a handler for this namespace to prevent any data loss.""", namespace);
            // End of a block/expression
            }
            // Calls a method
            handler = BlockHandler.Dummy.get(namespace);
        // End of a block/expression
        }
        // Returns a value to the caller
        return handler;
    // End of a block/expression
    }

    /**
     * Registers a {@link BlockPlacementRule}.
     *
     * @param blockPlacementRule the block placement rule to register
     * @throws IllegalArgumentException if <code>blockPlacementRule</code> block id is negative
     */
    // Start of a method/block
    public synchronized void registerBlockPlacementRule(BlockPlacementRule blockPlacementRule) {
        // Calls a method
        final int id = blockPlacementRule.getBlock().id();
        // Calls a method
        Check.argCondition(id < 0, "Block ID must be >= 0, got: " + id);
        // Calls a method
        placementRuleMap.put(id, blockPlacementRule);
    // End of a block/expression
    }

    /**
     * Gets the {@link BlockPlacementRule} of the specific block.
     *
     * @param block the block to check
     * @return the block placement rule associated with the block, null if not any
     */
    // Start of a method/block
    public synchronized @Nullable BlockPlacementRule getBlockPlacementRule(Block block) {
        // Returns a value to the caller
        return placementRuleMap.get(block.id());
    // End of a block/expression
    }
// End of a block/expression
}
