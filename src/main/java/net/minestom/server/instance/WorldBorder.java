// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.network.packet.server.play.*;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
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
// Type declaration (class/interface/enum/record)
public record WorldBorder(double diameter, double centerX, double centerZ, int warningDistance, int warningTime, int dimensionTeleportBoundary) {
    // Calls a method
    public static final WorldBorder DEFAULT_BORDER = new WorldBorder(ServerFlag.WORLD_BORDER_SIZE * 2, 0, 0, 5, 15, ServerFlag.WORLD_BORDER_SIZE);

    /**
     * @throws IllegalArgumentException if {@code diameter} is less than 0
     */
    // Start of a method/block
    public WorldBorder {
        // Calls a method
        Check.argCondition(diameter < 0, "Diameter should be >= 0");
    // End of a block/expression
    }

    // Start of a method/block
    public WorldBorder(double diameter, double centerX, double centerZ, int warningDistance, int warningTime) {
        // Calls a method
        this(diameter, centerX, centerZ, warningDistance, warningTime, ServerFlag.WORLD_BORDER_SIZE);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public WorldBorder withDiameter(double diameter) {
        // Returns a value to the caller
        return new WorldBorder(diameter, centerX, centerZ, warningDistance, warningTime, dimensionTeleportBoundary);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public WorldBorder withCenter(double centerX, double centerZ) {
        // Returns a value to the caller
        return new WorldBorder(diameter, centerX, centerZ, warningDistance, warningTime, dimensionTeleportBoundary);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public WorldBorder withWarningDistance(int warningDistance) {
        // Returns a value to the caller
        return new WorldBorder(diameter, centerX, centerZ, warningDistance, warningTime, dimensionTeleportBoundary);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public WorldBorder withWarningTime(int warningTime) {
        // Returns a value to the caller
        return new WorldBorder(diameter, centerX, centerZ, warningDistance, warningTime, dimensionTeleportBoundary);
    // End of a block/expression
    }

    /**
     * Used to know if a position is located inside the world border or not.
     *
     * @param point the point to check
     * @return true if {@code position} is inside the world border, false otherwise
     */
    // Start of a method/block
    public boolean inBounds(Point point) {
        // Assigns a value
        double radius = diameter / 2;
        // Returns a value to the caller
        return point.x() <= centerX + radius && point.x() >= centerX - radius &&
                // Calls a method
                point.z() <= centerZ + radius && point.z() >= centerZ - radius;
    // End of a block/expression
    }

    /**
     * Used to know if an entity is located inside the world border or not.
     *
     * @param entity the entity to check
     * @return true if {@code entity} is inside the world border, false otherwise
     */
    // Start of a method/block
    public boolean inBounds(Entity entity) {
        // Returns a value to the caller
        return inBounds(entity.getPosition());
    // End of a block/expression
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
    // Start of a method/block
    public InitializeWorldBorderPacket createInitializePacket(double targetDiameter, long transitionTime) {
        // Returns a value to the caller
        return new InitializeWorldBorderPacket(centerX, centerZ, diameter, targetDiameter, transitionTime, dimensionTeleportBoundary, warningTime, warningDistance);
    // End of a block/expression
    }

    /**
     * Creates a {@link WorldBorderSizePacket} which dictates the origin of the world border.
     *
     * @return the {@link WorldBorderSizePacket} with the center values of this world border
     */
    // Start of a method/block
    public WorldBorderCenterPacket createCenterPacket() {
        // Returns a value to the caller
        return new WorldBorderCenterPacket(centerX, centerZ);
    // End of a block/expression
    }

    /**
     * Creates a {@link WorldBorderLerpSizePacket} which lerps the border from its current
     * diameter to the target diameter over the given transition time.
     *
     * @param targetDiameter the final diameter of the border after this transition
     * @param transitionTime the transition time in milliseconds for this lerp
     * @return               the {@link WorldBorderLerpSizePacket} representing this lerp
     */
    // Start of a method/block
    public WorldBorderLerpSizePacket createLerpSizePacket(double targetDiameter, long transitionTime) {
        // Returns a value to the caller
        return new WorldBorderLerpSizePacket(diameter, targetDiameter, transitionTime);
    // End of a block/expression
    }

    /**
     * Creates a {@link WorldBorderSizePacket} with this world border's diameter.
     *
     * @return the {@link WorldBorderSizePacket} with this world border's diameter
     */
    // Start of a method/block
    public WorldBorderSizePacket createSizePacket() {
        // Returns a value to the caller
        return new WorldBorderSizePacket(diameter);
    // End of a block/expression
    }

    /**
     * Creates a {@link WorldBorderWarningDelayPacket} with this world border's warning time
     *
     * @return the {@link WorldBorderWarningDelayPacket} with this world border's warning time
     */
    // Start of a method/block
    public WorldBorderWarningDelayPacket createWarningDelayPacket() {
        // Returns a value to the caller
        return new WorldBorderWarningDelayPacket(warningTime);
    // End of a block/expression
    }

    /**
     * Creates a {@link WorldBorderWarningReachPacket} with this world border's warning distance
     *
     * @return the {@link WorldBorderWarningReachPacket} with this world border's warning distance
     */
    // Start of a method/block
    public WorldBorderWarningReachPacket createWarningReachPacket() {
        // Returns a value to the caller
        return new WorldBorderWarningReachPacket(warningDistance);
    // End of a block/expression
    }
// End of a block/expression
}
