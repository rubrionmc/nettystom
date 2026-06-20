// Déclaration du paquet de ce fichier
package net.minestom.server;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.KeyPattern;
// Import d'une classe nécessaire
import net.minestom.server.registry.StaticProtocolObject;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collection;

// Déclaration de type (classe/interface/enum/record)
public sealed interface FeatureFlag extends StaticProtocolObject<FeatureFlag>, FeatureFlags permits FeatureFlagImpl {

    // Début d'une méthode/d'un bloc
    static Collection<FeatureFlag> values() {
        // Renvoie une valeur à l'appelant
        return FeatureFlagImpl.REGISTRY.values();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable FeatureFlag fromKey(@KeyPattern String key) {
        // Renvoie une valeur à l'appelant
        return fromKey(Key.key(key));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable FeatureFlag fromKey(Key key) {
        // Renvoie une valeur à l'appelant
        return FeatureFlagImpl.REGISTRY.get(key);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable FeatureFlag fromId(int id) {
        // Renvoie une valeur à l'appelant
        return FeatureFlagImpl.REGISTRY.get(id);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
