// Déclaration du paquet de ce fichier
package net.minestom.server.entity.ai;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityCreature;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.lang.ref.WeakReference;

// Déclaration de type (classe/interface/enum/record)
public abstract class GoalSelector {

    // Instruction de code
    private WeakReference<EntityAIGroup> aiGroupWeakReference;
    // Instruction de code
    protected EntityCreature entityCreature;

    // Début d'une méthode/d'un bloc
    public GoalSelector(EntityCreature entityCreature) {
        // Accès à l'objet courant/parent
        this.entityCreature = entityCreature;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Whether this {@link GoalSelector} should start.
     *
     * @return true to start
     */
    // Appelle une méthode
    public abstract boolean shouldStart();

    /**
     * Starts this {@link GoalSelector}.
     */
    // Appelle une méthode
    public abstract void start();

    /**
     * Called every tick when this {@link GoalSelector} is running.
     *
     * @param time the time of the update in milliseconds
     */
    // Appelle une méthode
    public abstract void tick(long time);

    /**
     * Whether this {@link GoalSelector} should end.
     *
     * @return true to end
     */
    // Appelle une méthode
    public abstract boolean shouldEnd();

    /**
     * Ends this {@link GoalSelector}.
     */
    // Appelle une méthode
    public abstract void end();

    /**
     * Finds a target based on the entity {@link TargetSelector}.
     *
     * @return the target entity, null if not found
     */
    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public Entity findTarget() {
        // Appelle une méthode
        EntityAIGroup aiGroup = getAIGroup();
        // Embranchement : vérifie une condition
        if (aiGroup == null) {
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
        // Boucle : répète un bloc
        for (TargetSelector targetSelector : aiGroup.getTargetSelectors()) {
            // Appelle une méthode
            final Entity entity = targetSelector.findTarget();
            // Embranchement : vérifie une condition
            if (entity != null) {
                // Renvoie une valeur à l'appelant
                return entity;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the entity behind the goal selector.
     *
     * @return the entity
     */
    // Début d'une méthode/d'un bloc
    public EntityCreature getEntityCreature() {
        // Renvoie une valeur à l'appelant
        return entityCreature;
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public void setEntityCreature(EntityCreature entityCreature) {
        // Accès à l'objet courant/parent
        this.entityCreature = entityCreature;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void setAIGroup(EntityAIGroup group) {
        // Accès à l'objet courant/parent
        this.aiGroupWeakReference = new WeakReference<>(group);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    protected EntityAIGroup getAIGroup() {
        // Renvoie une valeur à l'appelant
        return this.aiGroupWeakReference.get();
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
