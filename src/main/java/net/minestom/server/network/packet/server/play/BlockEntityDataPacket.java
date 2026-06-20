// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockEntityType;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Objects;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record BlockEntityDataPacket(
        // Instruction de code
        Point blockPosition,
        // Instruction de code
        BlockEntityType type,
        // Annotation pour l'élément suivant
        @Nullable CompoundBinaryTag data
// Début d'une méthode/d'un bloc
) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<BlockEntityDataPacket> SERIALIZER = new Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, BlockEntityDataPacket value) {
            // Appelle une méthode
            buffer.write(BLOCK_POSITION, value.blockPosition);
            // Appelle une méthode
            buffer.write(BlockEntityType.NETWORK_TYPE, value.type);
            // Embranchement : vérifie une condition
            if (value.data != null) {
                // Appelle une méthode
                buffer.write(NBT_COMPOUND, value.data);
            // Branche alternative de la condition
            } else {
                // TAG_End
                // Appelle une méthode
                buffer.write(BYTE, (byte) 0x00);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public BlockEntityDataPacket read(NetworkBuffer buffer) {
            // Renvoie une valeur à l'appelant
            return new BlockEntityDataPacket(buffer.read(BLOCK_POSITION), buffer.read(BlockEntityType.NETWORK_TYPE), buffer.read(NBT_COMPOUND));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public BlockEntityDataPacket(Point blockPosition, int action, @Nullable CompoundBinaryTag data) {
        // Appelle une méthode
        this(blockPosition, Objects.requireNonNull(BlockEntityType.fromId(action), "Unknown block entity type"), data);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public int action() {
        // Renvoie une valeur à l'appelant
        return type.id();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
