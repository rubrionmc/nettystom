// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.item;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.EntityMeta;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;

// Déclaration de type (classe/interface/enum/record)
public class EyeOfEnderMeta extends EntityMeta {
    // Début d'une méthode/d'un bloc
    public EyeOfEnderMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ItemStack getItem() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.EyeOfEnder.ITEM);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setItem(ItemStack value) {
        // Appelle une méthode
        metadata.set(MetadataDef.EyeOfEnder.ITEM, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
