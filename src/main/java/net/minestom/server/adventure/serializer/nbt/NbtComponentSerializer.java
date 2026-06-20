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
public sealed interface NbtComponentSerializer extends ComponentSerializer<Component, Component, BinaryTag> permits NbtComponentSerializerImpl {
    // Début d'une méthode/d'un bloc
    static NbtComponentSerializer nbt() {
        // Renvoie une valeur à l'appelant
        return NbtComponentSerializerImpl.INSTANCE;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
