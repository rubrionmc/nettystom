// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.parser;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandSyntax;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;

// Déclaration de type (classe/interface/enum/record)
public record ArgumentQueryResult(CommandSyntax syntax,
                                  // Instruction de code
                                  Argument<?> argument,
                                  // Instruction de code
                                  CommandContext context,
                                  // Début d'une méthode/d'un bloc
                                  String input) {
// Fin d'un bloc/d'une expression
}
