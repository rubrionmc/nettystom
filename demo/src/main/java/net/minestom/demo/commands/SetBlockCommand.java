// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.minestom.demo.block.TestBlockHandler;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.minecraft.ArgumentBlockState;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.relative.ArgumentRelativeBlockPosition;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;

// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.BlockState;
// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.RelativeBlockPosition;

// Déclaration de type (classe/interface/enum/record)
public class SetBlockCommand extends Command {
    // Début d'une méthode/d'un bloc
    public SetBlockCommand() {
        // Accès à l'objet courant/parent
        super("setblock");

        // Appelle une méthode
        final ArgumentRelativeBlockPosition position = RelativeBlockPosition("position");
        // Appelle une méthode
        final ArgumentBlockState block = BlockState("block");

        // Début d'une méthode/d'un bloc
        addSyntax((sender, context) -> {
            // Affecte une valeur
            final Player player = (Player) sender;

            // Appelle une méthode
            Block blockToPlace = context.get(block);
            // Embranchement : vérifie une condition
            if (blockToPlace.stateId() == Block.GOLD_BLOCK.stateId())
                // Appelle une méthode
                blockToPlace = blockToPlace.withHandler(TestBlockHandler.INSTANCE);

            // Appelle une méthode
            player.getInstance().setBlock(context.get(position).from(player), blockToPlace);
        // Instruction de code
        }, position, block);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
