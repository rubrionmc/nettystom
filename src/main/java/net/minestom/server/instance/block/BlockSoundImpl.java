// Déclaration du paquet de ce fichier
package net.minestom.server.instance.block;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registry;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Déclaration de type (classe/interface/enum/record)
record BlockSoundImpl(RegistryData.BlockSoundTypeEntry registry) implements BlockSoundType {
    // Affecte une valeur
    static final Registry<BlockSoundType> REGISTRY = RegistryData.createStaticRegistry(Key.key("block_sound_type"),
            // Appelle une méthode
            (namespace, properties) -> new BlockSoundImpl(RegistryData.blockSoundTypeEntry(namespace, properties)));

    // Début d'une méthode/d'un bloc
    static @UnknownNullability BlockSoundType get(String key) {
        // Renvoie une valeur à l'appelant
        return REGISTRY.get(Key.key(key));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
