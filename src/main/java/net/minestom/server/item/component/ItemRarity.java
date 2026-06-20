// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;

// Import d'une classe nécessaire
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.stream.Collectors;

// Déclaration de type (classe/interface/enum/record)
public enum ItemRarity {
    // Instruction de code
    COMMON,
    // Instruction de code
    UNCOMMON,
    // Instruction de code
    RARE,
    // Instruction de code
    EPIC;

    // Affecte une valeur
    private static final Map<String, ItemRarity> BY_ID = Arrays.stream(values())
            // Appelle une méthode
            .collect(Collectors.toMap(v -> v.name().toLowerCase(), Function.identity()));

    // Appelle une méthode
    public static final NetworkBuffer.Type<ItemRarity> NETWORK_TYPE = NetworkBuffer.Enum(ItemRarity.class);
    // Appelle une méthode
    public static final Codec<ItemRarity> CODEC = Codec.STRING.transform(BY_ID::get, v -> v.name().toLowerCase());
// Fin d'un bloc/d'une expression
}
