// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING;

// Déclaration de type (classe/interface/enum/record)
public record SelectAdvancementTabPacket(@Nullable String identifier) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<SelectAdvancementTabPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            STRING.optional(), SelectAdvancementTabPacket::identifier,
            // Instruction de code
            SelectAdvancementTabPacket::new);
// Fin d'un bloc/d'une expression
}
