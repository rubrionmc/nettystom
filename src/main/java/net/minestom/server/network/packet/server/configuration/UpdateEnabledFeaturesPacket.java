// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.configuration;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING;

// Déclaration de type (classe/interface/enum/record)
public record UpdateEnabledFeaturesPacket(List<String> features) implements ServerPacket.Configuration {
    // Affecte une valeur
    public static final int MAX_FEATURES = 1024;

    // Début d'une méthode/d'un bloc
    public UpdateEnabledFeaturesPacket {
        // Embranchement : vérifie une condition
        if (features.size() > MAX_FEATURES)
            // Lève une exception
            throw new IllegalArgumentException("Too many features");
        // Appelle une méthode
        features = List.copyOf(features);
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final NetworkBuffer.Type<UpdateEnabledFeaturesPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            STRING.list(MAX_FEATURES), UpdateEnabledFeaturesPacket::features,
            // Instruction de code
            UpdateEnabledFeaturesPacket::new
    // Fin d'un bloc/d'une expression
    );
// Fin d'un bloc/d'une expression
}
