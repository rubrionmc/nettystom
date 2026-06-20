// Déclaration du paquet de ce fichier
package net.minestom.server.instance.gamerule;

// Import d'une classe nécessaire
import net.minestom.server.registry.Registry;
// Import d'une classe nécessaire
import net.minestom.server.registry.StaticProtocolObject;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

/// Bindings for [Game rule](https://minecraft.wiki/w/Game_rule)
// Déclaration de type (classe/interface/enum/record)
public sealed interface GameRule<T> extends GameRules, StaticProtocolObject<GameRule<?>> permits GameRuleImpl {
    // Début d'une méthode/d'un bloc
    static Registry<GameRule<?>> staticRegistry() {
        // Renvoie une valeur à l'appelant
        return GameRuleImpl.REGISTRY;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Deprecated(forRemoval = true)
    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    default Object registry() {
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    T defaultValue();
// Fin d'un bloc/d'une expression
}
