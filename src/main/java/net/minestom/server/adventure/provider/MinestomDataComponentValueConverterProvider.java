// Package declaration for this file
package net.minestom.server.adventure.provider;

// Import of a required class
import com.google.gson.JsonElement;
// Import of a required class
import com.google.gson.JsonNull;
// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.text.event.DataComponentValue;
// Import of a required class
import net.kyori.adventure.text.event.DataComponentValueConverterRegistry;
// Import of a required class
import net.kyori.adventure.text.serializer.gson.GsonDataComponentValue;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.adventure.MinestomDataComponentValue;
// Import of a required class
import net.minestom.server.adventure.serializer.nbt.NbtDataComponentValue;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.registry.RegistryTranscoder;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.kyori.adventure.text.event.DataComponentValueConverterRegistry.Conversion.convert;

// Annotation for the following element
@SuppressWarnings("UnstableApiUsage") // we are permitted to provide this
// Type declaration (class/interface/enum/record)
public final class MinestomDataComponentValueConverterProvider implements DataComponentValueConverterRegistry.Provider {

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Key id() {
        // Returns a value to the caller
        return Key.key("minestom", "data_component_value_converter");
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    public Iterable<DataComponentValueConverterRegistry.Conversion<?, ?>> conversions() {
        // Returns a value to the caller
        return List.of(
                // GSON
                // Start of a method/block
                convert(GsonDataComponentValue.class, MinestomDataComponentValue.class, (key, gsonValue) -> {
                    // Branch: checks a condition
                    if (gsonValue instanceof DataComponentValue.Removed)
                        // Returns a value to the caller
                        return MinestomDataComponentValue.removed();
                    // Calls a method
                    final DataComponent<Object> component = (DataComponent<Object>) DataComponent.fromKey(key);
                    // Branch: checks a condition
                    if (component == null) throw new IllegalArgumentException("Unknown data component: " + key);
                    // Assigns a value
                    final Object value = component.decode(new RegistryTranscoder<>(Transcoder.JSON,
                            // Calls a method
                            MinecraftServer.process()), gsonValue.element()).orElseThrow("failed to decode " + key);
                    // Returns a value to the caller
                    return MinestomDataComponentValue.dataComponentValue(value);
                // Code statement
                }),
                // Start of a method/block
                convert(MinestomDataComponentValue.class, GsonDataComponentValue.class, (key, minestomValue) -> {
                    // Branch: checks a condition
                    if (minestomValue instanceof DataComponentValue.Removed)
                        // Returns a value to the caller
                        return GsonDataComponentValue.gsonDataComponentValue(JsonNull.INSTANCE);
                    // Calls a method
                    final DataComponent<Object> component = (DataComponent<Object>) DataComponent.fromKey(key);
                    // Branch: checks a condition
                    if (component == null) throw new IllegalArgumentException("Unknown data component: " + key);
                    // Assigns a value
                    final JsonElement value = component.encode(new RegistryTranscoder<>(Transcoder.JSON,
                            // Calls a method
                            MinecraftServer.process()), minestomValue.value()).orElseThrow("failed to encode " + key);
                    // Returns a value to the caller
                    return GsonDataComponentValue.gsonDataComponentValue(value);
                // Code statement
                }),

                // NBT
                // Start of a method/block
                convert(NbtDataComponentValue.class, MinestomDataComponentValue.class, (key, nbtValue) -> {
                    // Branch: checks a condition
                    if (nbtValue instanceof DataComponentValue.Removed)
                        // Returns a value to the caller
                        return MinestomDataComponentValue.removed();
                    // Calls a method
                    final DataComponent<Object> component = (DataComponent<Object>) DataComponent.fromKey(key);
                    // Branch: checks a condition
                    if (component == null) throw new IllegalArgumentException("Unknown data component: " + key);
                    // Assigns a value
                    final Object value = component.decode(new RegistryTranscoder<>(Transcoder.NBT,
                            // Calls a method
                            MinecraftServer.process()), nbtValue.value()).orElseThrow("failed to decode " + key);
                    // Returns a value to the caller
                    return MinestomDataComponentValue.dataComponentValue(value);
                // Code statement
                }),
                // Start of a method/block
                convert(MinestomDataComponentValue.class, NbtDataComponentValue.class, (key, minestomValue) -> {
                    // Branch: checks a condition
                    if (minestomValue instanceof DataComponentValue.Removed)
                        // Returns a value to the caller
                        return NbtDataComponentValue.removed();
                    // Calls a method
                    final DataComponent<Object> component = (DataComponent<Object>) DataComponent.fromKey(key);
                    // Branch: checks a condition
                    if (component == null) throw new IllegalArgumentException("Unknown data component: " + key);
                    // Assigns a value
                    final BinaryTag value = component.encode(new RegistryTranscoder<>(Transcoder.NBT,
                            // Calls a method
                            MinecraftServer.process()), minestomValue.value()).orElseThrow("failed to encode " + key);
                    // Returns a value to the caller
                    return NbtDataComponentValue.nbtDataComponentValue(value);
                // Code statement
                })
        // End of a block/expression
        );
    // End of a block/expression
    }

// End of a block/expression
}
