// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.Result;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registry;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.Random;

// Déclaration de type (classe/interface/enum/record)
public sealed interface IntProvider {
    // Affecte une valeur
    Codec<IntProvider> CODEC = new Codec<>() {
        // Affecte une valeur
        public static final Registry<StructCodec<? extends IntProvider>> REGISTRY = DynamicRegistry.fromMap(Key.key("int_provider"),
                // Appelle une méthode
                Map.entry(Key.key("uniform"), Uniform.CODEC));
        // Appelle une méthode
        private static final StructCodec<IntProvider> TAGGED_CODEC = Codec.RegistryTaggedUnion(REGISTRY, IntProvider::codec);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable IntProvider value) {
            // Embranchement : vérifie une condition
            if (value instanceof IntProvider.Constant(int number))
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(coder.createInt(number));
            // Renvoie une valeur à l'appelant
            return TAGGED_CODEC.encode(coder, value);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<IntProvider> decode(Transcoder<D> coder, D value) {
            // Appelle une méthode
            final Result<Integer> numberResult = coder.getInt(value);
            // Embranchement : vérifie une condition
            if (numberResult instanceof Result.Ok(Integer number))
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(new IntProvider.Constant(number));
            // Renvoie une valeur à l'appelant
            return TAGGED_CODEC.decode(coder, value);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Déclaration de type (classe/interface/enum/record)
    record Constant(int value) implements IntProvider {
        // Appelle une méthode
        public static final Codec<Constant> CODEC = Codec.INT.transform(Constant::new, Constant::value);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int sample(Random random) {
            // Renvoie une valeur à l'appelant
            return value;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends IntProvider> codec() {
            // Lève une exception
            throw new UnsupportedOperationException("Constant values are serialized as a special case, use IntProvider#CODEC");
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
        public StructCodec<? extends IntProvider> codec() {
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
        public StructCodec<? extends IntProvider> codec() {
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
                "source", IntProvider.CODEC, Clamped::source,
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
        public StructCodec<? extends IntProvider> codec() {
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
                "distribution", WeightedList.codec(IntProvider.CODEC), Weighted::distribution,
                // Instruction de code
                Weighted::new);

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
        public StructCodec<? extends IntProvider> codec() {
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
        public StructCodec<? extends IntProvider> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    int sample(Random random);

    // Appelle une méthode
    StructCodec<? extends IntProvider> codec();
// Fin d'un bloc/d'une expression
}
