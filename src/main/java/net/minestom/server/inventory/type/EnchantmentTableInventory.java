// Déclaration du paquet de ce fichier
package net.minestom.server.inventory.type;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.inventory.Inventory;
// Import d'une classe nécessaire
import net.minestom.server.inventory.InventoryProperty;
// Import d'une classe nécessaire
import net.minestom.server.inventory.InventoryType;
// Import d'une classe nécessaire
import net.minestom.server.item.enchant.Enchantment;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class EnchantmentTableInventory extends Inventory {
    // Appelle une méthode
    private static final DynamicRegistry<Enchantment> ENCHANTMENT_REGISTRY = MinecraftServer.getEnchantmentRegistry();

    // Appelle une méthode
    private final short[] levelRequirements = new short[EnchantmentSlot.values().length];
    // Instruction de code
    private short seed;
    // Appelle une méthode
    private final short[] enchantmentShown = new short[EnchantmentSlot.values().length];
    // Appelle une méthode
    private final short[] enchantmentLevel = new short[EnchantmentSlot.values().length];

    // Début d'une méthode/d'un bloc
    public EnchantmentTableInventory(Component title) {
        // Accès à l'objet courant/parent
        super(InventoryType.ENCHANTMENT, title);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public EnchantmentTableInventory(String title) {
        // Accès à l'objet courant/parent
        super(InventoryType.ENCHANTMENT, title);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the level requirement in a slot.
     *
     * @param enchantmentSlot the slot to check the level requirement
     * @return the level requirement of the slot
     */
    // Début d'une méthode/d'un bloc
    public short getLevelRequirement(EnchantmentSlot enchantmentSlot) {
        // Renvoie une valeur à l'appelant
        return levelRequirements[enchantmentSlot.ordinal()];
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the level requirement of a slot.
     *
     * @param enchantmentSlot the slot
     * @param level           the level
     */
    // Début d'une méthode/d'un bloc
    public void setLevelRequirement(EnchantmentSlot enchantmentSlot, short level) {
        // Embranchement multiple (switch/case)
        switch (enchantmentSlot) {
            // Embranchement multiple (switch/case)
            case TOP -> sendProperty(InventoryProperty.ENCHANTMENT_TABLE_LEVEL_REQUIREMENT_TOP, level);
            // Embranchement multiple (switch/case)
            case MIDDLE -> sendProperty(InventoryProperty.ENCHANTMENT_TABLE_LEVEL_REQUIREMENT_MIDDLE, level);
            // Embranchement multiple (switch/case)
            case BOTTOM -> sendProperty(InventoryProperty.ENCHANTMENT_TABLE_LEVEL_REQUIREMENT_BOTTOM, level);
        // Fin d'un bloc/d'une expression
        }
        // Accès à l'objet courant/parent
        this.levelRequirements[enchantmentSlot.ordinal()] = level;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the enchantment seed.
     *
     * @return the enchantment seed
     */
    // Début d'une méthode/d'un bloc
    public short getSeed() {
        // Renvoie une valeur à l'appelant
        return seed;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the enchantment seed.
     *
     * @param seed the enchantment seed
     */
    // Début d'une méthode/d'un bloc
    public void setSeed(short seed) {
        // Accès à l'objet courant/parent
        this.seed = seed;
        // Appelle une méthode
        sendProperty(InventoryProperty.ENCHANTMENT_TABLE_SEED, seed);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the enchantment shown in a slot.
     *
     * @param enchantmentSlot the enchantment slot
     * @return the enchantment shown in the slot, null if it is hidden
     */
    // Début d'une méthode/d'un bloc
    public @Nullable RegistryKey<Enchantment> getEnchantmentShown(EnchantmentSlot enchantmentSlot) {
        // Appelle une méthode
        final int id = enchantmentShown[enchantmentSlot.ordinal()];
        // Embranchement : vérifie une condition
        if (id == -1) return null;
        // Renvoie une valeur à l'appelant
        return ENCHANTMENT_REGISTRY.getKey(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the enchantment shown in a slot.
     * <p>
     * Can be set to null to hide it.
     *
     * @param enchantmentSlot the enchantment slot
     * @param enchantment     the enchantment
     */
    // Début d'une méthode/d'un bloc
    public void setEnchantmentShown(EnchantmentSlot enchantmentSlot, @Nullable RegistryKey<Enchantment> enchantment) {
        // Appelle une méthode
        final short id = enchantment == null ? -1 : (short) ENCHANTMENT_REGISTRY.getId(enchantment);
        // Embranchement multiple (switch/case)
        switch (enchantmentSlot) {
            // Embranchement multiple (switch/case)
            case TOP -> sendProperty(InventoryProperty.ENCHANTMENT_TABLE_ENCH_ID_TOP, id);
            // Embranchement multiple (switch/case)
            case MIDDLE -> sendProperty(InventoryProperty.ENCHANTMENT_TABLE_ENCH_ID_MIDDLE, id);
            // Embranchement multiple (switch/case)
            case BOTTOM -> sendProperty(InventoryProperty.ENCHANTMENT_TABLE_ENCH_ID_BOTTOM, id);
        // Fin d'un bloc/d'une expression
        }
        // Accès à l'objet courant/parent
        this.enchantmentShown[enchantmentSlot.ordinal()] = id;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the enchantment level shown on mouse hover.
     *
     * @param enchantmentSlot the enchantment slot
     * @return the level shown, -1 if no enchant
     */
    // Début d'une méthode/d'un bloc
    public short getEnchantmentLevel(EnchantmentSlot enchantmentSlot) {
        // Renvoie une valeur à l'appelant
        return enchantmentLevel[enchantmentSlot.ordinal()];
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the enchantment level shown on mouse hover.
     * <p>
     * Can be set to -1 if no enchant.
     *
     * @param enchantmentSlot the enchantment slot
     * @param level           the level shown
     */
    // Début d'une méthode/d'un bloc
    public void setEnchantmentLevel(EnchantmentSlot enchantmentSlot, short level) {
        // Embranchement multiple (switch/case)
        switch (enchantmentSlot) {
            // Embranchement multiple (switch/case)
            case TOP -> sendProperty(InventoryProperty.ENCHANTMENT_TABLE_ENCH_LEVEL_TOP, level);
            // Embranchement multiple (switch/case)
            case MIDDLE -> sendProperty(InventoryProperty.ENCHANTMENT_TABLE_ENCH_LEVEL_MIDDLE, level);
            // Embranchement multiple (switch/case)
            case BOTTOM -> sendProperty(InventoryProperty.ENCHANTMENT_TABLE_ENCH_LEVEL_BOTTOM, level);
        // Fin d'un bloc/d'une expression
        }
        // Accès à l'objet courant/parent
        this.enchantmentLevel[enchantmentSlot.ordinal()] = level;
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum EnchantmentSlot {
        // Instruction de code
        TOP, MIDDLE, BOTTOM
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
