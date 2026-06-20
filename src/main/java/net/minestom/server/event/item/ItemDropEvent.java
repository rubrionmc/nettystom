// Déclaration du paquet de ce fichier
package net.minestom.server.event.item;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.ItemEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;

// Déclaration de type (classe/interface/enum/record)
public class ItemDropEvent implements PlayerInstanceEvent, ItemEvent, CancellableEvent {

    // Instruction de code
    private final Player player;
    // Instruction de code
    private final ItemStack itemStack;

    // Instruction de code
    private boolean cancelled;

    // Début d'une méthode/d'un bloc
    public ItemDropEvent(Player player, ItemStack itemStack) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.itemStack = itemStack;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Player getPlayer() {
        // Renvoie une valeur à l'appelant
        return player;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ItemStack getItemStack() {
        // Renvoie une valeur à l'appelant
        return itemStack;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isCancelled() {
        // Renvoie une valeur à l'appelant
        return cancelled;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setCancelled(boolean cancel) {
        // Accès à l'objet courant/parent
        this.cancelled = cancel;
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
