// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.advancements.AdvancementAction;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Map;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.STRING;

// Type declaration (class/interface/enum/record)
public record ClientAdvancementTabPacket(AdvancementAction action,
                                         // Annotation for the following element
                                         @Nullable String tabIdentifier) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientAdvancementTabPacket> SERIALIZER = NetworkBuffer.Tagged(
            // Code statement
            NetworkBuffer.Enum(AdvancementAction.class), ClientAdvancementTabPacket::action,
            // Code statement
            Map.of(
                    // Code statement
                    AdvancementAction.OPENED_TAB, NetworkBufferTemplate.template(
                            // Code statement
                            STRING, ClientAdvancementTabPacket::tabIdentifier,
                            // Code statement
                            tabIdentifier -> new ClientAdvancementTabPacket(AdvancementAction.OPENED_TAB, tabIdentifier))
            // End of a block/expression
            ),
            // Code statement
            NetworkBufferTemplate.template(new ClientAdvancementTabPacket(AdvancementAction.CLOSED_SCREEN, null))
    // End of a block/expression
    );

    // Start of a method/block
    public ClientAdvancementTabPacket {
        // Branch: checks a condition
        if (tabIdentifier != null && tabIdentifier.length() > 256) {
            // Throws an exception
            throw new IllegalArgumentException("Tab identifier too long: " + tabIdentifier.length());
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
