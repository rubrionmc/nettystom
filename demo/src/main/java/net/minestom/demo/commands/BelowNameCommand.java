// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.scoreboard.BelowNameTag;

// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.Literal;

// Type declaration (class/interface/enum/record)
public class BelowNameCommand extends Command {

    // Calls a method
    private final ArgumentEntity target = ArgumentType.Entity("target").onlyPlayers(true).singleEntity(true);
    // Calls a method
    private final Argument<Integer> value = ArgumentType.Integer("value");

    // Start of a method/block
    public BelowNameCommand() {
        // Access to the current/parent object
        super("belowname");

        // Calls a method
        BelowNameTag belowNameTag = new BelowNameTag("test", Component.text("lorum"));

        // Start of a method/block
        addSyntax((sender, context) -> {
            // Branch: checks a condition
            if (!(sender instanceof Player player)) return;
            // Calls a method
            Player targetPlayer = context.get(target).findFirstPlayer(player);
            // Branch: checks a condition
            if (targetPlayer == null) return;
            // Calls a method
            belowNameTag.addViewer(player);
            // Calls a method
            Integer targetValue = context.get(value);
            // Calls a method
            belowNameTag.updateScore(targetPlayer, targetValue);
        // Calls a method
        }, Literal("set"), target, value);
    // End of a block/expression
    }
// End of a block/expression
}
