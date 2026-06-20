// Déclaration du paquet de ce fichier
package net.minestom.server.component;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.Result;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder.MapLike;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.NotNull;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.function.IntFunction;

/**
 * <p>A component list, always stored as a patch of added and removed components (even if none are removed).</p>
 *
 * <p>The inner map contains the value for added components, null for removed components, and no entry for unmodified components.</p>
 *
 * @param components The component patch.
 */
// Déclaration de type (classe/interface/enum/record)
record DataComponentMapImpl(Int2ObjectMap<@Nullable Object> components) implements DataComponentMap {
    // Affecte une valeur
    private static final char REMOVAL_PREFIX = '!';

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isEmpty() {
        // Renvoie une valeur à l'appelant
        return components.isEmpty();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean has(DataComponent<?> component) {
        // Renvoie une valeur à l'appelant
        return components.containsKey(component.id()) && components.get(component.id()) != null;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T> @Nullable T get(DataComponent<T> component) {
        //noinspection unchecked
        // Renvoie une valeur à l'appelant
        return (T) components.get(component.id());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean has(DataComponentMap prototype, DataComponent<?> component) {
        // Embranchement : vérifie une condition
        if (components.containsKey(component.id())) {
            // Renvoie une valeur à l'appelant
            return components.get(component.id()) != null;
        // Branche alternative de la condition
        } else {
            // Renvoie une valeur à l'appelant
            return prototype.has(component);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T> @Nullable T get(DataComponentMap prototype, DataComponent<T> component) {
        // Embranchement : vérifie une condition
        if (components.containsKey(component.id())) {
            //noinspection unchecked
            // Renvoie une valeur à l'appelant
            return (T) components.get(component.id());
        // Branche alternative de la condition
        } else {
            // Renvoie une valeur à l'appelant
            return prototype.get(component);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T> DataComponentMap set(DataComponent<T> component, T value) {
        // Appelle une méthode
        Int2ObjectMap<Object> newComponents = new Int2ObjectArrayMap<>(components);
        // Appelle une méthode
        newComponents.put(component.id(), component.freeze(value));
        // Renvoie une valeur à l'appelant
        return new DataComponentMapImpl(newComponents);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public DataComponentMap remove(DataComponent<?> component) {
        // Appelle une méthode
        Int2ObjectMap<@Nullable Object> newComponents = new Int2ObjectArrayMap<>(components);
        // Appelle une méthode
        newComponents.put(component.id(), null);
        // Renvoie une valeur à l'appelant
        return new DataComponentMapImpl(newComponents);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<DataComponent.Value> entrySet() {
        // Embranchement : vérifie une condition
        if (components.isEmpty()) return List.of();
        // Appelle une méthode
        final List<DataComponent.Value> entries = new ArrayList<>(components.size());
        // Boucle : répète un bloc
        for (var entry : components.int2ObjectEntrySet())
            // Appelle une méthode
            entries.add(new DataComponent.Value(DataComponent.fromId(entry.getIntKey()), entry.getValue()));
        // Renvoie une valeur à l'appelant
        return entries;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Builder toBuilder() {
        // Renvoie une valeur à l'appelant
        return new BuilderImpl(new Int2ObjectArrayMap<>(components));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public PatchBuilder toPatchBuilder() {
        // Renvoie une valeur à l'appelant
        return new PatchBuilderImpl(new Int2ObjectArrayMap<>(components));
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record BuilderImpl(Int2ObjectMap<@Nullable Object> components) implements DataComponentMap.Builder {

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean has(DataComponent<?> component) {
            // Renvoie une valeur à l'appelant
            return components.get(component.id()) != null;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <T> @Nullable T get(DataComponent<T> component) {
            //noinspection unchecked
            // Renvoie une valeur à l'appelant
            return (T) components.get(component.id());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <T> Builder set(DataComponent<T> component, T value) {
            // Appelle une méthode
            components.put(component.id(), component.freeze(value));
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DataComponentMap build() {
            // Renvoie une valeur à l'appelant
            return new DataComponentMapImpl(new Int2ObjectArrayMap<>(components));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record PatchBuilderImpl(Int2ObjectMap<@Nullable Object> components) implements DataComponentMap.PatchBuilder {

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean has(DataComponent<?> component) {
            // Renvoie une valeur à l'appelant
            return components.get(component.id()) != null;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <T> @Nullable T get(DataComponent<T> component) {
            //noinspection unchecked
            // Renvoie une valeur à l'appelant
            return (T) components.get(component.id());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <T> PatchBuilder set(DataComponent<T> component, T value) {
            // Appelle une méthode
            components.put(component.id(), component.freeze(value));
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public PatchBuilder remove(DataComponent<?> component) {
            // Appelle une méthode
            components.put(component.id(), null);
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DataComponentMap build() {
            // Renvoie une valeur à l'appelant
            return new DataComponentMapImpl(new Int2ObjectArrayMap<>(components));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record NetworkTypeImpl(
            // Instruction de code
            IntFunction<@Nullable DataComponent<?>> idToType,
            // Instruction de code
            boolean isPatch, boolean isTrusted
    // Début d'une méthode/d'un bloc
    ) implements NetworkBuffer.Type<DataComponentMap> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, DataComponentMap value) {
            // Appelle une méthode
            final DataComponentMapImpl patch = (DataComponentMapImpl) value;
            // Affecte une valeur
            int added = 0;
            // Boucle : répète un bloc
            for (Object o : patch.components.values()) {
                // Embranchement : vérifie une condition
                if (o != null) added++;
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            buffer.write(NetworkBuffer.VAR_INT, added);
            // Embranchement : vérifie une condition
            if (isPatch) {
                // Appelle une méthode
                buffer.write(NetworkBuffer.VAR_INT, patch.components.size() - added);
            // Fin d'un bloc/d'une expression
            }
            // Boucle : répète un bloc
            for (var entry : patch.components.int2ObjectEntrySet()) {
                // Embranchement : vérifie une condition
                if (entry.getValue() == null) continue;

                // Appelle une méthode
                buffer.write(NetworkBuffer.VAR_INT, entry.getIntKey());
                //noinspection unchecked
                // Appelle une méthode
                DataComponent<Object> type = (DataComponent<@NotNull Object>) this.idToType.apply(entry.getIntKey());
                // Appelle une méthode
                Check.notNull(type, "Unknown component id: {0}", entry.getIntKey());
                // Embranchement : vérifie une condition
                if (isTrusted) {
                    // Appelle une méthode
                    type.write(buffer, entry.getValue());
                // Branche alternative de la condition
                } else {
                    // Need to length prefix it, so write to another buffer first then copy.
                    // Appelle une méthode
                    final byte[] componentData = NetworkBuffer.makeArray(b -> type.write(b, entry.getValue()), buffer.registries());
                    // Appelle une méthode
                    buffer.write(NetworkBuffer.BYTE_ARRAY, componentData);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Embranchement : vérifie une condition
            if (isPatch) {
                // Boucle : répète un bloc
                for (var entry : patch.components.int2ObjectEntrySet()) {
                    // Embranchement : vérifie une condition
                    if (entry.getValue() != null) continue;

                    // Appelle une méthode
                    buffer.write(NetworkBuffer.VAR_INT, entry.getIntKey());
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public DataComponentMap read(NetworkBuffer buffer) {
            // Appelle une méthode
            int added = buffer.read(NetworkBuffer.VAR_INT);
            // Appelle une méthode
            int removed = isPatch ? buffer.read(NetworkBuffer.VAR_INT) : 0;
            // Appelle une méthode
            Check.stateCondition(added + removed > 256, "Data component map too large: {0}", added + removed);
            // Appelle une méthode
            Int2ObjectMap<@Nullable Object> patch = new Int2ObjectArrayMap<>(added + removed);
            // Boucle : répète un bloc
            for (int i = 0; i < added; i++) {
                // Appelle une méthode
                int id = buffer.read(NetworkBuffer.VAR_INT);
                //noinspection unchecked
                // Appelle une méthode
                DataComponent<Object> type = (DataComponent<@NotNull Object>) this.idToType.apply(id);
                // Appelle une méthode
                Check.notNull(type, "Unknown component: {0}", id);
                // Embranchement : vérifie une condition
                if (isTrusted) {
                    // Appelle une méthode
                    patch.put(type.id(), type.read(buffer));
                // Branche alternative de la condition
                } else {
                    // Appelle une méthode
                    final byte[] array = buffer.read(NetworkBuffer.BYTE_ARRAY);
                    // Appelle une méthode
                    final NetworkBuffer tempBuffer = NetworkBuffer.wrap(array, 0, array.length, buffer.registries());
                    // Appelle une méthode
                    patch.put(type.id(), type.read(tempBuffer));
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Boucle : répète un bloc
            for (int i = 0; i < removed; i++) {
                // Appelle une méthode
                int id = buffer.read(NetworkBuffer.VAR_INT);
                // Appelle une méthode
                patch.put(id, null);
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return new DataComponentMapImpl(patch);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record CodecImpl(
            // Instruction de code
            IntFunction<@Nullable DataComponent<?>> idToType,
            // Instruction de code
            Function<String, @Nullable DataComponent<?>> nameToType,
            // Instruction de code
            boolean isPatch
    // Début d'une méthode/d'un bloc
    ) implements Codec<DataComponentMap> {
        // Début d'une méthode/d'un bloc
        CodecImpl {
            // Appelle une méthode
            Objects.requireNonNull(idToType, "idToType");
            // Appelle une méthode
            Objects.requireNonNull(nameToType, "nameToType");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<DataComponentMap> decode(Transcoder<D> coder, D value) {
            // Appelle une méthode
            final Result<MapLike<D>> mapResult = coder.getMap(value);
            // Embranchement : vérifie une condition
            if (!(mapResult instanceof Result.Ok(var map)))
                // Renvoie une valeur à l'appelant
                return mapResult.cast();
            // Embranchement : vérifie une condition
            if (map.isEmpty()) return new Result.Ok<>(EMPTY);

            // Appelle une méthode
            final Int2ObjectMap<@Nullable Object> patch = new Int2ObjectArrayMap<>(map.size());
            // Boucle : répète un bloc
            for (String key : map.keys()) {
                // Affecte une valeur
                boolean remove = false;
                // Embranchement : vérifie une condition
                if (!key.isEmpty() && key.charAt(0) == REMOVAL_PREFIX) {
                    // Appelle une méthode
                    key = key.substring(1);
                    // Affecte une valeur
                    remove = true;
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                final DataComponent<?> type = this.nameToType.apply(key);
                // Embranchement : vérifie une condition
                if (type == null) return new Result.Error<>("unknown data component: " + key);

                // Embranchement : vérifie une condition
                if (remove) {
                    // Embranchement : vérifie une condition
                    if (isPatch) patch.put(type.id(), null);
                    // Removing a component in an absolute (non-patch) builder is a noop because it is not yet present.
                // Branche alternative de la condition
                } else {
                    // Embranchement multiple (switch/case)
                    switch (map.getValue(key).map(v -> type.decode(coder, v))) {
                        // Embranchement multiple (switch/case)
                        case Result.Ok(Object componentData) -> patch.put(type.id(), componentData);
                        // Embranchement multiple (switch/case)
                        case Result.Error<?>(String message) -> {
                            // Renvoie une valeur à l'appelant
                            return new Result.Error<>(type.name() + ": " + message);
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(new DataComponentMapImpl(patch));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable DataComponentMap value) {
            // Embranchement : vérifie une condition
            if (value == null) return new Result.Error<>("null");
            // Appelle une méthode
            final DataComponentMapImpl patch = (DataComponentMapImpl) value;

            // Appelle une méthode
            final Transcoder.MapBuilder<D> map = coder.createMap();
            // Boucle : répète un bloc
            for (var entry : patch.components.int2ObjectEntrySet()) {
                //noinspection unchecked
                // Appelle une méthode
                DataComponent<Object> type = (DataComponent<@NotNull Object>) this.idToType.apply(entry.getIntKey());
                // Embranchement : vérifie une condition
                if (type == null) return new Result.Error<>("unknown data component id: " + entry.getIntKey());
                // Embranchement : vérifie une condition
                if (entry.getValue() == null) {
                    // Embranchement : vérifie une condition
                    if (isPatch) map.put(REMOVAL_PREFIX + type.name(), coder.createMap().build());
                    // Removing a component in an absolute (non-patch) builder is a noop because it is not yet present.
                // Branche alternative de la condition
                } else {
                    // Embranchement multiple (switch/case)
                    switch (type.encode(coder, entry.getValue())) {
                        // Embranchement multiple (switch/case)
                        case Result.Ok(D componentValue) -> map.put(type.name(), componentValue);
                        // Embranchement multiple (switch/case)
                        case Result.Error<?>(String message) -> {
                            // Renvoie une valeur à l'appelant
                            return new Result.Error<>(type.name() + ": " + message);
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(map.build());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
