// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
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

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record WrittenBookContent(FilteredText<String> title, String author, int generation,
                                 // Début d'une méthode/d'un bloc
                                 List<FilteredText<Component>> pages, boolean resolved) {
    // Appelle une méthode
    public static final WrittenBookContent EMPTY = new WrittenBookContent(new FilteredText<>("", null), "", 0, List.of(), true);

    // Affecte une valeur
    public static final NetworkBuffer.Type<WrittenBookContent> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            FilteredText.STRING_NETWORK_TYPE, WrittenBookContent::title,
            // Instruction de code
            STRING, WrittenBookContent::author,
            // Instruction de code
            VAR_INT, WrittenBookContent::generation,
            // Instruction de code
            FilteredText.COMPONENT_NETWORK_TYPE.list(100), WrittenBookContent::pages,
            // Instruction de code
            BOOLEAN, WrittenBookContent::resolved,
            // Instruction de code
            WrittenBookContent::new);
    // Affecte une valeur
    public static final Codec<WrittenBookContent> CODEC = StructCodec.struct(
            // Instruction de code
            "title", FilteredText.STRING_CODEC, WrittenBookContent::title,
            // Instruction de code
            "author", Codec.STRING, WrittenBookContent::author,
            // Instruction de code
            "generation", Codec.INT.optional(0), WrittenBookContent::generation,
            // Instruction de code
            "pages", FilteredText.COMPONENT_CODEC.list(100).optional(List.of()), WrittenBookContent::pages,
            // Instruction de code
            "resolved", Codec.BOOLEAN.optional(false), WrittenBookContent::resolved,
            // Instruction de code
            WrittenBookContent::new);

    // Début d'une méthode/d'un bloc
    public WrittenBookContent {
        // Appelle une méthode
        pages = List.copyOf(pages);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public WrittenBookContent(String title, String author, List<Component> pages) {
        // Appelle une méthode
        this(title, author, 0, pages, true);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public WrittenBookContent(String title, String author, int generation, List<Component> pages, boolean resolved) {
        // Appelle une méthode
        this(new FilteredText<>(title, null), author, generation, pages.stream().map(page -> new FilteredText<>(page, null)).toList(), resolved);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
