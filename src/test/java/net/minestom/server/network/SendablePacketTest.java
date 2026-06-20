// Déclaration du paquet de ce fichier
package net.minestom.server.network;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketReading;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketWriting;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientAnimationPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.CachedPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.LazyPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.SystemChatPacket;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicBoolean;
// Import d'une classe nécessaire
import java.util.zip.DataFormatException;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class SendablePacketTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void lazy() {
        // Appelle une méthode
        var packet = new SystemChatPacket(Component.text("Hello World!"), false);
        // Appelle une méthode
        AtomicBoolean called = new AtomicBoolean(false);
        // Affecte une valeur
        var lazy = new LazyPacket(() -> {
            // Embranchement : vérifie une condition
            if (called.getAndSet(true))
                // Appelle une méthode
                fail();
            // Renvoie une valeur à l'appelant
            return packet;
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        assertSame(packet, lazy.packet());
        // Appelle une méthode
        assertSame(packet, lazy.packet());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cached() {
        // Appelle une méthode
        var packet = new SystemChatPacket(Component.text("Hello World!"), false);
        // Appelle une méthode
        var cached = new CachedPacket(packet);
        // Appelle une méthode
        assertSame(packet, cached.packet(ConnectionState.PLAY));

        // Affecte une valeur
        var buffer = PacketWriting.allocateTrimmedPacket(ConnectionState.PLAY, packet,
                // Appelle une méthode
                MinecraftServer.getCompressionThreshold());
        // Appelle une méthode
        var cachedBuffer = cached.body(ConnectionState.PLAY);
        // Appelle une méthode
        assertTrue(NetworkBuffer.equals(buffer, cachedBuffer));
        // May fail in the very unlikely case where soft references are cleared
        // Rare enough to make this test worth it
        // Appelle une méthode
        assertSame(cached.body(ConnectionState.PLAY), cachedBuffer);

        // Appelle une méthode
        assertSame(packet, cached.packet(ConnectionState.PLAY));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void trimmed() throws DataFormatException {
        // Appelle une méthode
        var packet = new ClientAnimationPacket(PlayerHand.MAIN);

        // Appelle une méthode
        var buffer = PacketWriting.allocateTrimmedPacket(ConnectionState.PLAY, packet, 0);

        // Appelle une méthode
        var result = PacketReading.readClient(buffer, ConnectionState.PLAY, false);
        // Embranchement : vérifie une condition
        if (!(result instanceof PacketReading.Result.Success<ClientPacket> success)) {
            // Appelle une méthode
            fail();
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertEquals(1, success.packets().size());
        // Appelle une méthode
        ClientPacket readPacket = success.packets().getFirst().packet();
        // Appelle une méthode
        assertEquals(packet, readPacket);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
