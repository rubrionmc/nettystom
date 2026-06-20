// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class TurtleMeta extends AnimalMeta {
    // Début d'une méthode/d'un bloc
    public TurtleMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isHasEgg() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Turtle.HAS_EGG);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHasEgg(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Turtle.HAS_EGG, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isLayingEgg() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Turtle.IS_LAYING_EGG);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setLayingEgg(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Turtle.IS_LAYING_EGG, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
