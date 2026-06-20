// Déclaration du paquet de ce fichier
package net.minestom.server.inventory;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientCloseWindowPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.CloseWindowPacket;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class InventoryCloseStateTest {


    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void doNotReceiveClosePacketFromServerWhenSendingClientCloseWindowPacket(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        assertEquals(instance, player.getInstance());

        // Appelle une méthode
        var packetTracker = connection.trackIncoming(CloseWindowPacket.class);
        // Appelle une méthode
        var inventory = new Inventory(InventoryType.CHEST_2_ROW, Component.text("Test"));
        // Appelle une méthode
        player.openInventory(inventory);
        // Instruction de code
        player.closeInventory(); // Closes the inventory server-side, should send a CloseWindowPacket
        // Appelle une méthode
        player.openInventory(inventory);
        // Send the close window packet
        // Appelle une méthode
        player.addPacketToQueue(new ClientCloseWindowPacket(inventory.getWindowId()));
        // Appelle une méthode
        player.interpretPacketQueue();
        // Appelle une méthode
        packetTracker.assertSingle(closeWindowPacket -> assertEquals(inventory.getWindowId(), closeWindowPacket.windowId()));
        // Instruction de code
        packetTracker.assertCount(1); // Assert we only get 1 close window packet from the closeInventory(); call
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
