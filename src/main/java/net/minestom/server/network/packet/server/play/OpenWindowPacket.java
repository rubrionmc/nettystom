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
public record OpenWindowPacket(int windowId, int windowType,
                               // Début d'une méthode/d'un bloc
                               Component title) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Affecte une valeur
    public static final NetworkBuffer.Type<OpenWindowPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, OpenWindowPacket::windowId,
            // Instruction de code
            VAR_INT, OpenWindowPacket::windowType,
            // Instruction de code
            COMPONENT, OpenWindowPacket::title,
            // Instruction de code
            OpenWindowPacket::new);

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<Component> components() {
        // Renvoie une valeur à l'appelant
        return List.of(this.title);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Renvoie une valeur à l'appelant
        return new OpenWindowPacket(this.windowId, this.windowType, operator.apply(this.title));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
