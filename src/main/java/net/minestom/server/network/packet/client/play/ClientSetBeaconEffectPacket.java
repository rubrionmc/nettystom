// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.potion.PotionType;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public record ClientSetBeaconEffectPacket(@Nullable PotionType primaryEffect,
                                          // Annotation pour l'élément suivant
                                          @Nullable PotionType secondaryEffect) implements ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientSetBeaconEffectPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            PotionType.NETWORK_TYPE.optional(), ClientSetBeaconEffectPacket::primaryEffect,
            // Instruction de code
            PotionType.NETWORK_TYPE.optional(), ClientSetBeaconEffectPacket::secondaryEffect,
            // Instruction de code
            ClientSetBeaconEffectPacket::new
    // Fin d'un bloc/d'une expression
    );
// Fin d'un bloc/d'une expression
}
