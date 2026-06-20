// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// https://github.com/ai/easings.net/blob/master/src/easings/easingsFunctions.ts
// Déclaration de type (classe/interface/enum/record)
public final class Ease {
    // Affecte une valeur
    private static final float c1 = 1.70158f;
    // Affecte une valeur
    private static final float c2 = c1 * 1.525f;
    // Affecte une valeur
    private static final float c3 = c1 + 1;
    // Appelle une méthode
    private static final float c4 = (float) (2 * Math.PI) / 3;
    // Appelle une méthode
    private static final float c5 = (float) (2 * Math.PI) / 4.5f;

    // Début d'une méthode/d'un bloc
    public static float constant(float x) {
        // Renvoie une valeur à l'appelant
        return 0f;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float linear(float x) {
        // Renvoie une valeur à l'appelant
        return x;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float inQuad(float x) {
        // Renvoie une valeur à l'appelant
        return x * x;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float outQuad(float x) {
        // Renvoie une valeur à l'appelant
        return 1 - (1 - x) * (1 - x);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float inOutQuad(float x) {
        // Embranchement : vérifie une condition
        if (x < 0.5) return 2 * x * x;
        // Renvoie une valeur à l'appelant
        return 1 - (float) Math.pow(-2 * x + 2, 2) / 2;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float inCubic(float x) {
        // Renvoie une valeur à l'appelant
        return x * x * x;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float outCubic(float x) {
        // Renvoie une valeur à l'appelant
        return 1 - (float) Math.pow(1 - x, 3);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float inOutCubic(float x) {
        // Embranchement : vérifie une condition
        if (x < 0.5) return 4 * x * x * x;
        // Renvoie une valeur à l'appelant
        return 1 - (float) Math.pow(-2 * x + 2, 3) / 2;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float inQuart(float x) {
        // Renvoie une valeur à l'appelant
        return x * x * x * x;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float outQuart(float x) {
        // Renvoie une valeur à l'appelant
        return 1 - (float) Math.pow(1 - x, 4);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float inOutQuart(float x) {
        // Embranchement : vérifie une condition
        if (x < 0.5) return 8 * x * x * x * x;
        // Renvoie une valeur à l'appelant
        return 1 - (float) Math.pow(-2 * x + 2, 4) / 2;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float inQuint(float x) {
        // Renvoie une valeur à l'appelant
        return x * x * x * x * x;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float outQuint(float x) {
        // Renvoie une valeur à l'appelant
        return 1 - (float) Math.pow(1 - x, 5);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float inOutQuint(float x) {
        // Embranchement : vérifie une condition
        if (x < 0.5) return 16 * x * x * x * x * x;
        // Renvoie une valeur à l'appelant
        return 1 - (float) Math.pow(-2 * x + 2, 5) / 2;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float inSine(float x) {
        // Renvoie une valeur à l'appelant
        return 1 - (float) Math.cos((x * Math.PI) / 2);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float outSine(float x) {
        // Renvoie une valeur à l'appelant
        return (float) Math.sin((x * Math.PI) / 2);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float inOutSine(float x) {
        // Renvoie une valeur à l'appelant
        return (float) -(Math.cos(Math.PI * x) - 1) / 2;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float inExpo(float x) {
        // Embranchement : vérifie une condition
        if (x == 0) return 0;
        // Renvoie une valeur à l'appelant
        return (float) Math.pow(2, 10 * x - 10);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float outExpo(float x) {
        // Embranchement : vérifie une condition
        if (x == 1) return 1;
        // Renvoie une valeur à l'appelant
        return 1 - (float) Math.pow(2, -10 * x);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float inOutExpo(float x) {
        // Embranchement : vérifie une condition
        if (x == 0) return 0;
        // Embranchement : vérifie une condition
        if (x == 1) return 1;
        // Embranchement : vérifie une condition
        if (x < 0.5) return (float) Math.pow(2, 20 * x - 10) / 2;
        // Renvoie une valeur à l'appelant
        return (2 - (float) Math.pow(2, -20 * x + 10)) / 2;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float inCirc(float x) {
        // Renvoie une valeur à l'appelant
        return 1 - (float) Math.sqrt(1 - Math.pow(x, 2));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float outCirc(float x) {
        // Renvoie une valeur à l'appelant
        return (float) Math.sqrt(1 - Math.pow(x - 1, 2));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float inOutCirc(float x) {
        // Embranchement : vérifie une condition
        if (x < 0.5) return (float) (1 - Math.sqrt(1 - Math.pow(2 * x, 2))) / 2;
        // Renvoie une valeur à l'appelant
        return (float) (Math.sqrt(1 - Math.pow(-2 * x + 2, 2)) + 1) / 2;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float inBack(float x) {
        // Renvoie une valeur à l'appelant
        return c3 * x * x * x - c1 * x * x;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float outBack(float x) {
        // Renvoie une valeur à l'appelant
        return 1 + c3 * (float) Math.pow(x - 1, 3) + c1 * (float) Math.pow(x - 1, 2);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float inOutBack(float x) {
        // Renvoie une valeur à l'appelant
        return 1 + c3 * (float) Math.pow(x - 1, 3) + c1 * (float) Math.pow(x - 1, 2);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float inElastic(float x) {
        // Embranchement : vérifie une condition
        if (x == 0) return 0;
        // Embranchement : vérifie une condition
        if (x == 1) return 1;
        // Renvoie une valeur à l'appelant
        return (float) (-Math.pow(2, 10 * x - 10) * Math.sin((x * 10 - 10.75) * c4));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float outElastic(float x) {
        // Embranchement : vérifie une condition
        if (x == 0) return 0;
        // Embranchement : vérifie une condition
        if (x == 1) return 1;
        // Renvoie une valeur à l'appelant
        return (float) Math.pow(2, -10 * x) * (float) Math.sin((x * 10 - 0.75) * c4) + 1;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float inOutElastic(float x) {
        // Embranchement : vérifie une condition
        if (x == 0) return 0;
        // Embranchement : vérifie une condition
        if (x == 1) return 1;
        // Embranchement : vérifie une condition
        if (x < 0.5) return (float) -(Math.pow(2, 20 * x - 10) * Math.sin((20 * x - 11.125) * c5)) / 2;
        // Renvoie une valeur à l'appelant
        return (float) (Math.pow(2, -20 * x + 10) * Math.sin((20 * x - 11.125) * c5)) / 2 + 1;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float inBounce(float x) {
        // Renvoie une valeur à l'appelant
        return 1 - outBounce(1 - x);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float outBounce(float x) {
        // Affecte une valeur
        float n1 = 7.5625f;
        // Affecte une valeur
        float d1 = 2.75f;

        // Embranchement : vérifie une condition
        if (x < 1 / d1) {
            // Renvoie une valeur à l'appelant
            return n1 * x * x;
        // Embranchement : vérifie une condition
        } else if (x < 2 / d1) {
            // Instruction de code
            x -= 1.5f / d1;
            // Renvoie une valeur à l'appelant
            return n1 * x * x + 0.75f;
        // Embranchement : vérifie une condition
        } else if (x < 2.5 / d1) {
            // Instruction de code
            x -= 2.25f / d1;
            // Renvoie une valeur à l'appelant
            return n1 * x * x + 0.9375f;
        // Branche alternative de la condition
        } else {
            // Instruction de code
            x -= 2.625f / d1;
            // Renvoie une valeur à l'appelant
            return n1 * x * x + 0.984375f;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float inOutBounce(float x) {
        // Embranchement : vérifie une condition
        if (x < 0.5) return (1 - outBounce(1 - 2 * x)) / 2;
        // Renvoie une valeur à l'appelant
        return (1 + outBounce(2 * x - 1)) / 2;
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
