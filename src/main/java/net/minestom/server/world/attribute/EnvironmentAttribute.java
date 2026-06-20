// Déclaration du paquet de ce fichier
package net.minestom.server.world.attribute;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.util.ARGBLike;
// Import d'une classe nécessaire
import net.kyori.adventure.util.RGBLike;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
public sealed interface EnvironmentAttribute<T> extends EnvironmentAttributes permits EnvironmentAttributeImpl {

    // Appelle une méthode
    Key key();

    // Appelle une méthode
    Type<T> type();

    // Appelle une méthode
    T defaultValue();

    // Appelle une méthode
    Codec<T> valueCodec();

    // Début d'une méthode/d'un bloc
    static Collection<EnvironmentAttribute<?>> values() {
        // Renvoie une valeur à l'appelant
        return EnvironmentAttributeImpl.REGISTRY.values();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    sealed interface Type<T> extends EnvironmentAttributeTypes permits EnvironmentAttributeTypeImpl {

        // Appelle une méthode
        Codec<T> codec();

        // Appelle une méthode
        Codec<Modifier<T, ?>> modifierCodec();

    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SuppressWarnings({"unchecked", "rawtypes"})
    // Déclaration de type (classe/interface/enum/record)
    sealed interface Modifier<Sub, Arg> permits BooleanModifier, FloatModifier, ColorModifier, Modifier.Override {

        // Affecte une valeur
        Map<Operator, Modifier<java.lang.Boolean, ?>> BOOLEAN_OPERATORS = Map.of(
                // Instruction de code
                Operator.AND, Boolean.AND,
                // Instruction de code
                Operator.NAND, Boolean.NAND,
                // Instruction de code
                Operator.OR, Boolean.OR,
                // Instruction de code
                Operator.NOR, Boolean.NOR,
                // Instruction de code
                Operator.XOR, Boolean.XOR,
                // Instruction de code
                Operator.XNOR, Boolean.XNOR);
        // Affecte une valeur
        Map<Operator, Modifier<java.lang.Float, ?>> FLOAT_OPERATORS = Map.of(
                // Instruction de code
                Operator.ALPHA_BLEND, Float.ALPHA_BLEND,
                // Instruction de code
                Operator.ADD, Float.ADD,
                // Instruction de code
                Operator.SUBTRACT, Float.SUBTRACT,
                // Instruction de code
                Operator.MULTIPLY, Float.MULTIPLY,
                // Instruction de code
                Operator.MAXIMUM, Float.MAXIMUM,
                // Instruction de code
                Operator.MINIMUM, Float.MINIMUM);
        // Affecte une valeur
        Map<Operator, Modifier<RGBLike, ?>> RGB_OPERATORS = Map.of(
                // Instruction de code
                Operator.ALPHA_BLEND, Color.ALPHA_BLEND,
                // Instruction de code
                Operator.ADD, Color.ADD,
                // Instruction de code
                Operator.SUBTRACT, Color.SUBTRACT,
                // Instruction de code
                Operator.MULTIPLY, Color.MULTIPLY_RGB,
                // Instruction de code
                Operator.BLEND_TO_GRAY, Color.BLEND_TO_GRAY);
        // Affecte une valeur
        Map<Operator, Modifier<ARGBLike, ?>> ARGB_OPERATORS = Map.of(
                // Instruction de code
                Operator.ALPHA_BLEND, (Modifier) Color.ALPHA_BLEND,
                // Instruction de code
                Operator.ADD, (Modifier) Color.ADD,
                // Instruction de code
                Operator.SUBTRACT, (Modifier) Color.SUBTRACT,
                // Instruction de code
                Operator.MULTIPLY, (Modifier) Color.MULTIPLY_ARGB,
                // Appelle une méthode
                Operator.BLEND_TO_GRAY, (Modifier) Color.BLEND_TO_GRAY);

        // Déclaration de type (classe/interface/enum/record)
        enum Operator {
            // Instruction de code
            OVERRIDE,
            // Instruction de code
            ALPHA_BLEND,
            // Instruction de code
            ADD,
            // Instruction de code
            SUBTRACT,
            // Instruction de code
            MULTIPLY,
            // Instruction de code
            BLEND_TO_GRAY,
            // Instruction de code
            MINIMUM,
            // Instruction de code
            MAXIMUM,
            // Instruction de code
            AND,
            // Instruction de code
            NAND,
            // Instruction de code
            OR,
            // Instruction de code
            NOR,
            // Instruction de code
            XOR,
            // Instruction de code
            XNOR;

            // Appelle une méthode
            public static final Codec<Operator> CODEC = Codec.Enum(Operator.class);
        // Fin d'un bloc/d'une expression
        }

        // Déclaration de type (classe/interface/enum/record)
        record Override<Value>(
                // Instruction de code
                Codec<Value> argumentCodec
        // Début d'une méthode/d'un bloc
        ) implements Modifier<Value, Value> {
            // Annotation pour l'élément suivant
            @java.lang.Override
            // Début d'une méthode/d'un bloc
            public Value modify(Value subject, Value argument) {
                // Renvoie une valeur à l'appelant
                return argument;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Déclaration de type (classe/interface/enum/record)
        final class Boolean {
            // Affecte une valeur
            public static final Modifier<java.lang.Boolean, java.lang.Boolean> AND = BooleanModifier.AND;
            // Affecte une valeur
            public static final Modifier<java.lang.Boolean, java.lang.Boolean> NAND = BooleanModifier.NAND;
            // Affecte une valeur
            public static final Modifier<java.lang.Boolean, java.lang.Boolean> OR = BooleanModifier.OR;
            // Affecte une valeur
            public static final Modifier<java.lang.Boolean, java.lang.Boolean> NOR = BooleanModifier.NOR;
            // Affecte une valeur
            public static final Modifier<java.lang.Boolean, java.lang.Boolean> XOR = BooleanModifier.XOR;
            // Affecte une valeur
            public static final Modifier<java.lang.Boolean, java.lang.Boolean> XNOR = BooleanModifier.XNOR;
        // Fin d'un bloc/d'une expression
        }

        // Déclaration de type (classe/interface/enum/record)
        final class Float {
            // Affecte une valeur
            public static final Modifier<java.lang.Float, AlphaFloat> ALPHA_BLEND = FloatModifier.ALPHA_BLEND;
            // Affecte une valeur
            public static final Modifier<java.lang.Float, java.lang.Float> ADD = FloatModifier.ADD;
            // Affecte une valeur
            public static final Modifier<java.lang.Float, java.lang.Float> SUBTRACT = FloatModifier.SUBTRACT;
            // Affecte une valeur
            public static final Modifier<java.lang.Float, java.lang.Float> MULTIPLY = FloatModifier.MULTIPLY;
            // Affecte une valeur
            public static final Modifier<java.lang.Float, java.lang.Float> MAXIMUM = FloatModifier.MAXIMUM;
            // Affecte une valeur
            public static final Modifier<java.lang.Float, java.lang.Float> MINIMUM = FloatModifier.MINIMUM;
        // Fin d'un bloc/d'une expression
        }

        // Déclaration de type (classe/interface/enum/record)
        final class Color {
            // Affecte une valeur
            public static final Modifier<RGBLike, ARGBLike> ALPHA_BLEND = ColorModifier.ALPHA_BLEND;
            // Affecte une valeur
            public static final Modifier<RGBLike, RGBLike> ADD = ColorModifier.ADD;
            // Affecte une valeur
            public static final Modifier<RGBLike, RGBLike> SUBTRACT = ColorModifier.SUBTRACT;
            // Affecte une valeur
            public static final Modifier<RGBLike, RGBLike> MULTIPLY_RGB = ColorModifier.MULTIPLY_RGB;
            // Affecte une valeur
            public static final Modifier<RGBLike, ARGBLike> MULTIPLY_ARGB = ColorModifier.MULTIPLY_ARGB;
            // Affecte une valeur
            public static final Modifier<RGBLike, BlendToGray> BLEND_TO_GRAY = ColorModifier.BLEND_TO_GRAY;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        Sub modify(Sub subject, Arg argument);

        // Appelle une méthode
        Codec<Arg> argumentCodec();

    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
