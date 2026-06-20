// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.utils.location.RelativeVec;

// Déclaration de type (classe/interface/enum/record)
public class WorldBorderCommand extends Command {
    // Début d'une méthode/d'un bloc
    public WorldBorderCommand() {
        // Accès à l'objet courant/parent
        super("worldborder");

        // Affecte une valeur
        var diameterOptions = ArgumentType.Word("diameterOptions").from("set", "add"); // "center", "warning-time", "warning-distance"
        // Appelle une méthode
        var sizeInBlocks = ArgumentType.Integer("sizeInBlocks").setDefaultValue(0);
        // Appelle une méthode
        var timeInSeconds = ArgumentType.Double("timeInSeconds").setDefaultValue(0.0);

        // Appelle une méthode
        var centerOption = ArgumentType.Word("centerOption").from("center");
        // Appelle une méthode
        var centerCoordinate = ArgumentType.RelativeVec2("coordinate");

        // Appelle une méthode
        var warningTimeOption = ArgumentType.Word("warningTimeOption").from("warning-time");

        // Appelle une méthode
        var warningDistanceOption = ArgumentType.Word("warningDistanceOption").from("warning-distance");

        // Appelle une méthode
        addSyntax(this::handleDiameter, diameterOptions, sizeInBlocks, timeInSeconds);
        // Appelle une méthode
        addSyntax(this::handleCenter, centerOption, centerCoordinate);
        // Appelle une méthode
        addSyntax(this::handleWarningTime, warningTimeOption, timeInSeconds);
        // Appelle une méthode
        addSyntax(this::handleWarningDistance, warningDistanceOption, sizeInBlocks);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void handleDiameter(CommandSender source, CommandContext context) {
        // Affecte une valeur
        Player player = (Player) source;
        // Appelle une méthode
        int size = context.get("sizeInBlocks");
        // Boucle : répète un bloc
        double timeInSeconds = context.get("timeInSeconds");
        // Boucle : répète un bloc
        double diameter = size;
        // Embranchement : vérifie une condition
        if ((context.get("diameterOptions")).equals("add")) {
            // Appelle une méthode
            diameter += player.getInstance().getWorldBorder().diameter();
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        player.getInstance().setWorldBorder(player.getInstance().getWorldBorder().withDiameter(diameter), timeInSeconds);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void handleCenter(CommandSender source, CommandContext context) {
        // Affecte une valeur
        Player player = (Player) source;
        // Appelle une méthode
        RelativeVec coords = context.get("coordinate");
        // Appelle une méthode
        Vec vec = coords.from(new Pos(0, 0, 0));
        // Appelle une méthode
        player.getInstance().setWorldBorder(player.getInstance().getWorldBorder().withCenter(vec.x(), vec.z()));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void handleWarningTime(CommandSender source, CommandContext context) {
        // Affecte une valeur
        Player player = (Player) source;
        // Boucle : répète un bloc
        double timeInSeconds = context.get("timeInSeconds");
        // Appelle une méthode
        player.getInstance().setWorldBorder(player.getInstance().getWorldBorder().withWarningTime((int)timeInSeconds));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void handleWarningDistance(CommandSender source, CommandContext context) {
        // Affecte une valeur
        Player player = (Player) source;
        // Appelle une méthode
        int sizeInBlocks = context.get("sizeInBlocks");
        // Appelle une méthode
        player.getInstance().setWorldBorder(player.getInstance().getWorldBorder().withWarningDistance(sizeInBlocks));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
