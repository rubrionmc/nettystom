// Package declaration for this file
package net.minestom.server.instance.anvil;

// Import of a required class
import it.unimi.dsi.fastutil.ints.IntArrayList;
// Import of a required class
import it.unimi.dsi.fastutil.ints.IntList;
// Import of a required class
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
// Import of a required class
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
// Import of a required class
import it.unimi.dsi.fastutil.longs.LongSet;
// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.*;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.adventure.MinestomAdventure;
// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import net.minestom.server.instance.ChunkLoader;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.Section;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockHandler;
// Import of a required class
import net.minestom.server.instance.palette.Palettes;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.utils.MathUtils;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import net.minestom.server.world.DimensionType;
// Import of a required class
import net.minestom.server.world.biome.Biome;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.slf4j.Logger;
// Import of a required class
import org.slf4j.LoggerFactory;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.io.InputStream;
// Import of a required class
import java.io.OutputStream;
// Import of a required class
import java.nio.file.Files;
// Import of a required class
import java.nio.file.Path;
// Import of a required class
import java.nio.file.StandardCopyOption;
// Import of a required class
import java.nio.file.StandardOpenOption;
// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;
// Import of a required class
import java.util.concurrent.locks.ReentrantLock;

// Static import of a member
import static net.minestom.server.coordinate.CoordConversion.*;
// Static import of a member
import static net.minestom.server.instance.Chunk.CHUNK_SIZE_X;
// Static import of a member
import static net.minestom.server.instance.Chunk.CHUNK_SIZE_Z;

// Type declaration (class/interface/enum/record)
public class AnvilLoader implements ChunkLoader {
    // Calls a method
    private final static Logger LOGGER = LoggerFactory.getLogger(AnvilLoader.class);
    // Calls a method
    private static final DynamicRegistry<Biome> BIOME_REGISTRY = MinecraftServer.getBiomeRegistry();
    // Calls a method
    private final static int PLAINS_ID = BIOME_REGISTRY.getId(Biome.PLAINS);
    // Calls a method
    private static final CompoundBinaryTag[] BLOCK_STATE_ID_2_OBJECT_CACHE = new CompoundBinaryTag[Block.statesCount()];

    // Calls a method
    private final ReentrantLock fileCreationLock = new ReentrantLock();
    // Calls a method
    private final Map<String, RegionFile> alreadyLoaded = new ConcurrentHashMap<>();
    // Code statement
    private final Path path;
    // Code statement
    private final Path levelPath;
    // Code statement
    private final Path regionPath;

    /**
     * Represents the chunks currently loaded per region. Used to determine when a region file can be unloaded.
     * <p>
     * RegionIndex = Set<ChunkIndex>
     */
    // Calls a method
    private final Long2ObjectOpenHashMap<LongSet> perRegionLoadedChunks = new Long2ObjectOpenHashMap<>();
    // Calls a method
    private final ReentrantLock perRegionLoadedChunksLock = new ReentrantLock();

    /**
     * Creates a new AnvilLoader for the given world path and dimension.
     * @param path The path to the world
     * @param dimension The key for the dimension. Use {@link DimensionType} for getting vanilla keys for dimensions.
     */
    // Start of a method/block
    public AnvilLoader(Path path, Key dimension) {
        // Access to the current/parent object
        this.path = path;
        // Access to the current/parent object
        this.levelPath = path.resolve("level.dat");
        // Access to the current/parent object
        this.regionPath = path.resolve("dimensions").resolve(dimension.namespace()).resolve(dimension.value()).resolve("region");
    // End of a block/expression
    }

    /**
     * @deprecated This creates the AnvilLoader for worlds created before 26.1. Use {@link #AnvilLoader(Path, Key)} instead.
     * @param path The path to the world
     */
    // Annotation for the following element
    @Deprecated(forRemoval = true)
    // Start of a method/block
    public AnvilLoader(Path path) {
        // Access to the current/parent object
        this.path = path;
        // Access to the current/parent object
        this.levelPath = path.resolve("level.dat");
        // Access to the current/parent object
        this.regionPath = path.resolve("region");
    // End of a block/expression
    }

