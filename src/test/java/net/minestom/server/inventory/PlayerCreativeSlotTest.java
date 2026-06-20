// Déclaration du paquet de ce fichier
package net.minestom.server.inventory;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.GameMode;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.listener.CreativeInventoryActionListener;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientCreativeInventoryActionPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.inventory.PlayerInventoryUtils;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class PlayerCreativeSlotTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testCreativeSlots(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        assertEquals(instance, player.getInstance());

        // Appelle une méthode
        player.setGameMode(GameMode.CREATIVE);
        // Appelle une méthode
        player.addPacketToQueue(new ClientCreativeInventoryActionPacket((short) PlayerInventoryUtils.OFFHAND_SLOT, ItemStack.of(Material.STICK)));
        // Appelle une méthode
        player.interpretPacketQueue();
        // Appelle une méthode
        assertEquals(Material.STICK, player.getItemInOffHand().material());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testBoundsCheck(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        player.setGameMode(GameMode.CREATIVE);

        // Appelle une méthode
        assertDoesNotThrow(() -> CreativeInventoryActionListener.listener(new ClientCreativeInventoryActionPacket((short) 76, ItemStack.of(Material.OAK_LOG)), player));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
