// Package declaration for this file
package net.minestom.demo.entity;

// Import of a required class
import net.minestom.server.entity.EntityCreature;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.entity.ai.EntityAIGroupBuilder;
// Import of a required class
import net.minestom.server.entity.ai.goal.RandomLookAroundGoal;

// Type declaration (class/interface/enum/record)
public class ZombieCreature extends EntityCreature {

    // Start of a method/block
    public ZombieCreature() {
        // Access to the current/parent object
        super(EntityType.ZOMBIE);
        // Code statement
        addAIGroup(
                // Creates a new object
                new EntityAIGroupBuilder()
                        // Code statement
                        .addGoalSelector(new RandomLookAroundGoal(this, 20))
                        // Code statement
                        .build()
        // End of a block/expression
        );
    // End of a block/expression
    }
// End of a block/expression
}
