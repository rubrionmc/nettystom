// Déclaration du paquet de ce fichier
package net.minestom.server.instance.anvil;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.IntArrayList;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.IntList;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.longs.LongSet;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.*;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.adventure.MinestomAdventure;
// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.ChunkLoader;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.Section;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockHandler;
// Import d'une classe nécessaire
import net.minestom.server.instance.palette.Palettes;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import net.minestom.server.world.biome.Biome;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.slf4j.Logger;
// Import d'une classe nécessaire
import org.slf4j.LoggerFactory;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.io.InputStream;
// Import d'une classe nécessaire
import java.io.OutputStream;
// Import d'une classe nécessaire
import java.nio.file.Files;
// Import d'une classe nécessaire
import java.nio.file.Path;
// Import d'une classe nécessaire
import java.nio.file.StandardCopyOption;
// Import d'une classe nécessaire
import java.nio.file.StandardOpenOption;
// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;
// Import d'une classe nécessaire
import java.util.concurrent.locks.ReentrantLock;

// Import statique d'un membre
import static net.minestom.server.coordinate.CoordConversion.*;
// Import statique d'un membre
import static net.minestom.server.instance.Chunk.CHUNK_SIZE_X;
// Import statique d'un membre
import static net.minestom.server.instance.Chunk.CHUNK_SIZE_Z;

// Déclaration de type (classe/interface/enum/record)
public class AnvilLoader implements ChunkLoader {
    // Appelle une méthode
    private final static Logger LOGGER = LoggerFactory.getLogger(AnvilLoader.class);
    // Appelle une méthode
    private static final DynamicRegistry<Biome> BIOME_REGISTRY = MinecraftServer.getBiomeRegistry();
    // Appelle une méthode
    private final static int PLAINS_ID = BIOME_REGISTRY.getId(Biome.PLAINS);
    // Appelle une méthode
    private static final CompoundBinaryTag[] BLOCK_STATE_ID_2_OBJECT_CACHE = new CompoundBinaryTag[Block.statesCount()];

    // Appelle une méthode
    private final ReentrantLock fileCreationLock = new ReentrantLock();
    // Affecte une valeur
    private final Map<String, RegionFile> alreadyLoaded = new ConcurrentHashMap<>();
    // Instruction de code
    private final Path path;
    // Instruction de code
    private final Path levelPath;
    // Instruction de code
    private final Path regionPath;

    /**
     * Represents the chunks currently loaded per region. Used to determine when a region file can be unloaded.
     * <p>
     * RegionIndex = Set<ChunkIndex>
     */
    // Affecte une valeur
    private final Long2ObjectOpenHashMap<LongSet> perRegionLoadedChunks = new Long2ObjectOpenHashMap<>();
    // Appelle une méthode
    private final ReentrantLock perRegionLoadedChunksLock = new ReentrantLock();

