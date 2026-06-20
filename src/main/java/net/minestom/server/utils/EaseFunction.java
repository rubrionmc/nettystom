// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.stream.Collectors;

/// The set of ease functions available to the client, with the appropriate names.
///
/// @see Ease Ease for the functions themselves.
// Déclaration de type (classe/interface/enum/record)
public interface EaseFunction {
    // Affecte une valeur
    EaseFunction CONSTANT = Ease::constant;
    // Affecte une valeur
    EaseFunction LINEAR = Ease::linear;
    // Affecte une valeur
    EaseFunction IN_QUAD = Ease::inQuad;
    // Affecte une valeur
    EaseFunction OUT_QUAD = Ease::outQuad;
    // Affecte une valeur
    EaseFunction IN_OUT_QUAD = Ease::inOutQuad;
    // Affecte une valeur
    EaseFunction IN_CUBIC = Ease::inCubic;
    // Affecte une valeur
    EaseFunction OUT_CUBIC = Ease::outCubic;
    // Affecte une valeur
    EaseFunction IN_OUT_CUBIC = Ease::inOutCubic;
    // Affecte une valeur
    EaseFunction IN_QUART = Ease::inQuart;
    // Affecte une valeur
    EaseFunction OUT_QUART = Ease::outQuart;
    // Affecte une valeur
    EaseFunction IN_OUT_QUART = Ease::inOutQuart;
    // Affecte une valeur
    EaseFunction IN_QUINT = Ease::inQuint;
    // Affecte une valeur
    EaseFunction OUT_QUINT = Ease::outQuint;
    // Affecte une valeur
    EaseFunction IN_OUT_QUINT = Ease::inOutQuint;
    // Affecte une valeur
    EaseFunction IN_SINE = Ease::inSine;
    // Affecte une valeur
    EaseFunction OUT_SINE = Ease::outSine;
    // Affecte une valeur
    EaseFunction IN_OUT_SINE = Ease::inOutSine;
    // Affecte une valeur
    EaseFunction IN_EXPO = Ease::inExpo;
    // Affecte une valeur
    EaseFunction OUT_EXPO = Ease::outExpo;
    // Affecte une valeur
    EaseFunction IN_OUT_EXPO = Ease::inOutExpo;
    // Affecte une valeur
    EaseFunction IN_CIRC = Ease::inCirc;
    // Affecte une valeur
    EaseFunction OUT_CIRC = Ease::outCirc;
    // Affecte une valeur
    EaseFunction IN_OUT_CIRC = Ease::inOutCirc;
    // Affecte une valeur
    EaseFunction IN_BACK = Ease::inBack;
    // Affecte une valeur
    EaseFunction OUT_BACK = Ease::outBack;
    // Affecte une valeur
    EaseFunction IN_OUT_BACK = Ease::inOutBack;
    // Affecte une valeur
    EaseFunction IN_ELASTIC = Ease::inElastic;

    // Only contains the named functions
    // Affecte une valeur
    Map<String, EaseFunction> NAMED_BY_KEY = Map.ofEntries(
            // Instruction de code
            Map.entry("constant", CONSTANT),
            // Instruction de code
            Map.entry("linear", LINEAR),
            // Instruction de code
            Map.entry("in_quad", IN_QUAD),
            // Instruction de code
            Map.entry("out_quad", OUT_QUAD),
            // Instruction de code
            Map.entry("in_out_quad", IN_OUT_QUAD),
            // Instruction de code
            Map.entry("in_cubic", IN_CUBIC),
            // Instruction de code
            Map.entry("out_cubic", OUT_CUBIC),
            // Instruction de code
            Map.entry("in_out_cubic", IN_OUT_CUBIC),
            // Instruction de code
            Map.entry("in_quart", IN_QUART),
            // Instruction de code
            Map.entry("out_quart", OUT_QUART),
            // Instruction de code
            Map.entry("in_out_quart", IN_OUT_QUART),
            // Instruction de code
            Map.entry("in_quint", IN_QUINT),
            // Instruction de code
            Map.entry("out_quint", OUT_QUINT),
            // Instruction de code
            Map.entry("in_out_quint", IN_OUT_QUINT),
            // Instruction de code
            Map.entry("in_sine", IN_SINE),
            // Instruction de code
            Map.entry("out_sine", OUT_SINE),
            // Instruction de code
            Map.entry("in_out_sine", IN_OUT_SINE),
            // Instruction de code
            Map.entry("in_expo", IN_EXPO),
            // Instruction de code
            Map.entry("out_expo", OUT_EXPO),
            // Instruction de code
            Map.entry("in_out_expo", IN_OUT_EXPO),
            // Instruction de code
            Map.entry("in_circ", IN_CIRC),
            // Instruction de code
            Map.entry("out_circ", OUT_CIRC),
            // Instruction de code
            Map.entry("in_out_circ", IN_OUT_CIRC),
            // Instruction de code
            Map.entry("in_back", IN_BACK),
            // Instruction de code
            Map.entry("out_back", OUT_BACK),
            // Instruction de code
            Map.entry("in_out_back", IN_OUT_BACK),
            // Instruction de code
            Map.entry("in_elastic", IN_ELASTIC)
    // Fin d'un bloc/d'une expression
    );
    // Affecte une valeur
    Map<EaseFunction, String> NAMED_BY_VALUE = NAMED_BY_KEY.entrySet().stream()
            // Appelle une méthode
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    // Affecte une valeur
    Codec<EaseFunction> CODEC = Codec.Either(Codec.STRING.transform(NAMED_BY_KEY::get, NAMED_BY_VALUE::get), CubicBezier.CODEC)
            // Instruction de code
            .transform(either -> either.unify(f -> f, f -> f),
                    // Appelle une méthode
                    f -> f instanceof CubicBezier bezier ? Either.right(bezier) : Either.left(f));

