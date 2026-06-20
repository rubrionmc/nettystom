// Package declaration for this file
package net.minestom.server.inventory;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientCloseWindowPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.CloseWindowPacket;
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
public class InventoryCloseStateTest {


    // Annotation for the following element
    @Test
    // Start of a method/block
    public void doNotReceiveClosePacketFromServerWhenSendingClientCloseWindowPacket(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Calls a method
        assertEquals(instance, player.getInstance());

        // Calls a method
        var packetTracker = connection.trackIncoming(CloseWindowPacket.class);
        // Calls a method
        var inventory = new Inventory(InventoryType.CHEST_2_ROW, Component.text("Test"));
        // Calls a method
        player.openInventory(inventory);
        // Code statement
        player.closeInventory(); // Closes the inventory server-side, should send a CloseWindowPacket
        // Calls a method
        player.openInventory(inventory);
        // Send the close window packet
        // Calls a method
        player.addPacketToQueue(new ClientCloseWindowPacket(inventory.getWindowId()));
        // Calls a method
        player.interpretPacketQueue();
        // Calls a method
        packetTracker.assertSingle(closeWindowPacket -> assertEquals(inventory.getWindowId(), closeWindowPacket.windowId()));
        // Code statement
        packetTracker.assertCount(1); // Assert we only get 1 close window packet from the closeInventory(); call
    // End of a block/expression
    }
// End of a block/expression
}
