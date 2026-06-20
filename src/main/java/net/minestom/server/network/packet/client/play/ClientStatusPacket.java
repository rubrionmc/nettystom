// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Déclaration de type (classe/interface/enum/record)
public record ClientStatusPacket(Action action) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientStatusPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.Enum(Action.class), ClientStatusPacket::action,
            // Instruction de code
            ClientStatusPacket::new);

    // Déclaration de type (classe/interface/enum/record)
    public enum Action {
        // Instruction de code
        PERFORM_RESPAWN,
        // Instruction de code
        REQUEST_STATS
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
