// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class AbstractVehicleMeta extends EntityMeta {
    // Début d'une méthode/d'un bloc
    public AbstractVehicleMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getShakingTicks() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.AbstractVehicle.SHAKING_POWER);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setShakingTicks(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.AbstractVehicle.SHAKING_POWER, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getShakingDirection() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.AbstractVehicle.SHAKING_DIRECTION);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setShakingDirection(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.AbstractVehicle.SHAKING_DIRECTION, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public float getShakingMultiplier() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.AbstractVehicle.SHAKING_MULTIPLIER);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setShakingMultiplier(float value) {
        // Appelle une méthode
        metadata.set(MetadataDef.AbstractVehicle.SHAKING_MULTIPLIER, value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
