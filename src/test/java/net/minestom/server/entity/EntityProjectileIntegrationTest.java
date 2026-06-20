// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class EntityProjectileIntegrationTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void gravityVelocity(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var shooter = new EntityCreature(EntityType.SKELETON);
        // Appelle une méthode
        shooter.setInstance(instance, new Pos(0, 42, 0)).join();
        // Appelle une méthode
        var projectile = new EntityProjectile(shooter, EntityType.ARROW);
        // Affecte une valeur
        var from = new Pos(0, 42, 0).add(0,
                // Appelle une méthode
                shooter.getEyeHeight(), shooter.getPosition().direction().z());
        // Appelle une méthode
        var target = from.add(0, 0, 10);
        // Appelle une méthode
        projectile.setInstance(instance, from).join();
        // Appelle une méthode
        projectile.shoot(target, 1, 0);

        // Affecte une valeur
        var before = projectile.getPosition(); // at start
        // Affecte une valeur
        var after = projectile.getPosition(); // now - 1 tick, closest to target
        // Affecte une valeur
        var smallestDistance = 1e6;
        // Boucle : répète un bloc
        while (true) {
            // Appelle une méthode
            final var distance = projectile.getPosition().distanceSquared(target);
            // Embranchement : vérifie une condition
            if (distance <= smallestDistance) smallestDistance = distance;
            // Branche alternative de la condition
            else break;

            // Appelle une méthode
            after = projectile.getPosition();
            // Appelle une méthode
            env.tick();
        // Fin d'un bloc/d'une expression
        }

        // Ensure the position is correct.
        // x doesn't change
        // Big delta because ticks aren't very accurate
        // Appelle une méthode
        assertEquals(before.x(), after.x());
        // Appelle une méthode
        assertEquals(target.y(), after.y(), 0.6);
        // Appelle une méthode
        assertEquals(target.z(), after.z(), 0.6);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void noGravityVelocity(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var shooter = new EntityCreature(EntityType.SKELETON);
        // Appelle une méthode
        shooter.setInstance(instance, new Pos(0, 42, 0)).join();
        // Appelle une méthode
        var projectile = new EntityProjectile(shooter, EntityType.ARROW);
        // Affecte une valeur
        var from = new Pos(0, 42, 0).add(0,
                // Appelle une méthode
                shooter.getEyeHeight(), shooter.getPosition().direction().z());
        // Appelle une méthode
        var target = from.add(0, 0, 10);
        // Appelle une méthode
        projectile.setNoGravity(true);
        // Appelle une méthode
        projectile.setInstance(instance, from).join();
        // Appelle une méthode
        projectile.shoot(target, 1, 0);

        // Affecte une valeur
        var before = projectile.getPosition(); // at start
        // Affecte une valeur
        var after = projectile.getPosition(); // now - 1 tick, closest to target
        // Affecte une valeur
        var smallestDistance = 1e6;
        // Boucle : répète un bloc
        while (true) {
            // Appelle une méthode
            final var distance = projectile.getPosition().distanceSquared(target);
            // Embranchement : vérifie une condition
            if (distance <= smallestDistance) smallestDistance = distance;
            // Branche alternative de la condition
            else break;

            // Appelle une méthode
            after = projectile.getPosition();
            // Appelle une méthode
            env.tick();
        // Fin d'un bloc/d'une expression
        }

        // x and y don't change (no gravity) and z changes by Σz velocity.
        // Appelle une méthode
        assertEquals(before.x(), after.x());
        // Appelle une méthode
        assertEquals(before.y(), after.y());
        // Appelle une méthode
        assertEquals(target.z(), after.z(), 0.05);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
