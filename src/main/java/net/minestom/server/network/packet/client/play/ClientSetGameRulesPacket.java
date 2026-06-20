// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.instance.gamerule.GameRule;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
public record ClientSetGameRulesPacket(List<Entry> entries) implements ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientSetGameRulesPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            Entry.NETWORK_TYPE.list(GameRule.staticRegistry().size()), ClientSetGameRulesPacket::entries,
            // Instruction de code
            ClientSetGameRulesPacket::new
    // Fin d'un bloc/d'une expression
    );

    // Début d'une méthode/d'un bloc
    public ClientSetGameRulesPacket {
        // Appelle une méthode
        entries = List.copyOf(entries);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Entry(RegistryKey<GameRule<?>> key, String value) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Entry> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                RegistryKey.networkType(Registries::gameRule), Entry::key,
                // Instruction de code
                NetworkBuffer.STRING, Entry::value,
                // Instruction de code
                Entry::new);

        // Début d'une méthode/d'un bloc
        public Entry {
            // Appelle une méthode
            Objects.requireNonNull(key, "key");
            // Appelle une méthode
            Objects.requireNonNull(value, "value");
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
