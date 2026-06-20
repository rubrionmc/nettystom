// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

/**
 * Represents the base for any data type that is numeric.
 *
 * @param <T> The type numeric of the range object.
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface Range<T extends Number> {
    // Déclaration de type (classe/interface/enum/record)
    record Byte(byte min, byte max) implements Range<java.lang.Byte> {
        // Début d'une méthode/d'un bloc
        public Byte(byte value) {
            // Appelle une méthode
            this(value, value);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean inRange(byte value) {
            // Renvoie une valeur à l'appelant
            return value >= min && value <= max;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Short(short min, short max) implements Range<java.lang.Short> {
        // Début d'une méthode/d'un bloc
        public Short(short value) {
            // Appelle une méthode
            this(value, value);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean inRange(short value) {
            // Renvoie une valeur à l'appelant
            return value >= min && value <= max;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Int(int min, int max) implements Range<java.lang.Integer> {
        // Début d'une méthode/d'un bloc
        public Int(int value) {
            // Appelle une méthode
            this(value, value);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean inRange(int value) {
            // Renvoie une valeur à l'appelant
            return value >= min && value <= max;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Long(long min, long max) implements Range<java.lang.Long> {
        // Début d'une méthode/d'un bloc
        public Long(long value) {
            // Appelle une méthode
            this(value, value);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean inRange(long value) {
            // Renvoie une valeur à l'appelant
            return value >= min && value <= max;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Float(float min, float max) implements Range<java.lang.Float> {
        // Début d'une méthode/d'un bloc
        public Float(float value) {
            // Appelle une méthode
            this(value, value);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean inRange(float value) {
            // Renvoie une valeur à l'appelant
            return value >= min && value <= max;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Double(double min, double max) implements Range<java.lang.Double> {
        // Début d'une méthode/d'un bloc
        public Double(double value) {
            // Appelle une méthode
            this(value, value);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean inRange(double value) {
            // Renvoie une valeur à l'appelant
            return value >= min && value <= max;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
