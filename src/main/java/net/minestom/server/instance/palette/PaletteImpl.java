// Déclaration du paquet de ce fichier
package net.minestom.server.instance.palette;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.IntArrayList;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.IntSet;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;
// Import d'une classe nécessaire
import java.util.function.IntUnaryOperator;

// Import statique d'un membre
import static net.minestom.server.coordinate.CoordConversion.SECTION_BLOCK_COUNT;
// Import statique d'un membre
import static net.minestom.server.instance.palette.Palettes.*;

// Déclaration de type (classe/interface/enum/record)
final class PaletteImpl implements Palette {
    // Appelle une méthode
    private static final ThreadLocal<int[]> WRITE_CACHE = ThreadLocal.withInitial(() -> new int[SECTION_BLOCK_COUNT]);
    // Instruction de code
    final byte dimension, minBitsPerEntry, maxBitsPerEntry, directBits;

    // Affecte une valeur
    byte bitsPerEntry = 0;
    // Instruction de code
    int count = 0; // Serve as the single value if bitsPerEntry == 0

    // Instruction de code
    long @UnknownNullability [] values; // null when bitsPerEntry == 0
    // palette index = value
    // Annotation pour l'élément suivant
    @UnknownNullability IntArrayList paletteToValueList; // null when using direct mode (bitsPerEntry > maxBitsPerEntry)
    // value = palette index
    // Annotation pour l'élément suivant
    @UnknownNullability Int2IntOpenHashMap valueToPaletteMap; // null when using direct mode (bitsPerEntry > maxBitsPerEntry)

