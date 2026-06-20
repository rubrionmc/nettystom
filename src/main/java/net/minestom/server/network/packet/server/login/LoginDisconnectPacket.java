// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.login;

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
import static net.minestom.server.network.NetworkBuffer.JSON_COMPONENT;

// Déclaration de type (classe/interface/enum/record)
public record LoginDisconnectPacket(Component kickMessage) implements ServerPacket.Login,
        // Début d'une méthode/d'un bloc
        ServerPacket.ComponentHolding {
    // Affecte une valeur
    public static final NetworkBuffer.Type<LoginDisconnectPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            JSON_COMPONENT, LoginDisconnectPacket::kickMessage,
            // Instruction de code
            LoginDisconnectPacket::new);

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<Component> components() {
        // Renvoie une valeur à l'appelant
        return List.of(this.kickMessage);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public LoginDisconnectPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Renvoie une valeur à l'appelant
        return new LoginDisconnectPacket(operator.apply(this.kickMessage));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
