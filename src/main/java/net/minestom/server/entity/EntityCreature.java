// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.ai.EntityAI;
// Import d'une classe nécessaire
import net.minestom.server.entity.ai.EntityAIGroup;
// Import d'une classe nécessaire
import net.minestom.server.entity.pathfinding.NavigableEntity;
// Import d'une classe nécessaire
import net.minestom.server.entity.pathfinding.Navigator;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.entity.EntityAttackEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.thread.Acquirable;
// Import d'une classe nécessaire
import net.minestom.server.utils.time.TimeUnit;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.time.Duration;
// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.UUID;
// Import d'une classe nécessaire
import java.util.concurrent.CompletableFuture;
// Import d'une classe nécessaire
import java.util.concurrent.CopyOnWriteArraySet;

// Déclaration de type (classe/interface/enum/record)
public class EntityCreature extends LivingEntity implements NavigableEntity, EntityAI {

    // Affecte une valeur
    private int removalAnimationDelay = 1000;

    // Appelle une méthode
    private final Set<EntityAIGroup> aiGroups = new CopyOnWriteArraySet<>();

    // Appelle une méthode
    private final Navigator navigator = new Navigator(this);

    // Instruction de code
    private Entity target;

    /**
     * Constructor which allows to specify an UUID. Only use if you know what you are doing!
     */
    // Début d'une méthode/d'un bloc
    public EntityCreature(EntityType entityType, UUID uuid) {
        // Accès à l'objet courant/parent
        super(entityType, uuid);
        // Appelle une méthode
        heal();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public EntityCreature(EntityType entityType) {
        // Appelle une méthode
        this(entityType, UUID.randomUUID());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void update(long time) {
        // AI
        // Appelle une méthode
        aiTick(time);

        // Path finding
        // Accès à l'objet courant/parent
        this.navigator.tick();

        // Fire, item pickup, ...
        // Accès à l'objet courant/parent
        super.update(time);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public CompletableFuture<Void> setInstance(Instance instance, Pos spawnPosition) {
        // Accès à l'objet courant/parent
        this.navigator.reset();
        // Renvoie une valeur à l'appelant
        return super.setInstance(instance, spawnPosition);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void kill() {
        // Accès à l'objet courant/parent
        super.kill();

        // Embranchement : vérifie une condition
        if (removalAnimationDelay > 0) {
            // Needed for proper death animation (wait for it to finish before destroying the entity)
            // Appelle une méthode
            scheduleRemove(Duration.of(removalAnimationDelay, TimeUnit.MILLISECOND));
        // Branche alternative de la condition
        } else {
            // Instant removal without animation playback
            // Appelle une méthode
            remove();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the kill animation delay before vanishing the entity.
     *
     * @return the removal animation delay in milliseconds, 0 if not any
     */
    // Début d'une méthode/d'un bloc
    public int getRemovalAnimationDelay() {
        // Renvoie une valeur à l'appelant
        return removalAnimationDelay;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the removal animation delay of the entity.
     * <p>
     * Testing shows that 1000 is the minimum value to display the death particles.
     *
     * @param removalAnimationDelay the new removal animation delay in milliseconds, 0 to remove it
     */
    // Début d'une méthode/d'un bloc
    public void setRemovalAnimationDelay(int removalAnimationDelay) {
        // Accès à l'objet courant/parent
        this.removalAnimationDelay = removalAnimationDelay;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<EntityAIGroup> getAIGroups() {
        // Renvoie une valeur à l'appelant
        return aiGroups;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the entity target.
     *
     * @return the entity target, can be null if not any
     */
    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public Entity getTarget() {
        // Renvoie une valeur à l'appelant
        return target;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the entity target.
     *
     * @param target the new entity target, null to remove
     */
    // Début d'une méthode/d'un bloc
    public void setTarget(@Nullable Entity target) {
        // Accès à l'objet courant/parent
        this.target = target;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Navigator getNavigator() {
        // Renvoie une valeur à l'appelant
        return navigator;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Calls a {@link EntityAttackEvent} with this entity as the source and {@code target} as the target.
     *
     * @param target    the entity target
     * @param swingHand true to swing the entity main hand, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public void attack(Entity target, boolean swingHand) {
        // Embranchement : vérifie une condition
        if (swingHand)
            // Appelle une méthode
            swingMainHand();
        // Appelle une méthode
        EntityAttackEvent attackEvent = new EntityAttackEvent(this, target);
        // Appelle une méthode
        EventDispatcher.call(attackEvent);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Calls a {@link EntityAttackEvent} with this entity as the source and {@code target} as the target.
     * <p>
     * This does not trigger the hand animation.
     *
     * @param target the entity target
     */
    // Début d'une méthode/d'un bloc
    public void attack(Entity target) {
        // Appelle une méthode
        attack(target, false);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Acquirable<? extends EntityCreature> acquirable() {
        // Renvoie une valeur à l'appelant
        return (Acquirable<? extends EntityCreature>) super.acquirable();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
