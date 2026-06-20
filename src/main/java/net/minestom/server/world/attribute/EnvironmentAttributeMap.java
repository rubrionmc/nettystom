// Package declaration for this file
package net.minestom.server.world.attribute;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.utils.Either;
// Import of a required class
import net.minestom.server.world.attribute.EnvironmentAttribute.Modifier;

// Import of a required class
import java.util.HashMap;
// Import of a required class
import java.util.Map;

// Type declaration (class/interface/enum/record)
public record EnvironmentAttributeMap(Map<EnvironmentAttribute<?>, Entry<?, ?>> entries) {
    // Calls a method
    public static final EnvironmentAttributeMap EMPTY = new EnvironmentAttributeMap(Map.of());

    // Assigns a value
    public static final Codec<EnvironmentAttributeMap> CODEC = EnvironmentAttribute.CODEC
            // Code statement
            .mapValueTyped(Entry::codec0, true)
            // Calls a method
            .transform(EnvironmentAttributeMap::new, EnvironmentAttributeMap::entries);

    // Start of a method/block
    public static Builder builder() {
        // Returns a value to the caller
        return new Builder();
    // End of a block/expression
    }

    // Start of a method/block
    public static Builder builder(EnvironmentAttributeMap existing) {
        // Returns a value to the caller
        return new Builder(existing.entries);
    // End of a block/expression
    }

    // Start of a method/block
    public EnvironmentAttributeMap {
        // Calls a method
        entries = Map.copyOf(entries);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Entry<T, Arg>(Arg argument, Modifier<T, Arg> modifier) {

        // Annotation for the following element
        @SuppressWarnings("unchecked")
        // Start of a method/block
        public static <T> Codec<Entry<T, ?>> codec(EnvironmentAttribute<T> attribute) {
            // A value is represented by either a single value which acts as an override,
            // or a struct with `modifier` and `argument` keys (full codec).

            // Assigns a value
            Codec<Entry<T, ?>> fullCodec = attribute.type().modifierCodec()
                    // Calls a method
                    .unionType("modifier", Entry::fullCodec, Entry::modifier);

            // Calls a method
            final var override = new Modifier.Override<>(attribute.valueCodec());
            // Returns a value to the caller
            return Codec.Either(attribute.valueCodec(), fullCodec).transform(
                    // Code statement
                    either -> either.unify(
                            // Code statement
                            value -> new Entry<>(value, override),
                            // Code statement
                            u -> u),
                    // Code statement
                    entry -> entry.modifier instanceof Modifier.Override
                            // Calls a method
                            ? Either.left((T) entry.argument) : Either.right(entry));
        // End of a block/expression
        }

        // Start of a method/block
        private static Codec<Entry<?, ?>> codec0(EnvironmentAttribute<?> attribute) {
            //noinspection unchecked,rawtypes
            // Returns a value to the caller
            return (Codec) codec(attribute);
        // End of a block/expression
        }

        // Start of a method/block
        private static <T, Arg> StructCodec<Entry<T, Arg>> fullCodec(Modifier<T, Arg> modifier) {
            // Returns a value to the caller
            return StructCodec.struct(
                    // Code statement
                    "argument", modifier.argumentCodec(), Entry::argument,
                    // Code statement
                    (argument) -> new Entry<>(argument, modifier)
            // End of a block/expression
            );
        // End of a block/expression
        }

    // End of a block/expression
    }

    // Start of a method/block
    public static final class Builder {
        // Calls a method
        private final Map<EnvironmentAttribute<?>, Entry<?, ?>> entries = new HashMap<>();

        // Start of a method/block
        public Builder() {

        // End of a block/expression
        }

        // Start of a method/block
        public Builder(Map<EnvironmentAttribute<?>, Entry<?, ?>> existing) {
            // Calls a method
            entries.putAll(existing);
        // End of a block/expression
        }

        // Start of a method/block
        public <T> Builder set(EnvironmentAttribute<T> attribute, T value) {
            // Calls a method
            entries.put(attribute, new Entry<>(value, new Modifier.Override<>(attribute.valueCodec())));
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public <T, Arg> Builder modify(EnvironmentAttribute<T> attribute, Modifier<T, Arg> modifier, Arg argument) {
            // Calls a method
            entries.put(attribute, new Entry<>(argument, modifier));
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public EnvironmentAttributeMap build() {
            // Returns a value to the caller
            return new EnvironmentAttributeMap(entries);
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
