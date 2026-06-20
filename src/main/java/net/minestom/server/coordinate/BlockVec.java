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
// Import d'une classe nécessaire
import java.util.function.IntUnaryOperator;

// Import statique d'un membre
import static net.minestom.server.coordinate.CoordConversion.globalToBlock;

/**
 * Represents a 3D vector with block-aligned coordinates.
 * <p>
 * Using 12 bytes compared to 24 bytes for {@link Vec}.
 * Ideal for block positions, chunk coordinates, and anything on a grid.
 * <p>
 * Conversion: When constructed from {@code double} values,
 * coordinates are floored to the nearest integer block position.
 * <p>
 * Instances are immutable. All operations return new instances
 * (either {@link BlockVec} for integer results or {@link Vec} where doubles are used).
 *
 * @param blockX the block X coordinate
 * @param blockY the block Y coordinate
 * @param blockZ the block Z coordinate
 */
// Déclaration de type (classe/interface/enum/record)
public record BlockVec(int blockX, int blockY, int blockZ) implements Point {
    // Appelle une méthode
    public static final BlockVec ZERO = new BlockVec(0);
    // Appelle une méthode
    public static final BlockVec ONE = new BlockVec(1);
    // Appelle une méthode
    public static final BlockVec SECTION = new BlockVec(SECTION_SIZE);
    // Appelle une méthode
    public static final BlockVec CHUNK = new BlockVec(SECTION_SIZE, SECTION_SIZE);
    // Appelle une méthode
    public static final BlockVec REGION = new BlockVec(REGION_SIZE, REGION_SIZE);

