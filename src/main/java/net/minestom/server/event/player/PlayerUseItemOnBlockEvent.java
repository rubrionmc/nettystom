// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.ItemEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;

/**
 * Used when a player is clicking on a block with an item (but is not a block in item form).
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerUseItemOnBlockEvent implements PlayerInstanceEvent, ItemEvent {

    // Instruction de code
    private final Player player;
    // Instruction de code
    private final PlayerHand hand;
    // Instruction de code
    private final ItemStack itemStack;
    // Instruction de code
    private final Point position;
    // Instruction de code
    private final Point cursorPosition;
    // Instruction de code
    private final BlockFace blockFace;

    // Instruction de code
    public PlayerUseItemOnBlockEvent(Player player, PlayerHand hand,
                                     // Instruction de code
                                     ItemStack itemStack,
                                     // Instruction de code
                                     Point position, Point cursorPosition,
                                     // Début d'une méthode/d'un bloc
                                     BlockFace blockFace) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.hand = hand;
        // Accès à l'objet courant/parent
        this.itemStack = itemStack;
        // Accès à l'objet courant/parent
        this.position = position;
        // Accès à l'objet courant/parent
        this.cursorPosition = cursorPosition;
        // Accès à l'objet courant/parent
        this.blockFace = blockFace;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the position of the interacted block.
     *
     * @return the block position
     */
    // Début d'une méthode/d'un bloc
    public Point getPosition() {
        // Renvoie une valeur à l'appelant
        return position;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the cursor position of the interacted block
     *
     * @return the cursor position of the interaction
     */
    // Instruction de code
    public Point getCursorPosition() { return cursorPosition; }

    /**
     * Gets which face the player has interacted with.
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
     * Gets which hand the player used to interact with the block.
     *
     * @return the hand
     */
    // Début d'une méthode/d'un bloc
    public PlayerHand getHand() {
        // Renvoie une valeur à l'appelant
        return hand;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets with which item the player has interacted with the block.
     *
     * @return the item
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ItemStack getItemStack() {
        // Renvoie une valeur à l'appelant
        return itemStack;
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
