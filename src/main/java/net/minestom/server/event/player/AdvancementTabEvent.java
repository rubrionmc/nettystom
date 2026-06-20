// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.advancements.AdvancementAction;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;

/**
 * Called when a {@link Player} opens the advancement screens or switch the tab
 * and when he closes the screen.
 */
// Type declaration (class/interface/enum/record)
public class AdvancementTabEvent implements PlayerInstanceEvent {

    // Code statement
    private final Player player;
    // Code statement
    private final AdvancementAction action;
    // Code statement
    private final String tabId;

    // Start of a method/block
    public AdvancementTabEvent(Player player, AdvancementAction action, String tabId) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.action = action;
        // Access to the current/parent object
        this.tabId = tabId;
    // End of a block/expression
    }

    /**
     * Gets the action.
     *
     * @return the action
     */
    // Start of a method/block
    public AdvancementAction getAction() {
        // Returns a value to the caller
        return action;
    // End of a block/expression
    }

    /**
     * Gets the tab id.
     * <p>
     * Not null ony if {@link #getAction()} is equal to {@link AdvancementAction#OPENED_TAB}.
     *
     * @return the tab id
     */
    // Start of a method/block
    public String getTabId() {
        // Returns a value to the caller
        return tabId;
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
