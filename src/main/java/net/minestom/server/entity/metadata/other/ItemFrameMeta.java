// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.other;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.utils.Rotation;

// Déclaration de type (classe/interface/enum/record)
public class ItemFrameMeta extends HangingMeta {
    // Début d'une méthode/d'un bloc
    public ItemFrameMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ItemStack getItem() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.ItemFrame.ITEM);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setItem(ItemStack value) {
        // Appelle une méthode
        metadata.set(MetadataDef.ItemFrame.ITEM, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Rotation getRotation() {
        // Renvoie une valeur à l'appelant
        return Rotation.values()[metadata.get(MetadataDef.ItemFrame.ROTATION)];
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setRotation(Rotation value) {
        // Appelle une méthode
        metadata.set(MetadataDef.ItemFrame.ROTATION, value.ordinal());
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
