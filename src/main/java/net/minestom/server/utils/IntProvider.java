// Package declaration for this file
package net.minestom.server.utils;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;
// Import of a required class
import net.minestom.server.registry.Registry;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Random;
// Import of a required class
import java.util.function.Function;

// Type declaration (class/interface/enum/record)
public sealed interface IntProvider {
    // Assigns a value
    Registry<StructCodec<? extends IntProvider>> REGISTRY = DynamicRegistry.fromMap(Key.key("int_provider"),
            // Code statement
            Map.entry(Key.key("constant"), Constant.CODEC),
            // Code statement
            Map.entry(Key.key("uniform"), Uniform.CODEC),
            // Code statement
            Map.entry(Key.key("biased_to_bottom"), BiasedToBottom.CODEC),
            // Code statement
            Map.entry(Key.key("clamped"), Clamped.CODEC),
            // Code statement
            Map.entry(Key.key("weighted_list"), Weighted.CODEC),
            // Code statement
            Map.entry(Key.key("clamped_normal"), ClampedNormal.CODEC),
            // Code statement
            Map.entry(Key.key("trapezoid"), Trapezoid.CODEC)
    // End of a block/expression
    );
    // Calls a method
    StructCodec<IntProvider> REGISTRY_CODEC = Codec.RegistryTaggedUnion(REGISTRY, IntProvider::codec);

    // Assigns a value
    Codec<IntProvider> CODEC = Codec.Either(Codec.INT, REGISTRY_CODEC).transform(
            // Code statement
            it -> it.unify(Constant::new, Function.identity()),
            // Code statement
            it -> it instanceof Constant(int value) ? Either.left(value) : Either.right(it)
    // End of a block/expression
    );

    // Type declaration (class/interface/enum/record)
    record Constant(int value) implements IntProvider {
        // Assigns a value
        public static final StructCodec<Constant> CODEC = StructCodec.struct(
                // Code statement
                "value", Codec.INT, Constant::value,
                // Code statement
                Constant::new
        // End of a block/expression
        );

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int minInclusive() {
            // Returns a value to the caller
            return value;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int maxInclusive() {
            // Returns a value to the caller
            return value;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int sample(Random ignored) {
            // Returns a value to the caller
            return value;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<Constant> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Uniform(int minInclusive, int maxInclusive) implements IntProvider {
        // Assigns a value
        public static final StructCodec<Uniform> CODEC = StructCodec.struct(
                // Code statement
                "min_inclusive", Codec.INT, Uniform::minInclusive,
                // Code statement
                "max_inclusive", Codec.INT, Uniform::maxInclusive,
                // Code statement
                Uniform::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int sample(Random random) {
            // Returns a value to the caller
            return random.nextInt(minInclusive, maxInclusive + 1);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<Uniform> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record BiasedToBottom(int minInclusive, int maxInclusive) implements IntProvider {
        // Assigns a value
        public static final StructCodec<BiasedToBottom> CODEC = StructCodec.struct(
                // Code statement
                "min_inclusive", Codec.INT, BiasedToBottom::minInclusive,
                // Code statement
                "max_inclusive", Codec.INT, BiasedToBottom::maxInclusive,
                // Code statement
                BiasedToBottom::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int sample(Random random) {
            // Returns a value to the caller
            return minInclusive + random.nextInt(random.nextInt(maxInclusive - minInclusive + 1) + 1);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<BiasedToBottom> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Clamped(IntProvider source, int minInclusive, int maxInclusive) implements IntProvider {
        // Assigns a value
        public static final StructCodec<Clamped> CODEC = StructCodec.struct(
                // Code statement
                "source", Codec.ForwardRef(() -> IntProvider.CODEC), Clamped::source,
                // Code statement
                "min_inclusive", Codec.INT, Clamped::minInclusive,
                // Code statement
                "max_inclusive", Codec.INT, Clamped::maxInclusive,
                // Code statement
                Clamped::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int sample(Random random) {
            // Returns a value to the caller
            return Math.clamp(source.sample(random), minInclusive, maxInclusive);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<Clamped> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Weighted(WeightedList<IntProvider> distribution) implements IntProvider {
        // Assigns a value
        public static final StructCodec<Weighted> CODEC = StructCodec.struct(
                // Code statement
                "distribution", WeightedList.codec(Codec.ForwardRef(() -> IntProvider.CODEC)), Weighted::distribution,
                // Code statement
                Weighted::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int minInclusive() {
            // Returns a value to the caller
            return 0;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int maxInclusive() {
            // Returns a value to the caller
            return 0;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int sample(Random random) {
            // Returns a value to the caller
            return distribution.pickOrThrow(random).sample(random);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<Weighted> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record ClampedNormal(double mean, double deviation, int minInclusive, int maxInclusive) implements IntProvider {
        // Assigns a value
        public static final StructCodec<ClampedNormal> CODEC = StructCodec.struct(
                // Code statement
                "mean", Codec.DOUBLE, ClampedNormal::mean,
                // Code statement
                "deviation", Codec.DOUBLE, ClampedNormal::deviation,
                // Code statement
                "min_inclusive", Codec.INT, ClampedNormal::minInclusive,
                // Code statement
                "max_inclusive", Codec.INT, ClampedNormal::maxInclusive,
                // Code statement
                ClampedNormal::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int sample(Random random) {
            // Returns a value to the caller
            return Math.clamp((int) (mean + random.nextGaussian() * deviation), minInclusive, maxInclusive);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<ClampedNormal> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Trapezoid(int minInclusive, int maxInclusive, int plateau) implements IntProvider {
        // Assigns a value
        public static final StructCodec<Trapezoid> CODEC = StructCodec.struct(
                // Code statement
                "min", Codec.INT, Trapezoid::minInclusive,
                // Code statement
                "max", Codec.INT, Trapezoid::maxInclusive,
                // Code statement
                "plateau", Codec.INT, Trapezoid::plateau,
                // Code statement
                Trapezoid::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int sample(Random random) {
            // Branch: checks a condition
            if (plateau == 0 && maxInclusive == -minInclusive) {
                // Returns a value to the caller
                return random.nextInt(maxInclusive + 1) - random.nextInt(maxInclusive + 1);
            // End of a block/expression
            }
            // Assigns a value
            int range = maxInclusive - minInclusive;
            // Branch: checks a condition
            if (plateau == range) {
                // Returns a value to the caller
                return random.nextInt(minInclusive, maxInclusive + 1);
            // Alternative branch of the condition
            } else {
                // Calls a method
                int plateauStart = (range - plateau) / 2;
                // Assigns a value
                int plateauEnd = range - plateauStart;
                // Returns a value to the caller
                return minInclusive + random.nextInt(plateauEnd + 1) + random.nextInt(plateauStart + 1);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<Trapezoid> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Calls a method
    int minInclusive();

    // Calls a method
    int maxInclusive();

    // Calls a method
    int sample(Random random);

    // Calls a method
    StructCodec<? extends IntProvider> codec();
// End of a block/expression
}
