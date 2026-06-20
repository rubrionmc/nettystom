// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityPose;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.lang.ref.WeakReference;
// Import d'une classe nécessaire
import java.util.function.Consumer;

// Déclaration de type (classe/interface/enum/record)
public class EntityMeta {
    // Instruction de code
    private final WeakReference<@Nullable Entity> entityRef;
    // Instruction de code
    protected final MetadataHolder metadata;

    // Début d'une méthode/d'un bloc
    public EntityMeta(@Nullable Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        this.entityRef = new WeakReference<>(entity);
        // Accès à l'objet courant/parent
        this.metadata = metadata;
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public void setNotifyAboutChanges(boolean notifyAboutChanges) {
        // Accès à l'objet courant/parent
        this.metadata.setNotifyAboutChanges(notifyAboutChanges);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isOnFire() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.IS_ON_FIRE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setOnFire(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.IS_ON_FIRE, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isSneaking() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.IS_CROUCHING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setSneaking(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.IS_CROUCHING, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isSprinting() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.IS_SPRINTING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setSprinting(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.IS_SPRINTING, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isSwimming() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.IS_SWIMMING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setSwimming(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.IS_SWIMMING, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isInvisible() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.IS_INVISIBLE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setInvisible(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.IS_INVISIBLE, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isHasGlowingEffect() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.HAS_GLOWING_EFFECT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHasGlowingEffect(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.HAS_GLOWING_EFFECT, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isFlyingWithElytra() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.IS_FLYING_WITH_ELYTRA);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setFlyingWithElytra(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.IS_FLYING_WITH_ELYTRA, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getAirTicks() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.AIR_TICKS);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setAirTicks(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.AIR_TICKS, value);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#CUSTOM_NAME} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public @Nullable Component getCustomName() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.CUSTOM_NAME);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#CUSTOM_NAME} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public void setCustomName(@Nullable Component value) {
        // Appelle une méthode
        metadata.set(MetadataDef.CUSTOM_NAME, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isCustomNameVisible() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.CUSTOM_NAME_VISIBLE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setCustomNameVisible(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.CUSTOM_NAME_VISIBLE, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isSilent() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.IS_SILENT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setSilent(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.IS_SILENT, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isHasNoGravity() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.HAS_NO_GRAVITY);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHasNoGravity(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.HAS_NO_GRAVITY, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public EntityPose getPose() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.POSE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setPose(EntityPose value) {
        // Appelle une méthode
        metadata.set(MetadataDef.POSE, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getTickFrozen() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.TICKS_FROZEN);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setTickFrozen(int tickFrozen) {
        // Appelle une méthode
        metadata.set(MetadataDef.TICKS_FROZEN, tickFrozen);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected void consumeEntity(Consumer<? super Entity> consumer) {
        // Appelle une méthode
        Entity entity = this.entityRef.get();
        // Embranchement : vérifie une condition
        if (entity != null) {
            // Appelle une méthode
            consumer.accept(entity);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Exists to hide the component set implementation on meta to direct people to use the method on Entity.
     *
     * <p>Planned to only exist while we have both metadata and components separately/all metadata is not represented by components.</p>
     *
     * @see Entity#set(DataComponent, Object)
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public static <T> @Nullable T getComponent(EntityMeta meta, DataComponent<T> component) {
        // Renvoie une valeur à l'appelant
        return meta.get(component);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Exists to hide the component set implementation on meta to direct people to use the method on Entity.
     *
     * <p>Planned to only exist while we have both metadata and components separately/all metadata is not represented by components.</p>
     *
     * @see Entity#set(DataComponent, Object)
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public static <T> void setComponent(EntityMeta meta, DataComponent<T> component, T value) {
        // Appelle une méthode
        meta.set(component, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.CUSTOM_NAME)
            // Renvoie une valeur à l'appelant
            return (T) metadata.get(MetadataDef.CUSTOM_NAME);
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected <T> void set(DataComponent<T> component, T value) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.CUSTOM_NAME)
            // Appelle une méthode
            metadata.set(MetadataDef.CUSTOM_NAME, (Component) value);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Retrieves the value of the specified metadata entry.
     *
     * @param entry The metadata entry to retrieve the value from.
     * @param <T>   The type of the metadata value.
     * @return The value associated with the specified metadata entry.
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Début d'une méthode/d'un bloc
    public <T extends @UnknownNullability Object> T get(MetadataDef.Entry<T> entry) {
        // Renvoie une valeur à l'appelant
        return metadata.get(entry);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the value of the specified metadata entry.
     *
     * @param entry The metadata entry to be updated.
     * @param value The value to assign to the specified metadata entry.
     * @param <T>   The type of the metadata value.
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Début d'une méthode/d'un bloc
    public <T extends @UnknownNullability Object> void set(MetadataDef.Entry<T> entry, T value) {
        // Appelle une méthode
        metadata.set(entry, value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
