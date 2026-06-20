// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentBoolean;
// Import of a required class
import net.minestom.server.command.builder.arguments.number.ArgumentInteger;
// Import of a required class
import net.minestom.server.command.builder.arguments.relative.ArgumentRelativeBlockPosition;
// Import of a required class
import net.minestom.server.command.builder.condition.Conditions;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.instance.batch.BatchOption;
// Import of a required class
import net.minestom.server.instance.batch.RelativeBlockBatch;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.timer.TaskSchedule;
// Import of a required class
import net.minestom.server.utils.location.RelativeVec;


// Type declaration (class/interface/enum/record)
public class DebugGridCommand extends Command {
    // Assigns a value
    private final Argument<RelativeVec> center = new ArgumentRelativeBlockPosition("center")
            // Calls a method
            .setDefaultValue(new RelativeVec(new Vec(0, -1, 0), RelativeVec.CoordinateType.RELATIVE, true, true, true));
    // Assigns a value
    private final Argument<Integer> radius = new ArgumentInteger("radius")
            // Calls a method
            .setDefaultValue(100);
    // Calls a method
    private final Argument<Boolean> replace = new ArgumentBoolean("replace");

    // Start of a method/block
    public DebugGridCommand() {
        // Access to the current/parent object
        super("dg");
        // Calls a method
        setCondition(Conditions::playerOnly);
        // Calls a method
        addSyntax(this::execute, radius, center, replace);
    // End of a block/expression
    }

    // Start of a method/block
    private void execute(CommandSender sender, CommandContext context) {
        // Calls a method
        Player player = (Player) sender;
        // Calls a method
        final Boolean replace = context.get(this.replace);
        // Calls a method
        final RelativeBlockBatch relativeBlockBatch = new RelativeBlockBatch(new BatchOption().setCalculateInverse(replace));
        // Calls a method
        final int radius = context.get(this.radius);
        // Loop: repeats a block
        for (int x = -radius / 2; x < radius / 2; x++) {
            // Loop: repeats a block
            for (int z = -radius / 2; z < radius / 2; z++) {
                // Calls a method
                relativeBlockBatch.setBlock(x, 0, z, ((x % 2 == 0) ^ (z % 2) == 0) ? Block.WHITE_CONCRETE : Block.BLACK_CONCRETE);
            // End of a block/expression
            }
        // End of a block/expression
        }

        //noinspection ConstantConditions
        // Start of a method/block
        relativeBlockBatch.apply(player.getInstance(), context.get(center).from(player), (inverse) -> {
            // Branch: checks a condition
            if (!replace) return;
            // Start of a method/block
            player.getInstance().scheduler().scheduleTask(() -> {
                // Branch: checks a condition
                if (inverse == null) return;
                // Calls a method
                inverse.apply(player.getInstance(), null);
            // Calls a method
            }, TaskSchedule.seconds(1), TaskSchedule.stop());
        // End of a block/expression
        });
    // End of a block/expression
    }
// End of a block/expression
}
