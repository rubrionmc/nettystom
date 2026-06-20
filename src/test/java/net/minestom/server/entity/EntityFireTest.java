// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.event.entity.EntityFireExtinguishEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.entity.EntitySetFireEvent;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class EntityFireTest
// Début d'un bloc
{
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void duration(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.loadChunk(0, 0).join();

        // Affecte une valeur
        final int fireTicks = 10;
        // Appelle une méthode
        LivingEntity entity = new LivingEntity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Vec(0, 0, 0));

        // Appelle une méthode
        entity.setFireTicks(fireTicks);
        // Appelle une méthode
        assertTrue(entity.getEntityMeta().isOnFire());

        // Boucle : répète un bloc
        for (int i = 0; i < fireTicks; i++) {
            // Appelle une méthode
            assertTrue(entity.getEntityMeta().isOnFire());
            // Appelle une méthode
            assertEquals(fireTicks - i, entity.getFireTicks());
            // Appelle une méthode
            entity.tick(0);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        assertFalse(entity.getEntityMeta().isOnFire());
        // Appelle une méthode
        assertEquals(0, entity.getFireTicks());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void nonNegativeFireDuration(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.loadChunk(0, 0).join();

        // Appelle une méthode
        LivingEntity entity = new LivingEntity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Vec(0, 0, 0));

        // Natural fire decay
        // Appelle une méthode
        entity.setFireTicks(5);
        // Boucle : répète un bloc
        for (int i = 0; i < 20; i++) {
            // Appelle une méthode
            assertTrue(entity.getFireTicks() >= 0);
        // Fin d'un bloc/d'une expression
        }

        // Explicit negative
        // Appelle une méthode
        entity.setFireTicks(-1);
        // Appelle une méthode
        assertEquals(0, entity.getFireTicks());

        // Explicit negative in event
        // Appelle une méthode
        env.listen(EntitySetFireEvent.class).followup(e -> e.setFireTicks(-1));

        // Appelle une méthode
        entity.setFireTicks(1);
        // Appelle une méthode
        assertEquals(0, entity.getFireTicks());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void setFireMetadata(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.loadChunk(0, 0).join();

        // Appelle une méthode
        LivingEntity entity = new LivingEntity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Vec(0, 0, 0));

        // Do not extinguish an entity when they're set on fire explicitly
        // Appelle une méthode
        entity.getEntityMeta().setOnFire(true);
        // Boucle : répète un bloc
        for (int i = 0; i < 40; i++) {
            // Appelle une méthode
            entity.tick(0);
            // Appelle une méthode
            assertTrue(entity.getEntityMeta().isOnFire());
        // Fin d'un bloc/d'une expression
        }

        // Unless setFireTicks has been called to activate the internal remainingFireTicks timer
        // Appelle une méthode
        entity.setFireTicks(1);
        // Appelle une méthode
        entity.tick(0);
        // Appelle une méthode
        assertFalse(entity.isOnFire());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void extinguishEvent(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.loadChunk(0, 0).join();

        // Appelle une méthode
        LivingEntity entity = new LivingEntity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Vec(0, 0, 0));

        // Appelle une méthode
        AtomicInteger callCount = new AtomicInteger();
        // Début d'une méthode/d'un bloc
        env.listen(EntityFireExtinguishEvent.class).followup(e -> {
            // Appelle une méthode
            callCount.getAndIncrement();
            // Embranchement : vérifie une condition
            if (callCount.get() == 2) assertTrue(e.isNatural());
            // Branche alternative de la condition
            else assertFalse(e.isNatural());
        // Fin d'un bloc/d'une expression
        });

        // Don't call when the entity is already on fire
        // Appelle une méthode
        entity.setFireTicks(0);
        // Appelle une méthode
        assertEquals(0, callCount.get());

        // Call now, the entity is set on fire
        // Appelle une méthode
        entity.setFireTicks(1);
        // Appelle une méthode
        entity.setFireTicks(-1);
        // Appelle une méthode
        assertEquals(1, callCount.get());

        // Call naturally
        // Appelle une méthode
        entity.setFireTicks(3);
        // Boucle : répète un bloc
        for (int i = 0; i < 3; i++) {
            // Appelle une méthode
            entity.tick(0);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertEquals(2, callCount.get());

        // Don't call if cancelled EntitySetFireEvent
        // Appelle une méthode
        env.listen(EntitySetFireEvent.class).followup(e -> e.setCancelled(true));
        // Appelle une méthode
        entity.setFireTicks(5);
        // Appelle une méthode
        assertEquals(2, callCount.get());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
