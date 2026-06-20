// Déclaration du paquet de ce fichier
package net.minestom.server.coordinate;

/**
 * Helper class to iterate over chunks within a range.
 */
// Déclaration de type (classe/interface/enum/record)
public final class ChunkRange {

    /**
     * Get the amount of chunks in a square range.
     *
     * @param range the range
     * @return the amount of chunks in the square range
     */
    // Début d'une méthode/d'un bloc
    public static int chunksCount(int range) {
        // Embranchement : vérifie une condition
        if (range < 0) throw new IllegalArgumentException("Range cannot be negative");
        // Affecte une valeur
        final int square = range * 2 + 1;
        // Renvoie une valeur à l'appelant
        return square * square;
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static void chunksInRangeDiffering(int newChunkX, int newChunkZ,
                                              // Instruction de code
                                              int oldChunkX, int oldChunkZ,
                                              // Début d'une méthode/d'un bloc
                                              int range, ChunkConsumer callback) {
        // Boucle : répète un bloc
        for (int x = newChunkX - range; x <= newChunkX + range; x++) {
            // Boucle : répète un bloc
            for (int z = newChunkZ - range; z <= newChunkZ + range; z++) {
                // Embranchement : vérifie une condition
                if (Math.abs(x - oldChunkX) > range || Math.abs(z - oldChunkZ) > range) {
                    // Appelle une méthode
                    callback.accept(x, z);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public static void chunksInRangeDiffering(int newChunkX, int newChunkZ,
                                              // Instruction de code
                                              int oldChunkX, int oldChunkZ,
                                              // Instruction de code
                                              int range,
                                              // Début d'une méthode/d'un bloc
                                              ChunkConsumer newCallback, ChunkConsumer oldCallback) {
        // Find the new chunks
        // Appelle une méthode
        chunksInRangeDiffering(newChunkX, newChunkZ, oldChunkX, oldChunkZ, range, newCallback);
        // Find the old chunks
        // Appelle une méthode
        chunksInRangeDiffering(oldChunkX, oldChunkZ, newChunkX, newChunkZ, range, oldCallback);
    // Fin d'un bloc/d'une expression
    }

    /**
     * New implementation comes from <a href="https://github.com/KryptonMC/Krypton/blob/a9eff5463328f34072cdaf37aae3e77b14fcac93/server/src/main/kotlin/org/kryptonmc/krypton/util/math/Maths.kt#L62">Krypton</a>
     * which comes from kotlin port by <a href="https://github.com/Esophose">Esophose</a>, which comes from <a href="https://stackoverflow.com/questions/398299/looping-in-a-spiral">a stackoverflow answer</a>.
     */
    // Début d'une méthode/d'un bloc
    public static void chunksInRange(int chunkX, int chunkZ, int range, ChunkConsumer consumer) {
        // Send in spiral around the center chunk
        // Note: it's not really required to start at the center anymore since the chunk queue is sorted by distance,
        //       however we still should send a circle so this method is still fine, and good for any other case a
        //       spiral might be needed.
        // Appelle une méthode
        consumer.accept(chunkX, chunkZ);
        // Boucle : répète un bloc
        for (int id = 1; id < (range * 2 + 1) * (range * 2 + 1); id++) {
            // Affecte une valeur
            final int index = id - 1;
            // compute radius (inverse arithmetic sum of 8 + 16 + 24 + ...)
            // Appelle une méthode
            final int radius = (int) Math.floor((Math.sqrt(index + 1.0) - 1) / 2) + 1;
            // compute total point on radius -1 (arithmetic sum of 8 + 16 + 24 + ...)
            // Appelle une méthode
            final int p = 8 * radius * (radius - 1) / 2;
            // points by face
            // Affecte une valeur
            final int en = radius * 2;
            // compute de position and shift it so the first is (-r, -r) but (-r + 1, -r)
            // so the square can connect
            // Appelle une méthode
            final int a = (1 + index - p) % (radius * 8);
            // Embranchement multiple (switch/case)
            switch (a / (radius * 2)) {
                // find the face (0 = top, 1 = right, 2 = bottom, 3 = left)
                // Embranchement multiple (switch/case)
                case 0 -> consumer.accept(a - radius + chunkX, -radius + chunkZ);
                // Embranchement multiple (switch/case)
                case 1 -> consumer.accept(radius + chunkX, a % en - radius + chunkZ);
                // Embranchement multiple (switch/case)
                case 2 -> consumer.accept(radius - a % en + chunkX, radius + chunkZ);
                // Embranchement multiple (switch/case)
                case 3 -> consumer.accept(-radius + chunkX, radius - a % en + chunkZ);
                // Embranchement multiple (switch/case)
                default -> throw new IllegalStateException("unreachable");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void chunksInRange(Point point, int range, ChunkConsumer consumer) {
        // Appelle une méthode
        chunksInRange(point.chunkX(), point.chunkZ(), range, consumer);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface ChunkConsumer {
        // Appelle une méthode
        void accept(int chunkX, int chunkZ);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
