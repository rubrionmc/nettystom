// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.PlayerEvent;
// Import of a required class
import net.minestom.server.network.debug.DebugSubscription;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Unmodifiable;

// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.Set;

/**
 * An event wrapper for {@link net.minestom.server.network.packet.client.play.ClientDebugSubscriptionRequestPacket}
 * which is called when any {@link DebugSubscription} is requested/removed/updated by the client
 * with all {@code subscriptions} in its entirety, with entries missing if unregistering from last event,
 * <br>
 * For example by commonly pressing F3-2 for {@link DebugSubscription#DEDICATED_SERVER_TICK_TIME}
 * will be a set containing {@link DebugSubscription#DEDICATED_SERVER_TICK_TIME}
 * and requesting a {@link DebugSubscription#BEES} will be an event where {@code subscriptions} contains both subscriptions.
 * <br>
 * By default, no response ({@link net.minestom.server.network.packet.server.play.DebugEventPacket}) is sent by the server
 * and no response is required if you choose to ignore.
 */
// Type declaration (class/interface/enum/record)
public class PlayerDebugSubscriptionsRequestEvent implements PlayerEvent {
    // Code statement
    private final Player player;
    // Code statement
    private final Set<DebugSubscription<?>> subscriptions;

    /**
     * Construct a new {@link PlayerDebugSubscriptionsRequestEvent}
     *
     * @param player player
     * @param subscriptions subscriptions
     */
    // Annotation for the following element
    @ApiStatus.Experimental
    // Start of a method/block
    public PlayerDebugSubscriptionsRequestEvent(Player player, Set<DebugSubscription<?>> subscriptions) {
        // Access to the current/parent object
        this.player = Objects.requireNonNull(player, "player");
        // Access to the current/parent object
        this.subscriptions = Objects.requireNonNull(subscriptions, "subscriptions");
    // End of a block/expression
    }

    /**
     * Gets the subscriptions requested by the player.
     * <br>
     * To determine which subscriptions were added or removed, compare this set
     * with the previously stored one (using set difference operations)
     *
     * @return the subscriptions
     */
    // Start of a method/block
    public @Unmodifiable Set<DebugSubscription<?>> getSubscriptions() {
        // Returns a value to the caller
        return subscriptions;
    // End of a block/expression
    }

    /**
     * Checks if there are any subscriptions requested.
     *
     * @return true if {@link #getSubscriptions()} is not empty.
     */
    // Start of a method/block
    public boolean wantsSubscriptions() {
        // Returns a value to the caller
        return !subscriptions.isEmpty();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Player getPlayer() {
        // Returns a value to the caller
        return player;
    // End of a block/expression
    }
// End of a block/expression
}
