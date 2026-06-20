// Déclaration du paquet de ce fichier
package net.minestom.server.adventure.serializer.nbt;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTranscoder;

// Déclaration de type (classe/interface/enum/record)
final class NbtComponentSerializerImpl implements NbtComponentSerializer {
    // Appelle une méthode
    static final NbtComponentSerializer INSTANCE = new NbtComponentSerializerImpl();

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Component deserialize(BinaryTag input) {
        // Appelle une méthode
        final Transcoder<BinaryTag> coder = new RegistryTranscoder<>(Transcoder.NBT, MinecraftServer.process());
        // Renvoie une valeur à l'appelant
        return Codec.COMPONENT.decode(coder, input).orElseThrow();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public BinaryTag serialize(Component component) {
        // Appelle une méthode
        final Transcoder<BinaryTag> coder = new RegistryTranscoder<>(Transcoder.NBT, MinecraftServer.process());
        // Renvoie une valeur à l'appelant
        return Codec.COMPONENT.encode(coder, component).orElseThrow();
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
