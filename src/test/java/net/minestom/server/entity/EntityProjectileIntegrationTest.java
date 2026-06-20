// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class EntityProjectileIntegrationTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void gravityVelocity(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var shooter = new EntityCreature(EntityType.SKELETON);
        // Calls a method
        shooter.setInstance(instance, new Pos(0, 42, 0)).join();
        // Calls a method
        var projectile = new EntityProjectile(shooter, EntityType.ARROW);
        // Assigns a value
        var from = new Pos(0, 42, 0).add(0,
                // Calls a method
                shooter.getEyeHeight(), shooter.getPosition().direction().z());
        // Calls a method
        var target = from.add(0, 0, 10);
        // Calls a method
        projectile.setInstance(instance, from).join();
        // Calls a method
        projectile.shoot(target, 1, 0);

        // Assigns a value
        var before = projectile.getPosition(); // at start
        // Assigns a value
        var after = projectile.getPosition(); // now - 1 tick, closest to target
        // Assigns a value
        var smallestDistance = 1e6;
        // Loop: repeats a block
        while (true) {
            // Calls a method
            final var distance = projectile.getPosition().distanceSquared(target);
            // Branch: checks a condition
            if (distance <= smallestDistance) smallestDistance = distance;
            // Alternative branch of the condition
            else break;

            // Calls a method
            after = projectile.getPosition();
            // Calls a method
            env.tick();
        // End of a block/expression
        }

        // Ensure the position is correct.
        // x doesn't change
        // Big delta because ticks aren't very accurate
        // Calls a method
        assertEquals(before.x(), after.x());
        // Calls a method
        assertEquals(target.y(), after.y(), 0.6);
        // Calls a method
        assertEquals(target.z(), after.z(), 0.6);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void noGravityVelocity(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var shooter = new EntityCreature(EntityType.SKELETON);
        // Calls a method
        shooter.setInstance(instance, new Pos(0, 42, 0)).join();
        // Calls a method
        var projectile = new EntityProjectile(shooter, EntityType.ARROW);
        // Assigns a value
        var from = new Pos(0, 42, 0).add(0,
                // Calls a method
                shooter.getEyeHeight(), shooter.getPosition().direction().z());
        // Calls a method
        var target = from.add(0, 0, 10);
        // Calls a method
        projectile.setNoGravity(true);
        // Calls a method
        projectile.setInstance(instance, from).join();
        // Calls a method
        projectile.shoot(target, 1, 0);

        // Assigns a value
        var before = projectile.getPosition(); // at start
        // Assigns a value
        var after = projectile.getPosition(); // now - 1 tick, closest to target
        // Assigns a value
        var smallestDistance = 1e6;
        // Loop: repeats a block
        while (true) {
            // Calls a method
            final var distance = projectile.getPosition().distanceSquared(target);
            // Branch: checks a condition
            if (distance <= smallestDistance) smallestDistance = distance;
            // Alternative branch of the condition
            else break;

            // Calls a method
            after = projectile.getPosition();
            // Calls a method
            env.tick();
        // End of a block/expression
        }

        // x and y don't change (no gravity) and z changes by Σz velocity.
        // Calls a method
        assertEquals(before.x(), after.x());
        // Calls a method
        assertEquals(before.y(), after.y());
        // Calls a method
        assertEquals(target.z(), after.z(), 0.05);
    // End of a block/expression
    }
// End of a block/expression
}
