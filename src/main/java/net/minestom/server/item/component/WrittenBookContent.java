// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.item.book.FilteredText;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record WrittenBookContent(FilteredText<String> title, String author, int generation,
                                 // Start of a method/block
                                 List<FilteredText<Component>> pages, boolean resolved) {
    // Calls a method
    public static final WrittenBookContent EMPTY = new WrittenBookContent(new FilteredText<>("", null), "", 0, List.of(), true);

    // Assigns a value
    public static final NetworkBuffer.Type<WrittenBookContent> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            FilteredText.STRING_NETWORK_TYPE, WrittenBookContent::title,
            // Code statement
            STRING, WrittenBookContent::author,
            // Code statement
            VAR_INT, WrittenBookContent::generation,
            // Code statement
            FilteredText.COMPONENT_NETWORK_TYPE.list(100), WrittenBookContent::pages,
            // Code statement
            BOOLEAN, WrittenBookContent::resolved,
            // Code statement
            WrittenBookContent::new);
    // Assigns a value
    public static final Codec<WrittenBookContent> CODEC = StructCodec.struct(
            // Code statement
            "title", FilteredText.STRING_CODEC, WrittenBookContent::title,
            // Code statement
            "author", Codec.STRING, WrittenBookContent::author,
            // Code statement
            "generation", Codec.INT.optional(0), WrittenBookContent::generation,
            // Code statement
            "pages", FilteredText.COMPONENT_CODEC.list(100).optional(List.of()), WrittenBookContent::pages,
            // Code statement
            "resolved", Codec.BOOLEAN.optional(false), WrittenBookContent::resolved,
            // Code statement
            WrittenBookContent::new);

    // Start of a method/block
    public WrittenBookContent {
        // Calls a method
        pages = List.copyOf(pages);
    // End of a block/expression
    }

    // Start of a method/block
    public WrittenBookContent(String title, String author, List<Component> pages) {
        // Calls a method
        this(title, author, 0, pages, true);
    // End of a block/expression
    }

    // Start of a method/block
    public WrittenBookContent(String title, String author, int generation, List<Component> pages, boolean resolved) {
        // Calls a method
        this(new FilteredText<>(title, null), author, generation, pages.stream().map(page -> new FilteredText<>(page, null)).toList(), resolved);
    // End of a block/expression
    }
// End of a block/expression
}
