// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.kyori.adventure.audience.Audience;
// Import of a required class
import net.kyori.adventure.identity.Identity;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import net.minestom.server.tag.TagHandler;

/**
 * Sender used in {@link CommandManager#executeServerCommand(String)}.
 * <p>
 * Although this class implemented {@link CommandSender} and thus {@link Audience}, no
 * data can be sent to this sender because it's purpose is to process the data of
 * {@link CommandContext#getReturnData()}.
 */
// Type declaration (class/interface/enum/record)
public class ServerSender implements CommandSender {

    // Calls a method
    private final TagHandler tagHandler = TagHandler.newHandler();


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
        return Identity.nil();
    // End of a block/expression
    }
// End of a block/expression
}
