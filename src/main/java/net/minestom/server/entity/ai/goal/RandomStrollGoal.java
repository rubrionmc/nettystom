// Package declaration for this file
package net.minestom.server.entity.ai.goal;

// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.EntityCreature;
// Import of a required class
import net.minestom.server.entity.ai.GoalSelector;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Random;
// Import of a required class
import java.util.concurrent.TimeUnit;

// Type declaration (class/interface/enum/record)
public class RandomStrollGoal extends GoalSelector {

    // Calls a method
    private static final long DELAY = TimeUnit.MILLISECONDS.toNanos(2500);

    // Code statement
    private final int radius;
    // Code statement
    private final List<Vec> closePositions;
    // Calls a method
    private final Random random = new Random();

    // Code statement
    private long lastStroll;

    // Start of a method/block
    public RandomStrollGoal(EntityCreature entityCreature, int radius) {
        // Access to the current/parent object
        super(entityCreature);
        // Access to the current/parent object
        this.radius = radius;
        // Access to the current/parent object
        this.closePositions = getNearbyBlocks(radius);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean shouldStart() {
        // Returns a value to the caller
        return System.nanoTime() - lastStroll >= DELAY;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void start() {
        // Calls a method
        int remainingAttempt = closePositions.size();
        // Loop: repeats a block
        while (remainingAttempt-- > 0) {
            // Calls a method
            final int index = random.nextInt(closePositions.size());
            // Calls a method
            final Vec position = closePositions.get(index);

            // Calls a method
            final var target = entityCreature.getPosition().add(position);
            // Calls a method
            final boolean result = entityCreature.getNavigator().setPathTo(target);
            // Branch: checks a condition
            if (result) {
                // Breaks out of the loop/block
                break;
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void tick(long time) {
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean shouldEnd() {
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void end() {
        // Access to the current/parent object
        this.lastStroll = System.nanoTime();
    // End of a block/expression
    }

    // Start of a method/block
    public int getRadius() {
        // Returns a value to the caller
        return radius;
    // End of a block/expression
    }

    // Start of a method/block
    private static List<Vec> getNearbyBlocks(int radius) {
        // Calls a method
        List<Vec> blocks = new ArrayList<>();
        // Loop: repeats a block
        for (int x = -radius; x <= radius; x++) {
            // Loop: repeats a block
            for (int y = -radius; y <= radius; y++) {
                // Loop: repeats a block
                for (int z = -radius; z <= radius; z++) {
                    // Calls a method
                    blocks.add(new Vec(x, y, z));
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return blocks;
    // End of a block/expression
    }
// End of a block/expression
}
