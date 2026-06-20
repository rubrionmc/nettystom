// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record ClientPickItemFromEntityPacket(int entityId, boolean includeData) implements ClientPacket {
    // Affecte une valeur
    public static final Type<ClientPickItemFromEntityPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, ClientPickItemFromEntityPacket::entityId,
            // Instruction de code
            BOOLEAN, ClientPickItemFromEntityPacket::includeData,
            // Instruction de code
            ClientPickItemFromEntityPacket::new);
// Fin d'un bloc/d'une expression
}
