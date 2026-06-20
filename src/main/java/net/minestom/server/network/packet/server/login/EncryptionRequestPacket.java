// Package declaration for this file
package net.minestom.server.network.packet.server.login;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Import of a required class
import java.util.Arrays;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record EncryptionRequestPacket(
        // Code statement
        String serverId,
        // Code statement
        byte[] publicKey,
        // Code statement
        byte[] verifyToken,
        // Code statement
        boolean shouldAuthenticate
// Start of a method/block
) implements ServerPacket.Login {
    // Assigns a value
    public static final NetworkBuffer.Type<EncryptionRequestPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            STRING, EncryptionRequestPacket::serverId,
            // Code statement
            BYTE_ARRAY, EncryptionRequestPacket::publicKey,
            // Code statement
            BYTE_ARRAY, EncryptionRequestPacket::verifyToken,
            // Code statement
            BOOLEAN, EncryptionRequestPacket::shouldAuthenticate,
            // Code statement
            EncryptionRequestPacket::new);

    // Start of a method/block
    public EncryptionRequestPacket {
        // Calls a method
        publicKey = publicKey.clone();
        // Calls a method
        verifyToken = verifyToken.clone();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object object) {
        // Branch: checks a condition
        if (!(object instanceof EncryptionRequestPacket(String id, byte[] key, byte[] token, boolean authenticate))) return false;
        // Returns a value to the caller
        return shouldAuthenticate() == authenticate && serverId().equals(id) && Arrays.equals(publicKey(), key) && Arrays.equals(verifyToken(), token);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Calls a method
        int result = serverId().hashCode();
        // Calls a method
        result = 31 * result + Arrays.hashCode(publicKey());
        // Calls a method
        result = 31 * result + Arrays.hashCode(verifyToken());
        // Calls a method
        result = 31 * result + Boolean.hashCode(shouldAuthenticate());
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }
// End of a block/expression
}
