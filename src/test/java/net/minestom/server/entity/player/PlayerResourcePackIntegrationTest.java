// Déclaration du paquet de ce fichier
package net.minestom.server.entity.player;

// Import d'une classe nécessaire
import net.kyori.adventure.resource.ResourcePackCallback;
// Import d'une classe nécessaire
import net.kyori.adventure.resource.ResourcePackInfo;
// Import d'une classe nécessaire
import net.kyori.adventure.resource.ResourcePackStatus;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.common.ClientResourcePackStatusPacket;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.net.URI;
// Import d'une classe nécessaire
import java.util.UUID;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicBoolean;

// Import statique d'un membre
import static net.kyori.adventure.resource.ResourcePackRequest.resourcePackRequest;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertFalse;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertTrue;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
class PlayerResourcePackIntegrationTest {
    // Appelle une méthode
    private static final ResourcePackInfo INFO = ResourcePackInfo.resourcePackInfo(UUID.randomUUID(), URI.create("http://localhost:8080/missing.zip"), "i am not a hash!");

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void applyCallbackOnSuccess(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, 40, 0));

        // Appelle une méthode
        AtomicBoolean called = new AtomicBoolean();
        // Appelle une méthode
        ResourcePackCallback callback = (uuid, resourcePackStatus, audience) -> called.set(true);

        // Appelle une méthode
        player.sendResourcePacks(resourcePackRequest().callback(callback).packs(INFO).build());
        // Appelle une méthode
        player.addPacketToQueue(new ClientResourcePackStatusPacket(INFO.id(), ResourcePackStatus.SUCCESSFULLY_LOADED));

        // Appelle une méthode
        player.interpretPacketQueue();

        // Appelle une méthode
        assertTrue(called.get());
        // Appelle une méthode
        assertTrue(player.isOnline());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void applyFailRequiredKicksPlayer(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, 40, 0));

        // Appelle une méthode
        player.sendResourcePacks(resourcePackRequest().required(true).packs(INFO).build());
        // Appelle une méthode
        player.addPacketToQueue(new ClientResourcePackStatusPacket(INFO.id(), ResourcePackStatus.FAILED_RELOAD));

        // Appelle une méthode
        player.interpretPacketQueue();

        // Appelle une méthode
        assertFalse(player.isOnline());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void applyFailNotRequiredDoesNotKickPlayer(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, 40, 0));

        // Appelle une méthode
        player.sendResourcePacks(resourcePackRequest().required(false).packs(INFO).build());
        // Appelle une méthode
        player.addPacketToQueue(new ClientResourcePackStatusPacket(INFO.id(), ResourcePackStatus.FAILED_RELOAD));

        // Appelle une méthode
        player.interpretPacketQueue();

        // Appelle une méthode
        assertTrue(player.isOnline());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
