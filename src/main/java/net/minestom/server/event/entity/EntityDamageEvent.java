// Package declaration for this file
package net.minestom.server.event.entity;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.LivingEntity;
// Import of a required class
import net.minestom.server.entity.damage.Damage;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.EntityInstanceEvent;
// Import of a required class
import net.minestom.server.sound.SoundEvent;
// Import of a required class
import org.jetbrains.annotations.Nullable;

/**
 * Called with {@link LivingEntity#damage(net.minestom.server.registry.RegistryKey, float)}.
 */
// Type declaration (class/interface/enum/record)
public class EntityDamageEvent implements EntityInstanceEvent, CancellableEvent {

    // Code statement
    private final Entity entity;
    // Code statement
    private final Damage damage;
    // Code statement
    private SoundEvent sound;
    // Assigns a value
    private boolean animation = true;

    // Code statement
    private boolean cancelled;

    // Start of a method/block
    public EntityDamageEvent(LivingEntity entity, Damage damage, @Nullable SoundEvent sound) {
        // Access to the current/parent object
        this.entity = entity;
        // Access to the current/parent object
        this.damage = damage;
        // Access to the current/parent object
        this.sound = sound;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public LivingEntity getEntity() {
        // Returns a value to the caller
        return (LivingEntity) entity;
    // End of a block/expression
    }

    /**
     * Gets the damage type.
     *
     * @return the damage type
     */
    // Start of a method/block
    public Damage getDamage() {
        // Returns a value to the caller
        return damage;
    // End of a block/expression
    }

    /**
     * Gets the damage sound.
     *
     * @return the damage sound
     */
    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public SoundEvent getSound() {
        // Returns a value to the caller
        return sound;
    // End of a block/expression
    }

    /**
     * Changes the damage sound.
     *
     * @param sound the new damage sound
     */
    // Start of a method/block
    public void setSound(@Nullable SoundEvent sound) {
        // Access to the current/parent object
        this.sound = sound;
    // End of a block/expression
    }

    /**
     * Gets whether the damage animation should be played.
     *
     * @return true if the animation should be played
     */
    // Start of a method/block
    public boolean shouldAnimate() {
        // Returns a value to the caller
        return animation;
    // End of a block/expression
    }

    /**
     * Sets whether the damage animation should be played.
     *
     * @param animation whether the animation should be played or not
     */
    // Start of a method/block
    public void setAnimation(boolean animation) {
        // Access to the current/parent object
        this.animation = animation;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isCancelled() {
        // Returns a value to the caller
        return cancelled;
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
// End of a block/expression
}
