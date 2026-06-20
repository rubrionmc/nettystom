// Déclaration du paquet de ce fichier
package net.minestom.server.world;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;

// Déclaration de type (classe/interface/enum/record)
public enum MoonPhase {
    // Instruction de code
    FULL_MOON,
    // Instruction de code
    WANING_GIBBOUS,
    // Instruction de code
    THIRD_QUARTER,
    // Instruction de code
    WANING_CRESCENT,
    // Instruction de code
    NEW_MOON,
    // Instruction de code
    WAXING_CRESCENT,
    // Instruction de code
    FIRST_QUARTER,
    // Instruction de code
    WAXING_GIBBOUS;

    // Appelle une méthode
    public static final Codec<MoonPhase> CODEC = Codec.Enum(MoonPhase.class);
// Fin d'un bloc/d'une expression
}
