// Déclaration du paquet de ce fichier
package net.minestom.server.entity.pathfinding;

// Import d'une classe nécessaire
import net.minestom.server.collision.BoundingBox;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.LivingEntity;
// Import d'une classe nécessaire
import net.minestom.server.entity.pathfinding.followers.GroundNodeFollower;
// Import d'une classe nécessaire
import net.minestom.server.entity.pathfinding.followers.NodeFollower;
// Import d'une classe nécessaire
import net.minestom.server.entity.pathfinding.generators.GroundNodeGenerator;
// Import d'une classe nécessaire
import net.minestom.server.entity.pathfinding.generators.NodeGenerator;
// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.WorldBorder;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.ParticlePacket;
// Import d'une classe nécessaire
import net.minestom.server.particle.Particle;
// Import d'une classe nécessaire
import net.minestom.server.utils.chunk.ChunkUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.function.Supplier;

/**
 * Necessary object for all {@link NavigableEntity}.
 */
// Déclaration de type (classe/interface/enum/record)
public final class Navigator {
    // Instruction de code
    private Point goalPosition;
    // Instruction de code
    private final Entity entity;

    // Essentially a double buffer. Wait until a path is done computing before replacing the old one.
    // Instruction de code
    private PPath computingPath;
    // Instruction de code
    private PPath path;

    // Instruction de code
    private double minimumDistance;

    // Appelle une méthode
    NodeGenerator nodeGenerator = new GroundNodeGenerator();
    // Instruction de code
    private NodeFollower nodeFollower;

