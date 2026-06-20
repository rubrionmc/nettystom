// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class LegacyCommand extends net.minestom.server.command.builder.SimpleCommand {
    // Début d'une méthode/d'un bloc
    public LegacyCommand() {
        // Accès à l'objet courant/parent
        super("test", "alias");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean process(CommandSender sender, String command, String[] args) {
        // Embranchement : vérifie une condition
        if (!(sender instanceof Player)) return false;

        // Appelle une méthode
        System.gc();
        // Appelle une méthode
        sender.sendMessage("Explicit GC");
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean hasAccess(CommandSender sender, @Nullable String commandString) {
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
