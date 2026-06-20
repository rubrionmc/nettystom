// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandResult;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.network.packet.server.play.DeclareCommandsPacket;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.concurrent.atomic.AtomicBoolean;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class CommandManagerTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testCommandRegistration() {
        // Calls a method
        var manager = new CommandManager();

        // Calls a method
        var command = new Command("name1", "name2");

        // Calls a method
        manager.register(command);

        // Calls a method
        assertTrue(manager.commandExists("name1"));
        // Calls a method
        assertTrue(manager.commandExists("name2"));
        // Calls a method
        assertFalse(manager.commandExists("name3"));

        // Calls a method
        manager.unregister(command);

        // Calls a method
        assertFalse(manager.commandExists("name1"));
        // Calls a method
        assertFalse(manager.commandExists("name2"));
        // Calls a method
        assertFalse(manager.commandExists("name3"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testUnknownCommandCallback() {
        // Calls a method
        var manager = new CommandManager();

        // Calls a method
        AtomicBoolean check = new AtomicBoolean(false);
        // Calls a method
        manager.setUnknownCommandCallback((sender, command) -> check.set(true));

        // Calls a method
        manager.register(new Command("valid_command"));

        // Calls a method
        manager.executeServerCommand("valid_command");
        // Calls a method
        assertFalse(check.get());

        // Calls a method
        manager.executeServerCommand("invalid_command");
        // Calls a method
        assertTrue(check.get());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testSharedArgumentSyntaxABFirst() {
        // Calls a method
        var manager = new CommandManager();

        // Calls a method
        var checkA = new AtomicBoolean(false);
        // Calls a method
        var checkAB = new AtomicBoolean(false);

        // Calls a method
        var cmd = new Command("cmd");
        // Calls a method
        var argA = ArgumentType.String("a");
        // Calls a method
        var argB = ArgumentType.String("b");
        // Calls a method
        cmd.addSyntax((sender, context) -> checkAB.set(true), argA, argB);
        // Calls a method
        cmd.addSyntax((sender, context) -> checkA.set(true), argA);
        // Calls a method
        manager.register(cmd);

        // Calls a method
        var result = manager.executeServerCommand("cmd a");
        // Calls a method
        assertEquals(CommandResult.Type.SUCCESS, result.getType());
        // Calls a method
        assertTrue(checkA.get());
        // Calls a method
        assertFalse(checkAB.get());

        // Code statement
        checkA.set(false); // these should be different tests
        // Calls a method
        checkAB.set(false);

        // Calls a method
        result = manager.executeServerCommand("cmd a b");
        // Calls a method
        assertEquals(CommandResult.Type.SUCCESS, result.getType());
        // Calls a method
        assertFalse(checkA.get());
        // Calls a method
        assertTrue(checkAB.get());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testSharedArgumentSyntaxAFirst() {
        // Calls a method
        var manager = new CommandManager();

        // Calls a method
        var checkA = new AtomicBoolean(false);
        // Calls a method
        var checkAB = new AtomicBoolean(false);

        // Calls a method
        var cmd = new Command("cmd");
        // Calls a method
        var argA = ArgumentType.String("a");
        // Calls a method
        var argB = ArgumentType.String("b");
        // Calls a method
        cmd.addSyntax((sender, context) -> checkA.set(true), argA);
        // Calls a method
        cmd.addSyntax((sender, context) -> checkAB.set(true), argA, argB);
        // Calls a method
        manager.register(cmd);

        // Calls a method
        var result = manager.executeServerCommand("cmd a");
        // Calls a method
        assertEquals(CommandResult.Type.SUCCESS, result.getType());
        // Calls a method
        assertTrue(checkA.get());
        // Calls a method
        assertFalse(checkAB.get());

        // Code statement
        checkA.set(false); // these should be different tests
        // Calls a method
        checkAB.set(false);

        // Calls a method
        result = manager.executeServerCommand("cmd a b");
        // Calls a method
        assertEquals(CommandResult.Type.SUCCESS, result.getType());
        // Calls a method
        assertFalse(checkA.get());
        // Calls a method
        assertTrue(checkAB.get());
    // End of a block/expression
    }

    // Code statement
    private static void assertNodeEquals(DeclareCommandsPacket.Node node, byte flags, int[] children, int redirectedNode,
                                         // Start of a method/block
                                         String name, String parser, byte[] properties, String suggestionsType) {
        // Calls a method
        assertEquals(flags, node.flags);
        // Calls a method
        assertArrayEquals(children, node.children);
        // Calls a method
        assertEquals(redirectedNode, node.redirectedNode);
        // Calls a method
        assertEquals(name, node.name);
        // Calls a method
        assertEquals(parser, node.parser);
        // Calls a method
        assertArrayEquals(properties, node.properties);
        // Calls a method
        assertEquals(suggestionsType, node.suggestionsType);
    // End of a block/expression
    }

// End of a block/expression
}
