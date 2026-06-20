// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.STRING;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record ClientEditBookPacket(int slot, List<String> pages,
                                   // Annotation for the following element
                                   @Nullable String title) implements ClientPacket.Play {
    // Assigns a value
    public static final int MAX_PAGES = 100;
    // Assigns a value
    public static final int MAX_TITLE_LENGTH = 32;
    // Assigns a value
    public static final int MAX_PAGE_LENGTH = 1024;

    // Assigns a value
    public static final NetworkBuffer.Type<ClientEditBookPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, ClientEditBookPacket::slot,
            // Code statement
            STRING.list(MAX_PAGES), ClientEditBookPacket::pages,
            // Code statement
            STRING.optional(), ClientEditBookPacket::title,
            // Code statement
            ClientEditBookPacket::new);

    // Start of a method/block
    public ClientEditBookPacket {
        // Loop: repeats a block
        for (var page : pages) {
            // Branch: checks a condition
            if (page.length() > MAX_PAGE_LENGTH) {
                // Throws an exception
                throw new IllegalArgumentException("Page length cannot be greater than " + MAX_PAGE_LENGTH);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Branch: checks a condition
        if (title != null && title.length() > MAX_TITLE_LENGTH) {
            // Throws an exception
            throw new IllegalArgumentException("Title length cannot be greater than " + MAX_TITLE_LENGTH);
        // End of a block/expression
        }
        // Calls a method
        pages = List.copyOf(pages);
    // End of a block/expression
    }
// End of a block/expression
}
