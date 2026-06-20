// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.monster;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class BlazeMeta extends MonsterMeta {
    // Début d'une méthode/d'un bloc
    public BlazeMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isOnFire() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Blaze.IS_ON_FIRE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setOnFire(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Blaze.IS_ON_FIRE, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
