// Déclaration du paquet de ce fichier
package net.minestom.server.snapshot;

// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.tag.TagReadable;
// Import d'une classe nécessaire
import net.minestom.server.world.biome.Biome;

// Import d'une classe nécessaire
import java.util.Collection;

// Déclaration de type (classe/interface/enum/record)
public sealed interface ChunkSnapshot extends Snapshot, Block.Getter, Biome.Getter, TagReadable
        // Début d'une méthode/d'un bloc
        permits SnapshotImpl.Chunk {
    // Appelle une méthode
    int chunkX();

    // Appelle une méthode
    int chunkZ();

    // Appelle une méthode
    InstanceSnapshot instance();

    // Appelle une méthode
    Collection<EntitySnapshot> entities();
// Fin d'un bloc/d'une expression
}
