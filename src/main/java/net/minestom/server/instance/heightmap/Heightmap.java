// Déclaration du paquet de ce fichier
package net.minestom.server.instance.heightmap;

// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.palette.Palette;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;

// Import statique d'un membre
import static net.minestom.server.coordinate.CoordConversion.globalToChunk;
// Import statique d'un membre
import static net.minestom.server.coordinate.CoordConversion.globalToSectionRelative;
// Import statique d'un membre
import static net.minestom.server.instance.Chunk.CHUNK_SIZE_X;
// Import statique d'un membre
import static net.minestom.server.instance.Chunk.CHUNK_SIZE_Z;

// Déclaration de type (classe/interface/enum/record)
public abstract class Heightmap {
    // Déclaration de type (classe/interface/enum/record)
    public enum Type {
        // Instruction de code
        WORLD_SURFACE_WG,
        // Instruction de code
        WORLD_SURFACE,
        // Instruction de code
        OCEAN_FLOOR_WG,
        // Instruction de code
        OCEAN_FLOOR,
        // Instruction de code
        MOTION_BLOCKING,
        // Instruction de code
        MOTION_BLOCKING_NO_LEAVES;

        // Appelle une méthode
        public static final NetworkBuffer.Type<Type> NETWORK_TYPE = NetworkBuffer.Enum(Type.class);
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    private final short[] heights = new short[CHUNK_SIZE_X * CHUNK_SIZE_Z];
    // Instruction de code
    private final Chunk chunk;
    // Instruction de code
    private final int minHeight;
    // Affecte une valeur
    private boolean needsRefresh = true;

