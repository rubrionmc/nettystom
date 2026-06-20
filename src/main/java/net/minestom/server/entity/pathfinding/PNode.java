// Déclaration du paquet de ce fichier
package net.minestom.server.entity.pathfinding;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class PNode {
    // Déclaration de type (classe/interface/enum/record)
    public enum Type {
        // Instruction de code
        WALK,
        // Instruction de code
        JUMP,
        // Instruction de code
        FALL,
        // Instruction de code
        CLIMB,
        // Instruction de code
        CLIMB_WALL,
        // Instruction de code
        SWIM,
        // Instruction de code
        FLY, REPATH
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private double g;
    // Instruction de code
    private double h;
    // Instruction de code
    private PNode parent;
    // Instruction de code
    private double pointX;
    // Instruction de code
    private double pointY;
    // Instruction de code
    private double pointZ;
    // Instruction de code
    private int hashCode;

    // Instruction de code
    private Type type;

    // Début d'une méthode/d'un bloc
    public PNode(double px, double py, double pz, double g, double h, @Nullable PNode parent) {
        // Appelle une méthode
        this(px, py, pz, g, h, Type.WALK, parent);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public PNode(double px, double py, double pz, double g, double h, PNode.Type type, @Nullable PNode parent) {
        // Accès à l'objet courant/parent
        this.g = g;
        // Accès à l'objet courant/parent
        this.h = h;
        // Accès à l'objet courant/parent
        this.parent = parent;
        // Accès à l'objet courant/parent
        this.type = type;
        // Accès à l'objet courant/parent
        this.pointX = px;
        // Accès à l'objet courant/parent
        this.pointY = py;
        // Accès à l'objet courant/parent
        this.pointZ = pz;
        // Accès à l'objet courant/parent
        this.hashCode = cantor((int) Math.floor(px), cantor((int) Math.floor(py), (int) Math.floor(pz)));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public PNode(Point point, double g, double h, Type walk, @Nullable PNode parent) {
        // Appelle une méthode
        this(point.x(), point.y(), point.z(), g, h, walk, parent);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Renvoie une valeur à l'appelant
        return hashCode;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object obj) {
        // Embranchement : vérifie une condition
        if (obj == null) return false;
        // Embranchement : vérifie une condition
        if (obj == this) return true;
        // Embranchement : vérifie une condition
        if (!(obj instanceof PNode other)) return false;
        // Renvoie une valeur à l'appelant
        return this.hashCode == other.hashCode;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return "PNode{" +
                // Instruction de code
                "point=" + pointX + ", " + pointY + ", " + pointZ +
                // Instruction de code
                ", d=" + (g + h) +
                // Instruction de code
                ", type=" + type +
                // Instruction de code
                '}';
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public double x() {
        // Renvoie une valeur à l'appelant
        return pointX;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public double y() {
        // Renvoie une valeur à l'appelant
        return pointY;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public double z() {
        // Renvoie une valeur à l'appelant
        return pointZ;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int blockX() {
        // Renvoie une valeur à l'appelant
        return (int) Math.floor(pointX);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int blockY() {
        // Renvoie une valeur à l'appelant
        return (int) Math.floor(pointY);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int blockZ() {
        // Renvoie une valeur à l'appelant
        return (int) Math.floor(pointZ);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public Type getType() {
        // Renvoie une valeur à l'appelant
        return type;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public double g() {
        // Renvoie une valeur à l'appelant
        return g;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public double h() {
        // Renvoie une valeur à l'appelant
        return h;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void setG(double v) {
        // Accès à l'objet courant/parent
        this.g = v;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void setH(double heuristic) {
        // Accès à l'objet courant/parent
        this.h = heuristic;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void setType(PNode.Type newType) {
        // Accès à l'objet courant/parent
        this.type = newType;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void setPoint(double px, double py, double pz) {
        // Accès à l'objet courant/parent
        this.pointX = px;
        // Accès à l'objet courant/parent
        this.pointY = py;
        // Accès à l'objet courant/parent
        this.pointZ = pz;
        // Accès à l'objet courant/parent
        this.hashCode = cantor((int) Math.floor(px), cantor((int) Math.floor(py), (int) Math.floor(pz)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public @Nullable PNode parent() {
        // Renvoie une valeur à l'appelant
        return parent;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void setParent(@Nullable PNode current) {
        // Accès à l'objet courant/parent
        this.parent = current;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static int cantor(int a, int b) {
        // Affecte une valeur
        int ca = a >= 0 ? 2 * a : -2 * a - 1;
        // Affecte une valeur
        int cb = b >= 0 ? 2 * b : -2 * b - 1;
        // Renvoie une valeur à l'appelant
        return (ca + cb + 1) * (ca + cb) / 2 + cb;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
