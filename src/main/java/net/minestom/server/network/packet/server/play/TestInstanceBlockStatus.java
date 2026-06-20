// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Déclaration de type (classe/interface/enum/record)
public record TestInstanceBlockStatus(
        // Instruction de code
        Component status,
        // Annotation pour l'élément suivant
        @Nullable Point size
// Début d'une méthode/d'un bloc
) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Affecte une valeur
    public static final NetworkBuffer.Type<TestInstanceBlockStatus> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.COMPONENT, TestInstanceBlockStatus::status,
            // Instruction de code
            NetworkBuffer.VECTOR3I.optional(), TestInstanceBlockStatus::size,
            // Instruction de code
            TestInstanceBlockStatus::new);

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<Component> components() {
        // Renvoie une valeur à l'appelant
        return List.of(status);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Renvoie une valeur à l'appelant
        return new TestInstanceBlockStatus(operator.apply(status), size);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
