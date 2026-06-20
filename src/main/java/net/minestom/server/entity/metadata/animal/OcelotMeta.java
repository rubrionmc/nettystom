// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class OcelotMeta extends AnimalMeta {
    // Début d'une méthode/d'un bloc
    public OcelotMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isTrusting() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Ocelot.IS_TRUSTING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setTrusting(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Ocelot.IS_TRUSTING, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
