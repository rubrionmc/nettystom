// Déclaration du paquet de ce fichier
package net.minestom.demo.block.placement;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.rule.BlockPlacementRule;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
public class DripstonePlacementRule extends BlockPlacementRule {
    // Affecte une valeur
    private static final String PROP_VERTICAL_DIRECTION = "vertical_direction"; // Tip, frustum, middle(0 or more), base
    // Affecte une valeur
    private static final String PROP_THICKNESS = "thickness";

    // Début d'une méthode/d'un bloc
    public DripstonePlacementRule() {
        // Accès à l'objet courant/parent
        super(Block.POINTED_DRIPSTONE);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable Block blockPlace(PlacementState placementState) {
        // Appelle une méthode
        var blockFace = Objects.requireNonNullElse(placementState.blockFace(), BlockFace.TOP);
        // Affecte une valeur
        var direction = switch (blockFace) {
            // Embranchement multiple (switch/case)
            case TOP -> "up";
            // Embranchement multiple (switch/case)
            case BOTTOM -> "down";
            // Embranchement multiple (switch/case)
            default -> Objects.requireNonNullElse(placementState.cursorPosition(), Vec.ZERO).y() < 0.5 ? "up" : "down";
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        var thickness = getThickness(placementState.instance(), placementState.placePosition(), direction.equals("up"));
        // Renvoie une valeur à l'appelant
        return block.withProperties(Map.of(
                // Instruction de code
                PROP_VERTICAL_DIRECTION, direction,
                // Instruction de code
                PROP_THICKNESS, thickness
        // Instruction de code
        ));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Block blockUpdate(UpdateState updateState) {
        // Appelle une méthode
        var direction = updateState.currentBlock().getProperty(PROP_VERTICAL_DIRECTION).equals("up");
        // Appelle une méthode
        var newThickness = getThickness(updateState.instance(), updateState.blockPosition(), direction);
        // Renvoie une valeur à l'appelant
        return updateState.currentBlock().withProperty(PROP_THICKNESS, newThickness);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private String getThickness(Block.Getter instance, Point blockPosition, boolean direction) {
        // Appelle une méthode
        var abovePosition = blockPosition.add(0, direction ? 1 : -1, 0);
        // Appelle une méthode
        var aboveBlock = instance.getBlock(abovePosition, Block.Getter.Condition.TYPE);

        // If there is no dripstone above, it is always a tip
        // Embranchement : vérifie une condition
        if (aboveBlock.id() != Block.POINTED_DRIPSTONE.id())
            // Renvoie une valeur à l'appelant
            return "tip";
        // If there is an opposite facing dripstone above, it is always a merged tip
        // Embranchement : vérifie une condition
        if ((direction ? "down" : "up").equals(aboveBlock.getProperty(PROP_VERTICAL_DIRECTION)))
            // Renvoie une valeur à l'appelant
            return "tip_merge";

        // If the dripstone above this is a tip, it is a frustum
        // Appelle une méthode
        var aboveThickness = aboveBlock.getProperty(PROP_THICKNESS);
        // Embranchement : vérifie une condition
        if ("tip".equals(aboveThickness) || "tip_merge".equals(aboveThickness))
            // Renvoie une valeur à l'appelant
            return "frustum";

        // At this point we know that there is a dripstone above, and that the dripstone is facing the same direction.
        // Appelle une méthode
        var belowPosition = blockPosition.add(0, direction ? -1 : 1, 0);
        // Appelle une méthode
        var belowBlock = instance.getBlock(belowPosition, Block.Getter.Condition.TYPE);

        // If there is no dripstone below, it is always a base
        // Embranchement : vérifie une condition
        if (belowBlock.id() != Block.POINTED_DRIPSTONE.id())
            // Renvoie une valeur à l'appelant
            return "base";

        // Otherwise it is a middle
        // Renvoie une valeur à l'appelant
        return "middle";
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int maxUpdateDistance() {
        // Renvoie une valeur à l'appelant
        return 2;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
