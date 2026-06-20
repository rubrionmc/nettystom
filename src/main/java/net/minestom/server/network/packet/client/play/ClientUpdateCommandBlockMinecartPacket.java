// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record ClientUpdateCommandBlockMinecartPacket(int entityId, String command,
                                                     // Début d'une méthode/d'un bloc
                                                     boolean trackOutput) implements ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientUpdateCommandBlockMinecartPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, ClientUpdateCommandBlockMinecartPacket::entityId,
            // Instruction de code
            STRING, ClientUpdateCommandBlockMinecartPacket::command,
            // Instruction de code
            BOOLEAN, ClientUpdateCommandBlockMinecartPacket::trackOutput,
            // Instruction de code
            ClientUpdateCommandBlockMinecartPacket::new);
// Fin d'un bloc/d'une expression
}
