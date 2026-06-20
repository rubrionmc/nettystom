// Déclaration du paquet de ce fichier
package net.minestom.server.statistic;

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
public sealed interface StatisticType extends StaticProtocolObject<StatisticType>, StatisticTypes permits StatisticTypeImpl {

    // Début d'une méthode/d'un bloc
    static Collection<StatisticType> values() {
        // Renvoie une valeur à l'appelant
        return StatisticTypeImpl.REGISTRY.values();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable StatisticType fromKey(@KeyPattern String key) {
        // Renvoie une valeur à l'appelant
        return fromKey(Key.key(key));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable StatisticType fromKey(Key key) {
        // Renvoie une valeur à l'appelant
        return StatisticTypeImpl.REGISTRY.get(key);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable StatisticType fromId(int id) {
        // Renvoie une valeur à l'appelant
        return StatisticTypeImpl.REGISTRY.get(id);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
