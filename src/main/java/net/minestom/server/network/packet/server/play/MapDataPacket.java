// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record MapDataPacket(int mapId, byte scale, boolean locked,
                            // Instruction de code
                            boolean trackingPosition, List<Icon> icons,
                            // Annotation pour l'élément suivant
                            @Nullable MapDataPacket.ColorContent colorContent) implements ServerPacket.Play {
    // Affecte une valeur
    public static final int MAX_ICONS = 1024;

    // Début d'une méthode/d'un bloc
    public MapDataPacket {
        // Appelle une méthode
        icons = List.copyOf(icons);
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final NetworkBuffer.Type<MapDataPacket> SERIALIZER = new NetworkBuffer.Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, MapDataPacket value) {
            // Appelle une méthode
            buffer.write(VAR_INT, value.mapId);
            // Appelle une méthode
            buffer.write(BYTE, value.scale);
            // Appelle une méthode
            buffer.write(BOOLEAN, value.locked);
            // Appelle une méthode
            buffer.write(BOOLEAN, value.trackingPosition);
            // Embranchement : vérifie une condition
            if (value.trackingPosition) buffer.write(Icon.SERIALIZER.list(), value.icons);
            // Embranchement : vérifie une condition
            if (value.colorContent != null) {
                // Appelle une méthode
                buffer.write(ColorContent.SERIALIZER, value.colorContent);
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                buffer.write(BYTE, (byte) 0);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public MapDataPacket read(NetworkBuffer buffer) {
            // Appelle une méthode
            var mapId = buffer.read(VAR_INT);
            // Appelle une méthode
            var scale = buffer.read(BYTE);
            // Appelle une méthode
            var locked = buffer.read(BOOLEAN);
            // Appelle une méthode
            var trackingPosition = buffer.read(BOOLEAN);
            // Appelle une méthode
            List<Icon> icons = trackingPosition ? buffer.read(Icon.SERIALIZER.list(MAX_ICONS)) : List.of();

            // Appelle une méthode
            var columns = buffer.read(BYTE);
            // Embranchement : vérifie une condition
            if (columns <= 0) return new MapDataPacket(mapId, scale, locked, trackingPosition, icons, null);
            // Appelle une méthode
            byte rows = buffer.read(BYTE);
            // Appelle une méthode
            byte x = buffer.read(BYTE);
            // Appelle une méthode
            byte z = buffer.read(BYTE);
            // Appelle une méthode
            byte[] data = buffer.read(BYTE_ARRAY);
            // Renvoie une valeur à l'appelant
            return new MapDataPacket(mapId, scale, locked,
                    // Instruction de code
                    trackingPosition, icons, new ColorContent(columns, rows, x, z,
                    // Instruction de code
                    data));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Déclaration de type (classe/interface/enum/record)
    public record Icon(int type, byte x, byte z, byte direction,
                       // Annotation pour l'élément suivant
                       @Nullable Component displayName) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Icon> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                VAR_INT, Icon::type,
                // Instruction de code
                BYTE, Icon::x,
                // Instruction de code
                BYTE, Icon::z,
                // Instruction de code
                BYTE, Icon::direction,
                // Instruction de code
                COMPONENT.optional(), Icon::displayName,
                // Instruction de code
                Icon::new);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record ColorContent(byte columns, byte rows, byte x, byte z,
                               // Début d'une méthode/d'un bloc
                               byte[] data) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<ColorContent> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                BYTE, ColorContent::columns,
                // Instruction de code
                BYTE, ColorContent::rows,
                // Instruction de code
                BYTE, ColorContent::x,
                // Instruction de code
                BYTE, ColorContent::z,
                // Instruction de code
                BYTE_ARRAY, ColorContent::data,
                // Instruction de code
                ColorContent::new);

        // Début d'une méthode/d'un bloc
        public ColorContent {
            // Appelle une méthode
            data = data.clone();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean equals(Object object) {
            // Embranchement : vérifie une condition
            if (!(object instanceof ColorContent(byte columns1, byte rows1, byte x1, byte z1, byte[] data1))) return false;
            // Renvoie une valeur à l'appelant
            return x() == x1 && z() == z1 && rows() == rows1 && columns() == columns1 && Arrays.equals(data(), data1);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int hashCode() {
            // Appelle une méthode
            int result = columns();
            // Appelle une méthode
            result = 31 * result + rows();
            // Appelle une méthode
            result = 31 * result + x();
            // Appelle une méthode
            result = 31 * result + z();
            // Appelle une méthode
            result = 31 * result + Arrays.hashCode(data());
            // Renvoie une valeur à l'appelant
            return result;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
