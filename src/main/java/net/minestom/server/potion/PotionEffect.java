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
import net.minestom.server.registry.Registry;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import net.minestom.server.registry.StaticProtocolObject;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collection;

// Déclaration de type (classe/interface/enum/record)
public sealed interface PotionEffect extends StaticProtocolObject<PotionEffect>, PotionEffects permits PotionEffectImpl {
    // Appelle une méthode
    NetworkBuffer.Type<PotionEffect> NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(PotionEffect::fromId, PotionEffect::id);
    // Appelle une méthode
    Codec<PotionEffect> CODEC = Codec.KEY.transform(PotionEffect::fromKey, PotionEffect::key);

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    RegistryData.PotionEffectEntry registry();

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default Key key() {
        // Renvoie une valeur à l'appelant
        return registry().key();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default int id() {
        // Renvoie une valeur à l'appelant
        return registry().id();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Collection<PotionEffect> values() {
        // Renvoie une valeur à l'appelant
        return PotionEffectImpl.REGISTRY.values();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable PotionEffect fromKey(@KeyPattern String key) {
        // Renvoie une valeur à l'appelant
        return fromKey(Key.key(key));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable PotionEffect fromKey(Key key) {
        // Renvoie une valeur à l'appelant
        return PotionEffectImpl.REGISTRY.get(key);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable PotionEffect fromId(int id) {
        // Renvoie une valeur à l'appelant
        return PotionEffectImpl.REGISTRY.get(id);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Registry<PotionEffect> staticRegistry() {
        // Renvoie une valeur à l'appelant
        return PotionEffectImpl.REGISTRY;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
