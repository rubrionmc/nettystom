// Package declaration for this file
package net.minestom.server.event.entity;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityProjectile;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.EntityInstanceEvent;

/**
 * Called with {@link EntityProjectile#shoot(Point, double, double)}
 */
// Type declaration (class/interface/enum/record)
public class EntityShootEvent implements EntityInstanceEvent, CancellableEvent {

    // Code statement
    private final Entity entity;
    // Code statement
    private final Entity projectile;
    // Code statement
    private final Point to;
    // Code statement
    private double power;
    // Code statement
    private double spread;

    // Code statement
    private boolean cancelled;

    // Start of a method/block
    public EntityShootEvent(Entity entity, Entity projectile, Point to, double power, double spread) {
        // Access to the current/parent object
        this.entity = entity;
        // Access to the current/parent object
        this.projectile = projectile;
        // Access to the current/parent object
        this.to = to;
        // Access to the current/parent object
        this.power = power;
        // Access to the current/parent object
        this.spread = spread;
    // End of a block/expression
    }

    /**
     * Gets the projectile.
     *
     * @return the projectile.
     */
    // Start of a method/block
    public Entity getProjectile() {
        // Returns a value to the caller
        return this.projectile;
    // End of a block/expression
    }

    /**
     * Gets the position projectile was shot to.
     *
     * @return the position projectile was shot to.
     */
    // Start of a method/block
    public Point getTo() {
        // Returns a value to the caller
        return this.to;
    // End of a block/expression
    }

    /**
     * Gets shot spread.
     *
     * @return shot spread.
     */
    // Start of a method/block
    public double getSpread() {
        // Returns a value to the caller
        return this.spread;
    // End of a block/expression
    }

    /**
     * Sets shot spread.
     *
     * @param spread shot spread.
     */
    // Start of a method/block
    public void setSpread(double spread) {
        // Access to the current/parent object
        this.spread = spread;
    // End of a block/expression
    }

    /**
     * Gets shot power.
     *
     * @return shot power.
     */
    // Start of a method/block
    public double getPower() {
        // Returns a value to the caller
        return this.power;
    // End of a block/expression
    }

    /**
     * Sets shot power.
     *
     * @param power shot power.
     */
    // Start of a method/block
    public void setPower(double power) {
        // Access to the current/parent object
        this.power = power;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isCancelled() {
        // Returns a value to the caller
        return this.cancelled;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setCancelled(boolean cancel) {
        // Access to the current/parent object
        this.cancelled = cancel;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Entity getEntity() {
        // Returns a value to the caller
        return entity;
    // End of a block/expression
    }
// End of a block/expression
}
