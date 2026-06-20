// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registry;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.Random;
// Import d'une classe nécessaire
import java.util.function.Function;

// Déclaration de type (classe/interface/enum/record)
public sealed interface IntProvider {
    // Affecte une valeur
    Registry<StructCodec<? extends IntProvider>> REGISTRY = DynamicRegistry.fromMap(Key.key("int_provider"),
            // Instruction de code
            Map.entry(Key.key("constant"), Constant.CODEC),
            // Instruction de code
            Map.entry(Key.key("uniform"), Uniform.CODEC),
            // Instruction de code
            Map.entry(Key.key("biased_to_bottom"), BiasedToBottom.CODEC),
            // Instruction de code
            Map.entry(Key.key("clamped"), Clamped.CODEC),
            // Instruction de code
            Map.entry(Key.key("weighted_list"), Weighted.CODEC),
            // Instruction de code
            Map.entry(Key.key("clamped_normal"), ClampedNormal.CODEC),
            // Instruction de code
            Map.entry(Key.key("trapezoid"), Trapezoid.CODEC)
    // Fin d'un bloc/d'une expression
    );
    // Appelle une méthode
    StructCodec<IntProvider> REGISTRY_CODEC = Codec.RegistryTaggedUnion(REGISTRY, IntProvider::codec);

    // Affecte une valeur
    Codec<IntProvider> CODEC = Codec.Either(Codec.INT, REGISTRY_CODEC).transform(
            // Instruction de code
            it -> it.unify(Constant::new, Function.identity()),
            // Instruction de code
            it -> it instanceof Constant(int value) ? Either.left(value) : Either.right(it)
    // Fin d'un bloc/d'une expression
    );

    // Déclaration de type (classe/interface/enum/record)
    record Constant(int value) implements IntProvider {
        // Affecte une valeur
        public static final StructCodec<Constant> CODEC = StructCodec.struct(
                // Instruction de code
                "value", Codec.INT, Constant::value,
                // Instruction de code
                Constant::new
        // Fin d'un bloc/d'une expression
        );

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int minInclusive() {
            // Renvoie une valeur à l'appelant
            return value;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int maxInclusive() {
            // Renvoie une valeur à l'appelant
            return value;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int sample(Random ignored) {
            // Renvoie une valeur à l'appelant
            return value;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<Constant> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Uniform(int minInclusive, int maxInclusive) implements IntProvider {
        // Affecte une valeur
        public static final StructCodec<Uniform> CODEC = StructCodec.struct(
                // Instruction de code
                "min_inclusive", Codec.INT, Uniform::minInclusive,
                // Instruction de code
                "max_inclusive", Codec.INT, Uniform::maxInclusive,
                // Instruction de code
                Uniform::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int sample(Random random) {
            // Renvoie une valeur à l'appelant
            return random.nextInt(minInclusive, maxInclusive + 1);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<Uniform> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record BiasedToBottom(int minInclusive, int maxInclusive) implements IntProvider {
        // Affecte une valeur
        public static final StructCodec<BiasedToBottom> CODEC = StructCodec.struct(
                // Instruction de code
                "min_inclusive", Codec.INT, BiasedToBottom::minInclusive,
                // Instruction de code
                "max_inclusive", Codec.INT, BiasedToBottom::maxInclusive,
                // Instruction de code
                BiasedToBottom::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int sample(Random random) {
            // Renvoie une valeur à l'appelant
            return minInclusive + random.nextInt(random.nextInt(maxInclusive - minInclusive + 1) + 1);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<BiasedToBottom> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Clamped(IntProvider source, int minInclusive, int maxInclusive) implements IntProvider {
        // Affecte une valeur
        public static final StructCodec<Clamped> CODEC = StructCodec.struct(
                // Instruction de code
                "source", Codec.ForwardRef(() -> IntProvider.CODEC), Clamped::source,
                // Instruction de code
                "min_inclusive", Codec.INT, Clamped::minInclusive,
                // Instruction de code
                "max_inclusive", Codec.INT, Clamped::maxInclusive,
                // Instruction de code
                Clamped::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int sample(Random random) {
            // Renvoie une valeur à l'appelant
            return Math.clamp(source.sample(random), minInclusive, maxInclusive);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<Clamped> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Weighted(WeightedList<IntProvider> distribution) implements IntProvider {
        // Affecte une valeur
        public static final StructCodec<Weighted> CODEC = StructCodec.struct(
                // Instruction de code
                "distribution", WeightedList.codec(Codec.ForwardRef(() -> IntProvider.CODEC)), Weighted::distribution,
                // Instruction de code
                Weighted::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int minInclusive() {
            // Renvoie une valeur à l'appelant
            return 0;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int maxInclusive() {
            // Renvoie une valeur à l'appelant
            return 0;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int sample(Random random) {
            // Renvoie une valeur à l'appelant
            return distribution.pickOrThrow(random).sample(random);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<Weighted> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record ClampedNormal(double mean, double deviation, int minInclusive, int maxInclusive) implements IntProvider {
        // Affecte une valeur
        public static final StructCodec<ClampedNormal> CODEC = StructCodec.struct(
                // Instruction de code
                "mean", Codec.DOUBLE, ClampedNormal::mean,
                // Instruction de code
                "deviation", Codec.DOUBLE, ClampedNormal::deviation,
                // Instruction de code
                "min_inclusive", Codec.INT, ClampedNormal::minInclusive,
                // Instruction de code
                "max_inclusive", Codec.INT, ClampedNormal::maxInclusive,
                // Instruction de code
                ClampedNormal::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int sample(Random random) {
            // Renvoie une valeur à l'appelant
            return Math.clamp((int) (mean + random.nextGaussian() * deviation), minInclusive, maxInclusive);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<ClampedNormal> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Trapezoid(int minInclusive, int maxInclusive, int plateau) implements IntProvider {
        // Affecte une valeur
        public static final StructCodec<Trapezoid> CODEC = StructCodec.struct(
                // Instruction de code
                "min", Codec.INT, Trapezoid::minInclusive,
                // Instruction de code
                "max", Codec.INT, Trapezoid::maxInclusive,
                // Instruction de code
                "plateau", Codec.INT, Trapezoid::plateau,
                // Instruction de code
                Trapezoid::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int sample(Random random) {
            // Embranchement : vérifie une condition
            if (plateau == 0 && maxInclusive == -minInclusive) {
                // Renvoie une valeur à l'appelant
                return random.nextInt(maxInclusive + 1) - random.nextInt(maxInclusive + 1);
            // Fin d'un bloc/d'une expression
            }
            // Affecte une valeur
            int range = maxInclusive - minInclusive;
            // Embranchement : vérifie une condition
            if (plateau == range) {
                // Renvoie une valeur à l'appelant
                return random.nextInt(minInclusive, maxInclusive + 1);
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                int plateauStart = (range - plateau) / 2;
                // Affecte une valeur
                int plateauEnd = range - plateauStart;
                // Renvoie une valeur à l'appelant
                return minInclusive + random.nextInt(plateauEnd + 1) + random.nextInt(plateauStart + 1);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<Trapezoid> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    int minInclusive();

    // Appelle une méthode
    int maxInclusive();

    // Appelle une méthode
    int sample(Random random);

    // Appelle une méthode
    StructCodec<? extends IntProvider> codec();
// Fin d'un bloc/d'une expression
}
