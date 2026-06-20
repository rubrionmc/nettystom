// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record ClientUpdateSignPacket(
        // Instruction de code
        Point blockPosition,
        // Instruction de code
        boolean isFrontText,
        // Instruction de code
        List<String> lines
// Début d'une méthode/d'un bloc
) implements ClientPacket.Play {
    // Début d'une méthode/d'un bloc
    public ClientUpdateSignPacket {
        // Appelle une méthode
        lines = List.copyOf(lines);
        // Embranchement : vérifie une condition
        if (lines.size() != 4) {
            // Lève une exception
            throw new IllegalArgumentException("Signs must have 4 lines!");
        // Fin d'un bloc/d'une expression
        }
        // Boucle : répète un bloc
        for (String line : lines) {
            // Embranchement : vérifie une condition
            if (line.length() > 384) {
                // Lève une exception
                throw new IllegalArgumentException("Signs must have a maximum of 384 characters per line!");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientUpdateSignPacket> SERIALIZER = new NetworkBuffer.Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, ClientUpdateSignPacket value) {
            // Appelle une méthode
            buffer.write(BLOCK_POSITION, value.blockPosition);
            // Appelle une méthode
            buffer.write(BOOLEAN, value.isFrontText);
            // Appelle une méthode
            buffer.write(STRING, value.lines.get(0));
            // Appelle une méthode
            buffer.write(STRING, value.lines.get(1));
            // Appelle une méthode
            buffer.write(STRING, value.lines.get(2));
            // Appelle une méthode
            buffer.write(STRING, value.lines.get(3));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ClientUpdateSignPacket read(NetworkBuffer buffer) {
            // Renvoie une valeur à l'appelant
            return new ClientUpdateSignPacket(buffer.read(BLOCK_POSITION), buffer.read(BOOLEAN), readLines(buffer));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Début d'une méthode/d'un bloc
    private static List<String> readLines(NetworkBuffer reader) {
        // Renvoie une valeur à l'appelant
        return List.of(reader.read(STRING), reader.read(STRING),
                // Appelle une méthode
                reader.read(STRING), reader.read(STRING));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
