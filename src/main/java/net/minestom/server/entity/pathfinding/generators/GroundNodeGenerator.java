// Déclaration du paquet de ce fichier
package net.minestom.server.entity.pathfinding.generators;

// Import d'une classe nécessaire
import net.minestom.server.collision.BoundingBox;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
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
public class GroundNodeGenerator implements NodeGenerator {
    // Affecte une valeur
    private PNode tempNode = null;
    // Appelle une méthode
    private final BoundingBox.PointIterator pointIterator = new BoundingBox.PointIterator();
    // Affecte une valeur
    private final static int MAX_FALL_DISTANCE = 5;

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<? extends PNode> getWalkable(Block.Getter getter, Set<PNode> visited, PNode current, Point goal, BoundingBox boundingBox) {
        // Affecte une valeur
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
                // Boucle : répète un bloc
                double cost = Math.sqrt(x * x + z * z) * 0.98;

                // Boucle : répète un bloc
                double floorPointX = current.blockX() + 0.5 + x;
                // Boucle : répète un bloc
                double floorPointY = current.blockY();
                // Boucle : répète un bloc
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
                    Point jumpPoint = new Vec(current.blockX() + 0.5 + x, current.blockY() + i, current.blockZ() + 0.5 + z);
                    // Appelle une méthode
                    OptionalDouble jumpPointY = gravitySnap(getter, jumpPoint.x(), jumpPoint.y(), jumpPoint.z(), boundingBox, MAX_FALL_DISTANCE);
                    // Embranchement : vérifie une condition
                    if (jumpPointY.isEmpty()) continue;
                    // Appelle une méthode
                    jumpPoint = jumpPoint.withY(jumpPointY.getAsDouble());

                    // Embranchement : vérifie une condition
                    if (!floorPoint.sameBlock(jumpPoint)) {
                        // Appelle une méthode
                        var nodeJump = createJump(getter, jumpPoint, boundingBox, cost + 0.2, current, goal, visited);
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
        var n = newNode(start, cost, point, goal);
        // Embranchement : vérifie une condition
        if (closed.contains(n)) return null;

        // Embranchement : vérifie une condition
        if (Math.abs(point.y() - start.y()) > Vec.EPSILON && point.y() < start.y()) {
            // Embranchement : vérifie une condition
            if (start.y() - point.y() > MAX_FALL_DISTANCE) return null;
            // Embranchement : vérifie une condition
            if (!canMoveTowards(getter, new Vec(start.x(), start.y(), start.z()), point.withY(start.y()), boundingBox))
                // Renvoie une valeur à l'appelant
                return null;
            // Appelle une méthode
            n.setType(PNode.Type.FALL);
        // Branche alternative de la condition
        } else {
            // Embranchement : vérifie une condition
            if (!canMoveTowards(getter, new Vec(start.x(), start.y(), start.z()), point, boundingBox)) return null;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return n;
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
        final double pointY = (int) Math.floor(pointOrgY);
        // Appelle une méthode
        final double pointZ = (int) Math.floor(pointOrgZ) + 0.5;

        //Chunk c = instance.getChunkAt(pointX, pointZ);
        //if (c == null) return OptionalDouble.of(pointY);

        // Boucle : répète un bloc
        for (int axis = 1; axis <= maxFall; ++axis) {
            // Appelle une méthode
            pointIterator.reset(boundingBox, pointX, pointY, pointZ, BoundingBox.AxisMask.Y, -axis);

            // Boucle : répète un bloc
            while (pointIterator.hasNext()) {
                // Appelle une méthode
                var block = pointIterator.next();
                // Embranchement : vérifie une condition
                if (getter.getBlock(block.blockX(), block.blockY(), block.blockZ(), Block.Getter.Condition.TYPE).isSolid()) {
                    // Renvoie une valeur à l'appelant
                    return OptionalDouble.of(block.blockY() + 1);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return OptionalDouble.empty();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
