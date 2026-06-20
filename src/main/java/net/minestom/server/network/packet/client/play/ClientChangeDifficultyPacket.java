// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.world.Difficulty;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.Enum;

// Type declaration (class/interface/enum/record)
public record ClientChangeDifficultyPacket(Difficulty difficulty, boolean locked) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientChangeDifficultyPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            Enum(Difficulty.class), ClientChangeDifficultyPacket::difficulty,
            // Code statement
            BOOLEAN, ClientChangeDifficultyPacket::locked,
            // Code statement
            ClientChangeDifficultyPacket::new);
// End of a block/expression
}
