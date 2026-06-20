// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.projectile;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public interface ProjectileMeta {

    // Annotation pour l'élément suivant
    @Nullable
    // Appelle une méthode
    Entity getShooter();

    // Appelle une méthode
    void setShooter(@Nullable Entity shooter);

// Fin d'un bloc/d'une expression
}
