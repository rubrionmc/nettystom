// Package declaration for this file
package net.minestom.server.network.packet;

// Import of a required class
import net.minestom.server.network.debug.DebugSubscription;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientDebugSubscriptionRequestPacket;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.HashSet;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class ClientDebugSubscriptionRequestPacketTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testUnmodifiable() {
        // Calls a method
        var packet = new ClientDebugSubscriptionRequestPacket(new HashSet<>());
        // Calls a method
        assertThrows(UnsupportedOperationException.class, () -> packet.subscriptions().add(DebugSubscription.POIS));
    // End of a block/expression
    }
// End of a block/expression
}
