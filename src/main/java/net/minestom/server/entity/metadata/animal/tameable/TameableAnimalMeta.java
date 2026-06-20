// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal.tameable;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.AnimalMeta;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.UUID;

// Déclaration de type (classe/interface/enum/record)
public class TameableAnimalMeta extends AnimalMeta {
    // Début d'une méthode/d'un bloc
    protected TameableAnimalMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isSitting() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.TameableAnimal.IS_SITTING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setSitting(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.TameableAnimal.IS_SITTING, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isTamed() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.TameableAnimal.IS_TAMED);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setTamed(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.TameableAnimal.IS_TAMED, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public UUID getOwner() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.TameableAnimal.OWNER);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setOwner(@Nullable UUID value) {
        // Appelle une méthode
        metadata.set(MetadataDef.TameableAnimal.OWNER, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
