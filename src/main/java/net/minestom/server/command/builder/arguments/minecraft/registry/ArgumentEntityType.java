// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments.minecraft.registry;

// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.minecraft.SuggestionType;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;

/**
 * Represents an argument giving an {@link EntityType}.
 */
// Déclaration de type (classe/interface/enum/record)
public class ArgumentEntityType extends ArgumentRegistry<EntityType> {

    // Début d'une méthode/d'un bloc
    public ArgumentEntityType(String id) {
        // Accès à l'objet courant/parent
        super(id);
        // Affecte une valeur
        suggestionType = SuggestionType.SUMMONABLE_ENTITIES;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ArgumentParserType parser() {
        // Renvoie une valeur à l'appelant
        return ArgumentParserType.RESOURCE_LOCATION;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public EntityType getRegistry(String value) {
        // Renvoie une valeur à l'appelant
        return EntityType.fromKey(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format("EntityType<%s>", getId());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
