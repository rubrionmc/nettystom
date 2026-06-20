// Package declaration for this file
package net.minestom.server.world.attribute;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.sound.SoundEvent;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public record AmbientSounds(
        // Annotation for the following element
        @Nullable SoundEvent loop,
        // Annotation for the following element
        @Nullable Mood mood,
        // Code statement
        List<Additions> additions
// Start of a method/block
) {
    // Calls a method
    public static final AmbientSounds EMPTY = new AmbientSounds(null, null, List.of());

    // Assigns a value
    public static final Codec<AmbientSounds> CODEC = StructCodec.struct(
            // Code statement
            "loop", SoundEvent.CODEC.optional(), AmbientSounds::loop,
            // Code statement
            "mood", Mood.CODEC.optional(), AmbientSounds::mood,
            // Code statement
            "additions", Additions.CODEC.listOrSingle().optional(List.of()), AmbientSounds::additions,
            // Code statement
            AmbientSounds::new);

    // Type declaration (class/interface/enum/record)
    public record Mood(
            // Code statement
            SoundEvent sound,
            // Code statement
            int tickDelay,
            // Code statement
            int blockSearchExtent,
            // Code statement
            double offset
    // Start of a method/block
    ) {
        // Assigns a value
        public static final Codec<Mood> CODEC = StructCodec.struct(
                // Code statement
                "sound", SoundEvent.CODEC, Mood::sound,
                // Code statement
                "tick_delay", Codec.INT, Mood::tickDelay,
                // Code statement
                "block_search_extent", Codec.INT, Mood::blockSearchExtent,
                // Code statement
                "offset", Codec.DOUBLE, Mood::offset,
                // Code statement
                Mood::new);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Additions(SoundEvent sound, double tickChance) {
        // Assigns a value
        public static final Codec<Additions> CODEC = StructCodec.struct(
                // Code statement
                "sound", SoundEvent.CODEC, Additions::sound,
                // Code statement
                "tick_chance", Codec.DOUBLE, Additions::tickChance,
                // Code statement
                Additions::new);
    // End of a block/expression
    }
// End of a block/expression
}
