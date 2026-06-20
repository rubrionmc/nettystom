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
import net.minestom.server.command.builder.condition.Conditions;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.utils.entity.EntityFinder;

// Type declaration (class/interface/enum/record)
public class RemoveCommand extends Command {

    // Start of a method/block
    public RemoveCommand() {
        // Access to the current/parent object
        super("remove");
        // Calls a method
        addSubcommand(new RemoveEntities());
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    static class RemoveEntities extends Command {
        // Code statement
        private final ArgumentEntity entity;

        // Start of a method/block
        public RemoveEntities() {
            // Access to the current/parent object
            super("entities");
            // Calls a method
            setCondition(Conditions::playerOnly);
            // Calls a method
            entity = ArgumentType.Entity("entity");
            // Calls a method
            addSyntax(this::remove, entity);
        // End of a block/expression
        }

        // Start of a method/block
        private void remove(CommandSender commandSender, CommandContext commandContext) {
            // Calls a method
            final EntityFinder entityFinder = commandContext.get(entity);
            // Calls a method
            entityFinder.find(commandSender).forEach(Entity::remove);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}