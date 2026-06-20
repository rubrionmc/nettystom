// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.common.ClientPluginMessagePacket;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class ClientPluginMessagePacketTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testClientPluginMessagePacket() {
        // Affecte une valeur
        var array = NetworkBuffer.makeArray(
                // Instruction de code
                ClientPluginMessagePacket.SERIALIZER,
                // Crée un nouvel objet
                new ClientPluginMessagePacket("channel", new byte[0]));

        // Appelle une méthode
        var readBuffer = NetworkBuffer.wrap(array, 0, array.length);
        // Appelle une méthode
        var packet = readBuffer.read(ClientPluginMessagePacket.SERIALIZER);

        // Appelle une méthode
        assertEquals("channel", packet.channel());
        // Appelle une méthode
        assertArrayEquals(new byte[0], packet.data());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testClientPluginMessagePacketClone() {
        // Affecte une valeur
        var bytes = new byte[]{0x10, 0x11};
        // Appelle une méthode
        var message = new ClientPluginMessagePacket("channel", bytes);
        // Appelle une méthode
        message.data()[0] = 0x00;
        // Appelle une méthode
        assertArrayEquals(new byte[]{0x00, 0x11}, message.data());
        // Appelle une méthode
        assertNotSame(message.data(), bytes);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
