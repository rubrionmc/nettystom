// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING;

// Déclaration de type (classe/interface/enum/record)
public record CustomChatCompletionPacket(Action action,
                                         // Début d'une méthode/d'un bloc
                                         List<String> entries) implements ServerPacket.Play {
    // Affecte une valeur
    public static final int MAX_ENTRIES = Short.MAX_VALUE;

    // Affecte une valeur
    public static final NetworkBuffer.Type<CustomChatCompletionPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.Enum(Action.class), CustomChatCompletionPacket::action,
            // Instruction de code
            STRING.list(MAX_ENTRIES), CustomChatCompletionPacket::entries,
            // Instruction de code
            CustomChatCompletionPacket::new);

    // Début d'une méthode/d'un bloc
    public CustomChatCompletionPacket {
        // Appelle une méthode
        entries = List.copyOf(entries);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum Action {
        // Instruction de code
        ADD, REMOVE, SET
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
