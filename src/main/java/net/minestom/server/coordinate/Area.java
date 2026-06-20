// Déclaration du paquet de ce fichier
package net.minestom.server.coordinate;

// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.List;

/**
 * Represents a collection of aligned block coordinates in a 3D space.
 * <p>
 * If switched over, consider a fallback to the iterator as more implementations may be added in the future.
 */
// Annotation pour l'élément suivant
@ApiStatus.Experimental
// Déclaration de type (classe/interface/enum/record)
public sealed interface Area extends Iterable<BlockVec> {

    // Début d'une méthode/d'un bloc
    default Area offset(int x, int y, int z) {
        // Renvoie une valeur à l'appelant
        return switch (this) {
            // Embranchement multiple (switch/case)
            case Single single -> single(single.point().add(x, y, z));
            // Embranchement multiple (switch/case)
            case Line line -> line(line.start().add(x, y, z), line.end().add(x, y, z));
            // Embranchement multiple (switch/case)
            case Cuboid cuboid -> cuboid(cuboid.min().add(x, y, z), cuboid.max().add(x, y, z));
            // Embranchement multiple (switch/case)
            case Sphere sphere -> sphere(sphere.center().add(x, y, z), sphere.radius());
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default Area offset(Point offset) {
        // Renvoie une valeur à l'appelant
        return offset(offset.blockX(), offset.blockY(), offset.blockZ());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the bounding box of this
     *
     * @return a cuboid representing the bounding box with the lowest and highest points
     */
    // Début d'une méthode/d'un bloc
    default Cuboid bound() {
        // Renvoie une valeur à l'appelant
        return switch (this) {
            // Embranchement multiple (switch/case)
            case Single single -> cuboid(single.point(), single.point());
            // Embranchement multiple (switch/case)
            case Line line -> {
                // Appelle une méthode
                final BlockVec start = line.start();
                // Appelle une méthode
                final BlockVec end = line.end();
                // Appelle une méthode
                yield cuboid(start.min(end), start.max(end));
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case Cuboid cuboid -> cuboid;
            // Embranchement multiple (switch/case)
            case Sphere sphere -> {
                // Appelle une méthode
                final BlockVec center = sphere.center();
                // Appelle une méthode
                final int radius = sphere.radius();
                // Appelle une méthode
                yield cuboid(center.sub(radius, radius, radius), center.add(radius, radius, radius));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Splits this area into multiple section aligned cuboids.
     * <p>
     * Single sections may have multiple cuboids if they are not perfect cuboids.
     *
     * @return list of sub-cuboids covering this area
     */
    // Appelle une méthode
    List<Cuboid> split();

    // Début d'une méthode/d'un bloc
    static Single single(Point point) {
        // Renvoie une valeur à l'appelant
        return new AreaImpl.Single(point.asBlockVec());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Single single(int x, int y, int z) {
        // Renvoie une valeur à l'appelant
        return single(new BlockVec(x, y, z));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Line line(Point start, Point end) {
        // Renvoie une valeur à l'appelant
        return new AreaImpl.Line(start.asBlockVec(), end.asBlockVec());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Cuboid cuboid(Point min, Point max) {
        // Renvoie une valeur à l'appelant
        return new AreaImpl.Cuboid(min.asBlockVec(), max.asBlockVec());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Cuboid cube(Point center, int size) {
        // Renvoie une valeur à l'appelant
        return cuboid(center.sub((double) size / 2), center.add((double) size / 2));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Cuboid box(Point center, Point size) {
        // Appelle une méthode
        final Point half = size.div(2);
        // Renvoie une valeur à l'appelant
        return cuboid(center.sub(half), center.add(half));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Cuboid section(int sectionX, int sectionY, int sectionZ) {
        // Appelle une méthode
        final BlockVec section = BlockVec.SECTION.mul(sectionX, sectionY, sectionZ);
        // Renvoie une valeur à l'appelant
        return cuboid(section, BlockVec.SECTION.add(section).sub(1));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Sphere sphere(Point center, int radius) {
        // Renvoie une valeur à l'appelant
        return new AreaImpl.Sphere(center.asBlockVec(), radius);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    sealed interface Single extends Area permits AreaImpl.Single {
        // Appelle une méthode
        BlockVec point();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    sealed interface Line extends Area permits AreaImpl.Line {
        // Appelle une méthode
        BlockVec start();

        // Appelle une méthode
        BlockVec end();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    sealed interface Cuboid extends Area permits AreaImpl.Cuboid {
        // Appelle une méthode
        BlockVec min();

        // Appelle une méthode
        BlockVec max();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    sealed interface Sphere extends Area permits AreaImpl.Sphere {
        // Appelle une méthode
        BlockVec center();

        // Appelle une méthode
        int radius();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
