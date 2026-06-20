// Déclaration du paquet de ce fichier
package net.minestom.server.adventure.provider;

// Import d'une classe nécessaire
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
// Import d'une classe nécessaire
import net.kyori.adventure.text.serializer.json.JSONOptions;

// Import d'une classe nécessaire
import java.util.function.Consumer;

// Annotation pour l'élément suivant
@SuppressWarnings("UnstableApiUsage") // we are permitted to provide this
// Déclaration de type (classe/interface/enum/record)
public final class MinestomGsonComponentSerializerProvider implements GsonComponentSerializer.Provider {
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public GsonComponentSerializer gson() {
        // Renvoie une valeur à l'appelant
        return GsonComponentSerializer.builder()
                // Instruction de code
                .legacyHoverEventSerializer(NBTLegacyHoverEventSerializer.INSTANCE)
                // Appelle une méthode
                .build();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public GsonComponentSerializer gsonLegacy() {
        // Renvoie une valeur à l'appelant
        return GsonComponentSerializer.builder()
                // Instruction de code
                .legacyHoverEventSerializer(NBTLegacyHoverEventSerializer.INSTANCE)
                // Instruction de code
                .editOptions(features -> features.value(JSONOptions.EMIT_RGB, false))
                // Appelle une méthode
                .build();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Consumer<GsonComponentSerializer.Builder> builder() {
        // Renvoie une valeur à l'appelant
        return _ -> {}; // we don't need to touch the builder here
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
