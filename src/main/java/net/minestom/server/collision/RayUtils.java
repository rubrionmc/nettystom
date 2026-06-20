// Déclaration du paquet de ce fichier
package net.minestom.server.collision;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;

// Déclaration de type (classe/interface/enum/record)
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
    // Début d'une méthode/d'un bloc
    public static boolean BoundingBoxIntersectionCheck(BoundingBox moving, Point rayStart, Point rayDirection, BoundingBox collidableStatic, Point staticCollidableOffset, SweepResult finalResult) {
        // Appelle une méthode
        Point bbCentre = new Vec(moving.minX() + moving.width() / 2, moving.minY() + moving.height() / 2, moving.minZ() + moving.depth() / 2);
        // Appelle une méthode
        Point rayCentre = rayStart.add(bbCentre);

        // Translate bounding box
        // Appelle une méthode
        Vec bbOffMin = new Vec(collidableStatic.minX() - rayCentre.x() + staticCollidableOffset.x() - moving.width() / 2, collidableStatic.minY() - rayCentre.y() + staticCollidableOffset.y() - moving.height() / 2, collidableStatic.minZ() - rayCentre.z() + staticCollidableOffset.z() - moving.depth() / 2);
        // Appelle une méthode
        Vec bbOffMax = new Vec(collidableStatic.maxX() - rayCentre.x() + staticCollidableOffset.x() + moving.width() / 2, collidableStatic.maxY() - rayCentre.y() + staticCollidableOffset.y() + moving.height() / 2, collidableStatic.maxZ() - rayCentre.z() + staticCollidableOffset.z() + moving.depth() / 2);

        // This check is done in 2d. it can be visualised as a rectangle (the face we are checking), and a point.
        // If the point is within the rectangle, we know the vector intersects the face.

        // Boucle : répète un bloc
        double signumRayX = Math.signum(rayDirection.x());
        // Boucle : répète un bloc
        double signumRayY = Math.signum(rayDirection.y());
        // Boucle : répète un bloc
        double signumRayZ = Math.signum(rayDirection.z());

        // Affecte une valeur
        boolean isHit = false;
        // Boucle : répète un bloc
        double percentage = Double.MAX_VALUE;
        // Affecte une valeur
        int collisionFace = -1;

        // Intersect X
        // Left side of bounding box
        // Embranchement : vérifie une condition
        if (rayDirection.x() > 0) {
            // Boucle : répète un bloc
            double xFac = epsilon(bbOffMin.x() / rayDirection.x());
            // Embranchement : vérifie une condition
            if (xFac < percentage) {
                // Boucle : répète un bloc
                double yix = rayDirection.y() * xFac + rayCentre.y();
                // Boucle : répète un bloc
                double zix = rayDirection.z() * xFac + rayCentre.z();

                // Check if ray passes through y/z plane
                // Embranchement : vérifie une condition
                if (((yix - rayCentre.y()) * signumRayY) >= 0
                        // Instruction de code
                        && ((zix - rayCentre.z()) * signumRayZ) >= 0
                        // Instruction de code
                        && yix >= collidableStatic.minY() + staticCollidableOffset.y() - moving.height() / 2
                        // Instruction de code
                        && yix <= collidableStatic.maxY() + staticCollidableOffset.y() + moving.height() / 2
                        // Instruction de code
                        && zix >= collidableStatic.minZ() + staticCollidableOffset.z() - moving.depth() / 2
                        // Début d'une méthode/d'un bloc
                        && zix <= collidableStatic.maxZ() + staticCollidableOffset.z() + moving.depth() / 2) {
                    // Affecte une valeur
                    isHit = true;
                    // Affecte une valeur
                    percentage = xFac;
                    // Affecte une valeur
                    collisionFace = 0;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Right side of bounding box
        // Embranchement : vérifie une condition
        if (rayDirection.x() < 0) {
            // Boucle : répète un bloc
            double xFac = epsilon(bbOffMax.x() / rayDirection.x());
            // Embranchement : vérifie une condition
            if (xFac < percentage) {
                // Boucle : répète un bloc
                double yix = rayDirection.y() * xFac + rayCentre.y();
                // Boucle : répète un bloc
                double zix = rayDirection.z() * xFac + rayCentre.z();

                // Embranchement : vérifie une condition
                if (((yix - rayCentre.y()) * signumRayY) >= 0
                        // Instruction de code
                        && ((zix - rayCentre.z()) * signumRayZ) >= 0
                        // Instruction de code
                        && yix >= collidableStatic.minY() + staticCollidableOffset.y() - moving.height() / 2
                        // Instruction de code
                        && yix <= collidableStatic.maxY() + staticCollidableOffset.y() + moving.height() / 2
                        // Instruction de code
                        && zix >= collidableStatic.minZ() + staticCollidableOffset.z() - moving.depth() / 2
                        // Début d'une méthode/d'un bloc
                        && zix <= collidableStatic.maxZ() + staticCollidableOffset.z() + moving.depth() / 2) {
                    // Affecte une valeur
                    isHit = true;
                    // Affecte une valeur
                    percentage = xFac;
                    // Affecte une valeur
                    collisionFace = 0;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Intersect Z
        // Embranchement : vérifie une condition
        if (rayDirection.z() > 0) {
            // Boucle : répète un bloc
            double zFac = epsilon(bbOffMin.z() / rayDirection.z());
            // Embranchement : vérifie une condition
            if (zFac < percentage) {
                // Boucle : répète un bloc
                double xiz = rayDirection.x() * zFac + rayCentre.x();
                // Boucle : répète un bloc
                double yiz = rayDirection.y() * zFac + rayCentre.y();

                // Embranchement : vérifie une condition
                if (((yiz - rayCentre.y()) * signumRayY) >= 0
                        // Instruction de code
                        && ((xiz - rayCentre.x()) * signumRayX) >= 0
                        // Instruction de code
                        && xiz >= collidableStatic.minX() + staticCollidableOffset.x() - moving.width() / 2
                        // Instruction de code
                        && xiz <= collidableStatic.maxX() + staticCollidableOffset.x() + moving.width() / 2
                        // Instruction de code
                        && yiz >= collidableStatic.minY() + staticCollidableOffset.y() - moving.height() / 2
                        // Début d'une méthode/d'un bloc
                        && yiz <= collidableStatic.maxY() + staticCollidableOffset.y() + moving.height() / 2) {
                    // Affecte une valeur
                    isHit = true;
                    // Affecte une valeur
                    percentage = zFac;
                    // Affecte une valeur
                    collisionFace = 1;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (rayDirection.z() < 0) {
            // Boucle : répète un bloc
            double zFac = epsilon(bbOffMax.z() / rayDirection.z());
            // Embranchement : vérifie une condition
            if (zFac < percentage) {
                // Boucle : répète un bloc
                double xiz = rayDirection.x() * zFac + rayCentre.x();
                // Boucle : répète un bloc
                double yiz = rayDirection.y() * zFac + rayCentre.y();

                // Embranchement : vérifie une condition
                if (((yiz - rayCentre.y()) * signumRayY) >= 0
                        // Instruction de code
                        && ((xiz - rayCentre.x()) * signumRayX) >= 0
                        // Instruction de code
                        && xiz >= collidableStatic.minX() + staticCollidableOffset.x() - moving.width() / 2
                        // Instruction de code
                        && xiz <= collidableStatic.maxX() + staticCollidableOffset.x() + moving.width() / 2
                        // Instruction de code
                        && yiz >= collidableStatic.minY() + staticCollidableOffset.y() - moving.height() / 2
                        // Début d'une méthode/d'un bloc
                        && yiz <= collidableStatic.maxY() + staticCollidableOffset.y() + moving.height() / 2) {
                    // Affecte une valeur
                    isHit = true;
                    // Affecte une valeur
                    percentage = zFac;
                    // Affecte une valeur
                    collisionFace = 1;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Intersect Y
        // Embranchement : vérifie une condition
        if (rayDirection.y() > 0) {
            // Boucle : répète un bloc
            double yFac = epsilon(bbOffMin.y() / rayDirection.y());
            // Embranchement : vérifie une condition
            if (yFac < percentage) {
                // Boucle : répète un bloc
                double xiy = rayDirection.x() * yFac + rayCentre.x();
                // Boucle : répète un bloc
                double ziy = rayDirection.z() * yFac + rayCentre.z();

                // Embranchement : vérifie une condition
                if (((ziy - rayCentre.z()) * signumRayZ) >= 0
                        // Instruction de code
                        && ((xiy - rayCentre.x()) * signumRayX) >= 0
                        // Instruction de code
                        && xiy >= collidableStatic.minX() + staticCollidableOffset.x() - moving.width() / 2
                        // Instruction de code
                        && xiy <= collidableStatic.maxX() + staticCollidableOffset.x() + moving.width() / 2
                        // Instruction de code
                        && ziy >= collidableStatic.minZ() + staticCollidableOffset.z() - moving.depth() / 2
                        // Début d'une méthode/d'un bloc
                        && ziy <= collidableStatic.maxZ() + staticCollidableOffset.z() + moving.depth() / 2) {
                    // Affecte une valeur
                    isHit = true;
                    // Affecte une valeur
                    percentage = yFac;
                    // Affecte une valeur
                    collisionFace = 2;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (rayDirection.y() < 0) {
            // Boucle : répète un bloc
            double yFac = epsilon(bbOffMax.y() / rayDirection.y());
            // Embranchement : vérifie une condition
            if (yFac < percentage) {
                // Boucle : répète un bloc
                double xiy = rayDirection.x() * yFac + rayCentre.x();
                // Boucle : répète un bloc
                double ziy = rayDirection.z() * yFac + rayCentre.z();

                // Embranchement : vérifie une condition
                if (((ziy - rayCentre.z()) * signumRayZ) >= 0
                        // Instruction de code
                        && ((xiy - rayCentre.x()) * signumRayX) >= 0
                        // Instruction de code
                        && xiy >= collidableStatic.minX() + staticCollidableOffset.x() - moving.width() / 2
                        // Instruction de code
                        && xiy <= collidableStatic.maxX() + staticCollidableOffset.x() + moving.width() / 2
                        // Instruction de code
                        && ziy >= collidableStatic.minZ() + staticCollidableOffset.z() - moving.depth() / 2
                        // Début d'une méthode/d'un bloc
                        && ziy <= collidableStatic.maxZ() + staticCollidableOffset.z() + moving.depth() / 2) {
                    // Affecte une valeur
                    isHit = true;
                    // Affecte une valeur
                    percentage = yFac;
                    // Affecte une valeur
                    collisionFace = 2;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        percentage *= 0.99999;

        // Embranchement : vérifie une condition
        if (isHit && percentage >= 0 && percentage <= finalResult.res) {
            // Affecte une valeur
            finalResult.res = percentage;
            // Affecte une valeur
            finalResult.normalX = 0;
            // Affecte une valeur
            finalResult.normalY = 0;
            // Affecte une valeur
            finalResult.normalZ = 0;

            // Embranchement : vérifie une condition
            if (collisionFace == 0) finalResult.normalX = 1;
            // Embranchement : vérifie une condition
            if (collisionFace == 1) finalResult.normalZ = 1;
            // Embranchement : vérifie une condition
            if (collisionFace == 2) finalResult.normalY = 1;

            // Renvoie une valeur à l'appelant
            return true;
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static double epsilon(double value) {
        // Renvoie une valeur à l'appelant
        return Math.abs(value) < Vec.EPSILON ? 0 : value;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static boolean BoundingBoxRayIntersectionCheck(Vec start, Vec direction, BoundingBox boundingBox, Pos position) {
        // Renvoie une valeur à l'appelant
        return BoundingBoxIntersectionCheck(BoundingBox.ZERO, start, direction, boundingBox, position, new SweepResult(Double.MAX_VALUE, 0, 0, 0, null, 0, 0, 0, 0, 0, 0));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
