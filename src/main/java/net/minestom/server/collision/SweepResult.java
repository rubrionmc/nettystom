// Déclaration du paquet de ce fichier
package net.minestom.server.collision;

// Déclaration de type (classe/interface/enum/record)
public final class SweepResult {
    // Appelle une méthode
    public static final SweepResult NO_COLLISION  = new SweepResult(Double.MAX_VALUE, 0, 0, 0, null, 0, 0, 0, 0, 0, 0);

    // Instruction de code
    double res;
    // Instruction de code
    double normalX, normalY, normalZ;
    // Instruction de code
    double collidedPositionX, collidedPositionY, collidedPositionZ;
    // Instruction de code
    double collidedShapeX, collidedShapeY, collidedShapeZ;
    // Instruction de code
    Shape collidedShape;

    /**
     * Store the result of a movement operation
     *
     * @param res     Percentage of move completed
     * @param normalX -1 if intersected on left, 1 if intersected on right
     * @param normalY -1 if intersected on bottom, 1 if intersected on top
     * @param normalZ -1 if intersected on front, 1 if intersected on back
     */
    // Début d'une méthode/d'un bloc
    public SweepResult(double res, double normalX, double normalY, double normalZ, Shape collidedShape, double collidedPosX, double collidedPosY, double collidedPosZ, double collidedShapeX, double collidedShapeY, double collidedShapeZ) {
        // Accès à l'objet courant/parent
        this.res = res;
        // Accès à l'objet courant/parent
        this.normalX = normalX;
        // Accès à l'objet courant/parent
        this.normalY = normalY;
        // Accès à l'objet courant/parent
        this.normalZ = normalZ;
        // Accès à l'objet courant/parent
        this.collidedShape = collidedShape;
        // Accès à l'objet courant/parent
        this.collidedPositionX = collidedPosX;
        // Accès à l'objet courant/parent
        this.collidedPositionY = collidedPosY;
        // Accès à l'objet courant/parent
        this.collidedPositionZ = collidedPosZ;
        // Accès à l'objet courant/parent
        this.collidedShapeX = collidedShapeX;
        // Accès à l'objet courant/parent
        this.collidedShapeY = collidedShapeY;
        // Accès à l'objet courant/parent
        this.collidedShapeZ = collidedShapeZ;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
