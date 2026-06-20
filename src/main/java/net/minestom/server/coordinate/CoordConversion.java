// Package declaration for this file
package net.minestom.server.coordinate;

// Type declaration (class/interface/enum/record)
public final class CoordConversion {
    // Assigns a value
    public static final int REGION_SIZE = 512;
    // Assigns a value
    public static final int SECTION_SIZE = 16;
    // Assigns a value
    public static final int SECTION_BOUND = 15;
    // Assigns a value
    public static final int SECTION_BLOCK_COUNT = SECTION_SIZE * SECTION_SIZE * SECTION_SIZE;

    // COORDINATE CONVERSIONS

    // Start of a method/block
    public static int globalToBlock(double xyz) {
        // Returns a value to the caller
        return (int) Math.floor(xyz);
    // End of a block/expression
    }

    // Start of a method/block
    public static int globalToRegion(int xz) {
        // Returns a value to the caller
        return xz >> 9;
    // End of a block/expression
    }

    // Start of a method/block
    public static int globalToRegion(double xz) {
        // Calls a method
        final int block = globalToBlock(xz);
        // Returns a value to the caller
        return globalToRegion(block);
    // End of a block/expression
    }

    // Start of a method/block
    public static int globalToChunk(int xz) {
        // Returns a value to the caller
        return globalToSection(xz);
    // End of a block/expression
    }

    // Start of a method/block
    public static int globalToChunk(double xz) {
        // Calls a method
        final int block = globalToBlock(xz);
        // Returns a value to the caller
        return globalToChunk(block);
    // End of a block/expression
    }

    // Start of a method/block
    public static int globalToSection(int xyz) {
        // Returns a value to the caller
        return xyz >> 4;
    // End of a block/expression
    }

    // Start of a method/block
    public static int globalToSectionRelative(int xyz) {
        // Returns a value to the caller
        return xyz & SECTION_BOUND;
    // End of a block/expression
    }

    // Start of a method/block
    public static boolean sectionAligned(int xyz) {
        // Returns a value to the caller
        return globalToSectionRelative(xyz) == 0;
    // End of a block/expression
    }

    // Start of a method/block
    public static boolean sectionAligned(int x, int y, int z) {
        // Returns a value to the caller
        return sectionAligned(x) && sectionAligned(y) && sectionAligned(z);
    // End of a block/expression
    }

    // Start of a method/block
    public static boolean sectionAligned(Point point) {
        // Returns a value to the caller
        return sectionAligned(point.blockX(), point.blockY(), point.blockZ());
    // End of a block/expression
    }

    // Start of a method/block
    public static boolean sectionAligned(Point p1, Point p2) {
        // Calls a method
        final int minX = Math.min(p1.blockX(), p2.blockX());
        // Calls a method
        final int minY = Math.min(p1.blockY(), p2.blockY());
        // Calls a method
        final int minZ = Math.min(p1.blockZ(), p2.blockZ());
        // Calls a method
        final int maxX = Math.max(p1.blockX(), p2.blockX());
        // Calls a method
        final int maxY = Math.max(p1.blockY(), p2.blockY());
        // Calls a method
        final int maxZ = Math.max(p1.blockZ(), p2.blockZ());
        // Returns a value to the caller
        return ((minX | minY | minZ) & SECTION_BOUND) == 0 &&
                // Calls a method
                ((maxX | maxY | maxZ) & SECTION_BOUND) == SECTION_BOUND;
    // End of a block/expression
    }

    // Start of a method/block
    public static int chunkToRegion(int chunkCoordinate) {
        // Returns a value to the caller
        return chunkCoordinate >> 5;
    // End of a block/expression
    }

    // Start of a method/block
    public static int chunkToRegionLocal(int chunkCoordinate) {
        // Returns a value to the caller
        return chunkCoordinate & 0x1F;
    // End of a block/expression
    }

    // Start of a method/block
    public static int floorSection(int coordinate) {
        // Returns a value to the caller
        return coordinate & ~SECTION_BOUND;
    // End of a block/expression
    }

    // Start of a method/block
    public static int ceilSection(int coordinate) {
        // Returns a value to the caller
        return (coordinate + SECTION_BOUND) & ~SECTION_BOUND;
    // End of a block/expression
    }

    // REGION INDEX

    // Start of a method/block
    public static long regionIndex(int regionX, int regionZ) {
        // Returns a value to the caller
        return (((long) regionX) << 32) | (regionZ & 0xffffffffL);
    // End of a block/expression
    }

