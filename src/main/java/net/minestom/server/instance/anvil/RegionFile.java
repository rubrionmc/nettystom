// Package declaration for this file
package net.minestom.server.instance.anvil;

// Import of a required class
import net.kyori.adventure.nbt.BinaryTagIO;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.coordinate.CoordConversion;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.io.ByteArrayInputStream;
// Import of a required class
import java.io.ByteArrayOutputStream;
// Import of a required class
import java.io.IOException;
// Import of a required class
import java.io.RandomAccessFile;
// Import of a required class
import java.nio.ByteBuffer;
// Import of a required class
import java.nio.file.Path;
// Import of a required class
import java.util.BitSet;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implements a thread-safe reader and writer for Minecraft region files.
 *
 * @see <a href="https://minecraft.wiki/w/Region_file_format">Region file format</a>
 * @see <a href="https://github.com/Minestom/Hephaistos/blob/master/common/src/main/kotlin/org/jglrxavpok/hephaistos/mca/RegionFile.kt">Hephaistos implementation</a>
 */
// Type declaration (class/interface/enum/record)
final class RegionFile implements AutoCloseable {

    // Assigns a value
    private static final int MAX_ENTRY_COUNT = 1024;
    // Assigns a value
    private static final int SECTOR_SIZE = 4096;
    // Assigns a value
    private static final int SECTOR_1MB = 1024 * 1024 / SECTOR_SIZE;
    // Assigns a value
    private static final int HEADER_LENGTH = MAX_ENTRY_COUNT * 2 * 4; // 2 4-byte fields per entry
    // Assigns a value
    private static final int CHUNK_HEADER_LENGTH = 4 + 1; // Length + Compression type (todo non constant to support custom compression)

    // Assigns a value
    private static final int COMPRESSION_ZLIB = 2;

    // Calls a method
    private static final BinaryTagIO.Reader TAG_READER = BinaryTagIO.unlimitedReader();
    // Calls a method
    private static final BinaryTagIO.Writer TAG_WRITER = BinaryTagIO.writer();

    // Start of a method/block
    public static String getFileName(int regionX, int regionZ) {
        // Returns a value to the caller
        return "r." + regionX + "." + regionZ + ".mca";
    // End of a block/expression
    }

    // Calls a method
    private final ReentrantLock lock = new ReentrantLock();
    // Code statement
    private final RandomAccessFile file;

    // Assigns a value
    private final int[] locations = new int[MAX_ENTRY_COUNT];
    // Assigns a value
    private final int[] timestamps = new int[MAX_ENTRY_COUNT];
    // Calls a method
    private final BitSet freeSectors = new BitSet(2);

    // Cache header data to avoid repeated file I/O
    // Calls a method
    private final ByteBuffer headerBuffer = ByteBuffer.allocate(HEADER_LENGTH);
    // Assigns a value
    private boolean headerDirty = false;

    // Start of a method/block
    public RegionFile(Path path) throws IOException {
        // Access to the current/parent object
        this.file = new RandomAccessFile(path.toFile(), "rw");
        // Calls a method
        readHeader();
    // End of a block/expression
    }

