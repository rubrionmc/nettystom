// Déclaration du paquet de ce fichier
package net.minestom.server.ping;

// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.MainHand;
// Import d'une classe nécessaire
import net.minestom.server.message.ChatMessageType;
// Import d'une classe nécessaire
import net.minestom.server.network.player.ClientSettings;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.Locale;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertFalse;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class StatusIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testPlayerInfoSamples(Env env) {
        // Appelle une méthode
        var instance = env.createEmptyInstance();
        // Appelle une méthode
        env.createPlayer(instance, Pos.ZERO);
        // Appelle une méthode
        env.createPlayer(instance, Pos.ZERO);
        // Appelle une méthode
        var player3 = env.createPlayer(instance, Pos.ZERO);
        // Instruction de code
        player3.refreshSettings(new ClientSettings(
                // Instruction de code
                Locale.US, (byte) ServerFlag.CHUNK_VIEW_DISTANCE,
                // Instruction de code
                ChatMessageType.FULL, true,
                // Instruction de code
                (byte) 0x7F, MainHand.RIGHT,
                // Instruction de code
                true, false,
                // Instruction de code
                ClientSettings.ParticleSetting.ALL
        // Instruction de code
        ));

        // Appelle une méthode
        var unlimitedInfo = Status.PlayerInfo.online(20);
        // Appelle une méthode
        assertEquals(4, unlimitedInfo.maxPlayers());
        // Appelle une méthode
        assertEquals(3, unlimitedInfo.onlinePlayers());
        // Appelle une méthode
        assertEquals(2, unlimitedInfo.sample().size());

        // Affecte une valeur
        var containsHiddenPlayer = unlimitedInfo.sample().stream()
                // Appelle une méthode
                .anyMatch(entry -> entry.getUuid().equals(player3.getUuid()));
        // Appelle une méthode
        assertFalse(containsHiddenPlayer);

        // Appelle une méthode
        var limitedInfo = Status.PlayerInfo.online(1);
        // Appelle une méthode
        assertEquals(1, limitedInfo.sample().size());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
