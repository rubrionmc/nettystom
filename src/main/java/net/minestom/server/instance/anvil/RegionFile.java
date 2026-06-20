// Déclaration du paquet de ce fichier
package net.minestom.server.instance.anvil;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTagIO;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.CoordConversion;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.io.ByteArrayInputStream;
// Import d'une classe nécessaire
import java.io.ByteArrayOutputStream;
// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.io.RandomAccessFile;
// Import d'une classe nécessaire
import java.nio.ByteBuffer;
// Import d'une classe nécessaire
import java.nio.file.Path;
// Import d'une classe nécessaire
import java.util.BitSet;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implements a thread-safe reader and writer for Minecraft region files.
 *
 * @see <a href="https://minecraft.wiki/w/Region_file_format">Region file format</a>
 * @see <a href="https://github.com/Minestom/Hephaistos/blob/master/common/src/main/kotlin/org/jglrxavpok/hephaistos/mca/RegionFile.kt">Hephaistos implementation</a>
 */
// Déclaration de type (classe/interface/enum/record)
final class RegionFile implements AutoCloseable {

    // Affecte une valeur
    private static final int MAX_ENTRY_COUNT = 1024;
    // Affecte une valeur
    private static final int SECTOR_SIZE = 4096;
    // Affecte une valeur
    private static final int SECTOR_1MB = 1024 * 1024 / SECTOR_SIZE;
    // Affecte une valeur
    private static final int HEADER_LENGTH = MAX_ENTRY_COUNT * 2 * 4; // 2 4-byte fields per entry
    // Affecte une valeur
    private static final int CHUNK_HEADER_LENGTH = 4 + 1; // Length + Compression type (todo non constant to support custom compression)

    // Affecte une valeur
    private static final int COMPRESSION_ZLIB = 2;

    // Appelle une méthode
    private static final BinaryTagIO.Reader TAG_READER = BinaryTagIO.unlimitedReader();
    // Appelle une méthode
    private static final BinaryTagIO.Writer TAG_WRITER = BinaryTagIO.writer();

