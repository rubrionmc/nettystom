// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.other;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.PathfinderMobMeta;

// Déclaration de type (classe/interface/enum/record)
public class AllayMeta extends PathfinderMobMeta {
    // Début d'une méthode/d'un bloc
    public AllayMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isDancing() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Allay.IS_DANCING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setDancing(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Allay.IS_DANCING, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean canDuplicate() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Allay.CAN_DUPLICATE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setCanDuplicate(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Allay.CAN_DUPLICATE, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
