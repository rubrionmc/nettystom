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

// Déclaration de type (classe/interface/enum/record)
public record ClientInputPacket(byte flags) implements ClientPacket.Play {
    // Affecte une valeur
    private static final byte FLAG_FORWARD = 1;
    // Affecte une valeur
    private static final byte FLAG_BACKWARD = 1 << 1;
    // Affecte une valeur
    private static final byte FLAG_LEFT = 1 << 2;
    // Affecte une valeur
    private static final byte FLAG_RIGHT = 1 << 3;
    // Affecte une valeur
    private static final byte FLAG_JUMP = 1 << 4;
    // Affecte une valeur
    private static final byte FLAG_SHIFT = 1 << 5;
    // Affecte une valeur
    private static final byte FLAG_SPRINT = 1 << 6;

    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientInputPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            BYTE, ClientInputPacket::flags,
            // Instruction de code
            ClientInputPacket::new);

    // Début d'une méthode/d'un bloc
    public ClientInputPacket(boolean forward, boolean backward, boolean left, boolean right, boolean jump, boolean shift, boolean sprint) {
        // Instruction de code
        this((byte) ((forward ? FLAG_FORWARD : 0) |
                // Instruction de code
                (backward ? FLAG_BACKWARD : 0) |
                // Instruction de code
                (left ? FLAG_LEFT : 0) |
                // Instruction de code
                (right ? FLAG_RIGHT : 0) |
                // Instruction de code
                (jump ? FLAG_JUMP : 0) |
                // Instruction de code
                (shift ? FLAG_SHIFT : 0) |
                // Appelle une méthode
                (sprint ? FLAG_SPRINT : 0)));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean forward() {
        // Renvoie une valeur à l'appelant
        return (flags & FLAG_FORWARD) != 0;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean backward() {
        // Renvoie une valeur à l'appelant
        return (flags & FLAG_BACKWARD) != 0;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean left() {
        // Renvoie une valeur à l'appelant
        return (flags & FLAG_LEFT) != 0;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean right() {
        // Renvoie une valeur à l'appelant
        return (flags & FLAG_RIGHT) != 0;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean jump() {
        // Renvoie une valeur à l'appelant
        return (flags & FLAG_JUMP) != 0;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean shift() {
        // Renvoie une valeur à l'appelant
        return (flags & FLAG_SHIFT) != 0;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean sprint() {
        // Renvoie une valeur à l'appelant
        return (flags & FLAG_SPRINT) != 0;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
