// Déclaration du paquet de ce fichier
package net.minestom.server.item.enchant;

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
import net.minestom.server.gamedata.DataPack;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public interface LevelBasedValue {

    // Affecte une valeur
    StructCodec<LevelBasedValue> TAGGED_CODEC = Codec.RegistryTaggedUnion(
            // Instruction de code
            Registries::enchantmentLevelBasedValues, LevelBasedValue::codec);
    // Affecte une valeur
    Codec<LevelBasedValue> CODEC = new Codec<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable LevelBasedValue value) {
            // Embranchement : vérifie une condition
            if (value instanceof Constant(float constantValue))
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(coder.createFloat(constantValue));
            // Renvoie une valeur à l'appelant
            return TAGGED_CODEC.encode(coder, value);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<LevelBasedValue> decode(Transcoder<D> coder, D value) {
            // Appelle une méthode
            final Result<Float> numberResult = coder.getFloat(value);
            // Embranchement : vérifie une condition
            if (numberResult instanceof Result.Ok(Float number))
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(new Constant(number));
            // Renvoie une valeur à l'appelant
            return TAGGED_CODEC.decode(coder, value);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<StructCodec<? extends LevelBasedValue>> createDefaultRegistry() {
        // Appelle une méthode
        final DynamicRegistry<StructCodec<? extends LevelBasedValue>> registry = DynamicRegistry.create(Key.key("minestom:enchantment_value_effect"));
        // Note that constant is omitted from the registry, it has serialization handled out of band above.
        // Appelle une méthode
        registry.register("linear", Linear.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("clamped", Clamped.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("fraction", Fraction.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("levels_squared", LevelsSquared.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("lookup", Lookup.CODEC, DataPack.MINECRAFT_CORE);
        // Renvoie une valeur à l'appelant
        return registry;
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    float calc(int level);

    // Appelle une méthode
    StructCodec<? extends LevelBasedValue> codec();

    // Déclaration de type (classe/interface/enum/record)
    record Constant(float value) implements LevelBasedValue {

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public float calc(int level) {
            // Renvoie une valeur à l'appelant
            return value;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<Constant> codec() {
            // Lève une exception
            throw new UnsupportedOperationException("Constant values are serialized as a special case, see LevelBasedValue.CODEC");
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Linear(float base, float perLevelAboveFirst) implements LevelBasedValue {
        // Affecte une valeur
        public static final StructCodec<Linear> CODEC = StructCodec.struct(
                // Instruction de code
                "base", Codec.FLOAT, Linear::base,
                // Instruction de code
                "per_level_above_first", Codec.FLOAT, Linear::perLevelAboveFirst,
                // Instruction de code
                Linear::new
        // Fin d'un bloc/d'une expression
        );

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public float calc(int level) {
            // Renvoie une valeur à l'appelant
            return base + (perLevelAboveFirst * (level - 1));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<Linear> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Clamped(LevelBasedValue value, float min, float max) implements LevelBasedValue {
        // Affecte une valeur
        public static final StructCodec<Clamped> CODEC = StructCodec.struct(
                // Instruction de code
                "value", LevelBasedValue.CODEC, Clamped::value,
                // Instruction de code
                "min", Codec.FLOAT, Clamped::min,
                // Instruction de code
                "max", Codec.FLOAT, Clamped::max,
                // Instruction de code
                Clamped::new
        // Fin d'un bloc/d'une expression
        );

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public float calc(int level) {
            // Renvoie une valeur à l'appelant
            return MathUtils.clamp(value.calc(level), min, max);
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
    record Fraction(LevelBasedValue numerator,
                    // Début d'une méthode/d'un bloc
                    LevelBasedValue denominator) implements LevelBasedValue {
        // Affecte une valeur
        public static final StructCodec<Fraction> CODEC = StructCodec.struct(
                // Instruction de code
                "numerator", LevelBasedValue.CODEC, Fraction::numerator,
                // Instruction de code
                "denominator", LevelBasedValue.CODEC, Fraction::denominator,
                // Instruction de code
                Fraction::new
        // Fin d'un bloc/d'une expression
        );

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public float calc(int level) {
            // Appelle une méthode
            float denominator = this.denominator.calc(level);
            // Renvoie une valeur à l'appelant
            return denominator == 0f ? 0f : numerator.calc(level) / denominator;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<Fraction> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record LevelsSquared(float added) implements LevelBasedValue {
        // Affecte une valeur
        public static final StructCodec<LevelsSquared> CODEC = StructCodec.struct(
                // Instruction de code
                "added", Codec.FLOAT, LevelsSquared::added,
                // Instruction de code
                LevelsSquared::new
        // Fin d'un bloc/d'une expression
        );

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public float calc(int level) {
            // Renvoie une valeur à l'appelant
            return (level * level) + added;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<LevelsSquared> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Lookup(List<Float> values, LevelBasedValue fallback) implements LevelBasedValue {
        // Affecte une valeur
        public static final StructCodec<Lookup> CODEC = StructCodec.struct(
                // Instruction de code
                "values", Codec.FLOAT.list(), Lookup::values,
                // Instruction de code
                "fallback", LevelBasedValue.CODEC, Lookup::fallback,
                // Instruction de code
                Lookup::new
        // Fin d'un bloc/d'une expression
        );

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public float calc(int level) {
            // Embranchement : vérifie une condition
            if (level < 0 || level > values.size()) return fallback.calc(level);
            // Renvoie une valeur à l'appelant
            return values.get(level - 1);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<Lookup> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
