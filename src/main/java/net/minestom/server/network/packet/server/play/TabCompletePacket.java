// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.adventure.ComponentHolder;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
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
public record TabCompletePacket(int transactionId, int start, int length,
                                // Début d'une méthode/d'un bloc
                                List<Match> matches) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Affecte une valeur
    public static final int MAX_ENTRIES = Short.MAX_VALUE;

    // Affecte une valeur
    public static final NetworkBuffer.Type<TabCompletePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, TabCompletePacket::transactionId,
            // Instruction de code
            VAR_INT, TabCompletePacket::start,
            // Instruction de code
            VAR_INT, TabCompletePacket::length,
            // Instruction de code
            Match.SERIALIZER.list(MAX_ENTRIES), TabCompletePacket::matches,
            // Instruction de code
            TabCompletePacket::new);

    // Début d'une méthode/d'un bloc
    public TabCompletePacket {
        // Appelle une méthode
        matches = List.copyOf(matches);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<Component> components() {
        // Embranchement : vérifie une condition
        if (matches.isEmpty()) return List.of();
        // Appelle une méthode
        List<Component> components = new ArrayList<>(matches.size());
        // Boucle : répète un bloc
        for (Match match : matches) {
            // Embranchement : vérifie une condition
            if (match.tooltip != null) {
                // Appelle une méthode
                components.add(match.tooltip);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return components;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Embranchement : vérifie une condition
        if (matches.isEmpty()) return this;
        // Appelle une méthode
        final List<Match> updatedMatches = matches.stream().map(match -> match.copyWithOperator(operator)).toList();
        // Renvoie une valeur à l'appelant
        return new TabCompletePacket(transactionId, start, length, updatedMatches);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Match(String match, @Nullable Component tooltip) implements ComponentHolder<Match> {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Match> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                STRING, Match::match,
                // Instruction de code
                COMPONENT.optional(), Match::tooltip,
                // Instruction de code
                Match::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<Component> components() {
            // Renvoie une valeur à l'appelant
            return tooltip != null ? List.of(tooltip) : List.of();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Match copyWithOperator(UnaryOperator<Component> operator) {
            // Renvoie une valeur à l'appelant
            return tooltip != null ? new Match(match, operator.apply(tooltip)) : this;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
