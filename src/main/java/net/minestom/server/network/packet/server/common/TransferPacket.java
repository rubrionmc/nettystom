// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.common;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Déclaration de type (classe/interface/enum/record)
public record TransferPacket(
        // Instruction de code
        String host,
        // Instruction de code
        int port
// Début d'une méthode/d'un bloc
) implements ServerPacket.Configuration, ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<TransferPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.STRING, TransferPacket::host,
            // Instruction de code
            NetworkBuffer.VAR_INT, TransferPacket::port,
            // Instruction de code
            TransferPacket::new);
// Fin d'un bloc/d'une expression
}
