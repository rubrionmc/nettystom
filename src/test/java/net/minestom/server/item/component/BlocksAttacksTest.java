// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientPlayerActionPacket;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientUseItemPacket;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertFalse;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertTrue;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class BlocksAttacksTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void test(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Calls a method
        player.setItemInMainHand(ItemStack.of(Material.SHIELD));

        // Calls a method
        player.addPacketToQueue(new ClientUseItemPacket(PlayerHand.MAIN, 0, 0f, 0f));
        // Calls a method
        player.interpretPacketQueue();

        // Calls a method
        assertTrue(player.isUsingItem());
        // Calls a method
        assertTrue(player.getPlayerMeta().isHandActive());

        // Calls a method
        player.addPacketToQueue(new ClientPlayerActionPacket(ClientPlayerActionPacket.Status.UPDATE_ITEM_STATE, player.getPosition(), BlockFace.NORTH, 1));
        // Calls a method
        player.interpretPacketQueue();

        // Calls a method
        assertFalse(player.isUsingItem());
        // Calls a method
        assertFalse(player.getPlayerMeta().isHandActive());

        // Code statement
        player.setItemInMainHand(ItemStack.of(Material.DIAMOND_SWORD).with(DataComponents.BLOCKS_ATTACKS,
                // Creates a new object
                new BlocksAttacks(1f, 1f, List.of(), BlocksAttacks.ItemDamageFunction.DEFAULT, null, null, null)));

        // Calls a method
        player.addPacketToQueue(new ClientUseItemPacket(PlayerHand.MAIN, 0, 0f, 0f));
        // Calls a method
        player.interpretPacketQueue();

        // Calls a method
        assertTrue(player.isUsingItem());
        // Calls a method
        assertTrue(player.getPlayerMeta().isHandActive());
    // End of a block/expression
    }
// End of a block/expression
}
