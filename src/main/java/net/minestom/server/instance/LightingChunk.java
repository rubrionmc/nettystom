// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.collision.Shape;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.CoordConversion;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockHandler;
// Import d'une classe nécessaire
import net.minestom.server.instance.heightmap.Heightmap;
// Import d'une classe nécessaire
import net.minestom.server.instance.light.Light;
// Import d'une classe nécessaire
import net.minestom.server.instance.palette.Palette;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.CachedPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.data.LightData;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.CompletableFuture;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;
// Import d'une classe nécessaire
import java.util.concurrent.ExecutorService;
// Import d'une classe nécessaire
import java.util.concurrent.Executors;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;
// Import d'une classe nécessaire
import java.util.concurrent.locks.ReentrantLock;

// Import statique d'un membre
import static net.minestom.server.instance.light.LightCompute.EMPTY_CONTENT;

/**
 * A chunk which supports lighting computation.
 * <p>
 * This chunk is used to compute the light data for each block.
 * <p>
 */
// Déclaration de type (classe/interface/enum/record)
public class LightingChunk extends DynamicChunk {

    // Appelle une méthode
    private static final ExecutorService pool = Executors.newWorkStealingPool();

    // Instruction de code
    private volatile @Nullable OcclusionData occlusionData;
    // Appelle une méthode
    final CachedPacket partialLightCache = new CachedPacket(this::createLightPacket);
    // Instruction de code
    private @Nullable LightData partialLightData;
    // Instruction de code
    private @Nullable LightData fullLightData;

    // Affecte une valeur
    private boolean freezeInvalidation = false;

    // Appelle une méthode
    private final ReentrantLock packetGenerationLock = new ReentrantLock();
    // Appelle une méthode
    private final AtomicInteger resendTimer = new AtomicInteger(-1);
    // Affecte une valeur
    private final int resendDelay = ServerFlag.SEND_LIGHT_AFTER_BLOCK_PLACEMENT_DELAY;

    // Affecte une valeur
    private boolean doneInit = false;

