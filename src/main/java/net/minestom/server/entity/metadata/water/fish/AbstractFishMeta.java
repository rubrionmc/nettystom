// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.water.fish;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.water.WaterAnimalMeta;

// Déclaration de type (classe/interface/enum/record)
public class AbstractFishMeta extends WaterAnimalMeta {
    // Début d'une méthode/d'un bloc
    protected AbstractFishMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isFromBucket() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.AbstractFish.FROM_BUCKET);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setFromBucket(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.AbstractFish.FROM_BUCKET, value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
