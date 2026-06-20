// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.suggestion;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
public class SuggestionEntry {
    // Instruction de code
    private final String entry;
    // Instruction de code
    private final Component tooltip;

    // Début d'une méthode/d'un bloc
    public SuggestionEntry(String entry, @Nullable Component tooltip) {
        // Accès à l'objet courant/parent
        this.entry = entry;
        // Accès à l'objet courant/parent
        this.tooltip = tooltip;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public SuggestionEntry(String entry) {
        // Appelle une méthode
        this(entry, null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public String getEntry() {
        // Renvoie une valeur à l'appelant
        return entry;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable Component getTooltip() {
        // Renvoie une valeur à l'appelant
        return tooltip;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object o) {
        // Embranchement : vérifie une condition
        if (this == o) return true;
        // Embranchement : vérifie une condition
        if (o == null || getClass() != o.getClass()) return false;
        // Affecte une valeur
        SuggestionEntry that = (SuggestionEntry) o;
        // Renvoie une valeur à l'appelant
        return Objects.equals(entry, that.entry) && Objects.equals(tooltip, that.tooltip);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Renvoie une valeur à l'appelant
        return Objects.hash(entry, tooltip);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
