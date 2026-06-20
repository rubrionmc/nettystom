// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;

// Déclaration de type (classe/interface/enum/record)
public class TestCommand2 extends Command {
    // Début d'une méthode/d'un bloc
    public TestCommand2() {
        // Accès à l'objet courant/parent
        super("test2");

        // Appelle une méthode
        var argA = ArgumentType.String("a");
        // Appelle une méthode
        var argB = ArgumentType.String("b");

        // Appelle une méthode
        addSyntax((sender, context) -> sender.sendMessage("a only"), argA);
        // Appelle une méthode
        addSyntax((sender, context) -> sender.sendMessage("a and b"), argB, argA);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
