// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.FLOAT;

// Déclaration de type (classe/interface/enum/record)
public record ChangeGameStatePacket(Reason reason, float value) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ChangeGameStatePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.Enum(Reason.class), ChangeGameStatePacket::reason,
            // Instruction de code
            FLOAT, ChangeGameStatePacket::value,
            // Instruction de code
            ChangeGameStatePacket::new
    // Fin d'un bloc/d'une expression
    );

    // Déclaration de type (classe/interface/enum/record)
    public enum Reason {
        // Instruction de code
        NO_RESPAWN_BLOCK,
        // Instruction de code
        END_RAINING,
        // Instruction de code
        BEGIN_RAINING,
        // Instruction de code
        CHANGE_GAMEMODE,
        // Instruction de code
        WIN_GAME,
        // Instruction de code
        DEMO_EVENT,
        // Instruction de code
        ARROW_HIT_PLAYER,
        // Instruction de code
        RAIN_LEVEL_CHANGE,
        // Instruction de code
        THUNDER_LEVEL_CHANGE,
        // Instruction de code
        PLAY_PUFFERFISH_STING_SOUND,
        // Instruction de code
        PLAYER_ELDER_GUARDIAN_MOB_APPEARANCE,
        // Instruction de code
        ENABLE_RESPAWN_SCREEN,
        // Instruction de code
        LIMITED_CRAFTING,
        // Instruction de code
        LEVEL_CHUNKS_LOAD_START
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
