// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandResult;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.DeclareCommandsPacket;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicBoolean;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class CommandManagerTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testCommandRegistration() {
        // Appelle une méthode
        var manager = new CommandManager();

        // Appelle une méthode
        var command = new Command("name1", "name2");

        // Appelle une méthode
        manager.register(command);

        // Appelle une méthode
        assertTrue(manager.commandExists("name1"));
        // Appelle une méthode
        assertTrue(manager.commandExists("name2"));
        // Appelle une méthode
        assertFalse(manager.commandExists("name3"));

        // Appelle une méthode
        manager.unregister(command);

        // Appelle une méthode
        assertFalse(manager.commandExists("name1"));
        // Appelle une méthode
        assertFalse(manager.commandExists("name2"));
        // Appelle une méthode
        assertFalse(manager.commandExists("name3"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testUnknownCommandCallback() {
        // Appelle une méthode
        var manager = new CommandManager();

        // Appelle une méthode
        AtomicBoolean check = new AtomicBoolean(false);
        // Appelle une méthode
        manager.setUnknownCommandCallback((sender, command) -> check.set(true));

        // Appelle une méthode
        manager.register(new Command("valid_command"));

        // Appelle une méthode
        manager.executeServerCommand("valid_command");
        // Appelle une méthode
        assertFalse(check.get());

        // Appelle une méthode
        manager.executeServerCommand("invalid_command");
        // Appelle une méthode
        assertTrue(check.get());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testSharedArgumentSyntaxABFirst() {
        // Appelle une méthode
        var manager = new CommandManager();

        // Appelle une méthode
        var checkA = new AtomicBoolean(false);
        // Appelle une méthode
        var checkAB = new AtomicBoolean(false);

        // Appelle une méthode
        var cmd = new Command("cmd");
        // Appelle une méthode
        var argA = ArgumentType.String("a");
        // Appelle une méthode
        var argB = ArgumentType.String("b");
        // Appelle une méthode
        cmd.addSyntax((sender, context) -> checkAB.set(true), argA, argB);
        // Appelle une méthode
        cmd.addSyntax((sender, context) -> checkA.set(true), argA);
        // Appelle une méthode
        manager.register(cmd);

        // Appelle une méthode
        var result = manager.executeServerCommand("cmd a");
        // Appelle une méthode
        assertEquals(CommandResult.Type.SUCCESS, result.getType());
        // Appelle une méthode
        assertTrue(checkA.get());
        // Appelle une méthode
        assertFalse(checkAB.get());

        // Instruction de code
        checkA.set(false); // these should be different tests
        // Appelle une méthode
        checkAB.set(false);

        // Appelle une méthode
        result = manager.executeServerCommand("cmd a b");
        // Appelle une méthode
        assertEquals(CommandResult.Type.SUCCESS, result.getType());
        // Appelle une méthode
        assertFalse(checkA.get());
        // Appelle une méthode
        assertTrue(checkAB.get());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testSharedArgumentSyntaxAFirst() {
        // Appelle une méthode
        var manager = new CommandManager();

        // Appelle une méthode
        var checkA = new AtomicBoolean(false);
        // Appelle une méthode
        var checkAB = new AtomicBoolean(false);

        // Appelle une méthode
        var cmd = new Command("cmd");
        // Appelle une méthode
        var argA = ArgumentType.String("a");
        // Appelle une méthode
        var argB = ArgumentType.String("b");
        // Appelle une méthode
        cmd.addSyntax((sender, context) -> checkA.set(true), argA);
        // Appelle une méthode
        cmd.addSyntax((sender, context) -> checkAB.set(true), argA, argB);
        // Appelle une méthode
        manager.register(cmd);

        // Appelle une méthode
        var result = manager.executeServerCommand("cmd a");
        // Appelle une méthode
        assertEquals(CommandResult.Type.SUCCESS, result.getType());
        // Appelle une méthode
        assertTrue(checkA.get());
        // Appelle une méthode
        assertFalse(checkAB.get());

        // Instruction de code
        checkA.set(false); // these should be different tests
        // Appelle une méthode
        checkAB.set(false);

        // Appelle une méthode
        result = manager.executeServerCommand("cmd a b");
        // Appelle une méthode
        assertEquals(CommandResult.Type.SUCCESS, result.getType());
        // Appelle une méthode
        assertFalse(checkA.get());
        // Appelle une méthode
        assertTrue(checkAB.get());
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static void assertNodeEquals(DeclareCommandsPacket.Node node, byte flags, int[] children, int redirectedNode,
                                         // Début d'une méthode/d'un bloc
                                         String name, String parser, byte[] properties, String suggestionsType) {
        // Appelle une méthode
        assertEquals(flags, node.flags);
        // Appelle une méthode
        assertArrayEquals(children, node.children);
        // Appelle une méthode
        assertEquals(redirectedNode, node.redirectedNode);
        // Appelle une méthode
        assertEquals(name, node.name);
        // Appelle une méthode
        assertEquals(parser, node.parser);
        // Appelle une méthode
        assertArrayEquals(properties, node.properties);
        // Appelle une méthode
        assertEquals(suggestionsType, node.suggestionsType);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
