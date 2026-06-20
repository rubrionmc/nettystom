// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments.minecraft;

// Déclaration de type (classe/interface/enum/record)
public enum SuggestionType {

    // Instruction de code
    ASK_SERVER("minecraft:ask_server"),
    // Instruction de code
    ALL_RECIPES("minecraft:all_recipes"),
    // Instruction de code
    AVAILABLE_SOUNDS("minecraft:available_sounds"),
    // Appelle une méthode
    SUMMONABLE_ENTITIES("minecraft:summonable_entities");

    // Instruction de code
    private final String identifier;

    // Début d'une méthode/d'un bloc
    SuggestionType(String identifier) {
        // Accès à l'objet courant/parent
        this.identifier = identifier;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public String getIdentifier() {
        // Renvoie une valeur à l'appelant
        return identifier;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
