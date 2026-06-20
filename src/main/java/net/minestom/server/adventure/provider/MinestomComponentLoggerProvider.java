// Package declaration for this file
package net.minestom.server.adventure.provider;

// Import of a required class
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
// Import of a required class
import net.kyori.adventure.text.logger.slf4j.ComponentLoggerProvider;
// Import of a required class
import net.kyori.adventure.text.serializer.ansi.ANSIComponentSerializer;
// Import of a required class
import org.slf4j.LoggerFactory;

// Annotation for the following element
@SuppressWarnings("UnstableApiUsage") // we are permitted to provide this
// Type declaration (class/interface/enum/record)
public final class MinestomComponentLoggerProvider implements ComponentLoggerProvider {
    // Calls a method
    private static final ANSIComponentSerializer SERIALIZER = ANSIComponentSerializer.ansi();

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ComponentLogger logger(LoggerHelper helper, String name) {
        // Returns a value to the caller
        return helper.delegating(LoggerFactory.getLogger(name), SERIALIZER::serialize);
    // End of a block/expression
    }
// End of a block/expression
}
