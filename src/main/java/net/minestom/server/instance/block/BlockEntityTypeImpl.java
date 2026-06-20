// Déclaration du paquet de ce fichier
package net.minestom.server.instance.block;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registry;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Déclaration de type (classe/interface/enum/record)
public record BlockEntityTypeImpl(Key key, int id) implements BlockEntityType {
    // Affecte une valeur
    static final Registry<BlockEntityType> REGISTRY = RegistryData.createStaticRegistry(
            // Appelle une méthode
            Key.key("block_entity_types"), BlockEntityTypeImpl::new);

    // Début d'une méthode/d'un bloc
    private BlockEntityTypeImpl(String namespace, RegistryData.Properties properties) {
        // Appelle une méthode
        this(Key.key(namespace), properties.getInt("id"));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static @UnknownNullability BlockEntityType get(String key) {
        // Renvoie une valeur à l'appelant
        return REGISTRY.get(Key.key(key));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
