// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.COMPONENT;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record DeathCombatEventPacket(int playerId,
                                     // Début d'une méthode/d'un bloc
                                     Component message) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Affecte une valeur
    public static final NetworkBuffer.Type<DeathCombatEventPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, DeathCombatEventPacket::playerId,
            // Instruction de code
            COMPONENT, DeathCombatEventPacket::message,
            // Instruction de code
            DeathCombatEventPacket::new);

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<Component> components() {
        // Renvoie une valeur à l'appelant
        return List.of(this.message);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Renvoie une valeur à l'appelant
        return new DeathCombatEventPacket(this.playerId, operator.apply(this.message));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
