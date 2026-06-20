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

// Fin d'un bloc/d'une expression
}
