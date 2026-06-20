// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class ChestedHorseMeta extends AbstractHorseMeta {
    // Début d'une méthode/d'un bloc
    protected ChestedHorseMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isHasChest() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.ChestedHorse.HAS_CHEST);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHasChest(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.ChestedHorse.HAS_CHEST, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
