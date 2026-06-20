// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;

// Import d'une classe nécessaire
import java.util.HashMap;
// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
public record ItemBlockState(Map<String, String> properties) {
    // Appelle une méthode
    public static final ItemBlockState EMPTY = new ItemBlockState(Map.of());

    // Affecte une valeur
    public static final NetworkBuffer.Type<ItemBlockState> NETWORK_TYPE = NetworkBuffer.STRING.mapValue(NetworkBuffer.STRING)
            // Appelle une méthode
            .transform(ItemBlockState::new, ItemBlockState::properties);
    // Affecte une valeur
    public static final Codec<ItemBlockState> CODEC = Codec.STRING.mapValue(Codec.STRING)
            // Appelle une méthode
            .transform(ItemBlockState::new, ItemBlockState::properties);

    // Début d'une méthode/d'un bloc
    public ItemBlockState {
        // Appelle une méthode
        properties = Map.copyOf(properties);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ItemBlockState(String key, String value) {
        // Appelle une méthode
        this(Map.of(key, value));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ItemBlockState with(String key, String value) {
        // Affecte une valeur
        Map<String, String> newProperties = new HashMap<>(properties);
        // Appelle une méthode
        newProperties.put(key, value);
        // Renvoie une valeur à l'appelant
        return new ItemBlockState(newProperties);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Block apply(Block block) {
        // Boucle : répète un bloc
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            // Embranchement : vérifie une condition
            if (block.getProperty(entry.getKey()) == null)
                // Passe à l'itération suivante de la boucle
                continue; // Ignore properties not present on this block
            // Appelle une méthode
            block = block.withProperty(entry.getKey(), entry.getValue());
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return block;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
