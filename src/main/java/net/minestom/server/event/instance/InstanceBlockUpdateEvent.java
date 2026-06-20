// Déclaration du paquet de ce fichier
package net.minestom.server.event.instance;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.BlockVec;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.BlockEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;

/**
 * Called when a block in an instance is updated.
 * <p>
 * This event is triggered when a block's state changes from its instance.
 * If you wish to listen to all block updates, must be used in conjunction with {@link InstanceSectionInvalidateEvent}
 */
// Déclaration de type (classe/interface/enum/record)
public class InstanceBlockUpdateEvent implements BlockEvent {
    // Instruction de code
    private final Instance instance;
    // Instruction de code
    private final BlockVec blockPosition;
    // Instruction de code
    private final Block block;

    // Début d'une méthode/d'un bloc
    public InstanceBlockUpdateEvent(Instance instance, BlockVec blockPosition, Block block) {
        // Accès à l'objet courant/parent
        this.instance = instance;
        // Accès à l'objet courant/parent
        this.blockPosition = blockPosition;
        // Accès à l'objet courant/parent
        this.block = block;
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
    public Instance getInstance() {
        // Renvoie une valeur à l'appelant
        return instance;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
