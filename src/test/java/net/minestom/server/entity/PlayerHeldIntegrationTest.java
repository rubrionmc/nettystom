// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.event.player.PlayerChangeHeldSlotEvent;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientHeldItemChangePacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.HeldItemChangePacket;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class PlayerHeldIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    void playerHeld(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 40, 0));

        // Calls a method
        player.getInventory().setItemStack(1, ItemStack.of(Material.STONE));
        // Calls a method
        assertEquals(ItemStack.AIR, player.getItemInMainHand());
        // Calls a method
        assertEquals(0, player.getHeldSlot());

        // Calls a method
        player.addPacketToQueue(new ClientHeldItemChangePacket((short) 1));
        // Calls a method
        player.interpretPacketQueue();

        // Calls a method
        assertEquals(ItemStack.of(Material.STONE), player.getItemInMainHand());
        // Calls a method
        assertEquals(1, player.getHeldSlot());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void playerHeldEvent(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 40, 0));

        // Calls a method
        player.getInventory().setItemStack(1, ItemStack.of(Material.STONE));
        // Calls a method
        assertEquals(ItemStack.AIR, player.getItemInMainHand());
        // Calls a method
        assertEquals(0, player.getHeldSlot());

        // Calls a method
        player.addPacketToQueue(new ClientHeldItemChangePacket((short) 1));
        // Calls a method
        var listener = env.listen(PlayerChangeHeldSlotEvent.class);
        // Start of a method/block
        listener.followup(event -> {
            // Calls a method
            assertEquals(player, event.getPlayer());
            // Calls a method
            assertEquals(0, event.getOldSlot());
            // Calls a method
            assertEquals(1, event.getNewSlot());
        // End of a block/expression
        });
        // Calls a method
        player.interpretPacketQueue();
        // Calls a method
        assertEquals(ItemStack.of(Material.STONE), player.getItemInMainHand());
        // Calls a method
        assertEquals(1, player.getHeldSlot());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void playerChangingSlots(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 40, 0));

        // Calls a method
        player.getInventory().setItemStack(1, ItemStack.of(Material.STONE));
        // Calls a method
        player.getInventory().setItemStack(3, ItemStack.of(Material.OAK_PLANKS));

        // Calls a method
        player.addPacketToQueue(new ClientHeldItemChangePacket((short) 1));
        // Calls a method
        var listener = env.listen(PlayerChangeHeldSlotEvent.class);
        // Start of a method/block
        listener.followup(event -> {
            // Calls a method
            assertEquals(player, event.getPlayer());
            // Calls a method
            assertEquals(0, event.getOldSlot());
            // Calls a method
            assertEquals(1, event.getNewSlot());
            // Calls a method
            assertEquals(ItemStack.AIR, event.getItemInOldSlot());
            // Calls a method
            assertEquals(ItemStack.of(Material.STONE), event.getItemInNewSlot());
        // End of a block/expression
        });
        // Calls a method
        player.interpretPacketQueue();

        // Calls a method
        player.addPacketToQueue(new ClientHeldItemChangePacket((short) 3));
        // Start of a method/block
        listener.followup(event -> {
            // Calls a method
            assertEquals(player, event.getPlayer());
            // Calls a method
            assertEquals(1, event.getOldSlot());
            // Calls a method
            assertEquals(3, event.getNewSlot());
            // Calls a method
            assertEquals(ItemStack.of(Material.STONE), event.getItemInOldSlot());
            // Calls a method
            assertEquals(ItemStack.of(Material.OAK_PLANKS), event.getItemInNewSlot());
        // End of a block/expression
        });
        // Calls a method
        player.interpretPacketQueue();
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void eventChangeIsReflectedOnClient(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 40, 0));

        // Calls a method
        player.eventNode().addListener(PlayerChangeHeldSlotEvent.class, event -> event.setNewSlot((byte) 0));

        // Calls a method
        var listener = connection.trackIncoming(HeldItemChangePacket.class);
        // Calls a method
        player.addPacketToQueue(new ClientHeldItemChangePacket((short) 0));
        // Calls a method
        player.interpretPacketQueue();
        // Code statement
        listener.assertEmpty(); // Ensure we don't send an unneeded packet if there is no change

        // Assigns a value
        listener = connection.trackIncoming(HeldItemChangePacket.class); // Re-register listener
        // Calls a method
        player.addPacketToQueue(new ClientHeldItemChangePacket((short) 3));
        // Calls a method
        player.interpretPacketQueue();
        // Code statement
        listener.assertSingle(packet -> assertEquals((byte) 0, packet.slot())); // Ensure packet is sent
    // End of a block/expression
    }
// End of a block/expression
}
