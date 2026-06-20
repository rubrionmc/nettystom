// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.avatar;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class PlayerMeta extends AvatarMeta {
    // Début d'une méthode/d'un bloc
    public PlayerMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public float getAdditionalHearts() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Player.ADDITIONAL_HEARTS);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setAdditionalHearts(float value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Player.ADDITIONAL_HEARTS, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getScore() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Player.SCORE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setScore(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Player.SCORE, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable Integer getLeftShoulderEntityData() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Player.LEFT_SHOULDER_ENTITY_DATA);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setLeftShoulderEntityData(@Nullable Integer value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Player.LEFT_SHOULDER_ENTITY_DATA, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable Integer getRightShoulderEntityData() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Player.RIGHT_SHOULDER_ENTITY_DATA);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setRightShoulderEntityData(@Nullable Integer value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Player.RIGHT_SHOULDER_ENTITY_DATA, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
