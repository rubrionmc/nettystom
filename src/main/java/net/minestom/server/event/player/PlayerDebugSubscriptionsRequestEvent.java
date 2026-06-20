// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.debug.DebugSubscription;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Unmodifiable;

// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
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
// Déclaration de type (classe/interface/enum/record)
public class PlayerDebugSubscriptionsRequestEvent implements PlayerEvent {
    // Instruction de code
    private final Player player;
    // Instruction de code
    private final Set<DebugSubscription<?>> subscriptions;

    /**
     * Construct a new {@link PlayerDebugSubscriptionsRequestEvent}
     *
     * @param player player
     * @param subscriptions subscriptions
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Début d'une méthode/d'un bloc
    public PlayerDebugSubscriptionsRequestEvent(Player player, Set<DebugSubscription<?>> subscriptions) {
        // Accès à l'objet courant/parent
        this.player = Objects.requireNonNull(player, "player");
        // Accès à l'objet courant/parent
        this.subscriptions = Objects.requireNonNull(subscriptions, "subscriptions");
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the subscriptions requested by the player.
     * <br>
     * To determine which subscriptions were added or removed, compare this set
     * with the previously stored one (using set difference operations)
     *
     * @return the subscriptions
     */
    // Début d'une méthode/d'un bloc
    public @Unmodifiable Set<DebugSubscription<?>> getSubscriptions() {
        // Renvoie une valeur à l'appelant
        return subscriptions;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if there are any subscriptions requested.
     *
     * @return true if {@link #getSubscriptions()} is not empty.
     */
    // Début d'une méthode/d'un bloc
    public boolean wantsSubscriptions() {
        // Renvoie une valeur à l'appelant
        return !subscriptions.isEmpty();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Player getPlayer() {
        // Renvoie une valeur à l'appelant
        return player;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
