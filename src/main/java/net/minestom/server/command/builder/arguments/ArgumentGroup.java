// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments;

// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.parser.CommandParser;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.parser.ValidSyntaxHolder;
// Import d'une classe nécessaire
import net.minestom.server.utils.StringUtils;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public class ArgumentGroup extends Argument<CommandContext> {

    // Affecte une valeur
    public static final int INVALID_ARGUMENTS_ERROR = 1;

    // Instruction de code
    private final Argument<?>[] group;

    // Début d'une méthode/d'un bloc
    public ArgumentGroup(String id, Argument<?>... group) {
        // Accès à l'objet courant/parent
        super(id, true, false);
        // Accès à l'objet courant/parent
        this.group = group;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public CommandContext parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Appelle une méthode
        List<ValidSyntaxHolder> validSyntaxes = new ArrayList<>();
        // Appelle une méthode
        CommandParser.parse(sender, null, group, input.split(StringUtils.SPACE), input, validSyntaxes, null);

        // Appelle une méthode
        CommandContext context = new CommandContext(input);
        // Appelle une méthode
        CommandParser.findMostCorrectSyntax(validSyntaxes, context);
        // Embranchement : vérifie une condition
        if (validSyntaxes.isEmpty()) {
            // Lève une exception
            throw new ArgumentSyntaxException("Invalid arguments", input, INVALID_ARGUMENTS_ERROR);
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return context;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ArgumentParserType parser() {
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public List<Argument<?>> group() {
        // Renvoie une valeur à l'appelant
        return List.of(group);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
