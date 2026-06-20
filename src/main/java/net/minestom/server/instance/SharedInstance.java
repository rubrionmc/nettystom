// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockHandler;
// Import d'une classe nécessaire
import net.minestom.server.instance.generator.Generator;
// Import d'une classe nécessaire
import net.minestom.server.utils.chunk.ChunkSupplier;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.UUID;
// Import d'une classe nécessaire
import java.util.concurrent.CompletableFuture;

/**
 * The {@link SharedInstance} is an instance that shares the same chunks as its linked {@link InstanceContainer},
 * entities are separated.
 */
// Déclaration de type (classe/interface/enum/record)
public class SharedInstance extends Instance {
    // Instruction de code
    private final InstanceContainer instanceContainer;

    // Début d'une méthode/d'un bloc
    public SharedInstance(UUID uuid, InstanceContainer instanceContainer) {
        // Accès à l'objet courant/parent
        super(uuid, instanceContainer.getDimensionType());
        // Accès à l'objet courant/parent
        this.instanceContainer = instanceContainer;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setBlock(int x, int y, int z, Block block, boolean doBlockUpdates) {
        // Accès à l'objet courant/parent
        this.instanceContainer.setBlock(x, y, z, block, doBlockUpdates);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean placeBlock(BlockHandler.Placement placement, boolean doBlockUpdates) {
        // Renvoie une valeur à l'appelant
        return instanceContainer.placeBlock(placement, doBlockUpdates);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean breakBlock(Player player, Point blockPosition, BlockFace blockFace, boolean doBlockUpdates) {
        // Renvoie une valeur à l'appelant
        return instanceContainer.breakBlock(player, blockPosition, blockFace, doBlockUpdates);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public CompletableFuture<Chunk> loadChunk(int chunkX, int chunkZ) {
        // Renvoie une valeur à l'appelant
        return instanceContainer.loadChunk(chunkX, chunkZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public CompletableFuture<Chunk> loadOptionalChunk(int chunkX, int chunkZ) {
        // Renvoie une valeur à l'appelant
        return instanceContainer.loadOptionalChunk(chunkX, chunkZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void unloadChunk(Chunk chunk) {
        // Appelle une méthode
        instanceContainer.unloadChunk(chunk);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Chunk getChunk(int chunkX, int chunkZ) {
        // Renvoie une valeur à l'appelant
        return instanceContainer.getChunk(chunkX, chunkZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public CompletableFuture<Void> saveInstance() {
        // Renvoie une valeur à l'appelant
        return instanceContainer.saveInstance();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public CompletableFuture<Void> saveChunkToStorage(Chunk chunk) {
        // Renvoie une valeur à l'appelant
        return instanceContainer.saveChunkToStorage(chunk);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public CompletableFuture<Void> saveChunksToStorage() {
        // Renvoie une valeur à l'appelant
        return instanceContainer.saveChunksToStorage();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setChunkSupplier(ChunkSupplier chunkSupplier) {
        // Appelle une méthode
        instanceContainer.setChunkSupplier(chunkSupplier);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ChunkSupplier getChunkSupplier() {
        // Renvoie une valeur à l'appelant
        return instanceContainer.getChunkSupplier();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable Generator generator() {
        // Renvoie une valeur à l'appelant
        return instanceContainer.generator();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setGenerator(@Nullable Generator generator) {
        // Appelle une méthode
        instanceContainer.setGenerator(generator);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public CompletableFuture<Void> generateChunk(int chunkX, int chunkZ, Generator generator) {
        // Renvoie une valeur à l'appelant
        return instanceContainer.generateChunk(chunkX, chunkZ, generator);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<Chunk> getChunks() {
        // Renvoie une valeur à l'appelant
        return instanceContainer.getChunks();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void enableAutoChunkLoad(boolean enable) {
        // Appelle une méthode
        instanceContainer.enableAutoChunkLoad(enable);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean hasEnabledAutoChunkLoad() {
        // Renvoie une valeur à l'appelant
        return instanceContainer.hasEnabledAutoChunkLoad();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isInVoid(Point point) {
        // Renvoie une valeur à l'appelant
        return instanceContainer.isInVoid(point);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the {@link InstanceContainer} from where this instance takes its chunks from.
     *
     * @return the associated {@link InstanceContainer}
     */
    // Début d'une méthode/d'un bloc
    public InstanceContainer getInstanceContainer() {
        // Renvoie une valeur à l'appelant
        return instanceContainer;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if two instances share the same chunks.
     *
     * @param instance1 the first instance
     * @param instance2 the second instance
     * @return true if the two instances share the same chunks
     */
    // Début d'une méthode/d'un bloc
    public static boolean areLinked(Instance instance1, Instance instance2) {
        // SharedInstance check
        // Embranchement : vérifie une condition
        if (instance1 instanceof InstanceContainer && instance2 instanceof SharedInstance) {
            // Renvoie une valeur à l'appelant
            return ((SharedInstance) instance2).getInstanceContainer().equals(instance1);
        // Embranchement : vérifie une condition
        } else if (instance2 instanceof InstanceContainer && instance1 instanceof SharedInstance) {
            // Renvoie une valeur à l'appelant
            return ((SharedInstance) instance1).getInstanceContainer().equals(instance2);
        // Embranchement : vérifie une condition
        } else if (instance1 instanceof SharedInstance && instance2 instanceof SharedInstance) {
            // Appelle une méthode
            final InstanceContainer container1 = ((SharedInstance) instance1).getInstanceContainer();
            // Appelle une méthode
            final InstanceContainer container2 = ((SharedInstance) instance2).getInstanceContainer();
            // Renvoie une valeur à l'appelant
            return container1.equals(container2);
        // Fin d'un bloc/d'une expression
        }

        // InstanceContainer check (copied from)
        // Embranchement : vérifie une condition
        if (instance1 instanceof InstanceContainer container1 && instance2 instanceof InstanceContainer container2) {
            // Embranchement : vérifie une condition
            if (container1.getSrcInstance() != null) {
                // Renvoie une valeur à l'appelant
                return container1.getSrcInstance().equals(container2)
                        // Appelle une méthode
                        && container1.getLastBlockChangeTime() == container2.getLastBlockChangeTime();
            // Embranchement : vérifie une condition
            } else if (container2.getSrcInstance() != null) {
                // Renvoie une valeur à l'appelant
                return container2.getSrcInstance().equals(container1)
                        // Appelle une méthode
                        && container2.getLastBlockChangeTime() == container1.getLastBlockChangeTime();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
