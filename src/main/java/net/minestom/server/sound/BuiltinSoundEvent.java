// Package declaration for this file
package net.minestom.server.sound;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.registry.Registry;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import net.minestom.server.registry.StaticProtocolObject;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Type declaration (class/interface/enum/record)
public record BuiltinSoundEvent(Key key, int id) implements StaticProtocolObject<BuiltinSoundEvent>, SoundEvent {
    // Assigns a value
    static final Registry<BuiltinSoundEvent> REGISTRY = RegistryData.createStaticRegistry(Key.key("sound_event"),
            // Calls a method
            (namespace, properties) -> new BuiltinSoundEvent(Key.key(namespace), properties.getInt("id")));

    // Start of a method/block
    static @UnknownNullability SoundEvent get(String key) {
        // Returns a value to the caller
        return REGISTRY.get(Key.key(key));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return name();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String name() {
        // Returns a value to the caller
        return StaticProtocolObject.super.name();
    // End of a block/expression
    }
// End of a block/expression
}
