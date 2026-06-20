// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.utils.location.RelativeVec;

// Type declaration (class/interface/enum/record)
public class WorldBorderCommand extends Command {
    // Start of a method/block
    public WorldBorderCommand() {
        // Access to the current/parent object
        super("worldborder");

        // Assigns a value
        var diameterOptions = ArgumentType.Word("diameterOptions").from("set", "add"); // "center", "warning-time", "warning-distance"
        // Calls a method
        var sizeInBlocks = ArgumentType.Integer("sizeInBlocks").setDefaultValue(0);
        // Calls a method
        var timeInSeconds = ArgumentType.Double("timeInSeconds").setDefaultValue(0.0);

        // Calls a method
        var centerOption = ArgumentType.Word("centerOption").from("center");
        // Calls a method
        var centerCoordinate = ArgumentType.RelativeVec2("coordinate");

        // Calls a method
        var warningTimeOption = ArgumentType.Word("warningTimeOption").from("warning-time");

        // Calls a method
        var warningDistanceOption = ArgumentType.Word("warningDistanceOption").from("warning-distance");

        // Calls a method
        addSyntax(this::handleDiameter, diameterOptions, sizeInBlocks, timeInSeconds);
        // Calls a method
        addSyntax(this::handleCenter, centerOption, centerCoordinate);
        // Calls a method
        addSyntax(this::handleWarningTime, warningTimeOption, timeInSeconds);
        // Calls a method
        addSyntax(this::handleWarningDistance, warningDistanceOption, sizeInBlocks);
    // End of a block/expression
    }

    // Start of a method/block
    private void handleDiameter(CommandSender source, CommandContext context) {
        // Calls a method
        Player player = (Player) source;
        // Calls a method
        int size = context.get("sizeInBlocks");
        // Calls a method
        double timeInSeconds = context.get("timeInSeconds");
        // Assigns a value
        double diameter = size;
        // Branch: checks a condition
        if ((context.get("diameterOptions")).equals("add")) {
            // Calls a method
            diameter += player.getInstance().getWorldBorder().diameter();
        // End of a block/expression
        }

        // Calls a method
        player.getInstance().setWorldBorder(player.getInstance().getWorldBorder().withDiameter(diameter), timeInSeconds);
    // End of a block/expression
    }

    // Start of a method/block
    private void handleCenter(CommandSender source, CommandContext context) {
        // Calls a method
        Player player = (Player) source;
        // Calls a method
        RelativeVec coords = context.get("coordinate");
        // Calls a method
        Vec vec = coords.from(new Pos(0, 0, 0));
        // Calls a method
        player.getInstance().setWorldBorder(player.getInstance().getWorldBorder().withCenter(vec.x(), vec.z()));
    // End of a block/expression
    }

    // Start of a method/block
    private void handleWarningTime(CommandSender source, CommandContext context) {
        // Calls a method
        Player player = (Player) source;
        // Calls a method
        double timeInSeconds = context.get("timeInSeconds");
        // Calls a method
        player.getInstance().setWorldBorder(player.getInstance().getWorldBorder().withWarningTime((int)timeInSeconds));
    // End of a block/expression
    }

    // Start of a method/block
    private void handleWarningDistance(CommandSender source, CommandContext context) {
        // Calls a method
        Player player = (Player) source;
        // Calls a method
        int sizeInBlocks = context.get("sizeInBlocks");
        // Calls a method
        player.getInstance().setWorldBorder(player.getInstance().getWorldBorder().withWarningDistance(sizeInBlocks));
    // End of a block/expression
    }
// End of a block/expression
}
