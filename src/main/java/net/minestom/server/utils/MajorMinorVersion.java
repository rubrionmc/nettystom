// Package declaration for this file
package net.minestom.server.utils;

// Type declaration (class/interface/enum/record)
public record MajorMinorVersion(int major, int minor) {

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return major + "." + minor;
    // End of a block/expression
    }
// End of a block/expression
}
