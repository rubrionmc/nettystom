// Déclaration du paquet de ce fichier
package net.minestom.server.snapshot;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.CoordConversion;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.tag.TagReadable;
// Import d'une classe nécessaire
import net.minestom.server.world.DimensionType;
// Import d'une classe nécessaire
import net.minestom.server.world.biome.Biome;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
public sealed interface InstanceSnapshot extends Snapshot, Block.Getter, Biome.Getter, TagReadable
        // Début d'une méthode/d'un bloc
        permits SnapshotImpl.Instance {
    // Appelle une méthode
    RegistryKey<DimensionType> dimensionType();

    // Appelle une méthode
    long worldAge();

    // Appelle une méthode
    long time();

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default @UnknownNullability Block getBlock(int x, int y, int z, Condition condition) {
        // Appelle une méthode
        ChunkSnapshot chunk = chunk(CoordConversion.globalToChunk(x), CoordConversion.globalToChunk(z));
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(chunk).getBlock(x, y, z, condition);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default RegistryKey<Biome> getBiome(int x, int y, int z) {
        // Appelle une méthode
        ChunkSnapshot chunk = chunk(CoordConversion.globalToChunk(x), CoordConversion.globalToChunk(z));
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(chunk).getBiome(x, y, z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable ChunkSnapshot chunk(int chunkX, int chunkZ);

    // Début d'une méthode/d'un bloc
    default @Nullable ChunkSnapshot chunkAt(Point point) {
        // Renvoie une valeur à l'appelant
        return chunk(point.chunkX(), point.chunkZ());
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    Collection<ChunkSnapshot> chunks();

    // Appelle une méthode
    Collection<EntitySnapshot> entities();

    // Appelle une méthode
    ServerSnapshot server();
// Fin d'un bloc/d'une expression
}
