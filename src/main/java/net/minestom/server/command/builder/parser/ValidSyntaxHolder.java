// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.parser;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandSyntax;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;

// Import d'une classe nécessaire
import java.util.Map;

/**
 * Holds the data of a validated syntax.
 */
// Déclaration de type (classe/interface/enum/record)
public record ValidSyntaxHolder(String commandString,
                                // Instruction de code
                                CommandSyntax syntax,
                                // Début d'une méthode/d'un bloc
                                Map<Argument<?>, ArgumentParser.ArgumentResult> argumentResults) {

// Fin d'un bloc/d'une expression
}
