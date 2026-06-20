// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.title.Title;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.command.builder.condition.Conditions;
// Import of a required class
import net.minestom.server.entity.Player;

// Type declaration (class/interface/enum/record)
public class TitleCommand extends Command {
    // Start of a method/block
    public TitleCommand() {
        // Access to the current/parent object
        super("title");
        // Calls a method
        setDefaultExecutor((source, args) -> source.sendMessage(Component.text("Unknown syntax (note: title must be quoted)")));
        // Calls a method
        setCondition(Conditions::playerOnly);

        // Calls a method
        var content = ArgumentType.String("content");

        // Calls a method
        addSyntax(this::handleTitle, content);
    // End of a block/expression
    }

    // Start of a method/block
    private void handleTitle(CommandSender source, CommandContext context) {
        // Calls a method
        Player player = (Player) source;
        // Calls a method
        String titleContent = context.get("content");

        // Calls a method
        player.showTitle(Title.title(Component.text(titleContent), Component.empty(), Title.DEFAULT_TIMES));
    // End of a block/expression
    }
// End of a block/expression
}
