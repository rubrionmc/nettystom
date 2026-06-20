// Déclaration du paquet de ce fichier
package net.minestom.server.instance.block.predicate;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockHandler;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTag;
// Import d'une classe nécessaire
import net.minestom.server.utils.block.BlockUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.function.Predicate;

// Import statique d'un membre
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
// Déclaration de type (classe/interface/enum/record)
public record BlockPredicate(
        // Annotation pour l'élément suivant
        @Nullable RegistryTag<Block> blocks,
        // Annotation pour l'élément suivant
        @Nullable PropertiesPredicate state,
        // Annotation pour l'élément suivant
        @Nullable CompoundBinaryTag nbt,
        // Instruction de code
        DataComponentPredicates components
// Début d'une méthode/d'un bloc
) implements Predicate<Block> {
    /**
     * Matches all blocks.
     */
    // Appelle une méthode
    public static final BlockPredicate ALL = new BlockPredicate(null, null, null);
    /**
     * <p>Matches no blocks.</p>
     *
     * <p>Works based on the property that an exact property will never match a property which doesnt exist on any block.</p>
     */
    // Appelle une méthode
    public static final BlockPredicate NONE = new BlockPredicate(null, new PropertiesPredicate(Map.of("no_such_property", new PropertiesPredicate.ValuePredicate.Exact("never"))), null);

    // Affecte une valeur
    public static final NetworkBuffer.Type<BlockPredicate> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            RegistryTag.networkType(Registries::blocks).optional(), BlockPredicate::blocks,
            // Instruction de code
            PropertiesPredicate.NETWORK_TYPE.optional(), BlockPredicate::state,
            // Instruction de code
            NBT_COMPOUND.optional(), BlockPredicate::nbt,
            // Instruction de code
            DataComponentPredicates.NETWORK_TYPE, BlockPredicate::components,
            // Instruction de code
            BlockPredicate::new);
    // Affecte une valeur
    public static final StructCodec<BlockPredicate> CODEC = StructCodec.struct(
            // Instruction de code
            "blocks", RegistryTag.codec(Registries::blocks).optional(), BlockPredicate::blocks,
            // Instruction de code
            "state", PropertiesPredicate.CODEC.optional(), BlockPredicate::state,
            // Instruction de code
            "nbt", Codec.NBT_COMPOUND.optional(), BlockPredicate::nbt,
            // Instruction de code
            StructCodec.INLINE, DataComponentPredicates.CODEC, BlockPredicate::components,
            // Instruction de code
            BlockPredicate::new);

    // Début d'une méthode/d'un bloc
    public BlockPredicate(RegistryTag<Block> blocks) {
        // Appelle une méthode
        this(blocks, null, null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BlockPredicate(Block... blocks) {
        // Appelle une méthode
        this(RegistryTag.direct(blocks));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BlockPredicate(PropertiesPredicate state) {
        // Appelle une méthode
        this(null, state, null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BlockPredicate(CompoundBinaryTag nbt) {
        // Appelle une méthode
        this(null, null, nbt);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BlockPredicate(@Nullable RegistryTag<Block> blocks, @Nullable PropertiesPredicate state, @Nullable CompoundBinaryTag nbt) {
        // Appelle une méthode
        this(blocks, state, nbt, DataComponentPredicates.EMPTY);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean test(Block block) {
        // Embranchement : vérifie une condition
        if (blocks != null && !blocks.contains(block))
            // Renvoie une valeur à l'appelant
            return false;
        // Embranchement : vérifie une condition
        if (state != null && !state.test(block))
            // Renvoie une valeur à l'appelant
            return false;
        // Renvoie une valeur à l'appelant
        return nbt == null || Objects.equals(nbt, BlockUtils.extractClientNbt(block));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
