// Package declaration for this file
package net.minestom.server.utils.block;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.Entity;

// Import of a required class
import java.util.ArrayDeque;
// Import of a required class
import java.util.Iterator;
// Import of a required class
import java.util.NoSuchElementException;

/**
 * This class performs ray tracing and iterates along blocks on a line
 */
// Type declaration (class/interface/enum/record)
public class BlockIterator implements Iterator<Point> {
    // Assigns a value
    private final short[] signums = new short[3];
    // Code statement
    private Vec end;
    // Code statement
    private boolean smooth;

    // Assigns a value
    private boolean foundEnd = false;

    //length of ray from current position to next x or y-side
    // Code statement
    double sideDistX;
    // Code statement
    double sideDistY;
    // Code statement
    double sideDistZ;

    //length of ray from one x or y-side to next x or y-side
    // Code statement
    private double deltaDistX;
    // Code statement
    private double deltaDistY;
    // Code statement
    private double deltaDistZ;

    //which box of the map we're in
    // Code statement
    int mapX;
    // Code statement
    int mapY;
    // Code statement
    int mapZ;

    // Calls a method
    private final ArrayDeque<Point> extraPoints = new ArrayDeque<>();

    /**
     * Constructs the BlockIterator.
     * <p>
     * This considers all blocks as 1x1x1 in size.
     *
     * @param start       A Vector giving the initial position for the trace
     * @param direction   A Vector pointing in the direction for the trace
     * @param yOffset     The trace begins vertically offset from the start vector
     *                    by this value
     * @param smooth      A boolean indicating whether the cast should be smooth.
     *                    Smooth casts will only include one block when intersecting multiple axis lines.
     * @param maxDistance This is the maximum distance in blocks for the
     *                    trace. Setting this value above 140 may lead to problems with
     *                    unloaded chunks. A value of 0 indicates no limit
     */
    // Start of a method/block
    public BlockIterator(Vec start, Vec direction, double yOffset, double maxDistance, boolean smooth) {
        // Calls a method
        reset(start, direction, yOffset, maxDistance, smooth);
    // End of a block/expression
    }

    // Code statement
    public BlockIterator() {}

    // Start of a method/block
    public void reset(Vec start, Vec direction, double yOffset, double maxDistance, boolean smooth) {
        // Calls a method
        extraPoints.clear();
        // Assigns a value
        foundEnd = false;

        // Calls a method
        start = start.add(0, yOffset, 0);

        // Branch: checks a condition
        if (maxDistance != 0) end = start.add(direction.normalize().mul(maxDistance));
        // Alternative branch of the condition
        else end = null;

        // Branch: checks a condition
        if (direction.isZero()) this.foundEnd = true;

        // Access to the current/parent object
        this.smooth = smooth;

        // Calls a method
        Vec ray = direction.normalize();

        //which box of the map we're in
        // Calls a method
        mapX = start.blockX();
        // Calls a method
        mapY = start.blockY();
        // Calls a method
        mapZ = start.blockZ();

        // Calls a method
        signums[0] = (short) Math.signum(direction.x());
        // Calls a method
        signums[1] = (short) Math.signum(direction.y());
        // Calls a method
        signums[2] = (short) Math.signum(direction.z());

        // Calls a method
        deltaDistX = (ray.x() == 0) ? 1e30 : Math.abs(1 / ray.x());
        // Assigns a value
        deltaDistY = (ray.y() == 0) ? 1e30 : Math.abs(1 / ray.y());        // Find grid intersections for x, y, z
        // Assigns a value
        deltaDistZ = (ray.z() == 0) ? 1e30 : Math.abs(1 / ray.z());        // This works by calculating and storing the distance to the next grid intersection on the x, y and z axis

        //calculate step and initial sideDist
        // Branch: checks a condition
        if (ray.x() < 0) sideDistX = (start.x() - mapX) * deltaDistX;
        // Branch: checks a condition
        else if (ray.x() > 0) sideDistX = (mapX + signums[0] - start.x()) * deltaDistX;
        // Alternative branch of the condition
        else sideDistX = Double.MAX_VALUE;

        // Branch: checks a condition
        if (ray.y() < 0) sideDistY = (start.y() - mapY) * deltaDistY;
        // Branch: checks a condition
        else if (ray.y() > 0) sideDistY = (mapY + signums[1] - start.y()) * deltaDistY;
        // Alternative branch of the condition
        else sideDistY = Double.MAX_VALUE;

        // Branch: checks a condition
        if (ray.z() < 0) sideDistZ = (start.z() - mapZ) * deltaDistZ;
        // Branch: checks a condition
        else if (ray.z() > 0) sideDistZ = (mapZ + signums[2] - start.z()) * deltaDistZ;
        // Alternative branch of the condition
        else sideDistZ = Double.MAX_VALUE;
    // End of a block/expression
    }

