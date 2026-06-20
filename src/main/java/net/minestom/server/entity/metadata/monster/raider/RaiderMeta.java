// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.monster.raider;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.monster.MonsterMeta;

// Déclaration de type (classe/interface/enum/record)
public class RaiderMeta extends MonsterMeta {
    // Début d'une méthode/d'un bloc
    protected RaiderMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isCelebrating() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Raider.IS_CELEBRATING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setCelebrating(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Raider.IS_CELEBRATING, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
