// Package declaration for this file
package net.minestom.server.world.attribute;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.sound.Music;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public record BackgroundMusic(
        // Annotation for the following element
        @Nullable Music music,
        // Annotation for the following element
        @Nullable Music creativeMusic,
        // Annotation for the following element
        @Nullable Music underwaterMusic
// Start of a method/block
) {
    // Calls a method
    public static final BackgroundMusic EMPTY = new BackgroundMusic(null, null, null);
    // Calls a method
    public static final BackgroundMusic OVERWORLD = new BackgroundMusic(Music.GAME, Music.CREATIVE, null);

    // Assigns a value
    public static final Codec<BackgroundMusic> CODEC = StructCodec.struct(
            // Code statement
            "default", Music.CODEC.optional(), BackgroundMusic::music,
            // Code statement
            "creative", Music.CODEC.optional(), BackgroundMusic::creativeMusic,
            // Code statement
            "underwater", Music.CODEC.optional(), BackgroundMusic::underwaterMusic,
            // Code statement
            BackgroundMusic::new);
    
// End of a block/expression
}
