// Déclaration du paquet de ce fichier
package net.minestom.server.inventory;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.event.inventory.InventoryOpenEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.item.ItemDropEvent;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.*;
// Import d'une classe nécessaire
import net.minestom.server.utils.inventory.PlayerInventoryUtils;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicBoolean;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class InventoryIntegrationTest {

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
        Inventory inventory = new Inventory(InventoryType.CHEST_6_ROW, Component.empty());
        // Appelle une méthode
        player.openInventory(inventory);
        // Appelle une méthode
        assertEquals(inventory, player.getOpenInventory());

        // Appelle une méthode
        var packetTracker = connection.trackIncoming(SetSlotPacket.class);
        // Appelle une méthode
        inventory.setItemStack(3, MAGIC_STACK);
        // Instruction de code
        packetTracker.assertSingle(slot -> assertEquals(MAGIC_STACK, slot.itemStack())); // Setting a slot should send a packet

        // Appelle une méthode
        packetTracker = connection.trackIncoming(SetSlotPacket.class);
        // Appelle une méthode
        inventory.setItemStack(3, MAGIC_STACK);
        // Instruction de code
        packetTracker.assertEmpty(); // Setting the same slot to the same ItemStack should not send another packet

        // Appelle une méthode
        packetTracker = connection.trackIncoming(SetSlotPacket.class);
        // Appelle une méthode
        inventory.setItemStack(3, ItemStack.AIR);
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
        Inventory inventory = new Inventory(InventoryType.CHEST_6_ROW, Component.empty());
        // Appelle une méthode
        player.openInventory(inventory);
        // Appelle une méthode
        assertEquals(inventory, player.getOpenInventory());

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
        Inventory inventory = new Inventory(InventoryType.CHEST_6_ROW, Component.empty());
        // Appelle une méthode
        player.openInventory(inventory);
        // Appelle une méthode
        assertEquals(inventory, player.getOpenInventory());

        // Appelle une méthode
        var setSlotTracker = connection.trackIncoming(SetSlotPacket.class);
        // Appelle une méthode
        var setCursorTracker = connection.trackIncoming(SetCursorItemPacket.class);

        // Appelle une méthode
        inventory.setItemStack(1, MAGIC_STACK);
        // Appelle une méthode
        inventory.setItemStack(3, MAGIC_STACK);
        // Appelle une méthode
        inventory.setItemStack(19, MAGIC_STACK);
        // Appelle une méthode
        inventory.setItemStack(40, MAGIC_STACK);
        // Appelle une méthode
        player.getInventory().setCursorItem(MAGIC_STACK);

        // Appelle une méthode
        setSlotTracker.assertCount(4);
        // Appelle une méthode
        setCursorTracker.assertCount(1);

        // Appelle une méthode
        setSlotTracker = connection.trackIncoming(SetSlotPacket.class);
        // Appelle une méthode
        var updateWindowTracker = connection.trackIncoming(WindowItemsPacket.class);
        // Appelle une méthode
        var equipmentTracker = connection.trackIncoming(EntityEquipmentPacket.class);

        // Perform the clear operation we are testing
        // Appelle une méthode
        inventory.clear();

        // Make sure not individual SetSlotPackets get sent
        // Appelle une méthode
        setSlotTracker.assertEmpty();

        // Make sure WindowItemsPacket is empty except for cursor (clearing the player inventory itself clears the cursor)
        // Début d'une méthode/d'un bloc
        updateWindowTracker.assertSingle(windowItemsPacket -> {
            // Appelle une méthode
            assertEquals(MAGIC_STACK, windowItemsPacket.carriedItem());
            // Boucle : répète un bloc
            for (ItemStack item : windowItemsPacket.items()) {
                // Appelle une méthode
                assertEquals(ItemStack.AIR, item);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });

        // Make sure EntityEquipmentPacket isn't sent (this is an Inventory, not a PlayerInventory)
        // Appelle une méthode
        equipmentTracker.assertEmpty();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void clearingPlayerInventoryClearsCursorTest(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        assertEquals(instance, player.getInstance());

        // Appelle une méthode
        var setCursorTracker = connection.trackIncoming(SetCursorItemPacket.class);
        // Appelle une méthode
        player.getInventory().setCursorItem(MAGIC_STACK);
        // Appelle une méthode
        setCursorTracker.assertCount(1);

        // Appelle une méthode
        var setSlotTracker = connection.trackIncoming(SetSlotPacket.class);
        // Appelle une méthode
        var setPlayerSlotTracker = connection.trackIncoming(SetPlayerInventorySlotPacket.class);
        // Appelle une méthode
        setCursorTracker = connection.trackIncoming(SetCursorItemPacket.class);
        // Appelle une méthode
        var updateWindowTracker = connection.trackIncoming(WindowItemsPacket.class);
        // Appelle une méthode
        var equipmentTracker = connection.trackIncoming(EntityEquipmentPacket.class);

        // Perform the clear operation we are testing
        // Appelle une méthode
        player.getInventory().clear();

        // Make sure no individual set slot/set cursor/set player slot packets get sent
        // Appelle une méthode
        setSlotTracker.assertEmpty();
        // Appelle une méthode
        setPlayerSlotTracker.assertEmpty();
        // Appelle une méthode
        setCursorTracker.assertEmpty();

        // Make sure WindowItemsPacket is empty
        // Appelle une méthode
        updateWindowTracker.assertSingle(windowItemsPacket -> assertEquals(ItemStack.AIR, windowItemsPacket.carriedItem()));

        // Make sure EntityEquipmentPacket is sent
        // Appelle une méthode
        equipmentTracker.assertSingle();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void closeInventoryTest(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        final var inventory = new Inventory(InventoryType.CHEST_1_ROW, "title");
        // Appelle une méthode
        player.openInventory(inventory);
        // Appelle une méthode
        assertSame(inventory, player.getOpenInventory());
        // Appelle une méthode
        player.closeInventory();
        // Appelle une méthode
        assertNull(player.getOpenInventory());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void openInventoryOnItemDropFromInventoryClosingTest(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        var listener = env.listen(ItemDropEvent.class);
        // Appelle une méthode
        final var firstInventory = new Inventory(InventoryType.CHEST_1_ROW, "title");
        // Appelle une méthode
        player.openInventory(firstInventory);
        // Appelle une méthode
        assertSame(firstInventory, player.getOpenInventory());
        // Appelle une méthode
        player.getInventory().setCursorItem(ItemStack.of(Material.STONE));

        // Appelle une méthode
        listener.followup();
        // Appelle une méthode
        player.closeInventory();
        // Appelle une méthode
        assertNull(player.getOpenInventory());

        // Appelle une méthode
        player.openInventory(firstInventory);
        // Appelle une méthode
        player.getInventory().setCursorItem(ItemStack.of(Material.STONE));
        // Appelle une méthode
        final var secondInventory = new Inventory(InventoryType.CHEST_1_ROW, "title");
        // Appelle une méthode
        listener.followup(event -> event.getPlayer().openInventory(secondInventory));
        // Appelle une méthode
        player.closeInventory();
        // Appelle une méthode
        assertSame(secondInventory, player.getOpenInventory());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testInnerInventorySlotSending(Env env) {
        // Inner inventory changes are sent along with the open inventory
        // Otherwise, they are sent separately

        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        assertEquals(instance, player.getInstance());

        // Appelle une méthode
        Inventory inventory = new Inventory(InventoryType.CHEST_6_ROW, Component.empty());
        // Appelle une méthode
        player.openInventory(inventory);
        // Appelle une méthode
        assertEquals(inventory, player.getOpenInventory());

        // Ensure that slots not in the inner inventory are sent separately
        // Appelle une méthode
        var packetTracker = connection.trackIncoming(SetPlayerInventorySlotPacket.class);
        // Appelle une méthode
        player.getInventory().setItemStack(PlayerInventoryUtils.OFFHAND_SLOT, MAGIC_STACK);
        // Début d'une méthode/d'un bloc
        packetTracker.assertSingle(slot -> {
            // Instruction de code
            assertEquals(40, slot.slot()); // Off hand is slot 40 in player inventory
            // Appelle une méthode
            assertEquals(MAGIC_STACK, slot.itemStack());
        // Fin d'un bloc/d'une expression
        });

        // Ensure that inner inventory slots are sent as the opened inventory
        // Appelle une méthode
        packetTracker = connection.trackIncoming(SetPlayerInventorySlotPacket.class);
        // Instruction de code
        player.getInventory().setItemStack(0, MAGIC_STACK); // Test with first inner inventory slot
        // Début d'une méthode/d'un bloc
        packetTracker.assertSingle(slot -> {
            // Appelle une méthode
            assertEquals(0, slot.slot());
            // Appelle une méthode
            assertEquals(MAGIC_STACK, slot.itemStack());
        // Fin d'un bloc/d'une expression
        });

        // Appelle une méthode
        packetTracker = connection.trackIncoming(SetPlayerInventorySlotPacket.class);
        // Instruction de code
        player.getInventory().setItemStack(35, MAGIC_STACK); // Test with last inner inventory slot
        // Début d'une méthode/d'un bloc
        packetTracker.assertSingle(slot -> {
            // Appelle une méthode
            assertEquals(35, slot.slot());
            // Appelle une méthode
            assertEquals(MAGIC_STACK, slot.itemStack());
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testEventNode(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();

        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        assertEquals(instance, player.getInstance());

        // Appelle une méthode
        Inventory inventory = new Inventory(InventoryType.CHEST_6_ROW, Component.empty());
        // Appelle une méthode
        AtomicBoolean called = new AtomicBoolean(false);
        // Instruction de code
        inventory.eventNode().addListener(
                // Instruction de code
                InventoryOpenEvent.class,
                // Début d'une méthode/d'un bloc
                event -> {
                    // Appelle une méthode
                    assertSame(inventory, event.getInventory());
                    // Appelle une méthode
                    called.set(true);
                // Fin d'un bloc/d'une expression
                }
        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        player.openInventory(inventory);
        // Appelle une méthode
        assertTrue(called.get(), "InventoryOpenEvent not fired");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
