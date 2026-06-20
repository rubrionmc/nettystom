// Package declaration for this file
package net.minestom.server.entity.ai.goal;

// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.EntityCreature;
// Import of a required class
import net.minestom.server.entity.ai.GoalSelector;

// Import of a required class
import java.util.Random;
// Import of a required class
import java.util.function.Function;
// Import of a required class
import java.util.function.Supplier;

// Type declaration (class/interface/enum/record)
public class RandomLookAroundGoal extends GoalSelector {
    // Calls a method
    private static final Random RANDOM = new Random();
    // Code statement
    private final int chancePerTick;
    // Code statement
    private final Supplier<Integer> minimalLookTimeSupplier;
    // Code statement
    private final Function<EntityCreature, Vec> randomDirectionFunction;
    // Code statement
    private Vec lookDirection;
    // Assigns a value
    private int lookTime = 0;

    // Start of a method/block
    public RandomLookAroundGoal(EntityCreature entityCreature, int chancePerTick) {
        // Code statement
        this(entityCreature, chancePerTick,
                // These two functions act similarly enough to how MC randomly looks around.

                // Look in one direction for at most 40 ticks and at minimum 20 ticks.
                // Code statement
                () -> 20 + RANDOM.nextInt(20),
                // Look at a random block
                // Start of a method/block
                (creature) -> {
                    // Calls a method
                    final double n = Math.PI * 2 * RANDOM.nextDouble();
                    // Returns a value to the caller
                    return new Vec(
                            // Code statement
                            (float) Math.cos(n),
                            // Code statement
                            0,
                            // Code statement
                            (float) Math.sin(n)
                    // End of a block/expression
                    );
                // End of a block/expression
                });
    // End of a block/expression
    }

    /**
     * @param entityCreature          Creature that should randomly look around.
     * @param chancePerTick           The chance (per tick) that the entity looks around. Setting this to N would mean there is a 1 in N chance.
     * @param minimalLookTimeSupplier A supplier that returns the minimal amount of time an entity looks in a direction.
     * @param randomDirectionFunction A function that returns a random vector that the entity will look in/at.
     */
    // Code statement
    public RandomLookAroundGoal(
            // Code statement
            EntityCreature entityCreature,
            // Code statement
            int chancePerTick,
            // Code statement
            Supplier<Integer> minimalLookTimeSupplier,
            // Start of a method/block
            Function<EntityCreature, Vec> randomDirectionFunction) {
        // Access to the current/parent object
        super(entityCreature);
        // Access to the current/parent object
        this.chancePerTick = chancePerTick;
        // Access to the current/parent object
        this.minimalLookTimeSupplier = minimalLookTimeSupplier;
        // Access to the current/parent object
        this.randomDirectionFunction = randomDirectionFunction;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean shouldStart() {
        // Branch: checks a condition
        if (RANDOM.nextInt(chancePerTick) != 0) {
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
        // Returns a value to the caller
        return entityCreature.getNavigator().getPathPosition() == null;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void start() {
        // Calls a method
        lookTime = minimalLookTimeSupplier.get();
        // Calls a method
        lookDirection = randomDirectionFunction.apply(entityCreature);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void tick(long time) {
        // Code statement
        --lookTime;
        // Calls a method
        entityCreature.refreshPosition(entityCreature.getPosition().withDirection(lookDirection));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean shouldEnd() {
        // Returns a value to the caller
        return this.lookTime < 0;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void end() {
    // End of a block/expression
    }
// End of a block/expression
}
