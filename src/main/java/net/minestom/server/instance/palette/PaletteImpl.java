// Package declaration for this file
package net.minestom.server.instance.palette;

// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
// Import of a required class
import it.unimi.dsi.fastutil.ints.IntArrayList;
// Import of a required class
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
// Import of a required class
import it.unimi.dsi.fastutil.ints.IntSet;
// Import of a required class
import net.minestom.server.utils.MathUtils;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.Arrays;
// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;
// Import of a required class
import java.util.function.IntUnaryOperator;

// Static import of a member
import static net.minestom.server.coordinate.CoordConversion.SECTION_BLOCK_COUNT;
// Static import of a member
import static net.minestom.server.instance.palette.Palettes.*;

// Type declaration (class/interface/enum/record)
final class PaletteImpl implements Palette {
    // Calls a method
    private static final ThreadLocal<int[]> WRITE_CACHE = ThreadLocal.withInitial(() -> new int[SECTION_BLOCK_COUNT]);
    // Code statement
    final byte dimension, minBitsPerEntry, maxBitsPerEntry, directBits;

    // Assigns a value
    byte bitsPerEntry = 0;
    // Assigns a value
    int count = 0; // Serve as the single value if bitsPerEntry == 0

    // Code statement
    long @UnknownNullability [] values; // null when bitsPerEntry == 0
    // palette index = value
    // Annotation for the following element
    @UnknownNullability
    // Code statement
    IntArrayList paletteToValueList; // null when using direct mode (bitsPerEntry > maxBitsPerEntry)
    // value = palette index
    // Annotation for the following element
    @UnknownNullability
    // Code statement
    Int2IntOpenHashMap valueToPaletteMap; // null when using direct mode (bitsPerEntry > maxBitsPerEntry)

    // Start of a method/block
    PaletteImpl(byte dimension, byte minBitsPerEntry, byte maxBitsPerEntry, byte directBits) {
        // Calls a method
        validateDimension(dimension);
        // Access to the current/parent object
        this.dimension = dimension;
        // Access to the current/parent object
        this.minBitsPerEntry = minBitsPerEntry;
        // Access to the current/parent object
        this.maxBitsPerEntry = maxBitsPerEntry;
        // Access to the current/parent object
        this.directBits = directBits;
    // End of a block/expression
    }

