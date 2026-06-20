// Package declaration for this file
package net.minestom.server.item.enchant;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.Result;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.gamedata.DataPack;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.utils.MathUtils;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public interface LevelBasedValue {

    // Assigns a value
    StructCodec<LevelBasedValue> TAGGED_CODEC = Codec.RegistryTaggedUnion(
            // Code statement
            Registries::enchantmentLevelBasedValues, LevelBasedValue::codec);
    // Assigns a value
    Codec<LevelBasedValue> CODEC = new Codec<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable LevelBasedValue value) {
            // Branch: checks a condition
            if (value instanceof Constant(float constantValue))
                // Returns a value to the caller
                return new Result.Ok<>(coder.createFloat(constantValue));
            // Returns a value to the caller
            return TAGGED_CODEC.encode(coder, value);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<LevelBasedValue> decode(Transcoder<D> coder, D value) {
            // Calls a method
            final Result<Float> numberResult = coder.getFloat(value);
            // Branch: checks a condition
            if (numberResult instanceof Result.Ok(Float number))
                // Returns a value to the caller
                return new Result.Ok<>(new Constant(number));
            // Returns a value to the caller
            return TAGGED_CODEC.decode(coder, value);
        // End of a block/expression
        }
    // End of a block/expression
    };

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<StructCodec<? extends LevelBasedValue>> createDefaultRegistry() {
        // Calls a method
        final DynamicRegistry<StructCodec<? extends LevelBasedValue>> registry = DynamicRegistry.create(Key.key("minestom:enchantment_value_effect"));
        // Note that constant is omitted from the registry, it has serialization handled out of band above.
        // Calls a method
        registry.register("linear", Linear.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("clamped", Clamped.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("fraction", Fraction.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("levels_squared", LevelsSquared.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("lookup", Lookup.CODEC, DataPack.MINECRAFT_CORE);
        // Returns a value to the caller
        return registry;
    // End of a block/expression
    }

    // Calls a method
    float calc(int level);

    // Calls a method
    StructCodec<? extends LevelBasedValue> codec();

    // Type declaration (class/interface/enum/record)
    record Constant(float value) implements LevelBasedValue {

        // Annotation for the following element
        @Override
        // Start of a method/block
        public float calc(int level) {
            // Returns a value to the caller
            return value;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<Constant> codec() {
            // Throws an exception
            throw new UnsupportedOperationException("Constant values are serialized as a special case, see LevelBasedValue.CODEC");
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Linear(float base, float perLevelAboveFirst) implements LevelBasedValue {
        // Assigns a value
        public static final StructCodec<Linear> CODEC = StructCodec.struct(
                // Code statement
                "base", Codec.FLOAT, Linear::base,
                // Code statement
                "per_level_above_first", Codec.FLOAT, Linear::perLevelAboveFirst,
                // Code statement
                Linear::new
        // End of a block/expression
        );

        // Annotation for the following element
        @Override
        // Start of a method/block
        public float calc(int level) {
            // Returns a value to the caller
            return base + (perLevelAboveFirst * (level - 1));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<Linear> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Clamped(LevelBasedValue value, float min, float max) implements LevelBasedValue {
        // Assigns a value
        public static final StructCodec<Clamped> CODEC = StructCodec.struct(
                // Code statement
                "value", LevelBasedValue.CODEC, Clamped::value,
                // Code statement
                "min", Codec.FLOAT, Clamped::min,
                // Code statement
                "max", Codec.FLOAT, Clamped::max,
                // Code statement
                Clamped::new
        // End of a block/expression
        );

        // Annotation for the following element
        @Override
        // Start of a method/block
        public float calc(int level) {
            // Returns a value to the caller
            return MathUtils.clamp(value.calc(level), min, max);
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
    record Fraction(LevelBasedValue numerator,
                    // Start of a method/block
                    LevelBasedValue denominator) implements LevelBasedValue {
        // Assigns a value
        public static final StructCodec<Fraction> CODEC = StructCodec.struct(
                // Code statement
                "numerator", LevelBasedValue.CODEC, Fraction::numerator,
                // Code statement
                "denominator", LevelBasedValue.CODEC, Fraction::denominator,
                // Code statement
                Fraction::new
        // End of a block/expression
        );

        // Annotation for the following element
        @Override
        // Start of a method/block
        public float calc(int level) {
            // Calls a method
            float denominator = this.denominator.calc(level);
            // Returns a value to the caller
            return denominator == 0f ? 0f : numerator.calc(level) / denominator;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<Fraction> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record LevelsSquared(float added) implements LevelBasedValue {
        // Assigns a value
        public static final StructCodec<LevelsSquared> CODEC = StructCodec.struct(
                // Code statement
                "added", Codec.FLOAT, LevelsSquared::added,
                // Code statement
                LevelsSquared::new
        // End of a block/expression
        );

        // Annotation for the following element
        @Override
        // Start of a method/block
        public float calc(int level) {
            // Returns a value to the caller
            return (level * level) + added;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<LevelsSquared> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Lookup(List<Float> values, LevelBasedValue fallback) implements LevelBasedValue {
        // Assigns a value
        public static final StructCodec<Lookup> CODEC = StructCodec.struct(
                // Code statement
                "values", Codec.FLOAT.list(), Lookup::values,
                // Code statement
                "fallback", LevelBasedValue.CODEC, Lookup::fallback,
                // Code statement
                Lookup::new
        // End of a block/expression
        );

        // Annotation for the following element
        @Override
        // Start of a method/block
        public float calc(int level) {
            // Branch: checks a condition
            if (level < 0 || level > values.size()) return fallback.calc(level);
            // Returns a value to the caller
            return values.get(level - 1);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<Lookup> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
