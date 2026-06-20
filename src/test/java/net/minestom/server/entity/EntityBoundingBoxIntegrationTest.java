// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.collision.BoundingBox;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.event.item.PickupItemEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class EntityBoundingBoxIntegrationTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void pose(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Bounding box should be from the registry
        // Appelle une méthode
        assertEquals(player.getEntityType().registry().boundingBox(), player.getBoundingBox());
        // Appelle une méthode
        player.setPose(EntityPose.STANDING);
        // Appelle une méthode
        assertEquals(player.getEntityType().registry().boundingBox(), player.getBoundingBox());

        // Appelle une méthode
        player.setPose(EntityPose.SLEEPING);
        // Appelle une méthode
        assertEquals(new BoundingBox(0.2, 0.2, 0.2), player.getBoundingBox());

        // Appelle une méthode
        player.setPose(EntityPose.SNEAKING);
        // Appelle une méthode
        assertEquals(new BoundingBox(0.6, 1.5, 0.6), player.getBoundingBox());

        // Appelle une méthode
        player.setPose(EntityPose.FALL_FLYING);
        // Appelle une méthode
        assertEquals(new BoundingBox(0.6, 0.6, 0.6), player.getBoundingBox());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void eyeHeight(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Appelle une méthode
        assertEquals(1.62, player.getEyeHeight());

        // Appelle une méthode
        player.setPose(EntityPose.SLEEPING);
        // Appelle une méthode
        assertEquals(0.2, player.getEyeHeight());

        // Appelle une méthode
        player.setPose(EntityPose.SNEAKING);
        // Appelle une méthode
        assertEquals(1.27, player.getEyeHeight());

        // Appelle une méthode
        player.setPose(EntityPose.FALL_FLYING);
        // Appelle une méthode
        assertEquals(0.4, player.getEyeHeight());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void pickupItem(Env env) {
        // Appelle une méthode
        final var instance = env.createFlatInstance();
        // Appelle une méthode
        final var listener = env.listen(PickupItemEvent.class);
        // Appelle une méthode
        final var spawnPos = new Pos(0, 42, 0);
        // Appelle une méthode
        final var entity = new LivingEntity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setCanPickupItem(true);
        // Appelle une méthode
        entity.setInstance(instance, spawnPos).join();

        // 0 is fine here, it's just a delta
        // Affecte une valeur
        var time = 0L;

        // Appelle une méthode
        dropItem(instance, spawnPos);
        // Appelle une méthode
        listener.followup();
        // Appelle une méthode
        entity.update(time += 1_000L);

        // Appelle une méthode
        dropItem(instance, spawnPos.sub(.5));
        // Appelle une méthode
        listener.followup();
        // Appelle une méthode
        entity.update(time += 1_000L);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void dropItem(final Instance instance, final Pos position) {
        // Appelle une méthode
        final var entity = new ItemEntity(ItemStack.of(Material.STONE));
        // Affecte une valeur
        entity.hasPhysics = false;
        // Appelle une méthode
        entity.setNoGravity(true);
        // Appelle une méthode
        entity.setInstance(instance, position).join();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
