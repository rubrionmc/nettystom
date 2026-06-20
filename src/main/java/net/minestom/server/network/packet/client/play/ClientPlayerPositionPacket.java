// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BYTE;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VECTOR3D;

// Déclaration de type (classe/interface/enum/record)
public record ClientPlayerPositionPacket(Point position, byte flags) implements ClientPacket {
    // Affecte une valeur
    public static final int FLAG_ON_GROUND = 1;
    // Affecte une valeur
    public static final int FLAG_HORIZONTAL_COLLISION = 1 << 1;

    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientPlayerPositionPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VECTOR3D, ClientPlayerPositionPacket::position,
            // Instruction de code
            BYTE, ClientPlayerPositionPacket::flags,
            // Instruction de code
            ClientPlayerPositionPacket::new);

    // Début d'une méthode/d'un bloc
    public ClientPlayerPositionPacket(Point position, boolean onGround, boolean horizontalCollision) {
        // Instruction de code
        this(position, (byte) ((onGround ? FLAG_ON_GROUND : 0) |
                // Instruction de code
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
