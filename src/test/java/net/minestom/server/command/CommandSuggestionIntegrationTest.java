// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientTabCompletePacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.TabCompletePacket;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.*;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class CommandSuggestionIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void suggestion(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Calls a method
        var command = new Command("test");
        // Start of a method/block
        command.addSyntax((sender, context) -> {

        // Start of a method/block
        }, Literal("arg").setSuggestionCallback((sender, context, suggestion) -> {
            // Calls a method
            assertEquals(player, sender);
            // Calls a method
            assertEquals("test", context.getCommandName());
            // Calls a method
            assertEquals("test arg te", context.getInput());
            // Calls a method
            suggestion.addEntry(new SuggestionEntry("test1"));
        // Code statement
        }));

        // Calls a method
        env.process().command().register(command);

        // Calls a method
        var listener = connection.trackIncoming(TabCompletePacket.class);
        // Calls a method
        player.addPacketToQueue(new ClientTabCompletePacket(3, "test arg te"));
        // Calls a method
        player.interpretPacketQueue();

        // Start of a method/block
        listener.assertSingle(tabCompletePacket -> {
            // Calls a method
            assertEquals(3, tabCompletePacket.transactionId());
            // Calls a method
            assertEquals(10, tabCompletePacket.start());
            // Calls a method
            assertEquals(2, tabCompletePacket.length());
            // Calls a method
            assertEquals(List.of(new TabCompletePacket.Match("test1", null)), tabCompletePacket.matches());
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void suggestionWithDefaults(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Assigns a value
        var suggestArg = Word("suggestArg").setSuggestionCallback(
                // Code statement
                (sender, context, suggestion) -> suggestion.addEntry(new SuggestionEntry("suggestion"))
        // End of a block/expression
        );
        // Calls a method
        var defaultArg = Integer("defaultArg").setDefaultValue(123);

        // Calls a method
        var command = new Command("foo");

        // Calls a method
        command.addSyntax((sender,context)->{}, suggestArg, defaultArg);
        // Calls a method
        env.process().command().register(command);

        // Calls a method
        var listener = connection.trackIncoming(TabCompletePacket.class);
        // Calls a method
        player.addPacketToQueue(new ClientTabCompletePacket(1, "foo 1"));
        // Calls a method
        player.interpretPacketQueue();

        // Calls a method
        listener.assertSingle(tabCompletePacket -> assertEquals(List.of(new TabCompletePacket.Match("suggestion", null)), tabCompletePacket.matches()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void suggestionWithSubcommand(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Calls a method
        var command = new Command("foo");

        // Calls a method
        var subCommand = new Command("bar");

        // Calls a method
        var wordArg1 = Word("wordArg1").setSuggestionCallback((sender, context, suggestion) -> suggestion.addEntry(new SuggestionEntry("suggestionA")));
        // Calls a method
        var wordArg2 = Word("wordArg2").setSuggestionCallback((sender, context, suggestion) -> suggestion.addEntry(new SuggestionEntry("suggestionB")));

        // Calls a method
        subCommand.addSyntax((sender, context) -> {}, wordArg1, wordArg2);

        // Calls a method
        command.addSyntax((sender,context)->{}, Literal("literal"), wordArg2);

        // Calls a method
        command.addSubcommand(subCommand);

        // Calls a method
        env.process().command().register(command);

        // Calls a method
        var listener = connection.trackIncoming(TabCompletePacket.class);
        // Calls a method
        player.addPacketToQueue(new ClientTabCompletePacket(1, "foo bar "));
        // Calls a method
        player.interpretPacketQueue();

        // Calls a method
        listener.assertSingle(tabCompletePacket -> assertEquals(List.of(new TabCompletePacket.Match("suggestionA", null)), tabCompletePacket.matches()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void suggestionWithTwoLiterals(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Calls a method
        var command = new Command("foo");

        // Calls a method
        var wordArg1 = Word("wordArg1").setSuggestionCallback((sender, context, suggestion) -> suggestion.addEntry(new SuggestionEntry("suggestionA")));
        // Calls a method
        var wordArg2 = Word("wordArg2").setSuggestionCallback((sender, context, suggestion) -> suggestion.addEntry(new SuggestionEntry("suggestionB")));

        // Calls a method
        command.addSyntax((sender,context)->{}, Literal("literal1"), wordArg1);

        // Calls a method
        command.addSyntax((sender,context)->{}, Literal("literal2"), wordArg2);

        // Calls a method
        env.process().command().register(command);

        // Calls a method
        var listener = connection.trackIncoming(TabCompletePacket.class);
        // Calls a method
        player.addPacketToQueue(new ClientTabCompletePacket(1, "foo literal2 "));
        // Calls a method
        player.interpretPacketQueue();

        // Calls a method
        listener.assertSingle(tabCompletePacket -> assertEquals(List.of(new TabCompletePacket.Match("suggestionB", null)), tabCompletePacket.matches()));
    // End of a block/expression
    }
// End of a block/expression
}
