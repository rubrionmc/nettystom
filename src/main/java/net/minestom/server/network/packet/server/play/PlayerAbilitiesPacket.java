// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BYTE;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.FLOAT;

// Déclaration de type (classe/interface/enum/record)
public record PlayerAbilitiesPacket(byte flags, float flyingSpeed, float walkingSpeed) implements ServerPacket.Play {
    // Affecte une valeur
    public static final byte FLAG_INVULNERABLE = 0x01;
    // Affecte une valeur
    public static final byte FLAG_FLYING = 0x02;
    // Affecte une valeur
    public static final byte FLAG_ALLOW_FLYING = 0x04;
    // Affecte une valeur
    public static final byte FLAG_INSTANT_BREAK = 0x08;

    // Affecte une valeur
    public static final NetworkBuffer.Type<PlayerAbilitiesPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            BYTE, PlayerAbilitiesPacket::flags,
            // Instruction de code
            FLOAT, PlayerAbilitiesPacket::flyingSpeed,
            // Instruction de code
            FLOAT, PlayerAbilitiesPacket::walkingSpeed,
            // Instruction de code
            PlayerAbilitiesPacket::new);
// Fin d'un bloc/d'une expression
}
