// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.item.book.FilteredText;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public record WritableBookContent(List<FilteredText<String>> pages) {
    // Appelle une méthode
    public static final WritableBookContent EMPTY = new WritableBookContent(List.of());

    // Affecte une valeur
    public static final NetworkBuffer.Type<WritableBookContent> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            FilteredText.STRING_NETWORK_TYPE.list(100), WritableBookContent::pages,
            // Instruction de code
            WritableBookContent::new);
    // Affecte une valeur
    public static final Codec<WritableBookContent> CODEC = StructCodec.struct(
            // Instruction de code
            "pages", FilteredText.STRING_CODEC.list().optional(List.of()), WritableBookContent::pages,
            // Instruction de code
            WritableBookContent::new);

    // Début d'une méthode/d'un bloc
    public WritableBookContent {
        // Appelle une méthode
        pages = List.copyOf(pages);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
