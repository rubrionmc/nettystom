// Package declaration for this file
package net.minestom.server.utils.position;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.RelativeFlags;
// Import of a required class
import org.intellij.lang.annotations.MagicConstant;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class PositionUtils {
    // Start of a method/block
    public static Pos lookAlong(Pos position, double dx, double dy, double dz) {
        // Calls a method
        final float yaw = getLookYaw(dx, dz);
        // Calls a method
        final float pitch = getLookPitch(dx, dy, dz);
        // Returns a value to the caller
        return position.withView(yaw, pitch);
    // End of a block/expression
    }

    // Start of a method/block
    public static float getLookYaw(double dx, double dz) {
        // Calls a method
        final double radians = Math.atan2(dz, dx);
        // Calls a method
        final float degrees = (float)Math.toDegrees(radians) - 90;
        // Branch: checks a condition
        if (degrees < -180) return degrees + 360;
        // Branch: checks a condition
        if (degrees > 180) return degrees - 360;
        // Returns a value to the caller
        return degrees;
    // End of a block/expression
    }

    // Start of a method/block
    public static float getLookPitch(double dx, double dy, double dz) {
        // Calls a method
        final double radians = -Math.atan2(dy, Math.max(Math.abs(dx), Math.abs(dz)));
        // Returns a value to the caller
        return (float) Math.toDegrees(radians);
    // End of a block/expression
    }

    // Assigns a value
    public static Pos getPositionWithRelativeFlags(Pos start, Pos modifier, @MagicConstant(flagsFromClass = RelativeFlags.class) int flags) {
        // Calls a method
        double x = (flags & RelativeFlags.X) == 0 ? modifier.x() : start.x() + modifier.x();
        // Calls a method
        double y = (flags & RelativeFlags.Y) == 0 ? modifier.y() : start.y() + modifier.y();
        // Calls a method
        double z = (flags & RelativeFlags.Z) == 0 ? modifier.z() : start.z() + modifier.z();
        // Calls a method
        float yaw = (flags & RelativeFlags.YAW) == 0 ? modifier.yaw() : start.yaw() + modifier.yaw();
        // Calls a method
        float pitch = (flags & RelativeFlags.PITCH) == 0 ? modifier.pitch() : start.pitch() + modifier.pitch();
        // Returns a value to the caller
        return new Pos(x, y, z, yaw, pitch);
    // End of a block/expression
    }

    // Assigns a value
    public static Vec getVelocityWithRelativeFlags(Vec start, Vec modifier, @MagicConstant(flagsFromClass = RelativeFlags.class) int flags) {
        // Calls a method
        double x = (flags & RelativeFlags.DELTA_X) == 0 ? modifier.x() : start.x() + modifier.x();
        // Calls a method
        double y = (flags & RelativeFlags.DELTA_Y) == 0 ? modifier.y() : start.y() + modifier.y();
        // Calls a method
        double z = (flags & RelativeFlags.DELTA_Z) == 0 ? modifier.z() : start.z() + modifier.z();
        // Returns a value to the caller
        return new Vec(x, y, z);
    // End of a block/expression
    }
// End of a block/expression
}
