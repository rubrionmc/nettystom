// Package declaration for this file
package net.minestom.server.snapshot;

// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.entity.GameMode;
// Import of a required class
import net.minestom.server.instance.Section;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.tag.Tag;
// Import of a required class
import net.minestom.server.tag.TagReadable;
// Import of a required class
import net.minestom.server.utils.collection.IntMappedArray;
// Import of a required class
import net.minestom.server.utils.collection.MappedCollection;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import net.minestom.server.world.DimensionType;
// Import of a required class
import net.minestom.server.world.biome.Biome;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.UUID;
// Import of a required class
import java.util.concurrent.atomic.AtomicReference;

// Static import of a member
import static net.minestom.server.coordinate.CoordConversion.*;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class SnapshotImpl {
    // Type declaration (class/interface/enum/record)
    public record Server(Collection<InstanceSnapshot> instances,
                         // Start of a method/block
                         Int2ObjectOpenHashMap<AtomicReference<EntitySnapshot>> entityRefs) implements ServerSnapshot {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<EntitySnapshot> entities() {
            // Returns a value to the caller
            return MappedCollection.plainReferences(entityRefs.values());
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public @UnknownNullability EntitySnapshot entity(int id) {
            // Calls a method
            var ref = entityRefs.get(id);
            // Returns a value to the caller
            return ref != null ? ref.getPlain() : null;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Instance(AtomicReference<ServerSnapshot> serverRef,
                           // Code statement
                           RegistryKey<DimensionType> dimensionType, long worldAge, long time,
                           // Code statement
                           Map<Long, AtomicReference<ChunkSnapshot>> chunksMap,
                           // Code statement
                           int[] entitiesIds,
                           // Start of a method/block
                           TagReadable tagReadable) implements InstanceSnapshot {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public @Nullable ChunkSnapshot chunk(int chunkX, int chunkZ) {
            // Calls a method
            var ref = chunksMap.get(chunkIndex(chunkX, chunkZ));
            // Returns a value to the caller
            return Objects.requireNonNull(ref, "Chunk not found").getPlain();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<ChunkSnapshot> chunks() {
            // Returns a value to the caller
            return MappedCollection.plainReferences(chunksMap.values());
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<EntitySnapshot> entities() {
            // Returns a value to the caller
            return new IntMappedArray<>(entitiesIds, id -> server().entity(id));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ServerSnapshot server() {
            // Returns a value to the caller
            return serverRef.getPlain();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <T> @UnknownNullability T getTag(Tag<T> tag) {
            // Returns a value to the caller
            return tagReadable.getTag(tag);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Chunk(int minSection, int chunkX, int chunkZ,
                        // Code statement
                        Section[] sections,
                        // Code statement
                        Int2ObjectOpenHashMap<Block> blockEntries,
                        // Code statement
                        int[] entitiesIds,
                        // Code statement
                        AtomicReference<InstanceSnapshot> instanceRef,
                        // Start of a method/block
                        TagReadable tagReadable) implements ChunkSnapshot {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public @UnknownNullability Block getBlock(int x, int y, int z, Condition condition) {
            // Verify if the block object is present
            // Branch: checks a condition
            if (condition != Condition.TYPE) {
                // Assigns a value
                final Block entry = !blockEntries.isEmpty() ?
                        // Calls a method
                        blockEntries.get(chunkBlockIndex(x, y, z)) : null;
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
            final Section section = sections[globalToChunk(y) - minSection];
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
            final Section section = sections[globalToChunk(y) - minSection];
            // Assigns a value
            final int id = section.biomePalette()
                    // Calls a method
                    .get(globalToSectionRelative(x) / 4, globalToSectionRelative(y) / 4, globalToSectionRelative(z) / 4);
            // Calls a method
            RegistryKey<Biome> key = MinecraftServer.getBiomeRegistry().getKey(id);
            // Calls a method
            Check.notNull(key, "Biome with id {0} is not registered", id);
            // Returns a value to the caller
            return key;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <T> @UnknownNullability T getTag(Tag<T> tag) {
            // Returns a value to the caller
            return tagReadable.getTag(tag);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public InstanceSnapshot instance() {
            // Returns a value to the caller
            return instanceRef.getPlain();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<EntitySnapshot> entities() {
            // Returns a value to the caller
            return new IntMappedArray<>(entitiesIds, id -> instance().server().entity(id));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Entity(EntityType type, UUID uuid, int id, Pos position, Vec velocity,
                         // Code statement
                         AtomicReference<InstanceSnapshot> instanceRef, int chunkX, int chunkZ,
                         // Code statement
                         int[] viewersId, int[] passengersId, int vehicleId,
                         // Start of a method/block
                         TagReadable tagReadable) implements EntitySnapshot {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public <T> @UnknownNullability T getTag(Tag<T> tag) {
            // Returns a value to the caller
            return tagReadable.getTag(tag);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public InstanceSnapshot instance() {
            // Returns a value to the caller
            return instanceRef.getPlain();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ChunkSnapshot chunk() {
            // Returns a value to the caller
            return Objects.requireNonNull(instance().chunk(chunkX, chunkZ));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<PlayerSnapshot> viewers() {
            // Returns a value to the caller
            return new IntMappedArray<>(viewersId, id -> (PlayerSnapshot) instance().server().entity(id));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<EntitySnapshot> passengers() {
            // Returns a value to the caller
            return new IntMappedArray<>(passengersId, id -> instance().server().entity(id));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public @Nullable EntitySnapshot vehicle() {
            // Branch: checks a condition
            if (vehicleId == -1) return null;
            // Returns a value to the caller
            return instance().server().entity(vehicleId);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Player(EntitySnapshot snapshot, String username,
                         // Start of a method/block
                         GameMode gameMode) implements PlayerSnapshot {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public EntityType type() {
            // Returns a value to the caller
            return snapshot.type();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public UUID uuid() {
            // Returns a value to the caller
            return snapshot.uuid();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int id() {
            // Returns a value to the caller
            return snapshot.id();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Pos position() {
            // Returns a value to the caller
            return snapshot.position();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Vec velocity() {
            // Returns a value to the caller
            return snapshot.velocity();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public InstanceSnapshot instance() {
            // Returns a value to the caller
            return snapshot.instance();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ChunkSnapshot chunk() {
            // Returns a value to the caller
            return snapshot.chunk();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<PlayerSnapshot> viewers() {
            // Returns a value to the caller
            return snapshot.viewers();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<EntitySnapshot> passengers() {
            // Returns a value to the caller
            return snapshot.passengers();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public @Nullable EntitySnapshot vehicle() {
            // Returns a value to the caller
            return snapshot.vehicle();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <T> @UnknownNullability T getTag(Tag<T> tag) {
            // Returns a value to the caller
            return snapshot.getTag(tag);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
