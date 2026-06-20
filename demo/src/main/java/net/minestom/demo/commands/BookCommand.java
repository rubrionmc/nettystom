// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.kyori.adventure.inventory.Book;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.condition.Conditions;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;

// Déclaration de type (classe/interface/enum/record)
public class BookCommand extends Command {
    // Début d'une méthode/d'un bloc
    public BookCommand() {
        // Accès à l'objet courant/parent
        super("book");

        // Appelle une méthode
        setCondition(Conditions::playerOnly);

        // Appelle une méthode
        setDefaultExecutor(this::execute);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void execute(CommandSender sender, CommandContext context) {
        // Appelle une méthode
        Player player = (Player) sender;

        // Instruction de code
        player.openBook(Book.builder()
                // Instruction de code
                .author(Component.text(player.getUsername()))
                // Instruction de code
                .title(Component.text(player.getUsername() + "'s Book"))
                // Instruction de code
                .pages(Component.text("Page one", NamedTextColor.RED),
                        // Instruction de code
                        Component.text("Page two", NamedTextColor.GREEN),
                        // Appelle une méthode
                        Component.text("Page three", NamedTextColor.BLUE)));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
