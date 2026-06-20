// Package declaration for this file
package net.minestom.server.network.packet.client.common;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Type declaration (class/interface/enum/record)
public record ClientCustomClickActionPacket(Key key, BinaryTag payload) implements ClientPacket.Configuration, ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientCustomClickActionPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.KEY, ClientCustomClickActionPacket::key,
            // Code statement
            NetworkBuffer.NBT.lengthPrefixed(65536), ClientCustomClickActionPacket::payload,
            // Code statement
            ClientCustomClickActionPacket::new);
// End of a block/expression
}
