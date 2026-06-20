// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class MathUtils {

    // Début d'une méthode/d'un bloc
    private MathUtils() {
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int square(int num) {
        // Renvoie une valeur à l'appelant
        return num * num;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float square(float num) {
        // Renvoie une valeur à l'appelant
        return num * num;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static double square(double num) {
        // Renvoie une valeur à l'appelant
        return num * num;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static double round(double value, int places) {
        // Embranchement : vérifie une condition
        if (places < 0) throw new IllegalArgumentException();

        // Appelle une méthode
        final long factor = (long) Math.pow(10, places);
        // Affecte une valeur
        value = value * factor;
        // Appelle une méthode
        long tmp = Math.round(value);
        // Renvoie une valeur à l'appelant
        return (double) tmp / factor;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float round(float value, int places) {
        // Embranchement : vérifie une condition
        if (places < 0) throw new IllegalArgumentException();

        // Appelle une méthode
        final long factor = (long) Math.pow(10, places);
        // Affecte une valeur
        value = value * factor;
        // Appelle une méthode
        long tmp = Math.round(value);
        // Renvoie une valeur à l'appelant
        return (float) tmp / factor;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Direction getHorizontalDirection(float yawInDegrees) {
        // +45f gives a 90° angle for the direction (-1° and 1° are towards the same direction)
        // Appelle une méthode
        int directionIndex = (int) Math.floor(((yawInDegrees + 45f) / 90f));
        // Embranchement : vérifie une condition
        if (directionIndex < 0) {
            // Affecte une valeur
            directionIndex = (-directionIndex) % Direction.HORIZONTAL.length;
            // Affecte une valeur
            directionIndex = Direction.HORIZONTAL.length - directionIndex;
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        directionIndex %= Direction.HORIZONTAL.length;
        // Renvoie une valeur à l'appelant
        return Direction.HORIZONTAL[directionIndex];
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static boolean isBetween(byte number, byte min, byte max) {
        // Renvoie une valeur à l'appelant
        return number >= min && number <= max;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static boolean isBetween(int number, int min, int max) {
        // Renvoie une valeur à l'appelant
        return number >= min && number <= max;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static boolean isBetween(double number, double min, double max) {
        // Renvoie une valeur à l'appelant
        return number >= min && number <= max;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static boolean isBetween(float number, float min, float max) {
        // Renvoie une valeur à l'appelant
        return number >= min && number <= max;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static boolean isBetweenUnordered(double number, double compare1, double compare2) {
        // Embranchement : vérifie une condition
        if (compare1 > compare2) {
            // Renvoie une valeur à l'appelant
            return isBetween(number, compare2, compare1);
        // Branche alternative de la condition
        } else {
            // Renvoie une valeur à l'appelant
            return isBetween(number, compare1, compare2);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static boolean isBetweenUnordered(float number, float compare1, float compare2) {
        // Embranchement : vérifie une condition
        if (compare1 > compare2) {
            // Renvoie une valeur à l'appelant
            return isBetween(number, compare2, compare1);
        // Branche alternative de la condition
        } else {
            // Renvoie une valeur à l'appelant
            return isBetween(number, compare1, compare2);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int clamp(int value, int min, int max) {
        // Renvoie une valeur à l'appelant
        return Math.min(Math.max(value, min), max);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static float clamp(float value, float min, float max) {
        // Renvoie une valeur à l'appelant
        return Math.min(Math.max(value, min), max);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static double clamp(double value, double min, double max) {
        // Renvoie une valeur à l'appelant
        return Math.min(Math.max(value, min), max);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static double mod(final double a, final double b) {
        // Renvoie une valeur à l'appelant
        return (a % b + b) % b;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int bitsToRepresent(int n) {
        // Appelle une méthode
        Check.argCondition(n < 1, "n must be greater than 0");
        // Renvoie une valeur à l'appelant
        return Integer.SIZE - Integer.numberOfLeadingZeros(n);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static long ceilLong(double value) {
        // Affecte une valeur
        long i = (long) value;
        // Renvoie une valeur à l'appelant
        return value > i ? i + 1L : i;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static double absMax(double d0, double d1) {
        // Renvoie une valeur à l'appelant
        return Math.max(Math.abs(d0), Math.abs(d1));
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
