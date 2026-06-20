// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.scoreboard.Sidebar;
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
public record UpdateScorePacket(
        // Code statement
        String entityName,
        // Code statement
        String objectiveName,
        // Code statement
        int score,
        // Annotation for the following element
        @Nullable Component displayName,
        // Annotation for the following element
        @Nullable Sidebar.NumberFormat numberFormat
// Start of a method/block
) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Assigns a value
    public static final NetworkBuffer.Type<UpdateScorePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            STRING, UpdateScorePacket::entityName,
            // Code statement
            STRING, UpdateScorePacket::objectiveName,
            // Code statement
            VAR_INT, UpdateScorePacket::score,
            // Code statement
            COMPONENT.optional(), UpdateScorePacket::displayName,
            // Code statement
            Sidebar.NumberFormat.SERIALIZER.optional(), UpdateScorePacket::numberFormat,
            // Code statement
            UpdateScorePacket::new
    // End of a block/expression
    );

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<Component> components() {
        // Calls a method
        List<Component> list = new ArrayList<>();

        // Branch: checks a condition
        if (displayName != null) {
            // Calls a method
            list.add(displayName);
        // End of a block/expression
        }

        // Branch: checks a condition
        if (numberFormat != null) {
            // Calls a method
            list.addAll(numberFormat.components());
        // End of a block/expression
        }

        // Returns a value to the caller
        return List.copyOf(list);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Branch: checks a condition
        if (displayName == null && numberFormat == null) return this;

        // Assigns a value
        Component name = displayName;
        // Branch: checks a condition
        if (displayName != null) {
            // Calls a method
            name = operator.apply(displayName);
        // End of a block/expression
        }

        // Assigns a value
        Sidebar.NumberFormat format = numberFormat;
        // Branch: checks a condition
        if (numberFormat != null) {
            // Calls a method
            format = numberFormat.copyWithOperator(operator);
        // End of a block/expression
        }


        // Returns a value to the caller
        return new UpdateScorePacket(
                // Code statement
                entityName,
                // Code statement
                objectiveName,
                // Code statement
                score,
                // Code statement
                name,
                // Code statement
                format
        // End of a block/expression
        );
    // End of a block/expression
    }
// End of a block/expression
}
