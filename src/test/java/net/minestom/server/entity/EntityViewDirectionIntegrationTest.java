// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertTrue;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class EntityViewDirectionIntegrationTest {
    // Affecte une valeur
    private static final float EPSILON = 0.01f;

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void viewYawAndPitch(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 40, 0)).join();
        // Appelle une méthode
        entity.setView(0, 0);
        // Appelle une méthode
        assertEquals(0, entity.getPosition().yaw());
        // Appelle une méthode
        assertEquals(0, entity.getPosition().pitch());

        // Appelle une méthode
        entity.setView(90, 0);
        // Appelle une méthode
        assertEquals(90, entity.getPosition().yaw());
        // Appelle une méthode
        assertEquals(0, entity.getPosition().pitch());

        // Appelle une méthode
        entity.setView(0, 42);
        // Appelle une méthode
        assertEquals(0, entity.getPosition().yaw());
        // Appelle une méthode
        assertEquals(42, entity.getPosition().pitch());

        // Appelle une méthode
        entity.setView(37, 26);
        // Appelle une méthode
        assertEquals(37, entity.getPosition().yaw());
        // Appelle une méthode
        assertEquals(26, entity.getPosition().pitch());

        // check for NaN values
        // Appelle une méthode
        entity.setView(Float.NaN, 0);
        // Appelle une méthode
        assertTrue(Float.isNaN(entity.getPosition().yaw()));
        // Appelle une méthode
        assertEquals(0, entity.getPosition().pitch());

        // Appelle une méthode
        entity.setView(0, Float.NaN);
        // Appelle une méthode
        assertEquals(0, entity.getPosition().yaw());
        // Appelle une méthode
        assertTrue(Float.isNaN(entity.getPosition().pitch()));

        // Appelle une méthode
        entity.setView(Float.NaN, Float.NaN);
        // Appelle une méthode
        assertTrue(Float.isNaN(entity.getPosition().yaw()));
        // Appelle une méthode
        assertTrue(Float.isNaN(entity.getPosition().pitch()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void lookAtPos(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var entity = new Entity(EntityType.ZOMBIE);
        // Boucle : répète un bloc
        double eyeHeight = entity.getEyeHeight(); // adding this to some position Y coordinates, to look horizontally
        
        // Appelle une méthode
        entity.setInstance(instance, new Pos(0, 40, 0)).join();

        // make it look at its feet's position, it should look down
        // Appelle une méthode
        entity.lookAt(entity.getPosition());
        // looking vertically, not checking yaw
        // Appelle une méthode
        assertEquals(90f, entity.getPosition().pitch());

        // Appelle une méthode
        entity.lookAt(new Pos(16, 40 + eyeHeight, 16));
        // Appelle une méthode
        assertEquals(-45f, entity.getPosition().yaw());
        // Appelle une méthode
        assertEquals(0f, entity.getPosition().pitch(), EPSILON);

        // Appelle une méthode
        entity.lookAt(new Pos(-16, 40 + eyeHeight, 56));
        // Appelle une méthode
        assertEquals(15.94f, entity.getPosition().yaw(), EPSILON);
        // Appelle une méthode
        assertEquals(0f, entity.getPosition().pitch(), EPSILON);

        // Appelle une méthode
        entity.lookAt(new Pos(48, 36, 48));
        // Appelle une méthode
        assertEquals(-45f, entity.getPosition().yaw(), EPSILON);
        // Appelle une méthode
        assertEquals(6.81f, entity.getPosition().pitch(), EPSILON);

        // Appelle une méthode
        entity.lookAt(new Pos(48, 36, -17));
        // Appelle une méthode
        assertEquals(-109.50f, entity.getPosition().yaw(), EPSILON);
        // should have the same pitch as the previous position
        // Appelle une méthode
        assertEquals(6.81f, entity.getPosition().pitch(), EPSILON);

        // Appelle une méthode
        entity.lookAt(new Pos(0, 87, 0));
        // looking from below, not checking the yaw
        // Appelle une méthode
        assertEquals(-90f, entity.getPosition().pitch(), EPSILON);

        // Appelle une méthode
        entity.lookAt(new Pos(-25, 42, 4));
        // Appelle une méthode
        assertEquals(80.90f, entity.getPosition().yaw(), EPSILON);
        // Appelle une méthode
        assertEquals(-0.59f, entity.getPosition().pitch(), EPSILON);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void lookAtEntitySameType(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // same type, same eye height
        // Appelle une méthode
        var e1 = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        var e2 = new Entity(EntityType.ZOMBIE);
        // Appelle une méthode
        e1.setInstance(instance, new Pos(0, 40, 0)).join();
        // Appelle une méthode
        e2.setInstance(instance, new Pos(0, 40, 0)).join();

        // look at an entity with the same eye height and same position,
        // direction should not change
        // Appelle une méthode
        float prevYaw = e1.getPosition().yaw();
        // Appelle une méthode
        float prevPitch = e1.getPosition().pitch();
        // Appelle une méthode
        e1.lookAt(e2);
        // Appelle une méthode
        assertEquals(prevYaw, e1.getPosition().yaw());
        // Appelle une méthode
        assertEquals(prevPitch, e1.getPosition().pitch());

        // Appelle une méthode
        e2.teleport(new Pos(0, 50, 0)).join();
        // Appelle une méthode
        e1.lookAt(e2);
        // e2 is above e1, the pich should be negative
        // Appelle une méthode
        assertEquals(-90f, e1.getPosition().pitch(), EPSILON);

        // Appelle une méthode
        e2.teleport(new Pos(0, 10, 0)).join();
        // Appelle une méthode
        e1.lookAt(e2);
        // e2 is below e1, the pich should be positive
        // Appelle une méthode
        assertEquals(90f, e1.getPosition().pitch(), EPSILON);

        // Appelle une méthode
        e2.teleport(new Pos(16, 40, 16)).join();
        // Appelle une méthode
        e1.lookAt(e2);
        // Appelle une méthode
        assertEquals(-45f, e1.getPosition().yaw(), EPSILON);
        // e2 has the same y as e1, the pich should be 0
        // Appelle une méthode
        assertEquals(0f, e1.getPosition().pitch(), EPSILON);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void lookAtEntityDifferentType(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // same type, same eye height
        // Appelle une méthode
        var e1 = new Entity(EntityType.ZOMBIE);
        // a chicken has a lower eye height than a zombie
        // Appelle une méthode
        var e2 = new Entity(EntityType.CHICKEN);
        // Appelle une méthode
        e1.setInstance(instance, new Pos(0, 40, 0)).join();
        // Appelle une méthode
        e2.setInstance(instance, new Pos(0, 40, 0)).join();

        // Appelle une méthode
        e1.lookAt(e2);
        // e2 eyes are below e1, the pich should be positive
        // Appelle une méthode
        assertEquals(90f, e1.getPosition().pitch(), EPSILON);

        // Boucle : répète un bloc
        double eyeDifference = e1.getEyeHeight() - e2.getEyeHeight();
        // Appelle une méthode
        assertTrue(eyeDifference > 0);
        // Appelle une méthode
        var pos = new Pos(0, e1.getPosition().y() + eyeDifference, 0);
        // Appelle une méthode
        e2.teleport(pos).join();
        // e2 eyes are in the same position as e1, direction should not change
        // Appelle une méthode
        float prevYaw = e1.getPosition().yaw();
        // Appelle une méthode
        float prevPitch = e1.getPosition().pitch();
        // Appelle une méthode
        e1.lookAt(e2);
        // Appelle une méthode
        assertEquals(prevYaw, e1.getPosition().yaw());
        // Appelle une méthode
        assertEquals(prevPitch, e1.getPosition().pitch());

        // Appelle une méthode
        pos = new Pos(10, e1.getPosition().y() + eyeDifference, 10);
        // Appelle une méthode
        e2.teleport(pos).join();
        // Appelle une méthode
        e1.lookAt(e2);
        // e2 eyes are at the same height as e1's, the pitch should be 0
        // Appelle une méthode
        assertEquals(0f, e1.getPosition().pitch(), EPSILON);

        // Appelle une méthode
        e2.teleport(new Pos(-16, 40, -16)).join();
        // Appelle une méthode
        e1.lookAt(e2);
        // Appelle une méthode
        assertEquals(135f, e1.getPosition().yaw(), EPSILON);
        // Appelle une méthode
        assertEquals(3.91f, e1.getPosition().pitch(), EPSILON);

        // Appelle une méthode
        e2.teleport(new Pos(8, 50, -32)).join();
        // Appelle une méthode
        e1.lookAt(e2);
        // Appelle une méthode
        assertEquals(-165.96f, e1.getPosition().yaw(), EPSILON);
        // Appelle une méthode
        assertEquals(-15.54f, e1.getPosition().pitch(), EPSILON);

        // Appelle une méthode
        e2.teleport(new Pos(0, 30, -2)).join();
        // Appelle une méthode
        e1.lookAt(e2);
        // Appelle une méthode
        assertEquals(-180f, e1.getPosition().yaw(), EPSILON);
        // Appelle une méthode
        assertEquals(79.78f, e1.getPosition().pitch(), EPSILON);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
