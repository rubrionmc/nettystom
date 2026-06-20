// Déclaration du paquet de ce fichier
package net.minestom.server.item;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.KeyPattern;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponentMap;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
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
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.Collection;

// Déclaration de type (classe/interface/enum/record)
public sealed interface Material extends StaticProtocolObject<Material>, Materials permits MaterialImpl {

    // Appelle une méthode
    NetworkBuffer.Type<Material> NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(Material::fromId, Material::id);
    // Appelle une méthode
    Codec<Material> CODEC = Codec.KEY.transform(Material::fromKey, Material::key);

    /**
     * Returns the raw registry data for the material.
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    RegistryData.MaterialEntry registry();

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
    default boolean isBlock() {
        // Renvoie une valeur à l'appelant
        return registry().block() != null;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default @UnknownNullability Block block() {
        // Renvoie une valeur à l'appelant
        return registry().block();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default DataComponentMap prototype() {
        // Renvoie une valeur à l'appelant
        return registry().prototype();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default boolean isArmor() {
        // Renvoie une valeur à l'appelant
        return registry().isArmor();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default int maxStackSize() {
        // Renvoie une valeur à l'appelant
        return prototype().get(DataComponents.MAX_STACK_SIZE, 64);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Collection<Material> values() {
        // Renvoie une valeur à l'appelant
        return MaterialImpl.REGISTRY.values();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable Material fromKey(@KeyPattern String key) {
        // Renvoie une valeur à l'appelant
        return fromKey(Key.key(key));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable Material fromKey(Key key) {
        // Renvoie une valeur à l'appelant
        return MaterialImpl.REGISTRY.get(key);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable Material fromId(int id) {
        // Renvoie une valeur à l'appelant
        return MaterialImpl.REGISTRY.get(id);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Registry<Material> staticRegistry() {
        // Renvoie une valeur à l'appelant
        return MaterialImpl.REGISTRY;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
