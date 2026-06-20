// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.water.fish;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class TadpoleMeta extends AbstractFishMeta {
    // Début d'une méthode/d'un bloc
    public TadpoleMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }


    // Début d'une méthode/d'un bloc
    public boolean isAgeLocked() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Tadpole.AGE_LOCKED);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setAgeLocked(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Tadpole.AGE_LOCKED, value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
