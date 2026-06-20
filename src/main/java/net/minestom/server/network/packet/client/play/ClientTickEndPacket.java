// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Déclaration de type (classe/interface/enum/record)
public record ClientTickEndPacket() implements ClientPacket.Play {
    // Instruction de code
    public static final NetworkBuffer.Type<ClientTickEndPacket> SERIALIZER =
            // Appelle une méthode
            NetworkBufferTemplate.template(new ClientTickEndPacket());

// Fin d'un bloc/d'une expression
}
