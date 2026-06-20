// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.configuration;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Déclaration de type (classe/interface/enum/record)
public record CodeOfConductPacket(
        // Instruction de code
        String codeOfConduct
// Début d'une méthode/d'un bloc
) implements ServerPacket.Configuration {
    // Affecte une valeur
    public static final NetworkBuffer.Type<CodeOfConductPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.STRING, CodeOfConductPacket::codeOfConduct,
            // Instruction de code
            CodeOfConductPacket::new);
// Fin d'un bloc/d'une expression
}
