// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.minecart;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.AbstractVehicleMeta;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public abstract class AbstractMinecartMeta extends AbstractVehicleMeta {
    // Début d'une méthode/d'un bloc
    protected AbstractMinecartMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable Block getCustomBlockState() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.AbstractMinecart.CUSTOM_BLOCK_STATE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setCustomBlockState(@Nullable Block value) {
        // Appelle une méthode
        metadata.set(MetadataDef.AbstractMinecart.CUSTOM_BLOCK_STATE, value);
    // Fin d'un bloc/d'une expression
    }

    // in 16th of a block
    // Début d'une méthode/d'un bloc
    public int getCustomBlockYPosition() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.AbstractMinecart.CUSTOM_BLOCK_Y_POSITION);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setCustomBlockYPosition(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.AbstractMinecart.CUSTOM_BLOCK_Y_POSITION, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
