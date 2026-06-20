// Déclaration du paquet de ce fichier
package net.minestom.server.sound;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;

// Déclaration de type (classe/interface/enum/record)
public record Music(
        // Instruction de code
        SoundEvent sound,
        // Instruction de code
        int minDelay,
        // Instruction de code
        int maxDelay,
        // Instruction de code
        boolean replaceCurrentMusic
// Début d'une méthode/d'un bloc
) {
    // Appelle une méthode
    public static final Music MENU = new Music(SoundEvents.MUSIC_MENU, 20, 600, true);
    // Appelle une méthode
    public static final Music CREATIVE = new Music(SoundEvents.MUSIC_CREATIVE, 12000, 24000, false);
    // Appelle une méthode
    public static final Music CREDITS = new Music(SoundEvents.MUSIC_CREDITS, 0, 0, true);
    // Appelle une méthode
    public static final Music END_BOSS = new Music(SoundEvents.MUSIC_DRAGON, 0, 0, true);
    // Appelle une méthode
    public static final Music END = new Music(SoundEvents.MUSIC_END, 6000, 24000, true);
    // Appelle une méthode
    public static final Music UNDER_WATER = new Music(SoundEvents.MUSIC_UNDER_WATER, 12000, 24000, false);
    // Appelle une méthode
    public static final Music GAME = new Music(SoundEvents.MUSIC_GAME, 12000, 24000, false);

    // Affecte une valeur
    public static final Codec<Music> CODEC = StructCodec.struct(
            // Instruction de code
            "sound", SoundEvent.CODEC, Music::sound,
            // Instruction de code
            "min_delay", Codec.INT, Music::minDelay,
            // Instruction de code
            "max_delay", Codec.INT, Music::maxDelay,
            // Instruction de code
            "replace_current_music", Codec.BOOLEAN.optional(false), Music::replaceCurrentMusic,
            // Instruction de code
            Music::new);
// Fin d'un bloc/d'une expression
}
