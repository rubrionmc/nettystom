// Déclaration du paquet de ce fichier
package net.minestom.server.event.entity;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityProjectile;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.EntityInstanceEvent;

/**
 * Called with {@link EntityProjectile#shoot(Point, double, double)}
 */
// Déclaration de type (classe/interface/enum/record)
public class EntityShootEvent implements EntityInstanceEvent, CancellableEvent {

    // Instruction de code
    private final Entity entity;
    // Instruction de code
    private final Entity projectile;
    // Instruction de code
    private final Point to;
    // Instruction de code
    private double power;
    // Instruction de code
    private double spread;

    // Instruction de code
    private boolean cancelled;

    // Début d'une méthode/d'un bloc
    public EntityShootEvent(Entity entity, Entity projectile, Point to, double power, double spread) {
        // Accès à l'objet courant/parent
        this.entity = entity;
        // Accès à l'objet courant/parent
        this.projectile = projectile;
        // Accès à l'objet courant/parent
        this.to = to;
        // Accès à l'objet courant/parent
        this.power = power;
        // Accès à l'objet courant/parent
        this.spread = spread;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the projectile.
     *
     * @return the projectile.
     */
    // Début d'une méthode/d'un bloc
    public Entity getProjectile() {
        // Renvoie une valeur à l'appelant
        return this.projectile;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the position projectile was shot to.
     *
     * @return the position projectile was shot to.
     */
    // Début d'une méthode/d'un bloc
    public Point getTo() {
        // Renvoie une valeur à l'appelant
        return this.to;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets shot spread.
     *
     * @return shot spread.
     */
    // Début d'une méthode/d'un bloc
    public double getSpread() {
        // Renvoie une valeur à l'appelant
        return this.spread;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets shot spread.
     *
     * @param spread shot spread.
     */
    // Début d'une méthode/d'un bloc
    public void setSpread(double spread) {
        // Accès à l'objet courant/parent
        this.spread = spread;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets shot power.
     *
     * @return shot power.
     */
    // Début d'une méthode/d'un bloc
    public double getPower() {
        // Renvoie une valeur à l'appelant
        return this.power;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets shot power.
     *
     * @param power shot power.
     */
    // Début d'une méthode/d'un bloc
    public void setPower(double power) {
        // Accès à l'objet courant/parent
        this.power = power;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isCancelled() {
        // Renvoie une valeur à l'appelant
        return this.cancelled;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setCancelled(boolean cancel) {
        // Accès à l'objet courant/parent
        this.cancelled = cancel;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Entity getEntity() {
        // Renvoie une valeur à l'appelant
        return entity;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
