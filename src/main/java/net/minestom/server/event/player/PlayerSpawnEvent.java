// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;

/**
 * Called when a new instance is set for a player.
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerSpawnEvent implements PlayerInstanceEvent {
    // Instruction de code
    private final Player player;
    // Instruction de code
    private final Instance spawnInstance;
    // Instruction de code
    private final boolean firstSpawn;

    // Début d'une méthode/d'un bloc
    public PlayerSpawnEvent(Player player, Instance spawnInstance, boolean firstSpawn) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.spawnInstance = spawnInstance;
        // Accès à l'objet courant/parent
        this.firstSpawn = firstSpawn;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the player's new instance.
     *
     * @return the instance
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public Instance getSpawnInstance() {
        // Renvoie une valeur à l'appelant
        return spawnInstance;
    // Fin d'un bloc/d'une expression
    }

    /**
     * 'true' if the player is spawning for the first time. 'false' if this spawn event was triggered by a dimension teleport
     *
     * @return true if this is the first spawn, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean isFirstSpawn() {
        // Renvoie une valeur à l'appelant
        return firstSpawn;
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
