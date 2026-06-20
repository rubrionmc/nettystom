// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.other;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.EntityMeta;

// Déclaration de type (classe/interface/enum/record)
public class ExperienceOrbMeta extends EntityMeta {

    // Début d'une méthode/d'un bloc
    public ExperienceOrbMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getValue() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.ExperienceOrb.VALUE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setValue(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.ExperienceOrb.VALUE, value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
