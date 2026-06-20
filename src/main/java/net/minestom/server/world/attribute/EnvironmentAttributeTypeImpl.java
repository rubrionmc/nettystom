// Package declaration for this file
package net.minestom.server.world.attribute;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.KeyPattern;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.world.attribute.EnvironmentAttribute.Modifier;

// Import of a required class
import java.util.HashMap;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
record EnvironmentAttributeTypeImpl<T>(
        // Code statement
        Key key,
        // Code statement
        Codec<T> codec,
        // Code statement
        Codec<Modifier<T, ?>> modifierCodec
// Start of a method/block
) implements EnvironmentAttribute.Type<T> {

    // Code statement
    static <T> EnvironmentAttribute.Type<T> register(
            // Annotation for the following element
            @KeyPattern String key,
            // Code statement
            Codec<T> codec,
            // Code statement
            Map<Modifier.Operator, Modifier<T, ?>> operators
    // Start of a method/block
    ) {
        // Calls a method
        final var withOverride = new HashMap<>(operators);
        // Calls a method
        withOverride.put(Modifier.Operator.OVERRIDE, new Modifier.Override<>(codec));

        // Calls a method
        final var inverse = new HashMap<Modifier<T, ?>, Modifier.Operator>(operators.size());
        // Loop: repeats a block
        for (var entry : operators.entrySet()) inverse.put(entry.getValue(), entry.getKey());

        // Assigns a value
        final Codec<Modifier<T, ?>> modifierCodec = Modifier.Operator.CODEC.transform(op ->
                // Calls a method
                Objects.requireNonNull(withOverride.get(op), () -> "unsupported operator: " + op), inverse::get);
        // Returns a value to the caller
        return new EnvironmentAttributeTypeImpl<>(Key.key(key), codec, modifierCodec);
    // End of a block/expression
    }
// End of a block/expression
}
