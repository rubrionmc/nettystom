// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.coordinate.CoordConversion;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.WorldBorder;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.network.packet.server.play.EntityVelocityPacket;
// Import of a required class
import net.minestom.server.utils.chunk.ChunkUtils;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;
// Import of a required class
import java.util.function.BooleanSupplier;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class EntityVelocityIntegrationTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void gravity(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        loadChunks(instance);

        // Calls a method
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Code statement
        env.tick(); // Ensure velocity downwards is present

        // Code statement
        testMovement(env, entity, new Vec(0.0, 42.0, 0.0),
                // Creates a new object
                new Vec(0.0, 41.92159999847412, 0.0),
                // Creates a new object
                new Vec(0.0, 41.76636799395752, 0.0),
                // Creates a new object
                new Vec(0.0, 41.53584062504456, 0.0),
                // Creates a new object
                new Vec(0.0, 41.231523797587016, 0.0),
                // Creates a new object
                new Vec(0.0, 40.85489329934836, 0.0),
                // Creates a new object
                new Vec(0.0, 40.40739540236494, 0.0),
                // Creates a new object
                new Vec(0.0, 40.0, 0.0));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleKnockback(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        loadChunks(instance);

        // Calls a method
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 40, 0)).join();
        // Calls a method
        env.tick();
        // Code statement
        env.tick(); // Ensures the entity is onGround
        // Calls a method
        entity.takeKnockback(0.4f, 0, -1);

        // Code statement
        testMovement(env, entity, new Vec(0.0, 40.0, 0.0),
                // Creates a new object
                new Vec(0.0, 40.360800005197525, 0.4000000059604645),
                // Creates a new object
                new Vec(0.0, 40.63598401564693, 0.6184000345826153),
                // Creates a new object
                new Vec(0.0, 40.827264349610196, 0.8171440663565412),
                // Creates a new object
                new Vec(0.0, 40.9363190790167, 0.9980011404830835),
                // Creates a new object
                new Vec(0.0, 40.96479271438924, 1.1625810826814025),
                // Creates a new object
                new Vec(0.0, 40.914296876071546, 1.3123488343981535),
                // Creates a new object
                new Vec(0.0, 40.7864109520312, 1.4486374923882126),
                // Creates a new object
                new Vec(0.0, 40.58268274250654, 1.5726601747334787),
                // Creates a new object
                new Vec(0.0, 40.304629091760695, 1.685520818920295),
                // Creates a new object
                new Vec(0.0, 40.0, 1.7882240080901861),
                // Creates a new object
                new Vec(0.0, 40.0, 1.8816839129282854),
                // Creates a new object
                new Vec(0.0, 40.0, 1.9327130268970532),
                // Creates a new object
                new Vec(0.0, 40.0, 1.9605749263602332),
                // Creates a new object
                new Vec(0.0, 40.0, 1.9757875252341128),
                // Creates a new object
                new Vec(0.0, 40.0, 1.9840936051840241),
                // Creates a new object
                new Vec(0.0, 40.0, 1.9886287253634418),
                // Creates a new object
                new Vec(0.0, 40.0, 1.9886287253634418));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void doubleKnockback(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        loadChunks(instance);

        // Calls a method
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 40, 0)).join();
        // Calls a method
        env.tick();
        // Code statement
        env.tick(); // Ensures the entity is onGround
        // Calls a method
        entity.takeKnockback(0.4f, 0, -1);
        // Calls a method
        entity.takeKnockback(0.5f, 0, -1);

        // Calls a method
        assertTrue(entity.hasVelocity());

        // Code statement
        testMovement(env, entity, new Vec(0.0, 40.0, 0.0),
                // Creates a new object
                new Vec(0.0, 40.4, 0.7000000029802322),
                // Creates a new object
                new Vec(0.0, 40.71360000610351, 1.0822000490009787),
                // Creates a new object
                new Vec(0.0, 40.94252801654052, 1.4300021009034531),
                // Creates a new object
                new Vec(0.0, 41.088477469609366, 1.7465019772561767),
                // Creates a new object
                new Vec(0.0, 41.153107934874726, 2.0345168730376946),
                // Creates a new object
                new Vec(0.0, 41.138045790541625, 2.2966104357523673),
                // Creates a new object
                new Vec(0.0, 41.04488488728202, 2.5351155846963964),
                // Creates a new object
                new Vec(0.0, 40.87518719878482, 2.7521552764905097),
                // Creates a new object
                new Vec(0.0, 40.630483459294965, 2.949661401715245),
                // Creates a new object
                new Vec(0.0, 40.312273788401676, 3.1293919808495585),
                // Creates a new object
                new Vec(0.0, 40.0, 3.292946812575406),
                // Creates a new object
                new Vec(0.0, 40.0, 3.441781713735323),
                // Creates a new object
                new Vec(0.0, 40.0, 3.523045579207649),
                // Creates a new object
                new Vec(0.0, 40.0, 3.56741565490924),
                // Creates a new object
                new Vec(0.0, 40.0, 3.5916417190562298),
                // Creates a new object
                new Vec(0.0, 40.0, 3.6048691516168874),
                // Creates a new object
                new Vec(0.0, 40.0, 3.6120913306338815),
                // Creates a new object
                new Vec(0.0, 40.0, 3.616034640835186));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void flyingVelocity(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        loadChunks(instance);

        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, 42, 0));
        // Calls a method
        env.tick();

        // Assigns a value
        final double epsilon = 0.000001;

        // Calls a method
        assertEquals(-1.568, player.getVelocity().y(), epsilon);
        // Calls a method
        double previousVelocity = player.getVelocity().y();

        // Calls a method
        player.setFlying(true);
        // Calls a method
        env.tick();

        // Every tick, the y velocity is multiplied by 0.6, and after 27 ticks it should be 0
        // Loop: repeats a block
        for (int i = 0; i < 22; i++) {
            // Calls a method
            assertEquals(player.getVelocity().y(), previousVelocity * 0.6, epsilon);
            // Calls a method
            previousVelocity = player.getVelocity().y();
            // Calls a method
            env.tick();
        // End of a block/expression
        }
        // Calls a method
        assertEquals(0, player.getVelocity().y());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void flyingPlayerMovement(Env env) {
        // Player movement should not send velocity packets as already client predicted
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, 42, 0));
        // Calls a method
        player.setFlying(true);
        // Calls a method
        var witness = env.createConnection();
        // Calls a method
        witness.connect(instance, new Pos(0, 42, 0));

        // Calls a method
        var tracker = witness.trackIncoming(EntityVelocityPacket.class);
        // Code statement
        env.tick(); // Process gravity velocity
        // Calls a method
        tracker.assertEmpty();
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testHasVelocity(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        loadChunks(instance);

        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Should  be false because the new entity should have no velocity
        // Calls a method
        assertFalse(entity.hasVelocity());

        // Calls a method
        entity.setInstance(instance, new Pos(0, 41, 0)).join();
        // Calls a method
        entity.setVelocity(new Vec(0, -10, 0));

        // Calls a method
        env.tick();

        // Should be true: The entity is currently falling (in the air), so it does have a velocity.
        // Only entities on the ground should ignore the default velocity.
        // Calls a method
        assertTrue(entity.hasVelocity());

        // Tick entity so it falls on the ground
        // Loop: repeats a block
        for (int i = 0; i < 5; i++) {
            // Calls a method
            entity.tick(0);
        // End of a block/expression
        }

        // Now that the entity is on the ground, it should no longer have a velocity.
        // Calls a method
        assertFalse(entity.hasVelocity());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void countVelocityPackets(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var viewerConnection = env.createConnection();
        // Calls a method
        viewerConnection.connect(instance, new Pos(1, 40, 1));
        // Calls a method
        var entity = new Entity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Pos(0, 40, 0)).join();
        // Calls a method
        instance.setBlock(new Vec(0, 39, 0), Block.STONE);
        // Code statement
        env.tick(); // Tick because the entity is in the air, they'll send velocity from gravity

        // Calls a method
        AtomicInteger i = new AtomicInteger();
        // Calls a method
        BooleanSupplier tickLoopCondition = () -> i.getAndIncrement() < Math.max(entity.getSynchronizationTicks() - 1, 19);

        // Calls a method
        var tracker = viewerConnection.trackIncoming(EntityVelocityPacket.class);

        // Calls a method
        entity.setVelocity(new Vec(0, 5, 0));
        // Calls a method
        tracker = viewerConnection.trackIncoming(EntityVelocityPacket.class);
        // Calls a method
        i.set(0);
        // Calls a method
        env.tickWhile(tickLoopCondition, null);
        // Code statement
        tracker.assertCount(1); // Verify the update is only sent once
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void velocityWorldBorder(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        loadChunks(instance);

        // Calls a method
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Calls a method
        var point = new Pos(1.9, 40, 0.2);
        // Calls a method
        instance.setWorldBorder(new WorldBorder(4, 0, 0, 0, 0));
        // Calls a method
        instance.setBlock(new Vec(1, 39, 0), Block.ICE);
        // Calls a method
        instance.setBlock(new Vec(1, 39, 1), Block.SOUL_SAND);
        // Calls a method
        entity.setInstance(instance, point).join();
        // Calls a method
        env.tick();
        // Code statement
        env.tick(); // Ensure the entity is onGround

        // Calls a method
        var initialVelocity = new Vec(10, 0, 25);
        // Calls a method
        entity.setVelocity(initialVelocity);
        // Calls a method
        env.tick();

        // Calls a method
        double horizontalAirResistance = entity.getAerodynamics().horizontalAirResistance();
        // Calls a method
        double oldFriction = Block.ICE.registry().friction();
        // Calls a method
        double newFriction = Block.SOUL_SAND.registry().friction();
        // Calls a method
        assertNotEquals(oldFriction, newFriction, Vec.EPSILON);

        // Assigns a value
        double expectedDrag = newFriction * horizontalAirResistance;
        // Assigns a value
        double expectedOldDrag = oldFriction * horizontalAirResistance;

        // Calls a method
        assertEquals(point.x(), entity.getPosition().x(), Vec.EPSILON);
        // Calls a method
        assertTrue(entity.getPosition().z() > point.z());
        // Calls a method
        assertEquals(initialVelocity.x() * expectedDrag, entity.getVelocity().x(), Vec.EPSILON);
        // Calls a method
        assertEquals(initialVelocity.z() * expectedDrag, entity.getVelocity().z(), Vec.EPSILON);
        // Calls a method
        assertNotEquals(initialVelocity.x() * expectedOldDrag, entity.getVelocity().x(), Vec.EPSILON);
    // End of a block/expression
    }

    // Start of a method/block
    private void testMovement(Env env, Entity entity, Vec... sample) {
        // Assigns a value
        final double epsilon = 0.003;
        // Loop: repeats a block
        for (Vec vec : sample) {
            // Calls a method
            assertEquals(vec.x(), entity.getPosition().x(), epsilon);
            // Calls a method
            assertEquals(vec.y(), entity.getPosition().y(), epsilon);
            // Calls a method
            assertEquals(vec.z(), entity.getPosition().z(), epsilon);
            // Calls a method
            env.tick();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void loadChunks(Instance instance) {
        // Start of a method/block
        ChunkUtils.optionalLoadAll(instance, new long[]{
                // Code statement
                CoordConversion.chunkIndex(-1, -1),
                // Code statement
                CoordConversion.chunkIndex(-1, 0),
                // Code statement
                CoordConversion.chunkIndex(-1, 1),
                // Code statement
                CoordConversion.chunkIndex(0, -1),
                // Code statement
                CoordConversion.chunkIndex(0, 0),
                // Code statement
                CoordConversion.chunkIndex(0, 1),
                // Code statement
                CoordConversion.chunkIndex(1, -1),
                // Code statement
                CoordConversion.chunkIndex(1, 0),
                // Code statement
                CoordConversion.chunkIndex(1, 1),
        // Calls a method
        }, null).join();
    // End of a block/expression
    }
// End of a block/expression
}
