// Déclaration du paquet de ce fichier
package net.minestom.server.item.enchant;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.gamedata.DataPack;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.List;

// Début d'une méthode/d'un bloc
public non-sealed interface ValueEffect extends Enchantment.Effect {

    // Affecte une valeur
    StructCodec<ValueEffect> CODEC = Codec.RegistryTaggedUnion(
            // Instruction de code
            Registries::enchantmentValueEffects, ValueEffect::codec);

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<StructCodec<? extends ValueEffect>> createDefaultRegistry() {
        // Appelle une méthode
        final DynamicRegistry<StructCodec<? extends ValueEffect>> registry = DynamicRegistry.create(Key.key("minestom:enchantment_value_effect"));
        // Appelle une méthode
        registry.register("add", Add.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("all_of", AllOf.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("multiply", Multiply.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("remove_binomial", RemoveBinomial.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("exponential", Exponential.CODEC, DataPack.MINECRAFT_CORE);
        // Appelle une méthode
        registry.register("set", Set.CODEC, DataPack.MINECRAFT_CORE);
        // Renvoie une valeur à l'appelant
        return registry;
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    float apply(float base, int level);

    // Appelle une méthode
    StructCodec<? extends ValueEffect> codec();

    // Déclaration de type (classe/interface/enum/record)
    record Add(LevelBasedValue value) implements ValueEffect {
        // Affecte une valeur
        public static final StructCodec<Add> CODEC = StructCodec.struct(
                // Instruction de code
                "value", LevelBasedValue.CODEC, Add::value,
                // Instruction de code
                Add::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public float apply(float base, int level) {
            // Renvoie une valeur à l'appelant
            return base + value.calc(level);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<Add> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record AllOf(List<ValueEffect> effects) implements ValueEffect {
        // Affecte une valeur
        public static final StructCodec<AllOf> CODEC = StructCodec.struct(
                // Instruction de code
                "effects", ValueEffect.CODEC.list(), AllOf::effects,
                // Instruction de code
                AllOf::new);

        // Début d'une méthode/d'un bloc
        public AllOf {
            // Appelle une méthode
            effects = List.copyOf(effects);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public float apply(float base, int level) {
            // Boucle : répète un bloc
            for (ValueEffect effect : effects)
                // Appelle une méthode
                base = effect.apply(base, level);
            // Renvoie une valeur à l'appelant
            return base;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<AllOf> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Multiply(LevelBasedValue factor) implements ValueEffect {
        // Affecte une valeur
        public static final StructCodec<Multiply> CODEC = StructCodec.struct(
                // Instruction de code
                "factor", LevelBasedValue.CODEC, Multiply::factor,
                // Instruction de code
                Multiply::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public float apply(float base, int level) {
            // Renvoie une valeur à l'appelant
            return base * factor.calc(level);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<Multiply> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record RemoveBinomial(LevelBasedValue chance) implements ValueEffect {
        // Affecte une valeur
        public static final StructCodec<RemoveBinomial> CODEC = StructCodec.struct(
                // Instruction de code
                "chance", LevelBasedValue.CODEC, RemoveBinomial::chance,
                // Instruction de code
                RemoveBinomial::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public float apply(float base, int level) {
            // Lève une exception
            throw new UnsupportedOperationException("todo");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<RemoveBinomial> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Exponential(LevelBasedValue base, LevelBasedValue exponent) implements ValueEffect {
        // Affecte une valeur
        public static final StructCodec<Exponential> CODEC = StructCodec.struct(
                // Instruction de code
                "base", LevelBasedValue.CODEC, Exponential::base,
                // Instruction de code
                "exponent", LevelBasedValue.CODEC, Exponential::exponent,
                // Instruction de code
                Exponential::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public float apply(float base, int level) {
            // Renvoie une valeur à l'appelant
            return base * (float) Math.pow(this.base.calc(level), this.exponent.calc(level));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends ValueEffect> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Set(LevelBasedValue value) implements ValueEffect {
        // Affecte une valeur
        public static final StructCodec<Set> CODEC = StructCodec.struct(
                // Instruction de code
                "value", LevelBasedValue.CODEC, Set::value,
                // Instruction de code
                Set::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public float apply(float base, int level) {
            // Renvoie une valeur à l'appelant
            return value.calc(level);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<Set> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