    // Début d'une méthode/d'un bloc
    public Navigator(Entity entity) {
        // Accès à l'objet courant/parent
        this.entity = entity;
        // Appelle une méthode
        nodeFollower = new GroundNodeFollower(entity);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public PPath.State getState() {
        // Embranchement : vérifie une condition
        if (path == null && computingPath == null) return PPath.State.INVALID;
        // Embranchement : vérifie une condition
        if (path == null) return computingPath.getState();
        // Renvoie une valeur à l'appelant
        return path.getState();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public synchronized boolean setPathTo(@Nullable Point point) {
        // Appelle une méthode
        BoundingBox bb = this.entity.getBoundingBox();
        // Appelle une méthode
        double centerToCorner = Math.sqrt(bb.width() * bb.width() + bb.depth() * bb.depth()) / 2;
        // Renvoie une valeur à l'appelant
        return setPathTo(point, centerToCorner, null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public synchronized boolean setPathTo(@Nullable Point point, double minimumDistance, @Nullable Runnable onComplete) {
        // Renvoie une valeur à l'appelant
        return setPathTo(point, minimumDistance, 50, 20, onComplete);
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public synchronized boolean setPathTo(@Nullable Point point, double minimumDistance, double maxDistance, double pathVariance, @Nullable Runnable onComplete) {
        // Appelle une méthode
        final Instance instance = entity.getInstance();
        // Embranchement : vérifie une condition
        if (point == null) {
            // Accès à l'objet courant/parent
            this.path = null;
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }

        // Can't path with a null instance.
        // Embranchement : vérifie une condition
        if (instance == null) {
            // Accès à l'objet courant/parent
            this.path = null;
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }

        // Can't path outside the world border
        // Appelle une méthode
        final WorldBorder worldBorder = instance.getWorldBorder();
        // Embranchement : vérifie une condition
        if (!worldBorder.inBounds(point)) {
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
        // Can't path in an unloaded chunk
        // Appelle une méthode
        final Chunk chunk = instance.getChunkAt(point);
        // Embranchement : vérifie une condition
        if (!ChunkUtils.isLoaded(chunk)) {
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }

        // Accès à l'objet courant/parent
        this.minimumDistance = minimumDistance;
        // Embranchement : vérifie une condition
        if (this.entity.getPosition().distance(point) < minimumDistance) {
            // Embranchement : vérifie une condition
            if (onComplete != null) onComplete.run();
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (point.sameBlock(entity.getPosition())) {
            // Embranchement : vérifie une condition
            if (onComplete != null) onComplete.run();
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (this.computingPath != null) this.computingPath.setState(PPath.State.TERMINATING);

        // Accès à l'objet courant/parent
        this.computingPath = PathGenerator.generate(instance,
                // Accès à l'objet courant/parent
                this.entity.getPosition(),
                // Instruction de code
                point,
                // Instruction de code
                minimumDistance, maxDistance,
                // Instruction de code
                pathVariance,
                // Accès à l'objet courant/parent
                this.entity.getBoundingBox(),
                // Accès à l'objet courant/parent
                this.entity.isOnGround(),
                // Accès à l'objet courant/parent
                this.nodeGenerator,
                // Instruction de code
                onComplete);

        // Accès à l'objet courant/parent
        this.goalPosition = point;
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public synchronized void tick() {
        // Embranchement : vérifie une condition
        if (goalPosition == null) return; // No path
        // Embranchement : vérifie une condition
        if (entity instanceof LivingEntity && ((LivingEntity) entity).isDead())
            // Renvoie une valeur à l'appelant
            return; // No pathfinding tick for dead entities
        // Embranchement : vérifie une condition
        if (computingPath != null && (computingPath.getState() == PPath.State.COMPUTED || computingPath.getState() == PPath.State.BEST_EFFORT)) {
            // Affecte une valeur
            path = computingPath;
            // Affecte une valeur
            computingPath = null;
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (path == null) return;

        // If the path is computed start following it
        // Embranchement : vérifie une condition
        if (path.getState() == PPath.State.COMPUTED || path.getState() == PPath.State.BEST_EFFORT) {
            // Appelle une méthode
            path.setState(PPath.State.FOLLOWING);
            // Remove nodes that are too close to the start. Prevents doubling back to hit points that have already been hit
            // Boucle : répète un bloc
            for (int i = 0; i < path.getNodes().size(); i++) {
                // Embranchement : vérifie une condition
                if (isSameBlock(path.getNodes().get(i), entity.getPosition())) {
                    // Appelle une méthode
                    path.getNodes().subList(0, i).clear();
                    // Interrompt la boucle/le bloc
                    break;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // If the state is not following, wait until it is
        // Embranchement : vérifie une condition
        if (path.getState() != PPath.State.FOLLOWING) return;

        // If we're near the entity, we're done
        // Embranchement : vérifie une condition
        if (this.entity.getPosition().distance(goalPosition) < minimumDistance) {
            // Appelle une méthode
            path.runComplete();
            // Affecte une valeur
            path = null;

            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        Point currentTarget = path.getCurrent();
        // Appelle une méthode
        Point nextTarget = path.getNext();

        // Repath
        // Embranchement : vérifie une condition
        if (currentTarget == null || path.getCurrentType() == PNode.Type.REPATH || path.getCurrentType() == null) {
            // Embranchement : vérifie une condition
            if (computingPath != null && computingPath.getState() == PPath.State.CALCULATING) return;

            // Affecte une valeur
            computingPath = PathGenerator.generate(entity.getInstance(),
                    // Instruction de code
                    entity.getPosition(),
                    // Instruction de code
                    goalPosition.asPos(),
                    // Instruction de code
                    minimumDistance, path.maxDistance(),
                    // Appelle une méthode
                    path.pathVariance(), entity.getBoundingBox(), this.entity.isOnGround(), nodeGenerator, null);

            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (nextTarget == null) {
            // Appelle une méthode
            path.setState(PPath.State.INVALID);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        boolean nextIsRepath = nextTarget.sameBlock(Pos.ZERO);
        // Appelle une méthode
        nodeFollower.moveTowards(currentTarget, nodeFollower.movementSpeed(), nextIsRepath ? currentTarget : nextTarget);

        // Embranchement : vérifie une condition
        if (nodeFollower.isAtPoint(currentTarget)) path.next();
        // Embranchement : vérifie une condition
        else if (path.getCurrentType() == PNode.Type.JUMP) nodeFollower.jump(currentTarget, nextTarget);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the target pathfinder position.
     *
     * @return the target pathfinder position, null if there is no one
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Point getGoalPosition() {
        // Renvoie une valeur à l'appelant
        return goalPosition;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the entity which is navigating.
     *
     * @return the entity
     */
    // Début d'une méthode/d'un bloc
    public Entity getEntity() {
        // Renvoie une valeur à l'appelant
        return entity;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void reset() {
        // Embranchement : vérifie une condition
        if (this.path != null) this.path.setState(PPath.State.TERMINATING);
        // Accès à l'objet courant/parent
        this.goalPosition = null;
        // Accès à l'objet courant/parent
        this.path = null;

        // Embranchement : vérifie une condition
        if (this.computingPath != null) this.computingPath.setState(PPath.State.TERMINATING);
        // Accès à l'objet courant/parent
        this.computingPath = null;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isComplete() {
        // Embranchement : vérifie une condition
        if (this.path == null) return true;
        // Renvoie une valeur à l'appelant
        return goalPosition == null || entity.getPosition().sameBlock(goalPosition);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public List<PNode> getNodes() {
        // Embranchement : vérifie une condition
        if (this.path == null && computingPath == null) return null;
        // Embranchement : vérifie une condition
        if (this.path == null) return computingPath.getNodes();
        // Renvoie une valeur à l'appelant
        return this.path.getNodes();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Point getPathPosition() {
        // Renvoie une valeur à l'appelant
        return goalPosition;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setNodeFollower(Supplier<NodeFollower> nodeFollower) {
        // Accès à l'objet courant/parent
        this.nodeFollower = nodeFollower.get();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setNodeGenerator(Supplier<NodeGenerator> nodeGenerator) {
        // Accès à l'objet courant/parent
        this.nodeGenerator = nodeGenerator.get();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Visualise path for debugging
     *
     * @param path the path to draw
     */
    // Début d'une méthode/d'un bloc
    private void drawPath(PPath path) {
        // Embranchement : vérifie une condition
        if (path == null) return;

        // Boucle : répète un bloc
        for (PNode point : path.getNodes()) {
            // Appelle une méthode
            var packet = new ParticlePacket(Particle.COMPOSTER, point.x(), point.y() + 0.5, point.z(), 0, 0, 0, 0, 1);
            // Appelle une méthode
            entity.sendPacketToViewers(packet);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static boolean isSameBlock(PNode pNode, Pos position) {
        // Renvoie une valeur à l'appelant
        return Math.floor(pNode.x()) == position.blockX() && Math.floor(pNode.y()) == position.blockY() && Math.floor(pNode.z()) == position.blockZ();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
