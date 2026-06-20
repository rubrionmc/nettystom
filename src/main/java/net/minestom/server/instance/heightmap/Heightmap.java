// Package declaration for this file
package net.minestom.server.instance.heightmap;

// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.palette.Palette;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.utils.MathUtils;

// Static import of a member
import static net.minestom.server.coordinate.CoordConversion.globalToChunk;
// Static import of a member
import static net.minestom.server.coordinate.CoordConversion.globalToSectionRelative;
// Static import of a member
import static net.minestom.server.instance.Chunk.CHUNK_SIZE_X;
// Static import of a member
import static net.minestom.server.instance.Chunk.CHUNK_SIZE_Z;

// Type declaration (class/interface/enum/record)
public abstract class Heightmap {
    // Type declaration (class/interface/enum/record)
    public enum Type {
        // Code statement
        WORLD_SURFACE_WG,
        // Code statement
        WORLD_SURFACE,
        // Code statement
        OCEAN_FLOOR_WG,
        // Code statement
        OCEAN_FLOOR,
        // Code statement
        MOTION_BLOCKING,
        // Code statement
        MOTION_BLOCKING_NO_LEAVES;

        // Calls a method
        public static final NetworkBuffer.Type<Type> NETWORK_TYPE = NetworkBuffer.Enum(Type.class);
    // End of a block/expression
    }

    // Assigns a value
    private final short[] heights = new short[CHUNK_SIZE_X * CHUNK_SIZE_Z];
    // Code statement
    private final Chunk chunk;
    // Code statement
    private final int minHeight;
    // Assigns a value
    private boolean needsRefresh = true;

    // Start of a method/block
    public Heightmap(Chunk chunk) {
        // Access to the current/parent object
        this.chunk = chunk;
        // Access to the current/parent object
        this.minHeight = chunk.getInstance().getCachedDimensionType().minY() - 1;
    // End of a block/expression
    }

    // Calls a method
    public abstract Type type();

    // Calls a method
    protected abstract boolean checkBlock(Block block);

