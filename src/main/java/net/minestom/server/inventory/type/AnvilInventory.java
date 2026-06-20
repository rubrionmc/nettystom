// Déclaration du paquet de ce fichier
package net.minestom.server.inventory.type;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.inventory.Inventory;
// Import d'une classe nécessaire
import net.minestom.server.inventory.InventoryProperty;
// Import d'une classe nécessaire
import net.minestom.server.inventory.InventoryType;

// Déclaration de type (classe/interface/enum/record)
public class AnvilInventory extends Inventory {

    // Instruction de code
    private short repairCost;

    // Début d'une méthode/d'un bloc
    public AnvilInventory(Component title) {
        // Accès à l'objet courant/parent
        super(InventoryType.ANVIL, title);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public AnvilInventory(String title) {
        // Accès à l'objet courant/parent
        super(InventoryType.ANVIL, title);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the anvil repair cost.
     *
     * @return the repair cost
     */
    // Début d'une méthode/d'un bloc
    public short getRepairCost() {
        // Renvoie une valeur à l'appelant
        return repairCost;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the anvil repair cost.
     *
     * @param cost the new anvil repair cost
     */
    // Début d'une méthode/d'un bloc
    public void setRepairCost(short cost) {
        // Accès à l'objet courant/parent
        this.repairCost = cost;
        // Appelle une méthode
        sendProperty(InventoryProperty.ANVIL_REPAIR_COST, cost);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
