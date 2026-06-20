// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.minestom.demo.block.TestBlockHandler;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.arguments.minecraft.ArgumentBlockState;
// Import of a required class
import net.minestom.server.command.builder.arguments.relative.ArgumentRelativeBlockPosition;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.instance.block.Block;

// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.BlockState;
// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.RelativeBlockPosition;

// Type declaration (class/interface/enum/record)
public class SetBlockCommand extends Command {
    // Start of a method/block
    public SetBlockCommand() {
        // Access to the current/parent object
        super("setblock");

        // Calls a method
        final ArgumentRelativeBlockPosition position = RelativeBlockPosition("position");
        // Calls a method
        final ArgumentBlockState block = BlockState("block");

        // Start of a method/block
        addSyntax((sender, context) -> {
            // Calls a method
            final Player player = (Player) sender;

            // Calls a method
            Block blockToPlace = context.get(block);
            // Branch: checks a condition
            if (blockToPlace.stateId() == Block.GOLD_BLOCK.stateId())
                // Calls a method
                blockToPlace = blockToPlace.withHandler(TestBlockHandler.INSTANCE);

            // Calls a method
            player.getInstance().setBlock(context.get(position).from(player), blockToPlace);
        // Code statement
        }, position, block);
    // End of a block/expression
    }
// End of a block/expression
}
