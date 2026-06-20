// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;

// Déclaration de type (classe/interface/enum/record)
public class ArmadilloMeta extends AnimalMeta {
    // Début d'une méthode/d'un bloc
    public ArmadilloMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public State getState() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Armadillo.STATE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setState(State value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Armadillo.STATE, value);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum State {
        // Instruction de code
        IDLE,
        // Instruction de code
        ROLLING,
        // Instruction de code
        SCARED,
        // Instruction de code
        UNROLLING;

        // Appelle une méthode
        public static final NetworkBuffer.Type<State> NETWORK_TYPE = NetworkBuffer.Enum(State.class);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
