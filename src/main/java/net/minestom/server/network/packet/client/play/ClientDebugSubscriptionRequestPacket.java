// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.debug.DebugSubscription;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Import of a required class
import java.util.Set;

// Type declaration (class/interface/enum/record)
public record ClientDebugSubscriptionRequestPacket(
        // Code statement
        Set<DebugSubscription<?>> subscriptions
// Start of a method/block
) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientDebugSubscriptionRequestPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            DebugSubscription.NETWORK_TYPE.set(DebugSubscription.values().size()), ClientDebugSubscriptionRequestPacket::subscriptions,
            // Code statement
            ClientDebugSubscriptionRequestPacket::new);

    // Start of a method/block
    public ClientDebugSubscriptionRequestPacket {
        // Calls a method
        subscriptions = Set.copyOf(subscriptions);
    // End of a block/expression
    }
// End of a block/expression
}
