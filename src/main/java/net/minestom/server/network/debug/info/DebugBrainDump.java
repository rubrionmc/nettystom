// Déclaration du paquet de ce fichier
package net.minestom.server.network.debug.info;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Set;

// Déclaration de type (classe/interface/enum/record)
public record DebugBrainDump(
        // Instruction de code
        String name,
        // Instruction de code
        String profession,
        // Instruction de code
        int xp,
        // Instruction de code
        float health,
        // Instruction de code
        float maxHealth,
        // Instruction de code
        String inventory,
        // Instruction de code
        boolean wantsGolen,
        // Instruction de code
        int angerLevel,
        // Instruction de code
        List<String> activities,
        // Instruction de code
        List<String> behaviors,
        // Instruction de code
        List<String> memories,
        // Instruction de code
        List<String> gossips,
        // Instruction de code
        Set<Point> pois,
        // Instruction de code
        Set<Point> potentialPois
// Début d'une méthode/d'un bloc
) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<DebugBrainDump> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.STRING, DebugBrainDump::name,
            // Instruction de code
            NetworkBuffer.STRING, DebugBrainDump::profession,
            // Instruction de code
            NetworkBuffer.INT, DebugBrainDump::xp,
            // Instruction de code
            NetworkBuffer.FLOAT, DebugBrainDump::health,
            // Instruction de code
            NetworkBuffer.FLOAT, DebugBrainDump::maxHealth,
            // Instruction de code
            NetworkBuffer.STRING, DebugBrainDump::inventory,
            // Instruction de code
            NetworkBuffer.BOOLEAN, DebugBrainDump::wantsGolen,
            // Instruction de code
            NetworkBuffer.INT, DebugBrainDump::angerLevel,
            // Instruction de code
            NetworkBuffer.STRING.list(), DebugBrainDump::activities,
            // Instruction de code
            NetworkBuffer.STRING.list(), DebugBrainDump::behaviors,
            // Instruction de code
            NetworkBuffer.STRING.list(), DebugBrainDump::memories,
            // Instruction de code
            NetworkBuffer.STRING.list(), DebugBrainDump::gossips,
            // Instruction de code
            NetworkBuffer.BLOCK_POSITION.set(), DebugBrainDump::pois,
            // Instruction de code
            NetworkBuffer.BLOCK_POSITION.set(), DebugBrainDump::potentialPois,
            // Instruction de code
            DebugBrainDump::new);

    // Début d'une méthode/d'un bloc
    public DebugBrainDump {
        // Appelle une méthode
        activities = List.copyOf(activities);
        // Appelle une méthode
        behaviors = List.copyOf(behaviors);
        // Appelle une méthode
        memories = List.copyOf(memories);
        // Appelle une méthode
        gossips = List.copyOf(gossips);
        // Appelle une méthode
        pois = Set.copyOf(pois);
        // Appelle une méthode
        potentialPois = Set.copyOf(potentialPois);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
