// Déclaration du paquet de ce fichier
package net.minestom.server.instance.light;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.shorts.ShortArrayFIFOQueue;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.BlockVec;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.instance.palette.Palette;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.HashSet;
// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicBoolean;

// Import statique d'un membre
import static net.minestom.server.coordinate.CoordConversion.SECTION_BLOCK_COUNT;
// Import statique d'un membre
import static net.minestom.server.instance.light.LightCompute.*;

// Déclaration de type (classe/interface/enum/record)
final class SkyLight implements Light {
    // Instruction de code
    private byte[] content;
    // Instruction de code
    private byte[] contentPropagation;
    // Instruction de code
    private byte[] contentPropagationSwap;

    // Affecte une valeur
    private volatile boolean isValidBorders = true;
    // Appelle une méthode
    private final AtomicBoolean needsSend = new AtomicBoolean(false);

    // Affecte une valeur
    private boolean fullyLit = false;

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void flip() {
        // Embranchement : vérifie une condition
        if (this.contentPropagationSwap != null)
            // Accès à l'objet courant/parent
            this.contentPropagation = this.contentPropagationSwap;
        // Accès à l'objet courant/parent
        this.contentPropagationSwap = null;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static ShortArrayFIFOQueue buildInternalQueue(int[] heightmap, int maxY, int sectionY) {
        // Appelle une méthode
        ShortArrayFIFOQueue lightSources = new ShortArrayFIFOQueue();
        // Appelle une méthode
        final int sectionMaxY = (sectionY + 1) * 16 - 1;
        // Affecte une valeur
        final int sectionMinY = sectionY * 16;
        // Boucle : répète un bloc
        for (int x = 0; x < 16; x++) {
            // Boucle : répète un bloc
            for (int z = 0; z < 16; z++) {
                // Affecte une valeur
                final int height = heightmap[z << 4 | x];
                // Boucle : répète un bloc
                for (int y = Math.min(sectionMaxY, maxY); y >= Math.max(height, sectionMinY); y--) {
                    // Appelle une méthode
                    final int index = x | (z << 4) | ((y % 16) << 8);
                    // Appelle une méthode
                    lightSources.enqueue((short) (index | (15 << 12)));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return lightSources;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void invalidate() {
        // Accès à l'objet courant/parent
        this.needsSend.set(true);
        // Accès à l'objet courant/parent
        this.isValidBorders = false;
        // Accès à l'objet courant/parent
        this.contentPropagation = null;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean requiresUpdate() {
        // Renvoie une valeur à l'appelant
        return !isValidBorders;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void set(byte[] copyArray) {
        // Accès à l'objet courant/parent
        this.content = lazyArray(copyArray);
        // Accès à l'objet courant/parent
        this.contentPropagation = this.content;
        // Accès à l'objet courant/parent
        this.isValidBorders = true;
        // Accès à l'objet courant/parent
        this.needsSend.set(true);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean requiresSend() {
        // Renvoie une valeur à l'appelant
        return needsSend.getAndSet(false);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public byte[] array() {
        // Embranchement : vérifie une condition
        if (content == null) return UNSET_CONTENT;
        // Embranchement : vérifie une condition
        if (contentPropagation == null) return content;
        // Appelle une méthode
        var res = LightCompute.bake(contentPropagation, content);
        // Embranchement : vérifie une condition
        if (res == EMPTY_CONTENT) return UNSET_CONTENT;
        // Renvoie une valeur à l'appelant
        return res;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int getLevel(int x, int y, int z) {
        // Embranchement : vérifie une condition
        if (content == null) return 0;
        // Appelle une méthode
        int index = x | (z << 4) | (y << 8);
        // Embranchement : vérifie une condition
        if (contentPropagation == null) return LightCompute.getLight(content, index);
        // Renvoie une valeur à l'appelant
        return Math.max(LightCompute.getLight(contentPropagation, index), LightCompute.getLight(content, index));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Instruction de code
    public Set<Point> calculateInternal(Palette blockPalette,
                                        // Instruction de code
                                        int chunkX, int chunkY, int chunkZ,
                                        // Instruction de code
                                        int[] heightmap, int maxY,
                                        // Début d'une méthode/d'un bloc
                                        LightLookup lightLookup) {
        // Accès à l'objet courant/parent
        this.isValidBorders = true;

        // Update single section with base lighting changes
        // Affecte une valeur
        int queueSize = SECTION_BLOCK_COUNT;
        // Appelle une méthode
        ShortArrayFIFOQueue queue = new ShortArrayFIFOQueue(0);
        // Embranchement : vérifie une condition
        if (!fullyLit) {
            // Appelle une méthode
            queue = buildInternalQueue(heightmap, maxY, chunkY);
            // Appelle une méthode
            queueSize = queue.size();
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (queueSize == SECTION_BLOCK_COUNT) {
            // Accès à l'objet courant/parent
            this.fullyLit = true;
            // Accès à l'objet courant/parent
            this.content = CONTENT_FULLY_LIT;
        // Branche alternative de la condition
        } else {
            // Accès à l'objet courant/parent
            this.content = LightCompute.compute(blockPalette, queue);
        // Fin d'un bloc/d'une expression
        }

        // Propagate changes to neighbors and self
        // Appelle une méthode
        Set<Point> toUpdate = new HashSet<>();
        // Boucle : répète un bloc
        for (int i = -1; i <= 1; i++) {
            // Boucle : répète un bloc
            for (int j = -1; j <= 1; j++) {
                // Boucle : répète un bloc
                for (int k = -1; k <= 1; k++) {
                    // Affecte une valeur
                    final int neighborX = chunkX + i;
                    // Affecte une valeur
                    final int neighborY = chunkY + j;
                    // Affecte une valeur
                    final int neighborZ = chunkZ + k;
                    // Embranchement : vérifie une condition
                    if (!(lightLookup.light(neighborX, neighborY, neighborZ) instanceof SkyLight skyLight))
                        // Passe à l'itération suivante de la boucle
                        continue;
                    // Affecte une valeur
                    skyLight.contentPropagation = null;
                    // Appelle une méthode
                    toUpdate.add(new BlockVec(neighborX, neighborY, neighborZ));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        toUpdate.add(new BlockVec(chunkX, chunkY, chunkZ));
        // Renvoie une valeur à l'appelant
        return toUpdate;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Instruction de code
    public Set<Point> calculateExternal(Palette blockPalette,
                                        // Instruction de code
                                        Point[] neighbors,
                                        // Instruction de code
                                        LightLookup lightLookup,
                                        // Début d'une méthode/d'un bloc
                                        PaletteLookup paletteLookup) {
        // Embranchement : vérifie une condition
        if (!isValidBorders) return Set.of();
        // Affecte une valeur
        byte[] contentPropagationTemp = CONTENT_FULLY_LIT;
        // Embranchement : vérifie une condition
        if (!fullyLit) {
            // Appelle une méthode
            ShortArrayFIFOQueue queue = buildExternalQueue(blockPalette, neighbors, content, lightLookup, paletteLookup);
            // Appelle une méthode
            contentPropagationTemp = LightCompute.compute(blockPalette, queue);
            // Accès à l'objet courant/parent
            this.contentPropagationSwap = LightCompute.bake(contentPropagationSwap, contentPropagationTemp);
        // Branche alternative de la condition
        } else {
            // Accès à l'objet courant/parent
            this.contentPropagationSwap = null;
        // Fin d'un bloc/d'une expression
        }
        // Propagate changes to neighbors and self
        // Appelle une méthode
        Set<Point> toUpdate = new HashSet<>();
        // Boucle : répète un bloc
        for (int i = 0; i < neighbors.length; i++) {
            // Affecte une valeur
            final Point neighbor = neighbors[i];
            // Embranchement : vérifie une condition
            if (neighbor == null) continue;
            // Affecte une valeur
            final BlockFace face = FACES[i];
            // Embranchement : vérifie une condition
            if (!LightCompute.compareBorders(content, contentPropagation, contentPropagationTemp, face)) {
                // Appelle une méthode
                toUpdate.add(neighbor);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return toUpdate;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
