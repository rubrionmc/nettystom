// Package declaration for this file
package net.minestom.server.entity.pathfinding;

// Import of a required class
import net.minestom.server.collision.BoundingBox;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.LivingEntity;
// Import of a required class
import net.minestom.server.entity.pathfinding.followers.GroundNodeFollower;
// Import of a required class
import net.minestom.server.entity.pathfinding.followers.NodeFollower;
// Import of a required class
import net.minestom.server.entity.pathfinding.generators.GroundNodeGenerator;
// Import of a required class
import net.minestom.server.entity.pathfinding.generators.NodeGenerator;
// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.WorldBorder;
// Import of a required class
import net.minestom.server.network.packet.server.play.ParticlePacket;
// Import of a required class
import net.minestom.server.particle.Particle;
// Import of a required class
import net.minestom.server.utils.chunk.ChunkUtils;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.function.Supplier;

/**
 * Necessary object for all {@link NavigableEntity}.
 */
// Type declaration (class/interface/enum/record)
public final class Navigator {
    // Code statement
    private Point goalPosition;
    // Code statement
    private final Entity entity;

    // Essentially a double buffer. Wait until a path is done computing before replacing the old one.
    // Code statement
    private PPath computingPath;
    // Code statement
    private PPath path;

    // Code statement
    private double minimumDistance;

    // Calls a method
    NodeGenerator nodeGenerator = new GroundNodeGenerator();
    // Code statement
    private NodeFollower nodeFollower;

    // Start of a method/block
    public Navigator(Entity entity) {
        // Access to the current/parent object
        this.entity = entity;
        // Calls a method
        nodeFollower = new GroundNodeFollower(entity);
    // End of a block/expression
    }

    // Start of a method/block
    public PPath.State getState() {
        // Branch: checks a condition
        if (path == null && computingPath == null) return PPath.State.INVALID;
        // Branch: checks a condition
        if (path == null) return computingPath.getState();
        // Returns a value to the caller
        return path.getState();
    // End of a block/expression
    }

    // Start of a method/block
    public synchronized boolean setPathTo(@Nullable Point point) {
        // Calls a method
        BoundingBox bb = this.entity.getBoundingBox();
        // Calls a method
        double centerToCorner = Math.sqrt(bb.width() * bb.width() + bb.depth() * bb.depth()) / 2;
        // Returns a value to the caller
        return setPathTo(point, centerToCorner, null);
    // End of a block/expression
    }

    // Start of a method/block
    public synchronized boolean setPathTo(@Nullable Point point, double minimumDistance, @Nullable Runnable onComplete) {
        // Returns a value to the caller
        return setPathTo(point, minimumDistance, 50, 20, onComplete);
    // End of a block/expression
    }

