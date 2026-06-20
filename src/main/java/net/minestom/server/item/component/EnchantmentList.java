// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.item.enchant.Enchantment;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;

// Import d'une classe nécessaire
import java.util.HashMap;
// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
public record EnchantmentList(Map<RegistryKey<Enchantment>, Integer> enchantments) {
    // Appelle une méthode
    public static final EnchantmentList EMPTY = new EnchantmentList(Map.of());

    // Affecte une valeur
    public static final NetworkBuffer.Type<EnchantmentList> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            Enchantment.NETWORK_TYPE.mapValue(NetworkBuffer.VAR_INT, Short.MAX_VALUE), EnchantmentList::enchantments,
            // Instruction de code
            EnchantmentList::new);
    // Affecte une valeur
    public static final Codec<EnchantmentList> CODEC = Enchantment.CODEC.mapValue(Codec.INT, Short.MAX_VALUE)
            // Appelle une méthode
            .transform(EnchantmentList::new, EnchantmentList::enchantments);

    // Début d'une méthode/d'un bloc
    public EnchantmentList {
        // Appelle une méthode
        enchantments = Map.copyOf(enchantments);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public EnchantmentList(RegistryKey<Enchantment> enchantment, int level) {
        // Appelle une méthode
        this(Map.of(enchantment, level));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean has(RegistryKey<Enchantment> enchantment) {
        // Renvoie une valeur à l'appelant
        return enchantments.containsKey(enchantment);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int level(RegistryKey<Enchantment> enchantment) {
        // Renvoie une valeur à l'appelant
        return enchantments.getOrDefault(enchantment, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public EnchantmentList with(RegistryKey<Enchantment> enchantment, int level) {
        // Appelle une méthode
        Map<RegistryKey<Enchantment>, Integer> newEnchantments = new HashMap<>(enchantments);
        // Appelle une méthode
        newEnchantments.put(enchantment, level);
        // Renvoie une valeur à l'appelant
        return new EnchantmentList(newEnchantments);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public EnchantmentList remove(RegistryKey<Enchantment> enchantment) {
        // Appelle une méthode
        Map<RegistryKey<Enchantment>, Integer> newEnchantments = new HashMap<>(enchantments);
        // Appelle une méthode
        newEnchantments.remove(enchantment);
        // Renvoie une valeur à l'appelant
        return new EnchantmentList(newEnchantments);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