    // Début d'une méthode/d'un bloc
    public AnvilLoader(Path path) {
        // Accès à l'objet courant/parent
        this.path = path;
        // Accès à l'objet courant/parent
        this.levelPath = path.resolve("level.dat");
        // Accès à l'objet courant/parent
        this.regionPath = path.resolve("region");
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public AnvilLoader(String path) {
        // Appelle une méthode
        this(Path.of(path));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void loadInstance(Instance instance) {
        // Embranchement : vérifie une condition
        if (!Files.exists(levelPath)) {
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Gestion des exceptions
        try (InputStream is = Files.newInputStream(levelPath)) {
            // Appelle une méthode
            final CompoundBinaryTag tag = BinaryTagIO.reader().readNamed(is, BinaryTagIO.Compression.GZIP).getValue();
            // Appelle une méthode
            Files.copy(levelPath, path.resolve("level.dat_old"), StandardCopyOption.REPLACE_EXISTING);
            // Appelle une méthode
            instance.tagHandler().updateContent(tag);
        // Début d'une méthode/d'un bloc
        } catch (IOException e) {
            // Appelle une méthode
            MinecraftServer.getExceptionManager().handleException(e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable Chunk loadChunk(Instance instance, int chunkX, int chunkZ) {
        // Embranchement : vérifie une condition
        if (!Files.exists(path)) {
            // No world folder
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
        // Gestion des exceptions
        try {
            // Renvoie une valeur à l'appelant
            return loadMCA(instance, chunkX, chunkZ);
        // Début d'une méthode/d'un bloc
        } catch (Exception e) {
            // Appelle une méthode
            MinecraftServer.getExceptionManager().handleException(e);
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private @Nullable Chunk loadMCA(Instance instance, int chunkX, int chunkZ) throws IOException {
        // Appelle une méthode
        final RegionFile mcaFile = getMCAFile(chunkX, chunkZ);
        // Embranchement : vérifie une condition
        if (mcaFile == null) return null;
        // Appelle une méthode
        final CompoundBinaryTag chunkData = mcaFile.readChunkData(chunkX, chunkZ);
        // Embranchement : vérifie une condition
        if (chunkData == null) return null;

        // Load the chunk data (assuming it is fully generated)
        // Appelle une méthode
        final Chunk chunk = instance.getChunkSupplier().createChunk(instance, chunkX, chunkZ);
        // Instruction de code
        synchronized (chunk) { // todo: boo, synchronized
            // Appelle une méthode
            final String status = chunkData.getString("status");
            // TODO: Should we handle other statuses?
            // Embranchement : vérifie une condition
            if (status.isEmpty() || "minecraft:full".equals(status)) {
                // Blocks + Biomes
                // Appelle une méthode
                loadSections(chunk, chunkData);
                // Block entities
                // Appelle une méthode
                loadBlockEntities(chunk, chunkData);
                // Appelle une méthode
                chunk.loadHeightmapsFromNBT(chunkData.getCompound("Heightmaps"));
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                LOGGER.warn("Skipping partially generated chunk at {}, {} with status {}", chunkX, chunkZ, status);
            // Fin d'un bloc/d'une expression
            }
            // Affecte une valeur
            CompoundBinaryTag handlerData = CompoundBinaryTag.builder()
                    // Instruction de code
                    .put(chunkData)
                    // Instruction de code
                    .remove("Heightmaps")
                    // Instruction de code
                    .remove("sections")
                    // Instruction de code
                    .remove("sections")
                    // Instruction de code
                    .remove("block_entities")
                    // Appelle une méthode
                    .build();
            // Appelle une méthode
            chunk.tagHandler().updateContent(handlerData);
        // Fin d'un bloc/d'une expression
        }

        // Cache the index of the loaded chunk
        // Appelle une méthode
        perRegionLoadedChunksLock.lock();
        // Gestion des exceptions
        try {
            // Appelle une méthode
            final int regionX = chunkToRegion(chunkX), regionZ = chunkToRegion(chunkZ);
            // Appelle une méthode
            final long regionIndex = regionIndex(regionX, regionZ);
            // Affecte une valeur
            var chunks = perRegionLoadedChunks.computeIfAbsent(regionIndex, r -> new LongOpenHashSet()); // region cache may have been removed on another thread due to unloadChunk
            // Appelle une méthode
            final long chunkIndex = chunkIndex(chunkX, chunkZ);
            // Appelle une méthode
            chunks.add(chunkIndex);
        // Début d'une méthode/d'un bloc
        } finally {
            // Appelle une méthode
            perRegionLoadedChunksLock.unlock();
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return chunk;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private @Nullable RegionFile getMCAFile(int chunkX, int chunkZ) {
        // Appelle une méthode
        final int regionX = chunkToRegion(chunkX), regionZ = chunkToRegion(chunkZ);
        // Appelle une méthode
        final String fileName = RegionFile.getFileName(regionX, regionZ);

        // Appelle une méthode
        final RegionFile loadedFile = alreadyLoaded.get(fileName);
        // Embranchement : vérifie une condition
        if (loadedFile != null) return loadedFile;

        // Appelle une méthode
        perRegionLoadedChunksLock.lock();
        // Gestion des exceptions
        try {
            // Renvoie une valeur à l'appelant
            return alreadyLoaded.computeIfAbsent(fileName, n -> {
                // Appelle une méthode
                final Path regionPath = this.regionPath.resolve(n);
                // Embranchement : vérifie une condition
                if (!Files.exists(regionPath)) {
                    // Renvoie une valeur à l'appelant
                    return null;
                // Fin d'un bloc/d'une expression
                }

                // Gestion des exceptions
                try {
                    // Appelle une méthode
                    final long regionIndex = regionIndex(regionX, regionZ);
                    // Appelle une méthode
                    LongSet previousVersion = perRegionLoadedChunks.put(regionIndex, new LongOpenHashSet());
                    // Instruction de code
                    assert previousVersion == null : "The AnvilLoader cache should not already have data for this region.";
                    // Renvoie une valeur à l'appelant
                    return new RegionFile(regionPath);
                // Début d'une méthode/d'un bloc
                } catch (IOException e) {
                    // Appelle une méthode
                    MinecraftServer.getExceptionManager().handleException(e);
                    // Renvoie une valeur à l'appelant
                    return null;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            });
        // Début d'une méthode/d'un bloc
        } finally {
            // Appelle une méthode
            perRegionLoadedChunksLock.unlock();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void loadSections(Chunk chunk, CompoundBinaryTag chunkData) {
        // Boucle : répète un bloc
        for (BinaryTag sectionTag : chunkData.getList("sections", BinaryTagTypes.COMPOUND)) {
            // Embranchement : vérifie une condition
            if (!(sectionTag instanceof CompoundBinaryTag sectionData)) {
                // Appelle une méthode
                LOGGER.warn("Invalid section tag in chunk data: {}", sectionTag);
                // Passe à l'itération suivante de la boucle
                continue;
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            final int sectionY = sectionData.getInt("Y", Integer.MIN_VALUE);
            // Appelle une méthode
            Check.stateCondition(sectionY == Integer.MIN_VALUE, "Missing section Y value");
            // Embranchement : vérifie une condition
            if (sectionY < chunk.getMinSection() || sectionY >= chunk.getMaxSection()) {
                // Vanilla stores a section below and above the world for lighting, throw it out.
                // Passe à l'itération suivante de la boucle
                continue;
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            final Section section = chunk.getSection(sectionY);

            // Lighting
            // Embranchement : vérifie une condition
            if (sectionData.get("SkyLight") instanceof ByteArrayBinaryTag skyLightTag && skyLightTag.size() == 2048) {
                // Appelle une méthode
                section.skyLight().set(skyLightTag.value());
            // Fin d'un bloc/d'une expression
            }
            // Embranchement : vérifie une condition
            if (sectionData.get("BlockLight") instanceof ByteArrayBinaryTag blockLightTag && blockLightTag.size() == 2048) {
                // Appelle une méthode
                section.blockLight().set(blockLightTag.value());
            // Fin d'un bloc/d'une expression
            }

            // Instruction de code
            {   // Biomes
                // Appelle une méthode
                final CompoundBinaryTag biomesTag = sectionData.getCompound("biomes");
                // Appelle une méthode
                final ListBinaryTag biomePaletteTag = biomesTag.getList("palette", BinaryTagTypes.STRING);
                // Appelle une méthode
                int[] convertedBiomePalette = loadBiomePalette(biomePaletteTag);
                // Embranchement : vérifie une condition
                if (convertedBiomePalette.length == 1) {
                    // One solid block, no need to check the data
                    // Appelle une méthode
                    section.biomePalette().fill(convertedBiomePalette[0]);
                // Embranchement : vérifie une condition
                } else if (convertedBiomePalette.length > 1) {
                    // Appelle une méthode
                    final long[] packedIndices = biomesTag.getLongArray("data");
                    // Appelle une méthode
                    Check.stateCondition(packedIndices.length == 0, "Missing packed biomes data");
                    // Appelle une méthode
                    section.biomePalette().load(convertedBiomePalette, packedIndices);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // Instruction de code
            {   // Blocks
                // Appelle une méthode
                final CompoundBinaryTag blockStatesTag = sectionData.getCompound("block_states");
                // Appelle une méthode
                final ListBinaryTag blockPaletteTag = blockStatesTag.getList("palette", BinaryTagTypes.COMPOUND);
                // Appelle une méthode
                final int[] convertedPalette = loadBlockPalette(blockPaletteTag);
                // Embranchement : vérifie une condition
                if (blockPaletteTag.size() == 1) {
                    // One solid block, no need to check the data
                    // Appelle une méthode
                    section.blockPalette().fill(convertedPalette[0]);
                // Embranchement : vérifie une condition
                } else if (blockPaletteTag.size() > 1) {
                    // Appelle une méthode
                    final long[] packedStates = blockStatesTag.getLongArray("data");
                    // Appelle une méthode
                    Check.stateCondition(packedStates.length == 0, "Missing packed states data");
                    // Appelle une méthode
                    section.blockPalette().load(convertedPalette, packedStates);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private int[] loadBlockPalette(ListBinaryTag paletteTag) {
        // Appelle une méthode
        final int length = paletteTag.size();
        // Affecte une valeur
        int[] convertedPalette = new int[length];
        // Boucle : répète un bloc
        for (int i = 0; i < length; i++) {
            // Appelle une méthode
            CompoundBinaryTag paletteEntry = paletteTag.getCompound(i);
            // Appelle une méthode
            final String blockName = paletteEntry.getString("Name");
            // Embranchement : vérifie une condition
            if (blockName.equals("minecraft:air")) {
                // Appelle une méthode
                convertedPalette[i] = Block.AIR.stateId();
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                Block block = Objects.requireNonNull(Block.fromKey(blockName), "Unknown block " + blockName);
                // Properties
                // Appelle une méthode
                final CompoundBinaryTag propertiesNBT = paletteEntry.getCompound("Properties");
                // Embranchement : vérifie une condition
                if (!propertiesNBT.isEmpty()) {
                    // Appelle une méthode
                    final Map<String, String> properties = HashMap.newHashMap(propertiesNBT.size());
                    // Boucle : répète un bloc
                    for (var property : propertiesNBT) {
                        // Embranchement : vérifie une condition
                        if (property.getValue() instanceof StringBinaryTag propertyValue) {
                            // Appelle une méthode
                            properties.put(property.getKey(), propertyValue.value());
                        // Branche alternative de la condition
                        } else {
                            // Gestion des exceptions
                            try {
                                // Instruction de code
                                LOGGER.warn("Fail to parse block state properties {}, expected a string tag for {}, but contents were {}",
                                        // Appelle une méthode
                                        propertiesNBT, property.getKey(), MinestomAdventure.tagStringIO().asString(property.getValue()));
                            // Début d'une méthode/d'un bloc
                            } catch (IOException e) {
                                // Appelle une méthode
                                LOGGER.warn("Fail to parse block state properties {}, expected a string tag for {}, but contents were a {} tag", propertiesNBT, property.getKey(), property.getValue().examinableName());
                            // Fin d'un bloc/d'une expression
                            }
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                    // Appelle une méthode
                    block = block.withProperties(properties);
                // Fin d'un bloc/d'une expression
                }

                // Appelle une méthode
                convertedPalette[i] = block.stateId();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return convertedPalette;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private int[] loadBiomePalette(ListBinaryTag paletteTag) {
        // Appelle une méthode
        final int length = paletteTag.size();
        // Affecte une valeur
        int[] convertedPalette = new int[length];
        // Boucle : répète un bloc
        for (int i = 0; i < length; i++) {
            // Appelle une méthode
            final String name = paletteTag.getString(i);
            // Appelle une méthode
            int biomeId = BIOME_REGISTRY.getId(RegistryKey.unsafeOf(name));
            // Embranchement : vérifie une condition
            if (biomeId == -1) biomeId = PLAINS_ID;
            // Affecte une valeur
            convertedPalette[i] = biomeId;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return convertedPalette;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void loadBlockEntities(Chunk loadedChunk, CompoundBinaryTag chunkData) {
        // Boucle : répète un bloc
        for (BinaryTag blockEntityTag : chunkData.getList("block_entities", BinaryTagTypes.COMPOUND)) {
            // Embranchement : vérifie une condition
            if (!(blockEntityTag instanceof CompoundBinaryTag blockEntity)) {
                // Appelle une méthode
                LOGGER.warn("Invalid block entity tag in chunk data: {}", blockEntityTag);
                // Passe à l'itération suivante de la boucle
                continue;
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            final int x = blockEntity.getInt("x"), y = blockEntity.getInt("y"), z = blockEntity.getInt("z");
            // Appelle une méthode
            final int localX = globalToSectionRelative(x), localY = globalToSectionRelative(y), localZ = globalToSectionRelative(z);
            // Appelle une méthode
            Section section = loadedChunk.getSectionAt(y);
            // Appelle une méthode
            final int stateId = section.blockPalette().get(localX, localY, localZ);
            // Appelle une méthode
            Block block = Block.fromStateId(stateId);
            // Instruction de code
            assert block != null;
            // Load the block handler if the id is present
            // Embranchement : vérifie une condition
            if (blockEntity.get("id") instanceof StringBinaryTag blockEntityId) {
                // Appelle une méthode
                final BlockHandler handler = MinecraftServer.getBlockManager().getHandlerOrDummy(blockEntityId.value());
                // Appelle une méthode
                block = block.withHandler(handler);
            // Fin d'un bloc/d'une expression
            }
            // Remove anvil tags
            // Affecte une valeur
            CompoundBinaryTag trimmedTag = CompoundBinaryTag.builder()
                    // Instruction de code
                    .put(blockEntity)
                    // Instruction de code
                    .remove("id").remove("keepPacked")
                    // Instruction de code
                    .remove("x").remove("y").remove("z")
                    // Appelle une méthode
                    .build();

            // Place block
            // Appelle une méthode
            final Block finalBlock = !trimmedTag.isEmpty() ? block.withNbt(trimmedTag) : block;
            // Appelle une méthode
            loadedChunk.setBlock(x, y, z, finalBlock);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void saveInstance(Instance instance) {
        // Appelle une méthode
        final CompoundBinaryTag nbt = instance.tagHandler().asCompound();
        // Embranchement : vérifie une condition
        if (nbt.isEmpty()) {
            // Instance has no data
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Gestion des exceptions
        try (OutputStream os = Files.newOutputStream(levelPath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            // Appelle une méthode
            BinaryTagIO.writer().writeNamed(Map.entry("", nbt), os, BinaryTagIO.Compression.GZIP);
        // Début d'une méthode/d'un bloc
        } catch (IOException e) {
            // Appelle une méthode
            MinecraftServer.getExceptionManager().handleException(e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void saveChunk(Chunk chunk) {
        // Appelle une méthode
        final int chunkX = chunk.getChunkX(), chunkZ = chunk.getChunkZ();
        // Appelle une méthode
        final int regionX = chunkToRegion(chunkX), regionZ = chunkToRegion(chunkZ);
        // Appelle une méthode
        final long chunkIndex = chunkIndex(chunkX, chunkZ);
        // Appelle une méthode
        final long regionIndex = regionIndex(regionX, regionZ);

        // Find the region file or create an empty one if missing
        // Instruction de code
        RegionFile mcaFile;
        // Appelle une méthode
        fileCreationLock.lock();
        // Gestion des exceptions
        try {
            // Appelle une méthode
            mcaFile = getMCAFile(chunkX, chunkZ);

            // Embranchement : vérifie une condition
            if (mcaFile == null) {
                // Appelle une méthode
                final String regionFileName = RegionFile.getFileName(regionX, regionZ);
                // Gestion des exceptions
                try {
                    // Appelle une méthode
                    Path regionFile = regionPath.resolve(regionFileName);
                    // Embranchement : vérifie une condition
                    if (!Files.exists(regionFile)) {
                        // Appelle une méthode
                        Files.createDirectories(regionFile.getParent());
                        // Appelle une méthode
                        Files.createFile(regionFile);
                    // Fin d'un bloc/d'une expression
                    }

                    // Appelle une méthode
                    mcaFile = new RegionFile(regionFile);
                    // Appelle une méthode
                    alreadyLoaded.put(regionFileName, mcaFile);
                // Début d'une méthode/d'un bloc
                } catch (IOException e) {
                    // Appelle une méthode
                    LOGGER.error("Failed to create region file for {}, {}", chunkX, chunkZ, e);
                    // Appelle une méthode
                    MinecraftServer.getExceptionManager().handleException(e);
                    // Renvoie une valeur à l'appelant
                    return;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Début d'une méthode/d'un bloc
        } finally {
            // Appelle une méthode
            fileCreationLock.unlock();

            // Accès à l'objet courant/parent
            this.perRegionLoadedChunksLock.lock();
            // Gestion des exceptions
            try {
                // Accès à l'objet courant/parent
                this.perRegionLoadedChunks.computeIfAbsent(regionIndex, k -> new LongOpenHashSet())
                        // Appelle une méthode
                        .add(chunkIndex);
            // Début d'une méthode/d'un bloc
            } finally {
                // Accès à l'objet courant/parent
                this.perRegionLoadedChunksLock.unlock();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Gestion des exceptions
        try {
            // Appelle une méthode
            final CompoundBinaryTag.Builder chunkData = CompoundBinaryTag.builder();

            // Appelle une méthode
            chunkData.put(chunk.tagHandler().asCompound());

            // Appelle une méthode
            chunkData.putInt("DataVersion", MinecraftServer.DATA_VERSION);
            // Appelle une méthode
            chunkData.putInt("xPos", chunkX);
            // Appelle une méthode
            chunkData.putInt("zPos", chunkZ);
            // Appelle une méthode
            chunkData.putInt("yPos", chunk.getMinSection());
            // Appelle une méthode
            chunkData.putString("status", "minecraft:full");
            // Appelle une méthode
            chunkData.putLong("LastUpdate", chunk.getInstance().getWorldAge());

            // Appelle une méthode
            saveSectionData(chunk, chunkData);

            // Appelle une méthode
            mcaFile.writeChunkData(chunkX, chunkZ, chunkData.build());
        // Début d'une méthode/d'un bloc
        } catch (IOException e) {
            // Appelle une méthode
            LOGGER.error("Failed to save chunk {}, {}", chunkX, chunkZ, e);
            // Appelle une méthode
            MinecraftServer.getExceptionManager().handleException(e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void saveSectionData(Chunk chunk, CompoundBinaryTag.Builder chunkData) {
        // Appelle une méthode
        final ListBinaryTag.Builder<CompoundBinaryTag> sections = ListBinaryTag.builder(BinaryTagTypes.COMPOUND);
        // Appelle une méthode
        final ListBinaryTag.Builder<CompoundBinaryTag> blockEntities = ListBinaryTag.builder(BinaryTagTypes.COMPOUND);

        // Block & Biome arrays reused for each chunk
        // Affecte une valeur
        List<BinaryTag> biomePalette = new ArrayList<>();
        // Affecte une valeur
        int[] biomeIndices = new int[64];

        // Affecte une valeur
        List<BinaryTag> blockPaletteEntries = new ArrayList<>();
        // Affecte une valeur
        IntList blockPaletteIndices = new IntArrayList(); // Map block indices by state id to avoid doing a deep comparison on every block tag
        // Affecte une valeur
        int[] blockIndices = new int[SECTION_BLOCK_COUNT];

        // Début d'une méthode/d'un bloc
        synchronized (chunk) {
            // Boucle : répète un bloc
            for (int sectionY = chunk.getMinSection(); sectionY < chunk.getMaxSection(); sectionY++) {
                // Appelle une méthode
                final Section section = chunk.getSection(sectionY);

                // Appelle une méthode
                final CompoundBinaryTag.Builder sectionData = CompoundBinaryTag.builder();
                // Appelle une méthode
                sectionData.putByte("Y", (byte) sectionY);

                // Lighting
                // Appelle une méthode
                byte[] skyLight = section.skyLight().array();
                // Embranchement : vérifie une condition
                if (skyLight != null && skyLight.length > 0) sectionData.putByteArray("SkyLight", skyLight);
                // Appelle une méthode
                byte[] blockLight = section.blockLight().array();
                // Embranchement : vérifie une condition
                if (blockLight != null && blockLight.length > 0) sectionData.putByteArray("BlockLight", blockLight);

                // Affecte une valeur
                final int globalSectionY = sectionY * 16;
                // Retrieve block data
                // Embranchement : vérifie une condition
                if (section.blockPalette().singleValue() != -1) {
                    // Appelle une méthode
                    final Block block = Block.fromStateId(section.blockPalette().singleValue());
                    // Instruction de code
                    assert block != null;
                    // Appelle une méthode
                    final CompoundBinaryTag blockState = blockStateNbt(block);
                    // Appelle une méthode
                    blockPaletteEntries.add(blockState);
                // Branche alternative de la condition
                } else {
                    // Début d'une méthode/d'un bloc
                    section.blockPalette().getAll((x, y, z, value) -> {
                        // Appelle une méthode
                        Block block = chunk.getBlock(x, globalSectionY + y, z, Block.Getter.Condition.CACHED);
                        // Embranchement : vérifie une condition
                        if (block == null) block = Block.fromStateId(value);
                        // Instruction de code
                        assert block != null;
                        // Appelle une méthode
                        final CompoundBinaryTag blockState = blockStateNbt(block);
                        // Appelle une méthode
                        int blockPaletteIndex = blockPaletteIndices.indexOf(value);
                        // Embranchement : vérifie une condition
                        if (blockPaletteIndex == -1) {
                            // Appelle une méthode
                            blockPaletteIndex = blockPaletteEntries.size();
                            // Appelle une méthode
                            blockPaletteEntries.add(blockState);
                            // Appelle une méthode
                            blockPaletteIndices.add(value);
                        // Fin d'un bloc/d'une expression
                        }
                        // Affecte une valeur
                        final int blockIndex = x + y * 16 * 16 + z * 16;
                        // Affecte une valeur
                        blockIndices[blockIndex] = blockPaletteIndex;

                        // Add block entity if present
                        // Appelle une méthode
                        final BlockHandler handler = block.handler();
                        // Appelle une méthode
                        final CompoundBinaryTag originalNBT = block.nbt();
                        // Embranchement : vérifie une condition
                        if (originalNBT != null || handler != null) {
                            // Appelle une méthode
                            CompoundBinaryTag.Builder blockEntityTag = CompoundBinaryTag.builder();
                            // Embranchement : vérifie une condition
                            if (originalNBT != null) blockEntityTag.put(originalNBT);
                            // Embranchement : vérifie une condition
                            if (handler != null) blockEntityTag.putString("id", handler.getKey().asString());
                            // Appelle une méthode
                            blockEntityTag.putInt("x", x + CHUNK_SIZE_X * chunk.getChunkX());
                            // Appelle une méthode
                            blockEntityTag.putInt("y", y + globalSectionY);
                            // Appelle une méthode
                            blockEntityTag.putInt("z", z + CHUNK_SIZE_Z * chunk.getChunkZ());
                            // Appelle une méthode
                            blockEntityTag.putByte("keepPacked", (byte) 0);
                            // Appelle une méthode
                            blockEntities.add(blockEntityTag.build());
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    });
                // Fin d'un bloc/d'une expression
                }
                // Retrieve biome data
                // Embranchement : vérifie une condition
                if (section.biomePalette().singleValue() != -1) {
                    // Affecte une valeur
                    final RegistryKey<Biome> biomeKey = MinecraftServer.getBiomeRegistry()
                            // Appelle une méthode
                            .getKey(section.biomePalette().singleValue());
                    // Instruction de code
                    assert biomeKey != null;
                    // Appelle une méthode
                    final BinaryTag biomeName = StringBinaryTag.stringBinaryTag(biomeKey.key().asString());
                    // Appelle une méthode
                    biomePalette.add(biomeName);
                // Branche alternative de la condition
                } else {
                    // Début d'une méthode/d'un bloc
                    section.biomePalette().getAll((x, y, z, value) -> {
                        // Affecte une valeur
                        int biomeIndex = x + y * 4 * 4 + z * 4;
                        // Appelle une méthode
                        final RegistryKey<Biome> biomeKey = MinecraftServer.getBiomeRegistry().getKey(value);
                        // Instruction de code
                        assert biomeKey != null;
                        // Appelle une méthode
                        final BinaryTag biomeName = StringBinaryTag.stringBinaryTag(biomeKey.key().asString());
                        // Appelle une méthode
                        int biomePaletteIndex = biomePalette.indexOf(biomeName);
                        // Embranchement : vérifie une condition
                        if (biomePaletteIndex == -1) {
                            // Appelle une méthode
                            biomePaletteIndex = biomePalette.size();
                            // Appelle une méthode
                            biomePalette.add(biomeName);
                        // Fin d'un bloc/d'une expression
                        }
                        // Affecte une valeur
                        biomeIndices[biomeIndex] = biomePaletteIndex;
                    // Fin d'un bloc/d'une expression
                    });
                // Fin d'un bloc/d'une expression
                }

                // Save the block and biome palettes
                // Appelle une méthode
                final CompoundBinaryTag.Builder blockStates = CompoundBinaryTag.builder();
                // Appelle une méthode
                blockStates.put("palette", ListBinaryTag.listBinaryTag(BinaryTagTypes.COMPOUND, blockPaletteEntries));
                // Embranchement : vérifie une condition
                if (blockPaletteEntries.size() > 1) {
                    // If there is only one entry we do not need to write the packed indices
                    // Appelle une méthode
                    final int bitsPerEntry = Math.max(4, MathUtils.bitsToRepresent(blockPaletteEntries.size() - 1));
                    // Appelle une méthode
                    blockStates.putLongArray("data", Palettes.pack(blockIndices, bitsPerEntry));
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                sectionData.put("block_states", blockStates.build());

                // Appelle une méthode
                final CompoundBinaryTag.Builder biomes = CompoundBinaryTag.builder();
                // Appelle une méthode
                biomes.put("palette", ListBinaryTag.listBinaryTag(BinaryTagTypes.STRING, biomePalette));
                // Embranchement : vérifie une condition
                if (biomePalette.size() > 1) {
                    // If there is only one entry we do not need to write the packed indices
                    // Appelle une méthode
                    final int bitsPerEntry = MathUtils.bitsToRepresent(biomePalette.size() - 1);
                    // Appelle une méthode
                    biomes.putLongArray("data", Palettes.pack(biomeIndices, bitsPerEntry));
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                sectionData.put("biomes", biomes.build());

                // Appelle une méthode
                biomePalette.clear();
                // Appelle une méthode
                blockPaletteEntries.clear();
                // Appelle une méthode
                blockPaletteIndices.clear();

                // Appelle une méthode
                sections.add(sectionData.build());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        chunkData.put("sections", sections.build());
        // Appelle une méthode
        chunkData.put("block_entities", blockEntities.build());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static CompoundBinaryTag blockStateNbt(final Block block) {
        // Appelle une méthode
        final int stateId = block.stateId();
        // Affecte une valeur
        CompoundBinaryTag result = BLOCK_STATE_ID_2_OBJECT_CACHE[stateId];
        // Embranchement : vérifie une condition
        if (result == null) result = BLOCK_STATE_ID_2_OBJECT_CACHE[stateId] = blockStateNbtCompute(block);
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static CompoundBinaryTag blockStateNbtCompute(final Block block) {
        // Appelle une méthode
        final CompoundBinaryTag.Builder tag = CompoundBinaryTag.builder();
        // Appelle une méthode
        tag.putString("Name", block.name());
        // Embranchement : vérifie une condition
        if (!block.properties().isEmpty()) {
            // Appelle une méthode
            final Map<String, String> defaultProperties = block.defaultState().properties();
            // Appelle une méthode
            final CompoundBinaryTag.Builder propertiesTag = CompoundBinaryTag.builder();
            // Boucle : répète un bloc
            for (Map.Entry<String, String> entry : block.properties().entrySet()) {
                // Appelle une méthode
                final String key = entry.getKey(), value = entry.getValue();
                // Embranchement : vérifie une condition
                if (defaultProperties.get(key).equals(value))
                    // Passe à l'itération suivante de la boucle
                    continue; // Skip default values
                // Appelle une méthode
                propertiesTag.putString(key, value);
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            CompoundBinaryTag properties = propertiesTag.build();
            // Embranchement : vérifie une condition
            if (!properties.isEmpty()) tag.put("Properties", properties);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return tag.build();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Unload a given chunk. Also unloads a region when no chunk from that region is loaded.
     *
     * @param chunk the chunk to unload
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void unloadChunk(Chunk chunk) {
        // Appelle une méthode
        final int regionX = chunkToRegion(chunk.getChunkX()), regionZ = chunkToRegion(chunk.getChunkZ());
        // Appelle une méthode
        final long regionIndex = regionIndex(regionX, regionZ);

        // Appelle une méthode
        perRegionLoadedChunksLock.lock();
        // Gestion des exceptions
        try {
            // Appelle une méthode
            LongSet chunks = perRegionLoadedChunks.get(regionIndex);
            // Embranchement : vérifie une condition
            if (chunks != null) { // if null, trying to unload a chunk from a region that was not created by the AnvilLoader
                // don't check return value, trying to unload a chunk not created by the AnvilLoader is valid
                // Appelle une méthode
                final long chunkIndex = chunkIndex(chunk.getChunkX(), chunk.getChunkZ());
                // Appelle une méthode
                chunks.remove(chunkIndex);

                // Embranchement : vérifie une condition
                if (chunks.isEmpty()) {
                    // Appelle une méthode
                    perRegionLoadedChunks.remove(regionIndex);
                    // Appelle une méthode
                    RegionFile regionFile = alreadyLoaded.remove(RegionFile.getFileName(regionX, regionZ));
                    // Embranchement : vérifie une condition
                    if (regionFile != null) {
                        // Gestion des exceptions
                        try {
                            // Appelle une méthode
                            regionFile.close();
                        // Début d'une méthode/d'un bloc
                        } catch (IOException e) {
                            // Appelle une méthode
                            MinecraftServer.getExceptionManager().handleException(e);
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Début d'une méthode/d'un bloc
        } finally {
            // Appelle une méthode
            perRegionLoadedChunksLock.unlock();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean supportsParallelLoading() {
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean supportsParallelSaving() {
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
