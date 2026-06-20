// Package declaration for this file
package net.minestom.server.instance.light;

// Import of a required class
import net.minestom.server.coordinate.BlockVec;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import net.minestom.server.instance.palette.Palette;
// Import of a required class
import net.minestom.server.utils.Direction;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Set;

// Type declaration (class/interface/enum/record)
public interface Light {
    // Start of a method/block
    static Light sky() {
        // Returns a value to the caller
        return new SkyLight();
    // End of a block/expression
    }

    // Start of a method/block
    static Light block() {
        // Returns a value to the caller
        return new BlockLight();
    // End of a block/expression
    }

    // Calls a method
    boolean requiresSend();

    // Annotation for the following element
    @ApiStatus.Internal
    // Calls a method
    byte[] array();

    // Calls a method
    void flip();

    // Calls a method
    int getLevel(int x, int y, int z);

    // Calls a method
    void invalidate();

    // Calls a method
    boolean requiresUpdate();

    // Calls a method
    void set(byte[] copyArray);

    // Annotation for the following element
    @ApiStatus.Internal
    // Code statement
    Set<Point> calculateInternal(Palette blockPalette,
                                 // Code statement
                                 int chunkX, int chunkY, int chunkZ,
                                 // Code statement
                                 int[] heightmap, int maxY,
                                 // Code statement
                                 LightLookup lightLookup);

    // Annotation for the following element
    @ApiStatus.Internal
    // Code statement
    Set<Point> calculateExternal(Palette blockPalette,
                                 // Code statement
                                 Point[] neighbors,
                                 // Code statement
                                 LightLookup lightLookup,
                                 // Code statement
                                 PaletteLookup paletteLookup);

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static Point[] getNeighbors(Chunk chunk, int sectionY) {
        // Calls a method
        final int chunkX = chunk.getChunkX(), chunkZ = chunk.getChunkZ();

        // Assigns a value
        Point[] links = new BlockVec[LightCompute.DIRECTIONS.length];
        // Loop: repeats a block
        for (Direction direction : LightCompute.DIRECTIONS) {
            // Calls a method
            final int x = chunkX + direction.normalX();
            // Calls a method
            final int z = chunkZ + direction.normalZ();
            // Calls a method
            final int y = sectionY + direction.normalY();

            // Calls a method
            Chunk foundChunk = chunk.getInstance().getChunk(x, z);
            // Branch: checks a condition
            if (foundChunk == null) continue;
            // Branch: checks a condition
            if (y - foundChunk.getMinSection() > foundChunk.getMaxSection() || y - foundChunk.getMinSection() < 0)
                // Continues to the next loop iteration
                continue;

            // Calls a method
            links[direction.ordinal()] = new BlockVec(foundChunk.getChunkX(), y, foundChunk.getChunkZ());
        // End of a block/expression
        }
        // Returns a value to the caller
        return links;
    // End of a block/expression
    }

    // Annotation for the following element
    @FunctionalInterface
    // Type declaration (class/interface/enum/record)
    interface LightLookup {
        // Annotation for the following element
        @Nullable Light light(int x, int y, int z);
    // End of a block/expression
    }

    // Annotation for the following element
    @FunctionalInterface
    // Type declaration (class/interface/enum/record)
    interface PaletteLookup {
        // Annotation for the following element
        @Nullable Palette palette(int x, int y, int z);
    // End of a block/expression
    }
// End of a block/expression
}
