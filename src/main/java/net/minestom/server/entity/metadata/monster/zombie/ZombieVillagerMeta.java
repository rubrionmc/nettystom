// Package declaration for this file
package net.minestom.server.entity.metadata.monster.zombie;

// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.entity.VillagerType;
// Import of a required class
import net.minestom.server.entity.metadata.villager.VillagerMeta;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class ZombieVillagerMeta extends ZombieMeta {
    // Start of a method/block
    public ZombieVillagerMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isConverting() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.ZombieVillager.IS_CONVERTING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setConverting(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.ZombieVillager.IS_CONVERTING, value);
    // End of a block/expression
    }

    // Start of a method/block
    public VillagerMeta.VillagerData getVillagerData() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.ZombieVillager.VILLAGER_DATA);
    // End of a block/expression
    }

    // Start of a method/block
    public void setVillagerData(VillagerMeta.VillagerData data) {
        // Calls a method
        metadata.set(MetadataDef.ZombieVillager.VILLAGER_DATA, data);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isFinalized() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.ZombieVillager.IS_FINALIZED);
    // End of a block/expression
    }

    // Start of a method/block
    public void setFinalized(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.ZombieVillager.IS_FINALIZED, value);
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
// End of a block/expression
}
