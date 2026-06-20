// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class AbstractHorseMeta extends AnimalMeta {
    // Début d'une méthode/d'un bloc
    protected AbstractHorseMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isTamed() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.AbstractHorse.IS_TAME);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setTamed(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.AbstractHorse.IS_TAME, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isHasBred() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.AbstractHorse.HAS_BRED);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHasBred(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.AbstractHorse.HAS_BRED, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isEating() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.AbstractHorse.IS_EATING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setEating(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.AbstractHorse.IS_EATING, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isRearing() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.AbstractHorse.IS_REARING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setRearing(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.AbstractHorse.IS_REARING, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isMouthOpen() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.AbstractHorse.IS_MOUTH_OPEN);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setMouthOpen(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.AbstractHorse.IS_MOUTH_OPEN, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
