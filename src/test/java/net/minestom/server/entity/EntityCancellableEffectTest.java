// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.event.entity.EntityPotionAddEvent;
// Import of a required class
import net.minestom.server.potion.Potion;
// Import of a required class
import net.minestom.server.potion.PotionEffect;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class EntityCancellableEffectTest
// Start of a block
{

    // Start of a method/block
    static {
        // Calls a method
        MinecraftServer.init();
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cancelEffect(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        instance.loadChunk(0, 0).join();

        // Calls a method
        LivingEntity entity = new LivingEntity(EntityType.ZOMBIE);
        // Calls a method
        entity.setInstance(instance, new Vec(0, 0, 0));

        // Calls a method
        Potion potion = new Potion(PotionEffect.ABSORPTION, 0, Potion.INFINITE_DURATION);
        // Calls a method
        MinecraftServer.getGlobalEventHandler().addListener(EntityPotionAddEvent.class, event -> event.setCancelled(true));
        // Calls a method
        entity.addEffect(potion);

        // Calls a method
        assertFalse(entity.hasEffect(PotionEffect.ABSORPTION));
    // End of a block/expression
    }

// End of a block/expression
}
