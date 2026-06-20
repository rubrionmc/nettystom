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
import net.minestom.server.event.trait.*;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;

/**
 * Called when a player interacts with a block (right-click).
 * This is also called when a block is placed.
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerBlockInteractEvent implements PlayerInstanceEvent, BlockEvent, CancellableEvent {

    // Instruction de code
    private final Player player;
    // Instruction de code
    private final PlayerHand hand;
    // Instruction de code
    private final Instance instance;
    // Instruction de code
    private final Block block;
    // Instruction de code
    private final BlockVec blockPosition;
    // Instruction de code
    private final Point cursorPosition;
    // Instruction de code
    private final BlockFace blockFace;

    /**
     * Does this interaction block the normal item use?
     * True for containers which open an inventory instead of letting blocks be placed
     */
    // Instruction de code
    private boolean blocksItemUse;

    // Instruction de code
    private boolean cancelled;

    // Instruction de code
    public PlayerBlockInteractEvent(Player player, PlayerHand hand, Instance instance,
                                    // Instruction de code
                                    Block block, BlockVec blockPosition, Point cursorPosition,
                                    // Début d'une méthode/d'un bloc
                                    BlockFace blockFace) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.hand = hand;
        // Accès à l'objet courant/parent
        this.instance = instance;
        // Accès à l'objet courant/parent
        this.block = block;
        // Accès à l'objet courant/parent
        this.blockPosition = blockPosition;
        // Accès à l'objet courant/parent
        this.cursorPosition = cursorPosition;
        // Accès à l'objet courant/parent
        this.blockFace = blockFace;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the event should block the item use.
     *
     * @return true if the item use is blocked, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean isBlockingItemUse() {
        // Renvoie une valeur à l'appelant
        return blocksItemUse;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the blocking item use state of this event
     * Note: If this is true, then no {@link PlayerUseItemOnBlockEvent} will be fired.
     * @param blocks - true to block item interactions, false to not block
     */
    // Début d'une méthode/d'un bloc
    public void setBlockingItemUse(boolean blocks) {
        // Accès à l'objet courant/parent
        this.blocksItemUse = blocks;
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

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Block getBlock() {
        // Renvoie une valeur à l'appelant
        return block;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the position of the interacted block.
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
     * Gets the cursor position of the interacted block
     * @return the cursor position of the interaction
     */
    // Instruction de code
    public Point getCursorPosition() { return cursorPosition; }

    /**
     * Gets the hand used for the interaction.
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
     * Gets the block face.
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
