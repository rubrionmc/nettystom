// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.configuration;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Déclaration de type (classe/interface/enum/record)
public record ClientFinishConfigurationPacket() implements ClientPacket {
    // Appelle une méthode
    public static final NetworkBuffer.Type<ClientFinishConfigurationPacket> SERIALIZER = NetworkBufferTemplate.template(new ClientFinishConfigurationPacket());
// Fin d'un bloc/d'une expression
}
