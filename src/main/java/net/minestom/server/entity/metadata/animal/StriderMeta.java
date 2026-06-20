// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class StriderMeta extends AnimalMeta {
    // Début d'une méthode/d'un bloc
    public StriderMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getTimeToBoost() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Strider.FUNGUS_BOOST);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setTimeToBoost(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Strider.FUNGUS_BOOST, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isShaking() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Strider.IS_SHAKING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setShaking(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Strider.IS_SHAKING, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
