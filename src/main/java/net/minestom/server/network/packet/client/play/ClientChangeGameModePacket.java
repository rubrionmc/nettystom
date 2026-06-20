// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.entity.GameMode;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Déclaration de type (classe/interface/enum/record)
public record ClientChangeGameModePacket(GameMode gameMode) implements ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientChangeGameModePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            GameMode.NETWORK_TYPE, ClientChangeGameModePacket::gameMode,
            // Instruction de code
            ClientChangeGameModePacket::new);
// Fin d'un bloc/d'une expression
}
