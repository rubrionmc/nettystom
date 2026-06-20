// Déclaration du paquet de ce fichier
package net.minestom.server.coordinate;

// Import d'une classe nécessaire
import org.openjdk.jmh.annotations.*;
// Import d'une classe nécessaire
import org.openjdk.jmh.infra.Blackhole;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;

// Annotation pour l'élément suivant
@BenchmarkMode(Mode.AverageTime)
// Annotation pour l'élément suivant
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Annotation pour l'élément suivant
@State(Scope.Benchmark)
// Annotation pour l'élément suivant
@Warmup(iterations = 5, time = 1)
// Annotation pour l'élément suivant
@Measurement(iterations = 5, time = 1)
// Annotation pour l'élément suivant
@Fork(2)
// Déclaration de type (classe/interface/enum/record)
public class AreaBenchmark {

    // Déclaration de type (classe/interface/enum/record)
    public enum Shape {
        // Instruction de code
        SINGLE(Area.single(new BlockVec(5, 5, 5))),
        // Instruction de code
        LINE_AXIS(Area.line(new BlockVec(0, 0, 0), new BlockVec(128, 0, 0))),
        // Instruction de code
        LINE_DIAGONAL(Area.line(new BlockVec(0, 0, 0), new BlockVec(64, 32, 96))),
        // Instruction de code
        CUBOID_SECTION(Area.section(0, 0, 0)),
        // Instruction de code
        CUBOID_MULTISECTION(Area.cuboid(new BlockVec(-20, -20, -20), new BlockVec(20, 20, 20))),
        // Instruction de code
        SPHERE_SMALL(Area.sphere(new BlockVec(0, 0, 0), 4)),
        // Appelle une méthode
        SPHERE_LARGE(Area.sphere(new BlockVec(0, 0, 0), 16));

        // Instruction de code
        final Area area;
        // Instruction de code
        final Point inside;
        // Instruction de code
        final Point outside;

        // Début d'une méthode/d'un bloc
        Shape(Area area) {
            // Accès à l'objet courant/parent
            this.area = area;
            // Accès à l'objet courant/parent
            this.inside = area.iterator().next();
            // Appelle une méthode
            final BlockVec max = area.bound().max();
            // Accès à l'objet courant/parent
            this.outside = max.add(1000, 1000, 1000);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Param
    // Instruction de code
    public Shape shape;

    // Instruction de code
    private Area area;
    // Instruction de code
    private Point inside;
    // Instruction de code
    private Point outside;

    // Annotation pour l'élément suivant
    @Setup
    // Début d'une méthode/d'un bloc
    public void setup() {
        // Affecte une valeur
        area = shape.area;
        // Affecte une valeur
        inside = shape.inside;
        // Affecte une valeur
        outside = shape.outside;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public long blockCount() {
        // Renvoie une valeur à l'appelant
        return area.blockCount();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void iterate(Blackhole bh) {
        // Boucle : répète un bloc
        for (BlockVec v : area) bh.consume(v);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public List<Area.Cuboid> split() {
        // Renvoie une valeur à l'appelant
        return area.split();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public boolean containsInside() {
        // Renvoie une valeur à l'appelant
        return area.contains(inside);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public boolean containsOutside() {
        // Renvoie une valeur à l'appelant
        return area.contains(outside);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public Area bound() {
        // Renvoie une valeur à l'appelant
        return area.bound();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public Area offset() {
        // Renvoie une valeur à l'appelant
        return area.offset(7, -3, 11);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
