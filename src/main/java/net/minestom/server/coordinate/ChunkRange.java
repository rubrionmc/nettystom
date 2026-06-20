// Package declaration for this file
package net.minestom.server.coordinate;

/**
 * Helper class to iterate over chunks within a range.
 */
// Type declaration (class/interface/enum/record)
public final class ChunkRange {

    /**
     * Get the amount of chunks in a square range.
     *
     * @param range the range
     * @return the amount of chunks in the square range
     */
    // Start of a method/block
    public static int chunksCount(int range) {
        // Branch: checks a condition
        if (range < 0) throw new IllegalArgumentException("Range cannot be negative");
        // Assigns a value
        final int square = range * 2 + 1;
        // Returns a value to the caller
        return square * square;
    // End of a block/expression
    }

    // Code statement
    public static void chunksInRangeDiffering(int newChunkX, int newChunkZ,
                                              // Code statement
                                              int oldChunkX, int oldChunkZ,
                                              // Start of a method/block
                                              int range, ChunkConsumer callback) {
        // Loop: repeats a block
        for (int x = newChunkX - range; x <= newChunkX + range; x++) {
            // Loop: repeats a block
            for (int z = newChunkZ - range; z <= newChunkZ + range; z++) {
                // Branch: checks a condition
                if (Math.abs(x - oldChunkX) > range || Math.abs(z - oldChunkZ) > range) {
                    // Calls a method
                    callback.accept(x, z);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Code statement
    public static void chunksInRangeDiffering(int newChunkX, int newChunkZ,
                                              // Code statement
                                              int oldChunkX, int oldChunkZ,
                                              // Code statement
                                              int range,
                                              // Start of a method/block
                                              ChunkConsumer newCallback, ChunkConsumer oldCallback) {
        // Find the new chunks
        // Calls a method
        chunksInRangeDiffering(newChunkX, newChunkZ, oldChunkX, oldChunkZ, range, newCallback);
        // Find the old chunks
        // Calls a method
        chunksInRangeDiffering(oldChunkX, oldChunkZ, newChunkX, newChunkZ, range, oldCallback);
    // End of a block/expression
    }

    /**
     * New implementation comes from <a href="https://github.com/KryptonMC/Krypton/blob/a9eff5463328f34072cdaf37aae3e77b14fcac93/server/src/main/kotlin/org/kryptonmc/krypton/util/math/Maths.kt#L62">Krypton</a>
     * which comes from kotlin port by <a href="https://github.com/Esophose">Esophose</a>, which comes from <a href="https://stackoverflow.com/questions/398299/looping-in-a-spiral">a stackoverflow answer</a>.
     */
    // Start of a method/block
    public static void chunksInRange(int chunkX, int chunkZ, int range, ChunkConsumer consumer) {
        // Send in spiral around the center chunk
        // Note: it's not really required to start at the center anymore since the chunk queue is sorted by distance,
        //       however we still should send a circle so this method is still fine, and good for any other case a
        //       spiral might be needed.
        // Calls a method
        consumer.accept(chunkX, chunkZ);
        // Loop: repeats a block
        for (int id = 1; id < (range * 2 + 1) * (range * 2 + 1); id++) {
            // Assigns a value
            final int index = id - 1;
            // compute radius (inverse arithmetic sum of 8 + 16 + 24 + ...)
            // Calls a method
            final int radius = (int) Math.floor((Math.sqrt(index + 1.0) - 1) / 2) + 1;
            // compute total point on radius -1 (arithmetic sum of 8 + 16 + 24 + ...)
            // Calls a method
            final int p = 8 * radius * (radius - 1) / 2;
            // points by face
            // Assigns a value
            final int en = radius * 2;
            // compute de position and shift it so the first is (-r, -r) but (-r + 1, -r)
            // so the square can connect
            // Calls a method
            final int a = (1 + index - p) % (radius * 8);
            // Multiple branching (switch/case)
            switch (a / (radius * 2)) {
                // find the face (0 = top, 1 = right, 2 = bottom, 3 = left)
                // Multiple branching (switch/case)
                case 0 -> consumer.accept(a - radius + chunkX, -radius + chunkZ);
                // Multiple branching (switch/case)
                case 1 -> consumer.accept(radius + chunkX, a % en - radius + chunkZ);
                // Multiple branching (switch/case)
                case 2 -> consumer.accept(radius - a % en + chunkX, radius + chunkZ);
                // Multiple branching (switch/case)
                case 3 -> consumer.accept(-radius + chunkX, radius - a % en + chunkZ);
                // Multiple branching (switch/case)
                default -> throw new IllegalStateException("unreachable");
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static void chunksInRange(Point point, int range, ChunkConsumer consumer) {
        // Calls a method
        chunksInRange(point.chunkX(), point.chunkZ(), range, consumer);
    // End of a block/expression
    }

    // Annotation for the following element
    @FunctionalInterface
    // Type declaration (class/interface/enum/record)
    public interface ChunkConsumer {
        // Calls a method
        void accept(int chunkX, int chunkZ);
    // End of a block/expression
    }
// End of a block/expression
}
