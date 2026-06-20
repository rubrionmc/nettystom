// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.event.EventFilter;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerStabEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientPlayerActionPacket;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class PlayerActionListenerTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testStabInvalidWeapon(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, 0, 0));

        // Appelle une méthode
        var tracker = env.trackEvent(PlayerStabEvent.class, EventFilter.PLAYER, player);

        // Instruction de code
        PlayerActionListener.playerActionListener(new ClientPlayerActionPacket(
                // Instruction de code
                ClientPlayerActionPacket.Status.STAB,
                // Instruction de code
                Vec.ZERO, BlockFace.NORTH, 0
        // Instruction de code
        ), player);
        // Appelle une méthode
        tracker.assertEmpty();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testStabWithWeapon(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, 0, 0));
        // Appelle une méthode
        player.setItemInMainHand(ItemStack.of(Material.NETHERITE_SPEAR));

        // Appelle une méthode
        var tracker = env.trackEvent(PlayerStabEvent.class, EventFilter.PLAYER, player);
        // Instruction de code
        PlayerActionListener.playerActionListener(new ClientPlayerActionPacket(
                // Instruction de code
                ClientPlayerActionPacket.Status.STAB,
                // Instruction de code
                Vec.ZERO, BlockFace.NORTH, 0
        // Instruction de code
        ), player);

        // Appelle une méthode
        tracker.assertSingle();
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
