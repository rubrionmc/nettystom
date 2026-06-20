// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.advancements.AdvancementAction;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;

/**
 * Called when a {@link Player} opens the advancement screens or switch the tab
 * and when he closes the screen.
 */
// Déclaration de type (classe/interface/enum/record)
public class AdvancementTabEvent implements PlayerInstanceEvent {

    // Instruction de code
    private final Player player;
    // Instruction de code
    private final AdvancementAction action;
    // Instruction de code
    private final String tabId;

    // Début d'une méthode/d'un bloc
    public AdvancementTabEvent(Player player, AdvancementAction action, String tabId) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.action = action;
        // Accès à l'objet courant/parent
        this.tabId = tabId;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the action.
     *
     * @return the action
     */
    // Début d'une méthode/d'un bloc
    public AdvancementAction getAction() {
        // Renvoie une valeur à l'appelant
        return action;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the tab id.
     * <p>
     * Not null ony if {@link #getAction()} is equal to {@link AdvancementAction#OPENED_TAB}.
     *
     * @return the tab id
     */
    // Début d'une méthode/d'un bloc
    public String getTabId() {
        // Renvoie une valeur à l'appelant
        return tabId;
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
