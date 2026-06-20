// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play.data;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.CoordConversion;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockEntityType;
// Import d'une classe nécessaire
import net.minestom.server.instance.heightmap.Heightmap;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.utils.block.BlockUtils;

// Import d'une classe nécessaire
import java.util.HashMap;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.stream.Collectors;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record ChunkData(Map<Heightmap.Type, long[]> heightmaps, byte [] data,
                        // Début d'une méthode/d'un bloc
                        Map<Integer, Block> blockEntities) {
    // Début d'une méthode/d'un bloc
    public ChunkData {
        // Appelle une méthode
        heightmaps = Map.copyOf(heightmaps);
        // Affecte une valeur
        blockEntities = blockEntities.entrySet()
                // Instruction de code
                .stream()
                // Instruction de code
                .filter((entry) -> entry.getValue().registry().isBlockEntity())
                // Appelle une méthode
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final NetworkBuffer.Type<ChunkData> NETWORK_TYPE = new NetworkBuffer.Type<>() {
        // Affecte une valeur
        private static final NetworkBuffer.Type<Map<Heightmap.Type, long[]>> HEIGHTMAPS = Heightmap.Type.NETWORK_TYPE
                // Appelle une méthode
                .mapValue(LONG_ARRAY, Heightmap.Type.values().length);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, ChunkData value) {
            // Heightmaps
            // Appelle une méthode
            buffer.write(HEIGHTMAPS, value.heightmaps);
            // Data
            // Appelle une méthode
            buffer.write(BYTE_ARRAY, value.data);
            // Block entities
            // Appelle une méthode
            buffer.write(VAR_INT, value.blockEntities.size());
            // Boucle : répète un bloc
            for (var entry : value.blockEntities.entrySet()) {
                // Appelle une méthode
                final int index = entry.getKey();
                // Appelle une méthode
                final Block block = entry.getValue();
                // Appelle une méthode
                final var registry = block.registry();

                // Appelle une méthode
                final Point point = CoordConversion.chunkBlockIndexGetGlobal(index, 0, 0);
                // Instruction de code
                buffer.write(BYTE, (byte) ((point.blockX() & 15) << 4 | point.blockZ() & 15)); // xz
                // Instruction de code
                buffer.write(SHORT, (short) point.blockY()); // y

                // Appelle une méthode
                buffer.write(BlockEntityType.NETWORK_TYPE, registry.blockEntityType());
                // Appelle une méthode
                final CompoundBinaryTag nbt = BlockUtils.extractClientNbt(block);
                // Instruction de code
                assert nbt != null;
                // Instruction de code
                buffer.write(NBT, nbt); // block nbt
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ChunkData read(NetworkBuffer buffer) {
            // Renvoie une valeur à l'appelant
            return new ChunkData(buffer.read(HEIGHTMAPS), buffer.read(BYTE_ARRAY),
                    // Appelle une méthode
                    readBlockEntities(buffer));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Début d'une méthode/d'un bloc
    private static Map<Integer, Block> readBlockEntities(NetworkBuffer reader) {
        // Affecte une valeur
        final Map<Integer, Block> blockEntities = new HashMap<>();
        // Appelle une méthode
        final int size = reader.read(VAR_INT);
        // Boucle : répète un bloc
        for (int i = 0; i < size; i++) {
            // Appelle une méthode
            final byte xz = reader.read(BYTE);
            // Appelle une méthode
            final short y = reader.read(SHORT);
            // Appelle une méthode
            final BlockEntityType blockEntityType = reader.read(BlockEntityType.NETWORK_TYPE);
            // Appelle une méthode
            final CompoundBinaryTag nbt = reader.read(NBT_COMPOUND);
            // TODO create block object
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return blockEntities;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
