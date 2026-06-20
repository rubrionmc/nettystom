// Déclaration du paquet de ce fichier
package net.minestom.server.coordinate;

// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.utils.Direction;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.position.PositionUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;

// Import d'une classe nécessaire
import java.util.function.DoubleUnaryOperator;

/**
 * Represents a position containing coordinates and a view.
 * <p>
 * To become a value then primitive type.
 */
// Déclaration de type (classe/interface/enum/record)
public record Pos(double x, double y, double z, float yaw, float pitch) implements Point {
    // Appelle une méthode
    public static final Pos ZERO = new Pos(0, 0, 0);

    // Début d'une méthode/d'un bloc
    public Pos {
        // Appelle une méthode
        yaw = fixYaw(yaw);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Pos(double x, double y, double z) {
        // Appelle une méthode
        this(x, y, z, 0, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Pos(Point point, float yaw, float pitch) {
        // Appelle une méthode
        this(point.x(), point.y(), point.z(), yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

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
    @Deprecated
    // Début d'une méthode/d'un bloc
    public static Pos fromPoint(Point point) {
        // Embranchement : vérifie une condition
        if (point instanceof Pos pos) return pos;
        // Renvoie une valeur à l'appelant
        return new Pos(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the 3 coordinates of this position.
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

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pos withCoord(Point point) {
        // Renvoie une valeur à l'appelant
        return withCoord(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pos withView(float yaw, float pitch) {
        // Renvoie une valeur à l'appelant
        return new Pos(x, y, z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

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
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
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
        final double xz = Math.sqrt(MathUtils.square(x) + MathUtils.square(z));
        // Affecte une valeur
        final double _2PI = 2 * Math.PI;
        // Renvoie une valeur à l'appelant
        return withView((float) Math.toDegrees((theta + _2PI) % _2PI),
                // Appelle une méthode
                (float) Math.toDegrees(Math.atan(-point.y() / xz)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pos withYaw(float yaw) {
        // Renvoie une valeur à l'appelant
        return new Pos(x, y, z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pos withYaw(DoubleUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return withYaw((float) operator.applyAsDouble(yaw));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pos withPitch(float pitch) {
        // Renvoie une valeur à l'appelant
        return new Pos(x, y, z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

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

    // Début d'une méthode/d'un bloc
    public boolean sameView(float yaw, float pitch) {
        // Renvoie une valeur à l'appelant
        return Float.compare(this.yaw, yaw) == 0 &&
                // Appelle une méthode
                Float.compare(this.pitch, pitch) == 0;
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
     * @return The closest direction {@link #yaw() yaw} and {@link #pitch() pitch} are facing to.
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
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pos apply(Operator operator) {
        // Renvoie une valeur à l'appelant
        return operator.apply(x, y, z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pos withX(DoubleUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return new Pos(operator.applyAsDouble(x), y, z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pos withX(double x) {
        // Renvoie une valeur à l'appelant
        return new Pos(x, y, z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pos withY(DoubleUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return new Pos(x, operator.applyAsDouble(y), z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pos withY(double y) {
        // Renvoie une valeur à l'appelant
        return new Pos(x, y, z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pos withZ(DoubleUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return new Pos(x, y, operator.applyAsDouble(z), yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pos withZ(double z) {
        // Renvoie une valeur à l'appelant
        return new Pos(x, y, z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Pos add(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Pos(this.x + x, this.y + y, this.z + z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Pos add(Point point) {
        // Renvoie une valeur à l'appelant
        return add(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Pos add(double value) {
        // Renvoie une valeur à l'appelant
        return add(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Pos sub(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Pos(this.x - x, this.y - y, this.z - z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Pos sub(Point point) {
        // Renvoie une valeur à l'appelant
        return sub(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Pos sub(double value) {
        // Renvoie une valeur à l'appelant
        return sub(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Pos mul(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Pos(this.x * x, this.y * y, this.z * z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Pos mul(Point point) {
        // Renvoie une valeur à l'appelant
        return mul(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Pos mul(double value) {
        // Renvoie une valeur à l'appelant
        return mul(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Pos div(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Pos(this.x / x, this.y / y, this.z / z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Pos div(Point point) {
        // Renvoie une valeur à l'appelant
        return div(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Pos div(double value) {
        // Renvoie une valeur à l'appelant
        return div(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Pos relative(BlockFace face) {
        // Renvoie une valeur à l'appelant
        return (Pos) Point.super.relative(face);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface Operator {
        // Appelle une méthode
        Pos apply(double x, double y, double z, float yaw, float pitch);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Fixes a yaw value that is not between -180.0F and 180.0F
     * So for example -1355.0F becomes 85.0F and 225.0F becomes -135.0F
     *
     * @param yaw The possible "wrong" yaw
     * @return a fixed yaw
     */
    // Début d'une méthode/d'un bloc
    public static float fixYaw(float yaw) {
        // Affecte une valeur
        yaw = yaw % 360;
        // Embranchement : vérifie une condition
        if (yaw < -180.0F) {
            // Affecte une valeur
            yaw += 360.0F;
        // Embranchement : vérifie une condition
        } else if (yaw > 180.0F) {
            // Affecte une valeur
            yaw -= 360.0F;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return yaw;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
