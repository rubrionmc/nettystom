// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class GoatMeta extends AnimalMeta {
    // Début d'une méthode/d'un bloc
    public GoatMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isScreaming() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Goat.IS_SCREAMING_GOAT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setScreaming(boolean screaming) {
        // Appelle une méthode
        metadata.set(MetadataDef.Goat.IS_SCREAMING_GOAT, screaming);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean hasLeftHorn() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Goat.HAS_LEFT_HORN);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setLeftHorn(boolean leftHorn) {
        // Appelle une méthode
        metadata.set(MetadataDef.Goat.HAS_LEFT_HORN, leftHorn);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean hasRightHorn() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Goat.HAS_RIGHT_HORN);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setRightHorn(boolean rightHorn) {
        // Appelle une méthode
        metadata.set(MetadataDef.Goat.HAS_RIGHT_HORN, rightHorn);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
