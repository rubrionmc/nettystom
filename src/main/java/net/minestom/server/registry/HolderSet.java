// Déclaration du paquet de ce fichier
package net.minestom.server.registry;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;

// Import d'une classe nécessaire
import java.util.Iterator;
// Import d'une classe nécessaire
import java.util.List;

/**
 * A HolderSet is either a registry tag or a list of direct holders. Mixing is not allowed.
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface HolderSet<T> permits HolderSet.Direct, RegistryTag {

    // Instruction de code
    static <T extends Holder<T>> Codec<HolderSet<T>> codec(
            // Instruction de code
            Registries.Selector<T> selector,
            // Instruction de code
            Codec<T> registryCodec
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new RegistryCodecs.HolderSetImpl<>(RegistryTag.codec(selector), registryCodec);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Direct<T extends Holder.Direct<T>>(List<T> values) implements HolderSet<T>, Iterable<T> {
        // Début d'une méthode/d'un bloc
        public Direct {
            // Appelle une méthode
            values = List.copyOf(values);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @SafeVarargs
        // Début d'une méthode/d'un bloc
        public Direct(T... values) {
            // Appelle une méthode
            this(List.of(values));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Iterator<T> iterator() {
            // Renvoie une valeur à l'appelant
            return values.iterator();
        // Fin d'un bloc/d'une expression
        }

    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
