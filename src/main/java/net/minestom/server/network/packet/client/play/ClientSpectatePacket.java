// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import d'une classe nécessaire
import java.util.UUID;

/**
 * The ClientSpectatePacket is sent when the client interacts with their hot-bar to switch between entities.
 * Contrary to its name, it is actually used to teleport the player to the entity they are switching to,
 * rather than spectating them.
 */
// Déclaration de type (classe/interface/enum/record)
public record ClientSpectatePacket(UUID target) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientSpectatePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.UUID, ClientSpectatePacket::target,
            // Instruction de code
            ClientSpectatePacket::new);
// Fin d'un bloc/d'une expression
}
