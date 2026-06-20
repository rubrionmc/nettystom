// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;

/**
 * Called after a chunk being unload to a certain player.
 * <p>
 * Could be used to unload the chunk internally in order to save memory.
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerChunkUnloadEvent implements PlayerInstanceEvent {

    // Instruction de code
    private final Player player;
    // Instruction de code
    private final int chunkX, chunkZ;

    // Début d'une méthode/d'un bloc
    public PlayerChunkUnloadEvent(Player player, int chunkX, int chunkZ) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.chunkX = chunkX;
        // Accès à l'objet courant/parent
        this.chunkZ = chunkZ;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the chunk X.
     *
     * @return the chunk X
     */
    // Début d'une méthode/d'un bloc
    public int getChunkX() {
        // Renvoie une valeur à l'appelant
        return chunkX;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the chunk Z.
     *
     * @return the chunk Z
     */
    // Début d'une méthode/d'un bloc
    public int getChunkZ() {
        // Renvoie une valeur à l'appelant
        return chunkZ;
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
