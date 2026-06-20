// Package declaration for this file
package net.minestom.server.entity.ai;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;

/**
 * Represents an entity which can contain
 * {@link GoalSelector goal selectors} and {@link TargetSelector target selectors}.
 * <p>
 * Both types of selectors are being stored in {@link EntityAIGroup AI groups}.
 * For every group there could be only a single {@link GoalSelector goal selector} running at a time,
 * but multiple groups are independent of each other, so each of them can have own goal selector running.
 */
// Type declaration (class/interface/enum/record)
public interface EntityAI {

    /**
     * Gets the AI groups of this entity.
     *
     * @return a modifiable collection of AI groups of this entity.
     */
    // Calls a method
    Collection<EntityAIGroup> getAIGroups();

    /**
     * Adds new AI group to this entity.
     *
     * @param group a group to be added.
     */
    // Start of a method/block
    default void addAIGroup(EntityAIGroup group) {
        // Calls a method
        getAIGroups().add(group);
    // End of a block/expression
    }

    /**
     * Adds new AI group to this entity, consisting of the given
     * {@link GoalSelector goal selectors} and {@link TargetSelector target selectors}.
     * Their order is also a priority: the lower element index is, the higher priority is.
     *
     * @param goalSelectors   goal selectors of the group.
     * @param targetSelectors target selectors of the group.
     */
    // Start of a method/block
    default void addAIGroup(List<GoalSelector> goalSelectors, List<TargetSelector> targetSelectors) {
        // Calls a method
        EntityAIGroup group = new EntityAIGroup();
        // Calls a method
        group.getGoalSelectors().addAll(goalSelectors);
        // Calls a method
        group.getTargetSelectors().addAll(targetSelectors);
        // Calls a method
        addAIGroup(group);
    // End of a block/expression
    }

    // Start of a method/block
    default void aiTick(long time) {
        // Calls a method
        getAIGroups().forEach(group -> group.tick(time));
    // End of a block/expression
    }

// End of a block/expression
}
