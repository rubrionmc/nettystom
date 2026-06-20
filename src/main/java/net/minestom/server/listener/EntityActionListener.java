// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.PlayerLeaveBedEvent;
// Import of a required class
import net.minestom.server.event.player.PlayerStartFlyingWithElytraEvent;
// Import of a required class
import net.minestom.server.event.player.PlayerStartSprintingEvent;
// Import of a required class
import net.minestom.server.event.player.PlayerStopSprintingEvent;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientEntityActionPacket;

// Type declaration (class/interface/enum/record)
public class EntityActionListener {

    // Start of a method/block
    public static void listener(ClientEntityActionPacket packet, Player player) {
        // Multiple branching (switch/case)
        switch (packet.action()) {
            // Multiple branching (switch/case)
            case START_SPRINTING -> EntityActionListener.setSprinting(player, true);
            // Multiple branching (switch/case)
            case STOP_SPRINTING -> EntityActionListener.setSprinting(player, false);
            // Multiple branching (switch/case)
            case START_FLYING_ELYTRA -> EntityActionListener.startFlyingElytra(player);
            // Multiple branching (switch/case)
            case LEAVE_BED -> EntityActionListener.onLeaveBed(player);

            // TODO do remaining actions
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static void setSprinting(Player player, boolean sprinting) {
        // Calls a method
        boolean oldState = player.isSprinting();

        // Calls a method
        player.setSprinting(sprinting);

        // Branch: checks a condition
        if (oldState != sprinting) {
            // Branch: checks a condition
            if (sprinting) {
                // Calls a method
                EventDispatcher.call(new PlayerStartSprintingEvent(player));
            // Alternative branch of the condition
            } else {
                // Calls a method
                EventDispatcher.call(new PlayerStopSprintingEvent(player));
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static void startFlyingElytra(Player player) {
        // Calls a method
        player.setFlyingWithElytra(true);
        // Calls a method
        EventDispatcher.call(new PlayerStartFlyingWithElytraEvent(player));
    // End of a block/expression
    }

    // Start of a method/block
    private static void onLeaveBed(Player player) {
        // Calls a method
        var event = new PlayerLeaveBedEvent(player);
        // Start of a method/block
        EventDispatcher.callCancellable(event, () -> {
            // Calls a method
            player.getLivingEntityMeta().setBedInWhichSleepingPosition(null);
            // Calls a method
            player.leaveBed();
        // End of a block/expression
        });
    // End of a block/expression
    }
// End of a block/expression
}
