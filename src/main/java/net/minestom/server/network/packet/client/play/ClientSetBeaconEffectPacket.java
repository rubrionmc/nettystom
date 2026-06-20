// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.potion.PotionType;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public record ClientSetBeaconEffectPacket(@Nullable PotionType primaryEffect,
                                          // Annotation for the following element
                                          @Nullable PotionType secondaryEffect) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientSetBeaconEffectPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            PotionType.NETWORK_TYPE.optional(), ClientSetBeaconEffectPacket::primaryEffect,
            // Code statement
            PotionType.NETWORK_TYPE.optional(), ClientSetBeaconEffectPacket::secondaryEffect,
            // Code statement
            ClientSetBeaconEffectPacket::new
    // End of a block/expression
    );
// End of a block/expression
}
