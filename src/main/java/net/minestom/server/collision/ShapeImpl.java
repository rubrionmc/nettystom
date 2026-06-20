// Déclaration du paquet de ce fichier
package net.minestom.server.collision;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.doubles.DoubleList;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Unmodifiable;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.regex.Matcher;
// Import d'une classe nécessaire
import java.util.regex.Pattern;

// Déclaration de type (classe/interface/enum/record)
public record ShapeImpl(ShapeData shapeData, OcclusionData occlusionData) implements Shape {
    // Appelle une méthode
    private static final Pattern PATTERN = Pattern.compile("\\d.\\d+", Pattern.MULTILINE);

    // Déclaration de type (classe/interface/enum/record)
    record ShapeData(List<BoundingBox> boundingBoxes,
                     // Instruction de code
                     Point relativeStart, Point relativeEnd,
                     // Début d'une méthode/d'un bloc
                     byte fullFaces) {
        // Début d'une méthode/d'un bloc
        public ShapeData {
            // Appelle une méthode
            boundingBoxes = List.copyOf(boundingBoxes);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record OcclusionData(byte blockOcclusion, byte airOcclusion, byte lightEmission) {}

    // Début d'une méthode/d'un bloc
    public ShapeImpl withLightEmission(byte lightEmission) {
        // Renvoie une valeur à l'appelant
        return new ShapeImpl(this.shapeData, new OcclusionData(this.occlusionData.blockOcclusion(), this.occlusionData.airOcclusion, lightEmission));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Computes the occlusion for a given face.
     *
     * @param covering The rectangle set to check for covering.
     * @return 0 if face is not covered, 1 if face is covered partially, 2 if face is fully covered.
     */
    // Début d'une méthode/d'un bloc
    private static byte isFaceCovered(List<Rectangle> covering) {
        // Embranchement : vérifie une condition
        if (covering.isEmpty()) return 0;
        // Appelle une méthode
        Rectangle r = new Rectangle(0, 0, 1, 1);
        // Appelle une méthode
        List<Rectangle> toCover = new ArrayList<>();
        // Appelle une méthode
        toCover.add(r);
        // Boucle : répète un bloc
        for (Rectangle rect : covering) {
            // Appelle une méthode
            List<Rectangle> nextCovering = new ArrayList<>();
            // Boucle : répète un bloc
            for (Rectangle toCoverRect : toCover) {
                // Appelle une méthode
                List<Rectangle> remaining = getRemaining(rect, toCoverRect);
                // Appelle une méthode
                nextCovering.addAll(remaining);
            // Fin d'un bloc/d'une expression
            }
            // Affecte une valeur
            toCover = nextCovering;
            // Embranchement : vérifie une condition
            if (toCover.isEmpty()) return 2;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return 1;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Point relativeStart() {
        // Renvoie une valeur à l'appelant
        return shapeData.relativeStart;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Point relativeEnd() {
        // Renvoie une valeur à l'appelant
        return shapeData.relativeEnd;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isOccluded(Shape shape, BlockFace face) {
        // Affecte une valeur
        final OcclusionData occlusionData = this.occlusionData;
        // Appelle une méthode
        final OcclusionData otherOcclusionData = ((ShapeImpl) shape).occlusionData;

        // Appelle une méthode
        final boolean hasBlockOcclusion = (((occlusionData.blockOcclusion >> face.ordinal()) & 1) == 1);
        // Appelle une méthode
        final boolean hasBlockOcclusionOther = ((otherOcclusionData.blockOcclusion >> face.getOppositeFace().ordinal()) & 1) == 1;

        // Embranchement : vérifie une condition
        if (occlusionData.lightEmission > 0) return hasBlockOcclusionOther;

        // If either face is full, return true
        // Embranchement : vérifie une condition
        if (hasBlockOcclusion || hasBlockOcclusionOther) return true;

        // Appelle une méthode
        final boolean hasAirOcclusion = (((occlusionData.airOcclusion >> face.ordinal()) & 1) == 1);
        // Appelle une méthode
        final boolean hasAirOcclusionOther = ((otherOcclusionData.airOcclusion >> face.getOppositeFace().ordinal()) & 1) == 1;

        // If a single face is air, return false
        // Embranchement : vérifie une condition
        if (hasAirOcclusion || hasAirOcclusionOther) return false;

        // Affecte une valeur
        final ShapeData shapeData = this.shapeData;
        // Appelle une méthode
        final ShapeData otherShapeData = ((ShapeImpl) shape).shapeData;

        // Comparing two partial faces. Computation needed
        // Appelle une méthode
        List<Rectangle> allRectangles = computeOcclusionSet(face.getOppositeFace(), otherShapeData.boundingBoxes);
        // Appelle une méthode
        allRectangles.addAll(computeOcclusionSet(face, shapeData.boundingBoxes));
        // Renvoie une valeur à l'appelant
        return isFaceCovered(allRectangles) == 2;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isFaceFull(BlockFace face) {
        // Renvoie une valeur à l'appelant
        return (((shapeData.fullFaces >> face.ordinal()) & 1) == 1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean intersectBox(Point position, BoundingBox boundingBox) {
        // Boucle : répète un bloc
        for (BoundingBox blockSection : shapeData.boundingBoxes) {
            // Embranchement : vérifie une condition
            if (boundingBox.intersectBox(position, blockSection)) return true;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Instruction de code
    public boolean intersectBoxSwept(Point rayStart, Point rayDirection,
                                     // Début d'une méthode/d'un bloc
                                     Point shapePos, BoundingBox moving, SweepResult finalResult) {
        // Affecte une valeur
        boolean hitBlock = false;
        // Boucle : répète un bloc
        for (BoundingBox blockSection : shapeData.boundingBoxes) {
            // Update final result if the temp result collision is sooner than the current final result
            // Embranchement : vérifie une condition
            if (RayUtils.BoundingBoxIntersectionCheck(moving, rayStart, rayDirection, blockSection, shapePos, finalResult)) {
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
                // Affecte une valeur
                hitBlock = true;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return hitBlock;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the bounding boxes for this shape. There will be more than one bounds for more complex shapes e.g.
     * stairs.
     *
     * @return the bounding boxes for this shape
     */
    // Début d'une méthode/d'un bloc
    public @Unmodifiable List<BoundingBox> boundingBoxes() {
        // Renvoie une valeur à l'appelant
        return shapeData.boundingBoxes;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static ShapeImpl parseShapeFromRegistry(String shape, byte lightEmission) {
        // Appelle une méthode
        BoundingBox[] boundingBoxes = parseRegistryBoundingBoxString(shape);
        // Appelle une méthode
        final ShapeData shapeData = shapeData(List.of(boundingBoxes));
        // Appelle une méthode
        final OcclusionData occlusionData = occlusionData(shapeData, lightEmission);
        // Renvoie une valeur à l'appelant
        return new ShapeImpl(shapeData, occlusionData);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static ShapeImpl emptyShape(byte lightEmission) {
        // Affecte une valeur
        BoundingBox[] boundingBoxes = new BoundingBox[0];
        // Appelle une méthode
        final ShapeData shapeData = shapeData(List.of(boundingBoxes));
        // Appelle une méthode
        final OcclusionData occlusionData = occlusionData(shapeData, lightEmission);
        // Renvoie une valeur à l'appelant
        return new ShapeImpl(shapeData, occlusionData);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static BoundingBox[] parseRegistryBoundingBoxString(String str) {
        // Appelle une méthode
        final Matcher matcher = PATTERN.matcher(str);
        // Appelle une méthode
        DoubleList vals = new DoubleArrayList();
        // Boucle : répète un bloc
        while (matcher.find()) {
            // Appelle une méthode
            double newVal = Double.parseDouble(matcher.group());
            // Appelle une méthode
            vals.add(newVal);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        final int count = vals.size() / 6;
        // Affecte une valeur
        BoundingBox[] boundingBoxes = new BoundingBox[count];
        // Boucle : répète un bloc
        for (int i = 0; i < count; ++i) {
            // Appelle une méthode
            final double minX = vals.getDouble(0 + 6 * i);
            // Appelle une méthode
            final double minY = vals.getDouble(1 + 6 * i);
            // Appelle une méthode
            final double minZ = vals.getDouble(2 + 6 * i);

            // Appelle une méthode
            final double boundXSize = vals.getDouble(3 + 6 * i) - minX;
            // Appelle une méthode
            final double boundYSize = vals.getDouble(4 + 6 * i) - minY;
            // Appelle une méthode
            final double boundZSize = vals.getDouble(5 + 6 * i) - minZ;

            // Appelle une méthode
            final Vec min = new Vec(minX, minY, minZ);
            // Appelle une méthode
            final Vec max = new Vec(minX + boundXSize, minY + boundYSize, minZ + boundZSize);
            // Appelle une méthode
            final BoundingBox bb = new BoundingBox(min, max);
            // Appelle une méthode
            assert bb.minX() == minX;
            // Appelle une méthode
            assert bb.minY() == minY;
            // Appelle une méthode
            assert bb.minZ() == minZ;
            // Affecte une valeur
            boundingBoxes[i] = bb;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return boundingBoxes;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static ShapeData shapeData(List<BoundingBox> collisionBoundingBoxes) {
        // Find bounds of collision
        // Instruction de code
        Vec relativeStart;
        // Instruction de code
        Vec relativeEnd;
        // Embranchement : vérifie une condition
        if (!collisionBoundingBoxes.isEmpty()) {
            // Affecte une valeur
            double minX = 1, minY = 1, minZ = 1;
            // Affecte une valeur
            double maxX = 0, maxY = 0, maxZ = 0;
            // Boucle : répète un bloc
            for (BoundingBox blockSection : collisionBoundingBoxes) {
                // Min
                // Embranchement : vérifie une condition
                if (blockSection.minX() < minX) minX = blockSection.minX();
                // Embranchement : vérifie une condition
                if (blockSection.minY() < minY) minY = blockSection.minY();
                // Embranchement : vérifie une condition
                if (blockSection.minZ() < minZ) minZ = blockSection.minZ();
                // Max
                // Embranchement : vérifie une condition
                if (blockSection.maxX() > maxX) maxX = blockSection.maxX();
                // Embranchement : vérifie une condition
                if (blockSection.maxY() > maxY) maxY = blockSection.maxY();
                // Embranchement : vérifie une condition
                if (blockSection.maxZ() > maxZ) maxZ = blockSection.maxZ();
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            relativeStart = new Vec(minX, minY, minZ);
            // Appelle une méthode
            relativeEnd = new Vec(maxX, maxY, maxZ);
        // Branche alternative de la condition
        } else {
            // Affecte une valeur
            relativeStart = Vec.ZERO;
            // Affecte une valeur
            relativeEnd = Vec.ZERO;
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        byte fullCollisionFaces = 0;
        // Boucle : répète un bloc
        for (BlockFace f : BlockFace.values()) {
            // Appelle une méthode
            final byte res = isFaceCovered(computeOcclusionSet(f, collisionBoundingBoxes));
            // Appelle une méthode
            fullCollisionFaces |= ((res == 2) ? 0b1 : 0b0) << (byte) f.ordinal();
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return new ShapeData(collisionBoundingBoxes, relativeStart, relativeEnd, fullCollisionFaces);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static OcclusionData occlusionData(ShapeData shapeData, byte lightEmission) {
        // Affecte une valeur
        byte fullFaces = 0;
        // Affecte une valeur
        byte airFaces = 0;
        // Boucle : répète un bloc
        for (BlockFace f : BlockFace.values()) {
            // Appelle une méthode
            final byte res = isFaceCovered(computeOcclusionSet(f, shapeData.boundingBoxes));
            // Appelle une méthode
            fullFaces |= ((res == 2) ? 0b1 : 0b0) << (byte) f.ordinal();
            // Appelle une méthode
            airFaces |= ((res == 0) ? 0b1 : 0b0) << (byte) f.ordinal();
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new OcclusionData(fullFaces, airFaces, lightEmission);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static List<Rectangle> computeOcclusionSet(BlockFace face, List<BoundingBox> boundingBoxes) {
        // Appelle une méthode
        List<Rectangle> rSet = new ArrayList<>();
        // Boucle : répète un bloc
        for (BoundingBox boundingBox : boundingBoxes) {
            // Embranchement multiple (switch/case)
            switch (face) {
                // Embranchement multiple (switch/case)
                case NORTH -> // negative Z
                // Début d'un bloc
                {
                    // Embranchement : vérifie une condition
                    if (boundingBox.minZ() == 0)
                        // Appelle une méthode
                        rSet.add(new Rectangle(boundingBox.minX(), boundingBox.minY(), boundingBox.maxX(), boundingBox.maxY()));
                // Fin d'un bloc/d'une expression
                }
                // Embranchement multiple (switch/case)
                case SOUTH -> // positive Z
                // Début d'un bloc
                {
                    // Embranchement : vérifie une condition
                    if (boundingBox.maxZ() == 1)
                        // Appelle une méthode
                        rSet.add(new Rectangle(boundingBox.minX(), boundingBox.minY(), boundingBox.maxX(), boundingBox.maxY()));
                // Fin d'un bloc/d'une expression
                }
                // Embranchement multiple (switch/case)
                case WEST -> // negative X
                // Début d'un bloc
                {
                    // Embranchement : vérifie une condition
                    if (boundingBox.minX() == 0)
                        // Appelle une méthode
                        rSet.add(new Rectangle(boundingBox.minY(), boundingBox.minZ(), boundingBox.maxY(), boundingBox.maxZ()));
                // Fin d'un bloc/d'une expression
                }
                // Embranchement multiple (switch/case)
                case EAST -> // positive X
                // Début d'un bloc
                {
                    // Embranchement : vérifie une condition
                    if (boundingBox.maxX() == 1)
                        // Appelle une méthode
                        rSet.add(new Rectangle(boundingBox.minY(), boundingBox.minZ(), boundingBox.maxY(), boundingBox.maxZ()));
                // Fin d'un bloc/d'une expression
                }
                // Embranchement multiple (switch/case)
                case BOTTOM -> // negative Y
                // Début d'un bloc
                {
                    // Embranchement : vérifie une condition
                    if (boundingBox.minY() == 0)
                        // Appelle une méthode
                        rSet.add(new Rectangle(boundingBox.minX(), boundingBox.minZ(), boundingBox.maxX(), boundingBox.maxZ()));
                // Fin d'un bloc/d'une expression
                }
                // Embranchement multiple (switch/case)
                case TOP -> // positive Y
                // Début d'un bloc
                {
                    // Embranchement : vérifie une condition
                    if (boundingBox.maxY() == 1)
                        // Appelle une méthode
                        rSet.add(new Rectangle(boundingBox.minX(), boundingBox.minZ(), boundingBox.maxX(), boundingBox.maxZ()));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return rSet;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static List<Rectangle> getRemaining(Rectangle covering, Rectangle toCover) {
        // Appelle une méthode
        List<Rectangle> remaining = new ArrayList<>();
        // Appelle une méthode
        covering = clipRectangle(covering, toCover);
        // Up
        // Embranchement : vérifie une condition
        if (covering.y1() > toCover.y1()) {
            // Appelle une méthode
            remaining.add(new Rectangle(toCover.x1(), toCover.y1(), toCover.x2(), covering.y1()));
        // Fin d'un bloc/d'une expression
        }
        // Down
        // Embranchement : vérifie une condition
        if (covering.y2() < toCover.y2()) {
            // Appelle une méthode
            remaining.add(new Rectangle(toCover.x1(), covering.y2(), toCover.x2(), toCover.y2()));
        // Fin d'un bloc/d'une expression
        }
        // Left
        // Embranchement : vérifie une condition
        if (covering.x1() > toCover.x1()) {
            // Appelle une méthode
            remaining.add(new Rectangle(toCover.x1(), covering.y1(), covering.x1(), covering.y2()));
        // Fin d'un bloc/d'une expression
        }
        //Right
        // Embranchement : vérifie une condition
        if (covering.x2() < toCover.x2()) {
            // Appelle une méthode
            remaining.add(new Rectangle(covering.x2(), covering.y1(), toCover.x2(), covering.y2()));
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return remaining;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Rectangle clipRectangle(Rectangle covering, Rectangle toCover) {
        // Appelle une méthode
        final double x1 = Math.max(covering.x1(), toCover.x1());
        // Appelle une méthode
        final double y1 = Math.max(covering.y1(), toCover.y1());
        // Appelle une méthode
        final double x2 = Math.min(covering.x2(), toCover.x2());
        // Appelle une méthode
        final double y2 = Math.min(covering.y2(), toCover.y2());
        // Renvoie une valeur à l'appelant
        return new Rectangle(x1, y1, x2, y2);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private record Rectangle(double x1, double y1, double x2, double y2) {
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
