// Package declaration for this file
package net.minestom.server.world.attribute;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.KeyPattern;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;

// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
record EnvironmentAttributeImpl<T>(
        // Code statement
        Key key,
        // Code statement
        EnvironmentAttribute.Type<T> type,
        // Code statement
        T defaultValue
// Start of a method/block
) implements EnvironmentAttribute<T> {
    // Code statement
    public static final DynamicRegistry<EnvironmentAttribute<?>> REGISTRY =
            // Calls a method
            DynamicRegistry.create(Key.key("environment_attribute"));
    // Assigns a value
    public static final Codec<EnvironmentAttribute<?>> CODEC = Codec.KEY.transform(
            // Code statement
            key -> Objects.requireNonNull(REGISTRY.get(key), () -> "no such environment attribute: " + key),
            // Code statement
            EnvironmentAttribute::key);

    // Code statement
    static <T> EnvironmentAttribute<T> register(
            // Annotation for the following element
            @KeyPattern String key,
            // Code statement
            EnvironmentAttribute.Type<T> type,
            // Code statement
            T defaultValue
    // Start of a method/block
    ) {
        // Calls a method
        EnvironmentAttributeImpl<T> attribute = new EnvironmentAttributeImpl<>(Key.key(key), type, defaultValue);
        // Calls a method
        REGISTRY.register(attribute.key(), attribute);
        // Returns a value to the caller
        return attribute;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Codec<T> valueCodec() {
        // Returns a value to the caller
        return type.codec();
    // End of a block/expression
    }
// End of a block/expression
}
