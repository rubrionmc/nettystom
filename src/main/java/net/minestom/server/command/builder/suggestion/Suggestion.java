// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.suggestion;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public class Suggestion {

    // Instruction de code
    private final String input;
    // Instruction de code
    private int start;
    // Instruction de code
    private int length;
    // Appelle une méthode
    private final List<SuggestionEntry> suggestionEntries = new ArrayList<>();

    // Début d'une méthode/d'un bloc
    public Suggestion(String input, int start, int length) {
        // Accès à l'objet courant/parent
        this.input = input;
        // Accès à l'objet courant/parent
        this.start = start;
        // Accès à l'objet courant/parent
        this.length = length;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public String getInput() {
        // Renvoie une valeur à l'appelant
        return input;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getStart() {
        // Renvoie une valeur à l'appelant
        return start;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setStart(int start) {
        // Accès à l'objet courant/parent
        this.start = start;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getLength() {
        // Renvoie une valeur à l'appelant
        return length;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setLength(int length) {
        // Accès à l'objet courant/parent
        this.length = length;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public List<SuggestionEntry> getEntries() {
        // Renvoie une valeur à l'appelant
        return suggestionEntries;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void addEntry(SuggestionEntry entry) {
        // Accès à l'objet courant/parent
        this.suggestionEntries.add(entry);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
