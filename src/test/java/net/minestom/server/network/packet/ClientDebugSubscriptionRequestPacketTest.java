// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet;

// Import d'une classe nécessaire
import net.minestom.server.network.debug.DebugSubscription;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientDebugSubscriptionRequestPacket;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.HashSet;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class ClientDebugSubscriptionRequestPacketTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testUnmodifiable() {
        // Appelle une méthode
        var packet = new ClientDebugSubscriptionRequestPacket(new HashSet<>());
        // Appelle une méthode
        assertThrows(UnsupportedOperationException.class, () -> packet.subscriptions().add(DebugSubscription.POIS));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
