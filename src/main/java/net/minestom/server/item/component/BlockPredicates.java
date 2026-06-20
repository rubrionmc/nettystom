// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.predicate.BlockPredicate;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.function.Predicate;

// Déclaration de type (classe/interface/enum/record)
public record BlockPredicates(List<BlockPredicate> predicates) implements Predicate<Block> {
    /**
     * Will never match any block.
     */
    // Appelle une méthode
    public static final BlockPredicates NEVER = new BlockPredicates(List.of());

    // Affecte une valeur
    public static final NetworkBuffer.Type<BlockPredicates> NETWORK_TYPE = BlockPredicate.NETWORK_TYPE.list(Short.MAX_VALUE)
            // Appelle une méthode
            .transform(BlockPredicates::new, BlockPredicates::predicates);
    // Affecte une valeur
    public static final Codec<BlockPredicates> CODEC = BlockPredicate.CODEC.listOrSingle(Short.MAX_VALUE)
            // Appelle une méthode
            .transform(BlockPredicates::new, BlockPredicates::predicates);

    // Début d'une méthode/d'un bloc
    public BlockPredicates {
        // Appelle une méthode
        predicates = List.copyOf(predicates);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BlockPredicates(BlockPredicate predicate) {
        // Appelle une méthode
        this(List.of(predicate));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean test(Block block) {
        // Boucle : répète un bloc
        for (BlockPredicate predicate : predicates) {
            // Embranchement : vérifie une condition
            if (predicate.test(block)) {
                // Renvoie une valeur à l'appelant
                return true;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
