// Déclaration du paquet de ce fichier
package net.minestom.server.entity.attribute;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.entity.LivingEntity;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnmodifiableView;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicLong;
// Import d'une classe nécessaire
import java.util.function.Consumer;

/**
 * Represents an instance of an attribute and its modifiers. This class is thread-safe (you do not need to acquire the
 * entity to modify its attributes).
 */
// Déclaration de type (classe/interface/enum/record)
public final class AttributeInstance {
    // Affecte une valeur
    public static final NetworkBuffer.Type<AttributeInstance> NETWORK_TYPE = new NetworkBuffer.Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, AttributeInstance value) {
            // Appelle une méthode
            buffer.write(Attribute.NETWORK_TYPE, value.attribute());
            // Appelle une méthode
            buffer.write(NetworkBuffer.DOUBLE, value.getBaseValue());
            // Appelle une méthode
            buffer.write(AttributeModifier.NETWORK_TYPE.list(Short.MAX_VALUE), List.copyOf(value.modifiers()));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public AttributeInstance read(NetworkBuffer buffer) {
            // Renvoie une valeur à l'appelant
            return new AttributeInstance(buffer.read(Attribute.NETWORK_TYPE), buffer.read(NetworkBuffer.DOUBLE),
                    // Appelle une méthode
                    buffer.read(AttributeModifier.NETWORK_TYPE.list(Short.MAX_VALUE)), null);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Instruction de code
    private final Attribute attribute;
    // Instruction de code
    private final Map<Key, AttributeModifier> modifiers;
    // Instruction de code
    private final Collection<AttributeModifier> unmodifiableModifiers;
    // Instruction de code
    private final AtomicLong baseValueBits;

    // Instruction de code
    private final Consumer<AttributeInstance> propertyChangeListener;
    // Affecte une valeur
    private volatile double cachedValue = 0.0D;

    // Début d'une méthode/d'un bloc
    public AttributeInstance(Attribute attribute, @Nullable Consumer<AttributeInstance> listener) {
        // Appelle une méthode
        this(attribute, attribute.defaultValue(), new ArrayList<>(), listener);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public AttributeInstance(Attribute attribute, double baseValue, Collection<AttributeModifier> modifiers, @Nullable Consumer<AttributeInstance> listener) {
        // Accès à l'objet courant/parent
        this.attribute = attribute;
        // Accès à l'objet courant/parent
        this.modifiers = new ConcurrentHashMap<>();
        // Boucle : répète un bloc
        for (var modifier : modifiers) this.modifiers.put(modifier.id(), modifier);
        // Accès à l'objet courant/parent
        this.unmodifiableModifiers = Collections.unmodifiableCollection(this.modifiers.values());
        // Accès à l'objet courant/parent
        this.baseValueBits = new AtomicLong(Double.doubleToLongBits(baseValue));

        // Accès à l'objet courant/parent
        this.propertyChangeListener = listener;
        // Appelle une méthode
        refreshCachedValue(baseValue);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the attribute associated to this instance.
     *
     * @return the associated attribute
     */
    // Début d'une méthode/d'un bloc
    public Attribute attribute() {
        // Renvoie une valeur à l'appelant
        return attribute;
    // Fin d'un bloc/d'une expression
    }

    /**
     * The base value of this instance without modifiers
     *
     * @return the instance base value
     * @see #setBaseValue(double)
     */
    // Début d'une méthode/d'un bloc
    public double getBaseValue() {
        // Renvoie une valeur à l'appelant
        return Double.longBitsToDouble(baseValueBits.get());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the base value of this instance.
     *
     * @param baseValue the new base value
     * @see #getBaseValue()
     */
    // Début d'une méthode/d'un bloc
    public void setBaseValue(double baseValue) {
        // Appelle une méthode
        long newBits = Double.doubleToLongBits(baseValue);
        // Appelle une méthode
        long oldBits = this.baseValueBits.getAndSet(newBits);
        // Embranchement : vérifie une condition
        if (oldBits != newBits) {
            // Appelle une méthode
            refreshCachedValue(baseValue);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Get the modifiers applied to this instance.
     *
     * @return an immutable collection of the modifiers applied to this attribute.
     */
    // Annotation pour l'élément suivant
    @UnmodifiableView
    // Début d'une méthode/d'un bloc
    public Collection<AttributeModifier> modifiers() {
        // Renvoie une valeur à l'appelant
        return unmodifiableModifiers;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Add a modifier to this instance.
     *
     * @param modifier the modifier to add
     * @return the old modifier, or null if none
     */
    // Début d'une méthode/d'un bloc
    public @Nullable AttributeModifier addModifier(AttributeModifier modifier) {
        // Appelle une méthode
        final AttributeModifier previousModifier = modifiers.put(modifier.id(), modifier);
        // Embranchement : vérifie une condition
        if (!modifier.equals(previousModifier)) refreshCachedValue(getBaseValue());
        // Renvoie une valeur à l'appelant
        return previousModifier;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Remove a modifier from this instance.
     *
     * @param modifier the modifier to remove
     * @return the modifier that was removed, or null if none
     */
    // Début d'une méthode/d'un bloc
    public @Nullable AttributeModifier removeModifier(AttributeModifier modifier) {
        // Renvoie une valeur à l'appelant
        return removeModifier(modifier.id());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Clears all modifiers on this instance, excepting those whose ID is defined in
     * {@link LivingEntity#PROTECTED_MODIFIERS}.
     */
    // Début d'une méthode/d'un bloc
    public void clearModifiers() {
        // Accès à l'objet courant/parent
        this.modifiers.values().removeIf(modifier -> !LivingEntity.PROTECTED_MODIFIERS.contains(modifier.id()));
        // Appelle une méthode
        refreshCachedValue(getBaseValue());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Remove a modifier from this instance.
     *
     * @param id The namespace id of the modifier to remove
     * @return the modifier that was removed, or null if none
     */
    // Début d'une méthode/d'un bloc
    public @Nullable AttributeModifier removeModifier(Key id) {
        // Appelle une méthode
        final AttributeModifier removed = modifiers.remove(id);
        // Embranchement : vérifie une condition
        if (removed != null) {
            // Appelle une méthode
            refreshCachedValue(getBaseValue());
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return removed;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the value of this instance calculated with modifiers applied.
     *
     * @return the attribute value
     */
    // Début d'une méthode/d'un bloc
    public double getValue() {
        // Renvoie une valeur à l'appelant
        return cachedValue;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the value of this instance, calculated assuming the given {@code baseValue}.
     *
     * @param baseValue the value to be used as the base for this operation, rather than this instance's normal base
     *                  value
     * @return the attribute value
     */
    // Début d'une méthode/d'un bloc
    public double applyModifiers(double baseValue) {
        // Renvoie une valeur à l'appelant
        return computeValue(baseValue);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private double computeValue(double base) {
        // Appelle une méthode
        final Collection<AttributeModifier> modifiers = modifiers();

        // Boucle : répète un bloc
        for (var modifier : modifiers.stream().filter(mod -> mod.operation() == AttributeOperation.ADD_VALUE).toArray(AttributeModifier[]::new)) {
            // Appelle une méthode
            base += modifier.amount();
        // Fin d'un bloc/d'une expression
        }

        // Boucle : répète un bloc
        double result = base;

        // Boucle : répète un bloc
        for (var modifier : modifiers.stream().filter(mod -> mod.operation() == AttributeOperation.ADD_MULTIPLIED_BASE).toArray(AttributeModifier[]::new)) {
            // Appelle une méthode
            result += (base * modifier.amount());
        // Fin d'un bloc/d'une expression
        }
        // Boucle : répète un bloc
        for (var modifier : modifiers.stream().filter(mod -> mod.operation() == AttributeOperation.ADD_MULTIPLIED_TOTAL).toArray(AttributeModifier[]::new)) {
            // Appelle une méthode
            result *= (1.0f + modifier.amount());
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return Math.clamp(result, getAttribute().minValue(), getAttribute().maxValue());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Recalculate the value of this attribute instance using the modifiers.
     */
    // Début d'une méthode/d'un bloc
    private void refreshCachedValue(double baseValue) {
        // Accès à l'objet courant/parent
        this.cachedValue = computeValue(baseValue);

        // Signal entity
        // Embranchement : vérifie une condition
        if (propertyChangeListener != null) {
            // Appelle une méthode
            propertyChangeListener.accept(this);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public Collection<AttributeModifier> getModifiers() {
        // Renvoie une valeur à l'appelant
        return modifiers();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public Attribute getAttribute() {
        // Renvoie une valeur à l'appelant
        return attribute;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
