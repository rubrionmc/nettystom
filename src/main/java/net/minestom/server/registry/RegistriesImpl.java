// Déclaration du paquet de ce fichier
package net.minestom.server.registry;

// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.SendablePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.TagsPacket;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
final class RegistriesImpl {
    // Début d'une méthode/d'un bloc
    private RegistriesImpl() {
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static List<SendablePacket> registryDataPackets(Registries registries, boolean excludeVanilla) {
        // Appelle une méthode
        final List<SendablePacket> packets = new ArrayList<>();
        // Boucle : répète un bloc
        for (DynamicRegistry<?> registry : configurationRegistries(registries)) {
            // Appelle une méthode
            packets.add(registry.registryDataPacket(registries, excludeVanilla));
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return packets;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static TagsPacket tagsPacket(Registries registries) {
        // Appelle une méthode
        final List<TagsPacket.Registry> entries = new ArrayList<>();
        // Boucle : répète un bloc
        for (Registry<?> registry : tagRegistries(registries)) {
            // Appelle une méthode
            entries.add(registry.tagRegistry());
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new TagsPacket(entries);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static List<DynamicRegistry<?>> configurationRegistries(Registries registries) {
        // Renvoie une valeur à l'appelant
        return List.of(
                // Instruction de code
                registries.chatType(),
                // Instruction de code
                registries.biome(),
                // Instruction de code
                registries.dialog(),
                // Instruction de code
                registries.damageType(),
                // Instruction de code
                registries.trimMaterial(),
                // Instruction de code
                registries.trimPattern(),
                // Instruction de code
                registries.bannerPattern(),
                // Instruction de code
                registries.enchantment(),
                // Instruction de code
                registries.paintingVariant(),
                // Instruction de code
                registries.jukeboxSong(),
                // Instruction de code
                registries.instrument(),
                // Instruction de code
                registries.wolfVariant(),
                // Instruction de code
                registries.wolfSoundVariant(),
                // Instruction de code
                registries.catVariant(),
                // Instruction de code
                registries.catSoundVariant(),
                // Instruction de code
                registries.chickenVariant(),
                // Instruction de code
                registries.chickenSoundVariant(),
                // Instruction de code
                registries.cowVariant(),
                // Instruction de code
                registries.cowSoundVariant(),
                // Instruction de code
                registries.frogVariant(),
                // Instruction de code
                registries.pigVariant(),
                // Instruction de code
                registries.pigSoundVariant(),
                // Instruction de code
                registries.zombieNautilusVariant(),
                // Instruction de code
                registries.worldClock(),
                // Instruction de code
                registries.timeline(),
                // Instruction de code
                registries.dimensionType()
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static List<Registry<?>> tagRegistries(Registries registries) {
        // These are the registries which contain tags used by the vanilla client.
        // Registries unused by the client do not need to be included.
        // Appelle une méthode
        final List<Registry<?>> entries = new ArrayList<>();
        // Appelle une méthode
        entries.add(registries.blocks());
        // Appelle une méthode
        entries.add(registries.entityType());
        // Appelle une méthode
        entries.add(registries.fluid());
        // Appelle une méthode
        entries.add(registries.gameEvent());
        // Appelle une méthode
        entries.add(registries.material());
        // Appelle une méthode
        entries.addAll(configurationRegistries(registries));
        // Renvoie une valeur à l'appelant
        return entries;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
