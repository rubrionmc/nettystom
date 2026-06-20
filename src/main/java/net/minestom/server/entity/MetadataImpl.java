// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
final class MetadataImpl {
    // Annotation pour l'élément suivant
    @SuppressWarnings({"rawtypes", "unchecked"})
    // Déclaration de type (classe/interface/enum/record)
    record EntryImpl<T extends @UnknownNullability Object>(
            // Instruction de code
            Metadata.Type<T> metadataType,
            // Instruction de code
            T value
    // Début d'une méthode/d'un bloc
    ) implements Metadata.Entry<T> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int type() {
            // Renvoie une valeur à l'appelant
            return metadataType.id();
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        static final NetworkBuffer.Type<Metadata.Entry<?>> SERIALIZER = new NetworkBuffer.Type<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, Metadata.Entry<?> value) {
                // Appelle une méthode
                final EntryImpl impl = (EntryImpl) value;
                // Appelle une méthode
                buffer.write(VAR_INT, impl.metadataType.id());
                // Appelle une méthode
                buffer.write(impl.metadataType.serializer(), impl.value);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Metadata.Entry<?> read(NetworkBuffer buffer) {
                // Appelle une méthode
                final int id = buffer.read(VAR_INT);
                // Appelle une méthode
                final Metadata.Type<?> type = Metadata.typeById(id);
                // Embranchement : vérifie une condition
                if (type == null) throw new UnsupportedOperationException("Unknown value type: " + id);
                // Renvoie une valeur à l'appelant
                return readEntry(buffer, type);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Début d'une méthode/d'un bloc
        private static <T extends @UnknownNullability Object> Metadata.Entry<T> readEntry(NetworkBuffer buffer, Metadata.Type<T> type) {
            // Renvoie une valeur à l'appelant
            return type.entry(buffer.read(type.serializer()));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
