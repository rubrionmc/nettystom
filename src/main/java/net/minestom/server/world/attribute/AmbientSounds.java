// Déclaration du paquet de ce fichier
package net.minestom.server.world.attribute;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public record AmbientSounds(
        // Annotation pour l'élément suivant
        @Nullable SoundEvent loop,
        // Annotation pour l'élément suivant
        @Nullable Mood mood,
        // Instruction de code
        List<Additions> additions
// Début d'une méthode/d'un bloc
) {
    // Appelle une méthode
    public static final AmbientSounds EMPTY = new AmbientSounds(null, null, List.of());

    // Affecte une valeur
    public static final Codec<AmbientSounds> CODEC = StructCodec.struct(
            // Instruction de code
            "loop", SoundEvent.CODEC.optional(), AmbientSounds::loop,
            // Instruction de code
            "mood", Mood.CODEC.optional(), AmbientSounds::mood,
            // Instruction de code
            "additions", Additions.CODEC.listOrSingle().optional(List.of()), AmbientSounds::additions,
            // Instruction de code
            AmbientSounds::new);

    // Déclaration de type (classe/interface/enum/record)
    public record Mood(
            // Instruction de code
            SoundEvent sound,
            // Instruction de code
            int tickDelay,
            // Instruction de code
            int blockSearchExtent,
            // Instruction de code
            double offset
    // Début d'une méthode/d'un bloc
    ) {
        // Affecte une valeur
        public static final Codec<Mood> CODEC = StructCodec.struct(
                // Instruction de code
                "sound", SoundEvent.CODEC, Mood::sound,
                // Instruction de code
                "tick_delay", Codec.INT, Mood::tickDelay,
                // Instruction de code
                "block_search_extent", Codec.INT, Mood::blockSearchExtent,
                // Instruction de code
                "offset", Codec.DOUBLE, Mood::offset,
                // Instruction de code
                Mood::new);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Additions(SoundEvent sound, double tickChance) {
        // Affecte une valeur
        public static final Codec<Additions> CODEC = StructCodec.struct(
                // Instruction de code
                "sound", SoundEvent.CODEC, Additions::sound,
                // Instruction de code
                "tick_chance", Codec.DOUBLE, Additions::tickChance,
                // Instruction de code
                Additions::new);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
