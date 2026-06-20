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
public class FurnaceInventory extends Inventory {

    // Instruction de code
    private short remainingFuelTick;
    // Instruction de code
    private short maximumFuelBurnTime;
    // Instruction de code
    private short progressArrow;
    // Instruction de code
    private short maximumProgress;

    // Début d'une méthode/d'un bloc
    public FurnaceInventory(Component title) {
        // Accès à l'objet courant/parent
        super(InventoryType.FURNACE, title);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public FurnaceInventory(String title) {
        // Accès à l'objet courant/parent
        super(InventoryType.FURNACE, title);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Represents the amount of tick until the fire icon come empty.
     *
     * @return the amount of tick until the fire icon come empty
     */
    // Début d'une méthode/d'un bloc
    public short getRemainingFuelTick() {
        // Renvoie une valeur à l'appelant
        return remainingFuelTick;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Represents the amount of tick until the fire icon come empty.
     *
     * @param remainingFuelTick the amount of tick until the fire icon is empty
     */
    // Début d'une méthode/d'un bloc
    public void setRemainingFuelTick(short remainingFuelTick) {
        // Accès à l'objet courant/parent
        this.remainingFuelTick = remainingFuelTick;
        // Appelle une méthode
        sendProperty(InventoryProperty.FURNACE_FIRE_ICON, remainingFuelTick);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public short getMaximumFuelBurnTime() {
        // Renvoie une valeur à l'appelant
        return maximumFuelBurnTime;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setMaximumFuelBurnTime(short maximumFuelBurnTime) {
        // Accès à l'objet courant/parent
        this.maximumFuelBurnTime = maximumFuelBurnTime;
        // Appelle une méthode
        sendProperty(InventoryProperty.FURNACE_MAXIMUM_FUEL_BURN_TIME, maximumFuelBurnTime);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public short getProgressArrow() {
        // Renvoie une valeur à l'appelant
        return progressArrow;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setProgressArrow(short progressArrow) {
        // Accès à l'objet courant/parent
        this.progressArrow = progressArrow;
        // Appelle une méthode
        sendProperty(InventoryProperty.FURNACE_PROGRESS_ARROW, progressArrow);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public short getMaximumProgress() {
        // Renvoie une valeur à l'appelant
        return maximumProgress;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setMaximumProgress(short maximumProgress) {
        // Accès à l'objet courant/parent
        this.maximumProgress = maximumProgress;
        // Appelle une méthode
        sendProperty(InventoryProperty.FURNACE_MAXIMUM_PROGRESS, maximumProgress);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
