// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.collision.Shape;
// Import of a required class
import net.minestom.server.coordinate.CoordConversion;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import net.minestom.server.instance.block.BlockHandler;
// Import of a required class
import net.minestom.server.instance.heightmap.Heightmap;
// Import of a required class
import net.minestom.server.instance.light.Light;
// Import of a required class
import net.minestom.server.instance.palette.Palette;
// Import of a required class
import net.minestom.server.network.packet.server.CachedPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.data.LightData;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.CompletableFuture;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;
// Import of a required class
import java.util.concurrent.ExecutorService;
// Import of a required class
import java.util.concurrent.Executors;
// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;
// Import of a required class
import java.util.concurrent.locks.ReentrantLock;

// Static import of a member
import static net.minestom.server.instance.light.LightCompute.EMPTY_CONTENT;

/**
 * A chunk which supports lighting computation.
 * <p>
 * This chunk is used to compute the light data for each block.
 * <p>
 */
// Type declaration (class/interface/enum/record)
public class LightingChunk extends DynamicChunk {

    // Calls a method
    private static final ExecutorService pool = Executors.newWorkStealingPool();

    // Code statement
    private volatile @Nullable OcclusionData occlusionData;
    // Calls a method
    final CachedPacket partialLightCache = new CachedPacket(this::createLightPacket);
    // Code statement
    private @Nullable LightData partialLightData;
    // Code statement
    private @Nullable LightData fullLightData;

    // Assigns a value
    private boolean freezeInvalidation = false;

    // Calls a method
    private final ReentrantLock packetGenerationLock = new ReentrantLock();
    // Calls a method
    private final AtomicInteger resendTimer = new AtomicInteger(-1);
    // Assigns a value
    private final int resendDelay = ServerFlag.SEND_LIGHT_AFTER_BLOCK_PLACEMENT_DELAY;

    // Assigns a value
    private boolean doneInit = false;

    // Type declaration (class/interface/enum/record)
    enum LightType {
        // Code statement
        SKY,
        // Code statement
        BLOCK
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private enum QueueType {
        // Code statement
        INTERNAL,
        // Code statement
        EXTERNAL
    // End of a block/expression
    }

    // Assigns a value
    private static final Set<Key> DIFFUSE_SKY_LIGHT = Set.of(
            // Code statement
            Block.COBWEB.key(),
            // Code statement
            Block.ICE.key(),
            // Code statement
            Block.HONEY_BLOCK.key(),
            // Code statement
            Block.SLIME_BLOCK.key(),
            // Code statement
            Block.WATER.key(),
            // Code statement
            Block.ACACIA_LEAVES.key(),
            // Code statement
            Block.AZALEA_LEAVES.key(),
            // Code statement
            Block.BIRCH_LEAVES.key(),
            // Code statement
            Block.DARK_OAK_LEAVES.key(),
            // Code statement
            Block.FLOWERING_AZALEA_LEAVES.key(),
            // Code statement
            Block.JUNGLE_LEAVES.key(),
            // Code statement
            Block.CHERRY_LEAVES.key(),
            // Code statement
            Block.OAK_LEAVES.key(),
            // Code statement
            Block.SPRUCE_LEAVES.key(),
            // Code statement
            Block.SPAWNER.key(),
            // Code statement
            Block.BEACON.key(),
            // Code statement
            Block.END_GATEWAY.key(),
            // Code statement
            Block.CHORUS_PLANT.key(),
            // Code statement
            Block.CHORUS_FLOWER.key(),
            // Code statement
            Block.FROSTED_ICE.key(),
            // Code statement
            Block.SEAGRASS.key(),
            // Code statement
            Block.TALL_SEAGRASS.key(),
            // Code statement
            Block.LAVA.key()
    // End of a block/expression
    );

    // Start of a method/block
    public void invalidate() {
        // Access to the current/parent object
        this.partialLightCache.invalidate();
        // Access to the current/parent object
        this.chunkCache.invalidate();
        // Access to the current/parent object
        this.partialLightData = null;
        // Access to the current/parent object
        this.fullLightData = null;
    // End of a block/expression
    }