    // Start of a method/block
    PaletteImpl(byte dimension, byte minBitsPerEntry, byte maxBitsPerEntry, byte directBits, byte bitsPerEntry) {
        // Calls a method
        this(dimension, minBitsPerEntry, maxBitsPerEntry, directBits);

        // Access to the current/parent object
        this.bitsPerEntry = bitsPerEntry;
        // Branch: checks a condition
        if (bitsPerEntry != 0) {
            // Access to the current/parent object
            this.values = new long[arrayLength(dimension, bitsPerEntry)];

            // Branch: checks a condition
            if (hasPalette()) {
                // Access to the current/parent object
                this.paletteToValueList = new IntArrayList();
                // Access to the current/parent object
                this.valueToPaletteMap = new Int2IntOpenHashMap();
                // Access to the current/parent object
                this.valueToPaletteMap.defaultReturnValue(-1);
                // Access to the current/parent object
                this.paletteToValueList.add(0);
                // Access to the current/parent object
                this.valueToPaletteMap.put(0, 0);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int get(int x, int y, int z) {
        // Calls a method
        validateCoord(dimension, x, y, z);
        // Branch: checks a condition
        if (bitsPerEntry == 0) return count;
        // Calls a method
        final int value = read(dimension(), bitsPerEntry, values, x, y, z);
        // Returns a value to the caller
        return paletteIndexToValue(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void getAll(EntryConsumer consumer) {
        // Branch: checks a condition
        if (bitsPerEntry == 0) {
            // Calls a method
            Palettes.getAllFill(dimension, count, consumer);
        // Alternative branch of the condition
        } else {
            // Calls a method
            retrieveAll(consumer, true);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void getAllPresent(EntryConsumer consumer) {
        // Branch: checks a condition
        if (bitsPerEntry == 0) {
            // Branch: checks a condition
            if (count != 0) Palettes.getAllFill(dimension, count, consumer);
        // Alternative branch of the condition
        } else {
            // Calls a method
            retrieveAll(consumer, false);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int height(int x, int z, EntryPredicate predicate) {
        // Calls a method
        validateCoord(dimension, x, 0, z);
        // Assigns a value
        final int dimension = this.dimension;
        // Assigns a value
        final int startY = dimension - 1;
        // Branch: checks a condition
        if (bitsPerEntry == 0) return predicate.get(x, startY, z, count) ? startY : -1;
        // Assigns a value
        final long[] values = this.values;
        // Assigns a value
        final int bitsPerEntry = this.bitsPerEntry;
        // Assigns a value
        final int valuesPerLong = 64 / bitsPerEntry;
        // Calls a method
        final int mask = (1 << bitsPerEntry) - 1;
        // Calls a method
        final int @Nullable [] paletteIds = hasPalette() ? paletteToValueList.elements() : null;
        // Loop: repeats a block
        for (int y = startY; y >= 0; y--) {
            // Calls a method
            final int index = sectionIndex(dimension, x, y, z);
            // Assigns a value
            final int longIndex = index / valuesPerLong;
            // Calls a method
            final int bitIndex = (index % valuesPerLong) * bitsPerEntry;
            // Calls a method
            final int paletteIndex = (int) (values[longIndex] >> bitIndex) & mask;
            // Assigns a value
            final int value = paletteIds != null && paletteIndex < paletteIds.length ? paletteIds[paletteIndex]
                    // Code statement
                    : paletteIndex;
            // Branch: checks a condition
            if (predicate.get(x, y, z, value)) return y;
        // End of a block/expression
        }
        // Returns a value to the caller
        return -1;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void set(int x, int y, int z, int value) {
        // Calls a method
        validateCoord(dimension, x, y, z);
        // Calls a method
        final int paletteIndex = valueToPaletteIndex(value);
        // Calls a method
        final int oldValue = Palettes.write(dimension(), bitsPerEntry, values, x, y, z, paletteIndex);
        // Check if block count needs to be updated
        // Calls a method
        final boolean currentAir = paletteIndexToValue(oldValue) == 0;
        // Branch: checks a condition
        if (currentAir != (value == 0)) this.count += currentAir ? 1 : -1;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void fill(int value) {
        // Access to the current/parent object
        this.bitsPerEntry = 0;
        // Access to the current/parent object
        this.count = value;
        // Access to the current/parent object
        this.values = null;
        // Access to the current/parent object
        this.paletteToValueList = null;
        // Access to the current/parent object
        this.valueToPaletteMap = null;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void load(int[] palette, long[] values) {
        // Calls a method
        int bpe = palette.length <= 1 ? 0 : MathUtils.bitsToRepresent(palette.length - 1);
        // Calls a method
        bpe = Math.max(minBitsPerEntry, bpe);
        // Assigns a value
        boolean useDirectMode = bpe > maxBitsPerEntry;
        // Branch: checks a condition
        if (useDirectMode) bpe = directBits;
        // Access to the current/parent object
        this.bitsPerEntry = (byte) bpe;

        // Branch: checks a condition
        if (useDirectMode) {
            // Direct mode: convert from palette indices to direct values
            // Access to the current/parent object
            this.paletteToValueList = null;
            // Access to the current/parent object
            this.valueToPaletteMap = null;
            // Access to the current/parent object
            this.values = new long[arrayLength(dimension, directBits)];

            // Calls a method
            final int originalBpe = palette.length <= 1 ? 0 : MathUtils.bitsToRepresent(palette.length - 1);
            // Calls a method
            final int actualOriginalBpe = Math.max(minBitsPerEntry, originalBpe);
            // Calls a method
            final int originalMask = (1 << actualOriginalBpe) - 1;
            // Assigns a value
            final int originalValuesPerLong = 64 / actualOriginalBpe;

            // Assigns a value
            int nonZeroCount = 0;
            // Assigns a value
            final int dimension = this.dimension;
            // Loop: repeats a block
            for (int y = 0; y < dimension; y++) {
                // Loop: repeats a block
                for (int z = 0; z < dimension; z++) {
                    // Loop: repeats a block
                    for (int x = 0; x < dimension; x++) {
                        // Calls a method
                        final int index = sectionIndex(dimension, x, y, z);

                        // Read palette index from original values
                        // Assigns a value
                        final int longIndex = index / originalValuesPerLong;
                        // Calls a method
                        final int bitIndex = (index % originalValuesPerLong) * actualOriginalBpe;
                        // Calls a method
                        final int paletteIndex = (int) (values[longIndex] >> bitIndex) & originalMask;

                        // Convert to direct value
                        // Assigns a value
                        final int directValue = paletteIndex < palette.length ? palette[paletteIndex] : 0;
                        // Branch: checks a condition
                        if (directValue != 0) nonZeroCount++;

                        // Write direct value to new values array using coordinates
                        // Calls a method
                        write(dimension, directBits, this.values, x, y, z, directValue);
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Access to the current/parent object
            this.count = nonZeroCount;
        // Alternative branch of the condition
        } else {
            // Indirect mode: use palette
            // Access to the current/parent object
            this.paletteToValueList = new IntArrayList(palette);
            // Access to the current/parent object
            this.valueToPaletteMap = new Int2IntOpenHashMap(palette.length);
            // Access to the current/parent object
            this.valueToPaletteMap.defaultReturnValue(-1);
            // Loop: repeats a block
            for (int i = 0; i < palette.length; i++) {
                // Access to the current/parent object
                this.valueToPaletteMap.put(palette[i], i);
            // End of a block/expression
            }
            // Access to the current/parent object
            this.values = Arrays.copyOf(values, arrayLength(dimension, bitsPerEntry));
            // Calls a method
            recount();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void offset(int offset) {
        // Branch: checks a condition
        if (offset == 0) return;
        // Branch: checks a condition
        if (bitsPerEntry == 0) {
            // Access to the current/parent object
            this.count += offset;
        // Alternative branch of the condition
        } else {
            // Calls a method
            replaceAll((x, y, z, value) -> value + offset);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void replace(int oldValue, int newValue) {
        // Branch: checks a condition
        if (oldValue == newValue) return;
        // Branch: checks a condition
        if (bitsPerEntry == 0) {
            // Branch: checks a condition
            if (oldValue == count) fill(newValue);
        // Alternative branch of the condition
        } else {
            // Branch: checks a condition
            if (hasPalette()) {
                // Calls a method
                final int index = valueToPaletteMap.get(oldValue);
                // Branch: checks a condition
                if (index == -1) return; // Old value not present in palette
                // Calls a method
                final int newIndex = valueToPaletteMap.get(newValue);
                // Assigns a value
                final boolean countUpdate = newValue == 0 || oldValue == 0;
                // Code statement
                final int count;
                // Branch: checks a condition
                if (newIndex == -1) {
                    // Calls a method
                    count = countUpdate ? countPaletteIndex(index) : -1;
                    // Branch: checks a condition
                    if (count == 0) return; // No blocks to replace
                    // Calls a method
                    valueToPaletteMap.remove(oldValue);
                    // Calls a method
                    paletteToValueList.set(index, newValue);
                    // Calls a method
                    valueToPaletteMap.put(newValue, index);
                // Alternative branch of the condition
                } else {
                    // Calls a method
                    count = replacePaletteIndex(index, newIndex);
                    // Branch: checks a condition
                    if (count == 0) return; // No blocks to replace
                    // Calls a method
                    valueToPaletteMap.remove(oldValue);
                // End of a block/expression
                }
                // Update count
                // Branch: checks a condition
                if (newValue == 0) {
                    // Access to the current/parent object
                    this.count -= count; // Replacing with air
                // Branch: checks a condition
                } else if (oldValue == 0) {
                    // Access to the current/parent object
                    this.count += count; // Replacing air with a block
                // End of a block/expression
                }
            // Alternative branch of the condition
            } else {
                // Calls a method
                replaceAll((x, y, z, value) -> value == oldValue ? newValue : value);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setAll(EntrySupplier supplier) {
        // Calls a method
        int[] cache = WRITE_CACHE.get();
        // Calls a method
        final int dimension = dimension();
        // Fill cache with values
        // Assigns a value
        int fillValue = -1;
        // Assigns a value
        int count = 0;
        // Assigns a value
        int index = 0;
        // Loop: repeats a block
        for (int y = 0; y < dimension; y++) {
            // Loop: repeats a block
            for (int z = 0; z < dimension; z++) {
                // Loop: repeats a block
                for (int x = 0; x < dimension; x++) {
                    // Calls a method
                    int value = supplier.get(x, y, z);
                    // Support for fill fast exit if the supplier returns a constant value
                    // Branch: checks a condition
                    if (fillValue != -2) {
                        // Branch: checks a condition
                        if (fillValue == -1) {
                            // Assigns a value
                            fillValue = value;
                        // Branch: checks a condition
                        } else if (fillValue != value) {
                            // Assigns a value
                            fillValue = -2;
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                    // Set value in cache
                    // Branch: checks a condition
                    if (value != 0) count++;
                    // Assigns a value
                    cache[index++] = value;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        assert index == maxSize();
        // Update palette content
        // Branch: checks a condition
        if (fillValue < 0) {
            // Calls a method
            makeDirect();
            // Calls a method
            updateAll(cache);
            // Access to the current/parent object
            this.count = count;
        // Alternative branch of the condition
        } else {
            // Calls a method
            fill(fillValue);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void replace(int x, int y, int z, IntUnaryOperator operator) {
        // Calls a method
        validateCoord(dimension, x, y, z);
        // Calls a method
        final int oldValue = get(x, y, z);
        // Calls a method
        final int newValue = operator.applyAsInt(oldValue);
        // Branch: checks a condition
        if (oldValue != newValue) set(x, y, z, newValue);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void replaceAll(EntryFunction function) {
        // Calls a method
        int[] cache = WRITE_CACHE.get();
        // Calls a method
        AtomicInteger arrayIndex = new AtomicInteger();
        // Calls a method
        AtomicInteger count = new AtomicInteger();
        // Start of a method/block
        getAll((x, y, z, value) -> {
            // Calls a method
            final int newValue = function.apply(x, y, z, value);
            // Calls a method
            final int index = arrayIndex.getPlain();
            // Calls a method
            arrayIndex.setPlain(index + 1);
            // Assigns a value
            cache[index] = newValue;
            // Branch: checks a condition
            if (newValue != 0) count.setPlain(count.getPlain() + 1);
        // End of a block/expression
        });
        // Calls a method
        assert arrayIndex.getPlain() == maxSize();
        // Update palette content
        // Calls a method
        makeDirect();
        // Calls a method
        updateAll(cache);
        // Access to the current/parent object
        this.count = count.getPlain();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void copyFrom(Palette source, int offsetX, int offsetY, int offsetZ) {
        // Branch: checks a condition
        if (offsetX == 0 && offsetY == 0 && offsetZ == 0) {
            // Calls a method
            copyFrom(source);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        final PaletteImpl sourcePalette = (PaletteImpl) source;
        // Calls a method
        final int sourceDimension = sourcePalette.dimension();
        // Calls a method
        final int targetDimension = this.dimension();
        // Branch: checks a condition
        if (sourceDimension != targetDimension) {
            // Throws an exception
            throw new IllegalArgumentException("Source palette dimension (" + sourceDimension +
                    // Calls a method
                    ") must equal target palette dimension (" + targetDimension + ")");
        // End of a block/expression
        }

        // Calculate the actual copy bounds - only copy what fits within target bounds
        // Calls a method
        final int maxX = Math.min(sourceDimension, targetDimension - offsetX);
        // Calls a method
        final int maxY = Math.min(sourceDimension, targetDimension - offsetY);
        // Calls a method
        final int maxZ = Math.min(sourceDimension, targetDimension - offsetZ);

        // Early exit if nothing to copy (offset pushes everything out of bounds)
        // Branch: checks a condition
        if (maxX <= 0 || maxY <= 0 || maxZ <= 0) {
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Fast path: if source is single-value palette
        // Branch: checks a condition
        if (sourcePalette.bitsPerEntry == 0) {
            // Fill the region with the single value - optimized loop order
            // Assigns a value
            final int value = sourcePalette.count;
            // Calls a method
            final int paletteValue = valueToPaletteIndex(value);

            // Direct write to avoid repeated palette lookups
            // Loop: repeats a block
            for (int y = 0; y < maxY; y++) {
                // Assigns a value
                final int targetY = offsetY + y;
                // Loop: repeats a block
                for (int z = 0; z < maxZ; z++) {
                    // Assigns a value
                    final int targetZ = offsetZ + z;
                    // Loop: repeats a block
                    for (int x = 0; x < maxX; x++) {
                        // Assigns a value
                        final int targetX = offsetX + x;
                        // Calls a method
                        final int oldValue = Palettes.write(targetDimension, bitsPerEntry, values, targetX, targetY, targetZ, paletteValue);
                        // Update count based on air transitions
                        // Calls a method
                        final boolean wasAir = paletteIndexToValue(oldValue) == 0;
                        // Assigns a value
                        final boolean isAir = value == 0;
                        // Branch: checks a condition
                        if (wasAir != isAir) {
                            // Access to the current/parent object
                            this.count += wasAir ? 1 : -1;
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Source is empty, fill target region with air
        // Branch: checks a condition
        if (sourcePalette.count == 0) {
            // Branch: checks a condition
            if (this.count == 0) return;
            // Calls a method
            final int airPaletteIndex = valueToPaletteIndex(0);
            // Assigns a value
            int removedBlocks = 0;
            // Loop: repeats a block
            for (int y = 0; y < maxY; y++) {
                // Assigns a value
                final int targetY = offsetY + y;
                // Loop: repeats a block
                for (int z = 0; z < maxZ; z++) {
                    // Assigns a value
                    final int targetZ = offsetZ + z;
                    // Loop: repeats a block
                    for (int x = 0; x < maxX; x++) {
                        // Assigns a value
                        final int targetX = offsetX + x;
                        // Calls a method
                        final int oldValue = Palettes.write(targetDimension, bitsPerEntry, values, targetX, targetY, targetZ, airPaletteIndex);
                        // Branch: checks a condition
                        if (paletteIndexToValue(oldValue) != 0) removedBlocks++;
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Access to the current/parent object
            this.count -= removedBlocks;
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // General case: copy each value individually with bounds checking
        // Use optimized access patterns to minimize cache misses
        // Assigns a value
        final long[] sourceValues = sourcePalette.values;
        // Assigns a value
        final int sourceBitsPerEntry = sourcePalette.bitsPerEntry;
        // Calls a method
        final int sourceMask = (1 << sourceBitsPerEntry) - 1;
        // Assigns a value
        final int sourceValuesPerLong = 64 / sourceBitsPerEntry;
        // Calls a method
        final int sourceDimensionBitCount = MathUtils.bitsToRepresent(sourceDimension - 1);
        // Assigns a value
        final int sourceShiftedDimensionBitCount = sourceDimensionBitCount << 1;
        // Calls a method
        final int @Nullable [] sourcePaletteIds = sourcePalette.hasPalette() ? sourcePalette.paletteToValueList.elements() : null;

        // Assigns a value
        int countDelta = 0;
        // Loop: repeats a block
        for (int y = 0; y < maxY; y++) {
            // Assigns a value
            final int targetY = offsetY + y;
            // Loop: repeats a block
            for (int z = 0; z < maxZ; z++) {
                // Assigns a value
                final int targetZ = offsetZ + z;
                // Loop: repeats a block
                for (int x = 0; x < maxX; x++) {
                    // Assigns a value
                    final int targetX = offsetX + x;

                    // Assigns a value
                    final int sourceIndex = y << sourceShiftedDimensionBitCount | z << sourceDimensionBitCount | x;
                    // Assigns a value
                    final int longIndex = sourceIndex / sourceValuesPerLong;
                    // Calls a method
                    final int bitIndex = (sourceIndex - longIndex * sourceValuesPerLong) * sourceBitsPerEntry;
                    // Calls a method
                    final int sourcePaletteIndex = (int) (sourceValues[longIndex] >> bitIndex) & sourceMask;
                    // Assigns a value
                    final int sourceValue = sourcePaletteIds != null && sourcePaletteIndex < sourcePaletteIds.length ?
                            // Code statement
                            sourcePaletteIds[sourcePaletteIndex] : sourcePaletteIndex;

                    // Convert to target palette index and write
                    // Calls a method
                    final int targetPaletteIndex = valueToPaletteIndex(sourceValue);
                    // Calls a method
                    final int oldValue = Palettes.write(targetDimension, bitsPerEntry, values, targetX, targetY, targetZ, targetPaletteIndex);

                    // Update count
                    // Calls a method
                    final boolean wasAir = paletteIndexToValue(oldValue) == 0;
                    // Assigns a value
                    final boolean isAir = sourceValue == 0;
                    // Branch: checks a condition
                    if (wasAir != isAir) {
                        // Code statement
                        countDelta += wasAir ? 1 : -1;
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Access to the current/parent object
        this.count += countDelta;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void copyFrom(Palette source) {
        // Calls a method
        final PaletteImpl sourcePalette = (PaletteImpl) source;
        // Calls a method
        final int sourceDimension = sourcePalette.dimension();
        // Calls a method
        final int targetDimension = this.dimension();
        // Branch: checks a condition
        if (sourceDimension != targetDimension) {
            // Throws an exception
            throw new IllegalArgumentException("Source palette dimension (" + sourceDimension +
                    // Calls a method
                    ") must equal target palette dimension (" + targetDimension + ")");
        // End of a block/expression
        }

        // Branch: checks a condition
        if (sourcePalette.bitsPerEntry == 0) {
            // Calls a method
            fill(sourcePalette.count);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Branch: checks a condition
        if (sourcePalette.count == 0) {
            // Calls a method
            fill(0);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Copy
        // Access to the current/parent object
        this.bitsPerEntry = sourcePalette.bitsPerEntry;
        // Access to the current/parent object
        this.count = sourcePalette.count;

        // Branch: checks a condition
        if (sourcePalette.values != null) {
            // Access to the current/parent object
            this.values = sourcePalette.values.clone();
        // Alternative branch of the condition
        } else {
            // Access to the current/parent object
            this.values = null;
        // End of a block/expression
        }

        // Branch: checks a condition
        if (sourcePalette.paletteToValueList != null) {
            // Access to the current/parent object
            this.paletteToValueList = new IntArrayList(sourcePalette.paletteToValueList);
        // Alternative branch of the condition
        } else {
            // Access to the current/parent object
            this.paletteToValueList = null;
        // End of a block/expression
        }

        // Branch: checks a condition
        if (sourcePalette.valueToPaletteMap != null) {
            // Access to the current/parent object
            this.valueToPaletteMap = new Int2IntOpenHashMap(sourcePalette.valueToPaletteMap);
            // Access to the current/parent object
            this.valueToPaletteMap.defaultReturnValue(-1);
        // Alternative branch of the condition
        } else {
            // Access to the current/parent object
            this.valueToPaletteMap = null;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int count() {
        // Branch: checks a condition
        if (bitsPerEntry == 0) {
            // Returns a value to the caller
            return count == 0 ? 0 : maxSize();
        // Alternative branch of the condition
        } else {
            // Returns a value to the caller
            return count;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int count(int value) {
        // Branch: checks a condition
        if (bitsPerEntry == 0) return count == value ? maxSize() : 0;
        // Branch: checks a condition
        if (value == 0) return maxSize() - count();
        // Calls a method
        final int queryValue = valueToPalettIndexOrDefault(value);
        // Returns a value to the caller
        return countPaletteIndex(queryValue);
    // End of a block/expression
    }

    // Start of a method/block
    void recount() {
        // Branch: checks a condition
        if (bitsPerEntry != 0) {
            // Access to the current/parent object
            this.count = maxSize() - countPaletteIndex(valueToPalettIndexOrDefault(0));
        // End of a block/expression
        }
    // End of a block/expression
    }

    /// Assumes {@link PaletteImpl#bitsPerEntry} != 0
    // Start of a method/block
    int countPaletteIndex(int paletteIndex) {
        // Branch: checks a condition
        if (paletteIndex < 0) return 0;
        // Returns a value to the caller
        return Palettes.countEquals(bitsPerEntry, values, maxSize(), paletteIndex);
    // End of a block/expression
    }

    /// Assumes {@link PaletteImpl#bitsPerEntry} != 0
    // Start of a method/block
    int replacePaletteIndex(int oldPaletteIndex, int newPaletteIndex) {
        // Returns a value to the caller
        return Palettes.replaceEquals(bitsPerEntry, values, maxSize(), oldPaletteIndex, newPaletteIndex);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean any(int value) {
        // Branch: checks a condition
        if (bitsPerEntry == 0) return count == value;
        // Branch: checks a condition
        if (value == 0) return maxSize() != count;
        // Calls a method
        int queryValue = valueToPalettIndexOrDefault(value);
        // Branch: checks a condition
        if (queryValue == -1) return false;
        // Returns a value to the caller
        return Palettes.anyEquals(bitsPerEntry, values, maxSize(), queryValue);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int bitsPerEntry() {
        // Returns a value to the caller
        return bitsPerEntry;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int dimension() {
        // Returns a value to the caller
        return dimension;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void optimize(Optimization focus) {
        // Assigns a value
        final int bitsPerEntry = this.bitsPerEntry;
        // Branch: checks a condition
        if (bitsPerEntry == 0) {
            // Already optimized (single value)
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Count unique values
        // Calls a method
        IntSet uniqueValues = new IntOpenHashSet();
        // Calls a method
        getAll((x, y, z, value) -> uniqueValues.add(value));
        // Calls a method
        final int uniqueCount = uniqueValues.size();

        // If only one unique value, use fill for maximum optimization
        // Branch: checks a condition
        if (uniqueCount == 1) {
            // Calls a method
            fill(uniqueValues.iterator().nextInt());
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Branch: checks a condition
        if (focus == Optimization.SPEED) {
            // Speed optimization - use direct storage
            // Calls a method
            makeDirect();
        // Branch: checks a condition
        } else if (focus == Optimization.SIZE) {
            // Size optimization - calculate minimum bits needed for unique values
            // Calls a method
            final var paletteList = new IntArrayList(uniqueValues);
            // Calls a method
            downsizeWithPalette(paletteList);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean compare(Palette p) {
        // Calls a method
        final PaletteImpl palette = (PaletteImpl) p;
        // Calls a method
        final int dimension = this.dimension();
        // Branch: checks a condition
        if (palette.dimension() != dimension) return false;
        // Branch: checks a condition
        if (palette.count != this.count) return false;
        // Branch: checks a condition
        if (palette.count == 0) return true;
        // Branch: checks a condition
        if (palette.bitsPerEntry == 0 && this.bitsPerEntry == 0) return true;
        // Assigns a value
        final long[] thisValues = this.values;
        // Assigns a value
        final long[] thatValues = palette.values;
        // Assigns a value
        final int thisBpe = this.bitsPerEntry;
        // Assigns a value
        final int thatBpe = palette.bitsPerEntry;
        // Loop: repeats a block
        for (int y = 0; y < dimension; y++) {
            // Loop: repeats a block
            for (int z = 0; z < dimension; z++) {
                // Loop: repeats a block
                for (int x = 0; x < dimension; x++) {
                    // Assigns a value
                    final int v1 = thisBpe == 0 ? this.count
                            // Calls a method
                            : paletteIndexToValue(read(dimension, thisBpe, thisValues, x, y, z));
                    // Assigns a value
                    final int v2 = thatBpe == 0 ? palette.count
                            // Calls a method
                            : palette.paletteIndexToValue(read(dimension, thatBpe, thatValues, x, y, z));
                    // Branch: checks a condition
                    if (v1 != v2) return false;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    // Annotation for the following element
    @Override
    // Start of a method/block
    public Palette clone() {
        // Calls a method
        PaletteImpl clone = new PaletteImpl(dimension, minBitsPerEntry, maxBitsPerEntry, directBits);
        // Assigns a value
        clone.bitsPerEntry = this.bitsPerEntry;
        // Assigns a value
        clone.count = this.count;
        // Branch: checks a condition
        if (bitsPerEntry == 0) return clone;
        // Calls a method
        clone.values = values.clone();
        // Branch: checks a condition
        if (paletteToValueList != null) clone.paletteToValueList = paletteToValueList.clone();
        // Branch: checks a condition
        if (valueToPaletteMap != null) clone.valueToPaletteMap = valueToPaletteMap.clone();
        // Returns a value to the caller
        return clone;
    // End of a block/expression
    }

    // Start of a method/block
    private void retrieveAll(EntryConsumer consumer, boolean consumeEmpty) {
        // Branch: checks a condition
        if (!consumeEmpty && count == 0) return;
        // Assigns a value
        final long[] values = this.values;
        // Calls a method
        final int dimension = this.dimension();
        // Assigns a value
        final int bitsPerEntry = this.bitsPerEntry;
        // Calls a method
        final int magicMask = (1 << bitsPerEntry) - 1;
        // Assigns a value
        final int valuesPerLong = 64 / bitsPerEntry;
        // Calls a method
        final int size = maxSize();
        // Assigns a value
        final int dimensionMinus = dimension - 1;
        // Calls a method
        final int @Nullable [] ids = hasPalette() ? paletteToValueList.elements() : null;
        // Palette index that maps to air (value 0), or -1 when air is absent from the palette.
        // Calls a method
        final int airIndex = consumeEmpty ? -1 : valueToPalettIndexOrDefault(0);
        // Calls a method
        final int dimensionBitCount = MathUtils.bitsToRepresent(dimensionMinus);
        // Assigns a value
        final int shiftedDimensionBitCount = dimensionBitCount << 1;
        // Loop: repeats a block
        for (int i = 0; i < values.length; i++) {
            // Assigns a value
            final long value = values[i];
            // Skip whole longs of air; only valid when air sits at palette index 0
            // Branch: checks a condition
            if (!consumeEmpty && airIndex == 0 && value == 0) continue;
            // Assigns a value
            final int startIndex = i * valuesPerLong;
            // Calls a method
            final int endIndex = Math.min(startIndex + valuesPerLong, size);
            // Loop: repeats a block
            for (int index = startIndex; index < endIndex; index++) {
                // Calls a method
                final int bitIndex = (index - startIndex) * bitsPerEntry;
                // Calls a method
                final int paletteIndex = (int) (value >> bitIndex & magicMask);
                // Branch: checks a condition
                if (consumeEmpty || paletteIndex != airIndex) {
                    // Assigns a value
                    final int y = index >> shiftedDimensionBitCount;
                    // Assigns a value
                    final int z = index >> dimensionBitCount & dimensionMinus;
                    // Assigns a value
                    final int x = index & dimensionMinus;
                    // Assigns a value
                    final int result = ids != null && paletteIndex < ids.length ? ids[paletteIndex] : paletteIndex;
                    // Calls a method
                    consumer.accept(x, y, z, result);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void updateAll(int[] paletteValues) {
        // Calls a method
        final int size = maxSize();
        // Code statement
        assert paletteValues.length >= size;
        // Assigns a value
        final int bitsPerEntry = this.bitsPerEntry;
        // Assigns a value
        final int valuesPerLong = 64 / bitsPerEntry;
        // Calls a method
        final long clear = (1L << bitsPerEntry) - 1L;
        // Assigns a value
        final long[] values = this.values;
        // Loop: repeats a block
        for (int i = 0; i < values.length; i++) {
            // Assigns a value
            long block = values[i];
            // Assigns a value
            final int startIndex = i * valuesPerLong;
            // Calls a method
            final int endIndex = Math.min(startIndex + valuesPerLong, size);
            // Loop: repeats a block
            for (int index = startIndex; index < endIndex; index++) {
                // Calls a method
                final int bitIndex = (index - startIndex) * bitsPerEntry;
                // Calls a method
                block = block & ~(clear << bitIndex) | ((long) paletteValues[index] << bitIndex);
            // End of a block/expression
            }
            // Assigns a value
            values[i] = block;
        // End of a block/expression
        }
    // End of a block/expression
    }

    /// Assumes {@link PaletteImpl#bitsPerEntry} != 0
    // Start of a method/block
    private void downsizeWithPalette(IntArrayList palette) {
        // Assigns a value
        final byte bpe = this.bitsPerEntry;
        // Calls a method
        final byte newBpe = (byte) Math.max(MathUtils.bitsToRepresent(palette.size() - 1), minBitsPerEntry);
        // Branch: checks a condition
        if (newBpe >= bpe || newBpe > maxBitsPerEntry) return;

        // Fill new palette <-> value objects
        // Calls a method
        final Int2IntOpenHashMap newValueToPaletteMap = new Int2IntOpenHashMap(palette.size());
        // Calls a method
        newValueToPaletteMap.defaultReturnValue(-1);
        // Calls a method
        final AtomicInteger index = new AtomicInteger();
        // Start of a method/block
        palette.forEach(v -> {
            // Calls a method
            final int plainIndex = index.getPlain();
            // Calls a method
            newValueToPaletteMap.put(v, plainIndex);
            // Calls a method
            index.setPlain(plainIndex + 1);
        // End of a block/expression
        });

        // Branch: checks a condition
        if (!hasPalette()) {
            // Access to the current/parent object
            this.values = Palettes.remap(dimension, bpe, newBpe, values, newValueToPaletteMap::get);
        // Alternative branch of the condition
        } else {
            // Calls a method
            final IntArrayList transformList = new IntArrayList(paletteToValueList.size());
            // Calls a method
            paletteToValueList.forEach(value -> transformList.add(newValueToPaletteMap.get(value)));
            // Calls a method
            final int[] transformArray = transformList.elements();
            // Access to the current/parent object
            this.values = Palettes.remap(dimension, bpe, newBpe, values, value -> transformArray[value]);
        // End of a block/expression
        }

        // Access to the current/parent object
        this.bitsPerEntry = newBpe;
        // Access to the current/parent object
        this.valueToPaletteMap = newValueToPaletteMap;
        // Access to the current/parent object
        this.paletteToValueList = palette;
    // End of a block/expression
    }

    // Start of a method/block
    void makeDirect() {
        // Branch: checks a condition
        if (!hasPalette()) return;
        // Branch: checks a condition
        if (bitsPerEntry == 0) {
            // Assigns a value
            final int fillValue = this.count;
            // Access to the current/parent object
            this.values = new long[arrayLength(dimension, directBits)];
            // Branch: checks a condition
            if (fillValue != 0) {
                // Calls a method
                Palettes.fill(directBits, this.values, fillValue);
                // Access to the current/parent object
                this.count = maxSize();
            // End of a block/expression
            }
        // Alternative branch of the condition
        } else {
            // Calls a method
            final int[] ids = paletteToValueList.elements();
            // Access to the current/parent object
            this.values = Palettes.remap(dimension, bitsPerEntry, directBits, values, v -> ids[v]);
        // End of a block/expression
        }
        // Access to the current/parent object
        this.paletteToValueList = null;
        // Access to the current/parent object
        this.valueToPaletteMap = null;
        // Access to the current/parent object
        this.bitsPerEntry = directBits;
    // End of a block/expression
    }

    /// Assumes {@link PaletteImpl#bitsPerEntry} != 0
    // Start of a method/block
    void upsize() {
        // Assigns a value
        final byte bpe = this.bitsPerEntry;
        // Calls a method
        byte newBpe = (byte) (bpe + 1);
        // Branch: checks a condition
        if (newBpe > maxBitsPerEntry) {
            // Calls a method
            makeDirect();
        // Alternative branch of the condition
        } else {
            // Access to the current/parent object
            this.values = Palettes.remap(dimension, bpe, newBpe, values, (v) -> v);
            // Access to the current/parent object
            this.bitsPerEntry = newBpe;
        // End of a block/expression
        }
    // End of a block/expression
    }

    /// Assumes {@link PaletteImpl#bitsPerEntry} == 0
    // Start of a method/block
    void initIndirect() {
        // Assigns a value
        final int fillValue = this.count;
        // Access to the current/parent object
        this.valueToPaletteMap = new Int2IntOpenHashMap();
        // Access to the current/parent object
        this.valueToPaletteMap.defaultReturnValue(-1);
        // Access to the current/parent object
        this.paletteToValueList = new IntArrayList();
        // Access to the current/parent object
        this.valueToPaletteMap.put(fillValue, 0);
        // Calls a method
        paletteToValueList.add(fillValue);
        // Access to the current/parent object
        this.bitsPerEntry = minBitsPerEntry;
        // Access to the current/parent object
        this.values = new long[arrayLength(dimension, minBitsPerEntry)];
        // Access to the current/parent object
        this.count = fillValue == 0 ? 0 : maxSize();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int paletteIndexToValue(int value) {
        // Returns a value to the caller
        return hasPalette() ? paletteToValueList.elements()[value] : value;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int valueToPaletteIndex(int value) {
        // Branch: checks a condition
        if (!hasPalette()) return value;
        // Branch: checks a condition
        if (values == null) initIndirect();

        // Calls a method
        final int lastPaletteIndex = this.paletteToValueList.size();
        // Calls a method
        final int lookup = valueToPaletteMap.putIfAbsent(value, lastPaletteIndex);
        // Branch: checks a condition
        if (lookup != -1) return lookup;
        // Branch: checks a condition
        if (lastPaletteIndex >= maxPaletteSize(bitsPerEntry)) {
            // Palette is full, must resize
            // Calls a method
            upsize();
            // Branch: checks a condition
            if (!hasPalette()) return value;
        // End of a block/expression
        }
        // Access to the current/parent object
        this.paletteToValueList.add(value);
        // Returns a value to the caller
        return lastPaletteIndex;
    // End of a block/expression
    }

    /// Assumes {@link PaletteImpl#bitsPerEntry} != 0
    // Start of a method/block
    int valueToPalettIndexOrDefault(int value) {
        // Returns a value to the caller
        return hasPalette() ? valueToPaletteMap.get(value) : value;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int singleValue() {
        // Returns a value to the caller
        return bitsPerEntry == 0 ? count : -1;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public long @Nullable [] indexedValues() {
        // Returns a value to the caller
        return values;
    // End of a block/expression
    }

    // Start of a method/block
    boolean hasPalette() {
        // Returns a value to the caller
        return bitsPerEntry <= maxBitsPerEntry;
    // End of a block/expression
    }

    // Start of a method/block
    private static void validateCoord(int dimension, int x, int y, int z) {
        // Branch: checks a condition
        if (x < 0 || y < 0 || z < 0)
            // Throws an exception
            throw new IllegalArgumentException("Coordinates must be non-negative");
        // Branch: checks a condition
        if (x >= dimension || y >= dimension || z >= dimension)
            // Throws an exception
            throw new IllegalArgumentException("Coordinates must be less than the dimension size, got " + x + ", " + y + ", " + z + " for dimension " + dimension);
    // End of a block/expression
    }

    // Start of a method/block
    private static void validateDimension(int dimension) {
        // Branch: checks a condition
        if (dimension <= 1 || (dimension & dimension - 1) != 0)
            // Throws an exception
            throw new IllegalArgumentException("Dimension must be a positive power of 2, got " + dimension);
    // End of a block/expression
    }
// End of a block/expression
}
