// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerChangeHeldSlotEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerPacketOutEvent;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientHeldItemChangePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.HeldItemChangePacket;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class PlayerHeldIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void playerHeld(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 40, 0));

        // Appelle une méthode
        player.getInventory().setItemStack(1, ItemStack.of(Material.STONE));
        // Appelle une méthode
        assertEquals(ItemStack.AIR, player.getItemInMainHand());
        // Appelle une méthode
        assertEquals(0, player.getHeldSlot());

        // Appelle une méthode
        player.addPacketToQueue(new ClientHeldItemChangePacket((short) 1));
        // Appelle une méthode
        player.interpretPacketQueue();

        // Appelle une méthode
        assertEquals(ItemStack.of(Material.STONE), player.getItemInMainHand());
        // Appelle une méthode
        assertEquals(1, player.getHeldSlot());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void playerHeldEvent(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 40, 0));

        // Appelle une méthode
        player.getInventory().setItemStack(1, ItemStack.of(Material.STONE));
        // Appelle une méthode
        assertEquals(ItemStack.AIR, player.getItemInMainHand());
        // Appelle une méthode
        assertEquals(0, player.getHeldSlot());

        // Appelle une méthode
        player.addPacketToQueue(new ClientHeldItemChangePacket((short) 1));
        // Appelle une méthode
        var listener = env.listen(PlayerChangeHeldSlotEvent.class);
        // Début d'une méthode/d'un bloc
        listener.followup(event -> {
            // Appelle une méthode
            assertEquals(player, event.getPlayer());
            // Appelle une méthode
            assertEquals(0, event.getOldSlot());
            // Appelle une méthode
            assertEquals(1, event.getNewSlot());
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        player.interpretPacketQueue();
        // Appelle une méthode
        assertEquals(ItemStack.of(Material.STONE), player.getItemInMainHand());
        // Appelle une méthode
        assertEquals(1, player.getHeldSlot());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void playerChangingSlots(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 40, 0));

        // Appelle une méthode
        player.getInventory().setItemStack(1, ItemStack.of(Material.STONE));
        // Appelle une méthode
        player.getInventory().setItemStack(3, ItemStack.of(Material.OAK_PLANKS));

        // Appelle une méthode
        player.addPacketToQueue(new ClientHeldItemChangePacket((short) 1));
        // Appelle une méthode
        var listener = env.listen(PlayerChangeHeldSlotEvent.class);
        // Début d'une méthode/d'un bloc
        listener.followup(event -> {
            // Appelle une méthode
            assertEquals(player, event.getPlayer());
            // Appelle une méthode
            assertEquals(0, event.getOldSlot());
            // Appelle une méthode
            assertEquals(1, event.getNewSlot());
            // Appelle une méthode
            assertEquals(ItemStack.AIR, event.getItemInOldSlot());
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.STONE), event.getItemInNewSlot());
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        player.interpretPacketQueue();

        // Appelle une méthode
        player.addPacketToQueue(new ClientHeldItemChangePacket((short) 3));
        // Début d'une méthode/d'un bloc
        listener.followup(event -> {
            // Appelle une méthode
            assertEquals(player, event.getPlayer());
            // Appelle une méthode
            assertEquals(1, event.getOldSlot());
            // Appelle une méthode
            assertEquals(3, event.getNewSlot());
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.STONE), event.getItemInOldSlot());
            // Appelle une méthode
            assertEquals(ItemStack.of(Material.OAK_PLANKS), event.getItemInNewSlot());
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        player.interpretPacketQueue();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void eventChangeIsReflectedOnClient(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 40, 0));

        // Appelle une méthode
        player.eventNode().addListener(PlayerChangeHeldSlotEvent.class, event -> event.setNewSlot((byte) 0));

        // Appelle une méthode
        var listener = connection.trackIncoming(HeldItemChangePacket.class);
        // Appelle une méthode
        player.addPacketToQueue(new ClientHeldItemChangePacket((short) 0));
        // Appelle une méthode
        player.interpretPacketQueue();
        // Instruction de code
        listener.assertEmpty(); // Ensure we don't send an unneeded packet if there is no change

        // Affecte une valeur
        listener = connection.trackIncoming(HeldItemChangePacket.class); // Re-register listener
        // Appelle une méthode
        player.addPacketToQueue(new ClientHeldItemChangePacket((short) 3));
        // Appelle une méthode
        player.interpretPacketQueue();
        // Instruction de code
        listener.assertSingle(packet -> assertEquals((byte) 0, packet.slot())); // Ensure packet is sent
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
