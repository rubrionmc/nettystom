// Déclaration du paquet de ce fichier
package net.minestom.server.item.crossbow;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public record CrossbowChargingSounds(
        // Annotation pour l'élément suivant
        @Nullable SoundEvent start,
        // Annotation pour l'élément suivant
        @Nullable SoundEvent mid,
        // Annotation pour l'élément suivant
        @Nullable SoundEvent end
// Début d'une méthode/d'un bloc
) {
    // Affecte une valeur
    public static final Codec<CrossbowChargingSounds> NBT_TYPE = StructCodec.struct(
            // Instruction de code
            "start", SoundEvent.CODEC.optional(), CrossbowChargingSounds::start,
            // Instruction de code
            "mid", SoundEvent.CODEC.optional(), CrossbowChargingSounds::mid,
            // Instruction de code
            "end", SoundEvent.CODEC.optional(), CrossbowChargingSounds::end,
            // Instruction de code
            CrossbowChargingSounds::new);
// Fin d'un bloc/d'une expression
}
