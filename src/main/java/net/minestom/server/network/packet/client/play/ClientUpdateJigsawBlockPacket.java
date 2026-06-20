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
import static net.minestom.server.network.NetworkBuffer.BLOCK_POSITION;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record ClientUpdateJigsawBlockPacket(
        // Instruction de code
        Point location,
        // Instruction de code
        String name,
        // Instruction de code
        String target,
        // Instruction de code
        String pool,
        // Instruction de code
        String finalState,
        // Instruction de code
        String jointType,
        // Instruction de code
        int selectionPriority,
        // Instruction de code
        int placementPriority
// Début d'une méthode/d'un bloc
) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientUpdateJigsawBlockPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            BLOCK_POSITION, ClientUpdateJigsawBlockPacket::location,
            // Instruction de code
            STRING, ClientUpdateJigsawBlockPacket::name,
            // Instruction de code
            STRING, ClientUpdateJigsawBlockPacket::target,
            // Instruction de code
            STRING, ClientUpdateJigsawBlockPacket::pool,
            // Instruction de code
            STRING, ClientUpdateJigsawBlockPacket::finalState,
            // Instruction de code
            STRING, ClientUpdateJigsawBlockPacket::jointType,
            // Instruction de code
            VAR_INT, ClientUpdateJigsawBlockPacket::selectionPriority,
            // Instruction de code
            VAR_INT, ClientUpdateJigsawBlockPacket::placementPriority,
            // Instruction de code
            ClientUpdateJigsawBlockPacket::new);
// Fin d'un bloc/d'une expression
}
