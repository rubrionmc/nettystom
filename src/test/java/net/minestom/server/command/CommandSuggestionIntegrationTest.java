// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientTabCompletePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.TabCompletePacket;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.*;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNull;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class CommandSuggestionIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void suggestion(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Appelle une méthode
        var command = new Command("test");
        // Début d'une méthode/d'un bloc
        command.addSyntax((sender, context) -> {

        // Début d'une méthode/d'un bloc
        }, Literal("arg").setSuggestionCallback((sender, context, suggestion) -> {
            // Appelle une méthode
            assertEquals(player, sender);
            // Appelle une méthode
            assertEquals("test", context.getCommandName());
            // Appelle une méthode
            assertEquals("test arg te", context.getInput());
            // Appelle une méthode
            suggestion.addEntry(new SuggestionEntry("test1"));
        // Instruction de code
        }));

        // Appelle une méthode
        env.process().command().register(command);

        // Appelle une méthode
        var listener = connection.trackIncoming(TabCompletePacket.class);
        // Appelle une méthode
        player.addPacketToQueue(new ClientTabCompletePacket(3, "test arg te"));
        // Appelle une méthode
        player.interpretPacketQueue();

        // Début d'une méthode/d'un bloc
        listener.assertSingle(tabCompletePacket -> {
            // Appelle une méthode
            assertEquals(3, tabCompletePacket.transactionId());
            // Appelle une méthode
            assertEquals(10, tabCompletePacket.start());
            // Appelle une méthode
            assertEquals(2, tabCompletePacket.length());
            // Appelle une méthode
            assertEquals(List.of(new TabCompletePacket.Match("test1", null)), tabCompletePacket.matches());
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void suggestionWithDefaults(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Affecte une valeur
        var suggestArg = Word("suggestArg").setSuggestionCallback(
                // Instruction de code
                (sender, context, suggestion) -> suggestion.addEntry(new SuggestionEntry("suggestion"))
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        var defaultArg = Integer("defaultArg").setDefaultValue(123);

        // Appelle une méthode
        var command = new Command("foo");

        // Appelle une méthode
        command.addSyntax((sender,context)->{}, suggestArg, defaultArg);
        // Appelle une méthode
        env.process().command().register(command);

        // Appelle une méthode
        var listener = connection.trackIncoming(TabCompletePacket.class);
        // Appelle une méthode
        player.addPacketToQueue(new ClientTabCompletePacket(1, "foo 1"));
        // Appelle une méthode
        player.interpretPacketQueue();

        // Début d'une méthode/d'un bloc
        listener.assertSingle(tabCompletePacket -> {
            // Appelle une méthode
            assertEquals(List.of(new TabCompletePacket.Match("suggestion", null)), tabCompletePacket.matches());
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void suggestionWithSubcommand(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Appelle une méthode
        var command = new Command("foo");

        // Appelle une méthode
        var subCommand = new Command("bar");

        // Affecte une valeur
        var wordArg1 = Word("wordArg1").setSuggestionCallback((sender, context, suggestion) -> {
            // Appelle une méthode
            suggestion.addEntry(new SuggestionEntry("suggestionA"));
        // Fin d'un bloc/d'une expression
        });
        // Affecte une valeur
        var wordArg2 = Word("wordArg2").setSuggestionCallback((sender, context, suggestion) -> {
                    // Appelle une méthode
                    suggestion.addEntry(new SuggestionEntry("suggestionB"));
                // Fin d'un bloc/d'une expression
                });

        // Appelle une méthode
        subCommand.addSyntax((sender, context) -> {}, wordArg1, wordArg2);

        // Appelle une méthode
        command.addSyntax((sender,context)->{}, Literal("literal"), wordArg2);

        // Appelle une méthode
        command.addSubcommand(subCommand);

        // Appelle une méthode
        env.process().command().register(command);

        // Appelle une méthode
        var listener = connection.trackIncoming(TabCompletePacket.class);
        // Appelle une méthode
        player.addPacketToQueue(new ClientTabCompletePacket(1, "foo bar "));
        // Appelle une méthode
        player.interpretPacketQueue();

        // Début d'une méthode/d'un bloc
        listener.assertSingle(tabCompletePacket -> {
            // Appelle une méthode
            assertEquals(List.of(new TabCompletePacket.Match("suggestionA", null)), tabCompletePacket.matches());
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void suggestionWithTwoLiterals(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 42, 0));

        // Appelle une méthode
        var command = new Command("foo");

        // Affecte une valeur
        var wordArg1 = Word("wordArg1").setSuggestionCallback((sender, context, suggestion) -> {
            // Appelle une méthode
            suggestion.addEntry(new SuggestionEntry("suggestionA"));
        // Fin d'un bloc/d'une expression
        });
        // Affecte une valeur
        var wordArg2 = Word("wordArg2").setSuggestionCallback((sender, context, suggestion) -> {
            // Appelle une méthode
            suggestion.addEntry(new SuggestionEntry("suggestionB"));
        // Fin d'un bloc/d'une expression
        });

        // Appelle une méthode
        command.addSyntax((sender,context)->{}, Literal("literal1"), wordArg1);

        // Appelle une méthode
        command.addSyntax((sender,context)->{}, Literal("literal2"), wordArg2);

        // Appelle une méthode
        env.process().command().register(command);

        // Appelle une méthode
        var listener = connection.trackIncoming(TabCompletePacket.class);
        // Appelle une méthode
        player.addPacketToQueue(new ClientTabCompletePacket(1, "foo literal2 "));
        // Appelle une méthode
        player.interpretPacketQueue();

        // Début d'une méthode/d'un bloc
        listener.assertSingle(tabCompletePacket -> {
            // Appelle une méthode
            assertEquals(List.of(new TabCompletePacket.Match("suggestionB", null)), tabCompletePacket.matches());
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
