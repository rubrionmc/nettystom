// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Arrays;
// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record MapDataPacket(int mapId, byte scale, boolean locked,
                            // Code statement
                            boolean trackingPosition, List<Icon> icons,
                            // Annotation for the following element
                            @Nullable MapDataPacket.ColorContent colorContent) implements ServerPacket.Play {
    // Assigns a value
    public static final int MAX_ICONS = 1024;

    // Start of a method/block
    public MapDataPacket {
        // Calls a method
        icons = List.copyOf(icons);
    // End of a block/expression
    }

    // Assigns a value
    public static final NetworkBuffer.Type<MapDataPacket> SERIALIZER = new NetworkBuffer.Type<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, MapDataPacket value) {
            // Calls a method
            buffer.write(VAR_INT, value.mapId);
            // Calls a method
            buffer.write(BYTE, value.scale);
            // Calls a method
            buffer.write(BOOLEAN, value.locked);
            // Calls a method
            buffer.write(BOOLEAN, value.trackingPosition);
            // Branch: checks a condition
            if (value.trackingPosition) buffer.write(Icon.SERIALIZER.list(), value.icons);
            // Branch: checks a condition
            if (value.colorContent != null) {
                // Calls a method
                buffer.write(ColorContent.SERIALIZER, value.colorContent);
            // Alternative branch of the condition
            } else {
                // Calls a method
                buffer.write(BYTE, (byte) 0);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public MapDataPacket read(NetworkBuffer buffer) {
            // Calls a method
            var mapId = buffer.read(VAR_INT);
            // Calls a method
            var scale = buffer.read(BYTE);
            // Calls a method
            var locked = buffer.read(BOOLEAN);
            // Calls a method
            var trackingPosition = buffer.read(BOOLEAN);
            // Calls a method
            List<Icon> icons = trackingPosition ? buffer.read(Icon.SERIALIZER.list(MAX_ICONS)) : List.of();

            // Calls a method
            var columns = buffer.read(BYTE);
            // Branch: checks a condition
            if (columns <= 0) return new MapDataPacket(mapId, scale, locked, trackingPosition, icons, null);
            // Calls a method
            byte rows = buffer.read(BYTE);
            // Calls a method
            byte x = buffer.read(BYTE);
            // Calls a method
            byte z = buffer.read(BYTE);
            // Calls a method
            byte[] data = buffer.read(BYTE_ARRAY);
            // Returns a value to the caller
            return new MapDataPacket(mapId, scale, locked,
                    // Code statement
                    trackingPosition, icons, new ColorContent(columns, rows, x, z,
                    // Code statement
                    data));
        // End of a block/expression
        }
    // End of a block/expression
    };

    // Type declaration (class/interface/enum/record)
    public record Icon(int type, byte x, byte z, byte direction,
                       // Annotation for the following element
                       @Nullable Component displayName) {
        // Assigns a value
        public static final NetworkBuffer.Type<Icon> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                VAR_INT, Icon::type,
                // Code statement
                BYTE, Icon::x,
                // Code statement
                BYTE, Icon::z,
                // Code statement
                BYTE, Icon::direction,
                // Code statement
                COMPONENT.optional(), Icon::displayName,
                // Code statement
                Icon::new);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record ColorContent(byte columns, byte rows, byte x, byte z,
                               // Start of a method/block
                               byte[] data) {
        // Assigns a value
        public static final NetworkBuffer.Type<ColorContent> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                BYTE, ColorContent::columns,
                // Code statement
                BYTE, ColorContent::rows,
                // Code statement
                BYTE, ColorContent::x,
                // Code statement
                BYTE, ColorContent::z,
                // Code statement
                BYTE_ARRAY, ColorContent::data,
                // Code statement
                ColorContent::new);

        // Start of a method/block
        public ColorContent {
            // Calls a method
            data = data.clone();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean equals(Object object) {
            // Branch: checks a condition
            if (!(object instanceof ColorContent(byte columns1, byte rows1, byte x1, byte z1, byte[] data1))) return false;
            // Returns a value to the caller
            return x() == x1 && z() == z1 && rows() == rows1 && columns() == columns1 && Arrays.equals(data(), data1);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int hashCode() {
            // Calls a method
            int result = columns();
            // Calls a method
            result = 31 * result + rows();
            // Calls a method
            result = 31 * result + x();
            // Calls a method
            result = 31 * result + z();
            // Calls a method
            result = 31 * result + Arrays.hashCode(data());
            // Returns a value to the caller
            return result;
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
