// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Déclaration de type (classe/interface/enum/record)
public record LowDiskSpaceWarningPacket() implements ServerPacket.Play {
    // Appelle une méthode
    public static final NetworkBuffer.Type<LowDiskSpaceWarningPacket> SERIALIZER = NetworkBufferTemplate.template(new LowDiskSpaceWarningPacket());
// Fin d'un bloc/d'une expression
}
