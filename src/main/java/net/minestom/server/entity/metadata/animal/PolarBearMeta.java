// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class PolarBearMeta extends AnimalMeta {
    // Début d'une méthode/d'un bloc
    public PolarBearMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isStandingUp() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.PolarBear.IS_STANDING_UP);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setStandingUp(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.PolarBear.IS_STANDING_UP, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
