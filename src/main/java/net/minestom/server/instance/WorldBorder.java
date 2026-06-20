// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.*;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;

/**
 * Represents the world border state of an {@link Instance},
 * can be retrieved with {@link Instance#getWorldBorder()}.
 *
 * @param diameter                  the diameter of this world border
 * @param centerX                   the center x coordinate of this world border
 * @param centerZ                   the center z coordinate of this world border
 * @param warningDistance           the distance from this world border before
 *                                  the warning indicator is displayed
 * @param warningTime               the length of time the warning indicator
 *                                  is displayed
 * @param dimensionTeleportBoundary restricts the distance travelled when entering
 *                                  this world from another dimension (should be at
 *                                  least the diameter of the world border)
 */
// Déclaration de type (classe/interface/enum/record)
public record WorldBorder(double diameter, double centerX, double centerZ, int warningDistance, int warningTime, int dimensionTeleportBoundary) {
    // Appelle une méthode
    public static final WorldBorder DEFAULT_BORDER = new WorldBorder(ServerFlag.WORLD_BORDER_SIZE * 2, 0, 0, 5, 15, ServerFlag.WORLD_BORDER_SIZE);

    /**
     * @throws IllegalArgumentException if {@code diameter} is less than 0
     */
    // Début d'une méthode/d'un bloc
    public WorldBorder {
        // Appelle une méthode
        Check.argCondition(diameter < 0, "Diameter should be >= 0");
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public WorldBorder(double diameter, double centerX, double centerZ, int warningDistance, int warningTime) {
        // Appelle une méthode
        this(diameter, centerX, centerZ, warningDistance, warningTime, ServerFlag.WORLD_BORDER_SIZE);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public WorldBorder withDiameter(double diameter) {
        // Renvoie une valeur à l'appelant
        return new WorldBorder(diameter, centerX, centerZ, warningDistance, warningTime, dimensionTeleportBoundary);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public WorldBorder withCenter(double centerX, double centerZ) {
        // Renvoie une valeur à l'appelant
        return new WorldBorder(diameter, centerX, centerZ, warningDistance, warningTime, dimensionTeleportBoundary);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public WorldBorder withWarningDistance(int warningDistance) {
        // Renvoie une valeur à l'appelant
        return new WorldBorder(diameter, centerX, centerZ, warningDistance, warningTime, dimensionTeleportBoundary);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public WorldBorder withWarningTime(int warningTime) {
        // Renvoie une valeur à l'appelant
        return new WorldBorder(diameter, centerX, centerZ, warningDistance, warningTime, dimensionTeleportBoundary);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Used to know if a position is located inside the world border or not.
     *
     * @param point the point to check
     * @return true if {@code position} is inside the world border, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean inBounds(Point point) {
        // Boucle : répète un bloc
        double radius = diameter / 2;
        // Renvoie une valeur à l'appelant
        return point.x() <= centerX + radius && point.x() >= centerX - radius &&
                // Appelle une méthode
                point.z() <= centerZ + radius && point.z() >= centerZ - radius;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Used to know if an entity is located inside the world border or not.
     *
     * @param entity the entity to check
     * @return true if {@code entity} is inside the world border, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean inBounds(Entity entity) {
        // Renvoie une valeur à l'appelant
        return inBounds(entity.getPosition());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a {@link InitializeWorldBorderPacket} which dictates every property
     * of the world border.
     *
     * @param targetDiameter the target diameter if there is a current lerp in progress
     * @param transitionTime the transition time in milliseconds of the current
     *                       lerp in progress
     * @return               an {@link InitializeWorldBorderPacket} reflecting the
     *                       properties of this border
     */
    // Début d'une méthode/d'un bloc
    public InitializeWorldBorderPacket createInitializePacket(double targetDiameter, long transitionTime) {
        // Renvoie une valeur à l'appelant
        return new InitializeWorldBorderPacket(centerX, centerZ, diameter, targetDiameter, transitionTime, dimensionTeleportBoundary, warningTime, warningDistance);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a {@link WorldBorderSizePacket} which dictates the origin of the world border.
     *
     * @return the {@link WorldBorderSizePacket} with the center values of this world border
     */
    // Début d'une méthode/d'un bloc
    public WorldBorderCenterPacket createCenterPacket() {
        // Renvoie une valeur à l'appelant
        return new WorldBorderCenterPacket(centerX, centerZ);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a {@link WorldBorderLerpSizePacket} which lerps the border from its current
     * diameter to the target diameter over the given transition time.
     *
     * @param targetDiameter the final diameter of the border after this transition
     * @param transitionTime the transition time in milliseconds for this lerp
     * @return               the {@link WorldBorderLerpSizePacket} representing this lerp
     */
    // Début d'une méthode/d'un bloc
    public WorldBorderLerpSizePacket createLerpSizePacket(double targetDiameter, long transitionTime) {
        // Renvoie une valeur à l'appelant
        return new WorldBorderLerpSizePacket(diameter, targetDiameter, transitionTime);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a {@link WorldBorderSizePacket} with this world border's diameter.
     *
     * @return the {@link WorldBorderSizePacket} with this world border's diameter
     */
    // Début d'une méthode/d'un bloc
    public WorldBorderSizePacket createSizePacket() {
        // Renvoie une valeur à l'appelant
        return new WorldBorderSizePacket(diameter);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a {@link WorldBorderWarningDelayPacket} with this world border's warning time
     *
     * @return the {@link WorldBorderWarningDelayPacket} with this world border's warning time
     */
    // Début d'une méthode/d'un bloc
    public WorldBorderWarningDelayPacket createWarningDelayPacket() {
        // Renvoie une valeur à l'appelant
        return new WorldBorderWarningDelayPacket(warningTime);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a {@link WorldBorderWarningReachPacket} with this world border's warning distance
     *
     * @return the {@link WorldBorderWarningReachPacket} with this world border's warning distance
     */
    // Début d'une méthode/d'un bloc
    public WorldBorderWarningReachPacket createWarningReachPacket() {
        // Renvoie une valeur à l'appelant
        return new WorldBorderWarningReachPacket(warningDistance);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
