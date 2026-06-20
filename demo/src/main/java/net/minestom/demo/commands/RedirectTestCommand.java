// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentLiteral;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentLoop;

// Déclaration de type (classe/interface/enum/record)
public class RedirectTestCommand extends Command {
    // Début d'une méthode/d'un bloc
    public RedirectTestCommand() {
        // Accès à l'objet courant/parent
        super("redirect");

        // Appelle une méthode
        final ArgumentLiteral a = new ArgumentLiteral("a");
        // Appelle une méthode
        final ArgumentLiteral b = new ArgumentLiteral("b");
        // Appelle une méthode
        final ArgumentLiteral c = new ArgumentLiteral("c");
        // Appelle une méthode
        final ArgumentLiteral d = new ArgumentLiteral("d");

        // Appelle une méthode
        addSyntax(((sender, context) -> {}), new ArgumentLoop<>("test", a,b,c,d));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
