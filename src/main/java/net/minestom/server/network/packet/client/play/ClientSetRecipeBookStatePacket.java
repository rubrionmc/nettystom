// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;

// Type declaration (class/interface/enum/record)
public record ClientSetRecipeBookStatePacket(BookType bookType,
                                             // Start of a method/block
                                             boolean bookOpen, boolean filterActive) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientSetRecipeBookStatePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.Enum(BookType.class), ClientSetRecipeBookStatePacket::bookType,
            // Code statement
            BOOLEAN, ClientSetRecipeBookStatePacket::bookOpen,
            // Code statement
            BOOLEAN, ClientSetRecipeBookStatePacket::filterActive,
            // Code statement
            ClientSetRecipeBookStatePacket::new);

    // Type declaration (class/interface/enum/record)
    public enum BookType {
        // Code statement
        CRAFTING, FURNACE, BLAST_FURNACE, SMOKER
    // End of a block/expression
    }
// End of a block/expression
}
