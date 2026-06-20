// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.tameable.TameableAnimalMeta;

// Déclaration de type (classe/interface/enum/record)
public class AbstractNautilusMeta extends TameableAnimalMeta {
    // Début d'une méthode/d'un bloc
    public AbstractNautilusMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isDashing() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.AbstractNautilus.DASH);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setDashing(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.AbstractNautilus.DASH, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