    // Start of a method/block
    public LightingChunk(Instance instance, int chunkX, int chunkZ) {
        // Access to the current/parent object
        super(instance, chunkX, chunkZ);
    // End of a block/expression
    }

    // Start of a method/block
    protected LightingChunk(Instance instance, int chunkX, int chunkZ, List<Section> sections) {
        // Access to the current/parent object
        super(instance, chunkX, chunkZ, sections);
    // End of a block/expression
    }

    // Start of a method/block
    private boolean checkSkyOcclusion(Block block) {
        // Branch: checks a condition
        if (block == Block.AIR) return false;
        // Branch: checks a condition
        if (DIFFUSE_SKY_LIGHT.contains(block.key())) return true;

        // Calls a method
        Shape shape = block.registry().occlusionShape();
        // Calls a method
        boolean occludesTop = Block.AIR.registry().occlusionShape().isOccluded(shape, BlockFace.TOP);
        // Calls a method
        boolean occludesBottom = Block.AIR.registry().occlusionShape().isOccluded(shape, BlockFace.BOTTOM);

        // Returns a value to the caller
        return occludesBottom || occludesTop;
    // End of a block/expression
    }

    // Start of a method/block
    public void setFreezeInvalidation(boolean freezeInvalidation) {
        // Access to the current/parent object
        this.freezeInvalidation = freezeInvalidation;
    // End of a block/expression
    }

