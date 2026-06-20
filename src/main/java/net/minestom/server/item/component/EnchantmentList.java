// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.item.enchant.Enchantment;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.registry.RegistryKey;

// Import of a required class
import java.util.HashMap;
// Import of a required class
import java.util.Map;

// Type declaration (class/interface/enum/record)
public record EnchantmentList(Map<RegistryKey<Enchantment>, Integer> enchantments) {
    // Calls a method
    public static final EnchantmentList EMPTY = new EnchantmentList(Map.of());

    // Assigns a value
    public static final NetworkBuffer.Type<EnchantmentList> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            Enchantment.NETWORK_TYPE.mapValue(NetworkBuffer.VAR_INT, Short.MAX_VALUE), EnchantmentList::enchantments,
            // Code statement
            EnchantmentList::new);
    // Assigns a value
    public static final Codec<EnchantmentList> CODEC = Enchantment.CODEC.mapValue(Codec.INT, Short.MAX_VALUE)
            // Calls a method
            .transform(EnchantmentList::new, EnchantmentList::enchantments);

    // Start of a method/block
    public EnchantmentList {
        // Calls a method
        enchantments = Map.copyOf(enchantments);
    // End of a block/expression
    }

    // Start of a method/block
    public EnchantmentList(RegistryKey<Enchantment> enchantment, int level) {
        // Calls a method
        this(Map.of(enchantment, level));
    // End of a block/expression
    }

    // Start of a method/block
    public boolean has(RegistryKey<Enchantment> enchantment) {
        // Returns a value to the caller
        return enchantments.containsKey(enchantment);
    // End of a block/expression
    }

    // Start of a method/block
    public int level(RegistryKey<Enchantment> enchantment) {
        // Returns a value to the caller
        return enchantments.getOrDefault(enchantment, 0);
    // End of a block/expression
    }

    // Start of a method/block
    public EnchantmentList with(RegistryKey<Enchantment> enchantment, int level) {
        // Calls a method
        Map<RegistryKey<Enchantment>, Integer> newEnchantments = new HashMap<>(enchantments);
        // Calls a method
        newEnchantments.put(enchantment, level);
        // Returns a value to the caller
        return new EnchantmentList(newEnchantments);
    // End of a block/expression
    }

    // Start of a method/block
    public EnchantmentList remove(RegistryKey<Enchantment> enchantment) {
        // Calls a method
        Map<RegistryKey<Enchantment>, Integer> newEnchantments = new HashMap<>(enchantments);
        // Calls a method
        newEnchantments.remove(enchantment);
        // Returns a value to the caller
        return new EnchantmentList(newEnchantments);
    // End of a block/expression
    }
// End of a block/expression
}
