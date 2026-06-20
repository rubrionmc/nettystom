// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.advancements.AdvancementAction;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING;

// Déclaration de type (classe/interface/enum/record)
public record ClientAdvancementTabPacket(AdvancementAction action,
                                         // Annotation pour l'élément suivant
                                         @Nullable String tabIdentifier) implements ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientAdvancementTabPacket> SERIALIZER = NetworkBuffer.Tagged(
            // Instruction de code
            NetworkBuffer.Enum(AdvancementAction.class), ClientAdvancementTabPacket::action,
            // Instruction de code
            Map.of(
                    // Instruction de code
                    AdvancementAction.OPENED_TAB, NetworkBufferTemplate.template(
                            // Instruction de code
                            STRING, ClientAdvancementTabPacket::tabIdentifier,
                            // Instruction de code
                            tabIdentifier -> new ClientAdvancementTabPacket(AdvancementAction.OPENED_TAB, tabIdentifier))
            // Fin d'un bloc/d'une expression
            ),
            // Instruction de code
            NetworkBufferTemplate.template(new ClientAdvancementTabPacket(AdvancementAction.CLOSED_SCREEN, null))
    // Fin d'un bloc/d'une expression
    );

    // Début d'une méthode/d'un bloc
    public ClientAdvancementTabPacket {
        // Embranchement : vérifie une condition
        if (tabIdentifier != null && tabIdentifier.length() > 256) {
            // Lève une exception
            throw new IllegalArgumentException("Tab identifier too long: " + tabIdentifier.length());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
