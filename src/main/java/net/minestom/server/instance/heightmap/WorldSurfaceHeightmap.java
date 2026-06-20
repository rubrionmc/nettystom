// Déclaration du paquet de ce fichier
package net.minestom.server.instance.heightmap;

// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;

// Déclaration de type (classe/interface/enum/record)
public class WorldSurfaceHeightmap extends Heightmap {
    // Début d'une méthode/d'un bloc
    public WorldSurfaceHeightmap(Chunk attachedChunk) {
        // Accès à l'objet courant/parent
        super(attachedChunk);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected boolean checkBlock(Block block) {
        // Renvoie une valeur à l'appelant
        return !block.isAir();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Type type() {
        // Renvoie une valeur à l'appelant
        return Type.WORLD_SURFACE;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
