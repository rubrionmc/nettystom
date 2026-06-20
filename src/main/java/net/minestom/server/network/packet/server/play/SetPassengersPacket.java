// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record SetPassengersPacket(int vehicleEntityId,
                                  // Début d'une méthode/d'un bloc
                                  List<Integer> passengersId) implements ServerPacket.Play {
    // Affecte une valeur
    public static final int MAX_PASSENGERS = 16384;

    // Affecte une valeur
    public static final NetworkBuffer.Type<SetPassengersPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, SetPassengersPacket::vehicleEntityId,
            // Instruction de code
            VAR_INT.list(MAX_PASSENGERS), SetPassengersPacket::passengersId,
            // Instruction de code
            SetPassengersPacket::new);

    // Début d'une méthode/d'un bloc
    public SetPassengersPacket {
        // Appelle une méthode
        passengersId = List.copyOf(passengersId);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
