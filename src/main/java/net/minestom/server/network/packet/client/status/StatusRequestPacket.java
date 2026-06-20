// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.status;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Déclaration de type (classe/interface/enum/record)
public record StatusRequestPacket() implements ClientPacket.Status {
    // Appelle une méthode
    public static final NetworkBuffer.Type<StatusRequestPacket> SERIALIZER = NetworkBufferTemplate.template(new StatusRequestPacket());
// Fin d'un bloc/d'une expression
}
