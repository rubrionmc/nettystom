// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.monster.raider;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class PillagerMeta extends AbstractIllagerMeta {
    // Début d'une méthode/d'un bloc
    public PillagerMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isChargingCrossbow() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Pillager.IS_CHARGING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setChargingCrossbow(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Pillager.IS_CHARGING, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
