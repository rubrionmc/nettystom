// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.function.Function;

// Déclaration de type (classe/interface/enum/record)
public sealed interface Either<L, R> {

    // Début d'une méthode/d'un bloc
    static <L, R> Either<L, R> left(L value) {
        // Renvoie une valeur à l'appelant
        return new Left<>(value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <L, R> Either<L, R> right(R value) {
        // Renvoie une valeur à l'appelant
        return new Right<>(value);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Left<L, R>(L value) implements Either<L, R> {
        // Début d'une méthode/d'un bloc
        public Left {
            // Appelle une méthode
            Objects.requireNonNull(value, "Left value must not be null");
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Right<L, R>(R value) implements Either<L, R> {
        // Début d'une méthode/d'un bloc
        public Right {
            // Appelle une méthode
            Objects.requireNonNull(value, "Right value must not be null");
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default <T extends @UnknownNullability Object> T unify(Function<L, T> leftMapper, Function<R, T> rightMapper) {
        // Renvoie une valeur à l'appelant
        return switch (this) {
            // Embranchement multiple (switch/case)
            case Left(L value) -> leftMapper.apply(value);
            // Embranchement multiple (switch/case)
            case Right(R value) -> rightMapper.apply(value);
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
