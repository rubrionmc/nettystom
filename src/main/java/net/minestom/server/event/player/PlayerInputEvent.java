// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;

/**
 * Called when a player's input state changes.
 * This is raw input data and does not take into account any game mechanics.
 * <br>
 * For example, this event may say a player has their jump key held down
 * even if they are in a situation where they can not actually jump.
 */
// Déclaration de type (classe/interface/enum/record)
public final class PlayerInputEvent implements PlayerInstanceEvent {

    // Instruction de code
    private final Player player;

    // Instruction de code
    private final boolean oldForward;
    // Instruction de code
    private final boolean oldBackward;
    // Instruction de code
    private final boolean oldLeft;
    // Instruction de code
    private final boolean oldRight;
    // Instruction de code
    private final boolean oldJump;
    // Instruction de code
    private final boolean oldShift;
    // Instruction de code
    private final boolean oldSprint;

    // Début d'une méthode/d'un bloc
    public PlayerInputEvent(Player player, boolean oldForward, boolean oldBackward, boolean oldLeft, boolean oldRight, boolean oldJump, boolean oldShift, boolean oldSprint) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.oldForward = oldForward;
        // Accès à l'objet courant/parent
        this.oldBackward = oldBackward;
        // Accès à l'objet courant/parent
        this.oldLeft = oldLeft;
        // Accès à l'objet courant/parent
        this.oldRight = oldRight;
        // Accès à l'objet courant/parent
        this.oldJump = oldJump;
        // Accès à l'objet courant/parent
        this.oldShift = oldShift;
        // Accès à l'objet courant/parent
        this.oldSprint = oldSprint;
        // Accès à l'objet courant/parent
        super();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Player getPlayer() {
        // Renvoie une valeur à l'appelant
        return this.player;
    // Fin d'un bloc/d'une expression
    }

    // Movement keys

