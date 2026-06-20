// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.monster;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class EndermanMeta extends MonsterMeta {
    // Début d'une méthode/d'un bloc
    public EndermanMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable Block getCarriedBlock() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Enderman.CARRIED_BLOCK);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setCarriedBlock(@Nullable Block value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Enderman.CARRIED_BLOCK, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isScreaming() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Enderman.IS_SCREAMING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setScreaming(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Enderman.IS_SCREAMING, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isStaring() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Enderman.IS_STARING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setStaring(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Enderman.IS_STARING, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
