// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.other;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.EntityMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.ObjectDataProvider;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;

// Déclaration de type (classe/interface/enum/record)
public class FallingBlockMeta extends EntityMeta implements ObjectDataProvider {
    // Affecte une valeur
    private Block block = Block.STONE;

    // Début d'une méthode/d'un bloc
    public FallingBlockMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Point getSpawnPosition() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.FallingBlock.SPAWN_POSITION);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setSpawnPosition(Point value) {
        // Appelle une méthode
        metadata.set(MetadataDef.FallingBlock.SPAWN_POSITION, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Block getBlock() {
        // Renvoie une valeur à l'appelant
        return block;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets which block to display.
     * This is possible only before spawn packet is sent.
     *
     * @param block which block to display.
     */
    // Début d'une méthode/d'un bloc
    public void setBlock(Block block) {
        // Accès à l'objet courant/parent
        this.block = block;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int getObjectData() {
        // Renvoie une valeur à l'appelant
        return block.stateId();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean requiresVelocityPacketAtSpawn() {
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
