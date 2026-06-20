// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.other;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.AbstractVehicleMeta;

// Déclaration de type (classe/interface/enum/record)
public class BoatMeta extends AbstractVehicleMeta {
    // Début d'une méthode/d'un bloc
    public BoatMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isLeftPaddleTurning() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Boat.IS_LEFT_PADDLE_TURNING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setLeftPaddleTurning(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Boat.IS_LEFT_PADDLE_TURNING, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isRightPaddleTurning() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Boat.IS_RIGHT_PADDLE_TURNING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setRightPaddleTurning(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Boat.IS_RIGHT_PADDLE_TURNING, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getSplashTimer() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Boat.SPLASH_TIMER);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setSplashTimer(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Boat.SPLASH_TIMER, value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
