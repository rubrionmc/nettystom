// Package declaration for this file
package net.minestom.server.coordinate;

// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import org.jetbrains.annotations.Contract;

// Import of a required class
import java.util.function.DoubleUnaryOperator;

/**
 * Represents a 3D vector with double-precision coordinates.
 * <p>
 * This is the fundamental coordinate type for precise spatial calculations.
 * Supports standard vector operations including dot product, cross product,
 * normalization, and rotation.
 *
 * @param x the X coordinate
 * @param y the Y coordinate
 * @param z the Z coordinate
 */
// Type declaration (class/interface/enum/record)
public record Vec(double x, double y, double z) implements Point {
    // Calls a method
    public static final Vec ZERO = new Vec(0);
    // Calls a method
    public static final Vec ONE = new Vec(1);
    // Calls a method
    public static final Vec SECTION = new Vec(SECTION_SIZE);
    // Calls a method
    public static final Vec CHUNK = new Vec(SECTION_SIZE, SECTION_SIZE);
    // Calls a method
    public static final Vec REGION = new Vec(REGION_SIZE, REGION_SIZE);

    /**
     * Creates a new vec with the [x;z] coordinates set. Y is set to 0.
     *
     * @param x the X coordinate
     * @param z the Z coordinate
     */
    // Start of a method/block
    public Vec(double x, double z) {
        // Calls a method
        this(x, 0, z);
    // End of a block/expression
    }

    /**
     * Creates a vec with all 3 coordinates sharing the same value.
     *
     * @param value the coordinates
     */
    // Start of a method/block
    public Vec(double value) {
        // Calls a method
        this(value, value, value);
    // End of a block/expression
    }

    /**
     * Converts a {@link Point} into a {@link Vec}.
     * Will cast if possible, or instantiate a new object.
     *
     * @param point the point to convert
     * @return the converted vector
     * @deprecated use {@link Point#asVec()} instead
     */
    // Annotation for the following element
    @Deprecated(forRemoval = true)
    // Start of a method/block
    public static Vec fromPoint(Point point) {
        // Branch: checks a condition
        if (point instanceof Vec vec) return vec;
        // Returns a value to the caller
        return new Vec(point.x(), point.y(), point.z());
    // End of a block/expression
    }

