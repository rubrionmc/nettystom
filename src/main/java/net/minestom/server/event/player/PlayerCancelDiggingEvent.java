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
 * Called when a {@link Player} stops digging a block before it is broken
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerCancelDiggingEvent implements PlayerInstanceEvent, BlockEvent {
    // Instruction de code
    private final Player player;
    // Instruction de code
    private final Instance instance;
    // Instruction de code
    private final Block block;
    // Instruction de code
    private final BlockVec blockPosition;

    // Début d'une méthode/d'un bloc
    public PlayerCancelDiggingEvent(Player player, Instance instance, Block block, BlockVec blockPosition) {
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
     * Gets the block which was being dug.
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
