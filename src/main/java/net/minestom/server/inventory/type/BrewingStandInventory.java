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
public class BrewingStandInventory extends Inventory {

    // Instruction de code
    private short brewTime;
    // Instruction de code
    private short fuelTime;

    // Début d'une méthode/d'un bloc
    public BrewingStandInventory(Component title) {
        // Accès à l'objet courant/parent
        super(InventoryType.BREWING_STAND, title);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BrewingStandInventory(String title) {
        // Accès à l'objet courant/parent
        super(InventoryType.BREWING_STAND, title);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the brewing stand brew time.
     *
     * @return the brew time in tick
     */
    // Début d'une méthode/d'un bloc
    public short getBrewTime() {
        // Renvoie une valeur à l'appelant
        return brewTime;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the brew time.
     *
     * @param brewTime the new brew time in tick
     */
    // Début d'une méthode/d'un bloc
    public void setBrewTime(short brewTime) {
        // Accès à l'objet courant/parent
        this.brewTime = brewTime;
        // Appelle une méthode
        sendProperty(InventoryProperty.BREWING_STAND_BREW_TIME, brewTime);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the brewing stand fuel time.
     *
     * @return the fuel time in tick
     */
    // Début d'une méthode/d'un bloc
    public short getFuelTime() {
        // Renvoie une valeur à l'appelant
        return fuelTime;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the fuel time.
     *
     * @param fuelTime the new fuel time in tick
     */
    // Début d'une méthode/d'un bloc
    public void setFuelTime(short fuelTime) {
        // Accès à l'objet courant/parent
        this.fuelTime = fuelTime;
        // Appelle une méthode
        sendProperty(InventoryProperty.BREWING_STAND_FUEL_TIME, fuelTime);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
