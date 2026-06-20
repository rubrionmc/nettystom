// Package declaration for this file
package net.minestom.server.coordinate;

// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import net.minestom.server.utils.Direction;
// Import of a required class
import net.minestom.server.utils.position.PositionUtils;
// Import of a required class
import org.jetbrains.annotations.Contract;

// Import of a required class
import java.util.function.DoubleUnaryOperator;

/**
 * Represents a 3D position with double-precision coordinates and viewing direction.
 * <p>
 * Combines {@link Vec} with yaw and pitch angles, making it suitable for entities
 * and cameras that need both location and orientation.
 * <p>
 * View angles are automatically normalized.
 *
 * @param x     the X coordinate
 * @param y     the Y coordinate
 * @param z     the Z coordinate
 * @param yaw   the yaw (rotation around vertical axis) in degrees (-180, 180]
 * @param pitch the pitch (rotation around lateral axis) in degrees [-90, 90]
 */
// Type declaration (class/interface/enum/record)
public record Pos(double x, double y, double z, float yaw, float pitch) implements Point {
    // Calls a method
    public static final Pos ZERO = new Pos(0, 0, 0);

    /**
     * The epsilon used to compare two views (yaw/pitch) if applicable.
     */
    // Assigns a value
    public static final float VIEW_EPSILON = 1e-4f;

    // Start of a method/block
    public Pos {
        // Calls a method
        yaw = fixYaw(yaw);
        // Calls a method
        pitch = fixPitch(pitch);
    // End of a block/expression
    }

    /**
     * Creates a position with the given coordinates (x/y/z) and default view (yaw/pitch = 0).
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     * @param z the Z coordinate
     */
    // Start of a method/block
    public Pos(double x, double y, double z) {
        // Calls a method
        this(x, y, z, 0, 0);
    // End of a block/expression
    }

    /**
     * Creates a position from a point with the given view (yaw/pitch).
     *
     * @param point the point containing the coordinates (x/y/z)
     * @param yaw   the yaw
     * @param pitch the pitch
     * @deprecated Use {@link Point#asPos()} instead with {@link #withView(float, float)}
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public Pos(Point point, float yaw, float pitch) {
        // Calls a method
        this(point.x(), point.y(), point.z(), yaw, pitch);
    // End of a block/expression
    }

    /**
     * Creates a position from a point with the default view (yaw/pitch = 0).
     *
     * @param point the point containing the coordinates (x/y/z)
     * @deprecated Use {@link Point#asPos()} instead
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public Pos(Point point) {
        // Calls a method
        this(point, 0, 0);
    // End of a block/expression
    }

    /**
     * Converts a {@link Point} into a {@link Pos}.
     * Will cast if possible, or instantiate a new object.
     *
     * @param point the point to convert
     * @return the converted position
     * @deprecated use {@link Point#asPos()} instead
     */
    // Annotation for the following element
    @Deprecated(forRemoval = true)
    // Start of a method/block
    public static Pos fromPoint(Point point) {
        // Branch: checks a condition
        if (point instanceof Pos pos) return pos;
        // Returns a value to the caller
        return new Pos(point.x(), point.y(), point.z());
    // End of a block/expression
    }

    /**
     * Fixes a pitch value that is not between -90.0f and 90.0f
     * So for example, -135.0f becomes -90.0f and 225.0f becomes 90.0f
     *
     * @param pitch The possible "wrong" pitch
     * @return a fixed pitch in the range [-90.0f, 90.0f]
     */
    // Start of a method/block
    public static float fixPitch(float pitch) {
        // Returns a value to the caller
        return Math.clamp(pitch, -90.0f, 90.0f);
    // End of a block/expression
    }

    /**
     * Fixes a yaw value that is not between -180.0f (exclusive) and 180.0f (inclusive).
     * Wraps the yaw to the nearest equivalent angle in this range.
     * For example, -1355.0f becomes 85.0f and 225.0f becomes -135.0f.
     *
     * @param yaw The possible "wrong" yaw
     * @return a fixed yaw in the range (-180.0f, 180.0f]
     */
    // Start of a method/block
    public static float fixYaw(float yaw) {
        // Returns a value to the caller
        return yaw - 360.0f * (float) Math.ceil((yaw - 180.0f) / 360.0f);
    // End of a block/expression
    }

