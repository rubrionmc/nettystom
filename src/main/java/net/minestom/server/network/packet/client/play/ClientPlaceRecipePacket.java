// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record ClientPlaceRecipePacket(byte windowId, int recipeDisplayId, boolean makeAll) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientPlaceRecipePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            BYTE, ClientPlaceRecipePacket::windowId,
            // Instruction de code
            VAR_INT, ClientPlaceRecipePacket::recipeDisplayId,
            // Instruction de code
            BOOLEAN, ClientPlaceRecipePacket::makeAll,
            // Instruction de code
            ClientPlaceRecipePacket::new);
// Fin d'un bloc/d'une expression
}
