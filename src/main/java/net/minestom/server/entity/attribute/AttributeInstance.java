// Package declaration for this file
package net.minestom.server.entity.attribute;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.entity.LivingEntity;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnmodifiableView;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;
// Import of a required class
import java.util.concurrent.atomic.AtomicLong;
// Import of a required class
import java.util.function.Consumer;

/**
 * Represents an instance of an attribute and its modifiers. This class is thread-safe (you do not need to acquire the
 * entity to modify its attributes).
 */
// Type declaration (class/interface/enum/record)
public final class AttributeInstance {
    // Assigns a value
    public static final NetworkBuffer.Type<AttributeInstance> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            Attribute.NETWORK_TYPE, AttributeInstance::attribute,
            // Code statement
            NetworkBuffer.DOUBLE, AttributeInstance::getBaseValue,
            // Code statement
            AttributeModifier.NETWORK_TYPE.list(Short.MAX_VALUE), value -> List.copyOf(value.modifiers()),
            // Code statement
            (attribute, baseValue, modifiers) -> new AttributeInstance(attribute, baseValue, modifiers, null)
    // End of a block/expression
    );

    // Code statement
    private final Attribute attribute;
    // Code statement
    private final Map<Key, AttributeModifier> modifiers;
    // Code statement
    private final Collection<AttributeModifier> unmodifiableModifiers;
    // Code statement
    private final AtomicLong baseValueBits;

    // Code statement
    private final Consumer<AttributeInstance> propertyChangeListener;
    // Assigns a value
    private volatile double cachedValue = 0.0D;

    // Start of a method/block
    public AttributeInstance(Attribute attribute, @Nullable Consumer<AttributeInstance> listener) {
        // Calls a method
        this(attribute, attribute.defaultValue(), new ArrayList<>(), listener);
    // End of a block/expression
    }

    // Start of a method/block
    public AttributeInstance(Attribute attribute, double baseValue, Collection<AttributeModifier> modifiers, @Nullable Consumer<AttributeInstance> listener) {
        // Access to the current/parent object
        this.attribute = attribute;
        // Access to the current/parent object
        this.modifiers = new ConcurrentHashMap<>();
        // Loop: repeats a block
        for (var modifier : modifiers) this.modifiers.put(modifier.id(), modifier);
        // Access to the current/parent object
        this.unmodifiableModifiers = Collections.unmodifiableCollection(this.modifiers.values());
        // Access to the current/parent object
        this.baseValueBits = new AtomicLong(Double.doubleToLongBits(baseValue));

        // Access to the current/parent object
        this.propertyChangeListener = listener;
        // Calls a method
        refreshCachedValue(baseValue);
    // End of a block/expression
    }

    /**
     * Gets the attribute associated to this instance.
     *
     * @return the associated attribute
     */
    // Start of a method/block
    public Attribute attribute() {
        // Returns a value to the caller
        return attribute;
    // End of a block/expression
    }

    /**
     * The base value of this instance without modifiers
     *
     * @return the instance base value
     * @see #setBaseValue(double)
     */
    // Start of a method/block
    public double getBaseValue() {
        // Returns a value to the caller
        return Double.longBitsToDouble(baseValueBits.get());
    // End of a block/expression
    }

    /**
     * Sets the base value of this instance.
     *
     * @param baseValue the new base value
     * @see #getBaseValue()
     */
    // Start of a method/block
    public void setBaseValue(double baseValue) {
        // Calls a method
        long newBits = Double.doubleToLongBits(baseValue);
        // Calls a method
        long oldBits = this.baseValueBits.getAndSet(newBits);
        // Branch: checks a condition
        if (oldBits != newBits) {
            // Calls a method
            refreshCachedValue(baseValue);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Get the modifiers applied to this instance.
     *
     * @return an immutable collection of the modifiers applied to this attribute.
     */
    // Annotation for the following element
    @UnmodifiableView
    // Start of a method/block
    public Collection<AttributeModifier> modifiers() {
        // Returns a value to the caller
        return unmodifiableModifiers;
    // End of a block/expression
    }

    /**
     * Add a modifier to this instance.
     *
     * @param modifier the modifier to add
     * @return the old modifier, or null if none
     */
    // Start of a method/block
    public @Nullable AttributeModifier addModifier(AttributeModifier modifier) {
        // Calls a method
        final AttributeModifier previousModifier = modifiers.put(modifier.id(), modifier);
        // Branch: checks a condition
        if (!modifier.equals(previousModifier)) refreshCachedValue(getBaseValue());
        // Returns a value to the caller
        return previousModifier;
    // End of a block/expression
    }

    /**
     * Remove a modifier from this instance.
     *
     * @param modifier the modifier to remove
     * @return the modifier that was removed, or null if none
     */
    // Start of a method/block
    public @Nullable AttributeModifier removeModifier(AttributeModifier modifier) {
        // Returns a value to the caller
        return removeModifier(modifier.id());
    // End of a block/expression
    }

    /**
     * Clears all modifiers on this instance, excepting those whose ID is defined in
     * {@link LivingEntity#PROTECTED_MODIFIERS}.
     */
    // Start of a method/block
    public void clearModifiers() {
        // Access to the current/parent object
        this.modifiers.values().removeIf(modifier -> !LivingEntity.PROTECTED_MODIFIERS.contains(modifier.id()));
        // Calls a method
        refreshCachedValue(getBaseValue());
    // End of a block/expression
    }

    /**
     * Remove a modifier from this instance.
     *
     * @param id The namespace id of the modifier to remove
     * @return the modifier that was removed, or null if none
     */
    // Start of a method/block
    public @Nullable AttributeModifier removeModifier(Key id) {
        // Calls a method
        final AttributeModifier removed = modifiers.remove(id);
        // Branch: checks a condition
        if (removed != null) {
            // Calls a method
            refreshCachedValue(getBaseValue());
        // End of a block/expression
        }

        // Returns a value to the caller
        return removed;
    // End of a block/expression
    }

    /**
     * Gets the value of this instance calculated with modifiers applied.
     *
     * @return the attribute value
     */
    // Start of a method/block
    public double getValue() {
        // Returns a value to the caller
        return cachedValue;
    // End of a block/expression
    }

    /**
     * Gets the value of this instance, calculated assuming the given {@code baseValue}.
     *
     * @param baseValue the value to be used as the base for this operation, rather than this instance's normal base
     *                  value
     * @return the attribute value
     */
    // Start of a method/block
    public double applyModifiers(double baseValue) {
        // Returns a value to the caller
        return computeValue(baseValue);
    // End of a block/expression
    }

    // Start of a method/block
    private double computeValue(double base) {
        // Calls a method
        final Collection<AttributeModifier> modifiers = modifiers();

        // Loop: repeats a block
        for (var modifier : modifiers.stream().filter(mod -> mod.operation() == AttributeOperation.ADD_VALUE).toArray(AttributeModifier[]::new)) {
            // Calls a method
            base += modifier.amount();
        // End of a block/expression
        }

        // Assigns a value
        double result = base;

        // Loop: repeats a block
        for (var modifier : modifiers.stream().filter(mod -> mod.operation() == AttributeOperation.ADD_MULTIPLIED_BASE).toArray(AttributeModifier[]::new)) {
            // Calls a method
            result += (base * modifier.amount());
        // End of a block/expression
        }
        // Loop: repeats a block
        for (var modifier : modifiers.stream().filter(mod -> mod.operation() == AttributeOperation.ADD_MULTIPLIED_TOTAL).toArray(AttributeModifier[]::new)) {
            // Calls a method
            result *= (1.0f + modifier.amount());
        // End of a block/expression
        }

        // Returns a value to the caller
        return Math.clamp(result, getAttribute().minValue(), getAttribute().maxValue());
    // End of a block/expression
    }

    /**
     * Recalculate the value of this attribute instance using the modifiers.
     */
    // Start of a method/block
    private void refreshCachedValue(double baseValue) {
        // Access to the current/parent object
        this.cachedValue = computeValue(baseValue);

        // Signal entity
        // Branch: checks a condition
        if (propertyChangeListener != null) {
            // Calls a method
            propertyChangeListener.accept(this);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public Collection<AttributeModifier> getModifiers() {
        // Returns a value to the caller
        return modifiers();
    // End of a block/expression
    }

    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public Attribute getAttribute() {
        // Returns a value to the caller
        return attribute;
    // End of a block/expression
    }
// End of a block/expression
}
