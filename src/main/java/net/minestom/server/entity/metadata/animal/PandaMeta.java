// Package declaration for this file
package net.minestom.server.entity.metadata.animal;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class PandaMeta extends AnimalMeta {
    // Start of a method/block
    public PandaMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public int getBreedTimer() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Panda.BREED_TIMER);
    // End of a block/expression
    }

    // Start of a method/block
    public void setBreedTimer(int value) {
        // Calls a method
        metadata.set(MetadataDef.Panda.BREED_TIMER, value);
    // End of a block/expression
    }

    // Start of a method/block
    public int getSneezeTimer() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Panda.SNEEZE_TIMER);
    // End of a block/expression
    }

    // Start of a method/block
    public void setSneezeTimer(int value) {
        // Calls a method
        metadata.set(MetadataDef.Panda.SNEEZE_TIMER, value);
    // End of a block/expression
    }

    // Start of a method/block
    public int getEatTimer() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Panda.EAT_TIMER);
    // End of a block/expression
    }

    // Start of a method/block
    public void setEatTimer(int value) {
        // Calls a method
        metadata.set(MetadataDef.Panda.EAT_TIMER, value);
    // End of a block/expression
    }

    // Start of a method/block
    public Gene getMainGene() {
        // Returns a value to the caller
        return Gene.VALUES[metadata.get(MetadataDef.Panda.MAIN_GENE)];
    // End of a block/expression
    }

    // Start of a method/block
    public void setMainGene(Gene value) {
        // Calls a method
        metadata.set(MetadataDef.Panda.MAIN_GENE, (byte) value.ordinal());
    // End of a block/expression
    }

    // Start of a method/block
    public Gene getHiddenGene() {
        // Returns a value to the caller
        return Gene.VALUES[metadata.get(MetadataDef.Panda.HIDDEN_GENE)];
    // End of a block/expression
    }

    // Start of a method/block
    public void setHiddenGene(Gene value) {
        // Calls a method
        metadata.set(MetadataDef.Panda.HIDDEN_GENE, (byte) value.ordinal());
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isSneezing() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Panda.IS_SNEEZING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setSneezing(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Panda.IS_SNEEZING, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isRolling() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Panda.IS_ROLLING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setRolling(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Panda.IS_ROLLING, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isSitting() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Panda.IS_SITTING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setSitting(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Panda.IS_SITTING, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isOnBack() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Panda.IS_ON_BACK);
    // End of a block/expression
    }

    // Start of a method/block
    public void setOnBack(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Panda.IS_ON_BACK, value);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Gene {
        // Code statement
        NORMAL,
        // Code statement
        LAZY,
        // Code statement
        WORRIED,
        // Code statement
        PLAYFUL,
        // Code statement
        BROWN,
        // Code statement
        WEAK,
        // Code statement
        AGGRESSIVE;

        // Calls a method
        private final static Gene[] VALUES = values();
    // End of a block/expression
    }

// End of a block/expression
}
