// Package declaration for this file
package net.minestom.server.ping;

// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.MainHand;
// Import of a required class
import net.minestom.server.message.ChatMessageType;
// Import of a required class
import net.minestom.server.network.player.ClientSettings;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.Locale;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertFalse;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class StatusIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testPlayerInfoSamples(Env env) {
        // Calls a method
        var instance = env.createEmptyInstance();
        // Calls a method
        env.createPlayer(instance, Pos.ZERO);
        // Calls a method
        env.createPlayer(instance, Pos.ZERO);
        // Calls a method
        var player3 = env.createPlayer(instance, Pos.ZERO);
        // Code statement
        player3.refreshSettings(new ClientSettings(
                // Code statement
                Locale.US, (byte) ServerFlag.CHUNK_VIEW_DISTANCE,
                // Code statement
                ChatMessageType.FULL, true,
                // Code statement
                (byte) 0x7F, MainHand.RIGHT,
                // Code statement
                true, false,
                // Code statement
                ClientSettings.ParticleSetting.ALL
        // Code statement
        ));

        // Calls a method
        var unlimitedInfo = Status.PlayerInfo.online(20);
        // Calls a method
        assertEquals(4, unlimitedInfo.maxPlayers());
        // Calls a method
        assertEquals(3, unlimitedInfo.onlinePlayers());
        // Calls a method
        assertEquals(2, unlimitedInfo.sample().size());

        // Assigns a value
        var containsHiddenPlayer = unlimitedInfo.sample().stream()
                // Calls a method
                .anyMatch(entry -> entry.getUuid().equals(player3.getUuid()));
        // Calls a method
        assertFalse(containsHiddenPlayer);

        // Calls a method
        var limitedInfo = Status.PlayerInfo.online(1);
        // Calls a method
        assertEquals(1, limitedInfo.sample().size());
    // End of a block/expression
    }
// End of a block/expression
}
