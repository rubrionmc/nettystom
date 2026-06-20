// Package declaration for this file
package net.minestom.server.utils;

// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.function.Function;

// Type declaration (class/interface/enum/record)
public sealed interface Either<L, R> {

    // Start of a method/block
    static <L, R> Either<L, R> left(L value) {
        // Returns a value to the caller
        return new Left<>(value);
    // End of a block/expression
    }

    // Start of a method/block
    static <L, R> Either<L, R> right(R value) {
        // Returns a value to the caller
        return new Right<>(value);
    // End of a block/expression
    }

    // Start of a method/block
    static <V> V identity(Either<? extends V, ? extends V> either) {
        // Returns a value to the caller
        return either.unify(Function.identity(), Function.identity());
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Left<L, R>(L value) implements Either<L, R> {
        // Start of a method/block
        public Left {
            // Calls a method
            Objects.requireNonNull(value, "Left value must not be null");
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Right<L, R>(R value) implements Either<L, R> {
        // Start of a method/block
        public Right {
            // Calls a method
            Objects.requireNonNull(value, "Right value must not be null");
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    default <T extends @UnknownNullability Object> T unify(Function<? super L, ? extends T> leftMapper, Function<? super R, ? extends T> rightMapper) {
        // Returns a value to the caller
        return switch (this) {
            // Multiple branching (switch/case)
            case Left(L value) -> leftMapper.apply(value);
            // Multiple branching (switch/case)
            case Right(R value) -> rightMapper.apply(value);
        // End of a block/expression
        };
    // End of a block/expression
    }

// End of a block/expression
}
