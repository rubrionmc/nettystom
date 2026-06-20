// Déclaration du paquet de ce fichier
package net.minestom.server.coordinate;

// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.utils.Direction;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;

// Import d'une classe nécessaire
import java.util.function.DoubleUnaryOperator;

// Import statique d'un membre
import static net.minestom.server.coordinate.CoordConversion.SECTION_SIZE;

/**
 * Represents an immutable 3D vector.
 * <p>
 * To become a value then primitive type.
 */
// Déclaration de type (classe/interface/enum/record)
public record Vec(double x, double y, double z) implements Point {
    // Appelle une méthode
    public static final Vec ZERO = new Vec(0);
    // Appelle une méthode
    public static final Vec ONE = new Vec(1);
    // Appelle une méthode
    public static final Vec SECTION = new Vec(SECTION_SIZE);

    // Affecte une valeur
    public static final double EPSILON = 0.000001;

    /**
     * Creates a new vec with the [x;z] coordinates set. Y is set to 0.
     *
     * @param x the X coordinate
     * @param z the Z coordinate
     */
    // Début d'une méthode/d'un bloc
    public Vec(double x, double z) {
        // Appelle une méthode
        this(x, 0, z);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a vec with all 3 coordinates sharing the same value.
     *
     * @param value the coordinates
     */
    // Début d'une méthode/d'un bloc
    public Vec(double value) {
        // Appelle une méthode
        this(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Converts a {@link Point} into a {@link Vec}.
     * Will cast if possible, or instantiate a new object.
     *
     * @param point the point to convert
     * @return the converted vector
     * @deprecated use {@link Point#asVec()} instead
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public static Vec fromPoint(Point point) {
        // Embranchement : vérifie une condition
        if (point instanceof Vec vec) return vec;
        // Renvoie une valeur à l'appelant
        return new Vec(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new point with coordinated depending on {@code this}.
     *
     * @param operator the operator
     * @return the created point
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec apply(Operator operator) {
        // Renvoie une valeur à l'appelant
        return operator.apply(x, y, z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec withX(DoubleUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return new Vec(operator.applyAsDouble(x), y, z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec withX(double x) {
        // Renvoie une valeur à l'appelant
        return new Vec(x, y, z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec withY(DoubleUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return new Vec(x, operator.applyAsDouble(y), z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec withY(double y) {
        // Renvoie une valeur à l'appelant
        return new Vec(x, y, z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec withZ(DoubleUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return new Vec(x, y, operator.applyAsDouble(z));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec withZ(double z) {
        // Renvoie une valeur à l'appelant
        return new Vec(x, y, z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Vec add(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Vec(this.x + x, this.y + y, this.z + z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Vec add(Point point) {
        // Renvoie une valeur à l'appelant
        return add(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Vec add(double value) {
        // Renvoie une valeur à l'appelant
        return add(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Vec sub(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Vec(this.x - x, this.y - y, this.z - z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Vec sub(Point point) {
        // Renvoie une valeur à l'appelant
        return sub(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Vec sub(double value) {
        // Renvoie une valeur à l'appelant
        return sub(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Vec mul(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Vec(this.x * x, this.y * y, this.z * z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Vec mul(Point point) {
        // Renvoie une valeur à l'appelant
        return mul(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Vec mul(double value) {
        // Renvoie une valeur à l'appelant
        return mul(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Vec div(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Vec(this.x / x, this.y / y, this.z / z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Vec div(Point point) {
        // Renvoie une valeur à l'appelant
        return div(point.x(), point.y(), point.z());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Vec div(double value) {
        // Renvoie une valeur à l'appelant
        return div(value, value, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Vec relative(BlockFace face) {
        // Appelle une méthode
        final Direction direction = face.toDirection();
        // Renvoie une valeur à l'appelant
        return add(direction.normalX(), direction.normalY(), direction.normalZ());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec neg() {
        // Renvoie une valeur à l'appelant
        return new Vec(-x, -y, -z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec abs() {
        // Renvoie une valeur à l'appelant
        return new Vec(Math.abs(x), Math.abs(y), Math.abs(z));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec min(Point point) {
        // Renvoie une valeur à l'appelant
        return new Vec(Math.min(x, point.x()), Math.min(y, point.y()), Math.min(z, point.z()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec min(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Vec(Math.min(this.x, x), Math.min(this.y, y), Math.min(this.z, z));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec min(double value) {
        // Renvoie une valeur à l'appelant
        return new Vec(Math.min(x, value), Math.min(y, value), Math.min(z, value));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec max(Point point) {
        // Renvoie une valeur à l'appelant
        return new Vec(Math.max(x, point.x()), Math.max(y, point.y()), Math.max(z, point.z()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec max(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new Vec(Math.max(this.x, x), Math.max(this.y, y), Math.max(this.z, z));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec max(double value) {
        // Renvoie une valeur à l'appelant
        return new Vec(Math.max(x, value), Math.max(y, value), Math.max(z, value));
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link Point#asPos()} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Pos asPosition() {
        // Renvoie une valeur à l'appelant
        return new Pos(x, y, z);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the magnitude of the vector squared.
     *
     * @return the magnitude
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public double lengthSquared() {
        // Renvoie une valeur à l'appelant
        return MathUtils.square(x) + MathUtils.square(y) + MathUtils.square(z);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the magnitude of the vector, defined as sqrt(x^2+y^2+z^2). The
     * value of this method is not cached and uses a costly square-root
     * function, so do not repeatedly call this method to get the vector's
     * magnitude. NaN will be returned if the inner result of the sqrt()
     * function overflows, which will be caused if the length is too long.
     *
     * @return the magnitude
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public double length() {
        // Renvoie une valeur à l'appelant
        return Math.sqrt(lengthSquared());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Converts this vector to a unit vector (a vector with length of 1).
     *
     * @return the same vector
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec normalize() {
        // Appelle une méthode
        final double length = length();
        // Renvoie une valeur à l'appelant
        return new Vec(x / length, y / length, z / length);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns if a vector is normalized
     *
     * @return whether the vector is normalised
     */
    // Début d'une méthode/d'un bloc
    public boolean isNormalized() {
        // Renvoie une valeur à l'appelant
        return Math.abs(lengthSquared() - 1) < EPSILON;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the angle between this vector and another in radians.
     *
     * @param vec the other vector
     * @return angle in radians
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public double angle(Vec vec) {
        // Appelle une méthode
        final double dot = MathUtils.clamp(dot(vec) / (length() * vec.length()), -1.0, 1.0);
        // Renvoie une valeur à l'appelant
        return Math.acos(dot);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Calculates the dot product of this vector with another. The dot product
     * is defined as x1*x2+y1*y2+z1*z2. The returned value is a scalar.
     *
     * @param vec the other vector
     * @return dot product
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public double dot(Vec vec) {
        // Renvoie une valeur à l'appelant
        return x * vec.x + y * vec.y + z * vec.z;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Calculates the cross product of this vector with another. The cross
     * product is defined as:
     * <ul>
     * <li>x = y1 * z2 - y2 * z1
     * <li>y = z1 * x2 - z2 * x1
     * <li>z = x1 * y2 - x2 * y1
     * </ul>
     *
     * @param o the other vector
     * @return the same vector
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec cross(Vec o) {
        // Renvoie une valeur à l'appelant
        return new Vec(y * o.z - o.y * z,
                // Instruction de code
                z * o.x - o.z * x,
                // Instruction de code
                x * o.y - o.x * y);
    // Fin d'un bloc/d'une expression
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
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec rotateAroundX(double angle) {
        // Boucle : répète un bloc
        double angleCos = Math.cos(angle);
        // Boucle : répète un bloc
        double angleSin = Math.sin(angle);

        // Boucle : répète un bloc
        double newY = angleCos * y - angleSin * z;
        // Boucle : répète un bloc
        double newZ = angleSin * y + angleCos * z;
        // Renvoie une valeur à l'appelant
        return new Vec(x, newY, newZ);
    // Fin d'un bloc/d'une expression
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
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec rotateAroundY(double angle) {
        // Appelle une méthode
        final double angleCos = Math.cos(angle);
        // Appelle une méthode
        final double angleSin = Math.sin(angle);

        // Affecte une valeur
        final double newX = angleCos * x + angleSin * z;
        // Affecte une valeur
        final double newZ = -angleSin * x + angleCos * z;
        // Renvoie une valeur à l'appelant
        return new Vec(newX, y, newZ);
    // Fin d'un bloc/d'une expression
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
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec rotateAroundZ(double angle) {
        // Appelle une méthode
        final double angleCos = Math.cos(angle);
        // Appelle une méthode
        final double angleSin = Math.sin(angle);

        // Affecte une valeur
        final double newX = angleCos * x - angleSin * y;
        // Affecte une valeur
        final double newY = angleSin * x + angleCos * y;
        // Renvoie une valeur à l'appelant
        return new Vec(newX, newY, z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec rotate(double angleX, double angleY, double angleZ) {
        // Renvoie une valeur à l'appelant
        return rotateAroundX(angleX).rotateAroundY(angleY).rotateAroundZ(angleZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec rotateFromView(float yawDegrees, float pitchDegrees) {
        // Appelle une méthode
        final double yaw = Math.toRadians(-1 * (yawDegrees + 90));
        // Appelle une méthode
        final double pitch = Math.toRadians(-pitchDegrees);

        // Appelle une méthode
        final double cosYaw = Math.cos(yaw);
        // Appelle une méthode
        final double cosPitch = Math.cos(pitch);
        // Boucle : répète un bloc
        double sinYaw = Math.sin(yaw);
        // Boucle : répète un bloc
        double sinPitch = Math.sin(pitch);

        // Boucle : répète un bloc
        double initialX, initialY, initialZ;
        // Boucle : répète un bloc
        double x, y, z;

        // Z_Axis rotation (Pitch)
        // Appelle une méthode
        initialX = x();
        // Appelle une méthode
        initialY = y();
        // Affecte une valeur
        x = initialX * cosPitch - initialY * sinPitch;
        // Affecte une valeur
        y = initialX * sinPitch + initialY * cosPitch;

        // Y_Axis rotation (Yaw)
        // Appelle une méthode
        initialZ = z();
        // Affecte une valeur
        initialX = x;
        // Affecte une valeur
        z = initialZ * cosYaw - initialX * sinYaw;
        // Affecte une valeur
        x = initialZ * sinYaw + initialX * cosYaw;

        // Renvoie une valeur à l'appelant
        return new Vec(x, y, z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec rotateFromView(Pos pos) {
        // Renvoie une valeur à l'appelant
        return rotateFromView(pos.yaw(), pos.pitch());
    // Fin d'un bloc/d'une expression
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
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec rotateAroundAxis(Vec axis, double angle) throws IllegalArgumentException {
        // Renvoie une valeur à l'appelant
        return rotateAroundNonUnitAxis(axis.isNormalized() ? axis : axis.normalize(), angle);
    // Fin d'un bloc/d'une expression
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
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec rotateAroundNonUnitAxis(Vec axis, double angle) throws IllegalArgumentException {
        // Appelle une méthode
        final double x = x(), y = y(), z = z();
        // Appelle une méthode
        final double x2 = axis.x(), y2 = axis.y(), z2 = axis.z();
        // Boucle : répète un bloc
        double cosTheta = Math.cos(angle);
        // Boucle : répète un bloc
        double sinTheta = Math.sin(angle);
        // Boucle : répète un bloc
        double dotProduct = this.dot(axis);

        // Affecte une valeur
        final double newX = x2 * dotProduct * (1d - cosTheta)
                // Instruction de code
                + x * cosTheta
                // Instruction de code
                + (-z2 * y + y2 * z) * sinTheta;
        // Affecte une valeur
        final double newY = y2 * dotProduct * (1d - cosTheta)
                // Instruction de code
                + y * cosTheta
                // Instruction de code
                + (z2 * x - x2 * z) * sinTheta;
        // Affecte une valeur
        final double newZ = z2 * dotProduct * (1d - cosTheta)
                // Instruction de code
                + z * cosTheta
                // Instruction de code
                + (-y2 * x + x2 * y) * sinTheta;

        // Renvoie une valeur à l'appelant
        return new Vec(newX, newY, newZ);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Calculates a linear interpolation between this vector with another
     * vector.
     *
     * @param vec   the other vector
     * @param alpha The alpha value, must be between 0.0 and 1.0
     * @return Linear interpolated vector
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec lerp(Vec vec, double alpha) {
        // Renvoie une valeur à l'appelant
        return new Vec(x + (alpha * (vec.x - x)),
                // Instruction de code
                y + (alpha * (vec.y - y)),
                // Instruction de code
                z + (alpha * (vec.z - z)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Vec interpolate(Vec target, double alpha, Interpolation interpolation) {
        // Renvoie une valeur à l'appelant
        return lerp(target, interpolation.apply(alpha));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface Operator {
        // Appelle une méthode
        Operator EPSILON = operator(v -> Math.abs(v) < Vec.EPSILON ? 0 : v);
        // Appelle une méthode
        Operator FLOOR = operator(Math::floor);
        // Appelle une méthode
        Operator SIGNUM = operator(Math::signum);
        // Appelle une méthode
        Operator ABS = operator(Math::abs);
        // Appelle une méthode
        Operator NEG = operator(v -> -v);
        // Appelle une méthode
        Operator CEIL = operator(Math::ceil);

        // Début d'une méthode/d'un bloc
        static Operator operator(DoubleUnaryOperator operator) {
            // Renvoie une valeur à l'appelant
            return (x, y, z) -> new Vec(operator.applyAsDouble(x), operator.applyAsDouble(y), operator.applyAsDouble(z));
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        Vec apply(double x, double y, double z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    public interface Interpolation {
        // Affecte une valeur
        Interpolation LINEAR = a -> a;
        // Affecte une valeur
        Interpolation SMOOTH = a -> a * a * (3 - 2 * a);

        // Boucle : répète un bloc
        double apply(double a);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
