// Déclaration du paquet de ce fichier
package net.minestom.server.adventure.provider;

// Import d'une classe nécessaire
import java.util.function.Consumer;

// Import d'une classe nécessaire
import net.kyori.adventure.text.serializer.ansi.ANSIComponentSerializer;

// Annotation pour l'élément suivant
@SuppressWarnings("UnstableApiUsage") // we are permitted to provide this
// Déclaration de type (classe/interface/enum/record)
public final class MinestomAnsiComponentSerializerProvider implements ANSIComponentSerializer.Provider {
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ANSIComponentSerializer ansi() {
        // Appelle une méthode
        final ANSIComponentSerializer.Builder builder = ANSIComponentSerializer.builder();
        // Accès à l'objet courant/parent
        this.builder().accept(builder);
        // Renvoie une valeur à l'appelant
        return builder.build();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Consumer<ANSIComponentSerializer.Builder> builder() {
        // Renvoie une valeur à l'appelant
        return builder -> builder.flattener(MinestomFlattenerProvider.INSTANCE);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
