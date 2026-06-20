// Déclaration du paquet de ce fichier
package net.minestom.server.collision;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.function.Function;

// Déclaration de type (classe/interface/enum/record)
final class EntityCollision {
    // Début d'une méthode/d'un bloc
    static List<EntityCollisionResult> checkCollision(Instance instance, BoundingBox boundingBox, Point point, Vec entityVelocity, double extendRadius, Function<Entity, Boolean> entityFilter, @Nullable PhysicsResult physicsResult) {
        // Boucle : répète un bloc
        double minimumRes = physicsResult != null ? physicsResult.res().res : Double.MAX_VALUE;

        // Affecte une valeur
        List<EntityCollisionResult> result = new ArrayList<>();

        // Appelle une méthode
        var maxDistance = Math.pow(boundingBox.height() * boundingBox.height() + boundingBox.depth() / 2 * boundingBox.depth() / 2 + boundingBox.width() / 2 * boundingBox.width() / 2, 1 / 3.0);
        // Boucle : répète un bloc
        double projectileDistance = entityVelocity.length();

        // Boucle : répète un bloc
        for (Entity e : instance.getNearbyEntities(point, extendRadius + maxDistance + projectileDistance)) {
            // Appelle une méthode
            SweepResult sweepResult = new SweepResult(minimumRes, 0, 0, 0, null, 0, 0, 0, 0, 0, 0);

            // Embranchement : vérifie une condition
            if (!entityFilter.apply(e)) continue;
            // Embranchement : vérifie une condition
            if (!e.hasEntityCollision()) continue;

            // Overlapping with entity, math can't be done we return the entity
            // Embranchement : vérifie une condition
            if (e.getBoundingBox().intersectBox(e.getPosition().sub(point), boundingBox)) {
                // Appelle une méthode
                var p = point.asPos();
                // Appelle une méthode
                result.add(new EntityCollisionResult(p, e, Vec.ZERO, 0));
                // Passe à l'itération suivante de la boucle
                continue;
            // Fin d'un bloc/d'une expression
            }

            // Check collisions with entity
            // Appelle une méthode
            boolean intersected = e.getBoundingBox().intersectBoxSwept(point, entityVelocity, e.getPosition(), boundingBox, sweepResult);

            // Embranchement : vérifie une condition
            if (intersected && sweepResult.res < 1) {
                // Appelle une méthode
                var p = point.asPos().add(entityVelocity.mul(sweepResult.res));
                // Appelle une méthode
                Vec direction = new Vec(sweepResult.collidedPositionX, sweepResult.collidedPositionY, sweepResult.collidedPositionZ);
                // Appelle une méthode
                result.add(new EntityCollisionResult(p, e, direction, sweepResult.res));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
