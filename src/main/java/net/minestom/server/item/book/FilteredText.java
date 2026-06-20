// Déclaration du paquet de ce fichier
package net.minestom.server.item.book;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public record FilteredText<T>(T text, @Nullable T filtered) {

    // Appelle une méthode
    public static NetworkBuffer.Type<FilteredText<String>> STRING_NETWORK_TYPE = createNetworkType(NetworkBuffer.STRING);
    // Appelle une méthode
    public static Codec<FilteredText<String>> STRING_CODEC = createCodec(Codec.STRING);

    // Appelle une méthode
    public static NetworkBuffer.Type<FilteredText<Component>> COMPONENT_NETWORK_TYPE = createNetworkType(NetworkBuffer.COMPONENT);
    // Appelle une méthode
    public static Codec<FilteredText<Component>> COMPONENT_CODEC = createCodec(Codec.COMPONENT);

    // Début d'une méthode/d'un bloc
    private static <T> NetworkBuffer.Type<FilteredText<T>> createNetworkType(NetworkBuffer.Type<T> inner) {
        // Renvoie une valeur à l'appelant
        return NetworkBufferTemplate.template(
                // Instruction de code
                inner, FilteredText::text,
                // Instruction de code
                inner.optional(), FilteredText::filtered,
                // Instruction de code
                FilteredText::new);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static <T> Codec<FilteredText<T>> createCodec(Codec<T> inner) {
        // Renvoie une valeur à l'appelant
        return StructCodec.struct(
                // Instruction de code
                "raw", inner, FilteredText::text,
                // Instruction de code
                "filtered", inner.optional(), FilteredText::filtered,
                // Instruction de code
                FilteredText::new);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
