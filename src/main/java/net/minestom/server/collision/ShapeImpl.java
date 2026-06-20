// Package declaration for this file
package net.minestom.server.collision;

// Import of a required class
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
// Import of a required class
import it.unimi.dsi.fastutil.doubles.DoubleList;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import org.jetbrains.annotations.Unmodifiable;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.regex.Matcher;
// Import of a required class
import java.util.regex.Pattern;

// Type declaration (class/interface/enum/record)
public record ShapeImpl(ShapeData shapeData, OcclusionData occlusionData) implements Shape {
    // Calls a method
    private static final Pattern PATTERN = Pattern.compile("\\d.\\d+", Pattern.MULTILINE);

    // Type declaration (class/interface/enum/record)
    record ShapeData(List<BoundingBox> boundingBoxes,
                     // Code statement
                     Point relativeStart, Point relativeEnd,
                     // Start of a method/block
                     byte fullFaces) {
        // Start of a method/block
        public ShapeData {
            // Calls a method
            boundingBoxes = List.copyOf(boundingBoxes);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record OcclusionData(byte blockOcclusion, byte airOcclusion, byte lightEmission) {}

    // Start of a method/block
    public ShapeImpl withLightEmission(byte lightEmission) {
        // Returns a value to the caller
        return new ShapeImpl(this.shapeData, new OcclusionData(this.occlusionData.blockOcclusion(), this.occlusionData.airOcclusion, lightEmission));
    // End of a block/expression
    }

    /**
     * Computes the occlusion for a given face.
     *
     * @param covering The rectangle set to check for covering.
     * @return 0 if face is not covered, 1 if face is covered partially, 2 if face is fully covered.
     */
    // Start of a method/block
    private static byte isFaceCovered(List<Rectangle> covering) {
        // Branch: checks a condition
        if (covering.isEmpty()) return 0;
        // Calls a method
        Rectangle r = new Rectangle(0, 0, 1, 1);
        // Calls a method
        List<Rectangle> toCover = new ArrayList<>();
        // Calls a method
        toCover.add(r);
        // Loop: repeats a block
        for (Rectangle rect : covering) {
            // Calls a method
            List<Rectangle> nextCovering = new ArrayList<>();
            // Loop: repeats a block
            for (Rectangle toCoverRect : toCover) {
                // Calls a method
                List<Rectangle> remaining = getRemaining(rect, toCoverRect);
                // Calls a method
                nextCovering.addAll(remaining);
            // End of a block/expression
            }
            // Assigns a value
            toCover = nextCovering;
            // Branch: checks a condition
            if (toCover.isEmpty()) return 2;
        // End of a block/expression
        }
        // Returns a value to the caller
        return 1;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Point relativeStart() {
        // Returns a value to the caller
        return shapeData.relativeStart;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Point relativeEnd() {
        // Returns a value to the caller
        return shapeData.relativeEnd;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isOccluded(Shape shape, BlockFace face) {
        // Assigns a value
        final OcclusionData occlusionData = this.occlusionData;
        // Calls a method
        final OcclusionData otherOcclusionData = ((ShapeImpl) shape).occlusionData;

        // Calls a method
        final boolean hasBlockOcclusion = (((occlusionData.blockOcclusion >> face.ordinal()) & 1) == 1);
        // Calls a method
        final boolean hasBlockOcclusionOther = ((otherOcclusionData.blockOcclusion >> face.getOppositeFace().ordinal()) & 1) == 1;

        // Branch: checks a condition
        if (occlusionData.lightEmission > 0) return hasBlockOcclusionOther;

        // If either face is full, return true
        // Branch: checks a condition
        if (hasBlockOcclusion || hasBlockOcclusionOther) return true;

        // Calls a method
        final boolean hasAirOcclusion = (((occlusionData.airOcclusion >> face.ordinal()) & 1) == 1);
        // Calls a method
        final boolean hasAirOcclusionOther = ((otherOcclusionData.airOcclusion >> face.getOppositeFace().ordinal()) & 1) == 1;

        // If a single face is air, return false
        // Branch: checks a condition
        if (hasAirOcclusion || hasAirOcclusionOther) return false;

        // Assigns a value
        final ShapeData shapeData = this.shapeData;
        // Calls a method
        final ShapeData otherShapeData = ((ShapeImpl) shape).shapeData;

        // Comparing two partial faces. Computation needed
        // Calls a method
        List<Rectangle> allRectangles = computeOcclusionSet(face.getOppositeFace(), otherShapeData.boundingBoxes);
        // Calls a method
        allRectangles.addAll(computeOcclusionSet(face, shapeData.boundingBoxes));
        // Returns a value to the caller
        return isFaceCovered(allRectangles) == 2;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isFaceFull(BlockFace face) {
        // Returns a value to the caller
        return (((shapeData.fullFaces >> face.ordinal()) & 1) == 1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean intersectBox(Point position, BoundingBox boundingBox) {
        // Loop: repeats a block
        for (BoundingBox blockSection : shapeData.boundingBoxes) {
            // Branch: checks a condition
            if (boundingBox.intersectBox(position, blockSection)) return true;
        // End of a block/expression
        }
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Code statement
    public boolean intersectBoxSwept(Point rayStart, Point rayDirection,
                                     // Start of a method/block
                                     Point shapePos, BoundingBox moving, SweepResult finalResult) {
        // Assigns a value
        boolean hitBlock = false;
        // Loop: repeats a block
        for (BoundingBox blockSection : shapeData.boundingBoxes) {
            // Update final result if the temp result collision is sooner than the current final result
            // Branch: checks a condition
            if (RayUtils.BoundingBoxIntersectionCheck(moving, rayStart, rayDirection, blockSection, shapePos, finalResult)) {
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
                // Assigns a value
                hitBlock = true;
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return hitBlock;
    // End of a block/expression
    }

    /**
     * Gets the bounding boxes for this shape. There will be more than one bounds for more complex shapes e.g.
     * stairs.
     *
     * @return the bounding boxes for this shape
     */
    // Start of a method/block
    public @Unmodifiable List<BoundingBox> boundingBoxes() {
        // Returns a value to the caller
        return shapeData.boundingBoxes;
    // End of a block/expression
    }

    // Start of a method/block
    static ShapeImpl parseShapeFromRegistry(String shape, byte lightEmission) {
        // Calls a method
        BoundingBox[] boundingBoxes = parseRegistryBoundingBoxString(shape);
        // Calls a method
        final ShapeData shapeData = shapeData(List.of(boundingBoxes));
        // Calls a method
        final OcclusionData occlusionData = occlusionData(shapeData, lightEmission);
        // Returns a value to the caller
        return new ShapeImpl(shapeData, occlusionData);
    // End of a block/expression
    }

    // Start of a method/block
    static ShapeImpl emptyShape(byte lightEmission) {
        // Assigns a value
        BoundingBox[] boundingBoxes = new BoundingBox[0];
        // Calls a method
        final ShapeData shapeData = shapeData(List.of(boundingBoxes));
        // Calls a method
        final OcclusionData occlusionData = occlusionData(shapeData, lightEmission);
        // Returns a value to the caller
        return new ShapeImpl(shapeData, occlusionData);
    // End of a block/expression
    }

    // Start of a method/block
    private static BoundingBox[] parseRegistryBoundingBoxString(String str) {
        // Calls a method
        final Matcher matcher = PATTERN.matcher(str);
        // Calls a method
        DoubleList vals = new DoubleArrayList();
        // Loop: repeats a block
        while (matcher.find()) {
            // Calls a method
            double newVal = Double.parseDouble(matcher.group());
            // Calls a method
            vals.add(newVal);
        // End of a block/expression
        }
        // Calls a method
        final int count = vals.size() / 6;
        // Assigns a value
        BoundingBox[] boundingBoxes = new BoundingBox[count];
        // Loop: repeats a block
        for (int i = 0; i < count; ++i) {
            // Calls a method
            final double minX = vals.getDouble(0 + 6 * i);
            // Calls a method
            final double minY = vals.getDouble(1 + 6 * i);
            // Calls a method
            final double minZ = vals.getDouble(2 + 6 * i);

            // Calls a method
            final double boundXSize = vals.getDouble(3 + 6 * i) - minX;
            // Calls a method
            final double boundYSize = vals.getDouble(4 + 6 * i) - minY;
            // Calls a method
            final double boundZSize = vals.getDouble(5 + 6 * i) - minZ;

            // Calls a method
            final Vec min = new Vec(minX, minY, minZ);
            // Calls a method
            final Vec max = new Vec(minX + boundXSize, minY + boundYSize, minZ + boundZSize);
            // Calls a method
            final BoundingBox bb = new BoundingBox(min, max);
            // Calls a method
            assert bb.minX() == minX;
            // Calls a method
            assert bb.minY() == minY;
            // Calls a method
            assert bb.minZ() == minZ;
            // Assigns a value
            boundingBoxes[i] = bb;
        // End of a block/expression
        }
        // Returns a value to the caller
        return boundingBoxes;
    // End of a block/expression
    }

    // Start of a method/block
    private static ShapeData shapeData(List<BoundingBox> collisionBoundingBoxes) {
        // Find bounds of collision
        // Code statement
        Vec relativeStart;
        // Code statement
        Vec relativeEnd;
        // Branch: checks a condition
        if (!collisionBoundingBoxes.isEmpty()) {
            // Assigns a value
            double minX = 1, minY = 1, minZ = 1;
            // Assigns a value
            double maxX = 0, maxY = 0, maxZ = 0;
            // Loop: repeats a block
            for (BoundingBox blockSection : collisionBoundingBoxes) {
                // Min
                // Branch: checks a condition
                if (blockSection.minX() < minX) minX = blockSection.minX();
                // Branch: checks a condition
                if (blockSection.minY() < minY) minY = blockSection.minY();
                // Branch: checks a condition
                if (blockSection.minZ() < minZ) minZ = blockSection.minZ();
                // Max
                // Branch: checks a condition
                if (blockSection.maxX() > maxX) maxX = blockSection.maxX();
                // Branch: checks a condition
                if (blockSection.maxY() > maxY) maxY = blockSection.maxY();
                // Branch: checks a condition
                if (blockSection.maxZ() > maxZ) maxZ = blockSection.maxZ();
            // End of a block/expression
            }
            // Calls a method
            relativeStart = new Vec(minX, minY, minZ);
            // Calls a method
            relativeEnd = new Vec(maxX, maxY, maxZ);
        // Alternative branch of the condition
        } else {
            // Assigns a value
            relativeStart = Vec.ZERO;
            // Assigns a value
            relativeEnd = Vec.ZERO;
        // End of a block/expression
        }

        // Assigns a value
        byte fullCollisionFaces = 0;
        // Loop: repeats a block
        for (BlockFace f : BlockFace.values()) {
            // Calls a method
            final byte res = isFaceCovered(computeOcclusionSet(f, collisionBoundingBoxes));
            // Calls a method
            fullCollisionFaces |= ((res == 2) ? 0b1 : 0b0) << (byte) f.ordinal();
        // End of a block/expression
        }

        // Returns a value to the caller
        return new ShapeData(collisionBoundingBoxes, relativeStart, relativeEnd, fullCollisionFaces);
    // End of a block/expression
    }

    // Start of a method/block
    private static OcclusionData occlusionData(ShapeData shapeData, byte lightEmission) {
        // Assigns a value
        byte fullFaces = 0;
        // Assigns a value
        byte airFaces = 0;
        // Loop: repeats a block
        for (BlockFace f : BlockFace.values()) {
            // Calls a method
            final byte res = isFaceCovered(computeOcclusionSet(f, shapeData.boundingBoxes));
            // Calls a method
            fullFaces |= ((res == 2) ? 0b1 : 0b0) << (byte) f.ordinal();
            // Calls a method
            airFaces |= ((res == 0) ? 0b1 : 0b0) << (byte) f.ordinal();
        // End of a block/expression
        }
        // Returns a value to the caller
        return new OcclusionData(fullFaces, airFaces, lightEmission);
    // End of a block/expression
    }

    // Start of a method/block
    private static List<Rectangle> computeOcclusionSet(BlockFace face, List<BoundingBox> boundingBoxes) {
        // Calls a method
        List<Rectangle> rSet = new ArrayList<>();
        // Loop: repeats a block
        for (BoundingBox boundingBox : boundingBoxes) {
            // Multiple branching (switch/case)
            switch (face) {
                // Multiple branching (switch/case)
                case NORTH -> // negative Z
                // Start of a block
                {
                    // Branch: checks a condition
                    if (boundingBox.minZ() == 0)
                        // Calls a method
                        rSet.add(new Rectangle(boundingBox.minX(), boundingBox.minY(), boundingBox.maxX(), boundingBox.maxY()));
                // End of a block/expression
                }
                // Multiple branching (switch/case)
                case SOUTH -> // positive Z
                // Start of a block
                {
                    // Branch: checks a condition
                    if (boundingBox.maxZ() == 1)
                        // Calls a method
                        rSet.add(new Rectangle(boundingBox.minX(), boundingBox.minY(), boundingBox.maxX(), boundingBox.maxY()));
                // End of a block/expression
                }
                // Multiple branching (switch/case)
                case WEST -> // negative X
                // Start of a block
                {
                    // Branch: checks a condition
                    if (boundingBox.minX() == 0)
                        // Calls a method
                        rSet.add(new Rectangle(boundingBox.minY(), boundingBox.minZ(), boundingBox.maxY(), boundingBox.maxZ()));
                // End of a block/expression
                }
                // Multiple branching (switch/case)
                case EAST -> // positive X
                // Start of a block
                {
                    // Branch: checks a condition
                    if (boundingBox.maxX() == 1)
                        // Calls a method
                        rSet.add(new Rectangle(boundingBox.minY(), boundingBox.minZ(), boundingBox.maxY(), boundingBox.maxZ()));
                // End of a block/expression
                }
                // Multiple branching (switch/case)
                case BOTTOM -> // negative Y
                // Start of a block
                {
                    // Branch: checks a condition
                    if (boundingBox.minY() == 0)
                        // Calls a method
                        rSet.add(new Rectangle(boundingBox.minX(), boundingBox.minZ(), boundingBox.maxX(), boundingBox.maxZ()));
                // End of a block/expression
                }
                // Multiple branching (switch/case)
                case TOP -> // positive Y
                // Start of a block
                {
                    // Branch: checks a condition
                    if (boundingBox.maxY() == 1)
                        // Calls a method
                        rSet.add(new Rectangle(boundingBox.minX(), boundingBox.minZ(), boundingBox.maxX(), boundingBox.maxZ()));
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return rSet;
    // End of a block/expression
    }

    // Start of a method/block
    private static List<Rectangle> getRemaining(Rectangle covering, Rectangle toCover) {
        // Calls a method
        List<Rectangle> remaining = new ArrayList<>();
        // Calls a method
        covering = clipRectangle(covering, toCover);
        // Up
        // Branch: checks a condition
        if (covering.y1() > toCover.y1()) {
            // Calls a method
            remaining.add(new Rectangle(toCover.x1(), toCover.y1(), toCover.x2(), covering.y1()));
        // End of a block/expression
        }
        // Down
        // Branch: checks a condition
        if (covering.y2() < toCover.y2()) {
            // Calls a method
            remaining.add(new Rectangle(toCover.x1(), covering.y2(), toCover.x2(), toCover.y2()));
        // End of a block/expression
        }
        // Left
        // Branch: checks a condition
        if (covering.x1() > toCover.x1()) {
            // Calls a method
            remaining.add(new Rectangle(toCover.x1(), covering.y1(), covering.x1(), covering.y2()));
        // End of a block/expression
        }
        //Right
        // Branch: checks a condition
        if (covering.x2() < toCover.x2()) {
            // Calls a method
            remaining.add(new Rectangle(covering.x2(), covering.y1(), toCover.x2(), covering.y2()));
        // End of a block/expression
        }
        // Returns a value to the caller
        return remaining;
    // End of a block/expression
    }

    // Start of a method/block
    private static Rectangle clipRectangle(Rectangle covering, Rectangle toCover) {
        // Calls a method
        final double x1 = Math.max(covering.x1(), toCover.x1());
        // Calls a method
        final double y1 = Math.max(covering.y1(), toCover.y1());
        // Calls a method
        final double x2 = Math.min(covering.x2(), toCover.x2());
        // Calls a method
        final double y2 = Math.min(covering.y2(), toCover.y2());
        // Returns a value to the caller
        return new Rectangle(x1, y1, x2, y2);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private record Rectangle(double x1, double y1, double x2, double y2) {
    // End of a block/expression
    }
// End of a block/expression
}
