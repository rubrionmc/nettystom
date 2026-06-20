// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Déclaration de type (classe/interface/enum/record)
public enum Rotation {

    /**
     * No rotation
     */
    // Instruction de code
    NONE,
    /**
     * Rotated clockwise by 45 degrees
     */
    // Instruction de code
    CLOCKWISE_45,
    /**
     * Rotated clockwise by 90 degrees
     */
    // Instruction de code
    CLOCKWISE,
    /**
     * Rotated clockwise by 135 degrees
     */
    // Instruction de code
    CLOCKWISE_135,
    /**
     * Flipped upside-down, a 180 degree rotation
     */
    // Instruction de code
    FLIPPED,
    /**
     * Flipped upside-down + 45 degree rotation
     */
    // Instruction de code
    FLIPPED_45,
    /**
     * Rotated counter-clockwise by 90 degrees
     */
    // Instruction de code
    COUNTER_CLOCKWISE,
    /**
     * Rotated counter-clockwise by 45 degrees
     */
    // Instruction de code
    COUNTER_CLOCKWISE_45;

    // Appelle une méthode
    private static final Rotation[] rotations = values();

    /**
     * Rotate clockwise by 90 degrees.
     *
     * @return the relative rotation
     */
    // Début d'une méthode/d'un bloc
    public Rotation rotateClockwise() {
        // Renvoie une valeur à l'appelant
        return rotations[(this.ordinal() + 1) & 0x7];
    // Fin d'un bloc/d'une expression
    }

    /**
     * Rotate counter-clockwise by 90 degrees.
     *
     * @return the relative rotation
     */
    // Début d'une méthode/d'un bloc
    public Rotation rotateCounterClockwise() {
        // Renvoie une valeur à l'appelant
        return rotations[(this.ordinal() - 1) & 0x7];
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