    // Start of a method/block
    public boolean hasChunkData(int chunkX, int chunkZ) {
        // Calls a method
        lock.lock();
        // Exception handling
        try {
            // Returns a value to the caller
            return locations[getChunkIndex(chunkX, chunkZ)] != 0;
        // Start of a method/block
        } finally {
            // Calls a method
            lock.unlock();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable CompoundBinaryTag readChunkData(int chunkX, int chunkZ) throws IOException {
        // Calls a method
        lock.lock();
        // Exception handling
        try {
            // Branch: checks a condition
            if (!hasChunkData(chunkX, chunkZ)) return null;

            // Calls a method
            int location = locations[getChunkIndex(chunkX, chunkZ)];
            // Code statement
            file.seek((long) (location >> 8) * SECTOR_SIZE); // Move to start of first sector
            // Calls a method
            int length = file.readInt();
            // Calls a method
            int compressionType = file.readByte();
            // Assigns a value
            BinaryTagIO.Compression compression = switch (compressionType) {
                // Multiple branching (switch/case)
                case 1 -> BinaryTagIO.Compression.GZIP;
                // Multiple branching (switch/case)
                case COMPRESSION_ZLIB -> BinaryTagIO.Compression.ZLIB;
                // Multiple branching (switch/case)
                case 3 -> BinaryTagIO.Compression.NONE;
                // Multiple branching (switch/case)
                default -> throw new IOException("Unsupported compression type: " + compressionType);
            // End of a block/expression
            };

            // Read the raw content
            // Assigns a value
            byte[] data = new byte[length - 1];
            // Calls a method
            file.read(data);

            // Parse it as a compound tag
            // Returns a value to the caller
            return TAG_READER.read(new ByteArrayInputStream(data), compression);
        // Start of a method/block
        } finally {
            // Calls a method
            lock.unlock();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public void writeChunkData(int chunkX, int chunkZ, CompoundBinaryTag data) throws IOException {
        // Write the data (compressed)
        // Calls a method
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // Calls a method
        TAG_WRITER.writeNamed(Map.entry("", data), out, BinaryTagIO.Compression.ZLIB);
        // Calls a method
        byte[] dataBytes = out.toByteArray();
        // Assigns a value
        int chunkLength = CHUNK_HEADER_LENGTH + dataBytes.length;

        // Calls a method
        int sectorCount = (int) Math.ceil(chunkLength / (double) SECTOR_SIZE);
        // Calls a method
        Check.stateCondition(sectorCount >= SECTOR_1MB, "Chunk data is too large to fit in a region file");

        // Calls a method
        lock.lock();
        // Exception handling
        try {
            // We don't attempt to reuse the current allocation, just write it to a new position and free the old one.
            // Calls a method
            int chunkIndex = getChunkIndex(chunkX, chunkZ);
            // Assigns a value
            int oldLocation = locations[chunkIndex];

            // Find a new location
            // Calls a method
            int firstSector = findFreeSectors(sectorCount);
            // Branch: checks a condition
            if (firstSector == -1) {
                // Calls a method
                firstSector = allocSectors(sectorCount);
            // End of a block/expression
            }
            // Calls a method
            int newLocation = (firstSector << 8) | sectorCount;

            // Mark the sectors as used & free the old sectors
            // Calls a method
            markLocation(oldLocation, true);
            // Calls a method
            markLocation(newLocation, false);

            // Write the chunk data
            // Calls a method
            file.seek((long) firstSector * SECTOR_SIZE);
            // Calls a method
            file.writeInt(chunkLength);
            // Calls a method
            file.writeByte(COMPRESSION_ZLIB);
            // Calls a method
            file.write(dataBytes);

            // Update the header and write it
            // Assigns a value
            locations[chunkIndex] = newLocation;
            // store timestamps in seconds since epoch
            // Calls a method
            timestamps[chunkIndex] = (int) (System.currentTimeMillis() / 1000);
            // Calls a method
            writeHeader();
        // Start of a method/block
        } finally {
            // Calls a method
            lock.unlock();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void close() throws IOException {
        // Calls a method
        file.close();
    // End of a block/expression
    }

    // Start of a method/block
    private int getChunkIndex(int chunkX, int chunkZ) {
        // Returns a value to the caller
        return (CoordConversion.chunkToRegionLocal(chunkZ) << 5) | CoordConversion.chunkToRegionLocal(chunkX);
    // End of a block/expression
    }

    // Start of a method/block
    private void readHeader() throws IOException {
        // Calls a method
        file.seek(0);
        // Branch: checks a condition
        if (file.length() < HEADER_LENGTH) {
            // new file, fill in data
            // Calls a method
            file.write(new byte[HEADER_LENGTH]);
        // End of a block/expression
        }

        // Assigns a value
        final long totalSectors = ((file.length() - 1) / SECTOR_SIZE) + 1; // Round up, last sector does not need to be full size
        // Code statement
        freeSectors.set(0, (int) totalSectors); // Set all sectors as free initially
        // Code statement
        freeSectors.clear(0); // First sector is locations
        // Code statement
        freeSectors.clear(1); // Second sector is timestamps

        // Read entire header in one operation
        // Calls a method
        file.seek(0);
        // Assigns a value
        byte[] headerData = new byte[HEADER_LENGTH];
        // Calls a method
        file.readFully(headerData);
        // Calls a method
        headerBuffer.clear();
        // Calls a method
        headerBuffer.put(headerData);
        // Calls a method
        headerBuffer.flip();

        // Parse locations from buffer
        // Loop: repeats a block
        for (int i = 0; i < MAX_ENTRY_COUNT; i++) {
            // Calls a method
            int location = locations[i] = headerBuffer.getInt();
            // Branch: checks a condition
            if (location != 0) {
                // Calls a method
                markLocationInBitSet(location, false);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Parse timestamps from buffer
        // Loop: repeats a block
        for (int i = 0; i < MAX_ENTRY_COUNT; i++) {
            // Calls a method
            timestamps[i] = headerBuffer.getInt();
        // End of a block/expression
        }

        // Assigns a value
        headerDirty = false;
    // End of a block/expression
    }

    // Start of a method/block
    private void writeHeader() throws IOException {
        // Branch: checks a condition
        if (!headerDirty) return; // Skip if header hasn't changed

        // Calls a method
        headerBuffer.clear();

        // Write locations to buffer
        // Loop: repeats a block
        for (int location : locations) {
            // Calls a method
            headerBuffer.putInt(location);
        // End of a block/expression
        }

        // Write timestamps to buffer
        // Loop: repeats a block
        for (int timestamp : timestamps) {
            // Calls a method
            headerBuffer.putInt(timestamp);
        // End of a block/expression
        }

        // Write entire header in one operation
        // Calls a method
        file.seek(0);
        // Calls a method
        file.write(headerBuffer.array());
        // Assigns a value
        headerDirty = false;
    // End of a block/expression
    }

    // Start of a method/block
    private int findFreeSectors(int length) {
        // Calls a method
        int start = freeSectors.nextSetBit(0);
        // Loop: repeats a block
        while (start != -1 && start + length <= freeSectors.size()) {
            // Check if we have 'length' consecutive free sectors starting at 'start'
            // Calls a method
            int nextClear = freeSectors.nextClearBit(start);
            // Branch: checks a condition
            if (nextClear >= start + length) {
                // Returns a value to the caller
                return start;
            // End of a block/expression
            }
            // Calls a method
            start = freeSectors.nextSetBit(nextClear);
        // End of a block/expression
        }
        // Returns a value to the caller
        return -1;
    // End of a block/expression
    }

    // Start of a method/block
    private int allocSectors(int count) throws IOException {
        // Calls a method
        var eof = file.length();
        // Calls a method
        file.seek(eof);

        // Assigns a value
        byte[] emptySector = new byte[SECTOR_SIZE];
        // Calls a method
        int startSector = (int) (eof / SECTOR_SIZE);
        // Loop: repeats a block
        for (int i = 0; i < count; i++) {
            // Calls a method
            freeSectors.set(startSector + i, true);
            // Calls a method
            file.write(emptySector);
        // End of a block/expression
        }
        // Returns a value to the caller
        return startSector;
    // End of a block/expression
    }

    // Start of a method/block
    private void markLocation(int location, boolean free) {
        // Calls a method
        markLocationInBitSet(location, free);
        // Assigns a value
        headerDirty = true;
    // End of a block/expression
    }

    // Start of a method/block
    private void markLocationInBitSet(int location, boolean free) {
        // Assigns a value
        int sectorCount = location & 0xFF;
        // Assigns a value
        int sectorStart = location >> 8;
        // Calls a method
        Check.stateCondition(sectorStart + sectorCount > freeSectors.size(), "Invalid sector count");
        // Calls a method
        freeSectors.set(sectorStart, sectorStart + sectorCount, free);
    // End of a block/expression
    }
// End of a block/expression
}
