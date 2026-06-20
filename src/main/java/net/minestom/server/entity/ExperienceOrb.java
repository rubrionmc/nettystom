// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.thread.Acquirable;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.util.Comparator;

// Type declaration (class/interface/enum/record)
public class ExperienceOrb extends Entity {

    // Code statement
    private short experienceCount;
    // Code statement
    private Player target;
    // Code statement
    private long lastTargetUpdateTick;

    // Start of a method/block
    public ExperienceOrb(short experienceCount) {
        // Access to the current/parent object
        super(EntityType.EXPERIENCE_ORB);
        // Calls a method
        setBoundingBox(0.5f, 0.5f, 0.5f);
        //todo vanilla sets random velocity here?
        // Access to the current/parent object
        this.experienceCount = experienceCount;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void update(long time) {

        // TODO slide toward nearest player

        //todo water movement
        // Branch: checks a condition
        if (hasNoGravity()) {
            // Calls a method
            setVelocity(getVelocity().add(0, -0.3f, 0));
        // End of a block/expression
        }

        //todo lava

        // Assigns a value
        double d = 8.0;
        // Branch: checks a condition
        if (lastTargetUpdateTick < time - 20 + getEntityId() % 100) {
            // Branch: checks a condition
            if (target == null || target.getPosition().distanceSquared(getPosition()) > 64) {
                // Access to the current/parent object
                this.target = getClosestPlayer(this, 8);
            // End of a block/expression
            }

            // Assigns a value
            lastTargetUpdateTick = time;
        // End of a block/expression
        }

        // Branch: checks a condition
        if (target != null && target.getGameMode() == GameMode.SPECTATOR) {
            // Assigns a value
            target = null;
        // End of a block/expression
        }

        // Branch: checks a condition
        if (target != null) {
            // Calls a method
            final var pos = getPosition();
            // Calls a method
            final var targetPos = target.getPosition();
            // Calls a method
            final Vec toTarget = new Vec(targetPos.x() - pos.x(), targetPos.y() + (target.getEyeHeight() / 2) - pos.y(), targetPos.z() - pos.z());
            // Assigns a value
            double e = toTarget.length(); //could really be lengthSquared
            // Branch: checks a condition
            if (e < 8) {
                // Calls a method
                double f = 1 - (e / 8);
                // Calls a method
                setVelocity(getVelocity().add(toTarget.normalize().mul(f * f * 0.1)));
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Move should be called here
        // Assigns a value
        float g = 0.98f;
        // Branch: checks a condition
        if (this.onGround) {
//            g = 2f;
            // Assigns a value
            g = 0.6f * 0.98f;
        // End of a block/expression
        }
        // apply slipperiness

        // Calls a method
        setVelocity(getVelocity().mul(new Vec(g, 0.98f, g)));
        // Branch: checks a condition
        if (isOnGround()) {
            // Calls a method
            setVelocity(getVelocity().mul(new Vec(1, -0.9f, 1)));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void spawn() {

    // End of a block/expression
    }

    /**
     * Gets the experience count.
     *
     * @return the experience count
     */
    // Start of a method/block
    public short getExperienceCount() {
        // Returns a value to the caller
        return experienceCount;
    // End of a block/expression
    }

    /**
     * Changes the experience count.
     *
     * @param experienceCount the new experience count
     */
    // Start of a method/block
    public void setExperienceCount(short experienceCount) {
        // Remove the entity in order to respawn it with the correct experience count
        // Calls a method
        getViewers().forEach(this::removeViewer);

        // Access to the current/parent object
        this.experienceCount = experienceCount;

        // Calls a method
        getViewers().forEach(this::addViewer);
    // End of a block/expression
    }

    // Start of a method/block
    private Player getClosestPlayer(Entity entity, float maxDistance) {
        // Assigns a value
        Player closest = entity.getInstance()
                // Code statement
                .getPlayers()
                // Code statement
                .stream()
                // Code statement
                .min(Comparator.comparingDouble(a -> a.getDistanceSquared(entity)))
                // Calls a method
                .orElse(null);
        // Branch: checks a condition
        if (closest == null) return null;
        // Branch: checks a condition
        if (closest.getDistanceSquared(entity) > maxDistance * maxDistance) return null;
        // Returns a value to the caller
        return closest;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Experimental
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Annotation for the following element
    @Override
    // Start of a method/block
    public Acquirable<? extends ExperienceOrb> acquirable() {
        // Returns a value to the caller
        return (Acquirable<? extends ExperienceOrb>) super.acquirable();
    // End of a block/expression
    }
// End of a block/expression
}