    // Début d'une méthode/d'un bloc
    PaletteImpl(byte dimension, byte minBitsPerEntry, byte maxBitsPerEntry, byte directBits) {
        // Appelle une méthode
        validateDimension(dimension);
        // Accès à l'objet courant/parent
        this.dimension = dimension;
        // Accès à l'objet courant/parent
        this.minBitsPerEntry = minBitsPerEntry;
        // Accès à l'objet courant/parent
        this.maxBitsPerEntry = maxBitsPerEntry;
        // Accès à l'objet courant/parent
        this.directBits = directBits;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    PaletteImpl(byte dimension, byte minBitsPerEntry, byte maxBitsPerEntry, byte directBits, byte bitsPerEntry) {
        // Appelle une méthode
        this(dimension, minBitsPerEntry, maxBitsPerEntry, directBits);

        // Accès à l'objet courant/parent
        this.bitsPerEntry = bitsPerEntry;
        // Embranchement : vérifie une condition
        if (bitsPerEntry != 0) {
            // Accès à l'objet courant/parent
            this.values = new long[arrayLength(dimension, bitsPerEntry)];

            // Embranchement : vérifie une condition
            if (hasPalette()) {
                // Accès à l'objet courant/parent
                this.paletteToValueList = new IntArrayList();
                // Accès à l'objet courant/parent
                this.valueToPaletteMap = new Int2IntOpenHashMap();
                // Accès à l'objet courant/parent
                this.valueToPaletteMap.defaultReturnValue(-1);
                // Accès à l'objet courant/parent
                this.paletteToValueList.add(0);
                // Accès à l'objet courant/parent
                this.valueToPaletteMap.put(0, 0);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int get(int x, int y, int z) {
        // Appelle une méthode
        validateCoord(dimension, x, y, z);
        // Embranchement : vérifie une condition
        if (bitsPerEntry == 0) return count;
        // Appelle une méthode
        final int value = read(dimension(), bitsPerEntry, values, x, y, z);
        // Renvoie une valeur à l'appelant
        return paletteIndexToValue(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void getAll(EntryConsumer consumer) {
        // Embranchement : vérifie une condition
        if (bitsPerEntry == 0) {
            // Appelle une méthode
            Palettes.getAllFill(dimension, count, consumer);
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            retrieveAll(consumer, true);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void getAllPresent(EntryConsumer consumer) {
        // Embranchement : vérifie une condition
        if (bitsPerEntry == 0) {
            // Embranchement : vérifie une condition
            if (count != 0) Palettes.getAllFill(dimension, count, consumer);
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            retrieveAll(consumer, false);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int height(int x, int z, EntryPredicate predicate) {
        // Appelle une méthode
        validateCoord(dimension, x, 0, z);
        // Affecte une valeur
        final int dimension = this.dimension;
        // Affecte une valeur
        final int startY = dimension - 1;
        // Embranchement : vérifie une condition
        if (bitsPerEntry == 0) return predicate.get(x, startY, z, count) ? startY : -1;
        // Affecte une valeur
        final long[] values = this.values;
        // Affecte une valeur
        final int bitsPerEntry = this.bitsPerEntry;
        // Affecte une valeur
        final int valuesPerLong = 64 / bitsPerEntry;
        // Affecte une valeur
        final int mask = (1 << bitsPerEntry) - 1;
        // Appelle une méthode
        final int[] paletteIds = hasPalette() ? paletteToValueList.elements() : null;
        // Boucle : répète un bloc
        for (int y = startY; y >= 0; y--) {
            // Appelle une méthode
            final int index = sectionIndex(dimension, x, y, z);
            // Affecte une valeur
            final int longIndex = index / valuesPerLong;
            // Affecte une valeur
            final int bitIndex = (index % valuesPerLong) * bitsPerEntry;
            // Affecte une valeur
            final int paletteIndex = (int) (values[longIndex] >> bitIndex) & mask;
            // Instruction de code
            final int value = paletteIds != null && paletteIndex < paletteIds.length ? paletteIds[paletteIndex]
                    // Instruction de code
                    : paletteIndex;
            // Embranchement : vérifie une condition
            if (predicate.get(x, y, z, value)) return y;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return -1;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void set(int x, int y, int z, int value) {
        // Appelle une méthode
        validateCoord(dimension, x, y, z);
        // Appelle une méthode
        final int paletteIndex = valueToPaletteIndex(value);
        // Appelle une méthode
        final int oldValue = Palettes.write(dimension(), bitsPerEntry, values, x, y, z, paletteIndex);
        // Check if block count needs to be updated
        // Appelle une méthode
        final boolean currentAir = paletteIndexToValue(oldValue) == 0;
        // Embranchement : vérifie une condition
        if (currentAir != (value == 0)) this.count += currentAir ? 1 : -1;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void fill(int value) {
        // Accès à l'objet courant/parent
        this.bitsPerEntry = 0;
        // Accès à l'objet courant/parent
        this.count = value;
        // Accès à l'objet courant/parent
        this.values = null;
        // Accès à l'objet courant/parent
        this.paletteToValueList = null;
        // Accès à l'objet courant/parent
        this.valueToPaletteMap = null;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void load(int[] palette, long[] values) {
        // Appelle une méthode
        int bpe = palette.length <= 1 ? 0 : MathUtils.bitsToRepresent(palette.length - 1);
        // Appelle une méthode
        bpe = Math.max(minBitsPerEntry, bpe);
        // Affecte une valeur
        boolean useDirectMode = bpe > maxBitsPerEntry;
        // Embranchement : vérifie une condition
        if (useDirectMode) bpe = directBits;
        // Accès à l'objet courant/parent
        this.bitsPerEntry = (byte) bpe;

        // Embranchement : vérifie une condition
        if (useDirectMode) {
            // Direct mode: convert from palette indices to direct values
            // Accès à l'objet courant/parent
            this.paletteToValueList = null;
            // Accès à l'objet courant/parent
            this.valueToPaletteMap = null;
            // Accès à l'objet courant/parent
            this.values = new long[arrayLength(dimension, directBits)];

            // Appelle une méthode
            final int originalBpe = palette.length <= 1 ? 0 : MathUtils.bitsToRepresent(palette.length - 1);
            // Appelle une méthode
            final int actualOriginalBpe = Math.max(minBitsPerEntry, originalBpe);
            // Affecte une valeur
            final int originalMask = (1 << actualOriginalBpe) - 1;
            // Affecte une valeur
            final int originalValuesPerLong = 64 / actualOriginalBpe;

            // Affecte une valeur
            int nonZeroCount = 0;
            // Affecte une valeur
            final int dimension = this.dimension;
            // Boucle : répète un bloc
            for (int y = 0; y < dimension; y++) {
                // Boucle : répète un bloc
                for (int z = 0; z < dimension; z++) {
                    // Boucle : répète un bloc
                    for (int x = 0; x < dimension; x++) {
                        // Appelle une méthode
                        final int index = sectionIndex(dimension, x, y, z);

                        // Read palette index from original values
                        // Affecte une valeur
                        final int longIndex = index / originalValuesPerLong;
                        // Affecte une valeur
                        final int bitIndex = (index % originalValuesPerLong) * actualOriginalBpe;
                        // Affecte une valeur
                        final int paletteIndex = (int) (values[longIndex] >> bitIndex) & originalMask;

                        // Convert to direct value
                        // Affecte une valeur
                        final int directValue = paletteIndex < palette.length ? palette[paletteIndex] : 0;
                        // Embranchement : vérifie une condition
                        if (directValue != 0) nonZeroCount++;

                        // Write direct value to new values array using coordinates
                        // Appelle une méthode
                        write(dimension, directBits, this.values, x, y, z, directValue);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Accès à l'objet courant/parent
            this.count = nonZeroCount;
        // Branche alternative de la condition
        } else {
            // Indirect mode: use palette
            // Accès à l'objet courant/parent
            this.paletteToValueList = new IntArrayList(palette);
            // Accès à l'objet courant/parent
            this.valueToPaletteMap = new Int2IntOpenHashMap(palette.length);
            // Accès à l'objet courant/parent
            this.valueToPaletteMap.defaultReturnValue(-1);
            // Boucle : répète un bloc
            for (int i = 0; i < palette.length; i++) {
                // Accès à l'objet courant/parent
                this.valueToPaletteMap.put(palette[i], i);
            // Fin d'un bloc/d'une expression
            }
            // Accès à l'objet courant/parent
            this.values = Arrays.copyOf(values, arrayLength(dimension, bitsPerEntry));
            // Appelle une méthode
            recount();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void offset(int offset) {
        // Embranchement : vérifie une condition
        if (offset == 0) return;
        // Embranchement : vérifie une condition
        if (bitsPerEntry == 0) {
            // Accès à l'objet courant/parent
            this.count += offset;
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            replaceAll((x, y, z, value) -> value + offset);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void replace(int oldValue, int newValue) {
        // Embranchement : vérifie une condition
        if (oldValue == newValue) return;
        // Embranchement : vérifie une condition
        if (bitsPerEntry == 0) {
            // Embranchement : vérifie une condition
            if (oldValue == count) fill(newValue);
        // Branche alternative de la condition
        } else {
            // Embranchement : vérifie une condition
            if (hasPalette()) {
                // Appelle une méthode
                final int index = valueToPaletteMap.get(oldValue);
                // Embranchement : vérifie une condition
                if (index == -1) return; // Old value not present in palette
                // Instruction de code
                final boolean countUpdate = newValue == 0 || oldValue == 0;
                // Appelle une méthode
                final int count = countUpdate ? count(oldValue) : -1;
                // Embranchement : vérifie une condition
                if (count == 0) return; // No blocks to replace
                // Appelle une méthode
                paletteToValueList.set(index, newValue);
                // Appelle une méthode
                valueToPaletteMap.remove(oldValue);
                // Appelle une méthode
                valueToPaletteMap.put(newValue, index);
                // Update count
                // Embranchement : vérifie une condition
                if (newValue == 0) {
                    // Accès à l'objet courant/parent
                    this.count -= count; // Replacing with air
                // Embranchement : vérifie une condition
                } else if (oldValue == 0) {
                    // Accès à l'objet courant/parent
                    this.count += count; // Replacing air with a block
                // Fin d'un bloc/d'une expression
                }
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                replaceAll((x, y, z, value) -> value == oldValue ? newValue : value);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setAll(EntrySupplier supplier) {
        // Appelle une méthode
        int[] cache = WRITE_CACHE.get();
        // Appelle une méthode
        final int dimension = dimension();
        // Fill cache with values
        // Affecte une valeur
        int fillValue = -1;
        // Affecte une valeur
        int count = 0;
        // Affecte une valeur
        int index = 0;
        // Boucle : répète un bloc
        for (int y = 0; y < dimension; y++) {
            // Boucle : répète un bloc
            for (int z = 0; z < dimension; z++) {
                // Boucle : répète un bloc
                for (int x = 0; x < dimension; x++) {
                    // Appelle une méthode
                    int value = supplier.get(x, y, z);
                    // Support for fill fast exit if the supplier returns a constant value
                    // Embranchement : vérifie une condition
                    if (fillValue != -2) {
                        // Embranchement : vérifie une condition
                        if (fillValue == -1) {
                            // Affecte une valeur
                            fillValue = value;
                        // Embranchement : vérifie une condition
                        } else if (fillValue != value) {
                            // Affecte une valeur
                            fillValue = -2;
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                    // Set value in cache
                    // Embranchement : vérifie une condition
                    if (value != 0) count++;
                    // Affecte une valeur
                    cache[index++] = value;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assert index == maxSize();
        // Update palette content
        // Embranchement : vérifie une condition
        if (fillValue < 0) {
            // Appelle une méthode
            makeDirect();
            // Appelle une méthode
            updateAll(cache);
            // Accès à l'objet courant/parent
            this.count = count;
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            fill(fillValue);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void replace(int x, int y, int z, IntUnaryOperator operator) {
        // Appelle une méthode
        validateCoord(dimension, x, y, z);
        // Appelle une méthode
        final int oldValue = get(x, y, z);
        // Appelle une méthode
        final int newValue = operator.applyAsInt(oldValue);
        // Embranchement : vérifie une condition
        if (oldValue != newValue) set(x, y, z, newValue);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void replaceAll(EntryFunction function) {
        // Appelle une méthode
        int[] cache = WRITE_CACHE.get();
        // Appelle une méthode
        AtomicInteger arrayIndex = new AtomicInteger();
        // Appelle une méthode
        AtomicInteger count = new AtomicInteger();
        // Début d'une méthode/d'un bloc
        getAll((x, y, z, value) -> {
            // Appelle une méthode
            final int newValue = function.apply(x, y, z, value);
            // Appelle une méthode
            final int index = arrayIndex.getPlain();
            // Appelle une méthode
            arrayIndex.setPlain(index + 1);
            // Affecte une valeur
            cache[index] = newValue;
            // Embranchement : vérifie une condition
            if (newValue != 0) count.setPlain(count.getPlain() + 1);
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        assert arrayIndex.getPlain() == maxSize();
        // Update palette content
        // Appelle une méthode
        makeDirect();
        // Appelle une méthode
        updateAll(cache);
        // Accès à l'objet courant/parent
        this.count = count.getPlain();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void copyFrom(Palette source, int offsetX, int offsetY, int offsetZ) {
        // Embranchement : vérifie une condition
        if (offsetX == 0 && offsetY == 0 && offsetZ == 0) {
            // Appelle une méthode
            copyFrom(source);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        final PaletteImpl sourcePalette = (PaletteImpl) source;
        // Appelle une méthode
        final int sourceDimension = sourcePalette.dimension();
        // Appelle une méthode
        final int targetDimension = this.dimension();
        // Embranchement : vérifie une condition
        if (sourceDimension != targetDimension) {
            // Lève une exception
            throw new IllegalArgumentException("Source palette dimension (" + sourceDimension +
                    // Appelle une méthode
                    ") must equal target palette dimension (" + targetDimension + ")");
        // Fin d'un bloc/d'une expression
        }

        // Calculate the actual copy bounds - only copy what fits within target bounds
        // Appelle une méthode
        final int maxX = Math.min(sourceDimension, targetDimension - offsetX);
        // Appelle une méthode
        final int maxY = Math.min(sourceDimension, targetDimension - offsetY);
        // Appelle une méthode
        final int maxZ = Math.min(sourceDimension, targetDimension - offsetZ);

        // Early exit if nothing to copy (offset pushes everything out of bounds)
        // Embranchement : vérifie une condition
        if (maxX <= 0 || maxY <= 0 || maxZ <= 0) {
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Fast path: if source is single-value palette
        // Embranchement : vérifie une condition
        if (sourcePalette.bitsPerEntry == 0) {
            // Fill the region with the single value - optimized loop order
            // Affecte une valeur
            final int value = sourcePalette.count;
            // Appelle une méthode
            final int paletteValue = valueToPaletteIndex(value);

            // Direct write to avoid repeated palette lookups
            // Boucle : répète un bloc
            for (int y = 0; y < maxY; y++) {
                // Affecte une valeur
                final int targetY = offsetY + y;
                // Boucle : répète un bloc
                for (int z = 0; z < maxZ; z++) {
                    // Affecte une valeur
                    final int targetZ = offsetZ + z;
                    // Boucle : répète un bloc
                    for (int x = 0; x < maxX; x++) {
                        // Affecte une valeur
                        final int targetX = offsetX + x;
                        // Appelle une méthode
                        final int oldValue = Palettes.write(targetDimension, bitsPerEntry, values, targetX, targetY, targetZ, paletteValue);
                        // Update count based on air transitions
                        // Appelle une méthode
                        final boolean wasAir = paletteIndexToValue(oldValue) == 0;
                        // Instruction de code
                        final boolean isAir = value == 0;
                        // Embranchement : vérifie une condition
                        if (wasAir != isAir) {
                            // Accès à l'objet courant/parent
                            this.count += wasAir ? 1 : -1;
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Source is empty, fill target region with air
        // Embranchement : vérifie une condition
        if (sourcePalette.count == 0) {
            // Embranchement : vérifie une condition
            if (this.count == 0) return;
            // Appelle une méthode
            final int airPaletteIndex = valueToPaletteIndex(0);
            // Affecte une valeur
            int removedBlocks = 0;
            // Boucle : répète un bloc
            for (int y = 0; y < maxY; y++) {
                // Affecte une valeur
                final int targetY = offsetY + y;
                // Boucle : répète un bloc
                for (int z = 0; z < maxZ; z++) {
                    // Affecte une valeur
                    final int targetZ = offsetZ + z;
                    // Boucle : répète un bloc
                    for (int x = 0; x < maxX; x++) {
                        // Affecte une valeur
                        final int targetX = offsetX + x;
                        // Appelle une méthode
                        final int oldValue = Palettes.write(targetDimension, bitsPerEntry, values, targetX, targetY, targetZ, airPaletteIndex);
                        // Embranchement : vérifie une condition
                        if (paletteIndexToValue(oldValue) != 0) removedBlocks++;
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Accès à l'objet courant/parent
            this.count -= removedBlocks;
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // General case: copy each value individually with bounds checking
        // Use optimized access patterns to minimize cache misses
        // Affecte une valeur
        final long[] sourceValues = sourcePalette.values;
        // Affecte une valeur
        final int sourceBitsPerEntry = sourcePalette.bitsPerEntry;
        // Affecte une valeur
        final int sourceMask = (1 << sourceBitsPerEntry) - 1;
        // Affecte une valeur
        final int sourceValuesPerLong = 64 / sourceBitsPerEntry;
        // Appelle une méthode
        final int sourceDimensionBitCount = MathUtils.bitsToRepresent(sourceDimension - 1);
        // Affecte une valeur
        final int sourceShiftedDimensionBitCount = sourceDimensionBitCount << 1;
        // Appelle une méthode
        final int[] sourcePaletteIds = sourcePalette.hasPalette() ? sourcePalette.paletteToValueList.elements() : null;

        // Affecte une valeur
        int countDelta = 0;
        // Boucle : répète un bloc
        for (int y = 0; y < maxY; y++) {
            // Affecte une valeur
            final int targetY = offsetY + y;
            // Boucle : répète un bloc
            for (int z = 0; z < maxZ; z++) {
                // Affecte une valeur
                final int targetZ = offsetZ + z;
                // Boucle : répète un bloc
                for (int x = 0; x < maxX; x++) {
                    // Affecte une valeur
                    final int targetX = offsetX + x;

                    // Affecte une valeur
                    final int sourceIndex = y << sourceShiftedDimensionBitCount | z << sourceDimensionBitCount | x;
                    // Affecte une valeur
                    final int longIndex = sourceIndex / sourceValuesPerLong;
                    // Affecte une valeur
                    final int bitIndex = (sourceIndex - longIndex * sourceValuesPerLong) * sourceBitsPerEntry;
                    // Affecte une valeur
                    final int sourcePaletteIndex = (int) (sourceValues[longIndex] >> bitIndex) & sourceMask;
                    // Instruction de code
                    final int sourceValue = sourcePaletteIds != null && sourcePaletteIndex < sourcePaletteIds.length ?
                            // Instruction de code
                            sourcePaletteIds[sourcePaletteIndex] : sourcePaletteIndex;

                    // Convert to target palette index and write
                    // Appelle une méthode
                    final int targetPaletteIndex = valueToPaletteIndex(sourceValue);
                    // Appelle une méthode
                    final int oldValue = Palettes.write(targetDimension, bitsPerEntry, values, targetX, targetY, targetZ, targetPaletteIndex);

                    // Update count
                    // Appelle une méthode
                    final boolean wasAir = paletteIndexToValue(oldValue) == 0;
                    // Instruction de code
                    final boolean isAir = sourceValue == 0;
                    // Embranchement : vérifie une condition
                    if (wasAir != isAir) {
                        // Affecte une valeur
                        countDelta += wasAir ? 1 : -1;
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Accès à l'objet courant/parent
        this.count += countDelta;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void copyFrom(Palette source) {
        // Affecte une valeur
        final PaletteImpl sourcePalette = (PaletteImpl) source;
        // Appelle une méthode
        final int sourceDimension = sourcePalette.dimension();
        // Appelle une méthode
        final int targetDimension = this.dimension();
        // Embranchement : vérifie une condition
        if (sourceDimension != targetDimension) {
            // Lève une exception
            throw new IllegalArgumentException("Source palette dimension (" + sourceDimension +
                    // Appelle une méthode
                    ") must equal target palette dimension (" + targetDimension + ")");
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (sourcePalette.bitsPerEntry == 0) {
            // Appelle une méthode
            fill(sourcePalette.count);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (sourcePalette.count == 0) {
            // Appelle une méthode
            fill(0);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Copy
        // Accès à l'objet courant/parent
        this.bitsPerEntry = sourcePalette.bitsPerEntry;
        // Accès à l'objet courant/parent
        this.count = sourcePalette.count;

        // Embranchement : vérifie une condition
        if (sourcePalette.values != null) {
            // Accès à l'objet courant/parent
            this.values = sourcePalette.values.clone();
        // Branche alternative de la condition
        } else {
            // Accès à l'objet courant/parent
            this.values = null;
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (sourcePalette.paletteToValueList != null) {
            // Accès à l'objet courant/parent
            this.paletteToValueList = new IntArrayList(sourcePalette.paletteToValueList);
        // Branche alternative de la condition
        } else {
            // Accès à l'objet courant/parent
            this.paletteToValueList = null;
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (sourcePalette.valueToPaletteMap != null) {
            // Accès à l'objet courant/parent
            this.valueToPaletteMap = new Int2IntOpenHashMap(sourcePalette.valueToPaletteMap);
            // Accès à l'objet courant/parent
            this.valueToPaletteMap.defaultReturnValue(-1);
        // Branche alternative de la condition
        } else {
            // Accès à l'objet courant/parent
            this.valueToPaletteMap = null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int count() {
        // Embranchement : vérifie une condition
        if (bitsPerEntry == 0) {
            // Renvoie une valeur à l'appelant
            return count == 0 ? 0 : maxSize();
        // Branche alternative de la condition
        } else {
            // Renvoie une valeur à l'appelant
            return count;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int count(int value) {
        // Embranchement : vérifie une condition
        if (bitsPerEntry == 0) return count == value ? maxSize() : 0;
        // Embranchement : vérifie une condition
        if (value == 0) return maxSize() - count();
        // Appelle une méthode
        final int queryValue = valueToPalettIndexOrDefault(value);
        // Renvoie une valeur à l'appelant
        return countPaletteIndex(queryValue);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void recount() {
        // Embranchement : vérifie une condition
        if (bitsPerEntry != 0) {
            // Accès à l'objet courant/parent
            this.count = maxSize() - countPaletteIndex(valueToPalettIndexOrDefault(0));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /// Assumes {@link PaletteImpl#bitsPerEntry} != 0
    // Début d'une méthode/d'un bloc
    int countPaletteIndex(int paletteIndex) {
        // Embranchement : vérifie une condition
        if (paletteIndex < 0) return 0;
        // Affecte une valeur
        int result = 0;
        // Appelle une méthode
        final int size = maxSize();
        // Affecte une valeur
        final int bits = bitsPerEntry;
        // Affecte une valeur
        final int valuesPerLong = 64 / bits;
        // Affecte une valeur
        final int mask = (1 << bits) - 1;
        // Boucle : répète un bloc
        for (int i = 0, idx = 0; i < values.length; i++) {
            // Affecte une valeur
            long block = values[i];
            // Appelle une méthode
            int end = Math.min(valuesPerLong, size - idx);
            // Boucle : répète un bloc
            for (int j = 0; j < end; j++, idx++) {
                // Embranchement : vérifie une condition
                if (((int) (block & mask)) == paletteIndex) result++;
                // Instruction de code
                block >>>= bits;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean any(int value) {
        // Embranchement : vérifie une condition
        if (bitsPerEntry == 0) return count == value;
        // Embranchement : vérifie une condition
        if (value == 0) return maxSize() != count;
        // Appelle une méthode
        int queryValue = valueToPalettIndexOrDefault(value);
        // Embranchement : vérifie une condition
        if (queryValue == -1) return false;
        // Scan through the values
        // Appelle une méthode
        final int size = maxSize();
        // Affecte une valeur
        final int bits = bitsPerEntry;
        // Affecte une valeur
        final int valuesPerLong = 64 / bits;
        // Affecte une valeur
        final int mask = (1 << bits) - 1;
        // Boucle : répète un bloc
        for (int i = 0, idx = 0; i < values.length; i++) {
            // Affecte une valeur
            long block = values[i];
            // Appelle une méthode
            int end = Math.min(valuesPerLong, size - idx);
            // Boucle : répète un bloc
            for (int j = 0; j < end; j++, idx++) {
                // Embranchement : vérifie une condition
                if (((int) (block & mask)) == queryValue) return true;
                // Instruction de code
                block >>>= bits;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int bitsPerEntry() {
        // Renvoie une valeur à l'appelant
        return bitsPerEntry;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int dimension() {
        // Renvoie une valeur à l'appelant
        return dimension;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void optimize(Optimization focus) {
        // Affecte une valeur
        final int bitsPerEntry = this.bitsPerEntry;
        // Embranchement : vérifie une condition
        if (bitsPerEntry == 0) {
            // Already optimized (single value)
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Count unique values
        // Appelle une méthode
        IntSet uniqueValues = new IntOpenHashSet();
        // Appelle une méthode
        getAll((x, y, z, value) -> uniqueValues.add(value));
        // Appelle une méthode
        final int uniqueCount = uniqueValues.size();

        // If only one unique value, use fill for maximum optimization
        // Embranchement : vérifie une condition
        if (uniqueCount == 1) {
            // Appelle une méthode
            fill(uniqueValues.iterator().nextInt());
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (focus == Optimization.SPEED) {
            // Speed optimization - use direct storage
            // Appelle une méthode
            makeDirect();
        // Embranchement : vérifie une condition
        } else if (focus == Optimization.SIZE) {
            // Size optimization - calculate minimum bits needed for unique values
            // Appelle une méthode
            final var paletteList = new IntArrayList(uniqueValues);
            // Boucle : répète un bloc
            downsizeWithPalette(paletteList);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean compare(Palette p) {
        // Affecte une valeur
        final PaletteImpl palette = (PaletteImpl) p;
        // Appelle une méthode
        final int dimension = this.dimension();
        // Embranchement : vérifie une condition
        if (palette.dimension() != dimension) return false;
        // Embranchement : vérifie une condition
        if (palette.count != this.count) return false;
        // Embranchement : vérifie une condition
        if (palette.count == 0) return true;
        // Embranchement : vérifie une condition
        if (palette.bitsPerEntry == 0 && this.bitsPerEntry == 0) return true;
        // Boucle : répète un bloc
        for (int y = 0; y < dimension; y++) {
            // Boucle : répète un bloc
            for (int z = 0; z < dimension; z++) {
                // Boucle : répète un bloc
                for (int x = 0; x < dimension; x++) {
                    // Appelle une méthode
                    final int value1 = this.get(x, y, z);
                    // Appelle une méthode
                    final int value2 = palette.get(x, y, z);
                    // Embranchement : vérifie une condition
                    if (value1 != value2) return false;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Palette clone() {
        // Appelle une méthode
        PaletteImpl clone = new PaletteImpl(dimension, minBitsPerEntry, maxBitsPerEntry, directBits);
        // Affecte une valeur
        clone.bitsPerEntry = this.bitsPerEntry;
        // Affecte une valeur
        clone.count = this.count;
        // Embranchement : vérifie une condition
        if (bitsPerEntry == 0) return clone;
        // Appelle une méthode
        clone.values = values.clone();
        // Embranchement : vérifie une condition
        if (paletteToValueList != null) clone.paletteToValueList = paletteToValueList.clone();
        // Embranchement : vérifie une condition
        if (valueToPaletteMap != null) clone.valueToPaletteMap = valueToPaletteMap.clone();
        // Renvoie une valeur à l'appelant
        return clone;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void retrieveAll(EntryConsumer consumer, boolean consumeEmpty) {
        // Embranchement : vérifie une condition
        if (!consumeEmpty && count == 0) return;
        // Affecte une valeur
        final long[] values = this.values;
        // Appelle une méthode
        final int dimension = this.dimension();
        // Affecte une valeur
        final int bitsPerEntry = this.bitsPerEntry;
        // Affecte une valeur
        final int magicMask = (1 << bitsPerEntry) - 1;
        // Affecte une valeur
        final int valuesPerLong = 64 / bitsPerEntry;
        // Appelle une méthode
        final int size = maxSize();
        // Affecte une valeur
        final int dimensionMinus = dimension - 1;
        // Appelle une méthode
        final int[] ids = hasPalette() ? paletteToValueList.elements() : null;
        // Appelle une méthode
        final int dimensionBitCount = MathUtils.bitsToRepresent(dimensionMinus);
        // Affecte une valeur
        final int shiftedDimensionBitCount = dimensionBitCount << 1;
        // Boucle : répète un bloc
        for (int i = 0; i < values.length; i++) {
            // Affecte une valeur
            final long value = values[i];
            // Affecte une valeur
            final int startIndex = i * valuesPerLong;
            // Appelle une méthode
            final int endIndex = Math.min(startIndex + valuesPerLong, size);
            // Boucle : répète un bloc
            for (int index = startIndex; index < endIndex; index++) {
                // Affecte une valeur
                final int bitIndex = (index - startIndex) * bitsPerEntry;
                // Affecte une valeur
                final int paletteIndex = (int) (value >> bitIndex & magicMask);
                // Embranchement : vérifie une condition
                if (consumeEmpty || paletteIndex != 0) {
                    // Affecte une valeur
                    final int y = index >> shiftedDimensionBitCount;
                    // Affecte une valeur
                    final int z = index >> dimensionBitCount & dimensionMinus;
                    // Affecte une valeur
                    final int x = index & dimensionMinus;
                    // Instruction de code
                    final int result = ids != null && paletteIndex < ids.length ? ids[paletteIndex] : paletteIndex;
                    // Appelle une méthode
                    consumer.accept(x, y, z, result);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void updateAll(int[] paletteValues) {
        // Appelle une méthode
        final int size = maxSize();
        // Instruction de code
        assert paletteValues.length >= size;
        // Affecte une valeur
        final int bitsPerEntry = this.bitsPerEntry;
        // Affecte une valeur
        final int valuesPerLong = 64 / bitsPerEntry;
        // Affecte une valeur
        final long clear = (1L << bitsPerEntry) - 1L;
        // Affecte une valeur
        final long[] values = this.values;
        // Boucle : répète un bloc
        for (int i = 0; i < values.length; i++) {
            // Affecte une valeur
            long block = values[i];
            // Affecte une valeur
            final int startIndex = i * valuesPerLong;
            // Appelle une méthode
            final int endIndex = Math.min(startIndex + valuesPerLong, size);
            // Boucle : répète un bloc
            for (int index = startIndex; index < endIndex; index++) {
                // Affecte une valeur
                final int bitIndex = (index - startIndex) * bitsPerEntry;
                // Affecte une valeur
                block = block & ~(clear << bitIndex) | ((long) paletteValues[index] << bitIndex);
            // Fin d'un bloc/d'une expression
            }
            // Affecte une valeur
            values[i] = block;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /// Assumes {@link PaletteImpl#bitsPerEntry} != 0
    // Début d'une méthode/d'un bloc
    private void downsizeWithPalette(IntArrayList palette) {
        // Affecte une valeur
        final byte bpe = this.bitsPerEntry;
        // Appelle une méthode
        final byte newBpe = (byte) Math.max(MathUtils.bitsToRepresent(palette.size() - 1), minBitsPerEntry);
        // Embranchement : vérifie une condition
        if (newBpe >= bpe || newBpe > maxBitsPerEntry) return;

        // Fill new palette <-> value objects
        // Appelle une méthode
        final Int2IntOpenHashMap newValueToPaletteMap = new Int2IntOpenHashMap(palette.size());
        // Appelle une méthode
        newValueToPaletteMap.defaultReturnValue(-1);
        // Appelle une méthode
        final AtomicInteger index = new AtomicInteger();
        // Début d'une méthode/d'un bloc
        palette.forEach(v -> {
            // Appelle une méthode
            final int plainIndex = index.getPlain();
            // Appelle une méthode
            newValueToPaletteMap.put(v, plainIndex);
            // Appelle une méthode
            index.setPlain(plainIndex + 1);
        // Fin d'un bloc/d'une expression
        });

        // Embranchement : vérifie une condition
        if (!hasPalette()) {
            // Accès à l'objet courant/parent
            this.values = Palettes.remap(dimension, bpe, newBpe, values, newValueToPaletteMap::get);
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            final IntArrayList transformList = new IntArrayList(paletteToValueList.size());
            // Appelle une méthode
            paletteToValueList.forEach(value -> transformList.add(newValueToPaletteMap.get(value)));
            // Appelle une méthode
            final int[] transformArray = transformList.elements();
            // Accès à l'objet courant/parent
            this.values = Palettes.remap(dimension, bpe, newBpe, values, value -> transformArray[value]);
        // Fin d'un bloc/d'une expression
        }

        // Accès à l'objet courant/parent
        this.bitsPerEntry = newBpe;
        // Accès à l'objet courant/parent
        this.valueToPaletteMap = newValueToPaletteMap;
        // Accès à l'objet courant/parent
        this.paletteToValueList = palette;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void makeDirect() {
        // Embranchement : vérifie une condition
        if (!hasPalette()) return;
        // Embranchement : vérifie une condition
        if (bitsPerEntry == 0) {
            // Affecte une valeur
            final int fillValue = this.count;
            // Accès à l'objet courant/parent
            this.values = new long[arrayLength(dimension, directBits)];
            // Embranchement : vérifie une condition
            if (fillValue != 0) {
                // Appelle une méthode
                Palettes.fill(directBits, this.values, fillValue);
                // Accès à l'objet courant/parent
                this.count = maxSize();
            // Fin d'un bloc/d'une expression
            }
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            final int[] ids = paletteToValueList.elements();
            // Accès à l'objet courant/parent
            this.values = Palettes.remap(dimension, bitsPerEntry, directBits, values, v -> ids[v]);
        // Fin d'un bloc/d'une expression
        }
        // Accès à l'objet courant/parent
        this.paletteToValueList = null;
        // Accès à l'objet courant/parent
        this.valueToPaletteMap = null;
        // Accès à l'objet courant/parent
        this.bitsPerEntry = directBits;
    // Fin d'un bloc/d'une expression
    }

    /// Assumes {@link PaletteImpl#bitsPerEntry} != 0
    // Début d'une méthode/d'un bloc
    void upsize() {
        // Affecte une valeur
        final byte bpe = this.bitsPerEntry;
        // Affecte une valeur
        byte newBpe = (byte) (bpe + 1);
        // Embranchement : vérifie une condition
        if (newBpe > maxBitsPerEntry) {
            // Appelle une méthode
            makeDirect();
        // Branche alternative de la condition
        } else {
            // Accès à l'objet courant/parent
            this.values = Palettes.remap(dimension, bpe, newBpe, values, (v) -> v);
            // Accès à l'objet courant/parent
            this.bitsPerEntry = newBpe;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /// Assumes {@link PaletteImpl#bitsPerEntry} == 0
    // Début d'une méthode/d'un bloc
    void initIndirect() {
        // Affecte une valeur
        final int fillValue = this.count;
        // Accès à l'objet courant/parent
        this.valueToPaletteMap = new Int2IntOpenHashMap();
        // Accès à l'objet courant/parent
        this.valueToPaletteMap.defaultReturnValue(-1);
        // Accès à l'objet courant/parent
        this.paletteToValueList = new IntArrayList();
        // Accès à l'objet courant/parent
        this.valueToPaletteMap.put(fillValue, 0);
        // Appelle une méthode
        paletteToValueList.add(fillValue);
        // Accès à l'objet courant/parent
        this.bitsPerEntry = minBitsPerEntry;
        // Accès à l'objet courant/parent
        this.values = new long[arrayLength(dimension, minBitsPerEntry)];
        // Accès à l'objet courant/parent
        this.count = fillValue == 0 ? 0 : maxSize();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int paletteIndexToValue(int value) {
        // Renvoie une valeur à l'appelant
        return hasPalette() ? paletteToValueList.elements()[value] : value;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int valueToPaletteIndex(int value) {
        // Embranchement : vérifie une condition
        if (!hasPalette()) return value;
        // Embranchement : vérifie une condition
        if (values == null) initIndirect();

        // Appelle une méthode
        final int lastPaletteIndex = this.paletteToValueList.size();
        // Appelle une méthode
        final int lookup = valueToPaletteMap.putIfAbsent(value, lastPaletteIndex);
        // Embranchement : vérifie une condition
        if (lookup != -1) return lookup;
        // Embranchement : vérifie une condition
        if (lastPaletteIndex >= maxPaletteSize(bitsPerEntry)) {
            // Palette is full, must resize
            // Appelle une méthode
            upsize();
            // Embranchement : vérifie une condition
            if (!hasPalette()) return value;
        // Fin d'un bloc/d'une expression
        }
        // Accès à l'objet courant/parent
        this.paletteToValueList.add(value);
        // Renvoie une valeur à l'appelant
        return lastPaletteIndex;
    // Fin d'un bloc/d'une expression
    }

    /// Assumes {@link PaletteImpl#bitsPerEntry} != 0
    // Début d'une méthode/d'un bloc
    int valueToPalettIndexOrDefault(int value) {
        // Renvoie une valeur à l'appelant
        return hasPalette() ? valueToPaletteMap.get(value) : value;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int singleValue() {
        // Renvoie une valeur à l'appelant
        return bitsPerEntry == 0 ? count : -1;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public long @Nullable [] indexedValues() {
        // Renvoie une valeur à l'appelant
        return values;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    boolean hasPalette() {
        // Renvoie une valeur à l'appelant
        return bitsPerEntry <= maxBitsPerEntry;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void validateCoord(int dimension, int x, int y, int z) {
        // Embranchement : vérifie une condition
        if (x < 0 || y < 0 || z < 0)
            // Lève une exception
            throw new IllegalArgumentException("Coordinates must be non-negative");
        // Embranchement : vérifie une condition
        if (x >= dimension || y >= dimension || z >= dimension)
            // Lève une exception
            throw new IllegalArgumentException("Coordinates must be less than the dimension size, got " + x + ", " + y + ", " + z + " for dimension " + dimension);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void validateDimension(int dimension) {
        // Embranchement : vérifie une condition
        if (dimension <= 1 || (dimension & dimension - 1) != 0)
            // Lève une exception
            throw new IllegalArgumentException("Dimension must be a positive power of 2, got " + dimension);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
