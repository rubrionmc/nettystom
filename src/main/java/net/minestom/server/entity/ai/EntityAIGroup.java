// Package declaration for this file
package net.minestom.server.entity.ai;

// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.function.UnaryOperator;

/**
 * Represents a group of entity's AI.
 * It may contains {@link GoalSelector goal selectors} and {@link TargetSelector target selectors}.
 * All AI groups of a single entity are independent of each other.
 */
// Type declaration (class/interface/enum/record)
public class EntityAIGroup {

    // Code statement
    private GoalSelector currentGoalSelector;
    // Calls a method
    private final List<GoalSelector> goalSelectors = new GoalSelectorsArrayList();
    // Calls a method
    private final List<TargetSelector> targetSelectors = new ArrayList<>();

    /**
     * Gets the goal selectors of this group.
     *
     * @return a modifiable list containing this group goal selectors
     */
    // Start of a method/block
    public List<GoalSelector> getGoalSelectors() {
        // Returns a value to the caller
        return this.goalSelectors;
    // End of a block/expression
    }

    /**
     * Gets the target selectors of this group.
     *
     * @return a modifiable list containing this group target selectors
     */
    // Start of a method/block
    public List<TargetSelector> getTargetSelectors() {
        // Returns a value to the caller
        return this.targetSelectors;
    // End of a block/expression
    }

    /**
     * Gets the current goal selector of this group.
     *
     * @return the current goal selector of this group, null if not any
     */
    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public GoalSelector getCurrentGoalSelector() {
        // Returns a value to the caller
        return this.currentGoalSelector;
    // End of a block/expression
    }

    /**
     * Changes the current goal selector of this group.
     * <p>
     * Mostly unsafe since the current goal selector should normally
     * be chosen during the group tick method.
     *
     * @param goalSelector the new goal selector of this group, null to disable it
     */
    // Start of a method/block
    public void setCurrentGoalSelector(@Nullable GoalSelector goalSelector) {
        // Code statement
        Check.argCondition(
                // Code statement
                goalSelector != null && goalSelector.getAIGroup() != this,
                // Code statement
                "Tried to set goal selector attached to another AI group!"
        // End of a block/expression
        );
        // Access to the current/parent object
        this.currentGoalSelector = goalSelector;
    // End of a block/expression
    }

    // Start of a method/block
    public void tick(long time) {
        // Calls a method
        GoalSelector currentGoalSelector = getCurrentGoalSelector();

        // Branch: checks a condition
        if (currentGoalSelector != null && currentGoalSelector.shouldEnd()) {
            // Calls a method
            currentGoalSelector.end();
            // Assigns a value
            currentGoalSelector = null;
            // Calls a method
            setCurrentGoalSelector(null);
        // End of a block/expression
        }

        // Loop: repeats a block
        for (GoalSelector selector : getGoalSelectors()) {
            // Branch: checks a condition
            if (selector == currentGoalSelector) {
                // Breaks out of the loop/block
                break;
            // End of a block/expression
            }
            // Branch: checks a condition
            if (selector.shouldStart()) {
                // Branch: checks a condition
                if (currentGoalSelector != null) {
                    // Calls a method
                    currentGoalSelector.end();
                // End of a block/expression
                }
                // Assigns a value
                currentGoalSelector = selector;
                // Calls a method
                setCurrentGoalSelector(currentGoalSelector);
                // Calls a method
                currentGoalSelector.start();
                // Breaks out of the loop/block
                break;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Branch: checks a condition
        if (currentGoalSelector != null) {
            // Calls a method
            currentGoalSelector.tick(time);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * The purpose of this list is to guarantee that every {@link GoalSelector} added to that group
     * has a reference to it for some internal interactions. We don't provide developers with
     * methods like `addGoalSelector` or `removeGoalSelector`: instead we provide them with direct
     * access to list of goal selectors, so that they could use operations such as `clear`, `set`, `removeIf`, etc.
     */
    // Type declaration (class/interface/enum/record)
    private class GoalSelectorsArrayList extends ArrayList<GoalSelector> {

        // Start of a method/block
        private GoalSelectorsArrayList() {
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public GoalSelector set(int index, GoalSelector element) {
            // Calls a method
            element.setAIGroup(EntityAIGroup.this);
            // Returns a value to the caller
            return super.set(index, element);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean add(GoalSelector element) {
            // Calls a method
            element.setAIGroup(EntityAIGroup.this);
            // Returns a value to the caller
            return super.add(element);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void add(int index, GoalSelector element) {
            // Calls a method
            element.setAIGroup(EntityAIGroup.this);
            // Access to the current/parent object
            super.add(index, element);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean addAll(Collection<? extends GoalSelector> c) {
            // Calls a method
            c.forEach(goalSelector -> goalSelector.setAIGroup(EntityAIGroup.this));
            // Returns a value to the caller
            return super.addAll(c);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean addAll(int index, Collection<? extends GoalSelector> c) {
            // Calls a method
            c.forEach(goalSelector -> goalSelector.setAIGroup(EntityAIGroup.this));
            // Returns a value to the caller
            return super.addAll(index, c);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void replaceAll(UnaryOperator<GoalSelector> operator) {
            // Access to the current/parent object
            super.replaceAll(goalSelector -> {
                // Calls a method
                goalSelector = operator.apply(goalSelector);
                // Calls a method
                goalSelector.setAIGroup(EntityAIGroup.this);
                // Returns a value to the caller
                return goalSelector;
            // End of a block/expression
            });
        // End of a block/expression
        }

    // End of a block/expression
    }

// End of a block/expression
}
