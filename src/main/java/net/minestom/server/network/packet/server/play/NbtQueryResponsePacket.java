// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record NbtQueryResponsePacket(int transactionId, CompoundBinaryTag data) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<NbtQueryResponsePacket> SERIALIZER = new Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, NbtQueryResponsePacket value) {
            // Appelle une méthode
            buffer.write(VAR_INT, value.transactionId);
            // Embranchement : vérifie une condition
            if (value.data != null) {
                // Appelle une méthode
                buffer.write(NBT_COMPOUND, value.data);
            // Branche alternative de la condition
            } else {
                // TAG_End
                // Appelle une méthode
                buffer.write(BYTE, (byte) 0x00);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public NbtQueryResponsePacket read(NetworkBuffer buffer) {
            // Renvoie une valeur à l'appelant
            return new NbtQueryResponsePacket(buffer.read(VAR_INT), buffer.read(NBT_COMPOUND));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };
// Fin d'un bloc/d'une expression
}
