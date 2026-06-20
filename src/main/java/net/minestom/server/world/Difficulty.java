// Déclaration du paquet de ce fichier
package net.minestom.server.world;

/**
 * Those are all the difficulties which can be displayed in the player menu.
 * <p>
 * Sets with {@link net.minestom.server.MinecraftServer#setDifficulty(Difficulty)}.
 */
// Déclaration de type (classe/interface/enum/record)
public enum Difficulty {

    // Appelle une méthode
    PEACEFUL((byte) 0), EASY((byte) 1), NORMAL((byte) 2), HARD((byte) 3);

    // Instruction de code
    private final byte id;

    // Début d'une méthode/d'un bloc
    Difficulty(byte id) {
        // Accès à l'objet courant/parent
        this.id = id;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public byte getId() {
        // Renvoie une valeur à l'appelant
        return id;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
