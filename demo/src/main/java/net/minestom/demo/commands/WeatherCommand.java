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
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.instance.Weather;

// Déclaration de type (classe/interface/enum/record)
public class WeatherCommand extends Command {
    // Début d'une méthode/d'un bloc
    public WeatherCommand() {
        // Accès à l'objet courant/parent
        super("weather");

        // Appelle une méthode
        var rainLevel = ArgumentType.Float("rainLevel").setDefaultValue(0.0f);
        // Appelle une méthode
        var thunderLevel = ArgumentType.Float("thunderLevel").setDefaultValue(0.0f);
        // Appelle une méthode
        var transitionTicks = ArgumentType.Integer("transition").setDefaultValue(0);
        // Appelle une méthode
        addSyntax(this::handleWeather, rainLevel, thunderLevel, transitionTicks);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void handleWeather(CommandSender source, CommandContext context) {
        // Affecte une valeur
        Player player = (Player) source;
        // Appelle une méthode
        float rainLevel = context.get("rainLevel");
        // Appelle une méthode
        float thunderLevel = context.get("thunderLevel");
        // Appelle une méthode
        int transitionTicks = context.get("transition");
        // Appelle une méthode
        player.getInstance().setWeather(new Weather(rainLevel, thunderLevel), transitionTicks);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
