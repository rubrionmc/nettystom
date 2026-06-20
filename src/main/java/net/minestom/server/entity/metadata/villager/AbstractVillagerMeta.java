// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.villager;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.AgeableMobMeta;

// Déclaration de type (classe/interface/enum/record)
public class AbstractVillagerMeta extends AgeableMobMeta {
    // Début d'une méthode/d'un bloc
    protected AbstractVillagerMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getHeadShakeTimer() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.AbstractVillager.HEAD_SHAKE_TIMER);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHeadShakeTimer(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.AbstractVillager.HEAD_SHAKE_TIMER, value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
