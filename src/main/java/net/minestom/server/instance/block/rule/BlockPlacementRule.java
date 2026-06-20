// Déclaration du paquet de ce fichier
package net.minestom.server.instance.block.rule;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public abstract class BlockPlacementRule {
    // Affecte une valeur
    public static final int DEFAULT_UPDATE_RANGE = 10;

    // Instruction de code
    protected final Block block;

    // Début d'une méthode/d'un bloc
    protected BlockPlacementRule(Block block) {
        // Accès à l'objet courant/parent
        this.block = block;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Called when the block state id can be updated (for instance if a neighbour block changed).
     * This is first called on a newly placed block, and then this is called for all neighbors of the block
     *
     * @param updateState The current parameters to the block update
     * @return the updated block
     */
    // Début d'une méthode/d'un bloc
    public Block blockUpdate(UpdateState updateState) {
        // Renvoie une valeur à l'appelant
        return updateState.currentBlock();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Called when the block is placed.
     * It is recommended that you only set up basic properties on the block for this placement, such as determining facing, etc
     *
     * @param placementState The current parameters to the block placement
     * @return the block to place, {@code null} to cancel
     */
    // Appelle une méthode
    public abstract @Nullable Block blockPlace(PlacementState placementState);

    // Début d'une méthode/d'un bloc
    public boolean isSelfReplaceable(Replacement replacement) {
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Block getBlock() {
        // Renvoie une valeur à l'appelant
        return block;
    // Fin d'un bloc/d'une expression
    }

    /**
     * The max distance where a block update can be triggered. It is not based on block, so if the value is 3 and a completely
     * different block updates 3 blocks away it could still trigger an update.
     */
    // Début d'une méthode/d'un bloc
    public int maxUpdateDistance() {
        // Renvoie une valeur à l'appelant
        return DEFAULT_UPDATE_RANGE;
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record PlacementState(
            // Instruction de code
            Block.Getter instance,
            // Instruction de code
            Block block,
            // Annotation pour l'élément suivant
            @Nullable BlockFace blockFace,
            // Instruction de code
            Point placePosition,
            // Annotation pour l'élément suivant
            @Nullable Point cursorPosition,
            // Annotation pour l'élément suivant
            @Nullable Pos playerPosition,
            // Annotation pour l'élément suivant
            @Nullable ItemStack usedItemStack,
            // Instruction de code
            boolean isPlayerShifting
    // Début d'une méthode/d'un bloc
    ) {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record UpdateState(Block.Getter instance,
                              // Instruction de code
                              Point blockPosition,
                              // Instruction de code
                              Block currentBlock,
                              // Début d'une méthode/d'un bloc
                              BlockFace fromFace) {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Replacement(
            // Instruction de code
            Block block,
            // Instruction de code
            BlockFace blockFace,
            // Instruction de code
            Point cursorPosition,
            /**
			 * Whether or not the placement position is offset from the clicked block
			 * position.
			 */
            // Instruction de code
            boolean isOffset,
            // Instruction de code
            Material material
    // Début d'une méthode/d'un bloc
    ) {
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
