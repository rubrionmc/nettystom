// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.flying;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class GhastMeta extends FlyingMeta {
    // Début d'une méthode/d'un bloc
    public GhastMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isAttacking() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Ghast.IS_ATTACKING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setAttacking(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Ghast.IS_ATTACKING, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
