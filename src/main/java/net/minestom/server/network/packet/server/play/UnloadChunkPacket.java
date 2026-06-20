// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.INT;

// Déclaration de type (classe/interface/enum/record)
public record UnloadChunkPacket(int chunkX, int chunkZ) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<UnloadChunkPacket> SERIALIZER = new NetworkBuffer.Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, UnloadChunkPacket value) {
            // Client reads this as a single long in big endian, so we have to write it backwards
            // Appelle une méthode
            buffer.write(INT, value.chunkZ);
            // Appelle une méthode
            buffer.write(INT, value.chunkX);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public UnloadChunkPacket read(NetworkBuffer buffer) {
            // Appelle une méthode
            int z = buffer.read(INT);
            // Appelle une méthode
            int x = buffer.read(INT);
            // Renvoie une valeur à l'appelant
            return new UnloadChunkPacket(x, z);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };
// Fin d'un bloc/d'une expression
}
