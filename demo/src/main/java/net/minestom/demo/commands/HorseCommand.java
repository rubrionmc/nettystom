// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
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
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityCreature;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.HorseMeta;

// Import d'une classe nécessaire
import java.util.Locale;
// Import d'une classe nécessaire
import java.util.stream.Collectors;
// Import d'une classe nécessaire
import java.util.stream.Stream;

// Déclaration de type (classe/interface/enum/record)
public class HorseCommand extends Command {

    // Début d'une méthode/d'un bloc
    public HorseCommand() {
        // Accès à l'objet courant/parent
        super("horse");
        // Appelle une méthode
        setCondition(Conditions::playerOnly);
        // Appelle une méthode
        setDefaultExecutor(this::defaultExecutor);
        // Appelle une méthode
        var babyArg = ArgumentType.Boolean("baby");
        // Appelle une méthode
        var markingArg = ArgumentType.Enum("marking", HorseMeta.Marking.class);
        // Appelle une méthode
        var colorArg = ArgumentType.Enum("color", HorseMeta.Color.class);
        // Appelle une méthode
        setArgumentCallback(this::onBabyError, babyArg);
        // Appelle une méthode
        setArgumentCallback(this::onMarkingError, markingArg);
        // Appelle une méthode
        setArgumentCallback(this::onColorError, colorArg);
        // Appelle une méthode
        addSyntax(this::onHorseCommand, babyArg, markingArg, colorArg);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void defaultExecutor(CommandSender sender, CommandContext context) {
        // Appelle une méthode
        sender.sendMessage(Component.text("Correct usage: /horse <baby> <marking> <color>"));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void onBabyError(CommandSender sender, ArgumentSyntaxException exception) {
        // Appelle une méthode
        sender.sendMessage(Component.text("SYNTAX ERROR: '" + exception.getInput() + "' should be replaced by 'true' or 'false'"));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void onMarkingError(CommandSender sender, ArgumentSyntaxException exception) {
        // Affecte une valeur
        String values = Stream.of(HorseMeta.Marking.values())
                // Instruction de code
                .map(value -> "'" + value.name().toLowerCase(Locale.ROOT) + "'")
                // Appelle une méthode
                .collect(Collectors.joining(", "));
        // Appelle une méthode
        sender.sendMessage(Component.text("SYNTAX ERROR: '" + exception.getInput() + "' should be replaced by " + values + "."));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void onColorError(CommandSender sender, ArgumentSyntaxException exception) {
        // Affecte une valeur
        String values = Stream.of(HorseMeta.Color.values())
                // Instruction de code
                .map(value -> "'" + value.name().toLowerCase(Locale.ROOT) + "'")
                // Appelle une méthode
                .collect(Collectors.joining(", "));
        // Appelle une méthode
        sender.sendMessage(Component.text("SYNTAX ERROR: '" + exception.getInput() + "' should be replaced by " + values + "."));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void onHorseCommand(CommandSender sender, CommandContext context) {
        // Affecte une valeur
        var player = (Player) sender;

        // Appelle une méthode
        boolean baby = context.get("baby");
        // Appelle une méthode
        HorseMeta.Marking marking = context.get("marking");
        // Appelle une méthode
        HorseMeta.Color color = context.get("color");
        // Appelle une méthode
        var horse = new EntityCreature(EntityType.HORSE);
        // Appelle une méthode
        var meta = (HorseMeta) horse.getEntityMeta();
        // Appelle une méthode
        meta.setBaby(baby);
        // Appelle une méthode
        meta.setVariant(new HorseMeta.Variant(marking, color));
        //noinspection ConstantConditions - It should be impossible to execute a command without being in an instance
        // Appelle une méthode
        horse.setInstance(player.getInstance(), player.getPosition());
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
