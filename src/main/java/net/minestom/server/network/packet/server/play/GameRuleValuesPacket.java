// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.instance.gamerule.GameRule;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.RegistryKey;

// Import of a required class
import java.util.Map;

// Type declaration (class/interface/enum/record)
public record GameRuleValuesPacket(Map<RegistryKey<GameRule<?>>, String> values) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<GameRuleValuesPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            RegistryKey.networkType(Registries::gameRule).mapValue(NetworkBuffer.STRING, GameRule.staticRegistry().size()), GameRuleValuesPacket::values,
            // Code statement
            GameRuleValuesPacket::new
    // End of a block/expression
    );

    // Start of a method/block
    public GameRuleValuesPacket {
        // Calls a method
        values = Map.copyOf(values);
    // End of a block/expression
    }
// End of a block/expression
}
