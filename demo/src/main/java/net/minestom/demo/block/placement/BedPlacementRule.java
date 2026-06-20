// Déclaration du paquet de ce fichier
package net.minestom.demo.block.placement;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.rule.BlockPlacementRule;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Objects;

/**
 * https://gist.github.com/mworzala/0676c28343310458834d70ed29b11a37
 */
// Déclaration de type (classe/interface/enum/record)
public class BedPlacementRule extends BlockPlacementRule {


    // Affecte une valeur
    private static final String PROP_PART = "part";
    // Affecte une valeur
    private static final String PROP_FACING = "facing";

    // Début d'une méthode/d'un bloc
    public BedPlacementRule(Block block) {
        // Accès à l'objet courant/parent
        super(block);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable Block blockPlace(PlacementState placementState) {
        // Appelle une méthode
        var playerPosition = Objects.requireNonNullElse(placementState.playerPosition(), Pos.ZERO);
        // Appelle une méthode
        var facing = BlockFace.fromYaw(playerPosition.yaw());

        //todo bad code using instance directly
        // Embranchement : vérifie une condition
        if (!(placementState.instance() instanceof Instance instance)) return null;

        // Appelle une méthode
        var headPosition = placementState.placePosition().relative(facing);
        // Embranchement : vérifie une condition
        if (!instance.getBlock(headPosition, Block.Getter.Condition.TYPE).isAir())
            // Renvoie une valeur à l'appelant
            return null;

        // Affecte une valeur
        var headBlock = this.block.withProperty(PROP_PART, "head")
                // Appelle une méthode
                .withProperty(PROP_FACING, facing.name().toLowerCase());
        // Appelle une méthode
        instance.setBlock(headPosition, headBlock);

        // Renvoie une valeur à l'appelant
        return headBlock.withProperty(PROP_PART, "foot");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
