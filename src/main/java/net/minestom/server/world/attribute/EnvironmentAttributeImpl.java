// Déclaration du paquet de ce fichier
package net.minestom.server.world.attribute;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.KeyPattern;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;

// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
record EnvironmentAttributeImpl<T>(
        // Instruction de code
        Key key,
        // Instruction de code
        EnvironmentAttribute.Type<T> type,
        // Instruction de code
        T defaultValue
// Début d'une méthode/d'un bloc
) implements EnvironmentAttribute<T> {
    // Instruction de code
    public static final DynamicRegistry<EnvironmentAttribute<?>> REGISTRY =
            // Appelle une méthode
            DynamicRegistry.create(Key.key("environment_attribute"));
    // Affecte une valeur
    public static final Codec<EnvironmentAttribute<?>> CODEC = Codec.KEY.transform(
            // Instruction de code
            key -> Objects.requireNonNull(REGISTRY.get(key), () -> "no such environment attribute: " + key),
            // Instruction de code
            EnvironmentAttribute::key);

    // Instruction de code
    static <T> EnvironmentAttribute<T> register(
            // Annotation pour l'élément suivant
            @KeyPattern String key,
            // Instruction de code
            EnvironmentAttribute.Type<T> type,
            // Instruction de code
            T defaultValue
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        EnvironmentAttributeImpl<T> attribute = new EnvironmentAttributeImpl<>(Key.key(key), type, defaultValue);
        // Appelle une méthode
        REGISTRY.register(attribute.key(), attribute);
        // Renvoie une valeur à l'appelant
        return attribute;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Codec<T> valueCodec() {
        // Renvoie une valeur à l'appelant
        return type.codec();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
