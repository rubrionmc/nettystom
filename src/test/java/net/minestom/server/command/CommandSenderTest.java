// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.kyori.adventure.identity.Identity;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.minestom.server.tag.TagHandler;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertNull;

// Type declaration (class/interface/enum/record)
public class CommandSenderTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testMessageSending() {
        // Calls a method
        SenderTest sender = new SenderTest();

        // Calls a method
        assertNull(sender.getMostRecentMessage());

        // Calls a method
        sender.sendMessage("Hey!!");
        // Calls a method
        assertEquals(sender.getMostRecentMessage(), Component.text("Hey!!"));

        // Calls a method
        sender.sendMessage(new String[]{"Message", "Sending", "Test"});
        // Calls a method
        assertEquals(sender.getMostRecentMessage(), Component.text("Test"));

        // Calls a method
        sender.sendMessage(Component.text("Message test!", NamedTextColor.GREEN));
        // Calls a method
        assertEquals(sender.getMostRecentMessage(), Component.text("Message test!", NamedTextColor.GREEN));
    // End of a block/expression
    }

    // Start of a method/block
    private static final class SenderTest implements CommandSender {

        // Calls a method
        private final TagHandler handler = TagHandler.newHandler();

        // Assigns a value
        private Component mostRecentMessage = null;

        // Annotation for the following element
        @Override
        // Start of a method/block
        public TagHandler tagHandler() {
            // Returns a value to the caller
            return handler;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void sendMessage(Component message) {
            // Assigns a value
            mostRecentMessage = message;
        // End of a block/expression
        }

        // Start of a method/block
        public @Nullable Component getMostRecentMessage() {
            // Returns a value to the caller
            return mostRecentMessage;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Identity identity() {
            // Returns a value to the caller
            return Identity.nil();
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
