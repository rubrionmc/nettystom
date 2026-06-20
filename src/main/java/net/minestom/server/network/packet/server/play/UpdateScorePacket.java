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
import net.minestom.server.scoreboard.Sidebar;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record UpdateScorePacket(
        // Instruction de code
        String entityName,
        // Instruction de code
        String objectiveName,
        // Instruction de code
        int score,
        // Annotation pour l'élément suivant
        @Nullable Component displayName,
        // Annotation pour l'élément suivant
        @Nullable Sidebar.NumberFormat numberFormat
// Début d'une méthode/d'un bloc
) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Affecte une valeur
    public static final NetworkBuffer.Type<UpdateScorePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            STRING, UpdateScorePacket::entityName,
            // Instruction de code
            STRING, UpdateScorePacket::objectiveName,
            // Instruction de code
            VAR_INT, UpdateScorePacket::score,
            // Instruction de code
            COMPONENT.optional(), UpdateScorePacket::displayName,
            // Instruction de code
            Sidebar.NumberFormat.SERIALIZER.optional(), UpdateScorePacket::numberFormat,
            // Instruction de code
            UpdateScorePacket::new
    // Fin d'un bloc/d'une expression
    );

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<Component> components() {
        // Appelle une méthode
        List<Component> list = new ArrayList<>();

        // Embranchement : vérifie une condition
        if (displayName != null) {
            // Appelle une méthode
            list.add(displayName);
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (numberFormat != null) {
            // Appelle une méthode
            list.addAll(numberFormat.components());
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return List.copyOf(list);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Embranchement : vérifie une condition
        if (displayName == null && numberFormat == null) return this;

        // Affecte une valeur
        Component name = displayName;
        // Embranchement : vérifie une condition
        if (displayName != null) {
            // Appelle une méthode
            name = operator.apply(displayName);
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        Sidebar.NumberFormat format = numberFormat;
        // Embranchement : vérifie une condition
        if (numberFormat != null) {
            // Appelle une méthode
            format = numberFormat.copyWithOperator(operator);
        // Fin d'un bloc/d'une expression
        }


        // Renvoie une valeur à l'appelant
        return new UpdateScorePacket(
                // Instruction de code
                entityName,
                // Instruction de code
                objectiveName,
                // Instruction de code
                score,
                // Instruction de code
                name,
                // Instruction de code
                format
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