    /**
     * Constructs the BlockIterator.
     * <p>
     * This considers all blocks as 1x1x1 in size.
     *
     * @param start       A Vector giving the initial position for the trace
     * @param direction   A Vector pointing in the direction for the trace
     * @param yOffset     The trace begins vertically offset from the start vector
     *                    by this value
     * @param maxDistance This is the maximum distance in blocks for the
     *                    trace. Setting this value above 140 may lead to problems with
     *                    unloaded chunks. A value of 0 indicates no limit
     */
    // Start of a method/block
    public BlockIterator(Vec start, Vec direction, double yOffset, double maxDistance) {
        // Calls a method
        this(start, direction, yOffset, maxDistance, false);
    // End of a block/expression
    }

    /**
     * Constructs the BlockIterator.
     * <p>
     * This considers all blocks as 1x1x1 in size.
     *
     * @param pos         The position for the start of the ray trace
     * @param yOffset     The trace begins vertically offset from the start vector
     *                    by this value
     * @param maxDistance This is the maximum distance in blocks for the
     *                    trace. Setting this value above 140 may lead to problems with
     *                    unloaded chunks. A value of 0 indicates no limit
     */

    // Start of a method/block
    public BlockIterator(Pos pos, double yOffset, int maxDistance) {
        // Calls a method
        this(pos.asVec(), pos.direction(), yOffset, maxDistance, false);
    // End of a block/expression
    }

    /**
     * Constructs the BlockIterator.
     * <p>
     * This considers all blocks as 1x1x1 in size.
     *
     * @param pos     The position for the start of the ray trace
     * @param yOffset The trace begins vertically offset from the start vector
     *                by this value
     */

    // Start of a method/block
    public BlockIterator(Pos pos, double yOffset) {
        // Calls a method
        this(pos.asVec(), pos.direction(), yOffset, 0, false);
    // End of a block/expression
    }

    /**
     * Constructs the BlockIterator.
     * <p>
     * This considers all blocks as 1x1x1 in size.
     *
     * @param pos The position for the start of the ray trace
     */

    // Start of a method/block
    public BlockIterator(Pos pos) {
        // Calls a method
        this(pos, 0f);
    // End of a block/expression
    }

    /**
     * Constructs the BlockIterator.
     * <p>
     * This considers all blocks as 1x1x1 in size.
     *
     * @param entity      Information from the entity is used to set up the trace
     * @param maxDistance This is the maximum distance in blocks for the
     *                    trace. Setting this value above 140 may lead to problems with
     *                    unloaded chunks. A value of 0 indicates no limit
     */

    // Start of a method/block
    public BlockIterator(Entity entity, int maxDistance) {
        // Calls a method
        this(entity.getPosition(), entity.getEyeHeight(), maxDistance);
    // End of a block/expression
    }

    /**
     * Constructs the BlockIterator.
     * <p>
     * This considers all blocks as 1x1x1 in size.
     *
     * @param entity Information from the entity is used to set up the trace
     */

    // Start of a method/block
    public BlockIterator(Entity entity) {
        // Calls a method
        this(entity, 0);
    // End of a block/expression
    }

