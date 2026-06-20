// Package declaration for this file
package net.minestom.server.item.component;

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

// Type declaration (class/interface/enum/record)
public record WritableBookContent(List<FilteredText<String>> pages) {
    // Calls a method
    public static final WritableBookContent EMPTY = new WritableBookContent(List.of());

    // Assigns a value
    public static final NetworkBuffer.Type<WritableBookContent> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            FilteredText.STRING_NETWORK_TYPE.list(100), WritableBookContent::pages,
            // Code statement
            WritableBookContent::new);
    // Assigns a value
    public static final Codec<WritableBookContent> CODEC = StructCodec.struct(
            // Code statement
            "pages", FilteredText.STRING_CODEC.list().optional(List.of()), WritableBookContent::pages,
            // Code statement
            WritableBookContent::new);

    // Start of a method/block
    public WritableBookContent {
        // Calls a method
        pages = List.copyOf(pages);
    // End of a block/expression
    }

// End of a block/expression
}
