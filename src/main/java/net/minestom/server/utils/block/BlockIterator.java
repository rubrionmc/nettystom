// Déclaration du paquet de ce fichier
package net.minestom.server.utils.block;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;

// Import d'une classe nécessaire
import java.util.ArrayDeque;
// Import d'une classe nécessaire
import java.util.Iterator;
// Import d'une classe nécessaire
import java.util.NoSuchElementException;

/**
 * This class performs ray tracing and iterates along blocks on a line
 */
// Déclaration de type (classe/interface/enum/record)
public class BlockIterator implements Iterator<Point> {
    // Affecte une valeur
    private final short[] signums = new short[3];
    // Instruction de code
    private Vec end;
    // Instruction de code
    private boolean smooth;

    // Affecte une valeur
    private boolean foundEnd = false;

    //length of ray from current position to next x or y-side
    // Instruction de code
    double sideDistX;
    // Instruction de code
    double sideDistY;
    // Instruction de code
    double sideDistZ;

    //length of ray from one x or y-side to next x or y-side
    // Instruction de code
    private double deltaDistX;
    // Instruction de code
    private double deltaDistY;
    // Instruction de code
    private double deltaDistZ;

    //which box of the map we're in
    // Instruction de code
    int mapX;
    // Instruction de code
    int mapY;
    // Instruction de code
    int mapZ;

