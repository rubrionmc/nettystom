// Déclaration du paquet de ce fichier
package net.minestom.server.event.entity;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.LivingEntity;
// Import d'une classe nécessaire
import net.minestom.server.entity.damage.Damage;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.EntityInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

/**
 * Called with {@link LivingEntity#damage(net.minestom.server.registry.RegistryKey, float)}.
 */
// Déclaration de type (classe/interface/enum/record)
public class EntityDamageEvent implements EntityInstanceEvent, CancellableEvent {

    // Instruction de code
    private final Entity entity;
    // Instruction de code
    private final Damage damage;
    // Instruction de code
    private SoundEvent sound;
    // Affecte une valeur
    private boolean animation = true;

    // Instruction de code
    private boolean cancelled;

    // Début d'une méthode/d'un bloc
    public EntityDamageEvent(LivingEntity entity, Damage damage, @Nullable SoundEvent sound) {
        // Accès à l'objet courant/parent
        this.entity = entity;
        // Accès à l'objet courant/parent
        this.damage = damage;
        // Accès à l'objet courant/parent
        this.sound = sound;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public LivingEntity getEntity() {
        // Renvoie une valeur à l'appelant
        return (LivingEntity) entity;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the damage type.
     *
     * @return the damage type
     */
    // Début d'une méthode/d'un bloc
    public Damage getDamage() {
        // Renvoie une valeur à l'appelant
        return damage;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the damage sound.
     *
     * @return the damage sound
     */
    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public SoundEvent getSound() {
        // Renvoie une valeur à l'appelant
        return sound;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the damage sound.
     *
     * @param sound the new damage sound
     */
    // Début d'une méthode/d'un bloc
    public void setSound(@Nullable SoundEvent sound) {
        // Accès à l'objet courant/parent
        this.sound = sound;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets whether the damage animation should be played.
     *
     * @return true if the animation should be played
     */
    // Début d'une méthode/d'un bloc
    public boolean shouldAnimate() {
        // Renvoie une valeur à l'appelant
        return animation;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets whether the damage animation should be played.
     *
     * @param animation whether the animation should be played or not
     */
    // Début d'une méthode/d'un bloc
    public void setAnimation(boolean animation) {
        // Accès à l'objet courant/parent
        this.animation = animation;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isCancelled() {
        // Renvoie une valeur à l'appelant
        return cancelled;
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
// Fin d'un bloc/d'une expression
}
