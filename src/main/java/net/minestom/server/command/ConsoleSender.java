// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.kyori.adventure.identity.Identity;
// Import of a required class
import net.kyori.adventure.pointer.Pointers;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
// Import of a required class
import net.minestom.server.tag.TagHandler;

/**
 * Represents the console when sending a command to the server.
 */
// Type declaration (class/interface/enum/record)
public class ConsoleSender implements CommandSender {
    // Calls a method
    private static final ComponentLogger LOGGER = ComponentLogger.logger(ConsoleSender.class);

    // Calls a method
    private final TagHandler tagHandler = TagHandler.newHandler();

    // Calls a method
    private final Identity identity = Identity.nil();
    // Assigns a value
    private final Pointers pointers = Pointers.builder()
            // Code statement
            .withStatic(Identity.UUID, this.identity.uuid())
            // Calls a method
            .build();

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void sendMessage(String message) {
        // Calls a method
        LOGGER.info(message);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void sendMessage(Component message) {
        // Calls a method
        LOGGER.info(message);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public TagHandler tagHandler() {
        // Returns a value to the caller
        return tagHandler;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Identity identity() {
        // Returns a value to the caller
        return this.identity;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Pointers pointers() {
        // Returns a value to the caller
        return this.pointers;
    // End of a block/expression
    }
// End of a block/expression
}
