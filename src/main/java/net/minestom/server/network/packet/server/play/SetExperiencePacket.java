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
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record SetExperiencePacket(float percentage, int level, int totalExperience) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<SetExperiencePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            FLOAT, SetExperiencePacket::percentage,
            // Code statement
            VAR_INT, SetExperiencePacket::level,
            // Code statement
            VAR_INT, SetExperiencePacket::totalExperience,
            // Code statement
            SetExperiencePacket::new);
// End of a block/expression
}
