// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientEntityActionPacket;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientInputPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.EntityMetaDataPacket;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class PlayerSprintingMetadataTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void sprintingMetadata(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 40, 0));

        // Calls a method
        player.addPacketToQueue(new ClientInputPacket(true, false, false, false, false, false, true));
        // Code statement
        player.addPacketToQueue(new ClientEntityActionPacket(
                // Code statement
                player.getEntityId(),
                // Code statement
                ClientEntityActionPacket.Action.START_SPRINTING,
                // Code statement
                0
        // Code statement
        ));

        // Calls a method
        var tracker = connection.trackIncoming(EntityMetaDataPacket.class);
        // Calls a method
        player.interpretPacketQueue();

        // Calls a method
        var packets = tracker.collect();
        // Calls a method
        assertEquals(1, packets.size(), "Expected single packet, got multiple");
    // End of a block/expression
    }

// End of a block/expression
}
