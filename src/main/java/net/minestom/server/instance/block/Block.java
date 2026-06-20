// Déclaration du paquet de ce fichier
package net.minestom.server.instance.block;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.KeyPattern;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.Result;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.kyori.adventure.translation.Translatable;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Area;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.BlockVec;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.batch.Batch;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registry;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import net.minestom.server.registry.StaticProtocolObject;
// Import d'une classe nécessaire
import net.minestom.server.tag.Tag;
// Import d'une classe nécessaire
import net.minestom.server.tag.TagReadable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Unmodifiable;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.function.BiPredicate;

/**
 * Represents a block that can be placed anywhere.
 * Block objects are expected to be reusable and therefore do not
 * retain placement data (e.g. block position)
 * <p>
 * Implementations are expected to be immutable.
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface Block extends StaticProtocolObject<Block>, TagReadable, Blocks, Translatable permits BlockImpl {

    // Appelle une méthode
    NetworkBuffer.Type<Block> ID_NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(Block::fromBlockId, Block::id);
    // Appelle une méthode
    NetworkBuffer.Type<Block> STATE_NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(Block::fromStateId, Block::stateId);

    /**
     * Codec for blocks states as strings.
     * Format: <code>"minecraft:x[a=y,b=z]"</code>
     */
    // Affecte une valeur
    Codec<Block> STATE_CODEC = Codec.STRING.transform(state -> Objects.requireNonNull(
            // Appelle une méthode
            Block.fromState(state), () -> "not a block state: " + state), Block::state);

    /**
     * Codec for block states as a map.
     * Format: <code>{Name:"minecraft:x",Properties:{a:"y",b:"z"}}</code>
     */
    // Affecte une valeur
    Codec<Block> STATE_STRUCT_CODEC = new StructCodec<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<Block> decodeFromMap(Transcoder<D> coder, Transcoder.MapLike<D> map) {
            // Appelle une méthode
            Result<Block> blockResult = map.getValue("Name").map(coder::getString).mapResult(Block::fromKey);
            // Embranchement : vérifie une condition
            if (!(blockResult instanceof Result.Ok(Block block)))
                // Renvoie une valeur à l'appelant
                return blockResult.cast();
            // Appelle une méthode
            Result<Transcoder.MapLike<D>> propertiesResult = map.getValue("Properties").map(coder::getMap);
            // Embranchement : vérifie une condition
            if (!(propertiesResult instanceof Result.Ok(Transcoder.MapLike<D> properties)))
                // properties are optional
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(block);
            // Boucle : répète un bloc
            for (String key : properties.keys()) {
                // Appelle une méthode
                Result<String> valueResult = properties.getValue(key).map(coder::getString);
                // Embranchement : vérifie une condition
                if (!(valueResult instanceof Result.Ok(String mapValue))) {
                    // Renvoie une valeur à l'appelant
                    return new Result.Error<>("No string value found for property " + key + " in block state");
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                block = block.withProperty(key, mapValue);
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(block);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encodeToMap(Transcoder<D> coder, Block value, Transcoder.MapBuilder<D> map) {
            // Embranchement : vérifie une condition
            if (value == null) return new Result.Error<>("null");
            // Appelle une méthode
            map.put("Name", coder.createString(value.key().asMinimalString()));
            // Embranchement : vérifie une condition
            if (value.properties().isEmpty()) {
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(map.build());
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            Map<String, String> defaultProperties = value.defaultState().properties();
            // Appelle une méthode
            Transcoder.MapBuilder<D> propertiesBuilder = coder.createMap();
            // Affecte une valeur
            boolean nonDefaultPropertyExists = false;
            // Boucle : répète un bloc
            for (Map.Entry<String, String> entry : value.properties().entrySet()) {
                // Embranchement : vérifie une condition
                if (defaultProperties.getOrDefault(entry.getKey(), "").equals(entry.getValue()))
                    // Passe à l'itération suivante de la boucle
                    continue; // Skip default values
                // Appelle une méthode
                propertiesBuilder.put(entry.getKey(), coder.createString(entry.getValue()));
                // Affecte une valeur
                nonDefaultPropertyExists = true;
            // Fin d'un bloc/d'une expression
            }
            // Embranchement : vérifie une condition
            if (nonDefaultPropertyExists) {
                // Appelle une méthode
                map.put("Properties", propertiesBuilder.build());
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(map.build());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    /**
     * Creates a new block with the the property {@code property} sets to {@code value}.
     *
     * @param property the property name
     * @param value    the property value
     * @return a new block with its property changed
     * @throws IllegalArgumentException if the property or value are invalid
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
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
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Block withProperties(Map<String, String> properties);

    /**
     * Creates a new block with a tag modified.
     *
     * @param tag   the tag to modify
     * @param value the tag value, null to remove
     * @param <T>   the tag type
     * @return a new block with the modified tag
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    <T> Block withTag(Tag<T> tag, @Nullable T value);

    /**
     * Creates a new block with different nbt data.
     *
     * @param compound the new block nbt, null to remove
     * @return a new block with different nbt
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Block withNbt(@Nullable CompoundBinaryTag compound);

    /**
     * Creates a new block with the specified {@link BlockHandler handler}.
     *
     * @param handler the new block handler, null to remove
     * @return a new block with the specified handler
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Block withHandler(@Nullable BlockHandler handler);

    /**
     * Returns an unmodifiable view to the block nbt.
     * <p>
     * Be aware that {@link Tag tags} directly affect the block nbt.
     *
     * @return the block nbt, null if not present
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Annotation pour l'élément suivant
    @Nullable CompoundBinaryTag nbt();

    /**
     * Returns an unmodifiable view of the block nbt or an empty compound.
     *
     * @return the block nbt or an empty compound if not present
     */
    // Début d'une méthode/d'un bloc
    default CompoundBinaryTag nbtOrEmpty() {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNullElse(nbt(), CompoundBinaryTag.empty());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default boolean hasNbt() {
        // Renvoie une valeur à l'appelant
        return nbt() != null;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the block handler.
     *
     * @return the block handler, null if not present
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Annotation pour l'élément suivant
    @Nullable BlockHandler handler();

    /**
     * Returns the block properties.
     *
     * @return the block properties map
     */
    // Annotation pour l'élément suivant
    @Unmodifiable
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
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
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    String state();

    /**
     * Returns this block type with default properties, no tags and no handler.
     * As found in the {@link Blocks} listing.
     *
     * @return the default block
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Block defaultState();

    /**
     * Returns a property value from {@link #properties()}.
     *
     * @param property the property name
     * @return the property value, null if not present (due to an invalid property name)
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Annotation pour l'élément suivant
    @Nullable String getProperty(String property);

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Collection<Block> possibleStates();

    /**
     * Returns the block registry.
     * <p>
     * Registry data is directly linked to {@link #stateId()}.
     *
     * @return the block registry
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    RegistryData.BlockEntry registry();

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default Key key() {
        // Renvoie une valeur à l'appelant
        return registry().key();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default int id() {
        // Renvoie une valeur à l'appelant
        return registry().id();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default int stateId() {
        // Renvoie une valeur à l'appelant
        return registry().stateId();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default boolean isAir() {
        // Renvoie une valeur à l'appelant
        return registry().isAir();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default boolean isSolid() {
        // Renvoie une valeur à l'appelant
        return registry().isSolid();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default boolean isLiquid() {
        // Renvoie une valeur à l'appelant
        return registry().isLiquid();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default String translationKey() {
        // Renvoie une valeur à l'appelant
        return registry().translationKey();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default boolean compare(Block block, Comparator comparator) {
        // Renvoie une valeur à l'appelant
        return comparator.test(this, block);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default boolean compare(Block block) {
        // Renvoie une valeur à l'appelant
        return compare(block, Comparator.ID);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Collection<Block> values() {
        // Renvoie une valeur à l'appelant
        return BlockImpl.REGISTRY.values();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable Block fromKey(@KeyPattern String key) {
        // Renvoie une valeur à l'appelant
        return fromKey(Key.key(key));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable Block fromKey(Key key) {
        // Renvoie une valeur à l'appelant
        return BlockImpl.REGISTRY.get(key);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable Block fromState(String state) {
        // Renvoie une valeur à l'appelant
        return BlockImpl.parseState(state);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static int statesCount() {
        // Renvoie une valeur à l'appelant
        return BlockImpl.statesCount();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable Block fromStateId(int stateId) {
        // Renvoie une valeur à l'appelant
        return BlockImpl.getState(stateId);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable Block fromBlockId(int blockId) {
        // Renvoie une valeur à l'appelant
        return BlockImpl.REGISTRY.get(blockId);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Registry<Block> staticRegistry() {
        // Renvoie une valeur à l'appelant
        return BlockImpl.REGISTRY;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    interface Comparator extends BiPredicate<Block, Block> {
        // Appelle une méthode
        Comparator IDENTITY = (b1, b2) -> b1 == b2;

        // Appelle une méthode
        Comparator ID = (b1, b2) -> b1.id() == b2.id();

        // Appelle une méthode
        Comparator STATE = (b1, b2) -> b1.stateId() == b2.stateId();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Represents an element which can place blocks at position.
     * <p>
     * Notably used by {@link Instance}, {@link Batch}.
     */
    // Déclaration de type (classe/interface/enum/record)
    interface Setter {
        // Appelle une méthode
        void setBlock(int x, int y, int z, Block block);

        // Début d'une méthode/d'un bloc
        default void setBlock(Point blockPosition, Block block) {
            // Appelle une méthode
            setBlock(blockPosition.blockX(), blockPosition.blockY(), blockPosition.blockZ(), block);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default void setBlockArea(Area area, Block block) {
            // Boucle : répète un bloc
            for (BlockVec vec : area) setBlock(vec.blockX(), vec.blockY(), vec.blockZ(), block);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    interface Getter {
        // Annotation pour l'élément suivant
        @UnknownNullability
        // Appelle une méthode
        Block getBlock(int x, int y, int z, Condition condition);

        // Début d'une méthode/d'un bloc
        default @UnknownNullability Block getBlock(Point point, Condition condition) {
            // Renvoie une valeur à l'appelant
            return getBlock(point.blockX(), point.blockY(), point.blockZ(), condition);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default Block getBlock(int x, int y, int z) {
            // Renvoie une valeur à l'appelant
            return Objects.requireNonNull(getBlock(x, y, z, Condition.NONE));
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default Block getBlock(Point point) {
            // Renvoie une valeur à l'appelant
            return Objects.requireNonNull(getBlock(point, Condition.NONE));
        // Fin d'un bloc/d'une expression
        }

        /**
         * Represents a hint to retrieve blocks more efficiently.
         * Implementing interfaces do not have to honor this.
         */
        // Déclaration de type (classe/interface/enum/record)
        enum Condition {
            /**
             * Returns a block no matter what.
             * {@link Block#AIR} being the default result.
             */
            // Instruction de code
            NONE,
            /**
             * Hints that the method should return only if the block is cached.
             * <p>
             * Useful if you are only interested in a block handler or nbt.
             */
            // Instruction de code
            CACHED,
            /**
             * Hints that we only care about the block type.
             * <p>
             * Useful if you need to retrieve registry information about the block.
             * Be aware that the returned block may not return the proper handler/nbt.
             */
            // Instruction de code
            TYPE
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
