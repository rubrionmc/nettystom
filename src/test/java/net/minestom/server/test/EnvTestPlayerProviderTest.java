// Package declaration for this file
package net.minestom.server.test;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.player.GameProfile;
// Import of a required class
import net.minestom.server.network.player.PlayerConnection;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class EnvTestPlayerProviderTest {

    // Type declaration (class/interface/enum/record)
    public static class CustomPlayer extends Player {
        // Start of a method/block
        public CustomPlayer(PlayerConnection playerConnection, GameProfile gameProfile) {
            // Access to the current/parent object
            super(playerConnection, gameProfile);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testPlayerProviderUsedInEnvTest(Env env) {
        // Note: By default the test environment will use a player provider of its own to bypass the queued chunk system
        // overriding in a particular test will mean that chunk packets are not received consistently (they require the
        // chunk queue interaction). However, this is not a problem for many tests, so we do support it.

        // Calls a method
        env.process().connection().setPlayerProvider(CustomPlayer::new);
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, 42, 0));
        // Calls a method
        assertInstanceOf(CustomPlayer.class, player);
    // End of a block/expression
    }
// End of a block/expression
}
