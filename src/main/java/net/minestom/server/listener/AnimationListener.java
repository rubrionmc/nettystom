// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.PlayerHandAnimationEvent;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientAnimationPacket;

// Type declaration (class/interface/enum/record)
public class AnimationListener {

    // Start of a method/block
    public static void animationListener(ClientAnimationPacket packet, Player player) {
        // Calls a method
        final PlayerHand hand = packet.hand();
        // Calls a method
        final ItemStack itemStack = player.getItemInHand(hand);
        //itemStack.onLeftClick(player, hand);
        // Calls a method
        PlayerHandAnimationEvent handAnimationEvent = new PlayerHandAnimationEvent(player, hand);
        // Start of a method/block
        EventDispatcher.callCancellable(handAnimationEvent, () -> {
            // Multiple branching (switch/case)
            switch (hand) {
                // Multiple branching (switch/case)
                case MAIN -> player.swingMainHand(true);
                // Multiple branching (switch/case)
                case OFF -> player.swingOffHand(true);
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }

// End of a block/expression
}
