// Déclaration du paquet de ce fichier
package net.minestom.server.event.trait;

// Import d'une classe nécessaire
import net.minestom.server.event.Event;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;

/**
 * Represents any event called about an {@link ItemStack}.
 */
// Déclaration de type (classe/interface/enum/record)
public interface ItemEvent extends Event {
    // Appelle une méthode
    ItemStack getItemStack();
// Fin d'un bloc/d'une expression
}