    /**
     * Returns true if the iteration has more elements
     */

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean hasNext() {
        // Returns a value to the caller
        return !foundEnd;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void remove() {
        // Throws an exception
        throw new UnsupportedOperationException("[BlockIterator] doesn't support block removal");
    // End of a block/expression
    }

    /**
     * Returns the next BlockPosition in the trace
     *
     * @return the next BlockPosition in the trace
     */

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Point next() {
        // Branch: checks a condition
        if (foundEnd) throw new NoSuchElementException();
        // Branch: checks a condition
        if (!extraPoints.isEmpty()) {
            // Calls a method
            var res = extraPoints.poll();
            // Branch: checks a condition
            if (end != null && res.sameBlock(end)) foundEnd = true;
            // Returns a value to the caller
            return res;
        // End of a block/expression
        }

        // Calls a method
        var current = new Vec(mapX, mapY, mapZ);
        // Branch: checks a condition
        if (end != null && current.sameBlock(end)) foundEnd = true;

        // Calls a method
        double closest = Math.min(sideDistX, Math.min(sideDistY, sideDistZ));
        // Assigns a value
        boolean needsX = sideDistX - closest < 1e-10 && signums[0] != 0;
        // Assigns a value
        boolean needsY = sideDistY - closest < 1e-10 && signums[1] != 0;
        // Assigns a value
        boolean needsZ = sideDistZ - closest < 1e-10 && signums[2] != 0;

        // Branch: checks a condition
        if (needsZ) {
            // Code statement
            sideDistZ += deltaDistZ;
            // Code statement
            mapZ += signums[2];
        // End of a block/expression
        }

        // Branch: checks a condition
        if (needsX) {
            // Code statement
            sideDistX += deltaDistX;
            // Code statement
            mapX += signums[0];
        // End of a block/expression
        }

        // Branch: checks a condition
        if (needsY) {
            // Code statement
            sideDistY += deltaDistY;
            // Code statement
            mapY += signums[1];
        // End of a block/expression
        }

        // Branch: checks a condition
        if (needsX && needsY && needsZ) {
            // Calls a method
            extraPoints.add(new Vec(signums[0] + current.x(), signums[1] + current.y(), current.z()));
            // Branch: checks a condition
            if (smooth) return current;

            // Calls a method
            extraPoints.add(new Vec(current.x(), signums[1] + current.y(), signums[2] + current.z()));
            // Calls a method
            extraPoints.add(new Vec(signums[0] + current.x(), current.y(), signums[2] + current.z()));

            // Calls a method
            extraPoints.add(new Vec(signums[0] + current.x(), current.y(), current.z()));
            // Calls a method
            extraPoints.add(new Vec(current.x(), signums[1] + current.y(), current.z()));
            // Calls a method
            extraPoints.add(new Vec(current.x(), current.y(), signums[2] + current.z()));
        // Branch: checks a condition
        } else if (needsX && needsY) {
            // Calls a method
            extraPoints.add(new Vec(signums[0] + current.x(), current.y(), current.z()));
            // Branch: checks a condition
            if (smooth) return current;
            // Calls a method
            extraPoints.add(new Vec(current.x(), signums[1] + current.y(), current.z()));
        // Branch: checks a condition
        } else if (needsX && needsZ) {
            // Calls a method
            extraPoints.add(new Vec(signums[0] + current.x(), current.y(), current.z()));
            // Branch: checks a condition
            if (smooth) return current;
            // Calls a method
            extraPoints.add(new Vec(current.x(), current.y(), signums[2] + current.z()));
        // Branch: checks a condition
        } else if (needsY && needsZ) {
            // Calls a method
            extraPoints.add(new Vec(current.x(), signums[1] + current.y(), current.z()));
            // Branch: checks a condition
            if (smooth) return current;
            // Calls a method
            extraPoints.add(new Vec(current.x(), current.y(), signums[2] + current.z()));
        // End of a block/expression
        }

        // Returns a value to the caller
        return current;
    // End of a block/expression
    }
// End of a block/expression
}
