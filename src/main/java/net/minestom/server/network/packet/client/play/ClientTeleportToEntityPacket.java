// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.UUID;

// Déclaration de type (classe/interface/enum/record)
public record ClientTeleportToEntityPacket(UUID target) implements ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientTeleportToEntityPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.UUID, ClientTeleportToEntityPacket::target,
            // Instruction de code
            ClientTeleportToEntityPacket::new
    // Fin d'un bloc/d'une expression
    );

    // Début d'une méthode/d'un bloc
    public ClientTeleportToEntityPacket {
        // Appelle une méthode
        Objects.requireNonNull(target, "target");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
