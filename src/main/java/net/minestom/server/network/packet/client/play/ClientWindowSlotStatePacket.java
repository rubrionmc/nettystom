// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// This is the packet sent when you toggle a slot in a crafter UI
// Déclaration de type (classe/interface/enum/record)
public record ClientWindowSlotStatePacket(int slot, int windowId, boolean newState) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientWindowSlotStatePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, ClientWindowSlotStatePacket::slot,
            // Instruction de code
            VAR_INT, ClientWindowSlotStatePacket::windowId,
            // Instruction de code
            BOOLEAN, ClientWindowSlotStatePacket::newState,
            // Instruction de code
            ClientWindowSlotStatePacket::new);
// Fin d'un bloc/d'une expression
}
