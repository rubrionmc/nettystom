// Déclaration du paquet de ce fichier
package net.minestom.server.adventure.provider;

// Import d'une classe nécessaire
import com.google.gson.JsonElement;
// Import d'une classe nécessaire
import com.google.gson.JsonNull;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.DataComponentValue;
// Import d'une classe nécessaire
import net.kyori.adventure.text.event.DataComponentValueConverterRegistry;
// Import d'une classe nécessaire
import net.kyori.adventure.text.serializer.gson.GsonDataComponentValue;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.adventure.MinestomDataComponentValue;
// Import d'une classe nécessaire
import net.minestom.server.adventure.serializer.nbt.NbtDataComponentValue;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTranscoder;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.kyori.adventure.text.event.DataComponentValueConverterRegistry.Conversion.convert;

// Annotation pour l'élément suivant
@SuppressWarnings("UnstableApiUsage") // we are permitted to provide this
// Déclaration de type (classe/interface/enum/record)
public final class MinestomDataComponentValueConverterProvider implements DataComponentValueConverterRegistry.Provider {

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Key id() {
        // Renvoie une valeur à l'appelant
        return Key.key("minestom", "data_component_value_converter");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    public Iterable<DataComponentValueConverterRegistry.Conversion<?, ?>> conversions() {
        // Renvoie une valeur à l'appelant
        return List.of(
                // GSON
                // Début d'une méthode/d'un bloc
                convert(GsonDataComponentValue.class, MinestomDataComponentValue.class, (key, gsonValue) -> {
                    // Embranchement : vérifie une condition
                    if (gsonValue instanceof DataComponentValue.Removed)
                        // Renvoie une valeur à l'appelant
                        return MinestomDataComponentValue.removed();
                    // Appelle une méthode
                    final DataComponent<Object> component = (DataComponent<Object>) DataComponent.fromKey(key);
                    // Embranchement : vérifie une condition
                    if (component == null) throw new IllegalArgumentException("Unknown data component: " + key);
                    // Affecte une valeur
                    final Object value = component.decode(new RegistryTranscoder<>(Transcoder.JSON,
                            // Appelle une méthode
                            MinecraftServer.process()), gsonValue.element()).orElseThrow("failed to decode " + key);
                    // Renvoie une valeur à l'appelant
                    return MinestomDataComponentValue.dataComponentValue(value);
                // Instruction de code
                }),
                // Début d'une méthode/d'un bloc
                convert(MinestomDataComponentValue.class, GsonDataComponentValue.class, (key, minestomValue) -> {
                    // Embranchement : vérifie une condition
                    if (minestomValue instanceof DataComponentValue.Removed)
                        // Renvoie une valeur à l'appelant
                        return GsonDataComponentValue.gsonDataComponentValue(JsonNull.INSTANCE);
                    // Appelle une méthode
                    final DataComponent<Object> component = (DataComponent<Object>) DataComponent.fromKey(key);
                    // Embranchement : vérifie une condition
                    if (component == null) throw new IllegalArgumentException("Unknown data component: " + key);
                    // Affecte une valeur
                    final JsonElement value = component.encode(new RegistryTranscoder<>(Transcoder.JSON,
                            // Appelle une méthode
                            MinecraftServer.process()), minestomValue.value()).orElseThrow("failed to encode " + key);
                    // Renvoie une valeur à l'appelant
                    return GsonDataComponentValue.gsonDataComponentValue(value);
                // Instruction de code
                }),

                // NBT
                // Début d'une méthode/d'un bloc
                convert(NbtDataComponentValue.class, MinestomDataComponentValue.class, (key, nbtValue) -> {
                    // Embranchement : vérifie une condition
                    if (nbtValue instanceof DataComponentValue.Removed)
                        // Renvoie une valeur à l'appelant
                        return MinestomDataComponentValue.removed();
                    // Appelle une méthode
                    final DataComponent<Object> component = (DataComponent<Object>) DataComponent.fromKey(key);
                    // Embranchement : vérifie une condition
                    if (component == null) throw new IllegalArgumentException("Unknown data component: " + key);
                    // Affecte une valeur
                    final Object value = component.decode(new RegistryTranscoder<>(Transcoder.NBT,
                            // Appelle une méthode
                            MinecraftServer.process()), nbtValue.value()).orElseThrow("failed to decode " + key);
                    // Renvoie une valeur à l'appelant
                    return MinestomDataComponentValue.dataComponentValue(value);
                // Instruction de code
                }),
                // Début d'une méthode/d'un bloc
                convert(MinestomDataComponentValue.class, NbtDataComponentValue.class, (key, minestomValue) -> {
                    // Embranchement : vérifie une condition
                    if (minestomValue instanceof DataComponentValue.Removed)
                        // Renvoie une valeur à l'appelant
                        return NbtDataComponentValue.removed();
                    // Appelle une méthode
                    final DataComponent<Object> component = (DataComponent<Object>) DataComponent.fromKey(key);
                    // Embranchement : vérifie une condition
                    if (component == null) throw new IllegalArgumentException("Unknown data component: " + key);
                    // Affecte une valeur
                    final BinaryTag value = component.encode(new RegistryTranscoder<>(Transcoder.NBT,
                            // Appelle une méthode
                            MinecraftServer.process()), minestomValue.value()).orElseThrow("failed to encode " + key);
                    // Renvoie une valeur à l'appelant
                    return NbtDataComponentValue.nbtDataComponentValue(value);
                // Instruction de code
                })
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
