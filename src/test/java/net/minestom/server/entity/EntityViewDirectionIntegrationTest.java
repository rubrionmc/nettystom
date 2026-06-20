// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.network.packet.server.play.EntityHeadLookPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.EntityRotationPacket;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertTrue;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class EntityViewDirectionIntegrationTest {
    // Assigns a value
    private static final float EPSILON = 0.01f;

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void viewYawAndPitch(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        var spawnPos = new Pos(0, 40, 0);
        // Calls a method
        entity.setInstance(instance, spawnPos).join();
        // Calls a method
        entity.setView(0, 0);
        // Calls a method
        assertEquals(0, entity.getPosition().yaw());
        // Calls a method
        assertEquals(0, entity.getPosition().pitch());

        // Calls a method
        entity.setView(90, 0);
        // Calls a method
        assertEquals(90, entity.getPosition().yaw());
        // Calls a method
        assertEquals(0, entity.getPosition().pitch());

        // Calls a method
        entity.setView(0, 42);
        // Calls a method
        assertEquals(0, entity.getPosition().yaw());
        // Calls a method
        assertEquals(42, entity.getPosition().pitch());

        // Calls a method
        entity.setView(37, 26);
        // Calls a method
        assertEquals(37, entity.getPosition().yaw());
        // Calls a method
        assertEquals(26, entity.getPosition().pitch());

        // check for NaN values
        // Calls a method
        entity.setView(Float.NaN, 0);
        // Calls a method
        assertTrue(Float.isNaN(entity.getPosition().yaw()));
        // Calls a method
        assertEquals(0, entity.getPosition().pitch());

        // Calls a method
        entity.setView(0, Float.NaN);
        // Calls a method
        assertEquals(0, entity.getPosition().yaw());
        // Calls a method
        assertTrue(Float.isNaN(entity.getPosition().pitch()));

        // Calls a method
        entity.setView(Float.NaN, Float.NaN);
        // Calls a method
        assertTrue(Float.isNaN(entity.getPosition().yaw()));
        // Calls a method
        assertTrue(Float.isNaN(entity.getPosition().pitch()));

        // Calls a method
        env.tick();

        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, spawnPos);
        // Calls a method
        var player2 = env.createPlayer(instance, spawnPos);

        // Calls a method
        env.tick();

        // Calls a method
        var vehicle = new Entity(EntityType.SHEEP);
        // Calls a method
        vehicle.setInstance(instance, new Pos(0, 40, 0)).join();
        // Calls a method
        vehicle.addPassenger(player2);

        // Calls a method
        var rotationTracker = connection.trackIncoming(EntityRotationPacket.class);
        // Calls a method
        var headLookTracker = connection.trackIncoming(EntityHeadLookPacket.class);

        // Calls a method
        player2.setSynchronizationTicks(1);
        // Calls a method
        player2.setView(90, 45);

        // Calls a method
        env.tick();

        // Calls a method
        rotationTracker.assertCount(1);
        // Calls a method
        headLookTracker.assertCount(1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void lookAtPos(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Assigns a value
        double eyeHeight = entity.getEyeHeight(); // adding this to some position Y coordinates, to look horizontally
        
        // Calls a method
        entity.setInstance(instance, new Pos(0, 40, 0)).join();

        // make it look at its feet's position, it should look down
        // Calls a method
        entity.lookAt(entity.getPosition());
        // looking vertically, not checking yaw
        // Calls a method
        assertEquals(90f, entity.getPosition().pitch());

        // Calls a method
        entity.lookAt(new Pos(16, 40 + eyeHeight, 16));
        // Calls a method
        assertEquals(-45f, entity.getPosition().yaw());
        // Calls a method
        assertEquals(0f, entity.getPosition().pitch(), EPSILON);

        // Calls a method
        entity.lookAt(new Pos(-16, 40 + eyeHeight, 56));
        // Calls a method
        assertEquals(15.94f, entity.getPosition().yaw(), EPSILON);
        // Calls a method
        assertEquals(0f, entity.getPosition().pitch(), EPSILON);

        // Calls a method
        entity.lookAt(new Pos(48, 36, 48));
        // Calls a method
        assertEquals(-45f, entity.getPosition().yaw(), EPSILON);
        // Calls a method
        assertEquals(6.81f, entity.getPosition().pitch(), EPSILON);

        // Calls a method
        entity.lookAt(new Pos(48, 36, -17));
        // Calls a method
        assertEquals(-109.50f, entity.getPosition().yaw(), EPSILON);
        // should have the same pitch as the previous position
        // Calls a method
        assertEquals(6.81f, entity.getPosition().pitch(), EPSILON);

        // Calls a method
        entity.lookAt(new Pos(0, 87, 0));
        // looking from below, not checking the yaw
        // Calls a method
        assertEquals(-90f, entity.getPosition().pitch(), EPSILON);

        // Calls a method
        entity.lookAt(new Pos(-25, 42, 4));
        // Calls a method
        assertEquals(80.90f, entity.getPosition().yaw(), EPSILON);
        // Calls a method
        assertEquals(-0.59f, entity.getPosition().pitch(), EPSILON);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void lookAtEntitySameType(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // same type, same eye height
        // Calls a method
        var e1 = new Entity(EntityType.ZOMBIE);
        // Calls a method
        var e2 = new Entity(EntityType.ZOMBIE);
        // Calls a method
        e1.setInstance(instance, new Pos(0, 40, 0)).join();
        // Calls a method
        e2.setInstance(instance, new Pos(0, 40, 0)).join();

        // look at an entity with the same eye height and same position,
        // direction should not change
        // Calls a method
        float prevYaw = e1.getPosition().yaw();
        // Calls a method
        float prevPitch = e1.getPosition().pitch();
        // Calls a method
        e1.lookAt(e2);
        // Calls a method
        assertEquals(prevYaw, e1.getPosition().yaw());
        // Calls a method
        assertEquals(prevPitch, e1.getPosition().pitch());

        // Calls a method
        e2.teleport(new Pos(0, 50, 0)).join();
        // Calls a method
        e1.lookAt(e2);
        // e2 is above e1, the pich should be negative
        // Calls a method
        assertEquals(-90f, e1.getPosition().pitch(), EPSILON);

        // Calls a method
        e2.teleport(new Pos(0, 10, 0)).join();
        // Calls a method
        e1.lookAt(e2);
        // e2 is below e1, the pich should be positive
        // Calls a method
        assertEquals(90f, e1.getPosition().pitch(), EPSILON);

        // Calls a method
        e2.teleport(new Pos(16, 40, 16)).join();
        // Calls a method
        e1.lookAt(e2);
        // Calls a method
        assertEquals(-45f, e1.getPosition().yaw(), EPSILON);
        // e2 has the same y as e1, the pich should be 0
        // Calls a method
        assertEquals(0f, e1.getPosition().pitch(), EPSILON);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void lookAtEntityDifferentType(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // same type, same eye height
        // Calls a method
        var e1 = new Entity(EntityType.ZOMBIE);
        // a chicken has a lower eye height than a zombie
        // Calls a method
        var e2 = new Entity(EntityType.CHICKEN);
        // Calls a method
        e1.setInstance(instance, new Pos(0, 40, 0)).join();
        // Calls a method
        e2.setInstance(instance, new Pos(0, 40, 0)).join();

        // Calls a method
        e1.lookAt(e2);
        // e2 eyes are below e1, the pich should be positive
        // Calls a method
        assertEquals(90f, e1.getPosition().pitch(), EPSILON);

        // Calls a method
        double eyeDifference = e1.getEyeHeight() - e2.getEyeHeight();
        // Calls a method
        assertTrue(eyeDifference > 0);
        // Calls a method
        var pos = new Pos(0, e1.getPosition().y() + eyeDifference, 0);
        // Calls a method
        e2.teleport(pos).join();
        // e2 eyes are in the same position as e1, direction should not change
        // Calls a method
        float prevYaw = e1.getPosition().yaw();
        // Calls a method
        float prevPitch = e1.getPosition().pitch();
        // Calls a method
        e1.lookAt(e2);
        // Calls a method
        assertEquals(prevYaw, e1.getPosition().yaw());
        // Calls a method
        assertEquals(prevPitch, e1.getPosition().pitch());

        // Calls a method
        pos = new Pos(10, e1.getPosition().y() + eyeDifference, 10);
        // Calls a method
        e2.teleport(pos).join();
        // Calls a method
        e1.lookAt(e2);
        // e2 eyes are at the same height as e1's, the pitch should be 0
        // Calls a method
        assertEquals(0f, e1.getPosition().pitch(), EPSILON);

        // Calls a method
        e2.teleport(new Pos(-16, 40, -16)).join();
        // Calls a method
        e1.lookAt(e2);
        // Calls a method
        assertEquals(135f, e1.getPosition().yaw(), EPSILON);
        // Calls a method
        assertEquals(3.91f, e1.getPosition().pitch(), EPSILON);

        // Calls a method
        e2.teleport(new Pos(8, 50, -32)).join();
        // Calls a method
        e1.lookAt(e2);
        // Calls a method
        assertEquals(-165.96f, e1.getPosition().yaw(), EPSILON);
        // Calls a method
        assertEquals(-15.54f, e1.getPosition().pitch(), EPSILON);

        // Calls a method
        e2.teleport(new Pos(0, 30, -2)).join();
        // Calls a method
        e1.lookAt(e2);
        // Calls a method
        assertEquals(180f, e1.getPosition().yaw(), EPSILON);
        // Calls a method
        assertEquals(79.78f, e1.getPosition().pitch(), EPSILON);
    // End of a block/expression
    }
// End of a block/expression
}
