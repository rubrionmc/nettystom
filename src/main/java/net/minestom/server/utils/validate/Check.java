// Déclaration du paquet de ce fichier
package net.minestom.server.utils.validate;

// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.text.MessageFormat;
// Import d'une classe nécessaire
import java.util.Objects;

/**
 * Convenient class to check for common exceptions.
 */
// Déclaration de type (classe/interface/enum/record)
public final class Check {

    // Début d'une méthode/d'un bloc
    private Check() {

    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract("null, _ -> fail")
    // Début d'une méthode/d'un bloc
    public static void notNull(@Nullable Object object, String reason) {
        // Embranchement : vérifie une condition
        if (Objects.isNull(object)) {
            // Lève une exception
            throw new NullPointerException(reason);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract("null, _, _ -> fail")
    // Début d'une méthode/d'un bloc
    public static void notNull(@Nullable Object object, String reason, Object... arguments) {
        // Embranchement : vérifie une condition
        if (Objects.isNull(object)) {
            // Lève une exception
            throw new NullPointerException(MessageFormat.format(reason, arguments));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract("true, _ -> fail")
    // Début d'une méthode/d'un bloc
    public static void argCondition(boolean condition, String reason) {
        // Embranchement : vérifie une condition
        if (condition) {
            // Lève une exception
            throw new IllegalArgumentException(reason);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract("true, _, _ -> fail")
    // Début d'une méthode/d'un bloc
    public static void argCondition(boolean condition, String reason, Object... arguments) {
        // Embranchement : vérifie une condition
        if (condition) {
            // Lève une exception
            throw new IllegalArgumentException(MessageFormat.format(reason, arguments));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract("_ -> fail")
    // Début d'une méthode/d'un bloc
    public static void fail(String reason) {
        // Lève une exception
        throw new IllegalArgumentException(reason);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract("_, _ -> fail")
    // Début d'une méthode/d'un bloc
    public static void fail(String reason, Object... arguments) {
        // Lève une exception
        throw new IllegalArgumentException(MessageFormat.format(reason, arguments));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract("true, _ -> fail")
    // Début d'une méthode/d'un bloc
    public static void stateCondition(boolean condition, String reason) {
        // Embranchement : vérifie une condition
        if (condition) {
            // Lève une exception
            throw new IllegalStateException(reason);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract("true, _, _ -> fail")
    // Début d'une méthode/d'un bloc
    public static void stateCondition(boolean condition, String reason, Object... arguments) {
        // Embranchement : vérifie une condition
        if (condition) {
            // Lève une exception
            throw new IllegalStateException(MessageFormat.format(reason, arguments));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract("false, _ -> fail")
    // Début d'une méthode/d'un bloc
    public static void isTrue(boolean condition, String reason) {
        // Embranchement : vérifie une condition
        if (!condition) {
            // Lève une exception
            throw new IllegalStateException(reason);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract("false, _, _ -> fail")
    // Début d'une méthode/d'un bloc
    public static void isTrue(boolean condition, String reason, Object... arguments) {
        // Embranchement : vérifie une condition
        if (!condition) {
            // Lève une exception
            throw new IllegalStateException(MessageFormat.format(reason, arguments));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
