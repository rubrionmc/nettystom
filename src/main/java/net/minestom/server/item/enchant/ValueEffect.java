// Package declaration for this file
package net.minestom.server.item.enchant;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.gamedata.DataPack;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public non-sealed interface ValueEffect extends Enchantment.Effect {

    // Assigns a value
    StructCodec<ValueEffect> CODEC = Codec.RegistryTaggedUnion(
            // Code statement
            Registries::enchantmentValueEffects, ValueEffect::codec);

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<StructCodec<? extends ValueEffect>> createDefaultRegistry() {
        // Calls a method
        final DynamicRegistry<StructCodec<? extends ValueEffect>> registry = DynamicRegistry.create(Key.key("minestom:enchantment_value_effect"));
        // Calls a method
        registry.register("add", Add.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("all_of", AllOf.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("multiply", Multiply.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("remove_binomial", RemoveBinomial.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("exponential", Exponential.CODEC, DataPack.MINECRAFT_CORE);
        // Calls a method
        registry.register("set", Set.CODEC, DataPack.MINECRAFT_CORE);
        // Returns a value to the caller
        return registry;
    // End of a block/expression
    }

    // Calls a method
    float apply(float base, int level);

    // Calls a method
    StructCodec<? extends ValueEffect> codec();

    // Type declaration (class/interface/enum/record)
    record Add(LevelBasedValue value) implements ValueEffect {
        // Assigns a value
        public static final StructCodec<Add> CODEC = StructCodec.struct(
                // Code statement
                "value", LevelBasedValue.CODEC, Add::value,
                // Code statement
                Add::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public float apply(float base, int level) {
            // Returns a value to the caller
            return base + value.calc(level);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<Add> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record AllOf(List<ValueEffect> effects) implements ValueEffect {
        // Assigns a value
        public static final StructCodec<AllOf> CODEC = StructCodec.struct(
                // Code statement
                "effects", ValueEffect.CODEC.list(), AllOf::effects,
                // Code statement
                AllOf::new);

        // Start of a method/block
        public AllOf {
            // Calls a method
            effects = List.copyOf(effects);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public float apply(float base, int level) {
            // Loop: repeats a block
            for (ValueEffect effect : effects)
                // Calls a method
                base = effect.apply(base, level);
            // Returns a value to the caller
            return base;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<AllOf> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Multiply(LevelBasedValue factor) implements ValueEffect {
        // Assigns a value
        public static final StructCodec<Multiply> CODEC = StructCodec.struct(
                // Code statement
                "factor", LevelBasedValue.CODEC, Multiply::factor,
                // Code statement
                Multiply::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public float apply(float base, int level) {
            // Returns a value to the caller
            return base * factor.calc(level);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<Multiply> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record RemoveBinomial(LevelBasedValue chance) implements ValueEffect {
        // Assigns a value
        public static final StructCodec<RemoveBinomial> CODEC = StructCodec.struct(
                // Code statement
                "chance", LevelBasedValue.CODEC, RemoveBinomial::chance,
                // Code statement
                RemoveBinomial::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public float apply(float base, int level) {
            // Throws an exception
            throw new UnsupportedOperationException("todo");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<RemoveBinomial> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Exponential(LevelBasedValue base, LevelBasedValue exponent) implements ValueEffect {
        // Assigns a value
        public static final StructCodec<Exponential> CODEC = StructCodec.struct(
                // Code statement
                "base", LevelBasedValue.CODEC, Exponential::base,
                // Code statement
                "exponent", LevelBasedValue.CODEC, Exponential::exponent,
                // Code statement
                Exponential::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public float apply(float base, int level) {
            // Returns a value to the caller
            return base * (float) Math.pow(this.base.calc(level), this.exponent.calc(level));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<? extends ValueEffect> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Set(LevelBasedValue value) implements ValueEffect {
        // Assigns a value
        public static final StructCodec<Set> CODEC = StructCodec.struct(
                // Code statement
                "value", LevelBasedValue.CODEC, Set::value,
                // Code statement
                Set::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public float apply(float base, int level) {
            // Returns a value to the caller
            return value.calc(level);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<Set> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
