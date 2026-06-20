// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class CamelMeta extends AbstractHorseMeta {
    // Début d'une méthode/d'un bloc
    public CamelMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isDashing() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Camel.DASHING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setDashing(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Camel.DASHING, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public long getLastPoseChangeTick() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Camel.LAST_POSE_CHANGE_TICK);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setLastPoseChangeTick(long value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Camel.LAST_POSE_CHANGE_TICK, value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
