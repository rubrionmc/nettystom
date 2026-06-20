// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.adventure.ComponentHolder;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.function.UnaryOperator;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record TabCompletePacket(int transactionId, int start, int length,
                                // Start of a method/block
                                List<Match> matches) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Assigns a value
    public static final int MAX_ENTRIES = Short.MAX_VALUE;

    // Assigns a value
    public static final NetworkBuffer.Type<TabCompletePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, TabCompletePacket::transactionId,
            // Code statement
            VAR_INT, TabCompletePacket::start,
            // Code statement
            VAR_INT, TabCompletePacket::length,
            // Code statement
            Match.SERIALIZER.list(MAX_ENTRIES), TabCompletePacket::matches,
            // Code statement
            TabCompletePacket::new);

    // Start of a method/block
    public TabCompletePacket {
        // Calls a method
        matches = List.copyOf(matches);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<Component> components() {
        // Branch: checks a condition
        if (matches.isEmpty()) return List.of();
        // Calls a method
        List<Component> components = new ArrayList<>(matches.size());
        // Loop: repeats a block
        for (Match match : matches) {
            // Branch: checks a condition
            if (match.tooltip != null) {
                // Calls a method
                components.add(match.tooltip);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return components;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Branch: checks a condition
        if (matches.isEmpty()) return this;
        // Calls a method
        final List<Match> updatedMatches = matches.stream().map(match -> match.copyWithOperator(operator)).toList();
        // Returns a value to the caller
        return new TabCompletePacket(transactionId, start, length, updatedMatches);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Match(String match, @Nullable Component tooltip) implements ComponentHolder<Match> {
        // Assigns a value
        public static final NetworkBuffer.Type<Match> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                STRING, Match::match,
                // Code statement
                COMPONENT.optional(), Match::tooltip,
                // Code statement
                Match::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<Component> components() {
            // Returns a value to the caller
            return tooltip != null ? List.of(tooltip) : List.of();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Match copyWithOperator(UnaryOperator<Component> operator) {
            // Returns a value to the caller
            return tooltip != null ? new Match(match, operator.apply(tooltip)) : this;
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
