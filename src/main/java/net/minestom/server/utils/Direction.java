// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;

// Déclaration de type (classe/interface/enum/record)
public enum Direction {
    // Boucle : répète un bloc
    DOWN(0, -1, 0),
    // Instruction de code
    UP(0, 1, 0),
    // Instruction de code
    NORTH(0, 0, -1),
    // Instruction de code
    SOUTH(0, 0, 1),
    // Instruction de code
    WEST(-1, 0, 0),
    // Appelle une méthode
    EAST(1, 0, 0);

    // Affecte une valeur
    public static final Direction[] HORIZONTAL = {SOUTH, WEST, NORTH, EAST};

    // Instruction de code
    private final int normalX;
    // Instruction de code
    private final int normalY;
    // Instruction de code
    private final int normalZ;
    // Instruction de code
    private final Vec normalVec;

    // Début d'une méthode/d'un bloc
    Direction(int normalX, int normalY, int normalZ) {
        // Accès à l'objet courant/parent
        this.normalX = normalX;
        // Accès à l'objet courant/parent
        this.normalY = normalY;
        // Accès à l'objet courant/parent
        this.normalZ = normalZ;
        // Accès à l'objet courant/parent
        this.normalVec = new Vec(normalX, normalY, normalZ);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int normalX() {
        // Renvoie une valeur à l'appelant
        return normalX;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int normalY() {
        // Renvoie une valeur à l'appelant
        return normalY;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int normalZ() {
        // Renvoie une valeur à l'appelant
        return normalZ;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Vec vec() {
        // Renvoie une valeur à l'appelant
        return normalVec;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Vec mul(double mult) {
        // Renvoie une valeur à l'appelant
        return normalVec.mul(mult);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean positive() {
        // Renvoie une valeur à l'appelant
        return normalX > 0 || normalY > 0 || normalZ > 0;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean negative() {
        // Renvoie une valeur à l'appelant
        return !positive();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean vertical() {
        // Renvoie une valeur à l'appelant
        return this == UP || this == DOWN;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean horizontal() {
        // Renvoie une valeur à l'appelant
        return !vertical();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Direction opposite() {
        // Renvoie une valeur à l'appelant
        return switch (this) {
            // Embranchement multiple (switch/case)
            case UP -> DOWN;
            // Embranchement multiple (switch/case)
            case DOWN -> UP;
            // Embranchement multiple (switch/case)
            case EAST -> WEST;
            // Embranchement multiple (switch/case)
            case WEST -> EAST;
            // Embranchement multiple (switch/case)
            case NORTH -> SOUTH;
            // Embranchement multiple (switch/case)
            case SOUTH -> NORTH;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
