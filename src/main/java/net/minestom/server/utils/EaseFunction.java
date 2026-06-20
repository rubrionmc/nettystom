// Package declaration for this file
package net.minestom.server.utils;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.stream.Collectors;

/// The set of ease functions available to the client, with the appropriate names.
///
/// @see Ease Ease for the functions themselves.
// Type declaration (class/interface/enum/record)
public interface EaseFunction {
    // Assigns a value
    EaseFunction CONSTANT = Ease::constant;
    // Assigns a value
    EaseFunction LINEAR = Ease::linear;
    // Assigns a value
    EaseFunction IN_QUAD = Ease::inQuad;
    // Assigns a value
    EaseFunction OUT_QUAD = Ease::outQuad;
    // Assigns a value
    EaseFunction IN_OUT_QUAD = Ease::inOutQuad;
    // Assigns a value
    EaseFunction IN_CUBIC = Ease::inCubic;
    // Assigns a value
    EaseFunction OUT_CUBIC = Ease::outCubic;
    // Assigns a value
    EaseFunction IN_OUT_CUBIC = Ease::inOutCubic;
    // Assigns a value
    EaseFunction IN_QUART = Ease::inQuart;
    // Assigns a value
    EaseFunction OUT_QUART = Ease::outQuart;
    // Assigns a value
    EaseFunction IN_OUT_QUART = Ease::inOutQuart;
    // Assigns a value
    EaseFunction IN_QUINT = Ease::inQuint;
    // Assigns a value
    EaseFunction OUT_QUINT = Ease::outQuint;
    // Assigns a value
    EaseFunction IN_OUT_QUINT = Ease::inOutQuint;
    // Assigns a value
    EaseFunction IN_SINE = Ease::inSine;
    // Assigns a value
    EaseFunction OUT_SINE = Ease::outSine;
    // Assigns a value
    EaseFunction IN_OUT_SINE = Ease::inOutSine;
    // Assigns a value
    EaseFunction IN_EXPO = Ease::inExpo;
    // Assigns a value
    EaseFunction OUT_EXPO = Ease::outExpo;
    // Assigns a value
    EaseFunction IN_OUT_EXPO = Ease::inOutExpo;
    // Assigns a value
    EaseFunction IN_CIRC = Ease::inCirc;
    // Assigns a value
    EaseFunction OUT_CIRC = Ease::outCirc;
    // Assigns a value
    EaseFunction IN_OUT_CIRC = Ease::inOutCirc;
    // Assigns a value
    EaseFunction IN_BACK = Ease::inBack;
    // Assigns a value
    EaseFunction OUT_BACK = Ease::outBack;
    // Assigns a value
    EaseFunction IN_OUT_BACK = Ease::inOutBack;
    // Assigns a value
    EaseFunction IN_ELASTIC = Ease::inElastic;

    // Only contains the named functions
    // Assigns a value
    Map<String, EaseFunction> NAMED_BY_KEY = Map.ofEntries(
            // Code statement
            Map.entry("constant", CONSTANT),
            // Code statement
            Map.entry("linear", LINEAR),
            // Code statement
            Map.entry("in_quad", IN_QUAD),
            // Code statement
            Map.entry("out_quad", OUT_QUAD),
            // Code statement
            Map.entry("in_out_quad", IN_OUT_QUAD),
            // Code statement
            Map.entry("in_cubic", IN_CUBIC),
            // Code statement
            Map.entry("out_cubic", OUT_CUBIC),
            // Code statement
            Map.entry("in_out_cubic", IN_OUT_CUBIC),
            // Code statement
            Map.entry("in_quart", IN_QUART),
            // Code statement
            Map.entry("out_quart", OUT_QUART),
            // Code statement
            Map.entry("in_out_quart", IN_OUT_QUART),
            // Code statement
            Map.entry("in_quint", IN_QUINT),
            // Code statement
            Map.entry("out_quint", OUT_QUINT),
            // Code statement
            Map.entry("in_out_quint", IN_OUT_QUINT),
            // Code statement
            Map.entry("in_sine", IN_SINE),
            // Code statement
            Map.entry("out_sine", OUT_SINE),
            // Code statement
            Map.entry("in_out_sine", IN_OUT_SINE),
            // Code statement
            Map.entry("in_expo", IN_EXPO),
            // Code statement
            Map.entry("out_expo", OUT_EXPO),
            // Code statement
            Map.entry("in_out_expo", IN_OUT_EXPO),
            // Code statement
            Map.entry("in_circ", IN_CIRC),
            // Code statement
            Map.entry("out_circ", OUT_CIRC),
            // Code statement
            Map.entry("in_out_circ", IN_OUT_CIRC),
            // Code statement
            Map.entry("in_back", IN_BACK),
            // Code statement
            Map.entry("out_back", OUT_BACK),
            // Code statement
            Map.entry("in_out_back", IN_OUT_BACK),
            // Code statement
            Map.entry("in_elastic", IN_ELASTIC)
    // End of a block/expression
    );
    // Assigns a value
    Map<EaseFunction, String> NAMED_BY_VALUE = NAMED_BY_KEY.entrySet().stream()
            // Calls a method
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    // Assigns a value
    Codec<EaseFunction> CODEC = Codec.Either(Codec.STRING.transform(NAMED_BY_KEY::get, NAMED_BY_VALUE::get), CubicBezier.CODEC)
            // Code statement
            .transform(either -> either.unify(f -> f, f -> f),
                    // Calls a method
                    f -> f instanceof CubicBezier bezier ? Either.right(bezier) : Either.left(f));

