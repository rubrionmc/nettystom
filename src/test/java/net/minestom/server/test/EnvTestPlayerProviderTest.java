// Déclaration du paquet de ce fichier
package net.minestom.server.test;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.player.GameProfile;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerConnection;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class EnvTestPlayerProviderTest {

    // Déclaration de type (classe/interface/enum/record)
    public static class CustomPlayer extends Player {
        // Début d'une méthode/d'un bloc
        public CustomPlayer(PlayerConnection playerConnection, GameProfile gameProfile) {
            // Accès à l'objet courant/parent
            super(playerConnection, gameProfile);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testPlayerProviderUsedInEnvTest(Env env) {
        // Note: By default the test environment will use a player provider of its own to bypass the queued chunk system
        // overriding in a particular test will mean that chunk packets are not received consistently (they require the
        // chunk queue interaction). However, this is not a problem for many tests, so we do support it.

        // Appelle une méthode
        env.process().connection().setPlayerProvider(CustomPlayer::new);
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, 42, 0));
        // Appelle une méthode
        assertInstanceOf(CustomPlayer.class, player);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
