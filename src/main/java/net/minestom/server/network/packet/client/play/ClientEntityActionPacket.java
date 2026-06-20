// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.Enum;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record ClientEntityActionPacket(int playerId, Action action,
                                       // Début d'une méthode/d'un bloc
                                       int horseJumpBoost) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientEntityActionPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, ClientEntityActionPacket::playerId,
            // Instruction de code
            Enum(Action.class), ClientEntityActionPacket::action,
            // Instruction de code
            VAR_INT, ClientEntityActionPacket::horseJumpBoost,
            // Instruction de code
            ClientEntityActionPacket::new);

    // Déclaration de type (classe/interface/enum/record)
    public enum Action {
        // Instruction de code
        LEAVE_BED,
        // Instruction de code
        START_SPRINTING,
        // Instruction de code
        STOP_SPRINTING,
        // Instruction de code
        START_JUMP_HORSE,
        // Instruction de code
        STOP_JUMP_HORSE,
        // Instruction de code
        OPEN_HORSE_INVENTORY,
        // Instruction de code
        START_FLYING_ELYTRA
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
