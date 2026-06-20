// Package declaration for this file
package net.minestom.server.collision;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;

// Type declaration (class/interface/enum/record)
final class RayUtils {
    /**
     * Check if a bounding box intersects a ray
     *
     * @param rayStart         Ray start position
     * @param rayDirection     Ray to check
     * @param collidableStatic Bounding box
     * @param finalResult
     * @return true if an intersection between the ray and the bounding box was found
     */
    // Start of a method/block
    public static boolean BoundingBoxIntersectionCheck(BoundingBox moving, Point rayStart, Point rayDirection, BoundingBox collidableStatic, Point staticCollidableOffset, SweepResult finalResult) {
        // Calls a method
        final double halfWidth = moving.width() / 2;
        // Calls a method
        final double halfHeight = moving.height() / 2;
        // Calls a method
        final double halfDepth = moving.depth() / 2;

        // Calls a method
        final double rayCentreX = rayStart.x() + moving.minX() + halfWidth;
        // Calls a method
        final double rayCentreY = rayStart.y() + moving.minY() + halfHeight;
        // Calls a method
        final double rayCentreZ = rayStart.z() + moving.minZ() + halfDepth;

        // Calls a method
        final double rayDirX = rayDirection.x();
        // Calls a method
        final double rayDirY = rayDirection.y();
        // Calls a method
        final double rayDirZ = rayDirection.z();

        // Static box bounds in world space (offset by the shape position)
        // Calls a method
        final double staticMinX = collidableStatic.minX() + staticCollidableOffset.x();
        // Calls a method
        final double staticMinY = collidableStatic.minY() + staticCollidableOffset.y();
        // Calls a method
        final double staticMinZ = collidableStatic.minZ() + staticCollidableOffset.z();
        // Calls a method
        final double staticMaxX = collidableStatic.maxX() + staticCollidableOffset.x();
        // Calls a method
        final double staticMaxY = collidableStatic.maxY() + staticCollidableOffset.y();
        // Calls a method
        final double staticMaxZ = collidableStatic.maxZ() + staticCollidableOffset.z();

        // Expanded (Minkowski) bounds relative to the ray centre
        // Assigns a value
        final double bbOffMinX = staticMinX - rayCentreX - halfWidth;
        // Assigns a value
        final double bbOffMinY = staticMinY - rayCentreY - halfHeight;
        // Assigns a value
        final double bbOffMinZ = staticMinZ - rayCentreZ - halfDepth;
        // Assigns a value
        final double bbOffMaxX = staticMaxX - rayCentreX + halfWidth;
        // Assigns a value
        final double bbOffMaxY = staticMaxY - rayCentreY + halfHeight;
        // Assigns a value
        final double bbOffMaxZ = staticMaxZ - rayCentreZ + halfDepth;

        // This check is done in 2d. it can be visualised as a rectangle (the face we are checking), and a point.
        // If the point is within the rectangle, we know the vector intersects the face.

        // Calls a method
        double signumRayX = Math.signum(rayDirX);
        // Calls a method
        double signumRayY = Math.signum(rayDirY);
        // Calls a method
        double signumRayZ = Math.signum(rayDirZ);

        // Assigns a value
        boolean isHit = false;
        // Assigns a value
        double percentage = Double.MAX_VALUE;
        // Assigns a value
        int collisionFace = -1;

        // Intersect X
        // Left side of bounding box
        // Branch: checks a condition
        if (rayDirX > 0) {
            // Calls a method
            double xFac = epsilon(bbOffMinX / rayDirX);
            // Branch: checks a condition
            if (xFac < percentage) {
                // Assigns a value
                double yix = rayDirY * xFac + rayCentreY;
                // Assigns a value
                double zix = rayDirZ * xFac + rayCentreZ;

                // Check if ray passes through y/z plane
                // Branch: checks a condition
                if (((yix - rayCentreY) * signumRayY) >= 0
                        // Code statement
                        && ((zix - rayCentreZ) * signumRayZ) >= 0
                        // Code statement
                        && yix >= staticMinY - halfHeight
                        // Code statement
                        && yix <= staticMaxY + halfHeight
                        // Code statement
                        && zix >= staticMinZ - halfDepth
                        // Start of a method/block
                        && zix <= staticMaxZ + halfDepth) {
                    // Assigns a value
                    isHit = true;
                    // Assigns a value
                    percentage = xFac;
                    // Assigns a value
                    collisionFace = 0;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Right side of bounding box
        // Branch: checks a condition
        if (rayDirX < 0) {
            // Calls a method
            double xFac = epsilon(bbOffMaxX / rayDirX);
            // Branch: checks a condition
            if (xFac < percentage) {
                // Assigns a value
                double yix = rayDirY * xFac + rayCentreY;
                // Assigns a value
                double zix = rayDirZ * xFac + rayCentreZ;

                // Branch: checks a condition
                if (((yix - rayCentreY) * signumRayY) >= 0
                        // Code statement
                        && ((zix - rayCentreZ) * signumRayZ) >= 0
                        // Code statement
                        && yix >= staticMinY - halfHeight
                        // Code statement
                        && yix <= staticMaxY + halfHeight
                        // Code statement
                        && zix >= staticMinZ - halfDepth
                        // Start of a method/block
                        && zix <= staticMaxZ + halfDepth) {
                    // Assigns a value
                    isHit = true;
                    // Assigns a value
                    percentage = xFac;
                    // Assigns a value
                    collisionFace = 0;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Intersect Z
        // Branch: checks a condition
        if (rayDirZ > 0) {
            // Calls a method
            double zFac = epsilon(bbOffMinZ / rayDirZ);
            // Branch: checks a condition
            if (zFac < percentage) {
                // Assigns a value
                double xiz = rayDirX * zFac + rayCentreX;
                // Assigns a value
                double yiz = rayDirY * zFac + rayCentreY;

                // Branch: checks a condition
                if (((yiz - rayCentreY) * signumRayY) >= 0
                        // Code statement
                        && ((xiz - rayCentreX) * signumRayX) >= 0
                        // Code statement
                        && xiz >= staticMinX - halfWidth
                        // Code statement
                        && xiz <= staticMaxX + halfWidth
                        // Code statement
                        && yiz >= staticMinY - halfHeight
                        // Start of a method/block
                        && yiz <= staticMaxY + halfHeight) {
                    // Assigns a value
                    isHit = true;
                    // Assigns a value
                    percentage = zFac;
                    // Assigns a value
                    collisionFace = 1;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Branch: checks a condition
        if (rayDirZ < 0) {
            // Calls a method
            double zFac = epsilon(bbOffMaxZ / rayDirZ);
            // Branch: checks a condition
            if (zFac < percentage) {
                // Assigns a value
                double xiz = rayDirX * zFac + rayCentreX;
                // Assigns a value
                double yiz = rayDirY * zFac + rayCentreY;

                // Branch: checks a condition
                if (((yiz - rayCentreY) * signumRayY) >= 0
                        // Code statement
                        && ((xiz - rayCentreX) * signumRayX) >= 0
                        // Code statement
                        && xiz >= staticMinX - halfWidth
                        // Code statement
                        && xiz <= staticMaxX + halfWidth
                        // Code statement
                        && yiz >= staticMinY - halfHeight
                        // Start of a method/block
                        && yiz <= staticMaxY + halfHeight) {
                    // Assigns a value
                    isHit = true;
                    // Assigns a value
                    percentage = zFac;
                    // Assigns a value
                    collisionFace = 1;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Intersect Y
        // Branch: checks a condition
        if (rayDirY > 0) {
            // Calls a method
            double yFac = epsilon(bbOffMinY / rayDirY);
            // Branch: checks a condition
            if (yFac < percentage) {
                // Assigns a value
                double xiy = rayDirX * yFac + rayCentreX;
                // Assigns a value
                double ziy = rayDirZ * yFac + rayCentreZ;

                // Branch: checks a condition
                if (((ziy - rayCentreZ) * signumRayZ) >= 0
                        // Code statement
                        && ((xiy - rayCentreX) * signumRayX) >= 0
                        // Code statement
                        && xiy >= staticMinX - halfWidth
                        // Code statement
                        && xiy <= staticMaxX + halfWidth
                        // Code statement
                        && ziy >= staticMinZ - halfDepth
                        // Start of a method/block
                        && ziy <= staticMaxZ + halfDepth) {
                    // Assigns a value
                    isHit = true;
                    // Assigns a value
                    percentage = yFac;
                    // Assigns a value
                    collisionFace = 2;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Branch: checks a condition
        if (rayDirY < 0) {
            // Calls a method
            double yFac = epsilon(bbOffMaxY / rayDirY);
            // Branch: checks a condition
            if (yFac < percentage) {
                // Assigns a value
                double xiy = rayDirX * yFac + rayCentreX;
                // Assigns a value
                double ziy = rayDirZ * yFac + rayCentreZ;

                // Branch: checks a condition
                if (((ziy - rayCentreZ) * signumRayZ) >= 0
                        // Code statement
                        && ((xiy - rayCentreX) * signumRayX) >= 0
                        // Code statement
                        && xiy >= staticMinX - halfWidth
                        // Code statement
                        && xiy <= staticMaxX + halfWidth
                        // Code statement
                        && ziy >= staticMinZ - halfDepth
                        // Start of a method/block
                        && ziy <= staticMaxZ + halfDepth) {
                    // Assigns a value
                    isHit = true;
                    // Assigns a value
                    percentage = yFac;
                    // Assigns a value
                    collisionFace = 2;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Code statement
        percentage *= 0.99999;

        // Branch: checks a condition
        if (isHit && percentage >= 0 && percentage <= finalResult.res) {
            // Assigns a value
            finalResult.res = percentage;
            // Assigns a value
            finalResult.normalX = 0;
            // Assigns a value
            finalResult.normalY = 0;
            // Assigns a value
            finalResult.normalZ = 0;

            // Branch: checks a condition
            if (collisionFace == 0) finalResult.normalX = 1;
            // Branch: checks a condition
            if (collisionFace == 1) finalResult.normalZ = 1;
            // Branch: checks a condition
            if (collisionFace == 2) finalResult.normalY = 1;

            // Returns a value to the caller
            return true;
        // End of a block/expression
        }

        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    // Start of a method/block
    private static double epsilon(double value) {
        // Returns a value to the caller
        return Math.abs(value) < Vec.EPSILON ? 0 : value;
    // End of a block/expression
    }

    // Start of a method/block
    public static boolean BoundingBoxRayIntersectionCheck(Vec start, Vec direction, BoundingBox boundingBox, Pos position) {
        // Returns a value to the caller
        return BoundingBoxIntersectionCheck(BoundingBox.ZERO, start, direction, boundingBox, position, new SweepResult(Double.MAX_VALUE, 0, 0, 0, null, 0, 0, 0, 0, 0, 0));
    // End of a block/expression
    }
// End of a block/expression
}
