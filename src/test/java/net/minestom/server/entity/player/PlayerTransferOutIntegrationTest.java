// Package declaration for this file
package net.minestom.server.entity.player;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.event.player.OutgoingTransferEvent;
// Import of a required class
import net.minestom.server.network.packet.server.common.TransferPacket;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Assertions;
// Import of a required class
import org.junit.jupiter.api.Test;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class PlayerTransferOutIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testPlayerTransferOut(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, Pos.ZERO);
        // Calls a method
        var tracker = connection.trackIncoming(TransferPacket.class);

        // Calls a method
        player.getPlayerConnection().transfer("example.com", 25565);

        // Start of a method/block
        tracker.assertSingle(packet -> {
            // Calls a method
            Assertions.assertEquals("example.com", packet.host());
            // Calls a method
            Assertions.assertEquals(25565, packet.port());
        // End of a block/expression
        });
    // End of a block/expression
    }


    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testPlayerTransferOutEvent(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, Pos.ZERO);

        // Start of a method/block
        env.listen(OutgoingTransferEvent.class).followup(event -> {
            // Calls a method
            Assertions.assertEquals(player, event.getPlayer());
            // Calls a method
            Assertions.assertEquals("example.com", event.getHost());
            // Calls a method
            Assertions.assertEquals(25565, event.getPort());
        // End of a block/expression
        });

        // Calls a method
        player.getPlayerConnection().transfer("example.com", 25565);
    // End of a block/expression
    }


    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testPlayerTransferOutEventCancelled(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, Pos.ZERO);

        // Calls a method
        env.process().eventHandler().addListener(OutgoingTransferEvent.class, event -> event.setCancelled(true));

        // Calls a method
        player.getPlayerConnection().transfer("example.com", 25565);
        // Calls a method
        connection.trackIncoming(TransferPacket.class).assertEmpty();
    // End of a block/expression
    }
// End of a block/expression
}
