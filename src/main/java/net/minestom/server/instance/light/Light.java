// Déclaration du paquet de ce fichier
package net.minestom.server.instance.light;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.BlockVec;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.palette.Palette;
// Import d'une classe nécessaire
import net.minestom.server.utils.Direction;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Set;

// Déclaration de type (classe/interface/enum/record)
public interface Light {
    // Début d'une méthode/d'un bloc
    static Light sky() {
        // Renvoie une valeur à l'appelant
        return new SkyLight();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Light block() {
        // Renvoie une valeur à l'appelant
        return new BlockLight();
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    boolean requiresSend();

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Appelle une méthode
    byte[] array();

    // Appelle une méthode
    void flip();

    // Appelle une méthode
    int getLevel(int x, int y, int z);

    // Appelle une méthode
    void invalidate();

    // Appelle une méthode
    boolean requiresUpdate();

    // Appelle une méthode
    void set(byte[] copyArray);

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Instruction de code
    Set<Point> calculateInternal(Palette blockPalette,
                                 // Instruction de code
                                 int chunkX, int chunkY, int chunkZ,
                                 // Instruction de code
                                 int[] heightmap, int maxY,
                                 // Instruction de code
                                 LightLookup lightLookup);

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Instruction de code
    Set<Point> calculateExternal(Palette blockPalette,
                                 // Instruction de code
                                 Point[] neighbors,
                                 // Instruction de code
                                 LightLookup lightLookup,
                                 // Instruction de code
                                 PaletteLookup paletteLookup);

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static Point[] getNeighbors(Chunk chunk, int sectionY) {
        // Appelle une méthode
        final int chunkX = chunk.getChunkX(), chunkZ = chunk.getChunkZ();

        // Affecte une valeur
        Point[] links = new BlockVec[LightCompute.DIRECTIONS.length];
        // Boucle : répète un bloc
        for (Direction direction : LightCompute.DIRECTIONS) {
            // Appelle une méthode
            final int x = chunkX + direction.normalX();
            // Appelle une méthode
            final int z = chunkZ + direction.normalZ();
            // Appelle une méthode
            final int y = sectionY + direction.normalY();

            // Appelle une méthode
            Chunk foundChunk = chunk.getInstance().getChunk(x, z);
            // Embranchement : vérifie une condition
            if (foundChunk == null) continue;
            // Embranchement : vérifie une condition
            if (y - foundChunk.getMinSection() > foundChunk.getMaxSection() || y - foundChunk.getMinSection() < 0)
                // Passe à l'itération suivante de la boucle
                continue;

            // Appelle une méthode
            links[direction.ordinal()] = new BlockVec(foundChunk.getChunkX(), y, foundChunk.getChunkZ());
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return links;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    interface LightLookup {
        // Annotation pour l'élément suivant
        @Nullable Light light(int x, int y, int z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    interface PaletteLookup {
        // Annotation pour l'élément suivant
        @Nullable Palette palette(int x, int y, int z);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
