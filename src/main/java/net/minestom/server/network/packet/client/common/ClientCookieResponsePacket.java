// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.common;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.CookieStorePacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BYTE_ARRAY;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING;

// Déclaration de type (classe/interface/enum/record)
public record ClientCookieResponsePacket(
        // Instruction de code
        String key,
        // Instruction de code
        byte @Nullable [] value
// Début d'une méthode/d'un bloc
) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientCookieResponsePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            STRING, ClientCookieResponsePacket::key,
            // Instruction de code
            BYTE_ARRAY.optional(), ClientCookieResponsePacket::value,
            // Instruction de code
            ClientCookieResponsePacket::new);

    // Début d'une méthode/d'un bloc
    public ClientCookieResponsePacket {
        // Instruction de code
        Check.argCondition(value != null && value.length > CookieStorePacket.MAX_VALUE_LENGTH,
                // Instruction de code
                "Value is too long: {0} > {1}", value != null ? value.length : 0, CookieStorePacket.MAX_VALUE_LENGTH);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
