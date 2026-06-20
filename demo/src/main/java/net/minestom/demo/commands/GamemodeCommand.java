// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.kyori.adventure.audience.MessageType;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentEnum;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.GameMode;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.utils.entity.EntityFinder;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Locale;

/**
 * Command that make a player change gamemode, made in
 * the style of the vanilla /gamemode command.
 *
 * @see <a href="https://minecraft.wiki/w/Commands/gamemode">...</a>
 */
// Déclaration de type (classe/interface/enum/record)
public class GamemodeCommand extends Command {

    // Début d'une méthode/d'un bloc
    public GamemodeCommand() {
        // Accès à l'objet courant/parent
        super("gamemode", "gm");

        //GameMode parameter
        // Appelle une méthode
        ArgumentEnum<GameMode> gamemode = ArgumentType.Enum("gamemode", GameMode.class).setFormat(ArgumentEnum.Format.LOWER_CASED);
        // Début d'une méthode/d'un bloc
        gamemode.setCallback((sender, exception) -> {
            // Instruction de code
            sender.sendMessage(
                    // Instruction de code
                    Component.text("Invalid gamemode ", NamedTextColor.RED)
                            // Instruction de code
                            .append(Component.text(exception.getInput(), NamedTextColor.WHITE))
                            // Appelle une méthode
                            .append(Component.text("!")), MessageType.SYSTEM);
        // Fin d'un bloc/d'une expression
        });

        // Appelle une méthode
        ArgumentEntity player = ArgumentType.Entity("targets").onlyPlayers(true);

        //Upon invalid usage, print the correct usage of the command to the sender
        // Début d'une méthode/d'un bloc
        setDefaultExecutor((sender, context) -> {
            // Appelle une méthode
            String commandName = context.getCommandName();

            // Appelle une méthode
            sender.sendMessage(Component.text("Usage: /" + commandName + " <gamemode> [targets]", NamedTextColor.RED), MessageType.SYSTEM);
        // Fin d'un bloc/d'une expression
        });

        //Command Syntax for /gamemode <gamemode>
        // Début d'une méthode/d'un bloc
        addSyntax((sender, context) -> {
            //Limit execution to players only
            // Embranchement : vérifie une condition
            if (!(sender instanceof Player p)) {
                // Appelle une méthode
                sender.sendMessage(Component.text("Please run this command in-game.", NamedTextColor.RED));
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }

            // Embranchement : vérifie une condition
            if (p.getPermissionLevel() < 2) {
                // Appelle une méthode
                sender.sendMessage(Component.text("You don't have permission to use this command.", NamedTextColor.RED));
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            GameMode mode = context.get(gamemode);

            //Set the gamemode for the sender
            // Appelle une méthode
            executeSelf(p, mode);
        // Instruction de code
        }, gamemode);

        //Command Syntax for /gamemode <gamemode> [targets]
        // Début d'une méthode/d'un bloc
        addSyntax((sender, context) -> {
            //Check permission for players only
            //This allows the console to use this syntax too
            // Embranchement : vérifie une condition
            if (sender instanceof Player p && p.getPermissionLevel() < 2) {
                // Appelle une méthode
                sender.sendMessage(Component.text("You don't have permission to use this command.", NamedTextColor.RED));
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            EntityFinder finder = context.get(player);
            // Appelle une méthode
            GameMode mode = context.get(gamemode);

            //Set the gamemode for the targets
            // Appelle une méthode
            executeOthers(sender, mode, finder.find(sender));
        // Instruction de code
        }, gamemode, player);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the gamemode for the specified entities, and
     * notifies them (and the sender) in the chat.
     */
    // Début d'une méthode/d'un bloc
    private void executeOthers(CommandSender sender, GameMode mode, List<Entity> entities) {
        // Embranchement : vérifie une condition
        if (entities.size() == 0) {
            //If there are no players that could be modified, display an error message
            // Embranchement : vérifie une condition
            if (sender instanceof Player)
                // Appelle une méthode
                sender.sendMessage(Component.translatable("argument.entity.notfound.player", NamedTextColor.RED), MessageType.SYSTEM);
            // Branche alternative de la condition
            else sender.sendMessage(Component.text("No player was found", NamedTextColor.RED), MessageType.SYSTEM);
        // Branche alternative de la condition
        } else for (Entity entity : entities) {
            // Embranchement : vérifie une condition
            if (entity instanceof Player p) {
                // Embranchement : vérifie une condition
                if (p == sender) {
                    //If the player is the same as the sender, call
                    //executeSelf to display one message instead of two
                    // Appelle une méthode
                    executeSelf((Player) sender, mode);
                // Branche alternative de la condition
                } else {
                    // Appelle une méthode
                    p.setGameMode(mode);

                    // Appelle une méthode
                    String gamemodeString = "gameMode." + mode.name().toLowerCase(Locale.ROOT);
                    // Appelle une méthode
                    Component gamemodeComponent = Component.translatable(gamemodeString);
                    // Appelle une méthode
                    Component playerName = p.getDisplayName() == null ? p.getName() : p.getDisplayName();

                    //Send a message to the changed player and the sender
                    // Appelle une méthode
                    p.sendMessage(Component.translatable("gameMode.changed", gamemodeComponent), MessageType.SYSTEM);
                    // Appelle une méthode
                    sender.sendMessage(Component.translatable("commands.gamemode.success.other", playerName, gamemodeComponent), MessageType.SYSTEM);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the gamemode for the executing Player, and
     * notifies them in the chat.
     */
    // Début d'une méthode/d'un bloc
    private void executeSelf(Player sender, GameMode mode) {
        // Appelle une méthode
        sender.setGameMode(mode);

        //The translation keys 'gameMode.survival', 'gameMode.creative', etc.
        //correspond to the translated game mode names.
        // Appelle une méthode
        String gamemodeString = "gameMode." + mode.name().toLowerCase(Locale.ROOT);
        // Appelle une méthode
        Component gamemodeComponent = Component.translatable(gamemodeString);

        //Send the translated message to the player.
        // Appelle une méthode
        sender.sendMessage(Component.translatable("commands.gamemode.success.self", gamemodeComponent), MessageType.SYSTEM);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
