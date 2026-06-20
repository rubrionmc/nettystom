// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Déclaration de type (classe/interface/enum/record)
public class RelativeFlags {
    // Affecte une valeur
    public static final int NONE = 0x00;

    // Affecte une valeur
    public static final int X = 0x01;
    // Affecte une valeur
    public static final int Y = 0x02;
    // Affecte une valeur
    public static final int Z = 0x04;

    // Affecte une valeur
    public static final int YAW = 0x08;
    // Affecte une valeur
    public static final int PITCH = 0x10;

    // Affecte une valeur
    public static final int DELTA_X = 0x20;
    // Affecte une valeur
    public static final int DELTA_Y = 0x40;
    // Affecte une valeur
    public static final int DELTA_Z = 0x80;
    // Affecte une valeur
    public static final int ROTATE_DELTA = 0x100;

    // Affecte une valeur
    public static final int COORD = X | Y | Z;
    // Affecte une valeur
    public static final int VIEW = YAW | PITCH;
    // Affecte une valeur
    public static final int DELTA_COORD = DELTA_X | DELTA_Y | DELTA_Z;
    // Affecte une valeur
    public static final int DELTA = DELTA_COORD | ROTATE_DELTA;
    // Affecte une valeur
    public static final int ALL = COORD | VIEW | DELTA;
// Fin d'un bloc/d'une expression
}