    // Start of a method/block
    public static long regionIndex(Point point) {
        // Returns a value to the caller
        return regionIndex(point.regionX(), point.regionZ());
    // End of a block/expression
    }

    // Start of a method/block
    public static int regionIndexGetX(long index) {
        // Returns a value to the caller
        return (int) (index >> 32);
    // End of a block/expression
    }

    // Start of a method/block
    public static int regionIndexGetZ(long index) {
        // Returns a value to the caller
        return (int) index;
    // End of a block/expression
    }

    // CHUNK INDEX

    // Start of a method/block
    public static long chunkIndex(int chunkX, int chunkZ) {
        // Returns a value to the caller
        return (((long) chunkX) << 32) | (chunkZ & 0xffffffffL);
    // End of a block/expression
    }

    // Start of a method/block
    public static long chunkIndex(Point point) {
        // Returns a value to the caller
        return chunkIndex(point.chunkX(), point.chunkZ());
    // End of a block/expression
    }

    // Start of a method/block
    public static int chunkIndexGetX(long index) {
        // Returns a value to the caller
        return (int) (index >> 32);
    // End of a block/expression
    }

    // Start of a method/block
    public static int chunkIndexGetZ(long index) {
        // Returns a value to the caller
        return (int) index;
    // End of a block/expression
    }

    // BLOCK INDEX FROM CHUNK

    // Start of a method/block
    public static int chunkBlockIndex(int x, int y, int z) {
        // Mask x and z to ensure only the lower 4 bits are used.
        // Calls a method
        x = globalToSectionRelative(x);
        // Calls a method
        z = globalToSectionRelative(z);

        // Bits layout:
        // bits 0-3: x (4 bits)
        // bits 4-26: absolute value of y (23 bits)
        // bit 27: sign bit of y
        // bits 28-31: z (4 bits)
        // Returns a value to the caller
        return (z << 28)                          // Z component (shifted to the upper 4 bits)
                // Code statement
                | (y & 0x80000000) >>> 4          // Y sign bit if y was negative
                // Code statement
                | (Math.abs(y) & 0x007FFFFF) << 4 // Y component (23 bits for Y, sign encoded in the 24th)
                // Code statement
                | (x);                            // X component (4 bits for X)
    // End of a block/expression
    }

    // Start of a method/block
    public static int chunkBlockIndexGetX(int index) {
        // Returns a value to the caller
        return index & 0xF; // bits 0-3
    // End of a block/expression
    }

    // Start of a method/block
    public static int chunkBlockIndexGetY(int index) {
        // Calls a method
        int y = (index & 0x07FFFFF0) >>> 4;
        // Branch: checks a condition
        if ((index & 0x08000000) != 0) y = -y; // Sign bit set, invert sign
        // Returns a value to the caller
        return y; // 4-28 bits
    // End of a block/expression
    }

    // Start of a method/block
    public static int chunkBlockIndexGetZ(int index) {
        // Returns a value to the caller
        return (index >>> 28) & 0xF; // bits 28-31
    // End of a block/expression
    }

    // Start of a method/block
    public static Point chunkBlockIndexGetGlobal(int index, int chunkX, int chunkZ) {
        // Calls a method
        final int x = chunkBlockIndexGetX(index) + SECTION_SIZE * chunkX;
        // Calls a method
        final int y = chunkBlockIndexGetY(index);
        // Calls a method
        final int z = chunkBlockIndexGetZ(index) + SECTION_SIZE * chunkZ;
        // Returns a value to the caller
        return new Vec(x, y, z);
    // End of a block/expression
    }

    // Start of a method/block
    public static Point chunkBlockRelativeGetGlobal(int sectionRelativeX, int y, int sectionRelativeZ, int chunkX, int chunkZ) {
        // Assigns a value
        final int x = sectionRelativeX + 16 * chunkX;
        // Assigns a value
        final int z = sectionRelativeZ + 16 * chunkZ;
        // Returns a value to the caller
        return new Vec(x, y, z);
    // End of a block/expression
    }

    // SECTION INDEX

    // Start of a method/block
    public static long sectionIndex(int sectionX, int sectionY, int sectionZ) {
        // Use 21 bits for each, with sign extension
        // Assigns a value
        final long x = sectionX & 0x1FFFFF;
        // Assigns a value
        final long y = sectionY & 0x1FFFFF;
        // Assigns a value
        final long z = sectionZ & 0x1FFFFF;
        // Returns a value to the caller
        return (x << 42) | (y << 21) | z;
    // End of a block/expression
    }

