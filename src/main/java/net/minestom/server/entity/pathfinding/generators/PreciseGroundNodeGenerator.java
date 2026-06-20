// Déclaration du paquet de ce fichier
package net.minestom.server.entity.pathfinding.generators;

// Import d'une classe nécessaire
import net.minestom.server.collision.BoundingBox;
// Import d'une classe nécessaire
import net.minestom.server.collision.CollisionUtils;
// Import d'une classe nécessaire
import net.minestom.server.collision.PhysicsResult;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.pathfinding.PNode;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.OptionalDouble;
// Import d'une classe nécessaire
import java.util.Set;

// Déclaration de type (classe/interface/enum/record)
public class PreciseGroundNodeGenerator implements NodeGenerator {
    // Affecte une valeur
    private PNode tempNode = null;
    // Affecte une valeur
    private final static int MAX_FALL_DISTANCE = 5;

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<? extends PNode> getWalkable(Block.Getter getter, Set<PNode> visited, PNode current, Point goal, BoundingBox boundingBox) {
        // Appelle une méthode
        Collection<PNode> nearby = new ArrayList<>();
        // Appelle une méthode
        tempNode = new PNode(0, 0, 0, 0, 0, current);

        // Appelle une méthode
        int stepSize = (int) Math.max(Math.floor(boundingBox.width() / 2), 1);
        // Embranchement : vérifie une condition
        if (stepSize < 1) stepSize = 1;

        // Boucle : répète un bloc
        for (int x = -stepSize; x <= stepSize; ++x) {
            // Boucle : répète un bloc
            for (int z = -stepSize; z <= stepSize; ++z) {
                // Embranchement : vérifie une condition
                if (x == 0 && z == 0) continue;
                // Appelle une méthode
                double cost = Math.sqrt(x * x + z * z) * 0.98;

                // Appelle une méthode
                double floorPointX = current.blockX() + 0.5 + x;
                // Appelle une méthode
                double floorPointY = current.y();
                // Appelle une méthode
                double floorPointZ = current.blockZ() + 0.5 + z;

                // Appelle une méthode
                var optionalFloorPointY = gravitySnap(getter, floorPointX, floorPointY, floorPointZ, boundingBox, MAX_FALL_DISTANCE);
                // Embranchement : vérifie une condition
                if (optionalFloorPointY.isEmpty()) continue;
                // Appelle une méthode
                floorPointY = optionalFloorPointY.getAsDouble();

                // Appelle une méthode
                var floorPoint = new Vec(floorPointX, floorPointY, floorPointZ);
                // Appelle une méthode
                var nodeWalk = createWalk(getter, floorPoint, boundingBox, cost, current, goal, visited);

                // Embranchement : vérifie une condition
                if (nodeWalk != null && !visited.contains(nodeWalk)) nearby.add(nodeWalk);

                // Boucle : répète un bloc
                for (int i = 1; i <= 1; ++i) {
                    // Appelle une méthode
                    Point jumpPoint = new Vec(current.blockX() + 0.5 + x, current.y() + i, current.blockZ() + 0.5 + z);
                    // Appelle une méthode
                    OptionalDouble jumpPointY = gravitySnap(getter, jumpPoint.x(), jumpPoint.y(), jumpPoint.z(), boundingBox, MAX_FALL_DISTANCE);
                    // Embranchement : vérifie une condition
                    if (jumpPointY.isEmpty()) continue;
                    // Appelle une méthode
                    jumpPoint = jumpPoint.withY(jumpPointY.getAsDouble());

                    // Embranchement : vérifie une condition
                    if (!floorPoint.sameBlock(jumpPoint)) {
                        // Appelle une méthode
                        var nodeJump = createJump(getter, jumpPoint, boundingBox, cost + 0.8, current, goal, visited);
                        // Embranchement : vérifie une condition
                        if (nodeJump != null && !visited.contains(nodeJump)) nearby.add(nodeJump);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return nearby;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private PNode createWalk(Block.Getter getter, Point point, BoundingBox boundingBox, double cost, PNode start, Point goal, Set<PNode> closed) {
        // Appelle une méthode
        var snapped = gravitySnap(getter, point.x(), point.y(), point.z(), boundingBox, MAX_FALL_DISTANCE);

        // Embranchement : vérifie une condition
        if (snapped.isPresent()) {
            // Appelle une méthode
            var snappedPoint = new Vec(point.x(), snapped.getAsDouble(), point.z());

            // Appelle une méthode
            var n = newNode(start, cost, snappedPoint, goal);
            // Embranchement : vérifie une condition
            if (closed.contains(n)) {
                // Renvoie une valeur à l'appelant
                return null;
            // Fin d'un bloc/d'une expression
            }

            // Embranchement : vérifie une condition
            if (Math.abs(snappedPoint.y() - start.y()) > Vec.EPSILON && snappedPoint.y() < start.y()) {
                // Embranchement : vérifie une condition
                if (start.y() - snappedPoint.y() > MAX_FALL_DISTANCE) {
                    // Renvoie une valeur à l'appelant
                    return null;
                // Fin d'un bloc/d'une expression
                }
                // Embranchement : vérifie une condition
                if (!canMoveTowards(getter, new Vec(start.x(), start.y(), start.z()), snappedPoint.withY(start.y()), boundingBox)) {
                    // Renvoie une valeur à l'appelant
                    return null;
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                n.setType(PNode.Type.FALL);
            // Branche alternative de la condition
            } else {
                // Embranchement : vérifie une condition
                if (!canMoveTowards(getter, new Vec(start.x(), start.y(), start.z()), snappedPoint, boundingBox)) {
                    // Renvoie une valeur à l'appelant
                    return null;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // Renvoie une valeur à l'appelant
            return n;
        // Branche alternative de la condition
        } else {
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private PNode createJump(Block.Getter getter, Point point, BoundingBox boundingBox, double cost, PNode start, Point goal, Set<PNode> closed) {
        // Embranchement : vérifie une condition
        if (Math.abs(point.y() - start.y()) < Vec.EPSILON) return null;
        // Embranchement : vérifie une condition
        if (point.y() - start.y() > 2) return null;
        // Embranchement : vérifie une condition
        if (point.blockX() != start.blockX() && point.blockZ() != start.blockZ()) return null;

        // Appelle une méthode
        var n = newNode(start, cost, point, goal);
        // Embranchement : vérifie une condition
        if (closed.contains(n)) return null;

        // Embranchement : vérifie une condition
        if (pointInvalid(getter, point, boundingBox)) return null;
        // Embranchement : vérifie une condition
        if (pointInvalid(getter, new Vec(start.x(), start.y() + 1, start.z()), boundingBox)) return null;

        // Appelle une méthode
        n.setType(PNode.Type.JUMP);
        // Renvoie une valeur à l'appelant
        return n;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private PNode newNode(PNode current, double cost, Point point, Point goal) {
        // Appelle une méthode
        tempNode.setG(current.g() + cost);
        // Appelle une méthode
        tempNode.setH(heuristic(point, goal));
        // Appelle une méthode
        tempNode.setPoint(point.x(), point.y(), point.z());

        // Affecte une valeur
        var newNode = tempNode;
        // Appelle une méthode
        tempNode = new PNode(0, 0, 0, 0, 0, PNode.Type.WALK, current);

        // Renvoie une valeur à l'appelant
        return newNode;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean hasGravitySnap() {
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public OptionalDouble gravitySnap(Block.Getter getter, double pointOrgX, double pointOrgY, double pointOrgZ, BoundingBox boundingBox, double maxFall) {
        // Appelle une méthode
        final double pointX = (int) Math.floor(pointOrgX) + 0.5;
        // Appelle une méthode
        final double pointZ = (int) Math.floor(pointOrgZ) + 0.5;
        // Affecte une valeur
        final PhysicsResult res = CollisionUtils.handlePhysics(getter, boundingBox,
                // Crée un nouvel objet
                new Pos(pointX, pointOrgY, pointZ), new Vec(0, -MAX_FALL_DISTANCE, 0),
                // Instruction de code
                null, true);
        // Renvoie une valeur à l'appelant
        return OptionalDouble.of(res.newPosition().y());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean canMoveTowards(Block.Getter getter, Point startOrg, Point endOrg, BoundingBox boundingBox) {
        // Appelle une méthode
        final Point end = endOrg.add(0, Vec.EPSILON, 0);
        // Appelle une méthode
        final Point start = startOrg.add(0, Vec.EPSILON, 0);
        // Appelle une méthode
        final Point diff = end.sub(start);
        // Appelle une méthode
        PhysicsResult res = CollisionUtils.handlePhysics(getter, boundingBox, start.asPos(), diff.asVec(), null, false);
        // Renvoie une valeur à l'appelant
        return !res.collisionZ() && !res.collisionY() && !res.collisionX();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
