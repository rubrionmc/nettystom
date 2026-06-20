// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
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
import net.minestom.server.entity.Player;

// Déclaration de type (classe/interface/enum/record)
public class CookieCommand extends Command {
    // Début d'une méthode/d'un bloc
    public CookieCommand() {
        // Accès à l'objet courant/parent
        super("cookie");

        // Appelle une méthode
        addSubcommand(new Store());
        // Appelle une méthode
        addSubcommand(new Fetch());
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public static class Store extends Command {
        // Appelle une méthode
        private final Argument<Key> keyArg = ArgumentType.ResourceLocation("key");
        // Appelle une méthode
        private final Argument<String[]> valueArg = ArgumentType.StringArray("value");

        // Début d'une méthode/d'un bloc
        public Store() {
            // Accès à l'objet courant/parent
            super("store");

            // Appelle une méthode
            addSyntax(this::store, keyArg, valueArg);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private void store(CommandSender sender, CommandContext context) {
            // Embranchement : vérifie une condition
            if (!(sender instanceof Player player)) return;

            // Appelle une méthode
            String key = context.get(keyArg).asString();
            // Appelle une méthode
            byte[] value = String.join(" ", context.get(valueArg)).getBytes();

            // Appelle une méthode
            player.getPlayerConnection().storeCookie(key, value);
            // Appelle une méthode
            player.sendMessage(key + " stored");
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public static class Fetch extends Command {
        // Appelle une méthode
        private final Argument<Key> keyArg = ArgumentType.ResourceLocation("key");

        // Début d'une méthode/d'un bloc
        public Fetch() {
            // Accès à l'objet courant/parent
            super("fetch");

            // Appelle une méthode
            addSyntax(this::fetch, keyArg);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private void fetch(CommandSender sender, CommandContext context) {
            // Embranchement : vérifie une condition
            if (!(sender instanceof Player player)) return;

            // Appelle une méthode
            String key = context.get(keyArg).asString();

            // Début d'une méthode/d'un bloc
            player.getPlayerConnection().fetchCookie(key).thenAccept(value -> {
                // Embranchement : vérifie une condition
                if (value == null) {
                    // Appelle une méthode
                    player.sendMessage(key + ": null");
                // Branche alternative de la condition
                } else {
                    // Appelle une méthode
                    player.sendMessage(key + ": " + new String(value));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