    /**
     * @deprecated This creates the AnvilLoader for worlds created before 26.1. Use {@link #AnvilLoader(Path, Key)} instead.
     * @param path The path to the world
     */
    // Annotation for the following element
    @Deprecated(forRemoval = true)
    // Start of a method/block
    public AnvilLoader(String path) {
        // Calls a method
        this(Path.of(path));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void loadInstance(Instance instance) {
        // Branch: checks a condition
        if (!Files.exists(levelPath)) {
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Exception handling
        try (InputStream is = Files.newInputStream(levelPath)) {
            // Calls a method
            final CompoundBinaryTag tag = BinaryTagIO.reader().readNamed(is, BinaryTagIO.Compression.GZIP).getValue();
            // Calls a method
            Files.copy(levelPath, path.resolve("level.dat_old"), StandardCopyOption.REPLACE_EXISTING);
            // Calls a method
            instance.tagHandler().updateContent(tag);
        // Start of a method/block
        } catch (IOException e) {
            // Calls a method
            MinecraftServer.getExceptionManager().handleException(e);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable Chunk loadChunk(Instance instance, int chunkX, int chunkZ) {
        // Branch: checks a condition
        if (!Files.exists(path)) {
            // No world folder
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
        // Exception handling
        try {
            // Returns a value to the caller
            return loadMCA(instance, chunkX, chunkZ);
        // Start of a method/block
        } catch (Exception e) {
            // Calls a method
            MinecraftServer.getExceptionManager().handleException(e);
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private @Nullable Chunk loadMCA(Instance instance, int chunkX, int chunkZ) throws IOException {
        // Calls a method
        final RegionFile mcaFile = getMCAFile(chunkX, chunkZ);
        // Branch: checks a condition
        if (mcaFile == null) return null;
        // Calls a method
        final CompoundBinaryTag chunkData = mcaFile.readChunkData(chunkX, chunkZ);
        // Branch: checks a condition
        if (chunkData == null) return null;

        // Load the chunk data (assuming it is fully generated)
        // Calls a method
        final Chunk chunk = instance.getChunkSupplier().createChunk(instance, chunkX, chunkZ);
        // Calls a method
        chunk.lockWriteLock();
        // Exception handling
        try {
            // Calls a method
            final String status = chunkData.getString("status");
            // TODO: Should we handle other statuses?
            // Branch: checks a condition
            if (status.isEmpty() || "minecraft:full".equals(status)) {
                // Blocks + Biomes
                // Calls a method
                loadSections(chunk, chunkData);
                // Block entities
                // Calls a method
                loadBlockEntities(chunk, chunkData);
                // Calls a method
                chunk.loadHeightmapsFromNBT(chunkData.getCompound("Heightmaps"));
            // Alternative branch of the condition
            } else {
                // Calls a method
                LOGGER.warn("Skipping partially generated chunk at {}, {} with status {}", chunkX, chunkZ, status);
            // End of a block/expression
            }
            // Assigns a value
            CompoundBinaryTag handlerData = CompoundBinaryTag.builder()
                    // Code statement
                    .put(chunkData)
                    // Code statement
                    .remove("Heightmaps")
                    // Code statement
                    .remove("sections")
                    // Code statement
                    .remove("sections")
                    // Code statement
                    .remove("block_entities")
                    // Calls a method
                    .build();
            // Calls a method
            chunk.tagHandler().updateContent(handlerData);
        // Start of a method/block
        } finally {
            // Calls a method
            chunk.unlockWriteLock();
        // End of a block/expression
        }

        // Cache the index of the loaded chunk
        // Calls a method
        perRegionLoadedChunksLock.lock();
        // Exception handling
        try {
            // Calls a method
            final int regionX = chunkToRegion(chunkX), regionZ = chunkToRegion(chunkZ);
            // Calls a method
            final long regionIndex = regionIndex(regionX, regionZ);
            // Assigns a value
            var chunks = perRegionLoadedChunks.computeIfAbsent(regionIndex, r -> new LongOpenHashSet()); // region cache may have been removed on another thread due to unloadChunk
            // Calls a method
            final long chunkIndex = chunkIndex(chunkX, chunkZ);
            // Calls a method
            chunks.add(chunkIndex);
        // Start of a method/block
        } finally {
            // Calls a method
            perRegionLoadedChunksLock.unlock();
        // End of a block/expression
        }
        // Returns a value to the caller
        return chunk;
    // End of a block/expression
    }

    // Start of a method/block
    private @Nullable RegionFile getMCAFile(int chunkX, int chunkZ) {
        // Calls a method
        final int regionX = chunkToRegion(chunkX), regionZ = chunkToRegion(chunkZ);
        // Calls a method
        final String fileName = RegionFile.getFileName(regionX, regionZ);

        // Calls a method
        final RegionFile loadedFile = alreadyLoaded.get(fileName);
        // Branch: checks a condition
        if (loadedFile != null) return loadedFile;

        // Calls a method
        perRegionLoadedChunksLock.lock();
        // Exception handling
        try {
            // Returns a value to the caller
            return alreadyLoaded.computeIfAbsent(fileName, n -> {
                // Calls a method
                final Path regionPath = this.regionPath.resolve(n);
                // Branch: checks a condition
                if (!Files.exists(regionPath)) {
                    // Returns a value to the caller
                    return null;
                // End of a block/expression
                }

                // Exception handling
                try {
                    // Calls a method
                    final long regionIndex = regionIndex(regionX, regionZ);
                    // Calls a method
                    LongSet previousVersion = perRegionLoadedChunks.put(regionIndex, new LongOpenHashSet());
                    // Code statement
                    assert previousVersion == null : "The AnvilLoader cache should not already have data for this region.";
                    // Returns a value to the caller
                    return new RegionFile(regionPath);
                // Start of a method/block
                } catch (IOException e) {
                    // Calls a method
                    MinecraftServer.getExceptionManager().handleException(e);
                    // Returns a value to the caller
                    return null;
                // End of a block/expression
                }
            // End of a block/expression
            });
        // Start of a method/block
        } finally {
            // Calls a method
            perRegionLoadedChunksLock.unlock();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void loadSections(Chunk chunk, CompoundBinaryTag chunkData) {
        // Loop: repeats a block
        for (BinaryTag sectionTag : chunkData.getList("sections", BinaryTagTypes.COMPOUND)) {
            // Branch: checks a condition
            if (!(sectionTag instanceof CompoundBinaryTag sectionData)) {
                // Calls a method
                LOGGER.warn("Invalid section tag in chunk data: {}", sectionTag);
                // Continues to the next loop iteration
                continue;
            // End of a block/expression
            }

            // Calls a method
            final int sectionY = sectionData.getInt("Y", Integer.MIN_VALUE);
            // Calls a method
            Check.stateCondition(sectionY == Integer.MIN_VALUE, "Missing section Y value");
            // Branch: checks a condition
            if (sectionY < chunk.getMinSection() || sectionY >= chunk.getMaxSection()) {
                // Vanilla stores a section below and above the world for lighting, throw it out.
                // Continues to the next loop iteration
                continue;
            // End of a block/expression
            }

            // Calls a method
            final Section section = chunk.getSection(sectionY);

            // Lighting
            // Branch: checks a condition
            if (sectionData.get("SkyLight") instanceof ByteArrayBinaryTag skyLightTag && skyLightTag.size() == 2048) {
                // Calls a method
                section.skyLight().set(skyLightTag.value());
            // End of a block/expression
            }
            // Branch: checks a condition
            if (sectionData.get("BlockLight") instanceof ByteArrayBinaryTag blockLightTag && blockLightTag.size() == 2048) {
                // Calls a method
                section.blockLight().set(blockLightTag.value());
            // End of a block/expression
            }

            // Code statement
            {   // Biomes
                // Calls a method
                final CompoundBinaryTag biomesTag = sectionData.getCompound("biomes");
                // Calls a method
                final ListBinaryTag biomePaletteTag = biomesTag.getList("palette", BinaryTagTypes.STRING);
                // Calls a method
                int[] convertedBiomePalette = loadBiomePalette(biomePaletteTag);
                // Branch: checks a condition
                if (convertedBiomePalette.length == 1) {
                    // One solid block, no need to check the data
                    // Calls a method
                    section.biomePalette().fill(convertedBiomePalette[0]);
                // Branch: checks a condition
                } else if (convertedBiomePalette.length > 1) {
                    // Calls a method
                    final long[] packedIndices = biomesTag.getLongArray("data");
                    // Calls a method
                    Check.stateCondition(packedIndices.length == 0, "Missing packed biomes data");
                    // Calls a method
                    section.biomePalette().load(convertedBiomePalette, packedIndices);
                // End of a block/expression
                }
            // End of a block/expression
            }

            // Code statement
            {   // Blocks
                // Calls a method
                final CompoundBinaryTag blockStatesTag = sectionData.getCompound("block_states");
                // Calls a method
                final ListBinaryTag blockPaletteTag = blockStatesTag.getList("palette", BinaryTagTypes.COMPOUND);
                // Calls a method
                final int[] convertedPalette = loadBlockPalette(blockPaletteTag);
                // Branch: checks a condition
                if (blockPaletteTag.size() == 1) {
                    // One solid block, no need to check the data
                    // Calls a method
                    section.blockPalette().fill(convertedPalette[0]);
                // Branch: checks a condition
                } else if (blockPaletteTag.size() > 1) {
                    // Calls a method
                    final long[] packedStates = blockStatesTag.getLongArray("data");
                    // Calls a method
                    Check.stateCondition(packedStates.length == 0, "Missing packed states data");
                    // Calls a method
                    section.blockPalette().load(convertedPalette, packedStates);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private int[] loadBlockPalette(ListBinaryTag paletteTag) {
        // Calls a method
        final int length = paletteTag.size();
        // Assigns a value
        int[] convertedPalette = new int[length];
        // Loop: repeats a block
        for (int i = 0; i < length; i++) {
            // Calls a method
            CompoundBinaryTag paletteEntry = paletteTag.getCompound(i);
            // Calls a method
            final String blockName = paletteEntry.getString("Name");
            // Branch: checks a condition
            if (blockName.equals("minecraft:air")) {
                // Calls a method
                convertedPalette[i] = Block.AIR.stateId();
            // Alternative branch of the condition
            } else {
                // Calls a method
                Block block = Objects.requireNonNull(Block.fromKey(blockName), "Unknown block " + blockName);
                // Properties
                // Calls a method
                final CompoundBinaryTag propertiesNBT = paletteEntry.getCompound("Properties");
                // Branch: checks a condition
                if (!propertiesNBT.isEmpty()) {
                    // Calls a method
                    final Map<String, String> properties = HashMap.newHashMap(propertiesNBT.size());
                    // Loop: repeats a block
                    for (var property : propertiesNBT) {
                        // Branch: checks a condition
                        if (property.getValue() instanceof StringBinaryTag propertyValue) {
                            // Calls a method
                            properties.put(property.getKey(), propertyValue.value());
                        // Alternative branch of the condition
                        } else {
                            // Exception handling
                            try {
                                // Code statement
                                LOGGER.warn("Fail to parse block state properties {}, expected a string tag for {}, but contents were {}",
                                        // Calls a method
                                        propertiesNBT, property.getKey(), MinestomAdventure.tagStringIO().asString(property.getValue()));
                            // Start of a method/block
                            } catch (IOException e) {
                                // Calls a method
                                LOGGER.warn("Fail to parse block state properties {}, expected a string tag for {}, but contents were a {} tag", propertiesNBT, property.getKey(), property.getValue());
                            // End of a block/expression
                            }
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                    // Calls a method
                    block = block.withProperties(properties);
                // End of a block/expression
                }

                // Calls a method
                convertedPalette[i] = block.stateId();
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return convertedPalette;
    // End of a block/expression
    }

    // Start of a method/block
    private int[] loadBiomePalette(ListBinaryTag paletteTag) {
        // Calls a method
        final int length = paletteTag.size();
        // Assigns a value
        int[] convertedPalette = new int[length];
        // Loop: repeats a block
        for (int i = 0; i < length; i++) {
            // Calls a method
            final String name = paletteTag.getString(i);
            // Calls a method
            int biomeId = BIOME_REGISTRY.getId(RegistryKey.unsafeOf(name));
            // Branch: checks a condition
            if (biomeId == -1) biomeId = PLAINS_ID;
            // Assigns a value
            convertedPalette[i] = biomeId;
        // End of a block/expression
        }
        // Returns a value to the caller
        return convertedPalette;
    // End of a block/expression
    }

    // Start of a method/block
    private void loadBlockEntities(Chunk loadedChunk, CompoundBinaryTag chunkData) {
        // Loop: repeats a block
        for (BinaryTag blockEntityTag : chunkData.getList("block_entities", BinaryTagTypes.COMPOUND)) {
            // Branch: checks a condition
            if (!(blockEntityTag instanceof CompoundBinaryTag blockEntity)) {
                // Calls a method
                LOGGER.warn("Invalid block entity tag in chunk data: {}", blockEntityTag);
                // Continues to the next loop iteration
                continue;
            // End of a block/expression
            }
            // Calls a method
            final int x = blockEntity.getInt("x"), y = blockEntity.getInt("y"), z = blockEntity.getInt("z");
            // Calls a method
            final int localX = globalToSectionRelative(x), localY = globalToSectionRelative(y), localZ = globalToSectionRelative(z);
            // Calls a method
            Section section = loadedChunk.getSectionAt(y);
            // Calls a method
            final int stateId = section.blockPalette().get(localX, localY, localZ);
            // Calls a method
            Block block = Block.fromStateId(stateId);
            // Code statement
            assert block != null;
            // Load the block handler if the id is present
            // Branch: checks a condition
            if (blockEntity.get("id") instanceof StringBinaryTag blockEntityId) {
                // Calls a method
                final BlockHandler handler = MinecraftServer.getBlockManager().getHandlerOrDummy(blockEntityId.value());
                // Calls a method
                block = block.withHandler(handler);
            // End of a block/expression
            }
            // Remove anvil tags
            // Assigns a value
            CompoundBinaryTag trimmedTag = CompoundBinaryTag.builder()
                    // Code statement
                    .put(blockEntity)
                    // Code statement
                    .remove("id").remove("keepPacked")
                    // Code statement
                    .remove("x").remove("y").remove("z")
                    // Calls a method
                    .build();

            // Place block
            // Calls a method
            final Block finalBlock = !trimmedTag.isEmpty() ? block.withNbt(trimmedTag) : block;
            // Calls a method
            loadedChunk.setBlock(x, y, z, finalBlock);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void saveInstance(Instance instance) {
        // Calls a method
        final CompoundBinaryTag nbt = instance.tagHandler().asCompound();
        // Branch: checks a condition
        if (nbt.isEmpty()) {
            // Instance has no data
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Exception handling
        try (OutputStream os = Files.newOutputStream(levelPath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            // Calls a method
            BinaryTagIO.writer().writeNamed(Map.entry("", nbt), os, BinaryTagIO.Compression.GZIP);
        // Start of a method/block
        } catch (IOException e) {
            // Calls a method
            MinecraftServer.getExceptionManager().handleException(e);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void saveChunk(Chunk chunk) {
        // Calls a method
        final int chunkX = chunk.getChunkX(), chunkZ = chunk.getChunkZ();
        // Calls a method
        final int regionX = chunkToRegion(chunkX), regionZ = chunkToRegion(chunkZ);
        // Calls a method
        final long chunkIndex = chunkIndex(chunkX, chunkZ);
        // Calls a method
        final long regionIndex = regionIndex(regionX, regionZ);

        // Find the region file or create an empty one if missing
        // Code statement
        RegionFile mcaFile;
        // Calls a method
        fileCreationLock.lock();
        // Exception handling
        try {
            // Calls a method
            mcaFile = getMCAFile(chunkX, chunkZ);

            // Branch: checks a condition
            if (mcaFile == null) {
                // Calls a method
                final String regionFileName = RegionFile.getFileName(regionX, regionZ);
                // Exception handling
                try {
                    // Calls a method
                    Path regionFile = regionPath.resolve(regionFileName);
                    // Branch: checks a condition
                    if (!Files.exists(regionFile)) {
                        // Calls a method
                        Files.createDirectories(regionFile.getParent());
                        // Calls a method
                        Files.createFile(regionFile);
                    // End of a block/expression
                    }

                    // Calls a method
                    mcaFile = new RegionFile(regionFile);
                    // Calls a method
                    alreadyLoaded.put(regionFileName, mcaFile);
                // Start of a method/block
                } catch (IOException e) {
                    // Calls a method
                    LOGGER.error("Failed to create region file for {}, {}", chunkX, chunkZ, e);
                    // Calls a method
                    MinecraftServer.getExceptionManager().handleException(e);
                    // Returns a value to the caller
                    return;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // Start of a method/block
        } finally {
            // Calls a method
            fileCreationLock.unlock();

            // Access to the current/parent object
            this.perRegionLoadedChunksLock.lock();
            // Exception handling
            try {
                // Access to the current/parent object
                this.perRegionLoadedChunks.computeIfAbsent(regionIndex, k -> new LongOpenHashSet())
                        // Calls a method
                        .add(chunkIndex);
            // Start of a method/block
            } finally {
                // Access to the current/parent object
                this.perRegionLoadedChunksLock.unlock();
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Exception handling
        try {
            // Calls a method
            final CompoundBinaryTag.Builder chunkData = CompoundBinaryTag.builder();

            // Calls a method
            chunkData.put(chunk.tagHandler().asCompound());

            // Calls a method
            chunkData.putInt("DataVersion", MinecraftServer.DATA_VERSION);
            // Calls a method
            chunkData.putInt("xPos", chunkX);
            // Calls a method
            chunkData.putInt("zPos", chunkZ);
            // Calls a method
            chunkData.putInt("yPos", chunk.getMinSection());
            // Calls a method
            chunkData.putString("status", "minecraft:full");
            // Calls a method
            chunkData.putLong("LastUpdate", chunk.getInstance().getWorldAge());

            // Calls a method
            saveSectionData(chunk, chunkData);

            // Calls a method
            mcaFile.writeChunkData(chunkX, chunkZ, chunkData.build());
        // Start of a method/block
        } catch (IOException e) {
            // Calls a method
            LOGGER.error("Failed to save chunk {}, {}", chunkX, chunkZ, e);
            // Calls a method
            MinecraftServer.getExceptionManager().handleException(e);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void saveSectionData(Chunk chunk, CompoundBinaryTag.Builder chunkData) {
        // Calls a method
        final ListBinaryTag.Builder<CompoundBinaryTag> sections = ListBinaryTag.builder(BinaryTagTypes.COMPOUND);
        // Calls a method
        final ListBinaryTag.Builder<CompoundBinaryTag> blockEntities = ListBinaryTag.builder(BinaryTagTypes.COMPOUND);

        // Block & Biome arrays reused for each chunk
        // Calls a method
        List<BinaryTag> biomePalette = new ArrayList<>();
        // Assigns a value
        int[] biomeIndices = new int[64];

        // Calls a method
        List<BinaryTag> blockPaletteEntries = new ArrayList<>();
        // Assigns a value
        IntList blockPaletteIndices = new IntArrayList(); // Map block indices by state id to avoid doing a deep comparison on every block tag
        // Assigns a value
        int[] blockIndices = new int[SECTION_BLOCK_COUNT];

        // Calls a method
        chunk.lockWriteLock();
        // Exception handling
        try {
            // Loop: repeats a block
            for (int sectionY = chunk.getMinSection(); sectionY < chunk.getMaxSection(); sectionY++) {
                // Calls a method
                final Section section = chunk.getSection(sectionY);

                // Calls a method
                final CompoundBinaryTag.Builder sectionData = CompoundBinaryTag.builder();
                // Calls a method
                sectionData.putByte("Y", (byte) sectionY);

                // Lighting
                // Calls a method
                byte[] skyLight = section.skyLight().array();
                // Branch: checks a condition
                if (skyLight != null && skyLight.length > 0) sectionData.putByteArray("SkyLight", skyLight);
                // Calls a method
                byte[] blockLight = section.blockLight().array();
                // Branch: checks a condition
                if (blockLight != null && blockLight.length > 0) sectionData.putByteArray("BlockLight", blockLight);

                // Assigns a value
                final int globalSectionY = sectionY * 16;
                // Retrieve block data
                // Branch: checks a condition
                if (section.blockPalette().singleValue() != -1) {
                    // Calls a method
                    final Block block = Block.fromStateId(section.blockPalette().singleValue());
                    // Code statement
                    assert block != null;
                    // Calls a method
                    final CompoundBinaryTag blockState = blockStateNbt(block);
                    // Calls a method
                    blockPaletteEntries.add(blockState);
                // Alternative branch of the condition
                } else {
                    // Start of a method/block
                    section.blockPalette().getAll((x, y, z, value) -> {
                        // Calls a method
                        Block block = chunk.getBlock(x, globalSectionY + y, z, Block.Getter.Condition.CACHED);
                        // Branch: checks a condition
                        if (block == null) block = Block.fromStateId(value);
                        // Code statement
                        assert block != null;
                        // Calls a method
                        final CompoundBinaryTag blockState = blockStateNbt(block);
                        // Calls a method
                        int blockPaletteIndex = blockPaletteIndices.indexOf(value);
                        // Branch: checks a condition
                        if (blockPaletteIndex == -1) {
                            // Calls a method
                            blockPaletteIndex = blockPaletteEntries.size();
                            // Calls a method
                            blockPaletteEntries.add(blockState);
                            // Calls a method
                            blockPaletteIndices.add(value);
                        // End of a block/expression
                        }
                        // Assigns a value
                        final int blockIndex = x + y * 16 * 16 + z * 16;
                        // Assigns a value
                        blockIndices[blockIndex] = blockPaletteIndex;

                        // Add block entity if present
                        // Calls a method
                        final BlockHandler handler = block.handler();
                        // Calls a method
                        final CompoundBinaryTag originalNBT = block.nbt();
                        // Branch: checks a condition
                        if (originalNBT != null || handler != null) {
                            // Calls a method
                            CompoundBinaryTag.Builder blockEntityTag = CompoundBinaryTag.builder();
                            // Branch: checks a condition
                            if (originalNBT != null) blockEntityTag.put(originalNBT);
                            // Branch: checks a condition
                            if (handler != null) blockEntityTag.putString("id", handler.getKey().asString());
                            // Calls a method
                            blockEntityTag.putInt("x", x + CHUNK_SIZE_X * chunk.getChunkX());
                            // Calls a method
                            blockEntityTag.putInt("y", y + globalSectionY);
                            // Calls a method
                            blockEntityTag.putInt("z", z + CHUNK_SIZE_Z * chunk.getChunkZ());
                            // Calls a method
                            blockEntityTag.putByte("keepPacked", (byte) 0);
                            // Calls a method
                            blockEntities.add(blockEntityTag.build());
                        // End of a block/expression
                        }
                    // End of a block/expression
                    });
                // End of a block/expression
                }
                // Retrieve biome data
                // Branch: checks a condition
                if (section.biomePalette().singleValue() != -1) {
                    // Assigns a value
                    final RegistryKey<Biome> biomeKey = MinecraftServer.getBiomeRegistry()
                            // Calls a method
                            .getKey(section.biomePalette().singleValue());
                    // Code statement
                    assert biomeKey != null;
                    // Calls a method
                    final BinaryTag biomeName = StringBinaryTag.stringBinaryTag(biomeKey.key().asString());
                    // Calls a method
                    biomePalette.add(biomeName);
                // Alternative branch of the condition
                } else {
                    // Start of a method/block
                    section.biomePalette().getAll((x, y, z, value) -> {
                        // Assigns a value
                        int biomeIndex = x + y * 4 * 4 + z * 4;
                        // Calls a method
                        final RegistryKey<Biome> biomeKey = MinecraftServer.getBiomeRegistry().getKey(value);
                        // Code statement
                        assert biomeKey != null;
                        // Calls a method
                        final BinaryTag biomeName = StringBinaryTag.stringBinaryTag(biomeKey.key().asString());
                        // Calls a method
                        int biomePaletteIndex = biomePalette.indexOf(biomeName);
                        // Branch: checks a condition
                        if (biomePaletteIndex == -1) {
                            // Calls a method
                            biomePaletteIndex = biomePalette.size();
                            // Calls a method
                            biomePalette.add(biomeName);
                        // End of a block/expression
                        }
                        // Assigns a value
                        biomeIndices[biomeIndex] = biomePaletteIndex;
                    // End of a block/expression
                    });
                // End of a block/expression
                }

                // Save the block and biome palettes
                // Calls a method
                final CompoundBinaryTag.Builder blockStates = CompoundBinaryTag.builder();
                // Calls a method
                blockStates.put("palette", ListBinaryTag.listBinaryTag(BinaryTagTypes.COMPOUND, blockPaletteEntries));
                // Branch: checks a condition
                if (blockPaletteEntries.size() > 1) {
                    // If there is only one entry we do not need to write the packed indices
                    // Calls a method
                    final int bitsPerEntry = Math.max(4, MathUtils.bitsToRepresent(blockPaletteEntries.size() - 1));
                    // Calls a method
                    blockStates.putLongArray("data", Palettes.pack(blockIndices, bitsPerEntry));
                // End of a block/expression
                }
                // Calls a method
                sectionData.put("block_states", blockStates.build());

                // Calls a method
                final CompoundBinaryTag.Builder biomes = CompoundBinaryTag.builder();
                // Calls a method
                biomes.put("palette", ListBinaryTag.listBinaryTag(BinaryTagTypes.STRING, biomePalette));
                // Branch: checks a condition
                if (biomePalette.size() > 1) {
                    // If there is only one entry we do not need to write the packed indices
                    // Calls a method
                    final int bitsPerEntry = MathUtils.bitsToRepresent(biomePalette.size() - 1);
                    // Calls a method
                    biomes.putLongArray("data", Palettes.pack(biomeIndices, bitsPerEntry));
                // End of a block/expression
                }
                // Calls a method
                sectionData.put("biomes", biomes.build());

                // Calls a method
                biomePalette.clear();
                // Calls a method
                blockPaletteEntries.clear();
                // Calls a method
                blockPaletteIndices.clear();

                // Calls a method
                sections.add(sectionData.build());
            // End of a block/expression
            }
        // Start of a method/block
        } finally {
            // Calls a method
            chunk.unlockWriteLock();
        // End of a block/expression
        }

        // Calls a method
        chunkData.put("sections", sections.build());
        // Calls a method
        chunkData.put("block_entities", blockEntities.build());
    // End of a block/expression
    }

    // Start of a method/block
    private static CompoundBinaryTag blockStateNbt(final Block block) {
        // Calls a method
        final int stateId = block.stateId();
        // Assigns a value
        CompoundBinaryTag result = BLOCK_STATE_ID_2_OBJECT_CACHE[stateId];
        // Branch: checks a condition
        if (result == null) result = BLOCK_STATE_ID_2_OBJECT_CACHE[stateId] = blockStateNbtCompute(block);
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    // Start of a method/block
    private static CompoundBinaryTag blockStateNbtCompute(final Block block) {
        // Calls a method
        final CompoundBinaryTag.Builder tag = CompoundBinaryTag.builder();
        // Calls a method
        tag.putString("Name", block.name());
        // Branch: checks a condition
        if (!block.properties().isEmpty()) {
            // Calls a method
            final Map<String, String> defaultProperties = block.defaultState().properties();
            // Calls a method
            final CompoundBinaryTag.Builder propertiesTag = CompoundBinaryTag.builder();
            // Loop: repeats a block
            for (Map.Entry<String, String> entry : block.properties().entrySet()) {
                // Calls a method
                final String key = entry.getKey(), value = entry.getValue();
                // Branch: checks a condition
                if (defaultProperties.get(key).equals(value))
                    // Continues to the next loop iteration
                    continue; // Skip default values
                // Calls a method
                propertiesTag.putString(key, value);
            // End of a block/expression
            }
            // Calls a method
            CompoundBinaryTag properties = propertiesTag.build();
            // Branch: checks a condition
            if (!properties.isEmpty()) tag.put("Properties", properties);
        // End of a block/expression
        }
        // Returns a value to the caller
        return tag.build();
    // End of a block/expression
    }

    /**
     * Unload a given chunk. Also unloads a region when no chunk from that region is loaded.
     *
     * @param chunk the chunk to unload
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public void unloadChunk(Chunk chunk) {
        // Calls a method
        final int regionX = chunkToRegion(chunk.getChunkX()), regionZ = chunkToRegion(chunk.getChunkZ());
        // Calls a method
        final long regionIndex = regionIndex(regionX, regionZ);

        // Calls a method
        perRegionLoadedChunksLock.lock();
        // Exception handling
        try {
            // Calls a method
            LongSet chunks = perRegionLoadedChunks.get(regionIndex);
            // Branch: checks a condition
            if (chunks != null) { // if null, trying to unload a chunk from a region that was not created by the AnvilLoader
                // don't check return value, trying to unload a chunk not created by the AnvilLoader is valid
                // Calls a method
                final long chunkIndex = chunkIndex(chunk.getChunkX(), chunk.getChunkZ());
                // Calls a method
                chunks.remove(chunkIndex);

                // Branch: checks a condition
                if (chunks.isEmpty()) {
                    // Calls a method
                    perRegionLoadedChunks.remove(regionIndex);
                    // Calls a method
                    RegionFile regionFile = alreadyLoaded.remove(RegionFile.getFileName(regionX, regionZ));
                    // Branch: checks a condition
                    if (regionFile != null) {
                        // Exception handling
                        try {
                            // Calls a method
                            regionFile.close();
                        // Start of a method/block
                        } catch (IOException e) {
                            // Calls a method
                            MinecraftServer.getExceptionManager().handleException(e);
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
        // Start of a method/block
        } finally {
            // Calls a method
            perRegionLoadedChunksLock.unlock();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean supportsParallelLoading() {
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean supportsParallelSaving() {
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }
// End of a block/expression
}
