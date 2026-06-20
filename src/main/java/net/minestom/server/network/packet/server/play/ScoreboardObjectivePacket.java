// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.scoreboard.Sidebar;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.function.UnaryOperator;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record ScoreboardObjectivePacket(String objectiveName, byte mode,
                                        // Annotation for the following element
                                        @Nullable Component objectiveValue,
                                        // Annotation for the following element
                                        @Nullable Type type,
                                        // Annotation for the following element
                                        @Nullable Sidebar.NumberFormat numberFormat) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Assigns a value
    public static final NetworkBuffer.Type<ScoreboardObjectivePacket> SERIALIZER = new NetworkBuffer.Type<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, ScoreboardObjectivePacket value) {
            // Calls a method
            buffer.write(STRING, value.objectiveName);
            // Calls a method
            buffer.write(BYTE, value.mode);
            // Branch: checks a condition
            if (value.mode == 0 || value.mode == 2) {
                // Code statement
                assert value.objectiveValue != null;
                // Calls a method
                buffer.write(COMPONENT, value.objectiveValue);
                // Code statement
                assert value.type != null;
                // Calls a method
                buffer.write(VAR_INT, value.type.ordinal());
                // Calls a method
                buffer.write(Sidebar.NumberFormat.SERIALIZER.optional(), value.numberFormat);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ScoreboardObjectivePacket read(NetworkBuffer buffer) {
            // Calls a method
            String objectiveName = buffer.read(STRING);
            // Calls a method
            byte mode = buffer.read(BYTE);
            // Assigns a value
            Component objectiveValue = null;
            // Assigns a value
            Type type = null;
            // Assigns a value
            Sidebar.NumberFormat numberFormat = null;
            // Branch: checks a condition
            if (mode == 0 || mode == 2) {
                // Calls a method
                objectiveValue = buffer.read(COMPONENT);
                // Calls a method
                type = Type.values()[buffer.read(VAR_INT)];
                // Calls a method
                numberFormat = buffer.read(Sidebar.NumberFormat.SERIALIZER.optional());
            // End of a block/expression
            }
            // Returns a value to the caller
            return new ScoreboardObjectivePacket(objectiveName, mode, objectiveValue, type, numberFormat);
        // End of a block/expression
        }
    // End of a block/expression
    };

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<Component> components() {
        // Returns a value to the caller
        return mode == 0 || mode == 2 ? List.of(objectiveValue) :
                // Calls a method
                List.of();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Returns a value to the caller
        return mode == 0 || mode == 2 ? new ScoreboardObjectivePacket(objectiveName, mode,
                // Calls a method
                operator.apply(objectiveValue), type, numberFormat) : this;
    // End of a block/expression
    }

    /**
     * This enumeration represents all available types for the scoreboard objective
     */
    // Type declaration (class/interface/enum/record)
    public enum Type {
        // Code statement
        INTEGER,
        // Code statement
        HEARTS
    // End of a block/expression
    }
// End of a block/expression
}
