// Package declaration for this file
package net.minestom.server.network;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.network.player.GameProfile;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.BeforeEach;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.UUID;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertNull;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class ConnectionManagerIntegrationTest {


    // Code statement
    private GameProfile[] profiles;

    // Annotation for the following element
    @BeforeEach
    // Start of a method/block
    public void setup(Env env) {
        // Assigns a value
        profiles = new GameProfile[]{
                // Creates a new object
                new GameProfile(UUID.randomUUID(), "Minestom"),
                // Creates a new object
                new GameProfile(UUID.randomUUID(), "Notch")};
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testPartialFind(Env env) {
        // Calls a method
        Instance instance = env.createEmptyInstance();
        // Calls a method
        Player minestomPlayer = env.createConnection(profiles[0]).connect(instance, Pos.ZERO);
        // Calls a method
        ConnectionManager connectionManager = env.process().connection();

        // Calls a method
        assertEquals(minestomPlayer, connectionManager.findOnlinePlayer("Mine"));
        // Calls a method
        assertNull(connectionManager.findOnlinePlayer("No"));

        // Calls a method
        Player notchPlayer = env.createConnection(profiles[1]).connect(instance, Pos.ZERO);

        // Calls a method
        assertEquals(minestomPlayer, connectionManager.findOnlinePlayer("Mine"));
        // Calls a method
        assertEquals(notchPlayer, connectionManager.findOnlinePlayer("No"));
        // Calls a method
        assertNull(connectionManager.findOnlinePlayer("leo"));
    // End of a block/expression
    }

// End of a block/expression
}
