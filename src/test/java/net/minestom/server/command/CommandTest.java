// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.concurrent.atomic.AtomicBoolean;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class CommandTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testNames() {
        // Calls a method
        Command command = new Command("name1", "name2", "name3");

        // Calls a method
        assertEquals("name1", command.getName());
        // Calls a method
        assertArrayEquals(new String[]{"name2", "name3"}, command.getAliases());

        // command#getNames does not have any order guarantee, so that cannot be relied on
        // Calls a method
        assertEquals(Set.of("name1", "name2", "name3"), Set.of(command.getNames()));

        // Calls a method
        assertTrue(Command.isValidName(command, "name1"));
        // Calls a method
        assertTrue(Command.isValidName(command, "name2"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testGlobalListener() {
        // Calls a method
        var manager = new CommandManager();

        // Calls a method
        AtomicBoolean hasRun = new AtomicBoolean(false);

        // Assigns a value
        var command = new Command("command") {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void globalListener(CommandSender sender, CommandContext context, String command) {
                // Calls a method
                hasRun.set(true);
                // Calls a method
                context.setArg("key", "value", "value");
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Calls a method
        manager.register(command);

        // Calls a method
        AtomicBoolean checkSet = new AtomicBoolean(false);
        // Calls a method
        command.setDefaultExecutor((sender, context) -> checkSet.set("value".equals(context.get("key"))));

        // Calls a method
        manager.executeServerCommand("command");

        // Calls a method
        assertTrue(hasRun.get());
        // Calls a method
        assertTrue(checkSet.get());

    // End of a block/expression
    }
// End of a block/expression
}
