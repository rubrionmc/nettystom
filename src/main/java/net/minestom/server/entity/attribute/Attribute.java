// Déclaration du paquet de ce fichier
package net.minestom.server.entity.attribute;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.KeyPattern;
// Import d'une classe nécessaire
import net.kyori.adventure.translation.Translatable;
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
public sealed interface Attribute extends StaticProtocolObject<Attribute>, Attributes,
        // Début d'une méthode/d'un bloc
        Translatable permits AttributeImpl {
    // Appelle une méthode
    NetworkBuffer.Type<Attribute> NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(Attribute::fromId, Attribute::id);
    // Appelle une méthode
    Codec<Attribute> CODEC = Codec.STRING.transform(AttributeImpl::get, Attribute::name);

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    RegistryData.AttributeEntry registry();

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
    default double defaultValue() {
        // Renvoie une valeur à l'appelant
        return registry().defaultValue();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default double minValue() {
        // Renvoie une valeur à l'appelant
        return registry().minValue();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default double maxValue() {
        // Renvoie une valeur à l'appelant
        return registry().maxValue();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default boolean isSynced() {
        // Renvoie une valeur à l'appelant
        return registry().clientSync();
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
    static Collection<Attribute> values() {
        // Renvoie une valeur à l'appelant
        return AttributeImpl.REGISTRY.values();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable Attribute fromKey(@KeyPattern String key) {
        // Renvoie une valeur à l'appelant
        return fromKey(Key.key(key));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable Attribute fromKey(Key key) {
        // Renvoie une valeur à l'appelant
        return AttributeImpl.REGISTRY.get(key);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable Attribute fromId(int id) {
        // Renvoie une valeur à l'appelant
        return AttributeImpl.REGISTRY.get(id);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
