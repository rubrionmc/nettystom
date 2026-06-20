// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.potion.Potion;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record EntityEffectPacket(int entityId, Potion potion) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<EntityEffectPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, EntityEffectPacket::entityId,
            // Instruction de code
            Potion.NETWORK_TYPE, EntityEffectPacket::potion,
            // Instruction de code
            EntityEffectPacket::new
    // Fin d'un bloc/d'une expression
    );
// Fin d'un bloc/d'une expression
}
