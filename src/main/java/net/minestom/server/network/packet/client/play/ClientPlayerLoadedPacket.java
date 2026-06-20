// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Déclaration de type (classe/interface/enum/record)
public record ClientPlayerLoadedPacket() implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientPlayerLoadedPacket> SERIALIZER = NetworkBufferTemplate
            // Appelle une méthode
            .template(new ClientPlayerLoadedPacket());
// Fin d'un bloc/d'une expression
}
