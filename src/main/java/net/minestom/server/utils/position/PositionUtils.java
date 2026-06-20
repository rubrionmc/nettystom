// Déclaration du paquet de ce fichier
package net.minestom.server.utils.position;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.RelativeFlags;
// Import d'une classe nécessaire
import org.intellij.lang.annotations.MagicConstant;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class PositionUtils {
    // Début d'une méthode/d'un bloc
    public static Pos lookAlong(Pos position, double dx, double dy, double dz) {
        // Appelle une méthode
        final float yaw = getLookYaw(dx, dz);
        // Appelle une méthode
        final float pitch = getLookPitch(dx, dy, dz);
        // Renvoie une valeur à l'appelant
        return position.withView(yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float getLookYaw(double dx, double dz) {
        // Appelle une méthode
        final double radians = Math.atan2(dz, dx);
        // Appelle une méthode
        final float degrees = (float)Math.toDegrees(radians) - 90;
        // Embranchement : vérifie une condition
        if (degrees < -180) return degrees + 360;
        // Embranchement : vérifie une condition
        if (degrees > 180) return degrees - 360;
        // Renvoie une valeur à l'appelant
        return degrees;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float getLookPitch(double dx, double dy, double dz) {
        // Appelle une méthode
        final double radians = -Math.atan2(dy, Math.max(Math.abs(dx), Math.abs(dz)));
        // Renvoie une valeur à l'appelant
        return (float) Math.toDegrees(radians);
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static Pos getPositionWithRelativeFlags(Pos start, Pos modifier, @MagicConstant(flagsFromClass = RelativeFlags.class) int flags) {
        // Appelle une méthode
        double x = (flags & RelativeFlags.X) == 0 ? modifier.x() : start.x() + modifier.x();
        // Appelle une méthode
        double y = (flags & RelativeFlags.Y) == 0 ? modifier.y() : start.y() + modifier.y();
        // Appelle une méthode
        double z = (flags & RelativeFlags.Z) == 0 ? modifier.z() : start.z() + modifier.z();
        // Appelle une méthode
        float yaw = (flags & RelativeFlags.YAW) == 0 ? modifier.yaw() : start.yaw() + modifier.yaw();
        // Appelle une méthode
        float pitch = (flags & RelativeFlags.PITCH) == 0 ? modifier.pitch() : start.pitch() + modifier.pitch();
        // Renvoie une valeur à l'appelant
        return new Pos(x, y, z, yaw, pitch);
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static Vec getVelocityWithRelativeFlags(Vec start, Vec modifier, @MagicConstant(flagsFromClass = RelativeFlags.class) int flags) {
        // Appelle une méthode
        double x = (flags & RelativeFlags.DELTA_X) == 0 ? modifier.x() : start.x() + modifier.x();
        // Appelle une méthode
        double y = (flags & RelativeFlags.DELTA_Y) == 0 ? modifier.y() : start.y() + modifier.y();
        // Appelle une méthode
        double z = (flags & RelativeFlags.DELTA_Z) == 0 ? modifier.z() : start.z() + modifier.z();
        // Renvoie une valeur à l'appelant
        return new Vec(x, y, z);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
