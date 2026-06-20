// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.monster;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class SpiderMeta extends MonsterMeta {
    // Début d'une méthode/d'un bloc
    public SpiderMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isClimbing() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Spider.IS_CLIMBING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setClimbing(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Spider.IS_CLIMBING, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
