// Package declaration for this file
package net.minestom.server.registry;

// Import of a required class
import net.minestom.server.network.packet.server.SendablePacket;
// Import of a required class
import net.minestom.server.network.packet.server.common.TagsPacket;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
final class RegistriesImpl {
    // Start of a method/block
    private RegistriesImpl() {
    // End of a block/expression
    }

    // Start of a method/block
    static List<SendablePacket> registryDataPackets(Registries registries, boolean excludeVanilla) {
        // Calls a method
        final List<SendablePacket> packets = new ArrayList<>();
        // Loop: repeats a block
        for (DynamicRegistry<?> registry : configurationRegistries(registries)) {
            // Calls a method
            packets.add(registry.registryDataPacket(registries, excludeVanilla));
        // End of a block/expression
        }
        // Returns a value to the caller
        return packets;
    // End of a block/expression
    }

    // Start of a method/block
    static TagsPacket tagsPacket(Registries registries) {
        // Calls a method
        final List<TagsPacket.Registry> entries = new ArrayList<>();
        // Loop: repeats a block
        for (Registry<?> registry : tagRegistries(registries)) {
            // Calls a method
            entries.add(registry.tagRegistry());
        // End of a block/expression
        }
        // Returns a value to the caller
        return new TagsPacket(entries);
    // End of a block/expression
    }

    // Start of a method/block
    private static List<DynamicRegistry<?>> configurationRegistries(Registries registries) {
        // Returns a value to the caller
        return List.of(
                // Code statement
                registries.chatType(),
                // Code statement
                registries.biome(),
                // Code statement
                registries.dialog(),
                // Code statement
                registries.damageType(),
                // Code statement
                registries.trimMaterial(),
                // Code statement
                registries.trimPattern(),
                // Code statement
                registries.bannerPattern(),
                // Code statement
                registries.enchantment(),
                // Code statement
                registries.paintingVariant(),
                // Code statement
                registries.jukeboxSong(),
                // Code statement
                registries.instrument(),
                // Code statement
                registries.wolfVariant(),
                // Code statement
                registries.wolfSoundVariant(),
                // Code statement
                registries.catVariant(),
                // Code statement
                registries.catSoundVariant(),
                // Code statement
                registries.chickenVariant(),
                // Code statement
                registries.chickenSoundVariant(),
                // Code statement
                registries.cowVariant(),
                // Code statement
                registries.cowSoundVariant(),
                // Code statement
                registries.frogVariant(),
                // Code statement
                registries.pigVariant(),
                // Code statement
                registries.pigSoundVariant(),
                // Code statement
                registries.zombieNautilusVariant(),
                // Code statement
                registries.worldClock(),
                // Code statement
                registries.timeline(),
                // Code statement
                registries.dimensionType()
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Start of a method/block
    private static List<Registry<?>> tagRegistries(Registries registries) {
        // These are the registries which contain tags used by the vanilla client.
        // Registries unused by the client do not need to be included.
        // Calls a method
        final List<Registry<?>> entries = new ArrayList<>();
        // Calls a method
        entries.add(registries.blocks());
        // Calls a method
        entries.add(registries.entityType());
        // Calls a method
        entries.add(registries.fluid());
        // Calls a method
        entries.add(registries.gameEvent());
        // Calls a method
        entries.add(registries.material());
        // Calls a method
        entries.addAll(configurationRegistries(registries));
        // Returns a value to the caller
        return entries;
    // End of a block/expression
    }
// End of a block/expression
}
