// Déclaration du paquet de ce fichier
package net.minestom.server.snapshot;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.entity.GameMode;
// Import d'une classe nécessaire
import net.minestom.server.instance.Section;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.tag.Tag;
// Import d'une classe nécessaire
import net.minestom.server.tag.TagReadable;
// Import d'une classe nécessaire
import net.minestom.server.utils.collection.IntMappedArray;
// Import d'une classe nécessaire
import net.minestom.server.utils.collection.MappedCollection;
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
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.UUID;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicReference;

// Import statique d'un membre
import static net.minestom.server.coordinate.CoordConversion.*;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class SnapshotImpl {
    // Déclaration de type (classe/interface/enum/record)
    public record Server(Collection<InstanceSnapshot> instances,
                         // Début d'une méthode/d'un bloc
                         Int2ObjectOpenHashMap<AtomicReference<EntitySnapshot>> entityRefs) implements ServerSnapshot {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<EntitySnapshot> entities() {
            // Renvoie une valeur à l'appelant
            return MappedCollection.plainReferences(entityRefs.values());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public @UnknownNullability EntitySnapshot entity(int id) {
            // Appelle une méthode
            var ref = entityRefs.get(id);
            // Renvoie une valeur à l'appelant
            return ref != null ? ref.getPlain() : null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Instance(AtomicReference<ServerSnapshot> serverRef,
                           // Instruction de code
                           RegistryKey<DimensionType> dimensionType, long worldAge, long time,
                           // Instruction de code
                           Map<Long, AtomicReference<ChunkSnapshot>> chunksMap,
                           // Instruction de code
                           int[] entitiesIds,
                           // Début d'une méthode/d'un bloc
                           TagReadable tagReadable) implements InstanceSnapshot {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public @Nullable ChunkSnapshot chunk(int chunkX, int chunkZ) {
            // Appelle une méthode
            var ref = chunksMap.get(chunkIndex(chunkX, chunkZ));
            // Renvoie une valeur à l'appelant
            return Objects.requireNonNull(ref, "Chunk not found").getPlain();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<ChunkSnapshot> chunks() {
            // Renvoie une valeur à l'appelant
            return MappedCollection.plainReferences(chunksMap.values());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<EntitySnapshot> entities() {
            // Renvoie une valeur à l'appelant
            return new IntMappedArray<>(entitiesIds, id -> server().entity(id));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ServerSnapshot server() {
            // Renvoie une valeur à l'appelant
            return serverRef.getPlain();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <T> @UnknownNullability T getTag(Tag<T> tag) {
            // Renvoie une valeur à l'appelant
            return tagReadable.getTag(tag);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Chunk(int minSection, int chunkX, int chunkZ,
                        // Instruction de code
                        Section[] sections,
                        // Instruction de code
                        Int2ObjectOpenHashMap<Block> blockEntries,
                        // Instruction de code
                        int[] entitiesIds,
                        // Instruction de code
                        AtomicReference<InstanceSnapshot> instanceRef,
                        // Début d'une méthode/d'un bloc
                        TagReadable tagReadable) implements ChunkSnapshot {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public @UnknownNullability Block getBlock(int x, int y, int z, Condition condition) {
            // Verify if the block object is present
            // Embranchement : vérifie une condition
            if (condition != Condition.TYPE) {
                // Affecte une valeur
                final Block entry = !blockEntries.isEmpty() ?
                        // Appelle une méthode
                        blockEntries.get(chunkBlockIndex(x, y, z)) : null;
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
            final Section section = sections[globalToChunk(y) - minSection];
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
            final Section section = sections[globalToChunk(y) - minSection];
            // Affecte une valeur
            final int id = section.biomePalette()
                    // Appelle une méthode
                    .get(globalToSectionRelative(x) / 4, globalToSectionRelative(y) / 4, globalToSectionRelative(z) / 4);
            // Appelle une méthode
            RegistryKey<Biome> key = MinecraftServer.getBiomeRegistry().getKey(id);
            // Appelle une méthode
            Check.notNull(key, "Biome with id {0} is not registered", id);
            // Renvoie une valeur à l'appelant
            return key;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <T> @UnknownNullability T getTag(Tag<T> tag) {
            // Renvoie une valeur à l'appelant
            return tagReadable.getTag(tag);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public InstanceSnapshot instance() {
            // Renvoie une valeur à l'appelant
            return instanceRef.getPlain();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<EntitySnapshot> entities() {
            // Renvoie une valeur à l'appelant
            return new IntMappedArray<>(entitiesIds, id -> instance().server().entity(id));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Entity(EntityType type, UUID uuid, int id, Pos position, Vec velocity,
                         // Instruction de code
                         AtomicReference<InstanceSnapshot> instanceRef, int chunkX, int chunkZ,
                         // Instruction de code
                         int[] viewersId, int[] passengersId, int vehicleId,
                         // Début d'une méthode/d'un bloc
                         TagReadable tagReadable) implements EntitySnapshot {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <T> @UnknownNullability T getTag(Tag<T> tag) {
            // Renvoie une valeur à l'appelant
            return tagReadable.getTag(tag);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public InstanceSnapshot instance() {
            // Renvoie une valeur à l'appelant
            return instanceRef.getPlain();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ChunkSnapshot chunk() {
            // Renvoie une valeur à l'appelant
            return Objects.requireNonNull(instance().chunk(chunkX, chunkZ));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<PlayerSnapshot> viewers() {
            // Renvoie une valeur à l'appelant
            return new IntMappedArray<>(viewersId, id -> (PlayerSnapshot) instance().server().entity(id));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<EntitySnapshot> passengers() {
            // Renvoie une valeur à l'appelant
            return new IntMappedArray<>(passengersId, id -> instance().server().entity(id));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public @Nullable EntitySnapshot vehicle() {
            // Embranchement : vérifie une condition
            if (vehicleId == -1) return null;
            // Renvoie une valeur à l'appelant
            return instance().server().entity(vehicleId);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Player(EntitySnapshot snapshot, String username,
                         // Début d'une méthode/d'un bloc
                         GameMode gameMode) implements PlayerSnapshot {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public EntityType type() {
            // Renvoie une valeur à l'appelant
            return snapshot.type();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public UUID uuid() {
            // Renvoie une valeur à l'appelant
            return snapshot.uuid();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int id() {
            // Renvoie une valeur à l'appelant
            return snapshot.id();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Pos position() {
            // Renvoie une valeur à l'appelant
            return snapshot.position();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Vec velocity() {
            // Renvoie une valeur à l'appelant
            return snapshot.velocity();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public InstanceSnapshot instance() {
            // Renvoie une valeur à l'appelant
            return snapshot.instance();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ChunkSnapshot chunk() {
            // Renvoie une valeur à l'appelant
            return snapshot.chunk();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<PlayerSnapshot> viewers() {
            // Renvoie une valeur à l'appelant
            return snapshot.viewers();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<EntitySnapshot> passengers() {
            // Renvoie une valeur à l'appelant
            return snapshot.passengers();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public @Nullable EntitySnapshot vehicle() {
            // Renvoie une valeur à l'appelant
            return snapshot.vehicle();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <T> @UnknownNullability T getTag(Tag<T> tag) {
            // Renvoie une valeur à l'appelant
            return snapshot.getTag(tag);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
