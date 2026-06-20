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
import net.minestom.server.command.builder.condition.Conditions;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import of a required class
import net.minestom.server.entity.EntityCreature;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.entity.metadata.animal.HorseMeta;

// Import of a required class
import java.util.Locale;
// Import of a required class
import java.util.stream.Collectors;
// Import of a required class
import java.util.stream.Stream;

// Type declaration (class/interface/enum/record)
public class HorseCommand extends Command {

    // Start of a method/block
    public HorseCommand() {
        // Access to the current/parent object
        super("horse");
        // Calls a method
        setCondition(Conditions::playerOnly);
        // Calls a method
        setDefaultExecutor(this::defaultExecutor);
        // Calls a method
        var babyArg = ArgumentType.Boolean("baby");
        // Calls a method
        var markingArg = ArgumentType.Enum("marking", HorseMeta.Marking.class);
        // Calls a method
        var colorArg = ArgumentType.Enum("color", HorseMeta.Color.class);
        // Calls a method
        setArgumentCallback(this::onBabyError, babyArg);
        // Calls a method
        setArgumentCallback(this::onMarkingError, markingArg);
        // Calls a method
        setArgumentCallback(this::onColorError, colorArg);
        // Calls a method
        addSyntax(this::onHorseCommand, babyArg, markingArg, colorArg);
    // End of a block/expression
    }

    // Start of a method/block
    private void defaultExecutor(CommandSender sender, CommandContext context) {
        // Calls a method
        sender.sendMessage(Component.text("Correct usage: /horse <baby> <marking> <color>"));
    // End of a block/expression
    }

    // Start of a method/block
    private void onBabyError(CommandSender sender, ArgumentSyntaxException exception) {
        // Calls a method
        sender.sendMessage(Component.text("SYNTAX ERROR: '" + exception.getInput() + "' should be replaced by 'true' or 'false'"));
    // End of a block/expression
    }

    // Start of a method/block
    private void onMarkingError(CommandSender sender, ArgumentSyntaxException exception) {
        // Assigns a value
        String values = Stream.of(HorseMeta.Marking.values())
                // Code statement
                .map(value -> "'" + value.name().toLowerCase(Locale.ROOT) + "'")
                // Calls a method
                .collect(Collectors.joining(", "));
        // Calls a method
        sender.sendMessage(Component.text("SYNTAX ERROR: '" + exception.getInput() + "' should be replaced by " + values + "."));
    // End of a block/expression
    }

    // Start of a method/block
    private void onColorError(CommandSender sender, ArgumentSyntaxException exception) {
        // Assigns a value
        String values = Stream.of(HorseMeta.Color.values())
                // Code statement
                .map(value -> "'" + value.name().toLowerCase(Locale.ROOT) + "'")
                // Calls a method
                .collect(Collectors.joining(", "));
        // Calls a method
        sender.sendMessage(Component.text("SYNTAX ERROR: '" + exception.getInput() + "' should be replaced by " + values + "."));
    // End of a block/expression
    }

    // Start of a method/block
    private void onHorseCommand(CommandSender sender, CommandContext context) {
        // Calls a method
        var player = (Player) sender;

        // Calls a method
        boolean baby = context.get("baby");
        // Calls a method
        HorseMeta.Marking marking = context.get("marking");
        // Calls a method
        HorseMeta.Color color = context.get("color");
        // Calls a method
        var horse = new EntityCreature(EntityType.HORSE);
        // Calls a method
        var meta = (HorseMeta) horse.getEntityMeta();
        // Calls a method
        meta.setBaby(baby);
        // Calls a method
        meta.setVariant(new HorseMeta.Variant(marking, color));
        //noinspection ConstantConditions - It should be impossible to execute a command without being in an instance
        // Calls a method
        horse.setInstance(player.getInstance(), player.getPosition());
    // End of a block/expression
    }

// End of a block/expression
}
