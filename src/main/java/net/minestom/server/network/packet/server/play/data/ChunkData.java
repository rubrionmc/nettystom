// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play.data;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
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
import net.minestom.server.instance.palette.Palette;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.utils.block.BlockUtils;

// Import d'une classe nécessaire
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.HashMap;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.stream.Collectors;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record ChunkData(Map<Heightmap.Type, long[]> heightmaps, byte[] data,
                        // Début d'une méthode/d'un bloc
                        Map<Integer, Block> blockEntities) {
    // Début d'une méthode/d'un bloc
    public ChunkData {
        // Affecte une valeur
        heightmaps = Map.copyOf(heightmaps); // TODO deep copy?
        // Appelle une méthode
        data = data.clone();
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
            return new ChunkData(buffer.read(HEIGHTMAPS), buffer.read(BYTE_ARRAY), readBlockEntities(buffer));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Début d'une méthode/d'un bloc
    private static Map<Integer, Block> readBlockEntities(NetworkBuffer reader) {
        // Appelle une méthode
        final int size = reader.read(VAR_INT);
        // Appelle une méthode
        final Map<Integer, Block> blockEntities = HashMap.newHashMap(size);
        // Boucle : répète un bloc
        for (int i = 0; i < size; i++) {
            // Appelle une méthode
            final byte xz = reader.read(BYTE);
            // Appelle une méthode
            final short y = reader.read(SHORT);
            // Appelle une méthode
            final BlockEntityType blockEntity = reader.read(BlockEntityType.NETWORK_TYPE);
            // Vanilla sends a TAG_END when the block entity has no client-side NBT.
            // Appelle une méthode
            final BinaryTag nbt = reader.read(NBT);
            // Appelle une méthode
            final Block block = Block.fromKey(blockEntity.key());
            // Embranchement : vérifie une condition
            if (block == null) continue;
            // Appelle une méthode
            final int index = CoordConversion.chunkBlockIndex(xz >> 4, y, xz & 15);
            // Appelle une méthode
            blockEntities.put(index, nbt instanceof CompoundBinaryTag compound ? block.withNbt(compound) : block);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return blockEntities;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object o) {
        // Embranchement : vérifie une condition
        if (!(o instanceof ChunkData(
                // Instruction de code
                Map<Heightmap.Type, long[]> heightmaps1, byte[] data1, Map<Integer, Block> entities
        // Instruction de code
        ))) return false;
        // Renvoie une valeur à l'appelant
        return Arrays.equals(data(), data1) && blockEntities().equals(entities) && heightmaps().equals(heightmaps1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Appelle une méthode
        int result = heightmaps().hashCode();
        // Appelle une méthode
        result = 31 * result + Arrays.hashCode(data());
        // Appelle une méthode
        result = 31 * result + blockEntities().hashCode();
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Section(short blockCount, short liquidCount, Palette blockStates, Palette biomes) {
        // Début d'une méthode/d'un bloc
        public static NetworkBuffer.Type<Section> networkType(int biomeCount) {
            // Renvoie une valeur à l'appelant
            return NetworkBufferTemplate.template(
                    // Instruction de code
                    SHORT, Section::blockCount,
                    // Instruction de code
                    SHORT, Section::liquidCount,
                    // Instruction de code
                    Palette.BLOCK_SERIALIZER, Section::blockStates,
                    // Instruction de code
                    Palette.biomeSerializer(biomeCount), Section::biomes,
                    // Instruction de code
                    Section::new
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
