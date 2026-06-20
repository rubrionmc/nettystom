// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.common;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Déclaration de type (classe/interface/enum/record)
public record ClearDialogPacket() implements ServerPacket.Configuration, ServerPacket.Play {
    // Appelle une méthode
    public static final NetworkBuffer.Type<ClearDialogPacket> SERIALIZER = NetworkBufferTemplate.template(new ClearDialogPacket());
// Fin d'un bloc/d'une expression
}
