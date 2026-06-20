// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
// Import d'une classe nécessaire
import net.minestom.server.particle.Particle;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public class LivingEntityMeta extends EntityMeta {
    // Début d'une méthode/d'un bloc
    protected LivingEntityMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isHandActive() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.LivingEntity.IS_HAND_ACTIVE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHandActive(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.LivingEntity.IS_HAND_ACTIVE, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public PlayerHand getActiveHand() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.LivingEntity.ACTIVE_HAND) ? PlayerHand.OFF : PlayerHand.MAIN;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setActiveHand(PlayerHand hand) {
        // Appelle une méthode
        metadata.set(MetadataDef.LivingEntity.ACTIVE_HAND, hand == PlayerHand.OFF);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isInRiptideSpinAttack() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.LivingEntity.IS_RIPTIDE_SPIN_ATTACK);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setInRiptideSpinAttack(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.LivingEntity.IS_RIPTIDE_SPIN_ATTACK, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public float getHealth() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.LivingEntity.HEALTH);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHealth(float value) {
        // Appelle une méthode
        metadata.set(MetadataDef.LivingEntity.HEALTH, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public List<Particle> getEffectParticles() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.LivingEntity.POTION_EFFECT_PARTICLES);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setEffectParticles(List<Particle> value) {
        // Appelle une méthode
        metadata.set(MetadataDef.LivingEntity.POTION_EFFECT_PARTICLES, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isPotionEffectAmbient() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.LivingEntity.IS_POTION_EFFECT_AMBIANT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setPotionEffectAmbient(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.LivingEntity.IS_POTION_EFFECT_AMBIANT, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getArrowCount() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.LivingEntity.NUMBER_OF_ARROWS);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setArrowCount(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.LivingEntity.NUMBER_OF_ARROWS, value);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the amount of bee stingers in this entity
     *
     * @return The amount of bee stingers
     */
    // Début d'une méthode/d'un bloc
    public int getBeeStingerCount() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.LivingEntity.NUMBER_OF_BEE_STINGERS);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the amount of bee stingers in this entity
     *
     * @param value The amount of bee stingers to set, use 0 to clear all stingers
     */
    // Début d'une méthode/d'un bloc
    public void setBeeStingerCount(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.LivingEntity.NUMBER_OF_BEE_STINGERS, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public Point getBedInWhichSleepingPosition() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.LivingEntity.LOCATION_OF_BED);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setBedInWhichSleepingPosition(@Nullable Point value) {
        // Appelle une méthode
        metadata.set(MetadataDef.LivingEntity.LOCATION_OF_BED, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
