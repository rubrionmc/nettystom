// Package declaration for this file
package net.minestom.server.item.crossbow;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.sound.SoundEvent;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public record CrossbowChargingSounds(
        // Annotation for the following element
        @Nullable SoundEvent start,
        // Annotation for the following element
        @Nullable SoundEvent mid,
        // Annotation for the following element
        @Nullable SoundEvent end
// Start of a method/block
) {
    // Assigns a value
    public static final Codec<CrossbowChargingSounds> NBT_TYPE = StructCodec.struct(
            // Code statement
            "start", SoundEvent.CODEC.optional(), CrossbowChargingSounds::start,
            // Code statement
            "mid", SoundEvent.CODEC.optional(), CrossbowChargingSounds::mid,
            // Code statement
            "end", SoundEvent.CODEC.optional(), CrossbowChargingSounds::end,
            // Code statement
            CrossbowChargingSounds::new);
// End of a block/expression
}