    /**
     * Narrows an assumed global coordinate to a block coordinate by flooring the value.
     * <br>
     * Developer Note: Minestom should not call this constructor without explicit warnings.
     *
     * @param x the global x coordinate
     * @param y the global y coordinate
     * @param z the global z coordinate
     */
    // Début d'une méthode/d'un bloc
    public BlockVec(double x, double y, double z) {
        // Appelle une méthode
        this(globalToBlock(x), globalToBlock(y), globalToBlock(z));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a block vector with the given value for all coordinates.
     * See {@link #BlockVec(double, double, double)} for side effects.
     *
     * @param value the value
     */
    // Début d'une méthode/d'un bloc
    public BlockVec(double value) {
        // Appelle une méthode
        this(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a block vector from a point.
     *
     * @param point the point
     * @deprecated Use {@link Point#asBlockVec()} instead
     */
    // Annotation pour l'élément suivant
    @Deprecated(forRemoval = true)
    // Début d'une méthode/d'un bloc
    public BlockVec(Point point) {
        // Appelle une méthode
        this(point.blockX(), point.blockY(), point.blockZ());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a BlockVec with the blockX and blockZ, with blockY being zero.
     *
     * @param blockX the blockX
     * @param blockZ the blockZ
     */
    // Début d'une méthode/d'un bloc
    public BlockVec(int blockX, int blockZ) {
        // Appelle une méthode
        this(blockX, 0, blockZ);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a block vector with the given value for all coordinates (x/y/z).
     *
     * @param value the value
     */
    // Début d'une méthode/d'un bloc
    public BlockVec(int value) {
        // Appelle une méthode
        this(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public double x() {
        // Renvoie une valeur à l'appelant
        return blockX;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public double y() {
        // Renvoie une valeur à l'appelant
        return blockY;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public double z() {
        // Renvoie une valeur à l'appelant
        return blockZ;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Applies the given operator to this block vector.
     *
     * @param operator the operator to apply
     * @return the resulting block vector
     */
    // Début d'une méthode/d'un bloc
    public BlockVec apply(Operator operator) {
        // Renvoie une valeur à l'appelant
        return operator.apply(blockX, blockY, blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract("_ -> new")
    // Début d'une méthode/d'un bloc
    public Vec withX(DoubleUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return new Vec(operator.applyAsDouble(blockX), blockY, blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Vec withX(double x) {
        // Renvoie une valeur à l'appelant
        return new Vec(x, blockY, blockZ);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the block X coordinate to the given value.
     *
     * @param blockX the block X coordinate
     * @return the resulting block vector
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec withBlockX(int blockX) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockX, blockY, blockZ);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Applies the given operator to the block X coordinate.
     *
     * @param operator the operator to apply
     * @return the resulting block vector
     */
    // Annotation pour l'élément suivant
    @Contract("_ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec withBlockX(IntUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(operator.applyAsInt(blockX), blockY, blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract("_ -> new")
    // Début d'une méthode/d'un bloc
    public Vec withY(DoubleUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return new Vec(blockX, operator.applyAsDouble(blockY), blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Vec withY(double y) {
        // Renvoie une valeur à l'appelant
        return new Vec(blockX, y, blockZ);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the block Y coordinate to the given value.
     *
     * @param blockY the block Y coordinate
     * @return the resulting block vector
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec withBlockY(int blockY) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockX, blockY, blockZ);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Applies the given operator to the block Y coordinate.
     *
     * @param operator the operator to apply
     * @return the resulting block vector
     */
    // Annotation pour l'élément suivant
    @Contract("_ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec withBlockY(IntUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockX, operator.applyAsInt(blockY), blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract("_ -> new")
    // Début d'une méthode/d'un bloc
    public Vec withZ(DoubleUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return new Vec(blockX, blockY, operator.applyAsDouble(blockZ));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Vec withZ(double z) {
        // Renvoie une valeur à l'appelant
        return new Vec(blockX, blockY, z);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the block Z coordinate to the given value.
     *
     * @param blockZ the block Z coordinate
     * @return the resulting block vector
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec withBlockZ(int blockZ) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockX, blockY, blockZ);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Applies the given operator to the block Z coordinate.
     *
     * @param operator the operator to apply
     * @return the resulting block vector
     */
    // Annotation pour l'élément suivant
    @Contract("_ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec withBlockZ(IntUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockX, blockY, operator.applyAsInt(blockZ));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Début d'une méthode/d'un bloc
    public Vec add(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Vec(blockX + x, blockY + y, blockZ + z);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Adds the given block XYZ to this block vector.
     *
     * @param blockX the block X to add
     * @param blockY the block Y to add
     * @param blockZ the block Z to add
     * @return the resulting block vector
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec add(int blockX, int blockY, int blockZ) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(this.blockX + blockX, this.blockY + blockY, this.blockZ + blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Vec add(Point point) {
        // Renvoie une valeur à l'appelant
        return new Vec(blockX + point.x(), blockY + point.y(), blockZ + point.z());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Adds the given block vector to this block vector.
     *
     * @param blockVec the block vector to add
     * @return the resulting block vector
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec add(BlockVec blockVec) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockX + blockVec.blockX, blockY + blockVec.blockY, blockZ + blockVec.blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Vec add(double value) {
        // Renvoie une valeur à l'appelant
        return add(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Adds the given integer value to all coordinates of this block vector.
     *
     * @param value the value to add
     * @return the resulting block vector
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec add(int value) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockX + value, blockY + value, blockZ + value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Début d'une méthode/d'un bloc
    public Vec sub(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Vec(blockX - x, blockY - y, blockZ - z);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Subtracts the given block XYZ from this block vector.
     *
     * @param blockX the block X to subtract
     * @param blockY the block Y to subtract
     * @param blockZ the block Z to subtract
     * @return the resulting block vector
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec sub(int blockX, int blockY, int blockZ) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(this.blockX - blockX, this.blockY - blockY, this.blockZ - blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Vec sub(Point point) {
        // Renvoie une valeur à l'appelant
        return sub(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Subtracts the given block vector from this block vector.
     *
     * @param blockVec the block vector to subtract
     * @return the resulting block vector
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec sub(BlockVec blockVec) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockX - blockVec.blockX, blockY - blockVec.blockY, blockZ - blockVec.blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Vec sub(double value) {
        // Renvoie une valeur à l'appelant
        return sub(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Subtracts the given integer value from all coordinates of this block vector.
     *
     * @param value the value to subtract
     * @return the resulting block vector
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec sub(int value) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockX - value, blockY - value, blockZ - value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Début d'une méthode/d'un bloc
    public Vec mul(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Vec(blockX * x, blockY * y, blockZ * z);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Multiplies this block vector by the given integer values.
     *
     * @param blockX the block x to multiply by
     * @param blockY the block y to multiply by
     * @param blockZ the block z to multiply by
     * @return the resulting block vector
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec mul(int blockX, int blockY, int blockZ) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(this.blockX * blockX, this.blockY * blockY, this.blockZ * blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Vec mul(Point point) {
        // Renvoie une valeur à l'appelant
        return mul(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Multiplies this block vector by another block vector.
     *
     * @param blockVec the block vector to multiply by
     * @return the resulting block vector
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec mul(BlockVec blockVec) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockX * blockVec.blockX, blockY * blockVec.blockY, blockZ * blockVec.blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Vec mul(double value) {
        // Renvoie une valeur à l'appelant
        return mul(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Multiplies this block vector by the given integer value.
     *
     * @param value the value to multiply by
     * @return the resulting block vector
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec mul(int value) {
        // Renvoie une valeur à l'appelant
        return mul(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Début d'une méthode/d'un bloc
    public Vec div(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Vec(blockX / x, blockY / y, blockZ / z);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Divides this block vector by the given integer values.
     *
     * @param blockX the x divisor
     * @param blockY the y divisor
     * @param blockZ the z divisor
     * @return the resulting block vector
     * @throws ArithmeticException if any of the divisors is zero
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec div(int blockX, int blockY, int blockZ) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(this.blockX / blockX, this.blockY / blockY, this.blockZ / blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Vec div(Point point) {
        // Renvoie une valeur à l'appelant
        return div(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Divides this block vector by another block vector.
     *
     * @param blockVec the block vector divisor
     * @return the resulting block vector
     * @throws ArithmeticException if any component of the divisor is zero
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec div(BlockVec blockVec) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockX / blockVec.blockX, blockY / blockVec.blockY, blockZ / blockVec.blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Vec div(double value) {
        // Renvoie une valeur à l'appelant
        return div(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Divides this block vector by the given integer value.
     *
     * @param value the divisor
     * @return the resulting block vector
     * @throws ArithmeticException if the divisor is zero
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec div(int value) {
        // Renvoie une valeur à l'appelant
        return div(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec relative(BlockFace face) {
        // Cant use super because of return type of #add(double, double, double), use #add(int, int, int) instead
        // Appelle une méthode
        final Direction direction = face.toDirection();
        // Renvoie une valeur à l'appelant
        return add(direction.normalX(), direction.normalY(), direction.normalZ());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "-> new")
    // Début d'une méthode/d'un bloc
    public BlockVec neg() {
        // Renvoie une valeur à l'appelant
        return new BlockVec(-blockX, -blockY, -blockZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "-> new")
    // Début d'une méthode/d'un bloc
    public BlockVec abs() {
        // Renvoie une valeur à l'appelant
        return new BlockVec(Math.abs(blockX), Math.abs(blockY), Math.abs(blockZ));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Vec min(Point point) {
        // Renvoie une valeur à l'appelant
        return new Vec(Math.min(blockX, point.x()), Math.min(blockY, point.y()), Math.min(blockZ, point.z()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Début d'une méthode/d'un bloc
    public Vec min(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Vec(Math.min(blockX, x), Math.min(blockY, y), Math.min(blockZ, z));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Vec min(double value) {
        // Renvoie une valeur à l'appelant
        return new Vec(Math.min(blockX, value), Math.min(blockY, value), Math.min(blockZ, value));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Calculates the minimum between this block vector and another block vector.
     *
     * @param point the other block vector
     * @return the resulting block vector
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec min(BlockVec point) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(Math.min(blockX, point.blockX()), Math.min(blockY, point.blockY()), Math.min(blockZ, point.blockZ()));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Calculates the minimum between this block vector and the given block coordinates.
     *
     * @param blockX the blockX
     * @param blockY the blockY
     * @param blockZ the blockZ
     * @return the resulting block vector
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec min(int blockX, int blockY, int blockZ) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(Math.min(this.blockX, blockX), Math.min(this.blockY, blockY), Math.min(this.blockZ, blockZ));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Calculates the minimum between this block vector and the given integer value.
     *
     * @param value the value
     * @return the resulting block vector
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec min(int value) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(Math.min(blockX, value), Math.min(blockY, value), Math.min(blockZ, value));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Vec max(Point point) {
        // Renvoie une valeur à l'appelant
        return new Vec(Math.max(blockX, point.x()), Math.max(blockY, point.y()), Math.max(blockZ, point.z()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Début d'une méthode/d'un bloc
    public Vec max(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Vec(Math.max(blockX, x), Math.max(blockY, y), Math.max(blockZ, z));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Vec max(double value) {
        // Renvoie une valeur à l'appelant
        return new Vec(Math.max(blockX, value), Math.max(blockY, value), Math.max(blockZ, value));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "-> new")
    // Début d'une méthode/d'un bloc
    public Vec normalize() {
        // Appelle une méthode
        final double length = length();
        // Renvoie une valeur à l'appelant
        return new Vec(blockX / length, blockY / length, blockZ / length);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Vec cross(Point point) {
        // Renvoie une valeur à l'appelant
        return new Vec(blockY * point.z() - blockZ * point.y(),
                // Instruction de code
                blockZ * point.x() - blockX * point.z(),
                // Appelle une méthode
                blockX * point.y() - blockY * point.x());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Calculates the cross product of this point with another. The cross
     * product is defined as:
     * <ul>
     * <li>x = y1 * z2 - y2 * z1
     * <li>y = z1 * x2 - z2 * x1
     * <li>z = x1 * y2 - x2 * y1
     * </ul>
     *
     * @param point the other point
     * @return the cross product point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec cross(BlockVec point) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(blockY * point.blockZ - blockZ * point.blockY,
                // Instruction de code
                blockZ * point.blockX - blockX * point.blockZ,
                // Instruction de code
                blockX * point.blockY - blockY * point.blockX);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _ -> new")
    // Début d'une méthode/d'un bloc
    public Vec lerp(Point point, double alpha) {
        // Renvoie une valeur à l'appelant
        return new Vec(blockX + (alpha * (point.x() - blockX)),
                // Instruction de code
                blockY + (alpha * (point.y() - blockY)),
                // Appelle une méthode
                blockZ + (alpha * (point.z() - blockZ)));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Calculates the maximum between this block vector and another block vector.
     *
     * @param point the other block vector
     * @return the resulting block vector
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec max(BlockVec point) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(Math.max(blockX, point.blockX()), Math.max(blockY, point.blockY()), Math.max(blockZ, point.blockZ()));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Calculates the maximum between this block vector and the given block coordinates (x/y/z).
     *
     * @param blockX the block X
     * @param blockY the block Y
     * @param blockZ the block Z
     * @return the resulting block vector
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec max(int blockX, int blockY, int blockZ) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(Math.max(this.blockX, blockX), Math.max(this.blockY, blockY), Math.max(this.blockZ, blockZ));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Calculates the maximum between this block vector and the given integer value.
     *
     * @param value the value
     * @return the resulting block vector
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public BlockVec max(int value) {
        // Renvoie une valeur à l'appelant
        return new BlockVec(Math.max(blockX, value), Math.max(blockY, value), Math.max(blockZ, value));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if two block positions have the same coordinates (x/y/z).
     *
     * @param blockX the block X coordinate
     * @param blockY the block Y coordinate
     * @param blockZ the block Z coordinate
     * @return true if the coordinates are the same
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public boolean samePoint(int blockX, int blockY, int blockZ) {
        // Renvoie une valeur à l'appelant
        return this.blockX == blockX && this.blockY == blockY && this.blockZ == blockZ;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if two block vectors have the same coordinates (x/y/z).
     *
     * @param blockVec the other block vector
     * @return true if the coordinates are the same
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public boolean samePoint(BlockVec blockVec) {
        // Renvoie une valeur à l'appelant
        return blockX == blockVec.blockX && blockY == blockVec.blockY && blockZ == blockVec.blockZ;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Does nothing as this is already a {@link BlockVec}.
     * <p>
     * Marked as deprecated to warn against redundant usage.
     *
     * @return this block vector
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "-> this")
    // Début d'une méthode/d'un bloc
    public BlockVec asBlockVec() {
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * A functional interface representing an operation on the components of a {@link BlockVec}.
     */
    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface Operator {
        /**
         * Creates an operator from the given {@link IntUnaryOperator}.
         *
         * @param operator the operator to convert
         * @return the resulting operator
         */
        // Début d'une méthode/d'un bloc
        static Operator operator(IntUnaryOperator operator) {
            // Renvoie une valeur à l'appelant
            return (blockX, blockY, blockZ) -> new BlockVec(
                    // Instruction de code
                    operator.applyAsInt(blockX),
                    // Instruction de code
                    operator.applyAsInt(blockY),
                    // Appelle une méthode
                    operator.applyAsInt(blockZ));
        // Fin d'un bloc/d'une expression
        }

        /**
         * Applies this operator to the given block coordinates.
         *
         * @param blockX the blockX component
         * @param blockY the blockY component
         * @param blockZ the blockZ component
         * @return the resulting block vector
         */
        // Appelle une méthode
        BlockVec apply(int blockX, int blockY, int blockZ);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
