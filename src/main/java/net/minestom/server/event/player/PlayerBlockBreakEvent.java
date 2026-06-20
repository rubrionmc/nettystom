// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.BlockVec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.BlockEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;

// Déclaration de type (classe/interface/enum/record)
public class PlayerBlockBreakEvent implements PlayerInstanceEvent, BlockEvent, CancellableEvent {

    // Instruction de code
    private final Player player;
    // Instruction de code
    private final Block block;
    // Instruction de code
    private Block resultBlock;
    // Instruction de code
    private final BlockVec blockPosition;
    // Instruction de code
    private final BlockFace blockFace;

    // Instruction de code
    private boolean cancelled;

    // Instruction de code
    public PlayerBlockBreakEvent(Player player,
                                 // Instruction de code
                                 Block block, Block resultBlock, BlockVec blockPosition,
                                 // Début d'une méthode/d'un bloc
                                 BlockFace blockFace) {
        // Accès à l'objet courant/parent
        this.player = player;

        // Accès à l'objet courant/parent
        this.block = block;
        // Accès à l'objet courant/parent
        this.resultBlock = resultBlock;
        // Accès à l'objet courant/parent
        this.blockPosition = blockPosition;
        // Accès à l'objet courant/parent
        this.blockFace = blockFace;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the block to break
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
     * Gets the block which will replace {@link #getBlock()}.
     *
     * @return the result block
     */
    // Début d'une méthode/d'un bloc
    public Block getResultBlock() {
        // Renvoie une valeur à l'appelant
        return resultBlock;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the face at which the block was broken
     *
     * @return the block face
     */
    // Début d'une méthode/d'un bloc
    public BlockFace getBlockFace() {
        // Renvoie une valeur à l'appelant
        return blockFace;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the result of the event.
     *
     * @param resultBlock the new block
     */
    // Début d'une méthode/d'un bloc
    public void setResultBlock(Block resultBlock) {
        // Accès à l'objet courant/parent
        this.resultBlock = resultBlock;
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
