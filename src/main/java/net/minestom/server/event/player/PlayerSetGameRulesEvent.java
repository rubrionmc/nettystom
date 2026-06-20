// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientSetGameRulesPacket;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
public class PlayerSetGameRulesEvent implements PlayerInstanceEvent {
    // Instruction de code
    private final Player player;
    // Instruction de code
    private final List<ClientSetGameRulesPacket.Entry> requestedRules;

    // Début d'une méthode/d'un bloc
    public PlayerSetGameRulesEvent(Player player, List<ClientSetGameRulesPacket.Entry> requestedRules) {
        // Accès à l'objet courant/parent
        this.player = Objects.requireNonNull(player, "player");
        // Appelle une méthode
        Objects.requireNonNull(requestedRules, "requestedRules");
        // Accès à l'objet courant/parent
        this.requestedRules = List.copyOf(requestedRules);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Player getPlayer() {
        // Renvoie une valeur à l'appelant
        return player;
    // Fin d'un bloc/d'une expression
    }

    /// The requested new rules by the client.
    // Début d'une méthode/d'un bloc
    public List<ClientSetGameRulesPacket.Entry> getRequestedRules() {
        // Renvoie une valeur à l'appelant
        return requestedRules;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
