// Déclaration du paquet de ce fichier
package net.minestom.server.inventory;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.EquipmentSlot;
// Import d'une classe nécessaire
import net.minestom.server.event.EventFilter;
// Import d'une classe nécessaire
import net.minestom.server.event.inventory.InventoryCloseEvent;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientCloseWindowPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.*;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicBoolean;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class PlayerInventoryIntegrationTest {

    // Appelle une méthode
    private static final ItemStack MAGIC_STACK = ItemStack.of(Material.DIAMOND, 3);

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void setSlotDuplicateTest(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        assertEquals(instance, player.getInstance());

        // Appelle une méthode
        var packetTracker = connection.trackIncoming(SetPlayerInventorySlotPacket.class);
        // Appelle une méthode
        player.getInventory().setItemStack(3, MAGIC_STACK);
        // Instruction de code
        packetTracker.assertSingle(slot -> assertEquals(MAGIC_STACK, slot.itemStack())); // Setting a slot should send a packet

        // Appelle une méthode
        packetTracker = connection.trackIncoming(SetPlayerInventorySlotPacket.class);
        // Appelle une méthode
        player.getInventory().setItemStack(3, MAGIC_STACK);
        // Instruction de code
        packetTracker.assertEmpty(); // Setting the same slot to the same ItemStack should not send another packet

        // Appelle une méthode
        packetTracker = connection.trackIncoming(SetPlayerInventorySlotPacket.class);
        // Appelle une méthode
        player.getInventory().setItemStack(3, ItemStack.AIR);
        // Instruction de code
        packetTracker.assertSingle(slot -> assertEquals(ItemStack.AIR, slot.itemStack())); // Setting a slot should send a packet
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void setCursorItemDuplicateTest(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        assertEquals(instance, player.getInstance());

        // Appelle une méthode
        var packetTracker = connection.trackIncoming(SetCursorItemPacket.class);
        // Appelle une méthode
        player.getInventory().setCursorItem(MAGIC_STACK);
        // Instruction de code
        packetTracker.assertSingle(slot -> assertEquals(MAGIC_STACK, slot.itemStack())); // Setting a slot should send a packet

        // Appelle une méthode
        packetTracker = connection.trackIncoming(SetCursorItemPacket.class);
        // Appelle une méthode
        player.getInventory().setCursorItem(MAGIC_STACK);
        // Instruction de code
        packetTracker.assertEmpty(); // Setting the same slot to the same ItemStack should not send another packet

        // Appelle une méthode
        packetTracker = connection.trackIncoming(SetCursorItemPacket.class);
        // Appelle une méthode
        player.getInventory().setCursorItem(ItemStack.AIR);
        // Instruction de code
        packetTracker.assertSingle(slot -> assertEquals(ItemStack.AIR, slot.itemStack())); // Setting a slot should send a packet
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void clearInventoryTest(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        assertEquals(instance, player.getInstance());

        // Appelle une méthode
        var setPlayerInventorySlotTracker = connection.trackIncoming(SetPlayerInventorySlotPacket.class);
        // Appelle une méthode
        var setSlotTracker = connection.trackIncoming(SetSlotPacket.class);
        // Appelle une méthode
        var setCursorTracker = connection.trackIncoming(SetCursorItemPacket.class);

        // Appelle une méthode
        player.getInventory().setItemStack(1, MAGIC_STACK);
        // Appelle une méthode
        player.getInventory().setItemStack(3, MAGIC_STACK);
        // Appelle une méthode
        player.getInventory().setItemStack(19, MAGIC_STACK);
        // Appelle une méthode
        player.getInventory().setItemStack(40, MAGIC_STACK);
        // Appelle une méthode
        player.getInventory().setCursorItem(MAGIC_STACK);

        // Instruction de code
        setPlayerInventorySlotTracker.assertCount(3); // 1, 3, 19 are in player inventory
        // Instruction de code
        setSlotTracker.assertCount(1); // 40 is in crafting grid so window 0
        // Appelle une méthode
        setCursorTracker.assertCount(1);

        // Appelle une méthode
        setPlayerInventorySlotTracker = connection.trackIncoming(SetPlayerInventorySlotPacket.class);
        // Appelle une méthode
        setSlotTracker = connection.trackIncoming(SetSlotPacket.class);
        // Appelle une méthode
        setCursorTracker = connection.trackIncoming(SetCursorItemPacket.class);
        // Appelle une méthode
        var updateWindowTracker = connection.trackIncoming(WindowItemsPacket.class);
        // Appelle une méthode
        var equipmentTracker = connection.trackIncoming(EntityEquipmentPacket.class);

        // Perform the clear operation we are testing
        // Appelle une méthode
        player.getInventory().clear();

        // Make sure no individual set slot / set cursor item packets get sent
        // Appelle une méthode
        setSlotTracker.assertEmpty();
        // Appelle une méthode
        setPlayerInventorySlotTracker.assertEmpty();
        // Appelle une méthode
        setCursorTracker.assertEmpty();

        // Make sure WindowItemsPacket is empty
        // Début d'une méthode/d'un bloc
        updateWindowTracker.assertSingle(windowItemsPacket -> {
            // Appelle une méthode
            assertEquals(ItemStack.AIR, windowItemsPacket.carriedItem());
            // Boucle : répète un bloc
            for (ItemStack item : windowItemsPacket.items()) {
                // Appelle une méthode
                assertEquals(ItemStack.AIR, item);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });

        // Make sure EntityEquipmentPacket is empty
        // Début d'une méthode/d'un bloc
        equipmentTracker.assertSingle(entityEquipmentPacket -> {
            // Appelle une méthode
            assertEquals(EquipmentSlot.values().length, entityEquipmentPacket.equipments().size());
            // Boucle : répète un bloc
            for (Map.Entry<EquipmentSlot, ItemStack> entry : entityEquipmentPacket.equipments().entrySet()) {
                // Appelle une méthode
                assertEquals(ItemStack.AIR, entry.getValue());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void equipmentViewTest(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connectionArmored = env.createConnection();
        // Appelle une méthode
        var playerArmored = connectionArmored.connect(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        var connectionViewer = env.createConnection();
        // Appelle une méthode
        var playerViewer = connectionViewer.connect(instance, new Pos(0, 42, 0));

        // Appelle une méthode
        assertEquals(instance, playerArmored.getInstance());
        // Appelle une méthode
        assertEquals(instance, playerViewer.getInstance());

        // Appelle une méthode
        var equipmentTracker = connectionViewer.trackIncoming(EntityEquipmentPacket.class);

        // Setting to an item should send EntityEquipmentPacket to viewer
        // Appelle une méthode
        playerArmored.setEquipment(EquipmentSlot.HELMET, MAGIC_STACK);
        // Appelle une méthode
        equipmentTracker.assertSingle(entityEquipmentPacket -> assertEquals(MAGIC_STACK, entityEquipmentPacket.equipments().get(EquipmentSlot.HELMET)));

        // Setting to the same item shouldn't send packet
        // Appelle une méthode
        equipmentTracker = connectionViewer.trackIncoming(EntityEquipmentPacket.class);
        // Appelle une méthode
        playerArmored.setEquipment(EquipmentSlot.HELMET, MAGIC_STACK);
        // Appelle une méthode
        equipmentTracker.assertEmpty();

        // Setting to air should send packet
        // Appelle une méthode
        equipmentTracker = connectionViewer.trackIncoming(EntityEquipmentPacket.class);
        // Appelle une méthode
        playerArmored.setEquipment(EquipmentSlot.HELMET, ItemStack.AIR);
        // Appelle une méthode
        equipmentTracker.assertSingle(entityEquipmentPacket -> assertEquals(ItemStack.AIR, entityEquipmentPacket.equipments().get(EquipmentSlot.HELMET)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void heldItemViewTest(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connectionHolder = env.createConnection();
        // Appelle une méthode
        var playerHolder = connectionHolder.connect(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        var connectionViewer = env.createConnection();
        // Appelle une méthode
        var playerViewer = connectionViewer.connect(instance, new Pos(0, 42, 0));

        // Appelle une méthode
        assertEquals(instance, playerHolder.getInstance());
        // Appelle une méthode
        assertEquals(instance, playerViewer.getInstance());

        // Appelle une méthode
        playerHolder.setHeldItemSlot((byte) 0);

        // Setting held item
        // Appelle une méthode
        var equipmentTracker = connectionViewer.trackIncoming(EntityEquipmentPacket.class);
        // Appelle une méthode
        playerHolder.setItemInMainHand(MAGIC_STACK);
        // Appelle une méthode
        equipmentTracker.assertSingle(entityEquipmentPacket -> assertEquals(MAGIC_STACK, entityEquipmentPacket.equipments().get(EquipmentSlot.MAIN_HAND)));

        // Changing held slot to an empty slot should update MAIN_HAND to empty item
        // Appelle une méthode
        equipmentTracker = connectionViewer.trackIncoming(EntityEquipmentPacket.class);
        // Appelle une méthode
        playerHolder.setHeldItemSlot((byte) 3);
        // Appelle une méthode
        equipmentTracker.assertSingle(entityEquipmentPacket -> assertEquals(ItemStack.AIR, entityEquipmentPacket.equipments().get(EquipmentSlot.MAIN_HAND)));

        // Changing held slot to the original slot should update MAIN_HAND to original item
        // Appelle une méthode
        equipmentTracker = connectionViewer.trackIncoming(EntityEquipmentPacket.class);
        // Appelle une méthode
        playerHolder.setHeldItemSlot((byte) 0);
        // Appelle une méthode
        equipmentTracker.assertSingle(entityEquipmentPacket -> assertEquals(MAGIC_STACK, entityEquipmentPacket.equipments().get(EquipmentSlot.MAIN_HAND)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void closingPlayerInventorySendsEventTest(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Appelle une méthode
        var listener = env.listen(InventoryCloseEvent.class);

        // Appelle une méthode
        AtomicBoolean received = new AtomicBoolean(false);
        // Appelle une méthode
        listener.followup(event -> received.set(true));

        // Appelle une méthode
        player.addPacketToQueue(new ClientCloseWindowPacket(0));
        // Appelle une méthode
        player.interpretPacketQueue();

        // Appelle une méthode
        assertTrue(received.get());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void closeInventoryWithNoneOpenSendsPlayerInventoryClose(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Appelle une méthode
        var listener = env.trackEvent(InventoryCloseEvent.class, EventFilter.PLAYER, player);

        // Appelle une méthode
        player.closeInventory();

        // Début d'une méthode/d'un bloc
        listener.assertSingle(event -> {
            // Appelle une méthode
            assertEquals(0, event.getInventory().getWindowId());
            // Appelle une méthode
            assertFalse(event.isFromClient());
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
