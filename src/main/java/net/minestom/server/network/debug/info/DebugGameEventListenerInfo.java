// Déclaration du paquet de ce fichier
package net.minestom.server.network.debug.info;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Déclaration de type (classe/interface/enum/record)
public record DebugGameEventListenerInfo(int listenerRadius) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<DebugGameEventListenerInfo> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.VAR_INT, DebugGameEventListenerInfo::listenerRadius,
            // Instruction de code
            DebugGameEventListenerInfo::new);
// Fin d'un bloc/d'une expression
}