    /**
     * Applies the given operator to this vector's coordinates (x/y/z).
     *
     * @param operator the operator to apply
     * @return the resulting vector
     */
    // Start of a method/block
    public Vec apply(Operator operator) {
        // Returns a value to the caller
        return operator.apply(x, y, z);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract("_ -> new")
    // Start of a method/block
    public Vec withX(DoubleUnaryOperator operator) {
        // Returns a value to the caller
        return new Vec(operator.applyAsDouble(x), y, z);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec withX(double x) {
        // Returns a value to the caller
        return new Vec(x, y, z);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract("_ -> new")
    // Start of a method/block
    public Vec withY(DoubleUnaryOperator operator) {
        // Returns a value to the caller
        return new Vec(x, operator.applyAsDouble(y), z);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec withY(double y) {
        // Returns a value to the caller
        return new Vec(x, y, z);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract("_ -> new")
    // Start of a method/block
    public Vec withZ(DoubleUnaryOperator operator) {
        // Returns a value to the caller
        return new Vec(x, y, operator.applyAsDouble(z));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec withZ(double z) {
        // Returns a value to the caller
        return new Vec(x, y, z);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public Vec add(double x, double y, double z) {
        // Returns a value to the caller
        return new Vec(this.x + x, this.y + y, this.z + z);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec add(Point point) {
        // Returns a value to the caller
        return add(point.x(), point.y(), point.z());
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

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public Vec sub(double x, double y, double z) {
        // Returns a value to the caller
        return new Vec(this.x - x, this.y - y, this.z - z);
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

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public Vec mul(double x, double y, double z) {
        // Returns a value to the caller
        return new Vec(this.x * x, this.y * y, this.z * z);
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

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public Vec div(double x, double y, double z) {
        // Returns a value to the caller
        return new Vec(this.x / x, this.y / y, this.z / z);
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

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec relative(BlockFace face) {
        // Returns a value to the caller
        return (Vec) Point.super.relative(face);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "-> new")
    // Start of a method/block
    public Vec neg() {
        // Returns a value to the caller
        return new Vec(-x, -y, -z);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "-> new")
    // Start of a method/block
    public Vec abs() {
        // Returns a value to the caller
        return new Vec(Math.abs(x), Math.abs(y), Math.abs(z));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec min(Point point) {
        // Returns a value to the caller
        return new Vec(Math.min(x, point.x()), Math.min(y, point.y()), Math.min(z, point.z()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public Vec min(double x, double y, double z) {
        // Returns a value to the caller
        return new Vec(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec min(double value) {
        // Returns a value to the caller
        return new Vec(Math.min(x, value), Math.min(y, value), Math.min(z, value));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec max(Point point) {
        // Returns a value to the caller
        return new Vec(Math.max(x, point.x()), Math.max(y, point.y()), Math.max(z, point.z()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public Vec max(double x, double y, double z) {
        // Returns a value to the caller
        return new Vec(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec max(double value) {
        // Returns a value to the caller
        return new Vec(Math.max(x, value), Math.max(y, value), Math.max(z, value));
    // End of a block/expression
    }

    /**
     * @deprecated use {@link Point#asPos()} instead.
     */
    // Annotation for the following element
    @Deprecated(forRemoval = true)
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Pos asPosition() {
        // Returns a value to the caller
        return new Pos(x, y, z);
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
        return new Vec(x / length, y / length, z / length);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec cross(Point point) {
        // Returns a value to the caller
        return new Vec(y * point.z() - point.y() * z,
                // Code statement
                z * point.x() - point.z() * x,
                // Calls a method
                x * point.y() - point.x() * y);
    // End of a block/expression
    }

    /**
     * Rotates the vector around the x-axis.
     * <p>
     * This piece of math is based on the standard rotation matrix for vectors
     * in three-dimensional space. This matrix can be found here:
     * <a href="https://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">Rotation
     * Matrix</a>.
     *
     * @param angle the angle to rotate the vector about. This angle is passed
     *              in radians
     * @return a new, rotated vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec rotateAroundX(double angle) {
        // Calls a method
        double angleCos = Math.cos(angle);
        // Calls a method
        double angleSin = Math.sin(angle);

        // Assigns a value
        double newY = angleCos * y - angleSin * z;
        // Assigns a value
        double newZ = angleSin * y + angleCos * z;
        // Returns a value to the caller
        return new Vec(x, newY, newZ);
    // End of a block/expression
    }

    /**
     * Rotates the vector around the y-axis.
     * <p>
     * This piece of math is based on the standard rotation matrix for vectors
     * in three-dimensional space. This matrix can be found here:
     * <a href="https://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">Rotation
     * Matrix</a>.
     *
     * @param angle the angle to rotate the vector about. This angle is passed
     *              in radians
     * @return a new, rotated vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec rotateAroundY(double angle) {
        // Calls a method
        final double angleCos = Math.cos(angle);
        // Calls a method
        final double angleSin = Math.sin(angle);

        // Assigns a value
        final double newX = angleCos * x + angleSin * z;
        // Assigns a value
        final double newZ = -angleSin * x + angleCos * z;
        // Returns a value to the caller
        return new Vec(newX, y, newZ);
    // End of a block/expression
    }

    /**
     * Rotates the vector around the z axis
     * <p>
     * This piece of math is based on the standard rotation matrix for vectors
     * in three-dimensional space. This matrix can be found here:
     * <a href="https://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations">Rotation
     * Matrix</a>.
     *
     * @param angle the angle to rotate the vector about. This angle is passed
     *              in radians
     * @return a new, rotated vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec rotateAroundZ(double angle) {
        // Calls a method
        final double angleCos = Math.cos(angle);
        // Calls a method
        final double angleSin = Math.sin(angle);

        // Assigns a value
        final double newX = angleCos * x - angleSin * y;
        // Assigns a value
        final double newY = angleSin * x + angleCos * y;
        // Returns a value to the caller
        return new Vec(newX, newY, z);
    // End of a block/expression
    }

    /**
     * Rotates the vector around the x, y, and z axes.
     *
     * @param angleX the angle to rotate around the x-axis in radians
     * @param angleY the angle to rotate around the y-axis in radians
     * @param angleZ the angle to rotate around the z-axis in radians
     * @return a new, rotated vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public Vec rotate(double angleX, double angleY, double angleZ) {
        // Returns a value to the caller
        return rotateAroundX(angleX).rotateAroundY(angleY).rotateAroundZ(angleZ);
    // End of a block/expression
    }

    /**
     * Rotates the vector from a given yaw and pitch.
     *
     * @param yawDegrees   the yaw in degrees
     * @param pitchDegrees the pitch in degrees
     * @return a new, rotated vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_, _ -> new")
    // Start of a method/block
    public Vec rotateFromView(float yawDegrees, float pitchDegrees) {
        // Calls a method
        final double yaw = Math.toRadians(-1 * (yawDegrees + 90));
        // Calls a method
        final double pitch = Math.toRadians(-pitchDegrees);

        // Calls a method
        final double cosYaw = Math.cos(yaw);
        // Calls a method
        final double cosPitch = Math.cos(pitch);
        // Calls a method
        double sinYaw = Math.sin(yaw);
        // Calls a method
        double sinPitch = Math.sin(pitch);

        // Code statement
        double initialX, initialY, initialZ;
        // Code statement
        double x, y, z;

        // Z_Axis rotation (Pitch)
        // Calls a method
        initialX = x();
        // Calls a method
        initialY = y();
        // Assigns a value
        x = initialX * cosPitch - initialY * sinPitch;
        // Assigns a value
        y = initialX * sinPitch + initialY * cosPitch;

        // Y_Axis rotation (Yaw)
        // Calls a method
        initialZ = z();
        // Assigns a value
        initialX = x;
        // Assigns a value
        z = initialZ * cosYaw - initialX * sinYaw;
        // Assigns a value
        x = initialZ * sinYaw + initialX * cosYaw;

        // Returns a value to the caller
        return new Vec(x, y, z);
    // End of a block/expression
    }

    /**
     * Rotates the vector from a position's view (yaw/pitch).
     *
     * @param pos the position containing the view
     * @return a new, rotated vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Vec rotateFromView(Pos pos) {
        // Returns a value to the caller
        return rotateFromView(pos.yaw(), pos.pitch());
    // End of a block/expression
    }

    /**
     * Rotates the vector around a given arbitrary axis in 3 dimensional space.
     *
     * <p>
     * Rotation will follow the general Right-Hand-Rule, which means rotation
     * will be counterclockwise when the axis is pointing towards the observer.
     * <p>
     * This method will always make sure the provided axis is a unit vector, to
     * not modify the length of the vector when rotating. If you are experienced
     * with the scaling of a non-unit axis vector, you can use
     * {@link Vec#rotateAroundNonUnitAxis(Vec, double)}.
     *
     * @param axis  the axis to rotate the vector around. If the passed vector is
     *              not of length 1, it gets copied and normalized before using it for the
     *              rotation. Please use {@link Vec#normalize()} on the instance before
     *              passing it to this method
     * @param angle the angle to rotate the vector around the axis
     * @return a new vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_, _ -> new")
    // Start of a method/block
    public Vec rotateAroundAxis(Vec axis, double angle) throws IllegalArgumentException {
        // Returns a value to the caller
        return rotateAroundNonUnitAxis(axis.isNormalized() ? axis : axis.normalize(), angle);
    // End of a block/expression
    }

    /**
     * Rotates the vector around a given arbitrary axis in 3 dimensional space.
     *
     * <p>
     * Rotation will follow the general Right-Hand-Rule, which means rotation
     * will be counterclockwise when the axis is pointing towards the observer.
     * <p>
     * Note that the vector length will change accordingly to the axis vector
     * length. If the provided axis is not a unit vector, the rotated vector
     * will not have its previous length. The scaled length of the resulting
     * vector will be related to the axis vector. If you are not perfectly sure
     * about the scaling of the vector, use
     * {@link Vec#rotateAroundAxis(Vec, double)}
     *
     * @param axis  the axis to rotate the vector around.
     * @param angle the angle to rotate the vector around the axis
     * @return a new vector
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_, _ -> new")
    // Start of a method/block
    public Vec rotateAroundNonUnitAxis(Vec axis, double angle) throws IllegalArgumentException {
        // Calls a method
        final double x = x(), y = y(), z = z();
        // Calls a method
        final double x2 = axis.x(), y2 = axis.y(), z2 = axis.z();
        // Calls a method
        double cosTheta = Math.cos(angle);
        // Calls a method
        double sinTheta = Math.sin(angle);
        // Calls a method
        double dotProduct = this.dot(axis);

        // Assigns a value
        final double newX = x2 * dotProduct * (1d - cosTheta)
                // Code statement
                + x * cosTheta
                // Calls a method
                + (-z2 * y + y2 * z) * sinTheta;
        // Assigns a value
        final double newY = y2 * dotProduct * (1d - cosTheta)
                // Code statement
                + y * cosTheta
                // Calls a method
                + (z2 * x - x2 * z) * sinTheta;
        // Assigns a value
        final double newZ = z2 * dotProduct * (1d - cosTheta)
                // Code statement
                + z * cosTheta
                // Calls a method
                + (-y2 * x + x2 * y) * sinTheta;

        // Returns a value to the caller
        return new Vec(newX, newY, newZ);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_, _ -> new")
    // Start of a method/block
    public Vec lerp(Point point, double alpha) {
        // Returns a value to the caller
        return new Vec(x + (alpha * (point.x() - x)),
                // Code statement
                y + (alpha * (point.y() - y)),
                // Calls a method
                z + (alpha * (point.z() - z)));
    // End of a block/expression
    }

    /**
     * Calculates an interpolation between this vector and a target vector.
     *
     * @param target        the target vector
     * @param alpha         the alpha value, must be between 0.0 and 1.0
     * @param interpolation the interpolation function to use
     * @return the interpolated vector
     * @deprecated use {@link Point#lerp(Point, double)} with a {@link net.minestom.server.utils.EaseFunction} instead
     */
    // Annotation for the following element
    @Deprecated(forRemoval = true)
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Vec interpolate(Vec target, double alpha, Interpolation interpolation) {
        // Returns a value to the caller
        return lerp(target, interpolation.apply(alpha));
    // End of a block/expression
    }

    /**
     * Does nothing as this is already a {@link Vec}.
     * <p>
     * Marked as deprecated to warn against redundant usage.
     *
     * @return this vector
     */
    // Annotation for the following element
    @Deprecated
    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "-> this")
    // Start of a method/block
    public Vec asVec() {
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * A functional interface representing an operation on the components of a {@link Vec}.
     */
    // Annotation for the following element
    @FunctionalInterface
    // Type declaration (class/interface/enum/record)
    public interface Operator {
        // Calls a method
        Operator EPSILON = operator(v -> Math.abs(v) < Vec.EPSILON ? 0 : v);
        // Calls a method
        Operator FLOOR = operator(Math::floor);
        // Calls a method
        Operator SIGNUM = operator(Math::signum);
        // Calls a method
        Operator CEIL = operator(Math::ceil);
        // Calls a method
        Operator ROUND = operator(Math::round);

        /**
         * Shortcut utility to apply the operator on all 3 components.
         *
         * @param operator the unary operator to use
         * @return the vector operator
         */
        // Start of a method/block
        static Operator operator(DoubleUnaryOperator operator) {
            // Returns a value to the caller
            return (x, y, z) -> new Vec(operator.applyAsDouble(x), operator.applyAsDouble(y), operator.applyAsDouble(z));
        // End of a block/expression
        }

        /**
         * Applies the operator to the given x, y, z components.
         *
         * @param x the x component
         * @param y the y component
         * @param z the z component
         * @return the resulting vector
         */
        // Calls a method
        Vec apply(double x, double y, double z);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.utils.EaseFunction} instead with {@link #lerp(Point, double)}
     */
    // Annotation for the following element
    @Deprecated(forRemoval = true)
    // Annotation for the following element
    @FunctionalInterface
    // Type declaration (class/interface/enum/record)
    public interface Interpolation {
        // Assigns a value
        Interpolation LINEAR = a -> a;
        // Calls a method
        Interpolation SMOOTH = a -> a * a * (3 - 2 * a);

        // Calls a method
        double apply(double alpha);
    // End of a block/expression
    }
// End of a block/expression
}
