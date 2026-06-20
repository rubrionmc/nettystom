// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.concurrent.atomic.AtomicBoolean;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertFalse;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertTrue;

// Type declaration (class/interface/enum/record)
public class SubcommandTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testSubCommands() {
        // Calls a method
        var manager = new CommandManager();

        // Calls a method
        var parent = new Command("parent");
        // Calls a method
        var child = new Command("child");

        // Calls a method
        parent.addSubcommand(child);
        // Calls a method
        manager.register(parent);

        // Calls a method
        AtomicBoolean parentExecuted = new AtomicBoolean(false);
        // Calls a method
        AtomicBoolean childExecuted = new AtomicBoolean(false);

        // Calls a method
        parent.setDefaultExecutor((sender, context) -> parentExecuted.set(true));
        // Calls a method
        child.setDefaultExecutor((sender, context) -> childExecuted.set(true));

        // Calls a method
        manager.executeServerCommand("parent child");

        // Calls a method
        assertFalse(parentExecuted.get());
        // Calls a method
        assertTrue(childExecuted.get());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testSubCommandConditions() {
        // Calls a method
        var manager = new CommandManager();

        // Calls a method
        var parent = new Command("parent");
        // Calls a method
        var child = new Command("child");

        // Calls a method
        parent.addSubcommand(child);
        // Calls a method
        manager.register(parent);

        // Calls a method
        AtomicBoolean parentConditionTriggered = new AtomicBoolean(false);
        // Calls a method
        AtomicBoolean childConditionTriggered = new AtomicBoolean(false);
        // Calls a method
        AtomicBoolean parentExecuted = new AtomicBoolean(false);
        // Calls a method
        AtomicBoolean childExecuted = new AtomicBoolean(false);

        // Start of a method/block
        parent.setCondition((sender, commandString) -> {
            // Calls a method
            parentConditionTriggered.set(true);
            // Returns a value to the caller
            return true; // Return true so the child's condition has a chance to get tested
        // End of a block/expression
        });
        // Start of a method/block
        child.setCondition((sender, commandString) -> {
            // Calls a method
            childConditionTriggered.set(true);
            // Returns a value to the caller
            return false;
        // End of a block/expression
        });
        // Calls a method
        parent.setDefaultExecutor((sender, context) -> parentExecuted.set(true));
        // Calls a method
        child.setDefaultExecutor((sender, context) -> childExecuted.set(true));

        // Calls a method
        manager.executeServerCommand("parent child");

        // Calls a method
        assertTrue(parentConditionTriggered.get());
        // Calls a method
        assertTrue(childConditionTriggered.get());
        // Calls a method
        assertFalse(parentExecuted.get());
        // Calls a method
        assertFalse(childExecuted.get());
    // End of a block/expression
    }
// End of a block/expression
}
