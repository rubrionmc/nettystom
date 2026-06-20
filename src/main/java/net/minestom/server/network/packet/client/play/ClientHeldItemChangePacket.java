// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.SHORT;

// Déclaration de type (classe/interface/enum/record)
public record ClientHeldItemChangePacket(short slot) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientHeldItemChangePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            SHORT, ClientHeldItemChangePacket::slot,
            // Instruction de code
            ClientHeldItemChangePacket::new);
// Fin d'un bloc/d'une expression
}
