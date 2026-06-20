// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.monster.raider;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class WitchMeta extends RaiderMeta {
    // Début d'une méthode/d'un bloc
    public WitchMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isDrinkingPotion() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Witch.IS_DRINKING_POTION);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setDrinkingPotion(boolean value) {
        // Accès à l'objet courant/parent
        super.metadata.set(MetadataDef.Witch.IS_DRINKING_POTION, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
