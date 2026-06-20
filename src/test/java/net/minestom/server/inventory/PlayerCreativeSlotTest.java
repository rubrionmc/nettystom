// Package declaration for this file
package net.minestom.server.inventory;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.GameMode;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.listener.CreativeInventoryActionListener;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientCreativeInventoryActionPacket;
// Import of a required class
import net.minestom.server.utils.inventory.PlayerInventoryUtils;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class PlayerCreativeSlotTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testCreativeSlots(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Calls a method
        assertEquals(instance, player.getInstance());

        // Calls a method
        player.setGameMode(GameMode.CREATIVE);
        // Calls a method
        player.addPacketToQueue(new ClientCreativeInventoryActionPacket((short) PlayerInventoryUtils.OFFHAND_SLOT, ItemStack.of(Material.STICK)));
        // Calls a method
        player.interpretPacketQueue();
        // Calls a method
        assertEquals(Material.STICK, player.getItemInOffHand().material());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testBoundsCheck(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Calls a method
        player.setGameMode(GameMode.CREATIVE);

        // Calls a method
        assertDoesNotThrow(() -> CreativeInventoryActionListener.listener(new ClientCreativeInventoryActionPacket((short) 76, ItemStack.of(Material.OAK_LOG)), player));
    // End of a block/expression
    }
// End of a block/expression
}