    // Début d'une méthode/d'un bloc
    public static String getFileName(int regionX, int regionZ) {
        // Renvoie une valeur à l'appelant
        return "r." + regionX + "." + regionZ + ".mca";
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    private final ReentrantLock lock = new ReentrantLock();
    // Instruction de code
    private final RandomAccessFile file;

    // Affecte une valeur
    private final int[] locations = new int[MAX_ENTRY_COUNT];
    // Affecte une valeur
    private final int[] timestamps = new int[MAX_ENTRY_COUNT];
    // Appelle une méthode
    private final BitSet freeSectors = new BitSet(2);

    // Cache header data to avoid repeated file I/O
    // Appelle une méthode
    private final ByteBuffer headerBuffer = ByteBuffer.allocate(HEADER_LENGTH);
    // Affecte une valeur
    private boolean headerDirty = false;

    // Début d'une méthode/d'un bloc
    public RegionFile(Path path) throws IOException {
        // Accès à l'objet courant/parent
        this.file = new RandomAccessFile(path.toFile(), "rw");
        // Appelle une méthode
        readHeader();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean hasChunkData(int chunkX, int chunkZ) {
        // Appelle une méthode
        lock.lock();
        // Gestion des exceptions
        try {
            // Renvoie une valeur à l'appelant
            return locations[getChunkIndex(chunkX, chunkZ)] != 0;
        // Début d'une méthode/d'un bloc
        } finally {
            // Appelle une méthode
            lock.unlock();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable CompoundBinaryTag readChunkData(int chunkX, int chunkZ) throws IOException {
        // Appelle une méthode
        lock.lock();
        // Gestion des exceptions
        try {
            // Embranchement : vérifie une condition
            if (!hasChunkData(chunkX, chunkZ)) return null;

            // Appelle une méthode
            int location = locations[getChunkIndex(chunkX, chunkZ)];
            // Instruction de code
            file.seek((long) (location >> 8) * SECTOR_SIZE); // Move to start of first sector
            // Appelle une méthode
            int length = file.readInt();
            // Appelle une méthode
            int compressionType = file.readByte();
            // Affecte une valeur
            BinaryTagIO.Compression compression = switch (compressionType) {
                // Embranchement multiple (switch/case)
                case 1 -> BinaryTagIO.Compression.GZIP;
                // Embranchement multiple (switch/case)
                case COMPRESSION_ZLIB -> BinaryTagIO.Compression.ZLIB;
                // Embranchement multiple (switch/case)
                case 3 -> BinaryTagIO.Compression.NONE;
                // Embranchement multiple (switch/case)
                default -> throw new IOException("Unsupported compression type: " + compressionType);
            // Fin d'un bloc/d'une expression
            };

            // Read the raw content
            // Affecte une valeur
            byte[] data = new byte[length - 1];
            // Appelle une méthode
            file.read(data);

            // Parse it as a compound tag
            // Renvoie une valeur à l'appelant
            return TAG_READER.read(new ByteArrayInputStream(data), compression);
        // Début d'une méthode/d'un bloc
        } finally {
            // Appelle une méthode
            lock.unlock();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void writeChunkData(int chunkX, int chunkZ, CompoundBinaryTag data) throws IOException {
        // Write the data (compressed)
        // Appelle une méthode
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // Appelle une méthode
        TAG_WRITER.writeNamed(Map.entry("", data), out, BinaryTagIO.Compression.ZLIB);
        // Appelle une méthode
        byte[] dataBytes = out.toByteArray();
        // Affecte une valeur
        int chunkLength = CHUNK_HEADER_LENGTH + dataBytes.length;

        // Appelle une méthode
        int sectorCount = (int) Math.ceil(chunkLength / (double) SECTOR_SIZE);
        // Appelle une méthode
        Check.stateCondition(sectorCount >= SECTOR_1MB, "Chunk data is too large to fit in a region file");

        // Appelle une méthode
        lock.lock();
        // Gestion des exceptions
        try {
            // We don't attempt to reuse the current allocation, just write it to a new position and free the old one.
            // Appelle une méthode
            int chunkIndex = getChunkIndex(chunkX, chunkZ);
            // Affecte une valeur
            int oldLocation = locations[chunkIndex];

            // Find a new location
            // Appelle une méthode
            int firstSector = findFreeSectors(sectorCount);
            // Embranchement : vérifie une condition
            if (firstSector == -1) {
                // Appelle une méthode
                firstSector = allocSectors(sectorCount);
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            int newLocation = (firstSector << 8) | sectorCount;

            // Mark the sectors as used & free the old sectors
            // Appelle une méthode
            markLocation(oldLocation, true);
            // Appelle une méthode
            markLocation(newLocation, false);

            // Write the chunk data
            // Appelle une méthode
            file.seek((long) firstSector * SECTOR_SIZE);
            // Appelle une méthode
            file.writeInt(chunkLength);
            // Appelle une méthode
            file.writeByte(COMPRESSION_ZLIB);
            // Appelle une méthode
            file.write(dataBytes);

            // Update the header and write it
            // Affecte une valeur
            locations[chunkIndex] = newLocation;
            // store timestamps in seconds since epoch
            // Appelle une méthode
            timestamps[chunkIndex] = (int) (System.currentTimeMillis() / 1000);
            // Appelle une méthode
            writeHeader();
        // Début d'une méthode/d'un bloc
        } finally {
            // Appelle une méthode
            lock.unlock();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void close() throws IOException {
        // Appelle une méthode
        file.close();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private int getChunkIndex(int chunkX, int chunkZ) {
        // Renvoie une valeur à l'appelant
        return (CoordConversion.chunkToRegionLocal(chunkZ) << 5) | CoordConversion.chunkToRegionLocal(chunkX);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void readHeader() throws IOException {
        // Appelle une méthode
        file.seek(0);
        // Embranchement : vérifie une condition
        if (file.length() < HEADER_LENGTH) {
            // new file, fill in data
            // Appelle une méthode
            file.write(new byte[HEADER_LENGTH]);
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        final long totalSectors = ((file.length() - 1) / SECTOR_SIZE) + 1; // Round up, last sector does not need to be full size
        // Instruction de code
        freeSectors.set(0, (int) totalSectors); // Set all sectors as free initially
        // Instruction de code
        freeSectors.clear(0); // First sector is locations
        // Instruction de code
        freeSectors.clear(1); // Second sector is timestamps

        // Read entire header in one operation
        // Appelle une méthode
        file.seek(0);
        // Affecte une valeur
        byte[] headerData = new byte[HEADER_LENGTH];
        // Appelle une méthode
        file.readFully(headerData);
        // Appelle une méthode
        headerBuffer.clear();
        // Appelle une méthode
        headerBuffer.put(headerData);
        // Appelle une méthode
        headerBuffer.flip();

        // Parse locations from buffer
        // Boucle : répète un bloc
        for (int i = 0; i < MAX_ENTRY_COUNT; i++) {
            // Appelle une méthode
            int location = locations[i] = headerBuffer.getInt();
            // Embranchement : vérifie une condition
            if (location != 0) {
                // Appelle une méthode
                markLocationInBitSet(location, false);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Parse timestamps from buffer
        // Boucle : répète un bloc
        for (int i = 0; i < MAX_ENTRY_COUNT; i++) {
            // Appelle une méthode
            timestamps[i] = headerBuffer.getInt();
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        headerDirty = false;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void writeHeader() throws IOException {
        // Embranchement : vérifie une condition
        if (!headerDirty) return; // Skip if header hasn't changed

        // Appelle une méthode
        headerBuffer.clear();

        // Write locations to buffer
        // Boucle : répète un bloc
        for (int location : locations) {
            // Appelle une méthode
            headerBuffer.putInt(location);
        // Fin d'un bloc/d'une expression
        }

        // Write timestamps to buffer
        // Boucle : répète un bloc
        for (int timestamp : timestamps) {
            // Appelle une méthode
            headerBuffer.putInt(timestamp);
        // Fin d'un bloc/d'une expression
        }

        // Write entire header in one operation
        // Appelle une méthode
        file.seek(0);
        // Appelle une méthode
        file.write(headerBuffer.array());
        // Affecte une valeur
        headerDirty = false;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private int findFreeSectors(int length) {
        // Appelle une méthode
        int start = freeSectors.nextSetBit(0);
        // Boucle : répète un bloc
        while (start != -1 && start + length <= freeSectors.size()) {
            // Check if we have 'length' consecutive free sectors starting at 'start'
            // Appelle une méthode
            int nextClear = freeSectors.nextClearBit(start);
            // Embranchement : vérifie une condition
            if (nextClear >= start + length) {
                // Renvoie une valeur à l'appelant
                return start;
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            start = freeSectors.nextSetBit(nextClear);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return -1;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private int allocSectors(int count) throws IOException {
        // Appelle une méthode
        var eof = file.length();
        // Appelle une méthode
        file.seek(eof);

        // Affecte une valeur
        byte[] emptySector = new byte[SECTOR_SIZE];
        // Appelle une méthode
        int startSector = (int) (eof / SECTOR_SIZE);
        // Boucle : répète un bloc
        for (int i = 0; i < count; i++) {
            // Appelle une méthode
            freeSectors.set(startSector + i, true);
            // Appelle une méthode
            file.write(emptySector);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return startSector;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void markLocation(int location, boolean free) {
        // Appelle une méthode
        markLocationInBitSet(location, free);
        // Affecte une valeur
        headerDirty = true;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void markLocationInBitSet(int location, boolean free) {
        // Affecte une valeur
        int sectorCount = location & 0xFF;
        // Affecte une valeur
        int sectorStart = location >> 8;
        // Appelle une méthode
        Check.stateCondition(sectorStart + sectorCount > freeSectors.size(), "Invalid sector count");
        // Appelle une méthode
        freeSectors.set(sectorStart, sectorStart + sectorCount, free);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
