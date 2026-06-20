// Package declaration for this file
package net.minestom.server.entity;

// Type declaration (class/interface/enum/record)
public final class RelativeFlags {
    // Assigns a value
    public static final int NONE = 0x00;

    // Assigns a value
    public static final int X = 0x01;
    // Assigns a value
    public static final int Y = 0x02;
    // Assigns a value
    public static final int Z = 0x04;

    // Assigns a value
    public static final int YAW = 0x08;
    // Assigns a value
    public static final int PITCH = 0x10;

    // Assigns a value
    public static final int DELTA_X = 0x20;
    // Assigns a value
    public static final int DELTA_Y = 0x40;
    // Assigns a value
    public static final int DELTA_Z = 0x80;
    // Assigns a value
    public static final int ROTATE_DELTA = 0x100;

    // Assigns a value
    public static final int COORD = X | Y | Z;
    // Assigns a value
    public static final int VIEW = YAW | PITCH;
    // Assigns a value
    public static final int DELTA_COORD = DELTA_X | DELTA_Y | DELTA_Z;
    // Assigns a value
    public static final int DELTA = DELTA_COORD | ROTATE_DELTA;
    // Assigns a value
    public static final int ALL = COORD | VIEW | DELTA;
// End of a block/expression
}