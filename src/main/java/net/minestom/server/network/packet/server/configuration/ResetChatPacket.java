// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.configuration;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Déclaration de type (classe/interface/enum/record)
public record ResetChatPacket() implements ServerPacket.Configuration {
    // Appelle une méthode
    public static final NetworkBuffer.Type<ResetChatPacket> SERIALIZER = NetworkBufferTemplate.template(new ResetChatPacket());
// Fin d'un bloc/d'une expression
}
