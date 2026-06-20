// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.collision.BoundingBox;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.event.item.PickupItemEvent;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class EntityBoundingBoxIntegrationTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void pose(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Bounding box should be from the registry
        // Calls a method
        assertEquals(player.getEntityType().registry().boundingBox(), player.getBoundingBox());
        // Calls a method
        player.setPose(EntityPose.STANDING);
        // Calls a method
        assertEquals(player.getEntityType().registry().boundingBox(), player.getBoundingBox());

        // Calls a method
        player.setPose(EntityPose.SLEEPING);
        // Calls a method
        assertEquals(new BoundingBox(0.2, 0.2, 0.2), player.getBoundingBox());

        // Calls a method
        player.setPose(EntityPose.SNEAKING);
        // Calls a method
        assertEquals(new BoundingBox(0.6, 1.5, 0.6), player.getBoundingBox());

        // Calls a method
        player.setPose(EntityPose.FALL_FLYING);
        // Calls a method
        assertEquals(new BoundingBox(0.6, 0.6, 0.6), player.getBoundingBox());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void eyeHeight(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Calls a method
        assertEquals(1.62, player.getEyeHeight());

        // Calls a method
        player.setPose(EntityPose.SLEEPING);
        // Calls a method
        assertEquals(0.2, player.getEyeHeight());

        // Calls a method
        player.setPose(EntityPose.SNEAKING);
        // Calls a method
        assertEquals(1.27, player.getEyeHeight());

        // Calls a method
        player.setPose(EntityPose.FALL_FLYING);
        // Calls a method
        assertEquals(0.4, player.getEyeHeight());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void pickupItem(Env env) {
        // Calls a method
        final var instance = env.createFlatInstance();
        // Calls a method
        final var listener = env.listen(PickupItemEvent.class);
        // Calls a method
        final var spawnPos = new Pos(0, 42, 0);
        // Calls a method
        final var entity = new LivingEntity(EntityType.ZOMBIE);
        // Calls a method
        entity.setCanPickupItem(true);
        // Calls a method
        entity.setInstance(instance, spawnPos).join();

        // 0 is fine here, it's just a delta
        // Assigns a value
        var time = 0L;

        // Calls a method
        dropItem(instance, spawnPos);
        // Calls a method
        listener.followup();
        // Calls a method
        entity.update(time += 1_000L);

        // Calls a method
        dropItem(instance, spawnPos.sub(.5));
        // Calls a method
        listener.followup();
        // Calls a method
        entity.update(time + 1_000L);
    // End of a block/expression
    }

    // Start of a method/block
    private void dropItem(final Instance instance, final Pos position) {
        // Calls a method
        final var entity = new ItemEntity(ItemStack.of(Material.STONE));
        // Assigns a value
        entity.hasPhysics = false;
        // Calls a method
        entity.setNoGravity(true);
        // Calls a method
        entity.setInstance(instance, position).join();
    // End of a block/expression
    }
// End of a block/expression
}
