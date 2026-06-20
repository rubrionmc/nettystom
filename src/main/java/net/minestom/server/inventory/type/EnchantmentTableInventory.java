// Package declaration for this file
package net.minestom.server.inventory.type;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.inventory.Inventory;
// Import of a required class
import net.minestom.server.inventory.InventoryProperty;
// Import of a required class
import net.minestom.server.inventory.InventoryType;
// Import of a required class
import net.minestom.server.item.enchant.Enchantment;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class EnchantmentTableInventory extends Inventory {
    // Calls a method
    private static final DynamicRegistry<Enchantment> ENCHANTMENT_REGISTRY = MinecraftServer.getEnchantmentRegistry();

    // Calls a method
    private final short[] levelRequirements = new short[EnchantmentSlot.values().length];
    // Code statement
    private short seed;
    // Calls a method
    private final short[] enchantmentShown = new short[EnchantmentSlot.values().length];
    // Calls a method
    private final short[] enchantmentLevel = new short[EnchantmentSlot.values().length];

    // Start of a method/block
    public EnchantmentTableInventory(Component title) {
        // Access to the current/parent object
        super(InventoryType.ENCHANTMENT, title);
    // End of a block/expression
    }

    // Start of a method/block
    public EnchantmentTableInventory(String title) {
        // Access to the current/parent object
        super(InventoryType.ENCHANTMENT, title);
    // End of a block/expression
    }

    /**
     * Gets the level requirement in a slot.
     *
     * @param enchantmentSlot the slot to check the level requirement
     * @return the level requirement of the slot
     */
    // Start of a method/block
    public short getLevelRequirement(EnchantmentSlot enchantmentSlot) {
        // Returns a value to the caller
        return levelRequirements[enchantmentSlot.ordinal()];
    // End of a block/expression
    }

    /**
     * Sets the level requirement of a slot.
     *
     * @param enchantmentSlot the slot
     * @param level           the level
     */
    // Start of a method/block
    public void setLevelRequirement(EnchantmentSlot enchantmentSlot, short level) {
        // Multiple branching (switch/case)
        switch (enchantmentSlot) {
            // Multiple branching (switch/case)
            case TOP -> sendProperty(InventoryProperty.ENCHANTMENT_TABLE_LEVEL_REQUIREMENT_TOP, level);
            // Multiple branching (switch/case)
            case MIDDLE -> sendProperty(InventoryProperty.ENCHANTMENT_TABLE_LEVEL_REQUIREMENT_MIDDLE, level);
            // Multiple branching (switch/case)
            case BOTTOM -> sendProperty(InventoryProperty.ENCHANTMENT_TABLE_LEVEL_REQUIREMENT_BOTTOM, level);
        // End of a block/expression
        }
        // Access to the current/parent object
        this.levelRequirements[enchantmentSlot.ordinal()] = level;
    // End of a block/expression
    }

    /**
     * Gets the enchantment seed.
     *
     * @return the enchantment seed
     */
    // Start of a method/block
    public short getSeed() {
        // Returns a value to the caller
        return seed;
    // End of a block/expression
    }

    /**
     * Sets the enchantment seed.
     *
     * @param seed the enchantment seed
     */
    // Start of a method/block
    public void setSeed(short seed) {
        // Access to the current/parent object
        this.seed = seed;
        // Calls a method
        sendProperty(InventoryProperty.ENCHANTMENT_TABLE_SEED, seed);
    // End of a block/expression
    }

    /**
     * Gets the enchantment shown in a slot.
     *
     * @param enchantmentSlot the enchantment slot
     * @return the enchantment shown in the slot, null if it is hidden
     */
    // Start of a method/block
    public @Nullable RegistryKey<Enchantment> getEnchantmentShown(EnchantmentSlot enchantmentSlot) {
        // Calls a method
        final int id = enchantmentShown[enchantmentSlot.ordinal()];
        // Branch: checks a condition
        if (id == -1) return null;
        // Returns a value to the caller
        return ENCHANTMENT_REGISTRY.getKey(id);
    // End of a block/expression
    }

    /**
     * Sets the enchantment shown in a slot.
     * <p>
     * Can be set to null to hide it.
     *
     * @param enchantmentSlot the enchantment slot
     * @param enchantment     the enchantment
     */
    // Start of a method/block
    public void setEnchantmentShown(EnchantmentSlot enchantmentSlot, @Nullable RegistryKey<Enchantment> enchantment) {
        // Calls a method
        final short id = enchantment == null ? -1 : (short) ENCHANTMENT_REGISTRY.getId(enchantment);
        // Multiple branching (switch/case)
        switch (enchantmentSlot) {
            // Multiple branching (switch/case)
            case TOP -> sendProperty(InventoryProperty.ENCHANTMENT_TABLE_ENCH_ID_TOP, id);
            // Multiple branching (switch/case)
            case MIDDLE -> sendProperty(InventoryProperty.ENCHANTMENT_TABLE_ENCH_ID_MIDDLE, id);
            // Multiple branching (switch/case)
            case BOTTOM -> sendProperty(InventoryProperty.ENCHANTMENT_TABLE_ENCH_ID_BOTTOM, id);
        // End of a block/expression
        }
        // Access to the current/parent object
        this.enchantmentShown[enchantmentSlot.ordinal()] = id;
    // End of a block/expression
    }

    /**
     * Gets the enchantment level shown on mouse hover.
     *
     * @param enchantmentSlot the enchantment slot
     * @return the level shown, -1 if no enchant
     */
    // Start of a method/block
    public short getEnchantmentLevel(EnchantmentSlot enchantmentSlot) {
        // Returns a value to the caller
        return enchantmentLevel[enchantmentSlot.ordinal()];
    // End of a block/expression
    }

    /**
     * Sets the enchantment level shown on mouse hover.
     * <p>
     * Can be set to -1 if no enchant.
     *
     * @param enchantmentSlot the enchantment slot
     * @param level           the level shown
     */
    // Start of a method/block
    public void setEnchantmentLevel(EnchantmentSlot enchantmentSlot, short level) {
        // Multiple branching (switch/case)
        switch (enchantmentSlot) {
            // Multiple branching (switch/case)
            case TOP -> sendProperty(InventoryProperty.ENCHANTMENT_TABLE_ENCH_LEVEL_TOP, level);
            // Multiple branching (switch/case)
            case MIDDLE -> sendProperty(InventoryProperty.ENCHANTMENT_TABLE_ENCH_LEVEL_MIDDLE, level);
            // Multiple branching (switch/case)
            case BOTTOM -> sendProperty(InventoryProperty.ENCHANTMENT_TABLE_ENCH_LEVEL_BOTTOM, level);
        // End of a block/expression
        }
        // Access to the current/parent object
        this.enchantmentLevel[enchantmentSlot.ordinal()] = level;
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum EnchantmentSlot {
        // Code statement
        TOP, MIDDLE, BOTTOM
    // End of a block/expression
    }

// End of a block/expression
}
