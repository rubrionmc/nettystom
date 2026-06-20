// Déclaration du paquet de ce fichier
package net.minestom.server.coordinate;

// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.utils.Direction;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;

// Import d'une classe nécessaire
import java.util.function.DoubleUnaryOperator;

// Import statique d'un membre
import static net.minestom.server.coordinate.CoordConversion.SECTION_SIZE;
// Import statique d'un membre
import static net.minestom.server.coordinate.CoordConversion.globalToBlock;

/**
 * Represents an immutable block position.
 * <p>
 * Usage note: If you accept a block position as an argument to a method,
 * it's usually better to accept a Point rather than a BlockVec to avoid
 * callers continually having to convert.
 */
// Déclaration de type (classe/interface/enum/record)
public record BlockVec(int blockX, int blockY, int blockZ) implements Point {
    // Appelle une méthode
    public static final BlockVec ZERO = new BlockVec(0);
    // Appelle une méthode
    public static final BlockVec ONE = new BlockVec(1);
    // Appelle une méthode
    public static final BlockVec SECTION = new BlockVec(SECTION_SIZE);

    // Début d'une méthode/d'un bloc
    public BlockVec(double x, double y, double z) {
        // Appelle une méthode
        this(globalToBlock(x), globalToBlock(y), globalToBlock(z));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BlockVec(Point point) {
        // Appelle une méthode
        this(point.blockX(), point.blockY(), point.blockZ());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BlockVec(int value) {
        // Appelle une méthode
        this(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BlockVec(double value) {
        // Appelle une méthode
        this(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public double x() {
        // Renvoie une valeur à l'appelant
        return blockX;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public double y() {
        // Renvoie une valeur à l'appelant
        return blockY;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public double z() {
        // Renvoie une valeur à l'appelant
        return blockZ;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Point withX(DoubleUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return new Vec(operator.applyAsDouble(blockX), blockY, blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Point withX(double x) {
        // Renvoie une valeur à l'appelant
        return new Vec(x, blockY, blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BlockVec withBlockX(int x) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(x, blockY, blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Point withY(DoubleUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return new Vec(blockX, operator.applyAsDouble(blockY), blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Point withY(double y) {
        // Renvoie une valeur à l'appelant
        return new Vec(blockX, y, blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BlockVec withBlockY(int y) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockX, y, blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Point withZ(DoubleUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return new Vec(blockX, blockY, operator.applyAsDouble(blockZ));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Point withZ(double z) {
        // Renvoie une valeur à l'appelant
        return new Vec(blockX, blockY, z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BlockVec withBlockZ(int z) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockX, blockY, z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Point add(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Vec(blockX + x, blockY + y, blockZ + z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BlockVec add(int x, int y, int z) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockX + x, blockY + y, blockZ + z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Point add(Point point) {
        // Renvoie une valeur à l'appelant
        return new Vec(blockX + point.x(), blockY + point.y(), blockZ + point.z());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BlockVec add(BlockVec blockVec) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockX + blockVec.blockX, blockY + blockVec.blockY, blockZ + blockVec.blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Point add(double value) {
        // Renvoie une valeur à l'appelant
        return add(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BlockVec add(int value) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockX + value, blockY + value, blockZ + value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Point sub(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Vec(blockX - x, blockY - y, blockZ - z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BlockVec sub(int x, int y, int z) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockX - x, blockY - y, blockZ - z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Point sub(Point point) {
        // Renvoie une valeur à l'appelant
        return sub(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BlockVec sub(BlockVec blockVec) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockX - blockVec.blockX, blockY - blockVec.blockY, blockZ - blockVec.blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Point sub(double value) {
        // Renvoie une valeur à l'appelant
        return sub(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BlockVec sub(int value) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockX - value, blockY - value, blockZ - value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Point mul(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Vec(blockX * x, blockY * y, blockZ * z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BlockVec mul(int x, int y, int z) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockX * x, blockY * y, blockZ * z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Point mul(Point point) {
        // Renvoie une valeur à l'appelant
        return mul(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BlockVec mul(BlockVec blockVec) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockX * blockVec.blockX, blockY * blockVec.blockY, blockZ * blockVec.blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Point mul(double value) {
        // Renvoie une valeur à l'appelant
        return mul(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BlockVec mul(int value) {
        // Renvoie une valeur à l'appelant
        return mul(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Point div(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Vec(blockX / x, blockY / y, blockZ / z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BlockVec div(int x, int y, int z) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockX / x, blockY / y, blockZ / z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Point div(Point point) {
        // Renvoie une valeur à l'appelant
        return div(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BlockVec div(BlockVec blockVec) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockX / blockVec.blockX, blockY / blockVec.blockY, blockZ / blockVec.blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Point div(double value) {
        // Renvoie une valeur à l'appelant
        return div(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BlockVec div(int value) {
        // Renvoie une valeur à l'appelant
        return div(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BlockVec relative(BlockFace face) {
        // Appelle une méthode
        final Direction direction = face.toDirection();
        // Renvoie une valeur à l'appelant
        return add(direction.normalX(), direction.normalY(), direction.normalZ());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BlockVec neg() {
        // Renvoie une valeur à l'appelant
        return new BlockVec(-blockX, -blockY, -blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BlockVec abs() {
        // Renvoie une valeur à l'appelant
        return new BlockVec(Math.abs(blockX), Math.abs(blockY), Math.abs(blockZ));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Point min(Point point) {
        // Renvoie une valeur à l'appelant
        return new Vec(Math.min(blockX, point.x()), Math.min(blockY, point.y()), Math.min(blockZ, point.z()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BlockVec min(BlockVec point) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(Math.min(blockX, point.blockX()), Math.min(blockY, point.blockY()), Math.min(blockZ, point.blockZ()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BlockVec min(int x, int y, int z) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(Math.min(blockX, x), Math.min(blockY, y), Math.min(blockZ, z));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BlockVec min(int value) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(Math.min(blockX, value), Math.min(blockY, value), Math.min(blockZ, value));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Point max(Point point) {
        // Renvoie une valeur à l'appelant
        return new Vec(Math.max(blockX, point.x()), Math.max(blockY, point.y()), Math.max(blockZ, point.z()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BlockVec max(BlockVec point) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(Math.max(blockX, point.blockX()), Math.max(blockY, point.blockY()), Math.max(blockZ, point.blockZ()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BlockVec max(int x, int y, int z) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(Math.max(blockX, x), Math.max(blockY, y), Math.max(blockZ, z));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BlockVec max(int value) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(Math.max(blockX, value), Math.max(blockY, value), Math.max(blockZ, value));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public boolean samePoint(int x, int y, int z) {
        // Renvoie une valeur à l'appelant
        return blockX == x && blockY == y && blockZ == z;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public boolean samePoint(BlockVec blockVec) {
        // Renvoie une valeur à l'appelant
        return blockX == blockVec.blockX && blockY == blockVec.blockY && blockZ == blockVec.blockZ;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