    // Calls a method
    float sample(float value);

    // Type declaration (class/interface/enum/record)
    final class CubicBezier implements EaseFunction {
        // Assigns a value
        private static final int NEWTON_RAPHSON_ITERATIONS = 4;

        // Assigns a value
        private static final Codec<float[]> CONTROL_POINTS_CODEC = Codec.FLOAT.list(4).transform(
                // Code statement
                floats -> new float[]{floats.get(0), floats.get(1), floats.get(2), floats.get(3)},
                // Calls a method
                array -> List.of(array[0], array[1], array[2], array[3]));
        // Assigns a value
        public static final Codec<CubicBezier> CODEC = StructCodec.struct(
                // Code statement
                "cubic_bezier", CONTROL_POINTS_CODEC, CubicBezier::controlPoints,
                // Code statement
                CubicBezier::new);

        // Code statement
        private final float[] controlPoints;
        // Code statement
        private final Curve x, y;

        // Start of a method/block
        public CubicBezier(float[] controlPoints) {
            // Calls a method
            Objects.requireNonNull(controlPoints, "controlPoints");
            // Calls a method
            Check.argCondition(controlPoints.length != 4, "CubicBezier requires 4 control points");
            // Access to the current/parent object
            this.controlPoints = controlPoints;
            // Access to the current/parent object
            this.x = new Curve(controlPoints[0], controlPoints[1]);
            // Access to the current/parent object
            this.y = new Curve(controlPoints[2], controlPoints[3]);
        // End of a block/expression
        }

        // Start of a method/block
        public float[] controlPoints() {
            // Returns a value to the caller
            return this.controlPoints;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public float sample(float t) {
            // Assigns a value
            float currentT = t;
            // Loop: repeats a block
            for (int i = 0; i < NEWTON_RAPHSON_ITERATIONS; i++) {
                // Calls a method
                float slope = this.x.sampleGradient(currentT);
                // Branch: checks a condition
                if (slope < Vec.EPSILON) break;

                // Calls a method
                float error = this.x.sample(currentT) - t;
                // Code statement
                currentT -= error / slope;
            // End of a block/expression
            }

            // Returns a value to the caller
            return this.y.sample(currentT);
        // End of a block/expression
        }

        // Type declaration (class/interface/enum/record)
        private record Curve(float a, float b, float c) {
            // Start of a method/block
            public Curve(float cp1, float cp2) {
                // Calls a method
                this(3.0F * cp1 - 3.0F * cp2 + 1.0F, -6.0F * cp1 + 3.0F * cp2, 3.0F * cp1);
            // End of a block/expression
            }

            // Start of a method/block
            public float sample(float t) {
                // Returns a value to the caller
                return ((a * t + b) * t + c) * t;
            // End of a block/expression
            }

            // Start of a method/block
            public float sampleGradient(float t) {
                // Returns a value to the caller
                return 3 * a * t * t + 2 * b * t + c;
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
