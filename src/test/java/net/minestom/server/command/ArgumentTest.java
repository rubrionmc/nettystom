// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.command.builder.suggestion.Suggestion;
// Import of a required class
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class ArgumentTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testParseSelf() {
        // Calls a method
        assertEquals("example", Argument.parse(new ServerSender(), ArgumentType.String("example")));
        // Calls a method
        assertEquals(55, Argument.parse(new ServerSender(), ArgumentType.Integer("55")));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testCallback() {
        // Calls a method
        var arg = ArgumentType.String("id");

        // Calls a method
        assertFalse(arg.hasErrorCallback());
        // Start of a method/block
        arg.setCallback((sender, exception) -> {
        // End of a block/expression
        });
        // Calls a method
        assertTrue(arg.hasErrorCallback());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testDefaultValue() {
        // Calls a method
        var arg = ArgumentType.String("id");

        // Calls a method
        assertFalse(arg.isOptional());
        // Calls a method
        arg.setDefaultValue("default value");
        // Calls a method
        assertTrue(arg.isOptional());
        // Calls a method
        assertEquals("default value", arg.getDefaultValue().apply(new ServerSender()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testSuggestionCallback() {
        // Calls a method
        var arg = ArgumentType.String("id");

        // Calls a method
        assertFalse(arg.hasSuggestion());

        // Calls a method
        arg.setSuggestionCallback((sender, context, suggestion) -> suggestion.addEntry(new SuggestionEntry("entry")));
        // Calls a method
        assertTrue(arg.hasSuggestion());

        // Calls a method
        Suggestion suggestion = new Suggestion("input", 2, 4);
        // Calls a method
        arg.getSuggestionCallback().apply(new ServerSender(), new CommandContext("input"), suggestion);

        // Calls a method
        assertEquals(suggestion.getEntries(), List.of(new SuggestionEntry("entry")));
    // End of a block/expression
    }
// End of a block/expression
}