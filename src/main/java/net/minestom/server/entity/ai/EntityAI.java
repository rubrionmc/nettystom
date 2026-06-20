// Déclaration du paquet de ce fichier
package net.minestom.server.entity.ai;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;

/**
 * Represents an entity which can contain
 * {@link GoalSelector goal selectors} and {@link TargetSelector target selectors}.
 * <p>
 * Both types of selectors are being stored in {@link EntityAIGroup AI groups}.
 * For every group there could be only a single {@link GoalSelector goal selector} running at a time,
 * but multiple groups are independent of each other, so each of them can have own goal selector running.
 */
// Déclaration de type (classe/interface/enum/record)
public interface EntityAI {

    /**
     * Gets the AI groups of this entity.
     *
     * @return a modifiable collection of AI groups of this entity.
     */
    // Appelle une méthode
    Collection<EntityAIGroup> getAIGroups();

    /**
     * Adds new AI group to this entity.
     *
     * @param group a group to be added.
     */
    // Début d'une méthode/d'un bloc
    default void addAIGroup(EntityAIGroup group) {
        // Appelle une méthode
        getAIGroups().add(group);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Adds new AI group to this entity, consisting of the given
     * {@link GoalSelector goal selectors} and {@link TargetSelector target selectors}.
     * Their order is also a priority: the lower element index is, the higher priority is.
     *
     * @param goalSelectors   goal selectors of the group.
     * @param targetSelectors target selectors of the group.
     */
    // Début d'une méthode/d'un bloc
    default void addAIGroup(List<GoalSelector> goalSelectors, List<TargetSelector> targetSelectors) {
        // Appelle une méthode
        EntityAIGroup group = new EntityAIGroup();
        // Appelle une méthode
        group.getGoalSelectors().addAll(goalSelectors);
        // Appelle une méthode
        group.getTargetSelectors().addAll(targetSelectors);
        // Appelle une méthode
        addAIGroup(group);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default void aiTick(long time) {
        // Appelle une méthode
        getAIGroups().forEach(group -> group.tick(time));
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
