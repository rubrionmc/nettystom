// Déclaration du paquet de ce fichier
package net.minestom.server.coordinate;

// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.utils.Direction;
// Import d'une classe nécessaire
import net.minestom.server.utils.position.PositionUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;

// Import d'une classe nécessaire
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
// Déclaration de type (classe/interface/enum/record)
public record Pos(double x, double y, double z, float yaw, float pitch) implements Point {
    // Appelle une méthode
    public static final Pos ZERO = new Pos(0, 0, 0);

    /**
     * The epsilon used to compare two views (yaw/pitch) if applicable.
     */
    // Affecte une valeur
    public static final float VIEW_EPSILON = 1e-4f;

    // Début d'une méthode/d'un bloc
    public Pos {
        // Appelle une méthode
        yaw = fixYaw(yaw);
        // Appelle une méthode
        pitch = fixPitch(pitch);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a position with the given coordinates (x/y/z) and default view (yaw/pitch = 0).
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     * @param z the Z coordinate
     */
    // Début d'une méthode/d'un bloc
    public Pos(double x, double y, double z) {
        // Appelle une méthode
        this(x, y, z, 0, 0);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a position from a point with the given view (yaw/pitch).
     *
     * @param point the point containing the coordinates (x/y/z)
     * @param yaw   the yaw
     * @param pitch the pitch
     * @deprecated Use {@link Point#asPos()} instead with {@link #withView(float, float)}
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public Pos(Point point, float yaw, float pitch) {
        // Appelle une méthode
        this(point.x(), point.y(), point.z(), yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a position from a point with the default view (yaw/pitch = 0).
     *
     * @param point the point containing the coordinates (x/y/z)
     * @deprecated Use {@link Point#asPos()} instead
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public Pos(Point point) {
        // Appelle une méthode
        this(point, 0, 0);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Converts a {@link Point} into a {@link Pos}.
     * Will cast if possible, or instantiate a new object.
     *
     * @param point the point to convert
     * @return the converted position
     * @deprecated use {@link Point#asPos()} instead
     */
    // Annotation pour l'élément suivant
    @Deprecated(forRemoval = true)
    // Début d'une méthode/d'un bloc
    public static Pos fromPoint(Point point) {
        // Embranchement : vérifie une condition
        if (point instanceof Pos pos) return pos;
        // Renvoie une valeur à l'appelant
        return new Pos(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Fixes a pitch value that is not between -90.0f and 90.0f
     * So for example, -135.0f becomes -90.0f and 225.0f becomes 90.0f
     *
     * @param pitch The possible "wrong" pitch
     * @return a fixed pitch in the range [-90.0f, 90.0f]
     */
    // Début d'une méthode/d'un bloc
    public static float fixPitch(float pitch) {
        // Renvoie une valeur à l'appelant
        return Math.clamp(pitch, -90.0f, 90.0f);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Fixes a yaw value that is not between -180.0f (exclusive) and 180.0f (inclusive).
     * Wraps the yaw to the nearest equivalent angle in this range.
     * For example, -1355.0f becomes 85.0f and 225.0f becomes -135.0f.
     *
     * @param yaw The possible "wrong" yaw
     * @return a fixed yaw in the range (-180.0f, 180.0f]
     */
    // Début d'une méthode/d'un bloc
    public static float fixYaw(float yaw) {
        // Renvoie une valeur à l'appelant
        return yaw - 360.0f * (float) Math.ceil((yaw - 180.0f) / 360.0f);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the 3 coordinates of this position (x/y/z).
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     * @param z the Z coordinate
     * @return a new position
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pos withCoord(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Pos(x, y, z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the coordinates to match the provided point.
     *
     * @param point the point to use for coordinates (x/y/z)
     * @return a new position
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pos withCoord(Point point) {
        // Renvoie une valeur à l'appelant
        return withCoord(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the view of this position (yaw/pitch).
     *
     * @param yaw   the yaw
     * @param pitch the pitch
     * @return a new position
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pos withView(float yaw, float pitch) {
        // Renvoie une valeur à l'appelant
        return new Pos(x, y, z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the view to match the provided position.
     *
     * @param pos the position to use for the view (yaw/pitch)
     * @return a new position
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pos withView(Pos pos) {
        // Renvoie une valeur à l'appelant
        return withView(pos.yaw(), pos.pitch());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the yaw and pitch to point
     * in the direction of the point.
     *
     * @param point the point to look at
     * @return a new position
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Pos withDirection(Point point) {
        /*
         * Sin = Opp / Hyp
         * Cos = Adj / Hyp
         * Tan = Opp / Adj
         *
         * x = -Opp
         * z = Adj
         */
        // Appelle une méthode
        final double x = point.x();
        // Appelle une méthode
        final double z = point.z();
        // Embranchement : vérifie une condition
        if (x == 0 && z == 0) {
            // Renvoie une valeur à l'appelant
            return withPitch(point.y() > 0 ? -90f : 90f);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        final double theta = Math.atan2(-x, z);
        // Appelle une méthode
        final double xz = Math.sqrt((x * x) + (z * z));
        // Affecte une valeur
        final double _2PI = 2 * Math.PI;
        // Renvoie une valeur à l'appelant
        return withView((float) Math.toDegrees((theta + _2PI) % _2PI),
                // Appelle une méthode
                (float) Math.toDegrees(Math.atan(-point.y() / xz)));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the yaw of this position.
     *
     * @param yaw the new yaw
     * @return a new position
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pos withYaw(float yaw) {
        // Renvoie une valeur à l'appelant
        return new Pos(x, y, z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Applies an operator to the yaw of this position.
     *
     * @param operator the operator to apply to the yaw
     * @return a new position
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pos withYaw(DoubleUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return withYaw((float) operator.applyAsDouble(yaw));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the pitch of this position.
     *
     * @param pitch the new pitch
     * @return a new position
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pos withPitch(float pitch) {
        // Renvoie une valeur à l'appelant
        return new Pos(x, y, z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the view to look at a specific point.
     *
     * @param point the point to look at
     * @return a new position
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pos withLookAt(Point point) {
        // Embranchement : vérifie une condition
        if (samePoint(point)) return this;
        // Appelle une méthode
        final Vec delta = point.sub(this).asVec().normalize();
        // Renvoie une valeur à l'appelant
        return withView(PositionUtils.getLookYaw(delta.x(), delta.z()),
                // Appelle une méthode
                PositionUtils.getLookPitch(delta.x(), delta.y(), delta.z()));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Applies an operator to the pitch of this position.
     *
     * @param operator the operator to apply to the pitch
     * @return a new position
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pos withPitch(DoubleUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return withPitch((float) operator.applyAsDouble(pitch));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if two positions have a similar view (yaw/pitch).
     *
     * @param position the position to compare
     * @return true if the two positions have the same view
     */
    // Début d'une méthode/d'un bloc
    public boolean sameView(Pos position) {
        // Renvoie une valeur à l'appelant
        return sameView(position.yaw(), position.pitch());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if the yaw and pitch are the same as the given ones.
     *
     * @param yaw   the yaw
     * @param pitch the pitch
     * @return true if the yaw and pitch are the same
     */
    // Début d'une méthode/d'un bloc
    public boolean sameView(float yaw, float pitch) {
        // Renvoie une valeur à l'appelant
        return Float.compare(this.yaw, yaw) == 0 &&
                // Appelle une méthode
                Float.compare(this.pitch, pitch) == 0;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if the yaw and pitch are approximately the same as the given ones.
     *
     * @param yaw     the yaw
     * @param pitch   the pitch
     * @param epsilon the maximum difference to consider the values equal
     * @return true if the yaw and pitch are approximately the same
     */
    // Début d'une méthode/d'un bloc
    public boolean similarView(float yaw, float pitch, float epsilon) {
        // Renvoie une valeur à l'appelant
        return Math.abs(this.yaw - yaw) < epsilon &&
                // Appelle une méthode
                Math.abs(this.pitch - pitch) < epsilon;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if two positions have approximately similar views (yaw/pitch).
     *
     * @param position the position to compare
     * @param epsilon  the maximum difference to consider the values equal
     * @return true if the two positions have a similar view
     */
    // Début d'une méthode/d'un bloc
    public boolean similarView(Pos position, float epsilon) {
        // Renvoie une valeur à l'appelant
        return similarView(position.yaw(), position.pitch(), epsilon);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if two positions have approximately similar views (yaw/pitch).
     * <p>
     * Uses {@link #VIEW_EPSILON} as epsilon.
     *
     * @param position the position to compare
     * @return true if the two positions have a similar view
     */
    // Début d'une méthode/d'un bloc
    public boolean similarView(Pos position) {
        // Renvoie une valeur à l'appelant
        return similarView(position.yaw(), position.pitch(), VIEW_EPSILON);
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public boolean similarView(float yaw, float pitch) {
        // Renvoie une valeur à l'appelant
        return similarView(yaw, pitch, VIEW_EPSILON);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets a unit-vector pointing in the direction that this Location is
     * facing.
     *
     * @return a vector pointing the direction of this location's {@link
     * #pitch() pitch} and {@link #yaw() yaw}
     */
    // Début d'une méthode/d'un bloc
    public Vec direction() {
        // Affecte une valeur
        final float rotX = yaw;
        // Affecte une valeur
        final float rotY = pitch;
        // Appelle une méthode
        final double xz = Math.cos(Math.toRadians(rotY));
        // Renvoie une valeur à l'appelant
        return new Vec(-xz * Math.sin(Math.toRadians(rotX)),
                // Instruction de code
                -Math.sin(Math.toRadians(rotY)),
                // Appelle une méthode
                xz * Math.cos(Math.toRadians(rotX)));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the closest direction {@link #yaw()} and {@link #pitch()} are facing to.
     *
     * @return the direction this position is facing
     */
    // Début d'une méthode/d'un bloc
    public Direction facing() {
        // Embranchement : vérifie une condition
        if (pitch < -45) return Direction.UP;
        // Embranchement : vérifie une condition
        if (pitch > 45) return Direction.DOWN;
        // Embranchement : vérifie une condition
        if (yaw > 135 || yaw <= -135) return Direction.NORTH;
        // Embranchement : vérifie une condition
        if (-135 < yaw && yaw <= -45) return Direction.EAST;
        // Embranchement : vérifie une condition
        if (-45 < yaw && yaw <= 45) return Direction.SOUTH;
        // Embranchement : vérifie une condition
        if (45 < yaw) return Direction.WEST;
        // Lève une exception
        throw new IllegalStateException("Illegal yaw (%s) or pitch (%s) value.".formatted(this.yaw, pitch));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns a new position based on this position fields.
     *
     * @param operator the operator deconstructing this object and providing a new position
     * @return the new position
     */
    // Début d'une méthode/d'un bloc
    public Pos apply(Operator operator) {
        // Renvoie une valeur à l'appelant
        return operator.apply(x, y, z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract("_ -> new")
    // Début d'une méthode/d'un bloc
    public Pos withX(DoubleUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return new Pos(operator.applyAsDouble(x), y, z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Pos withX(double x) {
        // Renvoie une valeur à l'appelant
        return new Pos(x, y, z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract("_ -> new")
    // Début d'une méthode/d'un bloc
    public Pos withY(DoubleUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return new Pos(x, operator.applyAsDouble(y), z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Pos withY(double y) {
        // Renvoie une valeur à l'appelant
        return new Pos(x, y, z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract("_ -> new")
    // Début d'une méthode/d'un bloc
    public Pos withZ(DoubleUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return new Pos(x, y, operator.applyAsDouble(z), yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Pos withZ(double z) {
        // Renvoie une valeur à l'appelant
        return new Pos(x, y, z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Début d'une méthode/d'un bloc
    public Pos add(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Pos(this.x + x, this.y + y, this.z + z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Pos add(Point point) {
        // Renvoie une valeur à l'appelant
        return add(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Pos add(double value) {
        // Renvoie une valeur à l'appelant
        return add(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Début d'une méthode/d'un bloc
    public Pos sub(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Pos(this.x - x, this.y - y, this.z - z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Pos sub(Point point) {
        // Renvoie une valeur à l'appelant
        return sub(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Pos sub(double value) {
        // Renvoie une valeur à l'appelant
        return sub(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Début d'une méthode/d'un bloc
    public Pos mul(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Pos(this.x * x, this.y * y, this.z * z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Pos mul(Point point) {
        // Renvoie une valeur à l'appelant
        return mul(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Pos mul(double value) {
        // Renvoie une valeur à l'appelant
        return mul(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Début d'une méthode/d'un bloc
    public Pos div(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Pos(this.x / x, this.y / y, this.z / z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Pos div(Point point) {
        // Renvoie une valeur à l'appelant
        return div(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Pos div(double value) {
        // Renvoie une valeur à l'appelant
        return div(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Pos relative(BlockFace face) {
        // Renvoie une valeur à l'appelant
        return (Pos) Point.super.relative(face);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Does nothing as this is already a {@link Pos}.
     * <p>
     * Marked as deprecated to warn against redundant usage.
     *
     * @return this position
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "-> this")
    // Début d'une méthode/d'un bloc
    public Pos asPos() {
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
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
    // Annotation pour l'élément suivant
    @Deprecated
    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _ -> new")
    // Début d'une méthode/d'un bloc
    public Pos asPos(float yaw, float pitch) {
        // Renvoie une valeur à l'appelant
        return withView(yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "-> new")
    // Début d'une méthode/d'un bloc
    public Pos normalize() {
        // Appelle une méthode
        final double length = length();
        // Renvoie une valeur à l'appelant
        return new Pos(x / length, y / length, z / length, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Pos cross(Point point) {
        // Renvoie une valeur à l'appelant
        return new Pos(y * point.z() - point.y() * z,
                // Instruction de code
                z * point.x() - point.z() * x,
                // Instruction de code
                x * point.y() - point.x() * y,
                // Instruction de code
                yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _ -> new")
    // Début d'une méthode/d'un bloc
    public Pos lerp(Point point, double alpha) {
        // Renvoie une valeur à l'appelant
        return new Pos(x + (alpha * (point.x() - x)),
                // Instruction de code
                y + (alpha * (point.y() - y)),
                // Instruction de code
                z + (alpha * (point.z() - z)),
                // Instruction de code
                yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Calculates a linear interpolation between this position's view and another position's view (yaw/pitch).
     * The coordinates (x/y/z) remain unchanged.
     *
     * @param pos   the other position
     * @param alpha the alpha value, must be between 0.0 and 1.0
     * @return a new position with interpolated view
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _ -> new")
    // Début d'une méthode/d'un bloc
    public Pos lerpView(Pos pos, float alpha) {
        // Renvoie une valeur à l'appelant
        return new Pos(x, y, z,
                // Instruction de code
                yaw + (alpha * (pos.yaw() - yaw)),
                // Appelle une méthode
                pitch + (alpha * (pos.pitch() - pitch)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "-> new")
    // Début d'une méthode/d'un bloc
    public Pos neg() {
        // Renvoie une valeur à l'appelant
        return new Pos(-x, -y, -z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Negates the view (yaw/pitch) of this position.
     *
     * @return a new position
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "-> new")
    // Début d'une méthode/d'un bloc
    public Pos negView() {
        // Renvoie une valeur à l'appelant
        return new Pos(x, y, z, -yaw, -pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "-> new")
    // Début d'une méthode/d'un bloc
    public Pos abs() {
        // Renvoie une valeur à l'appelant
        return new Pos(Math.abs(x), Math.abs(y), Math.abs(z), yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the absolute value of the view (yaw/pitch).
     *
     * @return a new position
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "-> new")
    // Début d'une méthode/d'un bloc
    public Pos absView() {
        // Renvoie une valeur à l'appelant
        return new Pos(x, y, z, Math.abs(yaw), Math.abs(pitch));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Pos min(Point point) {
        // Renvoie une valeur à l'appelant
        return new Pos(Math.min(x, point.x()), Math.min(y, point.y()), Math.min(z, point.z()), yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Début d'une méthode/d'un bloc
    public Pos min(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Pos(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z), yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Pos min(double value) {
        // Renvoie une valeur à l'appelant
        return new Pos(Math.min(x, value), Math.min(y, value), Math.min(z, value), yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Pos max(Point point) {
        // Renvoie une valeur à l'appelant
        return new Pos(Math.max(x, point.x()), Math.max(y, point.y()), Math.max(z, point.z()), yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_, _, _ -> new")
    // Début d'une méthode/d'un bloc
    public Pos max(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Pos(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z), yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true, value = "_ -> new")
    // Début d'une méthode/d'un bloc
    public Pos max(double value) {
        // Renvoie une valeur à l'appelant
        return new Pos(Math.max(x, value), Math.max(y, value), Math.max(z, value), yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    /**
     * A functional interface representing an operation on the components of a {@link Pos}.
     */
    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface Operator {
        /**
         * Uses a {@link Vec.Operator} for the position components (x/y/z) for a {@link Operator}.
         *
         * @param operator the vector operator
         * @return the position operator
         */
        // Début d'une méthode/d'un bloc
        static Operator operator(Vec.Operator operator) {
            // Renvoie une valeur à l'appelant
            return (x, y, z, yaw, pitch) -> operator.apply(x, y, z).asPos().withView(yaw, pitch);
        // Fin d'un bloc/d'une expression
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
        // Appelle une méthode
        Pos apply(double x, double y, double z, float yaw, float pitch);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
