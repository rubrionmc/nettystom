// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.CoordConversion;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.WorldBorder;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.EntityVelocityPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.chunk.ChunkUtils;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;
// Import d'une classe nécessaire
import java.util.function.BooleanSupplier;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class EntityVelocityIntegrationTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void gravity(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        loadChunks(instance);

        // Appelle une méthode
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        // Instruction de code
        env.tick(); // Ensure velocity downwards is present

        // Instruction de code
        testMovement(env, entity, new Vec(0.0, 42.0, 0.0),
                // Crée un nouvel objet
                new Vec(0.0, 41.92159999847412, 0.0),
                // Crée un nouvel objet
                new Vec(0.0, 41.76636799395752, 0.0),
                // Crée un nouvel objet
                new Vec(0.0, 41.53584062504456, 0.0),
                // Crée un nouvel objet
                new Vec(0.0, 41.231523797587016, 0.0),
                // Crée un nouvel objet
                new Vec(0.0, 40.85489329934836, 0.0),
                // Crée un nouvel objet
                new Vec(0.0, 40.40739540236494, 0.0),
                // Crée un nouvel objet
                new Vec(0.0, 40.0, 0.0));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleKnockback(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        loadChunks(instance);

        // Appelle une méthode
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 40, 0)).join();
        // Appelle une méthode
        env.tick();
        // Instruction de code
        env.tick(); // Ensures the entity is onGround
        // Appelle une méthode
        entity.takeKnockback(0.4f, 0, -1);

        // Instruction de code
        testMovement(env, entity, new Vec(0.0, 40.0, 0.0),
                // Crée un nouvel objet
                new Vec(0.0, 40.360800005197525, 0.4000000059604645),
                // Crée un nouvel objet
                new Vec(0.0, 40.63598401564693, 0.6184000345826153),
                // Crée un nouvel objet
                new Vec(0.0, 40.827264349610196, 0.8171440663565412),
                // Crée un nouvel objet
                new Vec(0.0, 40.9363190790167, 0.9980011404830835),
                // Crée un nouvel objet
                new Vec(0.0, 40.96479271438924, 1.1625810826814025),
                // Crée un nouvel objet
                new Vec(0.0, 40.914296876071546, 1.3123488343981535),
                // Crée un nouvel objet
                new Vec(0.0, 40.7864109520312, 1.4486374923882126),
                // Crée un nouvel objet
                new Vec(0.0, 40.58268274250654, 1.5726601747334787),
                // Crée un nouvel objet
                new Vec(0.0, 40.304629091760695, 1.685520818920295),
                // Crée un nouvel objet
                new Vec(0.0, 40.0, 1.7882240080901861),
                // Crée un nouvel objet
                new Vec(0.0, 40.0, 1.8816839129282854),
                // Crée un nouvel objet
                new Vec(0.0, 40.0, 1.9327130268970532),
                // Crée un nouvel objet
                new Vec(0.0, 40.0, 1.9605749263602332),
                // Crée un nouvel objet
                new Vec(0.0, 40.0, 1.9757875252341128),
                // Crée un nouvel objet
                new Vec(0.0, 40.0, 1.9840936051840241),
                // Crée un nouvel objet
                new Vec(0.0, 40.0, 1.9886287253634418),
                // Crée un nouvel objet
                new Vec(0.0, 40.0, 1.9886287253634418));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void doubleKnockback(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        loadChunks(instance);

        // Appelle une méthode
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 40, 0)).join();
        // Appelle une méthode
        env.tick();
        // Instruction de code
        env.tick(); // Ensures the entity is onGround
        // Appelle une méthode
        entity.takeKnockback(0.4f, 0, -1);
        // Appelle une méthode
        entity.takeKnockback(0.5f, 0, -1);

        // Appelle une méthode
        assertTrue(entity.hasVelocity());

        // Instruction de code
        testMovement(env, entity, new Vec(0.0, 40.0, 0.0),
                // Crée un nouvel objet
                new Vec(0.0, 40.4, 0.7000000029802322),
                // Crée un nouvel objet
                new Vec(0.0, 40.71360000610351, 1.0822000490009787),
                // Crée un nouvel objet
                new Vec(0.0, 40.94252801654052, 1.4300021009034531),
                // Crée un nouvel objet
                new Vec(0.0, 41.088477469609366, 1.7465019772561767),
                // Crée un nouvel objet
                new Vec(0.0, 41.153107934874726, 2.0345168730376946),
                // Crée un nouvel objet
                new Vec(0.0, 41.138045790541625, 2.2966104357523673),
                // Crée un nouvel objet
                new Vec(0.0, 41.04488488728202, 2.5351155846963964),
                // Crée un nouvel objet
                new Vec(0.0, 40.87518719878482, 2.7521552764905097),
                // Crée un nouvel objet
                new Vec(0.0, 40.630483459294965, 2.949661401715245),
                // Crée un nouvel objet
                new Vec(0.0, 40.312273788401676, 3.1293919808495585),
                // Crée un nouvel objet
                new Vec(0.0, 40.0, 3.292946812575406),
                // Crée un nouvel objet
                new Vec(0.0, 40.0, 3.441781713735323),
                // Crée un nouvel objet
                new Vec(0.0, 40.0, 3.523045579207649),
                // Crée un nouvel objet
                new Vec(0.0, 40.0, 3.56741565490924),
                // Crée un nouvel objet
                new Vec(0.0, 40.0, 3.5916417190562298),
                // Crée un nouvel objet
                new Vec(0.0, 40.0, 3.6048691516168874),
                // Crée un nouvel objet
                new Vec(0.0, 40.0, 3.6120913306338815),
                // Crée un nouvel objet
                new Vec(0.0, 40.0, 3.616034640835186));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void flyingVelocity(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        loadChunks(instance);

        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        env.tick();

        // Affecte une valeur
        final double epsilon = 0.000001;

        // Appelle une méthode
        assertEquals(-1.568, player.getVelocity().y(), epsilon);
        // Appelle une méthode
        double previousVelocity = player.getVelocity().y();

        // Appelle une méthode
        player.setFlying(true);
        // Appelle une méthode
        env.tick();

        // Every tick, the y velocity is multiplied by 0.6, and after 27 ticks it should be 0
        // Boucle : répète un bloc
        for (int i = 0; i < 22; i++) {
            // Appelle une méthode
            assertEquals(player.getVelocity().y(), previousVelocity * 0.6, epsilon);
            // Appelle une méthode
            previousVelocity = player.getVelocity().y();
            // Appelle une méthode
            env.tick();
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertEquals(0, player.getVelocity().y());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void flyingPlayerMovement(Env env) {
        // Player movement should not send velocity packets as already client predicted
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        player.setFlying(true);
        // Appelle une méthode
        var witness = env.createConnection();
        // Appelle une méthode
        witness.connect(instance, new Pos(0, 42, 0));

        // Appelle une méthode
        var tracker = witness.trackIncoming(EntityVelocityPacket.class);
        // Instruction de code
        env.tick(); // Process gravity velocity
        // Appelle une méthode
        tracker.assertEmpty();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testHasVelocity(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        loadChunks(instance);

        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Should  be false because the new entity should have no velocity
        // Appelle une méthode
        assertFalse(entity.hasVelocity());

        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 41, 0)).join();
        // Appelle une méthode
        entity.setVelocity(new Vec(0, -10, 0));

        // Appelle une méthode
        env.tick();

        // Should be true: The entity is currently falling (in the air), so it does have a velocity.
        // Only entities on the ground should ignore the default velocity.
        // Appelle une méthode
        assertTrue(entity.hasVelocity());

        // Tick entity so it falls on the ground
        // Boucle : répète un bloc
        for (int i = 0; i < 5; i++) {
            // Appelle une méthode
            entity.tick(0);
        // Fin d'un bloc/d'une expression
        }

        // Now that the entity is on the ground, it should no longer have a velocity.
        // Appelle une méthode
        assertFalse(entity.hasVelocity());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void countVelocityPackets(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var viewerConnection = env.createConnection();
        // Appelle une méthode
        viewerConnection.connect(instance, new Pos(1, 40, 1));
        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 40, 0)).join();
        // Appelle une méthode
        instance.setBlock(new Vec(0, 39, 0), Block.STONE);
        // Instruction de code
        env.tick(); // Tick because the entity is in the air, they'll send velocity from gravity

        // Appelle une méthode
        AtomicInteger i = new AtomicInteger();
        // Appelle une méthode
        BooleanSupplier tickLoopCondition = () -> i.getAndIncrement() < Math.max(entity.getSynchronizationTicks() - 1, 19);

        // Appelle une méthode
        var tracker = viewerConnection.trackIncoming(EntityVelocityPacket.class);

        // Appelle une méthode
        entity.setVelocity(new Vec(0, 5, 0));
        // Appelle une méthode
        tracker = viewerConnection.trackIncoming(EntityVelocityPacket.class);
        // Appelle une méthode
        i.set(0);
        // Appelle une méthode
        env.tickWhile(tickLoopCondition, null);
        // Instruction de code
        tracker.assertCount(1); // Verify the update is only sent once
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void velocityWorldBorder(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        loadChunks(instance);

        // Appelle une méthode
        var entity = new Entity(EntityTypes.ZOMBIE);
        // Appelle une méthode
        var point = new Pos(1.9, 40, 0.2);
        // Appelle une méthode
        instance.setWorldBorder(new WorldBorder(4, 0, 0, 0, 0));
        // Appelle une méthode
        instance.setBlock(new Vec(1, 39, 0), Block.ICE);
        // Appelle une méthode
        instance.setBlock(new Vec(1, 39, 1), Block.SOUL_SAND);
        // Appelle une méthode
        entity.setInstance(instance, point).join();
        // Appelle une méthode
        env.tick();
        // Instruction de code
        env.tick(); // Ensure the entity is onGround

        // Appelle une méthode
        var initialVelocity = new Vec(10, 0, 25);
        // Appelle une méthode
        entity.setVelocity(initialVelocity);
        // Appelle une méthode
        env.tick();

        // Appelle une méthode
        double horizontalAirResistance = entity.getAerodynamics().horizontalAirResistance();
        // Appelle une méthode
        double oldFriction = Block.ICE.registry().friction();
        // Appelle une méthode
        double newFriction = Block.SOUL_SAND.registry().friction();
        // Appelle une méthode
        assertNotEquals(oldFriction, newFriction, Vec.EPSILON);

        // Affecte une valeur
        double expectedDrag = newFriction * horizontalAirResistance;
        // Affecte une valeur
        double expectedOldDrag = oldFriction * horizontalAirResistance;

        // Appelle une méthode
        assertEquals(point.x(), entity.getPosition().x(), Vec.EPSILON);
        // Appelle une méthode
        assertTrue(entity.getPosition().z() > point.z());
        // Appelle une méthode
        assertEquals(initialVelocity.x() * expectedDrag, entity.getVelocity().x(), Vec.EPSILON);
        // Appelle une méthode
        assertEquals(initialVelocity.z() * expectedDrag, entity.getVelocity().z(), Vec.EPSILON);
        // Appelle une méthode
        assertNotEquals(initialVelocity.x() * expectedOldDrag, entity.getVelocity().x(), Vec.EPSILON);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void testMovement(Env env, Entity entity, Vec... sample) {
        // Affecte une valeur
        final double epsilon = 0.003;
        // Boucle : répète un bloc
        for (Vec vec : sample) {
            // Appelle une méthode
            assertEquals(vec.x(), entity.getPosition().x(), epsilon);
            // Appelle une méthode
            assertEquals(vec.y(), entity.getPosition().y(), epsilon);
            // Appelle une méthode
            assertEquals(vec.z(), entity.getPosition().z(), epsilon);
            // Appelle une méthode
            env.tick();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void loadChunks(Instance instance) {
        // Début d'une méthode/d'un bloc
        ChunkUtils.optionalLoadAll(instance, new long[]{
                // Instruction de code
                CoordConversion.chunkIndex(-1, -1),
                // Instruction de code
                CoordConversion.chunkIndex(-1, 0),
                // Instruction de code
                CoordConversion.chunkIndex(-1, 1),
                // Instruction de code
                CoordConversion.chunkIndex(0, -1),
                // Instruction de code
                CoordConversion.chunkIndex(0, 0),
                // Instruction de code
                CoordConversion.chunkIndex(0, 1),
                // Instruction de code
                CoordConversion.chunkIndex(1, -1),
                // Instruction de code
                CoordConversion.chunkIndex(1, 0),
                // Instruction de code
                CoordConversion.chunkIndex(1, 1),
        // Appelle une méthode
        }, null).join();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
