// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.login;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Déclaration de type (classe/interface/enum/record)
public record ClientLoginAcknowledgedPacket() implements ClientPacket.Login {
    // Appelle une méthode
    public static final NetworkBuffer.Type<ClientLoginAcknowledgedPacket> SERIALIZER = NetworkBufferTemplate.template(new ClientLoginAcknowledgedPacket());
// Fin d'un bloc/d'une expression
}
