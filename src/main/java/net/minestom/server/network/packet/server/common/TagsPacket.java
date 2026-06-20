// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.common;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import d'une classe nécessaire
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Objects;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT_ARRAY;

// Déclaration de type (classe/interface/enum/record)
public record TagsPacket(List<Registry> registries) implements ServerPacket.Configuration, ServerPacket.Play {
    // Début d'une méthode/d'un bloc
    public TagsPacket {
        // Appelle une méthode
        registries = List.copyOf(registries);
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final NetworkBuffer.Type<TagsPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            Registry.SERIALIZER.list(), TagsPacket::registries,
            // Instruction de code
            TagsPacket::new
    // Fin d'un bloc/d'une expression
    );

    // Déclaration de type (classe/interface/enum/record)
    public record Registry(String registry, List<Tag> tags) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Registry> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                STRING, Registry::registry,
                // Instruction de code
                Tag.SERIALIZER.list(), Registry::tags,
                // Instruction de code
                Registry::new
        // Fin d'un bloc/d'une expression
        );

        // Début d'une méthode/d'un bloc
        public Registry {
            // Appelle une méthode
            tags = List.copyOf(tags);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Tag(String identifier, int[] entries) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Tag> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                STRING, Tag::identifier,
                // Instruction de code
                VAR_INT_ARRAY, Tag::entries,
                // Instruction de code
                Tag::new
        // Fin d'un bloc/d'une expression
        );

        // Début d'une méthode/d'un bloc
        public Tag {
            // Appelle une méthode
            entries = entries.clone();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean equals(Object object) {
            // Embranchement : vérifie une condition
            if (!(object instanceof Tag(String identifier1, int[] entries1))) return false;
            // Renvoie une valeur à l'appelant
            return Arrays.equals(entries(), entries1) && Objects.equals(identifier(), identifier1);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int hashCode() {
            // Appelle une méthode
            int result = Objects.hashCode(identifier());
            // Appelle une méthode
            result = 31 * result + Arrays.hashCode(entries());
            // Renvoie une valeur à l'appelant
            return result;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
