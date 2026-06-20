// Déclaration du paquet de ce fichier
package net.minestom.server.inventory.click;

// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;

// Déclaration de type (classe/interface/enum/record)
public final class InventoryClickResult {
    // Instruction de code
    private ItemStack clicked;
    // Instruction de code
    private ItemStack cursor;
    // Instruction de code
    private boolean cancel;

    // Début d'une méthode/d'un bloc
    public InventoryClickResult(ItemStack clicked, ItemStack cursor) {
        // Accès à l'objet courant/parent
        this.clicked = clicked;
        // Accès à l'objet courant/parent
        this.cursor = cursor;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ItemStack getClicked() {
        // Renvoie une valeur à l'appelant
        return clicked;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void setClicked(ItemStack clicked) {
        // Accès à l'objet courant/parent
        this.clicked = clicked;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ItemStack getCursor() {
        // Renvoie une valeur à l'appelant
        return cursor;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void setCursor(ItemStack cursor) {
        // Accès à l'objet courant/parent
        this.cursor = cursor;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isCancel() {
        // Renvoie une valeur à l'appelant
        return cancel;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void setCancel(boolean cancel) {
        // Accès à l'objet courant/parent
        this.cancel = cancel;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    InventoryClickResult cancelled() {
        // Appelle une méthode
        setCancel(true);
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
