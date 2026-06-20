// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.advancements.AdvancementAction;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING;

// Déclaration de type (classe/interface/enum/record)
public record ClientAdvancementTabPacket(AdvancementAction action,
                                         // Annotation pour l'élément suivant
                                         @Nullable String tabIdentifier) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientAdvancementTabPacket> SERIALIZER = new NetworkBuffer.Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, ClientAdvancementTabPacket value) {
            // Appelle une méthode
            buffer.write(NetworkBuffer.Enum(AdvancementAction.class), value.action);
            // Embranchement : vérifie une condition
            if (value.action == AdvancementAction.OPENED_TAB) {
                // Instruction de code
                assert value.tabIdentifier != null;
                // Appelle une méthode
                buffer.write(STRING, value.tabIdentifier);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ClientAdvancementTabPacket read(NetworkBuffer buffer) {
            // Appelle une méthode
            var action = buffer.read(NetworkBuffer.Enum(AdvancementAction.class));
            // Appelle une méthode
            var tabIdentifier = action == AdvancementAction.OPENED_TAB ? buffer.read(STRING) : null;
            // Renvoie une valeur à l'appelant
            return new ClientAdvancementTabPacket(action, tabIdentifier);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

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