    // Début d'une méthode/d'un bloc
    public Heightmap(Chunk chunk) {
        // Accès à l'objet courant/parent
        this.chunk = chunk;
        // Accès à l'objet courant/parent
        this.minHeight = chunk.getInstance().getCachedDimensionType().minY() - 1;
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    public abstract Type type();

    // Appelle une méthode
    protected abstract boolean checkBlock(Block block);

    // Début d'une méthode/d'un bloc
    public void refresh(int x, int y, int z, Block block) {
        // Appelle une méthode
        final int height = getHeight(x, z);
        // Embranchement : vérifie une condition
        if (checkBlock(block)) {
            // Embranchement : vérifie une condition
            if (height < y) setHeightY(x, z, y);
        // Embranchement : vérifie une condition
        } else if (y == height) {
            // Appelle une méthode
            refresh(x, z, y - 1);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void refresh(int startY) {
        // Embranchement : vérifie une condition
        if (!needsRefresh) return;
        // Début d'une méthode/d'un bloc
        synchronized (chunk) {
            // Boucle : répète un bloc
            for (int x = 0; x < CHUNK_SIZE_X; x++) {
                // Boucle : répète un bloc
                for (int z = 0; z < CHUNK_SIZE_Z; z++) {
                    // Appelle une méthode
                    refresh(x, z, startY);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        needsRefresh = false;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void refresh(int x, int z, int startY) {
        // Appelle une méthode
        final int localX = globalToSectionRelative(x);
        // Appelle une méthode
        final int localZ = globalToSectionRelative(z);

        // Affecte une valeur
        int foundHeight = minHeight;
        // Affecte une valeur
        int currentY = startY;
        // Boucle : répète un bloc
        while (currentY > minHeight) {
            // Appelle une méthode
            final int sectionY = globalToChunk(currentY);
            // Embranchement : vérifie une condition
            if (sectionY < chunk.getMinSection() || sectionY >= chunk.getMaxSection()) {
                // Affecte une valeur
                currentY = (sectionY << 4) - 1; // Move to the bottom of the previous section
                // Passe à l'itération suivante de la boucle
                continue;
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            final Palette blockPalette = chunk.getSection(sectionY).blockPalette();
            // Affecte une valeur
            final int localHeight = blockPalette.height(localX, localZ, (px, py, pz, value) -> {
                // Embranchement : vérifie une condition
                if (value == 0) return false;
                // Appelle une méthode
                final Block block = Block.fromStateId(value);
                // Renvoie une valeur à l'appelant
                return block != null && checkBlock(block);
            // Fin d'un bloc/d'une expression
            });
            // Embranchement : vérifie une condition
            if (localHeight >= 0) {
                // Found a matching block, convert local Y back to world Y
                // Affecte une valeur
                foundHeight = (sectionY << 4) + localHeight;
                // Interrompt la boucle/le bloc
                break;
            // Fin d'un bloc/d'une expression
            }

            // No matching block found in this section, move to the section below
            // Affecte une valeur
            currentY = (sectionY << 4) - 1;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        setHeightY(x, z, foundHeight);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public long[] getNBT() {
        // Appelle une méthode
        final int dimensionHeight = chunk.getInstance().getCachedDimensionType().height();
        // Appelle une méthode
        final int bitsForHeight = MathUtils.bitsToRepresent(dimensionHeight);
        // Renvoie une valeur à l'appelant
        return encode(heights, bitsForHeight);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void loadFrom(long[] data) {
        // Appelle une méthode
        final int dimensionHeight = chunk.getInstance().getCachedDimensionType().height();
        // Appelle une méthode
        final int bitsPerEntry = MathUtils.bitsToRepresent(dimensionHeight);

        // Affecte une valeur
        final int entriesPerLong = 64 / bitsPerEntry;

        // Affecte une valeur
        final int maxPossibleIndexInContainer = entriesPerLong - 1;
        // Affecte une valeur
        final int entryMask = (1 << bitsPerEntry) - 1;

        // Affecte une valeur
        int containerIndex = 0;
        // Boucle : répète un bloc
        for (int i = 0; i < heights.length; i++) {
            // Affecte une valeur
            final int indexInContainer = i % entriesPerLong;
            // Affecte une valeur
            heights[i] = (short) ((int) (data[containerIndex] >> (indexInContainer * bitsPerEntry)) & entryMask);
            // Embranchement : vérifie une condition
            if (indexInContainer == maxPossibleIndexInContainer) containerIndex++;
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        needsRefresh = false;
    // Fin d'un bloc/d'une expression
    }

    // highest breaking block in section
    // Début d'une méthode/d'un bloc
    public int getHeight(int x, int z) {
        // Embranchement : vérifie une condition
        if (needsRefresh) refresh(getHighestBlockSection(chunk));
        // Renvoie une valeur à l'appelant
        return heights[z << 4 | x] + minHeight;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void setHeightY(int x, int z, int height) {
        // Affecte une valeur
        heights[z << 4 | x] = (short) (height - minHeight);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int getHighestBlockSection(Chunk chunk) {
        // Appelle une méthode
        int y = chunk.getInstance().getCachedDimensionType().maxY();
        // Appelle une méthode
        final int sectionsCount = chunk.getMaxSection() - chunk.getMinSection();
        // Boucle : répète un bloc
        for (int i = 0; i < sectionsCount; i++) {
            // Appelle une méthode
            final int sectionY = chunk.getMaxSection() - i - 1;
            // Appelle une méthode
            final Palette blockPalette = chunk.getSection(sectionY).blockPalette();
            // Embranchement : vérifie une condition
            if (blockPalette.count() != 0) break;
            // Affecte une valeur
            y -= 16;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return y;
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    static long[] encode(short[] heights, int bitsPerEntry) {
        // Affecte une valeur
        final int entriesPerLong = 64 / bitsPerEntry;
        // ceil(HeightsCount / entriesPerLong)
        // Affecte une valeur
        final int len = (heights.length + entriesPerLong - 1) / entriesPerLong;

        // Affecte une valeur
        final int maxPossibleIndexInContainer = entriesPerLong - 1;
        // Affecte une valeur
        final int entryMask = (1 << bitsPerEntry) - 1;

        // Affecte une valeur
        long[] data = new long[len];
        // Affecte une valeur
        int containerIndex = 0;
        // Boucle : répète un bloc
        for (int i = 0; i < heights.length; i++) {
            // Affecte une valeur
            final int indexInContainer = i % entriesPerLong;
            // Affecte une valeur
            final int entry = heights[i];
            // Affecte une valeur
            data[containerIndex] |= ((long) (entry & entryMask)) << (indexInContainer * bitsPerEntry);
            // Embranchement : vérifie une condition
            if (indexInContainer == maxPossibleIndexInContainer) containerIndex++;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return data;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
