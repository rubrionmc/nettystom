// Package declaration for this file
package net.minestom.server.command.builder.arguments.minecraft;

// Import of a required class
import com.google.gson.JsonElement;
// Import of a required class
import com.google.gson.JsonParseException;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.Result;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import of a required class
import net.minestom.server.registry.RegistryTranscoder;
// Import of a required class
import net.minestom.server.utils.json.JsonUtil;

// Type declaration (class/interface/enum/record)
public class ArgumentComponent extends Argument<Component> {
    // Assigns a value
    public static final int INVALID_JSON_ERROR = 1;

    // Start of a method/block
    public ArgumentComponent(String id) {
        // Access to the current/parent object
        super(id, true);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Component parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Exception handling
        try {
            // Calls a method
            final Transcoder<JsonElement> coder = new RegistryTranscoder<>(Transcoder.JSON, MinecraftServer.process());
            // Calls a method
            final Result<Component> result = Codec.COMPONENT.decode(coder, JsonUtil.fromJson(input));
            // Returns a value to the caller
            return switch (result) {
                // Multiple branching (switch/case)
                case Result.Ok(var component) -> component;
                // Multiple branching (switch/case)
                case Result.Error(var message) ->
                        // Throws an exception
                        throw new ArgumentSyntaxException("Failed to parse component: " + message, input, INVALID_JSON_ERROR);
            // End of a block/expression
            };
        // Start of a method/block
        } catch (JsonParseException e) {
            // Throws an exception
            throw new ArgumentSyntaxException("Invalid JSON", input, INVALID_JSON_ERROR);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ArgumentParserType parser() {
        // Returns a value to the caller
        return ArgumentParserType.COMPONENT;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("Component<%s>", getId());
    // End of a block/expression
    }
// End of a block/expression
}
