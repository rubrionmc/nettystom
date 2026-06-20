// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.kyori.adventure.inventory.Book;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import net.minestom.server.command.builder.condition.Conditions;
// Import of a required class
import net.minestom.server.entity.Player;

// Type declaration (class/interface/enum/record)
public class BookCommand extends Command {
    // Start of a method/block
    public BookCommand() {
        // Access to the current/parent object
        super("book");

        // Calls a method
        setCondition(Conditions::playerOnly);

        // Calls a method
        setDefaultExecutor(this::execute);
    // End of a block/expression
    }

    // Start of a method/block
    private void execute(CommandSender sender, CommandContext context) {
        // Calls a method
        Player player = (Player) sender;

        // Code statement
        player.openBook(Book.builder()
                // Code statement
                .author(Component.text(player.getUsername()))
                // Code statement
                .title(Component.text(player.getUsername() + "'s Book"))
                // Code statement
                .pages(Component.text("Page one", NamedTextColor.RED),
                        // Code statement
                        Component.text("Page two", NamedTextColor.GREEN),
                        // Calls a method
                        Component.text("Page three", NamedTextColor.BLUE)));
    // End of a block/expression
    }
// End of a block/expression
}
