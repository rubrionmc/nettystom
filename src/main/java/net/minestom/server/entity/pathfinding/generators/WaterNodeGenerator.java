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
public class WaterNodeGenerator implements NodeGenerator {
    // Affecte une valeur
    private PNode tempNode = null;
    // Appelle une méthode
    private final BoundingBox.PointIterator pointIterator = new BoundingBox.PointIterator();

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
                double currentLevelPointX = current.blockX() + 0.5 + x;
                // Boucle : répète un bloc
                double currentLevelPointY = current.blockY();
                // Boucle : répète un bloc
                double currentLevelPointZ = current.blockZ() + 0.5 + z;

                // Boucle : répète un bloc
                double upPointX = current.blockX() + 0.5 + x;
                // Boucle : répète un bloc
                double upPointY = current.blockY() + 1 + 0.5;
                // Boucle : répète un bloc
                double upPointZ = current.blockZ() + 0.5 + z;

                // Boucle : répète un bloc
                double downPointX = current.blockX() + 0.5 + x;
                // Boucle : répète un bloc
                double downPointY = current.blockY() - 1 + 0.5;
                // Boucle : répète un bloc
                double downPointZ = current.blockZ() + 0.5 + z;

                // Embranchement : vérifie une condition
                if (getter.getBlock((int) Math.floor(currentLevelPointX), (int) Math.floor(currentLevelPointY), (int) Math.floor(currentLevelPointZ)).compare(Block.WATER)) {
                    // Appelle une méthode
                    var nodeWalk = createFly(getter, new Vec(currentLevelPointX, currentLevelPointY, currentLevelPointZ), boundingBox, cost, current, goal, visited);
                    // Embranchement : vérifie une condition
                    if (nodeWalk != null && !visited.contains(nodeWalk)) nearby.add(nodeWalk);
                // Fin d'un bloc/d'une expression
                }

                // Embranchement : vérifie une condition
                if (getter.getBlock((int) Math.floor(upPointX), (int) Math.floor(upPointY), (int) Math.floor(upPointZ)).compare(Block.WATER)) {
                    // Appelle une méthode
                    var nodeJump = createFly(getter, new Vec(upPointX, upPointY, upPointZ), boundingBox, cost, current, goal, visited);
                    // Embranchement : vérifie une condition
                    if (nodeJump != null && !visited.contains(nodeJump)) nearby.add(nodeJump);
                // Fin d'un bloc/d'une expression
                }

                // Embranchement : vérifie une condition
                if (getter.getBlock((int) Math.floor(downPointX), (int) Math.floor(downPointY), (int) Math.floor(downPointZ)).compare(Block.WATER)) {
                    // Appelle une méthode
                    var nodeFall = createFly(getter, new Vec(downPointX, downPointY, downPointZ), boundingBox, cost, current, goal, visited);
                    // Embranchement : vérifie une condition
                    if (nodeFall != null && !visited.contains(nodeFall)) nearby.add(nodeFall);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Straight up
        // Boucle : répète un bloc
        double upPointX = current.x();
        // Boucle : répète un bloc
        double upPointY = current.blockY() + 1 + 0.5;
        // Boucle : répète un bloc
        double upPointZ = current.z();

        // Embranchement : vérifie une condition
        if (getter.getBlock((int) Math.floor(upPointX), (int) Math.floor(upPointY), (int) Math.floor(upPointZ)).compare(Block.WATER)) {
            // Appelle une méthode
            var nodeJump = createFly(getter, new Vec(current.x(), current.y(), current.z()), boundingBox, 2, current, goal, visited);
            // Embranchement : vérifie une condition
            if (nodeJump != null && !visited.contains(nodeJump)) nearby.add(nodeJump);
        // Fin d'un bloc/d'une expression
        }

        // Straight down
        // Boucle : répète un bloc
        double downPointX = current.x();
        // Boucle : répète un bloc
        double downPointY = current.blockY() - 1 + 0.5;
        // Boucle : répète un bloc
        double downPointZ = current.z();

        // Embranchement : vérifie une condition
        if (getter.getBlock((int) Math.floor(downPointX), (int) Math.floor(downPointY), (int) Math.floor(downPointZ)).compare(Block.WATER)) {
            // Appelle une méthode
            var nodeFall = createFly(getter, new Vec(downPointX, downPointY, downPointZ), boundingBox, 2, current, goal, visited);
            // Embranchement : vérifie une condition
            if (nodeFall != null && !visited.contains(nodeFall)) nearby.add(nodeFall);
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return nearby;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private PNode createFly(Block.Getter getter, Point point, BoundingBox boundingBox, double cost, PNode start, Point goal, Set<PNode> closed) {
        // Appelle une méthode
        var n = newNode(start, cost, point, goal);
        // Embranchement : vérifie une condition
        if (closed.contains(n)) return null;
        // Embranchement : vérifie une condition
        if (!canMoveTowards(getter, new Vec(start.x(), start.y(), start.z()), point, boundingBox)) return null;
        // Appelle une méthode
        n.setType(PNode.Type.FLY);
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
        return false;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public OptionalDouble gravitySnap(Block.Getter getter, double pointX, double pointY, double pointZ, BoundingBox boundingBox, double maxFall) {
        // Renvoie une valeur à l'appelant
        return OptionalDouble.of(pointY);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
