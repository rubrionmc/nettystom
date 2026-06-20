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
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.utils.entity.EntityFinder;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public class EntitySelectorCommand extends Command {

    // Start of a method/block
    public EntitySelectorCommand() {
        // Access to the current/parent object
        super("ent");

        // Calls a method
        setDefaultExecutor((sender, context) -> System.out.println("DEFAULT"));

        // Calls a method
        ArgumentEntity argumentEntity = ArgumentType.Entity("entities").onlyPlayers(true);

        // Calls a method
        setArgumentCallback((sender, exception) -> exception.printStackTrace(), argumentEntity);

        // Calls a method
        addSyntax(this::executor, argumentEntity);

    // End of a block/expression
    }

    // Start of a method/block
    private void executor(CommandSender commandSender, CommandContext context) {
        // Calls a method
        EntityFinder entityFinder = context.get("entities");
        // Calls a method
        List<Entity> entities = entityFinder.find(commandSender);
        // Calls a method
        System.out.println("found " + entities.size() + " entities");
    // End of a block/expression
    }
// End of a block/expression
}
