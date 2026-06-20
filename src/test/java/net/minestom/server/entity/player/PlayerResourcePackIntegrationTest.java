// Package declaration for this file
package net.minestom.server.entity.player;

// Import of a required class
import net.kyori.adventure.resource.ResourcePackCallback;
// Import of a required class
import net.kyori.adventure.resource.ResourcePackInfo;
// Import of a required class
import net.kyori.adventure.resource.ResourcePackStatus;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.network.packet.client.common.ClientResourcePackStatusPacket;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.net.URI;
// Import of a required class
import java.util.UUID;
// Import of a required class
import java.util.concurrent.atomic.AtomicBoolean;

// Static import of a member
import static net.kyori.adventure.resource.ResourcePackRequest.resourcePackRequest;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertFalse;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertTrue;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
class PlayerResourcePackIntegrationTest {
    // Calls a method
    private static final ResourcePackInfo INFO = ResourcePackInfo.resourcePackInfo(UUID.randomUUID(), URI.create("http://localhost:8080/missing.zip"), "i am not a hash!");

    // Annotation for the following element
    @Test
    // Start of a method/block
    void applyCallbackOnSuccess(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, 40, 0));

        // Calls a method
        AtomicBoolean called = new AtomicBoolean();
        // Calls a method
        ResourcePackCallback callback = (uuid, resourcePackStatus, audience) -> called.set(true);

        // Calls a method
        player.sendResourcePacks(resourcePackRequest().callback(callback).packs(INFO).build());
        // Calls a method
        player.addPacketToQueue(new ClientResourcePackStatusPacket(INFO.id(), ResourcePackStatus.SUCCESSFULLY_LOADED));

        // Calls a method
        player.interpretPacketQueue();

        // Calls a method
        assertTrue(called.get());
        // Calls a method
        assertTrue(player.isOnline());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void applyFailRequiredKicksPlayer(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, 40, 0));

        // Calls a method
        player.sendResourcePacks(resourcePackRequest().required(true).packs(INFO).build());
        // Calls a method
        player.addPacketToQueue(new ClientResourcePackStatusPacket(INFO.id(), ResourcePackStatus.FAILED_RELOAD));

        // Calls a method
        player.interpretPacketQueue();

        // Calls a method
        assertFalse(player.isOnline());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void applyFailNotRequiredDoesNotKickPlayer(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, 40, 0));

        // Calls a method
        player.sendResourcePacks(resourcePackRequest().required(false).packs(INFO).build());
        // Calls a method
        player.addPacketToQueue(new ClientResourcePackStatusPacket(INFO.id(), ResourcePackStatus.FAILED_RELOAD));

        // Calls a method
        player.interpretPacketQueue();

        // Calls a method
        assertTrue(player.isOnline());
    // End of a block/expression
    }
// End of a block/expression
}
