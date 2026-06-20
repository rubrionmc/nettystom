// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.suggestion;

// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;

// Annotation pour l'élément suivant
@FunctionalInterface
// Déclaration de type (classe/interface/enum/record)
public interface SuggestionCallback {
    // Appelle une méthode
    void apply(CommandSender sender, CommandContext context, Suggestion suggestion);
// Fin d'un bloc/d'une expression
}
