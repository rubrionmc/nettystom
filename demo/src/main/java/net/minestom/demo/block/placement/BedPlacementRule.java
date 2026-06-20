// Package declaration for this file
package net.minestom.demo.block.placement;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import net.minestom.server.instance.block.rule.BlockPlacementRule;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Objects;

/**
 * <a href="https://gist.github.com/mworzala/0676c28343310458834d70ed29b11a37">...</a>
 */
// Type declaration (class/interface/enum/record)
public class BedPlacementRule extends BlockPlacementRule {


    // Assigns a value
    private static final String PROP_PART = "part";
    // Assigns a value
    private static final String PROP_FACING = "facing";

    // Start of a method/block
    public BedPlacementRule(Block block) {
        // Access to the current/parent object
        super(block);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable Block blockPlace(PlacementState placementState) {
        // Calls a method
        var playerPosition = Objects.requireNonNullElse(placementState.playerPosition(), Pos.ZERO);
        // Calls a method
        var facing = BlockFace.fromYaw(playerPosition.yaw());

        //todo bad code using instance directly
        // Branch: checks a condition
        if (!(placementState.instance() instanceof Instance instance)) return null;

        // Calls a method
        var headPosition = placementState.placePosition().relative(facing);
        // Branch: checks a condition
        if (!instance.getBlock(headPosition, Block.Getter.Condition.TYPE).isAir())
            // Returns a value to the caller
            return null;

        // Assigns a value
        var headBlock = this.block.withProperty(PROP_PART, "head")
                // Calls a method
                .withProperty(PROP_FACING, facing.name().toLowerCase());
        // Calls a method
        instance.setBlock(headPosition, headBlock);

        // Returns a value to the caller
        return headBlock.withProperty(PROP_PART, "foot");
    // End of a block/expression
    }
// End of a block/expression
}
