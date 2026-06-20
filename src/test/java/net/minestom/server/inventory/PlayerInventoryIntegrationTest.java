// Package declaration for this file
package net.minestom.server.inventory;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.EquipmentSlot;
// Import of a required class
import net.minestom.server.event.EventFilter;
// Import of a required class
import net.minestom.server.event.inventory.InventoryCloseEvent;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientCloseWindowPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.*;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.concurrent.atomic.AtomicBoolean;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class PlayerInventoryIntegrationTest {

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
        var packetTracker = connection.trackIncoming(SetPlayerInventorySlotPacket.class);
        // Calls a method
        player.getInventory().setItemStack(3, MAGIC_STACK);
        // Code statement
        packetTracker.assertSingle(slot -> assertEquals(MAGIC_STACK, slot.itemStack())); // Setting a slot should send a packet

        // Calls a method
        packetTracker = connection.trackIncoming(SetPlayerInventorySlotPacket.class);
        // Calls a method
        player.getInventory().setItemStack(3, MAGIC_STACK);
        // Code statement
        packetTracker.assertEmpty(); // Setting the same slot to the same ItemStack should not send another packet

        // Calls a method
        packetTracker = connection.trackIncoming(SetPlayerInventorySlotPacket.class);
        // Calls a method
        player.getInventory().setItemStack(3, ItemStack.AIR);
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
        var setPlayerInventorySlotTracker = connection.trackIncoming(SetPlayerInventorySlotPacket.class);
        // Calls a method
        var setSlotTracker = connection.trackIncoming(SetSlotPacket.class);
        // Calls a method
        var setCursorTracker = connection.trackIncoming(SetCursorItemPacket.class);

        // Calls a method
        player.getInventory().setItemStack(1, MAGIC_STACK);
        // Calls a method
        player.getInventory().setItemStack(3, MAGIC_STACK);
        // Calls a method
        player.getInventory().setItemStack(19, MAGIC_STACK);
        // Calls a method
        player.getInventory().setItemStack(40, MAGIC_STACK);
        // Calls a method
        player.getInventory().setCursorItem(MAGIC_STACK);

        // Code statement
        setPlayerInventorySlotTracker.assertCount(3); // 1, 3, 19 are in player inventory
        // Code statement
        setSlotTracker.assertCount(1); // 40 is in crafting grid so window 0
        // Calls a method
        setCursorTracker.assertCount(1);

        // Calls a method
        setPlayerInventorySlotTracker = connection.trackIncoming(SetPlayerInventorySlotPacket.class);
        // Calls a method
        setSlotTracker = connection.trackIncoming(SetSlotPacket.class);
        // Calls a method
        setCursorTracker = connection.trackIncoming(SetCursorItemPacket.class);
        // Calls a method
        var updateWindowTracker = connection.trackIncoming(WindowItemsPacket.class);
        // Calls a method
        var equipmentTracker = connection.trackIncoming(EntityEquipmentPacket.class);

        // Perform the clear operation we are testing
        // Calls a method
        player.getInventory().clear();

        // Make sure no individual set slot / set cursor item packets get sent
        // Calls a method
        setSlotTracker.assertEmpty();
        // Calls a method
        setPlayerInventorySlotTracker.assertEmpty();
        // Calls a method
        setCursorTracker.assertEmpty();

        // Make sure WindowItemsPacket is empty
        // Start of a method/block
        updateWindowTracker.assertSingle(windowItemsPacket -> {
            // Calls a method
            assertEquals(ItemStack.AIR, windowItemsPacket.carriedItem());
            // Loop: repeats a block
            for (ItemStack item : windowItemsPacket.items()) {
                // Calls a method
                assertEquals(ItemStack.AIR, item);
            // End of a block/expression
            }
        // End of a block/expression
        });

        // Make sure EntityEquipmentPacket is empty
        // Start of a method/block
        equipmentTracker.assertSingle(entityEquipmentPacket -> {
            // Calls a method
            assertEquals(EquipmentSlot.values().length, entityEquipmentPacket.equipments().size());
            // Loop: repeats a block
            for (Map.Entry<EquipmentSlot, ItemStack> entry : entityEquipmentPacket.equipments().entrySet()) {
                // Calls a method
                assertEquals(ItemStack.AIR, entry.getValue());
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void equipmentViewTest(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connectionArmored = env.createConnection();
        // Calls a method
        var playerArmored = connectionArmored.connect(instance, new Pos(0, 42, 0));
        // Calls a method
        var connectionViewer = env.createConnection();
        // Calls a method
        var playerViewer = connectionViewer.connect(instance, new Pos(0, 42, 0));

        // Calls a method
        assertEquals(instance, playerArmored.getInstance());
        // Calls a method
        assertEquals(instance, playerViewer.getInstance());

        // Calls a method
        var equipmentTracker = connectionViewer.trackIncoming(EntityEquipmentPacket.class);

        // Setting to an item should send EntityEquipmentPacket to viewer
        // Calls a method
        playerArmored.setEquipment(EquipmentSlot.HELMET, MAGIC_STACK);
        // Calls a method
        equipmentTracker.assertSingle(entityEquipmentPacket -> assertEquals(MAGIC_STACK, entityEquipmentPacket.equipments().get(EquipmentSlot.HELMET)));

        // Setting to the same item shouldn't send packet
        // Calls a method
        equipmentTracker = connectionViewer.trackIncoming(EntityEquipmentPacket.class);
        // Calls a method
        playerArmored.setEquipment(EquipmentSlot.HELMET, MAGIC_STACK);
        // Calls a method
        equipmentTracker.assertEmpty();

        // Setting to air should send packet
        // Calls a method
        equipmentTracker = connectionViewer.trackIncoming(EntityEquipmentPacket.class);
        // Calls a method
        playerArmored.setEquipment(EquipmentSlot.HELMET, ItemStack.AIR);
        // Calls a method
        equipmentTracker.assertSingle(entityEquipmentPacket -> assertEquals(ItemStack.AIR, entityEquipmentPacket.equipments().get(EquipmentSlot.HELMET)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void heldItemViewTest(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connectionHolder = env.createConnection();
        // Calls a method
        var playerHolder = connectionHolder.connect(instance, new Pos(0, 42, 0));
        // Calls a method
        var connectionViewer = env.createConnection();
        // Calls a method
        var playerViewer = connectionViewer.connect(instance, new Pos(0, 42, 0));

        // Calls a method
        assertEquals(instance, playerHolder.getInstance());
        // Calls a method
        assertEquals(instance, playerViewer.getInstance());

        // Calls a method
        playerHolder.setHeldItemSlot((byte) 0);

        // Setting held item
        // Calls a method
        var equipmentTracker = connectionViewer.trackIncoming(EntityEquipmentPacket.class);
        // Calls a method
        playerHolder.setItemInMainHand(MAGIC_STACK);
        // Calls a method
        equipmentTracker.assertSingle(entityEquipmentPacket -> assertEquals(MAGIC_STACK, entityEquipmentPacket.equipments().get(EquipmentSlot.MAIN_HAND)));

        // Changing held slot to an empty slot should update MAIN_HAND to empty item
        // Calls a method
        equipmentTracker = connectionViewer.trackIncoming(EntityEquipmentPacket.class);
        // Calls a method
        playerHolder.setHeldItemSlot((byte) 3);
        // Calls a method
        equipmentTracker.assertSingle(entityEquipmentPacket -> assertEquals(ItemStack.AIR, entityEquipmentPacket.equipments().get(EquipmentSlot.MAIN_HAND)));

        // Changing held slot to the original slot should update MAIN_HAND to original item
        // Calls a method
        equipmentTracker = connectionViewer.trackIncoming(EntityEquipmentPacket.class);
        // Calls a method
        playerHolder.setHeldItemSlot((byte) 0);
        // Calls a method
        equipmentTracker.assertSingle(entityEquipmentPacket -> assertEquals(MAGIC_STACK, entityEquipmentPacket.equipments().get(EquipmentSlot.MAIN_HAND)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void closingPlayerInventorySendsEventTest(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Calls a method
        var listener = env.listen(InventoryCloseEvent.class);

        // Calls a method
        AtomicBoolean received = new AtomicBoolean(false);
        // Calls a method
        listener.followup(event -> received.set(true));

        // Calls a method
        player.addPacketToQueue(new ClientCloseWindowPacket(0));
        // Calls a method
        player.interpretPacketQueue();

        // Calls a method
        assertTrue(received.get());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void closeInventoryWithNoneOpenSendsPlayerInventoryClose(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Calls a method
        var listener = env.trackEvent(InventoryCloseEvent.class, EventFilter.PLAYER, player);

        // Calls a method
        player.closeInventory();

        // Start of a method/block
        listener.assertSingle(event -> {
            // Calls a method
            assertEquals(0, event.getInventory().getWindowId());
            // Calls a method
            assertFalse(event.isFromClient());
        // End of a block/expression
        });
    // End of a block/expression
    }

// End of a block/expression
}