    // Start of a method/block
    public void refresh(int x, int y, int z, Block block) {
        // Calls a method
        final int height = getHeight(x, z);
        // Branch: checks a condition
        if (checkBlock(block)) {
            // Branch: checks a condition
            if (height < y) setHeightY(x, z, y);
        // Branch: checks a condition
        } else if (y == height) {
            // Calls a method
            refresh(x, z, y - 1);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public void refresh(int startY) {
        // Branch: checks a condition
        if (!needsRefresh) return;
        // Calls a method
        chunk.lockReadLock();
        // Exception handling
        try {
            // Loop: repeats a block
            for (int x = 0; x < CHUNK_SIZE_X; x++) {
                // Loop: repeats a block
                for (int z = 0; z < CHUNK_SIZE_Z; z++) {
                    // Calls a method
                    refresh(x, z, startY);
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Assigns a value
            needsRefresh = false;
        // Start of a method/block
        } finally {
            // Calls a method
            chunk.unlockReadLock();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public void refresh(int x, int z, int startY) {
        // Calls a method
        final int localX = globalToSectionRelative(x);
        // Calls a method
        final int localZ = globalToSectionRelative(z);

        // Assigns a value
        int foundHeight = minHeight;
        // Assigns a value
        int currentY = startY;
        // Loop: repeats a block
        while (currentY > minHeight) {
            // Calls a method
            final int sectionY = globalToChunk(currentY);
            // Branch: checks a condition
            if (sectionY < chunk.getMinSection() || sectionY >= chunk.getMaxSection()) {
                // Assigns a value
                currentY = (sectionY << 4) - 1; // Move to the bottom of the previous section
                // Continues to the next loop iteration
                continue;
            // End of a block/expression
            }

            // Calls a method
            final Palette blockPalette = chunk.getSection(sectionY).blockPalette();
            // Assigns a value
            final int localHeight = blockPalette.height(localX, localZ, (px, py, pz, value) -> {
                // Branch: checks a condition
                if (value == 0) return false;
                // Calls a method
                final Block block = Block.fromStateId(value);
                // Returns a value to the caller
                return block != null && checkBlock(block);
            // End of a block/expression
            });
            // Branch: checks a condition
            if (localHeight >= 0) {
                // Found a matching block, convert local Y back to world Y
                // Calls a method
                foundHeight = (sectionY << 4) + localHeight;
                // Breaks out of the loop/block
                break;
            // End of a block/expression
            }

            // No matching block found in this section, move to the section below
            // Calls a method
            currentY = (sectionY << 4) - 1;
        // End of a block/expression
        }
        // Calls a method
        setHeightY(x, z, foundHeight);
    // End of a block/expression
    }

    // Start of a method/block
    public long[] getNBT() {
        // Calls a method
        final int dimensionHeight = chunk.getInstance().getCachedDimensionType().height();
        // Calls a method
        final int bitsForHeight = MathUtils.bitsToRepresent(dimensionHeight);
        // Returns a value to the caller
        return encode(heights, bitsForHeight);
    // End of a block/expression
    }

    // Start of a method/block
    public void loadFrom(long[] data) {
        // Calls a method
        final int dimensionHeight = chunk.getInstance().getCachedDimensionType().height();
        // Calls a method
        final int bitsPerEntry = MathUtils.bitsToRepresent(dimensionHeight);

        // Assigns a value
        final int entriesPerLong = 64 / bitsPerEntry;

        // Assigns a value
        final int maxPossibleIndexInContainer = entriesPerLong - 1;
        // Calls a method
        final int entryMask = (1 << bitsPerEntry) - 1;

        // Assigns a value
        int containerIndex = 0;
        // Loop: repeats a block
        for (int i = 0; i < heights.length; i++) {
            // Assigns a value
            final int indexInContainer = i % entriesPerLong;
            // Calls a method
            heights[i] = (short) ((int) (data[containerIndex] >> (indexInContainer * bitsPerEntry)) & entryMask);
            // Branch: checks a condition
            if (indexInContainer == maxPossibleIndexInContainer) containerIndex++;
        // End of a block/expression
        }
        // Assigns a value
        needsRefresh = false;
    // End of a block/expression
    }

    // highest breaking block in section
    // Start of a method/block
    public int getHeight(int x, int z) {
        // Branch: checks a condition
        if (needsRefresh) refresh(getHighestBlockSection(chunk));
        // Returns a value to the caller
        return heights[z << 4 | x] + minHeight;
    // End of a block/expression
    }

    // Start of a method/block
    private void setHeightY(int x, int z, int height) {
        // Calls a method
        heights[z << 4 | x] = (short) (height - minHeight);
    // End of a block/expression
    }

    // Start of a method/block
    public static int getHighestBlockSection(Chunk chunk) {
        // Calls a method
        int y = chunk.getInstance().getCachedDimensionType().maxY();
        // Calls a method
        final int sectionsCount = chunk.getMaxSection() - chunk.getMinSection();
        // Loop: repeats a block
        for (int i = 0; i < sectionsCount; i++) {
            // Calls a method
            final int sectionY = chunk.getMaxSection() - i - 1;
            // Calls a method
            final Palette blockPalette = chunk.getSection(sectionY).blockPalette();
            // Branch: checks a condition
            if (blockPalette.count() != 0) break;
            // Code statement
            y -= 16;
        // End of a block/expression
        }
        // Returns a value to the caller
        return y;
    // End of a block/expression
    }

    /**
     * Creates compressed longs array from uncompressed heights array.
     *
     * @param heights      array of heights. Note that for this method it doesn't
     *                     matter what size this array will be.
     *                     But to get correct heights, array must be 256 elements
     *                     long, and at index `i` must be height of (z=i/16,
     *                     x=i%16).
     * @param bitsPerEntry bits that each entry from height will take in `long`
     *                     container.
     * @return array of encoded heights.
     */
    // Start of a method/block
    public static long[] encode(short[] heights, int bitsPerEntry) {
        // Assigns a value
        final int entriesPerLong = 64 / bitsPerEntry;
        // ceil(HeightsCount / entriesPerLong)
        // Calls a method
        final int len = (heights.length + entriesPerLong - 1) / entriesPerLong;

        // Assigns a value
        final int maxPossibleIndexInContainer = entriesPerLong - 1;
        // Calls a method
        final int entryMask = (1 << bitsPerEntry) - 1;

        // Assigns a value
        long[] data = new long[len];
        // Assigns a value
        int containerIndex = 0;
        // Loop: repeats a block
        for (int i = 0; i < heights.length; i++) {
            // Assigns a value
            final int indexInContainer = i % entriesPerLong;
            // Assigns a value
            final int entry = heights[i];
            // Calls a method
            data[containerIndex] |= ((long) (entry & entryMask)) << (indexInContainer * bitsPerEntry);
            // Branch: checks a condition
            if (indexInContainer == maxPossibleIndexInContainer) containerIndex++;
        // End of a block/expression
        }
        // Returns a value to the caller
        return data;
    // End of a block/expression
    }
// End of a block/expression
}
