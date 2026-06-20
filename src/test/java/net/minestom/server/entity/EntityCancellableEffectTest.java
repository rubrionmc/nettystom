// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.event.entity.EntityPotionAddEvent;
// Import d'une classe nécessaire
import net.minestom.server.potion.Potion;
// Import d'une classe nécessaire
import net.minestom.server.potion.PotionEffect;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class EntityCancellableEffectTest
// Début d'un bloc
{

    // Début d'une méthode/d'un bloc
    static {
        // Appelle une méthode
        MinecraftServer.init();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cancelEffect(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.loadChunk(0, 0).join();

        // Appelle une méthode
        LivingEntity entity = new LivingEntity(EntityType.ZOMBIE);
        // Appelle une méthode
        entity.setInstance(instance, new Vec(0, 0, 0));

        // Appelle une méthode
        Potion potion = new Potion(PotionEffect.ABSORPTION, 0, Potion.INFINITE_DURATION);
        // Appelle une méthode
        MinecraftServer.getGlobalEventHandler().addListener(EntityPotionAddEvent.class, event -> event.setCancelled(true));
        // Appelle une méthode
        entity.addEffect(potion);

        // Appelle une méthode
        assertFalse(entity.hasEffect(PotionEffect.ABSORPTION));
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
