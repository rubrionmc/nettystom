// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Arrays;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BYTE_ARRAY;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.COMPONENT;

// Déclaration de type (classe/interface/enum/record)
public record ServerDataPacket(Component motd, byte @Nullable [] iconBase64) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ServerDataPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            COMPONENT, ServerDataPacket::motd,
            // Instruction de code
            BYTE_ARRAY.optional(), ServerDataPacket::iconBase64,
            // Instruction de code
            ServerDataPacket::new);

    // Début d'une méthode/d'un bloc
    public ServerDataPacket {
        // Appelle une méthode
        iconBase64 = iconBase64 != null ? iconBase64.clone() : null;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object o) {
        // Embranchement : vérifie une condition
        if (!(o instanceof ServerDataPacket(Component motd1, byte[] base64))) return false;
        // Renvoie une valeur à l'appelant
        return motd().equals(motd1) && Arrays.equals(iconBase64(), base64);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Appelle une méthode
        int result = motd().hashCode();
        // Appelle une méthode
        result = 31 * result + Arrays.hashCode(iconBase64());
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
