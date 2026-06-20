// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.STRING;

// Type declaration (class/interface/enum/record)
public record ResetScorePacket(String owner, @Nullable String objective) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ResetScorePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            STRING, ResetScorePacket::owner,
            // Code statement
            STRING.optional(), ResetScorePacket::objective,
            // Code statement
            ResetScorePacket::new);
// End of a block/expression
}
