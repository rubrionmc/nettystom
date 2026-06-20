// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.display;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;

// Déclaration de type (classe/interface/enum/record)
public class BlockDisplayMeta extends AbstractDisplayMeta {
    // Début d'une méthode/d'un bloc
    public BlockDisplayMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Block getBlockStateId() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.BlockDisplay.DISPLAYED_BLOCK_STATE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setBlockState(Block value) {
        // Appelle une méthode
        metadata.set(MetadataDef.BlockDisplay.DISPLAYED_BLOCK_STATE, value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
