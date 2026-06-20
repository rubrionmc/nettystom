// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.configuration;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.NBT;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING;

// Déclaration de type (classe/interface/enum/record)
public record RegistryDataPacket(
        // Instruction de code
        String registryId,
        // Instruction de code
        List<Entry> entries
// Début d'une méthode/d'un bloc
) implements ServerPacket.Configuration {
    // Affecte une valeur
    public static final NetworkBuffer.Type<RegistryDataPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            STRING, RegistryDataPacket::registryId,
            // Instruction de code
            Entry.SERIALIZER.list(Integer.MAX_VALUE), RegistryDataPacket::entries,
            // Instruction de code
            RegistryDataPacket::new);

    // Déclaration de type (classe/interface/enum/record)
    public record Entry(
            // Instruction de code
            String id,
            // Annotation pour l'élément suivant
            @Nullable BinaryTag data
    // Début d'une méthode/d'un bloc
    ) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Entry> SERIALIZER = NetworkBufferTemplate.template(
                // Appelle une méthode
                STRING, Entry::id, NBT.optional(), Entry::data, Entry::new);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
