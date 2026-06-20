// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientPlayerActionPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientUseItemPacket;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertFalse;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertTrue;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class BlocksAttacksTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void test(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Appelle une méthode
        player.setItemInMainHand(ItemStack.of(Material.SHIELD));

        // Appelle une méthode
        player.addPacketToQueue(new ClientUseItemPacket(PlayerHand.MAIN, 0, 0f, 0f));
        // Appelle une méthode
        player.interpretPacketQueue();

        // Appelle une méthode
        assertTrue(player.isUsingItem());
        // Appelle une méthode
        assertTrue(player.getPlayerMeta().isHandActive());

        // Appelle une méthode
        player.addPacketToQueue(new ClientPlayerActionPacket(ClientPlayerActionPacket.Status.UPDATE_ITEM_STATE, player.getPosition(), BlockFace.NORTH, 1));
        // Appelle une méthode
        player.interpretPacketQueue();

        // Appelle une méthode
        assertFalse(player.isUsingItem());
        // Appelle une méthode
        assertFalse(player.getPlayerMeta().isHandActive());

        // Instruction de code
        player.setItemInMainHand(ItemStack.of(Material.DIAMOND_SWORD).with(DataComponents.BLOCKS_ATTACKS,
                // Crée un nouvel objet
                new BlocksAttacks(1f, 1f, List.of(), BlocksAttacks.ItemDamageFunction.DEFAULT, null, null, null)));

        // Appelle une méthode
        player.addPacketToQueue(new ClientUseItemPacket(PlayerHand.MAIN, 0, 0f, 0f));
        // Appelle une méthode
        player.interpretPacketQueue();

        // Appelle une méthode
        assertTrue(player.isUsingItem());
        // Appelle une méthode
        assertTrue(player.getPlayerMeta().isHandActive());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
