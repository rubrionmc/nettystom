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

// Import d'une classe nécessaire
import java.util.Arrays;

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
) implements ClientPacket.Login, ClientPacket.Configuration, ClientPacket.Play {
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
        // Appelle une méthode
        value = value != null ? value.clone() : null;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object object) {
        // Embranchement : vérifie une condition
        if (!(object instanceof ClientCookieResponsePacket(String key1, byte[] value1))) return false;
        // Renvoie une valeur à l'appelant
        return key().equals(key1) && Arrays.equals(value(), value1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Appelle une méthode
        int result = key().hashCode();
        // Appelle une méthode
        result = 31 * result + Arrays.hashCode(value());
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
