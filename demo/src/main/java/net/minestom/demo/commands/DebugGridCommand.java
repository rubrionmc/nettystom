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
import net.minestom.server.command.builder.arguments.ArgumentBoolean;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.number.ArgumentInteger;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.relative.ArgumentRelativeBlockPosition;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.condition.Conditions;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.instance.batch.BatchOption;
// Import d'une classe nécessaire
import net.minestom.server.instance.batch.RelativeBlockBatch;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.timer.TaskSchedule;
// Import d'une classe nécessaire
import net.minestom.server.utils.location.RelativeVec;


// Déclaration de type (classe/interface/enum/record)
public class DebugGridCommand extends Command {
    // Affecte une valeur
    private final Argument<RelativeVec> center = new ArgumentRelativeBlockPosition("center")
            // Appelle une méthode
            .setDefaultValue(new RelativeVec(new Vec(0, -1, 0), RelativeVec.CoordinateType.RELATIVE, true, true, true));
    // Affecte une valeur
    private final Argument<Integer> radius = new ArgumentInteger("radius")
            // Appelle une méthode
            .setDefaultValue(100);
    // Appelle une méthode
    private final Argument<Boolean> replace = new ArgumentBoolean("replace");

    // Début d'une méthode/d'un bloc
    public DebugGridCommand() {
        // Accès à l'objet courant/parent
        super("dg");
        // Appelle une méthode
        setCondition(Conditions::playerOnly);
        // Appelle une méthode
        addSyntax(this::execute, radius, center, replace);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void execute(CommandSender sender, CommandContext context) {
        // Affecte une valeur
        Player player = (Player) sender;
        // Appelle une méthode
        final Boolean replace = context.get(this.replace);
        // Appelle une méthode
        final RelativeBlockBatch relativeBlockBatch = new RelativeBlockBatch(new BatchOption().setCalculateInverse(replace));
        // Appelle une méthode
        final Integer radius = context.get(this.radius);
        // Boucle : répète un bloc
        for (int x = -radius / 2; x < radius / 2; x++) {
            // Boucle : répète un bloc
            for (int z = -radius / 2; z < radius / 2; z++) {
                // Appelle une méthode
                relativeBlockBatch.setBlock(x, 0, z, ((x % 2 == 0) ^ (z % 2) == 0) ? Block.WHITE_CONCRETE : Block.BLACK_CONCRETE);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        //noinspection ConstantConditions
        // Début d'une méthode/d'un bloc
        relativeBlockBatch.apply(player.getInstance(), context.get(center).from(player), (inverse) -> {
            // Embranchement : vérifie une condition
            if (!replace) return;
            // Début d'une méthode/d'un bloc
            player.getInstance().scheduler().scheduleTask(()->{
                // Embranchement : vérifie une condition
                if (inverse == null) return;
                // Appelle une méthode
                inverse.apply(player.getInstance(), null);
            // Appelle une méthode
            }, TaskSchedule.seconds(1), TaskSchedule.stop());
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
