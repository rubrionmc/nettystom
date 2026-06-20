// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.event.EventFilter;
// Import of a required class
import net.minestom.server.event.player.PlayerStabEvent;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientPlayerActionPacket;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class PlayerActionListenerTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testStabInvalidWeapon(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, 0, 0));

        // Calls a method
        var tracker = env.trackEvent(PlayerStabEvent.class, EventFilter.PLAYER, player);

        // Code statement
        PlayerActionListener.playerActionListener(new ClientPlayerActionPacket(
                // Code statement
                ClientPlayerActionPacket.Status.STAB,
                // Code statement
                Vec.ZERO, BlockFace.NORTH, 0
        // Code statement
        ), player);
        // Calls a method
        tracker.assertEmpty();
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testStabWithWeapon(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, 0, 0));
        // Calls a method
        player.setItemInMainHand(ItemStack.of(Material.NETHERITE_SPEAR));

        // Calls a method
        var tracker = env.trackEvent(PlayerStabEvent.class, EventFilter.PLAYER, player);
        // Code statement
        PlayerActionListener.playerActionListener(new ClientPlayerActionPacket(
                // Code statement
                ClientPlayerActionPacket.Status.STAB,
                // Code statement
                Vec.ZERO, BlockFace.NORTH, 0
        // Code statement
        ), player);

        // Calls a method
        tracker.assertSingle();
    // End of a block/expression
    }

// End of a block/expression
}
