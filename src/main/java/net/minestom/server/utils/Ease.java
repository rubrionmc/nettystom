// Package declaration for this file
package net.minestom.server.utils;

// https://github.com/ai/easings.net/blob/master/src/easings/easingsFunctions.ts
// Type declaration (class/interface/enum/record)
public final class Ease {
    // Assigns a value
    private static final float c1 = 1.70158f;
    // Assigns a value
    private static final float c2 = c1 * 1.525f;
    // Assigns a value
    private static final float c3 = c1 + 1;
    // Calls a method
    private static final float c4 = (float) (2 * Math.PI) / 3;
    // Calls a method
    private static final float c5 = (float) (2 * Math.PI) / 4.5f;

    // Start of a method/block
    public static float constant(float x) {
        // Returns a value to the caller
        return 0f;
    // End of a block/expression
    }

    // Start of a method/block
    public static float linear(float x) {
        // Returns a value to the caller
        return x;
    // End of a block/expression
    }

    // Start of a method/block
    public static float inQuad(float x) {
        // Returns a value to the caller
        return x * x;
    // End of a block/expression
    }

    // Start of a method/block
    public static float outQuad(float x) {
        // Returns a value to the caller
        return 1 - (1 - x) * (1 - x);
    // End of a block/expression
    }

    // Start of a method/block
    public static float inOutQuad(float x) {
        // Branch: checks a condition
        if (x < 0.5) return 2 * x * x;
        // Returns a value to the caller
        return 1 - (float) Math.pow(-2 * x + 2, 2) / 2;
    // End of a block/expression
    }

    // Start of a method/block
    public static float inCubic(float x) {
        // Returns a value to the caller
        return x * x * x;
    // End of a block/expression
    }

    // Start of a method/block
    public static float outCubic(float x) {
        // Returns a value to the caller
        return 1 - (float) Math.pow(1 - x, 3);
    // End of a block/expression
    }

    // Start of a method/block
    public static float inOutCubic(float x) {
        // Branch: checks a condition
        if (x < 0.5) return 4 * x * x * x;
        // Returns a value to the caller
        return 1 - (float) Math.pow(-2 * x + 2, 3) / 2;
    // End of a block/expression
    }

    // Start of a method/block
    public static float inQuart(float x) {
        // Returns a value to the caller
        return x * x * x * x;
    // End of a block/expression
    }

    // Start of a method/block
    public static float outQuart(float x) {
        // Returns a value to the caller
        return 1 - (float) Math.pow(1 - x, 4);
    // End of a block/expression
    }

    // Start of a method/block
    public static float inOutQuart(float x) {
        // Branch: checks a condition
        if (x < 0.5) return 8 * x * x * x * x;
        // Returns a value to the caller
        return 1 - (float) Math.pow(-2 * x + 2, 4) / 2;
    // End of a block/expression
    }

    // Start of a method/block
    public static float inQuint(float x) {
        // Returns a value to the caller
        return x * x * x * x * x;
    // End of a block/expression
    }

    // Start of a method/block
    public static float outQuint(float x) {
        // Returns a value to the caller
        return 1 - (float) Math.pow(1 - x, 5);
    // End of a block/expression
    }

    // Start of a method/block
    public static float inOutQuint(float x) {
        // Branch: checks a condition
        if (x < 0.5) return 16 * x * x * x * x * x;
        // Returns a value to the caller
        return 1 - (float) Math.pow(-2 * x + 2, 5) / 2;
    // End of a block/expression
    }

    // Start of a method/block
    public static float inSine(float x) {
        // Returns a value to the caller
        return 1 - (float) Math.cos((x * Math.PI) / 2);
    // End of a block/expression
    }

    // Start of a method/block
    public static float outSine(float x) {
        // Returns a value to the caller
        return (float) Math.sin((x * Math.PI) / 2);
    // End of a block/expression
    }

    // Start of a method/block
    public static float inOutSine(float x) {
        // Returns a value to the caller
        return (float) -(Math.cos(Math.PI * x) - 1) / 2;
    // End of a block/expression
    }

    // Start of a method/block
    public static float inExpo(float x) {
        // Branch: checks a condition
        if (x == 0) return 0;
        // Returns a value to the caller
        return (float) Math.pow(2, 10 * x - 10);
    // End of a block/expression
    }

    // Start of a method/block
    public static float outExpo(float x) {
        // Branch: checks a condition
        if (x == 1) return 1;
        // Returns a value to the caller
        return 1 - (float) Math.pow(2, -10 * x);
    // End of a block/expression
    }

