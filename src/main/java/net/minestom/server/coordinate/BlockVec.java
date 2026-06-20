// Package declaration for this file
package net.minestom.server.coordinate;

// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import net.minestom.server.utils.Direction;
// Import of a required class
import org.jetbrains.annotations.Contract;

// Import of a required class
import java.util.function.DoubleUnaryOperator;
// Import of a required class
import java.util.function.IntUnaryOperator;

// Static import of a member
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
// Type declaration (class/interface/enum/record)
public record BlockVec(int blockX, int blockY, int blockZ) implements Point {
    // Calls a method
    public static final BlockVec ZERO = new BlockVec(0);
    // Calls a method
    public static final BlockVec ONE = new BlockVec(1);
    // Calls a method
    public static final BlockVec SECTION = new BlockVec(SECTION_SIZE);
    // Calls a method
    public static final BlockVec CHUNK = new BlockVec(SECTION_SIZE, SECTION_SIZE);
    // Calls a method
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
    // Start of a method/block
    public BlockVec(double x, double y, double z) {
        // Calls a method
        this(globalToBlock(x), globalToBlock(y), globalToBlock(z));
    // End of a block/expression
    }

    /**
     * Creates a block vector with the given value for all coordinates.
     * See {@link #BlockVec(double, double, double)} for side effects.
     *
     * @param value the value
     */
    // Start of a method/block
    public BlockVec(double value) {
        // Calls a method
        this(value, value, value);
    // End of a block/expression
    }

    /**
     * Creates a block vector from a point.
     *
     * @param point the point
     * @deprecated Use {@link Point#asBlockVec()} instead
     */
    // Annotation for the following element
    @Deprecated(forRemoval = true)
    // Start of a method/block
    public BlockVec(Point point) {
        // Calls a method
        this(point.blockX(), point.blockY(), point.blockZ());
    // End of a block/expression
    }

    /**
     * Creates a BlockVec with the blockX and blockZ, with blockY being zero.
     *
     * @param blockX the blockX
     * @param blockZ the blockZ
     */
    // Start of a method/block
    public BlockVec(int blockX, int blockZ) {
        // Calls a method
        this(blockX, 0, blockZ);
    // End of a block/expression
    }

