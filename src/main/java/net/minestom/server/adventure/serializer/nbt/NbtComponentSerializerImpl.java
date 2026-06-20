// Package declaration for this file
package net.minestom.server.adventure.serializer.nbt;

// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.registry.RegistryTranscoder;

// Type declaration (class/interface/enum/record)
final class NbtComponentSerializerImpl implements NbtComponentSerializer {
    // Calls a method
    static final NbtComponentSerializer INSTANCE = new NbtComponentSerializerImpl();

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Component deserialize(BinaryTag input) {
        // Calls a method
        final Transcoder<BinaryTag> coder = new RegistryTranscoder<>(Transcoder.NBT, MinecraftServer.process());
        // Returns a value to the caller
        return Codec.COMPONENT.decode(coder, input).orElseThrow();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public BinaryTag serialize(Component component) {
        // Calls a method
        final Transcoder<BinaryTag> coder = new RegistryTranscoder<>(Transcoder.NBT, MinecraftServer.process());
        // Returns a value to the caller
        return Codec.COMPONENT.encode(coder, component).orElseThrow();
    // End of a block/expression
    }

// End of a block/expression
}
