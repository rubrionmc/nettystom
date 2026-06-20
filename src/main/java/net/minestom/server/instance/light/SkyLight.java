// Package declaration for this file
package net.minestom.server.instance.light;

// Import of a required class
import it.unimi.dsi.fastutil.shorts.ShortArrayFIFOQueue;
// Import of a required class
import net.minestom.server.coordinate.BlockVec;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import net.minestom.server.instance.palette.Palette;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.util.HashSet;
// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.concurrent.atomic.AtomicBoolean;

// Static import of a member
import static net.minestom.server.coordinate.CoordConversion.SECTION_BLOCK_COUNT;
// Static import of a member
import static net.minestom.server.instance.light.LightCompute.*;

// Type declaration (class/interface/enum/record)
final class SkyLight implements Light {
    // Code statement
    private byte[] content;
    // Code statement
    private byte[] contentPropagation;
    // Code statement
    private byte[] contentPropagationSwap;

    // Assigns a value
    private volatile boolean isValidBorders = true;
    // Calls a method
    private final AtomicBoolean needsSend = new AtomicBoolean(false);

    // Assigns a value
    private boolean fullyLit = false;

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void flip() {
        // Branch: checks a condition
        if (this.contentPropagationSwap != null)
            // Access to the current/parent object
            this.contentPropagation = this.contentPropagationSwap;
        // Access to the current/parent object
        this.contentPropagationSwap = null;
    // End of a block/expression
    }