    // Start of a method/block
    public static int sectionIndexGetX(long index) {
        // Calls a method
        int x = (int) (index >> 42) & 0x1FFFFF;
        // Sign extension for 21 bits
        // Branch: checks a condition
        if ((x & 0x100000) != 0) x |= ~0x1FFFFF;
        // Returns a value to the caller
        return x;
    // End of a block/expression
    }

    // Start of a method/block
    public static int sectionIndexGetY(long index) {
        // Calls a method
        int y = (int) (index >> 21) & 0x1FFFFF;
        // Branch: checks a condition
        if ((y & 0x100000) != 0) y |= ~0x1FFFFF;
        // Returns a value to the caller
        return y;
    // End of a block/expression
    }

    // Start of a method/block
    public static int sectionIndexGetZ(long index) {
        // Calls a method
        int z = (int) index & 0x1FFFFF;
        // Branch: checks a condition
        if ((z & 0x100000) != 0) z |= ~0x1FFFFF;
        // Returns a value to the caller
        return z;
    // End of a block/expression
    }

    // Start of a method/block
    public static long sectionIndexGlobal(int x, int y, int z) {
        // Calls a method
        final int sectionX = globalToChunk(x);
        // Calls a method
        final int sectionY = globalToChunk(y);
        // Calls a method
        final int sectionZ = globalToChunk(z);
        // Returns a value to the caller
        return sectionIndex(sectionX, sectionY, sectionZ);
    // End of a block/expression
    }

    // BLOCK INDEX FROM SECTION (0-15 for each coordinate)

    // Start of a method/block
    public static int sectionBlockIndex(int x, int y, int z) {
        // Returns a value to the caller
        return (x << 8) | (z << 4) | y;
    // End of a block/expression
    }

    // Start of a method/block
    public static int sectionBlockIndexGetX(int index) {
        // Returns a value to the caller
        return (index >> 8) & SECTION_BOUND;
    // End of a block/expression
    }

    // Start of a method/block
    public static int sectionBlockIndexGetY(int index) {
        // Returns a value to the caller
        return index & SECTION_BOUND;
    // End of a block/expression
    }

    // Start of a method/block
    public static int sectionBlockIndexGetZ(int index) {
        // Returns a value to the caller
        return (index >> 4) & SECTION_BOUND;
    // End of a block/expression
    }

    // Start of a method/block
    public static long encodeSectionBlockChange(int sectionBlockIndex, long value) {
        // To use with `MultiBlockChangePacket`
        // Assigns a value
        final long blockState = value << 12;
        // Returns a value to the caller
        return blockState | (long) sectionBlockIndex;
    // End of a block/expression
    }

    // Start of a method/block
    public static long encodeSectionBlockChange(int localX, int localY, int localZ, long value) {
        // Returns a value to the caller
        return encodeSectionBlockChange(sectionBlockIndex(localX, localY, localZ), value);
    // End of a block/expression
    }

    // Start of a method/block
    public static short deltaShort4096(double newCoordinate, double oldCoordinate) {
        // Returns a value to the caller
        return (short) ((newCoordinate - oldCoordinate) * 4096);
    // End of a block/expression
    }

    // HASHING

    // Assigns a value
    private static final long PRIME_X = 0x9E37_79B9_7F4A_7C15L;  // Large prime for X axis
    // Assigns a value
    private static final long PRIME_Y = 0xF9F9_F9F9_ECEC_ECECL;  // Large prime for Y axis
    // Assigns a value
    private static final long PRIME_Z = 0xA5A5_A5A5_A5A5_A5A5L;  // Large prime for Z axis

    // Rotation amounts optimized for 3D coordinate separation
    // Assigns a value
    private static final int ROT_X = 31;  // Prime rotation for X
    // Assigns a value
    private static final int ROT_Y = 37;  // Prime rotation for Y
    // Assigns a value
    private static final int ROT_Z = 41;  // Prime rotation for Z

    // Assigns a value
    private static final long AVALANCHE_1 = 0xFF51_AFD7_ED55_8CCDL;
    // Assigns a value
    private static final long AVALANCHE_2 = 0xC4CE_B9FE_1A85_EC53L;

