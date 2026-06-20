// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Déclaration de type (classe/interface/enum/record)
public interface Taggable extends TagReadable, TagWritable {

    // Appelle une méthode
    TagHandler tagHandler();

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default <T> @UnknownNullability T getTag(Tag<T> tag) {
        // Renvoie une valeur à l'appelant
        return tagHandler().getTag(tag);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default boolean hasTag(Tag<?> tag) {
        // Renvoie une valeur à l'appelant
        return tagHandler().hasTag(tag);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default <T> void setTag(Tag<T> tag, @Nullable T value) {
        // Appelle une méthode
        tagHandler().setTag(tag, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default void removeTag(Tag<?> tag) {
        // Appelle une méthode
        tagHandler().removeTag(tag);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default <T> @Nullable T getAndSetTag(Tag<T> tag, @Nullable T value) {
        // Renvoie une valeur à l'appelant
        return tagHandler().getAndSetTag(tag, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default <T> void updateTag(Tag<T> tag, UnaryOperator<@UnknownNullability T> value) {
        // Appelle une méthode
        tagHandler().updateTag(tag, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default <T> @UnknownNullability T updateAndGetTag(Tag<T> tag, UnaryOperator<@UnknownNullability T> value) {
        // Renvoie une valeur à l'appelant
        return tagHandler().updateAndGetTag(tag, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default <T> @UnknownNullability T getAndUpdateTag(Tag<T> tag, UnaryOperator<@UnknownNullability T> value) {
        // Renvoie une valeur à l'appelant
        return tagHandler().getAndUpdateTag(tag, value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
