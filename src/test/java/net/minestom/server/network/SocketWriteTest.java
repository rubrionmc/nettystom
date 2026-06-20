// Déclaration du paquet de ce fichier
package net.minestom.server.network;

// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketVanilla;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketWriting;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.nio.charset.StandardCharsets;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.INT;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNotEquals;

// Déclaration de type (classe/interface/enum/record)
public class SocketWriteTest {

    // Déclaration de type (classe/interface/enum/record)
    record IntPacket(int value) implements ServerPacket.Play {
        // Affecte une valeur
        public static final NetworkBuffer.Type<IntPacket> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                INT, IntPacket::value,
                // Instruction de code
                IntPacket::new);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record CompressiblePacket(String value) implements ServerPacket.Play {
        // Affecte une valeur
        public static final NetworkBuffer.Type<CompressiblePacket> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                STRING, CompressiblePacket::value,
                // Instruction de code
                CompressiblePacket::new);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void writeSingleUncompressed() {
        // Appelle une méthode
        var packet = new IntPacket(5);

        // Appelle une méthode
        var buffer = PacketVanilla.PACKET_POOL.get();
        // Appelle une méthode
        PacketWriting.writeFramedPacket(buffer, IntPacket.SERIALIZER, 1, packet, -1);

        // 3 bytes length [var-int] + 1 byte packet id [var-int] + 4 bytes int
        // The 3 bytes var-int length is hardcoded for performance purpose, could change in the future
        // Appelle une méthode
        assertEquals(3 + 1 + 4, buffer.writeIndex(), "Invalid buffer position");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void writeMultiUncompressed() {
        // Appelle une méthode
        var packet = new IntPacket(5);

        // Appelle une méthode
        var buffer = PacketVanilla.PACKET_POOL.get();
        // Appelle une méthode
        PacketWriting.writeFramedPacket(buffer, IntPacket.SERIALIZER, 1, packet, -1);
        // Appelle une méthode
        PacketWriting.writeFramedPacket(buffer, IntPacket.SERIALIZER, 1, packet, -1);

        // 3 bytes length [var-int] + 1 byte packet id [var-int] + 4 bytes int
        // The 3 bytes var-int length is hardcoded for performance purpose, could change in the future
        // Appelle une méthode
        assertEquals((3 + 1 + 4) * 2, buffer.writeIndex(), "Invalid buffer position");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void writeSingleCompressed() {
        // Appelle une méthode
        var string = "Hello world!".repeat(200);
        // Appelle une méthode
        var stringLength = string.getBytes(StandardCharsets.UTF_8).length;
        // Appelle une méthode
        var lengthLength = getVarIntSize(stringLength);

        // Appelle une méthode
        var packet = new CompressiblePacket(string);

        // Appelle une méthode
        var buffer = PacketVanilla.PACKET_POOL.get();
        // Appelle une méthode
        PacketWriting.writeFramedPacket(buffer, CompressiblePacket.SERIALIZER, 1, packet, 256);

        // 3 bytes packet length [var-int] + 3 bytes data length [var-int] + 1 byte packet id [var-int] + payload
        // The 3 bytes var-int length is hardcoded for performance purpose, could change in the future
        // Appelle une méthode
        assertNotEquals(3 + 3 + 1 + lengthLength + stringLength, buffer.writeIndex(), "Buffer position does not account for compression");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void writeSingleCompressedSmall() {
        // Appelle une méthode
        var packet = new IntPacket(5);

        // Appelle une méthode
        var buffer = PacketVanilla.PACKET_POOL.get();
        // Appelle une méthode
        PacketWriting.writeFramedPacket(buffer, IntPacket.SERIALIZER, 1, packet, 256);

        // 3 bytes packet length [var-int] + 3 bytes data length [var-int] + 1 byte packet id [var-int] + 4 bytes int
        // The 3 bytes var-int length is hardcoded for performance purpose, could change in the future
        // Appelle une méthode
        assertEquals(3 + 3 + 1 + 4, buffer.writeIndex(), "Invalid buffer position");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void writeMultiCompressedSmall() {
        // Appelle une méthode
        var packet = new IntPacket(5);

        // Appelle une méthode
        var buffer = PacketVanilla.PACKET_POOL.get();
        // Appelle une méthode
        PacketWriting.writeFramedPacket(buffer, IntPacket.SERIALIZER, 1, packet, 256);
        // Appelle une méthode
        PacketWriting.writeFramedPacket(buffer, IntPacket.SERIALIZER, 1, packet, 256);

        // 3 bytes packet length [var-int] + 3 bytes data length [var-int] + 1 byte packet id [var-int] + 4 bytes int
        // The 3 bytes var-int length is hardcoded for performance purpose, could change in the future
        // Appelle une méthode
        assertEquals((3 + 3 + 1 + 4) * 2, buffer.writeIndex(), "Invalid buffer position");
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static int getVarIntSize(int input) {
        // Renvoie une valeur à l'appelant
        return (input & 0xFFFFFF80) == 0
                // Instruction de code
                ? 1 : (input & 0xFFFFC000) == 0
                // Instruction de code
                ? 2 : (input & 0xFFE00000) == 0
                // Instruction de code
                ? 3 : (input & 0xF0000000) == 0
                // Instruction de code
                ? 4 : 5;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
