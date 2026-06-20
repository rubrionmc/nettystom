// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.crypto.MessageSignature;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.BitSet;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record ClientChatMessagePacket(String message, long timestamp,
                                      // Instruction de code
                                      long salt, @Nullable MessageSignature signature,
                                      // Début d'une méthode/d'un bloc
                                      int ackOffset, BitSet ackList, byte checksum) implements ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientChatMessagePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            STRING, ClientChatMessagePacket::message,
            // Instruction de code
            LONG, ClientChatMessagePacket::timestamp,
            // Instruction de code
            LONG, ClientChatMessagePacket::salt,
            // Instruction de code
            MessageSignature.SERIALIZER.optional(), ClientChatMessagePacket::signature,
            // Instruction de code
            VAR_INT, ClientChatMessagePacket::ackOffset,
            // Instruction de code
            FixedBitSet(20), ClientChatMessagePacket::ackList,
            // Instruction de code
            BYTE, ClientChatMessagePacket::checksum,
            // Instruction de code
            ClientChatMessagePacket::new
    // Fin d'un bloc/d'une expression
    );

    // Début d'une méthode/d'un bloc
    public ClientChatMessagePacket {
        // Appelle une méthode
        ackList = (BitSet) ackList.clone();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
