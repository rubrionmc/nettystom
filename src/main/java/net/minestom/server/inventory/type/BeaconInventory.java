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
// Import d'une classe nécessaire
import net.minestom.server.potion.PotionEffect;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class BeaconInventory extends Inventory {

    // Instruction de code
    private short powerLevel;
    // Instruction de code
    private @Nullable PotionEffect firstPotionEffect;
    // Instruction de code
    private @Nullable PotionEffect secondPotionEffect;

    // Début d'une méthode/d'un bloc
    public BeaconInventory(Component title) {
        // Accès à l'objet courant/parent
        super(InventoryType.BEACON, title);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BeaconInventory(String title) {
        // Accès à l'objet courant/parent
        super(InventoryType.BEACON, title);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the beacon power level.
     *
     * @return the power level
     */
    // Début d'une méthode/d'un bloc
    public short getPowerLevel() {
        // Renvoie une valeur à l'appelant
        return powerLevel;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the beacon power level.
     *
     * @param powerLevel the new beacon power level
     */
    // Début d'une méthode/d'un bloc
    public void setPowerLevel(short powerLevel) {
        // Accès à l'objet courant/parent
        this.powerLevel = powerLevel;
        // Appelle une méthode
        sendProperty(InventoryProperty.BEACON_POWER_LEVEL, powerLevel);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the first potion effect.
     *
     * @return the first potion effect, can be null
     */
    // Début d'une méthode/d'un bloc
    public @Nullable PotionEffect getFirstPotionEffect() {
        // Renvoie une valeur à l'appelant
        return firstPotionEffect;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the first potion effect.
     *
     * @param firstPotionEffect the new first potion effect, can be null
     */
    // Début d'une méthode/d'un bloc
    public void setFirstPotionEffect(@Nullable PotionEffect firstPotionEffect) {
        // Accès à l'objet courant/parent
        this.firstPotionEffect = firstPotionEffect;
        // Appelle une méthode
        sendProperty(InventoryProperty.BEACON_FIRST_POTION, firstPotionEffect == null ? -1 : (short) firstPotionEffect.id());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the second potion effect.
     *
     * @return the second potion effect, can be null
     */
    // Début d'une méthode/d'un bloc
    public @Nullable PotionEffect getSecondPotionEffect() {
        // Renvoie une valeur à l'appelant
        return secondPotionEffect;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the second potion effect.
     *
     * @param secondPotionEffect the new second potion effect, can be null
     */
    // Début d'une méthode/d'un bloc
    public void setSecondPotionEffect(@Nullable PotionEffect secondPotionEffect) {
        // Accès à l'objet courant/parent
        this.secondPotionEffect = secondPotionEffect;
        // Appelle une méthode
        sendProperty(InventoryProperty.BEACON_SECOND_POTION, secondPotionEffect == null ? -1 : (short) secondPotionEffect.id());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
