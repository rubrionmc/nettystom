// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.Collection;

/**
 * Called every time a {@link Player} writes and sends something in the chat.
 * The event can be cancelled to not send anything, and the final message can be changed.
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerChatEvent implements PlayerInstanceEvent, CancellableEvent {
    // Instruction de code
    private final Player player;
    // Instruction de code
    private final Collection<Player> recipients;
    // Instruction de code
    private final String rawMessage;
    // Instruction de code
    private Component formattedMessage;
    // Instruction de code
    private boolean cancelled;

    // Instruction de code
    public PlayerChatEvent(Player player, Collection<Player> recipients,
                           // Début d'une méthode/d'un bloc
                           String rawMessage) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.recipients = new ArrayList<>(recipients);
        // Accès à l'objet courant/parent
        this.rawMessage = rawMessage;
        // Boucle : répète un bloc
        formattedMessage = buildDefaultChatMessage();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the players who will receive the message.
     * <p>
     * It can be modified to add and remove recipients.
     *
     * @return a modifiable list of the message's targets
     */
    // Début d'une méthode/d'un bloc
    public Collection<Player> getRecipients() {
        // Renvoie une valeur à l'appelant
        return recipients;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the original message content sent by the player.
     *
     * @return the sender's message
     */
    // Début d'une méthode/d'un bloc
    public String getRawMessage() {
        // Renvoie une valeur à l'appelant
        return rawMessage;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the final message component that will be sent.
     *
     * @return the chat message component
     */
    // Début d'une méthode/d'un bloc
    public Component getFormattedMessage() {
        // Renvoie une valeur à l'appelant
        return formattedMessage;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Used to change the final message component.
     *
     * @param message the new message component
     */
    // Début d'une méthode/d'un bloc
    public void setFormattedMessage(Component message) {
        // Boucle : répète un bloc
        formattedMessage = message;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isCancelled() {
        // Renvoie une valeur à l'appelant
        return cancelled;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setCancelled(boolean cancel) {
        // Accès à l'objet courant/parent
        this.cancelled = cancel;
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

    // Début d'une méthode/d'un bloc
    private Component buildDefaultChatMessage() {
        // Renvoie une valeur à l'appelant
        return Component.translatable("chat.type.text")
                // Instruction de code
                .arguments(
                        // Instruction de code
                        Component.text(player.getUsername())
                                // Instruction de code
                                .insertion(player.getUsername())
                                // Instruction de code
                                .hoverEvent(player),
                        // Appelle une méthode
                        Component.text(rawMessage));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