    // Start of a method/block
    public static float inOutExpo(float x) {
        // Branch: checks a condition
        if (x == 0) return 0;
        // Branch: checks a condition
        if (x == 1) return 1;
        // Branch: checks a condition
        if (x < 0.5) return (float) Math.pow(2, 20 * x - 10) / 2;
        // Returns a value to the caller
        return (2 - (float) Math.pow(2, -20 * x + 10)) / 2;
    // End of a block/expression
    }

    // Start of a method/block
    public static float inCirc(float x) {
        // Returns a value to the caller
        return 1 - (float) Math.sqrt(1 - Math.pow(x, 2));
    // End of a block/expression
    }

    // Start of a method/block
    public static float outCirc(float x) {
        // Returns a value to the caller
        return (float) Math.sqrt(1 - Math.pow(x - 1, 2));
    // End of a block/expression
    }

    // Start of a method/block
    public static float inOutCirc(float x) {
        // Branch: checks a condition
        if (x < 0.5) return (float) (1 - Math.sqrt(1 - Math.pow(2 * x, 2))) / 2;
        // Returns a value to the caller
        return (float) (Math.sqrt(1 - Math.pow(-2 * x + 2, 2)) + 1) / 2;
    // End of a block/expression
    }

    // Start of a method/block
    public static float inBack(float x) {
        // Returns a value to the caller
        return c3 * x * x * x - c1 * x * x;
    // End of a block/expression
    }

    // Start of a method/block
    public static float outBack(float x) {
        // Returns a value to the caller
        return 1 + c3 * (float) Math.pow(x - 1, 3) + c1 * (float) Math.pow(x - 1, 2);
    // End of a block/expression
    }

    // Start of a method/block
    public static float inOutBack(float x) {
        // Returns a value to the caller
        return 1 + c3 * (float) Math.pow(x - 1, 3) + c1 * (float) Math.pow(x - 1, 2);
    // End of a block/expression
    }

    // Start of a method/block
    public static float inElastic(float x) {
        // Branch: checks a condition
        if (x == 0) return 0;
        // Branch: checks a condition
        if (x == 1) return 1;
        // Returns a value to the caller
        return (float) (-Math.pow(2, 10 * x - 10) * Math.sin((x * 10 - 10.75) * c4));
    // End of a block/expression
    }

    // Start of a method/block
    public static float outElastic(float x) {
        // Branch: checks a condition
        if (x == 0) return 0;
        // Branch: checks a condition
        if (x == 1) return 1;
        // Returns a value to the caller
        return (float) Math.pow(2, -10 * x) * (float) Math.sin((x * 10 - 0.75) * c4) + 1;
    // End of a block/expression
    }

    // Start of a method/block
    public static float inOutElastic(float x) {
        // Branch: checks a condition
        if (x == 0) return 0;
        // Branch: checks a condition
        if (x == 1) return 1;
        // Branch: checks a condition
        if (x < 0.5) return (float) -(Math.pow(2, 20 * x - 10) * Math.sin((20 * x - 11.125) * c5)) / 2;
        // Returns a value to the caller
        return (float) (Math.pow(2, -20 * x + 10) * Math.sin((20 * x - 11.125) * c5)) / 2 + 1;
    // End of a block/expression
    }

    // Start of a method/block
    public static float inBounce(float x) {
        // Returns a value to the caller
        return 1 - outBounce(1 - x);
    // End of a block/expression
    }

    // Start of a method/block
    public static float outBounce(float x) {
        // Assigns a value
        float n1 = 7.5625f;
        // Assigns a value
        float d1 = 2.75f;

        // Branch: checks a condition
        if (x < 1 / d1) {
            // Returns a value to the caller
            return n1 * x * x;
        // Branch: checks a condition
        } else if (x < 2 / d1) {
            // Code statement
            x -= 1.5f / d1;
            // Returns a value to the caller
            return n1 * x * x + 0.75f;
        // Branch: checks a condition
        } else if (x < 2.5 / d1) {
            // Code statement
            x -= 2.25f / d1;
            // Returns a value to the caller
            return n1 * x * x + 0.9375f;
        // Alternative branch of the condition
        } else {
            // Code statement
            x -= 2.625f / d1;
            // Returns a value to the caller
            return n1 * x * x + 0.984375f;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static float inOutBounce(float x) {
        // Branch: checks a condition
        if (x < 0.5) return (1 - outBounce(1 - 2 * x)) / 2;
        // Returns a value to the caller
        return (1 + outBounce(2 * x - 1)) / 2;
    // End of a block/expression
    }

// End of a block/expression
}
