// Déclaration du paquet de ce fichier
package net.minestom.server.registry;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Keyed;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;

// Déclaration de type (classe/interface/enum/record)
public sealed interface TagKey<T> extends Keyed permits TagKeyImpl {
    // Début d'une méthode/d'un bloc
    static <T> Codec<TagKey<T>> codec(Registries.Selector<T> selector) {
        // Renvoie une valeur à l'appelant
        return new RegistryCodecs.TagKeyImpl<>(selector, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> Codec<TagKey<T>> hashCodec(Registries.Selector<T> selector) {
        // Renvoie une valeur à l'appelant
        return new RegistryCodecs.TagKeyImpl<>(selector, true);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> NetworkBuffer.Type<TagKey<T>> networkType(Registries.Selector<T> selector) {
        // Renvoie une valeur à l'appelant
        return NetworkBuffer.KEY.transform(TagKeyImpl::new, TagKey::key);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> TagKey<T> ofHash(String hashedKey) {
        // Embranchement : vérifie une condition
        if (!hashedKey.startsWith("#"))
            // Lève une exception
            throw new IllegalArgumentException("Hashed key must start with '#': " + hashedKey);
        // Renvoie une valeur à l'appelant
        return new TagKeyImpl<>(Key.key(hashedKey.substring(1)));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default String hashedKey() {
        // Renvoie une valeur à l'appelant
        return "#" + key().asString();
    // Fin d'un bloc/d'une expression
    }


// Fin d'un bloc/d'une expression
}
