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

        // Début d'une méthode/d'un bloc
        addSyntax((sender, context) -> {
            // Appelle une méthode
            sender.sendMessage("a only");
        // Instruction de code
        }, argA);
        // Début d'une méthode/d'un bloc
        addSyntax((sender, context) -> {
            // Appelle une méthode
            sender.sendMessage("a and b");
        // Instruction de code
        }, argB, argA);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
