// Package declaration for this file
package net.minestom.server.instance.block;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.KeyPattern;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.Result;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.kyori.adventure.translation.Translatable;
// Import of a required class
import net.minestom.server.coordinate.Area;
// Import of a required class
import net.minestom.server.coordinate.BlockVec;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.batch.Batch;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.registry.Registry;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import net.minestom.server.registry.StaticProtocolObject;
// Import of a required class
import net.minestom.server.tag.Tag;
// Import of a required class
import net.minestom.server.tag.TagReadable;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;
// Import of a required class
import org.jetbrains.annotations.Unmodifiable;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.function.BiPredicate;

/**
 * Represents a block that can be placed anywhere.
 * Block objects are expected to be reusable and therefore do not
 * retain placement data (e.g. block position)
 * <p>
 * Implementations are expected to be immutable.
 */
// Type declaration (class/interface/enum/record)
public sealed interface Block extends StaticProtocolObject<Block>, TagReadable, Blocks, Translatable permits BlockImpl {

    // Calls a method
    NetworkBuffer.Type<Block> ID_NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(Block::fromBlockId, Block::id);
    // Calls a method
    NetworkBuffer.Type<Block> STATE_NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(Block::fromStateId, Block::stateId);

    /**
     * Codec for blocks states as strings.
     * Format: <code>"minecraft:x[a=y,b=z]"</code>
     */
    // Assigns a value
    Codec<Block> STATE_CODEC = Codec.STRING.transform(state -> Objects.requireNonNull(
            // Calls a method
            Block.fromState(state), () -> "not a block state: " + state), Block::state);

    /**
     * Codec for block states as a map.
     * Format: <code>{Name:"minecraft:x",Properties:{a:"y",b:"z"}}</code>
     */
    // Assigns a value
    Codec<Block> STATE_STRUCT_CODEC = new StructCodec<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<Block> decodeFromMap(Transcoder<D> coder, Transcoder.MapLike<D> map) {
            // Calls a method
            Result<Block> blockResult = map.getValue("Name").map(coder::getString).mapResult(Block::fromKey);
            // Branch: checks a condition
            if (!(blockResult instanceof Result.Ok(Block block)))
                // Returns a value to the caller
                return blockResult.cast();
            // Calls a method
            Result<Transcoder.MapLike<D>> propertiesResult = map.getValue("Properties").map(coder::getMap);
            // Branch: checks a condition
            if (!(propertiesResult instanceof Result.Ok(Transcoder.MapLike<D> properties)))
                // properties are optional
                // Returns a value to the caller
                return new Result.Ok<>(block);
            // Loop: repeats a block
            for (String key : properties.keys()) {
                // Calls a method
                Result<String> valueResult = properties.getValue(key).map(coder::getString);
                // Branch: checks a condition
                if (!(valueResult instanceof Result.Ok(String mapValue))) {
                    // Returns a value to the caller
                    return new Result.Error<>("No string value found for property " + key + " in block state");
                // End of a block/expression
                }
                // Calls a method
                block = block.withProperty(key, mapValue);
            // End of a block/expression
            }
            // Returns a value to the caller
            return new Result.Ok<>(block);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encodeToMap(Transcoder<D> coder, Block value, Transcoder.MapBuilder<D> map) {
            // Branch: checks a condition
            if (value == null) return new Result.Error<>("null");
            // Calls a method
            map.put("Name", coder.createString(value.key().asMinimalString()));
            // Branch: checks a condition
            if (value.properties().isEmpty()) {
                // Returns a value to the caller
                return new Result.Ok<>(map.build());
            // End of a block/expression
            }
            // Calls a method
            Map<String, String> defaultProperties = value.defaultState().properties();
            // Calls a method
            Transcoder.MapBuilder<D> propertiesBuilder = coder.createMap();
            // Assigns a value
            boolean nonDefaultPropertyExists = false;
            // Loop: repeats a block
            for (Map.Entry<String, String> entry : value.properties().entrySet()) {
                // Branch: checks a condition
                if (defaultProperties.getOrDefault(entry.getKey(), "").equals(entry.getValue()))
                    // Continues to the next loop iteration
                    continue; // Skip default values
                // Calls a method
                propertiesBuilder.put(entry.getKey(), coder.createString(entry.getValue()));
                // Assigns a value
                nonDefaultPropertyExists = true;
            // End of a block/expression
            }
            // Branch: checks a condition
            if (nonDefaultPropertyExists) {
                // Calls a method
                map.put("Properties", propertiesBuilder.build());
            // End of a block/expression
            }
            // Returns a value to the caller
            return new Result.Ok<>(map.build());
        // End of a block/expression
        }
    // End of a block/expression
    };

