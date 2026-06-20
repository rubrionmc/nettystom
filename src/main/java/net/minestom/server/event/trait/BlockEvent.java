// Déclaration du paquet de ce fichier
package net.minestom.server.event.trait;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.BlockVec;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents an event related to a {@link Block} happening in an {@link Instance}.
 */
// Déclaration de type (classe/interface/enum/record)
public interface BlockEvent extends InstanceEvent {
    // Appelle une méthode
    Block getBlock();

    // Appelle une méthode
    BlockVec getBlockPosition();
// Fin d'un bloc/d'une expression
}
