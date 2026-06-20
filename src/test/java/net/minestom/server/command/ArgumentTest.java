// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.suggestion.Suggestion;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class ArgumentTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testParseSelf() {
        // Appelle une méthode
        assertEquals("example", Argument.parse(new ServerSender(), ArgumentType.String("example")));
        // Appelle une méthode
        assertEquals(55, Argument.parse(new ServerSender(), ArgumentType.Integer("55")));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testCallback() {
        // Appelle une méthode
        var arg = ArgumentType.String("id");

        // Appelle une méthode
        assertFalse(arg.hasErrorCallback());
        // Début d'une méthode/d'un bloc
        arg.setCallback((sender, exception) -> {
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        assertTrue(arg.hasErrorCallback());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testDefaultValue() {
        // Appelle une méthode
        var arg = ArgumentType.String("id");

        // Appelle une méthode
        assertFalse(arg.isOptional());
        // Appelle une méthode
        arg.setDefaultValue("default value");
        // Appelle une méthode
        assertTrue(arg.isOptional());
        // Appelle une méthode
        assertEquals("default value", arg.getDefaultValue().apply(new ServerSender()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testSuggestionCallback() {
        // Appelle une méthode
        var arg = ArgumentType.String("id");

        // Appelle une méthode
        assertFalse(arg.hasSuggestion());

        // Appelle une méthode
        arg.setSuggestionCallback((sender, context, suggestion) -> suggestion.addEntry(new SuggestionEntry("entry")));
        // Appelle une méthode
        assertTrue(arg.hasSuggestion());

        // Appelle une méthode
        Suggestion suggestion = new Suggestion("input", 2, 4);
        // Appelle une méthode
        arg.getSuggestionCallback().apply(new ServerSender(), new CommandContext("input"), suggestion);

        // Appelle une méthode
        assertEquals(suggestion.getEntries(), List.of(new SuggestionEntry("entry")));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}