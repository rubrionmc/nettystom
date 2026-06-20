// Package declaration for this file
package net.minestom.server.utils;

/**
 * Represents the base for any data type that is numeric.
 *
 * @param <T> The type numeric of the range object.
 */
// Type declaration (class/interface/enum/record)
public sealed interface Range<T extends Number> {
    // Type declaration (class/interface/enum/record)
    record Byte(byte min, byte max) implements Range<java.lang.Byte> {
        // Start of a method/block
        public Byte(byte value) {
            // Calls a method
            this(value, value);
        // End of a block/expression
        }

        // Start of a method/block
        public boolean inRange(byte value) {
            // Returns a value to the caller
            return value >= min && value <= max;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Short(short min, short max) implements Range<java.lang.Short> {
        // Start of a method/block
        public Short(short value) {
            // Calls a method
            this(value, value);
        // End of a block/expression
        }

        // Start of a method/block
        public boolean inRange(short value) {
            // Returns a value to the caller
            return value >= min && value <= max;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Int(int min, int max) implements Range<java.lang.Integer> {
        // Start of a method/block
        public Int(int value) {
            // Calls a method
            this(value, value);
        // End of a block/expression
        }

        // Start of a method/block
        public boolean inRange(int value) {
            // Returns a value to the caller
            return value >= min && value <= max;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Long(long min, long max) implements Range<java.lang.Long> {
        // Start of a method/block
        public Long(long value) {
            // Calls a method
            this(value, value);
        // End of a block/expression
        }

        // Start of a method/block
        public boolean inRange(long value) {
            // Returns a value to the caller
            return value >= min && value <= max;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Float(float min, float max) implements Range<java.lang.Float> {
        // Start of a method/block
        public Float(float value) {
            // Calls a method
            this(value, value);
        // End of a block/expression
        }

        // Start of a method/block
        public boolean inRange(float value) {
            // Returns a value to the caller
            return value >= min && value <= max;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Double(double min, double max) implements Range<java.lang.Double> {
        // Start of a method/block
        public Double(double value) {
            // Calls a method
            this(value, value);
        // End of a block/expression
        }

        // Start of a method/block
        public boolean inRange(double value) {
            // Returns a value to the caller
            return value >= min && value <= max;
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
