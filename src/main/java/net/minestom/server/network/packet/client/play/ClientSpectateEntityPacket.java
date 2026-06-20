// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

/**
 * The ClientSpectateEntityPacket is sent when the client clicks on an entity to spectate it.
 */
// Déclaration de type (classe/interface/enum/record)
public record ClientSpectateEntityPacket(int targetId) implements ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientSpectateEntityPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.VAR_INT, ClientSpectateEntityPacket::targetId,
            // Instruction de code
            ClientSpectateEntityPacket::new);
// Fin d'un bloc/d'une expression
}
