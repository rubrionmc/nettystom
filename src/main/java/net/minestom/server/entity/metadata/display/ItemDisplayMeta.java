// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.display;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;

// Déclaration de type (classe/interface/enum/record)
public class ItemDisplayMeta extends AbstractDisplayMeta {
    // Début d'une méthode/d'un bloc
    public ItemDisplayMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ItemStack getItemStack() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.ItemDisplay.DISPLAYED_ITEM);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setItemStack(ItemStack value) {
        // Appelle une méthode
        metadata.set(MetadataDef.ItemDisplay.DISPLAYED_ITEM, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public DisplayContext getDisplayContext() {
        // Renvoie une valeur à l'appelant
        return DisplayContext.VALUES[metadata.get(MetadataDef.ItemDisplay.DISPLAY_TYPE)];
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setDisplayContext(DisplayContext value) {
        // Appelle une méthode
        metadata.set(MetadataDef.ItemDisplay.DISPLAY_TYPE, (byte) value.ordinal());
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum DisplayContext {
        // Instruction de code
        NONE,
        // Instruction de code
        THIRDPERSON_LEFT_HAND,
        // Instruction de code
        THIRDPERSON_RIGHT_HAND,
        // Instruction de code
        FIRSTPERSON_LEFT_HAND,
        // Instruction de code
        FIRSTPERSON_RIGHT_HAND,
        // Instruction de code
        HEAD,
        // Instruction de code
        GUI,
        // Instruction de code
        GROUND,
        // Instruction de code
        FIXED,
        // Instruction de code
        ON_SHELF;

        // Appelle une méthode
        private final static DisplayContext[] VALUES = values();

    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
