// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.BlockVec;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
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

/**
 * Called when a player tries placing a block.
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerBlockPlaceEvent implements PlayerInstanceEvent, BlockEvent, CancellableEvent {

    // Instruction de code
    private final Player player;
    // Instruction de code
    private Block block;
    // Instruction de code
    private final BlockFace blockFace;
    // Instruction de code
    private final BlockVec blockPosition;
    // Instruction de code
    private final Point cursorPosition;
    // Instruction de code
    private final PlayerHand hand;

    // Instruction de code
    private boolean consumeBlock;
    // Instruction de code
    private boolean doBlockUpdates;

    // Instruction de code
    private boolean cancelled;

    // Instruction de code
    public PlayerBlockPlaceEvent(Player player, Block block,
                                 // Instruction de code
                                 BlockFace blockFace, BlockVec blockPosition,
                                 // Début d'une méthode/d'un bloc
                                 Point cursorPosition, PlayerHand hand) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.block = block;
        // Accès à l'objet courant/parent
        this.blockFace = blockFace;
        // Accès à l'objet courant/parent
        this.blockPosition = blockPosition;
        // Accès à l'objet courant/parent
        this.cursorPosition = cursorPosition;
        // Accès à l'objet courant/parent
        this.hand = hand;
        // Accès à l'objet courant/parent
        this.consumeBlock = true;
        // Accès à l'objet courant/parent
        this.doBlockUpdates = true;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the block which will be placed.
     *
     * @return the block to place
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
     * Changes the block to be placed.
     *
     * @param block the new block
     */
    // Début d'une méthode/d'un bloc
    public void setBlock(Block block) {
        // Accès à l'objet courant/parent
        this.block = block;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BlockFace getBlockFace() {
        // Renvoie une valeur à l'appelant
        return blockFace;
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

    // Début d'une méthode/d'un bloc
    public Point getCursorPosition() {
        // Renvoie une valeur à l'appelant
        return cursorPosition;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the hand with which the player is trying to place.
     *
     * @return the hand used
     */
    // Début d'une méthode/d'un bloc
    public PlayerHand getHand() {
        // Renvoie une valeur à l'appelant
        return hand;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Should the block be consumed if not cancelled.
     *
     * @param consumeBlock true if the block should be consumer (-1 amount), false otherwise
     */
    // Début d'une méthode/d'un bloc
    public void consumeBlock(boolean consumeBlock) {
        // Accès à l'objet courant/parent
        this.consumeBlock = consumeBlock;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Should the block be consumed if not cancelled.
     *
     * @return true if the block will be consumed, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean doesConsumeBlock() {
        // Renvoie une valeur à l'appelant
        return consumeBlock;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Should the place trigger updates (on self and neighbors)
     * @param doBlockUpdates true if this placement should do block updates
     */
    // Début d'une méthode/d'un bloc
    public void setDoBlockUpdates(boolean doBlockUpdates) {
        // Accès à l'objet courant/parent
        this.doBlockUpdates = doBlockUpdates;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Should the place trigger updates (on self and neighbors)
     * @return true if this placement should do block updates
     */
    // Début d'une méthode/d'un bloc
    public boolean shouldDoBlockUpdates() {
        // Renvoie une valeur à l'appelant
        return doBlockUpdates;
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
