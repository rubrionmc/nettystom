// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Déclaration de type (classe/interface/enum/record)
public record ClientSetTestBlockPacket(
        // Instruction de code
        Point blockPosition,
        // Instruction de code
        TestBlockMode mode,
        // Instruction de code
        String message
// Début d'une méthode/d'un bloc
) implements ClientPacket {

    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientSetTestBlockPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.BLOCK_POSITION, ClientSetTestBlockPacket::blockPosition,
            // Instruction de code
            TestBlockMode.NETWORK_TYPE, ClientSetTestBlockPacket::mode,
            // Instruction de code
            NetworkBuffer.STRING, ClientSetTestBlockPacket::message,
            // Instruction de code
            ClientSetTestBlockPacket::new);

    // Déclaration de type (classe/interface/enum/record)
    public enum TestBlockMode {
        // Instruction de code
        START,
        // Instruction de code
        LOG,
        // Instruction de code
        FAIL,
        // Instruction de code
        ACCEPT;

        // Appelle une méthode
        public static final NetworkBuffer.Type<TestBlockMode> NETWORK_TYPE = NetworkBuffer.Enum(TestBlockMode.class);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
