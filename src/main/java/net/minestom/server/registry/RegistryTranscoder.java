// Package declaration for this file
package net.minestom.server.registry;

// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.codec.TranscoderProxy;

// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
public record RegistryTranscoder<D>(
        // Code statement
        Transcoder<D> transcoder,
        // Code statement
        Registries registries,
        // Code statement
        boolean forClient,
        // Code statement
        boolean init // True for initial load
// Start of a method/block
) implements TranscoderProxy<D> {

    // Start of a method/block
    public RegistryTranscoder(Transcoder<D> transcoder, Registries registries) {
        // Calls a method
        this(Objects.requireNonNull(transcoder), registries, false, false);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Transcoder<D> delegate() {
        // Returns a value to the caller
        return transcoder;
    // End of a block/expression
    }

// End of a block/expression
}
