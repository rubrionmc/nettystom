// Déclaration du paquet de ce fichier
package net.minestom.server.world.attribute;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.sound.Music;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public record BackgroundMusic(
        // Annotation pour l'élément suivant
        @Nullable Music music,
        // Annotation pour l'élément suivant
        @Nullable Music creativeMusic,
        // Annotation pour l'élément suivant
        @Nullable Music underwaterMusic
// Début d'une méthode/d'un bloc
) {
    // Appelle une méthode
    public static final BackgroundMusic EMPTY = new BackgroundMusic(null, null, null);
    // Appelle une méthode
    public static final BackgroundMusic OVERWORLD = new BackgroundMusic(Music.GAME, Music.CREATIVE, null);

    // Affecte une valeur
    public static final Codec<BackgroundMusic> CODEC = StructCodec.struct(
            // Instruction de code
            "music", Music.CODEC.optional(), BackgroundMusic::music,
            // Instruction de code
            "creative_music", Music.CODEC.optional(), BackgroundMusic::creativeMusic,
            // Instruction de code
            "underwater_music", Music.CODEC.optional(), BackgroundMusic::underwaterMusic,
            // Instruction de code
            BackgroundMusic::new);
    
// Fin d'un bloc/d'une expression
}
