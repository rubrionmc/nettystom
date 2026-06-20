// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;

/**
 * Called when a player's input state changes.
 * This is raw input data and does not take into account any game mechanics.
 * <br>
 * For example, this event may say a player has their jump key held down
 * even if they are in a situation where they can not actually jump.
 */
// Type declaration (class/interface/enum/record)
public final class PlayerInputEvent implements PlayerInstanceEvent {

    // Code statement
    private final Player player;

    // Code statement
    private final boolean oldForward;
    // Code statement
    private final boolean oldBackward;
    // Code statement
    private final boolean oldLeft;
    // Code statement
    private final boolean oldRight;
    // Code statement
    private final boolean oldJump;
    // Code statement
    private final boolean oldShift;
    // Code statement
    private final boolean oldSprint;

    // Start of a method/block
    public PlayerInputEvent(Player player, boolean oldForward, boolean oldBackward, boolean oldLeft, boolean oldRight, boolean oldJump, boolean oldShift, boolean oldSprint) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.oldForward = oldForward;
        // Access to the current/parent object
        this.oldBackward = oldBackward;
        // Access to the current/parent object
        this.oldLeft = oldLeft;
        // Access to the current/parent object
        this.oldRight = oldRight;
        // Access to the current/parent object
        this.oldJump = oldJump;
        // Access to the current/parent object
        this.oldShift = oldShift;
        // Access to the current/parent object
        this.oldSprint = oldSprint;
        // Access to the current/parent object
        super();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Player getPlayer() {
        // Returns a value to the caller
        return this.player;
    // End of a block/expression
    }

    // Movement keys

    /**
     * @return true if the player is currently holding the forward key (typically the 'W' key).
     */
    // Start of a method/block
    public boolean isHoldingForwardKey() {
        // Returns a value to the caller
        return this.player.inputs().forward();
    // End of a block/expression
    }

    /**
     * @return true if the player has just pressed the forward key (typically the 'W' key).
     */
    // Start of a method/block
    public boolean hasPressedForwardKey() {
        // Returns a value to the caller
        return !this.oldForward && this.player.inputs().forward();
    // End of a block/expression
    }

    /**
     * @return true if the player has just released the forward key (typically the 'W' key).
     */
    // Start of a method/block
    public boolean hasReleasedForwardKey() {
        // Returns a value to the caller
        return this.oldForward && !this.player.inputs().forward();
    // End of a block/expression
    }

    /**
     * @return true if the player is currently holding the backward key (typically the 'S' key).
     */
    // Start of a method/block
    public boolean isHoldingBackwardKey() {
        // Returns a value to the caller
        return this.player.inputs().backward();
    // End of a block/expression
    }

    /**
     * @return true if the player has just pressed the backward key (typically the 'S' key).
     */
    // Start of a method/block
    public boolean hasPressedBackwardKey() {
        // Returns a value to the caller
        return !this.oldBackward && this.player.inputs().backward();
    // End of a block/expression
    }

    /**
     * @return true if the player has just released the backward key (typically the 'S' key).
     */
    // Start of a method/block
    public boolean hasReleasedBackwardKey() {
        // Returns a value to the caller
        return this.oldBackward && !this.player.inputs().backward();
    // End of a block/expression
    }

    /**
     * @return true if the player is currently holding the left key (typically the 'A' key).
     */
    // Start of a method/block
    public boolean isHoldingLeftKey() {
        // Returns a value to the caller
        return this.player.inputs().left();
    // End of a block/expression
    }

    /**
     * @return true if the player has just pressed the left key (typically the 'A' key).
     */
    // Start of a method/block
    public boolean hasPressedLeftKey() {
        // Returns a value to the caller
        return !this.oldLeft && this.player.inputs().left();
    // End of a block/expression
    }

    /**
     * @return true if the player has just released the left key (typically the 'A' key).
     */
    // Start of a method/block
    public boolean hasReleasedLeftKey() {
        // Returns a value to the caller
        return this.oldLeft && !this.player.inputs().left();
    // End of a block/expression
    }

