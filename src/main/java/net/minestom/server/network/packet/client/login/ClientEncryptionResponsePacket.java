// Package declaration for this file
package net.minestom.server.network.packet.client.login;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Import of a required class
import java.util.Arrays;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BYTE_ARRAY;

// Type declaration (class/interface/enum/record)
public record ClientEncryptionResponsePacket(byte[] sharedSecret,
                                             // Start of a method/block
                                             byte[] encryptedVerifyToken) implements ClientPacket.Login {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientEncryptionResponsePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            BYTE_ARRAY, ClientEncryptionResponsePacket::sharedSecret,
            // Code statement
            BYTE_ARRAY, ClientEncryptionResponsePacket::encryptedVerifyToken,
            // Code statement
            ClientEncryptionResponsePacket::new);

    // Start of a method/block
    public ClientEncryptionResponsePacket {
        // Calls a method
        sharedSecret = sharedSecret.clone();
        // Calls a method
        encryptedVerifyToken = encryptedVerifyToken.clone();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object object) {
        // Branch: checks a condition
        if (!(object instanceof ClientEncryptionResponsePacket(byte[] secret, byte[] verifyToken))) return false;
        // Returns a value to the caller
        return Arrays.equals(sharedSecret(), secret) && Arrays.equals(encryptedVerifyToken(), verifyToken);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Calls a method
        int result = Arrays.hashCode(sharedSecret());
        // Calls a method
        result = 31 * result + Arrays.hashCode(encryptedVerifyToken());
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }
// End of a block/expression
}
