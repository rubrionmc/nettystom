// Déclaration du paquet de ce fichier
package net.minestom.server.collision;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.BlockVec;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

/**
 * A block-aligned, absolute bounding box.
 *
 * <p>This is in contrast to BoundingBox which is relative to its owner's position, and precise.</p>
 */
// Déclaration de type (classe/interface/enum/record)
public record BlockBoundingBox(Point min, Point max) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<BlockBoundingBox> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.BLOCK_POSITION, BlockBoundingBox::min,
            // Instruction de code
            NetworkBuffer.BLOCK_POSITION, BlockBoundingBox::max,
            // Instruction de code
            BlockBoundingBox::new);

    // Début d'une méthode/d'un bloc
    public BlockBoundingBox(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        // Appelle une méthode
        this(new BlockVec(minX, minY, minZ), new BlockVec(maxX, maxY, maxZ));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int minX() {
        // Renvoie une valeur à l'appelant
        return min.blockX();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int minY() {
        // Renvoie une valeur à l'appelant
        return min.blockY();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int minZ() {
        // Renvoie une valeur à l'appelant
        return min.blockZ();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int maxX() {
        // Renvoie une valeur à l'appelant
        return max.blockX();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int maxY() {
        // Renvoie une valeur à l'appelant
        return max.blockY();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int maxZ() {
        // Renvoie une valeur à l'appelant
        return max.blockZ();
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