    // Start of a method/block
    public void invalidateNeighborsSection(int coordinate) {
        // Branch: checks a condition
        if (freezeInvalidation) {
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Loop: repeats a block
        for (int i = -1; i <= 1; i++) {
            // Loop: repeats a block
            for (int j = -1; j <= 1; j++) {
                // Calls a method
                Chunk neighborChunk = instance.getChunk(chunkX + i, chunkZ + j);
                // Branch: checks a condition
                if (neighborChunk == null) continue;

                // Branch: checks a condition
                if (neighborChunk instanceof LightingChunk light) {
                    // Calls a method
                    light.invalidate();
                // End of a block/expression
                }

                // Loop: repeats a block
                for (int k = -1; k <= 1; k++) {
                    // Branch: checks a condition
                    if (k + coordinate < neighborChunk.getMinSection() || k + coordinate >= neighborChunk.getMaxSection())
                        // Continues to the next loop iteration
                        continue;
                    // Calls a method
                    neighborChunk.getSection(k + coordinate).invalidate();
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public void invalidateResendDelay() {
        // Branch: checks a condition
        if (!doneInit || freezeInvalidation) {
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Loop: repeats a block
        for (int i = -1; i <= 1; i++) {
            // Loop: repeats a block
            for (int j = -1; j <= 1; j++) {
                // Calls a method
                Chunk neighborChunk = instance.getChunk(chunkX + i, chunkZ + j);
                // Branch: checks a condition
                if (neighborChunk instanceof LightingChunk light) {
                    // Calls a method
                    light.resendTimer.set(resendDelay);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
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
        // Access to the current/parent object
        super.setBlock(x, y, z, block, placement, destroy);
        // Access to the current/parent object
        this.occlusionData = null;

        // Invalidate neighbor chunks, since they can be updated by this block change
        // Calls a method
        int coordinate = CoordConversion.globalToChunk(y);
        // Branch: checks a condition
        if (doneInit && !freezeInvalidation) {
            // Calls a method
            invalidateNeighborsSection(coordinate);
            // Calls a method
            invalidateResendDelay();
            // Access to the current/parent object
            this.partialLightCache.invalidate();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public void sendLighting() {
        // Branch: checks a condition
        if (!isLoaded()) return;
        // Calls a method
        sendPacketToViewers(partialLightCache);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected void onLoad() {
        // Assigns a value
        doneInit = true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void onGenerate() {
        // Access to the current/parent object
        super.onGenerate();

        // Loop: repeats a block
        for (int section = minSection; section < maxSection; section++) {
            // Calls a method
            getSection(section).invalidate();
        // End of a block/expression
        }

        // Calls a method
        invalidate();

        // Loop: repeats a block
        for (int i = -1; i <= 1; i++) {
            // Loop: repeats a block
            for (int j = -1; j <= 1; j++) {
                // Calls a method
                Chunk neighborChunk = instance.getChunk(chunkX + i, chunkZ + j);
                // Branch: checks a condition
                if (neighborChunk == null) continue;

                // Branch: checks a condition
                if (neighborChunk instanceof LightingChunk light) {
                    // Branch: checks a condition
                    if (light.doneInit) {
                        // Calls a method
                        light.resendTimer.set(20);
                        // Calls a method
                        light.invalidate();

                        // Loop: repeats a block
                        for (int section = minSection; section < maxSection; section++) {
                            // Calls a method
                            light.getSection(section).invalidate();
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    protected OcclusionData getOcclusionData() {
        // Calls a method
        assertReadLock();
        // Assigns a value
        final OcclusionData currentOcclusionData = this.occlusionData;
        // Branch: checks a condition
        if (currentOcclusionData != null) return currentOcclusionData;

        // Assigns a value
        final int[] occlusionMap = new int[CHUNK_SIZE_X * CHUNK_SIZE_Z];

        // Calls a method
        int minY = instance.getCachedDimensionType().minY();
        // Assigns a value
        int highestBlock = minY - 1;

        // Calls a method
        int startY = Heightmap.getHighestBlockSection(this);

        // Loop: repeats a block
        for (int x = 0; x < CHUNK_SIZE_X; x++) {
            // Loop: repeats a block
            for (int z = 0; z < CHUNK_SIZE_Z; z++) {
                // Assigns a value
                int height = startY;
                // Loop: repeats a block
                while (height >= minY) {
                    // Calls a method
                    Block block = getBlock(x, height, z, Condition.TYPE);
                    // Branch: checks a condition
                    if (block != Block.AIR) highestBlock = Math.max(highestBlock, height);
                    // Branch: checks a condition
                    if (checkSkyOcclusion(block)) break;
                    // Code statement
                    height--;
                // End of a block/expression
                }
                // Calls a method
                occlusionMap[z << 4 | x] = (height + 1);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        var occlusionData = new OcclusionData(highestBlock, occlusionMap);
        // While we only assert that we are in a read-lock, this should still be OK because even if two threads concurrently
        // compute the occlusionData, then both results will be the same, and the differing OcclusionData identites are of no concern.
        // Access to the current/parent object
        this.occlusionData = occlusionData;
        // Returns a value to the caller
        return occlusionData;
    // End of a block/expression
    }

    // Lazy compute occlusion map
    // Start of a method/block
    public int[] getOcclusionMap() {
        // Returns a value to the caller
        return getOcclusionData().occlusionMap();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected LightData createLightData(boolean requiredFullChunk) {
        // Calls a method
        packetGenerationLock.lock();
        // Exception handling
        try {
            // Branch: checks a condition
            if (requiredFullChunk) {
                // Branch: checks a condition
                if (fullLightData != null) {
                    // Returns a value to the caller
                    return fullLightData;
                // End of a block/expression
                }
            // Alternative branch of the condition
            } else {
                // Branch: checks a condition
                if (partialLightData != null) {
                    // Returns a value to the caller
                    return partialLightData;
                // End of a block/expression
                }
            // End of a block/expression
            }

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

            // Calls a method
            int chunkMin = instance.getCachedDimensionType().minY();
            // Calls a method
            int highestNeighborBlock = instance.getCachedDimensionType().minY();
            // Loop: repeats a block
            for (int i = -1; i <= 1; i++) {
                // Loop: repeats a block
                for (int j = -1; j <= 1; j++) {
                    // Calls a method
                    Chunk neighborChunk = instance.getChunk(chunkX + i, chunkZ + j);
                    // Branch: checks a condition
                    if (neighborChunk == null) continue;

                    // Branch: checks a condition
                    if (neighborChunk instanceof LightingChunk light) {
                        // Calls a method
                        light.lockReadLock();
                        // Exception handling
                        try {
                            // Calls a method
                            highestNeighborBlock = Math.max(highestNeighborBlock, light.getOcclusionData().highestBlock);
                        // Start of a method/block
                        } finally {
                            // Calls a method
                            light.unlockReadLock();
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }

            // Assigns a value
            int index = 0;
            // Loop: repeats a block
            for (Section section : sections) {
                // Assigns a value
                boolean wasUpdatedBlock = false;
                // Assigns a value
                boolean wasUpdatedSky = false;

                // Branch: checks a condition
                if (section.blockLight().requiresUpdate()) {
                    // Calls a method
                    relightSection(instance, this.chunkX, index + minSection, chunkZ, LightType.BLOCK);
                    // Assigns a value
                    wasUpdatedBlock = true;
                // Branch: checks a condition
                } else if (requiredFullChunk || section.blockLight().requiresSend()) {
                    // Assigns a value
                    wasUpdatedBlock = true;
                // End of a block/expression
                }

                // Branch: checks a condition
                if (section.skyLight().requiresUpdate()) {
                    // Calls a method
                    relightSection(instance, this.chunkX, index + minSection, chunkZ, LightType.SKY);
                    // Assigns a value
                    wasUpdatedSky = true;
                // Branch: checks a condition
                } else if (requiredFullChunk || section.skyLight().requiresSend()) {
                    // Assigns a value
                    wasUpdatedSky = true;
                // End of a block/expression
                }

                // Assigns a value
                final int sectionMinY = index * 16 + chunkMin;
                // Code statement
                index++;

                // Branch: checks a condition
                if ((wasUpdatedSky) && this.instance.getCachedDimensionType().hasSkylight() && sectionMinY <= (highestNeighborBlock + 16)) {
                    // Calls a method
                    final byte[] skyLight = section.skyLight().array();

                    // Branch: checks a condition
                    if (skyLight.length != 0 && skyLight != EMPTY_CONTENT) {
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
                // End of a block/expression
                }

                // Branch: checks a condition
                if (wasUpdatedBlock) {
                    // Calls a method
                    final byte[] blockLight = section.blockLight().array();

                    // Branch: checks a condition
                    if (blockLight.length != 0 && blockLight != EMPTY_CONTENT) {
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
            // End of a block/expression
            }

            // Assigns a value
            LightData lightData = new LightData(skyMask, blockMask,
                    // Code statement
                    emptySkyMask, emptyBlockMask,
                    // Code statement
                    skyLights, blockLights);

            // Branch: checks a condition
            if (requiredFullChunk) {
                // Access to the current/parent object
                this.fullLightData = lightData;
            // Alternative branch of the condition
            } else {
                // Access to the current/parent object
                this.partialLightData = lightData;
            // End of a block/expression
            }


            // Returns a value to the caller
            return lightData;
        // Start of a method/block
        } finally {
            // Calls a method
            packetGenerationLock.unlock();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void tick(long time) {
        // Access to the current/parent object
        super.tick(time);

        // Branch: checks a condition
        if (doneInit && resendTimer.get() > 0) {
            // Branch: checks a condition
            if (resendTimer.decrementAndGet() == 0) {
                // Calls a method
                sendLighting();
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static Set<Chunk> flushQueue(Instance instance, Set<Point> queue, LightType type, QueueType queueType) {
        // Calls a method
        Set<Light> sections = ConcurrentHashMap.newKeySet();
        // Calls a method
        Set<Point> newQueue = ConcurrentHashMap.newKeySet();

        // Calls a method
        Set<Chunk> responseChunks = ConcurrentHashMap.newKeySet();
        // Calls a method
        List<CompletableFuture<Void>> tasks = new ArrayList<>();

        // Assigns a value
        Light.LightLookup lightLookup = (x, y, z) -> {
            // Calls a method
            Chunk chunk = instance.getChunk(x, z);
            // Branch: checks a condition
            if (chunk == null) return null;
            // Branch: checks a condition
            if (!(chunk instanceof LightingChunk lighting)) return null;
            // Branch: checks a condition
            if (y - lighting.getMinSection() < 0 || y - lighting.getMaxSection() >= 0) return null;
            // Calls a method
            final Section section = lighting.getSection(y);
            // Returns a value to the caller
            return switch (type) {
                // Multiple branching (switch/case)
                case BLOCK -> section.blockLight();
                // Multiple branching (switch/case)
                case SKY -> section.skyLight();
            // End of a block/expression
            };
        // End of a block/expression
        };

        // Assigns a value
        Light.PaletteLookup paletteLookup = (x, y, z) -> {
            // Calls a method
            Chunk chunk = instance.getChunk(x, z);
            // Branch: checks a condition
            if (chunk == null) return null;
            // Branch: checks a condition
            if (!(chunk instanceof LightingChunk lighting)) return null;
            // Branch: checks a condition
            if (y - lighting.getMinSection() < 0 || y - lighting.getMaxSection() >= 0) return null;
            // Returns a value to the caller
            return chunk.getSection(y).blockPalette();
        // End of a block/expression
        };

        // Loop: repeats a block
        for (Point point : queue) {
            // Calls a method
            Chunk chunk = instance.getChunk(point.blockX(), point.blockZ());
            // Branch: checks a condition
            if (!(chunk instanceof LightingChunk lightingChunk)) continue;

            // Calls a method
            Section section = chunk.getSection(point.blockY());
            // Calls a method
            responseChunks.add(chunk);

            // Assigns a value
            Light light = switch (type) {
                // Multiple branching (switch/case)
                case BLOCK -> section.blockLight();
                // Multiple branching (switch/case)
                case SKY -> section.skyLight();
            // End of a block/expression
            };

            // Calls a method
            final Palette blockPalette = section.blockPalette();
            // Assigns a value
            CompletableFuture<Void> task = CompletableFuture.runAsync(() -> {
                // Exception handling
                try {
                    // Code statement
                    final Set<Point> toAdd;
                    // Calls a method
                    lightingChunk.lockReadLock();
                    // Exception handling
                    try {
                        // Assigns a value
                        toAdd = switch (queueType) {
                            // Multiple branching (switch/case)
                            case INTERNAL -> light.calculateInternal(blockPalette,
                                    // Code statement
                                    chunk.getChunkX(), point.blockY(), chunk.getChunkZ(),
                                    // Code statement
                                    lightingChunk.getOcclusionMap(), chunk.instance.getCachedDimensionType().maxY(),
                                    // Code statement
                                    lightLookup);
                            // Multiple branching (switch/case)
                            case EXTERNAL -> light.calculateExternal(blockPalette,
                                    // Code statement
                                    Light.getNeighbors(chunk, point.blockY()),
                                    // Code statement
                                    lightLookup, paletteLookup);
                        // End of a block/expression
                        };
                    // Start of a method/block
                    } finally {
                        // Calls a method
                        lightingChunk.unlockReadLock();
                    // End of a block/expression
                    }

                    // Calls a method
                    sections.add(light);

                    // Calls a method
                    light.flip();
                    // Calls a method
                    newQueue.addAll(toAdd);
                // Start of a method/block
                } catch (Exception e) {
                    // Calls a method
                    MinecraftServer.getExceptionManager().handleException(e);
                // End of a block/expression
                }
            // Code statement
            }, pool);

            // Calls a method
            tasks.add(task);
        // End of a block/expression
        }

        // Calls a method
        tasks.forEach(CompletableFuture::join);

        // Branch: checks a condition
        if (!newQueue.isEmpty()) {
            // Calls a method
            var newResponse = flushQueue(instance, newQueue, type, QueueType.EXTERNAL);
            // Calls a method
            responseChunks.addAll(newResponse);
        // End of a block/expression
        }

        // Returns a value to the caller
        return responseChunks;
    // End of a block/expression
    }

    /**
     * Forces a relight of the specified chunks.
     * <p>
     * This method is used to force a relight of the specified chunks.
     * <p>
     * This method is thread-safe and can be called from any thread.
     *
     * @param instance the instance
     * @param chunks   the chunks to relight
     * @return the chunks which have been relighted
     */
    // Start of a method/block
    public static List<Chunk> relight(Instance instance, Collection<Chunk> chunks) {
        // Calls a method
        Set<Point> sections = new HashSet<>();

        // Start of a method/block
        synchronized (instance) {
            // Loop: repeats a block
            for (Chunk chunk : chunks) {
                // Branch: checks a condition
                if (!(chunk instanceof LightingChunk lighting)) continue;
                // Loop: repeats a block
                for (int sectionIndex = chunk.minSection; sectionIndex < chunk.maxSection; sectionIndex++) {
                    // Calls a method
                    Section section = chunk.getSection(sectionIndex);
                    // Calls a method
                    section.invalidate();
                    // Calls a method
                    sections.add(new Vec(chunk.getChunkX(), sectionIndex, chunk.getChunkZ()));
                // End of a block/expression
                }
                // Calls a method
                lighting.invalidate();
            // End of a block/expression
            }

            // Expand the sections to include nearby sections
            // Calls a method
            var blockSections = new HashSet<Point>();
            // Loop: repeats a block
            for (Point point : sections) {
                // Calls a method
                blockSections.addAll(getNearbyRequired(instance, point, LightType.BLOCK));
            // End of a block/expression
            }

            // Calls a method
            var skySections = new HashSet<Point>();
            // Loop: repeats a block
            for (Point point : sections) {
                // Calls a method
                skySections.addAll(getNearbyRequired(instance, point, LightType.SKY));
            // End of a block/expression
            }

            // Calls a method
            relight(instance, blockSections, LightType.BLOCK);
            // Calls a method
            relight(instance, skySections, LightType.SKY);

            // Calls a method
            var chunksToRelight = new HashSet<Chunk>();
            // Loop: repeats a block
            for (Point point : blockSections) {
                // Calls a method
                chunksToRelight.add(instance.getChunk(point.blockX(), point.blockZ()));
            // End of a block/expression
            }

            // Loop: repeats a block
            for (Point point : skySections) {
                // Calls a method
                chunksToRelight.add(instance.getChunk(point.blockX(), point.blockZ()));
            // End of a block/expression
            }

            // Returns a value to the caller
            return new ArrayList<>(chunksToRelight);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static Set<Point> getNearbyRequired(Instance instance, Point point, LightType type) {
        // Calls a method
        Set<Point> collected = new HashSet<>();
        // Calls a method
        collected.add(point);

        // Calls a method
        int highestRegionPoint = instance.getCachedDimensionType().minY() - 1;

        // Loop: repeats a block
        for (int x = point.blockX() - 1; x <= point.blockX() + 1; x++) {
            // Loop: repeats a block
            for (int z = point.blockZ() - 1; z <= point.blockZ() + 1; z++) {
                // Calls a method
                Chunk chunkCheck = instance.getChunk(x, z);
                // Branch: checks a condition
                if (chunkCheck == null) continue;

                // Branch: checks a condition
                if (chunkCheck instanceof LightingChunk lighting) {
                    // Calls a method
                    lighting.lockReadLock();
                    // Exception handling
                    try {
                        // Calls a method
                        highestRegionPoint = Math.max(highestRegionPoint, lighting.getOcclusionData().highestBlock);
                    // Start of a method/block
                    } finally {
                        // Calls a method
                        lighting.unlockReadLock();
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Loop: repeats a block
        for (int x = point.blockX() - 1; x <= point.blockX() + 1; x++) {
            // Loop: repeats a block
            for (int z = point.blockZ() - 1; z <= point.blockZ() + 1; z++) {
                // Calls a method
                Chunk chunkCheck = instance.getChunk(x, z);
                // Branch: checks a condition
                if (chunkCheck == null) continue;

                // Loop: repeats a block
                for (int y = point.blockY() - 1; y <= point.blockY() + 1; y++) {
                    // Calls a method
                    Point sectionPosition = new Vec(x, y, z);
                    // Calls a method
                    int sectionHeight = instance.getCachedDimensionType().minY() + 16 * y;
                    // Branch: checks a condition
                    if ((sectionHeight + 16) > highestRegionPoint && type == LightType.SKY) continue;

                    // Branch: checks a condition
                    if (sectionPosition.blockY() < chunkCheck.getMaxSection() && sectionPosition.blockY() >= chunkCheck.getMinSection()) {
                        // Calls a method
                        Section s = chunkCheck.getSection(sectionPosition.blockY());
                        // Branch: checks a condition
                        if (type == LightType.BLOCK && !s.blockLight().requiresUpdate()) continue;
                        // Branch: checks a condition
                        if (type == LightType.SKY && !s.skyLight().requiresUpdate()) continue;

                        // Calls a method
                        collected.add(sectionPosition);
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Returns a value to the caller
        return collected;
    // End of a block/expression
    }

    // Start of a method/block
    private static Set<Point> collectRequiredNearby(Instance instance, Point point, LightType type) {
        // Calls a method
        final Set<Point> found = new HashSet<>();
        // Calls a method
        final ArrayDeque<Point> toCheck = new ArrayDeque<>();

        // Calls a method
        toCheck.add(point);
        // Calls a method
        found.add(point);

        // Loop: repeats a block
        while (!toCheck.isEmpty()) {
            // Calls a method
            final Point current = toCheck.poll();
            // Calls a method
            final Set<Point> nearby = getNearbyRequired(instance, current, type);
            // Start of a method/block
            nearby.forEach(p -> {
                // Branch: checks a condition
                if (!found.contains(p)) {
                    // Calls a method
                    found.add(p);
                    // Calls a method
                    toCheck.add(p);
                // End of a block/expression
                }
            // End of a block/expression
            });
        // End of a block/expression
        }

        // Returns a value to the caller
        return found;
    // End of a block/expression
    }

    // Start of a method/block
    static Set<Chunk> relightSection(Instance instance, int chunkX, int sectionY, int chunkZ) {
        // Calls a method
        var res = new HashSet<>(relightSection(instance, chunkX, sectionY, chunkZ, LightType.BLOCK));
        // Calls a method
        res.addAll(relightSection(instance, chunkX, sectionY, chunkZ, LightType.SKY));
        // Returns a value to the caller
        return res;
    // End of a block/expression
    }

    // Start of a method/block
    private static Set<Chunk> relightSection(Instance instance, int chunkX, int sectionY, int chunkZ, LightType type) {
        // Calls a method
        Chunk c = instance.getChunk(chunkX, chunkZ);
        // Branch: checks a condition
        if (c == null) return Set.of();
        // Branch: checks a condition
        if (!(c instanceof LightingChunk)) return Set.of();

        // Start of a method/block
        synchronized (instance) {
            // Calls a method
            Set<Point> collected = collectRequiredNearby(instance, new Vec(chunkX, sectionY, chunkZ), type);
            // Returns a value to the caller
            return relight(instance, collected, type);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static Set<Chunk> relight(Instance instance, Set<Point> queue, LightType type) {
        // Returns a value to the caller
        return flushQueue(instance, queue, type, QueueType.INTERNAL);
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
        LightingChunk lightingChunk = new LightingChunk(instance, chunkX, chunkZ, sections);
        // Calls a method
        lightingChunk.entries.putAll(entries);
        // Returns a value to the caller
        return lightingChunk;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isLoaded() {
        // Returns a value to the caller
        return super.isLoaded() && doneInit;
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    protected record OcclusionData(int highestBlock, int[] occlusionMap) {
    // End of a block/expression
    }
// End of a block/expression
}
