// Package declaration for this file
package net.minestom.server.collision;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.instance.WorldBorder;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import org.openjdk.jmh.annotations.*;

// Import of a required class
import java.util.concurrent.TimeUnit;
// Import of a required class
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Benchmarks the block-physics hot path ({@link CollisionUtils#handlePhysics} /
 * {@link PhysicsUtils#simulateMovement}) which runs for every entity every tick.
 * <p>
 * Each benchmark method is crafted to exercise a distinct branch of the physics code:
 * <ul>
 *     <li>{@link #zeroVelocity()} - the {@code velocity.isZero()} early return</li>
 *     <li>{@link #cachedStanding()} - the {@code cachedPhysics} fast-exit (resting on ground)</li>
 *     <li>{@link #fallThroughAir()} - {@code fastPhysics}, no collision (face traversal only)</li>
 *     <li>{@link #fallIntoFloor()} - {@code fastPhysics}, vertical collision</li>
 *     <li>{@link #walkOnFloor()} - {@code fastPhysics}, horizontal move + gravity into floor</li>
 *     <li>{@link #diagonalMove()} - {@code fastPhysics} diagonal special-case</li>
 *     <li>{@link #largeMoveSlow()} - {@code slowPhysics} ray-cast (velocity length &gt; 1)</li>
 *     <li>{@link #fenceCollision()} - multi-box shape + tall-below ({@code shouldCheckLower}) path</li>
 *     <li>{@link #denseCollision()} - collisions on all axes, multiple step-physics iterations</li>
 *     <li>{@link #simulateOnGround()} - full movement incl. friction lookup + velocity update</li>
 *     <li>{@link #simulateFalling()} - full movement incl. gravity velocity update</li>
 * </ul>
 */
// Annotation for the following element
@BenchmarkMode({Mode.AverageTime, Mode.Throughput})
// Annotation for the following element
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Annotation for the following element
@State(Scope.Thread)
// Annotation for the following element
@Warmup(iterations = 5, time = 1)
// Annotation for the following element
@Measurement(iterations = 8, time = 1)
// Annotation for the following element
@Fork(2)
// Type declaration (class/interface/enum/record)
public class BlockPhysicsBenchmark {

    // Player-sized bounding box (0.6 x 1.8 x 0.6)
    // Calls a method
    private static final BoundingBox PLAYER_BB = new BoundingBox(0.6, 1.8, 0.6);
    // Representative player aerodynamics (gravity, horizontal drag, vertical drag)
    // Calls a method
    private static final Aerodynamics AERO = new Aerodynamics(0.08, 0.91, 0.98);
    // Assigns a value
    private static final WorldBorder BORDER = WorldBorder.DEFAULT_BORDER;

    // --- In-memory block getters ---

    /** Everything is air: exercises face traversal with no collisions. */
    // Calls a method
    private static final Block.Getter AIR_GETTER = condGetter((x, y, z) -> Block.AIR);

    /** Stone floor with its top surface at y=64 (block layer y<=63), air above. */
    // Calls a method
    private static final Block.Getter FLOOR_GETTER = condGetter((x, y, z) -> y <= 63 ? Block.STONE : Block.AIR);

    /** Solid stone everywhere: maximal collision on every axis. */
    // Calls a method
    private static final Block.Getter DENSE_GETTER = condGetter((x, y, z) -> Block.STONE);

    /**
     * Stone floor (y<=62) topped by a layer of fences at y=63. Fences are multi-box, 1.5 tall shapes,
     * so this drives {@link ShapeImpl#intersectBoxSwept} over several boxes and the tall-below branch.
     */
    // Assigns a value
    private static final Block.Getter FENCE_GETTER = condGetter((x, y, z) -> {
        // Branch: checks a condition
        if (y <= 62) return Block.STONE;
        // Branch: checks a condition
        if (y == 63) return Block.OAK_FENCE;
        // Returns a value to the caller
        return Block.AIR;
    // End of a block/expression
    });

    // Getters that take a read lock per block lookup, mimicking the real ChunkCache cost (which the
    // plain lambda getters above do not capture). Used to measure the benefit of fewer/deduplicated
    // block lookups in the physics paths.
    // Calls a method
    private static final Block.Getter LOCKING_FLOOR_GETTER = lockingGetter((x, y, z) -> y <= 63 ? Block.STONE : Block.AIR);
    // Calls a method
    private static final Block.Getter LOCKING_DENSE_GETTER = lockingGetter((x, y, z) -> Block.STONE);
    // Calls a method
    private static final Block.Getter LOCKING_AIR_GETTER = lockingGetter((x, y, z) -> Block.AIR);

    // Cached "standing on the ground" physics result, primed in setup.
    // Code statement
    private Pos restPos;
    // Code statement
    private Vec restVelocity;
    // Code statement
    private PhysicsResult cachedResult;

    // Annotation for the following element
    @Setup
    // Start of a method/block
    public void setup() {
        // Prime the cached-physics fast path: rest an entity on the floor and feed the result
        // back until it stabilizes into a cacheable state.
        // Calls a method
        restPos = new Pos(0.5, 64.0, 0.5);
        // Calls a method
        restVelocity = new Vec(0, -AERO.gravity() * AERO.verticalAirResistance(), 0);
        // Assigns a value
        PhysicsResult result = null;
        // Loop: repeats a block
        for (int i = 0; i < 8; i++) {
            // Calls a method
            result = CollisionUtils.handlePhysics(FLOOR_GETTER, PLAYER_BB, restPos, restVelocity, result, false);
            // Calls a method
            restPos = result.newPosition();
        // End of a block/expression
        }
        // Access to the current/parent object
        this.cachedResult = result;
        // Branch: checks a condition
        if (!cachedResult.collisionY()) {
            // Throws an exception
            throw new IllegalStateException("Failed to prime resting state; collisionY expected to be true");
        // End of a block/expression
        }
    // End of a block/expression
    }

    // --- handlePhysics paths ---

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public PhysicsResult zeroVelocity() {
        // Returns a value to the caller
        return CollisionUtils.handlePhysics(FLOOR_GETTER, PLAYER_BB, restPos, Vec.ZERO, cachedResult, false);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public PhysicsResult cachedStanding() {
        // Returns a value to the caller
        return CollisionUtils.handlePhysics(FLOOR_GETTER, PLAYER_BB, restPos, restVelocity, cachedResult, false);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public PhysicsResult fallThroughAir() {
        // Returns a value to the caller
        return CollisionUtils.handlePhysics(AIR_GETTER, PLAYER_BB, new Pos(0.5, 100.0, 0.5),
                // Creates a new object
                new Vec(0, -0.4, 0), null, false);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public PhysicsResult fallIntoFloor() {
        // Returns a value to the caller
        return CollisionUtils.handlePhysics(FLOOR_GETTER, PLAYER_BB, new Pos(0.5, 64.3, 0.5),
                // Creates a new object
                new Vec(0, -0.4, 0), null, false);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public PhysicsResult walkOnFloor() {
        // Returns a value to the caller
        return CollisionUtils.handlePhysics(FLOOR_GETTER, PLAYER_BB, new Pos(0.5, 64.0, 0.5),
                // Creates a new object
                new Vec(0.2, -0.08, 0.15), null, false);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public PhysicsResult diagonalMove() {
        // Returns a value to the caller
        return CollisionUtils.handlePhysics(FLOOR_GETTER, PLAYER_BB, new Pos(0.5, 64.0, 0.5),
                // Creates a new object
                new Vec(1, 0, 1), null, false);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public PhysicsResult largeMoveSlow() {
        // length ~3.3, not diagonal -> slowPhysics ray-cast that crosses the floor
        // Returns a value to the caller
        return CollisionUtils.handlePhysics(FLOOR_GETTER, PLAYER_BB, new Pos(0.5, 67.0, 0.5),
                // Creates a new object
                new Vec(1.5, -2.5, 1.5), null, false);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public PhysicsResult fenceCollision() {
        // Standing atop the fence layer (top at y=64.5), walking into the adjacent fences.
        // Returns a value to the caller
        return CollisionUtils.handlePhysics(FENCE_GETTER, PLAYER_BB, new Pos(0.5, 64.5, 0.5),
                // Creates a new object
                new Vec(0.3, -0.08, 0.0), null, false);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public PhysicsResult denseCollision() {
        // Embedded in solid stone with a small velocity: collisions on all three axes,
        // multiple iterations of the stepPhysics while-loop.
        // Returns a value to the caller
        return CollisionUtils.handlePhysics(DENSE_GETTER, PLAYER_BB, new Pos(0.5, 64.5, 0.5),
                // Creates a new object
                new Vec(0.3, -0.3, 0.3), null, false);
    // End of a block/expression
    }

    // --- locking-getter variants: measure block-lookup cost (dedup benefit) ---

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public PhysicsResult walkOnFloorLocking() {
        // Returns a value to the caller
        return CollisionUtils.handlePhysics(LOCKING_FLOOR_GETTER, PLAYER_BB, new Pos(0.5, 64.0, 0.5),
                // Creates a new object
                new Vec(0.2, -0.08, 0.15), null, false);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public PhysicsResult denseCollisionLocking() {
        // Returns a value to the caller
        return CollisionUtils.handlePhysics(LOCKING_DENSE_GETTER, PLAYER_BB, new Pos(0.5, 64.5, 0.5),
                // Creates a new object
                new Vec(0.3, -0.3, 0.3), null, false);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public PhysicsResult fallThroughAirLocking() {
        // Returns a value to the caller
        return CollisionUtils.handlePhysics(LOCKING_AIR_GETTER, PLAYER_BB, new Pos(0.5, 100.0, 0.5),
                // Creates a new object
                new Vec(0, -0.4, 0), null, false);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public PhysicsResult largeMoveSlowLocking() {
        // Returns a value to the caller
        return CollisionUtils.handlePhysics(LOCKING_FLOOR_GETTER, PLAYER_BB, new Pos(0.5, 67.0, 0.5),
                // Creates a new object
                new Vec(1.5, -2.5, 1.5), null, false);
    // End of a block/expression
    }

    // --- simulateMovement paths (handlePhysics + world border + velocity update) ---

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public PhysicsResult simulateOnGround() {
        // Returns a value to the caller
        return PhysicsUtils.simulateMovement(new Pos(0.5, 64.0, 0.5), new Vec(0.1, 0, 0.1), PLAYER_BB,
                // Code statement
                BORDER, FLOOR_GETTER, AERO, false, true, true, false, null);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public PhysicsResult simulateFalling() {
        // Returns a value to the caller
        return PhysicsUtils.simulateMovement(new Pos(0.5, 100.0, 0.5), new Vec(0.1, -0.4, 0.1), PLAYER_BB,
                // Code statement
                BORDER, AIR_GETTER, AERO, false, true, false, false, null);
    // End of a block/expression
    }

    // --- helpers ---

    // Annotation for the following element
    @FunctionalInterface
    // Type declaration (class/interface/enum/record)
    private interface BlockAt {
        // Calls a method
        Block get(int x, int y, int z);
    // End of a block/expression
    }

    // Start of a method/block
    private static Block.Getter condGetter(BlockAt fn) {
        // Returns a value to the caller
        return (x, y, z, condition) -> fn.get(x, y, z);
    // End of a block/expression
    }

    // Start of a method/block
    private static Block.Getter lockingGetter(BlockAt fn) {
        // Calls a method
        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        // Returns a value to the caller
        return (x, y, z, condition) -> {
            // Calls a method
            lock.readLock().lock();
            // Exception handling
            try {
                // Returns a value to the caller
                return fn.get(x, y, z);
            // Start of a method/block
            } finally {
                // Calls a method
                lock.readLock().unlock();
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }
// End of a block/expression
}
