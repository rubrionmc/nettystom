// Déclaration du paquet de ce fichier
package net.minestom.server.potion;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.KeyPattern;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.registry.StaticProtocolObject;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collection;

// Déclaration de type (classe/interface/enum/record)
public sealed interface PotionType extends StaticProtocolObject<PotionType>, PotionTypes permits PotionTypeImpl {

    // Appelle une méthode
    NetworkBuffer.Type<PotionType> NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(PotionType::fromId, PotionType::id);
    // Appelle une méthode
    Codec<PotionType> CODEC = Codec.KEY.transform(PotionType::fromKey, PotionType::key);

    // Début d'une méthode/d'un bloc
    static Collection<PotionType> values() {
        // Renvoie une valeur à l'appelant
        return PotionTypeImpl.REGISTRY.values();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable PotionType fromKey(@KeyPattern String key) {
        // Renvoie une valeur à l'appelant
        return fromKey(Key.key(key));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable PotionType fromKey(Key key) {
        // Renvoie une valeur à l'appelant
        return PotionTypeImpl.REGISTRY.get(key);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable PotionType fromId(int id) {
        // Renvoie une valeur à l'appelant
        return PotionTypeImpl.REGISTRY.get(id);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
