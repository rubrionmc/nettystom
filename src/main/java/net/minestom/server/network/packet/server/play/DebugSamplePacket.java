// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.debug.DebugSubscription;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.Enum;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.LONG_ARRAY;

// Déclaration de type (classe/interface/enum/record)
public record DebugSamplePacket(long [] sample, Type type) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<DebugSamplePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            LONG_ARRAY, DebugSamplePacket::sample,
            // Instruction de code
            Enum(Type.class), DebugSamplePacket::type,
            // Instruction de code
            DebugSamplePacket::new);

    // Déclaration de type (classe/interface/enum/record)
    public enum Type {
        // Appelle une méthode
        TICK_TIME(DebugSubscription.DEDICATED_SERVER_TICK_TIME);

        // Instruction de code
        private final DebugSubscription<?> debugSubscription;

        // Début d'une méthode/d'un bloc
        Type(DebugSubscription<?> debugSubscription) {
            // Accès à l'objet courant/parent
            this.debugSubscription = debugSubscription;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public DebugSubscription<?> getDebugSubscription() {
            // Renvoie une valeur à l'appelant
            return debugSubscription;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
