// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

/**
 * Called when a player die in {@link Player#kill()}.
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerDeathEvent implements PlayerInstanceEvent {

    // Instruction de code
    private final Player player;
    // Instruction de code
    private Component deathText;
    // Instruction de code
    private Component chatMessage;

    // Début d'une méthode/d'un bloc
    public PlayerDeathEvent(Player player, Component deathText, Component chatMessage) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.deathText = deathText;
        // Accès à l'objet courant/parent
        this.chatMessage = chatMessage;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the text displayed in the death screen.
     *
     * @return the death text, can be null
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Component getDeathText() {
        // Renvoie une valeur à l'appelant
        return deathText;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the text displayed in the death screen.
     *
     * @param deathText the death text to display, null to remove
     */
    // Début d'une méthode/d'un bloc
    public void setDeathText(@Nullable Component deathText) {
        // Accès à l'objet courant/parent
        this.deathText = deathText;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the message sent to chat.
     *
     * @return the death chat message
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Component getChatMessage() {
        // Renvoie une valeur à l'appelant
        return chatMessage;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the text sent in chat
     *
     * @param chatMessage the death message to send, null to remove
     */
    // Début d'une méthode/d'un bloc
    public void setChatMessage(@Nullable Component chatMessage) {
        // Accès à l'objet courant/parent
        this.chatMessage = chatMessage;
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
