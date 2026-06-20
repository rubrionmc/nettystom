// Package declaration for this file
package net.minestom.server.world.attribute;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.util.ARGBLike;
// Import of a required class
import net.kyori.adventure.util.RGBLike;
// Import of a required class
import net.minestom.server.codec.Codec;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.Map;

// Type declaration (class/interface/enum/record)
public sealed interface EnvironmentAttribute<T> extends EnvironmentAttributes permits EnvironmentAttributeImpl {

    // Calls a method
    Key key();

    // Calls a method
    Type<T> type();

    // Calls a method
    T defaultValue();

    // Calls a method
    Codec<T> valueCodec();

    // Start of a method/block
    static Collection<EnvironmentAttribute<?>> values() {
        // Returns a value to the caller
        return EnvironmentAttributeImpl.REGISTRY.values();
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    sealed interface Type<T> extends EnvironmentAttributeTypes permits EnvironmentAttributeTypeImpl {

        // Calls a method
        Codec<T> codec();

        // Calls a method
        Codec<Modifier<T, ?>> modifierCodec();

    // End of a block/expression
    }

    // Annotation for the following element
    @SuppressWarnings({"unchecked", "rawtypes"})
    // Type declaration (class/interface/enum/record)
    sealed interface Modifier<Sub, Arg> permits BooleanModifier, FloatModifier, ColorModifier, Modifier.Override {

        // Assigns a value
        Map<Operator, Modifier<java.lang.Boolean, ?>> BOOLEAN_OPERATORS = Map.of(
                // Code statement
                Operator.AND, Boolean.AND,
                // Code statement
                Operator.NAND, Boolean.NAND,
                // Code statement
                Operator.OR, Boolean.OR,
                // Code statement
                Operator.NOR, Boolean.NOR,
                // Code statement
                Operator.XOR, Boolean.XOR,
                // Code statement
                Operator.XNOR, Boolean.XNOR);
        // Assigns a value
        Map<Operator, Modifier<java.lang.Float, ?>> FLOAT_OPERATORS = Map.of(
                // Code statement
                Operator.ALPHA_BLEND, Float.ALPHA_BLEND,
                // Code statement
                Operator.ADD, Float.ADD,
                // Code statement
                Operator.SUBTRACT, Float.SUBTRACT,
                // Code statement
                Operator.MULTIPLY, Float.MULTIPLY,
                // Code statement
                Operator.MAXIMUM, Float.MAXIMUM,
                // Code statement
                Operator.MINIMUM, Float.MINIMUM);
        // Assigns a value
        Map<Operator, Modifier<RGBLike, ?>> RGB_OPERATORS = Map.of(
                // Code statement
                Operator.ALPHA_BLEND, Color.ALPHA_BLEND,
                // Code statement
                Operator.ADD, Color.ADD,
                // Code statement
                Operator.SUBTRACT, Color.SUBTRACT,
                // Code statement
                Operator.MULTIPLY, Color.MULTIPLY_RGB,
                // Code statement
                Operator.BLEND_TO_GRAY, Color.BLEND_TO_GRAY);
        // Assigns a value
        Map<Operator, Modifier<ARGBLike, ?>> ARGB_OPERATORS = Map.of(
                // Code statement
                Operator.ALPHA_BLEND, (Modifier) Color.ALPHA_BLEND,
                // Code statement
                Operator.ADD, (Modifier) Color.ADD,
                // Code statement
                Operator.SUBTRACT, (Modifier) Color.SUBTRACT,
                // Code statement
                Operator.MULTIPLY, (Modifier) Color.MULTIPLY_ARGB,
                // Calls a method
                Operator.BLEND_TO_GRAY, (Modifier) Color.BLEND_TO_GRAY);

        // Type declaration (class/interface/enum/record)
        enum Operator {
            // Code statement
            OVERRIDE,
            // Code statement
            ALPHA_BLEND,
            // Code statement
            ADD,
            // Code statement
            SUBTRACT,
            // Code statement
            MULTIPLY,
            // Code statement
            BLEND_TO_GRAY,
            // Code statement
            MINIMUM,
            // Code statement
            MAXIMUM,
            // Code statement
            AND,
            // Code statement
            NAND,
            // Code statement
            OR,
            // Code statement
            NOR,
            // Code statement
            XOR,
            // Code statement
            XNOR;

            // Calls a method
            public static final Codec<Operator> CODEC = Codec.Enum(Operator.class);
        // End of a block/expression
        }

        // Type declaration (class/interface/enum/record)
        record Override<Value>(
                // Code statement
                Codec<Value> argumentCodec
        // Start of a method/block
        ) implements Modifier<Value, Value> {
            // Annotation for the following element
            @java.lang.Override
            // Start of a method/block
            public Value modify(Value subject, Value argument) {
                // Returns a value to the caller
                return argument;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Type declaration (class/interface/enum/record)
        final class Boolean {
            // Assigns a value
            public static final Modifier<java.lang.Boolean, java.lang.Boolean> AND = BooleanModifier.AND;
            // Assigns a value
            public static final Modifier<java.lang.Boolean, java.lang.Boolean> NAND = BooleanModifier.NAND;
            // Assigns a value
            public static final Modifier<java.lang.Boolean, java.lang.Boolean> OR = BooleanModifier.OR;
            // Assigns a value
            public static final Modifier<java.lang.Boolean, java.lang.Boolean> NOR = BooleanModifier.NOR;
            // Assigns a value
            public static final Modifier<java.lang.Boolean, java.lang.Boolean> XOR = BooleanModifier.XOR;
            // Assigns a value
            public static final Modifier<java.lang.Boolean, java.lang.Boolean> XNOR = BooleanModifier.XNOR;
        // End of a block/expression
        }

        // Type declaration (class/interface/enum/record)
        final class Float {
            // Assigns a value
            public static final Modifier<java.lang.Float, AlphaFloat> ALPHA_BLEND = FloatModifier.ALPHA_BLEND;
            // Assigns a value
            public static final Modifier<java.lang.Float, java.lang.Float> ADD = FloatModifier.ADD;
            // Assigns a value
            public static final Modifier<java.lang.Float, java.lang.Float> SUBTRACT = FloatModifier.SUBTRACT;
            // Assigns a value
            public static final Modifier<java.lang.Float, java.lang.Float> MULTIPLY = FloatModifier.MULTIPLY;
            // Assigns a value
            public static final Modifier<java.lang.Float, java.lang.Float> MAXIMUM = FloatModifier.MAXIMUM;
            // Assigns a value
            public static final Modifier<java.lang.Float, java.lang.Float> MINIMUM = FloatModifier.MINIMUM;
        // End of a block/expression
        }

        // Type declaration (class/interface/enum/record)
        final class Color {
            // Assigns a value
            public static final Modifier<RGBLike, ARGBLike> ALPHA_BLEND = ColorModifier.ALPHA_BLEND;
            // Assigns a value
            public static final Modifier<RGBLike, RGBLike> ADD = ColorModifier.ADD;
            // Assigns a value
            public static final Modifier<RGBLike, RGBLike> SUBTRACT = ColorModifier.SUBTRACT;
            // Assigns a value
            public static final Modifier<RGBLike, RGBLike> MULTIPLY_RGB = ColorModifier.MULTIPLY_RGB;
            // Assigns a value
            public static final Modifier<RGBLike, ARGBLike> MULTIPLY_ARGB = ColorModifier.MULTIPLY_ARGB;
            // Assigns a value
            public static final Modifier<RGBLike, BlendToGray> BLEND_TO_GRAY = ColorModifier.BLEND_TO_GRAY;
        // End of a block/expression
        }

        // Calls a method
        Sub modify(Sub subject, Arg argument);

        // Calls a method
        Codec<Arg> argumentCodec();

    // End of a block/expression
    }
// End of a block/expression
}
