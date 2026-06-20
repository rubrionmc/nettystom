// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.common;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
public record CustomReportDetailsPacket(
        // Instruction de code
        Map<String, String> details
// Début d'une méthode/d'un bloc
) implements ServerPacket.Configuration, ServerPacket.Play {
    // Affecte une valeur
    private static final int MAX_DETAILS = 32;

    // Affecte une valeur
    public static final NetworkBuffer.Type<CustomReportDetailsPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.STRING.mapValue(NetworkBuffer.STRING, MAX_DETAILS), CustomReportDetailsPacket::details,
            // Instruction de code
            CustomReportDetailsPacket::new
    // Fin d'un bloc/d'une expression
    );

    // Début d'une méthode/d'un bloc
    public CustomReportDetailsPacket {
        // Appelle une méthode
        details = Map.copyOf(details);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
