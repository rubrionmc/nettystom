// Package declaration for this file
package net.minestom.server.entity.metadata.water.fish;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class PufferfishMeta extends AbstractFishMeta {
    // Start of a method/block
    public PufferfishMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
        // Calls a method
        updateBoundingBox(State.UNPUFFED);
    // End of a block/expression
    }

    // Start of a method/block
    public State getState() {
        // Returns a value to the caller
        return State.VALUES[metadata.get(MetadataDef.PufferFish.PUFF_STATE)];
    // End of a block/expression
    }

    // Start of a method/block
    public void setState(State state) {
        // Calls a method
        metadata.set(MetadataDef.PufferFish.PUFF_STATE, state.ordinal());
        // Calls a method
        updateBoundingBox(state);
    // End of a block/expression
    }

    // Start of a method/block
    private void updateBoundingBox(State state) {
        // Access to the current/parent object
        this.consumeEntity((entity) -> {
            // Multiple branching (switch/case)
            switch (state) {
                // Multiple branching (switch/case)
                case UNPUFFED -> entity.setBoundingBox(.35D, .35D, .35D);
                // Multiple branching (switch/case)
                case SEMI_PUFFED -> entity.setBoundingBox(.5D, .5D, .5D);
                // Multiple branching (switch/case)
                default -> entity.setBoundingBox(.7D, .7D, .7D);
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum State {
        // Code statement
        UNPUFFED,
        // Code statement
        SEMI_PUFFED,
        // Code statement
        FULLY_PUFFED;

        // Calls a method
        private final static State[] VALUES = values();
    // End of a block/expression
    }

// End of a block/expression
}