    /**
     * Changes the 3 coordinates of this position (x/y/z).
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     * @param z the Z coordinate
     * @return a new position
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Pos withCoord(double x, double y, double z) {
        // Returns a value to the caller
        return new Pos(x, y, z, yaw, pitch);
    // End of a block/expression
    }

    /**
     * Changes the coordinates to match the provided point.
     *
     * @param point the point to use for coordinates (x/y/z)
     * @return a new position
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Pos withCoord(Point point) {
        // Returns a value to the caller
        return withCoord(point.x(), point.y(), point.z());
    // End of a block/expression
    }

    /**
     * Changes the view of this position (yaw/pitch).
     *
     * @param yaw   the yaw
     * @param pitch the pitch
     * @return a new position
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Pos withView(float yaw, float pitch) {
        // Returns a value to the caller
        return new Pos(x, y, z, yaw, pitch);
    // End of a block/expression
    }

    /**
     * Changes the view to match the provided position.
     *
     * @param pos the position to use for the view (yaw/pitch)
     * @return a new position
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Pos withView(Pos pos) {
        // Returns a value to the caller
        return withView(pos.yaw(), pos.pitch());
    // End of a block/expression
    }

    /**
     * Sets the yaw and pitch to point
     * in the direction of the point.
     *
     * @param point the point to look at
     * @return a new position
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Pos withDirection(Point point) {
        /*
         * Sin = Opp / Hyp
         * Cos = Adj / Hyp
         * Tan = Opp / Adj
         *
         * x = -Opp
         * z = Adj
         */
        // Calls a method
        final double x = point.x();
        // Calls a method
        final double z = point.z();
        // Branch: checks a condition
        if (x == 0 && z == 0) {
            // Returns a value to the caller
            return withPitch(point.y() > 0 ? -90f : 90f);
        // End of a block/expression
        }
        // Calls a method
        final double theta = Math.atan2(-x, z);
        // Calls a method
        final double xz = Math.sqrt((x * x) + (z * z));
        // Assigns a value
        final double _2PI = 2 * Math.PI;
        // Returns a value to the caller
        return withView((float) Math.toDegrees((theta + _2PI) % _2PI),
                // Calls a method
                (float) Math.toDegrees(Math.atan(-point.y() / xz)));
    // End of a block/expression
    }

    /**
     * Changes the yaw of this position.
     *
     * @param yaw the new yaw
     * @return a new position
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Pos withYaw(float yaw) {
        // Returns a value to the caller
        return new Pos(x, y, z, yaw, pitch);
    // End of a block/expression
    }

    /**
     * Applies an operator to the yaw of this position.
     *
     * @param operator the operator to apply to the yaw
     * @return a new position
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Pos withYaw(DoubleUnaryOperator operator) {
        // Returns a value to the caller
        return withYaw((float) operator.applyAsDouble(yaw));
    // End of a block/expression
    }

    /**
     * Changes the pitch of this position.
     *
     * @param pitch the new pitch
     * @return a new position
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Pos withPitch(float pitch) {
        // Returns a value to the caller
        return new Pos(x, y, z, yaw, pitch);
    // End of a block/expression
    }

    /**
     * Changes the view to look at a specific point.
     *
     * @param point the point to look at
     * @return a new position
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Pos withLookAt(Point point) {
        // Branch: checks a condition
        if (samePoint(point)) return this;
        // Calls a method
        final Vec delta = point.sub(this).asVec().normalize();
        // Returns a value to the caller
        return withView(PositionUtils.getLookYaw(delta.x(), delta.z()),
                // Calls a method
                PositionUtils.getLookPitch(delta.x(), delta.y(), delta.z()));
    // End of a block/expression
    }

    /**
     * Applies an operator to the pitch of this position.
     *
     * @param operator the operator to apply to the pitch
     * @return a new position
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Pos withPitch(DoubleUnaryOperator operator) {
        // Returns a value to the caller
        return withPitch((float) operator.applyAsDouble(pitch));
    // End of a block/expression
    }

    /**
     * Checks if two positions have a similar view (yaw/pitch).
     *
     * @param position the position to compare
     * @return true if the two positions have the same view
     */
    // Start of a method/block
    public boolean sameView(Pos position) {
        // Returns a value to the caller
        return sameView(position.yaw(), position.pitch());
    // End of a block/expression
    }

    /**
     * Checks if the yaw and pitch are the same as the given ones.
     *
     * @param yaw   the yaw
     * @param pitch the pitch
     * @return true if the yaw and pitch are the same
     */
    // Start of a method/block
    public boolean sameView(float yaw, float pitch) {
        // Returns a value to the caller
        return Float.compare(this.yaw, yaw) == 0 &&
                // Calls a method
                Float.compare(this.pitch, pitch) == 0;
    // End of a block/expression
    }

    /**
     * Checks if the yaw and pitch are approximately the same as the given ones.
     *
     * @param yaw     the yaw
     * @param pitch   the pitch
     * @param epsilon the maximum difference to consider the values equal
     * @return true if the yaw and pitch are approximately the same
     */
    // Start of a method/block
    public boolean similarView(float yaw, float pitch, float epsilon) {
        // Returns a value to the caller
        return Math.abs(this.yaw - yaw) < epsilon &&
                // Calls a method
                Math.abs(this.pitch - pitch) < epsilon;
    // End of a block/expression
    }

    /**
     * Checks if two positions have approximately similar views (yaw/pitch).
     *
     * @param position the position to compare
     * @param epsilon  the maximum difference to consider the values equal
     * @return true if the two positions have a similar view
     */
    // Start of a method/block
    public boolean similarView(Pos position, float epsilon) {
        // Returns a value to the caller
        return similarView(position.yaw(), position.pitch(), epsilon);
    // End of a block/expression
    }

    /**
     * Checks if two positions have approximately similar views (yaw/pitch).
     * <p>
     * Uses {@link #VIEW_EPSILON} as epsilon.
     *
     * @param position the position to compare
     * @return true if the two positions have a similar view
     */
    // Start of a method/block
    public boolean similarView(Pos position) {
        // Returns a value to the caller
        return similarView(position.yaw(), position.pitch(), VIEW_EPSILON);
    // End of a block/expression
    }

    /**
     * Checks if the yaw and pitch are approximately the same as the given ones.
     * <p>
     * Uses {@link #VIEW_EPSILON} as epsilon.
     *
     * @param yaw   the yaw
     * @param pitch the pitch
     * @return true if the yaw and pitch are approximately the same
     */
    // Start of a method/block
    public boolean similarView(float yaw, float pitch) {
        // Returns a value to the caller
        return similarView(yaw, pitch, VIEW_EPSILON);
    // End of a block/expression
    }

    /**
     * Gets a unit-vector pointing in the direction that this Location is
     * facing.
     *
     * @return a vector pointing the direction of this location's {@link
     * #pitch() pitch} and {@link #yaw() yaw}
     */
    // Start of a method/block
    public Vec direction() {
        // Assigns a value
        final float rotX = yaw;
        // Assigns a value
        final float rotY = pitch;
        // Calls a method
        final double xz = Math.cos(Math.toRadians(rotY));
        // Returns a value to the caller
        return new Vec(-xz * Math.sin(Math.toRadians(rotX)),
                // Code statement
                -Math.sin(Math.toRadians(rotY)),
                // Calls a method
                xz * Math.cos(Math.toRadians(rotX)));
    // End of a block/expression
    }

    /**
     * Gets the closest direction {@link #yaw()} and {@link #pitch()} are facing to.
     *
     * @return the direction this position is facing
     */
    // Start of a method/block
    public Direction facing() {
        // Branch: checks a condition
        if (pitch < -45) return Direction.UP;
        // Branch: checks a condition
        if (pitch > 45) return Direction.DOWN;
        // Branch: checks a condition
        if (yaw > 135 || yaw <= -135) return Direction.NORTH;
        // Branch: checks a condition
        if (-135 < yaw && yaw <= -45) return Direction.EAST;
        // Branch: checks a condition
        if (-45 < yaw && yaw <= 45) return Direction.SOUTH;
        // Branch: checks a condition
        if (45 < yaw) return Direction.WEST;
        // Throws an exception
        throw new IllegalStateException("Illegal yaw (%s) or pitch (%s) value.".formatted(this.yaw, pitch));
    // End of a block/expression
    }

    /**
     * Returns a new position based on this position fields.
     *
     * @param operator the operator deconstructing this object and providing a new position
     * @return the new position
     */
    // Start of a method/block
    public Pos apply(Operator operator) {
        // Returns a value to the caller
        return operator.apply(x, y, z, yaw, pitch);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract("_ -> new")
    // Start of a method/block
    public Pos withX(DoubleUnaryOperator operator) {
        // Returns a value to the caller
        return new Pos(operator.applyAsDouble(x), y, z, yaw, pitch);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Pos withX(double x) {
        // Returns a value to the caller
        return new Pos(x, y, z, yaw, pitch);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract("_ -> new")
    // Start of a method/block
    public Pos withY(DoubleUnaryOperator operator) {
        // Returns a value to the caller
        return new Pos(x, operator.applyAsDouble(y), z, yaw, pitch);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Pos withY(double y) {
        // Returns a value to the caller
        return new Pos(x, y, z, yaw, pitch);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract("_ -> new")
    // Start of a method/block
    public Pos withZ(DoubleUnaryOperator operator) {
        // Returns a value to the caller
        return new Pos(x, y, operator.applyAsDouble(z), yaw, pitch);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Pos withZ(double z) {
        // Returns a value to the caller
        return new Pos(x, y, z, yaw, pitch);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public Pos add(double x, double y, double z) {
        // Returns a value to the caller
        return new Pos(this.x + x, this.y + y, this.z + z, yaw, pitch);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Pos add(Point point) {
        // Returns a value to the caller
        return add(point.x(), point.y(), point.z());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Pos add(double value) {
        // Returns a value to the caller
        return add(value, value, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public Pos sub(double x, double y, double z) {
        // Returns a value to the caller
        return new Pos(this.x - x, this.y - y, this.z - z, yaw, pitch);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Pos sub(Point point) {
        // Returns a value to the caller
        return sub(point.x(), point.y(), point.z());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Pos sub(double value) {
        // Returns a value to the caller
        return sub(value, value, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public Pos mul(double x, double y, double z) {
        // Returns a value to the caller
        return new Pos(this.x * x, this.y * y, this.z * z, yaw, pitch);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Pos mul(Point point) {
        // Returns a value to the caller
        return mul(point.x(), point.y(), point.z());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Pos mul(double value) {
        // Returns a value to the caller
        return mul(value, value, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public Pos div(double x, double y, double z) {
        // Returns a value to the caller
        return new Pos(this.x / x, this.y / y, this.z / z, yaw, pitch);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Pos div(Point point) {
        // Returns a value to the caller
        return div(point.x(), point.y(), point.z());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Pos div(double value) {
        // Returns a value to the caller
        return div(value, value, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Pos relative(BlockFace face) {
        // Returns a value to the caller
        return (Pos) Point.super.relative(face);
    // End of a block/expression
    }

    /**
     * Does nothing as this is already a {@link Pos}.
     * <p>
     * Marked as deprecated to warn against redundant usage.
     *
     * @return this position
     */
    // Annotation for the following element
    @Deprecated
    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "-> this")
    // Start of a method/block
    public Pos asPos() {
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Equivalent to {@link #withView(float, float)} as this is already a {@link Pos}.
     * <p>
     * Marked as deprecated to warn against redundant usage.
     *
     * @param yaw   the yaw
     * @param pitch the pitch
     * @return a new position with the provided view
     */
    // Annotation for the following element
    @Deprecated
    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_, _ -> new")
    // Start of a method/block
    public Pos asPos(float yaw, float pitch) {
        // Returns a value to the caller
        return withView(yaw, pitch);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "-> new")
    // Start of a method/block
    public Pos normalize() {
        // Calls a method
        final double length = length();
        // Returns a value to the caller
        return new Pos(x / length, y / length, z / length, yaw, pitch);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Pos cross(Point point) {
        // Returns a value to the caller
        return new Pos(y * point.z() - point.y() * z,
                // Code statement
                z * point.x() - point.z() * x,
                // Code statement
                x * point.y() - point.x() * y,
                // Code statement
                yaw, pitch);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_, _ -> new")
    // Start of a method/block
    public Pos lerp(Point point, double alpha) {
        // Returns a value to the caller
        return new Pos(x + (alpha * (point.x() - x)),
                // Code statement
                y + (alpha * (point.y() - y)),
                // Code statement
                z + (alpha * (point.z() - z)),
                // Code statement
                yaw, pitch);
    // End of a block/expression
    }

    /**
     * Calculates a linear interpolation between this position's view and another position's view (yaw/pitch).
     * The coordinates (x/y/z) remain unchanged.
     *
     * @param pos   the other position
     * @param alpha the alpha value, must be between 0.0 and 1.0
     * @return a new position with interpolated view
     */
    // Annotation for the following element
    @Contract(pure = true, value = "_, _ -> new")
    // Start of a method/block
    public Pos lerpView(Pos pos, float alpha) {
        // Returns a value to the caller
        return new Pos(x, y, z,
                // Code statement
                yaw + (alpha * (pos.yaw() - yaw)),
                // Calls a method
                pitch + (alpha * (pos.pitch() - pitch)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "-> new")
    // Start of a method/block
    public Pos neg() {
        // Returns a value to the caller
        return new Pos(-x, -y, -z, yaw, pitch);
    // End of a block/expression
    }

    /**
     * Negates the view (yaw/pitch) of this position.
     *
     * @return a new position
     */
    // Annotation for the following element
    @Contract(pure = true, value = "-> new")
    // Start of a method/block
    public Pos negView() {
        // Returns a value to the caller
        return new Pos(x, y, z, -yaw, -pitch);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "-> new")
    // Start of a method/block
    public Pos abs() {
        // Returns a value to the caller
        return new Pos(Math.abs(x), Math.abs(y), Math.abs(z), yaw, pitch);
    // End of a block/expression
    }

    /**
     * Returns the absolute value of the view (yaw/pitch).
     *
     * @return a new position
     */
    // Annotation for the following element
    @Contract(pure = true, value = "-> new")
    // Start of a method/block
    public Pos absView() {
        // Returns a value to the caller
        return new Pos(x, y, z, Math.abs(yaw), Math.abs(pitch));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Pos min(Point point) {
        // Returns a value to the caller
        return new Pos(Math.min(x, point.x()), Math.min(y, point.y()), Math.min(z, point.z()), yaw, pitch);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public Pos min(double x, double y, double z) {
        // Returns a value to the caller
        return new Pos(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z), yaw, pitch);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Pos min(double value) {
        // Returns a value to the caller
        return new Pos(Math.min(x, value), Math.min(y, value), Math.min(z, value), yaw, pitch);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Pos max(Point point) {
        // Returns a value to the caller
        return new Pos(Math.max(x, point.x()), Math.max(y, point.y()), Math.max(z, point.z()), yaw, pitch);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_, _, _ -> new")
    // Start of a method/block
    public Pos max(double x, double y, double z) {
        // Returns a value to the caller
        return new Pos(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z), yaw, pitch);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Contract(pure = true, value = "_ -> new")
    // Start of a method/block
    public Pos max(double value) {
        // Returns a value to the caller
        return new Pos(Math.max(x, value), Math.max(y, value), Math.max(z, value), yaw, pitch);
    // End of a block/expression
    }

    /**
     * A functional interface representing an operation on the components of a {@link Pos}.
     */
    // Annotation for the following element
    @FunctionalInterface
    // Type declaration (class/interface/enum/record)
    public interface Operator {
        /**
         * Uses a {@link Vec.Operator} for the position components (x/y/z) for a {@link Operator}.
         *
         * @param operator the vector operator
         * @return the position operator
         */
        // Start of a method/block
        static Operator operator(Vec.Operator operator) {
            // Returns a value to the caller
            return (x, y, z, yaw, pitch) -> operator.apply(x, y, z).asPos().withView(yaw, pitch);
        // End of a block/expression
        }

        /**
         * Applies this operator to the given position components.
         *
         * @param x     the x component
         * @param y     the y component
         * @param z     the z component
         * @param yaw   the yaw component
         * @param pitch the pitch component
         * @return the resulting position
         */
        // Calls a method
        Pos apply(double x, double y, double z, float yaw, float pitch);
    // End of a block/expression
    }
// End of a block/expression
}
