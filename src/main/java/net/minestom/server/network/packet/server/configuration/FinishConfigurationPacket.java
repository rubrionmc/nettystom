// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.configuration;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Déclaration de type (classe/interface/enum/record)
public record FinishConfigurationPacket() implements ServerPacket.Configuration {
    // Appelle une méthode
    public static final NetworkBuffer.Type<FinishConfigurationPacket> SERIALIZER = NetworkBufferTemplate.template(new FinishConfigurationPacket());
// Fin d'un bloc/d'une expression
}
