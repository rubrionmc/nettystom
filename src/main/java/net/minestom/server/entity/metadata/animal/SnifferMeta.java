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
public class SnifferMeta extends AnimalMeta {
    // Début d'une méthode/d'un bloc
    public SnifferMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public State getState() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Sniffer.STATE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setState(State value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Sniffer.STATE, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getDropSeedAtTick() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Sniffer.DROP_SEED_AT_TICK);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setDropSeedAtTick(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Sniffer.DROP_SEED_AT_TICK, value);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum State {
        // Instruction de code
        IDLING,
        // Instruction de code
        FEELING_HAPPY,
        // Instruction de code
        SCENTING,
        // Instruction de code
        SNIFFING,
        // Instruction de code
        SEARCHING,
        // Instruction de code
        DIGGING,
        // Instruction de code
        RISING;

        // Appelle une méthode
        public static final NetworkBuffer.Type<State> NETWORK_TYPE = NetworkBuffer.Enum(State.class);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
