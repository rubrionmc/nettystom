// Déclaration du paquet de ce fichier
package net.minestom.testing;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Déclaration de type (classe/interface/enum/record)
public interface TestConnection {
    // Appelle une méthode
    Player connect(Instance instance, Pos pos);

    // Appelle une méthode
    <T extends ServerPacket> Collector<T> trackIncoming(Class<T> type);

    // Début d'une méthode/d'un bloc
    default Collector<ServerPacket> trackIncoming() {
        // Renvoie une valeur à l'appelant
        return trackIncoming(ServerPacket.class);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