    // Assigns a value
    private static final long INT_SEED = 0xCAFE_BABE_DEAD_BEEFL;
    // Assigns a value
    private static final long DOUBLE_SEED = 0xDEAD_BEEF_CAFE_BABEL;

    // Start of a method/block
    public static long hashBlockCoord(int x, int y, int z) {
        // Assigns a value
        long h = INT_SEED;
        // Calls a method
        h ^= Long.rotateLeft(Integer.toUnsignedLong(x) * PRIME_X, ROT_X);
        // Calls a method
        h ^= Long.rotateLeft(Integer.toUnsignedLong(y) * PRIME_Y, ROT_Y);
        // Calls a method
        h ^= Long.rotateLeft(Integer.toUnsignedLong(z) * PRIME_Z, ROT_Z);
        // Calls a method
        h = Long.rotateLeft(h, 23) ^ (h >>> 17);
        // Code statement
        h ^= h >>> 33;
        // Code statement
        h *= AVALANCHE_1;
        // Code statement
        h ^= h >>> 33;
        // Code statement
        h *= AVALANCHE_2;
        // Code statement
        h ^= h >>> 33;
        // Returns a value to the caller
        return h;
    // End of a block/expression
    }

    // Start of a method/block
    public static long hashBlockCoord(Point point) {
        // Returns a value to the caller
        return hashBlockCoord(point.blockX(), point.blockY(), point.blockZ());
    // End of a block/expression
    }

    // Start of a method/block
    public static long hashGlobalCoord(double x, double y, double z) {
        // Assigns a value
        long h = DOUBLE_SEED;
        // Calls a method
        long ix = Double.doubleToLongBits(x);
        // Calls a method
        long iy = Double.doubleToLongBits(y);
        // Calls a method
        long iz = Double.doubleToLongBits(z);
        // Calls a method
        long ex = (ix >>> 52) & 0x7FFL;
        // Calls a method
        long ey = (iy >>> 52) & 0x7FFL;
        // Calls a method
        long ez = (iz >>> 52) & 0x7FFL;
        // Assigns a value
        long mx = ix & 0x000F_FFFF_FFFF_FFFFL;
        // Assigns a value
        long my = iy & 0x000F_FFFF_FFFF_FFFFL;
        // Assigns a value
        long mz = iz & 0x000F_FFFF_FFFF_FFFFL;
        // Calls a method
        h ^= Long.rotateLeft(ix * PRIME_X, ROT_X);
        // Calls a method
        h ^= Long.rotateLeft(iy * PRIME_Y, ROT_Y);
        // Calls a method
        h ^= Long.rotateLeft(iz * PRIME_Z, ROT_Z);
        // Calls a method
        h ^= Long.rotateLeft((ex << 32) | (ey << 16) | ez, 19);
        // Calls a method
        h ^= Long.rotateLeft(mx ^ my ^ mz, 43);
        // Calls a method
        h = Long.rotateLeft(h, 29) ^ (h >>> 13);
        // Code statement
        h ^= h >>> 33;
        // Code statement
        h *= AVALANCHE_1;
        // Code statement
        h ^= h >>> 33;
        // Code statement
        h *= AVALANCHE_2;
        // Code statement
        h ^= h >>> 33;
        // Returns a value to the caller
        return h;
    // End of a block/expression
    }

    // Start of a method/block
    public static long hashGlobalCoord(Point point) {
        // Returns a value to the caller
        return hashGlobalCoord(point.x(), point.y(), point.z());
    // End of a block/expression
    }

    // STRING FORMATTING

    // Start of a method/block
    public static String formatGlobalCoord(double x, double y, double z) {
        // Returns a value to the caller
        return "(%.3f, %.3f, %.3f)".formatted(x, y, z);
    // End of a block/expression
    }

    // Start of a method/block
    public static String formatGlobalCoord(Point point) {
        // Returns a value to the caller
        return formatGlobalCoord(point.x(), point.y(), point.z());
    // End of a block/expression
    }

    // Start of a method/block
    public static String formatBlockCoord(int x, int y, int z) {
        // Returns a value to the caller
        return "(%d, %d, %d)".formatted(x, y, z);
    // End of a block/expression
    }

    // Start of a method/block
    public static String formatBlockCoord(Point point) {
        // Returns a value to the caller
        return formatBlockCoord(point.blockX(), point.blockY(), point.blockZ());
    // End of a block/expression
    }
// End of a block/expression
}
