// Déclaration du paquet de ce fichier
package net.minestom.server.coordinate;

// Déclaration de type (classe/interface/enum/record)
public final class CoordConversion {
    // Affecte une valeur
    public static final int REGION_SIZE = 512;
    // Affecte une valeur
    public static final int SECTION_SIZE = 16;
    // Affecte une valeur
    public static final int SECTION_BOUND = 15;
    // Affecte une valeur
    public static final int SECTION_BLOCK_COUNT = SECTION_SIZE * SECTION_SIZE * SECTION_SIZE;

    // COORDINATE CONVERSIONS

    // Début d'une méthode/d'un bloc
    public static int globalToBlock(double xyz) {
        // Renvoie une valeur à l'appelant
        return (int) Math.floor(xyz);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int globalToRegion(int xz) {
        // Renvoie une valeur à l'appelant
        return xz >> 9;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int globalToRegion(double xz) {
        // Appelle une méthode
        final int block = globalToBlock(xz);
        // Renvoie une valeur à l'appelant
        return globalToRegion(block);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int globalToChunk(int xz) {
        // Renvoie une valeur à l'appelant
        return globalToSection(xz);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int globalToChunk(double xz) {
        // Appelle une méthode
        final int block = globalToBlock(xz);
        // Renvoie une valeur à l'appelant
        return globalToChunk(block);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int globalToSection(int xyz) {
        // Renvoie une valeur à l'appelant
        return xyz >> 4;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int globalToSectionRelative(int xyz) {
        // Renvoie une valeur à l'appelant
        return xyz & SECTION_BOUND;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static boolean sectionAligned(int xyz) {
        // Renvoie une valeur à l'appelant
        return globalToSectionRelative(xyz) == 0;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static boolean sectionAligned(int x, int y, int z) {
        // Renvoie une valeur à l'appelant
        return sectionAligned(x) && sectionAligned(y) && sectionAligned(z);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static boolean sectionAligned(Point point) {
        // Renvoie une valeur à l'appelant
        return sectionAligned(point.blockX(), point.blockY(), point.blockZ());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static boolean sectionAligned(Point p1, Point p2) {
        // Appelle une méthode
        final int minX = Math.min(p1.blockX(), p2.blockX());
        // Appelle une méthode
        final int minY = Math.min(p1.blockY(), p2.blockY());
        // Appelle une méthode
        final int minZ = Math.min(p1.blockZ(), p2.blockZ());
        // Appelle une méthode
        final int maxX = Math.max(p1.blockX(), p2.blockX());
        // Appelle une méthode
        final int maxY = Math.max(p1.blockY(), p2.blockY());
        // Appelle une méthode
        final int maxZ = Math.max(p1.blockZ(), p2.blockZ());
        // Renvoie une valeur à l'appelant
        return ((minX | minY | minZ) & SECTION_BOUND) == 0 &&
                // Appelle une méthode
                ((maxX | maxY | maxZ) & SECTION_BOUND) == SECTION_BOUND;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int chunkToRegion(int chunkCoordinate) {
        // Renvoie une valeur à l'appelant
        return chunkCoordinate >> 5;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int chunkToRegionLocal(int chunkCoordinate) {
        // Renvoie une valeur à l'appelant
        return chunkCoordinate & 0x1F;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int floorSection(int coordinate) {
        // Renvoie une valeur à l'appelant
        return coordinate & ~SECTION_BOUND;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int ceilSection(int coordinate) {
        // Renvoie une valeur à l'appelant
        return (coordinate + SECTION_BOUND) & ~SECTION_BOUND;
    // Fin d'un bloc/d'une expression
    }

    // REGION INDEX

    // Début d'une méthode/d'un bloc
    public static long regionIndex(int regionX, int regionZ) {
        // Renvoie une valeur à l'appelant
        return (((long) regionX) << 32) | (regionZ & 0xffffffffL);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static long regionIndex(Point point) {
        // Renvoie une valeur à l'appelant
        return regionIndex(point.regionX(), point.regionZ());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int regionIndexGetX(long index) {
        // Renvoie une valeur à l'appelant
        return (int) (index >> 32);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int regionIndexGetZ(long index) {
        // Renvoie une valeur à l'appelant
        return (int) index;
    // Fin d'un bloc/d'une expression
    }

    // CHUNK INDEX

    // Début d'une méthode/d'un bloc
    public static long chunkIndex(int chunkX, int chunkZ) {
        // Renvoie une valeur à l'appelant
        return (((long) chunkX) << 32) | (chunkZ & 0xffffffffL);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static long chunkIndex(Point point) {
        // Renvoie une valeur à l'appelant
        return chunkIndex(point.chunkX(), point.chunkZ());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int chunkIndexGetX(long index) {
        // Renvoie une valeur à l'appelant
        return (int) (index >> 32);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int chunkIndexGetZ(long index) {
        // Renvoie une valeur à l'appelant
        return (int) index;
    // Fin d'un bloc/d'une expression
    }

    // BLOCK INDEX FROM CHUNK

    // Début d'une méthode/d'un bloc
    public static int chunkBlockIndex(int x, int y, int z) {
        // Mask x and z to ensure only the lower 4 bits are used.
        // Appelle une méthode
        x = globalToSectionRelative(x);
        // Appelle une méthode
        z = globalToSectionRelative(z);

        // Bits layout:
        // bits 0-3: x (4 bits)
        // bits 4-26: absolute value of y (23 bits)
        // bit 27: sign bit of y
        // bits 28-31: z (4 bits)
        // Renvoie une valeur à l'appelant
        return (z << 28)                          // Z component (shifted to the upper 4 bits)
                // Instruction de code
                | (y & 0x80000000) >>> 4          // Y sign bit if y was negative
                // Instruction de code
                | (Math.abs(y) & 0x007FFFFF) << 4 // Y component (23 bits for Y, sign encoded in the 24th)
                // Instruction de code
                | (x);                            // X component (4 bits for X)
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int chunkBlockIndexGetX(int index) {
        // Renvoie une valeur à l'appelant
        return index & 0xF; // bits 0-3
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int chunkBlockIndexGetY(int index) {
        // Appelle une méthode
        int y = (index & 0x07FFFFF0) >>> 4;
        // Embranchement : vérifie une condition
        if ((index & 0x08000000) != 0) y = -y; // Sign bit set, invert sign
        // Renvoie une valeur à l'appelant
        return y; // 4-28 bits
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int chunkBlockIndexGetZ(int index) {
        // Renvoie une valeur à l'appelant
        return (index >>> 28) & 0xF; // bits 28-31
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Point chunkBlockIndexGetGlobal(int index, int chunkX, int chunkZ) {
        // Appelle une méthode
        final int x = chunkBlockIndexGetX(index) + SECTION_SIZE * chunkX;
        // Appelle une méthode
        final int y = chunkBlockIndexGetY(index);
        // Appelle une méthode
        final int z = chunkBlockIndexGetZ(index) + SECTION_SIZE * chunkZ;
        // Renvoie une valeur à l'appelant
        return new Vec(x, y, z);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Point chunkBlockRelativeGetGlobal(int sectionRelativeX, int y, int sectionRelativeZ, int chunkX, int chunkZ) {
        // Affecte une valeur
        final int x = sectionRelativeX + 16 * chunkX;
        // Affecte une valeur
        final int z = sectionRelativeZ + 16 * chunkZ;
        // Renvoie une valeur à l'appelant
        return new Vec(x, y, z);
    // Fin d'un bloc/d'une expression
    }

    // SECTION INDEX

    // Début d'une méthode/d'un bloc
    public static long sectionIndex(int sectionX, int sectionY, int sectionZ) {
        // Use 21 bits for each, with sign extension
        // Affecte une valeur
        final long x = sectionX & 0x1FFFFF;
        // Affecte une valeur
        final long y = sectionY & 0x1FFFFF;
        // Affecte une valeur
        final long z = sectionZ & 0x1FFFFF;
        // Renvoie une valeur à l'appelant
        return (x << 42) | (y << 21) | z;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int sectionIndexGetX(long index) {
        // Appelle une méthode
        int x = (int) (index >> 42) & 0x1FFFFF;
        // Sign extension for 21 bits
        // Embranchement : vérifie une condition
        if ((x & 0x100000) != 0) x |= ~0x1FFFFF;
        // Renvoie une valeur à l'appelant
        return x;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int sectionIndexGetY(long index) {
        // Appelle une méthode
        int y = (int) (index >> 21) & 0x1FFFFF;
        // Embranchement : vérifie une condition
        if ((y & 0x100000) != 0) y |= ~0x1FFFFF;
        // Renvoie une valeur à l'appelant
        return y;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int sectionIndexGetZ(long index) {
        // Appelle une méthode
        int z = (int) index & 0x1FFFFF;
        // Embranchement : vérifie une condition
        if ((z & 0x100000) != 0) z |= ~0x1FFFFF;
        // Renvoie une valeur à l'appelant
        return z;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static long sectionIndexGlobal(int x, int y, int z) {
        // Appelle une méthode
        final int sectionX = globalToChunk(x);
        // Appelle une méthode
        final int sectionY = globalToChunk(y);
        // Appelle une méthode
        final int sectionZ = globalToChunk(z);
        // Renvoie une valeur à l'appelant
        return sectionIndex(sectionX, sectionY, sectionZ);
    // Fin d'un bloc/d'une expression
    }

    // BLOCK INDEX FROM SECTION (0-15 for each coordinate)

    // Début d'une méthode/d'un bloc
    public static int sectionBlockIndex(int x, int y, int z) {
        // Renvoie une valeur à l'appelant
        return (x << 8) | (z << 4) | y;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int sectionBlockIndexGetX(int index) {
        // Renvoie une valeur à l'appelant
        return (index >> 8) & SECTION_BOUND;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int sectionBlockIndexGetY(int index) {
        // Renvoie une valeur à l'appelant
        return index & SECTION_BOUND;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int sectionBlockIndexGetZ(int index) {
        // Renvoie une valeur à l'appelant
        return (index >> 4) & SECTION_BOUND;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static long encodeSectionBlockChange(int sectionBlockIndex, long value) {
        // To use with `MultiBlockChangePacket`
        // Affecte une valeur
        final long blockState = value << 12;
        // Renvoie une valeur à l'appelant
        return blockState | (long) sectionBlockIndex;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static long encodeSectionBlockChange(int localX, int localY, int localZ, long value) {
        // Renvoie une valeur à l'appelant
        return encodeSectionBlockChange(sectionBlockIndex(localX, localY, localZ), value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static short deltaShort4096(double newCoordinate, double oldCoordinate) {
        // Renvoie une valeur à l'appelant
        return (short) ((newCoordinate - oldCoordinate) * 4096);
    // Fin d'un bloc/d'une expression
    }

    // HASHING

    // Affecte une valeur
    private static final long PRIME_X = 0x9E37_79B9_7F4A_7C15L;  // Large prime for X axis
    // Affecte une valeur
    private static final long PRIME_Y = 0xF9F9_F9F9_ECEC_ECECL;  // Large prime for Y axis
    // Affecte une valeur
    private static final long PRIME_Z = 0xA5A5_A5A5_A5A5_A5A5L;  // Large prime for Z axis

    // Rotation amounts optimized for 3D coordinate separation
    // Affecte une valeur
    private static final int ROT_X = 31;  // Prime rotation for X
    // Affecte une valeur
    private static final int ROT_Y = 37;  // Prime rotation for Y
    // Affecte une valeur
    private static final int ROT_Z = 41;  // Prime rotation for Z

    // Affecte une valeur
    private static final long AVALANCHE_1 = 0xFF51_AFD7_ED55_8CCDL;
    // Affecte une valeur
    private static final long AVALANCHE_2 = 0xC4CE_B9FE_1A85_EC53L;

    // Affecte une valeur
    private static final long INT_SEED = 0xCAFE_BABE_DEAD_BEEFL;
    // Affecte une valeur
    private static final long DOUBLE_SEED = 0xDEAD_BEEF_CAFE_BABEL;

    // Début d'une méthode/d'un bloc
    public static long hashBlockCoord(int x, int y, int z) {
        // Affecte une valeur
        long h = INT_SEED;
        // Appelle une méthode
        h ^= Long.rotateLeft(Integer.toUnsignedLong(x) * PRIME_X, ROT_X);
        // Appelle une méthode
        h ^= Long.rotateLeft(Integer.toUnsignedLong(y) * PRIME_Y, ROT_Y);
        // Appelle une méthode
        h ^= Long.rotateLeft(Integer.toUnsignedLong(z) * PRIME_Z, ROT_Z);
        // Appelle une méthode
        h = Long.rotateLeft(h, 23) ^ (h >>> 17);
        // Instruction de code
        h ^= h >>> 33;
        // Instruction de code
        h *= AVALANCHE_1;
        // Instruction de code
        h ^= h >>> 33;
        // Instruction de code
        h *= AVALANCHE_2;
        // Instruction de code
        h ^= h >>> 33;
        // Renvoie une valeur à l'appelant
        return h;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static long hashBlockCoord(Point point) {
        // Renvoie une valeur à l'appelant
        return hashBlockCoord(point.blockX(), point.blockY(), point.blockZ());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static long hashGlobalCoord(double x, double y, double z) {
        // Affecte une valeur
        long h = DOUBLE_SEED;
        // Appelle une méthode
        long ix = Double.doubleToLongBits(x);
        // Appelle une méthode
        long iy = Double.doubleToLongBits(y);
        // Appelle une méthode
        long iz = Double.doubleToLongBits(z);
        // Appelle une méthode
        long ex = (ix >>> 52) & 0x7FFL;
        // Appelle une méthode
        long ey = (iy >>> 52) & 0x7FFL;
        // Appelle une méthode
        long ez = (iz >>> 52) & 0x7FFL;
        // Affecte une valeur
        long mx = ix & 0x000F_FFFF_FFFF_FFFFL;
        // Affecte une valeur
        long my = iy & 0x000F_FFFF_FFFF_FFFFL;
        // Affecte une valeur
        long mz = iz & 0x000F_FFFF_FFFF_FFFFL;
        // Appelle une méthode
        h ^= Long.rotateLeft(ix * PRIME_X, ROT_X);
        // Appelle une méthode
        h ^= Long.rotateLeft(iy * PRIME_Y, ROT_Y);
        // Appelle une méthode
        h ^= Long.rotateLeft(iz * PRIME_Z, ROT_Z);
        // Appelle une méthode
        h ^= Long.rotateLeft((ex << 32) | (ey << 16) | ez, 19);
        // Appelle une méthode
        h ^= Long.rotateLeft(mx ^ my ^ mz, 43);
        // Appelle une méthode
        h = Long.rotateLeft(h, 29) ^ (h >>> 13);
        // Instruction de code
        h ^= h >>> 33;
        // Instruction de code
        h *= AVALANCHE_1;
        // Instruction de code
        h ^= h >>> 33;
        // Instruction de code
        h *= AVALANCHE_2;
        // Instruction de code
        h ^= h >>> 33;
        // Renvoie une valeur à l'appelant
        return h;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static long hashGlobalCoord(Point point) {
        // Renvoie une valeur à l'appelant
        return hashGlobalCoord(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    // STRING FORMATTING

    // Début d'une méthode/d'un bloc
    public static String formatGlobalCoord(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return "(%.3f, %.3f, %.3f)".formatted(x, y, z);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static String formatGlobalCoord(Point point) {
        // Renvoie une valeur à l'appelant
        return formatGlobalCoord(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static String formatBlockCoord(int x, int y, int z) {
        // Renvoie une valeur à l'appelant
        return "(%d, %d, %d)".formatted(x, y, z);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static String formatBlockCoord(Point point) {
        // Renvoie une valeur à l'appelant
        return formatBlockCoord(point.blockX(), point.blockY(), point.blockZ());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
