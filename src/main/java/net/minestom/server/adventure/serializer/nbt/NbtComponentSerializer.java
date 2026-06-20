// Déclaration du paquet de ce fichier
package net.minestom.server.adventure.serializer.nbt;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.Style;
// Import d'une classe nécessaire
import net.kyori.adventure.text.serializer.ComponentSerializer;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTranscoder;

// Déclaration de type (classe/interface/enum/record)
public interface NbtComponentSerializer extends ComponentSerializer<Component, Component, BinaryTag> {
    // Début d'une méthode/d'un bloc
    static NbtComponentSerializer nbt() {
        // Renvoie une valeur à l'appelant
        return NbtComponentSerializerImpl.INSTANCE;
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link Codec#COMPONENT_STYLE} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated(forRemoval = true)
    // Début d'une méthode/d'un bloc
    default Style deserializeStyle(BinaryTag tag) {
        // Appelle une méthode
        final Transcoder<BinaryTag> coder = new RegistryTranscoder<>(Transcoder.NBT, MinecraftServer.process());
        // Renvoie une valeur à l'appelant
        return Codec.COMPONENT_STYLE.decode(coder, tag).orElseThrow();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link Codec#COMPONENT_STYLE} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated(forRemoval = true)
    // Début d'une méthode/d'un bloc
    default CompoundBinaryTag serializeStyle(Style style) {
        // Appelle une méthode
        final Transcoder<BinaryTag> coder = new RegistryTranscoder<>(Transcoder.NBT, MinecraftServer.process());
        // Renvoie une valeur à l'appelant
        return (CompoundBinaryTag) Codec.COMPONENT_STYLE.encode(coder, style).orElseThrow();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
