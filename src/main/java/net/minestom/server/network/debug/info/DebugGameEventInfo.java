// Déclaration du paquet de ce fichier
package net.minestom.server.network.debug.info;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.game.GameEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Déclaration de type (classe/interface/enum/record)
public record DebugGameEventInfo(GameEvent event, Point position) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<DebugGameEventInfo> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            GameEvent.NETWORK_TYPE, DebugGameEventInfo::event,
            // Instruction de code
            NetworkBuffer.VECTOR3, DebugGameEventInfo::position,
            // Instruction de code
            DebugGameEventInfo::new);
// Fin d'un bloc/d'une expression
}
