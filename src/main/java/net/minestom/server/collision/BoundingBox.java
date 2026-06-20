// Package declaration for this file
package net.minestom.server.collision;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.EntityPose;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Iterator;

/**
 * See <a href="https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Entity_metadata#Entities">the entity bounding box list</a>.
 */
// Type declaration (class/interface/enum/record)
public record BoundingBox(Vec relativeStart, Vec relativeEnd) implements Shape {
    // Calls a method
    private static final BoundingBox SLEEPING = new BoundingBox(0.2, 0.2, 0.2);
    // Calls a method
    private static final BoundingBox SNEAKING = new BoundingBox(0.6, 1.5, 0.6);
    // Calls a method
    private static final BoundingBox SMALL = new BoundingBox(0.6, 0.6, 0.6);

    // Calls a method
    final static BoundingBox ZERO = new BoundingBox(Vec.ZERO, Vec.ZERO);

    // Start of a method/block
    public BoundingBox(double width, double height, double depth, Point offset) {
        // Calls a method
        this(offset.asVec(), new Vec(width, height, depth).add(offset));
    // End of a block/expression
    }

    // Start of a method/block
    public BoundingBox(double width, double height, double depth) {
        // Calls a method
        this(width, height, depth, new Vec(-width / 2, 0, -depth / 2));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isOccluded(Shape shape, BlockFace face) {
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean intersectBox(Point positionRelative, BoundingBox boundingBox) {
        // Returns a value to the caller
        return (minX() + positionRelative.x() <= boundingBox.maxX() - Vec.EPSILON / 2 && maxX() + positionRelative.x() >= boundingBox.minX() + Vec.EPSILON / 2) &&
                // Code statement
                (minY() + positionRelative.y() <= boundingBox.maxY() - Vec.EPSILON / 2 && maxY() + positionRelative.y() >= boundingBox.minY() + Vec.EPSILON / 2) &&
                // Calls a method
                (minZ() + positionRelative.z() <= boundingBox.maxZ() - Vec.EPSILON / 2 && maxZ() + positionRelative.z() >= boundingBox.minZ() + Vec.EPSILON / 2);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean intersectBoxSwept(Point rayStart, Point rayDirection, Point shapePos, BoundingBox moving, SweepResult finalResult) {
        // Branch: checks a condition
        if (RayUtils.BoundingBoxIntersectionCheck(moving, rayStart, rayDirection, this, shapePos, finalResult)) {
            // Calls a method
            finalResult.collidedPositionX = rayStart.x() + rayDirection.x() * finalResult.res;
            // Calls a method
            finalResult.collidedPositionY = rayStart.y() + rayDirection.y() * finalResult.res;
            // Calls a method
            finalResult.collidedPositionZ = rayStart.z() + rayDirection.z() * finalResult.res;
            // Calls a method
            finalResult.collidedShapeX = shapePos.x();
            // Calls a method
            finalResult.collidedShapeY = shapePos.y();
            // Calls a method
            finalResult.collidedShapeZ = shapePos.z();
            // Assigns a value
            finalResult.collidedShape = this;
            // Returns a value to the caller
            return true;
        // End of a block/expression
        }

        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean boundingBoxRayIntersectionCheck(Vec start, Vec direction, Pos position) {
        // Returns a value to the caller
        return RayUtils.BoundingBoxRayIntersectionCheck(start, direction, this, position);
    // End of a block/expression
    }

    /**
     * Creates a new {@link BoundingBox} with an expanded size.
     *
     * @param x the X offset
     * @param y the Y offset
     * @param z the Z offset
     * @return a new {@link BoundingBox} expanded
     */
    // Start of a method/block
    public BoundingBox expand(double x, double y, double z) {
        // Returns a value to the caller
        return new BoundingBox(width() + x, height() + y, depth() + z);
    // End of a block/expression
    }

    /**
     * Creates a new {@link BoundingBox} with a contracted size.
     *
     * @param x the X offset
     * @param y the Y offset
     * @param z the Z offset
     * @return a new bounding box contracted
     */
    // Start of a method/block
    public BoundingBox contract(double x, double y, double z) {
        // Returns a value to the caller
        return new BoundingBox(width() - x, height() - y, depth() - z);
    // End of a block/expression
    }

    /**
     * Creates a new {@link BoundingBox} with an offset.
     *
     * @param offset the offset
     * @return a new bounding box with an offset.
     */
    // Start of a method/block
    public BoundingBox withOffset(Point offset) {
        // Returns a value to the caller
        return new BoundingBox(width(), height(), depth(), offset);
    // End of a block/expression
    }

    /**
     * Creates a new {@link BoundingBox} with an expanded size from its center in every plane.
     * <p>
     * Equivalent to an expansion and an offset where the point is the three-axis offset.
     * Particularly useful when you already use centered and aligned minY=0 position.
     *
     * @param x the X offset, this will be applied on both sides
     * @param y the Y offset, this will be applied on both sides
     * @param z the Z offset, this will be applied on both sides
     * @return a new {@link BoundingBox} expanded and centered from the original minY
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public BoundingBox grow(double x, double y, double z) {
        // Calls a method
        final double newWidth = width() + x, newDepth = depth() + z;
        // Calls a method
        final Vec centerOffset = new Vec(-newWidth / 2, minY() - y / 2, -newDepth / 2);
        // Returns a value to the caller
        return new BoundingBox(newWidth, height() + y, newDepth, centerOffset);
    // End of a block/expression
    }

    /**
     * Creates a new {@link BoundingBox} with an expanded size from its center in every plane.
     * <p>
     * Equivalent to a double expansion and an offset where the point is the three-axis offset.
     * Particularly useful when you already use centered and aligned minY=0 position.
     *
     * @param x the X offset, this will be applied on both sides
     * @param y the Y offset, this will be applied on both sides
     * @param z the Z offset, this will be applied on both sides
     * @return a new {@link BoundingBox} expanded and centered from the original minY
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public BoundingBox growSymmetrically(double x, double y, double z) {
        // Double all amounts to make it symmetric conformance to xyz
        // Returns a value to the caller
        return grow(x * 2, y * 2, z * 2);
    // End of a block/expression
    }

    // Start of a method/block
    public double width() {
        // Returns a value to the caller
        return relativeEnd.x() - relativeStart.x();
    // End of a block/expression
    }

    // Start of a method/block
    public double height() {
        // Returns a value to the caller
        return relativeEnd.y() - relativeStart.y();
    // End of a block/expression
    }

    // Start of a method/block
    public double depth() {
        // Returns a value to the caller
        return relativeEnd.z() - relativeStart.z();
    // End of a block/expression
    }

    // Start of a method/block
    public double minX() {
        // Returns a value to the caller
        return relativeStart.x();
    // End of a block/expression
    }

    // Start of a method/block
    public double maxX() {
        // Returns a value to the caller
        return relativeEnd.x();
    // End of a block/expression
    }

    // Start of a method/block
    public double minY() {
        // Returns a value to the caller
        return relativeStart.y();
    // End of a block/expression
    }

    // Start of a method/block
    public double maxY() {
        // Returns a value to the caller
        return relativeEnd.y();
    // End of a block/expression
    }

    // Start of a method/block
    public double minZ() {
        // Returns a value to the caller
        return relativeStart.z();
    // End of a block/expression
    }

    // Start of a method/block
    public double maxZ() {
        // Returns a value to the caller
        return relativeEnd.z();
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum AxisMask {
        // Code statement
        X,
        // Code statement
        Y,
        // Code statement
        Z,
        // Code statement
        NONE
    // End of a block/expression
    }

    // Start of a method/block
    public PointIterator getBlocks(Point point) {
        // Returns a value to the caller
        return new PointIterator(this, point, AxisMask.NONE, 0);
    // End of a block/expression
    }

    // Start of a method/block
    public PointIterator getBlocks(Point point, AxisMask axisMask, double axis) {
        // Returns a value to the caller
        return new PointIterator(this, point, axisMask, axis);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public static class MutablePoint {
        // Code statement
        double x, y, z;

        // Start of a method/block
        public void set(double x, double y, double z) {
            // Access to the current/parent object
            this.x = x;
            // Access to the current/parent object
            this.y = y;
            // Access to the current/parent object
            this.z = z;
        // End of a block/expression
        }

        // Start of a method/block
        public double x() {
            // Returns a value to the caller
            return x;
        // End of a block/expression
        }

        // Start of a method/block
        public double y() {
            // Returns a value to the caller
            return y;
        // End of a block/expression
        }

        // Start of a method/block
        public double z() {
            // Returns a value to the caller
            return z;
        // End of a block/expression
        }

        // Start of a method/block
        public int blockX() {
            // Returns a value to the caller
            return (int) Math.floor(x);
        // End of a block/expression
        }

        // Start of a method/block
        public int blockY() {
            // Returns a value to the caller
            return (int) Math.floor(y);
        // End of a block/expression
        }

        // Start of a method/block
        public int blockZ() {
            // Returns a value to the caller
            return (int) Math.floor(z);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public static class PointIterator implements Iterator<MutablePoint> {
        // Code statement
        private double sx, sy, sz;
        // Code statement
        double x, y, z;
        // Code statement
        private double minX, minY, minZ, maxX, maxY, maxZ;
        // Calls a method
        private final MutablePoint point = new MutablePoint();

        // Start of a method/block
        public PointIterator() {
        // End of a block/expression
        }

        // Start of a method/block
        public PointIterator(BoundingBox boundingBox, Point p, AxisMask axisMask, double axis) {
            // Calls a method
            reset(boundingBox, p, axisMask, axis);
        // End of a block/expression
        }

        // Start of a method/block
        public void reset(BoundingBox boundingBox, double pointX, double pointY, double pointZ, AxisMask axisMask, int axis) {
            // Calls a method
            minX = (int) Math.floor(boundingBox.minX() + pointX);
            // Calls a method
            minY = (int) Math.floor(boundingBox.minY() + pointY);
            // Calls a method
            minZ = (int) Math.floor(boundingBox.minZ() + pointZ);
            // Calls a method
            maxX = (int) Math.floor(boundingBox.maxX() + pointX);
            // Calls a method
            maxY = (int) Math.floor(boundingBox.maxY() + pointY);
            // Calls a method
            maxZ = (int) Math.floor(boundingBox.maxZ() + pointZ);

            // Assigns a value
            x = minX;
            // Assigns a value
            y = minY;
            // Assigns a value
            z = minZ;

            // Calls a method
            sx = boundingBox.minX() + pointX - minX;
            // Calls a method
            sy = boundingBox.minY() + pointY - minY;
            // Calls a method
            sz = boundingBox.minZ() + pointZ - minZ;

            // Branch: checks a condition
            if (axisMask == AxisMask.X) {
                // Assigns a value
                x = axis + pointX;
                // Assigns a value
                minX = x;
                // Assigns a value
                maxX = x;
            // Branch: checks a condition
            } else if (axisMask == AxisMask.Y) {
                // Assigns a value
                y = axis + pointY;
                // Assigns a value
                minY = y;
                // Assigns a value
                maxY = y;
            // Branch: checks a condition
            } else if (axisMask == AxisMask.Z) {
                // Assigns a value
                z = axis + pointZ;
                // Assigns a value
                minZ = z;
                // Assigns a value
                maxZ = z;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Start of a method/block
        public void reset(BoundingBox boundingBox, Point p, AxisMask axisMask, double axis) {
            // Calls a method
            reset(boundingBox, p.x(), p.y(), p.z(), axisMask, (int) axis);
        // End of a block/expression
        }

        // Start of a method/block
        public void reset(BoundingBox boundingBox, double x, double y, double z, AxisMask axisMask, double axis) {
            // Calls a method
            reset(boundingBox, x, y, z, axisMask, (int) axis);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean hasNext() {
            // Returns a value to the caller
            return x <= maxX && y <= maxY && z <= maxZ;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public MutablePoint next() {
            // Calls a method
            point.set(x + sx, y + sy, z + sz);

            // Code statement
            x++;
            // Branch: checks a condition
            if (x > maxX) {
                // Assigns a value
                x = minX;
                // Code statement
                y++;
                // Branch: checks a condition
                if (y > maxY) {
                    // Assigns a value
                    y = minY;
                    // Code statement
                    z++;
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Returns a value to the caller
            return point;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static @Nullable BoundingBox fromPose(EntityPose pose) {
        // Returns a value to the caller
        return switch (pose) {
            // Multiple branching (switch/case)
            case FALL_FLYING, SWIMMING, SPIN_ATTACK -> SMALL;
            // Multiple branching (switch/case)
            case SLEEPING, DYING -> SLEEPING;
            // Multiple branching (switch/case)
            case SNEAKING -> SNEAKING;
            // Multiple branching (switch/case)
            default -> null;
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    public static BoundingBox fromPoints(Point a, Point b) {
        // Calls a method
        Vec aVec = a.asVec();
        // Calls a method
        Vec min = aVec.min(b);
        // Calls a method
        Vec max = aVec.max(b);
        // Calls a method
        Vec dimensions = max.sub(min);
        // Returns a value to the caller
        return new BoundingBox(dimensions.x(), dimensions.y(), dimensions.z(), min);
    // End of a block/expression
    }
// End of a block/expression
}
