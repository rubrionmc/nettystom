// Package declaration for this file
package net.minestom.server.sound;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.KeyPattern;
// Import of a required class
import net.kyori.adventure.key.Keyed;
// Import of a required class
import net.kyori.adventure.sound.Sound;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.Result;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;

/**
 * Can represent a builtin/vanilla sound or a custom sound.
 */
// Type declaration (class/interface/enum/record)
public sealed interface SoundEvent extends Keyed, Sound.Type, SoundEvents permits BuiltinSoundEvent, CustomSoundEvent {

    // Assigns a value
    NetworkBuffer.Type<SoundEvent> NETWORK_TYPE = new NetworkBuffer.Type<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, SoundEvent value) {
            // Multiple branching (switch/case)
            switch (value) {
                // Multiple branching (switch/case)
                case BuiltinSoundEvent soundEvent -> buffer.write(NetworkBuffer.VAR_INT, soundEvent.id() + 1);
                // Multiple branching (switch/case)
                case CustomSoundEvent soundEvent -> {
                    // Code statement
                    buffer.write(NetworkBuffer.VAR_INT, 0); // Custom sound
                    // Calls a method
                    buffer.write(NetworkBuffer.STRING, soundEvent.name());
                    // Calls a method
                    buffer.write(NetworkBuffer.FLOAT.optional(), soundEvent.range());
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public SoundEvent read(NetworkBuffer buffer) {
            // Calls a method
            int id = buffer.read(NetworkBuffer.VAR_INT) - 1;
            // Branch: checks a condition
            if (id != -1) return BuiltinSoundEvent.REGISTRY.get(id);

            // Returns a value to the caller
            return new CustomSoundEvent(buffer.read(NetworkBuffer.KEY),
                    // Calls a method
                    buffer.read(NetworkBuffer.FLOAT.optional()));
        // End of a block/expression
        }
    // End of a block/expression
    };
    // Assigns a value
    Codec<SoundEvent> CODEC = new Codec<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<SoundEvent> decode(Transcoder<D> coder, D value) {
            // Calls a method
            final Result<String> stringResult = coder.getString(value);
            // Branch: checks a condition
            if (stringResult instanceof Result.Ok(String string)) {
                // Calls a method
                final SoundEvent soundEvent = BuiltinSoundEvent.get(string);
                // Branch: checks a condition
                if (soundEvent == null) return new Result.Error<>("Unknown sound event: " + string);
                // Returns a value to the caller
                return new Result.Ok<>(soundEvent);
            // End of a block/expression
            }

            // Calls a method
            final Result<CustomSoundEvent> customResult = CustomSoundEvent.CODEC.decode(coder, value);
            // Branch: checks a condition
            if (customResult instanceof Result.Ok(CustomSoundEvent customSoundEvent))
                // Returns a value to the caller
                return new Result.Ok<>(customSoundEvent);
            // Returns a value to the caller
            return customResult.cast();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable SoundEvent value) {
            // Branch: checks a condition
            if (value == null) return new Result.Error<>("null");
            // Returns a value to the caller
            return switch (value) {
                // Multiple branching (switch/case)
                case BuiltinSoundEvent soundEvent -> new Result.Ok<>(coder.createString(soundEvent.name()));
                // Multiple branching (switch/case)
                case CustomSoundEvent soundEvent -> CustomSoundEvent.CODEC.encode(coder, soundEvent);
            // End of a block/expression
            };
        // End of a block/expression
        }
    // End of a block/expression
    };

    /**
     * Get all the builtin sound events. Resource pack sounds will never be returned from this method.
     */
    // Start of a method/block
    static Collection<? extends SoundEvent> values() {
        // Returns a value to the caller
        return BuiltinSoundEvent.REGISTRY.values();
    // End of a block/expression
    }

    /**
     * Get a builtin sound event by its namespace ID. Will never return a custom/resource pack sound.
     *
     * @param key the key of the sound event
     * @return the sound event, or null if not found
     */
    // Start of a method/block
    static @Nullable SoundEvent fromKey(@KeyPattern String key) {
        // Returns a value to the caller
        return fromKey(Key.key(key));
    // End of a block/expression
    }

    /**
     * Get a builtin sound event by its key. Will never return a custom/resource pack sound.
     *
     * @param key the key of the sound event
     * @return the sound event, or null if not found
     */
    // Start of a method/block
    static @Nullable SoundEvent fromKey(Key key) {
        // Returns a value to the caller
        return BuiltinSoundEvent.REGISTRY.get(key);
    // End of a block/expression
    }

    /**
     * Get a builtin sound event by its protocol ID. Will never return a custom/resource pack sound.
     *
     * @param id the ID of the sound event
     * @return the sound event, or null if not found
     */
    // Start of a method/block
    static @Nullable SoundEvent fromId(int id) {
        // Returns a value to the caller
        return BuiltinSoundEvent.REGISTRY.get(id);
    // End of a block/expression
    }

    /**
     * Create a custom sound event. The namespace should match a sound provided in the resource pack.
     *
     * @param key   the key of the custom sound event
     * @param range the range of the sound event, or null for (legacy) dynamic range
     * @return the custom sound event
     */
    // Start of a method/block
    static SoundEvent of(String key, @Nullable Float range) {
        // Returns a value to the caller
        return new CustomSoundEvent(Key.key(key), range);
    // End of a block/expression
    }

    /**
     * Create a custom sound event. The {@link Key} should match a sound provided in the resource pack.
     *
     * @param key   the key of the custom sound event
     * @param range the range of the sound event, or null for (legacy) dynamic range
     * @return the custom sound event
     */
    // Start of a method/block
    static SoundEvent of(Key key, @Nullable Float range) {
        // Returns a value to the caller
        return new CustomSoundEvent(key, range);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    default String name() {
        // Returns a value to the caller
        return key().asString();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    Key key();

// End of a block/expression
}
