// Package declaration for this file
package net.minestom.server.network.packet.server.play.data;

// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.coordinate.CoordConversion;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockEntityType;
// Import of a required class
import net.minestom.server.instance.heightmap.Heightmap;
// Import of a required class
import net.minestom.server.instance.palette.Palette;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.utils.block.BlockUtils;

// Import of a required class
import java.util.Arrays;
// Import of a required class
import java.util.HashMap;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.stream.Collectors;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record ChunkData(Map<Heightmap.Type, long[]> heightmaps, byte[] data,
                        // Start of a method/block
                        Map<Integer, Block> blockEntities) {
    // Start of a method/block
    public ChunkData {
        // Assigns a value
        heightmaps = Map.copyOf(heightmaps); // TODO deep copy?
        // Calls a method
        data = data.clone();
        // Assigns a value
        blockEntities = blockEntities.entrySet()
                // Code statement
                .stream()
                // Code statement
                .filter((entry) -> entry.getValue().registry().isBlockEntity())
                // Calls a method
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    // End of a block/expression
    }

    // Assigns a value
    public static final NetworkBuffer.Type<ChunkData> NETWORK_TYPE = new NetworkBuffer.Type<>() {
        // Assigns a value
        private static final NetworkBuffer.Type<Map<Heightmap.Type, long[]>> HEIGHTMAPS = Heightmap.Type.NETWORK_TYPE
                // Calls a method
                .mapValue(LONG_ARRAY, Heightmap.Type.values().length);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, ChunkData value) {
            // Heightmaps
            // Calls a method
            buffer.write(HEIGHTMAPS, value.heightmaps);
            // Data
            // Calls a method
            buffer.write(BYTE_ARRAY, value.data);
            // Block entities
            // Calls a method
            buffer.write(VAR_INT, value.blockEntities.size());
            // Loop: repeats a block
            for (var entry : value.blockEntities.entrySet()) {
                // Calls a method
                final int index = entry.getKey();
                // Calls a method
                final Block block = entry.getValue();
                // Calls a method
                final var registry = block.registry();

                // Calls a method
                final Point point = CoordConversion.chunkBlockIndexGetGlobal(index, 0, 0);
                // Code statement
                buffer.write(BYTE, (byte) ((point.blockX() & 15) << 4 | point.blockZ() & 15)); // xz
                // Code statement
                buffer.write(SHORT, (short) point.blockY()); // y

                // Calls a method
                buffer.write(BlockEntityType.NETWORK_TYPE, registry.blockEntityType());
                // Calls a method
                final CompoundBinaryTag nbt = BlockUtils.extractClientNbt(block);
                // Code statement
                assert nbt != null;
                // Code statement
                buffer.write(NBT, nbt); // block nbt
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ChunkData read(NetworkBuffer buffer) {
            // Returns a value to the caller
            return new ChunkData(buffer.read(HEIGHTMAPS), buffer.read(BYTE_ARRAY), readBlockEntities(buffer));
        // End of a block/expression
        }
    // End of a block/expression
    };

    // Start of a method/block
    private static Map<Integer, Block> readBlockEntities(NetworkBuffer reader) {
        // Calls a method
        final int size = reader.read(VAR_INT);
        // Calls a method
        final Map<Integer, Block> blockEntities = HashMap.newHashMap(size);
        // Loop: repeats a block
        for (int i = 0; i < size; i++) {
            // Calls a method
            final byte xz = reader.read(BYTE);
            // Calls a method
            final short y = reader.read(SHORT);
            // Calls a method
            final BlockEntityType blockEntity = reader.read(BlockEntityType.NETWORK_TYPE);
            // Vanilla sends a TAG_END when the block entity has no client-side NBT.
            // Calls a method
            final BinaryTag nbt = reader.read(NBT);
            // Calls a method
            final Block block = Block.fromKey(blockEntity.key());
            // Branch: checks a condition
            if (block == null) continue;
            // Calls a method
            final int index = CoordConversion.chunkBlockIndex(xz >> 4, y, xz & 15);
            // Calls a method
            blockEntities.put(index, nbt instanceof CompoundBinaryTag compound ? block.withNbt(compound) : block);
        // End of a block/expression
        }
        // Returns a value to the caller
        return blockEntities;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object o) {
        // Branch: checks a condition
        if (!(o instanceof ChunkData(
                // Code statement
                Map<Heightmap.Type, long[]> heightmaps1, byte[] data1, Map<Integer, Block> entities
        // Code statement
        ))) return false;
        // Returns a value to the caller
        return Arrays.equals(data(), data1) && blockEntities().equals(entities) && heightmaps().equals(heightmaps1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Calls a method
        int result = heightmaps().hashCode();
        // Calls a method
        result = 31 * result + Arrays.hashCode(data());
        // Calls a method
        result = 31 * result + blockEntities().hashCode();
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Section(short blockCount, short liquidCount, Palette blockStates, Palette biomes) {
        // Start of a method/block
        public static NetworkBuffer.Type<Section> networkType(int biomeCount) {
            // Returns a value to the caller
            return NetworkBufferTemplate.template(
                    // Code statement
                    SHORT, Section::blockCount,
                    // Code statement
                    SHORT, Section::liquidCount,
                    // Code statement
                    Palette.BLOCK_SERIALIZER, Section::blockStates,
                    // Code statement
                    Palette.biomeSerializer(biomeCount), Section::biomes,
                    // Code statement
                    Section::new
            // End of a block/expression
            );
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