    // Appelle une méthode
    float sample(float value);

    // Déclaration de type (classe/interface/enum/record)
    final class CubicBezier implements EaseFunction {
        // Affecte une valeur
        private static final int NEWTON_RAPHSON_ITERATIONS = 4;

        // Affecte une valeur
        private static final Codec<float[]> CONTROL_POINTS_CODEC = Codec.FLOAT.list(4).transform(
                // Instruction de code
                floats -> new float[]{floats.get(0), floats.get(1), floats.get(2), floats.get(3)},
                // Appelle une méthode
                array -> List.of(array[0], array[1], array[2], array[3]));
        // Affecte une valeur
        public static final Codec<CubicBezier> CODEC = StructCodec.struct(
                // Instruction de code
                "cubic_bezier", CONTROL_POINTS_CODEC, CubicBezier::controlPoints,
                // Instruction de code
                CubicBezier::new);

        // Instruction de code
        private final float[] controlPoints;
        // Instruction de code
        private final Curve x, y;

        // Début d'une méthode/d'un bloc
        public CubicBezier(float[] controlPoints) {
            // Appelle une méthode
            Objects.requireNonNull(controlPoints, "controlPoints");
            // Appelle une méthode
            Check.argCondition(controlPoints.length != 4, "CubicBezier requires 4 control points");
            // Accès à l'objet courant/parent
            this.controlPoints = controlPoints;
            // Accès à l'objet courant/parent
            this.x = new Curve(controlPoints[0], controlPoints[1]);
            // Accès à l'objet courant/parent
            this.y = new Curve(controlPoints[2], controlPoints[3]);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public float[] controlPoints() {
            // Renvoie une valeur à l'appelant
            return this.controlPoints;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public float sample(float t) {
            // Affecte une valeur
            float currentT = t;
            // Boucle : répète un bloc
            for (int i = 0; i < NEWTON_RAPHSON_ITERATIONS; i++) {
                // Appelle une méthode
                float slope = this.x.sampleGradient(currentT);
                // Embranchement : vérifie une condition
                if (slope < Vec.EPSILON) break;

                // Appelle une méthode
                float error = this.x.sample(currentT) - t;
                // Instruction de code
                currentT -= error / slope;
            // Fin d'un bloc/d'une expression
            }

            // Renvoie une valeur à l'appelant
            return this.y.sample(currentT);
        // Fin d'un bloc/d'une expression
        }

        // Déclaration de type (classe/interface/enum/record)
        private record Curve(float a, float b, float c) {
            // Début d'une méthode/d'un bloc
            public Curve(float cp1, float cp2) {
                // Appelle une méthode
                this(3.0F * cp1 - 3.0F * cp2 + 1.0F, -6.0F * cp1 + 3.0F * cp2, 3.0F * cp1);
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public float sample(float t) {
                // Renvoie une valeur à l'appelant
                return ((a * t + b) * t + c) * t;
            // Fin d'un bloc/d'une expression
            }

            // Début d'une méthode/d'un bloc
            public float sampleGradient(float t) {
                // Renvoie une valeur à l'appelant
                return 3 * a * t * t + 2 * b * t + c;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
