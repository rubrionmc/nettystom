// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.monster;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class CreeperMeta extends MonsterMeta {
    // Début d'une méthode/d'un bloc
    public CreeperMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public State getState() {
        // Appelle une méthode
        int id = metadata.get(MetadataDef.Creeper.STATE);
        // Renvoie une valeur à l'appelant
        return id == -1 ? State.IDLE : State.FUSE;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setState(State value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Creeper.STATE, value == State.IDLE ? -1 : 1);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isCharged() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Creeper.IS_CHARGED);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setCharged(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Creeper.IS_CHARGED, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isIgnited() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Creeper.IS_IGNITED);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setIgnited(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Creeper.IS_IGNITED, value);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum State {
        // Instruction de code
        IDLE,
        // Instruction de code
        FUSE
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
