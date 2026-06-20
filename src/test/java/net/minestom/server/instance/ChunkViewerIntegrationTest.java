// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.ChunkRange;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.ChunkDataPacket;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;
// Import d'une classe nécessaire
import org.junit.jupiter.params.ParameterizedTest;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.ValueSource;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class ChunkViewerIntegrationTest {

    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @ValueSource(booleans = {false, true})
    // Début d'une méthode/d'un bloc
    public void basicJoin(boolean sharedInstance, Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Embranchement : vérifie une condition
        if (sharedInstance) {
            // Chunks get their viewers from the instance
            // Ensuring that the system works with shared instances is therefore important
            // Appelle une méthode
            var manager = env.process().instance();
            // Appelle une méthode
            instance = manager.createSharedInstance((InstanceContainer) instance);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        var chunk = instance.loadChunk(0, 0).join();
        // Appelle une méthode
        assertEquals(0, chunk.getViewers().size());

        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, 40, 0));
        // Instruction de code
        assertEquals(1, chunk.getViewers().size(), sharedInstance ?
                // Instruction de code
                "Chunk viewer set must include players from shared instance" : "Instance should have 1 viewer");
        // Appelle une méthode
        assertEquals(player, chunk.getViewers().iterator().next());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void renderDistance(Env env) {
        // Affecte une valeur
        final int viewRadius = ServerFlag.CHUNK_VIEW_DISTANCE;
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Check initial load
        // Début d'un bloc
        {
            // Appelle une méthode
            var tracker = connection.trackIncoming(ChunkDataPacket.class);
            // Appelle une méthode
            var player = connection.connect(instance, new Pos(0, 40, 0));
            // Appelle une méthode
            assertEquals(instance, player.getInstance());
            // Appelle une méthode
            assertEquals(new Pos(0, 40, 0), player.getPosition());
            // Appelle une méthode
            assertEquals(ChunkRange.chunksCount(player.effectiveViewDistance()), tracker.collect().size());
        // Fin d'un bloc/d'une expression
        }
        // Check chunk#sendChunk
        // Début d'un bloc
        {
            // Appelle une méthode
            var tracker = connection.trackIncoming(ChunkDataPacket.class);
            // Boucle : répète un bloc
            for (int x = -viewRadius; x <= viewRadius; x++) {
                // Boucle : répète un bloc
                for (int z = -viewRadius; z <= viewRadius; z++) {
                    // Appelle une méthode
                    instance.getChunk(x, z).sendChunk();
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            assertEquals(ChunkRange.chunksCount(viewRadius), tracker.collect().size());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
