// Package declaration for this file
package net.minestom.server.utils.location;

// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Objects;

/**
 * Represents a location which can have fields relative to an {@link Entity} position.
 * <p>
 * Useful for parsing Vec2 or Vec3 types
 */
// Type declaration (class/interface/enum/record)
public record RelativeVec(Vec vec, CoordinateType coordinateType, boolean relativeX, boolean relativeY, boolean relativeZ) {

    // Start of a method/block
    public RelativeVec {
        // Calls a method
        Check.argCondition(relativeX && coordinateType == CoordinateType.ABSOLUTE, "RelativeVec `x` cannot have relativity while coordinateType is absolute.");
        // Calls a method
        Check.argCondition(relativeY && coordinateType == CoordinateType.ABSOLUTE, "RelativeVec `y` cannot have relativity while coordinateType is absolute.");
        // Calls a method
        Check.argCondition(relativeZ && coordinateType == CoordinateType.ABSOLUTE, "RelativeVec `z` cannot have relativity while coordinateType is absolute.");

        // Only XZ for Vec2 types, so we need to check y as well.
        // Calls a method
        Check.argCondition(coordinateType == CoordinateType.LOCAL && !(relativeX && (relativeY || vec.y() == 0) && relativeZ), "RelativeVec is always relative while coordinateType is local.");
    // End of a block/expression
    }

    /**
     * Gets the location based on the relative fields and {@link #vec()}.
     *
     * @param origin the origin position, null if none
     * @return the location
     */
    // Start of a method/block
    public Vec from(@Nullable Pos origin) {
        // Calls a method
        origin = Objects.requireNonNullElse(origin, Pos.ZERO);
        // Returns a value to the caller
        return coordinateType.convert(vec, origin, relativeX, relativeY, relativeZ);
    // End of a block/expression
    }

    /**
     * Gets the location based on the relative fields.
     *
     * @param entity the entity to get the relative position from
     * @return the location
     */
    // Start of a method/block
    public Vec from(@Nullable Entity entity) {
        // Branch: checks a condition
        if (entity != null) {
            // Returns a value to the caller
            return from(entity.getPosition());
        // Alternative branch of the condition
        } else {
            // Returns a value to the caller
            return from(Pos.ZERO);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Shorthand for {@link #from(Pos)}
     * If player uses their position otherwise, {@link Vec#ZERO}
     *
     * @param sender entity
     * @return the position with any relativity
     */
    // Start of a method/block
    public Vec fromSender(@Nullable CommandSender sender) {
        // Calls a method
        final var entityPosition = sender instanceof Player ? ((Player) sender).getPosition() : Pos.ZERO;
        // Returns a value to the caller
        return from(entityPosition);
    // End of a block/expression
    }

    /**
     * Computes a view {@link Vec} based on the given point's yaw and pitch.
     * If no point is null, a default position {@link Pos#ZERO} is used.
     *
     * @param point The reference position used for computing relative coordinates. If null {@link Pos#ZERO}
     * @return A {@link Vec} with XZ based on the provided position. Y is ignored.
     */
    // Start of a method/block
    public Vec fromView(@Nullable Pos point) {
        // Branch: checks a condition
        if (!relativeX && !relativeY && !relativeZ) {
            // Returns a value to the caller
            return vec;
        // End of a block/expression
        }
        // Calls a method
        final var absolute = Objects.requireNonNullElse(point, Pos.ZERO);
        // Calls a method
        final double x = vec.x() + (relativeX ? absolute.yaw() : 0);
        // Calls a method
        final double z = vec.z() + (relativeZ ? absolute.pitch() : 0);
        // Returns a value to the caller
        return new Vec(x, 0, z);
    // End of a block/expression
    }

    /**
     * Shorthand for {@link #fromView(Pos)}
     * @param entity to get the position from, otherwise {@link Pos#ZERO}
     * @return the view.
     */
    // Start of a method/block
    public Vec fromView(@Nullable Entity entity) {
        // Calls a method
        final var entityPosition = entity != null ? entity.getPosition() : Pos.ZERO;
        // Returns a value to the caller
        return fromView(entityPosition);
    // End of a block/expression
    }

    /**
     * Gets if the 'x' field is relative.
     *
     * @return true if the 'x' field is relative
     */
    // Start of a method/block
    public boolean isRelativeX() {
        // Returns a value to the caller
        return relativeX;
    // End of a block/expression
    }

    /**
     * Gets if the 'y' field is relative.
     *
     * @return true if the 'y' field is relative
     */
    // Start of a method/block
    public boolean isRelativeY() {
        // Returns a value to the caller
        return relativeY;
    // End of a block/expression
    }

    /**
     * Gets if the 'z' field is relative.
     *
     * @return true if the 'z' field is relative
     */
    // Start of a method/block
    public boolean isRelativeZ() {
        // Returns a value to the caller
        return relativeZ;
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum CoordinateType {
        /**
         * Relative when any XYZ have the relative flag; unless local.
         */
        // Start of a method/block
        RELATIVE((relative, origin, relativeX, relativeY, relativeZ) -> {
            // Calls a method
            final var absolute = Objects.requireNonNullElse(origin, Vec.ZERO);
            // Calls a method
            final double x = relative.x() + (relativeX ? absolute.x() : 0);
            // Calls a method
            final double y = relative.y() + (relativeY ? absolute.y() : 0);
            // Calls a method
            final double z = relative.z() + (relativeZ ? absolute.z() : 0);
            // Returns a value to the caller
            return new Vec(x, y, z);
        // Code statement
        }),
        /**
         * Local type used in direction, requires full relatively on XZ/XYZ
         */
        // Start of a method/block
        LOCAL((local, origin, relativeX, relativeY, relativeZ) -> {
            // Calls a method
            final Vec vec1 = new Vec(Math.cos(Math.toRadians(origin.yaw() + 90.0f)), 0, Math.sin(Math.toRadians(origin.yaw() + 90.0f)));
            // Calls a method
            final Vec a = vec1.mul(Math.cos(Math.toRadians(-origin.pitch()))).withY(Math.sin(Math.toRadians(-origin.pitch())));
            // Calls a method
            final Vec b = vec1.mul(Math.cos(Math.toRadians(-origin.pitch() + 90.0f))).withY(Math.sin(Math.toRadians(-origin.pitch() + 90.0f)));
            // Calls a method
            final Vec c = a.cross(b).neg();
            // Calls a method
            final Vec relativePos = a.mul(local.z()).add(b.mul(local.y())).add(c.mul(local.x()));
            // Returns a value to the caller
            return origin.add(relativePos).asVec();
        // Code statement
        }),
        /**
         * Absolute just returns the original vector.
         */
        // Calls a method
        ABSOLUTE(((vec, origin, relativeX1, relativeY1, relativeZ1) -> vec));

        // Code statement
        private final CoordinateConverter converter;

        // Start of a method/block
        CoordinateType(CoordinateConverter converter) {
            // Access to the current/parent object
            this.converter = converter;
        // End of a block/expression
        }

        // Start of a method/block
        private Vec convert(Vec vec, Pos origin, boolean relativeX, boolean relativeY, boolean relativeZ) {
            // Returns a value to the caller
            return converter.convert(vec, origin, relativeX, relativeY, relativeZ);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @FunctionalInterface
    // Type declaration (class/interface/enum/record)
    private interface CoordinateConverter {
        // Calls a method
        Vec convert(Vec vec, Pos origin, boolean relativeX, boolean relativeY, boolean relativeZ);
    // End of a block/expression
    }
// End of a block/expression
}
