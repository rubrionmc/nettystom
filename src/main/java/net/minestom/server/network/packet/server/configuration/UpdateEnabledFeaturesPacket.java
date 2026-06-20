// Package declaration for this file
package net.minestom.server.network.packet.server.configuration;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.STRING;

// Type declaration (class/interface/enum/record)
public record UpdateEnabledFeaturesPacket(List<String> features) implements ServerPacket.Configuration {
    // Assigns a value
    public static final int MAX_FEATURES = 1024;

    // Start of a method/block
    public UpdateEnabledFeaturesPacket {
        // Branch: checks a condition
        if (features.size() > MAX_FEATURES)
            // Throws an exception
            throw new IllegalArgumentException("Too many features");
        // Calls a method
        features = List.copyOf(features);
    // End of a block/expression
    }

    // Assigns a value
    public static final NetworkBuffer.Type<UpdateEnabledFeaturesPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            STRING.list(MAX_FEATURES), UpdateEnabledFeaturesPacket::features,
            // Code statement
            UpdateEnabledFeaturesPacket::new
    // End of a block/expression
    );
// End of a block/expression
}
