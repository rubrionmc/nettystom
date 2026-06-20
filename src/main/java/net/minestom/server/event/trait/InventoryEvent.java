// Déclaration du paquet de ce fichier
package net.minestom.server.event.trait;

// Import d'une classe nécessaire
import net.minestom.server.event.Event;
// Import d'une classe nécessaire
import net.minestom.server.inventory.AbstractInventory;

/**
 * Represents any event inside an {@link AbstractInventory}.
 */
// Déclaration de type (classe/interface/enum/record)
public interface InventoryEvent extends Event {

    /**
     * Gets the inventory that was clicked.
     */
    // Appelle une méthode
    AbstractInventory getInventory();
// Fin d'un bloc/d'une expression
}
