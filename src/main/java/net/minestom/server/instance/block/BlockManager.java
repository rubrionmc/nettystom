// Déclaration du paquet de ce fichier
package net.minestom.server.instance.block;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.rule.BlockPlacementRule;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.slf4j.Logger;
// Import d'une classe nécessaire
import org.slf4j.LoggerFactory;

// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;
// Import d'une classe nécessaire
import java.util.function.Supplier;

// Déclaration de type (classe/interface/enum/record)
public final class BlockManager {
    // Appelle une méthode
    private final static Logger LOGGER = LoggerFactory.getLogger(BlockManager.class);
    // Namespace -> handler supplier
    // Affecte une valeur
    private final Map<String, Supplier<? extends BlockHandler>> blockHandlerMap = new ConcurrentHashMap<>();
    // block id -> block placement rule
    // Affecte une valeur
    private final Int2ObjectMap<BlockPlacementRule> placementRuleMap = new Int2ObjectOpenHashMap<>();

    // Affecte une valeur
    private final Set<String> dummyWarning = ConcurrentHashMap.newKeySet(); // Prevent warning spam

    // Début d'une méthode/d'un bloc
    public void registerHandler(String namespace, Supplier<? extends BlockHandler> handlerSupplier) {
        // Appelle une méthode
        blockHandlerMap.put(namespace, handlerSupplier);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void registerHandler(Key key, Supplier<? extends BlockHandler> handlerSupplier) {
        // Appelle une méthode
        registerHandler(key.toString(), handlerSupplier);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable BlockHandler getHandler(String namespace) {
        // Appelle une méthode
        final var handler = blockHandlerMap.get(namespace);
        // Renvoie une valeur à l'appelant
        return handler != null ? handler.get() : null;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public BlockHandler getHandlerOrDummy(String namespace) {
        // Appelle une méthode
        BlockHandler handler = getHandler(namespace);
        // Embranchement : vérifie une condition
        if (handler == null) {
            // Embranchement : vérifie une condition
            if (dummyWarning.add(namespace)) {
                // Instruction de code
                LOGGER.warn("""
                        Block {} does not have any corresponding handler, default to dummy.
                        You may want to register a handler for this namespace to prevent any data loss.""", namespace);
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            handler = BlockHandler.Dummy.get(namespace);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return handler;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Registers a {@link BlockPlacementRule}.
     *
     * @param blockPlacementRule the block placement rule to register
     * @throws IllegalArgumentException if <code>blockPlacementRule</code> block id is negative
     */
    // Début d'une méthode/d'un bloc
    public synchronized void registerBlockPlacementRule(BlockPlacementRule blockPlacementRule) {
        // Appelle une méthode
        final int id = blockPlacementRule.getBlock().id();
        // Appelle une méthode
        Check.argCondition(id < 0, "Block ID must be >= 0, got: " + id);
        // Appelle une méthode
        placementRuleMap.put(id, blockPlacementRule);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the {@link BlockPlacementRule} of the specific block.
     *
     * @param block the block to check
     * @return the block placement rule associated with the block, null if not any
     */
    // Début d'une méthode/d'un bloc
    public synchronized @Nullable BlockPlacementRule getBlockPlacementRule(Block block) {
        // Renvoie une valeur à l'appelant
        return placementRuleMap.get(block.id());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
