// Package declaration for this file
package net.minestom.server.collision;

// Type declaration (class/interface/enum/record)
public final class SweepResult {
    // Calls a method
    public static final SweepResult NO_COLLISION  = new SweepResult(Double.MAX_VALUE, 0, 0, 0, null, 0, 0, 0, 0, 0, 0);

    // Code statement
    double res;
    // Code statement
    double normalX, normalY, normalZ;
    // Code statement
    double collidedPositionX, collidedPositionY, collidedPositionZ;
    // Code statement
    double collidedShapeX, collidedShapeY, collidedShapeZ;
    // Code statement
    Shape collidedShape;

    /**
     * Store the result of a movement operation
     *
     * @param res     Percentage of move completed
     * @param normalX -1 if intersected on left, 1 if intersected on right
     * @param normalY -1 if intersected on bottom, 1 if intersected on top
     * @param normalZ -1 if intersected on front, 1 if intersected on back
     */
    // Start of a method/block
    public SweepResult(double res, double normalX, double normalY, double normalZ, Shape collidedShape, double collidedPosX, double collidedPosY, double collidedPosZ, double collidedShapeX, double collidedShapeY, double collidedShapeZ) {
        // Access to the current/parent object
        this.res = res;
        // Access to the current/parent object
        this.normalX = normalX;
        // Access to the current/parent object
        this.normalY = normalY;
        // Access to the current/parent object
        this.normalZ = normalZ;
        // Access to the current/parent object
        this.collidedShape = collidedShape;
        // Access to the current/parent object
        this.collidedPositionX = collidedPosX;
        // Access to the current/parent object
        this.collidedPositionY = collidedPosY;
        // Access to the current/parent object
        this.collidedPositionZ = collidedPosZ;
        // Access to the current/parent object
        this.collidedShapeX = collidedShapeX;
        // Access to the current/parent object
        this.collidedShapeY = collidedShapeY;
        // Access to the current/parent object
        this.collidedShapeZ = collidedShapeZ;
    // End of a block/expression
    }
// End of a block/expression
}
