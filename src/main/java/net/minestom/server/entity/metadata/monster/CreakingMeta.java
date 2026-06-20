// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.monster;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class CreakingMeta extends MonsterMeta {
    // Début d'une méthode/d'un bloc
    public CreakingMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean canMove() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Creaking.CAN_MOVE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setCanMove(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Creaking.CAN_MOVE, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isActive() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Creaking.IS_ACTIVE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setActive(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Creaking.IS_ACTIVE, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isTearingDown() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Creaking.IS_TEARING_DOWN);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setTearingDown(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Creaking.IS_TEARING_DOWN, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable Point getHomePos() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Creaking.HOME_POS);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHomePos(@Nullable Point value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Creaking.HOME_POS, value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
