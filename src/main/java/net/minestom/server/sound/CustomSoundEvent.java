// Package declaration for this file
package net.minestom.server.sound;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public record CustomSoundEvent(Key key, @Nullable Float range) implements SoundEvent {
    // Assigns a value
    public static final Codec<CustomSoundEvent> CODEC = StructCodec.struct(
            // Code statement
            "sound_id", Codec.KEY, CustomSoundEvent::key,
            // Code statement
            "range", Codec.FLOAT.optional(), CustomSoundEvent::range,
            // Code statement
            CustomSoundEvent::new);
// End of a block/expression
}
