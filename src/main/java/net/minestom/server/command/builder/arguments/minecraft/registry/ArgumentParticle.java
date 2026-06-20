// Package declaration for this file
package net.minestom.server.command.builder.arguments.minecraft.registry;

// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.particle.Particle;

/**
 * Represents an argument giving a {@link Particle}.
 */
// Type declaration (class/interface/enum/record)
public class ArgumentParticle extends ArgumentRegistry<Particle> {

    // Start of a method/block
    public ArgumentParticle(String id) {
        // Access to the current/parent object
        super(id);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ArgumentParserType parser() {
        // Returns a value to the caller
        return ArgumentParserType.PARTICLE;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Particle getRegistry(String value) {
        // Returns a value to the caller
        return Particle.fromKey(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("Particle<%s>", getId());
    // End of a block/expression
    }
// End of a block/expression
}
