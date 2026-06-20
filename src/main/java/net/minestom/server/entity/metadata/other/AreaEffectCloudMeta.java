// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.other;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.EntityMeta;
// Import d'une classe nécessaire
import net.minestom.server.particle.Particle;

// Déclaration de type (classe/interface/enum/record)
public class AreaEffectCloudMeta extends EntityMeta {
    // Début d'une méthode/d'un bloc
    public AreaEffectCloudMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public float getRadius() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.AreaEffectCloud.RADIUS);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setRadius(float value) {
        // Appelle une méthode
        metadata.set(MetadataDef.AreaEffectCloud.RADIUS, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isWaiting() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.AreaEffectCloud.WAITING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setWaiting(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.AreaEffectCloud.WAITING, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Particle getParticle() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.AreaEffectCloud.PARTICLE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setParticle(Particle value) {
        // Appelle une méthode
        metadata.set(MetadataDef.AreaEffectCloud.PARTICLE, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
