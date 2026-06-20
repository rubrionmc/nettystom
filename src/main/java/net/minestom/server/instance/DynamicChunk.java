// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.LongArrayBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.CoordConversion;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockHandler;
// Import d'une classe nécessaire
import net.minestom.server.instance.heightmap.Heightmap;
// Import d'une classe nécessaire
import net.minestom.server.instance.heightmap.MotionBlockingHeightmap;
// Import d'une classe nécessaire
import net.minestom.server.instance.heightmap.WorldSurfaceHeightmap;
// Import d'une classe nécessaire
import net.minestom.server.instance.palette.Palette;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.CachedPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.SendablePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.ChunkDataPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.UpdateLightPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.data.ChunkData;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.data.LightData;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.snapshot.ChunkSnapshot;
// Import d'une classe nécessaire
import net.minestom.server.snapshot.SnapshotImpl;
// Import d'une classe nécessaire
import net.minestom.server.snapshot.SnapshotUpdater;
// Import d'une classe nécessaire
import net.minestom.server.utils.ArrayUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import net.minestom.server.world.DimensionType;
// Import d'une classe nécessaire
import net.minestom.server.world.biome.Biome;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.slf4j.Logger;
// Import d'une classe nécessaire
import org.slf4j.LoggerFactory;

// Import d'une classe nécessaire
import java.util.*;

// Import statique d'un membre
import static net.minestom.server.coordinate.CoordConversion.globalToSectionRelative;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.SHORT;

/**
 * Represents a {@link Chunk} which store each individual block in memory.
 * <p>
 * WARNING: not thread-safe.
 */
