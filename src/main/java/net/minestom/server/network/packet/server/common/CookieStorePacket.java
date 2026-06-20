// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.common;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Déclaration de type (classe/interface/enum/record)
public record CookieStorePacket(
        // Instruction de code
        String key, byte[] value
// Début d'une méthode/d'un bloc
) implements ServerPacket.Configuration, ServerPacket.Play {
    // Affecte une valeur
    public static final int MAX_VALUE_LENGTH = 5120;

    // Affecte une valeur
    public static final NetworkBuffer.Type<CookieStorePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.STRING, CookieStorePacket::key,
            // Instruction de code
            NetworkBuffer.BYTE_ARRAY, CookieStorePacket::value,
            // Instruction de code
            CookieStorePacket::new);

    // Début d'une méthode/d'un bloc
    public CookieStorePacket {
        // Appelle une méthode
        Check.argCondition(value.length > MAX_VALUE_LENGTH, "Cookie value length too long: {0} > {1}", value.length, MAX_VALUE_LENGTH);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public CookieStorePacket(Key key, byte[] value) {
        // Appelle une méthode
        this(key.asString(), value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
