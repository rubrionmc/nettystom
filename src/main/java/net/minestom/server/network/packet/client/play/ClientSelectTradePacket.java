// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record ClientSelectTradePacket(int selectedSlot) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientSelectTradePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, ClientSelectTradePacket::selectedSlot,
            // Instruction de code
            ClientSelectTradePacket::new);
// Fin d'un bloc/d'une expression
}
