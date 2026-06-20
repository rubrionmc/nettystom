// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BYTE;
// Import statique d'un membre
import static net.minestom.server.network.packet.client.play.ClientPlayerPositionPacket.FLAG_HORIZONTAL_COLLISION;
// Import statique d'un membre
import static net.minestom.server.network.packet.client.play.ClientPlayerPositionPacket.FLAG_ON_GROUND;

// Déclaration de type (classe/interface/enum/record)
public record ClientPlayerPositionStatusPacket(byte flags) implements ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientPlayerPositionStatusPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            BYTE, ClientPlayerPositionStatusPacket::flags,
            // Instruction de code
            ClientPlayerPositionStatusPacket::new);

    // Début d'une méthode/d'un bloc
    public ClientPlayerPositionStatusPacket(boolean onGround, boolean horizontalCollision) {
        // Instruction de code
        this((byte) ((onGround ? FLAG_ON_GROUND : 0) |
                // Appelle une méthode
                (byte) (horizontalCollision ? FLAG_HORIZONTAL_COLLISION : 0)));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean onGround() {
        // Renvoie une valeur à l'appelant
        return (flags & FLAG_ON_GROUND) != 0;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean horizontalCollision() {
        // Renvoie une valeur à l'appelant
        return (flags & FLAG_HORIZONTAL_COLLISION) != 0;
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