    /**
     * @return true if the player is currently holding the right key (typically the 'D' key).
     */
    // Start of a method/block
    public boolean isHoldingRightKey() {
        // Returns a value to the caller
        return this.player.inputs().right();
    // End of a block/expression
    }

    /**
     * @return true if the player has just pressed the right key (typically the 'D' key).
     */
    // Start of a method/block
    public boolean hasPressedRightKey() {
        // Returns a value to the caller
        return !this.oldRight && this.player.inputs().right();
    // End of a block/expression
    }

    /**
     * @return true if the player has just released the right key (typically the 'D' key).
     */
    // Start of a method/block
    public boolean hasReleasedRightKey() {
        // Returns a value to the caller
        return this.oldRight && !this.player.inputs().right();
    // End of a block/expression
    }

    // Action Keys

    /**
     * @return true if the player is currently holding the jump key (typically the spacebar).
     * @apiNote If the player has auto-jump enabled, for 1 tick this will return true even if the player is not actually holding the jump key but may continue if they start holding it themselves.
     */
    // Start of a method/block
    public boolean isHoldingJumpKey() {
        // Returns a value to the caller
        return this.player.inputs().jump();
    // End of a block/expression
    }

    /**
     * @return true if the player has just pressed the jump key (typically the spacebar).
     * @apiNote If the player has auto-jump enabled, for 1 tick this will return true even if the player did not actually press the jump key.
     */
    // Start of a method/block
    public boolean hasPressedJumpKey() {
        // Returns a value to the caller
        return !this.oldJump && this.player.inputs().jump();
    // End of a block/expression
    }

    /**
     * @return true if the player has just released the jump key (typically the spacebar).
     * @apiNote If the player has auto-jump enabled, for 1 tick after auto-jump triggers if the player does not start holding the key themselves this will return true.
     */
    // Start of a method/block
    public boolean hasReleasedJumpKey() {
        // Returns a value to the caller
        return this.oldJump && !this.player.inputs().jump();
    // End of a block/expression
    }

    /**
     * @return true if the player is currently holding the shift key (typically the left shift key).
     */
    // Start of a method/block
    public boolean isHoldingShiftKey() {
        // Returns a value to the caller
        return this.player.inputs().shift();
    // End of a block/expression
    }

    /**
     * @return true if the player has just pressed the shift key (typically the left shift key).
     */
    // Start of a method/block
    public boolean hasPressedShiftKey() {
        // Returns a value to the caller
        return !this.oldShift && this.player.inputs().shift();
    // End of a block/expression
    }

    /**
     * @return true if the player has just released the shift key (typically the left shift key).
     */
    // Start of a method/block
    public boolean hasReleasedShiftKey() {
        // Returns a value to the caller
        return this.oldShift && !this.player.inputs().shift();
    // End of a block/expression
    }

    /**
     * @return true if the player is currently holding the sprint key (typically the left control key).
     * @apiNote This method only reports the state of the sprint key itself, not other ways to start sprinting such as double-tapping the forward key.
     */
    // Start of a method/block
    public boolean isHoldingSprintKey() {
        // Returns a value to the caller
        return this.player.inputs().sprint();
    // End of a block/expression
    }

    /**
     * @return true if the player has just pressed the sprint key (typically the left control key).
     * @apiNote This method only reports the state of the sprint key itself, not other ways to start sprinting such as double-tapping the forward key.
     */
    // Start of a method/block
    public boolean hasPressedSprintKey() {
        // Returns a value to the caller
        return !this.oldSprint && this.player.inputs().sprint();
    // End of a block/expression
    }

    /**
     * @return true if the player has just released the sprint key (typically the left control key).
     * @apiNote This method only reports the state of the sprint key itself, not other ways to start sprinting such as double-tapping the forward key.
     */
    // Start of a method/block
    public boolean hasReleasedSprintKey() {
        // Returns a value to the caller
        return this.oldSprint && !this.player.inputs().sprint();
    // End of a block/expression
    }

// End of a block/expression
}
