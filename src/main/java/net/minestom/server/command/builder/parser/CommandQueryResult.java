// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.parser;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public record CommandQueryResult(List<Command> parents,
                                 // Instruction de code
                                 Command command,
                                 // Instruction de code
                                 String commandName,
                                 // Début d'une méthode/d'un bloc
                                 String[] args) {
// Fin d'un bloc/d'une expression
}
