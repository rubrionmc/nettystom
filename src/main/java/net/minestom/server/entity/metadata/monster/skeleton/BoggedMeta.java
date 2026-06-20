// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.monster.skeleton;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class BoggedMeta extends AbstractSkeletonMeta {
    // Début d'une méthode/d'un bloc
    public BoggedMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isSheared() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Bogged.IS_SHEARED);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setSheared(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Bogged.IS_SHEARED, value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
