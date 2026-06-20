// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.condition.Conditions;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.minecart.AbstractMinecartMeta;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;

// Déclaration de type (classe/interface/enum/record)
public class MinecartCommand extends Command {

    // Appelle une méthode
    private final Argument<Type> type = ArgumentType.Enum("type", Type.class);
    // Appelle une méthode
    private final Argument<Block> block = ArgumentType.BlockState("block").setDefaultValue(Block.AIR);
    // Appelle une méthode
    private final Argument<Integer> offset = ArgumentType.Integer("offset").setDefaultValue(6);

    // Début d'une méthode/d'un bloc
    public MinecartCommand() {
        // Accès à l'objet courant/parent
        super("minecart");

        // Appelle une méthode
        setCondition(Conditions::playerOnly);
        // Appelle une méthode
        addSyntax(this::execute, type, block, offset);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void execute(CommandSender sender, CommandContext context) {
        // Affecte une valeur
        var player = (Player) sender;

        // Affecte une valeur
        var minecart = new Entity(switch (context.get(type)) {
            // Embranchement multiple (switch/case)
            case NORMAL -> EntityType.MINECART;
            // Embranchement multiple (switch/case)
            case CHEST -> EntityType.CHEST_MINECART;
            // Embranchement multiple (switch/case)
            case FURNACE -> EntityType.FURNACE_MINECART;
            // Embranchement multiple (switch/case)
            case TNT -> EntityType.TNT_MINECART;
            // Embranchement multiple (switch/case)
            case HOPPER -> EntityType.HOPPER_MINECART;
            // Embranchement multiple (switch/case)
            case SPAWNER -> EntityType.SPAWNER_MINECART;
            // Embranchement multiple (switch/case)
            case COMMAND_BLOCK -> EntityType.COMMAND_BLOCK_MINECART;
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        var meta = (AbstractMinecartMeta) minecart.getEntityMeta();
        // Appelle une méthode
        meta.setCustomBlockState(context.get(block));
        // Appelle une méthode
        meta.setCustomBlockYPosition(context.get(offset));

        // Appelle une méthode
        minecart.setInstance(player.getInstance(), player.getPosition().withView(0f, 0f));
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private enum Type {
        // Instruction de code
        NORMAL,
        // Instruction de code
        CHEST,
        // Instruction de code
        FURNACE,
        // Instruction de code
        TNT,
        // Instruction de code
        HOPPER,
        // Instruction de code
        SPAWNER,
        // Instruction de code
        COMMAND_BLOCK,
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
