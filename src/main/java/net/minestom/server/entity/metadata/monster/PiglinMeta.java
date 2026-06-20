// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.monster;

// Import d'une classe nécessaire
import net.minestom.server.collision.BoundingBox;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class PiglinMeta extends BasePiglinMeta {
    // Début d'une méthode/d'un bloc
    public PiglinMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isBaby() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Piglin.IS_BABY);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setBaby(boolean value) {
        // Embranchement : vérifie une condition
        if (isBaby() == value) {
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Accès à l'objet courant/parent
        this.consumeEntity((entity) -> {
            // Appelle une méthode
            BoundingBox bb = entity.getBoundingBox();
            // Embranchement : vérifie une condition
            if (value) {
                // Appelle une méthode
                double width = bb.width() / 2;
                // Appelle une méthode
                entity.setBoundingBox(width, bb.height() / 2, width);
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                double width = bb.width() * 2;
                // Appelle une méthode
                entity.setBoundingBox(width, bb.height() * 2, width);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        metadata.set(MetadataDef.Piglin.IS_BABY, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isChargingCrossbow() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Piglin.IS_CHARGING_CROSSBOW);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setChargingCrossbow(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Piglin.IS_CHARGING_CROSSBOW, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isDancing() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Piglin.IS_DANCING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setDancing(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Piglin.IS_DANCING, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
