// Déclaration du paquet de ce fichier
package net.minestom.server.instance.heightmap;

// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;

// Déclaration de type (classe/interface/enum/record)
public class MotionBlockingHeightmap extends Heightmap {
    // Début d'une méthode/d'un bloc
    public MotionBlockingHeightmap(Chunk attachedChunk) {
        // Accès à l'objet courant/parent
        super(attachedChunk);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected boolean checkBlock(Block block) {
        // Renvoie une valeur à l'appelant
        return (block.isSolid() && !block.compare(Block.COBWEB) && !block.compare(Block.BAMBOO_SAPLING))
                // Instruction de code
                || block.isLiquid()
                // Appelle une méthode
                || "true".equals(block.getProperty("waterlogged"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Type type() {
        // Renvoie une valeur à l'appelant
        return Type.MOTION_BLOCKING;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