    // Déclaration de type (classe/interface/enum/record)
    enum LightType {
        // Instruction de code
        SKY,
        // Instruction de code
        BLOCK
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private enum QueueType {
        // Instruction de code
        INTERNAL,
        // Instruction de code
        EXTERNAL
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    private static final Set<Key> DIFFUSE_SKY_LIGHT = Set.of(
            // Instruction de code
            Block.COBWEB.key(),
            // Instruction de code
            Block.ICE.key(),
            // Instruction de code
            Block.HONEY_BLOCK.key(),
            // Instruction de code
            Block.SLIME_BLOCK.key(),
            // Instruction de code
            Block.WATER.key(),
            // Instruction de code
            Block.ACACIA_LEAVES.key(),
            // Instruction de code
            Block.AZALEA_LEAVES.key(),
            // Instruction de code
            Block.BIRCH_LEAVES.key(),
            // Instruction de code
            Block.DARK_OAK_LEAVES.key(),
            // Instruction de code
            Block.FLOWERING_AZALEA_LEAVES.key(),
            // Instruction de code
            Block.JUNGLE_LEAVES.key(),
            // Instruction de code
            Block.CHERRY_LEAVES.key(),
            // Instruction de code
            Block.OAK_LEAVES.key(),
            // Instruction de code
            Block.SPRUCE_LEAVES.key(),
            // Instruction de code
            Block.SPAWNER.key(),
            // Instruction de code
            Block.BEACON.key(),
            // Instruction de code
            Block.END_GATEWAY.key(),
            // Instruction de code
            Block.CHORUS_PLANT.key(),
            // Instruction de code
            Block.CHORUS_FLOWER.key(),
            // Instruction de code
            Block.FROSTED_ICE.key(),
            // Instruction de code
            Block.SEAGRASS.key(),
            // Instruction de code
            Block.TALL_SEAGRASS.key(),
            // Instruction de code
            Block.LAVA.key()
    // Fin d'un bloc/d'une expression
    );

    // Début d'une méthode/d'un bloc
    public void invalidate() {
        // Accès à l'objet courant/parent
        this.partialLightCache.invalidate();
        // Accès à l'objet courant/parent
        this.chunkCache.invalidate();
        // Accès à l'objet courant/parent
        this.partialLightData = null;
        // Accès à l'objet courant/parent
        this.fullLightData = null;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public LightingChunk(Instance instance, int chunkX, int chunkZ) {
        // Accès à l'objet courant/parent
        super(instance, chunkX, chunkZ);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected LightingChunk(Instance instance, int chunkX, int chunkZ, List<Section> sections) {
        // Accès à l'objet courant/parent
        super(instance, chunkX, chunkZ, sections);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private boolean checkSkyOcclusion(Block block) {
        // Embranchement : vérifie une condition
        if (block == Block.AIR) return false;
        // Embranchement : vérifie une condition
        if (DIFFUSE_SKY_LIGHT.contains(block.key())) return true;

        // Appelle une méthode
        Shape shape = block.registry().occlusionShape();
        // Appelle une méthode
        boolean occludesTop = Block.AIR.registry().occlusionShape().isOccluded(shape, BlockFace.TOP);
        // Appelle une méthode
        boolean occludesBottom = Block.AIR.registry().occlusionShape().isOccluded(shape, BlockFace.BOTTOM);

        // Renvoie une valeur à l'appelant
        return occludesBottom || occludesTop;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setFreezeInvalidation(boolean freezeInvalidation) {
        // Accès à l'objet courant/parent
        this.freezeInvalidation = freezeInvalidation;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void invalidateNeighborsSection(int coordinate) {
        // Embranchement : vérifie une condition
        if (freezeInvalidation) {
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Boucle : répète un bloc
        for (int i = -1; i <= 1; i++) {
            // Boucle : répète un bloc
            for (int j = -1; j <= 1; j++) {
                // Appelle une méthode
                Chunk neighborChunk = instance.getChunk(chunkX + i, chunkZ + j);
                // Embranchement : vérifie une condition
                if (neighborChunk == null) continue;

                // Embranchement : vérifie une condition
                if (neighborChunk instanceof LightingChunk light) {
                    // Appelle une méthode
                    light.invalidate();
                // Fin d'un bloc/d'une expression
                }

                // Boucle : répète un bloc
                for (int k = -1; k <= 1; k++) {
                    // Embranchement : vérifie une condition
                    if (k + coordinate < neighborChunk.getMinSection() || k + coordinate >= neighborChunk.getMaxSection())
                        // Passe à l'itération suivante de la boucle
                        continue;
                    // Appelle une méthode
                    neighborChunk.getSection(k + coordinate).invalidate();
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void invalidateResendDelay() {
        // Embranchement : vérifie une condition
        if (!doneInit || freezeInvalidation) {
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Boucle : répète un bloc
        for (int i = -1; i <= 1; i++) {
            // Boucle : répète un bloc
            for (int j = -1; j <= 1; j++) {
                // Appelle une méthode
                Chunk neighborChunk = instance.getChunk(chunkX + i, chunkZ + j);
                // Embranchement : vérifie une condition
                if (neighborChunk instanceof LightingChunk light) {
                    // Appelle une méthode
                    light.resendTimer.set(resendDelay);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
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
        // Accès à l'objet courant/parent
        super.setBlock(x, y, z, block, placement, destroy);
        // Accès à l'objet courant/parent
        this.occlusionData = null;

        // Invalidate neighbor chunks, since they can be updated by this block change
        // Appelle une méthode
        int coordinate = CoordConversion.globalToChunk(y);
        // Embranchement : vérifie une condition
        if (doneInit && !freezeInvalidation) {
            // Appelle une méthode
            invalidateNeighborsSection(coordinate);
            // Appelle une méthode
            invalidateResendDelay();
            // Accès à l'objet courant/parent
            this.partialLightCache.invalidate();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void sendLighting() {
        // Embranchement : vérifie une condition
        if (!isLoaded()) return;
        // Appelle une méthode
        sendPacketToViewers(partialLightCache);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected void onLoad() {
        // Affecte une valeur
        doneInit = true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void onGenerate() {
        // Accès à l'objet courant/parent
        super.onGenerate();

        // Boucle : répète un bloc
        for (int section = minSection; section < maxSection; section++) {
            // Appelle une méthode
            getSection(section).invalidate();
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        invalidate();

        // Boucle : répète un bloc
        for (int i = -1; i <= 1; i++) {
            // Boucle : répète un bloc
            for (int j = -1; j <= 1; j++) {
                // Appelle une méthode
                Chunk neighborChunk = instance.getChunk(chunkX + i, chunkZ + j);
                // Embranchement : vérifie une condition
                if (neighborChunk == null) continue;

                // Embranchement : vérifie une condition
                if (neighborChunk instanceof LightingChunk light) {
                    // Embranchement : vérifie une condition
                    if (light.doneInit) {
                        // Appelle une méthode
                        light.resendTimer.set(20);
                        // Appelle une méthode
                        light.invalidate();

                        // Boucle : répète un bloc
                        for (int section = minSection; section < maxSection; section++) {
                            // Appelle une méthode
                            light.getSection(section).invalidate();
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected OcclusionData getOcclusionData() {
        // Appelle une méthode
        assertReadLock();
        // Affecte une valeur
        final OcclusionData currentOcclusionData = this.occlusionData;
        // Embranchement : vérifie une condition
        if (currentOcclusionData != null) return currentOcclusionData;

        // Affecte une valeur
        final int[] occlusionMap = new int[CHUNK_SIZE_X * CHUNK_SIZE_Z];

        // Appelle une méthode
        int minY = instance.getCachedDimensionType().minY();
        // Affecte une valeur
        int highestBlock = minY - 1;

        // Appelle une méthode
        int startY = Heightmap.getHighestBlockSection(this);

        // Boucle : répète un bloc
        for (int x = 0; x < CHUNK_SIZE_X; x++) {
            // Boucle : répète un bloc
            for (int z = 0; z < CHUNK_SIZE_Z; z++) {
                // Affecte une valeur
                int height = startY;
                // Boucle : répète un bloc
                while (height >= minY) {
                    // Appelle une méthode
                    Block block = getBlock(x, height, z, Condition.TYPE);
                    // Embranchement : vérifie une condition
                    if (block != Block.AIR) highestBlock = Math.max(highestBlock, height);
                    // Embranchement : vérifie une condition
                    if (checkSkyOcclusion(block)) break;
                    // Instruction de code
                    height--;
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                occlusionMap[z << 4 | x] = (height + 1);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        var occlusionData = new OcclusionData(highestBlock, occlusionMap);
        // While we only assert that we are in a read-lock, this should still be OK because even if two threads concurrently
        // compute the occlusionData, then both results will be the same, and the differing OcclusionData identites are of no concern.
        // Accès à l'objet courant/parent
        this.occlusionData = occlusionData;
        // Renvoie une valeur à l'appelant
        return occlusionData;
    // Fin d'un bloc/d'une expression
    }

    // Lazy compute occlusion map
    // Début d'une méthode/d'un bloc
    public int[] getOcclusionMap() {
        // Renvoie une valeur à l'appelant
        return getOcclusionData().occlusionMap();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected LightData createLightData(boolean requiredFullChunk) {
        // Appelle une méthode
        packetGenerationLock.lock();
        // Gestion des exceptions
        try {
            // Embranchement : vérifie une condition
            if (requiredFullChunk) {
                // Embranchement : vérifie une condition
                if (fullLightData != null) {
                    // Renvoie une valeur à l'appelant
                    return fullLightData;
                // Fin d'un bloc/d'une expression
                }
            // Branche alternative de la condition
            } else {
                // Embranchement : vérifie une condition
                if (partialLightData != null) {
                    // Renvoie une valeur à l'appelant
                    return partialLightData;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            BitSet skyMask = new BitSet();
            // Appelle une méthode
            BitSet blockMask = new BitSet();
            // Appelle une méthode
            BitSet emptySkyMask = new BitSet();
            // Appelle une méthode
            BitSet emptyBlockMask = new BitSet();
            // Appelle une méthode
            List<byte[]> skyLights = new ArrayList<>();
            // Appelle une méthode
            List<byte[]> blockLights = new ArrayList<>();

            // Appelle une méthode
            int chunkMin = instance.getCachedDimensionType().minY();
            // Appelle une méthode
            int highestNeighborBlock = instance.getCachedDimensionType().minY();
            // Boucle : répète un bloc
            for (int i = -1; i <= 1; i++) {
                // Boucle : répète un bloc
                for (int j = -1; j <= 1; j++) {
                    // Appelle une méthode
                    Chunk neighborChunk = instance.getChunk(chunkX + i, chunkZ + j);
                    // Embranchement : vérifie une condition
                    if (neighborChunk == null) continue;

                    // Embranchement : vérifie une condition
                    if (neighborChunk instanceof LightingChunk light) {
                        // Appelle une méthode
                        light.lockReadLock();
                        // Gestion des exceptions
                        try {
                            // Appelle une méthode
                            highestNeighborBlock = Math.max(highestNeighborBlock, light.getOcclusionData().highestBlock);
                        // Début d'une méthode/d'un bloc
                        } finally {
                            // Appelle une méthode
                            light.unlockReadLock();
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // Affecte une valeur
            int index = 0;
            // Boucle : répète un bloc
            for (Section section : sections) {
                // Affecte une valeur
                boolean wasUpdatedBlock = false;
                // Affecte une valeur
                boolean wasUpdatedSky = false;

                // Embranchement : vérifie une condition
                if (section.blockLight().requiresUpdate()) {
                    // Appelle une méthode
                    relightSection(instance, this.chunkX, index + minSection, chunkZ, LightType.BLOCK);
                    // Affecte une valeur
                    wasUpdatedBlock = true;
                // Embranchement : vérifie une condition
                } else if (requiredFullChunk || section.blockLight().requiresSend()) {
                    // Affecte une valeur
                    wasUpdatedBlock = true;
                // Fin d'un bloc/d'une expression
                }

                // Embranchement : vérifie une condition
                if (section.skyLight().requiresUpdate()) {
                    // Appelle une méthode
                    relightSection(instance, this.chunkX, index + minSection, chunkZ, LightType.SKY);
                    // Affecte une valeur
                    wasUpdatedSky = true;
                // Embranchement : vérifie une condition
                } else if (requiredFullChunk || section.skyLight().requiresSend()) {
                    // Affecte une valeur
                    wasUpdatedSky = true;
                // Fin d'un bloc/d'une expression
                }

                // Affecte une valeur
                final int sectionMinY = index * 16 + chunkMin;
                // Instruction de code
                index++;

                // Embranchement : vérifie une condition
                if ((wasUpdatedSky) && this.instance.getCachedDimensionType().hasSkylight() && sectionMinY <= (highestNeighborBlock + 16)) {
                    // Appelle une méthode
                    final byte[] skyLight = section.skyLight().array();

                    // Embranchement : vérifie une condition
                    if (skyLight.length != 0 && skyLight != EMPTY_CONTENT) {
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
                // Fin d'un bloc/d'une expression
                }

                // Embranchement : vérifie une condition
                if (wasUpdatedBlock) {
                    // Appelle une méthode
                    final byte[] blockLight = section.blockLight().array();

                    // Embranchement : vérifie une condition
                    if (blockLight.length != 0 && blockLight != EMPTY_CONTENT) {
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
            // Fin d'un bloc/d'une expression
            }

            // Affecte une valeur
            LightData lightData = new LightData(skyMask, blockMask,
                    // Instruction de code
                    emptySkyMask, emptyBlockMask,
                    // Instruction de code
                    skyLights, blockLights);

            // Embranchement : vérifie une condition
            if (requiredFullChunk) {
                // Accès à l'objet courant/parent
                this.fullLightData = lightData;
            // Branche alternative de la condition
            } else {
                // Accès à l'objet courant/parent
                this.partialLightData = lightData;
            // Fin d'un bloc/d'une expression
            }


            // Renvoie une valeur à l'appelant
            return lightData;
        // Début d'une méthode/d'un bloc
        } finally {
            // Appelle une méthode
            packetGenerationLock.unlock();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void tick(long time) {
        // Accès à l'objet courant/parent
        super.tick(time);

        // Embranchement : vérifie une condition
        if (doneInit && resendTimer.get() > 0) {
            // Embranchement : vérifie une condition
            if (resendTimer.decrementAndGet() == 0) {
                // Appelle une méthode
                sendLighting();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Set<Chunk> flushQueue(Instance instance, Set<Point> queue, LightType type, QueueType queueType) {
        // Appelle une méthode
        Set<Light> sections = ConcurrentHashMap.newKeySet();
        // Appelle une méthode
        Set<Point> newQueue = ConcurrentHashMap.newKeySet();

        // Appelle une méthode
        Set<Chunk> responseChunks = ConcurrentHashMap.newKeySet();
        // Appelle une méthode
        List<CompletableFuture<Void>> tasks = new ArrayList<>();

        // Affecte une valeur
        Light.LightLookup lightLookup = (x, y, z) -> {
            // Appelle une méthode
            Chunk chunk = instance.getChunk(x, z);
            // Embranchement : vérifie une condition
            if (chunk == null) return null;
            // Embranchement : vérifie une condition
            if (!(chunk instanceof LightingChunk lighting)) return null;
            // Embranchement : vérifie une condition
            if (y - lighting.getMinSection() < 0 || y - lighting.getMaxSection() >= 0) return null;
            // Appelle une méthode
            final Section section = lighting.getSection(y);
            // Renvoie une valeur à l'appelant
            return switch (type) {
                // Embranchement multiple (switch/case)
                case BLOCK -> section.blockLight();
                // Embranchement multiple (switch/case)
                case SKY -> section.skyLight();
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        };

        // Affecte une valeur
        Light.PaletteLookup paletteLookup = (x, y, z) -> {
            // Appelle une méthode
            Chunk chunk = instance.getChunk(x, z);
            // Embranchement : vérifie une condition
            if (chunk == null) return null;
            // Embranchement : vérifie une condition
            if (!(chunk instanceof LightingChunk lighting)) return null;
            // Embranchement : vérifie une condition
            if (y - lighting.getMinSection() < 0 || y - lighting.getMaxSection() >= 0) return null;
            // Renvoie une valeur à l'appelant
            return chunk.getSection(y).blockPalette();
        // Fin d'un bloc/d'une expression
        };

        // Boucle : répète un bloc
        for (Point point : queue) {
            // Appelle une méthode
            Chunk chunk = instance.getChunk(point.blockX(), point.blockZ());
            // Embranchement : vérifie une condition
            if (!(chunk instanceof LightingChunk lightingChunk)) continue;

            // Appelle une méthode
            Section section = chunk.getSection(point.blockY());
            // Appelle une méthode
            responseChunks.add(chunk);

            // Affecte une valeur
            Light light = switch (type) {
                // Embranchement multiple (switch/case)
                case BLOCK -> section.blockLight();
                // Embranchement multiple (switch/case)
                case SKY -> section.skyLight();
            // Fin d'un bloc/d'une expression
            };

            // Appelle une méthode
            final Palette blockPalette = section.blockPalette();
            // Affecte une valeur
            CompletableFuture<Void> task = CompletableFuture.runAsync(() -> {
                // Gestion des exceptions
                try {
                    // Instruction de code
                    final Set<Point> toAdd;
                    // Appelle une méthode
                    lightingChunk.lockReadLock();
                    // Gestion des exceptions
                    try {
                        // Affecte une valeur
                        toAdd = switch (queueType) {
                            // Embranchement multiple (switch/case)
                            case INTERNAL -> light.calculateInternal(blockPalette,
                                    // Instruction de code
                                    chunk.getChunkX(), point.blockY(), chunk.getChunkZ(),
                                    // Instruction de code
                                    lightingChunk.getOcclusionMap(), chunk.instance.getCachedDimensionType().maxY(),
                                    // Instruction de code
                                    lightLookup);
                            // Embranchement multiple (switch/case)
                            case EXTERNAL -> light.calculateExternal(blockPalette,
                                    // Instruction de code
                                    Light.getNeighbors(chunk, point.blockY()),
                                    // Instruction de code
                                    lightLookup, paletteLookup);
                        // Fin d'un bloc/d'une expression
                        };
                    // Début d'une méthode/d'un bloc
                    } finally {
                        // Appelle une méthode
                        lightingChunk.unlockReadLock();
                    // Fin d'un bloc/d'une expression
                    }

                    // Appelle une méthode
                    sections.add(light);

                    // Appelle une méthode
                    light.flip();
                    // Appelle une méthode
                    newQueue.addAll(toAdd);
                // Début d'une méthode/d'un bloc
                } catch (Exception e) {
                    // Appelle une méthode
                    MinecraftServer.getExceptionManager().handleException(e);
                // Fin d'un bloc/d'une expression
                }
            // Instruction de code
            }, pool);

            // Appelle une méthode
            tasks.add(task);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        tasks.forEach(CompletableFuture::join);

        // Embranchement : vérifie une condition
        if (!newQueue.isEmpty()) {
            // Appelle une méthode
            var newResponse = flushQueue(instance, newQueue, type, QueueType.EXTERNAL);
            // Appelle une méthode
            responseChunks.addAll(newResponse);
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return responseChunks;
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public static List<Chunk> relight(Instance instance, Collection<Chunk> chunks) {
        // Appelle une méthode
        Set<Point> sections = new HashSet<>();

        // Début d'une méthode/d'un bloc
        synchronized (instance) {
            // Boucle : répète un bloc
            for (Chunk chunk : chunks) {
                // Embranchement : vérifie une condition
                if (!(chunk instanceof LightingChunk lighting)) continue;
                // Boucle : répète un bloc
                for (int sectionIndex = chunk.minSection; sectionIndex < chunk.maxSection; sectionIndex++) {
                    // Appelle une méthode
                    Section section = chunk.getSection(sectionIndex);
                    // Appelle une méthode
                    section.invalidate();
                    // Appelle une méthode
                    sections.add(new Vec(chunk.getChunkX(), sectionIndex, chunk.getChunkZ()));
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                lighting.invalidate();
            // Fin d'un bloc/d'une expression
            }

            // Expand the sections to include nearby sections
            // Appelle une méthode
            var blockSections = new HashSet<Point>();
            // Boucle : répète un bloc
            for (Point point : sections) {
                // Appelle une méthode
                blockSections.addAll(getNearbyRequired(instance, point, LightType.BLOCK));
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            var skySections = new HashSet<Point>();
            // Boucle : répète un bloc
            for (Point point : sections) {
                // Appelle une méthode
                skySections.addAll(getNearbyRequired(instance, point, LightType.SKY));
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            relight(instance, blockSections, LightType.BLOCK);
            // Appelle une méthode
            relight(instance, skySections, LightType.SKY);

            // Appelle une méthode
            var chunksToRelight = new HashSet<Chunk>();
            // Boucle : répète un bloc
            for (Point point : blockSections) {
                // Appelle une méthode
                chunksToRelight.add(instance.getChunk(point.blockX(), point.blockZ()));
            // Fin d'un bloc/d'une expression
            }

            // Boucle : répète un bloc
            for (Point point : skySections) {
                // Appelle une méthode
                chunksToRelight.add(instance.getChunk(point.blockX(), point.blockZ()));
            // Fin d'un bloc/d'une expression
            }

            // Renvoie une valeur à l'appelant
            return new ArrayList<>(chunksToRelight);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Set<Point> getNearbyRequired(Instance instance, Point point, LightType type) {
        // Appelle une méthode
        Set<Point> collected = new HashSet<>();
        // Appelle une méthode
        collected.add(point);

        // Appelle une méthode
        int highestRegionPoint = instance.getCachedDimensionType().minY() - 1;

        // Boucle : répète un bloc
        for (int x = point.blockX() - 1; x <= point.blockX() + 1; x++) {
            // Boucle : répète un bloc
            for (int z = point.blockZ() - 1; z <= point.blockZ() + 1; z++) {
                // Appelle une méthode
                Chunk chunkCheck = instance.getChunk(x, z);
                // Embranchement : vérifie une condition
                if (chunkCheck == null) continue;

                // Embranchement : vérifie une condition
                if (chunkCheck instanceof LightingChunk lighting) {
                    // Appelle une méthode
                    lighting.lockReadLock();
                    // Gestion des exceptions
                    try {
                        // Appelle une méthode
                        highestRegionPoint = Math.max(highestRegionPoint, lighting.getOcclusionData().highestBlock);
                    // Début d'une méthode/d'un bloc
                    } finally {
                        // Appelle une méthode
                        lighting.unlockReadLock();
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Boucle : répète un bloc
        for (int x = point.blockX() - 1; x <= point.blockX() + 1; x++) {
            // Boucle : répète un bloc
            for (int z = point.blockZ() - 1; z <= point.blockZ() + 1; z++) {
                // Appelle une méthode
                Chunk chunkCheck = instance.getChunk(x, z);
                // Embranchement : vérifie une condition
                if (chunkCheck == null) continue;

                // Boucle : répète un bloc
                for (int y = point.blockY() - 1; y <= point.blockY() + 1; y++) {
                    // Appelle une méthode
                    Point sectionPosition = new Vec(x, y, z);
                    // Appelle une méthode
                    int sectionHeight = instance.getCachedDimensionType().minY() + 16 * y;
                    // Embranchement : vérifie une condition
                    if ((sectionHeight + 16) > highestRegionPoint && type == LightType.SKY) continue;

                    // Embranchement : vérifie une condition
                    if (sectionPosition.blockY() < chunkCheck.getMaxSection() && sectionPosition.blockY() >= chunkCheck.getMinSection()) {
                        // Appelle une méthode
                        Section s = chunkCheck.getSection(sectionPosition.blockY());
                        // Embranchement : vérifie une condition
                        if (type == LightType.BLOCK && !s.blockLight().requiresUpdate()) continue;
                        // Embranchement : vérifie une condition
                        if (type == LightType.SKY && !s.skyLight().requiresUpdate()) continue;

                        // Appelle une méthode
                        collected.add(sectionPosition);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return collected;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Set<Point> collectRequiredNearby(Instance instance, Point point, LightType type) {
        // Appelle une méthode
        final Set<Point> found = new HashSet<>();
        // Appelle une méthode
        final ArrayDeque<Point> toCheck = new ArrayDeque<>();

        // Appelle une méthode
        toCheck.add(point);
        // Appelle une méthode
        found.add(point);

        // Boucle : répète un bloc
        while (!toCheck.isEmpty()) {
            // Appelle une méthode
            final Point current = toCheck.poll();
            // Appelle une méthode
            final Set<Point> nearby = getNearbyRequired(instance, current, type);
            // Début d'une méthode/d'un bloc
            nearby.forEach(p -> {
                // Embranchement : vérifie une condition
                if (!found.contains(p)) {
                    // Appelle une méthode
                    found.add(p);
                    // Appelle une méthode
                    toCheck.add(p);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return found;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Set<Chunk> relightSection(Instance instance, int chunkX, int sectionY, int chunkZ) {
        // Appelle une méthode
        var res = new HashSet<>(relightSection(instance, chunkX, sectionY, chunkZ, LightType.BLOCK));
        // Appelle une méthode
        res.addAll(relightSection(instance, chunkX, sectionY, chunkZ, LightType.SKY));
        // Renvoie une valeur à l'appelant
        return res;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Set<Chunk> relightSection(Instance instance, int chunkX, int sectionY, int chunkZ, LightType type) {
        // Appelle une méthode
        Chunk c = instance.getChunk(chunkX, chunkZ);
        // Embranchement : vérifie une condition
        if (c == null) return Set.of();
        // Embranchement : vérifie une condition
        if (!(c instanceof LightingChunk)) return Set.of();

        // Début d'une méthode/d'un bloc
        synchronized (instance) {
            // Appelle une méthode
            Set<Point> collected = collectRequiredNearby(instance, new Vec(chunkX, sectionY, chunkZ), type);
            // Renvoie une valeur à l'appelant
            return relight(instance, collected, type);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Set<Chunk> relight(Instance instance, Set<Point> queue, LightType type) {
        // Renvoie une valeur à l'appelant
        return flushQueue(instance, queue, type, QueueType.INTERNAL);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Chunk copy(Instance instance, int chunkX, int chunkZ) {
        // Appelle une méthode
        assertReadLock();
        // Appelle une méthode
        var sections = this.sections.stream().map(Section::clone).toList();
        // Appelle une méthode
        LightingChunk lightingChunk = new LightingChunk(instance, chunkX, chunkZ, sections);
        // Appelle une méthode
        lightingChunk.entries.putAll(entries);
        // Renvoie une valeur à l'appelant
        return lightingChunk;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isLoaded() {
        // Renvoie une valeur à l'appelant
        return super.isLoaded() && doneInit;
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    protected record OcclusionData(int highestBlock, int[] occlusionMap) {
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
