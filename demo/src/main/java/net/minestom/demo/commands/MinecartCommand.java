// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.command.builder.condition.Conditions;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.entity.metadata.minecart.AbstractMinecartMeta;
// Import of a required class
import net.minestom.server.instance.block.Block;

// Type declaration (class/interface/enum/record)
public class MinecartCommand extends Command {

    // Calls a method
    private final Argument<Type> type = ArgumentType.Enum("type", Type.class);
    // Calls a method
    private final Argument<Block> block = ArgumentType.BlockState("block").setDefaultValue(Block.AIR);
    // Calls a method
    private final Argument<Integer> offset = ArgumentType.Integer("offset").setDefaultValue(6);

    // Start of a method/block
    public MinecartCommand() {
        // Access to the current/parent object
        super("minecart");

        // Calls a method
        setCondition(Conditions::playerOnly);
        // Calls a method
        addSyntax(this::execute, type, block, offset);
    // End of a block/expression
    }

    // Start of a method/block
    private void execute(CommandSender sender, CommandContext context) {
        // Calls a method
        var player = (Player) sender;

        // Assigns a value
        var minecart = new Entity(switch (context.get(type)) {
            // Multiple branching (switch/case)
            case NORMAL -> EntityType.MINECART;
            // Multiple branching (switch/case)
            case CHEST -> EntityType.CHEST_MINECART;
            // Multiple branching (switch/case)
            case FURNACE -> EntityType.FURNACE_MINECART;
            // Multiple branching (switch/case)
            case TNT -> EntityType.TNT_MINECART;
            // Multiple branching (switch/case)
            case HOPPER -> EntityType.HOPPER_MINECART;
            // Multiple branching (switch/case)
            case SPAWNER -> EntityType.SPAWNER_MINECART;
            // Multiple branching (switch/case)
            case COMMAND_BLOCK -> EntityType.COMMAND_BLOCK_MINECART;
        // End of a block/expression
        });
        // Calls a method
        var meta = (AbstractMinecartMeta) minecart.getEntityMeta();
        // Calls a method
        meta.setCustomBlockState(context.get(block));
        // Calls a method
        meta.setCustomBlockYPosition(context.get(offset));

        // Calls a method
        minecart.setInstance(player.getInstance(), player.getPosition().withView(0f, 0f));
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private enum Type {
        // Code statement
        NORMAL,
        // Code statement
        CHEST,
        // Code statement
        FURNACE,
        // Code statement
        TNT,
        // Code statement
        HOPPER,
        // Code statement
        SPAWNER,
        // Code statement
        COMMAND_BLOCK,
    // End of a block/expression
    }
// End of a block/expression
}
