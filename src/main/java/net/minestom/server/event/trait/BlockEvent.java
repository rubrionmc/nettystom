// Déclaration du paquet de ce fichier
package net.minestom.server.event.trait;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.BlockVec;
// Import d'une classe nécessaire
import net.minestom.server.event.Event;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;

// Déclaration de type (classe/interface/enum/record)
public interface BlockEvent extends Event {
    // Appelle une méthode
    Block getBlock();

    // Appelle une méthode
    BlockVec getBlockPosition();
// Fin d'un bloc/d'une expression
}
