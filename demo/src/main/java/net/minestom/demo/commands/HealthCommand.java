// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.command.builder.arguments.number.ArgumentNumber;
// Import of a required class
import net.minestom.server.command.builder.condition.Conditions;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import of a required class
import net.minestom.server.entity.Player;

// Type declaration (class/interface/enum/record)
public class HealthCommand extends Command {

    // Start of a method/block
    public HealthCommand() {
        // Access to the current/parent object
        super("health");

        // Calls a method
        setCondition(Conditions::playerOnly);

        // Calls a method
        setDefaultExecutor(this::defaultExecutor);

        // Calls a method
        var modeArg = ArgumentType.Word("mode").from("set", "add");

        // Calls a method
        var valueArg = ArgumentType.Integer("value").between(0, 100);

        // Calls a method
        setArgumentCallback(this::onModeError, modeArg);
        // Calls a method
        setArgumentCallback(this::onValueError, valueArg);

        // Calls a method
        addSyntax(this::sendSuggestionMessage, modeArg);
        // Calls a method
        addSyntax(this::onHealthCommand, modeArg, valueArg);
    // End of a block/expression
    }

    // Start of a method/block
    private void defaultExecutor(CommandSender sender, CommandContext context) {
        // Calls a method
        sender.sendMessage(Component.text("Correct usage: health set|add <number>"));
    // End of a block/expression
    }

    // Start of a method/block
    private void onModeError(CommandSender sender, ArgumentSyntaxException exception) {
        // Calls a method
        sender.sendMessage(Component.text("SYNTAX ERROR: '" + exception.getInput() + "' should be replaced by 'set' or 'add'"));
    // End of a block/expression
    }

    // Start of a method/block
    private void onValueError(CommandSender sender, ArgumentSyntaxException exception) {
        // Calls a method
        final int error = exception.getErrorCode();
        // Calls a method
        final String input = exception.getInput();
        // Multiple branching (switch/case)
        switch (error) {
            // Multiple branching (switch/case)
            case ArgumentNumber.NOT_NUMBER_ERROR:
                // Calls a method
                sender.sendMessage(Component.text("SYNTAX ERROR: '" + input + "' isn't a number!"));
                // Breaks out of the loop/block
                break;
            // Multiple branching (switch/case)
            case ArgumentNumber.TOO_LOW_ERROR:
            // Multiple branching (switch/case)
            case ArgumentNumber.TOO_HIGH_ERROR:
                // Calls a method
                sender.sendMessage(Component.text("SYNTAX ERROR: " + input + " is not between 0 and 100"));
                // Breaks out of the loop/block
                break;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void sendSuggestionMessage(CommandSender sender, CommandContext context) {
        // Calls a method
        sender.sendMessage(Component.text("/health " + context.get("mode") + " [Integer]"));
    // End of a block/expression
    }

    // Start of a method/block
    private void onHealthCommand(CommandSender sender, CommandContext context) {
        // Calls a method
        final Player player = (Player) sender;
        // Calls a method
        final String mode = context.get("mode");
        // Calls a method
        final int value = context.get("value");

        // Multiple branching (switch/case)
        switch (mode.toLowerCase()) {
            // Multiple branching (switch/case)
            case "set":
                // Calls a method
                player.setHealth(value);
                // Breaks out of the loop/block
                break;
            // Multiple branching (switch/case)
            case "add":
                // Calls a method
                player.setHealth(player.getHealth() + value);
                // Breaks out of the loop/block
                break;
        // End of a block/expression
        }

        // Calls a method
        player.sendMessage(Component.text("You have now " + player.getHealth() + " health"));
    // End of a block/expression
    }

// End of a block/expression
}