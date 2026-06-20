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
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.instance.Weather;

// Type declaration (class/interface/enum/record)
public class WeatherCommand extends Command {
    // Start of a method/block
    public WeatherCommand() {
        // Access to the current/parent object
        super("weather");

        // Calls a method
        var rainLevel = ArgumentType.Float("rainLevel").setDefaultValue(0.0f);
        // Calls a method
        var thunderLevel = ArgumentType.Float("thunderLevel").setDefaultValue(0.0f);
        // Calls a method
        var transitionTicks = ArgumentType.Integer("transition").setDefaultValue(0);
        // Calls a method
        addSyntax(this::handleWeather, rainLevel, thunderLevel, transitionTicks);
    // End of a block/expression
    }

    // Start of a method/block
    private void handleWeather(CommandSender source, CommandContext context) {
        // Calls a method
        Player player = (Player) source;
        // Calls a method
        float rainLevel = context.get("rainLevel");
        // Calls a method
        float thunderLevel = context.get("thunderLevel");
        // Calls a method
        int transitionTicks = context.get("transition");
        // Calls a method
        player.getInstance().setWeather(new Weather(rainLevel, thunderLevel), transitionTicks);
    // End of a block/expression
    }
// End of a block/expression
}
