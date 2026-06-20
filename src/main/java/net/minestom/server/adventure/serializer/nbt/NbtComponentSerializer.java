// Package declaration for this file
package net.minestom.server.adventure.serializer.nbt;

// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.format.Style;
// Import of a required class
import net.kyori.adventure.text.serializer.ComponentSerializer;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.registry.RegistryTranscoder;

// Type declaration (class/interface/enum/record)
public sealed interface NbtComponentSerializer extends ComponentSerializer<Component, Component, BinaryTag> permits NbtComponentSerializerImpl {
    // Start of a method/block
    static NbtComponentSerializer nbt() {
        // Returns a value to the caller
        return NbtComponentSerializerImpl.INSTANCE;
    // End of a block/expression
    }
// End of a block/expression
}
