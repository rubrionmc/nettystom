// Package declaration for this file
package net.minestom.server.collision;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityProjectile;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.entity.LivingEntity;
// Import of a required class
import net.minestom.server.event.EventNode;
// Import of a required class
import net.minestom.server.event.entity.projectile.ProjectileCollideWithBlockEvent;
// Import of a required class
import net.minestom.server.event.entity.projectile.ProjectileCollideWithEntityEvent;
// Import of a required class
import net.minestom.server.event.entity.projectile.ProjectileUncollideEvent;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.WorldBorder;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.utils.time.TimeUnit;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.concurrent.atomic.AtomicReference;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class EntityProjectileCollisionIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void blockShootAndBlockRemoval(Env env) {
        // Calls a method
        final Instance instance = env.createFlatInstance();
        // Calls a method
        instance.setWorldBorder(WorldBorder.DEFAULT_BORDER.withDiameter(1000));

        // Calls a method
        final Entity shooter = new Entity(EntityType.SKELETON);
        // Calls a method
        shooter.setInstance(instance, new Pos(0, 40, 0)).join();

        // Calls a method
        final EntityProjectile projectile = new EntityProjectile(shooter, EntityType.ARROW);
        // Calls a method
        projectile.setInstance(instance, shooter.getPosition().withY(y -> y + shooter.getEyeHeight())).join();

        // Calls a method
        final Point blockPosition = new Vec(5, 40, 0);
        // Assigns a value
        final Block block = Block.GRASS_BLOCK;
        // Calls a method
        instance.setBlock(blockPosition, block);
        // Calls a method
        projectile.shoot(blockPosition, 1, 0);

        // Calls a method
        final var eventRef = new AtomicReference<ProjectileCollideWithBlockEvent>();
        // Calls a method
        MinecraftServer.getGlobalEventHandler().addListener(ProjectileCollideWithBlockEvent.class, eventRef::set);

        // Calls a method
        final long tick = TimeUnit.SERVER_TICK.getDuration().toMillis();
        // Loop: repeats a block
        for (int i = 0; i < ServerFlag.SERVER_TICKS_PER_SECOND; ++i) {
            // Calls a method
            projectile.tick(i * tick);
        // End of a block/expression
        }

        // Calls a method
        var event = eventRef.get();
        // Calls a method
        assertNotNull(event);
        // Calls a method
        assertEquals(blockPosition, new Vec(event.getCollisionPosition().blockX(), event.getCollisionPosition().blockY(), event.getCollisionPosition().blockZ()));
        // Calls a method
        assertEquals(block, event.getBlock());

        // Calls a method
        final var eventRef2 = new AtomicReference<ProjectileUncollideEvent>();
        // Calls a method
        MinecraftServer.getGlobalEventHandler().addListener(ProjectileUncollideEvent.class, eventRef2::set);
        // Calls a method
        eventRef.set(null);
        // Calls a method
        instance.setBlock(blockPosition, Block.AIR);

        // Loop: repeats a block
        for (int i = 0; i < ServerFlag.SERVER_TICKS_PER_SECOND; ++i) {
            // Calls a method
            projectile.tick((ServerFlag.SERVER_TICKS_PER_SECOND + i) * tick);
        // End of a block/expression
        }
        // Calls a method
        event = eventRef.get();
        // Calls a method
        final var event2 = eventRef2.get();
        // Calls a method
        assertNotNull(event);
        // Calls a method
        assertNotNull(event2);
        // Calls a method
        assertEquals(blockPosition.withY(y -> y - 1), new Vec(event.getCollisionPosition().blockX(), event.getCollisionPosition().blockY(), event.getCollisionPosition().blockZ()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entityShoot(Env env) {
        // Calls a method
        final Instance instance = env.createFlatInstance();
        // Calls a method
        instance.setWorldBorder(WorldBorder.DEFAULT_BORDER.withDiameter(1000));

        // Calls a method
        final Entity shooter = new Entity(EntityType.SKELETON);
        // Calls a method
        shooter.setInstance(instance, new Pos(0, 40, 0)).join();

        // Loop: repeats a block
        for (double dx = 1; dx <= 3; dx += .2) {
            // Calls a method
            singleEntityShoot(instance, shooter, new Vec(dx, 40, 0));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Code statement
    private void singleEntityShoot(
            // Code statement
            Instance instance,
            // Code statement
            Entity shooter,
            // Code statement
            final Point targetPosition
    // Start of a method/block
    ) {
        // Calls a method
        final EntityProjectile projectile = new EntityProjectile(shooter, EntityType.ARROW);
        // Calls a method
        projectile.setInstance(instance, shooter.getPosition().withY(y -> y + shooter.getEyeHeight())).join();

        // Calls a method
        final LivingEntity target = new LivingEntity(EntityType.RABBIT);
        // Calls a method
        target.setInstance(instance, targetPosition.asPos()).join();
        // Calls a method
        projectile.shoot(targetPosition, 1, 0);

        // Calls a method
        final var eventRef = new AtomicReference<ProjectileCollideWithEntityEvent>();
        // Calls a method
        final var eventNode = EventNode.all("projectile-test");
        // Start of a method/block
        eventNode.addListener(ProjectileCollideWithEntityEvent.class, event -> {
            // Calls a method
            event.getEntity().remove();
            // Calls a method
            eventRef.set(event);
            // Calls a method
            MinecraftServer.getGlobalEventHandler().removeChild(eventNode);
        // End of a block/expression
        });
        // Calls a method
        MinecraftServer.getGlobalEventHandler().addChild(eventNode);

        // Calls a method
        final long tick = TimeUnit.SERVER_TICK.getDuration().toMillis();
        // Loop: repeats a block
        for (int i = 0; i < ServerFlag.SERVER_TICKS_PER_SECOND; ++i) {
            // Branch: checks a condition
            if (!projectile.isRemoved()) {
                // Calls a method
                projectile.tick(i * tick);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        final var event = eventRef.get();
        // Calls a method
        assertNotNull(event, "Could not hit entity at " + targetPosition);
        // Calls a method
        assertSame(target, event.getTarget());
        // Calls a method
        assertTrue(projectile.getBoundingBox().intersectEntity(event.getCollisionPosition(), target));
        // Calls a method
        target.remove();
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void entitySelfShoot(Env env) {
        // Calls a method
        final Instance instance = env.createFlatInstance();
        // Calls a method
        instance.setWorldBorder(WorldBorder.DEFAULT_BORDER.withDiameter(1000));

        // Calls a method
        final LivingEntity shooter = new LivingEntity(EntityType.SKELETON);
        // Calls a method
        shooter.setInstance(instance, new Pos(0, 40, 0)).join();

        // Calls a method
        final EntityProjectile projectile = new EntityProjectile(shooter, EntityType.ARROW);
        // Calls a method
        projectile.setInstance(instance, shooter.getPosition().withY(y -> y + shooter.getEyeHeight())).join();

        // Calls a method
        projectile.shoot(new Vec(0, 60, 0), 1, 0);

        // Calls a method
        final var eventRef = new AtomicReference<ProjectileCollideWithEntityEvent>();
        // Start of a method/block
        MinecraftServer.getGlobalEventHandler().addListener(ProjectileCollideWithEntityEvent.class, event -> {
            // Calls a method
            event.getEntity().remove();
            // Calls a method
            eventRef.set(event);
        // End of a block/expression
        });

        // Calls a method
        final long tick = TimeUnit.SERVER_TICK.getDuration().toMillis();
        // Loop: repeats a block
        for (int i = 0; i < ServerFlag.SERVER_TICKS_PER_SECOND * 5; ++i) {
            // Branch: checks a condition
            if (!projectile.isRemoved()) {
                // Calls a method
                projectile.tick(i * tick);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        final var event = eventRef.get();
        // Calls a method
        assertNotNull(event);
        // Calls a method
        assertSame(shooter, event.getTarget());
        // Calls a method
        assertTrue(shooter.getBoundingBox().intersectEntity(shooter.getPosition(), projectile));
    // End of a block/expression
    }

// End of a block/expression
}
