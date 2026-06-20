// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.configuration;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.configuration.SelectKnownPacksPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public record ClientSelectKnownPacksPacket(
        // Instruction de code
        List<SelectKnownPacksPacket.Entry> entries
// Début d'une méthode/d'un bloc
) implements ClientPacket {
    // Affecte une valeur
    private static final int MAX_ENTRIES = 64;

    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientSelectKnownPacksPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            SelectKnownPacksPacket.Entry.SERIALIZER.list(MAX_ENTRIES), ClientSelectKnownPacksPacket::entries,
            // Instruction de code
            ClientSelectKnownPacksPacket::new);

    // Début d'une méthode/d'un bloc
    public ClientSelectKnownPacksPacket {
        // Appelle une méthode
        Check.argCondition(entries.size() > MAX_ENTRIES, "Too many known packs: {0} > {1}", entries.size(), MAX_ENTRIES);
        // Appelle une méthode
        entries = List.copyOf(entries);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
