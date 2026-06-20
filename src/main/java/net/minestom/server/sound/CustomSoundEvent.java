// Déclaration du paquet de ce fichier
package net.minestom.server.sound;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public record CustomSoundEvent(Key key, @Nullable Float range) implements SoundEvent {
    // Affecte une valeur
    public static final Codec<CustomSoundEvent> CODEC = StructCodec.struct(
            // Instruction de code
            "sound_id", Codec.KEY, CustomSoundEvent::key,
            // Instruction de code
            "range", Codec.FLOAT.optional(), CustomSoundEvent::range,
            // Instruction de code
            CustomSoundEvent::new);
// Fin d'un bloc/d'une expression
}
