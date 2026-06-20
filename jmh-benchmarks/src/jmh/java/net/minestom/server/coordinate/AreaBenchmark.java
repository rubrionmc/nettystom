// Package declaration for this file
package net.minestom.server.coordinate;

// Import of a required class
import org.openjdk.jmh.annotations.*;
// Import of a required class
import org.openjdk.jmh.infra.Blackhole;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.concurrent.TimeUnit;

// Annotation for the following element
@BenchmarkMode(Mode.AverageTime)
// Annotation for the following element
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Annotation for the following element
@State(Scope.Benchmark)
// Annotation for the following element
@Warmup(iterations = 5, time = 1)
// Annotation for the following element
@Measurement(iterations = 5, time = 1)
// Annotation for the following element
@Fork(2)
// Type declaration (class/interface/enum/record)
public class AreaBenchmark {

    // Type declaration (class/interface/enum/record)
    public enum Shape {
        // Code statement
        SINGLE(Area.single(new BlockVec(5, 5, 5))),
        // Code statement
        LINE_AXIS(Area.line(new BlockVec(0, 0, 0), new BlockVec(128, 0, 0))),
        // Code statement
        LINE_DIAGONAL(Area.line(new BlockVec(0, 0, 0), new BlockVec(64, 32, 96))),
        // Code statement
        CUBOID_SECTION(Area.section(0, 0, 0)),
        // Code statement
        CUBOID_MULTISECTION(Area.cuboid(new BlockVec(-20, -20, -20), new BlockVec(20, 20, 20))),
        // Code statement
        SPHERE_SMALL(Area.sphere(new BlockVec(0, 0, 0), 4)),
        // Calls a method
        SPHERE_LARGE(Area.sphere(new BlockVec(0, 0, 0), 16));

        // Code statement
        final Area area;
        // Code statement
        final Point inside;
        // Code statement
        final Point outside;

        // Start of a method/block
        Shape(Area area) {
            // Access to the current/parent object
            this.area = area;
            // Access to the current/parent object
            this.inside = area.iterator().next();
            // Calls a method
            final BlockVec max = area.bound().max();
            // Access to the current/parent object
            this.outside = max.add(1000, 1000, 1000);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Param
    // Code statement
    public Shape shape;

    // Code statement
    private Area area;
    // Code statement
    private Point inside;
    // Code statement
    private Point outside;

    // Annotation for the following element
    @Setup
    // Start of a method/block
    public void setup() {
        // Assigns a value
        area = shape.area;
        // Assigns a value
        inside = shape.inside;
        // Assigns a value
        outside = shape.outside;
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public long blockCount() {
        // Returns a value to the caller
        return area.blockCount();
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void iterate(Blackhole bh) {
        // Loop: repeats a block
        for (BlockVec v : area) bh.consume(v);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public List<Area.Cuboid> split() {
        // Returns a value to the caller
        return area.split();
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public boolean containsInside() {
        // Returns a value to the caller
        return area.contains(inside);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public boolean containsOutside() {
        // Returns a value to the caller
        return area.contains(outside);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public Area bound() {
        // Returns a value to the caller
        return area.bound();
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public Area offset() {
        // Returns a value to the caller
        return area.offset(7, -3, 11);
    // End of a block/expression
    }
// End of a block/expression
}
