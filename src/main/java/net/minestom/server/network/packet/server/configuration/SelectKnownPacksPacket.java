// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.configuration;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public record SelectKnownPacksPacket(
        // Instruction de code
        List<Entry> entries
// Début d'une méthode/d'un bloc
) implements ServerPacket.Configuration {
    // Affecte une valeur
    private static final int MAX_ENTRIES = 64;
    // Appelle une méthode
    public static final Entry MINECRAFT_CORE = new Entry("minecraft", "core", MinecraftServer.VERSION_NAME);

    // Affecte une valeur
    public static final NetworkBuffer.Type<SelectKnownPacksPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            Entry.SERIALIZER.list(MAX_ENTRIES), SelectKnownPacksPacket::entries,
            // Instruction de code
            SelectKnownPacksPacket::new);

    // Début d'une méthode/d'un bloc
    public SelectKnownPacksPacket {
        // Appelle une méthode
        Check.argCondition(entries.size() > MAX_ENTRIES, "Too many known packs: {0} > {1}", entries.size(), MAX_ENTRIES);
        // Appelle une méthode
        entries = List.copyOf(entries);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Entry(
            // Instruction de code
            String namespace,
            // Instruction de code
            String id,
            // Instruction de code
            String version
    // Début d'une méthode/d'un bloc
    ) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Entry> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                NetworkBuffer.STRING, Entry::namespace,
                // Instruction de code
                NetworkBuffer.STRING, Entry::id,
                // Instruction de code
                NetworkBuffer.STRING, Entry::version,
                // Instruction de code
                Entry::new);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