    // Start of a method/block
    static ShortArrayFIFOQueue buildInternalQueue(int[] heightmap, int maxY, int sectionY) {
        // Calls a method
        ShortArrayFIFOQueue lightSources = new ShortArrayFIFOQueue();
        // Calls a method
        final int sectionMaxY = (sectionY + 1) * 16 - 1;
        // Assigns a value
        final int sectionMinY = sectionY * 16;
        // Loop: repeats a block
        for (int x = 0; x < 16; x++) {
            // Loop: repeats a block
            for (int z = 0; z < 16; z++) {
                // Assigns a value
                final int height = heightmap[z << 4 | x];
                // Loop: repeats a block
                for (int y = Math.min(sectionMaxY, maxY); y >= Math.max(height, sectionMinY); y--) {
                    // Calls a method
                    final int index = x | (z << 4) | ((y % 16) << 8);
                    // Calls a method
                    lightSources.enqueue((short) (index | (15 << 12)));
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return lightSources;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void invalidate() {
        // Access to the current/parent object
        this.needsSend.set(true);
        // Access to the current/parent object
        this.isValidBorders = false;
        // Access to the current/parent object
        this.contentPropagation = null;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean requiresUpdate() {
        // Returns a value to the caller
        return !isValidBorders;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void set(byte[] copyArray) {
        // Access to the current/parent object
        this.content = lazyArray(copyArray);
        // Access to the current/parent object
        this.contentPropagation = this.content;
        // Access to the current/parent object
        this.isValidBorders = true;
        // Access to the current/parent object
        this.needsSend.set(true);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean requiresSend() {
        // Returns a value to the caller
        return needsSend.getAndSet(false);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public byte[] array() {
        // Branch: checks a condition
        if (content == null) return UNSET_CONTENT;
        // Branch: checks a condition
        if (contentPropagation == null) return content;
        // Calls a method
        var res = LightCompute.bake(contentPropagation, content);
        // Branch: checks a condition
        if (res == EMPTY_CONTENT) return UNSET_CONTENT;
        // Returns a value to the caller
        return res;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int getLevel(int x, int y, int z) {
        // Branch: checks a condition
        if (content == null) return 0;
        // Calls a method
        int index = x | (z << 4) | (y << 8);
        // Branch: checks a condition
        if (contentPropagation == null) return LightCompute.getLight(content, index);
        // Returns a value to the caller
        return Math.max(LightCompute.getLight(contentPropagation, index), LightCompute.getLight(content, index));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Code statement
    public Set<Point> calculateInternal(Palette blockPalette,
                                        // Code statement
                                        int chunkX, int chunkY, int chunkZ,
                                        // Code statement
                                        int[] heightmap, int maxY,
                                        // Start of a method/block
                                        LightLookup lightLookup) {
        // Access to the current/parent object
        this.isValidBorders = true;

        // Update single section with base lighting changes
        // Assigns a value
        int queueSize = SECTION_BLOCK_COUNT;
        // Calls a method
        ShortArrayFIFOQueue queue = new ShortArrayFIFOQueue(0);
        // Branch: checks a condition
        if (!fullyLit) {
            // Calls a method
            queue = buildInternalQueue(heightmap, maxY, chunkY);
            // Calls a method
            queueSize = queue.size();
        // End of a block/expression
        }

        // Branch: checks a condition
        if (queueSize == SECTION_BLOCK_COUNT) {
            // Access to the current/parent object
            this.fullyLit = true;
            // Access to the current/parent object
            this.content = CONTENT_FULLY_LIT;
        // Alternative branch of the condition
        } else {
            // Access to the current/parent object
            this.content = LightCompute.compute(blockPalette, queue);
        // End of a block/expression
        }

        // Propagate changes to neighbors and self
        // Calls a method
        Set<Point> toUpdate = new HashSet<>();
        // Loop: repeats a block
        for (int i = -1; i <= 1; i++) {
            // Loop: repeats a block
            for (int j = -1; j <= 1; j++) {
                // Loop: repeats a block
                for (int k = -1; k <= 1; k++) {
                    // Assigns a value
                    final int neighborX = chunkX + i;
                    // Assigns a value
                    final int neighborY = chunkY + j;
                    // Assigns a value
                    final int neighborZ = chunkZ + k;
                    // Branch: checks a condition
                    if (!(lightLookup.light(neighborX, neighborY, neighborZ) instanceof SkyLight skyLight))
                        // Continues to the next loop iteration
                        continue;
                    // Assigns a value
                    skyLight.contentPropagation = null;
                    // Calls a method
                    toUpdate.add(new BlockVec(neighborX, neighborY, neighborZ));
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        toUpdate.add(new BlockVec(chunkX, chunkY, chunkZ));
        // Returns a value to the caller
        return toUpdate;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Code statement
    public Set<Point> calculateExternal(Palette blockPalette,
                                        // Code statement
                                        Point[] neighbors,
                                        // Code statement
                                        LightLookup lightLookup,
                                        // Start of a method/block
                                        PaletteLookup paletteLookup) {
        // Branch: checks a condition
        if (!isValidBorders) return Set.of();
        // Assigns a value
        byte[] contentPropagationTemp = CONTENT_FULLY_LIT;
        // Branch: checks a condition
        if (!fullyLit) {
            // Calls a method
            ShortArrayFIFOQueue queue = buildExternalQueue(blockPalette, neighbors, content, lightLookup, paletteLookup);
            // Calls a method
            contentPropagationTemp = LightCompute.compute(blockPalette, queue);
            // Access to the current/parent object
            this.contentPropagationSwap = LightCompute.bake(contentPropagationSwap, contentPropagationTemp);
        // Alternative branch of the condition
        } else {
            // Access to the current/parent object
            this.contentPropagationSwap = null;
        // End of a block/expression
        }
        // Propagate changes to neighbors and self
        // Calls a method
        Set<Point> toUpdate = new HashSet<>();
        // Loop: repeats a block
        for (int i = 0; i < neighbors.length; i++) {
            // Assigns a value
            final Point neighbor = neighbors[i];
            // Branch: checks a condition
            if (neighbor == null) continue;
            // Assigns a value
            final BlockFace face = FACES[i];
            // Branch: checks a condition
            if (!LightCompute.compareBorders(content, contentPropagation, contentPropagationTemp, face)) {
                // Calls a method
                toUpdate.add(neighbor);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return toUpdate;
    // End of a block/expression
    }
// End of a block/expression
}
