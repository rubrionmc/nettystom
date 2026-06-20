// Package declaration for this file
package net.minestom.server.inventory;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.event.inventory.InventoryOpenEvent;
// Import of a required class
import net.minestom.server.event.item.ItemDropEvent;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.network.packet.server.play.*;
// Import of a required class
import net.minestom.server.utils.inventory.PlayerInventoryUtils;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.concurrent.atomic.AtomicBoolean;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class InventoryIntegrationTest {

    // Calls a method
    private static final ItemStack MAGIC_STACK = ItemStack.of(Material.DIAMOND, 3);

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void setSlotDuplicateTest(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Calls a method
        assertEquals(instance, player.getInstance());

        // Calls a method
        Inventory inventory = new Inventory(InventoryType.CHEST_6_ROW, Component.empty());
        // Calls a method
        player.openInventory(inventory);
        // Calls a method
        assertEquals(inventory, player.getOpenInventory());

        // Calls a method
        var packetTracker = connection.trackIncoming(SetSlotPacket.class);
        // Calls a method
        inventory.setItemStack(3, MAGIC_STACK);
        // Code statement
        packetTracker.assertSingle(slot -> assertEquals(MAGIC_STACK, slot.itemStack())); // Setting a slot should send a packet

        // Calls a method
        packetTracker = connection.trackIncoming(SetSlotPacket.class);
        // Calls a method
        inventory.setItemStack(3, MAGIC_STACK);
        // Code statement
        packetTracker.assertEmpty(); // Setting the same slot to the same ItemStack should not send another packet

        // Calls a method
        packetTracker = connection.trackIncoming(SetSlotPacket.class);
        // Calls a method
        inventory.setItemStack(3, ItemStack.AIR);
        // Code statement
        packetTracker.assertSingle(slot -> assertEquals(ItemStack.AIR, slot.itemStack())); // Setting a slot should send a packet
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void setCursorItemDuplicateTest(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Calls a method
        assertEquals(instance, player.getInstance());

        // Calls a method
        Inventory inventory = new Inventory(InventoryType.CHEST_6_ROW, Component.empty());
        // Calls a method
        player.openInventory(inventory);
        // Calls a method
        assertEquals(inventory, player.getOpenInventory());

        // Calls a method
        var packetTracker = connection.trackIncoming(SetCursorItemPacket.class);
        // Calls a method
        player.getInventory().setCursorItem(MAGIC_STACK);
        // Code statement
        packetTracker.assertSingle(slot -> assertEquals(MAGIC_STACK, slot.itemStack())); // Setting a slot should send a packet

        // Calls a method
        packetTracker = connection.trackIncoming(SetCursorItemPacket.class);
        // Calls a method
        player.getInventory().setCursorItem(MAGIC_STACK);
        // Code statement
        packetTracker.assertEmpty(); // Setting the same slot to the same ItemStack should not send another packet

        // Calls a method
        packetTracker = connection.trackIncoming(SetCursorItemPacket.class);
        // Calls a method
        player.getInventory().setCursorItem(ItemStack.AIR);
        // Code statement
        packetTracker.assertSingle(slot -> assertEquals(ItemStack.AIR, slot.itemStack())); // Setting a slot should send a packet
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void clearInventoryTest(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Calls a method
        assertEquals(instance, player.getInstance());

        // Calls a method
        Inventory inventory = new Inventory(InventoryType.CHEST_6_ROW, Component.empty());
        // Calls a method
        player.openInventory(inventory);
        // Calls a method
        assertEquals(inventory, player.getOpenInventory());

        // Calls a method
        var setSlotTracker = connection.trackIncoming(SetSlotPacket.class);
        // Calls a method
        var setCursorTracker = connection.trackIncoming(SetCursorItemPacket.class);

        // Calls a method
        inventory.setItemStack(1, MAGIC_STACK);
        // Calls a method
        inventory.setItemStack(3, MAGIC_STACK);
        // Calls a method
        inventory.setItemStack(19, MAGIC_STACK);
        // Calls a method
        inventory.setItemStack(40, MAGIC_STACK);
        // Calls a method
        player.getInventory().setCursorItem(MAGIC_STACK);

        // Calls a method
        setSlotTracker.assertCount(4);
        // Calls a method
        setCursorTracker.assertCount(1);

        // Calls a method
        setSlotTracker = connection.trackIncoming(SetSlotPacket.class);
        // Calls a method
        var updateWindowTracker = connection.trackIncoming(WindowItemsPacket.class);
        // Calls a method
        var equipmentTracker = connection.trackIncoming(EntityEquipmentPacket.class);

        // Perform the clear operation we are testing
        // Calls a method
        inventory.clear();

        // Make sure not individual SetSlotPackets get sent
        // Calls a method
        setSlotTracker.assertEmpty();

        // Make sure WindowItemsPacket is empty except for cursor (clearing the player inventory itself clears the cursor)
        // Start of a method/block
        updateWindowTracker.assertSingle(windowItemsPacket -> {
            // Calls a method
            assertEquals(MAGIC_STACK, windowItemsPacket.carriedItem());
            // Loop: repeats a block
            for (ItemStack item : windowItemsPacket.items()) {
                // Calls a method
                assertEquals(ItemStack.AIR, item);
            // End of a block/expression
            }
        // End of a block/expression
        });

        // Make sure EntityEquipmentPacket isn't sent (this is an Inventory, not a PlayerInventory)
        // Calls a method
        equipmentTracker.assertEmpty();
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void clearingPlayerInventoryClearsCursorTest(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Calls a method
        assertEquals(instance, player.getInstance());

        // Calls a method
        var setCursorTracker = connection.trackIncoming(SetCursorItemPacket.class);
        // Calls a method
        player.getInventory().setCursorItem(MAGIC_STACK);
        // Calls a method
        setCursorTracker.assertCount(1);

        // Calls a method
        var setSlotTracker = connection.trackIncoming(SetSlotPacket.class);
        // Calls a method
        var setPlayerSlotTracker = connection.trackIncoming(SetPlayerInventorySlotPacket.class);
        // Calls a method
        setCursorTracker = connection.trackIncoming(SetCursorItemPacket.class);
        // Calls a method
        var updateWindowTracker = connection.trackIncoming(WindowItemsPacket.class);
        // Calls a method
        var equipmentTracker = connection.trackIncoming(EntityEquipmentPacket.class);

        // Perform the clear operation we are testing
        // Calls a method
        player.getInventory().clear();

        // Make sure no individual set slot/set cursor/set player slot packets get sent
        // Calls a method
        setSlotTracker.assertEmpty();
        // Calls a method
        setPlayerSlotTracker.assertEmpty();
        // Calls a method
        setCursorTracker.assertEmpty();

        // Make sure WindowItemsPacket is empty
        // Calls a method
        updateWindowTracker.assertSingle(windowItemsPacket -> assertEquals(ItemStack.AIR, windowItemsPacket.carriedItem()));

        // Make sure EntityEquipmentPacket is sent
        // Calls a method
        equipmentTracker.assertSingle();
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void closeInventoryTest(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Calls a method
        final var inventory = new Inventory(InventoryType.CHEST_1_ROW, "title");
        // Calls a method
        player.openInventory(inventory);
        // Calls a method
        assertSame(inventory, player.getOpenInventory());
        // Calls a method
        player.closeInventory();
        // Calls a method
        assertNull(player.getOpenInventory());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void openInventoryOnItemDropFromInventoryClosingTest(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Calls a method
        var listener = env.listen(ItemDropEvent.class);
        // Calls a method
        final var firstInventory = new Inventory(InventoryType.CHEST_1_ROW, "title");
        // Calls a method
        player.openInventory(firstInventory);
        // Calls a method
        assertSame(firstInventory, player.getOpenInventory());
        // Calls a method
        player.getInventory().setCursorItem(ItemStack.of(Material.STONE));

        // Calls a method
        listener.followup();
        // Calls a method
        player.closeInventory();
        // Calls a method
        assertNull(player.getOpenInventory());

        // Calls a method
        player.openInventory(firstInventory);
        // Calls a method
        player.getInventory().setCursorItem(ItemStack.of(Material.STONE));
        // Calls a method
        final var secondInventory = new Inventory(InventoryType.CHEST_1_ROW, "title");
        // Calls a method
        listener.followup(event -> event.getPlayer().openInventory(secondInventory));
        // Calls a method
        player.closeInventory();
        // Calls a method
        assertSame(secondInventory, player.getOpenInventory());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testInnerInventorySlotSending(Env env) {
        // Inner inventory changes are sent along with the open inventory
        // Otherwise, they are sent separately

        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Calls a method
        assertEquals(instance, player.getInstance());

        // Calls a method
        Inventory inventory = new Inventory(InventoryType.CHEST_6_ROW, Component.empty());
        // Calls a method
        player.openInventory(inventory);
        // Calls a method
        assertEquals(inventory, player.getOpenInventory());

        // Ensure that slots not in the inner inventory are sent separately
        // Calls a method
        var packetTracker = connection.trackIncoming(SetPlayerInventorySlotPacket.class);
        // Calls a method
        player.getInventory().setItemStack(PlayerInventoryUtils.OFFHAND_SLOT, MAGIC_STACK);
        // Start of a method/block
        packetTracker.assertSingle(slot -> {
            // Code statement
            assertEquals(40, slot.slot()); // Off hand is slot 40 in player inventory
            // Calls a method
            assertEquals(MAGIC_STACK, slot.itemStack());
        // End of a block/expression
        });

        // Ensure that inner inventory slots are sent as the opened inventory
        // Calls a method
        packetTracker = connection.trackIncoming(SetPlayerInventorySlotPacket.class);
        // Code statement
        player.getInventory().setItemStack(0, MAGIC_STACK); // Test with first inner inventory slot
        // Start of a method/block
        packetTracker.assertSingle(slot -> {
            // Calls a method
            assertEquals(0, slot.slot());
            // Calls a method
            assertEquals(MAGIC_STACK, slot.itemStack());
        // End of a block/expression
        });

        // Calls a method
        packetTracker = connection.trackIncoming(SetPlayerInventorySlotPacket.class);
        // Code statement
        player.getInventory().setItemStack(35, MAGIC_STACK); // Test with last inner inventory slot
        // Start of a method/block
        packetTracker.assertSingle(slot -> {
            // Calls a method
            assertEquals(35, slot.slot());
            // Calls a method
            assertEquals(MAGIC_STACK, slot.itemStack());
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testEventNode(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();

        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Calls a method
        assertEquals(instance, player.getInstance());

        // Calls a method
        Inventory inventory = new Inventory(InventoryType.CHEST_6_ROW, Component.empty());
        // Calls a method
        AtomicBoolean called = new AtomicBoolean(false);
        // Code statement
        inventory.eventNode().addListener(
                // Code statement
                InventoryOpenEvent.class,
                // Start of a method/block
                event -> {
                    // Calls a method
                    assertSame(inventory, event.getInventory());
                    // Calls a method
                    called.set(true);
                // End of a block/expression
                }
        // End of a block/expression
        );

        // Calls a method
        player.openInventory(inventory);
        // Calls a method
        assertTrue(called.get(), "InventoryOpenEvent not fired");
    // End of a block/expression
    }
// End of a block/expression
}
