// Déclaration du paquet de ce fichier
package net.minestom.server.entity.player;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.event.player.OutgoingTransferEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.TransferPacket;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Assertions;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class PlayerTransferOutIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testPlayerTransferOut(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, Pos.ZERO);
        // Appelle une méthode
        var tracker = connection.trackIncoming(TransferPacket.class);

        // Appelle une méthode
        player.getPlayerConnection().transfer("example.com", 25565);

        // Début d'une méthode/d'un bloc
        tracker.assertSingle(packet -> {
            // Appelle une méthode
            Assertions.assertEquals("example.com", packet.host());
            // Appelle une méthode
            Assertions.assertEquals(25565, packet.port());
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }


    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testPlayerTransferOutEvent(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, Pos.ZERO);

        // Début d'une méthode/d'un bloc
        env.listen(OutgoingTransferEvent.class).followup(event -> {
            // Appelle une méthode
            Assertions.assertEquals(player, event.getPlayer());
            // Appelle une méthode
            Assertions.assertEquals("example.com", event.getHost());
            // Appelle une méthode
            Assertions.assertEquals(25565, event.getPort());
        // Instruction de code
        });;

        // Appelle une méthode
        player.getPlayerConnection().transfer("example.com", 25565);
    // Fin d'un bloc/d'une expression
    }


    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testPlayerTransferOutEventCancelled(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, Pos.ZERO);

        // Appelle une méthode
        env.process().eventHandler().addListener(OutgoingTransferEvent.class, event -> event.setCancelled(true));

        // Appelle une méthode
        player.getPlayerConnection().transfer("example.com", 25565);
        // Appelle une méthode
        connection.trackIncoming(TransferPacket.class).assertEmpty();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
