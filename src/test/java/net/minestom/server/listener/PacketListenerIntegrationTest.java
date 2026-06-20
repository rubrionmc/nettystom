// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.GameMode;
// Import of a required class
import net.minestom.server.event.player.PlayerGameModeRequestEvent;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientChangeGameModePacket;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.params.ParameterizedTest;
// Import of a required class
import org.junit.jupiter.params.provider.Arguments;
// Import of a required class
import org.junit.jupiter.params.provider.MethodSource;

// Import of a required class
import java.util.stream.Stream;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class PacketListenerIntegrationTest {

    // Annotation for the following element
    @ParameterizedTest(name = "{0} vs {1}")
    // Annotation for the following element
    @MethodSource("gameModePairs")
    // Start of a method/block
    public void testGameModeSwitchSame(GameMode expectedGamemode, GameMode playerGameMode, Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, 41, 0));
        // Calls a method
        player.setGameMode(playerGameMode);
        // Calls a method
        var listener = env.listen(PlayerGameModeRequestEvent.class);
        // Code statement
        listener.followup(event ->
                // Code statement
                assertEquals(expectedGamemode, event.getRequestedGameMode())
        // End of a block/expression
        );
        // Calls a method
        var packet = new ClientChangeGameModePacket(expectedGamemode);
        // Calls a method
        player.addPacketToQueue(packet);
        // Calls a method
        player.tick(0);
    // End of a block/expression
    }

    // Junit does not support @EnumSource with the same enum value for both
    // Start of a method/block
    private static Stream<Arguments> gameModePairs() {
        // Returns a value to the caller
        return Stream.of(GameMode.values())
                // Calls a method
                .flatMap(a -> Stream.of(GameMode.values()).map(b -> Arguments.of(a, b)));
    // End of a block/expression
    }
// End of a block/expression
}
