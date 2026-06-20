// Déclaration du paquet de ce fichier
package net.minestom.server.collision;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityPose;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Iterator;

/**
 * See <a href="https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Entity_metadata#Entities">the entity bounding box list</a>.
 */
// Déclaration de type (classe/interface/enum/record)
public record BoundingBox(Vec relativeStart, Vec relativeEnd) implements Shape {
    // Appelle une méthode
    private static final BoundingBox SLEEPING = new BoundingBox(0.2, 0.2, 0.2);
    // Appelle une méthode
    private static final BoundingBox SNEAKING = new BoundingBox(0.6, 1.5, 0.6);
    // Appelle une méthode
    private static final BoundingBox SMALL = new BoundingBox(0.6, 0.6, 0.6);

    // Appelle une méthode
    final static BoundingBox ZERO = new BoundingBox(Vec.ZERO, Vec.ZERO);

    // Début d'une méthode/d'un bloc
    public BoundingBox(double width, double height, double depth, Point offset) {
        // Appelle une méthode
        this(offset.asVec(), new Vec(width, height, depth).add(offset));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BoundingBox(double width, double height, double depth) {
        // Appelle une méthode
        this(width, height, depth, new Vec(-width / 2, 0, -depth / 2));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isOccluded(Shape shape, BlockFace face) {
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean intersectBox(Point positionRelative, BoundingBox boundingBox) {
        // Renvoie une valeur à l'appelant
        return (minX() + positionRelative.x() <= boundingBox.maxX() - Vec.EPSILON / 2 && maxX() + positionRelative.x() >= boundingBox.minX() + Vec.EPSILON / 2) &&
                // Instruction de code
                (minY() + positionRelative.y() <= boundingBox.maxY() - Vec.EPSILON / 2 && maxY() + positionRelative.y() >= boundingBox.minY() + Vec.EPSILON / 2) &&
                // Appelle une méthode
                (minZ() + positionRelative.z() <= boundingBox.maxZ() - Vec.EPSILON / 2 && maxZ() + positionRelative.z() >= boundingBox.minZ() + Vec.EPSILON / 2);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean intersectBoxSwept(Point rayStart, Point rayDirection, Point shapePos, BoundingBox moving, SweepResult finalResult) {
        // Embranchement : vérifie une condition
        if (RayUtils.BoundingBoxIntersectionCheck(moving, rayStart, rayDirection, this, shapePos, finalResult)) {
            // Appelle une méthode
            finalResult.collidedPositionX = rayStart.x() + rayDirection.x() * finalResult.res;
            // Appelle une méthode
            finalResult.collidedPositionY = rayStart.y() + rayDirection.y() * finalResult.res;
            // Appelle une méthode
            finalResult.collidedPositionZ = rayStart.z() + rayDirection.z() * finalResult.res;
            // Appelle une méthode
            finalResult.collidedShapeX = shapePos.x();
            // Appelle une méthode
            finalResult.collidedShapeY = shapePos.y();
            // Appelle une méthode
            finalResult.collidedShapeZ = shapePos.z();
            // Affecte une valeur
            finalResult.collidedShape = this;
            // Renvoie une valeur à l'appelant
            return true;
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean boundingBoxRayIntersectionCheck(Vec start, Vec direction, Pos position) {
        // Renvoie une valeur à l'appelant
        return RayUtils.BoundingBoxRayIntersectionCheck(start, direction, this, position);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new {@link BoundingBox} with an expanded size.
     *
     * @param x the X offset
     * @param y the Y offset
     * @param z the Z offset
     * @return a new {@link BoundingBox} expanded
     */
    // Début d'une méthode/d'un bloc
    public BoundingBox expand(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new BoundingBox(width() + x, height() + y, depth() + z);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new {@link BoundingBox} with a contracted size.
     *
     * @param x the X offset
     * @param y the Y offset
     * @param z the Z offset
     * @return a new bounding box contracted
     */
    // Début d'une méthode/d'un bloc
    public BoundingBox contract(double x, double y, double z) {
        // Renvoie une valeur à l'appelant
        return new BoundingBox(width() - x, height() - y, depth() - z);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new {@link BoundingBox} with an offset.
     *
     * @param offset the offset
     * @return a new bounding box with an offset.
     */
    // Début d'une méthode/d'un bloc
    public BoundingBox withOffset(Point offset) {
        // Renvoie une valeur à l'appelant
        return new BoundingBox(width(), height(), depth(), offset);
    // Fin d'un bloc/d'une expression
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
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BoundingBox grow(double x, double y, double z) {
        // Appelle une méthode
        final double newWidth = width() + x, newDepth = depth() + z;
        // Appelle une méthode
        final Vec centerOffset = new Vec(-newWidth / 2, minY() - y / 2, -newDepth / 2);
        // Renvoie une valeur à l'appelant
        return new BoundingBox(newWidth, height() + y, newDepth, centerOffset);
    // Fin d'un bloc/d'une expression
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
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public BoundingBox growSymmetrically(double x, double y, double z) {
        // Double all amounts to make it symmetric conformance to xyz
        // Renvoie une valeur à l'appelant
        return grow(x * 2, y * 2, z * 2);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public double width() {
        // Renvoie une valeur à l'appelant
        return relativeEnd.x() - relativeStart.x();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public double height() {
        // Renvoie une valeur à l'appelant
        return relativeEnd.y() - relativeStart.y();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public double depth() {
        // Renvoie une valeur à l'appelant
        return relativeEnd.z() - relativeStart.z();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public double minX() {
        // Renvoie une valeur à l'appelant
        return relativeStart.x();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public double maxX() {
        // Renvoie une valeur à l'appelant
        return relativeEnd.x();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public double minY() {
        // Renvoie une valeur à l'appelant
        return relativeStart.y();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public double maxY() {
        // Renvoie une valeur à l'appelant
        return relativeEnd.y();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public double minZ() {
        // Renvoie une valeur à l'appelant
        return relativeStart.z();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public double maxZ() {
        // Renvoie une valeur à l'appelant
        return relativeEnd.z();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum AxisMask {
        // Instruction de code
        X,
        // Instruction de code
        Y,
        // Instruction de code
        Z,
        // Instruction de code
        NONE
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public PointIterator getBlocks(Point point) {
        // Renvoie une valeur à l'appelant
        return new PointIterator(this, point, AxisMask.NONE, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public PointIterator getBlocks(Point point, AxisMask axisMask, double axis) {
        // Renvoie une valeur à l'appelant
        return new PointIterator(this, point, axisMask, axis);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public static class MutablePoint {
        // Instruction de code
        double x, y, z;

        // Début d'une méthode/d'un bloc
        public void set(double x, double y, double z) {
            // Accès à l'objet courant/parent
            this.x = x;
            // Accès à l'objet courant/parent
            this.y = y;
            // Accès à l'objet courant/parent
            this.z = z;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public double x() {
            // Renvoie une valeur à l'appelant
            return x;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public double y() {
            // Renvoie une valeur à l'appelant
            return y;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public double z() {
            // Renvoie une valeur à l'appelant
            return z;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public int blockX() {
            // Renvoie une valeur à l'appelant
            return (int) Math.floor(x);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public int blockY() {
            // Renvoie une valeur à l'appelant
            return (int) Math.floor(y);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public int blockZ() {
            // Renvoie une valeur à l'appelant
            return (int) Math.floor(z);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public static class PointIterator implements Iterator<MutablePoint> {
        // Instruction de code
        private double sx, sy, sz;
        // Instruction de code
        double x, y, z;
        // Instruction de code
        private double minX, minY, minZ, maxX, maxY, maxZ;
        // Appelle une méthode
        private final MutablePoint point = new MutablePoint();

        // Début d'une méthode/d'un bloc
        public PointIterator() {
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public PointIterator(BoundingBox boundingBox, Point p, AxisMask axisMask, double axis) {
            // Appelle une méthode
            reset(boundingBox, p, axisMask, axis);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public void reset(BoundingBox boundingBox, double pointX, double pointY, double pointZ, AxisMask axisMask, int axis) {
            // Appelle une méthode
            minX = (int) Math.floor(boundingBox.minX() + pointX);
            // Appelle une méthode
            minY = (int) Math.floor(boundingBox.minY() + pointY);
            // Appelle une méthode
            minZ = (int) Math.floor(boundingBox.minZ() + pointZ);
            // Appelle une méthode
            maxX = (int) Math.floor(boundingBox.maxX() + pointX);
            // Appelle une méthode
            maxY = (int) Math.floor(boundingBox.maxY() + pointY);
            // Appelle une méthode
            maxZ = (int) Math.floor(boundingBox.maxZ() + pointZ);

            // Affecte une valeur
            x = minX;
            // Affecte une valeur
            y = minY;
            // Affecte une valeur
            z = minZ;

            // Appelle une méthode
            sx = boundingBox.minX() + pointX - minX;
            // Appelle une méthode
            sy = boundingBox.minY() + pointY - minY;
            // Appelle une méthode
            sz = boundingBox.minZ() + pointZ - minZ;

            // Embranchement : vérifie une condition
            if (axisMask == AxisMask.X) {
                // Affecte une valeur
                x = axis + pointX;
                // Affecte une valeur
                minX = x;
                // Affecte une valeur
                maxX = x;
            // Embranchement : vérifie une condition
            } else if (axisMask == AxisMask.Y) {
                // Affecte une valeur
                y = axis + pointY;
                // Affecte une valeur
                minY = y;
                // Affecte une valeur
                maxY = y;
            // Embranchement : vérifie une condition
            } else if (axisMask == AxisMask.Z) {
                // Affecte une valeur
                z = axis + pointZ;
                // Affecte une valeur
                minZ = z;
                // Affecte une valeur
                maxZ = z;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public void reset(BoundingBox boundingBox, Point p, AxisMask axisMask, double axis) {
            // Appelle une méthode
            reset(boundingBox, p.x(), p.y(), p.z(), axisMask, (int) axis);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public void reset(BoundingBox boundingBox, double x, double y, double z, AxisMask axisMask, double axis) {
            // Appelle une méthode
            reset(boundingBox, x, y, z, axisMask, (int) axis);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean hasNext() {
            // Renvoie une valeur à l'appelant
            return x <= maxX && y <= maxY && z <= maxZ;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public MutablePoint next() {
            // Appelle une méthode
            point.set(x + sx, y + sy, z + sz);

            // Instruction de code
            x++;
            // Embranchement : vérifie une condition
            if (x > maxX) {
                // Affecte une valeur
                x = minX;
                // Instruction de code
                y++;
                // Embranchement : vérifie une condition
                if (y > maxY) {
                    // Affecte une valeur
                    y = minY;
                    // Instruction de code
                    z++;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return point;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static @Nullable BoundingBox fromPose(EntityPose pose) {
        // Renvoie une valeur à l'appelant
        return switch (pose) {
            // Embranchement multiple (switch/case)
            case FALL_FLYING, SWIMMING, SPIN_ATTACK -> SMALL;
            // Embranchement multiple (switch/case)
            case SLEEPING, DYING -> SLEEPING;
            // Embranchement multiple (switch/case)
            case SNEAKING -> SNEAKING;
            // Embranchement multiple (switch/case)
            default -> null;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static BoundingBox fromPoints(Point a, Point b) {
        // Appelle une méthode
        Vec aVec = a.asVec();
        // Appelle une méthode
        Vec min = aVec.min(b);
        // Appelle une méthode
        Vec max = aVec.max(b);
        // Appelle une méthode
        Vec dimensions = max.sub(min);
        // Renvoie une valeur à l'appelant
        return new BoundingBox(dimensions.x(), dimensions.y(), dimensions.z(), min);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
