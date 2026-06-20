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
import net.minestom.server.instance.block.Block;

/**
 * Called when a player tries to pick a block (middle-click).
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerPickBlockEvent implements PlayerInstanceEvent, BlockEvent {

    // Instruction de code
    private final Player player;

    // Instruction de code
    private final Block block;
    // Instruction de code
    private final BlockVec blockPosition;
    // Instruction de code
    private final boolean includeData;

    // Instruction de code
    public PlayerPickBlockEvent(Player player, Block block,
                                // Début d'une méthode/d'un bloc
                                BlockVec blockPosition, boolean includeData) {
        // Accès à l'objet courant/parent
        this.player = player;

        // Accès à l'objet courant/parent
        this.block = block;
        // Accès à l'objet courant/parent
        this.blockPosition = blockPosition;
        // Accès à l'objet courant/parent
        this.includeData = includeData;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the block which was picked.
     *
     * @return the block which was picked
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
     * Gets the picked block position.
     *
     * @return the picked block position
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
     * Get if the entity data should be included in the result (control middle-click).
     *
     * @return if the entity data should be included.
     */
    // Début d'une méthode/d'un bloc
    public boolean isIncludeData() {
        // Renvoie une valeur à l'appelant
        return this.includeData;
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
