// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.water.fish;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class PufferfishMeta extends AbstractFishMeta {
    // Début d'une méthode/d'un bloc
    public PufferfishMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
        // Appelle une méthode
        updateBoundingBox(State.UNPUFFED);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public State getState() {
        // Renvoie une valeur à l'appelant
        return State.VALUES[metadata.get(MetadataDef.PufferFish.PUFF_STATE)];
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setState(State state) {
        // Appelle une méthode
        metadata.set(MetadataDef.PufferFish.PUFF_STATE, state.ordinal());
        // Appelle une méthode
        updateBoundingBox(state);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void updateBoundingBox(State state) {
        // Accès à l'objet courant/parent
        this.consumeEntity((entity) -> {
            // Embranchement multiple (switch/case)
            switch (state) {
                // Embranchement multiple (switch/case)
                case UNPUFFED -> entity.setBoundingBox(.35D, .35D, .35D);
                // Embranchement multiple (switch/case)
                case SEMI_PUFFED -> entity.setBoundingBox(.5D, .5D, .5D);
                // Embranchement multiple (switch/case)
                default -> entity.setBoundingBox(.7D, .7D, .7D);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum State {
        // Instruction de code
        UNPUFFED,
        // Instruction de code
        SEMI_PUFFED,
        // Instruction de code
        FULLY_PUFFED;

        // Appelle une méthode
        private final static State[] VALUES = values();
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
