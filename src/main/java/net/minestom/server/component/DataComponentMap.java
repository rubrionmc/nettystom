// Déclaration du paquet de ce fichier
package net.minestom.server.component;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.utils.Unit;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.function.IntFunction;

/**
 * <p>Represents any container of {@link DataComponent}s.</p>
 *
 * <p>This type is capable of storing a patch of added and removed components on top of a 'prototype', or existing
 * set of components. See {@link #diff(DataComponentMap, DataComponentMap)}.</p>
 */
// Annotation pour l'élément suivant
@ApiStatus.Experimental
// Déclaration de type (classe/interface/enum/record)
public sealed interface DataComponentMap extends DataComponent.Holder permits DataComponentMapImpl {
    // Appelle une méthode
    DataComponentMap EMPTY = new DataComponentMapImpl(Int2ObjectMaps.emptyMap());

    // Début d'une méthode/d'un bloc
    static DataComponentMap.Builder builder() {
        // Renvoie une valeur à l'appelant
        return new DataComponentMapImpl.BuilderImpl(new Int2ObjectArrayMap<>());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static DataComponentMap.PatchBuilder patchBuilder() {
        // Renvoie une valeur à l'appelant
        return new DataComponentMapImpl.PatchBuilderImpl(new Int2ObjectArrayMap<>());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Instruction de code
    static NetworkBuffer.Type<DataComponentMap> networkType(
            // Début d'une méthode/d'un bloc
            IntFunction<DataComponent<?>> idToType) {
        // Renvoie une valeur à l'appelant
        return new DataComponentMapImpl.NetworkTypeImpl(idToType, false, true);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a network type for the given component type. For internal use only, get the value from the target component class.
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Instruction de code
    static Codec<DataComponentMap> codec(
            // Instruction de code
            IntFunction<DataComponent<?>> idToType,
            // Instruction de code
            Function<String, DataComponent<?>> nameToType
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new DataComponentMapImpl.CodecImpl(idToType, nameToType, false);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a network type for the given component type. For internal use only, get the value from the target component class.
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static NetworkBuffer.Type<DataComponentMap> patchNetworkType(IntFunction<DataComponent<?>> idToType, boolean trusted) {
        // Renvoie une valeur à l'appelant
        return new DataComponentMapImpl.NetworkTypeImpl(idToType, true, trusted);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a network type for the given component type. For internal use only, get the value from the target component class.
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Instruction de code
    static Codec<DataComponentMap> patchCodec(
            // Instruction de code
            IntFunction<DataComponent<?>> idToType,
            // Instruction de code
            Function<String, DataComponent<?>> nameToType
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new DataComponentMapImpl.CodecImpl(idToType, nameToType, true);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static DataComponentMap diff(DataComponentMap prototype, DataComponentMap patch) {
        // Appelle une méthode
        final DataComponentMapImpl patchImpl = (DataComponentMapImpl) patch;
        // Embranchement : vérifie une condition
        if (patchImpl.components().isEmpty()) return EMPTY;

        // Appelle une méthode
        final DataComponentMapImpl protoImpl = (DataComponentMapImpl) prototype;

        // Appelle une méthode
        final Int2ObjectArrayMap<@Nullable Object> diff = new Int2ObjectArrayMap<>(patchImpl.components());
        // Appelle une méthode
        var iter = diff.int2ObjectEntrySet().fastIterator();
        // Boucle : répète un bloc
        while (iter.hasNext()) {
            // Affecte une valeur
            final var entry = iter.next(); // Entry in patch
            // Affecte une valeur
            final var protoComp = protoImpl.components().get(entry.getIntKey()); // Entry in prototype
            // Embranchement : vérifie une condition
            if (entry.getValue() == null) {
                // If the component is removed, remove it from the diff if it is not in the prototype
                // Embranchement : vérifie une condition
                if (!protoImpl.components().containsKey(entry.getIntKey())) {
                    // Appelle une méthode
                    iter.remove();
                // Fin d'un bloc/d'une expression
                }
            // Embranchement : vérifie une condition
            } else if (protoComp != null && protoComp.equals(entry.getValue())) {
                // If the component is the same as in the prototype, remove it from the diff
                // Appelle une méthode
                iter.remove();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return new DataComponentMapImpl(diff);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    boolean isEmpty();

    /**
     * Does a 'patch'ed has against the given prototype. That is, this map is treated as the primary source, but if
     * unspecified, the given prototype is used as a fallback.
     *
     * @param prototype The prototype to fall back to
     * @param component The component to check
     * @return True if the component is present (taking into account the prototype).
     */
    // Appelle une méthode
    boolean has(DataComponentMap prototype, DataComponent<?> component);

    /**
     * Does a 'patch'ed get against the given prototype. That is, this map is treated as the primary source, but if
     * unspecified, the given prototype is used as a fallback.
     *
     * @param prototype The prototype to fall back to
     * @param component The component to get
     * @return The value of the component, or null if not present (taking into account the prototype).
     * @param <T> The type of the component
     */
    // Appelle une méthode
    <T> @Nullable T get(DataComponentMap prototype, DataComponent<T> component);

    /**
     * Adds the component, overwriting any prior value if present.
     * <br>
     * Note: {@link DataComponent#freeze(Object)} will be called, so identity may be mutated.
     *
     * @param component component to set
     * @param value value of T
     * @param <T> the data component type
     * @return A new map with the component set to the value
     */
    // Appelle une méthode
    <T> DataComponentMap set(DataComponent<T> component, T value);

    // Début d'une méthode/d'un bloc
    default DataComponentMap set(DataComponent<Unit> component) {
        // Renvoie une valeur à l'appelant
        return set(component, Unit.INSTANCE);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Removes the component from the map (or patch).
     *
     * @param component The component to remove
     * @return A new map with the component removed
     */
    // Appelle une méthode
    DataComponentMap remove(DataComponent<?> component);

    // Appelle une méthode
    Collection<DataComponent.Value> entrySet();

    // Appelle une méthode
    Builder toBuilder();

    // Appelle une méthode
    PatchBuilder toPatchBuilder();

    // Déclaration de type (classe/interface/enum/record)
    sealed interface Builder extends DataComponent.Holder permits DataComponentMapImpl.BuilderImpl {

        // Appelle une méthode
        <T> Builder set(DataComponent<T> component, T value);

        // Début d'une méthode/d'un bloc
        default Builder set(DataComponent<Unit> component) {
            // Renvoie une valeur à l'appelant
            return set(component, Unit.INSTANCE);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        DataComponentMap build();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    sealed interface PatchBuilder extends DataComponent.Holder permits DataComponentMapImpl.PatchBuilderImpl {

        // Appelle une méthode
        <T> PatchBuilder set(DataComponent<T> component, T value);

        // Début d'une méthode/d'un bloc
        default PatchBuilder set(DataComponent<Unit> component) {
            // Renvoie une valeur à l'appelant
            return set(component, Unit.INSTANCE);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        PatchBuilder remove(DataComponent<?> component);

        // Appelle une méthode
        DataComponentMap build();
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
