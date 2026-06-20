// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.ai.EntityAI;
// Import of a required class
import net.minestom.server.entity.ai.EntityAIGroup;
// Import of a required class
import net.minestom.server.entity.pathfinding.NavigableEntity;
// Import of a required class
import net.minestom.server.entity.pathfinding.Navigator;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.entity.EntityAttackEvent;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.thread.Acquirable;
// Import of a required class
import net.minestom.server.utils.time.TimeUnit;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.time.Duration;
// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.UUID;
// Import of a required class
import java.util.concurrent.CompletableFuture;
// Import of a required class
import java.util.concurrent.CopyOnWriteArraySet;

// Type declaration (class/interface/enum/record)
public class EntityCreature extends LivingEntity implements NavigableEntity, EntityAI {

    // Assigns a value
    private int removalAnimationDelay = 1000;

    // Calls a method
    private final Set<EntityAIGroup> aiGroups = new CopyOnWriteArraySet<>();

    // Calls a method
    private final Navigator navigator = new Navigator(this);

    // Code statement
    private Entity target;

    /**
     * Constructor which allows to specify an UUID. Only use if you know what you are doing!
     */
    // Start of a method/block
    public EntityCreature(EntityType entityType, UUID uuid) {
        // Access to the current/parent object
        super(entityType, uuid);
        // Calls a method
        heal();
    // End of a block/expression
    }

    // Start of a method/block
    public EntityCreature(EntityType entityType) {
        // Calls a method
        this(entityType, UUID.randomUUID());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void update(long time) {
        // AI
        // Calls a method
        aiTick(time);

        // Path finding
        // Access to the current/parent object
        this.navigator.tick();

        // Fire, item pickup, ...
        // Access to the current/parent object
        super.update(time);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public CompletableFuture<Void> setInstance(Instance instance, Pos spawnPosition) {
        // Access to the current/parent object
        this.navigator.reset();
        // Returns a value to the caller
        return super.setInstance(instance, spawnPosition);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void kill() {
        // Access to the current/parent object
        super.kill();

        // Branch: checks a condition
        if (removalAnimationDelay > 0) {
            // Needed for proper death animation (wait for it to finish before destroying the entity)
            // Calls a method
            scheduleRemove(Duration.of(removalAnimationDelay, TimeUnit.MILLISECOND));
        // Alternative branch of the condition
        } else {
            // Instant removal without animation playback
            // Calls a method
            remove();
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets the kill animation delay before vanishing the entity.
     *
     * @return the removal animation delay in milliseconds, 0 if not any
     */
    // Start of a method/block
    public int getRemovalAnimationDelay() {
        // Returns a value to the caller
        return removalAnimationDelay;
    // End of a block/expression
    }

    /**
     * Changes the removal animation delay of the entity.
     * <p>
     * Testing shows that 1000 is the minimum value to display the death particles.
     *
     * @param removalAnimationDelay the new removal animation delay in milliseconds, 0 to remove it
     */
    // Start of a method/block
    public void setRemovalAnimationDelay(int removalAnimationDelay) {
        // Access to the current/parent object
        this.removalAnimationDelay = removalAnimationDelay;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<EntityAIGroup> getAIGroups() {
        // Returns a value to the caller
        return aiGroups;
    // End of a block/expression
    }

    /**
     * Gets the entity target.
     *
     * @return the entity target, can be null if not any
     */
    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public Entity getTarget() {
        // Returns a value to the caller
        return target;
    // End of a block/expression
    }

    /**
     * Changes the entity target.
     *
     * @param target the new entity target, null to remove
     */
    // Start of a method/block
    public void setTarget(@Nullable Entity target) {
        // Access to the current/parent object
        this.target = target;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Navigator getNavigator() {
        // Returns a value to the caller
        return navigator;
    // End of a block/expression
    }

    /**
     * Calls a {@link EntityAttackEvent} with this entity as the source and {@code target} as the target.
     *
     * @param target    the entity target
     * @param swingHand true to swing the entity main hand, false otherwise
     */
    // Start of a method/block
    public void attack(Entity target, boolean swingHand) {
        // Branch: checks a condition
        if (swingHand)
            // Calls a method
            swingMainHand();
        // Calls a method
        EntityAttackEvent attackEvent = new EntityAttackEvent(this, target);
        // Calls a method
        EventDispatcher.call(attackEvent);
    // End of a block/expression
    }

    /**
     * Calls a {@link EntityAttackEvent} with this entity as the source and {@code target} as the target.
     * <p>
     * This does not trigger the hand animation.
     *
     * @param target the entity target
     */
    // Start of a method/block
    public void attack(Entity target) {
        // Calls a method
        attack(target, false);
    // End of a block/expression
    }

    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Annotation for the following element
    @ApiStatus.Experimental
    // Annotation for the following element
    @Override
    // Start of a method/block
    public Acquirable<? extends EntityCreature> acquirable() {
        // Returns a value to the caller
        return (Acquirable<? extends EntityCreature>) super.acquirable();
    // End of a block/expression
    }
// End of a block/expression
}
