// Package declaration for this file
package net.minestom.server.entity.ai;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityCreature;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.lang.ref.WeakReference;

// Type declaration (class/interface/enum/record)
public abstract class GoalSelector {

    // Code statement
    private WeakReference<EntityAIGroup> aiGroupWeakReference;
    // Code statement
    protected EntityCreature entityCreature;

    // Start of a method/block
    public GoalSelector(EntityCreature entityCreature) {
        // Access to the current/parent object
        this.entityCreature = entityCreature;
    // End of a block/expression
    }

    /**
     * Whether this {@link GoalSelector} should start.
     *
     * @return true to start
     */
    // Calls a method
    public abstract boolean shouldStart();

    /**
     * Starts this {@link GoalSelector}.
     */
    // Calls a method
    public abstract void start();

    /**
     * Called every tick when this {@link GoalSelector} is running.
     *
     * @param time the time of the update in milliseconds
     */
    // Calls a method
    public abstract void tick(long time);

    /**
     * Whether this {@link GoalSelector} should end.
     *
     * @return true to end
     */
    // Calls a method
    public abstract boolean shouldEnd();

    /**
     * Ends this {@link GoalSelector}.
     */
    // Calls a method
    public abstract void end();

    /**
     * Finds a target based on the entity {@link TargetSelector}.
     *
     * @return the target entity, null if not found
     */
    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public Entity findTarget() {
        // Calls a method
        EntityAIGroup aiGroup = getAIGroup();
        // Branch: checks a condition
        if (aiGroup == null) {
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
        // Loop: repeats a block
        for (TargetSelector targetSelector : aiGroup.getTargetSelectors()) {
            // Calls a method
            final Entity entity = targetSelector.findTarget();
            // Branch: checks a condition
            if (entity != null) {
                // Returns a value to the caller
                return entity;
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }

    /**
     * Gets the entity behind the goal selector.
     *
     * @return the entity
     */
    // Start of a method/block
    public EntityCreature getEntityCreature() {
        // Returns a value to the caller
        return entityCreature;
    // End of a block/expression
    }

    /**
     * Changes the entity affected by the goal selector.
     * <p>
     * WARNING: this does not add the goal selector to {@code entityCreature},
     * this only change the internal entity AI group's field. Be sure to remove the goal from
     * the previous entity AI group and add it to the new one using {@link EntityAIGroup#getGoalSelectors()}.
     *
     * @param entityCreature the new affected entity
     */
    // Start of a method/block
    public void setEntityCreature(EntityCreature entityCreature) {
        // Access to the current/parent object
        this.entityCreature = entityCreature;
    // End of a block/expression
    }

    // Start of a method/block
    void setAIGroup(EntityAIGroup group) {
        // Access to the current/parent object
        this.aiGroupWeakReference = new WeakReference<>(group);
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable
    // Start of a method/block
    protected EntityAIGroup getAIGroup() {
        // Returns a value to the caller
        return this.aiGroupWeakReference.get();
    // End of a block/expression
    }

// End of a block/expression
}
