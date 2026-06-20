// Package declaration for this file
package net.minestom.demo.entity;

// Import of a required class
import net.minestom.server.entity.EntityCreature;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.entity.ai.goal.RandomStrollGoal;
// Import of a required class
import net.minestom.server.entity.attribute.Attribute;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public class ChickenCreature extends EntityCreature {

    // Start of a method/block
    public ChickenCreature() {
        // Access to the current/parent object
        super(EntityType.CHICKEN);

        // Code statement
        addAIGroup(
                // Code statement
                List.of(
//                        new DoNothingGoal(this, 500, 0.1f),
//                        new MeleeAttackGoal(this, 500, 2, TimeUnit.MILLISECOND),
                        // Creates a new object
                        new RandomStrollGoal(this, 2)
                // End of a block/expression
                ),
                // Code statement
                List.of(
//                        new LastEntityDamagerTarget(this, 15),
//                        new ClosestEntityTarget(this, 15, LivingEntity.class)
                // End of a block/expression
                )
        // End of a block/expression
        );

        // Another way to register previously added EntityAIGroup, using specialized builder:
//        addAIGroup(
//                new EntityAIGroupBuilder()
//                        .addGoalSelector(new DoNothingGoal(this, 500, .1F))
//                        .addGoalSelector(new MeleeAttackGoal(this, 500, 2, TimeUnit.MILLISECOND))
//                        .addGoalSelector(new RandomStrollGoal(this, 2))
//                        .addTargetSelector(new LastEntityDamagerTarget(this, 15))
//                        .addTargetSelector(new ClosestEntityTarget(this, 15, LivingEntity.class))
//                        .build()
//        );

        // Calls a method
        getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0.1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void spawn() {

    // End of a block/expression
    }
// End of a block/expression
}
