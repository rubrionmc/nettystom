// Déclaration du paquet de ce fichier
package net.minestom.server.adventure.provider;

// Import d'une classe nécessaire
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

// Import d'une classe nécessaire
import java.util.function.Consumer;

// Annotation pour l'élément suivant
@SuppressWarnings("UnstableApiUsage") // we are permitted to provide this
// Déclaration de type (classe/interface/enum/record)
public final class MinestomPlainTextComponentSerializerProvider implements PlainTextComponentSerializer.Provider {
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public PlainTextComponentSerializer plainTextSimple() {
        // Renvoie une valeur à l'appelant
        return PlainTextComponentSerializer.builder()
                // Instruction de code
                .flattener(MinestomFlattenerProvider.INSTANCE)
                // Appelle une méthode
                .build();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Consumer<PlainTextComponentSerializer.Builder> plainText() {
        // we will provide our flattener to allow for custom translations/etc
        // Renvoie une valeur à l'appelant
        return builder -> builder.flattener(MinestomFlattenerProvider.INSTANCE);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
