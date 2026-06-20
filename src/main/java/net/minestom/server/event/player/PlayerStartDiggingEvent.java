// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.BlockVec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.*;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;

/**
 * Called when a {@link Player} start digging a block,
 * can be used to forbid the {@link Player} from mining it.
 * <p>
 * Be aware that cancelling this event does not necessary prevent the player from breaking the block
 * (could be because of high latency or a modified client) so cancelling {@link PlayerBlockBreakEvent} is also necessary.
 * Could be fixed in future Minestom version.
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerStartDiggingEvent implements PlayerInstanceEvent, BlockEvent, CancellableEvent {

    // Instruction de code
    private final Player player;
    // Instruction de code
    private final Instance instance;
    // Instruction de code
    private final Block block;
    // Instruction de code
    private final BlockVec blockPosition;
    // Instruction de code
    private final BlockFace blockFace;

    // Instruction de code
    private boolean cancelled;

    // Instruction de code
    public PlayerStartDiggingEvent(Player player, Instance instance, Block block,
                                   // Instruction de code
                                   BlockVec blockPosition,
                                   // Début d'une méthode/d'un bloc
                                   BlockFace blockFace) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.instance = instance;
        // Accès à l'objet courant/parent
        this.block = block;
        // Accès à l'objet courant/parent
        this.blockPosition = blockPosition;
        // Accès à l'objet courant/parent
        this.blockFace = blockFace;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Instance getInstance() {
        // Renvoie une valeur à l'appelant
        return instance;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the block which is being dug.
     *
     * @return the block
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Block getBlock() {
        // Renvoie une valeur à l'appelant
        return block;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the block position.
     *
     * @return the block position
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public BlockVec getBlockPosition() {
        // Renvoie une valeur à l'appelant
        return blockPosition;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the face you are digging
     *
     * @return the block face
     */
    // Début d'une méthode/d'un bloc
    public BlockFace getBlockFace() {
        // Renvoie une valeur à l'appelant
        return blockFace;
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
