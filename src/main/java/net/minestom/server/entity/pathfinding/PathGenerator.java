// Déclaration du paquet de ce fichier
package net.minestom.server.entity.pathfinding;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.objects.ObjectHeapPriorityQueue;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.objects.ObjectOpenHashBigSet;
// Import d'une classe nécessaire
import net.minestom.server.collision.BoundingBox;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.pathfinding.generators.NodeGenerator;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.*;

// Déclaration de type (classe/interface/enum/record)
public final class PathGenerator {
    // Appelle une méthode
    private static final Comparator<PNode> pNodeComparator = (s1, s2) -> (int) (((s1.g() + s1.h()) - (s2.g() + s2.h())) * 1000);

    // Instruction de code
    public static PPath generate(Block.Getter getter, Pos orgStart, Point orgTarget,
                                          // Boucle : répète un bloc
                                          double closeDistance, double maxDistance, double pathVariance,
                                          // Instruction de code
                                          BoundingBox boundingBox, boolean isOnGround, NodeGenerator generator,
                                          // Annotation pour l'élément suivant
                                          @Nullable Runnable onComplete) {
        // Affecte une valeur
        final Point start = (!isOnGround && generator.hasGravitySnap())
                // Instruction de code
                ? orgStart.withY(generator.gravitySnap(getter, orgStart.x(), orgStart.y(), orgStart.z(), boundingBox, 100).orElse(orgStart.y()))
                // Instruction de code
                : orgStart;

        // Affecte une valeur
        final Point target = (generator.hasGravitySnap())
                // Instruction de code
                ? orgTarget.withY(generator.gravitySnap(getter, orgTarget.x(), orgTarget.y(), orgTarget.z(), boundingBox, 100).orElse(orgTarget.y()))
                // Appelle une méthode
                : orgTarget.asPos();

        // Appelle une méthode
        PPath path = new PPath(maxDistance, pathVariance, onComplete);
        // Appelle une méthode
        computePath(getter, start, target, closeDistance, maxDistance, pathVariance, boundingBox, path, generator);
        // Renvoie une valeur à l'appelant
        return path;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static PNode buildRepathNode(PNode parent) {
        // Renvoie une valeur à l'appelant
        return new PNode(0, 0, 0, 0, 0, PNode.Type.REPATH, parent);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static void computePath(Block.Getter getter, Point start, Point target,
                                    // Boucle : répète un bloc
                                    double closeDistance, double maxDistance, double pathVariance,
                                    // Début d'une méthode/d'un bloc
                                    BoundingBox boundingBox, PPath path, NodeGenerator generator) {
        // Boucle : répète un bloc
        double closestDistance = Double.MAX_VALUE;
        // Boucle : répète un bloc
        double straightDistance = generator.heuristic(start, target);
        // Appelle une méthode
        int maxSize = (int) Math.floor(maxDistance * 10);

        // Appelle une méthode
        closeDistance = Math.max(0.8, closeDistance);
        // Appelle une méthode
        List<PNode> closestFoundNodes = List.of();

        // Appelle une méthode
        PNode pStart = new PNode(start, 0, generator.heuristic(start, target), PNode.Type.WALK, null);

        // Affecte une valeur
        ObjectHeapPriorityQueue<PNode> open = new ObjectHeapPriorityQueue<>(pNodeComparator);
        // Appelle une méthode
        open.enqueue(pStart);

        // Affecte une valeur
        Set<PNode> closed = new ObjectOpenHashBigSet<>(maxSize);

        // Boucle : répète un bloc
        while (!open.isEmpty() && closed.size() < maxSize) {
            // Embranchement : vérifie une condition
            if (path.getState() == PPath.State.TERMINATING) {
                // Appelle une méthode
                path.setState(PPath.State.TERMINATED);
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            PNode current = open.dequeue();

            //var chunk = instance.getChunkAt(current.x(), current.z());
            //if (chunk == null) continue;
            //if (!chunk.isLoaded()) continue;

            // Embranchement : vérifie une condition
            if (((current.g() + current.h()) - straightDistance) > pathVariance) continue;
            // Embranchement : vérifie une condition
            if (!withinDistance(current, start, maxDistance)) continue;
            // Embranchement : vérifie une condition
            if (withinDistance(current, target, closeDistance)) {
                // Appelle une méthode
                open.enqueue(current);
                // Interrompt la boucle/le bloc
                break;
            // Fin d'un bloc/d'une expression
            }

            // Embranchement : vérifie une condition
            if (current.h() < closestDistance) {
                // Appelle une méthode
                closestDistance = current.h();
                // Appelle une méthode
                closestFoundNodes = List.of(current);
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            Collection<? extends PNode> found = generator.getWalkable(getter, closed, current, target, boundingBox);
            // Début d'une méthode/d'un bloc
            found.forEach(p -> {
                // Embranchement : vérifie une condition
                if (getDistanceSquared(p.x(), p.y(), p.z(), start) <= (maxDistance * maxDistance)) {
                    // Appelle une méthode
                    open.enqueue(p);
                    // Appelle une méthode
                    closed.add(p);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        PNode current = open.isEmpty() ? null : open.dequeue();

        // Embranchement : vérifie une condition
        if (current == null || !withinDistance(current, target, closeDistance)) {
            // Embranchement : vérifie une condition
            if (closestFoundNodes.isEmpty()) {
                // Appelle une méthode
                path.setState(PPath.State.INVALID);
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            current = closestFoundNodes.getFirst();

            // Embranchement : vérifie une condition
            if (!open.isEmpty()) {
                // Appelle une méthode
                current = buildRepathNode(current);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Boucle : répète un bloc
        while (current.parent() != null) {
            // Appelle une méthode
            path.getNodes().add(current);
            // Appelle une méthode
            current = current.parent();
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        Collections.reverse(path.getNodes());

        // Embranchement : vérifie une condition
        if (path.getCurrentType() == PNode.Type.REPATH) {
            // Appelle une méthode
            path.setState(PPath.State.INVALID);
            // Appelle une méthode
            path.getNodes().clear();
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (path.getNodes().isEmpty()) {
            // Appelle une méthode
            path.setState(PPath.State.INVALID);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        var lastNode = path.getNodes().getLast();
        // Embranchement : vérifie une condition
        if (getDistanceSquared(lastNode.x(), lastNode.y(), lastNode.z(), target) > (closeDistance * closeDistance)) {
            // Appelle une méthode
            path.setState(PPath.State.BEST_EFFORT);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        PNode pEnd = new PNode(target, 0, 0, PNode.Type.WALK, null);
        // Appelle une méthode
        path.getNodes().add(pEnd);
        // Appelle une méthode
        path.setState(PPath.State.COMPUTED);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static boolean withinDistance(PNode point, Point target, double closeDistance) {
        // Renvoie une valeur à l'appelant
        return getDistanceSquared(point.x(), point.y(), point.z(), target) < (closeDistance * closeDistance);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static double getDistanceSquared(double x, double y, double z, Point target) {
        // Boucle : répète un bloc
        double dx = x - target.x();
        // Boucle : répète un bloc
        double dy = y - target.y();
        // Boucle : répète un bloc
        double dz = z - target.z();
        // Renvoie une valeur à l'appelant
        return dx * dx + dy * dy + dz * dz;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
