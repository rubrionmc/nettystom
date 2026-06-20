// Déclaration du paquet de ce fichier
package net.minestom.server.instance.block;

// Import d'une classe nécessaire
import net.minestom.server.utils.Direction;

// Déclaration de type (classe/interface/enum/record)
public enum BlockFace {
    // Instruction de code
    BOTTOM(Direction.DOWN),
    // Instruction de code
    TOP(Direction.UP),
    // Instruction de code
    NORTH(Direction.NORTH),
    // Instruction de code
    SOUTH(Direction.SOUTH),
    // Instruction de code
    WEST(Direction.WEST),
    // Appelle une méthode
    EAST(Direction.EAST);

    // Instruction de code
    private final Direction direction;

    // Début d'une méthode/d'un bloc
    BlockFace(Direction direction) {
        // Accès à l'objet courant/parent
        this.direction = direction;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Direction toDirection() {
        // Renvoie une valeur à l'appelant
        return direction;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BlockFace getOppositeFace() {
        // Renvoie une valeur à l'appelant
        return switch (this) {
            // Embranchement multiple (switch/case)
            case BOTTOM -> TOP;
            // Embranchement multiple (switch/case)
            case TOP -> BOTTOM;
            // Embranchement multiple (switch/case)
            case NORTH -> SOUTH;
            // Embranchement multiple (switch/case)
            case SOUTH -> NORTH;
            // Embranchement multiple (switch/case)
            case WEST -> EAST;
            // Embranchement multiple (switch/case)
            case EAST -> WEST;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isSimilar(BlockFace other) {
        // Renvoie une valeur à l'appelant
        return this == other || this == other.getOppositeFace();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the horizontal BlockFace from the given yaw angle
     *
     * @param yaw the yaw angle
     * @return a horizontal BlockFace
     */
    // Début d'une méthode/d'un bloc
    public static BlockFace fromYaw(float yaw) {
        // Appelle une méthode
        float degrees = (yaw - 90) % 360;
        // Embranchement : vérifie une condition
        if (degrees < 0) {
            // Instruction de code
            degrees += 360;
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (0 <= degrees && degrees < 45) {
            // Renvoie une valeur à l'appelant
            return BlockFace.WEST;
        // Embranchement : vérifie une condition
        } else if (45 <= degrees && degrees < 135) {
            // Renvoie une valeur à l'appelant
            return BlockFace.NORTH;
        // Embranchement : vérifie une condition
        } else if (135 <= degrees && degrees < 225) {
            // Renvoie une valeur à l'appelant
            return BlockFace.EAST;
        // Embranchement : vérifie une condition
        } else if (225 <= degrees && degrees < 315) {
            // Renvoie une valeur à l'appelant
            return BlockFace.SOUTH;
        // Branche alternative de la condition
        } else { // 315 <= degrees && degrees < 360
            // Renvoie une valeur à l'appelant
            return BlockFace.WEST;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Get the BlockFace corresponding to the given {@link Direction}.
     *
     * @param direction the direction
     * @return the corresponding BlockFace
     */
    // Début d'une méthode/d'un bloc
    public static BlockFace fromDirection(Direction direction) {
        // Renvoie une valeur à l'appelant
        return switch (direction) {
            // Embranchement multiple (switch/case)
            case UP -> TOP;
            // Embranchement multiple (switch/case)
            case DOWN -> BOTTOM;
            // Embranchement multiple (switch/case)
            case NORTH -> NORTH;
            // Embranchement multiple (switch/case)
            case SOUTH -> SOUTH;
            // Embranchement multiple (switch/case)
            case WEST -> WEST;
            // Embranchement multiple (switch/case)
            case EAST -> EAST;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
