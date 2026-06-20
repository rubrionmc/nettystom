// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record ClientPlaceRecipePacket(byte windowId, int recipeDisplayId, boolean makeAll) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientPlaceRecipePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            BYTE, ClientPlaceRecipePacket::windowId,
            // Code statement
            VAR_INT, ClientPlaceRecipePacket::recipeDisplayId,
            // Code statement
            BOOLEAN, ClientPlaceRecipePacket::makeAll,
            // Code statement
            ClientPlaceRecipePacket::new);
// End of a block/expression
}
