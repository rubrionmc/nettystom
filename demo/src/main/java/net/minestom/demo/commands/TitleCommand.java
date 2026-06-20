// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.title.Title;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.condition.Conditions;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;

// Déclaration de type (classe/interface/enum/record)
public class TitleCommand extends Command {
    // Début d'une méthode/d'un bloc
    public TitleCommand() {
        // Accès à l'objet courant/parent
        super("title");
        // Appelle une méthode
        setDefaultExecutor((source, args) -> source.sendMessage(Component.text("Unknown syntax (note: title must be quoted)")));
        // Appelle une méthode
        setCondition(Conditions::playerOnly);

        // Appelle une méthode
        var content = ArgumentType.String("content");

        // Appelle une méthode
        addSyntax(this::handleTitle, content);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void handleTitle(CommandSender source, CommandContext context) {
        // Affecte une valeur
        Player player = (Player) source;
        // Appelle une méthode
        String titleContent = context.get("content");

        // Appelle une méthode
        player.showTitle(Title.title(Component.text(titleContent), Component.empty(), Title.DEFAULT_TIMES));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