    /**
     * @return true if the player is currently holding the forward key (typically the 'W' key).
     */
    // Début d'une méthode/d'un bloc
    public boolean isHoldingForwardKey() {
        // Renvoie une valeur à l'appelant
        return this.player.inputs().forward();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return true if the player has just pressed the forward key (typically the 'W' key).
     */
    // Début d'une méthode/d'un bloc
    public boolean hasPressedForwardKey() {
        // Renvoie une valeur à l'appelant
        return !this.oldForward && this.player.inputs().forward();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return true if the player has just released the forward key (typically the 'W' key).
     */
    // Début d'une méthode/d'un bloc
    public boolean hasReleasedForwardKey() {
        // Renvoie une valeur à l'appelant
        return this.oldForward && !this.player.inputs().forward();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return true if the player is currently holding the backward key (typically the 'S' key).
     */
    // Début d'une méthode/d'un bloc
    public boolean isHoldingBackwardKey() {
        // Renvoie une valeur à l'appelant
        return this.player.inputs().backward();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return true if the player has just pressed the backward key (typically the 'S' key).
     */
    // Début d'une méthode/d'un bloc
    public boolean hasPressedBackwardKey() {
        // Renvoie une valeur à l'appelant
        return !this.oldBackward && this.player.inputs().backward();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return true if the player has just released the backward key (typically the 'S' key).
     */
    // Début d'une méthode/d'un bloc
    public boolean hasReleasedBackwardKey() {
        // Renvoie une valeur à l'appelant
        return this.oldBackward && !this.player.inputs().backward();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return true if the player is currently holding the left key (typically the 'A' key).
     */
    // Début d'une méthode/d'un bloc
    public boolean isHoldingLeftKey() {
        // Renvoie une valeur à l'appelant
        return this.player.inputs().left();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return true if the player has just pressed the left key (typically the 'A' key).
     */
    // Début d'une méthode/d'un bloc
    public boolean hasPressedLeftKey() {
        // Renvoie une valeur à l'appelant
        return !this.oldLeft && this.player.inputs().left();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return true if the player has just released the left key (typically the 'A' key).
     */
    // Début d'une méthode/d'un bloc
    public boolean hasReleasedLeftKey() {
        // Renvoie une valeur à l'appelant
        return this.oldLeft && !this.player.inputs().left();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return true if the player is currently holding the right key (typically the 'D' key).
     */
    // Début d'une méthode/d'un bloc
    public boolean isHoldingRightKey() {
        // Renvoie une valeur à l'appelant
        return this.player.inputs().right();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return true if the player has just pressed the right key (typically the 'D' key).
     */
    // Début d'une méthode/d'un bloc
    public boolean hasPressedRightKey() {
        // Renvoie une valeur à l'appelant
        return !this.oldRight && this.player.inputs().right();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return true if the player has just released the right key (typically the 'D' key).
     */
    // Début d'une méthode/d'un bloc
    public boolean hasReleasedRightKey() {
        // Renvoie une valeur à l'appelant
        return this.oldRight && !this.player.inputs().right();
    // Fin d'un bloc/d'une expression
    }

    // Action Keys

    /**
     * @return true if the player is currently holding the jump key (typically the spacebar).
     * @apiNote If the player has auto-jump enabled, for 1 tick this will return true even if the player is not actually holding the jump key but may continue if they start holding it themselves.
     */
    // Début d'une méthode/d'un bloc
    public boolean isHoldingJumpKey() {
        // Renvoie une valeur à l'appelant
        return this.player.inputs().jump();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return true if the player has just pressed the jump key (typically the spacebar).
     * @apiNote If the player has auto-jump enabled, for 1 tick this will return true even if the player did not actually press the jump key.
     */
    // Début d'une méthode/d'un bloc
    public boolean hasPressedJumpKey() {
        // Renvoie une valeur à l'appelant
        return !this.oldJump && this.player.inputs().jump();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return true if the player has just released the jump key (typically the spacebar).
     * @apiNote If the player has auto-jump enabled, for 1 tick after auto-jump triggers if the player does not start holding the key themselves this will return true.
     */
    // Début d'une méthode/d'un bloc
    public boolean hasReleasedJumpKey() {
        // Renvoie une valeur à l'appelant
        return this.oldJump && !this.player.inputs().jump();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return true if the player is currently holding the shift key (typically the left shift key).
     */
    // Début d'une méthode/d'un bloc
    public boolean isHoldingShiftKey() {
        // Renvoie une valeur à l'appelant
        return this.player.inputs().shift();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return true if the player has just pressed the shift key (typically the left shift key).
     */
    // Début d'une méthode/d'un bloc
    public boolean hasPressedShiftKey() {
        // Renvoie une valeur à l'appelant
        return !this.oldShift && this.player.inputs().shift();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return true if the player has just released the shift key (typically the left shift key).
     */
    // Début d'une méthode/d'un bloc
    public boolean hasReleasedShiftKey() {
        // Renvoie une valeur à l'appelant
        return this.oldShift && !this.player.inputs().shift();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return true if the player is currently holding the sprint key (typically the left control key).
     * @apiNote This method only reports the state of the sprint key itself, not other ways to start sprinting such as double-tapping the forward key.
     */
    // Début d'une méthode/d'un bloc
    public boolean isHoldingSprintKey() {
        // Renvoie une valeur à l'appelant
        return this.player.inputs().sprint();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return true if the player has just pressed the sprint key (typically the left control key).
     * @apiNote This method only reports the state of the sprint key itself, not other ways to start sprinting such as double-tapping the forward key.
     */
    // Début d'une méthode/d'un bloc
    public boolean hasPressedSprintKey() {
        // Renvoie une valeur à l'appelant
        return !this.oldSprint && this.player.inputs().sprint();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return true if the player has just released the sprint key (typically the left control key).
     * @apiNote This method only reports the state of the sprint key itself, not other ways to start sprinting such as double-tapping the forward key.
     */
    // Début d'une méthode/d'un bloc
    public boolean hasReleasedSprintKey() {
        // Renvoie une valeur à l'appelant
        return this.oldSprint && !this.player.inputs().sprint();
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
