// Package declaration for this file
package net.minestom.server.instance.block.predicate;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockHandler;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.RegistryTag;
// Import of a required class
import net.minestom.server.utils.block.BlockUtils;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.function.Predicate;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.NBT_COMPOUND;

/**
 * <p>A predicate to filter blocks based on their name, properties, and/or nbt.</p>
 *
 * <p>Note: Inline with vanilla, providing none of the filters will match any block.</p>
 *
 * <p>Note: To match the vanilla behavior of comparing block NBT, the NBT predicate
 * will ONLY match data which would be sent to the client eg with
 * {@link BlockHandler#getBlockEntityTags()}. This is relevant because this structure
 * is used for matching adventure mode blocks and must line up with client prediction.</p>
 *
 * @param blocks The block names/tags to match.
 * @param state  The block properties to match.
 * @param nbt    The block nbt to match.
 */
// Type declaration (class/interface/enum/record)
public record BlockPredicate(
        // Annotation for the following element
        @Nullable RegistryTag<Block> blocks,
        // Annotation for the following element
        @Nullable PropertiesPredicate state,
        // Annotation for the following element
        @Nullable CompoundBinaryTag nbt,
        // Code statement
        DataComponentPredicates components
// Start of a method/block
) implements Predicate<Block> {
    /**
     * Matches all blocks.
     */
    // Calls a method
    public static final BlockPredicate ALL = new BlockPredicate(null, null, null);
    /**
     * <p>Matches no blocks.</p>
     *
     * <p>Works based on the property that an exact property will never match a property which doesnt exist on any block.</p>
     */
    // Calls a method
    public static final BlockPredicate NONE = new BlockPredicate(null, new PropertiesPredicate(Map.of("no_such_property", new PropertiesPredicate.ValuePredicate.Exact("never"))), null);

    // Assigns a value
    public static final NetworkBuffer.Type<BlockPredicate> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            RegistryTag.networkType(Registries::blocks).optional(), BlockPredicate::blocks,
            // Code statement
            PropertiesPredicate.NETWORK_TYPE.optional(), BlockPredicate::state,
            // Code statement
            NBT_COMPOUND.optional(), BlockPredicate::nbt,
            // Code statement
            DataComponentPredicates.NETWORK_TYPE, BlockPredicate::components,
            // Code statement
            BlockPredicate::new);
    // Assigns a value
    public static final StructCodec<BlockPredicate> CODEC = StructCodec.struct(
            // Code statement
            "blocks", RegistryTag.codec(Registries::blocks).optional(), BlockPredicate::blocks,
            // Code statement
            "state", PropertiesPredicate.CODEC.optional(), BlockPredicate::state,
            // Code statement
            "nbt", Codec.NBT_COMPOUND.optional(), BlockPredicate::nbt,
            // Code statement
            StructCodec.INLINE, DataComponentPredicates.CODEC, BlockPredicate::components,
            // Code statement
            BlockPredicate::new);

    // Start of a method/block
    public BlockPredicate(RegistryTag<Block> blocks) {
        // Calls a method
        this(blocks, null, null);
    // End of a block/expression
    }

    // Start of a method/block
    public BlockPredicate(Block... blocks) {
        // Calls a method
        this(RegistryTag.direct(blocks));
    // End of a block/expression
    }

    // Start of a method/block
    public BlockPredicate(PropertiesPredicate state) {
        // Calls a method
        this(null, state, null);
    // End of a block/expression
    }

    // Start of a method/block
    public BlockPredicate(CompoundBinaryTag nbt) {
        // Calls a method
        this(null, null, nbt);
    // End of a block/expression
    }

    // Start of a method/block
    public BlockPredicate(@Nullable RegistryTag<Block> blocks, @Nullable PropertiesPredicate state, @Nullable CompoundBinaryTag nbt) {
        // Calls a method
        this(blocks, state, nbt, DataComponentPredicates.EMPTY);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean test(Block block) {
        // Branch: checks a condition
        if (blocks != null && !blocks.contains(block))
            // Returns a value to the caller
            return false;
        // Branch: checks a condition
        if (state != null && !state.test(block))
            // Returns a value to the caller
            return false;
        // Returns a value to the caller
        return nbt == null || Objects.equals(nbt, BlockUtils.extractClientNbt(block));
    // End of a block/expression
    }
// End of a block/expression
}
