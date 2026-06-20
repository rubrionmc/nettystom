// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.instance.gamerule.GameRule;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
public record ClientSetGameRulesPacket(List<Entry> entries) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientSetGameRulesPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            Entry.NETWORK_TYPE.list(GameRule.staticRegistry().size()), ClientSetGameRulesPacket::entries,
            // Code statement
            ClientSetGameRulesPacket::new
    // End of a block/expression
    );

    // Start of a method/block
    public ClientSetGameRulesPacket {
        // Calls a method
        entries = List.copyOf(entries);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Entry(RegistryKey<GameRule<?>> key, String value) {
        // Assigns a value
        public static final NetworkBuffer.Type<Entry> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                RegistryKey.networkType(Registries::gameRule), Entry::key,
                // Code statement
                NetworkBuffer.STRING, Entry::value,
                // Code statement
                Entry::new);

        // Start of a method/block
        public Entry {
            // Calls a method
            Objects.requireNonNull(key, "key");
            // Calls a method
            Objects.requireNonNull(value, "value");
            // Calls a method
            Check.argCondition(value.length() > Short.MAX_VALUE, "Value length cannot be greater than Short.MAX_VALUE");
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
