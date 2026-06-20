// Package declaration for this file
package net.minestom.server.entity.metadata.animal;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;

// Type declaration (class/interface/enum/record)
public class ArmadilloMeta extends AnimalMeta {
    // Start of a method/block
    public ArmadilloMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public State getState() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Armadillo.STATE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setState(State value) {
        // Calls a method
        metadata.set(MetadataDef.Armadillo.STATE, value);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum State {
        // Code statement
        IDLE,
        // Code statement
        ROLLING,
        // Code statement
        SCARED,
        // Code statement
        UNROLLING;

        // Calls a method
        public static final NetworkBuffer.Type<State> NETWORK_TYPE = NetworkBuffer.Enum(State.class);
    // End of a block/expression
    }
// End of a block/expression
}
