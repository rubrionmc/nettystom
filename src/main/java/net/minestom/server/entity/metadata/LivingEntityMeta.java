// Package declaration for this file
package net.minestom.server.entity.metadata;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.particle.Particle;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public class LivingEntityMeta extends EntityMeta {
    // Start of a method/block
    protected LivingEntityMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isHandActive() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.LivingEntity.IS_HAND_ACTIVE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setHandActive(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.LivingEntity.IS_HAND_ACTIVE, value);
    // End of a block/expression
    }

    // Start of a method/block
    public PlayerHand getActiveHand() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.LivingEntity.ACTIVE_HAND) ? PlayerHand.OFF : PlayerHand.MAIN;
    // End of a block/expression
    }

    // Start of a method/block
    public void setActiveHand(PlayerHand hand) {
        // Calls a method
        metadata.set(MetadataDef.LivingEntity.ACTIVE_HAND, hand == PlayerHand.OFF);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isInRiptideSpinAttack() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.LivingEntity.IS_RIPTIDE_SPIN_ATTACK);
    // End of a block/expression
    }

    // Start of a method/block
    public void setInRiptideSpinAttack(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.LivingEntity.IS_RIPTIDE_SPIN_ATTACK, value);
    // End of a block/expression
    }

    // Start of a method/block
    public float getHealth() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.LivingEntity.HEALTH);
    // End of a block/expression
    }

    // Start of a method/block
    public void setHealth(float value) {
        // Calls a method
        metadata.set(MetadataDef.LivingEntity.HEALTH, value);
    // End of a block/expression
    }

    // Start of a method/block
    public List<Particle> getEffectParticles() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.LivingEntity.POTION_EFFECT_PARTICLES);
    // End of a block/expression
    }

    // Start of a method/block
    public void setEffectParticles(List<Particle> value) {
        // Calls a method
        metadata.set(MetadataDef.LivingEntity.POTION_EFFECT_PARTICLES, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isPotionEffectAmbient() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.LivingEntity.IS_POTION_EFFECT_AMBIANT);
    // End of a block/expression
    }

    // Start of a method/block
    public void setPotionEffectAmbient(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.LivingEntity.IS_POTION_EFFECT_AMBIANT, value);
    // End of a block/expression
    }

    // Start of a method/block
    public int getArrowCount() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.LivingEntity.NUMBER_OF_ARROWS);
    // End of a block/expression
    }

    // Start of a method/block
    public void setArrowCount(int value) {
        // Calls a method
        metadata.set(MetadataDef.LivingEntity.NUMBER_OF_ARROWS, value);
    // End of a block/expression
    }

    /**
     * Gets the amount of bee stingers in this entity
     *
     * @return The amount of bee stingers
     */
    // Start of a method/block
    public int getBeeStingerCount() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.LivingEntity.NUMBER_OF_BEE_STINGERS);
    // End of a block/expression
    }

    /**
     * Sets the amount of bee stingers in this entity
     *
     * @param value The amount of bee stingers to set, use 0 to clear all stingers
     */
    // Start of a method/block
    public void setBeeStingerCount(int value) {
        // Calls a method
        metadata.set(MetadataDef.LivingEntity.NUMBER_OF_BEE_STINGERS, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public Point getBedInWhichSleepingPosition() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.LivingEntity.LOCATION_OF_BED);
    // End of a block/expression
    }

    // Start of a method/block
    public void setBedInWhichSleepingPosition(@Nullable Point value) {
        // Calls a method
        metadata.set(MetadataDef.LivingEntity.LOCATION_OF_BED, value);
    // End of a block/expression
    }

// End of a block/expression
}
