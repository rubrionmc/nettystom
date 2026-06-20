// Déclaration du paquet de ce fichier
package net.minestom.server.event.entity.projectile;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;

// Déclaration de type (classe/interface/enum/record)
public final class ProjectileCollideWithBlockEvent extends ProjectileCollideEvent {

    // Instruction de code
    private final Block block;

    // Instruction de code
    public ProjectileCollideWithBlockEvent(
            // Instruction de code
            Entity projectile,
            // Instruction de code
            Pos position,
            // Instruction de code
            Block block
    // Début d'une méthode/d'un bloc
    ) {
        // Accès à l'objet courant/parent
        super(projectile, position);
        // Accès à l'objet courant/parent
        this.block = block;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Block getBlock() {
        // Renvoie une valeur à l'appelant
        return block;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
