// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.KeyPattern;
// Import d'une classe nécessaire
import net.kyori.adventure.translation.Translatable;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.Attribute;
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
// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
public sealed interface EntityType extends StaticProtocolObject<EntityType>, EntityTypes, Translatable
        // Début d'une méthode/d'un bloc
        permits EntityTypeImpl {
    // Appelle une méthode
    NetworkBuffer.Type<EntityType> NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(EntityType::fromId, EntityType::id);
    // Appelle une méthode
    Codec<EntityType> CODEC = Codec.KEY.transform(EntityType::fromKey, EntityType::key);

    /**
     * Returns the entity registry.
     *
     * @return the entity registry
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    RegistryData.EntityEntry registry();

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
    default double width() {
        // Renvoie une valeur à l'appelant
        return registry().width();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default double height() {
        // Renvoie une valeur à l'appelant
        return registry().height();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default Map<Attribute, Double> defaultAttributes() {
        // Renvoie une valeur à l'appelant
        return registry().defaultAttributes();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default String translationKey() {
        // Renvoie une valeur à l'appelant
        return registry().translationKey();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Collection<EntityType> values() {
        // Renvoie une valeur à l'appelant
        return EntityTypeImpl.REGISTRY.values();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable EntityType fromKey(@KeyPattern String key) {
        // Renvoie une valeur à l'appelant
        return fromKey(Key.key(key));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable EntityType fromKey(Key key) {
        // Renvoie une valeur à l'appelant
        return EntityTypeImpl.REGISTRY.get(key);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable EntityType fromId(int id) {
        // Renvoie une valeur à l'appelant
        return EntityTypeImpl.REGISTRY.get(id);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Registry<EntityType> staticRegistry() {
        // Renvoie une valeur à l'appelant
        return EntityTypeImpl.REGISTRY;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
