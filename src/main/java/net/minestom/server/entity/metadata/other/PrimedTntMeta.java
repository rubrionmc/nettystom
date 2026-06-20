// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.other;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.EntityMeta;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;

// Déclaration de type (classe/interface/enum/record)
public class PrimedTntMeta extends EntityMeta {
    // Début d'une méthode/d'un bloc
    public PrimedTntMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getFuseTime() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.PrimedTnt.FUSE_TIME);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setFuseTime(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.PrimedTnt.FUSE_TIME, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Block getBlockState() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.PrimedTnt.BLOCK_STATE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setBlockState(Block block) {
        // Appelle une méthode
        metadata.set(MetadataDef.PrimedTnt.BLOCK_STATE, block);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