    // Appelle une méthode
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
    // Début d'une méthode/d'un bloc
    public BlockIterator(Vec start, Vec direction, double yOffset, double maxDistance, boolean smooth) {
        // Appelle une méthode
        reset(start, direction, yOffset, maxDistance, smooth);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public BlockIterator() {}

    // Début d'une méthode/d'un bloc
    public void reset(Vec start, Vec direction, double yOffset, double maxDistance, boolean smooth) {
        // Appelle une méthode
        extraPoints.clear();
        // Affecte une valeur
        foundEnd = false;

        // Appelle une méthode
        start = start.add(0, yOffset, 0);

        // Embranchement : vérifie une condition
        if (maxDistance != 0) end = start.add(direction.normalize().mul(maxDistance));
        // Branche alternative de la condition
        else end = null;

        // Embranchement : vérifie une condition
        if (direction.isZero()) this.foundEnd = true;

        // Accès à l'objet courant/parent
        this.smooth = smooth;

        // Appelle une méthode
        Vec ray = direction.normalize();

        //which box of the map we're in
        // Appelle une méthode
        mapX = start.blockX();
        // Appelle une méthode
        mapY = start.blockY();
        // Appelle une méthode
        mapZ = start.blockZ();

        // Appelle une méthode
        signums[0] = (short) Math.signum(direction.x());
        // Appelle une méthode
        signums[1] = (short) Math.signum(direction.y());
        // Appelle une méthode
        signums[2] = (short) Math.signum(direction.z());

        // Appelle une méthode
        deltaDistX = (ray.x() == 0) ? 1e30 : Math.abs(1 / ray.x());
        // Affecte une valeur
        deltaDistY = (ray.y() == 0) ? 1e30 : Math.abs(1 / ray.y());        // Find grid intersections for x, y, z
        // Affecte une valeur
        deltaDistZ = (ray.z() == 0) ? 1e30 : Math.abs(1 / ray.z());        // This works by calculating and storing the distance to the next grid intersection on the x, y and z axis

        //calculate step and initial sideDist
        // Embranchement : vérifie une condition
        if (ray.x() < 0) sideDistX = (start.x() - mapX) * deltaDistX;
        // Embranchement : vérifie une condition
        else if (ray.x() > 0) sideDistX = (mapX + signums[0] - start.x()) * deltaDistX;
        // Branche alternative de la condition
        else sideDistX = Double.MAX_VALUE;

        // Embranchement : vérifie une condition
        if (ray.y() < 0) sideDistY = (start.y() - mapY) * deltaDistY;
        // Embranchement : vérifie une condition
        else if (ray.y() > 0) sideDistY = (mapY + signums[1] - start.y()) * deltaDistY;
        // Branche alternative de la condition
        else sideDistY = Double.MAX_VALUE;

        // Embranchement : vérifie une condition
        if (ray.z() < 0) sideDistZ = (start.z() - mapZ) * deltaDistZ;
        // Embranchement : vérifie une condition
        else if (ray.z() > 0) sideDistZ = (mapZ + signums[2] - start.z()) * deltaDistZ;
        // Branche alternative de la condition
        else sideDistZ = Double.MAX_VALUE;
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public BlockIterator(Vec start, Vec direction, double yOffset, double maxDistance) {
        // Appelle une méthode
        this(start, direction, yOffset, maxDistance, false);
    // Fin d'un bloc/d'une expression
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

    // Début d'une méthode/d'un bloc
    public BlockIterator(Pos pos, double yOffset, int maxDistance) {
        // Appelle une méthode
        this(pos.asVec(), pos.direction(), yOffset, maxDistance, false);
    // Fin d'un bloc/d'une expression
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

    // Début d'une méthode/d'un bloc
    public BlockIterator(Pos pos, double yOffset) {
        // Appelle une méthode
        this(pos.asVec(), pos.direction(), yOffset, 0, false);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Constructs the BlockIterator.
     * <p>
     * This considers all blocks as 1x1x1 in size.
     *
     * @param pos The position for the start of the ray trace
     */

    // Début d'une méthode/d'un bloc
    public BlockIterator(Pos pos) {
        // Appelle une méthode
        this(pos, 0f);
    // Fin d'un bloc/d'une expression
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

    // Début d'une méthode/d'un bloc
    public BlockIterator(Entity entity, int maxDistance) {
        // Appelle une méthode
        this(entity.getPosition(), entity.getEyeHeight(), maxDistance);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Constructs the BlockIterator.
     * <p>
     * This considers all blocks as 1x1x1 in size.
     *
     * @param entity Information from the entity is used to set up the trace
     */

    // Début d'une méthode/d'un bloc
    public BlockIterator(Entity entity) {
        // Appelle une méthode
        this(entity, 0);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns true if the iteration has more elements
     */

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean hasNext() {
        // Renvoie une valeur à l'appelant
        return !foundEnd;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void remove() {
        // Lève une exception
        throw new UnsupportedOperationException("[BlockIterator] doesn't support block removal");
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the next BlockPosition in the trace
     *
     * @return the next BlockPosition in the trace
     */

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Point next() {
        // Embranchement : vérifie une condition
        if (foundEnd) throw new NoSuchElementException();
        // Embranchement : vérifie une condition
        if (!extraPoints.isEmpty()) {
            // Appelle une méthode
            var res = extraPoints.poll();
            // Embranchement : vérifie une condition
            if (end != null && res.sameBlock(end)) foundEnd = true;
            // Renvoie une valeur à l'appelant
            return res;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        var current = new Vec(mapX, mapY, mapZ);
        // Embranchement : vérifie une condition
        if (end != null && current.sameBlock(end)) foundEnd = true;

        // Appelle une méthode
        double closest = Math.min(sideDistX, Math.min(sideDistY, sideDistZ));
        // Affecte une valeur
        boolean needsX = sideDistX - closest < 1e-10 && signums[0] != 0;
        // Affecte une valeur
        boolean needsY = sideDistY - closest < 1e-10 && signums[1] != 0;
        // Affecte une valeur
        boolean needsZ = sideDistZ - closest < 1e-10 && signums[2] != 0;

        // Embranchement : vérifie une condition
        if (needsZ) {
            // Instruction de code
            sideDistZ += deltaDistZ;
            // Instruction de code
            mapZ += signums[2];
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (needsX) {
            // Instruction de code
            sideDistX += deltaDistX;
            // Instruction de code
            mapX += signums[0];
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (needsY) {
            // Instruction de code
            sideDistY += deltaDistY;
            // Instruction de code
            mapY += signums[1];
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (needsX && needsY && needsZ) {
            // Appelle une méthode
            extraPoints.add(new Vec(signums[0] + current.x(), signums[1] + current.y(), current.z()));
            // Embranchement : vérifie une condition
            if (smooth) return current;

            // Appelle une méthode
            extraPoints.add(new Vec(current.x(), signums[1] + current.y(), signums[2] + current.z()));
            // Appelle une méthode
            extraPoints.add(new Vec(signums[0] + current.x(), current.y(), signums[2] + current.z()));

            // Appelle une méthode
            extraPoints.add(new Vec(signums[0] + current.x(), current.y(), current.z()));
            // Appelle une méthode
            extraPoints.add(new Vec(current.x(), signums[1] + current.y(), current.z()));
            // Appelle une méthode
            extraPoints.add(new Vec(current.x(), current.y(), signums[2] + current.z()));
        // Embranchement : vérifie une condition
        } else if (needsX && needsY) {
            // Appelle une méthode
            extraPoints.add(new Vec(signums[0] + current.x(), current.y(), current.z()));
            // Embranchement : vérifie une condition
            if (smooth) return current;
            // Appelle une méthode
            extraPoints.add(new Vec(current.x(), signums[1] + current.y(), current.z()));
        // Embranchement : vérifie une condition
        } else if (needsX && needsZ) {
            // Appelle une méthode
            extraPoints.add(new Vec(signums[0] + current.x(), current.y(), current.z()));
            // Embranchement : vérifie une condition
            if (smooth) return current;
            // Appelle une méthode
            extraPoints.add(new Vec(current.x(), current.y(), signums[2] + current.z()));
        // Embranchement : vérifie une condition
        } else if (needsY && needsZ) {
            // Appelle une méthode
            extraPoints.add(new Vec(current.x(), signums[1] + current.y(), current.z()));
            // Embranchement : vérifie une condition
            if (smooth) return current;
            // Appelle une méthode
            extraPoints.add(new Vec(current.x(), current.y(), signums[2] + current.z()));
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return current;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
