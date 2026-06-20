// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.potion.PotionEffect;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record RemoveEntityEffectPacket(int entityId, PotionEffect potionEffect) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<RemoveEntityEffectPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, RemoveEntityEffectPacket::entityId,
            // Instruction de code
            PotionEffect.NETWORK_TYPE, RemoveEntityEffectPacket::potionEffect,
            // Instruction de code
            RemoveEntityEffectPacket::new
    // Fin d'un bloc/d'une expression
    );
// Fin d'un bloc/d'une expression
}
