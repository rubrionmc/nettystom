// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.FLOAT;

// Type declaration (class/interface/enum/record)
public record ChangeGameStatePacket(Reason reason, float value) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ChangeGameStatePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.Enum(Reason.class), ChangeGameStatePacket::reason,
            // Code statement
            FLOAT, ChangeGameStatePacket::value,
            // Code statement
            ChangeGameStatePacket::new
    // End of a block/expression
    );

    // Type declaration (class/interface/enum/record)
    public enum Reason {
        // Code statement
        NO_RESPAWN_BLOCK,
        // Code statement
        END_RAINING,
        // Code statement
        BEGIN_RAINING,
        // Code statement
        CHANGE_GAMEMODE,
        // Code statement
        WIN_GAME,
        // Code statement
        DEMO_EVENT,
        // Code statement
        ARROW_HIT_PLAYER,
        // Code statement
        RAIN_LEVEL_CHANGE,
        // Code statement
        THUNDER_LEVEL_CHANGE,
        // Code statement
        PLAY_PUFFERFISH_STING_SOUND,
        // Code statement
        PLAYER_ELDER_GUARDIAN_MOB_APPEARANCE,
        // Code statement
        ENABLE_RESPAWN_SCREEN,
        // Code statement
        LIMITED_CRAFTING,
        // Code statement
        LEVEL_CHUNKS_LOAD_START
    // End of a block/expression
    }
// End of a block/expression
}
