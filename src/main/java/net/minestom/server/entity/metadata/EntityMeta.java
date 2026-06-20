// Package declaration for this file
package net.minestom.server.entity.metadata;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityPose;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.lang.ref.WeakReference;
// Import of a required class
import java.util.function.Consumer;

// Type declaration (class/interface/enum/record)
public class EntityMeta {
    // Code statement
    private final WeakReference<@Nullable Entity> entityRef;
    // Code statement
    protected final MetadataHolder metadata;

    // Start of a method/block
    public EntityMeta(@Nullable Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        this.entityRef = new WeakReference<>(entity);
        // Access to the current/parent object
        this.metadata = metadata;
    // End of a block/expression
    }

    /**
     * Sets whether any changes to this meta must result in a metadata packet being sent to entity viewers.
     * By default it's set to true.
     * <p>
     * It's usable if you want to change multiple values of this meta at the same time and want just a
     * single packet being sent: if so, disable notification before your first change and enable it
     * right after the last one: once notification is set to false, we collect all the updates
     * that are being performed, and when it's returned to true we send them all together.
     * An example usage could be found at
     * {@link net.minestom.server.entity.LivingEntity#refreshActiveHand(boolean, boolean, boolean)}.
     *
     * @param notifyAboutChanges if to notify entity viewers about this meta changes.
     */
    // Start of a method/block
    public void setNotifyAboutChanges(boolean notifyAboutChanges) {
        // Access to the current/parent object
        this.metadata.setNotifyAboutChanges(notifyAboutChanges);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isOnFire() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.IS_ON_FIRE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setOnFire(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.IS_ON_FIRE, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isSneaking() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.IS_CROUCHING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setSneaking(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.IS_CROUCHING, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isSprinting() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.IS_SPRINTING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setSprinting(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.IS_SPRINTING, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isSwimming() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.IS_SWIMMING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setSwimming(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.IS_SWIMMING, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isInvisible() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.IS_INVISIBLE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setInvisible(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.IS_INVISIBLE, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isHasGlowingEffect() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.HAS_GLOWING_EFFECT);
    // End of a block/expression
    }

    // Start of a method/block
    public void setHasGlowingEffect(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.HAS_GLOWING_EFFECT, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isFlyingWithElytra() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.IS_FLYING_WITH_ELYTRA);
    // End of a block/expression
    }

    // Start of a method/block
    public void setFlyingWithElytra(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.IS_FLYING_WITH_ELYTRA, value);
    // End of a block/expression
    }

    // Start of a method/block
    public int getAirTicks() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.AIR_TICKS);
    // End of a block/expression
    }

    // Start of a method/block
    public void setAirTicks(int value) {
        // Calls a method
        metadata.set(MetadataDef.AIR_TICKS, value);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#CUSTOM_NAME} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public @Nullable Component getCustomName() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.CUSTOM_NAME);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#CUSTOM_NAME} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public void setCustomName(@Nullable Component value) {
        // Calls a method
        metadata.set(MetadataDef.CUSTOM_NAME, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isCustomNameVisible() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.CUSTOM_NAME_VISIBLE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setCustomNameVisible(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.CUSTOM_NAME_VISIBLE, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isSilent() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.IS_SILENT);
    // End of a block/expression
    }

    // Start of a method/block
    public void setSilent(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.IS_SILENT, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isHasNoGravity() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.HAS_NO_GRAVITY);
    // End of a block/expression
    }

    // Start of a method/block
    public void setHasNoGravity(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.HAS_NO_GRAVITY, value);
    // End of a block/expression
    }

    // Start of a method/block
    public EntityPose getPose() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.POSE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setPose(EntityPose value) {
        // Calls a method
        metadata.set(MetadataDef.POSE, value);
    // End of a block/expression
    }

    // Start of a method/block
    public int getTickFrozen() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.TICKS_FROZEN);
    // End of a block/expression
    }

    // Start of a method/block
    public void setTickFrozen(int tickFrozen) {
        // Calls a method
        metadata.set(MetadataDef.TICKS_FROZEN, tickFrozen);
    // End of a block/expression
    }

    // Start of a method/block
    protected void consumeEntity(Consumer<? super Entity> consumer) {
        // Calls a method
        Entity entity = this.entityRef.get();
        // Branch: checks a condition
        if (entity != null) {
            // Calls a method
            consumer.accept(entity);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Exists to hide the component set implementation on meta to direct people to use the method on Entity.
     *
     * <p>Planned to only exist while we have both metadata and components separately/all metadata is not represented by components.</p>
     *
     * @see Entity#set(DataComponent, Object)
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public static <T> @Nullable T getComponent(EntityMeta meta, DataComponent<T> component) {
        // Returns a value to the caller
        return meta.get(component);
    // End of a block/expression
    }

    /**
     * Exists to hide the component set implementation on meta to direct people to use the method on Entity.
     *
     * <p>Planned to only exist while we have both metadata and components separately/all metadata is not represented by components.</p>
     *
     * @see Entity#set(DataComponent, Object)
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public static <T> void setComponent(EntityMeta meta, DataComponent<T> component, T value) {
        // Calls a method
        meta.set(component, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Branch: checks a condition
        if (component == DataComponents.CUSTOM_NAME)
            // Returns a value to the caller
            return (T) metadata.get(MetadataDef.CUSTOM_NAME);
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }

    // Start of a method/block
    protected <T> void set(DataComponent<T> component, T value) {
        // Branch: checks a condition
        if (component == DataComponents.CUSTOM_NAME)
            // Calls a method
            metadata.set(MetadataDef.CUSTOM_NAME, (Component) value);
    // End of a block/expression
    }

    /**
     * Retrieves the value of the specified metadata entry.
     *
     * @param entry The metadata entry to retrieve the value from.
     * @param <T>   The type of the metadata value.
     * @return The value associated with the specified metadata entry.
     */
    // Annotation for the following element
    @ApiStatus.Experimental
    // Start of a method/block
    public <T extends @UnknownNullability Object> T get(MetadataDef.Entry<T> entry) {
        // Returns a value to the caller
        return metadata.get(entry);
    // End of a block/expression
    }

    /**
     * Sets the value of the specified metadata entry.
     *
     * @param entry The metadata entry to be updated.
     * @param value The value to assign to the specified metadata entry.
     * @param <T>   The type of the metadata value.
     */
    // Annotation for the following element
    @ApiStatus.Experimental
    // Start of a method/block
    public <T extends @UnknownNullability Object> void set(MetadataDef.Entry<T> entry, T value) {
        // Calls a method
        metadata.set(entry, value);
    // End of a block/expression
    }
// End of a block/expression
}
