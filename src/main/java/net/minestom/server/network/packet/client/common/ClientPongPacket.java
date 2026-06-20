// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.common;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.INT;

// Déclaration de type (classe/interface/enum/record)
public record ClientPongPacket(int id) implements ClientPacket.Configuration, ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientPongPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            INT, ClientPongPacket::id, ClientPongPacket::new);
// Fin d'un bloc/d'une expression
}
