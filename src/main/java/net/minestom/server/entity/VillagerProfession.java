// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.KeyPattern;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
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
public sealed interface VillagerProfession extends StaticProtocolObject<VillagerProfession>, VillagerProfessions permits VillagerProfessionImpl {

    // Appelle une méthode
    NetworkBuffer.Type<VillagerProfession> NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(VillagerProfession::fromId, VillagerProfession::id);
    // Appelle une méthode
    Codec<VillagerProfession> NBT_TYPE = Codec.STRING.transform(VillagerProfession::fromKey, VillagerProfession::name);

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

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    RegistryData.VillagerProfessionEntry registry();

    // Début d'une méthode/d'un bloc
    static Collection<VillagerProfession> values() {
        // Renvoie une valeur à l'appelant
        return VillagerProfessionImpl.REGISTRY.values();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable VillagerProfession fromKey(@KeyPattern String key) {
        // Renvoie une valeur à l'appelant
        return fromKey(Key.key(key));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable VillagerProfession fromKey(Key key) {
        // Renvoie une valeur à l'appelant
        return VillagerProfessionImpl.REGISTRY.get(key);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable VillagerProfession fromId(int id) {
        // Renvoie une valeur à l'appelant
        return VillagerProfessionImpl.REGISTRY.get(id);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
