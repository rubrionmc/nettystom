// Déclaration du paquet de ce fichier
package net.minestom.server.sound;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.KeyPattern;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Keyed;
// Import d'une classe nécessaire
import net.kyori.adventure.sound.Sound;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.Result;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collection;

/**
 * Can represent a builtin/vanilla sound or a custom sound.
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface SoundEvent extends Keyed, Sound.Type, SoundEvents permits BuiltinSoundEvent, CustomSoundEvent {

    // Affecte une valeur
    NetworkBuffer.Type<SoundEvent> NETWORK_TYPE = new NetworkBuffer.Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, SoundEvent value) {
            // Embranchement multiple (switch/case)
            switch (value) {
                // Embranchement multiple (switch/case)
                case BuiltinSoundEvent soundEvent -> buffer.write(NetworkBuffer.VAR_INT, soundEvent.id() + 1);
                // Embranchement multiple (switch/case)
                case CustomSoundEvent soundEvent -> {
                    // Instruction de code
                    buffer.write(NetworkBuffer.VAR_INT, 0); // Custom sound
                    // Appelle une méthode
                    buffer.write(NetworkBuffer.STRING, soundEvent.name());
                    // Appelle une méthode
                    buffer.write(NetworkBuffer.FLOAT.optional(), soundEvent.range());
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public SoundEvent read(NetworkBuffer buffer) {
            // Appelle une méthode
            int id = buffer.read(NetworkBuffer.VAR_INT) - 1;
            // Embranchement : vérifie une condition
            if (id != -1) return BuiltinSoundEvent.REGISTRY.get(id);

            // Renvoie une valeur à l'appelant
            return new CustomSoundEvent(buffer.read(NetworkBuffer.KEY),
                    // Appelle une méthode
                    buffer.read(NetworkBuffer.FLOAT.optional()));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };
    // Affecte une valeur
    Codec<SoundEvent> CODEC = new Codec<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<SoundEvent> decode(Transcoder<D> coder, D value) {
            // Appelle une méthode
            final Result<String> stringResult = coder.getString(value);
            // Embranchement : vérifie une condition
            if (stringResult instanceof Result.Ok(String string)) {
                // Appelle une méthode
                final SoundEvent soundEvent = BuiltinSoundEvent.get(string);
                // Embranchement : vérifie une condition
                if (soundEvent == null) return new Result.Error<>("Unknown sound event: " + string);
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(soundEvent);
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            final Result<CustomSoundEvent> customResult = CustomSoundEvent.CODEC.decode(coder, value);
            // Embranchement : vérifie une condition
            if (customResult instanceof Result.Ok(CustomSoundEvent customSoundEvent))
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(customSoundEvent);
            // Renvoie une valeur à l'appelant
            return customResult.cast();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable SoundEvent value) {
            // Embranchement : vérifie une condition
            if (value == null) return new Result.Error<>("null");
            // Renvoie une valeur à l'appelant
            return switch (value) {
                // Embranchement multiple (switch/case)
                case BuiltinSoundEvent soundEvent -> new Result.Ok<>(coder.createString(soundEvent.name()));
                // Embranchement multiple (switch/case)
                case CustomSoundEvent soundEvent -> CustomSoundEvent.CODEC.encode(coder, soundEvent);
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    /**
     * Get all the builtin sound events. Resource pack sounds will never be returned from this method.
     */
    // Début d'une méthode/d'un bloc
    static Collection<? extends SoundEvent> values() {
        // Renvoie une valeur à l'appelant
        return BuiltinSoundEvent.REGISTRY.values();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Get a builtin sound event by its namespace ID. Will never return a custom/resource pack sound.
     *
     * @param key the key of the sound event
     * @return the sound event, or null if not found
     */
    // Début d'une méthode/d'un bloc
    static @Nullable SoundEvent fromKey(@KeyPattern String key) {
        // Renvoie une valeur à l'appelant
        return fromKey(Key.key(key));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Get a builtin sound event by its key. Will never return a custom/resource pack sound.
     *
     * @param key the key of the sound event
     * @return the sound event, or null if not found
     */
    // Début d'une méthode/d'un bloc
    static @Nullable SoundEvent fromKey(Key key) {
        // Renvoie une valeur à l'appelant
        return BuiltinSoundEvent.REGISTRY.get(key);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Get a builtin sound event by its protocol ID. Will never return a custom/resource pack sound.
     *
     * @param id the ID of the sound event
     * @return the sound event, or null if not found
     */
    // Début d'une méthode/d'un bloc
    static @Nullable SoundEvent fromId(int id) {
        // Renvoie une valeur à l'appelant
        return BuiltinSoundEvent.REGISTRY.get(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Create a custom sound event. The namespace should match a sound provided in the resource pack.
     *
     * @param key   the key of the custom sound event
     * @param range the range of the sound event, or null for (legacy) dynamic range
     * @return the custom sound event
     */
    // Début d'une méthode/d'un bloc
    static SoundEvent of(String key, @Nullable Float range) {
        // Renvoie une valeur à l'appelant
        return new CustomSoundEvent(Key.key(key), range);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Create a custom sound event. The {@link Key} should match a sound provided in the resource pack.
     *
     * @param key   the key of the custom sound event
     * @param range the range of the sound event, or null for (legacy) dynamic range
     * @return the custom sound event
     */
    // Début d'une méthode/d'un bloc
    static SoundEvent of(Key key, @Nullable Float range) {
        // Renvoie une valeur à l'appelant
        return new CustomSoundEvent(key, range);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default String name() {
        // Renvoie une valeur à l'appelant
        return key().asString();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Key key();

// Fin d'un bloc/d'une expression
}
