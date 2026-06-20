// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;

/**
 * Called every time a player send a message starting by '/'.
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerCommandEvent implements PlayerInstanceEvent, CancellableEvent {

    // Instruction de code
    private final Player player;
    // Instruction de code
    private String command;

    // Instruction de code
    private boolean cancelled;

    // Début d'une méthode/d'un bloc
    public PlayerCommandEvent(Player player, String command) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.command = command;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the command used (command name + arguments).
     *
     * @return the command that the player wants to execute
     */
    // Début d'une méthode/d'un bloc
    public String getCommand() {
        // Renvoie une valeur à l'appelant
        return command;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the command to execute.
     *
     * @param command the new command
     */
    // Début d'une méthode/d'un bloc
    public void setCommand(String command) {
        // Accès à l'objet courant/parent
        this.command = command;
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
// Fin d'un bloc/d'une expression
}