    /**
     * Sets the path to {@code position} and ask the entity to follow the path.
     *
     * @param point           the position to find the path to, null to reset the pathfinder
     * @param minimumDistance distance to target when completed
     * @param maxDistance     maximum search distance
     * @param pathVariance    how far to search off of the direct path. For open worlds, this can be low (around 20) and for large mazes this needs to be very high.
     * @param onComplete      called when the path has been completed
     * @return true if a path is being generated
     */
    // Start of a method/block
    public synchronized boolean setPathTo(@Nullable Point point, double minimumDistance, double maxDistance, double pathVariance, @Nullable Runnable onComplete) {
        // Calls a method
        final Instance instance = entity.getInstance();
        // Branch: checks a condition
        if (point == null) {
            // Access to the current/parent object
            this.path = null;
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }

        // Can't path with a null instance.
        // Branch: checks a condition
        if (instance == null) {
            // Access to the current/parent object
            this.path = null;
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }

        // Can't path outside the world border
        // Calls a method
        final WorldBorder worldBorder = instance.getWorldBorder();
        // Branch: checks a condition
        if (!worldBorder.inBounds(point)) {
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
        // Can't path in an unloaded chunk
        // Calls a method
        final Chunk chunk = instance.getChunkAt(point);
        // Branch: checks a condition
        if (!ChunkUtils.isLoaded(chunk)) {
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }

        // Access to the current/parent object
        this.minimumDistance = minimumDistance;
        // Branch: checks a condition
        if (this.entity.getPosition().distance(point) < minimumDistance) {
            // Branch: checks a condition
            if (onComplete != null) onComplete.run();
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }

        // Branch: checks a condition
        if (point.sameBlock(entity.getPosition())) {
            // Branch: checks a condition
            if (onComplete != null) onComplete.run();
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }

        // Branch: checks a condition
        if (this.computingPath != null) this.computingPath.setState(PPath.State.TERMINATING);

        // Access to the current/parent object
        this.computingPath = PathGenerator.generate(instance,
                // Access to the current/parent object
                this.entity.getPosition(),
                // Code statement
                point,
                // Code statement
                minimumDistance, maxDistance,
                // Code statement
                pathVariance,
                // Access to the current/parent object
                this.entity.getBoundingBox(),
                // Access to the current/parent object
                this.entity.isOnGround(),
                // Access to the current/parent object
                this.nodeGenerator,
                // Code statement
                onComplete);

        // Access to the current/parent object
        this.goalPosition = point;
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public synchronized void tick() {
        // Branch: checks a condition
        if (goalPosition == null) return; // No path
        // Branch: checks a condition
        if (entity instanceof LivingEntity && ((LivingEntity) entity).isDead())
            // Returns a value to the caller
            return; // No pathfinding tick for dead entities
        // Branch: checks a condition
        if (computingPath != null && (computingPath.getState() == PPath.State.COMPUTED || computingPath.getState() == PPath.State.BEST_EFFORT)) {
            // Assigns a value
            path = computingPath;
            // Assigns a value
            computingPath = null;
        // End of a block/expression
        }

        // Branch: checks a condition
        if (path == null) return;

        // If the path is computed start following it
        // Branch: checks a condition
        if (path.getState() == PPath.State.COMPUTED || path.getState() == PPath.State.BEST_EFFORT) {
            // Calls a method
            path.setState(PPath.State.FOLLOWING);
            // Remove nodes that are too close to the start. Prevents doubling back to hit points that have already been hit
            // Loop: repeats a block
            for (int i = 0; i < path.getNodes().size(); i++) {
                // Branch: checks a condition
                if (isSameBlock(path.getNodes().get(i), entity.getPosition())) {
                    // Calls a method
                    path.getNodes().subList(0, i).clear();
                    // Breaks out of the loop/block
                    break;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // If the state is not following, wait until it is
        // Branch: checks a condition
        if (path.getState() != PPath.State.FOLLOWING) return;

        // If we're near the entity, we're done
        // Branch: checks a condition
        if (this.entity.getPosition().distance(goalPosition) < minimumDistance) {
            // Calls a method
            path.runComplete();
            // Assigns a value
            path = null;

            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        Point currentTarget = path.getCurrent();
        // Calls a method
        Point nextTarget = path.getNext();

        // Repath
        // Branch: checks a condition
        if (currentTarget == null || path.getCurrentType() == PNode.Type.REPATH || path.getCurrentType() == null) {
            // Branch: checks a condition
            if (computingPath != null && computingPath.getState() == PPath.State.CALCULATING) return;

            // Assigns a value
            computingPath = PathGenerator.generate(entity.getInstance(),
                    // Code statement
                    entity.getPosition(),
                    // Code statement
                    goalPosition.asPos(),
                    // Code statement
                    minimumDistance, path.maxDistance(),
                    // Calls a method
                    path.pathVariance(), entity.getBoundingBox(), this.entity.isOnGround(), nodeGenerator, null);

            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Branch: checks a condition
        if (nextTarget == null) {
            // Calls a method
            path.setState(PPath.State.INVALID);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        boolean nextIsRepath = nextTarget.sameBlock(Pos.ZERO);
        // Calls a method
        nodeFollower.moveTowards(currentTarget, nodeFollower.movementSpeed(), nextIsRepath ? currentTarget : nextTarget);

        // Branch: checks a condition
        if (nodeFollower.isAtPoint(currentTarget)) path.next();
        // Branch: checks a condition
        else if (path.getCurrentType() == PNode.Type.JUMP) nodeFollower.jump(currentTarget, nextTarget);
    // End of a block/expression
    }

    /**
     * Gets the target pathfinder position.
     *
     * @return the target pathfinder position, null if there is no one
     */
    // Start of a method/block
    public @Nullable Point getGoalPosition() {
        // Returns a value to the caller
        return goalPosition;
    // End of a block/expression
    }

    /**
     * Gets the entity which is navigating.
     *
     * @return the entity
     */
    // Start of a method/block
    public Entity getEntity() {
        // Returns a value to the caller
        return entity;
    // End of a block/expression
    }

    // Start of a method/block
    public void reset() {
        // Branch: checks a condition
        if (this.path != null) this.path.setState(PPath.State.TERMINATING);
        // Access to the current/parent object
        this.goalPosition = null;
        // Access to the current/parent object
        this.path = null;

        // Branch: checks a condition
        if (this.computingPath != null) this.computingPath.setState(PPath.State.TERMINATING);
        // Access to the current/parent object
        this.computingPath = null;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isComplete() {
        // Branch: checks a condition
        if (this.path == null) return true;
        // Returns a value to the caller
        return goalPosition == null || entity.getPosition().sameBlock(goalPosition);
    // End of a block/expression
    }

    // Start of a method/block
    public List<PNode> getNodes() {
        // Branch: checks a condition
        if (this.path == null && computingPath == null) return null;
        // Branch: checks a condition
        if (this.path == null) return computingPath.getNodes();
        // Returns a value to the caller
        return this.path.getNodes();
    // End of a block/expression
    }

    // Start of a method/block
    public Point getPathPosition() {
        // Returns a value to the caller
        return goalPosition;
    // End of a block/expression
    }

    // Start of a method/block
    public void setNodeFollower(Supplier<NodeFollower> nodeFollower) {
        // Access to the current/parent object
        this.nodeFollower = nodeFollower.get();
    // End of a block/expression
    }

    // Start of a method/block
    public void setNodeGenerator(Supplier<NodeGenerator> nodeGenerator) {
        // Access to the current/parent object
        this.nodeGenerator = nodeGenerator.get();
    // End of a block/expression
    }

    /**
     * Visualise path for debugging
     *
     * @param path the path to draw
     */
    // Start of a method/block
    private void drawPath(PPath path) {
        // Branch: checks a condition
        if (path == null) return;

        // Loop: repeats a block
        for (PNode point : path.getNodes()) {
            // Calls a method
            var packet = new ParticlePacket(Particle.COMPOSTER, point.x(), point.y() + 0.5, point.z(), 0, 0, 0, 0, 1);
            // Calls a method
            entity.sendPacketToViewers(packet);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static boolean isSameBlock(PNode pNode, Pos position) {
        // Returns a value to the caller
        return Math.floor(pNode.x()) == position.blockX() && Math.floor(pNode.y()) == position.blockY() && Math.floor(pNode.z()) == position.blockZ();
    // End of a block/expression
    }
// End of a block/expression
}
