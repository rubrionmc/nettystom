// Déclaration du paquet de ce fichier
package net.minestom.server.entity.player;

// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.ChunkRange;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientStatusPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.ChunkDataPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.UnloadChunkPacket;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.HashSet;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Set;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertFalse;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertTrue;


// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class PlayerRespawnChunkIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testChunkUnloadsOnRespawn(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        Player player = connection.connect(instance, new Pos(0, 40, 0));
        // Appelle une méthode
        player.teleport(new Pos(32, 40, 32)).join();

        // Appelle une méthode
        var unloadChunkTracker = connection.trackIncoming(UnloadChunkPacket.class);
        // Appelle une méthode
        player.setHealth(0);
        // Appelle une méthode
        player.respawn();
        // Since client unloads the chunks, we shouldn't receive any unload packets
        // Appelle une méthode
        unloadChunkTracker.assertCount(0);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testChunkReloadCount(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        Player player = connection.connect(instance, new Pos(0, 40, 0));

        // Appelle une méthode
        var loadChunkTracker = connection.trackIncoming(ChunkDataPacket.class);
        // Appelle une méthode
        player.setHealth(0);
        // Appelle une méthode
        player.respawn();
        // Player should have all their chunks reloaded
        // Appelle une méthode
        int chunkLoads = ChunkRange.chunksCount(player.effectiveViewDistance());
        // Appelle une méthode
        loadChunkTracker.assertCount(chunkLoads);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testPlayerTryRespawn(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        Player player = connection.connect(instance, new Pos(0, 40, 0));

        // Appelle une méthode
        var loadChunkTracker = connection.trackIncoming(ChunkDataPacket.class);
        // Appelle une méthode
        player.setHealth(0);
        // Appelle une méthode
        player.addPacketToQueue(new ClientStatusPacket(ClientStatusPacket.Action.PERFORM_RESPAWN));
        // Appelle une méthode
        player.interpretPacketQueue();
        // Appelle une méthode
        List<ChunkDataPacket> dataPacketList = loadChunkTracker.collect();
        // Affecte une valeur
        Set<ChunkDataPacket> duplicateCheck = new HashSet<>();
        // Appelle une méthode
        int chunkLoads = ChunkRange.chunksCount(player.effectiveViewDistance());
        // Appelle une méthode
        loadChunkTracker.assertCount(chunkLoads);
        // Boucle : répète un bloc
        for (ChunkDataPacket packet : dataPacketList) {
            // Appelle une méthode
            assertFalse(duplicateCheck.contains(packet));
            // Appelle une méthode
            duplicateCheck.add(packet);
            // Appelle une méthode
            assertTrue(Math.abs(packet.chunkX()) <= player.effectiveViewDistance() && Math.abs(packet.chunkZ()) <= player.effectiveViewDistance());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
