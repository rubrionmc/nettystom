// Déclaration du paquet de ce fichier
package net.minestom.server.collision;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityProjectile;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.entity.LivingEntity;
// Import d'une classe nécessaire
import net.minestom.server.event.EventNode;
// Import d'une classe nécessaire
import net.minestom.server.event.entity.projectile.ProjectileCollideWithBlockEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.entity.projectile.ProjectileCollideWithEntityEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.entity.projectile.ProjectileUncollideEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.WorldBorder;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.utils.time.TimeUnit;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicReference;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class EntityProjectileCollisionIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blockShootAndBlockRemoval(Env env) {
        // Appelle une méthode
        final Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setWorldBorder(WorldBorder.DEFAULT_BORDER.withDiameter(1000));

        // Appelle une méthode
        final Entity shooter = new Entity(EntityType.SKELETON);
        // Appelle une méthode
        shooter.setInstance(instance, new Pos(0, 40, 0)).join();

        // Appelle une méthode
        final EntityProjectile projectile = new EntityProjectile(shooter, EntityType.ARROW);
        // Appelle une méthode
        projectile.setInstance(instance, shooter.getPosition().withY(y -> y + shooter.getEyeHeight())).join();

        // Appelle une méthode
        final Point blockPosition = new Vec(5, 40, 0);
        // Affecte une valeur
        final Block block = Block.GRASS_BLOCK;
        // Appelle une méthode
        instance.setBlock(blockPosition, block);
        // Appelle une méthode
        projectile.shoot(blockPosition, 1, 0);

        // Appelle une méthode
        final var eventRef = new AtomicReference<ProjectileCollideWithBlockEvent>();
        // Appelle une méthode
        MinecraftServer.getGlobalEventHandler().addListener(ProjectileCollideWithBlockEvent.class, eventRef::set);

        // Appelle une méthode
        final long tick = TimeUnit.SERVER_TICK.getDuration().toMillis();
        // Boucle : répète un bloc
        for (int i = 0; i < ServerFlag.SERVER_TICKS_PER_SECOND; ++i) {
            // Appelle une méthode
            projectile.tick(i * tick);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        var event = eventRef.get();
        // Appelle une méthode
        assertNotNull(event);
        // Appelle une méthode
        assertEquals(blockPosition, new Vec(event.getCollisionPosition().blockX(), event.getCollisionPosition().blockY(), event.getCollisionPosition().blockZ()));
        // Appelle une méthode
        assertEquals(block, event.getBlock());

        // Appelle une méthode
        final var eventRef2 = new AtomicReference<ProjectileUncollideEvent>();
        // Appelle une méthode
        MinecraftServer.getGlobalEventHandler().addListener(ProjectileUncollideEvent.class, eventRef2::set);
        // Appelle une méthode
        eventRef.set(null);
        // Appelle une méthode
        instance.setBlock(blockPosition, Block.AIR);

        // Boucle : répète un bloc
        for (int i = 0; i < ServerFlag.SERVER_TICKS_PER_SECOND; ++i) {
            // Appelle une méthode
            projectile.tick((ServerFlag.SERVER_TICKS_PER_SECOND + i) * tick);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        event = eventRef.get();
        // Appelle une méthode
        final var event2 = eventRef2.get();
        // Appelle une méthode
        assertNotNull(event);
        // Appelle une méthode
        assertNotNull(event2);
        // Appelle une méthode
        assertEquals(blockPosition.withY(y -> y - 1), new Vec(event.getCollisionPosition().blockX(), event.getCollisionPosition().blockY(), event.getCollisionPosition().blockZ()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entityShoot(Env env) {
        // Appelle une méthode
        final Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setWorldBorder(WorldBorder.DEFAULT_BORDER.withDiameter(1000));

        // Appelle une méthode
        final Entity shooter = new Entity(EntityType.SKELETON);
        // Appelle une méthode
        shooter.setInstance(instance, new Pos(0, 40, 0)).join();

        // Boucle : répète un bloc
        for (double dx = 1; dx <= 3; dx += .2) {
            // Appelle une méthode
            singleEntityShoot(instance, shooter, new Vec(dx, 40, 0));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private void singleEntityShoot(
            // Instruction de code
            Instance instance,
            // Instruction de code
            Entity shooter,
            // Instruction de code
            final Point targetPosition
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        final EntityProjectile projectile = new EntityProjectile(shooter, EntityType.ARROW);
        // Appelle une méthode
        projectile.setInstance(instance, shooter.getPosition().withY(y -> y + shooter.getEyeHeight())).join();

        // Appelle une méthode
        final LivingEntity target = new LivingEntity(EntityType.RABBIT);
        // Appelle une méthode
        target.setInstance(instance, targetPosition.asPos()).join();
        // Appelle une méthode
        projectile.shoot(targetPosition, 1, 0);

        // Appelle une méthode
        final var eventRef = new AtomicReference<ProjectileCollideWithEntityEvent>();
        // Appelle une méthode
        final var eventNode = EventNode.all("projectile-test");
        // Début d'une méthode/d'un bloc
        eventNode.addListener(ProjectileCollideWithEntityEvent.class, event -> {
            // Appelle une méthode
            event.getEntity().remove();
            // Appelle une méthode
            eventRef.set(event);
            // Appelle une méthode
            MinecraftServer.getGlobalEventHandler().removeChild(eventNode);
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        MinecraftServer.getGlobalEventHandler().addChild(eventNode);

        // Appelle une méthode
        final long tick = TimeUnit.SERVER_TICK.getDuration().toMillis();
        // Boucle : répète un bloc
        for (int i = 0; i < ServerFlag.SERVER_TICKS_PER_SECOND; ++i) {
            // Embranchement : vérifie une condition
            if (!projectile.isRemoved()) {
                // Appelle une méthode
                projectile.tick(i * tick);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final var event = eventRef.get();
        // Appelle une méthode
        assertNotNull(event, "Could not hit entity at " + targetPosition);
        // Appelle une méthode
        assertSame(target, event.getTarget());
        // Appelle une méthode
        assertTrue(projectile.getBoundingBox().intersectEntity(event.getCollisionPosition(), target));
        // Appelle une méthode
        target.remove();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void entitySelfShoot(Env env) {
        // Appelle une méthode
        final Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setWorldBorder(WorldBorder.DEFAULT_BORDER.withDiameter(1000));

        // Appelle une méthode
        final LivingEntity shooter = new LivingEntity(EntityType.SKELETON);
        // Appelle une méthode
        shooter.setInstance(instance, new Pos(0, 40, 0)).join();

        // Appelle une méthode
        final EntityProjectile projectile = new EntityProjectile(shooter, EntityType.ARROW);
        // Appelle une méthode
        projectile.setInstance(instance, shooter.getPosition().withY(y -> y + shooter.getEyeHeight())).join();

        // Appelle une méthode
        projectile.shoot(new Vec(0, 60, 0), 1, 0);

        // Appelle une méthode
        final var eventRef = new AtomicReference<ProjectileCollideWithEntityEvent>();
        // Début d'une méthode/d'un bloc
        MinecraftServer.getGlobalEventHandler().addListener(ProjectileCollideWithEntityEvent.class, event -> {
            // Appelle une méthode
            event.getEntity().remove();
            // Appelle une méthode
            eventRef.set(event);
        // Fin d'un bloc/d'une expression
        });

        // Appelle une méthode
        final long tick = TimeUnit.SERVER_TICK.getDuration().toMillis();
        // Boucle : répète un bloc
        for (int i = 0; i < ServerFlag.SERVER_TICKS_PER_SECOND * 5; ++i) {
            // Embranchement : vérifie une condition
            if (!projectile.isRemoved()) {
                // Appelle une méthode
                projectile.tick(i * tick);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final var event = eventRef.get();
        // Appelle une méthode
        assertNotNull(event);
        // Appelle une méthode
        assertSame(shooter, event.getTarget());
        // Appelle une méthode
        assertTrue(shooter.getBoundingBox().intersectEntity(shooter.getPosition(), projectile));
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
