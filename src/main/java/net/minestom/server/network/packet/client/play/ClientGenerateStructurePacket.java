// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record ClientGenerateStructurePacket(Point blockPosition,
                                            // Début d'une méthode/d'un bloc
                                            int level, boolean keepJigsaws) implements ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientGenerateStructurePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            BLOCK_POSITION, ClientGenerateStructurePacket::blockPosition,
            // Instruction de code
            VAR_INT, ClientGenerateStructurePacket::level,
            // Instruction de code
            BOOLEAN, ClientGenerateStructurePacket::keepJigsaws,
            // Instruction de code
            ClientGenerateStructurePacket::new);
// Fin d'un bloc/d'une expression
}
