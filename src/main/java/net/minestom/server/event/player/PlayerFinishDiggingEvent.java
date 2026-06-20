// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.BlockVec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.BlockEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;

/**
 * Called when a {@link Player} successfully finishes digging a block
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerFinishDiggingEvent implements PlayerInstanceEvent, BlockEvent {
    // Instruction de code
    private final Player player;
    // Instruction de code
    private final Instance instance;
    // Instruction de code
    private Block block;
    // Instruction de code
    private final BlockVec blockPosition;

    // Début d'une méthode/d'un bloc
    public PlayerFinishDiggingEvent(Player player, Instance instance, Block block, BlockVec blockPosition) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.instance = instance;
        // Accès à l'objet courant/parent
        this.block = block;
        // Accès à l'objet courant/parent
        this.blockPosition = blockPosition;
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
     * Changes which block was dug
     * <p>
     * This has somewhat odd behavior;
     * If you set it from a previously solid block to a non-solid block
     * then cancel the respective {@link PlayerBlockBreakEvent}
     * it will allow the player to phase through the block and into the floor
     * (only if the player is standing on top of the block)
     *
     * @param block the block to set the result to
     */
    // Début d'une méthode/d'un bloc
    public void setBlock(Block block) {
        // Accès à l'objet courant/parent
        this.block = block;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the block which was dug.
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
