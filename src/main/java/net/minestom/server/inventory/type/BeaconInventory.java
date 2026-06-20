// Package declaration for this file
package net.minestom.server.inventory.type;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.inventory.Inventory;
// Import of a required class
import net.minestom.server.inventory.InventoryProperty;
// Import of a required class
import net.minestom.server.inventory.InventoryType;
// Import of a required class
import net.minestom.server.potion.PotionEffect;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class BeaconInventory extends Inventory {

    // Code statement
    private short powerLevel;
    // Code statement
    private @Nullable PotionEffect firstPotionEffect;
    // Code statement
    private @Nullable PotionEffect secondPotionEffect;

    // Start of a method/block
    public BeaconInventory(Component title) {
        // Access to the current/parent object
        super(InventoryType.BEACON, title);
    // End of a block/expression
    }

    // Start of a method/block
    public BeaconInventory(String title) {
        // Access to the current/parent object
        super(InventoryType.BEACON, title);
    // End of a block/expression
    }

    /**
     * Gets the beacon power level.
     *
     * @return the power level
     */
    // Start of a method/block
    public short getPowerLevel() {
        // Returns a value to the caller
        return powerLevel;
    // End of a block/expression
    }

    /**
     * Changes the beacon power level.
     *
     * @param powerLevel the new beacon power level
     */
    // Start of a method/block
    public void setPowerLevel(short powerLevel) {
        // Access to the current/parent object
        this.powerLevel = powerLevel;
        // Calls a method
        sendProperty(InventoryProperty.BEACON_POWER_LEVEL, powerLevel);
    // End of a block/expression
    }

    /**
     * Gets the first potion effect.
     *
     * @return the first potion effect, can be null
     */
    // Start of a method/block
    public @Nullable PotionEffect getFirstPotionEffect() {
        // Returns a value to the caller
        return firstPotionEffect;
    // End of a block/expression
    }

    /**
     * Changes the first potion effect.
     *
     * @param firstPotionEffect the new first potion effect, can be null
     */
    // Start of a method/block
    public void setFirstPotionEffect(@Nullable PotionEffect firstPotionEffect) {
        // Access to the current/parent object
        this.firstPotionEffect = firstPotionEffect;
        // Calls a method
        sendProperty(InventoryProperty.BEACON_FIRST_POTION, firstPotionEffect == null ? -1 : (short) firstPotionEffect.id());
    // End of a block/expression
    }

    /**
     * Gets the second potion effect.
     *
     * @return the second potion effect, can be null
     */
    // Start of a method/block
    public @Nullable PotionEffect getSecondPotionEffect() {
        // Returns a value to the caller
        return secondPotionEffect;
    // End of a block/expression
    }

    /**
     * Changes the second potion effect.
     *
     * @param secondPotionEffect the new second potion effect, can be null
     */
    // Start of a method/block
    public void setSecondPotionEffect(@Nullable PotionEffect secondPotionEffect) {
        // Access to the current/parent object
        this.secondPotionEffect = secondPotionEffect;
        // Calls a method
        sendProperty(InventoryProperty.BEACON_SECOND_POTION, secondPotionEffect == null ? -1 : (short) secondPotionEffect.id());
    // End of a block/expression
    }
// End of a block/expression
}
