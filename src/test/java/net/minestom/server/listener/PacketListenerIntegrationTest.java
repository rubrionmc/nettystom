// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.GameMode;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerGameModeRequestEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientChangeGameModePacket;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.params.ParameterizedTest;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.Arguments;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.MethodSource;

// Import d'une classe nécessaire
import java.util.stream.Stream;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class PacketListenerIntegrationTest {

    // Annotation pour l'élément suivant
    @ParameterizedTest(name = "{0} vs {1}")
    // Annotation pour l'élément suivant
    @MethodSource("gameModePairs")
    // Début d'une méthode/d'un bloc
    public void testGameModeSwitchSame(GameMode expectedGamemode, GameMode playerGameMode, Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, 41, 0));
        // Appelle une méthode
        player.setGameMode(playerGameMode);
        // Appelle une méthode
        var listener = env.listen(PlayerGameModeRequestEvent.class);
        // Instruction de code
        listener.followup(event ->
                // Instruction de code
                assertEquals(expectedGamemode, event.getRequestedGameMode())
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        var packet = new ClientChangeGameModePacket(expectedGamemode);
        // Appelle une méthode
        player.addPacketToQueue(packet);
        // Appelle une méthode
        player.tick(0);
    // Fin d'un bloc/d'une expression
    }

    // Junit does not support @EnumSource with the same enum value for both
    // Début d'une méthode/d'un bloc
    private static Stream<Arguments> gameModePairs() {
        // Renvoie une valeur à l'appelant
        return Stream.of(GameMode.values())
                // Appelle une méthode
                .flatMap(a -> Stream.of(GameMode.values()).map(b -> Arguments.of(a, b)));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
