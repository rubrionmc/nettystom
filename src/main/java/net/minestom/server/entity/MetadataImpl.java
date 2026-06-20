// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
final class MetadataImpl {
    // Annotation for the following element
    @SuppressWarnings({"rawtypes", "unchecked"})
    // Type declaration (class/interface/enum/record)
    record EntryImpl<T extends @UnknownNullability Object>(
            // Code statement
            Metadata.Type<T> metadataType,
            // Code statement
            T value
    // Start of a method/block
    ) implements Metadata.Entry<T> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public int type() {
            // Returns a value to the caller
            return metadataType.id();
        // End of a block/expression
        }

        // Assigns a value
        static final NetworkBuffer.Type<Metadata.Entry<?>> SERIALIZER = new NetworkBuffer.Type<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void write(NetworkBuffer buffer, Metadata.Entry<?> value) {
                // Calls a method
                final EntryImpl impl = (EntryImpl) value;
                // Calls a method
                buffer.write(VAR_INT, impl.metadataType.id());
                // Calls a method
                buffer.write(impl.metadataType.serializer(), impl.value);
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Metadata.Entry<?> read(NetworkBuffer buffer) {
                // Calls a method
                final int id = buffer.read(VAR_INT);
                // Calls a method
                final Metadata.Type<?> type = Metadata.typeById(id);
                // Branch: checks a condition
                if (type == null) throw new UnsupportedOperationException("Unknown value type: " + id);
                // Returns a value to the caller
                return readEntry(buffer, type);
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Start of a method/block
        private static <T extends @UnknownNullability Object> Metadata.Entry<T> readEntry(NetworkBuffer buffer, Metadata.Type<T> type) {
            // Returns a value to the caller
            return type.entry(buffer.read(type.serializer()));
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
