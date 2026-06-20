// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentCommand;

// Déclaration de type (classe/interface/enum/record)
public class ExecuteCommand extends Command {

    // Début d'une méthode/d'un bloc
    public ExecuteCommand() {
        // Accès à l'objet courant/parent
        super("execute");
        // Appelle une méthode
        ArgumentCommand run = new ArgumentCommand("run");

        // Appelle une méthode
        addSyntax(((sender, context) -> {}), run);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