// Déclaration de type (classe/interface/enum/record)
public class DynamicChunk extends Chunk {
    // Appelle une méthode
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicChunk.class);

    // Instruction de code
    protected final List<Section> sections;

    // Affecte une valeur
    private boolean needsCompleteHeightmapRefresh = true;

    // Appelle une méthode
    protected Heightmap motionBlocking = new MotionBlockingHeightmap(this);
    // Appelle une méthode
    protected Heightmap worldSurface = new WorldSurfaceHeightmap(this);

    // Key = ChunkUtils#getBlockIndex
    // Affecte une valeur
    protected final Int2ObjectOpenHashMap<Block> entries = new Int2ObjectOpenHashMap<>(0);
    // Affecte une valeur
    protected final Int2ObjectOpenHashMap<Block> tickableMap = new Int2ObjectOpenHashMap<>(0);

    // Appelle une méthode
    final CachedPacket chunkCache = new CachedPacket(this::createChunkPacket);
    // Appelle une méthode
    private static final DynamicRegistry<Biome> BIOME_REGISTRY = MinecraftServer.getBiomeRegistry();

    // Début d'une méthode/d'un bloc
    public DynamicChunk(Instance instance, int chunkX, int chunkZ) {
        // Accès à l'objet courant/parent
        super(instance, chunkX, chunkZ, true);
        // Required to be here because the super call populates the min and max section.
        // Affecte une valeur
        var sectionsTemp = new Section[maxSection - minSection];
        // Appelle une méthode
        Arrays.setAll(sectionsTemp, value -> new Section());
        // Accès à l'objet courant/parent
        this.sections = List.of(sectionsTemp);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected DynamicChunk(Instance instance, int chunkX, int chunkZ, List<Section> sections) {
        // Accès à l'objet courant/parent
        super(instance, chunkX, chunkZ, true);
        // Accès à l'objet courant/parent
        this.sections = List.copyOf(sections);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Instruction de code
    public void setBlock(int x, int y, int z, Block block,
                         // Annotation pour l'élément suivant
                         @Nullable BlockHandler.Placement placement,
                         // Annotation pour l'élément suivant
                         @Nullable BlockHandler.Destroy destroy) {
        // Appelle une méthode
        final DimensionType instanceDim = instance.getCachedDimensionType();
        // Embranchement : vérifie une condition
        if (y >= instanceDim.maxY() || y < instanceDim.minY()) {
            // Instruction de code
            LOGGER.warn("tried to set a block outside the world bounds, should be within [{}, {}): {}",
                    // Appelle une méthode
                    instanceDim.minY(), instanceDim.maxY(), y);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertLock();

        // Accès à l'objet courant/parent
        this.chunkCache.invalidate();

        // Appelle une méthode
        Section section = getSectionAt(y);

        // Appelle une méthode
        int sectionRelativeX = globalToSectionRelative(x);
        // Appelle une méthode
        int sectionRelativeZ = globalToSectionRelative(z);

        // Instruction de code
        section.blockPalette().set(
                // Instruction de code
                sectionRelativeX,
                // Instruction de code
                globalToSectionRelative(y),
                // Instruction de code
                sectionRelativeZ,
                // Instruction de code
                block.stateId()
        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        final int index = CoordConversion.chunkBlockIndex(x, y, z);
        // Handler
        // Appelle une méthode
        final BlockHandler handler = block.handler();
        // Instruction de code
        final Block lastCachedBlock;
        // Embranchement : vérifie une condition
        if (handler != null || block.hasNbt() || block.registry().isBlockEntity()) {
            // Appelle une méthode
            lastCachedBlock = this.entries.put(index, block);
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            lastCachedBlock = this.entries.remove(index);
        // Fin d'un bloc/d'une expression
        }
        // Block tick
        // Embranchement : vérifie une condition
        if (handler != null && handler.isTickable()) {
            // Accès à l'objet courant/parent
            this.tickableMap.put(index, block);
        // Branche alternative de la condition
        } else {
            // Accès à l'objet courant/parent
            this.tickableMap.remove(index);
        // Fin d'un bloc/d'une expression
        }

        // Update block handlers
        // Embranchement : vérifie une condition
        if (lastCachedBlock != null && lastCachedBlock.handler() != null) {
            // Previous destroy
            // Instruction de code
            lastCachedBlock.handler().onDestroy(Objects.requireNonNullElseGet(destroy,
                    // Appelle une méthode
                    () -> new BlockHandler.Destroy(lastCachedBlock, block, instance, CoordConversion.chunkBlockRelativeGetGlobal(sectionRelativeX, y, sectionRelativeZ, chunkX, chunkZ))));
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (handler != null) {
            // New placement
            // Affecte une valeur
            final Block finalBlock = block;
            // Appelle une méthode
            final Point placePoint = CoordConversion.chunkBlockRelativeGetGlobal(sectionRelativeX, y, sectionRelativeZ, chunkX, chunkZ);
            // Instruction de code
            handler.onPlace(Objects.requireNonNullElseGet(placement,
                    // Appelle une méthode
                    () -> new BlockHandler.Placement(finalBlock, Objects.requireNonNullElseGet(lastCachedBlock, () -> this.getBlock(placePoint, Condition.TYPE)), instance, placePoint)));
        // Fin d'un bloc/d'une expression
        }

        // UpdateHeightMaps
        // Embranchement : vérifie une condition
        if (needsCompleteHeightmapRefresh) calculateFullHeightmap();
        // Appelle une méthode
        motionBlocking.refresh(sectionRelativeX, y, sectionRelativeZ, block);
        // Appelle une méthode
        worldSurface.refresh(sectionRelativeX, y, sectionRelativeZ, block);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setBiome(int x, int y, int z, RegistryKey<Biome> biome) {
        // Appelle une méthode
        assertLock();
        // Accès à l'objet courant/parent
        this.chunkCache.invalidate();
        // Appelle une méthode
        Section section = getSectionAt(y);

        // Appelle une méthode
        var id = BIOME_REGISTRY.getId(biome);
        // Embranchement : vérifie une condition
        if (id == -1) throw new IllegalStateException("Biome has not been registered: " + biome.key());

        // Instruction de code
        section.biomePalette().set(
                // Instruction de code
                globalToSectionRelative(x) / 4,
                // Instruction de code
                globalToSectionRelative(y) / 4,
                // Appelle une méthode
                globalToSectionRelative(z) / 4, id);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public List<Section> getSections() {
        // Renvoie une valeur à l'appelant
        return sections;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Section getSection(int section) {
        // Renvoie une valeur à l'appelant
        return sections.get(section - minSection);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Heightmap motionBlockingHeightmap() {
        // Renvoie une valeur à l'appelant
        return motionBlocking;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Heightmap worldSurfaceHeightmap() {
        // Renvoie une valeur à l'appelant
        return worldSurface;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void loadHeightmapsFromNBT(CompoundBinaryTag heightmapsNBT) {
        // Embranchement : vérifie une condition
        if (heightmapsNBT.get(motionBlockingHeightmap().type().name()) instanceof LongArrayBinaryTag array) {
            // Appelle une méthode
            motionBlockingHeightmap().loadFrom(array.value());
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (heightmapsNBT.get(worldSurfaceHeightmap().type().name()) instanceof LongArrayBinaryTag array) {
            // Appelle une méthode
            worldSurfaceHeightmap().loadFrom(array.value());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void tick(long time) {
        // Embranchement : vérifie une condition
        if (tickableMap.isEmpty()) return;
        // Début d'une méthode/d'un bloc
        tickableMap.int2ObjectEntrySet().fastForEach(entry -> {
            // Appelle une méthode
            final int index = entry.getIntKey();
            // Appelle une méthode
            final Block block = entry.getValue();
            // Appelle une méthode
            final BlockHandler handler = block.handler();
            // Embranchement : vérifie une condition
            if (handler == null) return;
            // Appelle une méthode
            final Point blockPosition = CoordConversion.chunkBlockIndexGetGlobal(index, chunkX, chunkZ);
            // Appelle une méthode
            handler.tick(new BlockHandler.Tick(block, instance, blockPosition));
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable Block getBlock(int x, int y, int z, Condition condition) {
        // Appelle une méthode
        assertLock();
        // Embranchement : vérifie une condition
        if (y < minSection * CHUNK_SECTION_SIZE || y >= maxSection * CHUNK_SECTION_SIZE)
            // Renvoie une valeur à l'appelant
            return Block.AIR; // Out of bounds

        // Verify if the block object is present
        // Embranchement : vérifie une condition
        if (condition != Condition.TYPE) {
            // Affecte une valeur
            final Block entry = !entries.isEmpty() ?
                    // Appelle une méthode
                    entries.get(CoordConversion.chunkBlockIndex(x, y, z)) : null;
            // Embranchement : vérifie une condition
            if (entry != null || condition == Condition.CACHED) {
                // Renvoie une valeur à l'appelant
                return entry;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Retrieve the block from state id
        // Appelle une méthode
        final Section section = getSectionAt(y);
        // Affecte une valeur
        final int blockStateId = section.blockPalette()
                // Appelle une méthode
                .get(globalToSectionRelative(x), globalToSectionRelative(y), globalToSectionRelative(z));
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNullElse(Block.fromStateId(blockStateId), Block.AIR);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public RegistryKey<Biome> getBiome(int x, int y, int z) {
        // Appelle une méthode
        assertLock();
        // Appelle une méthode
        final Section section = getSectionAt(y);
        // Affecte une valeur
        final int id = section.biomePalette()
                // Appelle une méthode
                .get(globalToSectionRelative(x) / 4, globalToSectionRelative(y) / 4, globalToSectionRelative(z) / 4);

        // Appelle une méthode
        RegistryKey<Biome> biome = BIOME_REGISTRY.getKey(id);
        // Appelle une méthode
        Check.notNull(biome, "Biome with id {0} is not registered", id);
        // Renvoie une valeur à l'appelant
        return biome;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public SendablePacket getFullDataPacket() {
        // Renvoie une valeur à l'appelant
        return chunkCache;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Chunk copy(Instance instance, int chunkX, int chunkZ) {
        // Appelle une méthode
        var sections = this.sections.stream().map(Section::clone).toList();
        // Appelle une méthode
        DynamicChunk dynamicChunk = new DynamicChunk(instance, chunkX, chunkZ, sections);
        // Appelle une méthode
        dynamicChunk.entries.putAll(entries);
        // Renvoie une valeur à l'appelant
        return dynamicChunk;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void reset() {
        // Boucle : répète un bloc
        for (Section section : sections) section.clear();
        // Accès à l'objet courant/parent
        this.entries.clear();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void invalidate() {
        // Accès à l'objet courant/parent
        this.needsCompleteHeightmapRefresh = true;
        // Accès à l'objet courant/parent
        this.chunkCache.invalidate();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private ChunkDataPacket createChunkPacket() {
        // Instruction de code
        final byte[] data;
        // Instruction de code
        final Map<Heightmap.Type, long[]> heightmaps;
        // Début d'une méthode/d'un bloc
        synchronized (this) {
            // Appelle une méthode
            heightmaps = getHeightmaps();

            // Appelle une méthode
            NetworkBuffer.Type<Palette> biomeSerializer = Palette.biomeSerializer(MinecraftServer.getBiomeRegistry().size());
            // Affecte une valeur
            data = NetworkBuffer.makeArray(networkBuffer -> {
                // Boucle : répète un bloc
                for (Section section : sections) {
                    // Appelle une méthode
                    networkBuffer.write(SHORT, (short) section.blockPalette().count());
                    // Appelle une méthode
                    networkBuffer.write(Palette.BLOCK_SERIALIZER, section.blockPalette());
                    // Appelle une méthode
                    networkBuffer.write(biomeSerializer, section.biomePalette());
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return new ChunkDataPacket(chunkX, chunkZ,
                // Crée un nouvel objet
                new ChunkData(heightmaps, data, entries),
                // Instruction de code
                createLightData(true)
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    UpdateLightPacket createLightPacket() {
        // Renvoie une valeur à l'appelant
        return new UpdateLightPacket(chunkX, chunkZ, createLightData(false));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected LightData createLightData(boolean requiredFullChunk) {
        // Appelle une méthode
        BitSet skyMask = new BitSet();
        // Appelle une méthode
        BitSet blockMask = new BitSet();
        // Appelle une méthode
        BitSet emptySkyMask = new BitSet();
        // Appelle une méthode
        BitSet emptyBlockMask = new BitSet();
        // Affecte une valeur
        List<byte[]> skyLights = new ArrayList<>();
        // Affecte une valeur
        List<byte[]> blockLights = new ArrayList<>();

        // Affecte une valeur
        int index = 0;
        // Boucle : répète un bloc
        for (Section section : sections) {
            // Instruction de code
            index++;
            // Appelle une méthode
            final byte[] skyLight = section.skyLight().array();
            // Appelle une méthode
            final byte[] blockLight = section.blockLight().array();
            // Embranchement : vérifie une condition
            if (skyLight.length != 0) {
                // Appelle une méthode
                skyLights.add(skyLight);
                // Appelle une méthode
                skyMask.set(index);
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                emptySkyMask.set(index);
            // Fin d'un bloc/d'une expression
            }
            // Embranchement : vérifie une condition
            if (blockLight.length != 0) {
                // Appelle une méthode
                blockLights.add(blockLight);
                // Appelle une méthode
                blockMask.set(index);
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                emptyBlockMask.set(index);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new LightData(
                // Instruction de code
                skyMask, blockMask,
                // Instruction de code
                emptySkyMask, emptyBlockMask,
                // Instruction de code
                skyLights, blockLights
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected Map<Heightmap.Type, long[]> getHeightmaps() {
        // Embranchement : vérifie une condition
        if (needsCompleteHeightmapRefresh) calculateFullHeightmap();
        // Renvoie une valeur à l'appelant
        return Map.of(
                // Instruction de code
                motionBlocking.type(), motionBlocking.getNBT(),
                // Instruction de code
                worldSurface.type(), worldSurface.getNBT()
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void calculateFullHeightmap() {
        // Appelle une méthode
        final int startY = Heightmap.getHighestBlockSection(this);
        // Accès à l'objet courant/parent
        this.motionBlocking.refresh(startY);
        // Accès à l'objet courant/parent
        this.worldSurface.refresh(startY);
        // Accès à l'objet courant/parent
        this.needsCompleteHeightmapRefresh = false;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ChunkSnapshot updateSnapshot(SnapshotUpdater updater) {
        // Appelle une méthode
        Section[] clonedSections = new Section[sections.size()];
        // Boucle : répète un bloc
        for (int i = 0; i < clonedSections.length; i++)
            // Appelle une méthode
            clonedSections[i] = sections.get(i).clone();
        // Appelle une méthode
        var entities = instance.getEntityTracker().chunkEntities(chunkX, chunkZ, EntityTracker.Target.ENTITIES);
        // Appelle une méthode
        final int[] entityIds = ArrayUtils.mapToIntArray(entities, Entity::getEntityId);
        // Renvoie une valeur à l'appelant
        return new SnapshotImpl.Chunk(minSection, chunkX, chunkZ,
                // Instruction de code
                clonedSections, entries.clone(), entityIds, updater.reference(instance),
                // Appelle une méthode
                tagHandler().readableCopy());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    void assertLock() {
        // Appelle une méthode
        assert Thread.holdsLock(this) : "Chunk must be locked before access";
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
