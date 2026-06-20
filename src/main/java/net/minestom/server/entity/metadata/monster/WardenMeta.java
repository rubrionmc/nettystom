// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.monster;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class WardenMeta extends MonsterMeta {
    // Début d'une méthode/d'un bloc
    public WardenMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getAngerLevel() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Warden.ANGER_LEVEL);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setAngerLevel(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Warden.ANGER_LEVEL, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
