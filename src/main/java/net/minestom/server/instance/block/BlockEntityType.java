// Déclaration du paquet de ce fichier
package net.minestom.server.instance.block;

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
import net.minestom.server.registry.StaticProtocolObject;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collection;

// Déclaration de type (classe/interface/enum/record)
public sealed interface BlockEntityType extends StaticProtocolObject<BlockEntityType>, BlockEntityTypes permits BlockEntityTypeImpl {
    // Appelle une méthode
    NetworkBuffer.Type<BlockEntityType> NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(BlockEntityType::fromId, BlockEntityType::id);
    // Appelle une méthode
    Codec<BlockEntityType> CODEC = Codec.KEY.transform(BlockEntityType::fromKey, BlockEntityType::key);

    // Début d'une méthode/d'un bloc
    static Collection<BlockEntityType> values() {
        // Renvoie une valeur à l'appelant
        return BlockEntityTypeImpl.REGISTRY.values();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable BlockEntityType fromKey(@KeyPattern String key) {
        // Renvoie une valeur à l'appelant
        return fromKey(Key.key(key));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable BlockEntityType fromKey(Key key) {
        // Renvoie une valeur à l'appelant
        return BlockEntityTypeImpl.REGISTRY.get(key);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable BlockEntityType fromId(int id) {
        // Renvoie une valeur à l'appelant
        return BlockEntityTypeImpl.REGISTRY.get(id);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Registry<BlockEntityType> staticRegistry() {
        // Renvoie une valeur à l'appelant
        return BlockEntityTypeImpl.REGISTRY;
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
