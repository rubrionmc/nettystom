// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientEntityActionPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientInputPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.EntityMetaDataPacket;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class PlayerSprintingMetadataTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void sprintingMetadata(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 40, 0));

        // Appelle une méthode
        player.addPacketToQueue(new ClientInputPacket(true, false, false, false, false, false, true));
        // Instruction de code
        player.addPacketToQueue(new ClientEntityActionPacket(
                // Instruction de code
                player.getEntityId(),
                // Instruction de code
                ClientEntityActionPacket.Action.START_SPRINTING,
                // Instruction de code
                0
        // Instruction de code
        ));

        // Appelle une méthode
        var tracker = connection.trackIncoming(EntityMetaDataPacket.class);
        // Appelle une méthode
        player.interpretPacketQueue();

        // Appelle une méthode
        var packets = tracker.collect();
        // Appelle une méthode
        assertEquals(1, packets.size(), "Expected single packet, got multiple");
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
