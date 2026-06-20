// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.parser;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandSyntax;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;

/**
 * Holds the data of an invalidated syntax.
 */
// Déclaration de type (classe/interface/enum/record)
public record CommandSuggestionHolder(CommandSyntax syntax,
                                      // Instruction de code
                                      ArgumentSyntaxException argumentSyntaxException,
                                      // Début d'une méthode/d'un bloc
                                      int argIndex) {
// Fin d'un bloc/d'une expression
}
