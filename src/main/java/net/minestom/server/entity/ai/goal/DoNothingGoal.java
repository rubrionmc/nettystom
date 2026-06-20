// Package declaration for this file
package net.minestom.server.entity.ai.goal;

// Import of a required class
import net.minestom.server.entity.EntityCreature;
// Import of a required class
import net.minestom.server.entity.ai.GoalSelector;
// Import of a required class
import net.minestom.server.utils.MathUtils;

// Import of a required class
import java.util.Random;
// Import of a required class
import java.util.concurrent.TimeUnit;

// Type declaration (class/interface/enum/record)
public class DoNothingGoal extends GoalSelector {

    // Calls a method
    private static final Random RANDOM = new Random();

    // Code statement
    private final long time;
    // Code statement
    private final float chance;
    // Code statement
    private long startTime;

    /**
     * Create a DoNothing goal
     *
     * @param entityCreature the entity
     * @param time           the time in milliseconds where nothing happen
     * @param chance         the chance to do nothing (0-1)
     */
    // Start of a method/block
    public DoNothingGoal(EntityCreature entityCreature, long time, float chance) {
        // Access to the current/parent object
        super(entityCreature);
        // Access to the current/parent object
        this.time = TimeUnit.MILLISECONDS.toNanos(time);
        // Access to the current/parent object
        this.chance = MathUtils.clamp(chance, 0, 1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void end() {
        // Access to the current/parent object
        this.startTime = 0;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean shouldEnd() {
        // Returns a value to the caller
        return System.nanoTime() - startTime >= time;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean shouldStart() {
        // Returns a value to the caller
        return RANDOM.nextFloat() <= chance;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void start() {
        // Access to the current/parent object
        this.startTime = System.nanoTime();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void tick(long time) {

    // End of a block/expression
    }
// End of a block/expression
}
