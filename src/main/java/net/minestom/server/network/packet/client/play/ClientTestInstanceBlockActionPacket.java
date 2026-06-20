// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public record ClientTestInstanceBlockActionPacket(
        // Code statement
        Point blockPosition,
        // Code statement
        Action action,
        // Code statement
        Data data
// Start of a method/block
) implements ClientPacket.Play {

    // Assigns a value
    public static final NetworkBuffer.Type<ClientTestInstanceBlockActionPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.BLOCK_POSITION, ClientTestInstanceBlockActionPacket::blockPosition,
            // Code statement
            Action.NETWORK_TYPE, ClientTestInstanceBlockActionPacket::action,
            // Code statement
            Data.NETWORK_TYPE, ClientTestInstanceBlockActionPacket::data,
            // Code statement
            ClientTestInstanceBlockActionPacket::new);

    // Type declaration (class/interface/enum/record)
    public record Data(
            // Annotation for the following element
            @Nullable String test,
            // Code statement
            Point size,
            // Code statement
            int rotation,
            // Code statement
            boolean ignoreEntities,
            // Code statement
            Status status,
            // Annotation for the following element
            @Nullable Component errorMessage
    // Start of a method/block
    ) {
        // Assigns a value
        public static final NetworkBuffer.Type<Data> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                NetworkBuffer.STRING.optional(), Data::test,
                // Code statement
                NetworkBuffer.VECTOR3I, Data::size,
                // Code statement
                NetworkBuffer.VAR_INT, Data::rotation,
                // Code statement
                NetworkBuffer.BOOLEAN, Data::ignoreEntities,
                // Code statement
                Status.NETWORK_TYPE, Data::status,
                // Code statement
                NetworkBuffer.COMPONENT.optional(), Data::errorMessage,
                // Code statement
                Data::new);

        // Start of a method/block
        public Data {
            // Branch: checks a condition
            if (test != null)
                // Calls a method
                Check.argCondition(test.length() > Short.MAX_VALUE, "Test length cannot be greater than Short.MAX_VALUE");
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Action {
        // Code statement
        INIT,
        // Code statement
        QUERY,
        // Code statement
        SET,
        // Code statement
        RESET,
        // Code statement
        SAVE,
        // Code statement
        EXPORT,
        // Code statement
        RUN;

        // Calls a method
        public static final NetworkBuffer.Type<Action> NETWORK_TYPE = NetworkBuffer.Enum(Action.class);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Status {
        // Code statement
        CLEARED,
        // Code statement
        RUNNING,
        // Code statement
        FINISHED;

        // Calls a method
        public static final NetworkBuffer.Type<Status> NETWORK_TYPE = NetworkBuffer.Enum(Status.class);
    // End of a block/expression
    }
// End of a block/expression
}
