// Package declaration for this file
package net.minestom.server.sound;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;

// Type declaration (class/interface/enum/record)
public record Music(
        // Code statement
        SoundEvent sound,
        // Code statement
        int minDelay,
        // Code statement
        int maxDelay,
        // Code statement
        boolean replaceCurrentMusic
// Start of a method/block
) {
    // Calls a method
    public static final Music MENU = new Music(SoundEvents.MUSIC_MENU, 20, 600, true);
    // Calls a method
    public static final Music CREATIVE = new Music(SoundEvents.MUSIC_CREATIVE, 12000, 24000, false);
    // Calls a method
    public static final Music CREDITS = new Music(SoundEvents.MUSIC_CREDITS, 0, 0, true);
    // Calls a method
    public static final Music END_BOSS = new Music(SoundEvents.MUSIC_DRAGON, 0, 0, true);
    // Calls a method
    public static final Music END = new Music(SoundEvents.MUSIC_END, 6000, 24000, true);
    // Calls a method
    public static final Music UNDER_WATER = new Music(SoundEvents.MUSIC_UNDER_WATER, 12000, 24000, false);
    // Calls a method
    public static final Music GAME = new Music(SoundEvents.MUSIC_GAME, 12000, 24000, false);

    // Assigns a value
    public static final Codec<Music> CODEC = StructCodec.struct(
            // Code statement
            "sound", SoundEvent.CODEC, Music::sound,
            // Code statement
            "min_delay", Codec.INT, Music::minDelay,
            // Code statement
            "max_delay", Codec.INT, Music::maxDelay,
            // Code statement
            "replace_current_music", Codec.BOOLEAN.optional(false), Music::replaceCurrentMusic,
            // Code statement
            Music::new);
// End of a block/expression
}