    /**
     * Creates a new block with the the property {@code property} sets to {@code value}.
     *
     * @param property the property name
     * @param value    the property value
     * @return a new block with its property changed
     * @throws IllegalArgumentException if the property or value are invalid
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    Block withProperty(String property, String value);

    /**
     * Changes multiple properties at once.
     * <p>
     * Equivalent to calling {@link #withProperty(String, String)} for each map entry.
     *
     * @param properties map containing all the properties to change
     * @return a new block with its properties changed
     * @throws IllegalArgumentException if the property or value are invalid
     * @see #withProperty(String, String)
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    Block withProperties(Map<String, String> properties);

    /**
     * Creates a new block with a tag modified.
     *
     * @param tag   the tag to modify
     * @param value the tag value, null to remove
     * @param <T>   the tag type
     * @return a new block with the modified tag
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    <T> Block withTag(Tag<T> tag, @Nullable T value);

    /**
     * Creates a new block with different nbt data.
     *
     * @param compound the new block nbt, null to remove
     * @return a new block with different nbt
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    Block withNbt(@Nullable CompoundBinaryTag compound);

    /**
     * Creates a new block with the specified {@link BlockHandler handler}.
     *
     * @param handler the new block handler, null to remove
     * @return a new block with the specified handler
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    Block withHandler(@Nullable BlockHandler handler);

    /**
     * Returns an unmodifiable view to the block nbt.
     * <p>
     * Be aware that {@link Tag tags} directly affect the block nbt.
     *
     * @return the block nbt, null if not present
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Annotation for the following element
    @Nullable CompoundBinaryTag nbt();

    /**
     * Returns an unmodifiable view of the block nbt or an empty compound.
     *
     * @return the block nbt or an empty compound if not present
     */
    // Start of a method/block
    default CompoundBinaryTag nbtOrEmpty() {
        // Returns a value to the caller
        return Objects.requireNonNullElse(nbt(), CompoundBinaryTag.empty());
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    default boolean hasNbt() {
        // Returns a value to the caller
        return nbt() != null;
    // End of a block/expression
    }

    /**
     * Returns the block handler.
     *
     * @return the block handler, null if not present
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Annotation for the following element
    @Nullable BlockHandler handler();

    /**
     * Returns the block properties.
     *
     * @return the block properties map
     */
    // Annotation for the following element
    @Unmodifiable
    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    Map<String, String> properties();

    /**
     * Returns the block states as a string.
     * <p>
     * The format is `block_name[property1=value1,property2=value2,...]`.
     * <p>
     * More portable than {@link #stateId()} across game versions, but less efficient.
     * Do not rely on exact string comparison as properties order may vary, use {@link #fromState(String)}.
     *
     * @return the block properties as a string
     * @see #fromState(String)
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    String state();

    /**
     * Returns this block type with default properties, no tags and no handler.
     * As found in the {@link Blocks} listing.
     *
     * @return the default block
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    Block defaultState();

    /**
     * Returns a property value from {@link #properties()}.
     *
     * @param property the property name
     * @return the property value, null if not present (due to an invalid property name)
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Annotation for the following element
    @Nullable String getProperty(String property);

    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    Collection<Block> possibleStates();

    /**
     * Returns the block registry.
     * <p>
     * Registry data is directly linked to {@link #stateId()}.
     *
     * @return the block registry
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    RegistryData.BlockEntry registry();

    // Annotation for the following element
    @Override
    // Start of a method/block
    default Key key() {
        // Returns a value to the caller
        return registry().key();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default int id() {
        // Returns a value to the caller
        return registry().id();
    // End of a block/expression
    }

    // Start of a method/block
    default int stateId() {
        // Returns a value to the caller
        return registry().stateId();
    // End of a block/expression
    }

    // Start of a method/block
    default boolean isAir() {
        // Returns a value to the caller
        return registry().isAir();
    // End of a block/expression
    }

    // Start of a method/block
    default boolean isSolid() {
        // Returns a value to the caller
        return registry().isSolid();
    // End of a block/expression
    }

    /** Whether this block stops entity movement (motion-blocking collision); unlike {@link #isSolid()}, e.g. cobweb is solid but does not block motion. */
    // Start of a method/block
    default boolean blocksMotion() {
        // Returns a value to the caller
        return registry().blocksMotion();
    // End of a block/expression
    }

    // Start of a method/block
    default boolean isLiquid() {
        // Returns a value to the caller
        return registry().isLiquid();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default String translationKey() {
        // Returns a value to the caller
        return registry().translationKey();
    // End of a block/expression
    }

    // Start of a method/block
    default boolean compare(Block block, Comparator comparator) {
        // Returns a value to the caller
        return comparator.test(this, block);
    // End of a block/expression
    }

    // Start of a method/block
    default boolean compare(Block block) {
        // Returns a value to the caller
        return compare(block, Comparator.ID);
    // End of a block/expression
    }

    // Start of a method/block
    static Collection<Block> values() {
        // Returns a value to the caller
        return BlockImpl.REGISTRY.values();
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable Block fromKey(@KeyPattern String key) {
        // Returns a value to the caller
        return fromKey(Key.key(key));
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable Block fromKey(Key key) {
        // Returns a value to the caller
        return BlockImpl.REGISTRY.get(key);
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable Block fromState(String state) {
        // Returns a value to the caller
        return BlockImpl.parseState(state);
    // End of a block/expression
    }

    // Start of a method/block
    static int statesCount() {
        // Returns a value to the caller
        return BlockImpl.statesCount();
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable Block fromStateId(int stateId) {
        // Returns a value to the caller
        return BlockImpl.getState(stateId);
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable Block fromBlockId(int blockId) {
        // Returns a value to the caller
        return BlockImpl.REGISTRY.get(blockId);
    // End of a block/expression
    }

    // Start of a method/block
    static Registry<Block> staticRegistry() {
        // Returns a value to the caller
        return BlockImpl.REGISTRY;
    // End of a block/expression
    }

    // Annotation for the following element
    @FunctionalInterface
    // Type declaration (class/interface/enum/record)
    interface Comparator extends BiPredicate<Block, Block> {
        // Calls a method
        Comparator IDENTITY = (b1, b2) -> b1 == b2;

        // Calls a method
        Comparator ID = (b1, b2) -> b1.id() == b2.id();

        // Calls a method
        Comparator STATE = (b1, b2) -> b1.stateId() == b2.stateId();
    // End of a block/expression
    }

    /**
     * Represents an element which can place blocks at position.
     * <p>
     * Notably used by {@link Instance}, {@link Batch}.
     */
    // Type declaration (class/interface/enum/record)
    interface Setter {
        // Calls a method
        void setBlock(int x, int y, int z, Block block);

        // Start of a method/block
        default void setBlock(Point blockPosition, Block block) {
            // Calls a method
            setBlock(blockPosition.blockX(), blockPosition.blockY(), blockPosition.blockZ(), block);
        // End of a block/expression
        }

        // Start of a method/block
        default void setBlockArea(Area area, Block block) {
            // Loop: repeats a block
            for (BlockVec vec : area) setBlock(vec.blockX(), vec.blockY(), vec.blockZ(), block);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    interface Getter {
        // Annotation for the following element
        @UnknownNullability
        // Calls a method
        Block getBlock(int x, int y, int z, Condition condition);

        // Start of a method/block
        default @UnknownNullability Block getBlock(Point point, Condition condition) {
            // Returns a value to the caller
            return getBlock(point.blockX(), point.blockY(), point.blockZ(), condition);
        // End of a block/expression
        }

        // Start of a method/block
        default Block getBlock(int x, int y, int z) {
            // Returns a value to the caller
            return Objects.requireNonNull(getBlock(x, y, z, Condition.NONE));
        // End of a block/expression
        }

        // Start of a method/block
        default Block getBlock(Point point) {
            // Returns a value to the caller
            return Objects.requireNonNull(getBlock(point, Condition.NONE));
        // End of a block/expression
        }

        /**
         * Represents a hint to retrieve blocks more efficiently.
         * Implementing interfaces do not have to honor this.
         */
        // Type declaration (class/interface/enum/record)
        enum Condition {
            /**
             * Returns a block no matter what.
             * {@link Block#AIR} being the default result.
             */
            // Code statement
            NONE,
            /**
             * Hints that the method should return only if the block is cached.
             * <p>
             * Useful if you are only interested in a block handler or nbt.
             */
            // Code statement
            CACHED,
            /**
             * Hints that we only care about the block type.
             * <p>
             * Useful if you need to retrieve registry information about the block.
             * Be aware that the returned block may not return the proper handler/nbt.
             */
            // Code statement
            TYPE
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
