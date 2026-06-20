// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.instance.gamerule.GameRule;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;

// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
public record GameRuleValuesPacket(Map<RegistryKey<GameRule<?>>, String> values) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<GameRuleValuesPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            RegistryKey.networkType(Registries::gameRule).mapValue(NetworkBuffer.STRING, GameRule.staticRegistry().size()), GameRuleValuesPacket::values,
            // Instruction de code
            GameRuleValuesPacket::new
    // Fin d'un bloc/d'une expression
    );

    // Début d'une méthode/d'un bloc
    public GameRuleValuesPacket {
        // Appelle une méthode
        values = Map.copyOf(values);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
