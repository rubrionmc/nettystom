// Déclaration du paquet de ce fichier
package net.minestom.server.entity.pathfinding.followers;

// Import d'une classe nécessaire
import net.minestom.server.collision.CollisionUtils;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.LivingEntity;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.Attribute;
// Import d'une classe nécessaire
import net.minestom.server.utils.position.PositionUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class FlyingNodeFollower implements NodeFollower {
    // Instruction de code
    private final Entity entity;

    // Début d'une méthode/d'un bloc
    public FlyingNodeFollower(Entity entity) {
        // Accès à l'objet courant/parent
        this.entity = entity;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Used to move the entity toward {@code direction} in the X and Z axis
     * Gravity is still applied but the entity will not attempt to jump
     * Also update the yaw/pitch of the entity to look along 'direction'
     *
     * @param direction the targeted position
     * @param speed     define how far the entity will move
     */
    // Début d'une méthode/d'un bloc
    public void moveTowards(Point direction, double speed, Point lookAt) {
        // Appelle une méthode
        final Pos position = entity.getPosition();
        // Appelle une méthode
        final double dx = direction.x() - position.x();
        // Appelle une méthode
        final double dy = direction.y() - position.y();
        // Appelle une méthode
        final double dz = direction.z() - position.z();

        // Appelle une méthode
        final double dxLook = lookAt.x() - position.x();
        // Appelle une méthode
        final double dyLook = lookAt.y() - position.y();
        // Appelle une méthode
        final double dzLook = lookAt.z() - position.z();

        // the purpose of these few lines is to slow down entities when they reach their destination
        // Affecte une valeur
        final double distSquared = dx * dx + dy * dy + dz * dz;
        // Embranchement : vérifie une condition
        if (speed > distSquared) {
            // Affecte une valeur
            speed = distSquared;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final double radians = Math.atan2(dz, dx);
        // Appelle une méthode
        final double speedX = Math.cos(radians) * speed;
        // Appelle une méthode
        final double speedZ = Math.sin(radians) * speed;
        // Appelle une méthode
        final float yaw = PositionUtils.getLookYaw(dxLook, dzLook);
        // Appelle une méthode
        final float pitch = PositionUtils.getLookPitch(dxLook, dyLook, dzLook);

        // Appelle une méthode
        double speedY = Math.signum(dy) * 0.5 * speed;
        // Embranchement : vérifie une condition
        if (Math.min(Math.abs(dy), Math.abs(speedY)) == Math.abs(dy)) {
            // Affecte une valeur
            speedY = dy;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final var physicsResult = CollisionUtils.handlePhysics(entity, new Vec(speedX, speedY, speedZ));
        // Accès à l'objet courant/parent
        this.entity.refreshPosition(physicsResult.newPosition().asPos().withView(yaw, pitch));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void jump(@Nullable Point point, @Nullable Point target) {
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isAtPoint(Point point) {
        // Renvoie une valeur à l'appelant
        return entity.getPosition().sameBlock(point);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public double movementSpeed() {
        // Embranchement : vérifie une condition
        if (entity instanceof LivingEntity living) {
            // Renvoie une valeur à l'appelant
            return living.getAttribute(Attribute.MOVEMENT_SPEED).getValue();
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return 0.1f;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
