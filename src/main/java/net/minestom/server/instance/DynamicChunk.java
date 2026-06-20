// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.LongArrayBinaryTag;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.coordinate.CoordConversion;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockHandler;
// Import of a required class
import net.minestom.server.instance.heightmap.Heightmap;
// Import of a required class
import net.minestom.server.instance.heightmap.MotionBlockingHeightmap;
// Import of a required class
import net.minestom.server.instance.heightmap.WorldSurfaceHeightmap;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.server.CachedPacket;
// Import of a required class
import net.minestom.server.network.packet.server.SendablePacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.ChunkDataPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.UpdateLightPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.data.ChunkData;
// Import of a required class
import net.minestom.server.network.packet.server.play.data.LightData;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.snapshot.ChunkSnapshot;
// Import of a required class
import net.minestom.server.snapshot.SnapshotImpl;
// Import of a required class
import net.minestom.server.snapshot.SnapshotUpdater;
// Import of a required class
import net.minestom.server.utils.ArrayUtils;
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
import java.util.*;

// Static import of a member
import static net.minestom.server.coordinate.CoordConversion.globalToSectionRelative;

/**
 * Represents a {@link Chunk} which store each individual block in memory.
 * <p>
 * WARNING: not thread-safe.
 */
// Type declaration (class/interface/enum/record)
public class DynamicChunk extends Chunk {
    // Calls a method
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicChunk.class);

    // Code statement
    protected final List<Section> sections;

    // Assigns a value
    private volatile boolean needsCompleteHeightmapRefresh = true;

    // Calls a method
    protected Heightmap motionBlocking = new MotionBlockingHeightmap(this);
    // Calls a method
    protected Heightmap worldSurface = new WorldSurfaceHeightmap(this);

    // Key = ChunkUtils#getBlockIndex
    // Calls a method
    protected final Int2ObjectOpenHashMap<Block> entries = new Int2ObjectOpenHashMap<>(0);
    // Calls a method
    protected final Int2ObjectOpenHashMap<Block> tickableMap = new Int2ObjectOpenHashMap<>(0);

    // Calls a method
    final CachedPacket chunkCache = new CachedPacket(this::createChunkPacket);
    // Calls a method
    private static final DynamicRegistry<Biome> BIOME_REGISTRY = MinecraftServer.getBiomeRegistry();

    // Start of a method/block
    public DynamicChunk(Instance instance, int chunkX, int chunkZ) {
        // Access to the current/parent object
        super(instance, chunkX, chunkZ, true);
        // Required to be here because the super call populates the min and max section.
        // Assigns a value
        var sectionsTemp = new Section[maxSection - minSection];
        // Calls a method
        Arrays.setAll(sectionsTemp, value -> new Section());
        // Access to the current/parent object
        this.sections = List.of(sectionsTemp);
    // End of a block/expression
    }

    // Start of a method/block
    protected DynamicChunk(Instance instance, int chunkX, int chunkZ, List<Section> sections) {
        // Access to the current/parent object
        super(instance, chunkX, chunkZ, true);
        // Access to the current/parent object
        this.sections = List.copyOf(sections);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Code statement
    public void setBlock(int x, int y, int z, Block block,
                         // Annotation for the following element
                         @Nullable BlockHandler.Placement placement,
                         // Annotation for the following element
                         @Nullable BlockHandler.Destroy destroy) {
        // Calls a method
        assertWriteLock();
        // Calls a method
        final DimensionType instanceDim = instance.getCachedDimensionType();
        // Branch: checks a condition
        if (y >= instanceDim.maxY() || y < instanceDim.minY()) {
            // Code statement
            LOGGER.warn("tried to set a block outside the world bounds, should be within [{}, {}): {}",
                    // Calls a method
                    instanceDim.minY(), instanceDim.maxY(), y);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Access to the current/parent object
        this.chunkCache.invalidate();

        // Calls a method
        Section section = getSectionAt(y);

        // Calls a method
        int sectionRelativeX = globalToSectionRelative(x);
        // Calls a method
        int sectionRelativeZ = globalToSectionRelative(z);

        // Code statement
        section.blockPalette().set(
                // Code statement
                sectionRelativeX,
                // Code statement
                globalToSectionRelative(y),
                // Code statement
                sectionRelativeZ,
                // Code statement
                block.stateId()
        // End of a block/expression
        );

        // Calls a method
        final int index = CoordConversion.chunkBlockIndex(x, y, z);
        // Handler
        // Calls a method
        final BlockHandler handler = block.handler();
        // Code statement
        final Block lastCachedBlock;
        // Branch: checks a condition
        if (handler != null || block.hasNbt() || block.registry().isBlockEntity()) {
            // Calls a method
            lastCachedBlock = this.entries.put(index, block);
        // Alternative branch of the condition
        } else {
            // Calls a method
            lastCachedBlock = this.entries.remove(index);
        // End of a block/expression
        }
        // Block tick
        // Branch: checks a condition
        if (handler != null && handler.isTickable()) {
            // Access to the current/parent object
            this.tickableMap.put(index, block);
        // Alternative branch of the condition
        } else {
            // Access to the current/parent object
            this.tickableMap.remove(index);
        // End of a block/expression
        }

        // Update block handlers
        // Branch: checks a condition
        if (lastCachedBlock != null && lastCachedBlock.handler() != null) {
            // Previous destroy
            // Code statement
            lastCachedBlock.handler().onDestroy(Objects.requireNonNullElseGet(destroy,
                    // Calls a method
                    () -> new BlockHandler.Destroy(lastCachedBlock, block, instance, CoordConversion.chunkBlockRelativeGetGlobal(sectionRelativeX, y, sectionRelativeZ, chunkX, chunkZ))));
        // End of a block/expression
        }
        // Branch: checks a condition
        if (handler != null) {
            // New placement
            // Assigns a value
            final Block finalBlock = block;
            // Calls a method
            final Point placePoint = CoordConversion.chunkBlockRelativeGetGlobal(sectionRelativeX, y, sectionRelativeZ, chunkX, chunkZ);
            // Code statement
            handler.onPlace(Objects.requireNonNullElseGet(placement,
                    // Calls a method
                    () -> new BlockHandler.Placement(finalBlock, Objects.requireNonNullElseGet(lastCachedBlock, () -> this.getBlock(placePoint, Condition.TYPE)), instance, placePoint)));
        // End of a block/expression
        }

        // UpdateHeightMaps
        // Branch: checks a condition
        if (needsCompleteHeightmapRefresh) calculateFullHeightmap();
        // Calls a method
        motionBlocking.refresh(sectionRelativeX, y, sectionRelativeZ, block);
        // Calls a method
        worldSurface.refresh(sectionRelativeX, y, sectionRelativeZ, block);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setBiome(int x, int y, int z, RegistryKey<Biome> biome) {
        // Calls a method
        assertWriteLock();
        // Access to the current/parent object
        this.chunkCache.invalidate();
        // Calls a method
        Section section = getSectionAt(y);

        // Calls a method
        var id = BIOME_REGISTRY.getId(biome);
        // Branch: checks a condition
        if (id == -1) throw new IllegalStateException("Biome has not been registered: " + biome.key());

        // Code statement
        section.biomePalette().set(
                // Code statement
                globalToSectionRelative(x) / 4,
                // Code statement
                globalToSectionRelative(y) / 4,
                // Calls a method
                globalToSectionRelative(z) / 4, id);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public List<Section> getSections() {
        // Returns a value to the caller
        return sections;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Section getSection(int section) {
        // Returns a value to the caller
        return sections.get(section - minSection);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Heightmap motionBlockingHeightmap() {
        // Returns a value to the caller
        return motionBlocking;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Heightmap worldSurfaceHeightmap() {
        // Returns a value to the caller
        return worldSurface;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void loadHeightmapsFromNBT(CompoundBinaryTag heightmapsNBT) {
        // Calls a method
        assertWriteLock();
        // Branch: checks a condition
        if (heightmapsNBT.get(motionBlockingHeightmap().type().name()) instanceof LongArrayBinaryTag array) {
            // Calls a method
            motionBlockingHeightmap().loadFrom(array.value());
        // End of a block/expression
        }

        // Branch: checks a condition
        if (heightmapsNBT.get(worldSurfaceHeightmap().type().name()) instanceof LongArrayBinaryTag array) {
            // Calls a method
            worldSurfaceHeightmap().loadFrom(array.value());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void tick(long time) {
        // Branch: checks a condition
        if (tickableMap.isEmpty()) return;
        // Start of a method/block
        tickableMap.int2ObjectEntrySet().fastForEach(entry -> {
            // Calls a method
            final int index = entry.getIntKey();
            // Calls a method
            final Block block = entry.getValue();
            // Calls a method
            final BlockHandler handler = block.handler();
            // Branch: checks a condition
            if (handler == null) return;
            // Calls a method
            final Point blockPosition = CoordConversion.chunkBlockIndexGetGlobal(index, chunkX, chunkZ);
            // Calls a method
            handler.tick(new BlockHandler.Tick(block, instance, blockPosition));
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable Block getBlock(int x, int y, int z, Condition condition) {
        // Calls a method
        assertReadLock();
        // Branch: checks a condition
        if (y < minSection * CHUNK_SECTION_SIZE || y >= maxSection * CHUNK_SECTION_SIZE)
            // Returns a value to the caller
            return Block.AIR; // Out of bounds

        // Verify if the block object is present
        // Branch: checks a condition
        if (condition != Condition.TYPE) {
            // Assigns a value
            final Block entry = !entries.isEmpty() ?
                    // Calls a method
                    entries.get(CoordConversion.chunkBlockIndex(x, y, z)) : null;
            // Branch: checks a condition
            if (entry != null || condition == Condition.CACHED) {
                // Returns a value to the caller
                return entry;
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Retrieve the block from state id
        // Calls a method
        final Section section = getSectionAt(y);
        // Assigns a value
        final int blockStateId = section.blockPalette()
                // Calls a method
                .get(globalToSectionRelative(x), globalToSectionRelative(y), globalToSectionRelative(z));
        // Returns a value to the caller
        return Objects.requireNonNullElse(Block.fromStateId(blockStateId), Block.AIR);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public RegistryKey<Biome> getBiome(int x, int y, int z) {
        // Calls a method
        assertReadLock();
        // Calls a method
        final Section section = getSectionAt(y);
        // Assigns a value
        final int id = section.biomePalette()
                // Calls a method
                .get(globalToSectionRelative(x) / 4, globalToSectionRelative(y) / 4, globalToSectionRelative(z) / 4);

        // Calls a method
        RegistryKey<Biome> biome = BIOME_REGISTRY.getKey(id);
        // Calls a method
        Check.notNull(biome, "Biome with id {0} is not registered", id);
        // Returns a value to the caller
        return biome;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public SendablePacket getFullDataPacket() {
        // Returns a value to the caller
        return chunkCache;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Chunk copy(Instance instance, int chunkX, int chunkZ) {
        // Calls a method
        assertReadLock();
        // Calls a method
        var sections = this.sections.stream().map(Section::clone).toList();
        // Calls a method
        DynamicChunk dynamicChunk = new DynamicChunk(instance, chunkX, chunkZ, sections);
        // Calls a method
        dynamicChunk.entries.putAll(entries);
        // Returns a value to the caller
        return dynamicChunk;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void reset() {
        // Calls a method
        assertWriteLock();
        // Loop: repeats a block
        for (Section section : sections) section.clear();
        // Access to the current/parent object
        this.entries.clear();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void invalidate() {
        // Access to the current/parent object
        this.needsCompleteHeightmapRefresh = true;
        // Access to the current/parent object
        this.chunkCache.invalidate();
    // End of a block/expression
    }

    // Start of a method/block
    private ChunkDataPacket createChunkPacket() {
        // Code statement
        final Map<Heightmap.Type, long[]> heightmaps;
        // Calls a method
        lockWriteLock();
        // Exception handling
        try {
            // Calls a method
            heightmaps = getHeightmaps();
        // Start of a method/block
        } finally {
            // Calls a method
            unlockWriteLock();
        // End of a block/expression
        }
        // Compute light data outside any locks. This *should* prevent deadlocks
        // Calls a method
        var lightData = createLightData(true);

        // Calls a method
        lockReadLock();
        // Exception handling
        try {
            // Calls a method
            NetworkBuffer.Type<ChunkData.Section> sectionSerializer = ChunkData.Section.networkType(MinecraftServer.getBiomeRegistry().size());
            // Assigns a value
            final byte[] data = NetworkBuffer.makeArray(networkBuffer -> {
                // Loop: repeats a block
                for (Section section : sections) {
                    // Calls a method
                    final short blockCount = (short) section.blockPalette().count();
                    // Assigns a value
                    final short liquidCount = (short) (blockCount > 0 ? 1 : 0); //TODO(26.1) proper fluid count
                    // Calls a method
                    networkBuffer.write(sectionSerializer, new ChunkData.Section(blockCount, liquidCount, section.blockPalette(), section.biomePalette()));
                // End of a block/expression
                }
            // End of a block/expression
            });

            // Returns a value to the caller
            return new ChunkDataPacket(chunkX, chunkZ,
                    // Creates a new object
                    new ChunkData(heightmaps, data, entries),
                    // Code statement
                    lightData
            // End of a block/expression
            );
        // Start of a method/block
        } finally {
            // Calls a method
            unlockReadLock();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    UpdateLightPacket createLightPacket() {
        // Returns a value to the caller
        return new UpdateLightPacket(chunkX, chunkZ, createLightData(false));
    // End of a block/expression
    }

    // Start of a method/block
    protected LightData createLightData(boolean requiredFullChunk) {
        // Calls a method
        BitSet skyMask = new BitSet();
        // Calls a method
        BitSet blockMask = new BitSet();
        // Calls a method
        BitSet emptySkyMask = new BitSet();
        // Calls a method
        BitSet emptyBlockMask = new BitSet();
        // Calls a method
        List<byte[]> skyLights = new ArrayList<>();
        // Calls a method
        List<byte[]> blockLights = new ArrayList<>();

        // Assigns a value
        int index = 0;
        // Loop: repeats a block
        for (Section section : sections) {
            // Code statement
            index++;
            // Calls a method
            final byte[] skyLight = section.skyLight().array();
            // Calls a method
            final byte[] blockLight = section.blockLight().array();
            // Branch: checks a condition
            if (skyLight.length != 0) {
                // Calls a method
                skyLights.add(skyLight);
                // Calls a method
                skyMask.set(index);
            // Alternative branch of the condition
            } else {
                // Calls a method
                emptySkyMask.set(index);
            // End of a block/expression
            }
            // Branch: checks a condition
            if (blockLight.length != 0) {
                // Calls a method
                blockLights.add(blockLight);
                // Calls a method
                blockMask.set(index);
            // Alternative branch of the condition
            } else {
                // Calls a method
                emptyBlockMask.set(index);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return new LightData(
                // Code statement
                skyMask, blockMask,
                // Code statement
                emptySkyMask, emptyBlockMask,
                // Code statement
                skyLights, blockLights
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Start of a method/block
    protected Map<Heightmap.Type, long[]> getHeightmaps() {
        // Calls a method
        assertReadLock();
        // Branch: checks a condition
        if (needsCompleteHeightmapRefresh) calculateFullHeightmap();
        // Returns a value to the caller
        return Map.of(
                // Code statement
                motionBlocking.type(), motionBlocking.getNBT(),
                // Code statement
                worldSurface.type(), worldSurface.getNBT()
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Start of a method/block
    private void calculateFullHeightmap() {
        // Calls a method
        assertWriteLock();
        // Calls a method
        final int startY = Heightmap.getHighestBlockSection(this);
        // Access to the current/parent object
        this.motionBlocking.refresh(startY);
        // Access to the current/parent object
        this.worldSurface.refresh(startY);
        // Access to the current/parent object
        this.needsCompleteHeightmapRefresh = false;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ChunkSnapshot updateSnapshot(SnapshotUpdater updater) {
        // Calls a method
        Section[] clonedSections = new Section[sections.size()];
        // Loop: repeats a block
        for (int i = 0; i < clonedSections.length; i++)
            // Calls a method
            clonedSections[i] = sections.get(i).clone();
        // Calls a method
        var entities = instance.getEntityTracker().chunkEntities(chunkX, chunkZ, EntityTracker.Target.ENTITIES);
        // Calls a method
        final int[] entityIds = ArrayUtils.mapToIntArray(entities, Entity::getEntityId);
        // Returns a value to the caller
        return new SnapshotImpl.Chunk(minSection, chunkX, chunkZ,
                // Code statement
                clonedSections, entries.clone(), entityIds, updater.reference(instance),
                // Calls a method
                tagHandler().readableCopy());
    // End of a block/expression
    }
// End of a block/expression
}
