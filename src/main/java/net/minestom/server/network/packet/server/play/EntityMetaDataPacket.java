// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.entity.Metadata;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.HashMap;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.function.UnaryOperator;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BYTE;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record EntityMetaDataPacket(int entityId,
                                   // Start of a method/block
                                   Map<Integer, Metadata.Entry<?>> entries) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Start of a method/block
    public EntityMetaDataPacket {
        // Calls a method
        entries = Map.copyOf(entries);
    // End of a block/expression
    }

    // Assigns a value
    public static final NetworkBuffer.Type<EntityMetaDataPacket> SERIALIZER = new NetworkBuffer.Type<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, EntityMetaDataPacket value) {
            // Calls a method
            buffer.write(VAR_INT, value.entityId);
            // Loop: repeats a block
            for (Map.Entry<Integer, Metadata.Entry<?>> entry : value.entries.entrySet()) {
                // Calls a method
                buffer.write(BYTE, entry.getKey().byteValue());
                // Calls a method
                buffer.write(Metadata.Entry.SERIALIZER, entry.getValue());
            // End of a block/expression
            }
            // Code statement
            buffer.write(BYTE, (byte) 0xFF); // End
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public EntityMetaDataPacket read(NetworkBuffer buffer) {
            // Returns a value to the caller
            return new EntityMetaDataPacket(buffer.read(VAR_INT), readEntries(buffer));
        // End of a block/expression
        }
    // End of a block/expression
    };

    // Start of a method/block
    private static Map<Integer, Metadata.Entry<?>> readEntries(NetworkBuffer reader) {
        // Calls a method
        Map<Integer, Metadata.Entry<?>> entries = new HashMap<>();
        // Loop: repeats a block
        while (true) {
            // Calls a method
            final byte index = reader.read(BYTE);
            // Branch: checks a condition
            if (index == (byte) 0xFF) { // reached the end
                // Breaks out of the loop/block
                break;
            // End of a block/expression
            }
            // Calls a method
            Metadata.Entry<?> entry = Metadata.Entry.SERIALIZER.read(reader);
            // Calls a method
            entries.put((int) index, entry);
        // End of a block/expression
        }
        // Returns a value to the caller
        return entries;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<Component> components() {
        // Returns a value to the caller
        return this.entries.values()
                // Code statement
                .stream()
                // Code statement
                .map(Metadata.Entry::value)
                // Code statement
                .filter(entry -> entry instanceof Component)
                // Code statement
                .map(entry -> (Component) entry)
                // Calls a method
                .toList();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Calls a method
        final var entries = new HashMap<Integer, Metadata.Entry<?>>();

        // Access to the current/parent object
        this.entries.forEach((key, value) -> {
            // Calls a method
            final var t = value.type();
            // Calls a method
            final var v = value.value();

            // Branch: checks a condition
            if (v instanceof Component c) {
                // Calls a method
                var translated = operator.apply(c);
                // Calls a method
                entries.put(key, t == Metadata.TYPE_OPT_CHAT ? Metadata.OptComponent(translated) : Metadata.Component(translated));
            // Alternative branch of the condition
            } else {
                // Calls a method
                entries.put(key, value);
            // End of a block/expression
            }
        // End of a block/expression
        });

        // Returns a value to the caller
        return new EntityMetaDataPacket(this.entityId, entries);
    // End of a block/expression
    }
// End of a block/expression
}
