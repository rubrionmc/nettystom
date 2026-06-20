// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Déclaration de type (classe/interface/enum/record)
public record ClientConfigurationAckPacket() implements ClientPacket.Play {
    // Appelle une méthode
    public static final NetworkBuffer.Type<ClientConfigurationAckPacket> SERIALIZER = NetworkBufferTemplate.template(new ClientConfigurationAckPacket());
// Fin d'un bloc/d'une expression
}
