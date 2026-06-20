// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.world.Difficulty;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.Enum;

// Type declaration (class/interface/enum/record)
public record ServerDifficultyPacket(Difficulty difficulty, boolean locked) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ServerDifficultyPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            Enum(Difficulty.class), ServerDifficultyPacket::difficulty,
            // Code statement
            BOOLEAN, ServerDifficultyPacket::locked,
            // Code statement
            ServerDifficultyPacket::new);
// End of a block/expression
}
