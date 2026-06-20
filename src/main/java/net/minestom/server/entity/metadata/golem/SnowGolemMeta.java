// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.golem;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class SnowGolemMeta extends AbstractGolemMeta {
    // Début d'une méthode/d'un bloc
    public SnowGolemMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isHasPumpkinHat() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.SnowGolem.PUMPKIN_HAT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHasPumpkinHat(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.SnowGolem.PUMPKIN_HAT, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