    /**
     * Creates a block vector with the given value for all coordinates (x/y/z).
     *
     * @param value the value
     */
    // Start of a method/block
    public BlockVec(int value) {
        // Calls a method
        this(value, value, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public double x() {
        // Returns a value to the caller
        return blockX;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public double y() {
        // Returns a value to the caller
        return blockY;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public double z() {
        // Returns a value to the caller
        return blockZ;
    // End of a block/expression
    }

    /**
     * Applies the given operator to this block vector.
     *
     * @param operator the operator to apply
     * @return the resulting block vector
     */
    // Start of a method/block
    public BlockVec apply(Operator operator) {
        // Returns a value to the caller
        return operator.apply(blockX, blockY, blockZ);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract("_ -> new")
    // Start of a method/block
    public Vec withX(DoubleUnaryOperator operator) {
        // Returns a value to the caller
        return new Vec(operator.applyAsDouble(blockX), blockY, blockZ);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec withX(double x) {
        // Returns a value to the caller
        return new Vec(x, blockY, blockZ);
    // End of a block/expression
    }

    /**
     * Sets the block X coordinate to the given value.
     *
     * @param blockX the block X coordinate
     * @return the resulting block vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public BlockVec withBlockX(int blockX) {
        // Returns a value to the caller
        return new BlockVec(blockX, blockY, blockZ);
    // End of a block/expression
    }

    /**
     * Applies the given operator to the block X coordinate.
     *
     * @param operator the operator to apply
     * @return the resulting block vector
     */
    // Annotation for the following element
    @Contract("_ -> new")
    // Start of a method/block
    public BlockVec withBlockX(IntUnaryOperator operator) {
        // Returns a value to the caller
        return new BlockVec(operator.applyAsInt(blockX), blockY, blockZ);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract("_ -> new")
    // Start of a method/block
    public Vec withY(DoubleUnaryOperator operator) {
        // Returns a value to the caller
        return new Vec(blockX, operator.applyAsDouble(blockY), blockZ);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec withY(double y) {
        // Returns a value to the caller
        return new Vec(blockX, y, blockZ);
    // End of a block/expression
    }

    /**
     * Sets the block Y coordinate to the given value.
     *
     * @param blockY the block Y coordinate
     * @return the resulting block vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public BlockVec withBlockY(int blockY) {
        // Returns a value to the caller
        return new BlockVec(blockX, blockY, blockZ);
    // End of a block/expression
    }

    /**
     * Applies the given operator to the block Y coordinate.
     *
     * @param operator the operator to apply
     * @return the resulting block vector
     */
    // Annotation for the following element
    @Contract("_ -> new")
    // Start of a method/block
    public BlockVec withBlockY(IntUnaryOperator operator) {
        // Returns a value to the caller
        return new BlockVec(blockX, operator.applyAsInt(blockY), blockZ);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract("_ -> new")
    // Start of a method/block
    public Vec withZ(DoubleUnaryOperator operator) {
        // Returns a value to the caller
        return new Vec(blockX, blockY, operator.applyAsDouble(blockZ));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec withZ(double z) {
        // Returns a value to the caller
        return new Vec(blockX, blockY, z);
    // End of a block/expression
    }

    /**
     * Sets the block Z coordinate to the given value.
     *
     * @param blockZ the block Z coordinate
     * @return the resulting block vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public BlockVec withBlockZ(int blockZ) {
        // Returns a value to the caller
        return new BlockVec(blockX, blockY, blockZ);
    // End of a block/expression
    }

    /**
     * Applies the given operator to the block Z coordinate.
     *
     * @param operator the operator to apply
     * @return the resulting block vector
     */
    // Annotation for the following element
    @Contract("_ -> new")
    // Start of a method/block
    public BlockVec withBlockZ(IntUnaryOperator operator) {
        // Returns a value to the caller
        return new BlockVec(blockX, blockY, operator.applyAsInt(blockZ));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public Vec add(double x, double y, double z) {
        // Returns a value to the caller
        return new Vec(blockX + x, blockY + y, blockZ + z);
    // End of a block/expression
    }

    /**
     * Adds the given block XYZ to this block vector.
     *
     * @param blockX the block X to add
     * @param blockY the block Y to add
     * @param blockZ the block Z to add
     * @return the resulting block vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public BlockVec add(int blockX, int blockY, int blockZ) {
        // Returns a value to the caller
        return new BlockVec(this.blockX + blockX, this.blockY + blockY, this.blockZ + blockZ);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec add(Point point) {
        // Returns a value to the caller
        return new Vec(blockX + point.x(), blockY + point.y(), blockZ + point.z());
    // End of a block/expression
    }

    /**
     * Adds the given block vector to this block vector.
     *
     * @param blockVec the block vector to add
     * @return the resulting block vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public BlockVec add(BlockVec blockVec) {
        // Returns a value to the caller
        return new BlockVec(blockX + blockVec.blockX, blockY + blockVec.blockY, blockZ + blockVec.blockZ);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec add(double value) {
        // Returns a value to the caller
        return add(value, value, value);
    // End of a block/expression
    }

    /**
     * Adds the given integer value to all coordinates of this block vector.
     *
     * @param value the value to add
     * @return the resulting block vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public BlockVec add(int value) {
        // Returns a value to the caller
        return new BlockVec(blockX + value, blockY + value, blockZ + value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public Vec sub(double x, double y, double z) {
        // Returns a value to the caller
        return new Vec(blockX - x, blockY - y, blockZ - z);
    // End of a block/expression
    }

    /**
     * Subtracts the given block XYZ from this block vector.
     *
     * @param blockX the block X to subtract
     * @param blockY the block Y to subtract
     * @param blockZ the block Z to subtract
     * @return the resulting block vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public BlockVec sub(int blockX, int blockY, int blockZ) {
        // Returns a value to the caller
        return new BlockVec(this.blockX - blockX, this.blockY - blockY, this.blockZ - blockZ);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec sub(Point point) {
        // Returns a value to the caller
        return sub(point.x(), point.y(), point.z());
    // End of a block/expression
    }

    /**
     * Subtracts the given block vector from this block vector.
     *
     * @param blockVec the block vector to subtract
     * @return the resulting block vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public BlockVec sub(BlockVec blockVec) {
        // Returns a value to the caller
        return new BlockVec(blockX - blockVec.blockX, blockY - blockVec.blockY, blockZ - blockVec.blockZ);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec sub(double value) {
        // Returns a value to the caller
        return sub(value, value, value);
    // End of a block/expression
    }

    /**
     * Subtracts the given integer value from all coordinates of this block vector.
     *
     * @param value the value to subtract
     * @return the resulting block vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public BlockVec sub(int value) {
        // Returns a value to the caller
        return new BlockVec(blockX - value, blockY - value, blockZ - value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public Vec mul(double x, double y, double z) {
        // Returns a value to the caller
        return new Vec(blockX * x, blockY * y, blockZ * z);
    // End of a block/expression
    }

    /**
     * Multiplies this block vector by the given integer values.
     *
     * @param blockX the block x to multiply by
     * @param blockY the block y to multiply by
     * @param blockZ the block z to multiply by
     * @return the resulting block vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public BlockVec mul(int blockX, int blockY, int blockZ) {
        // Returns a value to the caller
        return new BlockVec(this.blockX * blockX, this.blockY * blockY, this.blockZ * blockZ);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec mul(Point point) {
        // Returns a value to the caller
        return mul(point.x(), point.y(), point.z());
    // End of a block/expression
    }

    /**
     * Multiplies this block vector by another block vector.
     *
     * @param blockVec the block vector to multiply by
     * @return the resulting block vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public BlockVec mul(BlockVec blockVec) {
        // Returns a value to the caller
        return new BlockVec(blockX * blockVec.blockX, blockY * blockVec.blockY, blockZ * blockVec.blockZ);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec mul(double value) {
        // Returns a value to the caller
        return mul(value, value, value);
    // End of a block/expression
    }

    /**
     * Multiplies this block vector by the given integer value.
     *
     * @param value the value to multiply by
     * @return the resulting block vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public BlockVec mul(int value) {
        // Returns a value to the caller
        return mul(value, value, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public Vec div(double x, double y, double z) {
        // Returns a value to the caller
        return new Vec(blockX / x, blockY / y, blockZ / z);
    // End of a block/expression
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
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public BlockVec div(int blockX, int blockY, int blockZ) {
        // Returns a value to the caller
        return new BlockVec(this.blockX / blockX, this.blockY / blockY, this.blockZ / blockZ);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec div(Point point) {
        // Returns a value to the caller
        return div(point.x(), point.y(), point.z());
    // End of a block/expression
    }

    /**
     * Divides this block vector by another block vector.
     *
     * @param blockVec the block vector divisor
     * @return the resulting block vector
     * @throws ArithmeticException if any component of the divisor is zero
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public BlockVec div(BlockVec blockVec) {
        // Returns a value to the caller
        return new BlockVec(blockX / blockVec.blockX, blockY / blockVec.blockY, blockZ / blockVec.blockZ);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec div(double value) {
        // Returns a value to the caller
        return div(value, value, value);
    // End of a block/expression
    }

    /**
     * Divides this block vector by the given integer value.
     *
     * @param value the divisor
     * @return the resulting block vector
     * @throws ArithmeticException if the divisor is zero
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public BlockVec div(int value) {
        // Returns a value to the caller
        return div(value, value, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public BlockVec relative(BlockFace face) {
        // Cant use super because of return type of #add(double, double, double), use #add(int, int, int) instead
        // Calls a method
        final Direction direction = face.toDirection();
        // Returns a value to the caller
        return add(direction.normalX(), direction.normalY(), direction.normalZ());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "-> new")
    // Start of a method/block
    public BlockVec neg() {
        // Returns a value to the caller
        return new BlockVec(-blockX, -blockY, -blockZ);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "-> new")
    // Start of a method/block
    public BlockVec abs() {
        // Returns a value to the caller
        return new BlockVec(Math.abs(blockX), Math.abs(blockY), Math.abs(blockZ));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec min(Point point) {
        // Returns a value to the caller
        return new Vec(Math.min(blockX, point.x()), Math.min(blockY, point.y()), Math.min(blockZ, point.z()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public Vec min(double x, double y, double z) {
        // Returns a value to the caller
        return new Vec(Math.min(blockX, x), Math.min(blockY, y), Math.min(blockZ, z));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec min(double value) {
        // Returns a value to the caller
        return new Vec(Math.min(blockX, value), Math.min(blockY, value), Math.min(blockZ, value));
    // End of a block/expression
    }

    /**
     * Calculates the minimum between this block vector and another block vector.
     *
     * @param point the other block vector
     * @return the resulting block vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public BlockVec min(BlockVec point) {
        // Returns a value to the caller
        return new BlockVec(Math.min(blockX, point.blockX()), Math.min(blockY, point.blockY()), Math.min(blockZ, point.blockZ()));
    // End of a block/expression
    }

    /**
     * Calculates the minimum between this block vector and the given block coordinates.
     *
     * @param blockX the blockX
     * @param blockY the blockY
     * @param blockZ the blockZ
     * @return the resulting block vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public BlockVec min(int blockX, int blockY, int blockZ) {
        // Returns a value to the caller
        return new BlockVec(Math.min(this.blockX, blockX), Math.min(this.blockY, blockY), Math.min(this.blockZ, blockZ));
    // End of a block/expression
    }

    /**
     * Calculates the minimum between this block vector and the given integer value.
     *
     * @param value the value
     * @return the resulting block vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public BlockVec min(int value) {
        // Returns a value to the caller
        return new BlockVec(Math.min(blockX, value), Math.min(blockY, value), Math.min(blockZ, value));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec max(Point point) {
        // Returns a value to the caller
        return new Vec(Math.max(blockX, point.x()), Math.max(blockY, point.y()), Math.max(blockZ, point.z()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public Vec max(double x, double y, double z) {
        // Returns a value to the caller
        return new Vec(Math.max(blockX, x), Math.max(blockY, y), Math.max(blockZ, z));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec max(double value) {
        // Returns a value to the caller
        return new Vec(Math.max(blockX, value), Math.max(blockY, value), Math.max(blockZ, value));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "-> new")
    // Start of a method/block
    public Vec normalize() {
        // Calls a method
        final double length = length();
        // Returns a value to the caller
        return new Vec(blockX / length, blockY / length, blockZ / length);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec cross(Point point) {
        // Returns a value to the caller
        return new Vec(blockY * point.z() - blockZ * point.y(),
                // Code statement
                blockZ * point.x() - blockX * point.z(),
                // Calls a method
                blockX * point.y() - blockY * point.x());
    // End of a block/expression
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
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public BlockVec cross(BlockVec point) {
        // Returns a value to the caller
        return new BlockVec(blockY * point.blockZ - blockZ * point.blockY,
                // Code statement
                blockZ * point.blockX - blockX * point.blockZ,
                // Code statement
                blockX * point.blockY - blockY * point.blockX);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_, _ -> new")
    // Start of a method/block
    public Vec lerp(Point point, double alpha) {
        // Returns a value to the caller
        return new Vec(blockX + (alpha * (point.x() - blockX)),
                // Code statement
                blockY + (alpha * (point.y() - blockY)),
                // Calls a method
                blockZ + (alpha * (point.z() - blockZ)));
    // End of a block/expression
    }

    /**
     * Calculates the maximum between this block vector and another block vector.
     *
     * @param point the other block vector
     * @return the resulting block vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public BlockVec max(BlockVec point) {
        // Returns a value to the caller
        return new BlockVec(Math.max(blockX, point.blockX()), Math.max(blockY, point.blockY()), Math.max(blockZ, point.blockZ()));
    // End of a block/expression
    }

    /**
     * Calculates the maximum between this block vector and the given block coordinates (x/y/z).
     *
     * @param blockX the block X
     * @param blockY the block Y
     * @param blockZ the block Z
     * @return the resulting block vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public BlockVec max(int blockX, int blockY, int blockZ) {
        // Returns a value to the caller
        return new BlockVec(Math.max(this.blockX, blockX), Math.max(this.blockY, blockY), Math.max(this.blockZ, blockZ));
    // End of a block/expression
    }

    /**
     * Calculates the maximum between this block vector and the given integer value.
     *
     * @param value the value
     * @return the resulting block vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public BlockVec max(int value) {
        // Returns a value to the caller
        return new BlockVec(Math.max(blockX, value), Math.max(blockY, value), Math.max(blockZ, value));
    // End of a block/expression
    }

    /**
     * Checks if two block positions have the same coordinates (x/y/z).
     *
     * @param blockX the block X coordinate
     * @param blockY the block Y coordinate
     * @param blockZ the block Z coordinate
     * @return true if the coordinates are the same
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public boolean samePoint(int blockX, int blockY, int blockZ) {
        // Returns a value to the caller
        return this.blockX == blockX && this.blockY == blockY && this.blockZ == blockZ;
    // End of a block/expression
    }

    /**
     * Checks if two block vectors have the same coordinates (x/y/z).
     *
     * @param blockVec the other block vector
     * @return true if the coordinates are the same
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public boolean samePoint(BlockVec blockVec) {
        // Returns a value to the caller
        return blockX == blockVec.blockX && blockY == blockVec.blockY && blockZ == blockVec.blockZ;
    // End of a block/expression
    }

    /**
     * Does nothing as this is already a {@link BlockVec}.
     * <p>
     * Marked as deprecated to warn against redundant usage.
     *
     * @return this block vector
     */
    // Annotation for the following element
    @Deprecated
    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "-> this")
    // Start of a method/block
    public BlockVec asBlockVec() {
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * A functional interface representing an operation on the components of a {@link BlockVec}.
     */
    // Annotation for the following element
    @FunctionalInterface
    // Type declaration (class/interface/enum/record)
    public interface Operator {
        /**
         * Creates an operator from the given {@link IntUnaryOperator}.
         *
         * @param operator the operator to convert
         * @return the resulting operator
         */
        // Start of a method/block
        static Operator operator(IntUnaryOperator operator) {
            // Returns a value to the caller
            return (blockX, blockY, blockZ) -> new BlockVec(
                    // Code statement
                    operator.applyAsInt(blockX),
                    // Code statement
                    operator.applyAsInt(blockY),
                    // Calls a method
                    operator.applyAsInt(blockZ));
        // End of a block/expression
        }

        /**
         * Applies this operator to the given block coordinates.
         *
         * @param blockX the blockX component
         * @param blockY the blockY component
         * @param blockZ the blockZ component
         * @return the resulting block vector
         */
        // Calls a method
        BlockVec apply(int blockX, int blockY, int blockZ);
    // End of a block/expression
    }
// End of a block/expression
}
