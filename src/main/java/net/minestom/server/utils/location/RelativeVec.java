// Déclaration du paquet de ce fichier
package net.minestom.server.utils.location;

// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Objects;

/**
 * Represents a location which can have fields relative to an {@link Entity} position.
 * <p>
 * Useful for parsing Vec2 or Vec3 types
 */
// Déclaration de type (classe/interface/enum/record)
public record RelativeVec(Vec vec, CoordinateType coordinateType, boolean relativeX, boolean relativeY, boolean relativeZ) {

    // Début d'une méthode/d'un bloc
    public RelativeVec {
        // Appelle une méthode
        Check.argCondition(relativeX && coordinateType == CoordinateType.ABSOLUTE, "RelativeVec `x` cannot have relativity while coordinateType is absolute.");
        // Appelle une méthode
        Check.argCondition(relativeY && coordinateType == CoordinateType.ABSOLUTE, "RelativeVec `y` cannot have relativity while coordinateType is absolute.");
        // Appelle une méthode
        Check.argCondition(relativeZ && coordinateType == CoordinateType.ABSOLUTE, "RelativeVec `z` cannot have relativity while coordinateType is absolute.");

        // Only XZ for Vec2 types, so we need to check y as well.
        // Appelle une méthode
        Check.argCondition(coordinateType == CoordinateType.LOCAL && !(relativeX && (relativeY || vec.y() == 0) && relativeZ), "RelativeVec is always relative while coordinateType is local.");
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the location based on the relative fields and {@link #vec()}.
     *
     * @param origin the origin position, null if none
     * @return the location
     */
    // Début d'une méthode/d'un bloc
    public Vec from(@Nullable Pos origin) {
        // Appelle une méthode
        origin = Objects.requireNonNullElse(origin, Pos.ZERO);
        // Renvoie une valeur à l'appelant
        return coordinateType.convert(vec, origin, relativeX, relativeY, relativeZ);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the location based on the relative fields.
     *
     * @param entity the entity to get the relative position from
     * @return the location
     */
    // Début d'une méthode/d'un bloc
    public Vec from(@Nullable Entity entity) {
        // Embranchement : vérifie une condition
        if (entity != null) {
            // Renvoie une valeur à l'appelant
            return from(entity.getPosition());
        // Branche alternative de la condition
        } else {
            // Renvoie une valeur à l'appelant
            return from(Pos.ZERO);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Shorthand for {@link #from(Pos)}
     * If player uses their position otherwise, {@link Vec#ZERO}
     *
     * @param sender entity
     * @return the position with any relativity
     */
    // Début d'une méthode/d'un bloc
    public Vec fromSender(@Nullable CommandSender sender) {
        // Appelle une méthode
        final var entityPosition = sender instanceof Player ? ((Player) sender).getPosition() : Pos.ZERO;
        // Renvoie une valeur à l'appelant
        return from(entityPosition);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Computes a view {@link Vec} based on the given point's yaw and pitch.
     * If no point is null, a default position {@link Pos#ZERO} is used.
     *
     * @param point The reference position used for computing relative coordinates. If null {@link Pos#ZERO}
     * @return A {@link Vec} with XZ based on the provided position. Y is ignored.
     */
    // Début d'une méthode/d'un bloc
    public Vec fromView(@Nullable Pos point) {
        // Embranchement : vérifie une condition
        if (!relativeX && !relativeY && !relativeZ) {
            // Renvoie une valeur à l'appelant
            return vec;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        final var absolute = Objects.requireNonNullElse(point, Pos.ZERO);
        // Appelle une méthode
        final double x = vec.x() + (relativeX ? absolute.yaw() : 0);
        // Appelle une méthode
        final double z = vec.z() + (relativeZ ? absolute.pitch() : 0);
        // Renvoie une valeur à l'appelant
        return new Vec(x, 0, z);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Shorthand for {@link #fromView(Pos)}
     * @param entity to get the position from, otherwise {@link Pos#ZERO}
     * @return the view.
     */
    // Début d'une méthode/d'un bloc
    public Vec fromView(@Nullable Entity entity) {
        // Appelle une méthode
        final var entityPosition = entity != null ? entity.getPosition() : Pos.ZERO;
        // Renvoie une valeur à l'appelant
        return fromView(entityPosition);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the 'x' field is relative.
     *
     * @return true if the 'x' field is relative
     */
    // Début d'une méthode/d'un bloc
    public boolean isRelativeX() {
        // Renvoie une valeur à l'appelant
        return relativeX;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the 'y' field is relative.
     *
     * @return true if the 'y' field is relative
     */
    // Début d'une méthode/d'un bloc
    public boolean isRelativeY() {
        // Renvoie une valeur à l'appelant
        return relativeY;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the 'z' field is relative.
     *
     * @return true if the 'z' field is relative
     */
    // Début d'une méthode/d'un bloc
    public boolean isRelativeZ() {
        // Renvoie une valeur à l'appelant
        return relativeZ;
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum CoordinateType {
        /**
         * Relative when any XYZ have the relative flag; unless local.
         */
        // Début d'une méthode/d'un bloc
        RELATIVE((relative, origin, relativeX, relativeY, relativeZ) -> {
            // Appelle une méthode
            final var absolute = Objects.requireNonNullElse(origin, Vec.ZERO);
            // Appelle une méthode
            final double x = relative.x() + (relativeX ? absolute.x() : 0);
            // Appelle une méthode
            final double y = relative.y() + (relativeY ? absolute.y() : 0);
            // Appelle une méthode
            final double z = relative.z() + (relativeZ ? absolute.z() : 0);
            // Renvoie une valeur à l'appelant
            return new Vec(x, y, z);
        // Instruction de code
        }),
        /**
         * Local type used in direction, requires full relatively on XZ/XYZ
         */
        // Début d'une méthode/d'un bloc
        LOCAL((local, origin, relativeX, relativeY, relativeZ) -> {
            // Appelle une méthode
            final Vec vec1 = new Vec(Math.cos(Math.toRadians(origin.yaw() + 90.0f)), 0, Math.sin(Math.toRadians(origin.yaw() + 90.0f)));
            // Appelle une méthode
            final Vec a = vec1.mul(Math.cos(Math.toRadians(-origin.pitch()))).withY(Math.sin(Math.toRadians(-origin.pitch())));
            // Appelle une méthode
            final Vec b = vec1.mul(Math.cos(Math.toRadians(-origin.pitch() + 90.0f))).withY(Math.sin(Math.toRadians(-origin.pitch() + 90.0f)));
            // Appelle une méthode
            final Vec c = a.cross(b).neg();
            // Appelle une méthode
            final Vec relativePos = a.mul(local.z()).add(b.mul(local.y())).add(c.mul(local.x()));
            // Renvoie une valeur à l'appelant
            return origin.add(relativePos).asVec();
        // Instruction de code
        }),
        /**
         * Absolute just returns the original vector.
         */
        // Appelle une méthode
        ABSOLUTE(((vec, origin, relativeX1, relativeY1, relativeZ1) -> vec));

        // Instruction de code
        private final CoordinateConverter converter;

        // Début d'une méthode/d'un bloc
        CoordinateType(CoordinateConverter converter) {
            // Accès à l'objet courant/parent
            this.converter = converter;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private Vec convert(Vec vec, Pos origin, boolean relativeX, boolean relativeY, boolean relativeZ) {
            // Renvoie une valeur à l'appelant
            return converter.convert(vec, origin, relativeX, relativeY, relativeZ);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    private interface CoordinateConverter {
        // Appelle une méthode
        Vec convert(Vec vec, Pos origin, boolean relativeX, boolean relativeY, boolean relativeZ);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
