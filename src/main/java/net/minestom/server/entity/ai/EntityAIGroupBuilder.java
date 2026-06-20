// Package declaration for this file
package net.minestom.server.entity.ai;

// Type declaration (class/interface/enum/record)
public class EntityAIGroupBuilder {

    // Calls a method
    private final EntityAIGroup group = new EntityAIGroup();

    /**
     * Adds {@link GoalSelector} to the list of goal selectors of the building {@link EntityAIGroup}.
     * Addition order is also a priority: priority the higher the earlier selector was added.
     *
     * @param goalSelector goal selector to be added.
     * @return this builder.
     */
    // Start of a method/block
    public EntityAIGroupBuilder addGoalSelector(GoalSelector goalSelector) {
        // Access to the current/parent object
        this.group.getGoalSelectors().add(goalSelector);
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Adds {@link TargetSelector} to the list of target selectors of the building {@link EntityAIGroup}.
     * Addition order is also a priority: priority the higher the earlier selector was added.
     *
     * @param targetSelector target selector to be added.
     * @return this builder.
     */
    // Start of a method/block
    public EntityAIGroupBuilder addTargetSelector(TargetSelector targetSelector) {
        // Access to the current/parent object
        this.group.getTargetSelectors().add(targetSelector);
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Creates new {@link EntityAIGroup}.
     *
     * @return new {@link EntityAIGroup}.
     */
    // Start of a method/block
    public EntityAIGroup build() {
        // Returns a value to the caller
        return this.group;
    // End of a block/expression
    }

// End of a block/expression
}
