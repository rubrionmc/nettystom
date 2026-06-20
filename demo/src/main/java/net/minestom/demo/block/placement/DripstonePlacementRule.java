// Package declaration for this file
package net.minestom.demo.block.placement;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import net.minestom.server.instance.block.rule.BlockPlacementRule;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
public class DripstonePlacementRule extends BlockPlacementRule {
    // Assigns a value
    private static final String PROP_VERTICAL_DIRECTION = "vertical_direction"; // Tip, frustum, middle(0 or more), base
    // Assigns a value
    private static final String PROP_THICKNESS = "thickness";

    // Start of a method/block
    public DripstonePlacementRule() {
        // Access to the current/parent object
        super(Block.POINTED_DRIPSTONE);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable Block blockPlace(PlacementState placementState) {
        // Calls a method
        var blockFace = Objects.requireNonNullElse(placementState.blockFace(), BlockFace.TOP);
        // Assigns a value
        var direction = switch (blockFace) {
            // Multiple branching (switch/case)
            case TOP -> "up";
            // Multiple branching (switch/case)
            case BOTTOM -> "down";
            // Multiple branching (switch/case)
            default -> Objects.requireNonNullElse(placementState.cursorPosition(), Vec.ZERO).y() < 0.5 ? "up" : "down";
        // End of a block/expression
        };
        // Calls a method
        var thickness = getThickness(placementState.instance(), placementState.placePosition(), direction.equals("up"));
        // Returns a value to the caller
        return block.withProperties(Map.of(
                // Code statement
                PROP_VERTICAL_DIRECTION, direction,
                // Code statement
                PROP_THICKNESS, thickness
        // Code statement
        ));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Block blockUpdate(UpdateState updateState) {
        // Calls a method
        var direction = updateState.currentBlock().getProperty(PROP_VERTICAL_DIRECTION).equals("up");
        // Calls a method
        var newThickness = getThickness(updateState.instance(), updateState.blockPosition(), direction);
        // Returns a value to the caller
        return updateState.currentBlock().withProperty(PROP_THICKNESS, newThickness);
    // End of a block/expression
    }

    // Start of a method/block
    private String getThickness(Block.Getter instance, Point blockPosition, boolean direction) {
        // Calls a method
        var abovePosition = blockPosition.add(0, direction ? 1 : -1, 0);
        // Calls a method
        var aboveBlock = instance.getBlock(abovePosition, Block.Getter.Condition.TYPE);

        // If there is no dripstone above, it is always a tip
        // Branch: checks a condition
        if (aboveBlock.id() != Block.POINTED_DRIPSTONE.id())
            // Returns a value to the caller
            return "tip";
        // If there is an opposite facing dripstone above, it is always a merged tip
        // Branch: checks a condition
        if ((direction ? "down" : "up").equals(aboveBlock.getProperty(PROP_VERTICAL_DIRECTION)))
            // Returns a value to the caller
            return "tip_merge";

        // If the dripstone above this is a tip, it is a frustum
        // Calls a method
        var aboveThickness = aboveBlock.getProperty(PROP_THICKNESS);
        // Branch: checks a condition
        if ("tip".equals(aboveThickness) || "tip_merge".equals(aboveThickness))
            // Returns a value to the caller
            return "frustum";

        // At this point we know that there is a dripstone above, and that the dripstone is facing the same direction.
        // Calls a method
        var belowPosition = blockPosition.add(0, direction ? -1 : 1, 0);
        // Calls a method
        var belowBlock = instance.getBlock(belowPosition, Block.Getter.Condition.TYPE);

        // If there is no dripstone below, it is always a base
        // Branch: checks a condition
        if (belowBlock.id() != Block.POINTED_DRIPSTONE.id())
            // Returns a value to the caller
            return "base";

        // Otherwise it is a middle
        // Returns a value to the caller
        return "middle";
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int maxUpdateDistance() {
        // Returns a value to the caller
        return 2;
    // End of a block/expression
    }
// End of a block/expression
}
