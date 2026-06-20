// Package declaration for this file
package net.minestom.server.entity.metadata.villager;

// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.entity.*;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class VillagerMeta extends AbstractVillagerMeta {
    // Start of a method/block
    public VillagerMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public VillagerData getVillagerData() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Villager.VARIANT);
    // End of a block/expression
    }

    // Start of a method/block
    public void setVillagerData(VillagerData data) {
        // Calls a method
        metadata.set(MetadataDef.Villager.VARIANT, data);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isFinalized() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Villager.IS_FINALIZED);
    // End of a block/expression
    }

    // Start of a method/block
    public void setFinalized(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Villager.IS_FINALIZED, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Branch: checks a condition
        if (component == DataComponents.VILLAGER_VARIANT)
            // Returns a value to the caller
            return (T) getVillagerData().type();
        // Returns a value to the caller
        return super.get(component);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected <T> void set(DataComponent<T> component, T value) {
        // Branch: checks a condition
        if (component == DataComponents.VILLAGER_VARIANT)
            // Calls a method
            setVillagerData(getVillagerData().withType((VillagerType) value));
        // Alternative branch of the condition
        else super.set(component, value);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record VillagerData(
            // Code statement
            VillagerType type,
            // Code statement
            VillagerProfession profession,
            // Code statement
            Level level
    // Start of a method/block
    ) {
        // Calls a method
        public static final VillagerData DEFAULT = new VillagerData(VillagerType.DESERT, VillagerProfession.NONE, Level.NOVICE);

        // Assigns a value
        public static final NetworkBuffer.Type<VillagerData> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                VillagerType.NETWORK_TYPE, VillagerData::type,
                // Code statement
                VillagerProfession.NETWORK_TYPE, VillagerData::profession,
                // Code statement
                Level.NETWORK_TYPE, VillagerData::level,
                // Code statement
                VillagerData::new);

        // Start of a method/block
        public VillagerData withType(VillagerType type) {
            // Returns a value to the caller
            return new VillagerData(type, this.profession, this.level);
        // End of a block/expression
        }

        // Start of a method/block
        public VillagerData withProfession(VillagerProfession profession) {
            // Returns a value to the caller
            return new VillagerData(this.type, profession, this.level);
        // End of a block/expression
        }

        // Start of a method/block
        public VillagerData withLevel(Level level) {
            // Returns a value to the caller
            return new VillagerData(this.type, this.profession, level);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Level {
        // Code statement
        NOVICE,
        // Code statement
        APPRENTICE,
        // Code statement
        JOURNEYMAN,
        // Code statement
        EXPERT,
        // Code statement
        MASTER;

        // Calls a method
        private static final Level[] VALUES = values();

        // Start of a method/block
        private int toProtocolId() {
            // Returns a value to the caller
            return this.ordinal() + 1;  // Villager levels are 1-indexed
        // End of a block/expression
        }

        // Start of a method/block
        private static Level fromProtocolId(int value) {
            // Returns a value to the caller
            return VALUES[value - 1];
        // End of a block/expression
        }

        // Calls a method
        public static final NetworkBuffer.Type<Level> NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(Level::fromProtocolId, Level::toProtocolId);
    // End of a block/expression
    }

// End of a block/expression
}
