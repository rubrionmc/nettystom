// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record ClientTabCompletePacket(int transactionId, String text) implements ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientTabCompletePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, ClientTabCompletePacket::transactionId,
            // Instruction de code
            STRING, ClientTabCompletePacket::text,
            // Instruction de code
            ClientTabCompletePacket::new);
// Fin d'un bloc/d'une expression
}
