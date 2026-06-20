// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class HoglinMeta extends AnimalMeta {
    // Début d'une méthode/d'un bloc
    public HoglinMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isImmuneToZombification() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Hoglin.IMMUNE_ZOMBIFICATION);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setImmuneToZombification(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Hoglin.IMMUNE_ZOMBIFICATION, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
